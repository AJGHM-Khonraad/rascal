/*******************************************************************************
 * Copyright (c) 2009-2013 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:

 *   * Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI
 *   * Mark Hills - Mark.Hills@cwi.nl (CWI)
 *   * Paul Klint - Paul.Klint@cwi.nl (CWI)
*******************************************************************************/
package org.rascalmpl.interpreter.result;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;
import org.eclipse.imp.pdb.facts.util.AbstractSpecialisedImmutableMap;
import org.eclipse.imp.pdb.facts.util.ImmutableMap;
import org.rascalmpl.ast.AbstractAST;
import org.rascalmpl.ast.FunctionDeclaration;
import org.rascalmpl.ast.FunctionModifier;
import org.rascalmpl.ast.Signature;
import org.rascalmpl.ast.Tag;
import org.rascalmpl.ast.TagString;
import org.rascalmpl.ast.Tags;
import org.rascalmpl.interpreter.IEvaluator;
import org.rascalmpl.interpreter.control_exceptions.MatchFailed;
import org.rascalmpl.interpreter.env.Environment;
import org.rascalmpl.interpreter.env.KeywordParameter;
import org.rascalmpl.interpreter.result.util.MemoizationCache;
import org.rascalmpl.interpreter.staticErrors.UnexpectedKeywordArgumentType;
import org.rascalmpl.interpreter.types.FunctionType;
import org.rascalmpl.interpreter.utils.Names;

abstract public class NamedFunction extends AbstractFunction {
	private static final String RESOURCE_TAG = "resource";
  private static final String RESOLVER_TAG = "resolver";
  protected final String name;
  protected final boolean isDefault;
  protected final boolean isTest;
  protected final boolean isStatic;
  protected final String resourceScheme;
  protected final String resolverScheme;
  protected final Map<String, String> tags;
	private SoftReference<MemoizationCache> memoization;
  protected final boolean hasMemoization;
  
	public NamedFunction(AbstractAST ast, IEvaluator<Result<IValue>> eval, FunctionType functionType, String name,
			boolean varargs, boolean isDefault, boolean isTest, Environment env) {
		super(ast, eval, functionType, varargs, env);
		this.name = name;
		this.isDefault = isDefault;
		this.isTest = isTest;
	  this.isStatic = env.isRootScope() && eval.__getRootScope() != env;
	  
		if (ast instanceof FunctionDeclaration) {
      tags = parseTags((FunctionDeclaration) ast);
      this.resourceScheme = getResourceScheme((FunctionDeclaration) ast);
      this.resolverScheme = getResolverScheme((FunctionDeclaration) ast);
      this.hasMemoization = checkMemoization((FunctionDeclaration) ast);
    } else {
      tags = new HashMap<String, String>();
      this.resourceScheme = null;
      this.resolverScheme = null;
      this.hasMemoization = false;
    }
	}

	protected static boolean hasTestMod(Signature sig) {
	  for (FunctionModifier m : sig.getModifiers().getModifiers()) {
	    if (m.isTest()) {
	      return true;
	    }
	  }

	  return false;
	}
	 
	@Override
	public String getName() {
		return name;
	}
	
	protected Result<IValue> getMemoizedResult(IValue[] argValues, Map<String, IValue> keyArgValues) {
		if (hasMemoization()) {
			MemoizationCache memoizationActual = getMemoizationCache(false);
			if (memoizationActual == null) {
				return null;
			}
			return memoizationActual.getStoredResult(argValues, keyArgValues);
		}
		return null;
	}

	private MemoizationCache getMemoizationCache(boolean returnFresh) {
		MemoizationCache result = null;
		if (memoization == null) {
			result = new MemoizationCache();
			memoization = new SoftReference<>(result);
			return returnFresh ? result : null;

		}
		result = memoization.get();
		if (result == null ) {
			result = new MemoizationCache();
			memoization = new SoftReference<>(result);
			return returnFresh ? result : null;
		}
		return result;
	}
	
	protected Result<IValue> storeMemoizedResult(IValue[] argValues, Map<String, IValue> keyArgValues, Result<IValue> result) {
		if (hasMemoization()) {
			getMemoizationCache(true).storeResult(argValues, keyArgValues, result);
		}
		return result;
	}
	
	
	@Override
	public Result<IValue> call(Type[] argTypes, IValue[] argValues,
			Map<String, IValue> keyArgValues) throws MatchFailed {
		Result<IValue> result = getMemoizedResult(argValues, keyArgValues);
		if (result == null) {
			result = super.call(argTypes, argValues, keyArgValues);
			storeMemoizedResult(argValues, keyArgValues, result);
		}
		return result;
	}
	
	protected static String getResourceScheme(FunctionDeclaration declaration) {
  	return getScheme(RESOURCE_TAG, declaration);
  }

  protected static String getResolverScheme(FunctionDeclaration declaration) {
  	return getScheme(RESOLVER_TAG, declaration);
  }

  protected boolean checkMemoization(FunctionDeclaration func) {
  	for (Tag tag : func.getTags().getTags()) {
  		if (Names.name(tag.getName()).equals("memo")) {
  			return true;
  		}
  	}
  	return false;
  }

  protected boolean hasMemoization() {
    return hasMemoization;
  }

  protected Map<String, String> parseTags(FunctionDeclaration declaration) {
  	Map<String, String> result = new HashMap<String, String>();
  	Tags tags = declaration.getTags();
  	if (tags.hasTags()) {
  		for (Tag tag : tags.getTags()) {
  			if(tag.hasContents()){
  				String key = Names.name(tag.getName());
  				String value = ((TagString.Lexical) tag.getContents()).getString();
  				if (value.length() > 2 && value.startsWith("{")) {
  					value = value.substring(1, value.length() - 1);
  				}
  				result.put(key, value);
  			}
  		}
  	}
  	return result;
  }

  @Override
  public String getTag(String key) {
  	return tags.get(key);
  }

  @Override
  public boolean hasTag(String key) {
  	return tags.containsKey(key);
  }

  private static String getScheme(String schemeTag, FunctionDeclaration declaration) {
  	Tags tags = declaration.getTags();
  	
  	if (tags.hasTags()) {
  		for (Tag tag : tags.getTags()) {
  			if (Names.name(tag.getName()).equals(schemeTag)) {
  				String contents = ((TagString.Lexical) tag.getContents()).getString();
  				
  				if (contents.length() > 2 && contents.startsWith("{")) {
  					contents = contents.substring(1, contents.length() - 1);
  				}
  				return contents;
  			}
  		}
  	}
  	
  	return null;
  }

  protected static boolean isDefault(FunctionDeclaration func) {
  	List<FunctionModifier> mods = func.getSignature().getModifiers().getModifiers();
  	for (FunctionModifier m : mods) {
  		if (m.isDefault()) {
  			return true;
  		}
  	}
  	return false;
  }

  protected void bindKeywordArgs(Map<String, IValue> keyArgValues){
    Environment env = ctx.getCurrentEnvt();
    ImmutableMap<String,IValue> args = AbstractSpecialisedImmutableMap.mapOf();
    
    for (Entry<String, Result<IValue>> var : env.getVariables().entrySet()) {
    	args = args.__put(var.getKey(), var.getValue().getValue());
    }
    
    if (functionType.hasKeywordParameters()) {
    	for (String kwparam : functionType.getKeywordParameterTypes().getFieldNames()){
    		Type kwType = functionType.getKeywordParameterType(kwparam);

    		if (keyArgValues.containsKey(kwparam)){
    			IValue r = keyArgValues.get(kwparam);

    			if(!r.getType().isSubtypeOf(kwType)) {
    				throw new UnexpectedKeywordArgumentType(kwparam, kwType, r.getType(), ctx.getCurrentAST());
    			}

    			args = args.__put(kwparam, r);
    			env.declareVariable(kwType, kwparam);
    			env.storeVariable(kwparam, ResultFactory.makeResult(kwType, r, ctx));
    		} 
    		else {
    			env.declareVariable(kwType, kwparam);
    			IValue def = functionType.getKeywordParameterInitializer(kwparam).initialize(args);
    			args = args.__put(kwparam, def);
    			env.storeVariable(kwparam, ResultFactory.makeResult(kwType, def, ctx));
    		}
    	}
    }
    
    // TODO: what if the caller provides more arguments then are declared? They are
    // silently lost here.
  }
	
	@Override
  public boolean isTest() {
  	return isTest;
  }

  public String getHeader(){
		String sep = "";
		String strFormals = "";
		for(Type tp : getFormals()){
			strFormals = strFormals + sep + tp;
			sep = ", ";
		}
		
		String name = getName();
		if (name == null) {
			name = "";
		}
		
		
		String kwFormals = "";
		
		if(keywordParameterDefaults != null){
			sep = (strFormals.length() > 0) ? ", " : "";
				
			for(KeywordParameter kw : keywordParameterDefaults){
				Result<IValue> r = kw.getDefault();
				kwFormals += sep + r.getType() + " " + kw.getName() + "=" + r.getValue();
				sep = ", ";
			}
		}
		
		return getReturnType() + " " + name + "(" + strFormals + kwFormals + ")";
	}

  @Override
  public boolean isDefault() {
  	return isDefault;
  }

  @Override
  public String getResourceScheme() {
  	return this.resourceScheme;
  }

  @Override
  public boolean hasResourceScheme() {
  	return this.resourceScheme != null;
  }

  @Override
  public boolean hasResolverScheme() {
  	return this.resolverScheme != null;
  }

  @Override
  public String getResolverScheme() {
  	return this.resolverScheme;
  }

}
