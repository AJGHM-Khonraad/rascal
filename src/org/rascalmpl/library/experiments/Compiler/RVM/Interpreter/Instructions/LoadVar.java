package org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.Instructions;

import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.CodeBlock;
import org.rascalmpl.library.experiments.Compiler.RVM.ToJVM.BytecodeGenerator;

public class LoadVar extends Instruction {

	final int pos;
	final String fuid;

	public LoadVar(CodeBlock ins, String fuid, int pos) {
		super(ins, Opcode.LOADVAR);
		this.fuid = fuid;
		this.pos = pos;
	}

	public String toString() {
		return "LOADVAR " + fuid + ", " + pos;
	}

	public void generate() {
		int what = (pos == -1) ? codeblock.getConstantIndex(codeblock.vf.string(fuid)) : codeblock.getFunctionIndex(fuid);
		codeblock.addCode2(opcode.getOpcode(), what, pos);
	}

	public void generateByteCode(BytecodeGenerator codeEmittor, boolean debug) {
		if (debug)
			codeEmittor.emitDebugCall(opcode.name());
		
		int what = (pos == -1) ? codeblock.getConstantIndex(codeblock.vf.string(fuid)) : codeblock.getFunctionIndex(fuid);

		//codeEmittor.emitCallWithArgsSSFIIZ("insnLOADVAR", what, pos, pos == -1,debug);

		if (pos == -1) {
			codeEmittor.emitCallWithArgsFI_A("LOADVARMODULE", what, debug);
		} else {
			codeEmittor.emitCallWithArgsFII_A("LOADVARSCOPED", what, pos, debug);
		}

	}
}
