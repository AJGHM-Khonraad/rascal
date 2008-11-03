package org.meta_environment.rascal.ast;

import org.eclipse.imp.pdb.facts.ITree;

public abstract class Renamings extends AbstractAST {
	static public class Ambiguity extends Renamings {
		private final java.util.List<org.meta_environment.rascal.ast.Renamings> alternatives;

		public Ambiguity(
				java.util.List<org.meta_environment.rascal.ast.Renamings> alternatives) {
			this.alternatives = java.util.Collections
					.unmodifiableList(alternatives);
		}

		public java.util.List<org.meta_environment.rascal.ast.Renamings> getAlternatives() {
			return alternatives;
		}
	}

	static public class Default extends Renamings {
		private java.util.List<org.meta_environment.rascal.ast.Renaming> renamings;

		/* "renaming" renamings:{Renaming ","}+ -> Renamings {cons("Default")} */
		private Default() {
		}

		/* package */Default(
				ITree tree,
				java.util.List<org.meta_environment.rascal.ast.Renaming> renamings) {
			this.tree = tree;
			this.renamings = renamings;
		}

		private void $setRenamings(
				java.util.List<org.meta_environment.rascal.ast.Renaming> x) {
			this.renamings = x;
		}

		public IVisitable accept(IASTVisitor visitor) {
			return visitor.visitRenamingsDefault(this);
		}

		public java.util.List<org.meta_environment.rascal.ast.Renaming> getRenamings() {
			return renamings;
		}

		public Default setRenamings(
				java.util.List<org.meta_environment.rascal.ast.Renaming> x) {
			Default z = new Default();
			z.$setRenamings(x);
			return z;
		}
	}
}
