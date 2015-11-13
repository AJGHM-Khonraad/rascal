package org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.Instructions;

import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.CodeBlock;
import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.MuPrimitive;
import org.rascalmpl.library.experiments.Compiler.RVM.ToJVM.BytecodeGenerator;

public class CallMuPrim2 extends Instruction {

	MuPrimitive muprim;

	public CallMuPrim2(CodeBlock ins, MuPrimitive muprim) {
		super(ins, Opcode.CALLMUPRIM2);
		this.muprim = muprim;
	}

	public String toString() {
		return "CALLMUPRIM " + muprim;
	}

	public void generate() {
		codeblock.addCode1(opcode.getOpcode(), muprim.ordinal());
	}
	
	public void generateByteCode(BytecodeGenerator codeEmittor, boolean debug) {
		if ( debug ) 
			codeEmittor.emitDebugCall(opcode.name());
		codeEmittor.emitInlineCallMuPrime(muprim, 2, debug);
	}
}
