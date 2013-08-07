package org.rascalmpl.library.experiments.CoreRascal.RVM.Examples;

import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.rascalmpl.library.experiments.CoreRascal.RVM.CodeBlock;
import org.rascalmpl.library.experiments.CoreRascal.RVM.Function;
import org.rascalmpl.library.experiments.CoreRascal.RVM.Primitive;
import org.rascalmpl.library.experiments.CoreRascal.RVM.RVM;
import org.rascalmpl.values.ValueFactoryFactory;


public class Fac {
		
	public static void main(String[] args) {
		RVM rvm = new RVM(ValueFactoryFactory.getValueFactory());
		IValueFactory vf = rvm.vf;
		
		rvm.declareConst("ZERO", rvm.vf.integer(0));
		rvm.declareConst("ONE", rvm.vf.integer(1));
		rvm.declareConst("FOUR", rvm.vf.integer(4));
		rvm.declareConst("TEN", rvm.vf.integer(10));
		rvm.declareConst("THOUSAND", rvm.vf.integer(1000));
		rvm.declareConst("MANY", rvm.vf.integer(100000));
		
		rvm.declare(new Function("fac", 1, 1, 1, 6, 
				new CodeBlock(vf).
					loadloc(0).
					loadcon("ONE").
					callprim(Primitive.equal_num_num).
					jmpfalse("L").
					loadcon("ONE").
					ret1().
					label("L").
					loadloc(0).
					loadloc(0).
					loadcon("ONE").
					callprim(Primitive.substraction_num_num).
					call("fac").
					callprim(Primitive.multiplication_num_num).
					ret1()));
		
		rvm.declare(new Function("main_fac", 0, 0, 0, 7,
				new CodeBlock(vf).
					loadcon("FOUR").
					call("fac").
					halt()));
		
		rvm.declare(new Function("main_repeat", 0, 0, 2, 20,
				new CodeBlock(vf).
					loadcon("TEN").
					storeloc(0). // n
					loadcon("MANY").
					storeloc(1). // cnt
					label("L").
					loadloc(1). // cnt
					loadcon("ZERO").
					callprim(Primitive.greater_num_num).
					jmptrue("M").
					halt().
					label("M").
					loadloc(0).
					call( "fac").
					pop().
					loadloc(1).
					loadcon("ONE").
					callprim(Primitive.substraction_num_num).
					storeloc(1).
					jmp("L")));
		
		long total = 0;
		int times = 20;
		rvm.setDebug(true);
		
		for(int i = 0; i < times; i++){
			long start = System.currentTimeMillis();
			
			rvm.executeProgram("main_repeat", new IValue[] {});
			long now = System.currentTimeMillis();
			total += now - start;
			
		}
		System.out.println("RVM: average elapsed time in msecs:" + total/times);
	}

}
