package org.rascalmpl.library.experiments.Compiler.RVM.Interpreter;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

import org.eclipse.imp.pdb.facts.IBool;
import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IInteger;
import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IListWriter;
import org.eclipse.imp.pdb.facts.IMap;
import org.eclipse.imp.pdb.facts.ISet;
import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.ITuple;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.eclipse.imp.pdb.facts.type.Type;
import org.rascalmpl.interpreter.DefaultTestResultListener;
import org.rascalmpl.interpreter.IEvaluatorContext;  // TODO: remove import? NOT YET: Only used as argument of reclective library function
import org.rascalmpl.interpreter.ITestResultListener;
import org.rascalmpl.interpreter.utils.Timing;
import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.Instructions.Opcode;

public class Execute {

	private IValueFactory vf;
	private static ITestResultListener testResultListener;

	public Execute(IValueFactory vf) {
		this.vf = vf;
	}
	
	String moduleInit(String moduleName){
		return  "/#" + moduleName + "_init(list(value());)#0";
	}
	
	String muModuleInit(String moduleName){
		return   "/#" + moduleName + "_init";
	}
	
	static void setTestResultListener(ITestResultListener trl){
		testResultListener = trl;
	}
	
	// Library function to execute a RVM program from Rascal

	public ITuple executeProgram(IConstructor program,
								 IMap imported_types,
								 IList imported_functions,
								 IList imported_overloaded_functions,
								 IMap imported_overloading_resolvers,
								 IList argumentsAsList,
								 IBool debug, 
								 IBool testsuite, 
								 IBool profile, 
								 IBool trackCalls, 
								 IBool coverage,
								 IEvaluatorContext ctx) {
			
		boolean isTestSuite = testsuite.getValue();
		String moduleName = ((IString) program.get("name")).getValue();
		
		String main = isTestSuite ? "/<moduleName>_testsuite(list(value());)#0" : "/main(list(value());)#0";
		String mu_main = isTestSuite ? "/TESTSUITE" : "/MAIN";
		
		String module_init = moduleInit(moduleName);
		String mu_module_init = muModuleInit(moduleName);
				
		String uid_main = null;
		String uid_module_init = null;
		
		PrintWriter stdout = ctx.getStdOut();
		PrintWriter stderr = ctx.getStdErr();
		
		if(testResultListener == null){
			testResultListener = (ITestResultListener) new DefaultTestResultListener(stderr);
		}
		
		IMap symbol_definitions = (IMap) program.get("symbol_definitions");
		
		RVM rvm = new RVM(new RascalExecutionContext(vf, symbol_definitions, debug.getValue(), profile.getValue(), trackCalls.getValue(), coverage.getValue(), ctx, testResultListener));
		
		ProfileLocationCollector profilingCollector = null;
		CoverageLocationCollector coverageCollector = null;
		
		ArrayList<String> initializers = new ArrayList<String>();  	// initializers of imported modules
		ArrayList<String> testsuites =  new ArrayList<String>();	// testsuites of imported modules
		
		Iterator<Entry<IValue, IValue>> entries = imported_types.entryIterator();
		while(entries.hasNext()) {
			Entry<IValue, IValue> entry = entries.next();
			rvm.declareConstructor(((IString) entry.getKey()).getValue(), (IConstructor) entry.getValue());
		}
		
		for(IValue imp : imported_functions){
			IConstructor declaration = (IConstructor) imp;
//			if(((IString) declaration.get("qname")).getValue().indexOf("complement") > 0){
//				stdout.println("import function/coroutine: " + declaration.get("qname") + ", " + declaration.get("src"));
//			}
			if (declaration.getName().contentEquals("FUNCTION")) {
				String name = ((IString) declaration.get("qname")).getValue();
				
				if(name.endsWith("_init(list(value());)#0")){
					initializers.add(name);
				}
				if(name.endsWith("_testsuite(list(value());)#0")){
					testsuites.add(name);
				}
				loadInstructions(name, declaration, rvm, false);
			}
			if (declaration.getName().contentEquals("COROUTINE")) {
				String name = ((IString) declaration.get("qname")).getValue();
				loadInstructions(name, declaration, rvm, true);
			}
		}
		
		// Overloading resolution of imported functions
		rvm.addResolver(imported_overloading_resolvers);
		rvm.fillOverloadedStore(imported_overloaded_functions);

		IMap types = (IMap) program.get("types");
		entries = types.entryIterator();
		while(entries.hasNext()) {
			Entry<IValue, IValue> entry = entries.next();
			rvm.declareConstructor(((IString) entry.getKey()).getValue(), (IConstructor) entry.getValue());
		}
		
		IMap declarations = (IMap) program.get("declarations");
		for (IValue dname : declarations) {
			IConstructor declaration = (IConstructor) declarations.get(dname);

			if (declaration.getName().contentEquals("FUNCTION")) {
				String name = ((IString) declaration.get("qname")).getValue();
				if(name.endsWith(main) || name.endsWith(mu_main)) {
					uid_main = name;			// Get main's uid in current module
				}
				if(name.endsWith(module_init) || name.endsWith(mu_module_init)) {
					uid_module_init = name;		// Get module_init's uid in current module
				}
				if(name.endsWith("_testsuite(list(value());)#0")){
					testsuites.add(name);
				}
				loadInstructions(name, declaration, rvm, false);
			}
			
			if(declaration.getName().contentEquals("COROUTINE")) {
				String name = ((IString) declaration.get("qname")).getValue();
				loadInstructions(name, declaration, rvm, true);
			}
		}
		
		// Overloading resolution
		rvm.addResolver((IMap) program.get("resolver"));
		rvm.fillOverloadedStore((IList) program.get("overloaded_functions"));
		
		IValue[] arguments = new IValue[argumentsAsList.length()];
		for(int i = 0; i < argumentsAsList.length(); i++){
			arguments[i] = argumentsAsList.get(i);
		}
		
		if(profile.getValue()){
			profilingCollector = new ProfileLocationCollector();
			rvm.setLocationCollector(profilingCollector);
			profilingCollector.start();
	
		} else if(coverage.getValue()){
			coverageCollector = new CoverageLocationCollector();
			rvm.setLocationCollector(coverageCollector);
		}
		
		// Execute initializers of imported modules
		for(String initializer: initializers){
			rvm.executeProgram("UNDEFINED", initializer, arguments);
		}
		
		if((uid_module_init == null)) {
			// TODO remove collector
			throw new CompilerError("No module_init function found when loading RVM code!");
		}
		
		try {
			long start = Timing.getCpuTime();
			IValue result = null;
			if(isTestSuite){
				/*
				 * Execute as testsuite
				 */
				rvm.executeProgram("TESTSUITE", uid_module_init, arguments);

				IListWriter w = vf.listWriter();
				for(String uid_testsuite: testsuites){
					RascalPrimitive.reset();
					IList test_results = (IList)rvm.executeProgram("TESTSUITE", uid_testsuite, arguments);
					w.insertAll(test_results);
				}
				result = w.done();
			} else {
				/*
				 * Standard execution of main function
				 */
				if((uid_main == null)) {
					throw RascalRuntimeException.noMainFunction(null);
				}
				rvm.executeProgram(moduleName, uid_module_init, arguments);
				result = rvm.executeProgram(moduleName, uid_main, arguments);
			}
			long now = Timing.getCpuTime();
			MuPrimitive.exit();
			RascalPrimitive.exit();
			Opcode.exit();
			if(profile.getValue()){
				profilingCollector.report(rvm.getStdOut());
			} else if(coverage.getValue()){
				coverageCollector.report(rvm.getStdOut());
			}
			
			return vf.tuple((IValue) result, vf.integer((now - start)/1000000));
			
		} catch(Thrown e) {
			e.printStackTrace(stdout);
			return vf.tuple(vf.string("Runtime exception: " + e.value), vf.integer(0));
		}
	}
	
	// Get Boolean field from an instruction

	private boolean getBooleanField(IConstructor instruction, String field) {
		return ((IBool) instruction.get(field)).getValue();
	}

	// Get integer field from an instruction

	private int getIntField(IConstructor instruction, String field) {
		return ((IInteger) instruction.get(field)).intValue();
	}

	// Get String field from an instruction

	private String getStrField(IConstructor instruction, String field) {
		return ((IString) instruction.get(field)).getValue();
	}
	
	// Get Location field from an instruction

	private ISourceLocation getLocField(IConstructor instruction, String field) {
		return ((ISourceLocation) instruction.get(field));
	}

	/**
	 * Load the instructions of a function in a RVM.
	 * 
	 * @param name of the function to be loaded
	 * @param declaration the declaration of that function
	 * @param rvm in which function will be loaded
	 */
	private void loadInstructions(String name, IConstructor declaration, RVM rvm, boolean isCoroutine){
	
		Type ftype = isCoroutine ? null : rvm.symbolToType((IConstructor) declaration.get("ftype"));
		
		//System.err.println("loadInstructions: " + name + ": ftype = " + ftype + ", declaration = " + declaration);
		
		String scopeIn = ((IString) declaration.get("scopeIn")).getValue();
		if(scopeIn.equals("")) {
			scopeIn = null;
		}
		
		Integer nlocals = ((IInteger) declaration.get("nlocals")).intValue();
		IMap localNames = ((IMap) declaration.get("localNames"));
		Integer nformals = ((IInteger) declaration.get("nformals")).intValue();
		Integer maxstack = ((IInteger) declaration.get("maxStack")).intValue();
		IList code = (IList) declaration.get("instructions");
		ISourceLocation src = (ISourceLocation) declaration.get("src");
		CodeBlock codeblock = new CodeBlock(name, vf);
		// Loading instructions
		try {
		for (int i = 0; i < code.length(); i++) {
			IConstructor instruction = (IConstructor) code.get(i);
			String opcode = instruction.getName();

			switch (opcode) {
			case "LOADCON":
				codeblock.LOADCON(instruction.get("val"));
				break;

			case "LOADVAR":
				codeblock.LOADVAR(getStrField(instruction, "fuid"), 
								  getIntField(instruction, "pos"));
				break;

			case "LOADLOC":
				codeblock.LOADLOC(getIntField(instruction, "pos"));
				break;
				
			case "LOADCONT":
				codeblock.LOADCONT(getStrField(instruction, "fuid"));
				break;

			case "STOREVAR":
				codeblock.STOREVAR(getStrField(instruction, "fuid"), 
								   getIntField(instruction, "pos"));
				break;

			case "STORELOC":
				codeblock.STORELOC(getIntField(instruction, "pos"));
				break;

			case "LABEL":
				codeblock = codeblock.LABEL(getStrField(instruction, "label"));
				break;

			case "CALLPRIM":
				codeblock.CALLPRIM(RascalPrimitive.valueOf(getStrField(instruction, "name")), 
								   getIntField(instruction, "arity"), 
								   getLocField(instruction, "src"));
				break;

			case "CALLMUPRIM":
				codeblock.CALLMUPRIM(MuPrimitive.valueOf(getStrField(instruction, "name")), 
									 getIntField(instruction, "arity"));
				break;

			case "CALL":
				codeblock.CALL(getStrField(instruction, "fuid"), getIntField(instruction, "arity"));
				break;

			case "CALLDYN":
				codeblock.CALLDYN( getIntField(instruction, "arity"));
				break;
				
			case "APPLY":
				codeblock.APPLY(getStrField(instruction, "fuid"), 
								getIntField(instruction, "arity"));
				break;
				
			case "APPLYDYN":
				codeblock.APPLYDYN(getIntField(instruction, "arity"));
				break;

			case "LOADFUN":
				codeblock.LOADFUN(getStrField(instruction, "fuid"));
				break;

			case "RETURN0":
				codeblock.RETURN0();
				break;

			case "RETURN1":
				codeblock.RETURN1(getIntField(instruction, "arity"));
				break;

			case "JMP":
				codeblock.JMP(getStrField(instruction, "label"));
				break;

			case "JMPTRUE":
				codeblock.JMPTRUE(getStrField(instruction, "label"));
				break;

			case "JMPFALSE":
				codeblock.JMPFALSE(getStrField(instruction, "label"));
				break;

			case "HALT":
				codeblock.HALT();
				break;
				
			case "CREATE":
				codeblock.CREATE(getStrField(instruction, "fuid"), 
								 getIntField(instruction, "arity"));
				break;

			case "CREATEDYN":
				codeblock.CREATEDYN(getIntField(instruction, "arity"));
				break;
				
			case "RESET":
				codeblock.RESET();
				break;

			case "NEXT0":
				codeblock.NEXT0();
				break;

			case "NEXT1":
				codeblock.NEXT1();
				break;

			case "YIELD0":
				codeblock.YIELD0();
				break;

			case "YIELD1":
				codeblock.YIELD1(getIntField(instruction, "arity"));
				break;
				
			case "SHIFT":
				codeblock.SHIFT();
				break;

			case "PRINTLN":
				codeblock.PRINTLN(getIntField(instruction, "arity"));
				break;

			case "POP":
				codeblock.POP();
				break;

			case "LOADLOCREF":
				codeblock.LOADLOCREF(getIntField(instruction, "pos"));
				break;

			case "LOADVARREF":
				codeblock.LOADVARREF(getStrField(instruction, "fuid"), 
									 getIntField(instruction, "pos"));
				break;

			case "LOADLOCDEREF":
				codeblock.LOADLOCDEREF(getIntField(instruction, "pos"));
				break;

			case "LOADVARDEREF":
				codeblock.LOADVARDEREF(getStrField(instruction, "fuid"), 
									   getIntField(instruction, "pos"));
				break;

			case "STORELOCDEREF":
				codeblock.STORELOCDEREF(getIntField(instruction, "pos"));
				break;

			case "STOREVARDEREF":
				codeblock.STOREVARDEREF(getStrField(instruction, "fuid"), 
										getIntField(instruction, "pos"));
				break;

			case "LOAD_NESTED_FUN":
				codeblock.LOADNESTEDFUN(getStrField(instruction, "fuid"), 
										getStrField(instruction, "scopeIn"));
				break;

			case "LOADCONSTR":
				codeblock.LOADCONSTR(getStrField(instruction, "fuid"));
				break;

			case "CALLCONSTR":
				codeblock.CALLCONSTR(getStrField(instruction, "fuid"), 
									 getIntField(instruction, "arity")/*, getLocField(instruction, "src")*/);
				break;

			case "LOADTYPE":
				codeblock.LOADTYPE(rvm.symbolToType((IConstructor) instruction.get("type")));
				break;
			case "LOADBOOL":
				codeblock.LOADBOOL(getBooleanField(instruction, "bval"));
				break;

			case "LOADINT":
				codeblock.LOADINT(getIntField(instruction, "nval"));
				break;

			case "FAILRETURN":
				codeblock.FAILRETURN();
				break;

			case "LOADOFUN" :
				codeblock.LOADOFUN(getStrField(instruction, "fuid"));
				break;

			case "OCALL" :
				codeblock.OCALL(getStrField(instruction, "fuid"), 
								getIntField(instruction, "arity"), 
								getLocField(instruction, "src"));
				break;

			case "OCALLDYN" :
				codeblock.OCALLDYN(rvm.symbolToType((IConstructor) instruction.get("types")), 
								   getIntField(instruction, "arity"), 
								   getLocField(instruction, "src"));
				break;

			case "CALLJAVA":
				codeblock.CALLJAVA(getStrField(instruction, "name"), 
						           getStrField(instruction, "class"), 
						 		   rvm.symbolToType((IConstructor) instruction.get("parameterTypes")), 
						 		   rvm.symbolToType((IConstructor) instruction.get("keywordTypes")), 
						 		   getIntField(instruction, "reflect"));
				break;

			case "THROW":
				codeblock.THROW(getLocField(instruction, "src"));
				break;
			
			case "TYPESWITCH":
				codeblock.TYPESWITCH((IList)instruction.get("labels"));
				break;
				
			case "UNWRAPTHROWNLOC":
				codeblock.UNWRAPTHROWNLOC(getIntField(instruction, "pos"));
				break;
				
			case "FILTERRETURN":
				codeblock.FILTERRETURN();
				break;
				
			case "EXHAUST":
				codeblock.EXHAUST();
				break;
				
			case "GUARD":
				codeblock.GUARD();
				break;
				
			case "SUBSCRIPTARRAY":
				codeblock.SUBSCRIPTARRAY();
				break;
				
			case "SUBSCRIPTLIST":
				codeblock.SUBSCRIPTLIST();
				break;
				
			case "LESSINT":
				codeblock.LESSINT();
				break;
				
			case "GREATEREQUALINT":
				codeblock.GREATEREQUALINT();
				break;
				
			case "ADDINT":
				codeblock.ADDINT();
				break;
				
			case "SUBTRACTINT":
				codeblock.SUBTRACTINT();
				break;
				
			case "ANDBOOL":
				codeblock.ANDBOOL();
				break;
				
			case "TYPEOF":
				codeblock.TYPEOF();
				break;
				
			case "SUBTYPE":
				codeblock.SUBTYPE();
				break;
				
			case "CHECKARGTYPEANDCOPY":
				codeblock.CHECKARGTYPEANDCOPY(getIntField(instruction, "pos1"),
									  rvm.symbolToType((IConstructor) instruction.get("type")),
									  getIntField(instruction, "pos2"));
				break;
				
			case "JMPINDEXED":
				codeblock.JMPINDEXED((IList)instruction.get("labels"));
				break;
				
			case "LOADLOCKWP":
				codeblock.LOADLOCKWP(getStrField(instruction, "name"));
				break;
				
			case "LOADVARKWP":
				codeblock.LOADVARKWP(getStrField(instruction, "fuid"), 
									 getStrField(instruction, "name"));
				break;
				
			case "STORELOCKWP":
				codeblock.STORELOCKWP(getStrField(instruction, "name"));
				break;
				
			case "STOREVARKWP":
				codeblock.STOREVARKWP(getStrField(instruction, "fuid"), 
									  getStrField(instruction, "name"));
				break;
				
			case "UNWRAPTHROWNVAR":
				codeblock.UNWRAPTHROWNVAR(getStrField(instruction, "fuid"), 
									      getIntField(instruction, "pos"));
				break;
			
			case "SWITCH":
				codeblock.SWITCH((IMap)instruction.get("caseLabels"),
								 getStrField(instruction, "caseDefault"));
				break;
				
			case "RESETLOC":
				codeblock.RESETLOC(getIntField(instruction, "pos"));
				break;	
				
			default:
				throw new CompilerError("In function " + name + ", unknown instruction: " + opcode);
			}

		}
		} catch (Exception e){
			throw new CompilerError("In function " + name + " : " + e.getMessage());
		}
		
		Function function = new Function(name, ftype, scopeIn, nformals, nlocals, localNames, maxstack, codeblock, src);
		
		IList exceptions = (IList) declaration.get("exceptions");
		function.attachExceptionTable(exceptions, rvm);
		
		if(isCoroutine) {
			function.isCoroutine = true;
			IList refList = (IList) declaration.get("refs");
			int[] refs = new int[refList.length()];
			int i = 0;
			for(IValue ref : refList) {
				refs[i++] = ((IInteger) ref).intValue();
			}
			function.refs = refs;
		} else {
			
			boolean isVarArgs = ((IBool) declaration.get("isVarArgs")).getValue();
			function.isVarArgs = isVarArgs;
		}
		rvm.declare(function);
	}

}
