package org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.Instructions;

import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.CodeBlock;
import org.rascalmpl.library.experiments.Compiler.RVM.ToJVM.BytecodeGenerator;

public class Guard extends Instruction {
	private int continuationPoint ;
	
	public Guard(CodeBlock ins, int continuationPoint) {
		super(ins, Opcode.GUARD);
		this.continuationPoint = continuationPoint ;
	}
	public void generate(BytecodeGenerator codeEmittor, boolean debug){
		
		if ( !debug ) 
			codeEmittor.emitDebugCall(opcode.name());
		

		codeEmittor.emitInlineGuard(continuationPoint,debug) ;
		codeblock.addCode0(opcode.getOpcode());
	}
}
