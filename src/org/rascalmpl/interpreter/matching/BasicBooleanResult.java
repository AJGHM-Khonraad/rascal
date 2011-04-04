/*******************************************************************************
 * Copyright (c) 2009-2011 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:

 *   * Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI
 *   * Emilie Balland - emilie.balland@inria.fr (INRIA)
 *   * Mark Hills - Mark.Hills@cwi.nl (CWI)
*******************************************************************************/
package org.rascalmpl.interpreter.matching;

import org.eclipse.imp.pdb.facts.IValue;
import org.rascalmpl.ast.Expression;
import org.rascalmpl.interpreter.IEvaluatorContext;
import org.rascalmpl.interpreter.result.Result;
import org.rascalmpl.interpreter.staticErrors.UnexpectedTypeError;


public class BasicBooleanResult extends AbstractBooleanResult {
	private Result<IValue> result;
	private org.rascalmpl.ast.Expression expr;
	private boolean firstTime = true;

	public BasicBooleanResult(IEvaluatorContext ctx, Expression expr) {
		super(ctx);
		this.expr = expr;
	}

	@Override
	public void init() {
		super.init();
		firstTime = true;
	}
	
	@Override
	public boolean hasNext() {
		return firstTime;
	}

	@Override
	public boolean next() {
		if (firstTime) {
			/* Evaluate expression only once */
			firstTime = false;
			result = expr.interpret(ctx.getEvaluator());
			if (result.getType().isBoolType() && result.getValue() != null) {
				if (result.getValue().isEqual(ctx.getValueFactory().bool(true))) {
					return true;
				}
				
				
				return false;
			}

			throw new UnexpectedTypeError(tf.boolType(), result.getType(), expr);
		}
		
		return false;
	}
}