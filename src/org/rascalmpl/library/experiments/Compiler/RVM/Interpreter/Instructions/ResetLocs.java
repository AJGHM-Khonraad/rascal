package org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.Instructions;

import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.CodeBlock;
import org.rascalmpl.library.experiments.Compiler.RVM.ToJVM.BytecodeGenerator;

public class ResetLocs extends Instruction {

	int positions;
	
	public ResetLocs(CodeBlock ins, int positions){
		super(ins, Opcode.RESETLOCS);
		this.positions = positions;
	}
	
	public String toString() { return "RESETLOCS " + codeblock.getConstantValue(positions); }
	
	public void generate(BytecodeGenerator codeEmittor, boolean debug){
		if (!debug)
			codeEmittor.emitDebugCall(opcode.name());
		codeEmittor.emitInlineResetLocs(positions,codeblock.getConstantValue(positions), debug) ;
		codeblock.addCode1(opcode.getOpcode(), positions);
	}

	public void generateByteCode(BytecodeGenerator codeEmittor, boolean debug){
		if (debug)
			codeEmittor.emitDebugCall(opcode.name());
		codeEmittor.emitInlineResetLocs(positions,codeblock.getConstantValue(positions), debug) ;
	}
	
	public void generate(){
		codeblock.addCode1(opcode.getOpcode(), positions);
	}
}
