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

import org.eclipse.imp.pdb.facts.type.Type;
import org.rascalmpl.ast.Expression.CallOrTree;
import org.rascalmpl.interpreter.env.Environment;

class ConcreteAmbiguityPattern extends AbstractMatchingResult {

	public ConcreteAmbiguityPattern(
			CallOrTree x,
			java.util.List<AbstractBooleanResult> args) {
		super(x);
	}

	public Type getType(Environment env) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean next() {
		// TODO Auto-generated method stub
		return false;
	}
}
