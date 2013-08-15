package org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.Examples;

import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.CodeBlock;
import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.Function;
import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.RVM;
import org.rascalmpl.values.ValueFactoryFactory;

public class Closure {

	public static void main(String[] args) {
		
		RVM rvm = new RVM(ValueFactoryFactory.getValueFactory());
		IValueFactory vf = rvm.vf;
		
		/* f() {
		 * 		n = 1;
		 * 		g() { 
		 * 			return n; 
		 * 		}
		 * 		return g;
		 * }
		 */
		rvm.declare(new Function("g", 1, 0, 0, 6,
				new CodeBlock(vf).
					LOADVAR(2,0).          // <<-
					RETURN1()
		));
		
		rvm.declare(new Function("f", 2, 0, 1, 6,
				new CodeBlock(vf).
					LOADCON(1).
					STORELOC(0).
					LOADNESTEDFUN("g", 2). // <<-
					RETURN1()
		));
		
		rvm.declare(new Function("main", 3, 1, 1, 6,
					new CodeBlock(vf).
						CALL("f").
						CALLDYN().
						RETURN1().
						HALT()));
	
		rvm.declare(new Function("#module_init", 0, 0, 1, 6, 
				new CodeBlock(vf)
					.LOADLOC(0)
					.CALL("main")
					.HALT()));

		rvm.executeProgram("main", new IValue[] {});
	}

}
