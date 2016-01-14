package org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.Instructions;

import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.CodeBlock;
import org.rascalmpl.library.experiments.Compiler.RVM.ToJVM.BytecodeGenerator;

public class ResetVar extends Instruction {

	final int pos;
	final String fuid;

	public ResetVar(CodeBlock ins, String fuid, int pos) {
		super(ins, Opcode.RESETVAR);
		this.fuid = fuid;
		this.pos = pos;
	}

	public String toString() {
		return "RESETVAR " + fuid + ", " + pos;
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
//TODO: adapt this!
		if (pos == -1) {
			codeEmittor.emitCallWithArgsSSFI("insnLOADVARmax", what, debug);
		} else {
			codeEmittor.emitCallWithArgsSSFII("insnLOADVAR", what, pos, debug);
		}

	}
}
