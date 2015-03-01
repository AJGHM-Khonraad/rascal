package org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.Instructions;

import org.eclipse.imp.pdb.facts.IMap;
import org.eclipse.imp.pdb.facts.IMapWriter;
import org.eclipse.imp.pdb.facts.ISet;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.IValue;
import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.CodeBlock;
import org.rascalmpl.library.experiments.Compiler.RVM.ToJVM.BytecodeGenerator;

public class Switch extends Instruction {
	IMap caseLabels;
	String caseDefault;
	
	public Switch(CodeBlock ins, IMap caseLabels, String caseDefault) {
		super(ins, Opcode.SWITCH);
		this.caseLabels = caseLabels;
		this.caseDefault = caseDefault;
	}

	public String toString() { 
		String res = "SWITCH (";
		String sep = "";
		for(IValue key : caseLabels){
			String label = ((IString)caseLabels.get(key)).getValue();
			res += sep + key + ": " + label;
			sep = ", ";
		}
		res += "), " + caseDefault;
		return res;
	}
	
	public void generate(BytecodeGenerator codeEmittor, boolean dcode){
		IMapWriter w = codeblock.vf.mapWriter();
		for(IValue key : caseLabels){
			String label = ((IString)caseLabels.get(key)).getValue();
			w.put(key, codeblock.vf.integer(codeblock.getLabelPC(label)));
		}
		codeEmittor.emitInlineSwitch(caseLabels, caseDefault, dcode) ;
		codeblock.addCode2(opcode.getOpcode(), 
							codeblock.getConstantIndex(w.done()), 
							codeblock.getLabelPC(caseDefault));
	}
}
