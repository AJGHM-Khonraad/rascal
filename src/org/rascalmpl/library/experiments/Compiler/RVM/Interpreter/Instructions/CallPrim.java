package org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.Instructions;

import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.CodeBlock;
import org.rascalmpl.library.experiments.Compiler.RVM.ToJVM.BytecodeGenerator;
import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.RascalPrimitive;

public class CallPrim extends Instruction {

	RascalPrimitive prim;
	int arity;
	ISourceLocation src;
	
	public CallPrim(CodeBlock ins, RascalPrimitive prim, int arity, ISourceLocation src){
		super(ins, Opcode.CALLPRIM);
		this.prim = prim;
		this.arity = arity;
		this.src = src;
	}
	
	public String toString() { return "CALLPRIM " + prim + ", " + arity + ", " + src; }
	
	public void generate(BytecodeGenerator codeEmittor, boolean dcode){
		//codeEmittor.emitCall("insnCALLPRIM", prim.ordinal(), arity);
		
		codeEmittor.emitInlineCallPrime(prim, arity, dcode); 
		codeblock.addCode2(opcode.getOpcode(), prim.ordinal(), arity);
		codeblock.addCode(codeblock.getConstantIndex(src));
	}
}
