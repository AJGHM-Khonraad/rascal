package org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.Instructions;

import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.CodeBlock;
import org.rascalmpl.library.experiments.Compiler.RVM.ToJVM.BytecodeGenerator;

public class JmpFalse extends Instruction {

	String label;

	public JmpFalse(CodeBlock ins, String label) {
		super(ins, Opcode.JMPFALSE);
		this.label = label;
	}

	public String toString() {
		return "JMPFALSE " + label + " [" + codeblock.getLabelPC(label) + "]";
	}

	public void generate(BytecodeGenerator codeEmittor, boolean debug) {

		if ( !debug ) 
			codeEmittor.emitDebugCall(opcode.name());
		
		codeEmittor.emitJMPFALSE(label, debug);
		codeblock.addCode1(opcode.getOpcode(), codeblock.getLabelPC(label));
	}

	public void generateByteCode(BytecodeGenerator codeEmittor, boolean debug) {
		if ( debug ) 
			codeEmittor.emitDebugCall(opcode.name());
		
		codeEmittor.emitJMPFALSE(label, debug);
	}
}
