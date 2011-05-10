/*******************************************************************************
 * Copyright (c) 2009-2011 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:

 *   * Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI
 *   * Emilie Balland - (CWI)
*******************************************************************************/
package org.rascalmpl.interpreter.matching;

import org.eclipse.imp.pdb.facts.type.TypeFactory;
import org.rascalmpl.interpreter.IEvaluatorContext;

public abstract class AbstractBooleanResult implements IBooleanResult {
	protected boolean initialized = false;
	protected boolean hasNext = true;
	protected final TypeFactory tf = TypeFactory.getInstance();
	protected final IEvaluatorContext ctx;
	
	public AbstractBooleanResult(IEvaluatorContext ctx){
		super();
		
		this.ctx = ctx;
	}
	
	public void init() {
		this.initialized = true;
		this.hasNext = true;
	}
	
	public boolean hasNext() {
		return initialized && hasNext;
	}
	
	abstract public boolean next();
}
