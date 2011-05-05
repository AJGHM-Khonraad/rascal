/*******************************************************************************
 * Copyright (c) 2009-2011 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   * Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI
 *   * Tijs van der Storm - Tijs.van.der.Storm@cwi.nl
 *   * Emilie Balland - (CWI)
 *   * Paul Klint - Paul.Klint@cwi.nl - CWI
 *   * Mark Hills - Mark.Hills@cwi.nl (CWI)
 *   * Arnold Lankamp - Arnold.Lankamp@cwi.nl
*******************************************************************************/
package org.rascalmpl.interpreter.result;

import static org.rascalmpl.interpreter.result.ResultFactory.makeResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import org.eclipse.imp.pdb.facts.INode;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;
import org.rascalmpl.ast.AbstractAST;
import org.rascalmpl.ast.Expression;
import org.rascalmpl.ast.FunctionDeclaration;
import org.rascalmpl.ast.FunctionModifier;
import org.rascalmpl.ast.Parameters;
import org.rascalmpl.ast.Statement;
import org.rascalmpl.ast.Expression.Closure;
import org.rascalmpl.ast.Expression.VoidClosure;
import org.rascalmpl.ast.Type.Structured;
import org.rascalmpl.interpreter.Accumulator;
import org.rascalmpl.interpreter.Evaluator;
import org.rascalmpl.interpreter.IEvaluatorContext;
import org.rascalmpl.interpreter.PatternEvaluator;
import org.rascalmpl.interpreter.asserts.ImplementationError;
import org.rascalmpl.interpreter.control_exceptions.InterruptException;
import org.rascalmpl.interpreter.control_exceptions.MatchFailed;
import org.rascalmpl.interpreter.control_exceptions.Return;
import org.rascalmpl.interpreter.env.Environment;
import org.rascalmpl.interpreter.matching.IMatchingResult;
import org.rascalmpl.interpreter.staticErrors.MissingReturnError;
import org.rascalmpl.interpreter.staticErrors.UnexpectedTypeError;
import org.rascalmpl.interpreter.staticErrors.UnsupportedPatternError;
import org.rascalmpl.interpreter.types.FunctionType;
import org.rascalmpl.interpreter.utils.Names;
import org.rascalmpl.parser.ASTBuilder;

public class RascalFunction extends NamedFunction {
	private final List<Statement> body;
	private final boolean isVoidFunction;
	private final Stack<Accumulator> accumulators;
	private final IMatchingResult[] matchers;
	private final boolean isDefault;

	public RascalFunction(Evaluator eval, FunctionDeclaration.Default func, boolean varargs, Environment env,
				Stack<Accumulator> accumulators) {
		this(func, eval,
				(FunctionType) func.getSignature().typeOf(env),
				varargs, isDefault(func),
				func.getBody().getStatements(), env, accumulators);
		this.name = Names.name(func.getSignature().getName());
	}
	
	public RascalFunction(Evaluator eval, FunctionDeclaration.Expression func, boolean varargs, Environment env,
			Stack<Accumulator> accumulators) {
	this(func, eval,
			(FunctionType) func.getSignature().typeOf(env),
			varargs, isDefault(func),
			Arrays.asList(new Statement[] { eval.getBuilder().makeStat("Return", func.getTree(), eval.getBuilder().makeStat("Expression", func.getTree(), func.getExpression()))}),
			env, accumulators);
	this.name = Names.name(func.getSignature().getName());
   }
	
	

	@SuppressWarnings("unchecked")
	public RascalFunction(AbstractAST ast, Evaluator eval, FunctionType functionType,
			boolean varargs, boolean isDefault, List<Statement> body, Environment env, Stack<Accumulator> accumulators) {
		super(ast, eval, functionType, null, varargs, env);
		this.body = body;
		this.isDefault = isDefault;
		this.isVoidFunction = this.functionType.getReturnType().isSubtypeOf(TF.voidType());
		this.accumulators = (Stack<Accumulator>) accumulators.clone();
		this.matchers = prepareFormals(eval);
	}
	
	public boolean isAnonymous() {
		return getName() == null;
	}

	private static boolean isDefault(FunctionDeclaration func) {
		List<FunctionModifier> mods = func.getSignature().getModifiers().getModifiers();
		for (FunctionModifier m : mods) {
			if (m.isDefault()) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean isDefault() {
		return isDefault;
	}
	
	@Override
	public Result<IValue> call(Type[] actualTypes, IValue[] actuals) {
		synchronized (ctx.getEvaluator()) {
			Environment old = ctx.getCurrentEnvt();
			AbstractAST oldAST = ctx.getCurrentAST();
			Stack<Accumulator> oldAccus = ctx.getAccumulators();

			try {
				String label = isAnonymous() ? "Anonymous Function" : name;
				Environment environment = new Environment(declarationEnvironment, ctx.getCurrentEnvt(), ctx.getCurrentAST().getLocation(), ast.getLocation(), label);
				ctx.setCurrentEnvt(environment);
				ctx.setAccumulators(accumulators);
				ctx.pushEnv();

				if (hasVarArgs) {
					actuals = computeVarArgsActuals(actuals, getFormals());
				}

				assignFormals(actuals, ctx.getCurrentEnvt());

				if (callTracing) {
					printStartTrace();
				}

				for (Statement stat: body) {
					eval.setCurrentAST(stat);
					stat.interpret(eval);
				}

				if (callTracing) {
					printEndTrace();
				}

				if(!isVoidFunction){
					throw new MissingReturnError(ast);
				}

				return makeResult(TF.voidType(), null, eval);
			}
			catch (Return e) {
				Result<IValue> result = e.getValue();

				Type returnType = getReturnType();
				Type instantiatedReturnType = returnType.instantiate(ctx.getCurrentEnvt().getTypeBindings());

				if(!result.getType().isSubtypeOf(instantiatedReturnType)){
					throw new UnexpectedTypeError(instantiatedReturnType, result.getType(), e.getLocation());
				}

				if (!returnType.isVoidType() && result.getType().isVoidType()) {
					throw new UnexpectedTypeError(returnType, result.getType(), e.getLocation());
				}

				return makeResult(instantiatedReturnType, result.getValue(), eval);
			} 
			finally {
				if (callTracing) {
					printFinally();
				}
				ctx.setCurrentEnvt(old);
				ctx.setAccumulators(oldAccus);
				ctx.setCurrentAST(oldAST);
			}
		}
	}
	
	private void assignFormals(IValue[] actuals, Environment env) {
		// we assume here the list of formals is just as long as the list of actuals
		int size = actuals.length;
		Environment[] olds = new Environment[size];
		int i = 0;
		
		if (size == 0) {
			return;
		}
		
		matchers[0].initMatch(makeResult(actuals[0].getType(), actuals[0], ctx));
		olds[0] = ctx.getCurrentEnvt();
		ctx.pushEnv();

		// pattern matching requires backtracking due to list, set and map matching and
		// non-linear use of variables between formal parameters of a function...
		
		while (i >= 0 && i < size) {
			if (ctx.isInterrupted()) {
				throw new InterruptException(ctx.getStackTrace());
			}
			if (matchers[i].hasNext() && matchers[i].next()) {
				if (i == size - 1) {
					// formals are now bound by side effect of the pattern matcher
					return;
				} else {
					i++;
					matchers[i].initMatch(makeResult(actuals[i].getType(), actuals[i], ctx));
					olds[i] = ctx.getCurrentEnvt();
					ctx.pushEnv();
				}
			} else {
				ctx.unwind(olds[i]);
				i--;
				ctx.pushEnv();
			}
		}
		
		throw new MatchFailed();
	}

	private IMatchingResult[] prepareFormals(IEvaluatorContext ctx) {
		List<Expression> formals;
		Parameters params;
		ASTBuilder af = ctx.getBuilder();
		
		if (ast instanceof FunctionDeclaration) {
			params = ((FunctionDeclaration) ast).getSignature().getParameters();
		}
		else if (ast instanceof Closure) {
			params = ((Closure) ast).getParameters();
		}
		else if (ast instanceof VoidClosure) {
			params = ((VoidClosure) ast).getParameters();
		}
		else {
			throw new ImplementationError("Unexpected kind of Rascal function: " + ast);
		}
		
		formals = params.getFormals().getFormals();
		if (params.isVarArgs() && formals.size() > 0) {
			// deal with varags, change the last argument to a list if its not a pattern
			Expression last = formals.get(formals.size() - 1);
			if (last.isTypedVariable()) {
				org.rascalmpl.ast.Type oldType = last.getType();
				INode origin = last.getTree();
				Structured newType = af.make("Type","Structured", origin, af.make("StructuredType",origin, af.make("BasicType","List", origin), Arrays.asList(af.make("TypeArg","Default", origin,oldType))));
				last = af.make("Expression","TypedVariable",origin, newType, last.getName());
				formals = replaceLast(formals, last);
			}
			else if (last.isQualifiedName()) {
				INode origin = last.getTree();
				org.rascalmpl.ast.Type newType = af.make("Type","Structured",origin, af.make("StructuredType",origin, af.make("BasicType","List", origin), Arrays.asList(af.make("TypeArg",origin, af.make("Type","Basic", origin, af.make("BasicType","Value", origin))))));
				last = af.makeExp("TypedVariable", origin, newType, Names.lastName(last.getQualifiedName()));
				formals = replaceLast(formals, last);
			}
			else {
				throw new UnsupportedPatternError("...", last);
			}
		}
		
		int size = formals.size();
		IMatchingResult[] matchers = new IMatchingResult[size];
		PatternEvaluator pe = new PatternEvaluator(ctx);
		
		for (int i = 0; i < size; i++) {
			matchers[i] = formals.get(i).buildMatcher(pe);
		}
		
		return matchers;
	}

	private List<Expression> replaceLast(List<Expression> formals,
			Expression last) {
		List<Expression> tmp = new ArrayList<Expression>(formals.size());
		tmp.addAll(formals);
		tmp.set(formals.size() - 1, last);
		formals = tmp;
		return formals;
	}
	
	@Override
	public <U extends IValue, V extends IValue> Result<U> equals(Result<V> that) {
		return that.equalToRascalFunction(this);
	}
	
	@Override
	public <U extends IValue> Result<U> equalToRascalFunction(RascalFunction that) {
		return ResultFactory.bool((this == that), ctx);
	}
}
