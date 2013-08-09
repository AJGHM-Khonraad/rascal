package org.rascalmpl.library.experiments.CoreRascal.RVM.Examples;

import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.rascalmpl.library.experiments.CoreRascal.RVM.CodeBlock;
import org.rascalmpl.library.experiments.CoreRascal.RVM.Function;
import org.rascalmpl.library.experiments.CoreRascal.RVM.Primitive;
import org.rascalmpl.library.experiments.CoreRascal.RVM.RVM;
import org.rascalmpl.values.ValueFactoryFactory;


public class Do {
		
	public static void main(String[] args) {
		RVM rvm = new RVM(ValueFactoryFactory.getValueFactory());
		IValueFactory vf = rvm.vf;
		
		rvm.declare(new Function("square", 1, 1, 1, 6, 
				new CodeBlock(vf).
					LOADLOC(0).
					LOADLOC(0).
					CALLPRIM(Primitive.multiplication_num_num).
					RETURN1()));
		
		rvm.declare(new Function("cube", 1, 1, 1, 6, 
				new CodeBlock(vf).
					LOADLOC(0).
					LOADLOC(0).
					CALLPRIM(Primitive.multiplication_num_num).
					LOADLOC(0).
					CALLPRIM(Primitive.multiplication_num_num).
					RETURN1()));
		
		rvm.declare(new Function("do", 1, 2, 2, 6, 
				new CodeBlock(vf).
					LOADLOC(1).
					LOADLOC(0).
					CALLDYN().
					RETURN1()));
		
		rvm.declare(new Function("main", 0, 0, 0, 7,
				new CodeBlock(vf).
					LOADFUN("cube").
					LOADCON(4).
					CALL("do").
					HALT()));
		
		rvm.executeProgram("main", new IValue[] {});
	}

}
