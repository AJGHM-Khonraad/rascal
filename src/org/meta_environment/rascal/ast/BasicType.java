package org.meta_environment.rascal.ast;

import org.eclipse.imp.pdb.facts.INode;

public abstract class BasicType extends AbstractAST {
	public boolean isBool() {
		return false;
	}

	static public class Bool extends BasicType {
		/** &syms -> &sort {&attr*1, cons(&strcon), &attr*2} */
		private Bool() {
		}

		/* package */Bool(INode node) {
			this.node = node;
		}

		@Override
		public <T> T accept(IASTVisitor<T> visitor) {
			return visitor.visitBasicTypeBool(this);
		}

		@Override
		public boolean isBool() {
			return true;
		}
	}

	static public class Ambiguity extends BasicType {
		private final java.util.List<org.meta_environment.rascal.ast.BasicType> alternatives;

		public Ambiguity(
				INode node,
				java.util.List<org.meta_environment.rascal.ast.BasicType> alternatives) {
			this.alternatives = java.util.Collections
					.unmodifiableList(alternatives);
			this.node = node;
		}

		public java.util.List<org.meta_environment.rascal.ast.BasicType> getAlternatives() {
			return alternatives;
		}

		@Override
		public <T> T accept(IASTVisitor<T> v) {
			return v.visitBasicTypeAmbiguity(this);
		}
	}

	public boolean isInt() {
		return false;
	}

	static public class Int extends BasicType {
		/** &syms -> &sort {&attr*1, cons(&strcon), &attr*2} */
		private Int() {
		}

		/* package */Int(INode node) {
			this.node = node;
		}

		@Override
		public <T> T accept(IASTVisitor<T> visitor) {
			return visitor.visitBasicTypeInt(this);
		}

		@Override
		public boolean isInt() {
			return true;
		}
	}

	@Override
	public abstract <T> T accept(IASTVisitor<T> visitor);

	public boolean isReal() {
		return false;
	}

	static public class Real extends BasicType {
		/** &syms -> &sort {&attr*1, cons(&strcon), &attr*2} */
		private Real() {
		}

		/* package */Real(INode node) {
			this.node = node;
		}

		@Override
		public <T> T accept(IASTVisitor<T> visitor) {
			return visitor.visitBasicTypeReal(this);
		}

		@Override
		public boolean isReal() {
			return true;
		}
	}

	public boolean isString() {
		return false;
	}

	static public class String extends BasicType {
		/** &syms -> &sort {&attr*1, cons(&strcon), &attr*2} */
		private String() {
		}

		/* package */String(INode node) {
			this.node = node;
		}

		@Override
		public <T> T accept(IASTVisitor<T> visitor) {
			return visitor.visitBasicTypeString(this);
		}

		@Override
		public boolean isString() {
			return true;
		}
	}

	public boolean isValue() {
		return false;
	}

	static public class Value extends BasicType {
		/** &syms -> &sort {&attr*1, cons(&strcon), &attr*2} */
		private Value() {
		}

		/* package */Value(INode node) {
			this.node = node;
		}

		@Override
		public <T> T accept(IASTVisitor<T> visitor) {
			return visitor.visitBasicTypeValue(this);
		}

		@Override
		public boolean isValue() {
			return true;
		}
	}

	public boolean isNode() {
		return false;
	}

	static public class Node extends BasicType {
		/** &syms -> &sort {&attr*1, cons(&strcon), &attr*2} */
		private Node() {
		}

		/* package */Node(INode node) {
			this.node = node;
		}

		@Override
		public <T> T accept(IASTVisitor<T> visitor) {
			return visitor.visitBasicTypeNode(this);
		}

		@Override
		public boolean isNode() {
			return true;
		}
	}

	public boolean isVoid() {
		return false;
	}

	static public class Void extends BasicType {
		/** &syms -> &sort {&attr*1, cons(&strcon), &attr*2} */
		private Void() {
		}

		/* package */Void(INode node) {
			this.node = node;
		}

		@Override
		public <T> T accept(IASTVisitor<T> visitor) {
			return visitor.visitBasicTypeVoid(this);
		}

		@Override
		public boolean isVoid() {
			return true;
		}
	}

	public boolean isLoc() {
		return false;
	}

	static public class Loc extends BasicType {
		/** &syms -> &sort {&attr*1, cons(&strcon), &attr*2} */
		private Loc() {
		}

		/* package */Loc(INode node) {
			this.node = node;
		}

		@Override
		public <T> T accept(IASTVisitor<T> visitor) {
			return visitor.visitBasicTypeLoc(this);
		}

		@Override
		public boolean isLoc() {
			return true;
		}
	}

	public boolean isArea() {
		return false;
	}

	static public class Area extends BasicType {
		/** &syms -> &sort {&attr*1, cons(&strcon), &attr*2} */
		private Area() {
		}

		/* package */Area(INode node) {
			this.node = node;
		}

		@Override
		public <T> T accept(IASTVisitor<T> visitor) {
			return visitor.visitBasicTypeArea(this);
		}

		@Override
		public boolean isArea() {
			return true;
		}
	}
}