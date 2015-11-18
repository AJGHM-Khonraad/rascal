/**
 * 
 */
package org.rascalmpl.library.experiments.Compiler.RVM.Interpreter;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import org.rascalmpl.value.IValue;

public class RVMJVM extends RVM {

	RVMExecutable rrs;
	RascalExecutionContext rex;
	byte[] generatedRunner = null;
	String generatedName = null;
	RVMRun runner = null;

	/*
	 * 
	 */
	private static final long serialVersionUID = -3447489546163673435L;

	/**
	 * @param rrs
	 * @param rex
	 */
	public RVMJVM(RVMExecutable rrs, RascalExecutionContext rex) {
		super(rrs, rex);
		//if (rrs instanceof RVMJVMExecutable) {
			generatedRunner = rrs.getJvmByteCode();
			generatedName = rrs.getFullyQualifiedDottedName();
		//}
		this.rrs = rrs;
		this.rex = rex;
		try {
			createRunner();
		}
		catch(Exception e) {
			e.printStackTrace() ;
		}

	}

	private void createRunner() {
		// Oneshot classloader
		try {
			Class<?> generatedClass = new ClassLoader(RVMJVM.class.getClassLoader()) {
				public Class<?> defineClass(String name, byte[] bytes) {
					return super.defineClass(name, bytes, 0, bytes.length);
				}

				public Class<?> loadClass(String name) {
					try {
						return super.loadClass(name);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					return null;
				}
			}.defineClass(generatedName, generatedRunner);

			Constructor<?>[] cons = generatedClass.getConstructors();

			runner = (RVMRun) cons[0].newInstance(rrs, rex);
			// Inject is obsolete the constructor holds rrs.
			runner.inject(rrs.getFunctionStore(), rrs.getConstructorStore(), RVMExecutable.store, rrs.getFunctionMap());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	boolean useRVMInterpreter = false;
	public IValue executeProgram(String moduleName, String uid_main, IValue[] args, HashMap<String,IValue> kwArgs) {
		if (useRVMInterpreter) {
			return super.executeProgram(moduleName, uid_main, args, kwArgs);
		} else {
			rex.setCurrentModuleName(moduleName);

			Function main_function = functionStore.get(functionMap.get(uid_main));

			if (main_function == null) {
				throw new RuntimeException("PANIC: No function " + uid_main + " found");
			}

//			if (main_function.nformals != 1) { // Empty map of keyword parameters
//				throw new RuntimeException("PANIC: function " + uid_main + " should have one argument");
//			}

			Object o = null;

			o = runner.dynRun(uid_main, args);
			if (o != null && o instanceof Thrown) {
				throw (Thrown) o;
			}
			return narrow(o);
		}
	}

	protected Object executeProgram(Frame root, Frame cf) {
		return runner.dynRun(root.function.funId, root);
	}
}
