package org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.Instructions;

import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.CodeBlock;

public class JmpFalse extends Instruction {

	String label;
	
	public JmpFalse(CodeBlock ins, String label){
		super(ins, Opcode.JMPFALSE);
		this.label = label;
	}
	
	public String toString() { return "JMPFALSE " + label + " [" + codeblock.getLabelPC(label) + "]"; }
	
	public void generate(){
		codeblock.addCode(opcode.getOpcode());
		codeblock.addCode(codeblock.getLabelPC(label));
	}
}
