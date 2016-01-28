package org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.Instructions;

import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.CodeBlock;
import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.MuPrimitive;
import org.rascalmpl.library.experiments.Compiler.RVM.ToJVM.BytecodeGenerator;

public class PushCallMuPrim0 extends Instruction {

	MuPrimitive muprim;

	public PushCallMuPrim0(CodeBlock ins, MuPrimitive muprim) {
		super(ins, Opcode.PUSHCALLMUPRIM0);
		this.muprim = muprim;
	}

	public String toString() {
		return "PUSHCALLMUPRIM0 " + muprim ;
	}

	public void generate() {
		codeblock.addCode1(opcode.getOpcode(), muprim.ordinal());
	}
	
	public void generateByteCode(BytecodeGenerator codeEmittor, boolean debug) {
		if ( debug ) 
			codeEmittor.emitDebugCall(opcode.name());
		codeEmittor.emitInlinePushCallMuPrim0(muprim, debug);
	}
}
