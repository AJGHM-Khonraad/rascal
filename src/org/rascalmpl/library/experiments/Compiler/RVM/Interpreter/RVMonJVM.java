package org.rascalmpl.library.experiments.Compiler.RVM.Interpreter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IInteger;
import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IListWriter;
import org.eclipse.imp.pdb.facts.IMap;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.ITuple;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.eclipse.imp.pdb.facts.type.Type;
import org.eclipse.imp.pdb.facts.type.TypeStore;
import org.rascalmpl.interpreter.IEvaluatorContext;
import org.rascalmpl.library.experiments.Compiler.RVM.ToJVM.BytecodeGenerator;


public class RVMonJVM implements IRVM {

	public final IValueFactory vf;

	private boolean listing = false;
	private boolean finalized = false;

	protected final ArrayList<Function> functionStore;
	protected final Map<String, Integer> functionMap;

	// Function overloading
	private final Map<String, Integer> resolver;
	private final ArrayList<OverloadedFunction> overloadedStore;

	private final TypeStore typeStore = new TypeStore();
	private final Types types;

	private final ArrayList<Type> constructorStore;
	private final Map<String, Integer> constructorMap;

	RascalExecutionContext rex ;

	PrintWriter stdout;
	PrintWriter stderr;

	IEvaluatorContext ctx;

		public RVMonJVM(RascalExecutionContext rascalExecutionContext) {
		super();
		
		rex = rascalExecutionContext ;	
		this.vf = rex.getValueFactory() ;
		
		//this.classLoaders = rex.getClassLoaders();
		this.stdout = rex.getStdOut();
		this.stderr = rex.getStdErr();
		//this.debug = rex.getDebug();
		this.finalized = false;
		
		this.types = new Types(this.vf);
		
		functionStore = new ArrayList<Function>();
		constructorStore = new ArrayList<Type>();
		
		functionMap = new HashMap<String, Integer>();
		constructorMap = new HashMap<String, Integer>();
		
		resolver = new HashMap<String,Integer>();
		overloadedStore = new ArrayList<OverloadedFunction>();
	}

	public void declare(Function f) {
		if (functionMap.get(f.getName()) != null) {
			throw new RuntimeException("PANIC: Double declaration of function: " + f.getName());
		}
		int fss = functionStore.size();
		f.funId = fss; // ID of function to find entry in dynrun
		functionMap.put(f.getName(), fss);
		functionStore.add(f);
	}

	public void declareConstructor(String name, IConstructor symbol) {
		Type constr = types.symbolToType(symbol, typeStore);
		if (constructorMap.get(name) != null) {
			throw new RuntimeException("PANIC: Double declaration of constructor: " + name);
		}
		constructorMap.put(name, constructorStore.size());
		constructorStore.add(constr);
	}

	public Type symbolToType(IConstructor symbol) {
		return types.symbolToType(symbol, typeStore);
	}

	public void addResolver(IMap resolver) {
		for (IValue fuid : resolver) {
			String of = ((IString) fuid).getValue();
			int index = ((IInteger) resolver.get(fuid)).intValue();
			this.resolver.put(of, index);
		}
	}

	public void fillOverloadedStore(IList overloadedStore) {
		for (IValue of : overloadedStore) {
			ITuple ofTuple = (ITuple) of;
			String scopeIn = ((IString) ofTuple.get(0)).getValue();
			if (scopeIn.equals("")) {
				scopeIn = null;
			}
			IList fuids = (IList) ofTuple.get(1);
			int[] funs = new int[fuids.length()];
			int i = 0;
			for (IValue fuid : fuids) {
				Integer index = functionMap.get(((IString) fuid).getValue());
				if (index == null) {
					throw new RuntimeException("No definition for " + fuid + " in functionMap");
				}
				funs[i++] = index;
			}
			fuids = (IList) ofTuple.get(2);
			int[] constrs = new int[fuids.length()];
			i = 0;
			for (IValue fuid : fuids) {
				Integer index = constructorMap.get(((IString) fuid).getValue());
				if (index == null) {
					throw new RuntimeException("No definition for " + fuid + " in constructorMap");
				}
				constrs[i++] = index;
			}
			this.overloadedStore.add(new OverloadedFunction(funs, constrs, scopeIn));
		}
	}

	/**
	 * Narrow an Object as occurring on the RVM runtime stack to an IValue that can be returned. Note that various non-IValues can occur: - Coroutine - Reference - FunctionInstance -
	 * Object[] (is converted to an IList)
	 * 
	 * @param result
	 *            to be returned
	 * @return converted result or an exception
	 */
	private IValue narrow(Object result) {
		if (result instanceof Boolean) {
			return vf.bool((Boolean) result);
		}
		if (result instanceof Integer) {
			return vf.integer((Integer) result);
		}
		if (result instanceof IValue) {
			return (IValue) result;
		}
		if (result instanceof Thrown) {
			((Thrown) result).printStackTrace(stdout);
			return vf.string(((Thrown) result).toString());
		}
		if (result instanceof Object[]) {
			IListWriter w = vf.listWriter();
			Object[] lst = (Object[]) result;
			for (int i = 0; i < lst.length; i++) {
				w.append(narrow(lst[i]));
			}
			return w.done();
		}
		throw new RuntimeException("PANIC: Cannot convert object back to IValue: " + result);
	}

	public void finalize(BytecodeGenerator codeEmittor) {
		// Finalize the instruction generation of all functions, if needed
		if (!finalized) {
			finalized = true;

			codeEmittor.emitClass("org/rascalmpl/library/experiments/Compiler/RVM/Interpreter", "Running");

			for (Function f : functionStore) {
				f.finalize(codeEmittor, functionMap, constructorMap, resolver, listing);
			}

			// All functions are created create int based dispatcher
			codeEmittor.emitDynDispatch(functionMap.size());

			for (Map.Entry<String, Integer> e : functionMap.entrySet()) {
				String fname = e.getKey();
				codeEmittor.emitDynCaLL(fname, e.getValue());
			}
			codeEmittor.emitDynFinalize();

			int oid = 0;
			for (OverloadedFunction of : overloadedStore) {
				of.finalize(codeEmittor, functionMap, oid++);
			}
		}
	}

	public String getFunctionName(int n) {
		for (String fname : functionMap.keySet()) {
			if (functionMap.get(fname) == n) {
				return fname;
			}
		}
		throw new RuntimeException("PANIC: undefined function index " + n);
	}

	public String getConstructorName(int n) {
		for (String cname : constructorMap.keySet()) {
			if (constructorMap.get(cname) == n) {
				return cname;
			}
		}
		throw new RuntimeException("PANIC: undefined constructor index " + n);
	}

	public String getOverloadedFunctionName(int n) {
		for (String ofname : resolver.keySet()) {
			if (resolver.get(ofname) == n) {
				return ofname;
			}
		}
		throw new RuntimeException("PANIC: undefined overloaded function index " + n);
	}

	RVMRun runner = null;

	public void buildRunner(boolean profile) {
		byte[] rvmGenCode = null;
		if (!finalized) {
			try {
				// TODO; in the future create classes with the same name as a Rascal module
				String packageName = "org.rascalmpl.library.experiments.Compiler.RVM.Interpreter";				
				String className   = "RVMRunner";

				BytecodeGenerator codeEmittor = new BytecodeGenerator(packageName, className);

				finalize(codeEmittor);
				rvmGenCode = codeEmittor.finalizeCode();
				
				/* DEBUG */	codeEmittor.dump("/Users/ferryrietveld/Running.class");

				// Oneshot classloader
				Class<?> generatedClass = new ClassLoader(RVMonJVM.class.getClassLoader()) {
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
				}.defineClass("org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.RVMRunner", rvmGenCode);

				Constructor<?>[] cons = generatedClass.getConstructors();

				//runner = (RVMRun) cons[0].newInstance(vf, ctx, debug, profile);
				runner = (RVMRun) cons[0].newInstance(rex);

				runner.inject(functionStore, overloadedStore, constructorStore, typeStore, functionMap);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public IValue executeProgramP(String uid_main, IValue[] args) {
		boolean profile = false;

		buildRunner(profile);

		Function main_function = functionStore.get(functionMap.get(uid_main));

		if (main_function == null) {
			throw new RuntimeException("PANIC: No function " + uid_main + " found");
		}

		if (main_function.nformals != 2) { // List of IValues and empty map of
											// keyword parameters
			throw new RuntimeException("PANIC: function " + uid_main + " should have two arguments");
		}

		Frame root = new Frame(main_function.scopeId, null, main_function.maxstack, main_function);
		Frame cf = root;
		cf.stack[0] = vf.list(args); // pass the program argument to
										// main_function as a IList object
		cf.stack[1] = vf.mapWriter().done();

		Object o = null;
		o = runner.dynRun(uid_main, args);
		if (o != null && o instanceof Thrown) {
			throw (Thrown) o;
		}
		return narrow(o);
	}

	public IValue executeProgram(String uid_main, IValue[] args) {
		ThreadMXBean bean = ManagementFactory.getThreadMXBean();
		Object o = null;
		boolean profile = false;
		long[] runTimingJVM = new long[35];

		long buildTime = 0;
		if (runner == null) {
			buildTime = bean.getCurrentThreadUserTime();
			buildRunner(profile);
			buildTime = (bean.getCurrentThreadUserTime() - buildTime) / 1000000;
		}

		Function main_function = functionStore.get(functionMap.get(uid_main));

		if (main_function == null) {
			throw new RuntimeException("PANIC: No function " + uid_main + " found");
		}

		if (main_function.nformals != 2) { // List of IValues and empty map of
											// keyword parameters
			throw new RuntimeException("PANIC: function " + uid_main + " should have two arguments");
		}

		Frame root = new Frame(main_function.scopeId, null, main_function.maxstack, main_function);
		Frame cf = root;
		cf.stack[0] = vf.list(args); // pass the program argument to
										// main_function as a IList object
		cf.stack[1] = vf.mapWriter().done();

		for (int i = 0; i < 35; i++) {
			long startTime = bean.getCurrentThreadUserTime();
			o = runner.dynRun(uid_main, args);
			runTimingJVM[i] = bean.getCurrentThreadUserTime() - startTime;
		}

		for (int i = 0; i < 35; i++) {
			runTimingJVM[i] = runTimingJVM[i] / 1000000;
		}

		try {
			if (uid_main.contains("main")) {
				File file = new File("/Users/ferryrietveld/measurements.csv");
				if (!file.exists()) {
					file.createNewFile();
					FileWriter fileWriter = new FileWriter("/Users/ferryrietveld/measurements.csv", true);
					fileWriter.write("Name;runType;buildTime;firstRun");
					for (int i = 1; i < 35; i++)
						fileWriter.write(";" + i);
					fileWriter.write(";average last 30;stdev last 30\n");
					fileWriter.close();
				}

				FileWriter fileWritter = new FileWriter("/Users/ferryrietveld/measurements.csv", true);

				fileWritter.write(uid_main.replace(';','|'));
				fileWritter.write(";(Partial)");
				fileWritter.write(";" + buildTime);

				for (int i = 0; i < 35; i++) {
					fileWritter.write(";" + runTimingJVM[i]);
				}

				fileWritter.write(";" + mean(runTimingJVM, 2, 4));
				fileWritter.write(";" + stddev(runTimingJVM, 0, 4));
				fileWritter.write("\n");

				fileWritter.close();
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

		if (o != null && o instanceof Thrown) {
			throw (Thrown) o;
		}
		return narrow(o);
	}

	// A poor mans statistics, Stolen and modified.
	public float sum(long[] a, int lo, int hi) {
		if (lo < 0 || hi >= a.length || lo > hi)
			throw new RuntimeException("Subarray indices out of bounds");
		float sum = 0;
		for (int i = lo; i <= hi; i++) {
			sum += a[i];
		}
		return sum;
	}

	public float mean(long[] a, int lo, int hi) {
		int length = hi - lo + 1;
		if (lo < 0 || hi >= a.length || lo > hi)
			throw new RuntimeException("Subarray indices out of bounds");
		if (length == 0)
			return Float.NaN;
		float sum = sum(a, lo, hi);
		return sum / length;
	}

	public float var(long[] a, int lo, int hi) {
		int length = hi - lo + 1;
		if (lo < 0 || hi >= a.length || lo > hi)
			throw new RuntimeException("Subarray indices out of bounds");
		if (length == 0)
			return Float.NaN;
		float avg = mean(a, lo, hi);
		float sum = 0;
		for (int i = lo; i <= hi; i++) {
			sum += (a[i] - avg) * (a[i] - avg);
		}
		return sum / (length - 1);
	}

	public float stddev(long[] a, int lo, int hi) {
		return (float) Math.sqrt(var(a, lo, hi));
	}

	
	@Override
	public RascalExecutionContext getRex() {
		return rex;
	}
	
	@Override
	public IValue executeFunction(String uid_main, IValue[] args) {
		return null;
	}

	@Override
	public IValue executeFunction(FunctionInstance functionInstance, IValue[] args) {
		return null;
	}
}
