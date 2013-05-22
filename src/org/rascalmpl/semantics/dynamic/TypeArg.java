/*******************************************************************************
 * Copyright (c) 2009-2013 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:

 *   * Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI
 *   * Mark Hills - Mark.Hills@cwi.nl (CWI)
*******************************************************************************/
package org.rascalmpl.semantics.dynamic;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.type.Type;
import org.rascalmpl.ast.Name;
import org.rascalmpl.interpreter.env.Environment;

public abstract class TypeArg extends org.rascalmpl.ast.TypeArg {

	static public class Default extends org.rascalmpl.ast.TypeArg.Default {

		public Default(IConstructor __param1, org.rascalmpl.ast.Type __param2) {
			super(__param1, __param2);
		}

		@Override
		public Type typeOf(Environment __eval, boolean instantiateTypeParameters) {
			return this.getType().typeOf(__eval, instantiateTypeParameters);
		}

	}

	static public class Named extends org.rascalmpl.ast.TypeArg.Named {

		public Named(IConstructor __param1, org.rascalmpl.ast.Type __param2,
				Name __param3) {
			super(__param1, __param2, __param3);
		}

		@Override
		public Type typeOf(Environment __eval, boolean instantiateTypeParameters) {
			return this.getType().typeOf(__eval, instantiateTypeParameters);
		}

	}

	public TypeArg(IConstructor __param1) {
		super(__param1);
	}
}
