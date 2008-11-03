package org.meta_environment.rascal.ast;

import org.eclipse.imp.pdb.facts.ITree;

public abstract class Tags extends AbstractAST {
	static public class Ambiguity extends Tags {
		private final java.util.List<org.meta_environment.rascal.ast.Tags> alternatives;

		public Ambiguity(
				java.util.List<org.meta_environment.rascal.ast.Tags> alternatives) {
			this.alternatives = java.util.Collections
					.unmodifiableList(alternatives);
		}

		public java.util.List<org.meta_environment.rascal.ast.Tags> getAlternatives() {
			return alternatives;
		}
	}

	static public class Default extends Tags {
		private java.util.List<org.meta_environment.rascal.ast.Tag> annotations;

		/* annotations:Tag -> Tags {cons("Default")} */
		private Default() {
		}

		/* package */Default(ITree tree,
				java.util.List<org.meta_environment.rascal.ast.Tag> annotations) {
			this.tree = tree;
			this.annotations = annotations;
		}

		private void $setAnnotations(
				java.util.List<org.meta_environment.rascal.ast.Tag> x) {
			this.annotations = x;
		}

		public IVisitable accept(IASTVisitor visitor) {
			return visitor.visitTagsDefault(this);
		}

		public java.util.List<org.meta_environment.rascal.ast.Tag> getAnnotations() {
			return annotations;
		}

		public Default setAnnotations(
				java.util.List<org.meta_environment.rascal.ast.Tag> x) {
			Default z = new Default();
			z.$setAnnotations(x);
			return z;
		}
	}
}
