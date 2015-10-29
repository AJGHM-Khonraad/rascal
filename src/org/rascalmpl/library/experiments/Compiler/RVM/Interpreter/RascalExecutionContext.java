package org.rascalmpl.library.experiments.Compiler.RVM.Interpreter;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import org.rascalmpl.debug.IRascalMonitor;
import org.rascalmpl.interpreter.Configuration;
import org.rascalmpl.interpreter.ConsoleRascalMonitor;
import org.rascalmpl.interpreter.DefaultTestResultListener;
import org.rascalmpl.interpreter.Evaluator;
import org.rascalmpl.interpreter.ITestResultListener;
import org.rascalmpl.interpreter.load.IRascalSearchPathContributor;
import org.rascalmpl.interpreter.load.RascalSearchPath;
import org.rascalmpl.interpreter.load.StandardLibraryContributor;
import org.rascalmpl.interpreter.load.URIContributor;
import org.rascalmpl.interpreter.result.ICallableValue;
import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.traverse.DescendantDescriptor;
import org.rascalmpl.uri.URIUtil;
import org.rascalmpl.value.IConstructor;
import org.rascalmpl.value.IListWriter;
import org.rascalmpl.value.IMap;
import org.rascalmpl.value.ISourceLocation;
import org.rascalmpl.value.IString;
import org.rascalmpl.value.IValue;
import org.rascalmpl.value.IValueFactory;
import org.rascalmpl.value.type.Type;
import org.rascalmpl.value.type.TypeFactory;
import org.rascalmpl.value.type.TypeStore;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * Provides all context information that is needed during the execution of a compiled Rascal program
 * and contains:
 * - I/O streams
 * - class loaders
 * - RascalSearchPath
 * - execution flags
 * - state variables need by RascalPrimitives
 *
 */
public class RascalExecutionContext implements IRascalMonitor {

	private IRascalMonitor monitor;
	private final PrintWriter stderr;
	private final Configuration config;
	private final List<ClassLoader> classLoaders;
	private final PrintWriter stdout;
	
	private final IValueFactory vf;
	private final TypeStore typeStore;
	private final boolean debug;
	private final boolean testsuite;
	private final boolean profile;
	private final boolean trackCalls;
	private final ITestResultListener testResultListener;
	private final IMap symbol_definitions;
	private RascalSearchPath rascalSearchPath;
	
	private String currentModuleName;
	private RVM rvm;
	private boolean coverage;
	private boolean useJVM;
	private final IMap moduleTags;
	
	// State for RascalPrimitive
	
	private final ParsingTools parsingTools; 
	Stack<String> indentStack = new Stack<String>();
	//final HashMap<Type,IConstructor> type2symbolCache = new HashMap<Type,IConstructor>();
	private Cache<Type, IConstructor> type2symbolCache = Caffeine.newBuilder().build();
	
	StringBuilder templateBuilder = null;
	private final Stack<StringBuilder> templateBuilderStack = new Stack<StringBuilder>();
	private IListWriter test_results;
	
	private final HashMap<IString,DescendantDescriptor> descendantDescriptorMap = new HashMap<IString,DescendantDescriptor>();
	
	public RascalExecutionContext(
			String moduleName, 
			IValueFactory vf, 
			PrintStream out, PrintStream err) {
		this(moduleName, vf, new PrintWriter(out), new PrintWriter(err));
	}
	
	public RascalExecutionContext(
			String moduleName, 
			IValueFactory vf, 
			PrintWriter out, PrintWriter err) {
		this(vf, out, err, null, null, null, false, false, false, false, false, false, null, null);
		setCurrentModuleName(moduleName);
	}
	
	public RascalExecutionContext(
			IValueFactory vf, 
			PrintWriter stdout, 
			PrintWriter stderr, 
			IMap moduleTags, 
			IMap symbol_definitions, 
			TypeStore typeStore, 
			boolean debug, 
			boolean testsuite, 
			boolean profile, 
			boolean trackCalls, 
			boolean coverage, 
			boolean useJVM, 
			ITestResultListener testResultListener, 
			RascalSearchPath rascalSearchPath
	){
		
		this.vf = vf;
		this.moduleTags = moduleTags;
		this.symbol_definitions = symbol_definitions;
		this.typeStore = typeStore == null ? new TypeStore() : typeStore;
		this.debug = debug;
		this.testsuite = testsuite;
		this.profile = profile;
		this.coverage = coverage;
		this.useJVM = useJVM;
		this.trackCalls = trackCalls;
		
		currentModuleName = "UNDEFINED";
		
		if(rascalSearchPath == null){
			this.rascalSearchPath = new RascalSearchPath();
			addRascalSearchPath(URIUtil.rootLocation("test-modules"));
            addRascalSearchPathContributor(StandardLibraryContributor.getInstance());
		} else {
			this.rascalSearchPath = rascalSearchPath;
		}
	
		monitor = new ConsoleRascalMonitor(); //ctx.getEvaluator().getMonitor();
		this.stdout = stdout;
		this.stderr = stderr;
		config = new Configuration();
		this.classLoaders = new ArrayList<ClassLoader>(Collections.singleton(Evaluator.class.getClassLoader()));
		this.testResultListener = (testResultListener == null) ? (ITestResultListener) new DefaultTestResultListener(stderr)
															  : testResultListener;
		parsingTools = new ParsingTools(vf);
	}

	public ParsingTools getParsingTools(){
		return parsingTools;
	}
	
	IValueFactory getValueFactory(){ return vf; }
	
	public IMap getSymbolDefinitions() { return symbol_definitions; }
	
	public TypeStore getTypeStore() { 
		return typeStore; 
	}
	
	boolean getDebug() { return debug; }
	
	boolean getTestSuite() { return testsuite; }
	
	boolean getProfile(){ return profile; }
	
	boolean getCoverage(){ return coverage; }
	
	boolean getUseJVM() { return useJVM; }
	
	boolean getTrackCalls() { return trackCalls; }
	
	public RVM getRVM(){ return rvm; }
	
	protected void setRVM(RVM rvm){ 
		this.rvm = rvm; 
	}
	
	public void addClassLoader(ClassLoader loader) {
		// later loaders have precedence
		classLoaders.add(0, loader);
	}
	
	List<ClassLoader> getClassLoaders() { return classLoaders; }
	
	IRascalMonitor getMonitor() {return monitor;}
	
	void setMonitor(IRascalMonitor monitor) {
		this.monitor = monitor;
	}
	
	public PrintWriter getStdErr() { return stderr; }
	
	public PrintWriter getStdOut() { return stdout; }
	
	Configuration getConfiguration() { return config; }
	
	ITestResultListener getTestResultListener() { return testResultListener; }
	
	public String getCurrentModuleName(){ return currentModuleName; }
	
	public void setCurrentModuleName(String moduleName) { currentModuleName = moduleName; }
	
	public Stack<String> getIndentStack() { return indentStack; }
	
	//public HashMap<Type,IConstructor> getType2SymbolCache(){ return type2symbolCache; }
	
	public IConstructor type2Symbol(final Type t){
		return type2symbolCache.get(t, k -> RascalPrimitive.$type2symbol(t));
	}
	
	HashMap<IString,DescendantDescriptor> getDescendantDescriptorMap() {
		return descendantDescriptorMap;
	}
	
	private Cache<Type[], Boolean> subtypeCache = Caffeine.newBuilder().build();
	
	public boolean isSubtypeOf(Type t1, Type t2){
		return t1.isSubtypeOf(t2);
//		Type[] key = new Type[] { t1, t2};
//		
//		return subtypeCache.get(key, k -> t1.isSubtypeOf(t2));
	}
	
	StringBuilder getTemplateBuilder() { return templateBuilder; }
	
	void setTemplateBuilder(StringBuilder sb) { templateBuilder = sb; }
	
	Stack<StringBuilder> getTemplateBuilderStack() { return  templateBuilderStack; }
	
	IListWriter getTestResults() { return test_results; }
	
	void setTestResults(IListWriter writer) { test_results = writer; }
	
	private Cache<String, Function> companionDefaultFunctionCache = Caffeine.newBuilder().build();
	
	public Function getCompanionDefaultsFunction(String name, Type ftype){
		String key = name + ftype;
		
		Function result = companionDefaultFunctionCache.get(key, k -> rvm.getCompanionDefaultsFunction(name, ftype));
		//System.err.println("RascalExecutionContext.getCompanionDefaultsFunction: " + key + " => " + result.name);
		return result;
	}
	
	public void clearCaches(){
		companionDefaultFunctionCache = Caffeine.newBuilder().build();
		descendantDescriptorMap.clear();
		subtypeCache = Caffeine.newBuilder().build();
	}
	
	boolean bootstrapParser(String moduleName){
		if(moduleTags != null){
			IMap tags = (IMap) moduleTags.get(vf.string(moduleName));
			if(tags != null)
				return tags.get(vf.string("bootstrapParser")) != null;
		}
		return false;
	}
	
	public int endJob(boolean succeeded) {
		if (monitor != null)
			return monitor.endJob(succeeded);
		return 0;
	}
	
	public void event(int inc) {
		if (monitor != null)
			monitor.event(inc);
	}
	
	public void event(String name, int inc) {
		if (monitor != null)
			monitor.event(name, inc);
	}

	public void event(String name) {
		if (monitor != null)
			monitor.event(name);
	}

	public void startJob(String name, int workShare, int totalWork) {
		if (monitor != null)
			monitor.startJob(name, workShare, totalWork);
		stdout.println(name);
		stdout.flush();
	}
	
	public void startJob(String name, int totalWork) {
		if (monitor != null)
			monitor.startJob(name, totalWork);
	}
	
	public void startJob(String name) {
		if (monitor != null)
			monitor.startJob(name);
		stdout.println(name);
		stdout.flush();
	}
		
	public void todo(int work) {
		if (monitor != null)
			monitor.todo(work);
	}
	
	@Override
	public boolean isCanceled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void warning(String message, ISourceLocation src) {
		stdout.println("Warning: " + message);
		stdout.flush();
	}

	public RascalSearchPath getRascalSearchPath() { 
		return rascalSearchPath; 
	}
	
	private void addRascalSearchPathContributor(IRascalSearchPathContributor contrib) {
		rascalSearchPath.addPathContributor(contrib);
	}
	
	private void addRascalSearchPath(final ISourceLocation uri) {
		rascalSearchPath.addPathContributor(new URIContributor(uri));
	}
	/**
	 * Source location resolvers map user defined schemes to primitive schemes
	 */
	private final HashMap<String, ICallableValue> sourceResolvers = new HashMap<String, ICallableValue>();
	
	/**
	 * Register a source resolver for a specific scheme. Will overwrite the previously
	 * registered source resolver for this scheme.
	 * 
	 * @param scheme   intended be a scheme name without + or :
	 * @param function a Rascal function of type `loc (loc)`
	 */
	public void registerSourceResolver(String scheme, ICallableValue function) {
		sourceResolvers.put(scheme, function);
	}
	
	public ISourceLocation resolveSourceLocation(ISourceLocation loc) {
		String scheme = loc.getScheme();
		int pos;
		
		ICallableValue resolver = sourceResolvers.get(scheme);
		if (resolver == null) {
			for (char sep : new char[] {'+',':'}) {
				pos = scheme.indexOf(sep);
				if (pos != -1) {
					scheme = scheme.substring(0, pos);
				}
			}

			resolver = sourceResolvers.get(scheme);
			if (resolver == null) {
				return loc;
			}
		}
		
		Type[] argTypes = new Type[] { TypeFactory.getInstance().sourceLocationType() };
		IValue[] argValues = new IValue[] { loc };
		
		return (ISourceLocation) resolver.call(argTypes, argValues, null).getValue();
	}
	
	void registerCommonSchemes(){
		addRascalSearchPath(URIUtil.rootLocation("test-modules"));
		addRascalSearchPathContributor(StandardLibraryContributor.getInstance());
	}
}
