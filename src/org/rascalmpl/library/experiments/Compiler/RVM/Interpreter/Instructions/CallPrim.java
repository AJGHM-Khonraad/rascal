package org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.Instructions;

import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.CodeBlock;
import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.RascalPrimitive;

public class CallPrim extends Instruction {

	RascalPrimitive prim;
	int arity;
	
	public CallPrim(CodeBlock ins, RascalPrimitive prim, int arity){
		super(ins, Opcode.CALLPRIM);
		this.prim = prim;
		this.arity = arity;
	}
	
	public int spIncrement() {
		return arity + 1;
	}
	
	public String toString() { return "CALLPRIM " + prim + ", " + arity; }
	
	public void generate(){
		codeblock.addCode(opcode.getOpcode());
		codeblock.addCode(prim.ordinal());
		codeblock.addCode(arity);
	}
}
