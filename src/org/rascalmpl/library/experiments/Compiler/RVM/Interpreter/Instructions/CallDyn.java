package org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.Instructions;

import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.CodeBlock;
import org.rascalmpl.library.experiments.Compiler.RVM.ToJVM.BytecodeGenerator;

public class CallDyn extends Instruction {
	
	final int arity;
	final int continuationPoint ;

	public CallDyn(CodeBlock ins, int arity, int cp){
		super(ins, Opcode.CALLDYN);
		this.arity = arity;
		this.continuationPoint = cp ;
	}
	
	public String toString() { return "CALLDYN " + arity; }
	
	public void generate(){
		codeblock.addCode1(opcode.getOpcode(), arity);
	}

	public void generateByteCode(BytecodeGenerator codeEmittor, boolean debug){
		if ( debug ) 
			codeEmittor.emitDebugCall(opcode.name());
		
		codeEmittor.emitInlineCalldyn(arity, continuationPoint,debug) ;
	}
}
