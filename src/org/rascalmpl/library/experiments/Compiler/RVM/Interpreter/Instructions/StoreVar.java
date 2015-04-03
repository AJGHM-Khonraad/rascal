package org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.Instructions;

import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.CodeBlock;
import org.rascalmpl.library.experiments.Compiler.RVM.ToJVM.BytecodeGenerator;

public class StoreVar extends Instruction {

	final int pos;
	final String fuid;

	public StoreVar(CodeBlock ins, String fuid, int pos) {
		super(ins, Opcode.STOREVAR);
		this.fuid = fuid;
		this.pos = pos;
	}

	public String toString() {
		return "STOREVAR " + fuid + ", " + pos;
	}

	public void generate(BytecodeGenerator codeEmittor, boolean debug) {
		if (!debug)
			codeEmittor.emitDebugCall(opcode.name());

		int what = (pos == -1) ? codeblock.getConstantIndex(codeblock.vf.string(fuid)) : codeblock.getFunctionIndex(fuid);
		
		codeEmittor.emitVoidCallWithArgsSSFIIZ("insnSTOREVAR", what, pos, pos == -1 ,debug);
		
		codeblock.addCode2(opcode.getOpcode(), what, pos);
	}

	public void generateByteCode(BytecodeGenerator codeEmittor, boolean debug) {
		if (debug)
			codeEmittor.emitDebugCall(opcode.name());

		int what = (pos == -1) ? codeblock.getConstantIndex(codeblock.vf.string(fuid)) : codeblock.getFunctionIndex(fuid);
		
		codeEmittor.emitVoidCallWithArgsSSFIIZ("insnSTOREVAR", what, pos, pos == -1 ,debug);
	}
}
