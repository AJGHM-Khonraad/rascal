package org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.Instructions;

import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.CodeBlock;
import org.rascalmpl.library.experiments.Compiler.RVM.ToJVM.BytecodeGenerator;

public class LoadInt extends Instruction {
	
	final int nval;
	
	public LoadInt(CodeBlock ins, int nval) {
		super(ins, Opcode.LOADINT);
		this.nval = nval;
	}
	
	public String toString() { return "LOADINT " + nval; }
	
	public void generate(BytecodeGenerator codeEmittor, boolean debug){
		if ( !debug ) 
			codeEmittor.emitDebugCall(opcode.name());
		
		codeEmittor.emitCallWithArgsSSI("insnLOADINT", nval, debug);
		codeblock.addCode1(opcode.getOpcode(), nval);
	}
}
