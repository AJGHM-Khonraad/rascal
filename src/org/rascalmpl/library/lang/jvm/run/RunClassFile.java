/*******************************************************************************
 * Copyright (c) 2009-2011 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   * Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI
 *   * Atze van der Ploeg - Atze.van.der.Ploeg@cwi.nl (CWI)
 *   * Arnold Lankamp - Arnold.Lankamp@cwi.nl
*******************************************************************************/
package org.rascalmpl.library.lang.jvm.run;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;

import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.rascalmpl.interpreter.IEvaluatorContext;
import org.rascalmpl.interpreter.utils.RuntimeExceptionFactory;
import org.rascalmpl.values.ValueFactoryFactory;

public class RunClassFile {
	public RunClassFile(IValueFactory values) {
		super();
	}
	
	private Class<?> getClass(ISourceLocation path, IEvaluatorContext ctx,BinaryClassLoader load){
		try {
			URI input = ctx.getResolverRegistry().getResourceURI(path.getURI());
			Class<?> c = load.defineClass(input);
			return c;
		} catch(IOException e){
			throw RuntimeExceptionFactory.io(ValueFactoryFactory.getValueFactory().string(e.getMessage()), null, null);
		} 
	}
	
	public void runClassFile(ISourceLocation path, IList dependencies, IEvaluatorContext ctx){
		try {
			BinaryClassLoader load = new BinaryClassLoader(ctx.getEvaluator().getClassLoaders());
			for(IValue elem : dependencies){
				getClass((ISourceLocation) elem,ctx,load);
			}
			Class<?> c = getClass(path,ctx,load);
			c.getDeclaredMethod("main",  new Class[] { String[].class }).invoke(null,new Object[] { new String[0] });
		} catch(NoSuchMethodException e){
			throw RuntimeExceptionFactory.io(ValueFactoryFactory.getValueFactory().string(e.getMessage()), null, null);
		} catch (IllegalArgumentException e) {
			throw RuntimeExceptionFactory.io(ValueFactoryFactory.getValueFactory().string(e.getMessage()), null, null);
		} catch (SecurityException e) {
			throw RuntimeExceptionFactory.io(ValueFactoryFactory.getValueFactory().string(e.getMessage()), null, null);
		} catch (IllegalAccessException e) {
			throw RuntimeExceptionFactory.io(ValueFactoryFactory.getValueFactory().string(e.getMessage()), null, null);
		} catch (InvocationTargetException e) {
			throw RuntimeExceptionFactory.io(ValueFactoryFactory.getValueFactory().string(e.getMessage()), null, null);
		}
	}
	
	
}
