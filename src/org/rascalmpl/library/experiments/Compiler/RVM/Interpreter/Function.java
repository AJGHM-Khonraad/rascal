package org.rascalmpl.library.experiments.Compiler.RVM.Interpreter;

import java.util.Map;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.ITuple;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;

public class Function {
	final public String name;
	public int   funId ;    // Id of function in functionMap, used for dynamic invocation.
	final Type ftype;
	public int scopeId;
	public String funIn;
	public int scopeIn = -1;
	public final int nformals;
	public final int nlocals;
	public final int maxstack;
	final CodeBlock codeblock;
	public IValue[] constantStore;
	public Type[] typeConstantStore;

	public int continuationPoints = 0;

	int[] froms;
	int[] tos;
	int[] types;
	int[] handlers;

	boolean isCoroutine = false;
	int[] refs;

	boolean isVarArgs = false;

	public Function(String name, Type ftype, String funIn, int nformals, int nlocals, int maxstack, CodeBlock codeblock, int continuationPoint) {
		this.name = name;
		this.ftype = ftype;
		this.funIn = funIn;
		this.nformals = nformals;
		this.nlocals = nlocals;
		this.maxstack = maxstack;
		this.codeblock = codeblock;
		this.continuationPoints = continuationPoint;
	}

	public void finalize(Generator codeEmittor, Map<String, Integer> codeMap, Map<String, Integer> constructorMap, Map<String, Integer> resolver, boolean listing) {

		codeEmittor.enableOutput(true);

//		if (name.contains("Library"))
//			codeEmittor.enableOutput(false);
//		if (name.contains("init"))
//			codeEmittor.enableOutput(false);
//		if (name.contains("Simple_testsuite"))
//			codeEmittor.enableOutput(false);
//		if (name.contains("Exception"))
//			codeEmittor.enableOutput(false);

		codeEmittor.emitMethod(NameMangler.mangle(name),continuationPoints,false);

		codeblock.done(codeEmittor, name, codeMap, constructorMap, resolver, listing);
		this.scopeId = codeblock.getFunctionIndex(name);
		if (funIn != null) {
			this.scopeIn = codeblock.getFunctionIndex(funIn);
		}
		this.constantStore = codeblock.getConstants();
		this.typeConstantStore = codeblock.getTypeConstants();

		codeEmittor.closeMethod();
		codeEmittor.enableOutput(true);
	}

	public void attachExceptionTable(IList exceptions, RVM rvm) {
		froms = new int[exceptions.length()];
		tos = new int[exceptions.length()];
		types = new int[exceptions.length()];
		handlers = new int[exceptions.length()];

		int i = 0;
		for (IValue entry : exceptions) {
			ITuple tuple = (ITuple) entry;
			String from = ((IString) tuple.get(0)).getValue();
			String to = ((IString) tuple.get(1)).getValue();
			Type type = rvm.symbolToType((IConstructor) tuple.get(2));
			String handler = ((IString) tuple.get(3)).getValue();

			froms[i] = codeblock.getLabelPC(from);
			tos[i] = codeblock.getLabelPC(to);
			types[i] = codeblock.getTypeConstantIndex(type);
			handlers[i] = codeblock.getLabelPC(handler);
			i++;
		}
	}

	public int getHandler(int pc, Type type) {
		int i = 0;
		for (int from : froms) {
			if (pc >= from) {
				if (pc < tos[i]) {
					// In the range...
					if (type.isSubtypeOf(codeblock.getConstantType(types[i]))) {
						return handlers[i];
					}
				}
			}
			i++;
		}
		return -1;
	}

	public String getName() {
		return name;
	}

}
