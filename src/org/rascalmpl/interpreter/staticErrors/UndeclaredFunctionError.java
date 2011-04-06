/*******************************************************************************
 * Copyright (c) 2009-2011 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   * 
 *   * Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI
 *   * Paul Klint - Paul.Klint@cwi.nl - CWI
 *   * Arnold Lankamp - Arnold.Lankamp@cwi.nl
*******************************************************************************/
package org.rascalmpl.interpreter.staticErrors;

import org.eclipse.imp.pdb.facts.type.Type;
import org.rascalmpl.ast.AbstractAST;
import org.rascalmpl.interpreter.IEvaluatorContext;

public class UndeclaredFunctionError extends StaticError {
	private static final long serialVersionUID = -3215674987633177L;
	
	public UndeclaredFunctionError(String name, Type[] argTypes, IEvaluatorContext ctx, AbstractAST node) {
		super("Undeclared function: " + toString(name, argTypes), node);
	}

	private static String toString(String name, Type[] argTypes) {
		StringBuilder b = new StringBuilder();
		b.append(name);
		b.append('(');
		int i = 0;
		for (Type arg : argTypes) {
			if (i > 0) b.append(',');
			b.append(arg);
		}
		b.append(')');
		return b.toString();
	}
}
