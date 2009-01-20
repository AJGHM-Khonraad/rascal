package org.meta_environment.rascal.ast;

import org.eclipse.imp.pdb.facts.ITree;

public abstract class Formals extends AbstractAST {
	static public class Ambiguity extends Formals {
		private final java.util.List<org.meta_environment.rascal.ast.Formals> alternatives;

		public Ambiguity(
				ITree tree,
				java.util.List<org.meta_environment.rascal.ast.Formals> alternatives) {
			this.alternatives = java.util.Collections
					.unmodifiableList(alternatives);
			this.tree = tree;
		}

		@Override
		public <T> T accept(IASTVisitor<T> v) {
			return v.visitFormalsAmbiguity(this);
		}

		public java.util.List<org.meta_environment.rascal.ast.Formals> getAlternatives() {
			return alternatives;
		}
	}

	static public class Default extends Formals {
		private java.util.List<org.meta_environment.rascal.ast.Formal> formals;

		/* formals:{Formal ","} -> Formals {cons("Default")} */
		private Default() {
		}

		/* package */Default(ITree tree,
				java.util.List<org.meta_environment.rascal.ast.Formal> formals) {
			this.tree = tree;
			this.formals = formals;
		}

		private void $setFormals(
				java.util.List<org.meta_environment.rascal.ast.Formal> x) {
			this.formals = x;
		}

		@Override
		public <T> T accept(IASTVisitor<T> visitor) {
			return visitor.visitFormalsDefault(this);
		}

		@Override
		public java.util.List<org.meta_environment.rascal.ast.Formal> getFormals() {
			return formals;
		}

		@Override
		public boolean hasFormals() {
			return true;
		}

		@Override
		public boolean isDefault() {
			return true;
		}

		public Default setFormals(
				java.util.List<org.meta_environment.rascal.ast.Formal> x) {
			final Default z = new Default();
			z.$setFormals(x);
			return z;
		}
	}

	public java.util.List<org.meta_environment.rascal.ast.Formal> getFormals() {
		throw new UnsupportedOperationException();
	}

	public boolean hasFormals() {
		return false;
	}

	public boolean isDefault() {
		return false;
	}
}