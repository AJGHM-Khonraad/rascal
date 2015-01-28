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
 *   * Mark Hills - Mark.Hills@cwi.nl (CWI)
*******************************************************************************/
package org.rascalmpl.library.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.imp.pdb.facts.IBool;
import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.rascalmpl.interpreter.Evaluator;
import org.rascalmpl.interpreter.IEvaluator;
import org.rascalmpl.interpreter.IEvaluatorContext;
import org.rascalmpl.interpreter.env.GlobalEnvironment;
import org.rascalmpl.interpreter.env.ModuleEnvironment;
import org.rascalmpl.interpreter.load.SourceLocationListContributor;
import org.rascalmpl.interpreter.utils.RuntimeExceptionFactory;
import org.rascalmpl.library.Prelude;
import org.rascalmpl.uri.URIUtil;

public class Reflective {
	protected final IValueFactory values;
	private Evaluator cachedEvaluator;
	private int robin = 0;
	protected final Prelude prelude;
	private static final int maxCacheRounds = 500;

	public Reflective(IValueFactory values){
		super();
		this.values = values;
		prelude = new Prelude(values);
	}
	
	// REFLECT -- copy in ReflectiveCompiled
	public IValue parseCommand(IString str, ISourceLocation loc, IEvaluatorContext ctx) {
		IEvaluator<?> evaluator = ctx.getEvaluator();
		return evaluator.parseCommand(evaluator.getMonitor(), str.getValue(), loc);
	}

	// REFLECT -- copy in ReflectiveCompiled
	public IValue parseCommands(IString str, ISourceLocation loc, IEvaluatorContext ctx) {
		IEvaluator<?> evaluator = ctx.getEvaluator();
		return evaluator.parseCommands(evaluator.getMonitor(), str.getValue(), loc);
	}
	
	// REFLECT -- copy in ReflectiveCompiled
	public IValue parseModule(ISourceLocation loc, IEvaluatorContext ctx) {
		try {
			Evaluator ownEvaluator = getPrivateEvaluator(ctx);
			return ownEvaluator.parseModule(ownEvaluator.getMonitor(), loc);
		}
		catch (IOException e) {
			throw RuntimeExceptionFactory.io(values.string(e.getMessage()), null, null);
		}
		catch (Throwable e) {
		  throw RuntimeExceptionFactory.javaException(e, null, null);
		}
	}

	private Evaluator getPrivateEvaluator(IEvaluatorContext ctx) {
		if (cachedEvaluator == null || robin++ > maxCacheRounds) {
			robin = 0;
			IEvaluator<?> callingEval = ctx.getEvaluator();
			
			
			GlobalEnvironment heap = new GlobalEnvironment();
			ModuleEnvironment root = heap.addModule(new ModuleEnvironment("___full_module_parser___", heap));
			cachedEvaluator = new Evaluator(callingEval.getValueFactory(), callingEval.getStdErr(), callingEval.getStdOut(), root, heap);
			
			// Update the classpath so it is the same as in the context interpreter.
			cachedEvaluator.getConfiguration().setRascalJavaClassPathProperty(ctx.getConfiguration().getRascalJavaClassPathProperty());
		  // clone the classloaders
	    for (ClassLoader loader : ctx.getEvaluator().getClassLoaders()) {
	      cachedEvaluator.addClassLoader(loader);
	    }
		}
		
		return cachedEvaluator;
	}
	
	// REFLECT -- copy in ReflectiveCompiled
	public IValue parseModule(IString str, ISourceLocation loc, IEvaluatorContext ctx) {
		Evaluator ownEvaluator = getPrivateEvaluator(ctx);
		return ownEvaluator.parseModule(ownEvaluator.getMonitor(), str.getValue().toCharArray(), loc);
	}
	
	// REFLECT -- copy in ReflectiveCompiled
	public IValue parseModule(ISourceLocation loc, final IList searchPath, IEvaluatorContext ctx) {
    final Evaluator ownEvaluator = getPrivateEvaluator(ctx);

    // add the given locations to the search path
    SourceLocationListContributor contrib = new SourceLocationListContributor("reflective", searchPath);
    ownEvaluator.addRascalSearchPathContributor(contrib);
    
    try { 
      return ownEvaluator.parseModule(ownEvaluator.getMonitor(), loc);
    } catch (IOException e) {
      throw RuntimeExceptionFactory.io(values.string(e.getMessage()), null, null);
    }
    catch (Throwable e) {
      throw RuntimeExceptionFactory.javaException(e, null, null);
    }
    finally {
      ownEvaluator.removeSearchPathContributor(contrib);
    }
  }
	
	// REFLECT -- copy in ReflectiveCompiled
	public IValue getModuleLocation(IString modulePath, IEvaluatorContext ctx) {
		ISourceLocation uri = ctx.getEvaluator().getRascalResolver().resolveModule(modulePath.getValue());
		if (uri == null) {
		  throw RuntimeExceptionFactory.moduleNotFound(modulePath, ctx.getCurrentAST(), null);
		}
		return uri;
	}
	
	// REFLECT -- copy in ReflectiveCompiled
	public ISourceLocation getSearchPathLocation(IString path, IEvaluatorContext ctx) {
		String value = path.getValue();
		
		if (path.length() == 0) {
			throw RuntimeExceptionFactory.io(values.string("File not found in search path: [" + path + "]"), null, null);
		}
		
		if (!value.startsWith("/")) {
			value = "/" + value;
		}
		
		try {
			ISourceLocation uri = ctx.getEvaluator().getRascalResolver().resolvePath(value);
			if (uri == null) {
				URI parent = URIUtil.getParentURI(URIUtil.createFile(value));
				
				if (parent == null) {
					// if the parent does not exist we are at the root and we look up the first path contributor:
					parent = URIUtil.createFile("/"); 
				}
				
				// here we recurse on the parent to see if it might exist
				ISourceLocation result = getSearchPathLocation(values.string(parent.getPath()), ctx);
				
				if (result != null) {
					String child = URIUtil.getURIName(URIUtil.createFile(value));
					return URIUtil.getChildLocation(result, child);
				}
				
				throw RuntimeExceptionFactory.io(values.string("File not found in search path: " + path), null, null);
			}

			return uri;
		} catch (URISyntaxException e) {
			throw  RuntimeExceptionFactory.malformedURI(value, null, null);
		}
	}
	
	// Note -- copy in ReflectiveCompiled
	
	public IBool inCompiledMode() { return values.bool(false); }
	
	// REFLECT -- copy in ReflectiveCompiled
	public IValue watch(IValue tp, IValue val, IString name, IEvaluatorContext ctx){
		return watch(tp, val, name, values.string(""), ctx);
	}
	
	protected String stripQuotes(IValue suffixVal){
		String s1 = suffixVal.toString();
		if(s1.startsWith("\"")){
			s1 = s1.substring(1, s1.length() - 1);
		}
		return s1;
	}
	
	
	
	// REFLECT -- copy in ReflectiveCompiled
	public IValue watch(IValue tp, IValue val, IString name, IValue suffixVal, IEvaluatorContext ctx){
		ISourceLocation watchLoc;
		String suffix = stripQuotes(suffixVal);
		String name1 = stripQuotes(name);

		String path = "watchpoints/" + (suffix.length() == 0 ? name1 : (name1 + "-" + suffix)) + ".txt";
		try {
			watchLoc = values.sourceLocation("home", null, path, null, null);
		} catch (URISyntaxException e) {
			throw RuntimeExceptionFactory.io(values.string("Cannot create |home:///" + name1 + "|"), null, null);
		}
		prelude.writeTextValueFile(watchLoc, val, ctx);
		return val;
	}

}
