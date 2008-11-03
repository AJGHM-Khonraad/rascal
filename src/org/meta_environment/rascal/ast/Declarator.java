package org.meta_environment.rascal.ast;

import org.eclipse.imp.pdb.facts.ITree;

public abstract class Declarator extends AbstractAST {
	static public class Ambiguity extends Declarator {
		private final java.util.List<org.meta_environment.rascal.ast.Declarator> alternatives;

		public Ambiguity(
				java.util.List<org.meta_environment.rascal.ast.Declarator> alternatives) {
			this.alternatives = java.util.Collections
					.unmodifiableList(alternatives);
		}

		public java.util.List<org.meta_environment.rascal.ast.Declarator> getAlternatives() {
			return alternatives;
		}
	}

	static public class Default extends Declarator {
		private org.meta_environment.rascal.ast.Type type;
		private java.util.List<org.meta_environment.rascal.ast.Variable> variables;

		/* type:Type variables:{Variable ","}+ -> Declarator {cons("Default")} */
		private Default() {
		}

		/* package */Default(
				ITree tree,
				org.meta_environment.rascal.ast.Type type,
				java.util.List<org.meta_environment.rascal.ast.Variable> variables) {
			this.tree = tree;
			this.type = type;
			this.variables = variables;
		}

		private void $setType(org.meta_environment.rascal.ast.Type x) {
			this.type = x;
		}

		private void $setVariables(
				java.util.List<org.meta_environment.rascal.ast.Variable> x) {
			this.variables = x;
		}

		public IVisitable accept(IASTVisitor visitor) {
			return visitor.visitDeclaratorDefault(this);
		}

		@Override
		public org.meta_environment.rascal.ast.Type getType() {
			return type;
		}

		@Override
		public java.util.List<org.meta_environment.rascal.ast.Variable> getVariables() {
			return variables;
		}

		public Default setType(org.meta_environment.rascal.ast.Type x) {
			Default z = new Default();
			z.$setType(x);
			return z;
		}

		public Default setVariables(
				java.util.List<org.meta_environment.rascal.ast.Variable> x) {
			Default z = new Default();
			z.$setVariables(x);
			return z;
		}
	}

	public org.meta_environment.rascal.ast.Type getType() {
		throw new UnsupportedOperationException();
	}

	public java.util.List<org.meta_environment.rascal.ast.Variable> getVariables() {
		throw new UnsupportedOperationException();
	}
}
