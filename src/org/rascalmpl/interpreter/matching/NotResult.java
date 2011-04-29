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
 * The not operator backtracks over its argument just like the other operator.
 * Notice however that any variables introduced below a not will never be available after it.
 * 
 * @author jurgenv
 *
 */
public class NotResult extends AbstractBooleanResult {
	private final IBooleanResult arg;

	public NotResult(IBooleanResult arg) {
		super();
		this.arg = arg;
	}

	@Override
	public void init(IEvaluatorContext ctx) {
		super.init(ctx);
		arg.init(ctx);
	}

	@Override
	public boolean hasNext() {
		return arg.hasNext();
	}
	
	@Override
	public boolean next() {
		Environment old = ctx.getCurrentEnvt();
		ctx.pushEnv();
		try {
			return !arg.next();
		}
		finally {
			ctx.unwind(old);
		}
	}
}
