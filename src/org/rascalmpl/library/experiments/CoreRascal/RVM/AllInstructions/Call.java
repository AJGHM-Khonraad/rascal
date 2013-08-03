package org.rascalmpl.library.experiments.CoreRascal.RVM.AllInstructions;

public class Call extends Instruction {

	String fun;
	
	Call(Instructions ins, String fun){
		super(ins, Opcode.CALL);
		this.fun = fun;
	}
	
	public String toString() { return "CALL " + fun + "[" + ins.codeMap.get(fun) + "]"; }
	
	public void generate(){
		ins.addCode(opcode.getOpcode());
		Object o = ins.codeMap.get(fun);
		if(o == null){
			throw new RuntimeException("Cannot happen: undefined constant " + fun);
		}
		ins.addCode((int)o);
	}

}
