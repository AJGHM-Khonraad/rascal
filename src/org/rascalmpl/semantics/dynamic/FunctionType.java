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
*******************************************************************************/
package org.rascalmpl.semantics.dynamic;

import java.util.List;

import org.eclipse.imp.pdb.facts.INode;
import org.eclipse.imp.pdb.facts.type.Type;
import org.rascalmpl.ast.TypeArg;
import org.rascalmpl.interpreter.env.Environment;
import org.rascalmpl.interpreter.utils.TypeUtils;

public abstract class FunctionType extends org.rascalmpl.ast.FunctionType {

	static public class TypeArguments extends
			org.rascalmpl.ast.FunctionType.TypeArguments {

		public TypeArguments(INode __param1, org.rascalmpl.ast.Type __param2,
				List<TypeArg> __param3) {
			super(__param1, __param2, __param3);
		}

		@Override
		public Type typeOf(Environment __eval) {
			Type returnType = this.getType().typeOf(__eval);
			Type argTypes = TypeUtils.typeOf(this.getArguments(), __eval);
			return org.rascalmpl.interpreter.types.RascalTypeFactory
					.getInstance().functionType(returnType, argTypes);
		}
	}

	public FunctionType(INode __param1) {
		super(__param1);
	}
}
