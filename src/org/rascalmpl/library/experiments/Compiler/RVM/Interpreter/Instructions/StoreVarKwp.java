package org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.Instructions;

import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.CodeBlock;
import org.rascalmpl.library.experiments.Compiler.RVM.ToJVM.BytecodeGenerator;

public class StoreVarKwp extends Instruction {
	
	final String fuid;
	final String name;
	
	public StoreVarKwp(CodeBlock ins, String fuid, String name) {
		super(ins, Opcode.STOREVARKWP);
		this.fuid = fuid;
		this.name = name;
	}
	
	public String toString() { return "STOREVARKWP " + fuid + ", " + name + " [" + codeblock.getFunctionIndex(fuid) + ", " + codeblock.getConstantIndex(codeblock.vf.string(name)) + "]"; }
	
	public void generate(BytecodeGenerator codeEmittor, boolean dcode){
		if (!dcode)
			codeEmittor.emitDebugCall(opcode.name());
		
		codeEmittor.emitCall("insnSTOREVARKWP", codeblock.getFunctionIndex(fuid), codeblock.getConstantIndex(codeblock.vf.string(name)));
		
		codeblock.addCode2(opcode.getOpcode(), codeblock.getFunctionIndex(fuid), codeblock.getConstantIndex(codeblock.vf.string(name)));
	}

}
