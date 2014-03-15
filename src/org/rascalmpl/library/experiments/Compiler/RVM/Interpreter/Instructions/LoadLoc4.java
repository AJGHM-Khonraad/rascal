package org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.Instructions;

import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.CodeBlock;
import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.Generator;


public class LoadLoc4 extends Instruction {

	public LoadLoc4(CodeBlock ins){
		super(ins, Opcode.LOADLOC4);
	}
	public void generate(Generator codeEmittor){
		System.out.println("LOADLOC4");
		codeEmittor.emitCall("insnLOADLOC4");
		codeblock.addCode0(opcode.getOpcode());
	}
}
