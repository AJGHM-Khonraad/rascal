package org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.Instructions;

import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.CodeBlock;
import org.rascalmpl.library.experiments.Compiler.RVM.ToJVM.BytecodeGenerator;

public class AndBool extends Instruction {
	
	public AndBool(CodeBlock ins) {
		super(ins, Opcode.ANDBOOL);
	}
	public void generate(BytecodeGenerator codeEmittor, boolean dcode){
		if ( !dcode ) 
			codeEmittor.emitDebugCall(opcode.name());
		
		codeEmittor.emitCallWithArgsSS("insnANDBOOL");
		codeblock.addCode0(opcode.getOpcode());
	}
}
