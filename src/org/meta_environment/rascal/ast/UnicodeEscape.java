package org.meta_environment.rascal.ast;

import org.eclipse.imp.pdb.facts.ITree;

public abstract class UnicodeEscape extends AbstractAST {
	static public class Ambiguity extends UnicodeEscape {
		private final java.util.List<org.meta_environment.rascal.ast.UnicodeEscape> alternatives;

		public Ambiguity(
				ITree tree,
				java.util.List<org.meta_environment.rascal.ast.UnicodeEscape> alternatives) {
			this.alternatives = java.util.Collections
					.unmodifiableList(alternatives);
			this.tree = tree;
		}

		@Override
		public <T> T accept(IASTVisitor<T> v) {
			return v.visitUnicodeEscapeAmbiguity(this);
		}

		public java.util.List<org.meta_environment.rascal.ast.UnicodeEscape> getAlternatives() {
			return alternatives;
		}
	}

	static public class Lexical extends UnicodeEscape {
		private final String string;

		/* package */Lexical(ITree tree, String string) {
			this.tree = tree;
			this.string = string;
		}

		@Override
		public <T> T accept(IASTVisitor<T> v) {
			return v.visitUnicodeEscapeLexical(this);
		}

		public String getString() {
			return string;
		}
	}
}