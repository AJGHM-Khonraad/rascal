package org.rascalmpl.library.experiments.Compiler.RVM.Interpreter;

import org.eclipse.imp.pdb.facts.IBool;
import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IInteger;
import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IMap;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.ITuple;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.rascalmpl.interpreter.IEvaluatorContext;

public class Execute {

	private IValueFactory vf;

	public Execute(IValueFactory vf) {
		this.vf = vf;
	}

	// Get integer field from an instruction

	private int getIntField(IConstructor instruction, String field) {
		return ((IInteger) instruction.get(field)).intValue();
	}

	// Get String field from an instruction

	private String getStrField(IConstructor instruction, String field) {
		return ((IString) instruction.get(field)).getValue();
	}

	// Library function to execute a RVM program from Rascal

	public ITuple executeProgram(IConstructor program, IBool debug,
			IInteger repeat, IEvaluatorContext ctx) {
		String func = "main";
		RVM rvm = new RVM(vf, ctx.getStdOut(), debug.getValue());

		IList types = (IList) program.get("types");
		for(IValue type : types) {
			rvm.declareConstructor((IConstructor) type);
		}
		IMap declarations = (IMap) program.get("declarations");

		for (IValue dname : declarations) {
			IConstructor declaration = (IConstructor) declarations.get(dname);

			if (declaration.getName().contentEquals("FUNCTION")) {

				String name = ((IString) declaration.get("name")).getValue();
				Integer scope = ((IInteger) declaration.get("scope")).intValue();
				Integer nlocals = ((IInteger) declaration.get("nlocals")).intValue();
				Integer nformals = ((IInteger) declaration.get("nformals")).intValue();
				Integer maxstack = ((IInteger) declaration.get("maxStack")).intValue();
				IList code = (IList) declaration.get("instructions");
				CodeBlock codeblock = new CodeBlock(vf);

				// Loading instructions
				for (int i = 0; i < code.length(); i++) {
					IConstructor instruction = (IConstructor) code.get(i);
					String opcode = instruction.getName();

					switch (opcode) {
					case "LOADCON":
						codeblock.LOADCON(instruction.get("val"));
						break;
						
					case "LOADVAR":
						codeblock.LOADVAR(getIntField(instruction, "scope"), getIntField(instruction, "pos"));
						break;
						
					case "LOADLOC":
						codeblock.LOADLOC(getIntField(instruction, "pos"));
						break;
						
					case "STOREVAR":
						codeblock.STOREVAR(getIntField(instruction, "scope"), getIntField(instruction, "pos"));
						break;
						
					case "STORELOC":
						codeblock.STORELOC(getIntField(instruction, "pos"));
						break;
						
					case "LABEL":
						codeblock = codeblock.LABEL(getStrField(instruction, "label"));
						break;
						
					case "CALLPRIM":
						codeblock.CALLPRIM(Primitive.valueOf(getStrField(instruction, "name")), getIntField(instruction, "arity"));
						break;
						
					case "CALL":
						codeblock.CALL(getStrField(instruction, "name"));
						break;
						
					case "CALLDYN":
						codeblock.CALLDYN();
						break;
						
					case "LOADFUN":
						codeblock.LOADFUN(getStrField(instruction, "name"));
						break;
						
					case "RETURN0":
						codeblock.RETURN0();
						break;
						
					case "RETURN1":
						codeblock.RETURN1();
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
						codeblock.CREATE(getStrField(instruction, "fun"), getIntField(instruction, "arity"));
						break;
						
					case "CREATEDYN":
						codeblock.CREATEDYN(getIntField(instruction, "arity"));
						break;
						
					case "INIT":
						codeblock.INIT(getIntField(instruction, "arity"));
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
						codeblock.YIELD1();
						break;
						
					case "HASNEXT":
						codeblock.HASNEXT();
						break;
						
					case "PRINTLN":
						codeblock.PRINTLN();
						break;
						
					case "POP":
						codeblock.POP();
						break;
						
					case "LOADLOC_AS_REF":
						codeblock.LOADLOCASREF(getIntField(instruction, "pos"));
						break;
						
					case "LOADVAR_AS_REF":
						codeblock.LOADVARASREF(getIntField(instruction, "scope"), getIntField(instruction, "pos"));
						break;
						
					case "LOADLOCREF":
						codeblock.LOADLOCREF(getIntField(instruction, "pos"));
						break;
						
					case "LOADVARREF":
						codeblock.LOADVARREF(getIntField(instruction, "scope"), getIntField(instruction, "pos"));
						break;
					
					case "STORELOCREF":
						codeblock.STORELOCREF(getIntField(instruction, "pos"));
						break;
						
					case "STOREVARREF":
						codeblock.STOREVARREF(getIntField(instruction, "scope"), getIntField(instruction, "pos"));
						break;
						
					case "LOAD_NESTED_FUN":
						codeblock.LOADNESTEDFUN(getStrField(instruction, "name"), getIntField(instruction, "scope"));
						break;
						
					case "LOADCONSTR":
						codeblock.LOADCONSTR(getStrField(instruction, "name"));
						break;
						
					case "CALLCONSTR":
						codeblock.CALLCONSTR(getStrField(instruction, "name"));
						break;
						
					case "LOADTYPE":
						codeblock.LOADTYPE(rvm.symbolToType((IConstructor) instruction.get("type")));
						break;
						
					case "LOADVARDYN":
						codeblock.LOADVARDYN();
						break;
					case "STOREVARDYN":
						codeblock.STOREVARDYN();
						break;
					
					default:
						throw new RuntimeException("PANIC: Unknown instruction: " + opcode + " has been used");
					}

				}
				rvm.declare(new Function(name, scope, nformals, nlocals,
						maxstack, codeblock));
			}
		}

		long start = System.currentTimeMillis();
		Object result = null;
		for (int i = 0; i < repeat.intValue(); i++)
			result = rvm.executeProgram(func, new IValue[] {});
		long now = System.currentTimeMillis();
		return vf.tuple((IValue) result, vf.integer(now - start));
	}

}
