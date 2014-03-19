package org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.Instructions;

import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.CodeBlock;
import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.Generator;

public class Jmp extends Instruction {

	String label;

	public Jmp(CodeBlock ins, String label){
		super(ins, Opcode.JMP);
		this.label = label;
	}
	
	public String toString() { return "JMP " + label + " [" + codeblock.getLabelPC(label) + "]"; }
	
	public void generate(Generator codeEmittor){
		
		System.out.println("\tJMP " + label + " [" + codeblock.getLabelPC(label) + "]");
		/* TODO debug */ codeEmittor.emitCall("dinsnJMP", codeblock.getLabelPC(label));
		
		codeEmittor.emitJMP(label);
		codeblock.addCode1(opcode.getOpcode(), codeblock.getLabelPC(label));
	}
}
