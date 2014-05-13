package org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.Instructions;

import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.CodeBlock;
import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.Generator;

public class LoadType extends Instruction {
	
	final int type;
	
	public LoadType(CodeBlock ins, int type) {
		super(ins, Opcode.LOADTYPE);
		this.type = type;
	}
	
	public String toString() { return "LOADTYPE " + type + "[" + codeblock.getConstantType(type) + "]"; }
	
	public void generate(Generator codeEmittor, boolean dcode){
		//codeEmittor.emitInlineLoadType(type, dcode);
		codeEmittor.emitCall("insnLOADTYPE", type) ;
		codeblock.addCode1(opcode.getOpcode(), type);
	}
}
