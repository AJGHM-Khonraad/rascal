package org.rascalmpl.library.experiments.CoreRascal.RVM.Instructions;

import org.rascalmpl.library.experiments.CoreRascal.RVM.CodeBlock;
import org.rascalmpl.library.experiments.CoreRascal.RVM.Primitive;

public enum Opcode {
	LOADCON (0, 2),
	LOADVAR (1, 3),
	LOADLOC (2, 2),
	STOREVAR (3, 3),
	STORELOC (4, 2),
	CALL (5, 2),
	CALLPRIM (6, 2),
	RETURN (7, 1),
	JMP (8, 2),
	JMPTRUE (9, 2),
	JMPFALSE (10, 2),
	LABEL (11, 0),
	HALT (12, 1),
	POP (13, 1),
	CALLDYN(14,1),
	LOADFUN(15,2);
	
	private final int op;
	private final int incr;
	
	private final static Opcode[] values = Opcode.values();
	
	public static Opcode fromInteger(int n){
		return values[n];
	}
	
	// TODO: compiler does not like Opcode.LOADCON.getOpcode() in case expressions
	// Here is a -- hopefully temporary -- hack that introduces explicit constants:
	// Beware! Should be in sync with the above operator codes!
	public static final int OP_LOADCON = 0;
	static public final int OP_LOADVAR = 1;
	static public final int OP_LOADLOC = 2;
	static public final int OP_STOREVAR= 3;
	static public final int OP_STORELOC = 4;
	static public final int OP_CALL = 5;
	static public final int OP_CALLPRIM = 6;
	static public final int OP_RETURN = 7;
	static public final int OP_JMP = 8;
	static public final int OP_JMPTRUE = 9;
	static public final int OP_JMPFALSE = 10;
	static public final int OP_LABEL = 11;
	static public final int OP_HALT = 12;
	static public final int OP_POP = 13;
	static public final int OP_CALLDYN = 14;
	static public final int OP_LOADFUN = 15;
	
	 Opcode(int op, int incr){
		this.op = op;
		this.incr = incr;
	}
	
	public int getIncrement(){
		return incr;
	}
	
	public int getOpcode(){
		return op;
	}
	
	public static String toString(CodeBlock ins, Opcode opc, int pc){
		switch(opc){
		case LOADCON:
			return "LOADCON " + ins.finalCode[pc + 1]  + " [" + ins.findConstantName(ins.finalCode[pc + 1]) + "]";
			
		case LOADVAR:
			return "LOADVAR " + ins.finalCode[pc + 1] + ", " + ins.finalCode[pc + 2];
			
		case LOADLOC:
			return "LOADLOC " + ins.finalCode[pc + 1];
			
		case STOREVAR:
			return "STOREVAR " + ins.finalCode[pc + 1] + ", " + ins.finalCode[pc + 2];	
			
		case STORELOC:
			return "STORELOC " + ins.finalCode[pc + 1];
			
		case CALL:
			return "CALL " + ins.finalCode[pc + 1]  + " [" + ins.findCodeName(ins.finalCode[pc + 1]) + "]";
			
		case CALLPRIM:
			return "CALLPRIM " + ins.finalCode[pc + 1] + " [" + Primitive.fromInteger(ins.finalCode[pc + 1]).name() + "]";
			
		case RETURN:
			return "RETURN";
			
		case JMP:
			return "JMP " + ins.finalCode[pc + 1];
			
		case JMPTRUE:
			return "JMPTRUE " + ins.finalCode[pc + 1];
			
		case JMPFALSE:
			return "JMPFALSE " + ins.finalCode[pc + 1];
			
		case LABEL:
			break;
			
		case HALT:
			return "HALT";
			
		case POP: 
			return "POP";	
		case CALLDYN:
			return "CALLDYN";
		case LOADFUN:
			return "LOADFUN " + ins.finalCode[pc + 1]  + " [" + ins.findFunctionName(ins.finalCode[pc + 1]) + "]";
		}
		
		throw new RuntimeException("Cannot happen: unrecognized opcode " + opc);
	}
}
