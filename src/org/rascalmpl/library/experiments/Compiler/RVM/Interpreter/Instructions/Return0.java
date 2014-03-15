package org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.Instructions;

import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.CodeBlock;
import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.Generator;

public class Return0 extends Instruction {
	
	public Return0(CodeBlock ins) {
		super(ins, Opcode.RETURN0);
	}
	public void generate(Generator codeEmittor){
		System.out.println("RETURN0");
		codeEmittor.emitReturn();
		codeblock.addCode0(opcode.getOpcode());
	}
}
