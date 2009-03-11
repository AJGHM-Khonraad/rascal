package org.meta_environment.rascal.ast;

import org.eclipse.imp.pdb.facts.INode;

public abstract class Field extends AbstractAST {
	public org.meta_environment.rascal.ast.Name getFieldName() {
		throw new UnsupportedOperationException();
	}

	public boolean hasFieldName() {
		return false;
	}

	public boolean isName() {
		return false;
	}

	static public class Name extends Field {
		/** &syms -> &sort {&attr*1, cons(&strcon), &attr*2} */
		private Name() {
		}

		/* package */Name(INode node,
				org.meta_environment.rascal.ast.Name fieldName) {
			this.node = node;
			this.fieldName = fieldName;
		}

		@Override
		public <T> T accept(IASTVisitor<T> visitor) {
			return visitor.visitFieldName(this);
		}

		@Override
		public boolean isName() {
			return true;
		}

		@Override
		public boolean hasFieldName() {
			return true;
		}

		private org.meta_environment.rascal.ast.Name fieldName;

		@Override
		public org.meta_environment.rascal.ast.Name getFieldName() {
			return fieldName;
		}

		private void $setFieldName(org.meta_environment.rascal.ast.Name x) {
			this.fieldName = x;
		}

		public Name setFieldName(org.meta_environment.rascal.ast.Name x) {
			Name z = new Name();
			z.$setFieldName(x);
			return z;
		}
	}

	static public class Ambiguity extends Field {
		private final java.util.List<org.meta_environment.rascal.ast.Field> alternatives;

		public Ambiguity(
				INode node,
				java.util.List<org.meta_environment.rascal.ast.Field> alternatives) {
			this.alternatives = java.util.Collections
					.unmodifiableList(alternatives);
			this.node = node;
		}

		public java.util.List<org.meta_environment.rascal.ast.Field> getAlternatives() {
			return alternatives;
		}

		@Override
		public <T> T accept(IASTVisitor<T> v) {
			return v.visitFieldAmbiguity(this);
		}
	}

	public org.meta_environment.rascal.ast.IntegerLiteral getFieldIndex() {
		throw new UnsupportedOperationException();
	}

	public boolean hasFieldIndex() {
		return false;
	}

	public boolean isIndex() {
		return false;
	}

	static public class Index extends Field {
		/** &syms -> &sort {&attr*1, cons(&strcon), &attr*2} */
		private Index() {
		}

		/* package */Index(INode node,
				org.meta_environment.rascal.ast.IntegerLiteral fieldIndex) {
			this.node = node;
			this.fieldIndex = fieldIndex;
		}

		@Override
		public <T> T accept(IASTVisitor<T> visitor) {
			return visitor.visitFieldIndex(this);
		}

		@Override
		public boolean isIndex() {
			return true;
		}

		@Override
		public boolean hasFieldIndex() {
			return true;
		}

		private org.meta_environment.rascal.ast.IntegerLiteral fieldIndex;

		@Override
		public org.meta_environment.rascal.ast.IntegerLiteral getFieldIndex() {
			return fieldIndex;
		}

		private void $setFieldIndex(
				org.meta_environment.rascal.ast.IntegerLiteral x) {
			this.fieldIndex = x;
		}

		public Index setFieldIndex(
				org.meta_environment.rascal.ast.IntegerLiteral x) {
			Index z = new Index();
			z.$setFieldIndex(x);
			return z;
		}
	}

	@Override
	public abstract <T> T accept(IASTVisitor<T> visitor);
}