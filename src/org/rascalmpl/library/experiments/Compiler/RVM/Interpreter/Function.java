package org.rascalmpl.library.experiments.Compiler.RVM.Interpreter;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.Map;

import org.rascalmpl.interpreter.result.util.MemoizationCache;
import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.serialize.RVMExecutableReader;
import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.serialize.RVMExecutableWriter;
import org.rascalmpl.value.IConstructor;
import org.rascalmpl.value.IInteger;
import org.rascalmpl.value.IList;
import org.rascalmpl.value.IMap;
import org.rascalmpl.value.ISourceLocation;
import org.rascalmpl.value.IString;
import org.rascalmpl.value.ITuple;
import org.rascalmpl.value.IValue;
import org.rascalmpl.value.IValueFactory;
import org.rascalmpl.value.type.Type;
import org.rascalmpl.value.type.TypeStore;

/**
 * Function contains all data needed for a single RVM function
 *
 * Function is serialized by write/read also defined in this class, make sure that
 * all fields declared here are synced with the serializer.
 */

public class Function  {
//	private static final long serialVersionUID = -1741144671553091111L;
	
	String name;
	Type ftype;
	int scopeId;
	String funIn;
	public int scopeIn = -1;
	public int nformals;
	private int nlocals;
	boolean isDefault;
	int maxstack;
	public CodeBlock codeblock;
	public IValue[] constantStore;			
	public Type[] typeConstantStore;
	boolean concreteArg = false;
	int abstractFingerprint = 0;
	int concreteFingerprint = 0;

	int[] froms;
	int[] tos;
	public int[] types;
	int[] handlers;
	int[] fromSPs;
	int lastHandler = -1;

	public Integer funId; // USED in dynRun to find the function, in the JVM version only.

	public String[] fromLabels;
	public String[] toLabels;
    public String[] handlerLabels;
    public int[] fromSPsCorrected;
	
	public int continuationPoints = 0;
	
	boolean isCoroutine = false;
	int[] refs;

	boolean isVarArgs = false;

	public ISourceLocation src;			
	IMap localNames;
	
	// transient fields 
	transient static IValueFactory vf;
	transient SoftReference<MemoizationCache<IValue>> memoization;
	
	public static void initSerialization(IValueFactory vfactory, TypeStore ts){
		vf = vfactory;
	}
	
	public Function(final String name, final Type ftype, final String funIn, final int nformals, final int nlocals, boolean isDefault, final IMap localNames, 
			 final int maxstack, boolean concreteArg, int abstractFingerprint,
			int concreteFingerprint, final CodeBlock codeblock, final ISourceLocation src, int ctpt){
		this.name = name;
		this.ftype = ftype;
		this.funIn = funIn;
		this.nformals = nformals;
		this.setNlocals(nlocals);
		this.isDefault = isDefault;
		this.localNames = localNames;
		this.maxstack = maxstack;
		this.concreteArg = concreteArg;
		this.abstractFingerprint = abstractFingerprint;
		this.concreteFingerprint = concreteFingerprint;
		this.codeblock = codeblock;
		this.src = src;
		this.continuationPoints = ctpt ;
	}
	
	Function(final String name, final Type ftype, final String funIn, final int nformals, final int nlocals, boolean isDefault, final IMap localNames, 
			 final int maxstack, boolean concreteArg, int abstractFingerprint,
			int concreteFingerprint, final CodeBlock codeblock, final ISourceLocation src, int scopeIn, IValue[] constantStore, Type[] typeConstantStore,
			int[] froms, int[] tos, int[] types, int[] handlers, int[] fromSPs, int lastHandler, int scopeId,
			boolean isCoroutine, int[] refs, boolean isVarArgs, int ctpt){
		this.name = name;
		this.ftype = ftype;
		this.funIn = funIn;
		this.nformals = nformals;
		this.setNlocals(nlocals);
		this.isDefault = isDefault;
		this.localNames = localNames;
		this.maxstack = maxstack;
		this.concreteArg = concreteArg;
		this.abstractFingerprint = abstractFingerprint;
		this.concreteFingerprint = concreteFingerprint;
		this.codeblock = codeblock;
		this.src = src;
		this.scopeIn = scopeIn;
		this.constantStore = constantStore;
		this.typeConstantStore = typeConstantStore;
		this.froms = froms;
		this.tos = tos;
		this.types = types;
		this.handlers = handlers;
		this.fromSPs = fromSPs;
		this.lastHandler = lastHandler;
		this.scopeId = scopeId;
		this.isCoroutine = isCoroutine;
		this.refs = refs;
		this.isVarArgs = isVarArgs;
		this.continuationPoints = ctpt ;
	}
	
	public void  finalize(final Map<String, Integer> codeMap, final Map<String, Integer> constructorMap, final Map<String, Integer> resolver){
		if(constructorMap == null){
			System.out.println("finalize: null");
		}
		codeblock.done(name, codeMap, constructorMap, resolver);
		this.scopeId = codeblock.getFunctionIndex(name);
		if(funIn.length() != 0) {
			this.scopeIn = codeblock.getFunctionIndex(funIn);
		}
		this.constantStore = codeblock.getConstants();
		this.typeConstantStore = codeblock.getTypeConstants();
	}
	
	public void clearForJVM(){
		codeblock.clearForJVM();
	}
	
	public void attachExceptionTable(final IList exceptions, final RVMLinker rascalLinker) {
			froms = new int[exceptions.length()];
			tos = new int[exceptions.length()];
			types = new int[exceptions.length()];
			handlers = new int[exceptions.length()];
			fromSPs = new int[exceptions.length()];
			fromSPsCorrected = new int[exceptions.length()];

			fromLabels = new String[exceptions.length()];
			toLabels = new String[exceptions.length()];
			handlerLabels = new String[exceptions.length()];
					
			int i = 0;
			for(IValue entry : exceptions) {
				ITuple tuple = (ITuple) entry;
				String from = ((IString) tuple.get(0)).getValue();
				String to = ((IString) tuple.get(1)).getValue();
				Type type = rascalLinker.symbolToType((IConstructor) tuple.get(2));
				String handler = ((IString) tuple.get(3)).getValue();
				int fromSP =  ((IInteger) tuple.get(4)).intValue();
				
				froms[i] = codeblock.getLabelPC(from);
				tos[i] = codeblock.getLabelPC(to);
				types[i] = codeblock.getTypeConstantIndex(type);
				handlers[i] = codeblock.getLabelPC(handler);	
				fromSPs[i] = fromSP;
				fromSPsCorrected[i] = fromSP + getNlocals();
				fromLabels[i] = from;
				toLabels[i] = to;
				handlerLabels[i] = handler;			

				i++;
			}
	}
	
	public int getHandler(final int pc, final Type type) {
		int i = 0;
		lastHandler = -1;
		for(int from : froms) {
			if(pc >= from) {
				if(pc < tos[i]) {
					// In the range...
					if(type.isSubtypeOf(codeblock.getConstantType(types[i]))) {
						lastHandler = i;
						return handlers[i];
					}
				}
			}
			i++;
		}
		return -1;
	}
	
	public int getFromSP(){
		return getNlocals() + fromSPs[lastHandler];
	}
	
	public String getName() {
		return name;
	}
	
	public int getNlocals() {
		return nlocals;
	}

	public void setNlocals(int nlocals) {
		this.nlocals = nlocals;
	}

	public String getPrintableName(){
		int from = name.lastIndexOf("/")+1;
		int to = name.indexOf("(", from);
		if(to < 0){
			to = name.length();
		}
		return name.substring(from, to);
	}
	
	public String getQualifiedName(){
		return name.substring(0, name.indexOf("("));
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("FUNCTION ").append(name).append(" ->> ").append(ftype).append("\n");
		for(int i = 0; i < constantStore.length; i++){
			sb.append("\t constant "). append(i).append(": "). append(constantStore[i]).append("\n");
		}
		for(int i = 0; i < typeConstantStore.length; i++){
			sb.append("\t type constant "). append(i).append(": "). append(typeConstantStore[i]).append("\n");
		}
//		codeblock.toString() ;
		sb.append(codeblock.toString());
		return sb.toString();
	}
	
	public void write(RVMExecutableWriter out)	throws IOException {

		// String name;
		out.writeJString(name);

		// Type ftype;
		out.writeType(ftype);

		// int scopeId;
		out.writeInt(scopeId);

		// private String funIn;
		out.writeJString(funIn);

		// int scopeIn = -1;
		out.writeInt(scopeIn);

		// int nformals;
		out.writeInt(nformals);

		// int nlocals;
		out.writeInt(getNlocals());

		// boolean isDefault;
		out.writeBool(isDefault);

		// int maxstack;
		out.writeInt(maxstack);

		// CodeBlock codeblock;
		codeblock.write(out);

		// IValue[] constantStore;
		int n = constantStore.length;
		out.writeInt(n);

		for(int i = 0; i < n; i++){
			out.writeValue(constantStore[i]);
		}

		// Type[] typeConstantStore;
		n = typeConstantStore.length;
		out.writeInt(n);

		for(int i = 0; i < n; i++){
			out.writeType(RascalExecutionContext.shareTypeConstant(typeConstantStore[i]));
		}

		// boolean concreteArg = false;
		out.writeBool(concreteArg);

		// int abstractFingerprint = 0;
		out.writeInt(abstractFingerprint);

		// int concreteFingerprint = 0;
		out.writeInt(concreteFingerprint);

		// int[] froms;
		out.writeIntArray(froms);

		// int[] tos;
		out.writeIntArray(tos);

		// int[] types;
		out.writeIntArray(types);

		// int[] handlers;
		out.writeIntArray(handlers);

		// int[] fromSPs;
		out.writeIntArray(fromSPs);

		// int lastHandler = -1;
		out.writeInt(lastHandler);
		
		//public Integer funId; 
		out.writeInt(funId);

		// boolean isCoroutine = false;
		out.writeBool(isCoroutine);

		// int[] refs;
		out.writeIntArray(refs);

		// boolean isVarArgs = false;
		out.writeBool(isVarArgs);

		// ISourceLocation src;
		out.writeValue(src);

		// IMap localNames;
		out.writeValue(localNames);

		// int continuationPoints
		out.writeInt(continuationPoints);
	}
	
	public static Function read(RVMExecutableReader in) throws IOException 
	{

		// String name;
		String name = in.readJString();

		// Type ftype;
		Type ftype = in.readType();

		// int scopeId;
		Integer scopeId = in.readInt();

		// private String funIn;
		String funIn = in.readJString();

		// int scopeIn = -1;
		Integer scopeIn = in.readInt();

		// int nformals;
		Integer nformals = in.readInt();

		// int nlocals;
		Integer nlocals = in.readInt();

		// boolean isDefault;
		Boolean isDefault = in.readBool();

		// int maxstack;
		Integer maxstack = in.readInt();

		// CodeBlock codeblock;
		CodeBlock codeblock = CodeBlock.read(in);

		// IValue[] constantStore;
		int n = in.readInt();
		IValue[] constantStore = new IValue[n];

		for(int i = 0; i < n; i++){
			constantStore[i] = in.readValue();
		}

		// Type[] typeConstantStore;
		n = in.readInt();
		Type[] typeConstantStore = new Type[n];

		for(int i = 0; i < n; i++){
			typeConstantStore[i] = RascalExecutionContext.shareTypeConstant(in.readType());
		}

		// boolean concreteArg = false;
		Boolean concreteArg = in.readBool();

		// int abstractFingerprint = 0;
		Integer abstractFingerprint = in.readInt();

		// int concreteFingerprint = 0;
		Integer concreteFingerprint = in.readInt();

		// int[] froms;
		int[] froms = in.readIntArray();

		// int[] tos;
		int[] tos = in.readIntArray();

		// int[] types;
		int[] types = in.readIntArray();

		// int[] handlers;
		int[] handlers = in.readIntArray();

		// int[] fromSPs;
		int[] fromSPs = in.readIntArray();

		// int lastHandler = -1;
		Integer lastHandler = in.readInt();
		
		//public Integer funId; 
		Integer funId = in.readInt();

		// boolean isCoroutine = false;
		Boolean isCoroutine = in.readBool();

		// int[] refs;
		int[] refs = in.readIntArray();

		// boolean isVarArgs = false;
		Boolean isVarArgs = (Boolean)in.readBool();

		// ISourceLocation src;
		ISourceLocation src = (ISourceLocation) in.readValue();

		// IMap localNames;
		IMap localNames = (IMap) in.readValue();
		
		// int continuationPoints
		Integer continuationPoints = in.readInt();
		
		Function func = new Function(name, ftype, funIn, nformals, nlocals, isDefault, localNames, maxstack, concreteArg, abstractFingerprint, concreteFingerprint, 
				codeblock, src, scopeIn, constantStore, typeConstantStore,
				froms, tos, types, handlers, fromSPs, lastHandler, scopeId,
				isCoroutine, refs, isVarArgs, continuationPoints);
		func.funId = funId;
		return func;
	}
}