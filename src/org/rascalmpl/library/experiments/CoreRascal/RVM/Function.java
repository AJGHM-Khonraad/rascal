package org.rascalmpl.library.experiments.CoreRascal.RVM;

import org.rascalmpl.library.experiments.CoreRascal.RVM.AllInstructions.Instructions;

public class Function {
	 final String name;
	 final int scope;
	 final int nformals;
	 final int nlocals;
	 final int maxstack;
	 final Instructions instructions;
	
	public Function(String name, int scope, int nformals, int nlocals, int maxstack, Instructions instructions){
		this.name = name;
		this.scope = scope;
		this.nformals = nformals;
		this.nlocals = nlocals;
		this.maxstack = maxstack;
		this.instructions = instructions;
	}
}
