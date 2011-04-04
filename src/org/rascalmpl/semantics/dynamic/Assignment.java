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

import java.util.List;

import org.eclipse.imp.pdb.facts.INode;

public abstract class Assignment extends org.rascalmpl.ast.Assignment {

	static public class Addition extends org.rascalmpl.ast.Assignment.Addition {

		public Addition(INode __param1) {
			super(__param1);
		}

	}

	static public class Ambiguity extends
			org.rascalmpl.ast.Assignment.Ambiguity {

		public Ambiguity(INode __param1,
				List<org.rascalmpl.ast.Assignment> __param2) {
			super(__param1, __param2);
		}

	}

	static public class Default extends org.rascalmpl.ast.Assignment.Default {

		public Default(INode __param1) {
			super(__param1);
		}

	}

	static public class Division extends org.rascalmpl.ast.Assignment.Division {

		public Division(INode __param1) {
			super(__param1);
		}

	}

	static public class IfDefined extends
			org.rascalmpl.ast.Assignment.IfDefined {

		public IfDefined(INode __param1) {
			super(__param1);
		}

	}

	static public class Intersection extends
			org.rascalmpl.ast.Assignment.Intersection {

		public Intersection(INode __param1) {
			super(__param1);
		}

	}

	static public class Product extends org.rascalmpl.ast.Assignment.Product {

		public Product(INode __param1) {
			super(__param1);
		}

	}

	static public class Subtraction extends
			org.rascalmpl.ast.Assignment.Subtraction {

		public Subtraction(INode __param1) {
			super(__param1);
		}

	}

	public Assignment(INode __param1) {
		super(__param1);
	}
}
