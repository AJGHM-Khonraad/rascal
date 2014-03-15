package org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.Instructions;

import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.CodeBlock;
import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.Generator;

public class StoreLoc extends  Instruction {

	int pos;
	
	public StoreLoc(CodeBlock ins, int pos){
		super(ins, Opcode.STORELOC);
		this.codeblock = ins;
		this.pos = pos;
	}
	
	public String toString() { return "STORELOC " + pos; }
	
	public void generate(Generator codeEmittor){
		codeEmittor.emitCall("insnSTORELOC", pos);
		codeblock.addCode1(opcode.getOpcode(), pos);
	}
}
