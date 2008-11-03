package org.meta_environment.rascal.ast;

import org.eclipse.imp.pdb.facts.ITree;

public abstract class FloatingPointLiteral extends AbstractAST {
	static public class Ambiguity extends FloatingPointLiteral {
		private final java.util.List<org.meta_environment.rascal.ast.FloatingPointLiteral> alternatives;

		public Ambiguity(
				java.util.List<org.meta_environment.rascal.ast.FloatingPointLiteral> alternatives) {
			this.alternatives = java.util.Collections
					.unmodifiableList(alternatives);
		}

		public java.util.List<org.meta_environment.rascal.ast.FloatingPointLiteral> getAlternatives() {
			return alternatives;
		}
	}

	static public class Lexical extends FloatingPointLiteral {
		private String string;

		/* package */Lexical(ITree tree, String string) {
			this.tree = tree;
			this.string = string;
		}

		public String getString() {
			return string;
		}
	}
}
