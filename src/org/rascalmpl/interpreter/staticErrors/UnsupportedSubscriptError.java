/*******************************************************************************
 * Copyright (c) 2009-2011 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   * Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI
*******************************************************************************/
package org.rascalmpl.interpreter.staticErrors;

import org.eclipse.imp.pdb.facts.type.Type;
import org.rascalmpl.ast.AbstractAST;

public class UnsupportedSubscriptError extends StaticError {
	private static final long serialVersionUID = -315365847166484727L;

	public UnsupportedSubscriptError(Type receiver, Type subscript, AbstractAST ast) {
		super("Unsupported subscript of type " + subscript + " on type " + receiver, ast);
	}

}
