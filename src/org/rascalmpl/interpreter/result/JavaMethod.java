/*******************************************************************************
 * Copyright (c) 2009-2013 CWI
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
 *   * Anya Helene Bagge - anya@ii.uib.no (UiB)
*******************************************************************************/
package org.rascalmpl.interpreter.result;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;
import org.rascalmpl.ast.FunctionDeclaration;
import org.rascalmpl.ast.Tag;
import org.rascalmpl.interpreter.Configuration;
import org.rascalmpl.interpreter.IEvaluator;
import org.rascalmpl.interpreter.StackTrace;
import org.rascalmpl.interpreter.asserts.ImplementationError;
import org.rascalmpl.interpreter.control_exceptions.Throw;
import org.rascalmpl.interpreter.env.Environment;
import org.rascalmpl.interpreter.env.KeywordParameter;
import org.rascalmpl.interpreter.staticErrors.NoKeywordParameters;
import org.rascalmpl.interpreter.staticErrors.StaticError;
import org.rascalmpl.interpreter.staticErrors.UndeclaredKeywordParameter;
import org.rascalmpl.interpreter.staticErrors.UnexpectedKeywordArgumentType;
import org.rascalmpl.interpreter.types.FunctionType;
import org.rascalmpl.interpreter.utils.JavaBridge;
import org.rascalmpl.interpreter.utils.Names;
import org.rascalmpl.interpreter.utils.RuntimeExceptionFactory;

public class JavaMethod extends NamedFunction {
	private final Object instance;
	private final Method method;
	private final boolean hasReflectiveAccess;
	
	public JavaMethod(IEvaluator<Result<IValue>> eval, FunctionDeclaration func, boolean varargs, Environment env, JavaBridge javaBridge){
		super(func, eval, (FunctionType) func.getSignature().typeOf(env), Names.name(func.getSignature().getName()), varargs, null, env);
		
		this.hasReflectiveAccess = hasReflectiveAccess(func);
		this.instance = javaBridge.getJavaClassInstance(func);
		this.method = javaBridge.lookupJavaMethod(eval, func, env, hasReflectiveAccess);
	}
	
	@Override
	public boolean isStatic() {
		return true;
	}
	
	@Override
	public boolean isDefault() {
		return false;
	}
	
	private boolean hasReflectiveAccess(FunctionDeclaration func) {
		for (Tag tag : func.getTags().getTags()) {
			if (Names.name(tag.getName()).equals("reflect")) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Result<IValue> call(Type[] actualTypes, IValue[] actuals, Map<String, Result<IValue>> keyArgValues) {
		Type actualTypesTuple;
		Type formals = getFormals();
		Object[] oActuals;

		if (hasVarArgs) {
			oActuals = computeVarArgsActuals(actuals, formals);
		}
		else {
			oActuals = actuals;
		}
		oActuals = addKeywordActuals(oActuals, formals, keyArgValues);

		if (hasReflectiveAccess) {
			oActuals = addCtxActual(oActuals);
		}

		if (callTracing) {
			printStartTrace();
		}

		Environment old = ctx.getCurrentEnvt();

		try {
			ctx.pushEnv(getName());

			if (hasVarArgs) {
				actualTypesTuple = computeVarArgsActualTypes(actualTypes, formals);
			}
			else {
				actualTypesTuple = TF.tupleType(actualTypes);
			}

			Environment env = ctx.getCurrentEnvt();
			bindTypeParameters(actualTypesTuple, formals, env); 
			
			IValue result = invoke(oActuals);
			
			Type resultType = getReturnType().instantiate(env.getTypeBindings());
			
			return ResultFactory.makeResult(resultType, result, eval);
		}
		catch (Throw t) {
			throw t;
		}
		finally {
			if (callTracing) {
				printEndTrace();
			}
			ctx.unwind(old);
		}
	}
	
	@Override
	public Result<IValue> call(Type[] actualTypes, IValue[] actuals, Map<String, Result<IValue>> keyArgValues, Result<IValue> self, List<String> selfParams, List<Result<IValue>> selfParamBounds) {
		return call(actualTypes, actuals, keyArgValues);
	}
	
	private Object[] addCtxActual(Object[] oActuals) {
		Object[] newActuals = new Object[oActuals.length + 1];
		System.arraycopy(oActuals, 0, newActuals, 0, oActuals.length);
		newActuals[oActuals.length] = ctx;
		return newActuals;
	}
	
	protected Object[] addKeywordActuals(Object[] oldActuals, Type formals, Map<String, Result<IValue>> keyArgValues){
		if(keywordParameterDefaults == null){
			if(keyArgValues != null){
				throw new NoKeywordParameters(getName(), ctx.getCurrentAST());
			}
			return oldActuals;
		}
		Object[] newActuals = new Object[formals.getArity() + keywordParameterDefaults.size()];
		System.arraycopy(oldActuals, 0, newActuals, 0, oldActuals.length);
		int posArity = formals.getArity();
		
		if(keyArgValues == null){
			if(keywordParameterDefaults != null){
				for(int i = 0; i < keywordParameterDefaults.size(); i++){
					KeywordParameter kw = keywordParameterDefaults.get(i);
					Result<IValue> r = kw.getDefault();
					newActuals[posArity + i] = r.getValue();
				}
			}
			return newActuals;
		}
		if(keywordParameterDefaults == null)
			throw new NoKeywordParameters(getName(), ctx.getCurrentAST());
		
		int nBoundKeywordArgs = 0;
		for(int i = 0; i < keywordParameterDefaults.size(); i++){
			KeywordParameter kw = keywordParameterDefaults.get(i);
			String kwparam = kw.getName();
			if(keyArgValues.containsKey(kwparam)){
				nBoundKeywordArgs++;
				Result<IValue> r = keyArgValues.get(kwparam);
				if(!r.getType().isSubtypeOf(keywordParameterTypes[i])){
					throw new UnexpectedKeywordArgumentType(kwparam, keywordParameterTypes[i], r.getType(), ctx.getCurrentAST());
				}
				newActuals[posArity + i] = r.getValue();
			} else {
				Result<IValue> r = kw.getDefault();
				newActuals[posArity + i] = r.getValue();
			}
		}
		if(nBoundKeywordArgs != keyArgValues.size()){
			main:
			for(String kwparam : keyArgValues.keySet())
				for(KeywordParameter kw : keywordParameterDefaults){
					if(kwparam.equals(kw.getName()))
							continue main;
					throw new UndeclaredKeywordParameter(getName(), kwparam, ctx.getCurrentAST());
				}
		}
		return newActuals;
	}
	

	public IValue invoke(Object[] oActuals) {
		Configuration.printErrors();
		try {
			return (IValue) method.invoke(instance, oActuals);
		}
		catch (InvocationTargetException e) {
			Throwable targetException = e.getTargetException();
			
			if (targetException instanceof Throw) {
				Throw th = (Throw) targetException;
				
				StackTrace trace = new StackTrace();
				trace.addAll(th.getTrace());
				
				ISourceLocation loc = th.getLocation();
				if (loc == null) {
				  loc = getAst().getLocation();
				}
				trace.add(loc, null);

				th.setLocation(loc);
				trace.addAll(eval.getStackTrace());
				th.setTrace(trace.freeze());
				throw th;
			}
			else if (targetException instanceof StaticError) {
				throw (StaticError) targetException;
			}
			else if (targetException instanceof ImplementationError) {
			  throw (ImplementationError) targetException;
			}

			if(Configuration.printErrors()){
				targetException.printStackTrace();
			}
			
			throw RuntimeExceptionFactory.javaException(e.getTargetException(), getAst(), eval.getStackTrace());
		}
		catch (Throwable e) {
		  if(Configuration.printErrors()){
        e.printStackTrace();
      }
		  
		  throw RuntimeExceptionFactory.javaException(e, getAst(), eval.getStackTrace());
		}
	}
}
