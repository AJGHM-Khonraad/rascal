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
 *   * Arnold Lankamp - Arnold.Lankamp@cwi.nl
*******************************************************************************/
package org.rascalmpl.interpreter.matching;

import org.rascalmpl.interpreter.IEvaluatorContext;
import org.rascalmpl.interpreter.env.Environment;

/**
 * The or boolean operator backtracks for both the lhs and the rhs. This means
 * that if the lhs or rhs have multiple ways of assigning a value to a variable,
 * this and operator will be evaluated as many times.
 * 
 * Note that variables introduced in the left hand side will NOT be visible in the
 * right hand side. The right hand side is only evaluated if the left hand side is false,
 * which means that no variables have been bound. Also note that both sides of a
 * disjunction are required to introduce exactly the same variables of exactly the same
 * type
 * 
 * @author jurgenv
 *
 */
public class OrResult extends AbstractBooleanResult {
	private final IBooleanResult left;
	private final IBooleanResult right;

	public OrResult(IBooleanResult left, IBooleanResult right) {
		super();
		this.left = left;
		this.right = right;
	}

	@Override
	public void init(IEvaluatorContext ctx) {
		super.init(ctx);
		left.init(ctx);
		right.init(ctx);
		hasNext = true;
	}

	@Override
	public boolean hasNext() {
		return hasNext && (left.hasNext() || right.hasNext());
	}
	
	@Override
	public boolean next() {
		// note how we clean up introduced variables, but only if one of the branches fails
		if (left.hasNext()) {
			Environment old = ctx.getCurrentEnvt();
			ctx.pushEnv();
			if (left.next()) {
				hasNext = false;
				return true;
			}
			ctx.unwind(old);
		}
		
		if (right.hasNext()) {
			Environment old = ctx.getCurrentEnvt();
			ctx.pushEnv();
			if (right.next()) {
				hasNext = false;
				return true;
			}
			ctx.unwind(old);
		}
		
		hasNext = false;
		return false;
	}
}
