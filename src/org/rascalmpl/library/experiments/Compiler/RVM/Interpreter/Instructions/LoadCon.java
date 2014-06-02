package org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.Instructions;

import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.CodeBlock;
import org.rascalmpl.library.experiments.Compiler.RVM.ToJVM.BytecodeGenerator;

public class LoadCon extends Instruction {

	int constant;
	
	public LoadCon(CodeBlock cb, int constant){
		super(cb, Opcode.LOADCON);
		this.constant = constant;
	}
	
	public String toString() { return "LOADCON " + constant + "[" + codeblock.getConstantValue(constant) + "]"; }
	
	public void generate(BytecodeGenerator codeEmittor, boolean dcode){
		
		//codeEmittor.emitInlineLoadCon(constant,dcode);
		codeEmittor.emitCall("insnLOADCON", constant);
		codeblock.addCode1(opcode.getOpcode(), constant);
	}
}
