package org.rascalmpl.library.experiments.CoreRascal.RVM.Instructions;

import org.rascalmpl.library.experiments.CoreRascal.RVM.CodeBlock;

public class Return extends Instruction {

	public Return(CodeBlock ins){
		super(ins, Opcode.RETURN);
	}

}
