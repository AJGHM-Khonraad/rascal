package org.rascalmpl.library.experiments.CoreRascal.RVM.Examples;

import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.rascalmpl.library.experiments.CoreRascal.RVM.CodeBlock;
import org.rascalmpl.library.experiments.CoreRascal.RVM.Function;
import org.rascalmpl.library.experiments.CoreRascal.RVM.Primitive;
import org.rascalmpl.library.experiments.CoreRascal.RVM.RVM;
import org.rascalmpl.values.ValueFactoryFactory;

public class CountDown_b {
	
	public static void main(String[] args) {
		
		RVM rvm = new RVM(ValueFactoryFactory.getValueFactory());
		IValueFactory v = rvm.vf;
		
		/*
		 * g (n) 
		 * { 
		 * 		while(n > 1) { 
		 * 			yield n; 
		 * 			n = n - 1; 
		 * 		}; 
		 * 		return 0; 
		 * }
		 */
		
		rvm.declare(new Function("g", 0, 1, 1, 6,
					new CodeBlock(v)
							.LABEL("LOOP")
							.LOADLOC(0)
							.LOADCON(0)
							.CALLPRIM(Primitive.greater_num_num)
							.JMPTRUE("BODY")
							.LOADCON(0)
							.RETURN1()
							.LABEL("BODY")
							.LOADLOC(0)
							.YIELD1()
							.LOADLOC(0)
							.LOADCON(1)
							.CALLPRIM(Primitive.subtraction_num_num)
							.STORELOC(0)
							.JMP("LOOP")));
		/*
		 * h() {
		 * n = 9 + 1;
		 * c = create(g);
		 * c.init(n);
		 * return c;
		 * }
		 */
		
		rvm.declare(new Function("h", 0, 0, 2, 6, 
					new CodeBlock(v)
						.LOADCON(9)
						.LOADCON(1)
						.CALLPRIM(Primitive.addition_num_num)
						.STORELOC(0)
						.CREATE("g")
						.STORELOC(1)
						.LOADLOC(0)
						.LOADLOC(1)
						.INIT()
						.POP()
						.LOADLOC(1)
						.RETURN1()));
		
		/*
		 * c1 = h();
		 * c2 = h();
		 * 
		 * count = 0;
		 * while(hasNext(c1)) {
		 * 		count = (c1.resume() + c2.resume()) + count;
		 * }
		 */
		/*
		 * result: 0
		 */
		rvm.declare(new Function("main", 0, 0, 3, 6,
					new CodeBlock(v)
						.CALL("h")
						.STORELOC(0)
						.CALL("h")
						.STORELOC(1)
						.LOADCON(0)
						.STORELOC(2)
						
						.LABEL("LOOP")
						.LOADLOC(0)
						.HASNEXT()
						//.loadloc(1)
						//.hasNext()
											
						.JMPTRUE("BODY")
						.HALT()
						.LABEL("BODY")
						.LOADLOC(0)
						.NEXT0()
						.LOADLOC(1)
						.NEXT0()
						.CALLPRIM(Primitive.addition_num_num)
						.LOADLOC(2)
						.CALLPRIM(Primitive.addition_num_num)
						.STORELOC(2)
						
						.JMP("LOOP")));
	
		rvm.executeProgram("main", new IValue[] {});
	}
	
}
