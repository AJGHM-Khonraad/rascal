package org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.Instructions;

import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.CodeBlock;
import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.Generator;


public class LoadLoc0 extends Instruction {

	public LoadLoc0(CodeBlock ins){
		super(ins, Opcode.LOADLOC0);
	}
	public void generate(Generator codeEmittor){
		codeEmittor.emitCall("insnLOADLOC0");
		codeblock.addCode0(opcode.getOpcode());
	}
}
