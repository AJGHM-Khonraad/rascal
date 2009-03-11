package org.meta_environment.rascal.ast;

import org.eclipse.imp.pdb.facts.INode;

public abstract class Bound extends AbstractAST {
	public boolean isEmpty() {
		return false;
	}

	static public class Empty extends Bound {
		/** &syms -> &sort {&attr*1, cons(&strcon), &attr*2} */
		private Empty() {
		}

		/* package */Empty(INode node) {
			this.node = node;
		}

		@Override
		public <T> T accept(IASTVisitor<T> visitor) {
			return visitor.visitBoundEmpty(this);
		}

		@Override
		public boolean isEmpty() {
			return true;
		}
	}

	static public class Ambiguity extends Bound {
		private final java.util.List<org.meta_environment.rascal.ast.Bound> alternatives;

		public Ambiguity(
				INode node,
				java.util.List<org.meta_environment.rascal.ast.Bound> alternatives) {
			this.alternatives = java.util.Collections
					.unmodifiableList(alternatives);
			this.node = node;
		}

		public java.util.List<org.meta_environment.rascal.ast.Bound> getAlternatives() {
			return alternatives;
		}

		@Override
		public <T> T accept(IASTVisitor<T> v) {
			return v.visitBoundAmbiguity(this);
		}
	}

	public org.meta_environment.rascal.ast.Expression getExpression() {
		throw new UnsupportedOperationException();
	}

	public boolean hasExpression() {
		return false;
	}

	public boolean isDefault() {
		return false;
	}

	static public class Default extends Bound {
		/** &syms -> &sort {&attr*1, cons(&strcon), &attr*2} */
		private Default() {
		}

		/* package */Default(INode node,
				org.meta_environment.rascal.ast.Expression expression) {
			this.node = node;
			this.expression = expression;
		}

		@Override
		public <T> T accept(IASTVisitor<T> visitor) {
			return visitor.visitBoundDefault(this);
		}

		@Override
		public boolean isDefault() {
			return true;
		}

		@Override
		public boolean hasExpression() {
			return true;
		}

		private org.meta_environment.rascal.ast.Expression expression;

		@Override
		public org.meta_environment.rascal.ast.Expression getExpression() {
			return expression;
		}

		private void $setExpression(org.meta_environment.rascal.ast.Expression x) {
			this.expression = x;
		}

		public Default setExpression(
				org.meta_environment.rascal.ast.Expression x) {
			Default z = new Default();
			z.$setExpression(x);
			return z;
		}
	}

	@Override
	public abstract <T> T accept(IASTVisitor<T> visitor);
}