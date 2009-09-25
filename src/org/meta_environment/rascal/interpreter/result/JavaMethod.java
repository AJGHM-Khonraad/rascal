package org.meta_environment.rascal.interpreter.result;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;
import org.meta_environment.rascal.ast.FunctionDeclaration;
import org.meta_environment.rascal.ast.Tag;
import org.meta_environment.rascal.interpreter.Evaluator;
import org.meta_environment.rascal.interpreter.asserts.ImplementationError;
import org.meta_environment.rascal.interpreter.control_exceptions.Throw;
import org.meta_environment.rascal.interpreter.env.Environment;
import org.meta_environment.rascal.interpreter.types.FunctionType;
import org.meta_environment.rascal.interpreter.utils.JavaBridge;
import org.meta_environment.rascal.interpreter.utils.Names;


public class JavaMethod extends NamedFunction {
	private final Method method;
	private final FunctionDeclaration func;
	private final boolean hasReflectiveAccess;
	
	public JavaMethod(Evaluator eval, FunctionDeclaration func, boolean varargs, Environment env, JavaBridge javaBridge) {
		super(func, eval,
				(FunctionType) TE.eval(func.getSignature(),env), 
				Names.name(func.getSignature().getName()),
				varargs, env);
		this.hasReflectiveAccess = hasReflectiveAccess(func);
		this.method = javaBridge.lookupJavaMethod(eval, func, env, hasReflectiveAccess);
		this.func = func;
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
	public Result<IValue> call(Type[] actualTypes, IValue[] actuals) {
		Type actualTypesTuple;
		Type formals = getFormals();
		Object[] oActuals;
		
		if (hasVarArgs) {
			oActuals = computeVarArgsActuals(actuals, formals);
		}
		else {
			oActuals = actuals;
		}
		
		if (hasReflectiveAccess) {
			oActuals = addCtxActual(oActuals);
		}
		
		if (callTracing) {
			printStartTrace();
		}

		Environment old = ctx.getCurrentEnvt();
		
		try {
			ctx.pushEnv();
	
			IValue result = invoke(oActuals);

			if (hasVarArgs) {
				actualTypesTuple = computeVarArgsActualTypes(actualTypes, formals);
			}
			else {
				actualTypesTuple = TF.tupleType(actualTypes);
			}

			Environment env = ctx.getCurrentEnvt();
			bindTypeParameters(actualTypesTuple, formals, env); 
			Type resultType = getReturnType().instantiate(env.getStore(), env.getTypeBindings());
			return ResultFactory.makeResult(resultType, result, eval);
		}
		catch (Throw t) {
			t.setTrace(ctx.getStackTrace());
			t.setLocation(ctx.getCurrentAST().getLocation());
			throw t;
		}
		finally {
			if (callTracing) {
				printEndTrace();
			}
			ctx.unwind(old);
		}
	}
	
	private Object[] addCtxActual(Object[] oActuals) {
		Object[] newActuals = new Object[oActuals.length + 1];
		System.arraycopy(oActuals, 0, newActuals, 0, oActuals.length);
		newActuals[oActuals.length] = ctx;
		return newActuals;
	}

	public IValue invoke(Object[] oActuals) {
		try {
			return (IValue) method.invoke(null, oActuals);
		} catch (SecurityException e) {
			throw new ImplementationError("Unexpected security exception", e);
		} catch (IllegalArgumentException e) {
			throw new ImplementationError("An illegal argument was generated for a generated method", e);
		} catch (IllegalAccessException e) {
			throw new ImplementationError("Unexpected illegal access exception", e);
		} catch (InvocationTargetException e) {
			Throwable targetException = e.getTargetException();
			
			if (targetException instanceof Throw) {
				Throw th = (Throw) targetException;
				((Throw) targetException).setLocation(eval.getCurrentAST().getLocation());
				((Throw) targetException).setTrace(eval.getStackTrace());
				throw th;
			}
			
			e.printStackTrace();
			String msg = targetException.getMessage() != null ? targetException.getMessage() : "Exception in Java code";
			throw org.meta_environment.rascal.interpreter.utils.RuntimeExceptionFactory.javaException(msg, eval.getCurrentAST(), eval.getStackTrace());
		}
	}
	
	@Override
	public String toString() {
		return func.toString();
	}
	
}
