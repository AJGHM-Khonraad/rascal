
package org.meta_environment.rascal.interpreter;

import static org.meta_environment.rascal.interpreter.result.ResultFactory.makeResult;
import static org.meta_environment.rascal.interpreter.result.ResultFactory.nothing;
import static org.meta_environment.rascal.interpreter.utils.Utils.unescape;

import java.io.IOException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;
import java.util.Map.Entry;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IInteger;
import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IListWriter;
import org.eclipse.imp.pdb.facts.IMap;
import org.eclipse.imp.pdb.facts.IMapWriter;
import org.eclipse.imp.pdb.facts.INode;
import org.eclipse.imp.pdb.facts.IRelation;
import org.eclipse.imp.pdb.facts.ISet;
import org.eclipse.imp.pdb.facts.ISetWriter;
import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.ITuple;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.eclipse.imp.pdb.facts.IWriter;
import org.eclipse.imp.pdb.facts.exceptions.UndeclaredFieldException;
import org.eclipse.imp.pdb.facts.type.Type;
import org.eclipse.imp.pdb.facts.type.TypeFactory;
import org.eclipse.imp.pdb.facts.type.TypeStore;
import org.meta_environment.rascal.ast.AbstractAST;
import org.meta_environment.rascal.ast.Bound;
import org.meta_environment.rascal.ast.Case;
import org.meta_environment.rascal.ast.Catch;
import org.meta_environment.rascal.ast.Declaration;
import org.meta_environment.rascal.ast.Declarator;
import org.meta_environment.rascal.ast.Expression;
import org.meta_environment.rascal.ast.Field;
import org.meta_environment.rascal.ast.FunctionDeclaration;
import org.meta_environment.rascal.ast.FunctionModifier;
import org.meta_environment.rascal.ast.Import;
import org.meta_environment.rascal.ast.Module;
import org.meta_environment.rascal.ast.Name;
import org.meta_environment.rascal.ast.NullASTVisitor;
import org.meta_environment.rascal.ast.QualifiedName;
import org.meta_environment.rascal.ast.Replacement;
import org.meta_environment.rascal.ast.Statement;
import org.meta_environment.rascal.ast.Strategy;
import org.meta_environment.rascal.ast.StringLiteral;
import org.meta_environment.rascal.ast.Tags;
import org.meta_environment.rascal.ast.Toplevel;
import org.meta_environment.rascal.ast.Assignable.Constructor;
import org.meta_environment.rascal.ast.Assignable.FieldAccess;
import org.meta_environment.rascal.ast.Declaration.Alias;
import org.meta_environment.rascal.ast.Declaration.Annotation;
import org.meta_environment.rascal.ast.Declaration.Data;
import org.meta_environment.rascal.ast.Declaration.Function;
import org.meta_environment.rascal.ast.Declaration.Rule;
import org.meta_environment.rascal.ast.Declaration.Tag;
import org.meta_environment.rascal.ast.Declaration.Variable;
import org.meta_environment.rascal.ast.Declaration.View;
import org.meta_environment.rascal.ast.Expression.Addition;
import org.meta_environment.rascal.ast.Expression.All;
import org.meta_environment.rascal.ast.Expression.Ambiguity;
import org.meta_environment.rascal.ast.Expression.And;
import org.meta_environment.rascal.ast.Expression.Any;
import org.meta_environment.rascal.ast.Expression.Bracket;
import org.meta_environment.rascal.ast.Expression.CallOrTree;
import org.meta_environment.rascal.ast.Expression.Closure;
import org.meta_environment.rascal.ast.Expression.Composition;
import org.meta_environment.rascal.ast.Expression.Comprehension;
import org.meta_environment.rascal.ast.Expression.Division;
import org.meta_environment.rascal.ast.Expression.EnumeratorWithStrategy;
import org.meta_environment.rascal.ast.Expression.Equivalence;
import org.meta_environment.rascal.ast.Expression.FieldProject;
import org.meta_environment.rascal.ast.Expression.FieldUpdate;
import org.meta_environment.rascal.ast.Expression.GreaterThan;
import org.meta_environment.rascal.ast.Expression.GreaterThanOrEq;
import org.meta_environment.rascal.ast.Expression.IfDefinedOtherwise;
import org.meta_environment.rascal.ast.Expression.Implication;
import org.meta_environment.rascal.ast.Expression.In;
import org.meta_environment.rascal.ast.Expression.Intersection;
import org.meta_environment.rascal.ast.Expression.IsDefined;
import org.meta_environment.rascal.ast.Expression.Join;
import org.meta_environment.rascal.ast.Expression.LessThan;
import org.meta_environment.rascal.ast.Expression.LessThanOrEq;
import org.meta_environment.rascal.ast.Expression.Lexical;
import org.meta_environment.rascal.ast.Expression.List;
import org.meta_environment.rascal.ast.Expression.Literal;
import org.meta_environment.rascal.ast.Expression.Location;
import org.meta_environment.rascal.ast.Expression.Match;
import org.meta_environment.rascal.ast.Expression.Modulo;
import org.meta_environment.rascal.ast.Expression.Negation;
import org.meta_environment.rascal.ast.Expression.Negative;
import org.meta_environment.rascal.ast.Expression.NoMatch;
import org.meta_environment.rascal.ast.Expression.NonEmptyBlock;
import org.meta_environment.rascal.ast.Expression.NotIn;
import org.meta_environment.rascal.ast.Expression.OperatorAsValue;
import org.meta_environment.rascal.ast.Expression.Or;
import org.meta_environment.rascal.ast.Expression.Product;
import org.meta_environment.rascal.ast.Expression.Range;
import org.meta_environment.rascal.ast.Expression.Set;
import org.meta_environment.rascal.ast.Expression.StepRange;
import org.meta_environment.rascal.ast.Expression.Subscript;
import org.meta_environment.rascal.ast.Expression.Subtraction;
import org.meta_environment.rascal.ast.Expression.TransitiveClosure;
import org.meta_environment.rascal.ast.Expression.TransitiveReflexiveClosure;
import org.meta_environment.rascal.ast.Expression.Tuple;
import org.meta_environment.rascal.ast.Expression.TypedVariable;
import org.meta_environment.rascal.ast.Expression.Visit;
import org.meta_environment.rascal.ast.Expression.VoidClosure;
import org.meta_environment.rascal.ast.FunctionDeclaration.Abstract;
import org.meta_environment.rascal.ast.Header.Parameters;
import org.meta_environment.rascal.ast.IntegerLiteral.DecimalIntegerLiteral;
import org.meta_environment.rascal.ast.Literal.Boolean;
import org.meta_environment.rascal.ast.Literal.Integer;
import org.meta_environment.rascal.ast.Literal.Real;
import org.meta_environment.rascal.ast.LocalVariableDeclaration.Default;
import org.meta_environment.rascal.ast.PatternWithAction.Arbitrary;
import org.meta_environment.rascal.ast.PatternWithAction.Replacing;
import org.meta_environment.rascal.ast.Statement.Assert;
import org.meta_environment.rascal.ast.Statement.AssertWithMessage;
import org.meta_environment.rascal.ast.Statement.Assignment;
import org.meta_environment.rascal.ast.Statement.Block;
import org.meta_environment.rascal.ast.Statement.Break;
import org.meta_environment.rascal.ast.Statement.Continue;
import org.meta_environment.rascal.ast.Statement.DoWhile;
import org.meta_environment.rascal.ast.Statement.EmptyStatement;
import org.meta_environment.rascal.ast.Statement.Fail;
import org.meta_environment.rascal.ast.Statement.For;
import org.meta_environment.rascal.ast.Statement.GlobalDirective;
import org.meta_environment.rascal.ast.Statement.IfThen;
import org.meta_environment.rascal.ast.Statement.IfThenElse;
import org.meta_environment.rascal.ast.Statement.Insert;
import org.meta_environment.rascal.ast.Statement.Solve;
import org.meta_environment.rascal.ast.Statement.Switch;
import org.meta_environment.rascal.ast.Statement.Throw;
import org.meta_environment.rascal.ast.Statement.Try;
import org.meta_environment.rascal.ast.Statement.TryFinally;
import org.meta_environment.rascal.ast.Statement.VariableDeclaration;
import org.meta_environment.rascal.ast.Statement.While;
import org.meta_environment.rascal.ast.Toplevel.DefaultVisibility;
import org.meta_environment.rascal.ast.Toplevel.GivenVisibility;
import org.meta_environment.rascal.ast.Visit.DefaultStrategy;
import org.meta_environment.rascal.ast.Visit.GivenStrategy;
import org.meta_environment.rascal.interpreter.asserts.Ambiguous;
import org.meta_environment.rascal.interpreter.asserts.ImplementationError;
import org.meta_environment.rascal.interpreter.asserts.NotYetImplemented;
import org.meta_environment.rascal.interpreter.control_exceptions.Failure;
import org.meta_environment.rascal.interpreter.control_exceptions.Return;
import org.meta_environment.rascal.interpreter.env.ConcreteSyntaxType;
import org.meta_environment.rascal.interpreter.env.Environment;
import org.meta_environment.rascal.interpreter.env.FileParserFunction;
import org.meta_environment.rascal.interpreter.env.GlobalEnvironment;
import org.meta_environment.rascal.interpreter.env.JavaFunction;
import org.meta_environment.rascal.interpreter.env.Lambda;
import org.meta_environment.rascal.interpreter.env.ModuleEnvironment;
import org.meta_environment.rascal.interpreter.env.ParserFunction;
import org.meta_environment.rascal.interpreter.env.RascalFunction;
import org.meta_environment.rascal.interpreter.env.RewriteRule;
import org.meta_environment.rascal.interpreter.load.FromCurrentWorkingDirectoryLoader;
import org.meta_environment.rascal.interpreter.load.FromResourceLoader;
import org.meta_environment.rascal.interpreter.load.IModuleFileLoader;
import org.meta_environment.rascal.interpreter.load.ISdfSearchPathContributor;
import org.meta_environment.rascal.interpreter.load.ModuleLoader;
import org.meta_environment.rascal.interpreter.matching.IBooleanResult;
import org.meta_environment.rascal.interpreter.matching.IMatchingResult;
import org.meta_environment.rascal.interpreter.matching.LiteralPattern;
import org.meta_environment.rascal.interpreter.matching.RegExpPatternValue;
import org.meta_environment.rascal.interpreter.result.BoolResult;
import org.meta_environment.rascal.interpreter.result.ConcreteSyntaxResult;
import org.meta_environment.rascal.interpreter.result.Result;
import org.meta_environment.rascal.interpreter.result.ResultFactory;
import org.meta_environment.rascal.interpreter.staticErrors.AmbiguousConcretePattern;
import org.meta_environment.rascal.interpreter.staticErrors.MissingModifierError;
import org.meta_environment.rascal.interpreter.staticErrors.ModuleNameMismatchError;
import org.meta_environment.rascal.interpreter.staticErrors.NonWellformedTypeError;
import org.meta_environment.rascal.interpreter.staticErrors.RedeclaredVariableError;
import org.meta_environment.rascal.interpreter.staticErrors.SyntaxError;
import org.meta_environment.rascal.interpreter.staticErrors.UndeclaredAnnotationError;
import org.meta_environment.rascal.interpreter.staticErrors.UndeclaredFieldError;
import org.meta_environment.rascal.interpreter.staticErrors.UndeclaredFunctionError;
import org.meta_environment.rascal.interpreter.staticErrors.UndeclaredModuleError;
import org.meta_environment.rascal.interpreter.staticErrors.UnexpectedTypeError;
import org.meta_environment.rascal.interpreter.staticErrors.UnguardedFailError;
import org.meta_environment.rascal.interpreter.staticErrors.UnguardedInsertError;
import org.meta_environment.rascal.interpreter.staticErrors.UnguardedReturnError;
import org.meta_environment.rascal.interpreter.staticErrors.UninitializedVariableError;
import org.meta_environment.rascal.interpreter.staticErrors.UnsupportedOperationError;
import org.meta_environment.rascal.interpreter.utils.JavaBridge;
import org.meta_environment.rascal.interpreter.utils.Names;
import org.meta_environment.rascal.interpreter.utils.Profiler;
import org.meta_environment.rascal.interpreter.utils.RuntimeExceptionFactory;
import org.meta_environment.rascal.interpreter.utils.Symbols;
import org.meta_environment.rascal.parser.ModuleParser;
import org.meta_environment.uptr.Factory;
import org.meta_environment.uptr.SymbolAdapter;
import org.meta_environment.uptr.TreeAdapter;

@SuppressWarnings("unchecked")
public class Evaluator extends NullASTVisitor<Result<IValue>> {
	public final IValueFactory vf;
	public final TypeFactory tf = TypeFactory.getInstance();
	private final TypeEvaluator te = TypeEvaluator.getInstance();
	protected Environment currentEnvt;

	protected final GlobalEnvironment heap;

	private final JavaBridge javaBridge;
	//	private final boolean LAZY = false;
	protected boolean importResetsInterpreter = true;

	enum DIRECTION  {BottomUp, TopDown}	// Parameters for traversing trees
	enum FIXEDPOINT {Yes, No}
	enum PROGRESS   {Continuing, Breaking}


	private AbstractAST currentAST; 	// used in runtime errormessages

	private Profiler profiler;
	private boolean doProfiling = false;

	private TypeDeclarationEvaluator typeDeclarator = new TypeDeclarationEvaluator();
	protected final ModuleLoader loader;

	private java.util.List<ClassLoader> classLoaders;
	protected ModuleEnvironment rootScope;
	private boolean concreteListsShouldBeSpliced;

	public Evaluator(IValueFactory f, Writer errorWriter, ModuleEnvironment scope) {
		this(f, errorWriter, scope, new GlobalEnvironment());
	}

	public Evaluator(IValueFactory f, Writer errorWriter, ModuleEnvironment scope, GlobalEnvironment heap) {
		this(f, errorWriter, scope, new GlobalEnvironment(), new ModuleParser());
	}

	public Evaluator(IValueFactory f, Writer errorWriter, ModuleEnvironment scope, GlobalEnvironment heap, ModuleParser parser) {
		this.vf = f;
		this.heap = heap;
		currentEnvt = scope;
		rootScope = scope;
		heap.addModule(scope);
		this.classLoaders = new LinkedList<ClassLoader>();
		this.javaBridge = new JavaBridge(errorWriter, classLoaders);
		loader = new ModuleLoader(parser);
		parser.setLoader(loader);

		// cwd loader
		loader.addFileLoader(new FromCurrentWorkingDirectoryLoader());

		// library
		loader.addFileLoader(new FromResourceLoader(this.getClass(), "StandardLibrary"));

		// everything rooted at the src directory 
		loader.addFileLoader(new FromResourceLoader(this.getClass()));

		// add current wd and sdf-library to search path for SDF modules
		loader.addSdfSearchPathContributor(new ISdfSearchPathContributor() {
			public java.util.List<String> contributePaths() {
				java.util.List<String> result = new LinkedList<String>();
				//System.err.println("getproperty user.dir: " + System.getProperty("user.dir"));
				result.add(System.getProperty("user.dir"));
				result.add(Configuration.getSdfLibraryPathProperty());
				return result;
			}
		});

		// load Java classes from the current jar (for the standard library)
		classLoaders.add(getClass().getClassLoader());
	}


	//	public IConstructor parseCommand(String command, String fileName) throws IOException {
	//		return loader.parseCommand(command, fileName);
	//	}

	/**
	 * In interactive mode this flag should be set to true, such that re-importing
	 * a module causes a re-initialization. 
	 */
	public void setImportResetsInterpreter(boolean flag) {
		this.importResetsInterpreter = flag;
	}

	private void checkPoint(Environment env) {
		env.checkPoint();
	}

	private void rollback(Environment env) {
		env.rollback();
	}

	private void commit(Environment env) {
		env.commit();
	}

	public void setCurrentAST(AbstractAST currentAST) {
		this.currentAST = currentAST;
	}

	public AbstractAST getCurrentAST() {
		return currentAST;
	}

	public void addModuleLoader(IModuleFileLoader fileLoader) {
		loader.addFileLoader(fileLoader);
	}

	public void addSdfSearchPathContributor(ISdfSearchPathContributor contrib) {
		loader.addSdfSearchPathContributor(contrib);
	}

	public void addClassLoader(ClassLoader loader) {
		// later loaders have precedence
		classLoaders.add(0, loader);
	}

	public String getStackTrace() {
		StringBuilder b = new StringBuilder();
		Environment env = currentEnvt;
		while (env != null) {
			ISourceLocation loc = env.getLocation();
			String name = env.getName();
			if (name != null && loc != null) {
				URL url = loc.getURL();
				b.append('\t');
				b.append(url.getAuthority() + url.getPath() + ":" + loc.getBeginLine() + "," + loc.getBeginColumn() + ": " + name);
				b.append('\n');
			}
			env = env.getParent();
		}
		return b.toString();
	}

	/**
	 * Evaluate a statement
	 * @param stat
	 * @return
	 */
	public Result<IValue> eval(Statement stat) {
		try {
			if(doProfiling){
				profiler = new Profiler(this);
				profiler.start();

			}
			currentAST = stat;
			Result<IValue> r = stat.accept(this);
			if(r != null){
				if(doProfiling){
					profiler.pleaseStop();
					profiler.report();
				}
				return r;
			}
			throw new ImplementationError("Not yet implemented: " + stat.toString());
		} catch (Return e){
			throw new UnguardedReturnError(stat);
		}
		catch (Failure e){
			throw new UnguardedFailError(stat);
		}
		catch (org.meta_environment.rascal.interpreter.control_exceptions.Insert e){
			throw new UnguardedInsertError(stat);
		}
	}

	/**
	 * Evaluate an expression
	 * @param expr
	 * @return
	 */
	public Result<IValue> eval(Expression expr) {
		currentAST = expr;
		Result<IValue> r = expr.accept(this);
		if(r != null){
			return r;
		}

		throw new NotYetImplemented(expr.toString());
	}

	/**
	 * Evaluate a declaration
	 * @param declaration
	 * @return
	 */
	public Result<IValue> eval(Declaration declaration) {
		currentAST = declaration;
		Result<IValue> r = declaration.accept(this);
		if(r != null){
			return r;
		}

		throw new NotYetImplemented(declaration.toString());
	}

	/**
	 * Evaluate an import
	 * @param imp
	 * @return
	 */
	public IValue eval(org.meta_environment.rascal.ast.Import imp) {
		currentAST = imp;
		Result<IValue> r = imp.accept(this);
		if(r != null){
			return r.getValue();
		}

		throw new ImplementationError("Not yet implemented: " + imp.getTree());
	}

	/* First a number of general utility methods */

	/*
	 * Return an evaluation result that is already in normal form,
	 * i.e., all potential rules have already been applied to it.
	 */

	Result<IValue> normalizedResult(Type t, IValue v){
		Map<Type, Type> bindings = getCurrentEnvt().getTypeBindings();
		Type instance;

		if (bindings.size() > 0) {
			instance = t.instantiate(getCurrentEnvt().getStore(), bindings);
		}
		else {
			instance = t;
		}

		if (v != null) {
			checkType(v.getType(), instance);
		}
		return makeResult(instance, v, makeEvContext());
	}


	private Result<IValue> applyRules(Result<IValue> v) {
		//System.err.println("applyRules(" + v + ")");
		// we search using the run-time type of a value
		Type typeToSearchFor = v.getValue().getType();
		if (typeToSearchFor.isAbstractDataType()) {
			typeToSearchFor = ((IConstructor) v.getValue()).getConstructorType();
		}

		java.util.List<RewriteRule> rules = heap.getRules(typeToSearchFor);
		if(rules.isEmpty()){
			// weird side-effect but it works
			declareConcreteSyntaxType(v.getValue());
			return v;
		}

		TraverseResult tr = traverseTop(v, new CasesOrRules(rules));
		/* innermost is achieved by repeated applications of applyRules
		 * when intermediate results are produced.
		 */

		// weird side-effect but it works
		declareConcreteSyntaxType(tr.value.getValue());

		return tr.value;
	}

	private void declareConcreteSyntaxType(IValue value) {
		// if somebody constructs a sort, then this implicitly declares
		// a corresponding Rascal type.
		Type type = value.getType();

		if (type == Factory.Symbol) {
			IConstructor symbol = (IConstructor) value;

			if (symbol.getConstructorType() == Factory.Symbol_Sort) {
				currentEnvt.concreteSyntaxType(new SymbolAdapter(symbol).getName(), 
						(IConstructor) Factory.Symbol_Cf.make(vf, value));
			}
		}

	}

	public Environment pushEnv() {
		Environment env = new Environment(getCurrentEnvt(), getCurrentEnvt().getName());
		setCurrentEnvt(env);
		return env;
	}
	
	void unwind(Environment old) {
		while (getCurrentEnvt() != old) {
			goodPopEnv();
		}
	}

	public Environment goodPushEnv() {
		Environment env = new Environment(getCurrentEnvt(), getCurrentEnvt().getName());
		setCurrentEnvt(env);
		return env;
	}

	public Environment goodPopEnv() {
		setCurrentEnvt(getCurrentEnvt().getParent());
		return getCurrentEnvt();
	}

	public Environment popEnv() {
		setCurrentEnvt(getCurrentEnvt().getParent());
		return getCurrentEnvt();
	}

	Environment pushEnv(Statement s) {
		/* use the same name as the current envt */
		Environment env = new Environment(getCurrentEnvt(), s.getLocation(), getCurrentEnvt().getName());
		setCurrentEnvt(env);
		return env;
	}
	
	Environment goodPushEnv(Statement s) {
		/* use the same name as the current envt */
		Environment env = new Environment(getCurrentEnvt(), s.getLocation(), getCurrentEnvt().getName());
		setCurrentEnvt(env);
		return env;
	}


	private void checkType(Type given, Type expected) {
		if (expected == org.meta_environment.rascal.interpreter.env.Lambda.getClosureType()) {
			return;
		}
		if (!given.isSubtypeOf(expected)){
			throw new UnexpectedTypeError(expected, given, getCurrentAST());
		}
	}

	public boolean mayOccurIn(Type small, Type large) {
		return mayOccurIn(small, large, new HashSet<Type>());
	}

	boolean mayOccurIn(Type small, Type large, java.util.Set<Type> seen){
		if(small.isVoidType())
			return true;
		if(large.isVoidType())
			return false;
		if(small.isValueType())
			return true;
		if(small.isSubtypeOf(large))
			return true;
		if(large.isListType() || large.isSetType())
			return mayOccurIn(small,large.getElementType(), seen);
		if(large.isMapType())
			return mayOccurIn(small, large.getKeyType(), seen) ||
			mayOccurIn(small, large.getValueType(), seen);
		if(large.isTupleType()){
			for(int i = 0; i < large.getArity(); i++){
				if(mayOccurIn(small, large.getFieldType(i), seen))
					return true;
			}
			return false;
		}
		if(large.isConstructorType()){
			for(int i = 0; i < large.getArity(); i++){
				if(mayOccurIn(small, large.getFieldType(i), seen))
					return true;
			}
			return false;
		}
		if(large.isAbstractDataType()){
			if(small.isNodeType() && !small.isAbstractDataType())
				return true;
			if(small.isConstructorType() && small.getAbstractDataType().equivalent(large.getAbstractDataType()))
				return true;
			seen.add(large);
			for(Type alt : getCurrentEnvt().lookupAlternatives(large)){				
				if(alt.isConstructorType()){
					for(int i = 0; i < alt.getArity(); i++){
						Type fType = alt.getFieldType(i);
						if(seen.add(fType) && mayOccurIn(small, fType, seen))
							return true;
					}
				} else
					throw new ImplementationError("ADT");

			}
			return false;
		}
		return small.isSubtypeOf(large);
	}

	


	// Ambiguity ...................................................

	@Override
	public Result<IValue> visitExpressionAmbiguity(Ambiguity x) {
		// TODO: assuming that that is the only reason for an expression to be ambiguous
		// we might also check if this is an "appl" constructor...
		
//			System.err.println("Env: " + currentEnvt);
//			int i = 0;
//			for (Expression exp: x.getAlternatives()) {
//				System.err.println("Alt " + i++ + ": " + exp.getTree());
//			}
		throw new ImplementationError("Unexpected ambiguous program part: " + x);
	}
	
	
		
	@Override
	public Result<IValue> visitStatementAmbiguity(
			org.meta_environment.rascal.ast.Statement.Ambiguity x) {
		throw new Ambiguous(x.toString());
	}

	// Modules -------------------------------------------------------------

	@Override
	public Result<IValue> visitImportDefault(
			org.meta_environment.rascal.ast.Import.Default x) {
		// TODO support for full complexity of import declarations
		String name = getUnescapedModuleName(x);

		// TODO: this logic cannot be understood...
		// handleSDFModule only loads the ParseTree module,
		// yet the SDF module *will* have been loaded

		if (isSDFModule(name)) {
			handleSDFModule(x);
			return nothing();
		}

		if (isNonExistingRascalModule(name)) {
			evalRascalModule(x, name);
		}
		else {
			reloadAllIfNeeded(x);
		}
		addImportToCurrentModule(x, name);
		return nothing();
	}

	protected void handleSDFModule(
			org.meta_environment.rascal.ast.Import.Default x) {
		loadParseTreeModule(x);
		getCurrentModuleEnvironment().addSDFImport(getUnescapedModuleName(x));
	}

	private void addImportToCurrentModule(
			org.meta_environment.rascal.ast.Import.Default x, String name) {
		getCurrentModuleEnvironment().addImport(name, heap.getModule(name, x));
	}

	private ModuleEnvironment getCurrentModuleEnvironment() {
		if (!(currentEnvt instanceof ModuleEnvironment)) {
			throw new ImplementationError("Current env should be a module environment");
		}
		return ((ModuleEnvironment) currentEnvt);
	}

	private boolean reloadNeeded() {
		return importResetsInterpreter && currentEnvt == rootScope;
	}

	private String getUnescapedModuleName(
			org.meta_environment.rascal.ast.Import.Default x) {
		String name = x.getModule().getName().toString();
		if (name.startsWith("\\")) {
			return name.substring(1);
		}
		return name;
	}

	private void loadParseTreeModule(
			org.meta_environment.rascal.ast.Import.Default x) {
		String parseTreeModName = "ParseTree";
		if (!heap.existsModule(parseTreeModName)) {
			evalRascalModule(x, parseTreeModName);
		}
		addImportToCurrentModule(x, parseTreeModName);
	}

	private boolean isSDFModule(String name) {
		return loader.isSdfModule(name);
	}

	private boolean isNonExistingRascalModule(String name) {
		return !heap.existsModule(name) && !isSDFModule(name);
	}

	protected Module evalRascalModule(AbstractAST x,
			String name) {
		Module module = loader.loadModule(name, x);

		if (module != null) {
			if (!getModuleName(module).equals(name)) {
				throw new ModuleNameMismatchError(getModuleName(module), name, x);
			}
			module.accept(this);
			return module;
		}
		return null;
		// it was an SDF module
	}


	protected void reloadAllIfNeeded(AbstractAST cause) {
		if (!reloadNeeded()) {
			return;
		}
		heap.clear();

		java.util.Set<String> topModules = getCurrentModuleEnvironment().getImports();
		for (String mod : topModules) {
			Module module = evalRascalModule(cause, mod);
			if (module != null) {
				getCurrentModuleEnvironment().addImport(mod, heap.getModule(mod, cause));
			}
		}
	}




	@Override 
	public Result<IValue> visitModuleDefault(
			org.meta_environment.rascal.ast.Module.Default x) {
		String name = getModuleName(x);

		if (!heap.existsModule(name)) {
			ModuleEnvironment env = new ModuleEnvironment(name);
			Environment oldEnv = getCurrentEnvt();
			setCurrentEnvt(env); // such that declarations end up in the module scope
			try {
				x.getHeader().accept(this);

				java.util.List<Toplevel> decls = x.getBody().getToplevels();
				typeDeclarator.evaluateDeclarations(decls, getCurrentEnvt());

				for (Toplevel l : decls) {
					l.accept(this);
				}

				// only after everything was successful add the module
				heap.addModule(env);
			}
			finally {
				setCurrentEnvt(oldEnv);
			}
		}
		return ResultFactory.nothing();
	}

	protected String getModuleName(
			Module module) {
		String name = module.getHeader().getName().toString();
		if (name.startsWith("\\")) {
			name = name.substring(1);
		}
		return name;
	}

	@Override
	public Result<IValue> visitHeaderDefault(
			org.meta_environment.rascal.ast.Header.Default x) {
		visitImports(x.getImports());
		return ResultFactory.nothing();
	}

	@Override
	public Result<IValue> visitDeclarationAlias(Alias x) {
		typeDeclarator.declareAlias(x, getCurrentEnvt());
		return ResultFactory.nothing();
	}

	@Override
	public Result<IValue> visitDeclarationData(Data x) {
		typeDeclarator.declareConstructor(x, getCurrentEnvt());
		return ResultFactory.nothing();
	}

	private void visitImports(java.util.List<Import> imports) {
		for (Import i : imports) {
			i.accept(this);
		}
	}

	@Override
	public Result<IValue> visitHeaderParameters(Parameters x) {
		visitImports(x.getImports());
		return ResultFactory.nothing();
	}

	@Override
	public Result<IValue> visitToplevelDefaultVisibility(DefaultVisibility x) {
		Result<IValue> r = x.getDeclaration().accept(this);
		r.setPublic(false);
		return r;
	}

	@Override
	public Result<IValue> visitToplevelGivenVisibility(GivenVisibility x) {
		Result<IValue> r = x.getDeclaration().accept(this);
		r.setPublic(x.getVisibility().isPublic());
		return r;
	}

	@Override
	public Result<IValue> visitDeclarationFunction(Function x) {
		return x.getFunctionDeclaration().accept(this);
	}

	@Override
	public Result<IValue> visitDeclarationVariable(Variable x) {
		Type declaredType = te.eval(x.getType(), getCurrentModuleEnvironment());
		Result<IValue> r = nothing();

		for (org.meta_environment.rascal.ast.Variable var : x.getVariables()) {
			// TODO: should this be on getCurrentModuleEnvironment?
			// (it probably is same env anyway).
			if(getCurrentEnvt().getLocalVariable(var.getName()) != null){
				throw new RedeclaredVariableError(var.getName().toString(), var);
			}
			if (var.isUnInitialized()) {  
				throw new UninitializedVariableError(var.toString(), var);
			}

			Result<IValue> v = var.getInitial().accept(this);
			if(v.getType().isSubtypeOf(declaredType)){
				// TODO: do we actually want to instantiate the locally bound type parameters?
				Map<Type,Type> bindings = new HashMap<Type,Type>();
				declaredType.match(v.getType(), bindings);
				declaredType = declaredType.instantiate(getCurrentEnvt().getStore(), bindings);
				r = makeResult(declaredType, v.getValue(), makeEvContext());
				getCurrentModuleEnvironment().storeInnermostVariable(var.getName(), r);
			} else {
				throw new UnexpectedTypeError(declaredType, v.getType(), var);
			}
		}

		return r;
	}

	@Override
	public Result<IValue> visitDeclarationAnnotation(Annotation x) {
		Type annoType = te.eval(x.getAnnoType(), getCurrentModuleEnvironment());
		String name = x.getName().toString();

		Type onType = te.eval(x.getOnType(), getCurrentModuleEnvironment());
		getCurrentModuleEnvironment().declareAnnotation(onType, name, annoType);	

		return ResultFactory.nothing();
	}


	private Type evalType(org.meta_environment.rascal.ast.Type type) {
		return te.eval(type, getCurrentEnvt());
	}

	@Override
	public Result<IValue> visitDeclarationView(View x) {
		// TODO implement
		throw new NotYetImplemented("Views");
	}

	@Override
	public Result<IValue> visitDeclarationRule(Rule x) {
		return x.getPatternAction().accept(this);
	}

	@Override
	public Result<IValue> visitPatternWithActionArbitrary(Arbitrary x) {
		IMatchingResult pv = (IMatchingResult) x.getPattern().accept(makePatternEvaluator(x));
		Type pt = pv.getType(getCurrentEnvt());
		if(!(pt.isAbstractDataType() || pt.isConstructorType() || pt.isNodeType()))
			throw new UnexpectedTypeError(tf.nodeType(), pt, x);
		heap.storeRule(pv.getType(getCurrentModuleEnvironment()), x, getCurrentModuleEnvironment());
		return ResultFactory.nothing();
	}

	private PatternEvaluator makePatternEvaluator(AbstractAST ast) {
		return new PatternEvaluator(vf, new EvaluatorContext(this, ast));
	}

	@Override
	public Result<IValue> visitPatternWithActionReplacing(Replacing x) {
		IMatchingResult pv = (IMatchingResult) x.getPattern().accept(makePatternEvaluator(x));
		Type pt = pv.getType(getCurrentEnvt());
		if(!(pt.isAbstractDataType() || pt.isConstructorType() || pt.isNodeType()))
			throw new UnexpectedTypeError(tf.nodeType(), pt, x);
		heap.storeRule(pv.getType(getCurrentModuleEnvironment()), x, getCurrentModuleEnvironment());
		return ResultFactory.nothing();
	}


	@Override
	public Result<IValue> visitDeclarationTag(Tag x) {
		throw new NotYetImplemented("tags");
	}

	// Variable Declarations -----------------------------------------------

	@Override
	public Result<IValue> visitLocalVariableDeclarationDefault(Default x) {
		// TODO deal with dynamic variables
		return x.getDeclarator().accept(this);
	}

	@Override
	public Result<IValue> visitDeclaratorDefault(
			org.meta_environment.rascal.ast.Declarator.Default x) {
		Type declaredType = evalType(x.getType());
		Result<IValue> r = ResultFactory.nothing();

		for (org.meta_environment.rascal.ast.Variable var : x.getVariables()) {
			String varAsString = var.getName().toString();
			if(getCurrentEnvt().getLocalVariable(varAsString) != null ||
					getCurrentEnvt().isRootScope() && getCurrentEnvt().getInnermostVariable(var.getName()) != null){
				throw new RedeclaredVariableError(varAsString, var);
			}
			if (var.isUnInitialized()) {  // variable declaration without initialization
				r = ResultFactory.makeResult(declaredType, null, new EvaluatorContext(this, var));
				getCurrentEnvt().storeInnermostVariable(var.getName(), r);
			} else {                     // variable declaration with initialization
				Result<IValue> v = var.getInitial().accept(this);
				if(v.getType().isSubtypeOf(declaredType)){
					// TODO: do we actually want to instantiate the locally bound type parameters?
					Map<Type,Type> bindings = new HashMap<Type,Type>();
					declaredType.match(v.getType(), bindings);
					declaredType = declaredType.instantiate(getCurrentEnvt().getStore(), bindings);
					// Was: r = makeResult(declaredType, applyRules(v.getValue()));
					r = makeResult(declaredType, v.getValue(), new EvaluatorContext(this, var));
					getCurrentEnvt().storeInnermostVariable(var.getName(), r);
				} else {
					throw new UnexpectedTypeError(declaredType, v.getType(), var);
				}
			}
		}

		return r;
	}

	private org.meta_environment.rascal.ast.QualifiedName makeQualifiedName(IConstructor node, String name) {
		Name simple = new Name.Lexical(node, name);
		java.util.List<Name> list = new ArrayList<Name>(1);
		list.add(simple);
		return new QualifiedName.Default(node, list);
	}

	@Override
	public Result<IValue> visitExpressionCallOrTree(CallOrTree x) {
		java.util.List<org.meta_environment.rascal.ast.Expression> args = x.getArguments();
		// TODO: deal with all the new expressions one can type in now like ("a" + "b")(hello);
		Expression nameExpr = x.getExpression();
		QualifiedName name;
		boolean unTyped = false;
		
		if (nameExpr.isQualifiedName() && getCurrentEnvt().getVariable(nameExpr.getQualifiedName()) == null) {
			name = nameExpr.getQualifiedName();
		}
		else if (nameExpr.isQualifiedName() && getCurrentEnvt().isDeclaredFunctionName(nameExpr.getQualifiedName())) {
			name = nameExpr.getQualifiedName();
		}
		else { // its a computed name or a string name
			Result result = nameExpr.accept(this);
			
			// TODO creating an AST here will make things slow, instead pass on the string name to the next phase
			if (result.getType().isStringType()) {
				String str = ((IString) result.getValue()).getValue();
				
				if (!tf.isIdentifier(str)) {
					throw RuntimeExceptionFactory.illegalIdentifier(str, nameExpr, getStackTrace());
				}
				name = makeQualifiedName((IConstructor) nameExpr.getTree(), str);
				unTyped = true;
			}
			else {
				throw new UnexpectedTypeError(tf.stringType(), result.getType(), nameExpr);
			}
		}
		
		IValue[] actuals = new IValue[args.size()];
		Type[] types = new Type[args.size()];
		boolean done = false;

		// TODO: this is also not quite acceptible, since other functions might be called appl...
		if (Names.name(Names.lastName(name)).equals("appl") && args.size() == 2) {
			Result<IValue> resultElem = args.get(0).accept(this);
			// TODO: it should be easier to detect this.... What is the accepted way?
			if (resultElem.getType() == Factory.Production && 
					((IConstructor)resultElem.getValue()).getConstructorType() == Factory.Production_List) {
				actuals[0] = resultElem.getValue();
				types[0] = resultElem.getType();
				concreteListsShouldBeSpliced = true;
				Result<IValue> argsResult = args.get(1).accept(this);
				concreteListsShouldBeSpliced = false;
				actuals[1] = argsResult.getValue();
				types[1] = argsResult.getType();
				done = true;
			}			
		}

		if (!done) {
			// The normal case
			for (int i = 0; i < args.size(); i++) {
				Result<IValue> resultElem = args.get(i).accept(this);
				types[i] = resultElem.getType();
				actuals[i] = resultElem.getValue();
			}
		}

		
		Type signature = tf.tupleType(types);

		if (unTyped || isTreeConstructorName(name, signature)) {
			return constructTree(name, actuals, signature);
		}

		return call(name, actuals, signature);
	}

	private Result<IValue> call(QualifiedName name, IValue[] actuals, Type actualTypes) {
		String moduleName = Names.moduleName(name);

		Result<IValue> func;
		if (moduleName == null) {
			Environment env = getCurrentEnvt();
			
			// TODO: store all functions in the variable environment, ditch the function environment
			func = env.getVariable(name);
			
			if (func == null) {
				func = env.getFunction(Names.name(Names.lastName(name)), actualTypes, name);
			}
			
			if (!(func instanceof Lambda)) {
				throw new UndeclaredFunctionError(name.toString(), name);
			}
		}
		else {
			ModuleEnvironment env = getCurrentEnvt().getImport(moduleName);
			if (env == null) {
				throw new UndeclaredModuleError(moduleName, name);
			}
			func = env.getLocalPublicFunction(Names.name(Names.lastName(name)), actualTypes, name);
		}
		
		if (func != null) {
			Environment oldEnv = getCurrentEnvt();
			pushCallFrame(((Lambda) func).getEnv(), name.getLocation(), ((Lambda) func).getName());
			try {
				return ((Lambda) func).call(actuals, actualTypes, getCurrentEnvt());
			}
			finally {
				setCurrentEnvt(oldEnv);
			}
		}
		undefinedFunctionException(name, actualTypes);
		return null;
	}


	private Environment pushCallFrame(Environment env, ISourceLocation loc, String name) {
		//create a new Environment with a defined caller scope and location
		Environment newEnv = new Environment(env, getCurrentEnvt(), getCurrentAST().getLocation(), loc, name);
		setCurrentEnvt(newEnv);
		return newEnv;
	}

	private void undefinedFunctionException(QualifiedName name, Type actualTypes) {
		StringBuffer sb = new StringBuffer();
		String sep = "";
		for(int i = 0; i < actualTypes.getArity(); i++){
			sb.append(sep);
			sep = ", ";
			sb.append(actualTypes.getFieldType(i).toString());
		}

		throw new UndeclaredFunctionError(name + "(" +  sb.toString() + ")", name);
	}

	private boolean isTreeConstructorName(QualifiedName name, Type signature) {
		return getCurrentEnvt().isTreeConstructorName(name, signature);
	}

	/**
	 * A top-down algorithm is needed to type check a constructor call since the
	 * result type of constructors can be overloaded. We bring down the expected type
	 * of each argument.
	 * @param expected
	 * @param functionName
	 * @param args
	 * @return
	 * 
	 * TODO: code does not deal with import structure, rather data def's are global.
	 * TODO: We now first build the tree and then apply rules to it. Change this so
	 * that we can avoid building the tree at all.
	 */
	private Result<IValue> constructTree(QualifiedName functionName, IValue[] actuals, Type signature) {
		String sort;
		String cons;
		Result<IValue> result = null;

		cons = Names.consName(functionName);
		sort = Names.sortName(functionName);

		Type candidate = null;

		if (sort != null) {
			Type sortType = getCurrentEnvt().getAbstractDataType(sort);

			if (sortType != null) {
				candidate = getCurrentEnvt().getConstructor(sortType, cons, signature);
			}
			else {
				return applyRules(ResultFactory.makeResult(tf.nodeType(), vf.node(cons, actuals), makeEvContext()));
			}
		}

		candidate = getCurrentEnvt().getConstructor(cons, signature);
		if (candidate != null) {
			Map<Type,Type> localBindings = new HashMap<Type,Type>();
			candidate.getFieldTypes().match(tf.tupleType(actuals), localBindings);

			Result<IValue> afterRules = applyRules(ResultFactory.makeResult(candidate.getAbstractDataType(), candidate.make(vf, actuals), 
					makeEvContext()));
			result = makeResult(candidate.getAbstractDataType().instantiate(new TypeStore(), localBindings), 
					afterRules.getValue(), makeEvContext());
		}
		else {
			result = applyRules(makeResult(tf.nodeType(), vf.node(cons, actuals), makeEvContext()));
		}

		declareConcreteSyntaxType(result.getValue());
		return detectConcreteSyntaxTree(result);
	}

	private EvaluatorContext makeEvContext() {
		return new EvaluatorContext(this, getCurrentAST());
	}

	private <T extends IValue> Result<T> detectConcreteSyntaxTree(Result<T> result) {
		if (result.getType() == Factory.Tree) {
			IConstructor tree = (IConstructor) result.getValue();

			Type cons = tree.getConstructorType();
			if (cons == Factory.Tree_Appl || cons == Factory.Tree_Amb) {
				TreeAdapter adapter = new TreeAdapter(tree);
				if (adapter.isAppl() && adapter.getProduction().getRhs().isCf()) {
					return (Result<T>) new ConcreteSyntaxResult(new ConcreteSyntaxType((IConstructor) result.getValue()), (IConstructor) result.getValue(), makeEvContext());
				}
			}
		}
		return result;
	}

	private boolean hasJavaModifier(FunctionDeclaration func) {
		java.util.List<FunctionModifier> mods = func.getSignature().getModifiers().getModifiers();
		for (FunctionModifier m : mods) {
			if (m.isJava()) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Result<IValue> visitFunctionBodyDefault(
			org.meta_environment.rascal.ast.FunctionBody.Default x) {
		Result<IValue> result = nothing();

		for (Statement statement : x.getStatements()) {
			setCurrentAST(statement);
			result = statement.accept(this);
		}

		return result;
	}

	// Statements ---------------------------------------------------------

	@Override
	public Result<IValue> visitStatementAssert(Assert x) {
		Result<IValue> r = x.getExpression().accept(this);
		if (!r.getType().equals(tf.boolType())) {
			throw new UnexpectedTypeError(tf.boolType(), r.getType(), x);	
		}

		if(r.getValue().isEqual(vf.bool(false))) {
			throw RuntimeExceptionFactory.assertionFailed(x, getStackTrace());
		}
		return r;	
	}

	@Override
	public Result<IValue> visitStatementAssertWithMessage(AssertWithMessage x) {
		Result<IValue> r = x.getExpression().accept(this);
		if (!r.getType().equals(tf.boolType())) {
			throw new UnexpectedTypeError(tf.boolType(),r.getType(), x);	
		}
		if(r.getValue().isEqual(vf.bool(false))){
			String str = x.getMessage().toString();
			IString msg = vf.string(unescape(str, x, getCurrentEnvt()));
			throw RuntimeExceptionFactory.assertionFailed(msg, getCurrentAST(), getStackTrace());
		}
		return r;	
	}

	@Override
	public Result<IValue> visitStatementVariableDeclaration(VariableDeclaration x) {
		return x.getDeclaration().accept(this);
	}

	@Override
	public Result<IValue> visitStatementExpression(Statement.Expression x) {
		Environment old = getCurrentEnvt();
		
		try {
			goodPushEnv();
			return x.getExpression().accept(this);
		}
		finally {
			unwind(old);
		}
	}

	@Override
	public Result<IValue> visitStatementFunctionDeclaration(
			org.meta_environment.rascal.ast.Statement.FunctionDeclaration x) {
		return x.getFunctionDeclaration().accept(this);
	}


	@Override
	public Result<IValue> visitExpressionSubscript(Subscript x) {		
		Result<IValue> expr = x.getExpression().accept(this);
		int nSubs = x.getSubscripts().size();
		Result<?> subscripts[] = new Result<?>[nSubs];
		for (int i = 0; i < nSubs; i++) {
			Expression subsExpr = x.getSubscripts().get(i);
			subscripts[i] = isWildCard(subsExpr.toString()) ? null : subsExpr.accept(this);
		}
		return expr.subscript(subscripts, new EvaluatorContext(this, x));
	}

	@Override
	public Result<IValue> visitExpressionFieldAccess(
			org.meta_environment.rascal.ast.Expression.FieldAccess x) {
		Result<IValue> expr = x.getExpression().accept(this);
		String field = x.getField().toString();
		return expr.fieldAccess(field, getCurrentEnvt().getStore(), new EvaluatorContext(this, x));
	}

	private boolean isWildCard(String fieldName){
		return fieldName.equals("_");
	}

	@Override
	public Result<IValue> visitExpressionFieldProject(FieldProject x) {
		// TODO: move to result classes
		Result<IValue>  base = x.getExpression().accept(this);

		java.util.List<Field> fields = x.getFields();
		int nFields = fields.size();
		int selectedFields[] = new int[nFields];

		if(base.getType().isTupleType()){
			Type fieldTypes[] = new Type[nFields];

			for(int i = 0 ; i < nFields; i++){
				Field f = fields.get(i);
				if(f.isIndex()){
					selectedFields[i] = ((IInteger) f.getFieldIndex().accept(this).getValue()).intValue();
				} else {
					String fieldName = f.getFieldName().toString();
					try {
						selectedFields[i] = base.getType().getFieldIndex(fieldName);
					} catch (UndeclaredFieldException e){
						throw new UndeclaredFieldError(fieldName, base.getType(), x);
					}
				}

				if (selectedFields[i] < 0 || selectedFields[i] > base.getType().getArity()) {
					throw RuntimeExceptionFactory.indexOutOfBounds(vf.integer(i), getCurrentAST(), getStackTrace());
				}
				fieldTypes[i] = base.getType().getFieldType(selectedFields[i]);
			}

		//	if(duplicateIndices(selectedFields)){
				// TODO: what does it matter if there are duplicate indices???
		//		throw new ImplementationError("Duplicate fields in projection");
		//	}
			Type resultType = nFields == 1 ? fieldTypes[0] : tf.tupleType(fieldTypes);

			// Was: return makeResult(resultType, applyRules(((ITuple)base.getValue()).select(selectedFields)));
			return makeResult(resultType, ((ITuple)base.getValue()).select(selectedFields), makeEvContext());
		}
		if(base.getType().isRelationType()){

			Type fieldTypes[] = new Type[nFields];

			for(int i = 0 ; i < nFields; i++){
				Field f = fields.get(i);
				if(f.isIndex()){
					selectedFields[i] = ((IInteger) f.getFieldIndex().accept(this).getValue()).intValue();
				} else {
					String fieldName = f.getFieldName().toString();
					try {
						selectedFields[i] = base.getType().getFieldIndex(fieldName);
					} catch (Exception e){
						throw new UndeclaredFieldError(fieldName, base.getType(), x);
					}
				}
				if(selectedFields[i] < 0 || selectedFields[i] > base.getType().getArity()) {
					throw RuntimeExceptionFactory.indexOutOfBounds(vf.integer(i), getCurrentAST(), getStackTrace());
				}
				fieldTypes[i] = base.getType().getFieldType(selectedFields[i]);
			}
		////	if(duplicateIndices(selectedFields)){
				// TODO: what does it matter if there are duplicate indices? Duplicate
				// field names may be a problem, but not this.
		//		throw new ImplementationError("Duplicate fields in projection");
		//	}
			Type resultType = nFields == 1 ? tf.setType(fieldTypes[0]) : tf.relType(fieldTypes);

			//return makeResult(resultType, applyRules(((IRelation)base.getValue()).select(selectedFields)));	
			return makeResult(resultType, ((IRelation)base.getValue()).select(selectedFields), makeEvContext());	
		}

		throw new UnsupportedOperationError("projection", base.getType(), x);
	}

	@Override
	public Result<IValue> visitStatementEmptyStatement(EmptyStatement x) {
		return ResultFactory.nothing();
	}

	@Override
	public Result<IValue> visitStatementFail(Fail x) {
		if (x.getFail().isWithLabel()) {
			throw new Failure(x.getFail().getLabel().toString());
		}

		throw new Failure();
	}

	@Override
	public Result<IValue> visitStatementReturn(
			org.meta_environment.rascal.ast.Statement.Return x) {
		org.meta_environment.rascal.ast.Return r = x.getRet();
		if (r.isWithExpression()) {
			throw new Return(x.getRet().getExpression().accept(this));
		}

		throw new Return(nothing());
	}

	@Override
	public Result<IValue> visitStatementBreak(Break x) {
		throw new NotYetImplemented(x.toString()); // TODO
	}

	@Override
	public Result<IValue> visitStatementContinue(Continue x) {
		throw new NotYetImplemented(x.toString()); // TODO
	}

	@Override
	public Result<IValue> visitStatementGlobalDirective(GlobalDirective x) {
		throw new NotYetImplemented(x.toString()); // TODO
	}

	@Override
	public Result<IValue> visitStatementThrow(Throw x) {
		throw new org.meta_environment.rascal.interpreter.control_exceptions.Throw(x.getExpression().accept(this).getValue(), getCurrentAST(), getStackTrace());
	}

	@Override
	public Result<IValue> visitStatementTry(Try x) {
		return evalStatementTry(x.getBody(), x.getHandlers(), null);
	}

	@Override
	public Result<IValue> visitStatementTryFinally(TryFinally x) {
		return evalStatementTry(x.getBody(), x.getHandlers(), x.getFinallyBody());
	}

	private Result<IValue> evalStatementTry(Statement body, java.util.List<Catch> handlers, Statement finallyBody){
		Result<IValue> res = nothing();

		try {
			res = body.accept(this);
		} catch (org.meta_environment.rascal.interpreter.control_exceptions.Throw e){
			IValue eValue = e.getException();

			for (Catch c : handlers){
				if(c.isDefault()){
					res = c.getBody().accept(this);
					break;
				} 

				// TODO: Throw should contain Result<IValue> instead of IValue
				if(matchAndEval(makeResult(eValue.getType(), eValue, makeEvContext()), c.getPattern(), c.getBody())){
					break;
				}
			}
		}
		finally {
			if (finallyBody != null) {
				finallyBody.accept(this);
			}
		}
		return res;
	}

	@Override
	public Result<IValue> visitStatementVisit(
			org.meta_environment.rascal.ast.Statement.Visit x) {
		return x.getVisit().accept(this);
	}

	@Override
	public Result<IValue> visitStatementInsert(Insert x) {
		throw new org.meta_environment.rascal.interpreter.control_exceptions.Insert(x.getExpression().accept(this));
	}

	@Override
	public Result<IValue> visitStatementAssignment(Assignment x) {
		Result<IValue> right = x.getExpression().accept(this);
		return x.getAssignable().accept(new AssignableEvaluator(getCurrentEnvt(), x.getOperator(), right, this));
	}

	@Override
	public Result<IValue> visitStatementBlock(Block x) {
		Result<IValue> r = nothing();
		Environment old = getCurrentEnvt();

		goodPushEnv(x);
		try {
			for (Statement stat : x.getStatements()) {
				setCurrentAST(stat);
				r = stat.accept(this);
			}
		}
		finally {
			unwind(old);
		}
		return r;
	}

	@Override
	public Result<IValue> visitAssignableVariable(
			org.meta_environment.rascal.ast.Assignable.Variable x) {
		return getCurrentEnvt().getVariable(x.getQualifiedName(),x.getQualifiedName().toString());
	}

	@Override
	public Result<IValue> visitAssignableFieldAccess(FieldAccess x) {
		Result<IValue> receiver = x.getReceiver().accept(this);
		String label = x.getField().toString();

		if (receiver.getType().isTupleType()) {
			IValue result = ((ITuple) receiver.getValue()).get(label);
			Type type = ((ITuple) receiver.getValue()).getType().getFieldType(label);
			return makeResult(type, result, new EvaluatorContext(this, x));
		}
		else if (receiver.getType().isConstructorType() || receiver.getType().isAbstractDataType()) {
			IConstructor cons = (IConstructor) receiver.getValue();
			Type node = cons.getConstructorType();

			if (!receiver.getType().hasField(label)) {
				throw new UndeclaredFieldError(label, receiver.getType(), x);
			}

			if (!node.hasField(label)) {
				throw RuntimeExceptionFactory.noSuchField(label, x,getStackTrace());
			}

			int index = node.getFieldIndex(label);
			return makeResult(node.getFieldType(index), cons.get(index), new EvaluatorContext(this, x));
		}
		else {
			throw new UndeclaredFieldError(label, receiver.getType(), x);
		}
	}

	@Override
	public Result<IValue> visitAssignableAnnotation(
			org.meta_environment.rascal.ast.Assignable.Annotation x) {
		Result<IValue> receiver = x.getReceiver().accept(this);
		String label = x.getAnnotation().toString();

		if (!getCurrentEnvt().declaresAnnotation(receiver.getType(), label)) {
			throw new UndeclaredAnnotationError(label, receiver.getType(), x);
		}

		Type type = getCurrentEnvt().getAnnotationType(receiver.getType(), label);
		IValue value = ((IConstructor) receiver.getValue()).getAnnotation(label);

		return makeResult(type, value, new EvaluatorContext(this, x));
	}

	@Override
	public Result<IValue> visitAssignableConstructor(Constructor x) {
		throw new ImplementationError("Constructor assignable does not represent a value");
	}

	@Override
	public Result<IValue> visitAssignableIfDefinedOrDefault(
			org.meta_environment.rascal.ast.Assignable.IfDefinedOrDefault x) {
		throw new ImplementationError("ifdefined assignable does not represent a value");
	}

	@Override
	public Result<IValue> visitAssignableSubscript(
			org.meta_environment.rascal.ast.Assignable.Subscript x) {
		Result<IValue> receiver = x.getReceiver().accept(this);
		Result<IValue> subscript = x.getSubscript().accept(this);

		if (receiver.getType().isListType()) {
			if (subscript.getType().isIntegerType()) {
				IList list = (IList) receiver.getValue();
				IValue result = list.get(((IInteger) subscript.getValue()).intValue());
				Type type = receiver.getType().getElementType();
				return normalizedResult(type, result);
			}

			throw new UnexpectedTypeError(tf.integerType(), subscript.getType(), x);
		}
		else if (receiver.getType().isMapType()) {
			Type keyType = receiver.getType().getKeyType();

			if (subscript.getType().isSubtypeOf(keyType)) {
				IValue result = ((IMap) receiver.getValue()).get(subscript.getValue());
				Type type = receiver.getType().getValueType();
				return makeResult(type, result, new EvaluatorContext(this, x));
			}

			throw new UnexpectedTypeError(keyType, subscript.getType(), x);
		}
		// TODO implement other subscripts
		throw new UnsupportedOperationError("subscript", receiver.getType(), x);
	}

	@Override
	public Result<IValue> visitAssignableTuple(
			org.meta_environment.rascal.ast.Assignable.Tuple x) {
		throw new ImplementationError("Tuple in assignable does not represent a value:" + x);
	}

	@Override
	public Result<IValue> visitAssignableAmbiguity(
			org.meta_environment.rascal.ast.Assignable.Ambiguity x) {
		throw new Ambiguous(x.toString());
	}

	@Override
	public Result visitFunctionDeclarationDefault(
			org.meta_environment.rascal.ast.FunctionDeclaration.Default x) {
		Lambda lambda;
		boolean varArgs = x.getSignature().getParameters().isVarArgs();

		if (hasJavaModifier(x)) {
			lambda = new JavaFunction(this, x, varArgs, getCurrentEnvt(), javaBridge);
		}
		else {
			if (!x.getBody().isDefault()) {
				throw new MissingModifierError("java", x);
			}

			lambda = new RascalFunction(this, x, varArgs, getCurrentEnvt());
		}

		String name = Names.name(x.getSignature().getName());
		getCurrentEnvt().storeFunction(name, lambda);

		return lambda;
	}

	@Override
	public Result visitFunctionDeclarationAbstract(Abstract x) {
		Lambda lambda = null;
		String funcName;
		boolean varArgs = x.getSignature().getParameters().isVarArgs();

		if (hasTag(x, "stringParser") || hasTag(x, "fileParser")) {
			funcName = setupParserFunction(x);
			if (hasTag(x, "stringParser")) {
				lambda = new ParserFunction(this, x, getCurrentEnvt(), loader);	
			}
			
			if (hasTag(x, "fileParser")) {
				lambda = new FileParserFunction(this, x, getCurrentEnvt(), loader);	
			}
			getCurrentEnvt().storeFunction(funcName, lambda);
			return lambda;
		}

		if (!hasJavaModifier(x)) {
			throw new MissingModifierError("java", x);
		}

		lambda = new org.meta_environment.rascal.interpreter.env.JavaMethod(this, x, varArgs, getCurrentEnvt(), javaBridge);
		String name = Names.name(x.getSignature().getName());
		getCurrentEnvt().storeFunction(name, lambda);

		return lambda;
	}

	private boolean hasTag(Abstract x, String tagName) {
		// TODO: check type and arity of of signature
		Tags tags = x.getTags();
		if (tags.hasAnnotations()) {
			for (org.meta_environment.rascal.ast.Tag tag : tags.getAnnotations()) {
				if (tag.getName().toString().equals(tagName)) {
					return true; 
				}
			}
		}
		return false;
	}

	
	private String setupParserFunction(Abstract x) {
		org.meta_environment.rascal.ast.Type type = x.getSignature().getType();
		// TODO: how should we get at the name, and why???
		// Why isn't the constructor representing the concrete syntax type enough?
		IConstructor cons = (IConstructor) Symbols.typeToSymbol(type);
		IConstructor cfSym = (IConstructor) cons.get(0);
		IValue typeValue = cfSym.get(0);
		if (!typeValue.getType().isStringType()) {
			throw new NonWellformedTypeError("result type of parser functions must be sorts", type);
		}
		String sortName = ((IString)cfSym.get(0)).getValue(); 
		getCurrentEnvt().concreteSyntaxType(sortName, cons);
		return Names.name(x.getSignature().getName());
	}

	@Override
	public Result<IValue> visitStatementIfThenElse(IfThenElse x) {
		Statement body = x.getThenStatement();
		java.util.List<Expression> generators = x.getConditions();
		int size = generators.size();
		IBooleanResult[] gens = new IBooleanResult[size];
		Environment[] olds = new Environment[size];
		Environment old = getCurrentEnvt();

		int i = 0;
		try {
			gens[0] = makeBooleanResult(generators.get(0));
			gens[0].init();
			olds[0] = getCurrentEnvt();
			while(i >= 0 && i < size) {		
				if(gens[i].hasNext() && gens[i].next()){
					if(i == size - 1){
						return body.accept(this);
					} else {
						i++;
						gens[i] = makeBooleanResult(generators.get(i));
						gens[i].init();
						olds[i] = getCurrentEnvt();
					}
				} else {
					unwind(olds[i]);
					i--;
				}
			}
		} finally {
			unwind(old);
		}

		return x.getElseStatement().accept(this);
	}




	@Override
	public Result<IValue> visitStatementIfThen(IfThen x) {
		Statement body = x.getThenStatement();
		java.util.List<Expression> generators = x.getConditions();
		int size = generators.size();
		IBooleanResult[] gens = new IBooleanResult[size];
		Environment[] olds = new Environment[size];
		Environment old = getCurrentEnvt();

		int i = 0;
		try {
			gens[0] = makeBooleanResult(generators.get(0));
			gens[0].init();
			olds[0] = getCurrentEnvt();
			while(i >= 0 && i < size) {		
				if(gens[i].hasNext() && gens[i].next()){
					if(i == size - 1){
						return body.accept(this);
					} else {
						i++;
						gens[i] = makeBooleanResult(generators.get(i));
						gens[i].init();
						olds[i] = getCurrentEnvt();
					}
				} else {
					unwind(olds[i]);
					i--;
				}
			}
		} finally {
			unwind(old);
		}
		return nothing();
	}

	@Override
	public Result<IValue> visitStatementWhile(While x) {
		Statement body = x.getBody();
		Expression generator = x.getCondition();
		IBooleanResult gen;
		Environment old = getCurrentEnvt();
		Result<IValue> result = nothing();

		// a while statement is different from a for statement, the body of the while can influence the
		// variables that are used to test the condition of the loop
		// while does not iterate over all possible matches, rather it produces every time the first match
		// that makes the condition true
		
		while (true) {
			try {
				gen = makeBooleanResult(generator);
				gen.init();
				goodPushEnv();
				if(gen.hasNext() && gen.next()){
					result = body.accept(this);
				}
				else {
					return result;
				}
			} finally {
				unwind(old);
			}
		}
	}
	
	@Override
	public Result<IValue> visitStatementDoWhile(DoWhile x) {
		Statement body = x.getBody();
		Expression generator = x.getCondition();
		IBooleanResult gen;
		Environment old = getCurrentEnvt();
		Result<IValue> result = nothing();

		while (true) {
			try {
				result = body.accept(this);
				
				gen = makeBooleanResult(generator);
				gen.init();
				if(!(gen.hasNext() && gen.next())) {
					return result;
				}
			} finally {
				unwind(old);
			}
		}
	}

	@Override
	public Result<IValue> visitExpressionMatch(Match x) {
		return evalBooleanExpression(x);
	}

	@Override
	public Result<IValue> visitExpressionNoMatch(NoMatch x) {
		return evalBooleanExpression(x);
	}

	// ----- General method for matching --------------------------------------------------

	public IBooleanResult makeBooleanResult(org.meta_environment.rascal.ast.Expression pat){
		if (pat instanceof Expression.Ambiguity) {
			// TODO: wrong exception here.
			throw new AmbiguousConcretePattern(pat);
		}

		BooleanEvaluator pe = new BooleanEvaluator(vf, makeEvContext());
		return pat.accept(pe);
	}

	// Expressions -----------------------------------------------------------

	@Override
	public Result<IValue> visitExpressionLiteral(Literal x) {
		return x.getLiteral().accept(this);
	}

	@Override
	public Result<IValue> visitLiteralInteger(Integer x) {
		return x.getIntegerLiteral().accept(this);
	}

	@Override
	public Result<IValue> visitLiteralReal(Real x) {
		String str = x.getRealLiteral().toString();
		return makeResult(tf.realType(), vf.real(str), new EvaluatorContext(this, x));
	}

	@Override
	public Result<IValue> visitLiteralBoolean(Boolean x) {
		String str = x.getBooleanLiteral().toString();
		return makeResult(tf.boolType(), vf.bool(str.equals("true")), new EvaluatorContext(this, x));
	}

	@Override
	public Result<IValue> visitLiteralString(
			org.meta_environment.rascal.ast.Literal.String x) {
		String str = ((StringLiteral.Lexical) x.getStringLiteral()).getString();
		return makeResult(tf.stringType(), vf.string(unescape(str, x, getCurrentEnvt())), new EvaluatorContext(this, x));
	}



	@Override
	public Result<IValue> visitIntegerLiteralDecimalIntegerLiteral(
			DecimalIntegerLiteral x) {
		String str = ((org.meta_environment.rascal.ast.DecimalIntegerLiteral.Lexical) x.getDecimal()).getString();
		return makeResult(tf.integerType(), vf.integer(str), new EvaluatorContext(this, x));
	}

	@Override
	public Result<IValue> visitExpressionQualifiedName(
			org.meta_environment.rascal.ast.Expression.QualifiedName x) {
		QualifiedName name = x.getQualifiedName();
		Result<IValue> result ;
		
		try {
			result = getCurrentEnvt().getVariable(name);
		}
		catch (UndeclaredModuleError e) {
			// TODO: find a better way to deal with this exception, BTW the Bool::and notation what this is for should dissappear
			// anyway and be replaced by Bool.and, so this problem should evaporate by itself.
			if (isTreeConstructorName(name, tf.tupleEmpty())) {
				return constructTree(name, new IValue[0], tf.tupleType(new Type[0]));
			}
			throw e;
		}
		
		if (result != null && result.getValue() != null) {
			return result;
		}

		// TODO: deal with overloading, now only accept unique ones
		// TODO: deal with qualified function names
		try {
			result = getCurrentEnvt().getFunction(Names.name(Names.lastName(name)), tf.voidType(), x);
		}
		catch (UndeclaredFunctionError e) {
			// TODO: change getFunction to return null instead
			result = null;
		}
		
		if (result != null) {
			return result;
		}
		
		if (isTreeConstructorName(name, tf.tupleEmpty())) {
			return constructTree(name, new IValue[0], tf.tupleType(new Type[0]));
		}

	
		throw new UninitializedVariableError(name.toString(), x);
	}

	@Override
	public Result<IValue> visitExpressionList(List x) {
		java.util.List<org.meta_environment.rascal.ast.Expression> elements = x
		.getElements();

		Type elementType =  tf.voidType();
		java.util.List<IValue> results = new ArrayList<IValue>();

		// Splicing is true for the complete list; a terrible, terrible hack.
		boolean splicing = concreteListsShouldBeSpliced;
		boolean first = true;
		int skip = 0;
		
		for (org.meta_environment.rascal.ast.Expression expr : elements) {
			Result<IValue> resultElem = expr.accept(this);
			
			if (skip > 0) {
				skip--;
				continue;
			}

			Type resultType = resultElem.getType();
			if (splicing && resultType instanceof ConcreteSyntaxType) {
				SymbolAdapter sym = new SymbolAdapter(((ConcreteSyntaxType)resultType).getSymbol());

				if (sym.isAnyList()) {
					IConstructor appl = ((IConstructor)resultElem.getValue());
					TreeAdapter tree = new TreeAdapter(appl);
					IList listElems = tree.getArgs();
					// Splice elements in list if element types permit this
					
					if (!listElems.isEmpty()) {
						for(IValue val : listElems){
							elementType = elementType.lub(val.getType());
							results.add(val);
						}
					}
					else {
						// make sure to remove surrounding sep
						if (!first) {
							if (sym.isCf()) {
								SymbolAdapter listSym = sym.getSymbol();
								if (listSym.isIterStar()) {
									results.remove(results.size() - 1);
								}
								else if (listSym.isIterStarSep()) {
									results.remove(results.size() - 1);
									results.remove(results.size() - 1);
									results.remove(results.size() - 1);
								}
							}
							if (sym.isLex()) {
								SymbolAdapter listSym = sym.getSymbol();
								if (listSym.isIterStarSep()) {
									results.remove(results.size() - 1);
									results.remove(results.size() - 1);
								}
							}
						}
						else {
							if (sym.isCf()) {
								SymbolAdapter listSym = sym.getSymbol();
								if (listSym.isIterStar()) {
									skip = 1;
								}
								else if (listSym.isIterStarSep()) {
									skip = 3;
								}
							}
							if (sym.isLex()) {
								SymbolAdapter listSym = sym.getSymbol();
								if (listSym.isIterStarSep()) {
									skip = 2;
								}
							}
						}
					}
				}
				else {
					// Just add it.
					elementType = elementType.lub(resultElem.getType());
					results.add(results.size(), resultElem.getValue());
				}
			}
			else {
				/* = no concrete syntax */ 
				if(resultElem.getType().isListType() &&
						!expr.isList() &&
						elementType.isSubtypeOf(resultElem.getType().getElementType())
				){
					/*
					 * Splice elements in list if element types permit this
					 */
					for(IValue val : ((IList) resultElem.getValue())){
						elementType = elementType.lub(val.getType());
						results.add(val);
					}
				} else {
					elementType = elementType.lub(resultElem.getType());

					results.add(results.size(), resultElem.getValue());
				}
			}
			
			
			first = false;
		}
		Type resultType = tf.listType(elementType);
		IListWriter w = resultType.writer(vf);
		w.appendAll(results);
		// Was: return makeResult(resultType, applyRules(w.done()));
		return makeResult(resultType, w.done(), new EvaluatorContext(this, x));
	}

	@Override
	public Result<IValue> visitExpressionSet(Set x) {
		java.util.List<org.meta_environment.rascal.ast.Expression> elements = x
		.getElements();

		Type elementType = tf.voidType();
		java.util.List<IValue> results = new ArrayList<IValue>();

		for (org.meta_environment.rascal.ast.Expression expr : elements) {
			Result<IValue> resultElem = expr.accept(this);
			if(resultElem.getType().isSetType() && !expr.isSet() &&
					elementType.isSubtypeOf(resultElem.getType().getElementType())){
				/*
				 * Splice the elements in the set if element types permit this.
				 */
				for(IValue val : ((ISet) resultElem.getValue())){
					elementType = elementType.lub(val.getType());
					results.add(val);
				}
			} else {
				elementType = elementType.lub(resultElem.getType());
				results.add(results.size(), resultElem.getValue());
			}
		}
		Type resultType = tf.setType(elementType);
		ISetWriter w = resultType.writer(vf);
		w.insertAll(results);
		//Was: return makeResult(resultType, applyRules(w.done()));
		return makeResult(resultType, w.done(), new EvaluatorContext(this, x));
	}

	@Override
	public Result<IValue> visitExpressionMap(
			org.meta_environment.rascal.ast.Expression.Map x) {

		java.util.List<org.meta_environment.rascal.ast.Mapping> mappings = x
		.getMappings();
		Map<IValue,IValue> result = new HashMap<IValue,IValue>();
		Type keyType = tf.voidType();
		Type valueType = tf.voidType();

		for (org.meta_environment.rascal.ast.Mapping mapping : mappings) {
			Result<IValue> keyResult = mapping.getFrom().accept(this);
			Result<IValue> valueResult = mapping.getTo().accept(this);

			keyType = keyType.lub(keyResult.getType());
			valueType = valueType.lub(valueResult.getType());

			result.put(keyResult.getValue(), valueResult.getValue());
		}

		Type type = tf.mapType(keyType, valueType);
		IMapWriter w = type.writer(vf);
		w.putAll(result);

		//return makeResult(type, applyRules(w.done()));
		return makeResult(type, w.done(), new EvaluatorContext(this, x));
	}

	@Override
	public Result visitExpressionNonEmptyBlock(NonEmptyBlock x) {
		return new Lambda(x, this, tf.voidType(), "", tf.tupleEmpty(), false, x.getStatements(), getCurrentEnvt());
	}

	@Override
	public Result<IValue> visitExpressionTuple(Tuple x) {
		java.util.List<org.meta_environment.rascal.ast.Expression> elements = x
		.getElements();

		IValue[] values = new IValue[elements.size()];
		Type[] types = new Type[elements.size()];

		for (int i = 0; i < elements.size(); i++) {
			Result<IValue> resultElem = elements.get(i).accept(this);
			types[i] = resultElem.getType();
			values[i] = resultElem.getValue();
		}

		//return makeResult(tf.tupleType(types), applyRules(vf.tuple(values)));
		return makeResult(tf.tupleType(types), vf.tuple(values), new EvaluatorContext(this, x));
	}

	@Override
	public Result<IValue> visitExpressionGetAnnotation(
			org.meta_environment.rascal.ast.Expression.GetAnnotation x) {
		Result<IValue> base = x.getExpression().accept(this);
		String annoName = x.getName().toString();
		return base.getAnnotation(annoName, getCurrentEnvt(), new EvaluatorContext(this, x));
	}

	@Override
	public Result<IValue> visitExpressionSetAnnotation(
			org.meta_environment.rascal.ast.Expression.SetAnnotation x) {
		Result<IValue> base = x.getExpression().accept(this);
		String annoName = x.getName().toString();
		Result<IValue> anno = x.getValue().accept(this);
		return base.setAnnotation(annoName, anno, getCurrentEnvt(), new EvaluatorContext(this, x));
	}

	@Override
	public Result<IValue> visitExpressionAddition(Addition x) {
		Result<IValue> left = x.getLhs().accept(this);
		Result<IValue> right = x.getRhs().accept(this);
		return left.add(right, new EvaluatorContext(this, x));

	}

	@Override
	public Result<IValue> visitExpressionSubtraction(Subtraction x) {
		Result<IValue> left = x.getLhs().accept(this);
		Result<IValue> right = x.getRhs().accept(this);
		return left.subtract(right, new EvaluatorContext(this, x));

	}

	@Override
	public Result<IValue> visitExpressionNegative(Negative x) {
		Result<IValue> arg = x.getArgument().accept(this);
		return arg.negative(new EvaluatorContext(this, x));
	}

	@Override
	public Result<IValue> visitExpressionProduct(Product x) {
		Result<IValue> left = x.getLhs().accept(this);
		Result<IValue> right = x.getRhs().accept(this);
		return left.multiply(right, new EvaluatorContext(this, x));
	}

	@Override
	public Result<IValue> visitExpressionJoin(Join x) {
		Result<IValue> left = x.getLhs().accept(this);
		Result<IValue> right = x.getRhs().accept(this);
		return left.join(right, new EvaluatorContext(this, x));
	}

	@Override
	public Result<IValue> visitExpressionDivision(Division x) {
		Result<IValue> left = x.getLhs().accept(this);
		Result<IValue> right = x.getRhs().accept(this);
		return left.divide(right, new EvaluatorContext(this, x));
	}

	@Override
	public Result<IValue> visitExpressionModulo(Modulo x) {
		Result<IValue> left = x.getLhs().accept(this);
		Result<IValue> right = x.getRhs().accept(this);
		return left.modulo(right, new EvaluatorContext(this, x));
	}

	@Override
	public Result<IValue> visitExpressionBracket(Bracket x) {
		return x.getExpression().accept(this);
	}

	@Override
	public Result<IValue> visitExpressionIntersection(Intersection x) {
		Result<IValue> left = x.getLhs().accept(this);
		Result<IValue> right = x.getRhs().accept(this);
		return left.intersect(right, new EvaluatorContext(this, x));
	}

	@Override
	public Result<IValue> visitExpressionOr(Or x) {
		return evalBooleanExpression(x);
	}

	@Override
	public Result<IValue> visitExpressionAnd(And x) {
		return evalBooleanExpression(x);
	}

	private Result<IValue> evalBooleanExpression(Expression x) {
		IBooleanResult mp = makeBooleanResult(x);
		mp.init();
		while(mp.hasNext()){
			if(mp.next()) {
				return ResultFactory.bool(true);
			}
		}
		return ResultFactory.bool(false);
	}
	
	@Override
	public Result<IValue> visitExpressionNegation(Negation x) {
		return evalBooleanExpression(x);
	}

	@Override
	public Result<IValue> visitExpressionImplication(Implication x) {
		return evalBooleanExpression(x);
	}

	@Override
	public Result<IValue> visitExpressionEquivalence(Equivalence x) {
		return evalBooleanExpression(x);
	}

	@Override
	public Result<IValue> visitExpressionEquals(
			org.meta_environment.rascal.ast.Expression.Equals x) {
		Result<IValue> left = x.getLhs().accept(this);
		Result<IValue> right = x.getRhs().accept(this);
		return left.equals(right, new EvaluatorContext(this, x));
	}

	@Override
	public Result<IValue> visitExpressionOperatorAsValue(OperatorAsValue x) {
		// TODO
		throw new NotYetImplemented(x);
	}

	@Override
	public Result<IValue> visitExpressionLocation(Location x){

		String urlText = x.getUrl().toString();

		Result<IValue> length = x.getLength().accept(this);
		int iLength = ((IInteger) length.getValue()).intValue();

		Result<IValue> offset = x.getOffset().accept(this);	
		int iOffset = ((IInteger) offset.getValue()).intValue();

		Result<IValue> beginLine = x.getBeginLine().accept(this);
		int iBeginLine = ((IInteger) beginLine.getValue()).intValue();

		Result<IValue> endLine = x.getEndLine().accept(this);
		int iEndLine = ((IInteger) endLine.getValue()).intValue();

		Result<IValue> beginColumn = x.getBeginColumn().accept(this);
		int iBeginColumn = ((IInteger) beginColumn.getValue()).intValue();

		Result<IValue> endColumn = x.getEndColumn().accept(this);
		int iEndColumn = ((IInteger) endColumn.getValue()).intValue();

		try {
			URL url = new URL(urlText);
			ISourceLocation r = vf.sourceLocation(url, iOffset, iLength, iBeginLine, iEndLine, iBeginColumn, iEndColumn);
			return makeResult(tf.sourceLocationType(), r, new EvaluatorContext(this, x));
		} catch (MalformedURLException e){
			throw new SyntaxError("location (malformed URL)", x.getLocation());
		}
	}

	@Override
	public Result visitExpressionClosure(Closure x) {
		Type formals = te.eval(x.getParameters(), getCurrentEnvt());
		Type returnType = evalType(x.getType());
		return new Lambda(x, this, returnType, "", formals, x.getParameters().isVarArgs(), x.getStatements(), getCurrentEnvt());
	}

	@Override
	public Result visitExpressionVoidClosure(VoidClosure x) {
		Type formals = te.eval(x.getParameters(), getCurrentEnvt());
		return new Lambda(x, this, tf.voidType(), "", formals, x.getParameters().isVarArgs(), x.getStatements(), getCurrentEnvt());
	}

	@Override
	public Result<IValue> visitExpressionFieldUpdate(FieldUpdate x) {
		Result<IValue> expr = x.getExpression().accept(this);
		Result<IValue> repl = x.getReplacement().accept(this);
		String name = x.getKey().toString();
		return expr.fieldUpdate(name, repl, getCurrentEnvt().getStore(), new EvaluatorContext(this, x));
	}


	@Override
	public Result<IValue> visitExpressionLexical(Lexical x) {
		throw new NotYetImplemented(x);// TODO
	}

	@Override
	public Result<IValue> visitExpressionRange(Range x) {
		//IListWriter w = vf.listWriter(tf.integerType());
		Result<IValue> from = x.getFirst().accept(this);
		Result<IValue> to = x.getLast().accept(this);
		return from.makeRange(to, new EvaluatorContext(this, x));
	}

	@Override
	public Result<IValue> visitExpressionStepRange(StepRange x) {
		Result<IValue> from = x.getFirst().accept(this);
		Result<IValue> to = x.getLast().accept(this);
		Result<IValue> second = x.getSecond().accept(this);
		return from.makeStepRange(to, second, new EvaluatorContext(this, x));
	}

	@Override
	public Result<IValue> visitExpressionTypedVariable(TypedVariable x) {
		// TODO: should allow qualified names in TypeVariables?!?
		Result<IValue> result = getCurrentEnvt().getVariable(Names.name(x.getName()));

		if (result != null && result.getValue() != null) {
			return result;
		}

		throw new UninitializedVariableError(x.getQualifiedName().toString(), x);
	}

	private boolean matchAndEval(Result<IValue> subject, org.meta_environment.rascal.ast.Expression pat, Statement stat){
		Environment old = getCurrentEnvt();
		
		try {
			IMatchingResult mp = (IMatchingResult) makeBooleanResult(pat);
			mp.initMatch(subject);
			//System.err.println("matchAndEval: subject=" + subject + ", pat=" + pat);
			while(mp.hasNext()){
				//System.err.println("matchAndEval: mp.hasNext()==true");
				if(mp.next()){
					//System.err.println("matchAndEval: mp.next()==true");
					try {
						checkPoint(getCurrentEnvt());
						//System.err.println(stat.toString());
						try {
							stat.accept(this);
						} catch (org.meta_environment.rascal.interpreter.control_exceptions.Insert e){
							// Make sure that the match pattern is set
							if(e.getMatchPattern() == null)
								e.setMatchPattern(mp);
							throw e;
						}
						commit(getCurrentEnvt());
						return true;
					} catch (Failure e){
						//System.err.println("failure occurred");
						rollback(getCurrentEnvt());
					}
				}
			}
		} finally {
			unwind(old);
		}
		return false;
	}

	private boolean matchEvalAndReplace(Result<IValue> subject, 
			org.meta_environment.rascal.ast.Expression pat, 
			java.util.List<Expression> conditions,
			Expression replacementExpr){
		Environment old = getCurrentEnvt();
		try {
			IMatchingResult mp = (IMatchingResult) makeBooleanResult(pat);
			mp.initMatch(subject);
			//System.err.println("matchEvalAndReplace: subject=" + subject + ", pat=" + pat + ", conditions=" + conditions);

			while(mp.hasNext()){
				//System.err.println("mp.hasNext()==true; mp=" + mp);
				if(mp.next()){
					try {
						boolean trueConditions = true;
						for(Expression cond : conditions){
							//System.err.println("cond = " + cond);
							if(!cond.accept(this).isTrue()){
								trueConditions = false;
								//System.err.println("false cond = " + cond);
								break;
							}
						}
						if(trueConditions){
							//System.err.println("evaluating replacement expression: " + replacementExpr);
							throw new org.meta_environment.rascal.interpreter.control_exceptions.Insert(replacementExpr.accept(this), mp);		
						}
					} catch (Failure e){
						//System.err.println("failure occurred");
					}
				}
			}
		} finally {
			unwind(old);
		}
		return false;
	}

	@Override
	public Result<IValue> visitStatementSwitch(Switch x) {
		Result<IValue> subject = x.getExpression().accept(this);

		for(Case cs : x.getCases()){
			if(cs.isDefault()){
				// TODO: what if the default statement uses a fail statement?
				return cs.getStatement().accept(this);
			}
			org.meta_environment.rascal.ast.PatternWithAction rule = cs.getPatternWithAction();
			if(rule.isArbitrary() && matchAndEval(subject, rule.getPattern(), rule.getStatement())){
				return ResultFactory.nothing();
				/*
			} else if(rule.isGuarded())	{
				org.meta_environment.rascal.ast.Type tp = rule.getType();
				Type t = evalType(tp);
				if(subject.getType().isSubtypeOf(t) && matchAndEval(subject.getValue(), rule.getPattern(), rule.getStatement())){
					return ResultFactory.nothing();
				}
				 */
			} else if(rule.isReplacing()){
				throw new NotYetImplemented(rule);
			}
		}
		return null;
	}

	@Override
	public Result<IValue> visitExpressionVisit(Visit x) {
		return x.getVisit().accept(this);
	}

	/*
	 * TraverseResult contains the value returned by a traversal
	 * and a changed flag that indicates whether the value itself or
	 * any of its children has been changed during the traversal.
	 */

	class TraverseResult {
		boolean matched;   // Some rule matched;
		Result<IValue> value; 		// Result<IValue> of the 
		boolean changed;   // Original subject has been changed

		TraverseResult(boolean someMatch, Result<IValue> value){
			this.matched = someMatch;
			this.value = value;
			this.changed = false;
		}

		TraverseResult(Result<IValue> value){
			this.matched = false;
			this.value = value;
			this.changed = false;
		}

		TraverseResult(Result<IValue> value, boolean changed){
			this.matched = true;
			this.value   = value;
			this.changed = changed;
		}
		TraverseResult(boolean someMatch, Result<IValue> value, boolean changed){
			this.matched = someMatch;
			this.value   = value;
			this.changed = changed;
		}
	}

	/*
	 * CaseOrRule is the union of a Case or a Rule and allows the sharing of
	 * traversal code for both.
	 */
	class CasesOrRules {
		private java.util.List<Case> cases;
		private java.util.List<RewriteRule> rules;

		CasesOrRules(java.util.List<?> casesOrRules){
			if(casesOrRules.get(0) instanceof Case){
				this.cases = (java.util.List<Case>) casesOrRules;
			} else {
				rules = (java.util.List<RewriteRule>)casesOrRules;
			}
		}

		public boolean hasRules(){
			return rules != null;
		}

		public boolean hasCases(){
			return cases != null;
		}

		public int length(){
			return (cases != null) ? cases.size() : rules.size();
		}

		public java.util.List<Case> getCases(){
			return cases;
		}
		public java.util.List<RewriteRule> getRules(){
			return rules;
		}
	}

	private TraverseResult traverse(Result<IValue> subject, CasesOrRules casesOrRules,
			DIRECTION direction, PROGRESS progress, FIXEDPOINT fixedpoint) {
		//System.err.println("traverse: subject=" + subject + ", casesOrRules=" + casesOrRules);
		do {
			TraverseResult tr = traverseOnce(subject, casesOrRules, direction, progress);
			if(fixedpoint == FIXEDPOINT.Yes){
				if (!tr.changed) {
					return tr;
				}
				subject = tr.value;
			} else {
				return tr;
			}
		} while (true);
	}

	/*
	 * StringReplacement represents a single replacement in the subject string.
	 */

	private class StringReplacement {
		int start;
		int end;
		String replacement;

		StringReplacement(int start, int end, String repl){
			this.start = start;
			this.end = end;
			replacement = repl;
		}

		@Override
		public String toString(){
			return "StringReplacement(" + start + ", " + end + ", " + replacement + ")";
		}
	}

	/*
	 *  singleCase returns a single case or rules if casesOrRueles has length 1 and null otherwise.
	 */

	private Object singleCase(CasesOrRules casesOrRules){
		if(casesOrRules.length() == 1){
			if(casesOrRules.hasCases()){
				return casesOrRules.getCases().get(0);
			}

			return casesOrRules.getRules().get(0);
		}
		return null;
	}

	/*
	 * traverString implements a visit of a string subject and applies the set of cases
	 * for all substrings of the subject. At the end, all replacements are applied and the modified
	 * subject is returned.
	 */

	// TODO: decouple visiting of strings from case statement
	private TraverseResult traverseString(Result<IValue> subject, CasesOrRules casesOrRules){
		String subjectString = ((IString) subject.getValue()).getValue();
		int len = subjectString.length();
		java.util.List<StringReplacement> replacements = new ArrayList<StringReplacement>();
		boolean matched = false;
		boolean changed = false;
		int cursor = 0;

		Case cs = (Case) singleCase(casesOrRules);

//		PatternEvaluator re = new PatternEvaluator(vf, this, getCurrentEnvt());
		if(cs != null && cs.isPatternWithAction() && cs.getPatternWithAction().getPattern().isLiteral() && cs.getPatternWithAction().getPattern().getLiteral().isRegExp()){
			/*
			 * In the frequently occurring case that there is one case with a regexp as pattern,
			 * we can delegate all the work to the regexp matcher.
			 */
			org.meta_environment.rascal.ast.PatternWithAction rule = cs.getPatternWithAction();

			Expression patexp = rule.getPattern();
			IMatchingResult mp = (IMatchingResult) makeBooleanResult(patexp);
			mp.initMatch(subject);
			Environment old = getCurrentEnvt();
			try {
				while(mp.hasNext()){
					if(mp.next()){
						try {
							if(rule.isReplacing()){
								Replacement repl = rule.getReplacement();
								boolean trueConditions = true;
								if(repl.isConditional()){
									for(Expression cond : repl.getConditions()){
										Result<IValue> res = cond.accept(this);
										if(!res.isTrue()){         // TODO: How about alternatives?
											trueConditions = false;
											break;
										}
									}
								}
								if(trueConditions){
									throw new org.meta_environment.rascal.interpreter.control_exceptions.Insert(repl.getReplacementExpression().accept(this), mp);
								}

							} else {
								rule.getStatement().accept(this);
							}
						} catch (org.meta_environment.rascal.interpreter.control_exceptions.Insert e){
							changed = true;
							IValue repl = e.getValue().getValue();
							if(repl.getType().isStringType()){
								int start = ((RegExpPatternValue) mp).getStart();
								int end = ((RegExpPatternValue) mp).getEnd();
								replacements.add(new StringReplacement(start, end, ((IString)repl).getValue()));
							} else {
								throw new UnexpectedTypeError(tf.stringType(),repl.getType(), rule);
							}
						} catch (Failure e){
							//System.err.println("failure occurred");
						}
					}
				}
			} finally {
				unwind(old);
			}
		} else {
			/*
			 * In all other cases we generate subsequent substrings subject[0,len], subject[1,len] ...
			 * and try to match all the cases.
			 * Performance issue: we create a lot of garbage by producing all these substrings.
			 */

			while(cursor < len){
				//System.err.println("cursor = " + cursor);
				try {
					IString substring = vf.string(subjectString.substring(cursor, len));
					Result<IValue> subresult  = ResultFactory.makeResult(tf.stringType(), substring, makeEvContext());
					TraverseResult tr = applyCasesOrRules(subresult, casesOrRules);
					matched |= tr.matched;
					changed |= tr.changed;
					//System.err.println("matched=" + matched + ", changed=" + changed);
					cursor++;
				} catch (org.meta_environment.rascal.interpreter.control_exceptions.Insert e){
					IValue repl = e.getValue().getValue();
					if(repl.getType().isStringType()){
						int start;
						int end;
						IBooleanResult lastPattern = e.getMatchPattern();
						if(lastPattern == null)
							throw new ImplementationError("no last pattern known");
						if(lastPattern instanceof RegExpPatternValue){
							start = ((RegExpPatternValue)lastPattern).getStart();
							end = ((RegExpPatternValue)lastPattern).getEnd();
						} else if(lastPattern instanceof LiteralPattern){
							start = 0;
							end = ((IString)repl).getValue().length();
						} else {
							throw new SyntaxError("Illegal pattern " + lastPattern + " in string visit", getCurrentAST().getLocation());
						}

						replacements.add(new StringReplacement(cursor + start, cursor + end, ((IString)repl).getValue()));
						matched = changed = true;
						cursor += end;
					} else {
						throw new UnexpectedTypeError(tf.stringType(),repl.getType(), getCurrentAST());
					}
				}
			}
		}

		if(!changed){
			return new TraverseResult(matched, subject, changed);
		}
		/*
		 * The replacements are now known. Create a version of the subject with all replacement applied.
		 */
		StringBuffer res = new StringBuffer();
		cursor = 0;
		for(StringReplacement sr : replacements){
			for( ;cursor < sr.start; cursor++){
				res.append(subjectString.charAt(cursor));
			}
			cursor = sr.end;
			res.append(sr.replacement);
		}
		for( ; cursor < len; cursor++){
			res.append(subjectString.charAt(cursor));
		}

		return new TraverseResult(matched, ResultFactory.makeResult(tf.stringType(), vf.string(res.toString()), makeEvContext()), changed);
	}

	/*
	 * traverseOnce: traverse an arbitrary IVAlue once. Implements the strategies bottomup/topdown.
	 */

	private TraverseResult traverseOnce(Result<IValue> subject, CasesOrRules casesOrRules, 
			DIRECTION direction, 
			PROGRESS progress){
		Type subjectType = subject.getType();
		boolean matched = false;
		boolean changed = false;
		Result<IValue> result = subject;

		//System.err.println("traverseOnce: " + subject + ", type=" + subject.getType());
		if(subjectType.isStringType()){
			return traverseString(subject, casesOrRules);
		}

		if(direction == DIRECTION.TopDown){
			TraverseResult tr = traverseTop(subject, casesOrRules);
			matched |= tr.matched;
			changed |= tr.changed;
			if((progress == PROGRESS.Breaking) && changed){
				return tr;
			}
			subject = tr.value;
		}

		if(subjectType.isAbstractDataType()){
			IConstructor cons = (IConstructor)subject.getValue();
			if(cons.arity() == 0){
				result = subject;
			} else {
				IValue args[] = new IValue[cons.arity()];

				for(int i = 0; i < cons.arity(); i++){
					IValue child = cons.get(i);
					Type childType = cons.getConstructorType().getFieldType(i);
					TraverseResult tr = traverseOnce(ResultFactory.makeResult(childType, child, makeEvContext()), casesOrRules, direction, progress);
					matched |= tr.matched;
					changed |= tr.changed;
					args[i] = tr.value.getValue();
				}
				IConstructor rcons = vf.constructor(cons.getConstructorType(), args);
				result = applyRules(makeResult(subjectType, rcons.setAnnotations(cons.getAnnotations()), makeEvContext()));
			}
		} else
			if(subjectType.isNodeType()){
				INode node = (INode)subject.getValue();
				if(node.arity() == 0){
					result = subject;
				} else {
					IValue args[] = new IValue[node.arity()];

					for(int i = 0; i < node.arity(); i++){
						IValue child = node.get(i);
						TraverseResult tr = traverseOnce(ResultFactory.makeResult(tf.valueType(), child, makeEvContext()), casesOrRules, direction, progress);
						matched |= tr.matched;
						changed |= tr.changed;
						args[i] = tr.value.getValue();
					}
					result = applyRules(makeResult(tf.nodeType(), vf.node(node.getName(), args).setAnnotations(node.getAnnotations()), makeEvContext()));
				}
			} else
				if(subjectType.isListType()){
					IList list = (IList) subject;
					int len = list.length();
					if(len > 0){
						IListWriter w = list.getType().writer(vf);
						Type elemType = list.getType().getElementType();
						
						for(int i = len - 1; i >= 0; i--){
							IValue elem = list.get(i);
							TraverseResult tr = traverseOnce(ResultFactory.makeResult(elemType, elem, makeEvContext()), casesOrRules, direction, progress);
							matched |= tr.matched;
							changed |= tr.changed;
							w.insert(tr.value.getValue());
						}
						result = makeResult(subjectType, w.done(), makeEvContext());
					} else {
						result = subject;
					}
				} else 
					if(subjectType.isSetType()){
						ISet set = (ISet) subject;
						if(!set.isEmpty()){
							ISetWriter w = set.getType().writer(vf);
							Type elemType = set.getType().getElementType();
							
							for (IValue v : set){
								TraverseResult tr = traverseOnce(ResultFactory.makeResult(elemType, v, makeEvContext()), casesOrRules, direction, progress);
								matched |= tr.matched;
								changed |= tr.changed;
								w.insert(tr.value.getValue());
							}
							result = makeResult(subjectType, w.done(), makeEvContext());
						} else {
							result = subject;
						}
					} else
						if (subjectType.isMapType()) {
							IMap map = (IMap) subject.getValue();
							if(!map.isEmpty()){
								IMapWriter w = map.getType().writer(vf);
								Iterator<Entry<IValue,IValue>> iter = map.entryIterator();
								Type keyType = map.getKeyType();
								Type valueType = map.getValueType();

								while (iter.hasNext()) {
									Entry<IValue,IValue> entry = iter.next();
									TraverseResult tr = traverseOnce(ResultFactory.makeResult(keyType, entry.getKey(), makeEvContext()), casesOrRules, direction, progress);
									matched |= tr.matched;
									changed |= tr.changed;
									IValue newKey = tr.value.getValue();
									tr = traverseOnce(ResultFactory.makeResult(valueType, entry.getValue(), makeEvContext()), casesOrRules, direction, progress);
									matched |= tr.matched;
									changed |= tr.changed;
									IValue newValue = tr.value.getValue();
									w.put(newKey, newValue);
								}
								result = makeResult(subjectType, w.done(), makeEvContext());
							} else {
								result = subject;
							}
						} else
							if(subjectType.isTupleType()){
								ITuple tuple = (ITuple) subject.getValue();
								int arity = tuple.arity();
								IValue args[] = new IValue[arity];
								for(int i = 0; i < arity; i++){
									Type fieldType = subjectType.getFieldType(i);
									TraverseResult tr = traverseOnce(ResultFactory.makeResult(fieldType, tuple.get(i), makeEvContext()), casesOrRules, direction, progress);
									matched |= tr.matched;
									changed |= tr.changed;
									args[i] = tr.value.getValue();
								}
								result = makeResult(subjectType, vf.tuple(args), makeEvContext());
							} else {
								result = subject;
							}

		if(direction == DIRECTION.BottomUp){
			
			if((progress == PROGRESS.Breaking) && changed){
				return new TraverseResult(matched, result, changed);
			}

			TraverseResult tr = traverseTop(result, casesOrRules);
			matched |= tr.matched;
			changed |= tr.changed;
			return new TraverseResult(matched, tr.value, changed);
		}
		return new TraverseResult(matched,result,changed);
	}

	/**
	 * Replace an old subject by a new one as result of an insert statement.
	 */
	private TraverseResult replacement(Result<IValue> oldSubject, Result<IValue> newSubject){
		if(newSubject.getType().equivalent((oldSubject.getType())))
			return new TraverseResult(true, newSubject, true);
		throw new UnexpectedTypeError(oldSubject.getType(), newSubject.getType(), getCurrentAST());
	}

	/**
	 * Loop over all cases or rules.
	 */

	private TraverseResult applyCasesOrRules(Result<IValue> subject, CasesOrRules casesOrRules) {
		if(casesOrRules.hasCases()){
			for (Case cs : casesOrRules.getCases()) {
				setCurrentAST(cs);
				if (cs.isDefault()) {
					cs.getStatement().accept(this);
					return new TraverseResult(true,subject);
				}

				TraverseResult tr = applyOneRule(subject, cs.getPatternWithAction());
				if(tr.matched){
					return tr;
				}
			}
		} else {
			//System.err.println("hasRules");

			for(RewriteRule rule : casesOrRules.getRules()){
				setCurrentAST(rule.getRule());
				Environment oldEnv = getCurrentEnvt();
				setCurrentEnvt(rule.getEnvironment());
				try {
					TraverseResult tr = applyOneRule(subject, rule.getRule());
					if(tr.matched){
						return tr;
					}
				}
				finally {
					setCurrentEnvt(oldEnv);
				}
			}
		}
		//System.err.println("applyCasesorRules does not match");
		return new TraverseResult(subject);
	}

	/*
	 * traverseTop: traverse the outermost symbol of the subject.
	 */

	private TraverseResult traverseTop(Result<IValue> subject, CasesOrRules casesOrRules) {
		//System.err.println("traversTop(" + subject + ")");
		try {
			return applyCasesOrRules(subject, casesOrRules);	
		} catch (org.meta_environment.rascal.interpreter.control_exceptions.Insert e) {

			return replacement(subject, e.getValue());
		}
	}

	/*
	 * applyOneRule: try to apply one rule to the subject.
	 */

	private TraverseResult applyOneRule(Result<IValue> subject,
			org.meta_environment.rascal.ast.PatternWithAction rule) {

		//System.err.println("applyOneRule: subject=" + subject + ", type=" + subject.getType() + ", rule=" + rule);

		if (rule.isArbitrary()){
			if(matchAndEval(subject, rule.getPattern(), rule.getStatement())) {
				return new TraverseResult(true, subject);
			}
			/*
		} else if (rule.isGuarded()) {
			org.meta_environment.rascal.ast.Type tp = rule.getType();
			Type type = evalType(tp);
			rule = rule.getRule();
			if (subject.getType().isSubtypeOf(type) && 
				matchAndEval(subject, rule.getPattern(), rule.getStatement())) {
				return new TraverseResult(true, subject);
			}
			 */
		} else if (rule.isReplacing()) {
			Replacement repl = rule.getReplacement();
			java.util.List<Expression> conditions = repl.isConditional() ? repl.getConditions() : new ArrayList<Expression>();
			if(matchEvalAndReplace(subject, rule.getPattern(), conditions, repl.getReplacementExpression())){
				return new TraverseResult(true, subject);
			}
		} else {
			throw new ImplementationError("Impossible case in rule");
		}
		return new TraverseResult(subject);
	}

	@Override
	public Result<IValue> visitVisitDefaultStrategy(DefaultStrategy x) {

		Result<IValue> subject = x.getSubject().accept(this);
		java.util.List<Case> cases = x.getCases();

		TraverseResult tr = traverse(subject, new CasesOrRules(cases), 
				DIRECTION.BottomUp,
				PROGRESS.Continuing,
				FIXEDPOINT.No);
		return tr.value;
	}

	@Override
	public Result<IValue> visitVisitGivenStrategy(GivenStrategy x) {
		Result<IValue> subject = x.getSubject().accept(this);
		
		// TODO: warning switched to static type here, but not sure if that's correct...
		Type subjectType = subject.getType();

		if(subjectType.isConstructorType()){
			subjectType = subjectType.getAbstractDataType();
		}

		java.util.List<Case> cases = x.getCases();
		Strategy s = x.getStrategy();

		DIRECTION direction = DIRECTION.BottomUp;
		PROGRESS progress = PROGRESS.Continuing;
		FIXEDPOINT fixedpoint = FIXEDPOINT.No;

		if(s.isBottomUp()){
			direction = DIRECTION.BottomUp;
		} else if(s.isBottomUpBreak()){
			direction = DIRECTION.BottomUp;
			progress = PROGRESS.Breaking;
		} else if(s.isInnermost()){
			direction = DIRECTION.BottomUp;
			fixedpoint = FIXEDPOINT.Yes;
		} else if(s.isTopDown()){
			direction = DIRECTION.TopDown;
		} else if(s.isTopDownBreak()){
			direction = DIRECTION.TopDown;
			progress = PROGRESS.Breaking;
		} else if(s.isOutermost()){
			direction = DIRECTION.TopDown;
			fixedpoint = FIXEDPOINT.Yes;
		} else {
			throw new ImplementationError("Unknown strategy " + s);
		}

		TraverseResult tr = traverse(subject, new CasesOrRules(cases), direction, progress, fixedpoint);
		return tr.value;
	}

	@Override
	public Result<IValue> visitExpressionNonEquals(
			org.meta_environment.rascal.ast.Expression.NonEquals x) {
		Result<IValue> left = x.getLhs().accept(this);
		Result<IValue> right = x.getRhs().accept(this);
		return left.nonEquals(right, new EvaluatorContext(this, x));
	}


	@Override
	public Result<IValue> visitExpressionLessThan(LessThan x) {
		Result<IValue> left = x.getLhs().accept(this);
		Result<IValue> right = x.getRhs().accept(this);
		return left.lessThan(right, new EvaluatorContext(this, x));
	}

	@Override
	public Result<IValue> visitExpressionLessThanOrEq(LessThanOrEq x) {
		Result<IValue> left = x.getLhs().accept(this);
		Result<IValue> right = x.getRhs().accept(this);
		return left.lessThanOrEqual(right, new EvaluatorContext(this, x));
	}
	@Override
	public Result<IValue> visitExpressionGreaterThan(GreaterThan x) {
		Result<IValue> left = x.getLhs().accept(this);
		Result<IValue> right = x.getRhs().accept(this);
		return left.greaterThan(right, new EvaluatorContext(this, x));
	}

	@Override
	public Result<IValue> visitExpressionGreaterThanOrEq(GreaterThanOrEq x) {
		Result<IValue> left = x.getLhs().accept(this);
		Result<IValue> right = x.getRhs().accept(this);
		return left.greaterThanOrEqual(right, new EvaluatorContext(this, x));
	}

	@Override
	public Result<IValue> visitExpressionIfThenElse(
			org.meta_environment.rascal.ast.Expression.IfThenElse x) {
		Result<IValue> cval = x.getCondition().accept(this);

		if (!cval.getType().isBoolType()) {
			throw new UnexpectedTypeError(tf.boolType(), cval.getType(), x);
		}

		if (cval.isTrue()) {
			return x.getThenExp().accept(this);
		}
		
		return x.getElseExp().accept(this);	
	}


	@Override
	public Result<IValue> visitExpressionIfDefinedOtherwise(IfDefinedOtherwise x) {
		try {
			return x.getLhs().accept(this);
		}
		catch (UninitializedVariableError e){
			return x.getRhs().accept(this);
		}
		catch (org.meta_environment.rascal.interpreter.control_exceptions.Throw e) {
			// TODO For now we accept any Throw here, restrict to NoSuchKey and NoSuchAnno?
			return x.getRhs().accept(this);
		}
	}


	@Override
	public Result<IValue> visitExpressionIsDefined(IsDefined x) {
		try {
			x.getArgument().accept(this); // wait for exception
			return makeResult(tf.boolType(), vf.bool(true), new EvaluatorContext(this, x));

		} catch (org.meta_environment.rascal.interpreter.control_exceptions.Throw e) {
			// TODO For now we accept any Throw here, restrict to NoSuchKey and NoSuchAnno?
			return makeResult(tf.boolType(), vf.bool(false), new EvaluatorContext(this, x));
		}
	}


	@Override
	public Result<IValue> visitExpressionIn(In x) {
		Result<IValue> left = x.getLhs().accept(this);
		Result<IValue> right = x.getRhs().accept(this);
		// TODO:?!?!!? makes this less obtrusive?
		return right.in(left, new EvaluatorContext(this, x));
		//return result(vf.bool(in(x.getLhs(), x.getRhs())));
	}

	@Override
	public Result<IValue> visitExpressionNotIn(NotIn x) {
		Result<IValue> left = x.getLhs().accept(this);
		Result<IValue> right = x.getRhs().accept(this);
		// TODO:?!?!!? makes this less obtrusive?
		return right.notIn(left, new EvaluatorContext(this, x));
		//return result(vf.bool(!in(x.getLhs(), x.getRhs())));
	}

	@Override
	public Result<IValue> visitExpressionComposition(Composition x) {
		Result<IValue> left = x.getLhs().accept(this);
		Result<IValue> right = x.getRhs().accept(this);
		return left.compose(right, new EvaluatorContext(this, x));
	}

	@Override
	public Result<IValue> visitExpressionTransitiveClosure(TransitiveClosure x) {
		return x.getArgument().accept(this).transitiveClosure(new EvaluatorContext(this, x));
	}

	@Override
	public Result<IValue> visitExpressionTransitiveReflexiveClosure(TransitiveReflexiveClosure x) {
		return x.getArgument().accept(this).transitiveReflexiveClosure(new EvaluatorContext(this, x));
	}

	// Comprehensions ----------------------------------------------------

	@Override
	public Result<IValue> visitExpressionComprehension(Comprehension x) {
		return x.getComprehension().accept(this);	
	}

	@Override
	public Result<IValue> visitExpressionEnumerator(
			org.meta_environment.rascal.ast.Expression.Enumerator x) {
		return evalBooleanExpression(x);
	}

	@Override
	public Result<IValue> visitExpressionEnumeratorWithStrategy(
			EnumeratorWithStrategy x) {
		return evalBooleanExpression(x);
	}

	private Result<IValue> makeGenerator(Expression x){
			return evalBooleanExpression(x);
	}

	/*
	 * ComprehensionWriter provides a uniform framework for writing elements
	 * to a list/set/map during the evaluation of a list/set/map comprehension.
	 */

	private abstract class ComprehensionWriter {
		protected Type elementType1;
		protected Type elementType2;
		protected Type resultType;
		protected java.util.List<org.meta_environment.rascal.ast.Expression> resultExprs;
		protected IWriter writer;
		protected Evaluator ev;

		ComprehensionWriter(
				java.util.List<org.meta_environment.rascal.ast.Expression> resultExprs,
				Evaluator ev){
			this.ev = ev;
			this.resultExprs = resultExprs;
			this.writer = null;
		}

		public void check(Result<IValue> r, Type t, String kind, org.meta_environment.rascal.ast.Expression expr){
			if(!r.getType().isSubtypeOf(t)){
				throw new UnexpectedTypeError(t, r.getType() ,
						expr);
			}
		}

		public EvaluatorContext getContext(AbstractAST ast) {
			return new EvaluatorContext(ev, ast);
		}

		public abstract void append();


		public abstract Result<IValue> done();
	}

	private class ListComprehensionWriter extends
	ComprehensionWriter {

		private boolean splicing[];
			
			ListComprehensionWriter(
				java.util.List<org.meta_environment.rascal.ast.Expression> resultExprs,
				Evaluator ev) {
			super(resultExprs, ev);
			splicing = new boolean[resultExprs.size()];
		}

		@Override
		public void append() {
			if(writer == null){
				int k = 0;
				elementType1 = tf.voidType();
				for(Expression resExpr : resultExprs){
					Result<IValue> res = resExpr.accept(ev);
					Type elementType = res.getType();
					if(elementType.isListType() && !resExpr.isList()){
						elementType = elementType.getElementType();
						splicing[k] = true;
					} else
						splicing[k] = false;
					k++;
					elementType1 = elementType1.lub(elementType);
				}
				resultType = tf.listType(elementType1);		
				writer = resultType.writer(vf);
			}
			int k = 0;
			for(Expression resExpr : resultExprs){
				Result<IValue> res = resExpr.accept(ev);
				if(splicing[k++]){
					/*
					 * Splice elements of the value of the result expression in the result list
					 */
					for(IValue val : ((IList) res.getValue())){
						if(!val.getType().isSubtypeOf(elementType1))
							throw new UnexpectedTypeError(elementType1, val.getType(), resExpr);
						elementType1 = elementType1.lub(val.getType());
						((IListWriter) writer).append(val);
					}
				} else {
					check(res, elementType1, "list", resExpr);
					elementType1 = elementType1.lub(res.getType());
					((IListWriter) writer).append(res.getValue());
				}
			}
		}

		@Override
		public Result<IValue> done() {
			return (writer == null) ? makeResult(tf.listType(tf.voidType()), vf.list(), getContext(resultExprs.get(0))) : 
				makeResult(tf.listType(elementType1), writer.done(), getContext(resultExprs.get(0)));
		}
	}

	private class SetComprehensionWriter extends
	ComprehensionWriter {
		
		private boolean splicing[];

		SetComprehensionWriter(
				java.util.List<org.meta_environment.rascal.ast.Expression> resultExprs,
				Evaluator ev) {
			super(resultExprs, ev);
			splicing = new boolean[resultExprs.size()];
		}
		
		@Override
		public void append() {
			if(writer == null){
				int k = 0;
				elementType1 = tf.voidType();
				for(Expression resExpr : resultExprs){
					Result<IValue> res = resExpr.accept(ev);
					Type elementType = res.getType();
					if(elementType.isSetType() && !resExpr.isSet()){
						elementType = elementType.getElementType();
						splicing[k] = true;
					} else
						splicing[k] = false;
					k++;
					elementType1 = elementType1.lub(elementType);
				}
				resultType = tf.setType(elementType1);		
				writer = resultType.writer(vf);
			}
			int k = 0;
			for(Expression resExpr : resultExprs){
				Result<IValue> res = resExpr.accept(ev);
				if(splicing[k++]){
					/*
					 * Splice elements of the value of the result expression in the result set
					 */
					for(IValue val : ((ISet) res.getValue())){
						if(!val.getType().isSubtypeOf(elementType1))
							throw new UnexpectedTypeError(elementType1, val.getType(), resExpr);
						elementType1 = elementType1.lub(val.getType());
						((ISetWriter) writer).insert(val);
					}
				} else {
					check(res, elementType1, "set", resExpr);
					elementType1 = elementType1.lub(res.getType());
					((ISetWriter) writer).insert(res.getValue());
				}
			}
		}
	
		@Override
		public Result<IValue> done() {
			return (writer == null) ? makeResult(tf.setType(tf.voidType()), vf.set(), getContext(resultExprs.get(0))) : 
				makeResult(tf.setType(elementType1), writer.done(), getContext(resultExprs.get(0)));
		}
	}

	private class MapComprehensionWriter extends
	ComprehensionWriter {

		MapComprehensionWriter(
				java.util.List<org.meta_environment.rascal.ast.Expression> resultExprs,
				Evaluator ev) {
			super(resultExprs, ev);
			if(resultExprs.size() != 2)
				throw new ImplementationError("Map comprehensions needs two result expressions");
		}

		@Override
		public void append() {
			Result<IValue> r1 = resultExprs.get(0).accept(ev);
			Result<IValue> r2 = resultExprs.get(1).accept(ev);
			if (writer == null) {
				elementType1 = r1.getType();
				elementType2 = r2.getType();
				resultType = tf.mapType(elementType1, elementType2);
				writer = resultType.writer(vf);
			}
			check(r1, elementType1, "map", resultExprs.get(0));
			check(r2, elementType2, "map", resultExprs.get(1));
			((IMapWriter) writer).put(r1.getValue(), r2.getValue());
		}

		@Override
		public Result<IValue> done() {
			return (writer == null) ? 
					makeResult(tf.mapType(tf.voidType(), tf.voidType()), vf.map(tf.voidType(), tf.voidType()), getContext(resultExprs.get(0)))
					: makeResult(tf.mapType(elementType1, elementType2), writer.done(), getContext(resultExprs.get(0)));
		}
	}


	/*
	 * The common comprehension evaluator
	 */

	private Result<IValue> evalComprehension(java.util.List<Expression> generators, 
			ComprehensionWriter w){
		int size = generators.size();
		IBooleanResult[] gens = new IBooleanResult[size];
		Environment[] olds = new Environment[size];
		Environment old = getCurrentEnvt();
		int i = 0;
		
		try {
			gens[0] = makeBooleanResult(generators.get(0));
			gens[0].init();
			olds[0] = getCurrentEnvt();
			goodPushEnv();

			while (i >= 0 && i < size) {
				if (gens[i].hasNext() && gens[i].next()) {
					if(i == size - 1){
						w.append();
						unwind(olds[i]);
						goodPushEnv();
					} 
					else {
						i++;
						gens[i] = makeBooleanResult(generators.get(i));
						gens[i].init();
						olds[i] = getCurrentEnvt();
						goodPushEnv();
					}
				} else {
					unwind(olds[i]);
					i--;
				}
			}
		}
		finally {
			unwind(old);
		}
		return w.done();
	}

	@Override
	public Result<IValue> visitComprehensionList(org.meta_environment.rascal.ast.Comprehension.List x) {
		return evalComprehension(
				x.getGenerators(),
				new ListComprehensionWriter(x.getResults(), this));
	}

	@Override
	public Result<IValue> visitComprehensionSet(
			org.meta_environment.rascal.ast.Comprehension.Set x) {
		return evalComprehension(
				x.getGenerators(),
				new SetComprehensionWriter(x.getResults(), this));
	}

	@Override
	public Result<IValue> visitComprehensionMap(
			org.meta_environment.rascal.ast.Comprehension.Map x) {
		java.util.List<Expression> resultExprs = new LinkedList<Expression>();
		resultExprs.add(x.getFrom());
		resultExprs.add(x.getTo());
		return evalComprehension(
				x.getGenerators(),
				new MapComprehensionWriter(resultExprs, this));
	}

	@Override
	public Result<IValue> visitStatementFor(For x) {
		Statement body = x.getBody();
		java.util.List<Expression> generators = x.getGenerators();
		int size = generators.size();
		IBooleanResult[] gens = new IBooleanResult[size];
		Environment old = getCurrentEnvt();
		Environment[] olds = new Environment[size];
		Result<IValue> result = nothing();

		// TODO: does this prohibit that the body influences the behavior of the generators??
		
		int i = 0;
		try {
			gens[0] = makeBooleanResult(generators.get(0));
			gens[0].init();
			olds[0] = getCurrentEnvt();
			while(i >= 0 && i < size) {		
				if(gens[i].hasNext() && gens[i].next()){
					if(i == size - 1){
						result = body.accept(this);
					} else {
						i++;
						gens[i] = makeBooleanResult(generators.get(i));
						gens[i].init();
						olds[i] = getCurrentEnvt();
					}
				} else {
					unwind(olds[i]);
					i--;
				}
			}
		} finally {
			unwind(old);
		}
		return result;
	}

	@Override
	public Result visitExpressionAny(Any x) {
		java.util.List<Expression> generators = x.getGenerators();
		int size = generators.size();
		IBooleanResult[] gens = new IBooleanResult[size];

		int i = 0;
		gens[0] = makeBooleanResult(generators.get(0));
		gens[0].init();
		while (i >= 0 && i < size) {
			if (gens[i].hasNext() && gens[i].next()) {
				if (i == size - 1) {
					return new BoolResult(true, null, null);
				}

				i++;
				gens[i] = makeBooleanResult(generators.get(i));
				gens[i].init();
			} else {
				i--;
			}
		}
		return new BoolResult(false, null, null);
	}

	@Override
	public Result visitExpressionAll(All x) {
		java.util.List<Expression> producers = x.getGenerators();
		int size = producers.size();
		IBooleanResult[] gens = new IBooleanResult[size];

		int i = 0;
		gens[0] = makeBooleanResult(producers.get(0));
		gens[0].init();
		while (i >= 0 && i < size) {
			if (gens[i].hasNext()) {
				if (!gens[i].next()) {
					return new BoolResult(false, null, null);
				}
				if (i < size - 1) {
					i++;
					gens[i] = makeBooleanResult(producers.get(i));
					gens[i].init();
				}
			} else {
				i--;
			}
		}
		return new BoolResult(true, null, null);
	}

	// ------------ solve -----------------------------------------

	@Override
	public Result<IValue> visitStatementSolve(Solve x) {
		java.util.ArrayList<org.meta_environment.rascal.ast.Variable> vars = new java.util.ArrayList<org.meta_environment.rascal.ast.Variable>();
		Environment old = getCurrentEnvt();

		goodPushEnv();
		try {
			for(Declarator d : x.getDeclarations()){
				for(org.meta_environment.rascal.ast.Variable v : d.getVariables()){
					vars.add(v);
				}
				d.accept(this);
			}
			IValue currentValue[] = new IValue[vars.size()];
			for(int i = 0; i < vars.size(); i++){
				org.meta_environment.rascal.ast.Variable v = vars.get(i);
				currentValue[i] = getCurrentEnvt().getVariable(v, Names.name(v.getName())).getValue();
			}

			Statement body = x.getBody();

			int max = -1;

			Bound bound= x.getBound();
			if(bound.isDefault()){
				Result<IValue> res = bound.getExpression().accept(this);
				if(!res.getType().isIntegerType()){
					throw new UnexpectedTypeError(tf.integerType(),res.getType(), x);
				}
				max = ((IInteger)res.getValue()).intValue();
				if(max <= 0){
					throw RuntimeExceptionFactory.indexOutOfBounds((IInteger) res.getValue(), getCurrentAST(), getStackTrace());
				}
			}

			Result<IValue> bodyResult = null;

			boolean change = true;
			int iterations = 0;

			while (change && (max == -1 || iterations < max)){
				change = false;
				iterations++;
				bodyResult = body.accept(this);
				for(int i = 0; i < vars.size(); i++){
					org.meta_environment.rascal.ast.Variable var = vars.get(i);
					Result<IValue> v = getCurrentEnvt().getVariable(var, Names.name(var.getName()));
					if(currentValue[i] == null || !v.getValue().isEqual(currentValue[i])){
						change = true;
						currentValue[i] = v.getValue();
					}
				}
			}
			return bodyResult;
		}
		finally {
			unwind(old);
		}
	}

	public Stack<Environment> getCallStack() {
		Stack<Environment> stack = new Stack<Environment>();
		Environment env = currentEnvt;
		while (env != null) {
			stack.add(0, env);
			env = env.getCallerScope();
		}
		return stack;
	}

	public ModuleLoader getModuleLoader() {
		return loader;
	}

	public Environment getCurrentEnvt() {
		return currentEnvt;
	}

	public void setCurrentEnvt(Environment env) {
		currentEnvt = env;
	}

	public IConstructor parseCommand(String command) throws IOException {
		throw new ImplementationError("should not be called in Evaluator but only in subclasses");
	}


}
