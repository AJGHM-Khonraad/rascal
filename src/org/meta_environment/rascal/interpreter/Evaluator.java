
package org.meta_environment.rascal.interpreter;

import java.io.IOException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.imp.pdb.facts.IBool;
import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IInteger;
import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IListWriter;
import org.eclipse.imp.pdb.facts.IMap;
import org.eclipse.imp.pdb.facts.IMapWriter;
import org.eclipse.imp.pdb.facts.INode;
import org.eclipse.imp.pdb.facts.IReal;
import org.eclipse.imp.pdb.facts.IRelation;
import org.eclipse.imp.pdb.facts.IRelationWriter;
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
import org.meta_environment.rascal.ast.ASTFactory;
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
import org.meta_environment.rascal.ast.Toplevel;
import org.meta_environment.rascal.ast.Assignable.Constructor;
import org.meta_environment.rascal.ast.Assignable.FieldAccess;
import org.meta_environment.rascal.ast.ClosureAsFunction.Evaluated;
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
import org.meta_environment.rascal.ast.Expression.ClosureCall;
import org.meta_environment.rascal.ast.Expression.Composition;
import org.meta_environment.rascal.ast.Expression.Comprehension;
import org.meta_environment.rascal.ast.Expression.Division;
import org.meta_environment.rascal.ast.Expression.Equivalence;
import org.meta_environment.rascal.ast.Expression.FieldProject;
import org.meta_environment.rascal.ast.Expression.FieldUpdate;
import org.meta_environment.rascal.ast.Expression.FunctionAsValue;
import org.meta_environment.rascal.ast.Expression.GreaterThan;
import org.meta_environment.rascal.ast.Expression.GreaterThanOrEq;
import org.meta_environment.rascal.ast.Expression.IfDefinedOtherwise;
import org.meta_environment.rascal.ast.Expression.Implication;
import org.meta_environment.rascal.ast.Expression.In;
import org.meta_environment.rascal.ast.Expression.Intersection;
import org.meta_environment.rascal.ast.Expression.IsDefined;
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
import org.meta_environment.rascal.ast.Expression.ValueProducerWithStrategy;
import org.meta_environment.rascal.ast.Expression.Visit;
import org.meta_environment.rascal.ast.Expression.VoidClosure;
import org.meta_environment.rascal.ast.FunctionDeclaration.Abstract;
import org.meta_environment.rascal.ast.Header.Parameters;
import org.meta_environment.rascal.ast.IntegerLiteral.DecimalIntegerLiteral;
import org.meta_environment.rascal.ast.Literal.Boolean;
import org.meta_environment.rascal.ast.Literal.Integer;
import org.meta_environment.rascal.ast.Literal.Real;
import org.meta_environment.rascal.ast.LocalVariableDeclaration.Default;
import org.meta_environment.rascal.ast.Rule.Arbitrary;
import org.meta_environment.rascal.ast.Rule.Guarded;
import org.meta_environment.rascal.ast.Rule.Replacing;
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
import org.meta_environment.rascal.interpreter.LazySet.LazyInsert;
import org.meta_environment.rascal.interpreter.LazySet.LazyUnion;
import org.meta_environment.rascal.interpreter.asserts.Ambiguous;
import org.meta_environment.rascal.interpreter.asserts.ImplementationError;
import org.meta_environment.rascal.interpreter.asserts.NotYetImplemented;
import org.meta_environment.rascal.interpreter.control_exceptions.Failure;
import org.meta_environment.rascal.interpreter.control_exceptions.Return;
import org.meta_environment.rascal.interpreter.env.Environment;
import org.meta_environment.rascal.interpreter.env.GlobalEnvironment;
import org.meta_environment.rascal.interpreter.env.JavaFunction;
import org.meta_environment.rascal.interpreter.env.Lambda;
import org.meta_environment.rascal.interpreter.env.ModuleEnvironment;
import org.meta_environment.rascal.interpreter.env.RascalFunction;
import org.meta_environment.rascal.interpreter.load.FromResourceLoader;
import org.meta_environment.rascal.interpreter.load.IModuleLoader;
import org.meta_environment.rascal.interpreter.result.Result;
import org.meta_environment.rascal.interpreter.staticErrors.ArityError;
import org.meta_environment.rascal.interpreter.staticErrors.MissingModifierError;
import org.meta_environment.rascal.interpreter.staticErrors.ModuleNameMismatchError;
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


public class Evaluator extends NullASTVisitor<Result> {
	final IValueFactory vf;
	final TypeFactory tf = TypeFactory.getInstance();
	private final TypeEvaluator te = TypeEvaluator.getInstance();
	private final java.util.ArrayDeque<Environment> callStack;
	private final GlobalEnvironment heap;
	private final java.util.ArrayDeque<ModuleEnvironment> scopeStack;
	
	private final JavaBridge javaBridge;
	private final boolean LAZY = false;
	
	enum DIRECTION  {BottomUp, TopDown};	// Parameters for traversing trees
	enum FIXEDPOINT {Yes, No};
	enum PROGRESS   {Continuing, Breaking};
	
	
	private Statement currentStatement; // used in runtime errormessages
	private Profiler profiler;
	private boolean doProfiling = false;
	
	// TODO: can we remove this?
	protected MatchPattern lastPattern;	// The most recent pattern applied in a match
	                                    	// For the benefit of string matching.
	private TypeDeclarationEvaluator typeDeclarator = new TypeDeclarationEvaluator();
	private java.util.List<IModuleLoader> loaders;
	private java.util.List<ClassLoader> classLoaders;

	public Evaluator(IValueFactory f, ASTFactory astFactory, Writer errorWriter, ModuleEnvironment scope) {
		this(f, astFactory, errorWriter, scope, new GlobalEnvironment());
	}

	public Evaluator(IValueFactory f, ASTFactory astFactory, Writer errorWriter, ModuleEnvironment scope, GlobalEnvironment heap) {
		this.vf = f;
		this.heap = heap;
		this.callStack = new ArrayDeque<Environment>();
		this.callStack.push(scope);
		this.scopeStack = new ArrayDeque<ModuleEnvironment>();
		this.scopeStack.push(scope);
		this.loaders = new LinkedList<IModuleLoader>();
		this.classLoaders = new LinkedList<ClassLoader>();
		this.javaBridge = new JavaBridge(errorWriter, classLoaders);
		
		// library
	    loaders.add(new FromResourceLoader(this.getClass(), "StandardLibrary"));
	    
	    // everything rooted at the src directory 
	    loaders.add(new FromResourceLoader(this.getClass()));
	    
	    // load Java classes from the current jar (for the standard library)
	    classLoaders.add(getClass().getClassLoader());
	    
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
	
	public void setCurrentStatement(Statement currentStatement) {
		this.currentStatement = currentStatement;
	}


	public Statement getCurrentStatement() {
		return currentStatement;
	}


	private Environment peek() {
		return this.callStack.peek();
	}
	
	public void addModuleLoader(IModuleLoader loader) {
		loaders.add(loader);
	}
	
	public void addClassLoader(ClassLoader loader) {
		classLoaders.add(loader);
	}
	
	/**
	 * Evaluate a statement
	 * @param stat
	 * @return
	 */
	public IValue eval(Statement stat) {
		try {
			 if(doProfiling){
			    	profiler = new Profiler(this);
			    	profiler.start();
			    	
			    }
			Result r = stat.accept(this);
	        if(r != null){
	        	if(doProfiling){
	        		profiler.pleaseStop();
	        		profiler.report();
	        	}
	        	return r.getValue();
	        } else {
	        	throw new ImplementationError("Not yet implemented: " + stat.toString());
	        }
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
	 * Evaluate a declaration
	 * @param declaration
	 * @return
	 */
	public IValue eval(Declaration declaration) {
		Result r = declaration.accept(this);
        if(r != null){
        	return r.getValue();
        } else {
        	throw new NotYetImplemented(declaration.toString());
        }
	}
	
	/**
	 * Evaluate an import
	 * @param imp
	 * @return
	 */
	public IValue eval(org.meta_environment.rascal.ast.Import imp) {
		Result r = imp.accept(this);
        if(r != null){
        	return r.getValue();
        } else {
        	throw new ImplementationError("Not yet implemented: " + imp.getTree());
        }
	}

	/* First a number of general utility methods */
	
	/*
	 * Return an evaluation result that is already in normal form,
	 * i.e., all potential rules have already been applied to it.
	 */
	
	Result normalizedResult(Type t, IValue v){
		Map<Type, Type> bindings = peek().getTypeBindings();
		Type instance;
		
		if (bindings.size() > 0) {
		    instance = t.instantiate(peek().getStore(), bindings);
		}
		else {
			instance = t;
		}
		
		if (v != null) {
			checkType(v.getType(), instance);
		}
		return new Result(instance, v);
	}
	
	/*
	 * Return an evaluation result that may need normalization.
	 */
	
	Result result(Type t, IValue v) {
		Map<Type, Type> bindings = peek().getTypeBindings();
		Type instance;
		
		if (bindings.size() > 0) {
		    instance = t.instantiate(peek().getStore(), bindings);
		}
		else {
			instance = t;
		}
		
		if (v != null) {
			checkType(v.getType(), instance);
			// rewrite rules do not change the declared type
			return new Result(instance, applyRules(v));
		}
		return new Result(instance, v);
	}

	// TODO: package visibility?
	Result result(IValue v) {
		return new Result(v.getType(), applyRules(v));
	}
	
	private IValue applyRules(IValue v) {
		
		//System.err.println("applyRules(" + v + ")");
		// we search using the run-time type of a value
		Type typeToSearchFor = v.getType();
		if (typeToSearchFor.isAbstractDataType()) {
			typeToSearchFor = ((IConstructor) v).getConstructorType();
		}
		
		java.util.List<org.meta_environment.rascal.ast.Rule> rules = heap.getRules(typeToSearchFor);
		if(rules.isEmpty()){
			return v;
		}
		
		TraverseResult tr = traverse(v, new CasesOrRules(rules), 
				DIRECTION.BottomUp,
				PROGRESS.Continuing,
				FIXEDPOINT.No);
				/* innermost is achieved by repeated applications of applyRules
				 * when intermediate results are produced.
				 */
		return tr.value;
	}


	private void pop() {
		callStack.pop();
	}


	private void push() {
		callStack.push(new Environment(peek()));
	}
	
	private Result result() {
		return new Result(null, null);
	}
	
	private void checkType(Type given, Type expected) {
		if (expected == org.meta_environment.rascal.interpreter.env.Lambda.getClosureType()) {
			return;
		}
		if (!given.isSubtypeOf(expected)){
			throw new UnexpectedTypeError(expected, given, getCurrentStatement());
		}
	}
	
	// Ambiguity ...................................................
	
	@Override
	public Result visitExpressionAmbiguity(Ambiguity x) {
		throw new Ambiguous(x.toString());
	}
	
	@Override
	public Result visitStatementAmbiguity(
			org.meta_environment.rascal.ast.Statement.Ambiguity x) {
		throw new Ambiguous(x.toString());
	}
	
	// Modules -------------------------------------------------------------
	
	@Override
	public Result visitImportDefault(
			org.meta_environment.rascal.ast.Import.Default x) {
		// TODO support for full complexity of import declarations
		String name = x.getModule().getName().toString();
		if (name.startsWith("\\")) {
			name = name.substring(1);
		}
		if (!heap.existsModule(name)) {
			evalModule(x, name);
		}
		else {
			if (scopeStack.size() == 1 && callStack.size() == 1) {
				reloadAll(x);
			}
		}
		
		scopeStack.peek().addImport(name, heap.getModule(name, x));
		return result();
	}

	private void evalModule(AbstractAST x,
			String name) {
		Module module = loadModule(name);
		if (!getModuleName(module).equals(name)) {
			throw new ModuleNameMismatchError(getModuleName(module), name, x);
		}
		module.accept(this);
	}


	private void reloadAll(AbstractAST cause) {
		heap.clear();
		
		java.util.Set<String> topModules = scopeStack.getFirst().getImports();
		for (String mod : topModules) {
			evalModule(cause, mod);
			scopeStack.peek().addImport(mod, heap.getModule(mod, cause));
		}
	}
	

	private Module loadModule(String name) {
		for (IModuleLoader loader : loaders) {
			try {
				return loader.loadModule(name);
			}
			catch (IOException e) {
				// this happens regularly
			}
		}
		
		throw RuntimeExceptionFactory.moduleNotFound(vf.string(name));
	}
	
	@Override 
	public Result visitModuleDefault(
			org.meta_environment.rascal.ast.Module.Default x) {
		String name = getModuleName(x);

		if (!heap.existsModule(name)) {
			ModuleEnvironment env = new ModuleEnvironment(name);
			scopeStack.push(env);
			callStack.push(env); // such that declarations end up in the module scope
			
			try {
				x.getHeader().accept(this);

				java.util.List<Toplevel> decls = x.getBody().getToplevels();
				typeDeclarator.evaluateDeclarations(decls, peek());
				
				for (Toplevel l : decls) {
					l.accept(this);
				}
				
				// only after everything was successful add the module
				heap.addModule(env);
			}
			finally {
				scopeStack.pop();
				callStack.pop();
			}
		}
		
		return result();
	}

	private String getModuleName(
			Module module) {
		String name = module.getHeader().getName().toString();
		if (name.startsWith("\\")) {
			name = name.substring(1);
		}
		return name;
	}

	@Override
	public Result visitHeaderDefault(
			org.meta_environment.rascal.ast.Header.Default x) {
		visitImports(x.getImports());
		return result();
	}
	
	@Override
	public Result visitDeclarationAlias(Alias x) {
		typeDeclarator.declareAlias(x, peek());
		return result();
	}
	
	@Override
	public Result visitDeclarationData(Data x) {
		typeDeclarator.declareConstructor(x, peek());
		return result();
	}
	
	private void visitImports(java.util.List<Import> imports) {
		for (Import i : imports) {
			i.accept(this);
		}
	}
	
	@Override
	public Result visitHeaderParameters(Parameters x) {
		visitImports(x.getImports());
		return result();
	}
	
	@Override
	public Result visitToplevelDefaultVisibility(DefaultVisibility x) {
		Result r = x.getDeclaration().accept(this);
		r.setPublic(false);
		return r;
	}

	@Override
	public Result visitToplevelGivenVisibility(GivenVisibility x) {
		Result r = x.getDeclaration().accept(this);
		r.setPublic(x.getVisibility().isPublic());
		return r;
	}
	
	@Override
	public Result visitDeclarationFunction(Function x) {
		return x.getFunctionDeclaration().accept(this);
	}
	
	@Override
	public Result visitDeclarationVariable(Variable x) {
		Type declaredType = te.eval(x.getType(), scopeStack.peek());
		Result r = result();

		for (org.meta_environment.rascal.ast.Variable var : x.getVariables()) {
			if (var.isUnInitialized()) {  
				throw new UninitializedVariableError(var.toString(), var);
			} else {
				Result v = var.getInitial().accept(this);
				if(v.getType().isSubtypeOf(declaredType)){
					// TODO: do we actually want to instantiate the locally bound type parameters?
					Map<Type,Type> bindings = new HashMap<Type,Type>();
					declaredType.match(v.getType(), bindings);
					declaredType = declaredType.instantiate(peek().getStore(), bindings);
					r = normalizedResult(declaredType, v.getValue());
					scopeStack.peek().storeVariable(var.getName(), r);
				} else {
					throw new UnexpectedTypeError(declaredType, v.getType(), var);
				}
			}
		}
		
		return r;
	}

	@Override
	public Result visitDeclarationAnnotation(Annotation x) {
		Type annoType = te.eval(x.getAnnoType(), scopeStack.peek());
		String name = x.getName().toString();

		Type onType = te.eval(x.getOnType(), scopeStack.peek());
		scopeStack.peek().declareAnnotation(onType, name, annoType);	

		return result();
	}
	
	
	private Type evalType(org.meta_environment.rascal.ast.Type type) {
		return te.eval(type, peek());
	}
	
	@Override
	public Result visitDeclarationView(View x) {
		// TODO implement
		throw new NotYetImplemented("Views");
	}
	
	@Override
	public Result visitDeclarationRule(Rule x) {
		return x.getRule().accept(this);
	}
	
	@Override
	public Result visitRuleArbitrary(Arbitrary x) {
		MatchPattern pv = x.getPattern().accept(makePatternEvaluator());
		heap.storeRule(pv.getType(scopeStack.peek()), x);
		return result();
	}


	private AbstractPatternEvaluator makePatternEvaluator() {
		return new AbstractPatternEvaluator(vf, peek(), peek(), this);
	}
	
	@Override
	public Result visitRuleReplacing(Replacing x) {
		MatchPattern pv = x.getPattern().accept(makePatternEvaluator());
		//System.err.println("visitRule: " + pv.getType(this));
		heap.storeRule(pv.getType(scopeStack.peek()), x);
		return result();
	}
	
	@Override
	public Result visitRuleGuarded(Guarded x) {
		//TODO adapt to new scheme
		Result result = x.getRule().getPattern().getPattern().accept(this);
		Type expected = evalType(x.getType());
		Type got = result.getType();
		if (!got.isSubtypeOf(expected)) {
			throw new UnexpectedTypeError(expected, got, x);
		}
		return x.getRule().accept(this);
	}
	
	@Override
	public Result visitDeclarationTag(Tag x) {
		throw new NotYetImplemented("tags");
	}
	
	// Variable Declarations -----------------------------------------------

	@Override
	public Result visitLocalVariableDeclarationDefault(Default x) {
		// TODO deal with dynamic variables
		return x.getDeclarator().accept(this);
	}

	@Override
	public Result visitDeclaratorDefault(
			org.meta_environment.rascal.ast.Declarator.Default x) {
		Type declaredType = evalType(x.getType());
		Result r = result();

		for (org.meta_environment.rascal.ast.Variable var : x.getVariables()) {
			if (var.isUnInitialized()) {  // variable declaration without initialization
				r = result(declaredType, null);
				peek().storeVariable(var.getName(), r);
			} else {                     // variable declaration with initialization
				Result v = var.getInitial().accept(this);
				if(v.getType().isSubtypeOf(declaredType)){
					// TODO: do we actually want to instantiate the locally bound type parameters?
					Map<Type,Type> bindings = new HashMap<Type,Type>();
					declaredType.match(v.getType(), bindings);
					declaredType = declaredType.instantiate(peek().getStore(), bindings);
					r = result(declaredType, v.getValue());
					peek().storeVariable(var.getName(), r);
				} else {
					throw new UnexpectedTypeError(declaredType, v.getType(), var);
				}
			}
		}
		return r;
	}
	
	// Function calls and node constructors
	
	@Override
	public Result visitClosureAsFunctionEvaluated(Evaluated x) {
		Expression expr = x.getExpression();
		
		if (expr.isQualifiedName()) {
			
		}
		
		return result(vf.string(Names.name(Names.lastName(expr.getQualifiedName()))));
	}
	
	@Override
	public Result visitExpressionClosureCall(ClosureCall x) {
		Result func = x.getClosure().getExpression().accept(this);
		java.util.List<org.meta_environment.rascal.ast.Expression> args = x.getArguments();

		IValue[] actuals = new IValue[args.size()];
		Type[] types = new Type[args.size()];

		for (int i = 0; i < args.size(); i++) {
			Result resultElem = args.get(i).accept(this);
			types[i] = resultElem.getType();
			actuals[i] = resultElem.getValue();
		}

		Type actualTypes = tf.tupleType(types);

		if (func.getType() == Lambda.getClosureType()) {
			Lambda lambda = (Lambda) func.getValue();
			try {
				pushCallFrame(lambda.getEnv()); 
				return lambda.call(actuals, actualTypes, peek());
			}
			finally {
				pop();
			}
		}
		else {
			throw new ImplementationError("Lambda's should have the closure type");
		}
	}
	
	@Override
	public Result visitExpressionCallOrTree(CallOrTree x) {
		 java.util.List<org.meta_environment.rascal.ast.Expression> args = x.getArguments();
		 QualifiedName name = x.getQualifiedName();
		 
		 IValue[] actuals = new IValue[args.size()];
		 Type[] types = new Type[args.size()];

		 for (int i = 0; i < args.size(); i++) {
			 Result resultElem = args.get(i).accept(this);
			 types[i] = resultElem.getType();
			 actuals[i] = resultElem.getValue();
		 }
		 
		 Type signature = tf.tupleType(types);
		 
		 if (isTreeConstructorName(name, signature)) {
			 return constructTree(name, actuals, signature);
		 }
		 else {
			 return call(name, actuals, signature);
		 }
	}
	
	private Result call(QualifiedName name, IValue[] actuals, Type actualTypes) {
		String moduleName = Names.moduleName(name);
		Environment env;
		
		if (moduleName == null) {
			env = peek();
		}
		else {
			env = peek().getImport(moduleName);
			if (env == null) {
				throw new UndeclaredModuleError(moduleName, name);
			}
		}
		Lambda func = env.getFunction(Names.name(Names.lastName(name)), actualTypes, name);
		
		if (func != null) {
			try {
				pushCallFrame(func.getEnv());
				return func.call(actuals, actualTypes, peek());
			}
			finally {
				pop();
			}
		}
		undefinedFunctionException(name, actualTypes);
		return null;
	}


	private void pushCallFrame(Environment env) {
		callStack.push(new Environment(env));
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
		return peek().isTreeConstructorName(name, signature);
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
	 */
	private Result constructTree(QualifiedName functionName, IValue[] actuals, Type signature) {
		String sort;
		String cons;
		
		cons = Names.consName(functionName);
		sort = Names.sortName(functionName);

		Type candidate = null;
	
		if (sort != null) {
			Type sortType = peek().getAbstractDataType(sort);
			
			if (sortType != null) {
			  candidate = peek().getConstructor(sortType, cons, signature);
			}
			else {
			  return result(tf.nodeType(), vf.node(cons, actuals));
			}
		}
		
		candidate = peek().getConstructor(cons, signature);
		if (candidate != null) {
			Map<Type,Type> localBindings = new HashMap<Type,Type>();
			candidate.getFieldTypes().match(tf.tupleType(actuals), localBindings);
			
			return result(candidate.getAbstractDataType().instantiate(new TypeStore(), localBindings), candidate.make(vf, actuals));
		}
		
		return result(tf.nodeType(), vf.node(cons, actuals));
	}
	
	@Override
	public Result visitExpressionFunctionAsValue(FunctionAsValue x) {
		return x.getFunction().accept(this);
	}
	
	@Override
	public Result visitFunctionAsValueDefault(
			org.meta_environment.rascal.ast.FunctionAsValue.Default x) {
		Name name = x.getName();
		
		//TODO is this a bug, what if name was overloaded?
		//TODO add support for typed function names
		Lambda func = peek().getFunction(Names.name(name), tf.voidType(), x);
		
		if (func == null) {
			throw new UndeclaredFunctionError(Names.name(name), x);
		}
		
		return func;
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
	public Result visitFunctionBodyDefault(
			org.meta_environment.rascal.ast.FunctionBody.Default x) {
		Result result = result();
		
		for (Statement statement : x.getStatements()) {
			setCurrentStatement(statement);
			result = statement.accept(this);
		}
		
		return result;
	}
	
   // Statements ---------------------------------------------------------
	
	@Override
	public Result visitStatementAssert(Assert x) {
		Result r = x.getExpression().accept(this);
		if (!r.getType().equals(tf.boolType())) {
			throw new UnexpectedTypeError(tf.boolType(), r.getType(), x);	
		}
		
		if(r.getValue().isEqual(vf.bool(false))) {
			throw RuntimeExceptionFactory.assertionFailed();
		}
		return r;	
	}
	
	@Override
	public Result visitStatementAssertWithMessage(AssertWithMessage x) {
		Result r = x.getExpression().accept(this);
		if (!r.getType().equals(tf.boolType())) {
			throw new UnexpectedTypeError(tf.boolType(),r.getType(), x);	
		}
		if(r.getValue().isEqual(vf.bool(false))){
			String str = x.getMessage().toString();
			IString msg = vf.string(unescape(str, x));
			throw RuntimeExceptionFactory.assertionFailed(msg);
		}
		return r;	
	}
	
	@Override
	public Result visitStatementVariableDeclaration(VariableDeclaration x) {
		return x.getDeclaration().accept(this);
	}
	
	@Override
	public Result visitStatementExpression(Statement.Expression x) {
		setCurrentStatement(x);
		return x.getExpression().accept(this);
	}
	
	@Override
	public Result visitStatementFunctionDeclaration(
			org.meta_environment.rascal.ast.Statement.FunctionDeclaration x) {
		return x.getFunctionDeclaration().accept(this);
	}
	
	// TODO?? package visibility??
	Result assignVariable(QualifiedName name, Result right){
		Environment env = getEnv(name);
		Result previous = env.getVariable(name);
		
		if (previous != null) {
			if (right.getType().isSubtypeOf(previous.getType())) {
				right.setType(previous.getType());
			} else {
				throw new UnexpectedTypeError(previous.getType(), right.getType(), null);
			}
		}
		
		env.storeVariable(name, right);
		return right;
	}


	private Environment getEnv(QualifiedName name) {
		String moduleName = Names.moduleName(name);
		Environment env;
		
		if (moduleName == null) {
			env = peek();
		}
		else {
			env = peek().getImport(moduleName);
		}
		return env;
	}
	
	@Override
	public Result visitExpressionSubscript(Subscript x) {
		
		Result expr = x.getExpression().accept(this);
		Type exprType = expr.getType();
		int nSubs = x.getSubscripts().size();
		
		if (exprType.isRelationType()) {
			int relArity = exprType.getArity();
			
			if(nSubs >= relArity){
				throw new ArityError(exprType, nSubs, x);
			}
			Result subscriptResult[] = new Result[nSubs];
			Type subscriptType[] = new Type[nSubs];
			boolean subscriptIsSet[] = new boolean[nSubs];
			
			for(int i = 0; i < nSubs; i++){
				subscriptResult[i] = x.getSubscripts().get(i).accept(this);
				subscriptType[i] = subscriptResult[i].getType();
			}
			
			boolean yieldSet = (relArity - nSubs) == 1;
			Type resFieldType[] = new Type[relArity - nSubs];
			for (int i = 0; i < relArity; i++) {
				Type relFieldType = exprType.getFieldType(i);
				if(i < nSubs) {
					if (subscriptType[i].isSetType() && 
					    subscriptType[i].getElementType().isSubtypeOf(relFieldType)){
						subscriptIsSet[i] = true;
					} else if (subscriptType[i].isSubtypeOf(relFieldType)){
						subscriptIsSet[i] = false;
					} else {
						throw new UnexpectedTypeError(relFieldType, subscriptType[i], x);
					}
				} else {
					resFieldType[i - nSubs] = relFieldType;
				}
			}
			Type resultType;
			ISetWriter wset = null;
			IRelationWriter wrel = null;
			
			if(yieldSet){
				resultType = tf.setType(resFieldType[0]);
				wset = resultType.writer(vf);
			} else {
				resultType = tf.relType(resFieldType);
				wrel = resultType.writer(vf);
			}

			for (IValue v : ((IRelation) expr.getValue())) {
				ITuple tup = (ITuple) v;
				boolean allEqual = true;
				for(int k = 0; k < nSubs; k++){
					if(subscriptIsSet[k] && ((ISet) subscriptResult[k].getValue()).contains(tup.get(k))){
						/* ok */
					} else if (tup.get(k).isEqual(subscriptResult[k].getValue())){
						/* ok */
					} else {
						allEqual = false;
					}
				}
				
				if (allEqual) {
					IValue args[] = new IValue[relArity - nSubs];
					for (int i = nSubs; i < relArity; i++) {
						args[i - nSubs] = tup.get(i);
					}
					if(yieldSet){
						wset.insert(args[0]);
					} else {
						wrel.insert(vf.tuple(args));
					}
				}
			}
			return normalizedResult(resultType, yieldSet ? wset.done() : wrel.done());
		}
		
		if (nSubs > 1){
			throw new SyntaxError("No more than one subscript allowed on " + exprType, x.getLocation());
		}
		
		Result subs = x.getSubscripts().get(0).accept(this);
		Type subsBase = subs.getType();
		
		if (exprType.isMapType()
			&& subsBase.isSubtypeOf(exprType.getKeyType())) {
			Type valueType = exprType.getValueType();
			IValue v = ((IMap) expr.getValue()).get(subs.getValue());
			if(v == null){
				throw RuntimeExceptionFactory.noSuchKey(subs.getValue());
			}
			return normalizedResult(valueType,v);
		}
		
		if(!subsBase.isIntegerType()){
			throw new UnexpectedTypeError(tf.integerType(), subsBase, x);
		}
		int index = ((IInteger) subs.getValue()).intValue();

		if (exprType.isListType()) {
			Type elementType = exprType.getElementType();
			try {
				IValue element = ((IList) expr.getValue()).get(index);
				return normalizedResult(elementType, element);
			} catch (java.lang.IndexOutOfBoundsException e) {
				throw RuntimeExceptionFactory.indexOutOfBounds((IInteger) subs.getValue());
			}
		}
		if (exprType.isAbstractDataType()) {
			if(index >= ((IConstructor) expr.getValue()).arity()){
				throw RuntimeExceptionFactory.indexOutOfBounds((IInteger) subs.getValue());
			}
			
			Type elementType = ((IConstructor) expr.getValue()).getConstructorType().getFieldType(index);
			IValue element = ((IConstructor) expr.getValue()).get(index);
			return normalizedResult(elementType, element);
		}
		if (exprType.isNodeType()) {
			if(index >= ((INode) expr.getValue()).arity()){
				throw RuntimeExceptionFactory.indexOutOfBounds((IInteger) subs.getValue());
			}
			Type elementType = tf.valueType();
			IValue element = ((INode) expr.getValue()).get(index);
			return normalizedResult(elementType, element);
		}
		if (exprType.isTupleType()) {
			try {
				Type elementType = exprType.getFieldType(index);
				IValue element = ((ITuple) expr.getValue()).get(index);
				return normalizedResult(elementType, element);
			} catch (ArrayIndexOutOfBoundsException e){
				throw RuntimeExceptionFactory.indexOutOfBounds((IInteger) subs.getValue());
			}
		}
		
		throw new UnsupportedOperationError("subscript", exprType, x);
	}

	@Override
	public Result visitExpressionFieldAccess(
			org.meta_environment.rascal.ast.Expression.FieldAccess x) {
		Result expr = x.getExpression().accept(this);
		String field = x.getField().toString();
		
		if (expr.getType().isTupleType()) {
			Type tuple = expr.getType();
			if (!tuple.hasFieldNames()) {
				throw new UndeclaredFieldError(field, tuple, x);
			}
			 try {
				 return normalizedResult(tuple.getFieldType(field), ((ITuple) expr.getValue()).get(tuple.getFieldIndex(field)));
			 } catch (UndeclaredFieldException e){
				 throw new UndeclaredFieldError(field, tuple, x.getField());
			 }
			 }
		else if (expr.getType().isRelationType()) {
			Type tuple = expr.getType().getFieldTypes();
			
			try {
				ISetWriter w = vf.setWriter(tuple.getFieldType(field));
				for (IValue e : (ISet) expr.getValue()) {
					w.insert(((ITuple) e).get(tuple.getFieldIndex(field)));
				}
				return result(tf.setType(tuple.getFieldType(field)), w.done());
			}
			catch (UndeclaredFieldException e) {
				throw new UndeclaredFieldError(field, tuple, x);
			}
		}
		else if (expr.getType().isAbstractDataType() || expr.getType().isConstructorType()) {
			Type node = ((IConstructor) expr.getValue()).getConstructorType();
			
			if (!expr.getType().hasField(field, callStack.peek().getStore())) {
				throw new UndeclaredFieldError(field, expr.getType(), x);
			}
			
			if (!node.hasField(field)) {
				throw new UndeclaredFieldError(field, expr.getType(), x);
			}
			
			int index = node.getFieldIndex(field);
			return normalizedResult(node.getFieldType(index),((IConstructor) expr.getValue()).get(index));
		}
		else if(expr.getType().isSourceLocationType()){
			ISourceLocation loc = (ISourceLocation) expr.getValue();
			if(field.equals("length")){
				return result(tf.integerType(), vf.integer(loc.getLength()));
			} else if(field.equals("offset")){
				return result(tf.integerType(), vf.integer(loc.getOffset()));
			} else if(field.equals("beginLine")){
				return result(tf.integerType(), vf.integer(loc.getBeginLine()));
			} else if(field.equals("beginColumn")){
				return result(tf.integerType(), vf.integer(loc.getBeginColumn()));
			} else if(field.equals("endLine")){
				return result(tf.integerType(), vf.integer(loc.getEndLine()));
			} else if(field.equals("endColumn")){
				return result(tf.integerType(), vf.integer(loc.getEndColumn()));
			} else if(field.equals("url")){
				return result(tf.stringType(), vf.string(loc.getURL().toString()));
			} else {
				throw new UndeclaredFieldError(field, tf.sourceLocationType(), x);
			}
		}
		
		throw new UndeclaredFieldError(field, expr.getType(), x);
	}
	
	private boolean duplicateIndices(int indices[]){
		for(int i = 0; i < indices.length; i ++){
			for(int j = 0; j < indices.length; j++){
				if(i != j && indices[i] == indices[j]){
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public Result visitExpressionFieldProject(FieldProject x) {
		Result  base = x.getExpression().accept(this);
		
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
					throw RuntimeExceptionFactory.indexOutOfBounds(vf.integer(i));
				}
				fieldTypes[i] = base.getType().getFieldType(selectedFields[i]);
			}
			
			if(duplicateIndices(selectedFields)){
				// TODO: what does it matter if there are duplicate indices???
				throw new ImplementationError("Duplicate fields in projection");
			}
			Type resultType = nFields == 1 ? fieldTypes[0] : tf.tupleType(fieldTypes);
			
			return result(resultType, ((ITuple)base.getValue()).select(selectedFields));				     
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
					throw RuntimeExceptionFactory.indexOutOfBounds(vf.integer(i));
				}
				fieldTypes[i] = base.getType().getFieldType(selectedFields[i]);
			}
			if(duplicateIndices(selectedFields)){
				// TODO: what does it matter if there are duplicate indices? Duplicate
				// field names may be a problem, but not this.
				throw new ImplementationError("Duplicate fields in projection");
			}
			Type resultType = nFields == 1 ? tf.setType(fieldTypes[0]) : tf.relType(fieldTypes);
			
			return result(resultType, ((IRelation)base.getValue()).select(selectedFields));				     
		}
		
		throw new UnsupportedOperationError("projection", base.getType(), x);
	}
	
	@Override
	public Result visitStatementEmptyStatement(EmptyStatement x) {
		return result();
	}
	
	@Override
	public Result visitStatementFail(Fail x) {
		if (x.getFail().isWithLabel()) {
			throw new Failure(x.getFail().getLabel().toString());
		}
		else {
		  throw new Failure();
		}
	}
	
	@Override
	public Result visitStatementReturn(
			org.meta_environment.rascal.ast.Statement.Return x) {
		org.meta_environment.rascal.ast.Return r = x.getRet();
		
		if (r.isWithExpression()) {
		  throw new Return(x.getRet().getExpression().accept(this));
		}
		else {
			throw new Return(result(tf.voidType(), null));
		}
	}
	
	@Override
	public Result visitStatementBreak(Break x) {
		throw new NotYetImplemented(x.toString()); // TODO
	}
	
	@Override
	public Result visitStatementContinue(Continue x) {
		throw new NotYetImplemented(x.toString()); // TODO
	}
	
	@Override
	public Result visitStatementGlobalDirective(GlobalDirective x) {
		throw new NotYetImplemented(x.toString()); // TODO
	}
	
	@Override
	public Result visitStatementThrow(Throw x) {
		throw new org.meta_environment.rascal.interpreter.control_exceptions.Throw(x.getExpression().accept(this).getValue());
	}
	
	@Override
	public Result visitStatementTry(Try x) {
		return evalStatementTry(x.getBody(), x.getHandlers(), null);
	}
	
	@Override
	public Result visitStatementTryFinally(TryFinally x) {
		return evalStatementTry(x.getBody(), x.getHandlers(), x.getFinallyBody());
	}
	
	private Result evalStatementTry(Statement body, java.util.List<Catch> handlers, Statement finallyBody){
		Result res = result();
		
		try {
			res = body.accept(this);
		} catch (org.meta_environment.rascal.interpreter.control_exceptions.Throw e){
			IValue eValue = e.getException();

			for (Catch c : handlers){
				if(c.isDefault()){
					res = c.getBody().accept(this);
					break;
				} 
				
				try {
					push();	
					if(matchAndEval(eValue, c.getPattern(), c.getBody())){
						break;
					}
				} 
				finally {
					pop();
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
	public Result visitStatementVisit(
			org.meta_environment.rascal.ast.Statement.Visit x) {
		return x.getVisit().accept(this);
	}
	
	@Override
	public Result visitStatementInsert(Insert x) {
		throw new org.meta_environment.rascal.interpreter.control_exceptions.Insert(x.getExpression().accept(this));
	}
	
	@Override
	public Result visitStatementAssignment(Assignment x) {
		Result right = x.getExpression().accept(this);
		return x.getAssignable().accept(new AssignableEvaluator(peek(), x.getOperator(), right, this));
	}
	
	@Override
	public Result visitStatementBlock(Block x) {
		Result r = result();
		try {
			push(); 
			for (Statement stat : x.getStatements()) {
				setCurrentStatement(stat);
				r = stat.accept(this);
			}
		}
		finally {
			pop();
		}
		return r;
	}
  
	@Override
	public Result visitAssignableVariable(
			org.meta_environment.rascal.ast.Assignable.Variable x) {
		return peek().getVariable(x.getQualifiedName(),x.getQualifiedName().toString());
	}
	
	@Override
	public Result visitAssignableFieldAccess(FieldAccess x) {
		Result receiver = x.getReceiver().accept(this);
		String label = x.getField().toString();
	
		if (receiver.getType().isTupleType()) {
			IValue result = ((ITuple) receiver.getValue()).get(label);
			Type type = ((ITuple) receiver.getValue()).getType().getFieldType(label);
			return normalizedResult(type, result);
		}
		else if (receiver.getType().isConstructorType() || receiver.getType().isAbstractDataType()) {
			IConstructor cons = (IConstructor) receiver.getValue();
			Type node = cons.getConstructorType();
			
			if (!receiver.getType().hasField(label)) {
				throw new UndeclaredFieldError(label, receiver.getType(), x);
			}
			
			if (!node.hasField(label)) {
				throw new UndeclaredFieldError(label, receiver.getValueType(), x);
			}
			
			int index = node.getFieldIndex(label);
			return normalizedResult(node.getFieldType(index), cons.get(index));
		}
		else {
			throw new UndeclaredFieldError(label, receiver.getType(), x);
		}
	}
	
	@Override
	public Result visitAssignableAnnotation(
			org.meta_environment.rascal.ast.Assignable.Annotation x) {
		Result receiver = x.getReceiver().accept(this);
		String label = x.getAnnotation().toString();
		
		if (!callStack.peek().declaresAnnotation(receiver.getType(), label)) {
			throw new UndeclaredAnnotationError(label, receiver.getType(), x);
		}
		
		Type type = callStack.peek().getAnnotationType(receiver.getType(), label);
		IValue value = ((IConstructor) receiver.getValue()).getAnnotation(label);
		
		return normalizedResult(type, value);
	}
	
	@Override
	public Result visitAssignableConstructor(Constructor x) {
		throw new ImplementationError("Constructor assignable does not represent a value");
	}
	
	@Override
	public Result visitAssignableIfDefinedOrDefault(
			org.meta_environment.rascal.ast.Assignable.IfDefinedOrDefault x) {
		throw new ImplementationError("ifdefined assignable does not represent a value");
	}
	
	@Override
	public Result visitAssignableSubscript(
			org.meta_environment.rascal.ast.Assignable.Subscript x) {
		Result receiver = x.getReceiver().accept(this);
		Result subscript = x.getSubscript().accept(this);
		
		if (receiver.getType().isListType()) {
			if (subscript.getType().isIntegerType()) {
				IList list = (IList) receiver.getValue();
				IValue result = list.get(((IInteger) subscript.getValue()).intValue());
				Type type = receiver.getType().getElementType();
				return normalizedResult(type, result);
			}
			
			throw new UnexpectedTypeError(tf.integerType(), subscript.getDeclaredType(), x);
		}
		else if (receiver.getType().isMapType()) {
			Type keyType = receiver.getType().getKeyType();
			
			if (subscript.getType().isSubtypeOf(keyType)) {
				IValue result = ((IMap) receiver.getValue()).get(subscript.getValue());
				Type type = receiver.getType().getValueType();
				return normalizedResult(type, result);
			}
			
			throw new UnexpectedTypeError(keyType, subscript.getDeclaredType(), x);
		}
		
		// TODO implement other subscripts
		throw new UnsupportedOperationError("subscript", receiver.getType(), x);
	}
	
	@Override
	public Result visitAssignableTuple(
			org.meta_environment.rascal.ast.Assignable.Tuple x) {
		throw new ImplementationError("Tuple in assignable does not represent a value:" + x);
	}
	
	@Override
	public Result visitAssignableAmbiguity(
			org.meta_environment.rascal.ast.Assignable.Ambiguity x) {
		throw new Ambiguous(x.toString());
	}
	
	@Override
	public Result visitFunctionDeclarationDefault(
			org.meta_environment.rascal.ast.FunctionDeclaration.Default x) {
		Lambda lambda;
		boolean varArgs = x.getSignature().getParameters().isVarArgs();
		
		if (hasJavaModifier(x)) {
			lambda = new JavaFunction(this, x, varArgs, peek(), javaBridge);
		}
		else {
			if (!x.getBody().isDefault()) {
				throw new MissingModifierError("java", x);
			}
			
			lambda = new RascalFunction(this, x, varArgs, peek());
		}
		
		String name = Names.name(x.getSignature().getName());
		peek().storeFunction(name, lambda);
		
		return lambda;
	}
	
	@Override
	public Result visitFunctionDeclarationAbstract(Abstract x) {
		Lambda lambda;
		boolean varArgs = x.getSignature().getParameters().isVarArgs();
		
		if (hasJavaModifier(x)) {
			lambda = new org.meta_environment.rascal.interpreter.env.JavaMethod(this, x, varArgs, peek(), javaBridge);
		}
		else {
			throw new MissingModifierError("java", x);
		}
		
		String name = Names.name(x.getSignature().getName());
		peek().storeFunction(name, lambda);
		
		return lambda;
	}
	
	@Override
	public Result visitStatementIfThenElse(IfThenElse x) {
		elseBranch: 
			do {
				push(); // For the benefit of variables bound in the condition
				try {
					for (org.meta_environment.rascal.ast.Expression expr : x.getConditions()) {
						Result cval = expr.accept(this);
						if (!cval.getType().isBoolType()) {
							throw new UnexpectedTypeError(tf.boolType(),
									cval.getType(), x);
						}
						if (cval.getValue().isEqual(vf.bool(false))) {
							break elseBranch;
						}
						// condition is true: continue
					}
					return x.getThenStatement().accept(this);
				} finally {
					pop();	// Remove any bindings due to condition evaluation.
				}
			} 
			while (false);
		return x.getElseStatement().accept(this);
	}

	
	
	@Override
	public Result visitStatementIfThen(IfThen x) {
		push(); // For the benefit of variables bound in the condition
		try {
			for (org.meta_environment.rascal.ast.Expression expr : x.getConditions()) {
				Result cval = expr.accept(this);
				if (!cval.getType().isBoolType()) {
					throw new UnexpectedTypeError(tf.boolType(),cval.getType(), x);
				}
				if (cval.getValue().isEqual(vf.bool(false))) {
					return result();
				}
			}
			return x.getThenStatement().accept(this);
		}
		finally {
			pop();
		}
	}
	
	@Override
	public Result visitStatementWhile(While x) {
		org.meta_environment.rascal.ast.Expression expr = x.getCondition();
		Result statVal = result();
		
		do {
			push();
			try {
				Result cval = expr.accept(this);
				if (!cval.getType().isBoolType()) {
					throw new UnexpectedTypeError(tf.boolType(),cval.getType(), x);
				}
				if (cval.getValue().isEqual(vf.bool(false))) {
					return statVal;
				}
				statVal = x.getBody().accept(this);
			}
			finally {
				pop();
			}
		} while (true);
	}
		
	@Override
	public Result visitStatementDoWhile(DoWhile x) {
		org.meta_environment.rascal.ast.Expression expr = x.getCondition();
		do {
			Result result = x.getBody().accept(this);

			push();
			try {
				Result cval = expr.accept(this);
				if (!cval.getType().isBoolType()) {
					throw new UnexpectedTypeError(tf.boolType(),cval.getType(), x);
				}
				if (cval.getValue().isEqual(vf.bool(false))) {
					return result;
				}
			}
			finally {
				pop();
			}
		} while (true);
	}
	
    @Override
    public Result visitExpressionMatch(Match x) {
    	return new MatchEvaluator(x.getPattern(), x.getExpression(), true, peek(), this).next();
    }
    
    @Override
    public Result visitExpressionNoMatch(NoMatch x) {
    	return new MatchEvaluator(x.getPattern(), x.getExpression(), false, peek(), this).next();
    }
	
	// ----- General method for matching --------------------------------------------------
    
    protected MatchPattern evalPattern(org.meta_environment.rascal.ast.Expression pat){
    	AbstractPatternEvaluator pe = makePatternEvaluator();
		if(pe.isPattern(pat)){
    		return pat.accept(pe);
    	} else {
			RegExpPatternEvaluator re = new RegExpPatternEvaluator(vf, peek());
			if(re.isRegExpPattern(pat)){ 
				return pat.accept(re);
			} else {
				// TODO how can this happen?
				throw new ImplementationError("Pattern expected instead of " + pat);
			}
		}
    }
	
    // TODO remove dead code
//	private boolean matchOne(IValue subj, org.meta_environment.rascal.ast.Expression pat){
//		//System.err.println("matchOne: subj=" + subj + ", pat= " + pat);
//		MatchPattern mp = evalPattern(pat);
//		lastPattern = mp;
//		mp.initMatch(subj, this);
//		return mp.next();
//	}


	// Expressions -----------------------------------------------------------

	@Override
	public Result visitExpressionLiteral(Literal x) {
		return x.getLiteral().accept(this);
	}

	@Override
	public Result visitLiteralInteger(Integer x) {
		return x.getIntegerLiteral().accept(this);
	}

	@Override
	public Result visitLiteralReal(Real x) {
		String str = x.getRealLiteral().toString();
		return result(vf.real(str));
	}

	@Override
	public Result visitLiteralBoolean(Boolean x) {
		String str = x.getBooleanLiteral().toString();
		return result(vf.bool(str.equals("true")));
	}

	@Override
	public Result visitLiteralString(
			org.meta_environment.rascal.ast.Literal.String x) {
		String str = x.getStringLiteral().toString();
		return result(vf.string(unescape(str, x)));
	}

	private String unescape(String str, AbstractAST ast) {
		byte[] bytes = str.getBytes();
		StringBuffer result = new StringBuffer();
		
		for (int i = 1; i < bytes.length - 1; i++) {
			char b = (char) bytes[i];
			switch (b) {
			/*
			 * Replace <var> by var's value.
			 */
			case '<':
				StringBuffer var = new StringBuffer();
				char varchar;
				while((varchar = (char) bytes[++i]) != '>'){
					var.append(varchar);
				}
				Result val = peek().getVariable(ast, var.toString());
				String replacement;
				if(val == null || val.getValue() == null) {
					throw new UninitializedVariableError(var.toString(), ast);
				} else {
					if(val.getType().isStringType()){
						replacement = ((IString)val.getValue()).getValue();
					} else {
						replacement = val.getValue().toString();
					}
				}
//				replacement = replacement.replaceAll("<", "\\\\<"); TODO: maybe we need this after all?
				result.append(replacement);
				continue;
			case '\\':
				switch (bytes[++i]) {
				case '\\':
					b = '\\'; 
					break;
				case 'n':
					b = '\n'; 
					break;
				case '"':
					b = '"'; 
					break;
				case 't':
					b = '\t'; 
					break;
				case 'b':
					b = '\b'; 
					break;
				case 'f':
					b = '\f'; 
					break;
				case 'r':
					b = '\r'; 
					break;
				case '<':
					b = '<'; 
					break;
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
					b = (char) (bytes[i] - '0');
					if (i < bytes.length - 1 && Character.isDigit(bytes[i+1])) {
						b = (char) (b * 8 + (bytes[++i] - '0'));
						
						if (i < bytes.length - 1 && Character.isDigit(bytes[i+1])) {
							b = (char) (b * 8 + (bytes[++i] - '0'));
						}
					}
					break;
				case 'u':
					// TODO unicode escape
					break;
				default:
				    b = '\\';	
				}
			}
			
			result.append(b);
		}
		
		return result.toString();
	}

	@Override
	public Result visitIntegerLiteralDecimalIntegerLiteral(
			DecimalIntegerLiteral x) {
		String str = x.getDecimal().toString();
		return result(vf.integer(str));
	}
	
	@Override
	public Result visitExpressionQualifiedName(
			org.meta_environment.rascal.ast.Expression.QualifiedName x) {
		if (isTreeConstructorName(x.getQualifiedName(), tf.tupleEmpty())) {
			return constructTree(x.getQualifiedName(), new IValue[0], tf.tupleType(new Type[0]));
		}
		else {
			Result result = peek().getVariable(x.getQualifiedName());

			if (result != null && result.getValue() != null) {
				return result;
			} else {
				throw new UninitializedVariableError(x.getQualifiedName().toString(), x);
			}
		}
	}
	
	@Override
	public Result visitExpressionList(List x) {
		java.util.List<org.meta_environment.rascal.ast.Expression> elements = x
				.getElements();
		
		Type elementType = tf.voidType();
		java.util.List<IValue> results = new ArrayList<IValue>();

		for (org.meta_environment.rascal.ast.Expression expr : elements) {
			Result resultElem = expr.accept(this);
			if(resultElem.getType().isListType() && !expr.isList() &&
					elementType.isSubtypeOf(resultElem.getType().getElementType())){
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

		Type resultType = tf.listType(elementType);
		IListWriter w = resultType.writer(vf);
		w.appendAll(results);
		return result(resultType, w.done());
	}

	@Override
	public Result visitExpressionSet(Set x) {
		java.util.List<org.meta_environment.rascal.ast.Expression> elements = x
				.getElements();
		
		Type elementType = tf.voidType();
		java.util.List<IValue> results = new ArrayList<IValue>();

		for (org.meta_environment.rascal.ast.Expression expr : elements) {
			Result resultElem = expr.accept(this);
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
		return result(resultType, w.done());
	}

	@Override
	public Result visitExpressionMap(
			org.meta_environment.rascal.ast.Expression.Map x) {

		java.util.List<org.meta_environment.rascal.ast.Mapping> mappings = x
				.getMappings();
		Map<IValue,IValue> result = new HashMap<IValue,IValue>();
		Type keyType = tf.voidType();
		Type valueType = tf.voidType();

		for (org.meta_environment.rascal.ast.Mapping mapping : mappings) {
			Result keyResult = mapping.getFrom().accept(this);
			Result valueResult = mapping.getTo().accept(this);
			
			keyType = keyType.lub(keyResult.getType());
			valueType = valueType.lub(valueResult.getType());
			
			result.put(keyResult.getValue(), valueResult.getValue());
		}
		
		Type type = tf.mapType(keyType, valueType);
		IMapWriter w = type.writer(vf);
		w.putAll(result);
		
		return result(type, w.done());
	}
	
	@Override
	public Result visitExpressionNonEmptyBlock(NonEmptyBlock x) {
		return new Lambda(x, this, tf.voidType(), "", tf.tupleEmpty(), false, x.getStatements(), peek());
	}

	@Override
	public Result visitExpressionTuple(Tuple x) {
		java.util.List<org.meta_environment.rascal.ast.Expression> elements = x
				.getElements();

		IValue[] values = new IValue[elements.size()];
		Type[] types = new Type[elements.size()];

		for (int i = 0; i < elements.size(); i++) {
			Result resultElem = elements.get(i).accept(this);
			types[i] = resultElem.getType();
			values[i] = resultElem.getValue();
		}

		return result(tf.tupleType(types), vf.tuple(values));
	}
	
	@Override
	public Result visitExpressionGetAnnotation(
			org.meta_environment.rascal.ast.Expression.GetAnnotation x) {
		  Result base = x.getExpression().accept(this);
		String annoName = x.getName().toString();

		Type annoType = callStack.peek().getAnnotationType(base.getType(), annoName);

		if (annoType == null) {
			throw new UndeclaredAnnotationError(annoName
					,base.getType(), x);
		}

		IValue annoValue = ((IConstructor) base.getValue()).getAnnotation(annoName);
		
		if (annoValue == null) {
			throw RuntimeExceptionFactory.noSuchAnnotation(annoName);
		}
		return result(annoType, annoValue);
	}
	
	@Override
	public Result visitExpressionSetAnnotation(
			org.meta_environment.rascal.ast.Expression.SetAnnotation x) {
		Result base = x.getExpression().accept(this);
		String annoName = x.getName().toString();
		Result anno = x.getValue().accept(this);

		Type annoType = callStack.peek().getAnnotationType(base.getType(), annoName);

		if (annoType == null) {
			throw new UndeclaredAnnotationError(annoName
					,base.getType(), x);
		}
		if(!anno.getType().isSubtypeOf(annoType)){
			throw new UnexpectedTypeError(annoType, anno.getType(), x);
		}

		IValue annotatedBase = ((IConstructor) base.getValue()).setAnnotation(annoName, anno.getValue());
		
		return result(base.getType(), annotatedBase);
	}
	
	public static void widenArgs(Result left, Result right) {
		TypeFactory tf = TypeFactory.getInstance();
		
		Type leftValType = left.getValueType();
		Type rightValType = right.getValueType();
		if (leftValType.isIntegerType() && rightValType.isRealType()) {
			if(left.getType().isIntegerType()){
				left.setType(tf.realType());
			}
			left.setValue(((IInteger) left.getValue()).toReal());
		} else if (leftValType.isRealType() && rightValType.isIntegerType()) {
			if(right.getType().isIntegerType()){
				right.setType(tf.realType());
			}
			right.setValue(((IInteger) right.getValue()).toReal());
		} 
		/*else if(left.getType().isConstructorType()){
			left.getType() = left.getType().getAbstractDataType();
		} else 	if(right.getType().isConstructorType()){
			right.getType() = right.getType().getAbstractDataType();
		}
		*/
	}
	
    @Override
	public Result visitExpressionAddition(Addition x) {
		Result left = x.getLhs().accept(this);
		Result right = x.getRhs().accept(this);

		widenArgs(left, right);
		Type resultType = left.getType().lub(right.getType());
		
		//System.err.println("left=" + left + "; right=" + right + "; resulType=" + resultType);

		// Integer
		if (left.getType().isIntegerType() && right.getType().isIntegerType()) {
			return result(((IInteger) left.getValue()).add((IInteger) right.getValue()));
			
		}
		//Real
		if (left.getType().isRealType() && right.getType().isRealType()) {
			return result(((IReal) left.getValue()).add((IReal) right.getValue()));
			
		}
		//String
		if (left.getType().isStringType() && right.getType().isStringType()) {
			return result(vf.string(((IString) left.getValue()).getValue()
					+ ((IString) right.getValue()).getValue()));
			
		}
		//List
		if (left.getType().isListType()){
				if(right.getType().isListType()) {
					return result(resultType, ((IList) left.getValue())
					.concat((IList) right.getValue()));
				}
				if(right.getType().isSubtypeOf(left.getType().getElementType())){
					return result(left.getType(), ((IList)left.getValue()).append(right.getValue()));
				}
		}
		
		if (right.getType().isListType()){
			if(left.getType().isSubtypeOf(right.getType().getElementType())){
				return result(right.getType(), ((IList)right.getValue()).insert(left.getValue()));
			}
		}
		
		//Relation
		if (left.getType().isRelationType() && right.getType().isRelationType()) {
			if(LAZY)
				return result(resultType, new LazyUnion(((ISet) left.getValue()),
						                                 (ISet) right.getValue()));
			else							
				return result(resultType, ((ISet) left.getValue())
						.union((ISet) right.getValue()));
		}
		
		//Set
		if (left.getType().isSetType()){
			if(right.getType().isSetType()) {
				if(LAZY)
					return result(resultType, new LazyUnion(((ISet) left.getValue()),
						                                     (ISet) right.getValue()));
				else
					return result(resultType, ((ISet) left.getValue())
							.union((ISet) right.getValue()));
			}
			if(right.getType().isSubtypeOf(left.getType().getElementType())){
				if(LAZY)
					return result(left.getType(), new LazyInsert((ISet)left.getValue(),
																   right.getValue()));
				else
				 return result(left.getType(), ((ISet)left.getValue()).insert(right.getValue()));
			}
		}
	
		if (right.getType().isSetType()){
			if(left.getType().isSubtypeOf(right.getType().getElementType())){
				if(LAZY)
					return result(right.getType(), new LazyInsert((ISet)right.getValue(),
																	left.getValue()));
				return result(right.getType(), ((ISet)right.getValue()).insert(left.getValue()));
			}
		}
		
		//Map
		if (left.getType().isMapType() && right.getType().isMapType()) {
			return result(resultType, ((IMap) left.getValue())              //TODO: is this the right operation?
					.join((IMap) right.getValue()));
			
		}
		//Tuple
		if(left.getType().isTupleType() && right.getType().isTupleType()) {
			Type leftType = left.getType();
			Type rightType = right.getType();
			
			int leftArity = leftType.getArity();
			int rightArity = rightType.getArity();
			int newArity = leftArity + rightArity;
			
			Type fieldTypes[] = new Type[newArity];
			String fieldNames[] = new String[newArity];
			IValue fieldValues[] = new IValue[newArity];
			
			for(int i = 0; i < leftArity; i++){
				fieldTypes[i] = leftType.getFieldType(i);
				fieldNames[i] = leftType.getFieldName(i);
				fieldValues[i] = ((ITuple) left.getValue()).get(i);
			}
			
			for(int i = 0; i < rightArity; i++){
				fieldTypes[leftArity + i] = rightType.getFieldType(i);
				fieldNames[leftArity + i] = rightType.getFieldName(i);
				fieldValues[leftArity + i] = ((ITuple) right.getValue()).get(i);
			}
			
			//TODO: avoid null fieldnames
			for(int i = 0; i < newArity; i++){
				if(fieldNames[i] == null){
					fieldNames[i] = "f" + String.valueOf(i);
				}
			}
			Type newTupleType = tf.tupleType(fieldTypes, fieldNames);
			return result(newTupleType, vf.tuple(fieldValues));
			
		}
		
		
		throw new UnsupportedOperationError("+", left.getType(), right.getType(),x);
	}
    
	@Override
	public Result visitExpressionSubtraction(Subtraction x) {
		Result left = x.getLhs().accept(this);
		Result right = x.getRhs().accept(this);
		Type resultType = left.getType().lub(right.getType());
		
		widenArgs(left, right);

		// Integer
		if (left.getType().isIntegerType() && right.getType().isIntegerType()) {
			return result(((IInteger) left.getValue()).subtract((IInteger) right.getValue()));
		}
		
		// Real
		if (left.getType().isRealType() && right.getType().isRealType()) {
			return result(((IReal) left.getValue()).subtract((IReal) right.getValue()));
		}
		
		// List
		if (left.getType().isListType() && right.getType().isListType()) {
			IListWriter w = left.getType().writer(vf);
			
			IList listLeft = (IList)left.getValue();
			IList listRight = (IList)right.getValue();
			
			int lenLeft = listLeft.length();
			int lenRight = listRight.length();

			for(int i = lenLeft-1; i > 0; i--) {
				boolean found = false;
				IValue leftVal = listLeft.get(i);
				for(int j = 0; j < lenRight; j++){
					if(leftVal.isEqual(listRight.get(j))){
						found = true;
						break;
					}
				}
				if(!found){
			        w.insert(leftVal);
				}
			}
			return result(left.getType(), w.done());
		}
		
		// Set
		if (left.getType().isSetType()){
				if(right.getType().isSetType()) {
					return result(resultType, ((ISet) left.getValue())
							.subtract((ISet) right.getValue()));
				}
				if(right.getType().isSubtypeOf(left.getType().getElementType())){
					return result(left.getType(), ((ISet)left.getValue())
							.subtract(vf.set(right.getValue())));
				}
		}
		
		// Map
		if (left.getType().isMapType() && right.getType().isMapType()) {
			return result(resultType, ((IMap) left.getValue())
					.remove((IMap) right.getValue()));
		}
		
		//Relation
		if (left.getType().isRelationType() && right.getType().isRelationType()) {
			return result(resultType, ((ISet) left.getValue())
					.subtract((ISet) right.getValue()));
		}
		
		throw new UnsupportedOperationError("-", left.getType(), right.getType(),x);
	}
	
	@Override
	public Result visitExpressionNegative(Negative x) {
		Result arg = x.getArgument().accept(this);
		
		if (arg.getType().isIntegerType()) {
			return result(((IInteger) arg.getValue()).negate());
		}
		else if (arg.getType().isRealType()) {
				return result(((IReal) arg.getValue()).negate());
		} else {
			throw new UnsupportedOperationError("-", arg.getType(), x);
		}
	}
	
	@Override
	public Result visitExpressionProduct(Product x) {
		Result left = x.getLhs().accept(this);
		Result right = x.getRhs().accept(this);
		
		widenArgs(left, right);

		//Integer
		if (left.getType().isIntegerType() && right.getType().isIntegerType()) {
			return result(((IInteger) left.getValue()).multiply((IInteger) right.getValue()));
		} 
		
		//Real 
		else if (left.getType().isRealType() && right.getType().isRealType()) {
			return result(((IReal) left.getValue()).multiply((IReal) right.getValue()));
		}
		
		// List
		else if (left.getType().isListType() && right.getType().isListType()){
			Type leftElementType = left.getType().getElementType();
			Type rightElementType = right.getType().getElementType();
			Type resultType = tf.listType(tf.tupleType(leftElementType, rightElementType));
			IListWriter w = resultType.writer(vf);
			
			for(IValue v1 : (IList) left.getValue()){
				for(IValue v2 : (IList) right.getValue()){
					w.append(vf.tuple(v1, v2));	
				}
			}
			return result(resultType, w.done());	
		}
		
		// Relation
		else if(left.getType().isRelationType() && right.getType().isRelationType()){
			Type leftElementType = left.getType().getElementType();
			Type rightElementType = right.getType().getElementType();
			
			int leftArity = leftElementType.getArity();
			int rightArity = rightElementType.getArity();
			int newArity = leftArity + rightArity;
			
			Type fieldTypes[] = new Type[newArity];
			String fieldNames[] = new String[newArity];
			
			for(int i = 0; i < leftArity; i++){
				fieldTypes[i] = leftElementType.getFieldType(i);
				fieldNames[i] = leftElementType.getFieldName(i);
			}
			for(int i = 0; i < rightArity; i++){
				fieldTypes[leftArity + i] = rightElementType.getFieldType(i);
				fieldNames[leftArity + i] = rightElementType.getFieldName(i);
			}
			
			// TODO: avoid empty field names
			for(int i = 0; i < newArity; i++){
				if(fieldNames[i] == null){
					fieldNames[i] = "f" + String.valueOf(i);
				}
			}
			
			Type resElementType = tf.tupleType(fieldTypes, fieldNames);
			Type resultType = tf.relTypeFromTuple(resElementType);
			IRelationWriter w = resultType.writer(vf);
			
			for(IValue v1 : (IRelation) left.getValue()){
				IValue elementValues[] = new IValue[newArity];
				
				for(int i = 0; i < leftArity; i++){
					elementValues[i] = ((ITuple) v1).get(i);
				}
				for(IValue v2 : (IRelation) right.getValue()){
					for(int i = 0; i <rightArity; i++){
						elementValues[leftArity + i] = ((ITuple) v2).get(i);
					}
					w.insert(vf.tuple(elementValues));	
				}
			}
			
			return result(resultType, w.done());
		}
		
		//Set
		else if (left.getType().isSetType() && right.getType().isSetType()){
			Type leftElementType = left.getType().getElementType();
			Type rightElementType = right.getType().getElementType();
			Type resultType = tf.relType(leftElementType, rightElementType);
			IRelationWriter w = resultType.writer(vf);
			
			for(IValue v1 : (ISet) left.getValue()){
				for(IValue v2 : (ISet) right.getValue()){
					w.insert(vf.tuple(v1, v2));	
				}
			}
			return result(resultType, w.done());	
		}
		else {
			throw new UnsupportedOperationError("*", left.getType(), right.getType(),x);
		}
	}
	
	@Override
	public Result visitExpressionDivision(Division x) {
		Result left = x.getLhs().accept(this);
		Result right = x.getRhs().accept(this);
		
		widenArgs(left, right);

		try {
			//Integer
			if (left.getType().isIntegerType() && right.getType().isIntegerType()) {
					return result(((IInteger) left.getValue()).divide((IInteger) right.getValue()));
				
			} 
			
			// Real
			else if (left.getType().isRealType() && right.getType().isRealType()) {
				// TODO magic constant 80*80 should dissappear when Tijs is done
				return result(((IReal) left.getValue()).divide((IReal) right.getValue(), 80*80));
			}
			else {
				throw new UnsupportedOperationError("/", left.getType(), right.getType(),x);
			}
		}
		catch (ArithmeticException e){
			// TODO throw better exception
			throw new ImplementationError(e.getMessage());
		}
	}
	
	@Override
	public Result visitExpressionModulo(Modulo x) {
		Result left = x.getLhs().accept(this);
		Result right = x.getRhs().accept(this);

		if (left.getType().isIntegerType() && right.getType().isIntegerType()) {
			return result(((IInteger) left.getValue()).remainder((IInteger) right.getValue()));
		} 
		else {
			throw new UnsupportedOperationError("%", left.getType(), right.getType(),x);
		}
	}
	
	@Override
	public Result visitExpressionBracket(Bracket x) {
		return x.getExpression().accept(this);
	}
	
	@Override
	public Result visitExpressionIntersection(Intersection x) {
		Result left = x.getLhs().accept(this);
		Result right = x.getRhs().accept(this);
		Type resultType = left.getType().lub(right.getType());

		//Set
		if (left.getType().isSetType() && right.getType().isSetType()) {
				return result(resultType, ((ISet) left.getValue())
				.intersect((ISet) right.getValue()));
		} 
		
		//Map
		else if (left.getType().isMapType() && right.getType().isMapType()) {
			return result(resultType, ((IMap) left.getValue())
					.common((IMap) right.getValue()));
		} 
		
		//Relation
		else if (left.getType().isRelationType() && right.getType().isRelationType()) {
			return result(resultType, ((ISet) left.getValue())
				.intersect((ISet) right.getValue()));
		} else {
			throw new UnsupportedOperationError("&", left.getType() , right.getType(), x);
		}
	}

	@Override
	public Result visitExpressionOr(Or x) {
		return new OrEvaluator(x, this).next();
	}

	@Override
	public Result visitExpressionAnd(And x) {
		return new AndEvaluator(x, this).next();
	}

	@Override
	public Result visitExpressionNegation(Negation x) {
		return new NegationEvaluator(x, this).next();
	}
	
	@Override
	public Result visitExpressionImplication(Implication x) {
		return new ImplicationEvaluator(x, this).next();
	}
	
	@Override
	public Result visitExpressionEquivalence(Equivalence x) {
		return new EquivalenceEvaluator(x, this).next();
	}
	
	// TODO factor out into Result or a subclass thereof
	public static boolean equals(Result left, Result right){
		widenArgs(left, right);
		
		//System.err.println("equals: left=" + left + ", right=" + right + " comparable=" + left.getType().comparable(right.getType()));
		if (left.getType().comparable(right.getType())) {
			return compare(left, right) == 0;
		} else {
			return false;
				//TODO; type error
		}
	}

	@Override
	public Result visitExpressionEquals(
			org.meta_environment.rascal.ast.Expression.Equals x) {
		Result left = x.getLhs().accept(this);
		Result right = x.getRhs().accept(this);
		
		widenArgs(left, right);
/*		
		if (!left.getType().comparable(right.getType())) {
			throw new RascalTypeError("Arguments of equals have incomparable types: " + left.getType() + " and " + right.getType(), x);
		}
*/
		
		return result(vf.bool(equals(left, right)));
	}
	
	@Override
	public Result visitExpressionOperatorAsValue(OperatorAsValue x) {
		// TODO
		throw new NotYetImplemented(x);
	}
	
	@Override
	public Result visitExpressionLocation(Location x){
		
		String urlText = x.getUrl().toString();
		
		Result length = x.getLength().accept(this);
		int iLength = ((IInteger) length.getValue()).intValue();
		
		Result offset = x.getOffset().accept(this);	
		int iOffset = ((IInteger) offset.getValue()).intValue();
		
		Result beginLine = x.getBeginLine().accept(this);
		int iBeginLine = ((IInteger) beginLine.getValue()).intValue();
		
		Result endLine = x.getEndLine().accept(this);
		int iEndLine = ((IInteger) endLine.getValue()).intValue();
		
		Result beginColumn = x.getBeginColumn().accept(this);
		int iBeginColumn = ((IInteger) beginColumn.getValue()).intValue();
		
		Result endColumn = x.getEndColumn().accept(this);
		int iEndColumn = ((IInteger) endColumn.getValue()).intValue();

		try {
			URL url = new URL(urlText);
			  ISourceLocation r = vf.sourceLocation(url, iOffset, iLength, iBeginLine, iEndLine, iBeginColumn, iEndColumn);
			  return result(tf.sourceLocationType(), r);
		} catch (MalformedURLException e){
			throw new SyntaxError("URL", x.getLocation());
		}
	}
	
	@Override
	public Result visitExpressionClosure(Closure x) {
		Type formals = te.eval(x.getParameters(), peek());
		Type returnType = evalType(x.getType());
		return new Lambda(x, this, returnType, "", formals, x.getParameters().isVarArgs(), x.getStatements(), peek());
	}

	@Override
	public Result visitExpressionVoidClosure(VoidClosure x) {
		Type formals = te.eval(x.getParameters(), peek());
		return new Lambda(x, this, tf.voidType(), "", formals, x.getParameters().isVarArgs(), x.getStatements(), peek());
	}
	
	@Override
	public Result visitExpressionFieldUpdate(FieldUpdate x) {
		Result expr = x.getExpression().accept(this);
		Result repl = x.getReplacement().accept(this);
		String name = x.getKey().toString();
		
		try {
			if (expr.getType().isTupleType()) {
				Type tuple = expr.getType();
				Type argType = tuple.getFieldType(name);
				ITuple value = (ITuple) expr.getValue();
				
				checkType(repl.getType(), argType);
				
				return result(expr.getType(), value.set(name, repl.getValue()));
			}
			else if (expr.getType().isAbstractDataType() || expr.getType().isConstructorType()) {
				Type node = ((IConstructor) expr.getValue()).getConstructorType();
			
				if (!node.hasField(name)) {
					throw new UndeclaredFieldError(name, expr.getValueType(), x);
				}
			
				int index = node.getFieldIndex(name);
				
				checkType(repl.getType(), node.getFieldType(index));
				
				return result(expr.getType(), ((IConstructor) expr.getValue()).set(index, repl.getValue()));
			}
			else if (expr.getType().isSourceLocationType()){
				ISourceLocation loc = (ISourceLocation) expr.getValue();
				
				return sourceLocationFieldUpdate(loc, name, repl.getValue(),repl.getType(), x);
			} else {
				throw new UnsupportedOperationError("Field update", expr.getType(), x);
			}
		} catch (UndeclaredFieldException e) {
			// TODO I don't think that this should ever happen (ImplementationError?)
			throw new UndeclaredFieldError(e.getLabel(), expr.getType(), x);
		}
	}
	
	protected Result sourceLocationFieldUpdate(ISourceLocation loc, String field, IValue value, Type type, AbstractAST x){
		int iLength = loc.getLength();
		int iOffset = loc.getOffset();
		int iBeginLine = loc.getBeginLine();
		int iBeginColumn = loc.getBeginColumn();
		int iEndLine = loc.getEndLine();
		int iEndColumn = loc.getEndColumn();
		String urlText = loc.getURL().toString();

		if(field.equals("url")){
			if(!type.isStringType())
				throw new UnexpectedTypeError(tf.stringType(), type, x);
			urlText = ((IString) value).getValue();
		} else {
			if(!type.isIntegerType()) {
				throw new UnexpectedTypeError(tf.integerType(), type, x);
			}

			if(field.equals("length")){
				iLength = ((IInteger) value).intValue();
			} else if(field.equals("offset")){
				iOffset = ((IInteger) value).intValue();
			} else if(field.equals("beginLine")){
				iBeginLine = ((IInteger) value).intValue();
			} else if(field.equals("beginColumn")){
				iBeginColumn = ((IInteger) value).intValue();
			} else if(field.equals("endLine")){
				iEndLine = ((IInteger) value).intValue();
			} else if(field.equals("endColumn")){
				iEndColumn = ((IInteger) value).intValue();
			} else {
				throw new UndeclaredFieldError(field, tf.sourceLocationType(), x);
			}
		}
		try {
			URL url = new URL(urlText);
			ISourceLocation nloc = vf.sourceLocation(url, iOffset, iLength, iBeginLine, iEndLine, iBeginColumn, iEndColumn);
			return result(tf.sourceLocationType(), nloc);
		} 
		catch (MalformedURLException e) {
			throw new SyntaxError("URL", x.getLocation());
		} 
		catch (IllegalArgumentException e) {
			throw RuntimeExceptionFactory.illegalArgument();
		}
	}
	
	@Override
	public Result visitExpressionLexical(Lexical x) {
		throw new NotYetImplemented(x);// TODO
	}
	
	@Override
	public Result visitExpressionRange(Range x) {
		IListWriter w = vf.listWriter(tf.integerType());
		Result from = x.getFirst().accept(this);
		Result to = x.getLast().accept(this);

		if (!from.getType().isIntegerType()) {
			throw new UnexpectedTypeError(tf.integerType(), from.getType(), x.getFirst());
		}
		
		if (!to.getType().isIntegerType()) {
			throw new UnexpectedTypeError(tf.integerType(), to.getType(), x.getLast());
		}
		
		IInteger iFrom = ((IInteger) from.getValue());
		IInteger iTo = ((IInteger) to.getValue());
		
		
		
		IInteger one = vf.integer(1);
		
		if (iTo.less(iFrom).getValue()) {
			while (iFrom.greaterEqual(iTo).getValue()) {
				w.append(iFrom);
				iFrom = iFrom.subtract(one);
			} 
		}
		else {
			while (iFrom.lessEqual(iTo).getValue()) {
				w.append(iFrom);
				iFrom = iFrom.add(one);
			}
		}
		
		return result(tf.listType(tf.integerType()), w.done());
	}
	
	@Override
	public Result visitExpressionStepRange(StepRange x) {
		IListWriter w = vf.listWriter(tf.integerType());
		Result from = x.getFirst().accept(this);
		Result to = x.getLast().accept(this);
		Result second = x.getSecond().accept(this);

		if (!from.getType().isIntegerType()) {
			throw new UnexpectedTypeError(tf.integerType(), from.getType(), x.getFirst());
		}
		
		if (!to.getType().isIntegerType()) {
			throw new UnexpectedTypeError(tf.integerType(), to.getType(), x.getLast());
		}
	
		if (!second.getType().isIntegerType()) {
			throw new UnexpectedTypeError(tf.integerType(), second.getType(), x.getSecond());
		}
	
		IInteger iFrom = ((IInteger) from.getValue());
		IInteger iSecond = ((IInteger) second.getValue());
		IInteger iTo = ((IInteger) to.getValue());
		
		IInteger diff = iSecond.subtract(iFrom);
		
		if (iFrom.lessEqual(iTo).getValue() && diff.greater(vf.integer(0)).getValue()) {
			do {
				w.append(iFrom);
				iFrom = iFrom.add(diff);
			} while (iFrom.lessEqual(iTo).getValue());
		} 
		else if(iFrom.greaterEqual(iTo).getValue() && diff.less(vf.integer(0)).getValue()) {
			do {
				w.append(iFrom);
				iFrom = iFrom.add(diff);
			} while (iFrom.greaterEqual(iTo).getValue());
		}
		
		return result(tf.listType(tf.integerType()), w.done());
		
	}
	
	@Override
	public Result visitExpressionTypedVariable(TypedVariable x) {
		throw new SyntaxError("Typed variable outside matching context", x.getLocation());
	}
	
	private boolean matchAndEval(IValue subject, org.meta_environment.rascal.ast.Expression pat, Statement stat){
		MatchPattern mp = evalPattern(pat);
		mp.initMatch(subject, peek());
		
		lastPattern = mp;
		//System.err.println("matchAndEval: subject=" + subject + ", pat=" + pat);
		try {
			push(); 	// Create a separate scope for match and statement
			peek().storeVariable("subject", result(subject.getType(), subject));
			while(mp.hasNext()){
				//System.err.println("matchAndEval: mp.hasNext()==true");
				if(mp.next()){
					//System.err.println("matchAndEval: mp.next()==true");
					try {
						checkPoint(peek());
						//System.err.println(stat.toString());
						stat.accept(this);
						commit(peek());
						return true;
					} catch (Failure e){
						//System.err.println("failure occurred");
						rollback(peek());
					}
				}
			}
		} finally {
			pop();
		}
		return false;
	}
	

	
	private boolean matchEvalAndReplace(IValue subject, 
			org.meta_environment.rascal.ast.Expression pat, 
			java.util.List<Expression> conditions,
			Expression replacementExpr){
		MatchPattern mp = evalPattern(pat);
		mp.initMatch(subject, peek());
		lastPattern = mp;
		//System.err.println("matchEvalAndReplace: subject=" + subject + ", pat=" + pat + ", conditions=" + conditions);
		try {
			push(); 	// Create a separate scope for match and statement
			while(mp.hasNext()){
				//System.err.println("mp.hasNext()==true; mp=" + mp);
				if(mp.next()){
					try {
						boolean trueConditions = true;
						for(Expression cond : conditions){
							//System.err.println("cond = " + cond);
							if(!cond.accept(this).isTrue()){
								trueConditions = false;
								break;
							}
						}
						if(trueConditions){
							throw new org.meta_environment.rascal.interpreter.control_exceptions.Insert(replacementExpr.accept(this));		
						}
					} catch (Failure e){
						//System.err.println("failure occurred");
					}
				}
			}
		} finally {
			pop();
		}
		return false;
	}
	
	@Override
	public Result visitStatementSwitch(Switch x) {
		Result subject = x.getExpression().accept(this);

		for(Case cs : x.getCases()){
			if(cs.isDefault()){
				return cs.getStatement().accept(this);
			}
			org.meta_environment.rascal.ast.Rule rule = cs.getRule();
			if(rule.isArbitrary() && matchAndEval(subject.getValue(), rule.getPattern(), rule.getStatement())){
				return result();
			} else if(rule.isGuarded())	{
				org.meta_environment.rascal.ast.Type tp = rule.getType();
				Type t = evalType(tp);
				if(subject.getType().isSubtypeOf(t) && matchAndEval(subject.getValue(), rule.getPattern(), rule.getStatement())){
					return result();
				}
			} else if(rule.isReplacing()){
				throw new NotYetImplemented(rule);
			}
		}
		return null;
	}
	
	@Override
	public Result visitExpressionVisit(Visit x) {
		return x.getVisit().accept(this);
	}
	
	/*
	 * TraverseResult contains the value returned by a traversal
	 * and a changed flag that indicates whether the value itself or
	 * any of its children has been changed during the traversal.
	 */
	
	class TraverseResult {
		boolean matched;   // Some rule matched;
		IValue value; 		// Result of the 
		boolean changed;   // Original subject has been changed
		
		TraverseResult(boolean someMatch, IValue value){
			this.matched = someMatch;
			this.value = value;
			this.changed = false;
		}
		
		TraverseResult(IValue value){
			this.matched = false;
			this.value = value;
			this.changed = false;
		}
		
		TraverseResult(IValue value, boolean changed){
			this.matched = true;
			this.value   = value;
			this.changed = changed;
		}
		TraverseResult(boolean someMatch, IValue value, boolean changed){
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
		private java.util.List<org.meta_environment.rascal.ast.Rule> rules;
		
		@SuppressWarnings("unchecked")
		CasesOrRules(java.util.List<?> casesOrRules){
			if(casesOrRules.get(0) instanceof Case){
				this.cases = (java.util.List<Case>) casesOrRules;
			} else {
				rules = (java.util.List<org.meta_environment.rascal.ast.Rule>)casesOrRules;
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
		public java.util.List<org.meta_environment.rascal.ast.Rule> getRules(){
			return rules;
		}
	}
	
	private TraverseResult traverse(IValue subject, CasesOrRules casesOrRules,
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
			} else {
				return casesOrRules.getRules().get(0);
			}
		}
		return null;
	}
	
	/*
	 * traverString implements a visit of a string subject and applies the set of cases
	 * for all substrings of the subject. At the end, all replacements are applied and the modified
	 * subject is returned.
	 */
	
	private TraverseResult traverseString(IString subject, CasesOrRules casesOrRules){
		String subjectString = subject.getValue();
		int len = subjectString.length();
		java.util.List<StringReplacement> replacements = new ArrayList<StringReplacement>();
		boolean matched = false;
		boolean changed = false;
		int cursor = 0;
		
		Case cs = (Case) singleCase(casesOrRules);
		
		RegExpPatternEvaluator re = new RegExpPatternEvaluator(vf, peek());
		if(cs != null && cs.isRule() && re.isRegExpPattern(cs.getRule().getPattern())){
			/*
			 * In the frequently occurring case that there is one case with a regexp as pattern,
			 * we can delegate all the work to the regexp matcher.
			 */
			org.meta_environment.rascal.ast.Rule rule = cs.getRule();
			
			Expression patexp = rule.getPattern();
			MatchPattern mp = evalPattern(patexp);
			mp.initMatch(subject, peek());

			try {
				push(); // a separate scope for match and statement/replacement
				while(mp.hasNext()){
					if(mp.next()){
						try {
							if(rule.isReplacing()){
								Replacement repl = rule.getReplacement();
								boolean trueConditions = true;
								if(repl.isConditional()){
									for(Expression cond : repl.getConditions()){
										Result res = cond.accept(this);
										if(!res.isTrue()){         // TODO: How about alternatives?
											trueConditions = false;
											break;
										}
									}
								}
								if(trueConditions){
									throw new org.meta_environment.rascal.interpreter.control_exceptions.Insert(repl.getReplacementExpression().accept(this));
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
				pop();
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
					TraverseResult tr = applyCasesOrRules(vf.string(subjectString.substring(cursor, len)), casesOrRules);
					matched |= tr.matched;
					changed |= tr.changed;
					//System.err.println("matched=" + matched + ", changed=" + changed);
					cursor++;
				} catch (org.meta_environment.rascal.interpreter.control_exceptions.Insert e){
					IValue repl = e.getValue().getValue();
					if(repl.getType().isStringType()){
						int start;
						int end;
						if(lastPattern instanceof RegExpPatternValue){
							start = ((RegExpPatternValue)lastPattern).getStart();
							end = ((RegExpPatternValue)lastPattern).getEnd();
						} else if(lastPattern instanceof AbstractPatternLiteral){
							start = 0;
							end = ((IString)repl).getValue().length();
						} else {
							throw new SyntaxError("Illegal pattern " + lastPattern + " in string visit", getCurrentStatement().getLocation());
						}
						
						replacements.add(new StringReplacement(cursor + start, cursor + end, ((IString)repl).getValue()));
						matched = changed = true;
						cursor += end;
					} else {
						throw new UnexpectedTypeError(tf.stringType(),repl.getType(), getCurrentStatement());
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
		
		return new TraverseResult(matched, vf.string(res.toString()), changed);
	}
	
	/*
	 * traverseOnce: traverse an arbitrary IVAlue once. Implements the strategies bottomup/topdown.
	 */
	
	private TraverseResult traverseOnce(IValue subject, CasesOrRules casesOrRules, 
			DIRECTION direction, 
			PROGRESS progress){
		Type subjectType = subject.getType();
		boolean matched = false;
		boolean changed = false;
		IValue result = subject;

		//System.err.println("traverseOnce: " + subject + ", type=" + subject.getType());
		if(subjectType.isStringType()){
			return traverseString((IString) subject, casesOrRules);
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
			IConstructor cons = (IConstructor)subject;
			if(cons.arity() == 0){
				result = subject;
			} else {
				IValue args[] = new IValue[cons.arity()];

				for(int i = 0; i < cons.arity(); i++){
					TraverseResult tr = traverseOnce(cons.get(i), casesOrRules, direction, progress);
					matched |= tr.matched;
					changed |= tr.changed;
					args[i] = tr.value;
				}
				IConstructor rcons = vf.constructor(cons.getConstructorType(), args);
				result = rcons.setAnnotations(cons.getAnnotations());
			}
		} else
			if(subjectType.isNodeType()){
				INode node = (INode)subject;
				if(node.arity() == 0){
					result = subject;
				} else {
					IValue args[] = new IValue[node.arity()];

					for(int i = 0; i < node.arity(); i++){
						TraverseResult tr = traverseOnce(node.get(i), casesOrRules, direction, progress);
						matched |= tr.matched;
						changed |= tr.changed;
						args[i] = tr.value;
					}
					result = vf.node(node.getName(), args).setAnnotations(node.getAnnotations());
				}
			} else
				if(subjectType.isListType()){
					IList list = (IList) subject;
					int len = list.length();
					if(len > 0){
						IListWriter w = list.getType().writer(vf);
						for(int i = len - 1; i >= 0; i--){
							TraverseResult tr = traverseOnce(list.get(i), casesOrRules, direction, progress);
							matched |= tr.matched;
							changed |= tr.changed;
							w.insert(tr.value);
						}
						result = w.done();
					} else {
						result = subject;
					}
				} else 
					if(subjectType.isSetType()){
						ISet set = (ISet) subject;
						if(!set.isEmpty()){
							ISetWriter w = set.getType().writer(vf);
							for(IValue v : set){
								TraverseResult tr = traverseOnce(v, casesOrRules, direction, progress);
								matched |= tr.matched;
								changed |= tr.changed;
								w.insert(tr.value);
							}
							result = w.done();
						} else {
							result = subject;
						}
					} else
						if (subjectType.isMapType()) {
							IMap map = (IMap) subject;
							if(!map.isEmpty()){
								IMapWriter w = map.getType().writer(vf);
								Iterator<Entry<IValue,IValue>> iter = map.entryIterator();

								while (iter.hasNext()) {
									Entry<IValue,IValue> entry = iter.next();
									TraverseResult tr = traverseOnce(entry.getKey(), casesOrRules, direction, progress);
									matched |= tr.matched;
									changed |= tr.changed;
									IValue newKey = tr.value;
									tr = traverseOnce(entry.getValue(), casesOrRules, direction, progress);
									matched |= tr.matched;
									changed |= tr.changed;
									IValue newValue = tr.value;
									w.put(newKey, newValue);
								}
								result = w.done();
							} else {
								result = subject;
							}
						} else
							if(subjectType.isTupleType()){
								ITuple tuple = (ITuple) subject;
								int arity = tuple.arity();
								IValue args[] = new IValue[arity];
								for(int i = 0; i < arity; i++){
									TraverseResult tr = traverseOnce(tuple.get(i), casesOrRules, direction, progress);
									matched |= tr.matched;
									changed |= tr.changed;
									args[i] = tr.value;
								}
								result = vf.tuple(args);
							} else {
								result = subject;
							}

		if(direction == DIRECTION.BottomUp){
			if((progress == PROGRESS.Breaking) && changed){
				return new TraverseResult(matched, result, changed);
			} else {
				TraverseResult tr = traverseTop(result, casesOrRules);
				matched |= tr.matched;
				changed |= tr.changed;
				return new TraverseResult(matched, tr.value, changed);
			}
		}
		return new TraverseResult(matched,result,changed);
	}
	
	/**
	 * Replace an old subject by a new one as result of an insert statement.
	 */
	private TraverseResult replacement(IValue oldSubject, IValue newSubject){
		return new TraverseResult(true, newSubject, true);
	}
	
	/**
	 * Loop over all cases or rules.
	 */
	
	private TraverseResult applyCasesOrRules(IValue subject, CasesOrRules casesOrRules) {
		if(casesOrRules.hasCases()){
			for (Case cs : casesOrRules.getCases()) {
				if (cs.isDefault()) {
					cs.getStatement().accept(this);
					return new TraverseResult(true,subject);
				} else {
					TraverseResult tr = applyOneRule(subject, cs.getRule());
					if(tr.matched){
						//System.err.println(" *** matches ***");
						return tr;
					}
				}
			}
		} else {
			//System.err.println("hasRules");
			for(org.meta_environment.rascal.ast.Rule rule : casesOrRules.getRules()){
				//System.err.println(rule);
				TraverseResult tr = applyOneRule(subject, rule);
				//System.err.println("rule fails");
				if(tr.matched){
					//System.err.println(" *** matches ***");
					return tr;
				}
			}
		}
		//System.err.println("applyCasesorRules does not match");
		return new TraverseResult(subject);
	}
	
	/*
	 * traverseTop: traverse the outermost symbol of the subject.
	 */

	private TraverseResult traverseTop(IValue subject, CasesOrRules casesOrRules) {
		//System.err.println("traversTop(" + subject + ")");
		try {
			return applyCasesOrRules(subject, casesOrRules);	
		} catch (org.meta_environment.rascal.interpreter.control_exceptions.Insert e) {

			return replacement(subject, e.getValue().getValue());
		}
	}
	
	/*
	 * applyOneRule: try to apply one rule to the subject.
	 */
	
	private TraverseResult applyOneRule(IValue subject,
			org.meta_environment.rascal.ast.Rule rule) {
		
		//System.err.println("applyOneRule: subject=" + subject + ", type=" + subject.getType() + ", rule=" + rule);
	
		if (rule.isArbitrary()){
			if(matchAndEval(subject, rule.getPattern(), rule.getStatement())) {
				return new TraverseResult(true, subject);
			}
		} else if (rule.isGuarded()) {
			org.meta_environment.rascal.ast.Type tp = rule.getType();
			Type type = evalType(tp);
			rule = rule.getRule();
			if (subject.getType().isSubtypeOf(type) && 
				matchAndEval(subject, rule.getPattern(), rule.getStatement())) {
				return new TraverseResult(true, subject);
			}
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
	public Result visitVisitDefaultStrategy(DefaultStrategy x) {
		
		IValue subject = x.getSubject().accept(this).getValue();
		java.util.List<Case> cases = x.getCases();
		
		TraverseResult tr = traverse(subject, new CasesOrRules(cases), 
									DIRECTION.BottomUp,
									PROGRESS.Continuing,
									FIXEDPOINT.No);
		return normalizedResult(tr.value.getType(), tr.value);
	}
	
	@Override
	public Result visitVisitGivenStrategy(GivenStrategy x) {
		
		IValue subject = x.getSubject().accept(this).getValue();
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
		return normalizedResult(subjectType, tr.value);
	}
	
	@Override
	public Result visitExpressionNonEquals(
			org.meta_environment.rascal.ast.Expression.NonEquals x) {
		Result left = x.getLhs().accept(this);
		Result right = x.getRhs().accept(this);
		
		if (!left.getType().comparable(right.getType())) {
			throw new UnexpectedTypeError(left.getType(), right.getType(), x);
		}
		
		return result(vf.bool(compare(left, right) != 0));
	}
	
	
	// TODO distribute over subclasses of Result
	public static int compare(Result left, Result right){
		// compare must use run-time types because it is complete for all types
		// even if statically two values have type 'value' but one is an int 1
		// and the other is Real 1.0 they must be equal.
		
		// TODO distribute this method over subclasses of Result,
		// IntegerResult to MapResult which implement equals and compare and add and
		// subtract, etc.

		widenArgs(left, right);
		Type leftType = left.getValueType();
		Type rightType = right.getValueType();
		
		if (leftType.isBoolType() && rightType.isBoolType()) {
			boolean lb = ((IBool) left.getValue()).getValue();
			boolean rb = ((IBool) right.getValue()).getValue();
			return (lb == rb) ? 0 : ((!lb && rb) ? -1 : 1);
		}
		if (left.getType().isIntegerType() && rightType.isIntegerType()) {
			return ((IInteger) left.getValue()).compare((IInteger) right.getValue());
		}
		if (leftType.isRealType() && rightType.isRealType()) {
			return ((IReal) left.getValue()).compare((IReal) right.getValue());
		}
		if (leftType.isStringType() && rightType.isStringType()) {
			return ((IString) left.getValue()).compare((IString) right.getValue());
		}
		if (leftType.isListType() && rightType.isListType()) {
			return compareList(((IList) left.getValue()).iterator(), ((IList) left.getValue()).length(),
					            ((IList) right.getValue()).iterator(), ((IList) right.getValue()).length());
		}
		if (leftType.isSetType() && rightType.isSetType()) {
			return compareSet((ISet) left.getValue(), (ISet) right.getValue());
		}
		if (leftType.isMapType() && rightType.isMapType()) {
			return compareMap((IMap) left.getValue(), (IMap) right.getValue());
		}
		if (leftType.isTupleType() && rightType.isTupleType()) {
			return compareList(((ITuple) left.getValue()).iterator(), ((ITuple) left.getValue()).arity(),
		            ((ITuple) right.getValue()).iterator(), ((ITuple) right.getValue()).arity());
		} 
		if (leftType.isRelationType() && rightType.isRelationType()) {
			return compareSet((ISet) left.getValue(), (ISet) right.getValue());
		}
		
		if (leftType.isNodeType() && rightType.isNodeType()) {
			return compareNode((INode) left.getValue(), (INode) right.getValue());
		}
		
		if (leftType.isAbstractDataType() && rightType.isAbstractDataType()) {
			return compareNode((INode) left.getValue(), (INode) right.getValue());
		}
		
		if(leftType.isSourceLocationType() && rightType.isSourceLocationType()){	
			return compareSourceLocation((ISourceLocation) left.getValue(), (ISourceLocation) right.getValue());
		}
			
		// VoidType
		// ValueType
		
		return leftType.toString().compareTo(rightType.toString());
	}
	
	private static int compareNode(INode left, INode right){
		String leftName = left.getName().toString();
		String rightName = right.getName().toString();
		int compare = leftName.compareTo(rightName);
		
		if(compare != 0){
			return compare;
		}
		return compareList(left.iterator(), left.arity(), right.iterator(), right.arity());
	}
	
	private static int compareSourceLocation(ISourceLocation leftSL, ISourceLocation rightSL){
		if(leftSL.equals(rightSL))
			return 0;
		if(leftSL.getURL().equals(rightSL.getURL())){
			int lBeginLine = leftSL.getBeginLine();
			int rBeginLine = rightSL.getBeginLine();
			
			int lEndLine = leftSL.getEndLine();
			int rEndLine = rightSL.getEndLine();
			
			int lBeginColumn = leftSL.getBeginColumn();
			int rBeginColumn = rightSL.getBeginColumn();
			
			int lEndColumn = leftSL.getEndColumn();
			int rEndColumn = rightSL.getEndColumn();
			
			if((lBeginLine > rBeginLine ||
				(lBeginLine == rBeginLine && lBeginColumn > rBeginColumn)) &&
				(lEndLine < rEndLine ||
						((lEndLine == rEndLine) && lEndColumn < rEndColumn))){
				return -1;	
			} else {
				return 1;
			}
		} else {
			return leftSL.getURL().toString().compareTo(rightSL.getURL().toString());
		}
	}
	
	private static int compareSet(ISet value1, ISet value2) {
		
		
		//System.err.println("compareSet: value1=" + value1 + ", value2=" + value2 + "isEqual=" + value1.isEqual(value2));
		if (value1.isEqual(value2)) {
			return 0;
		}
		else if (value1.isSubsetOf(value2)) {
			return -1;
		}
		else {
			return 1;
		}
	}
	
	private static int compareMap(IMap value1, IMap value2) {
		if (value1.isEqual(value2)) {
			return 0;
		}
		else if (value1.isSubMap(value2)) {
			return -1;
		}
		else {
			return 1;
		}
	}

	private static int compareList(Iterator<IValue> left, int leftLen, Iterator<IValue> right, int rightLen){
		
		if(leftLen == 0){
			return rightLen == 0 ? 0 : -1;
		}
		if(rightLen == 0){
			return 1;
		}
		int m = (leftLen > rightLen) ? rightLen : leftLen;  
		int compare = 0;
		
		for(int i = 0; i < m; i++){
			IValue leftVal = left.next();
			IValue rightVal = right.next();
			Result vl = new Result(leftVal.getType(), leftVal);
			Result vr = new Result(rightVal.getType(), rightVal);
			int c = compare(vl, vr);

			if (c < 0 || c > 0) {
				return c;
			}
		}
		
		if(compare == 0 && leftLen != rightLen){
			compare = leftLen < rightLen ? -1 : 1;
		}
			
		return compare;
	}
	
	@Override
	public Result visitExpressionLessThan(LessThan x) {
		Result left = x.getLhs().accept(this);
		Result right = x.getRhs().accept(this);
		
		return result(vf.bool(compare(left, right) < 0));
	}
	
	@Override
	public Result visitExpressionLessThanOrEq(LessThanOrEq x) {
		Result left = x.getLhs().accept(this);
		Result right = x.getRhs().accept(this);
		
		return result(vf.bool(compare(left, right) <= 0));
	}
	@Override
	public Result visitExpressionGreaterThan(GreaterThan x) {
		Result left = x.getLhs().accept(this);
		Result right = x.getRhs().accept(this);
		
		return result(vf.bool(compare(left, right) > 0));
	}
	
	@Override
	public Result visitExpressionGreaterThanOrEq(GreaterThanOrEq x) {
		Result left = x.getLhs().accept(this);
		Result right = x.getRhs().accept(this);
		
		return result(vf.bool(compare(left, right) >= 0));
	}
	
	@Override
	public Result visitExpressionIfThenElse(
			org.meta_environment.rascal.ast.Expression.IfThenElse x) {
		Result cval = x.getCondition().accept(this);
	
		if (cval.getType().isBoolType()) {
			if (cval.getValue().isEqual(vf.bool(true))) {
				return x.getThenExp().accept(this);
			}
		} else {
			throw new UnexpectedTypeError(tf.boolType(),cval.getType(), x);
		}
		return x.getElseExp().accept(this);
	}
	

	@Override
	public Result visitExpressionIfDefinedOtherwise(IfDefinedOtherwise x) {
		try {
			return x.getLhs().accept(this);
		} catch (UninitializedVariableError e){
			return x.getRhs().accept(this);
		}
		catch (org.meta_environment.rascal.interpreter.control_exceptions.Throw e) {
			// TODO For now we accept any Throw here, restrict to NoSuchKey and NoSuchAnno?
			return x.getRhs().accept(this);
		}

	}
	
	@Override
	public Result visitExpressionIsDefined(IsDefined x) {
		try {
			x.getArgument().accept(this); // wait for exception
			return result(tf.boolType(), vf.bool(true));
			
		} catch (org.meta_environment.rascal.interpreter.control_exceptions.Throw e) {
			// TODO For now we accept any Throw here, restrict to NoSuchKey and NoSuchAnno?
			return result(tf.boolType(), vf.bool(false));
		}
	}
	
	private boolean in(org.meta_environment.rascal.ast.Expression expression, org.meta_environment.rascal.ast.Expression expression2){
		Result left = expression.accept(this);
		Result right = expression2.accept(this);
		
		//List
		if(right.getType().isListType() &&
		    left.getType().isSubtypeOf(right.getType().getElementType())){
			IList lst = (IList) right.getValue();
			IValue val = left.getValue();
			for(int i = 0; i < lst.length(); i++){
				if(lst.get(i).isEqual(val))
					return true;
			}
			return false;
			
	    //Set
		} else if(right.getType().isSetType() && 
				   left.getType().isSubtypeOf(right.getType().getElementType())){
			return ((ISet) right.getValue()).contains(left.getValue());
		//Map
		} else if(right.getType().isMapType() && left.getType().isSubtypeOf(right.getType().getValueType())){
			return ((IMap) right.getValue()).containsValue(left.getValue());
			
		//Relation
		} else if(right.getType().isRelationType() && left.getType().isSubtypeOf(right.getType().getElementType())){
			return ((ISet) right.getValue()).contains(left.getValue());
		} else {
			throw new UnsupportedOperationError("in"
					, left.getType(), right.getType(), expression2);
		}
	}
	
	@Override
	public Result visitExpressionIn(In x) {
		return result(vf.bool(in(x.getLhs(), x.getRhs())));
	}
	
	@Override
	public Result visitExpressionNotIn(NotIn x) {
		return result(vf.bool(!in(x.getLhs(), x.getRhs())));
	}
	
	@Override
	public Result visitExpressionComposition(Composition x) {
		Result left = x.getLhs().accept(this);
		Result right = x.getRhs().accept(this);
	
		//Relation
		if(left.getType().isRelationType() && 
			right.getType().isRelationType()){
			Type leftrelType = left.getType(); 
			Type rightrelType = right.getType();
			int leftArity = leftrelType.getArity();
			int rightArity = rightrelType.getArity();

			
			if ((leftArity == 0 || leftArity == 2) && (rightArity == 0 || rightArity ==2 )) {
				Type resultType = leftrelType.compose(rightrelType);
				return result(resultType, ((IRelation) left.getValue())
						.compose((IRelation) right.getValue()));
			}
		}
		
		throw new UnsupportedOperationError("o"
				, left.getType(), right.getType(), x);
	}

	private Result closure(AbstractAST expression, boolean reflexive) {

		Result res = expression.accept(this);
		//Relation
		if (res.getType().isRelationType() && res.getType().getArity() < 3) {
			Type relType = res.getType();
			Type fieldType1 = relType.getFieldType(0);
			Type fieldType2 = relType.getFieldType(1);
			if (fieldType1.comparable(fieldType2)) {
				Type lub = fieldType1.lub(fieldType2);
				
				Type resultType = relType.hasFieldNames() ? tf.relType(lub, relType.getFieldName(0), lub, relType.getFieldName(1)) : tf.relType(lub,lub);
				return result(resultType, reflexive ? ((IRelation) res.getValue()).closureStar()
						: ((IRelation) res.getValue()).closure());
			}
		}
		
		throw new UnsupportedOperationError("*",
				res.getType(), expression);
	}
	
	@Override
	public Result visitExpressionTransitiveClosure(TransitiveClosure x) {
		return closure(x.getArgument(), false);
	}
	
	@Override
	public Result visitExpressionTransitiveReflexiveClosure(
			TransitiveReflexiveClosure x) {
		return closure(x.getArgument(), true);
	}
	
	// Comprehensions ----------------------------------------------------
	
	@Override
	public Result visitExpressionComprehension(Comprehension x) {
		push();
		try {
			return x.getComprehension().accept(this);	
		}
		finally {
			pop();
		}
	}
	
	
	/*
	@Override
	public Result visitGeneratorExpression(
			org.meta_environment.rascal.ast.Generator.Expression x) {
		return new GeneratorEvaluator(x, this).next();
	}
	*/
	
	@Override
	public Result visitExpressionValueProducer(
			org.meta_environment.rascal.ast.Expression.ValueProducer x) {
		return new GeneratorEvaluator(x, this);
	}
	
	@Override
	public Result visitExpressionValueProducerWithStrategy(
			ValueProducerWithStrategy x) {
		return new GeneratorEvaluator(x, this);
	}
	
	class GeneratorEvaluator extends Result {
		private boolean isValueProducer;
		private boolean firstTime = true;
		private org.meta_environment.rascal.ast.Expression expr;
		private MatchPattern pat;
		private org.meta_environment.rascal.ast.Expression patexpr;
		private Evaluator evaluator;
		private Iterator<?> iterator;

		GeneratorEvaluator(Expression g, Evaluator ev){
			make(g, ev);
		}
		
		void make(Expression vp, Evaluator ev){
			if(vp.isValueProducer() || vp.isValueProducerWithStrategy()){
				evaluator = ev;
				isValueProducer = true;
				
				pat = evalPattern(vp.getPattern());
				patexpr = vp.getExpression();
				Result r = patexpr.accept(ev);
				// List
				if(r.getType().isListType()){
					if(vp.hasStrategy()) {
						throw new UnsupportedOperationError(vp.toString(), r.getType(), vp);
					}
					iterator = ((IList) r.getValue()).iterator();
					
				// Set
				} else 	if(r.getType().isSetType()){
					if(vp.hasStrategy()) {
						throw new UnsupportedOperationError(vp.toString(), r.getType(), vp);
					}
					iterator = ((ISet) r.getValue()).iterator();
				
				// Map
				} else if(r.getType().isMapType()){
					if(vp.hasStrategy()) {
						throw new UnsupportedOperationError(vp.toString(), r.getType(), vp);
					}
					iterator = ((IMap) r.getValue()).iterator();
					
				// Node and ADT
				} else if(r.getType().isNodeType() || r.getType().isAbstractDataType()){
					boolean bottomup = true;
					if(vp.hasStrategy()){
						Strategy strat = vp.getStrategy();
	
						if(strat.isTopDown()){
							bottomup = false;
						} else if(strat.isBottomUp()){
								bottomup = true;
						} else {
							throw new UnsupportedOperationError(vp.toString(), r.getType(), vp);
						}
					}
					iterator = new INodeReader((INode) r.getValue(), bottomup);
				} else if(r.getType().isStringType()){
					if(vp.hasStrategy()) {
						throw new UnsupportedOperationError(vp.getStrategy().toString(), r.getType(), vp.getStrategy());
					}
					iterator = new SingleIValueIterator(r.getValue());
				} else {
					throw new ImplementationError("Unimplemented expression type " + r.getType() + " in generator");
				}
			} else {
				evaluator = ev;
				isValueProducer = false;
				expr = vp;
			}
		}
		
		@Override
		public Type getType(){
			return TypeFactory.getInstance().boolType();
		}
		
		@Override
		public Type getValueType(){
			return TypeFactory.getInstance().boolType();
		}
		
		@Override
		public boolean hasNext(){
			if(isValueProducer){
				return pat.hasNext() || iterator.hasNext();
			} else {
				return firstTime;
			}	
		}

		@Override
		public Result next(){
			if(isValueProducer){
				//System.err.println("getNext, trying pat " + pat);
				/*
				 * First, explore alternatives that remain to be matched by the current pattern
				 */
				while(pat.hasNext()){
					if(pat.next()){
						//System.err.println("return true");
						return new Result(this, true);
					}
				}
				
				/*
				 * Next, fetch a new data element (if any) and create a new pattern.
				 */
				
				while(iterator.hasNext()){
					IValue v = (IValue) iterator.next();
					//System.err.println("getNext, try next from value iterator: " + v);
					pat.initMatch(v, peek());
					while(pat.hasNext()){
						if(pat.next()){
							//System.err.println("return true");
							return new Result(this,true);						
						}	
					}
				}
				//System.err.println("return false");
				return normalizedResult(tf.boolType(), vf.bool(false));
			} else {
				if(firstTime){
					/* Evaluate expression only once */
					firstTime = false;
					Result v = expr.accept(evaluator);
					if(v.getType().isBoolType() && v.getValue() != null){
						// FIXME: if result is of type void, you get a null pointer here.
						if (v.getValue().isEqual(vf.bool(true))) {
						//if(v.isTrue()){
							return result(tf.boolType(), vf.bool(true));
						}
						return result(tf.boolType(), vf.bool(false));
					} else {
						throw new UnexpectedTypeError(tf.boolType(), v.getType(), expr);
					}
				} else {
					// TODO: why false here? Shouldn't we save the first-time eval result?
					return result(tf.boolType(), vf.bool(false));
				}
			}
		}

		@Override
		public void remove() {
			throw new ImplementationError("remove() not implemented for GeneratorEvaluator");
		}
	}
	
	
	/*
	 * ComprehensionWriter provides a uniform framework for writing elements
	 * to a list/set/map during the evaluation of a list/set/map comprehension.
	 */
	
	private abstract class ComprehensionWriter {
		protected Type elementType1;
		protected Type elementType2;
		protected Type resultType;
		protected org.meta_environment.rascal.ast.Expression resultExpr1;
		protected org.meta_environment.rascal.ast.Expression resultExpr2;
		protected IWriter writer;
		protected Evaluator ev;
		
		ComprehensionWriter(
				org.meta_environment.rascal.ast.Expression resultExpr1,
				org.meta_environment.rascal.ast.Expression resultExpr2, 
				Evaluator ev){
			this.ev = ev;
			this.resultExpr1 = resultExpr1;
			this.resultExpr2 = resultExpr2;
			this.writer = null;
		}
		
		public void check(Result r, Type t, String kind, org.meta_environment.rascal.ast.Expression expr){
			if(!r.getType().isSubtypeOf(t)){
				throw new UnexpectedTypeError(t, r.getType() ,
					                    expr);
			}
		}
		
		public abstract void append();
		
		public abstract Result done();
	}
	
	private class ListComprehensionWriter extends
			ComprehensionWriter {

		ListComprehensionWriter(
				org.meta_environment.rascal.ast.Expression resultExpr1,
				Evaluator ev) {
			super(resultExpr1, null, ev);
		}

		@Override
		public void append() {
			Result r1 = resultExpr1.accept(ev);
			if (writer == null) {
				elementType1 = r1.getType();
				resultType = tf.listType(elementType1);
				writer = resultType.writer(vf);
			}
			check(r1, elementType1, "list", resultExpr1);
			elementType1 = elementType1.lub(r1.getType());
			((IListWriter) writer).append(r1.getValue());
		}

		@Override
		public Result done() {
			return (writer == null) ? result(tf.listType(tf.voidType()), vf
					.list()) : result(tf.listType(elementType1), writer.done());
		}
	}
	
	private class SetComprehensionWriter extends
			ComprehensionWriter {

		SetComprehensionWriter(
				org.meta_environment.rascal.ast.Expression resultExpr1,
				Evaluator ev) {
			super(resultExpr1, null, ev);
		}

		@Override
		public void append() {
			Result r1 = resultExpr1.accept(ev);
			if (writer == null) {
				elementType1 = r1.getType();
				resultType = tf.setType(elementType1);
				writer = resultType.writer(vf);
			}
			check(r1, elementType1, "set", resultExpr1);
			elementType1 = elementType1.lub(r1.getType());
			((ISetWriter) writer).insert(r1.getValue());
		}

		@Override
		public Result done() {
			return (writer == null) ? result(tf.setType(tf.voidType()), vf
					.set()) : result(tf.setType(elementType1), writer.done());
		}
	}

	private class MapComprehensionWriter extends
			ComprehensionWriter {

		MapComprehensionWriter(
				org.meta_environment.rascal.ast.Expression resultExpr1,
				org.meta_environment.rascal.ast.Expression resultExpr2,
				Evaluator ev) {
			super(resultExpr1, resultExpr2, ev);
		}

		@Override
		public void append() {
			Result r1 = resultExpr1.accept(ev);
			Result r2 = resultExpr2.accept(ev);
			if (writer == null) {
				elementType1 = r1.getType();
				elementType2 = r2.getType();
				resultType = tf.mapType(elementType1, elementType2);
				writer = resultType.writer(vf);
			}
			check(r1, elementType1, "map", resultExpr1);
			check(r2, elementType2, "map", resultExpr2);
			((IMapWriter) writer).put(r1.getValue(), r2.getValue());
		}

		@Override
		public Result done() {
			return (writer == null) ? result(tf.mapType(tf.voidType(), tf
					.voidType()), vf.map(tf.voidType(), tf.voidType()))
					: result(tf.mapType(elementType1, elementType2), writer
							.done());
		}
	}
	
	
	/*
	 * The common comprehension evaluator
	 */
	
	private Result evalComprehension(java.util.List<Expression> generators, 
										  ComprehensionWriter w){
		int size = generators.size();
		GeneratorEvaluator[] gens = new GeneratorEvaluator[size];
		
		int i = 0;
		gens[0] = new GeneratorEvaluator(generators.get(0), this);
		while (i >= 0 && i < size){
			if (gens[i].hasNext() && gens[i].next().isTrue()) {
				if(i == size - 1){
					w.append();
				} 
				else {
					i++;
					gens[i] = new GeneratorEvaluator(generators.get(i), this);
				}
			} else {
				i--;
			}
		}
		return w.done();
	}
	
	@Override
	public Result visitComprehensionList(org.meta_environment.rascal.ast.Comprehension.List x) {
		return evalComprehension(
				x.getGenerators(),
				new ListComprehensionWriter(x.getResult(), this));
	}
	
	@Override
	public Result visitComprehensionSet(
			org.meta_environment.rascal.ast.Comprehension.Set x) {
		return evalComprehension(
				x.getGenerators(),
				new SetComprehensionWriter(x.getResult(), this));
	}
	
	@Override
	public Result visitComprehensionMap(
			org.meta_environment.rascal.ast.Comprehension.Map x) {
		return evalComprehension(
				x.getGenerators(),
				new MapComprehensionWriter(x.getFrom(), x.getTo(), this));
	}

	@Override
	public Result visitStatementFor(For x) {
		Statement body = x.getBody();
		java.util.List<Expression> generators = x.getGenerators();
		int size = generators.size();
		GeneratorEvaluator[] gens = new GeneratorEvaluator[size];
		Result result = result();
		
		int i = 0;
		gens[0] = new GeneratorEvaluator(generators.get(0), this);
		while(i >= 0 && i < size){		
			if(gens[i].hasNext() && gens[i].next().isTrue()){
				if(i == size - 1){
					result = body.accept(this);
				} else {
					i++;
					gens[i] = new GeneratorEvaluator(generators.get(i), this);
				}
			} else {
				i--;
			}
		}
		return result;
	}
	
	@Override
	public Result visitExpressionAny(Any x) {
		java.util.List<Expression> generators = x.getGenerators();
		int size = generators.size();
		GeneratorEvaluator[] gens = new GeneratorEvaluator[size];

		int i = 0;
		gens[0] = new GeneratorEvaluator(generators.get(0), this);
		while (i >= 0 && i < size) {
			if (gens[i].hasNext() && gens[i].next().isTrue()) {
				if (i == size - 1) {
					return result(vf.bool(true));
				} else {
					i++;
					gens[i] = new GeneratorEvaluator(generators.get(i), this);
				}
			} else {
				i--;
			}
		}
		return result(vf.bool(false));
	}
	
	@Override
	public Result visitExpressionAll(All x) {
		java.util.List<Expression> producers = x.getGenerators();
		int size = producers.size();
		GeneratorEvaluator[] gens = new GeneratorEvaluator[size];

		int i = 0;
		gens[0] = new GeneratorEvaluator(producers.get(0), this);
		while (i >= 0 && i < size) {
			if (gens[i].hasNext()) {
				if (!gens[i].next().isTrue()) {
					return result(vf.bool(false));
				}
				if (i < size - 1) {
					i++;
					gens[i] = new GeneratorEvaluator(producers.get(i), this);
				}
			} else {
				i--;
			}
		}
		return result(vf.bool(true));
	}
	
	// ------------ solve -----------------------------------------
	
	@Override
	public Result visitStatementSolve(Solve x) {
		java.util.ArrayList<org.meta_environment.rascal.ast.Variable> vars = new java.util.ArrayList<org.meta_environment.rascal.ast.Variable>();
		
		for(Declarator d : x.getDeclarations()){
			for(org.meta_environment.rascal.ast.Variable v : d.getVariables()){
				vars.add(v);
			}
			d.accept(this);
		}
		IValue currentValue[] = new IValue[vars.size()];
		for(int i = 0; i < vars.size(); i++){
			org.meta_environment.rascal.ast.Variable v = vars.get(i);
			currentValue[i] = peek().getVariable(v, Names.name(v.getName())).getValue();
		}
		
		Statement body = x.getBody();
		
		int max = 1000;
		
		Bound bound= x.getBound();
		if(bound.isDefault()){
			Result res = bound.getExpression().accept(this);
			if(!res.getType().isIntegerType()){
				throw new UnexpectedTypeError(tf.integerType(),res.getType(), x);
			}
			max = ((IInteger)res.getValue()).intValue();
			if(max <= 0){
				throw RuntimeExceptionFactory.indexOutOfBounds((IInteger) res.getValue());
			}
		}
		
		Result bodyResult = null;
		
		boolean change = true;
		int iterations = 0;
		
		while (change && iterations < max){
			change = false;
			iterations++;
			bodyResult = body.accept(this);
			for(int i = 0; i < vars.size(); i++){
				org.meta_environment.rascal.ast.Variable var = vars.get(i);
				Result v = peek().getVariable(var, Names.name(var.getName()));
				if(currentValue[i] == null || !v.getValue().isEqual(currentValue[i])){
					change = true;
					currentValue[i] = v.getValue();
				}
			}
		}
		return bodyResult;
	}
}
