package org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.Instructions;

import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.CodeBlock;
import org.rascalmpl.library.experiments.Compiler.RVM.ToJVM.BytecodeGenerator;

public class SubType extends Instruction {
	
	public SubType(CodeBlock ins) {
		super(ins, Opcode.SUBTYPE);
	}
	public void generate(BytecodeGenerator codeEmittor, boolean debug){
		if (!debug)
			codeEmittor.emitDebugCall(opcode.name());
		
		codeEmittor.emitCallWithArgsSS("insnSUBTYPE");
		codeblock.addCode0(opcode.getOpcode());
	}

	public void generateByteCode(BytecodeGenerator codeEmittor, boolean debug){
		if (debug)
			codeEmittor.emitDebugCall(opcode.name());
		
		codeEmittor.emitCallWithArgsSS("insnSUBTYPE");
	}
}
