/*******************************************************************************
 * Copyright (c) 2012 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:

 *   * Michael Steindorfer - Michael.Steindorfer@cwi.nl - CWI  
*******************************************************************************/
package org.rascalmpl.interpreter.debug;

import org.rascalmpl.ast.AbstractAST;
import org.rascalmpl.interpreter.IEvaluator;

/**
 * Interface for suspending an program interpretation.
 */
public interface IRascalSuspendTriggerListener {

	/**
	 * Blocking suspension of the current program interpretation, used to allow
	 * debugger stepping functionality.
	 * 
	 * @param evaluator the evaluator in charge of interpreting @param context.
	 * @param currentAST the AST that is causes the suspension.
	 */
	public void suspended(IEvaluator<?> evaluator, AbstractAST currentAST);
	
}
