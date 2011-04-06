/*******************************************************************************
 * Copyright (c) 2009-2011 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   * Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI
 *   * Mark Hills - Mark.Hills@cwi.nl (CWI)
 *   * Arnold Lankamp - Arnold.Lankamp@cwi.nl
*******************************************************************************/
package org.rascalmpl.semantics.dynamic;

import org.eclipse.imp.pdb.facts.INode;

public abstract class ShortChar extends org.rascalmpl.ast.ShortChar {

	static public class Lexical extends org.rascalmpl.ast.ShortChar.Lexical {
		public Lexical(INode __param1, String __param2) {
			super(__param1, __param2);
		}
	}

	public ShortChar(INode __param1) {
		super(__param1);
	}
}
