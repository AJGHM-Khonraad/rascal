package org.meta_environment.rascal.interpreter.env;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.imp.pdb.facts.type.TupleType;
import org.meta_environment.rascal.ast.FunctionDeclaration;
import org.meta_environment.rascal.ast.Visibility;
import org.meta_environment.rascal.interpreter.EvalResult;
import org.meta_environment.rascal.interpreter.RascalTypeError;

/**
 * A module environment represents a module object (i.e. a running module).
 * It manages imported modules and visibility of the
 * functions and variables it declares. 
 * 
 * TODO: add management of locally declared types and constructors
 * 
 */
public class ModuleEnvironment extends Environment {
	private final String name;
	protected final Set<String> importedModules;
	protected final Map<FunctionDeclaration, Visibility> functionVisibility;
	protected final Map<String, Visibility> variableVisibility;
	
	public ModuleEnvironment(String name) {
		this.name = name;
		this.importedModules = new HashSet<String>();
		this.functionVisibility = new HashMap<FunctionDeclaration, Visibility>();
		this.variableVisibility = new HashMap<String, Visibility>();
	}
	
	public void setFunctionVisibility(FunctionDeclaration decl, Visibility vis) {
		functionVisibility.put(decl, vis);
	}
	
	public void setVariableVisibility(String var, Visibility vis) {
		variableVisibility.put(var, vis);
	}
	
	public boolean isModuleEnvironment() {
		return true;
	}
	
	public void addImport(String name) {
		importedModules.add(name);
	}
	
	public Set<String> getImports() {
		return importedModules;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public EvalResult getVariable(String name) {
		EvalResult result = super.getVariable(name);
		
		if (result == null) {
			List<EvalResult> results = new LinkedList<EvalResult>();
			for (String i : getImports()) {
				// imports are not transitive!
				result = GlobalEnvironment.getInstance().getModule(i).getLocalVariable(name);
				
				if (result != null) {
					results.add(result);
				}
			}
			
			if (results.size() == 1) {
				return results.get(0);
			}
			else if (results.size() == 0) {
				throw new RascalTypeError("No such variable " + name);
			}
			else {
				throw new RascalTypeError("Variable " + name + " is ambiguous, please qualify");
			}
		}
		
		return result;
	}
	
	@Override
	public FunctionDeclaration getFunction(String name, TupleType types) {
		FunctionDeclaration result = super.getFunction(name, types);
		
		if (result == null) {
			List<FunctionDeclaration> results = new LinkedList<FunctionDeclaration>();
			for (String i : getImports()) {
				// imports are not transitive!
				result = GlobalEnvironment.getInstance().getModule(i).getLocalPublicFunction(name, types);
				
				if (result != null) {
					results.add(result);
				}
			}
			
			if (results.size() == 1) {
				return results.get(0);
			}
			else if (results.size() == 0) {
				return null;
			}
			else {
				throw new RascalTypeError("Function " + name + " is ambiguous, please qualify");
			}
		}
		
		return result;
	}
	
	public FunctionDeclaration getLocalFunction(String name, TupleType types) {
		return super.getFunction(name, types);
	}
	
	public FunctionDeclaration getLocalPublicFunction(String name, TupleType types) {
		FunctionDeclaration decl = getLocalFunction(name, types);
		if (decl != null) {
			Visibility vis = functionVisibility.get(decl);
			
			if (vis != null && vis.isPublic()) {
				return decl;
			}
		}
		return null;
	}
	
	public EvalResult getLocalVariable(String name) {
		return super.getVariable(name);
	}
	
	public EvalResult getLocalPublicVariable(String name) {
		EvalResult var = getLocalVariable(name);
		
		if (var != null) {
			Visibility vis = variableVisibility.get(name);
			
			if (vis != null && vis.isPublic()) {
				return var;
			}
		}
		
		return null;
	}
}
