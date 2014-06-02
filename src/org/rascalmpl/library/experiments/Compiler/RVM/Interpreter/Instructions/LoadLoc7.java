package org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.Instructions;

import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.CodeBlock;
import org.rascalmpl.library.experiments.Compiler.RVM.ToJVM.BytecodeGenerator;

public class LoadLoc7 extends Instruction {

	public LoadLoc7(CodeBlock ins) {
		super(ins, Opcode.LOADLOC7);
	}

	public void generate(BytecodeGenerator codeEmittor, boolean debug) {
		codeEmittor.emitCall("insnLOADLOC7");
		codeblock.addCode0(opcode.getOpcode());
	}
}
