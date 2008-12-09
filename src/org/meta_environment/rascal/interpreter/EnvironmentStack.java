package org.meta_environment.rascal.interpreter;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.eclipse.imp.pdb.facts.type.ParameterType;
import org.eclipse.imp.pdb.facts.type.TupleType;
import org.eclipse.imp.pdb.facts.type.Type;
import org.meta_environment.rascal.ast.FunctionDeclaration;
import org.meta_environment.rascal.ast.Rule;

/**
 * An environment that implements the scoping rules of Rascal.
 * 
 * @author jurgenv
 *
 */
public class EnvironmentStack extends Environment {
	protected final Stack<Environment> stack = new Stack<Environment>();
    
	public EnvironmentStack(TypeEvaluator te) {
		super(te);
	}
	
	public void push() {
		stack.push(new Environment(types));
	}
	
	public void push(Environment e) {
		stack.push(e);
	}

	protected ModuleEnvironment getGlobalEnvironment() {
		return (ModuleEnvironment) stack.get(0);
	}
	
	public void pop() {
		stack.pop();
	}
	
	public void pushModule(String name) {
		stack.push(getGlobalEnvironment().getModule(name));
	}
	
	@Override
	protected FunctionDeclaration getFunction(String name, TupleType actuals) {
		Environment env =  getFunctionDefiningEnvironment(name, actuals);
		
		if (env == this) {
			return super.getFunction(name, actuals);
		}
		else {
			return env.getFunction(name, actuals);
		}
	}

	@Override
	protected EvalResult getVariable(String name) {
		Environment env = getVariableDefiningEnvironment(name);
		
		if (env == this) {
			return super.getVariable(name);
		}
		else {
			return env.getVariable(name);
		}
	}
	
	@Override
	protected void storeFunction(String name, FunctionDeclaration function) {
		TupleType formals = (TupleType) function.getSignature().accept(types);
		
		Environment env = getFunctionDefiningEnvironment(name, formals);
		
		if (env == this) {
			super.storeFunction(name, function);
		}
		else {
			env.storeFunction(name, function);
		}
	}

	@Override
	protected void storeVariable(String name, EvalResult value) {
		Environment env = getVariableDefiningEnvironment(name);
		
		if (env == this) {
			super.storeVariable(name, value);
		}
		else {
			env.storeVariable(name, value);
		}
	}
	
	protected Environment getFunctionDefiningEnvironment(String name, TupleType formals) {
		int i;
		
		for (i = stack.size() - 1; i >= 0; i--) {
			Environment environment = stack.get(i);
			
			if (environment.isModuleEnvironment()
					|| environment.getFunction(name, formals) != null) {
				return environment;
			}
		}
		
		return getGlobalEnvironment();
	}
	
	protected Environment getVariableDefiningEnvironment(String name) {
		int i;
		
		for (i = stack.size() - 1; i >= 0; i--) {
			Environment environment = stack.get(i);
			
            if (environment.isModuleEnvironment() || environment.getVariable(name) != null) {
            	return environment;
            }
		}
		
		return getGlobalEnvironment();
	}

	protected Environment getModuleEnvironment() {
		int i;
		for (i = stack.size() - 1; i >= 0; i--) {
			Environment environment = stack.get(i);
			if (environment.isModuleEnvironment()) {
				return environment;
			}
		}
		
		throw new RascalBug("There should be a module environment");
	}
	
	public void storeRule(Type forType, Rule rule) {
		getGlobalEnvironment().storeRule(forType, rule);
	}
	
    public List<Rule> getRules(Type forType) {
    	return getGlobalEnvironment().getRules(forType);
    } 
	
	public void addModule(String name) {
		getGlobalEnvironment().addModule(name);
	}

	protected ModuleEnvironment getModule(String name) {
		return getGlobalEnvironment().getModule(name);
	}
	
	protected EvalResult getModuleVariable(String module, String variable) {
		ModuleEnvironment env = getGlobalEnvironment().getModule(module);
		return env.getVariable(variable);
	}
	
	@Override
	public Map<ParameterType, Type> getTypes() {
		Map<ParameterType,Type> types = new HashMap<ParameterType,Type>();
		
		for (int i = 0; i < stack.size(); i++) {
			Environment environment = stack.get(i);
			if (environment == this) {
				types.putAll(super.getTypes());
			}
			else {
			  types.putAll(environment.getTypes());
			}
		}
		
		// result can not be given to a match
		return Collections.unmodifiableMap(types);
	}

	@Override
	protected void storeType(ParameterType par, Type type) {
		stack.peek().storeType(par, type);
	}
	
	@Override
	public void storeTypes(Map<ParameterType, Type> bindings) {
		stack.peek().storeTypes(bindings);
	}
}
