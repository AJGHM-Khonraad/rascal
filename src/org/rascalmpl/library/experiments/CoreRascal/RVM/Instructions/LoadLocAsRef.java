package org.rascalmpl.library.experiments.CoreRascal.RVM.Instructions;

import org.rascalmpl.library.experiments.CoreRascal.RVM.CodeBlock;

public class LoadLocAsRef extends Instruction {
	
	final int pos;
	
	public LoadLocAsRef(CodeBlock ins, int pos) {
		super(ins, Opcode.LOADLOC_AS_REF);
		this.pos = pos;
	}

	public String toString() { return "LOADLOC_AS_REF " + pos; }
	
	public void generate(){
		codeblock.addCode(opcode.getOpcode());
		codeblock.addCode(pos);
	}
	
}
