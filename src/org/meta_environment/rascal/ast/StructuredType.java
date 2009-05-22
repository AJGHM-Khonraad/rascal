package org.meta_environment.rascal.ast;

import org.eclipse.imp.pdb.facts.INode;

public abstract class StructuredType extends AbstractAST {
	public org.meta_environment.rascal.ast.TypeArg getTypeArg() {
		throw new UnsupportedOperationException();
	}

	public boolean hasTypeArg() {
		return false;
	}

	public boolean isList() {
		return false;
	}

	static public class List extends StructuredType {
		/* "list" "[" typeArg:TypeArg "]" -> StructuredType {cons("List")} */
		private List() {
			super();
		}

		public List(INode node, org.meta_environment.rascal.ast.TypeArg typeArg) {
			this.node = node;
			this.typeArg = typeArg;
		}

		@Override
		public <T> T accept(IASTVisitor<T> visitor) {
			return visitor.visitStructuredTypeList(this);
		}

		@Override
		public boolean isList() {
			return true;
		}

		@Override
		public boolean hasTypeArg() {
			return true;
		}

		private org.meta_environment.rascal.ast.TypeArg typeArg;

		@Override
		public org.meta_environment.rascal.ast.TypeArg getTypeArg() {
			return typeArg;
		}

		private void $setTypeArg(org.meta_environment.rascal.ast.TypeArg x) {
			this.typeArg = x;
		}

		public List setTypeArg(org.meta_environment.rascal.ast.TypeArg x) {
			List z = new List();
			z.$setTypeArg(x);
			return z;
		}
	}

	static public class Ambiguity extends StructuredType {
		private final java.util.List<org.meta_environment.rascal.ast.StructuredType> alternatives;

		public Ambiguity(
				INode node,
				java.util.List<org.meta_environment.rascal.ast.StructuredType> alternatives) {
			this.alternatives = java.util.Collections
					.unmodifiableList(alternatives);
			this.node = node;
		}

		public java.util.List<org.meta_environment.rascal.ast.StructuredType> getAlternatives() {
			return alternatives;
		}

		@Override
		public <T> T accept(IASTVisitor<T> v) {
			return v.visitStructuredTypeAmbiguity(this);
		}
	}

	public boolean isSet() {
		return false;
	}

	static public class Set extends StructuredType {
		/* "set" "[" typeArg:TypeArg "]" -> StructuredType {cons("Set")} */
		private Set() {
			super();
		}

		public Set(INode node, org.meta_environment.rascal.ast.TypeArg typeArg) {
			this.node = node;
			this.typeArg = typeArg;
		}

		@Override
		public <T> T accept(IASTVisitor<T> visitor) {
			return visitor.visitStructuredTypeSet(this);
		}

		@Override
		public boolean isSet() {
			return true;
		}

		@Override
		public boolean hasTypeArg() {
			return true;
		}

		private org.meta_environment.rascal.ast.TypeArg typeArg;

		@Override
		public org.meta_environment.rascal.ast.TypeArg getTypeArg() {
			return typeArg;
		}

		private void $setTypeArg(org.meta_environment.rascal.ast.TypeArg x) {
			this.typeArg = x;
		}

		public Set setTypeArg(org.meta_environment.rascal.ast.TypeArg x) {
			Set z = new Set();
			z.$setTypeArg(x);
			return z;
		}
	}

	@Override
	public abstract <T> T accept(IASTVisitor<T> visitor);

	public boolean isBag() {
		return false;
	}

	static public class Bag extends StructuredType {
		/* "bag" "[" typeArg:TypeArg "]" -> StructuredType {cons("Bag")} */
		private Bag() {
			super();
		}

		public Bag(INode node, org.meta_environment.rascal.ast.TypeArg typeArg) {
			this.node = node;
			this.typeArg = typeArg;
		}

		@Override
		public <T> T accept(IASTVisitor<T> visitor) {
			return visitor.visitStructuredTypeBag(this);
		}

		@Override
		public boolean isBag() {
			return true;
		}

		@Override
		public boolean hasTypeArg() {
			return true;
		}

		private org.meta_environment.rascal.ast.TypeArg typeArg;

		@Override
		public org.meta_environment.rascal.ast.TypeArg getTypeArg() {
			return typeArg;
		}

		private void $setTypeArg(org.meta_environment.rascal.ast.TypeArg x) {
			this.typeArg = x;
		}

		public Bag setTypeArg(org.meta_environment.rascal.ast.TypeArg x) {
			Bag z = new Bag();
			z.$setTypeArg(x);
			return z;
		}
	}

	public org.meta_environment.rascal.ast.TypeArg getFirst() {
		throw new UnsupportedOperationException();
	}

	public org.meta_environment.rascal.ast.TypeArg getSecond() {
		throw new UnsupportedOperationException();
	}

	public boolean hasFirst() {
		return false;
	}

	public boolean hasSecond() {
		return false;
	}

	public boolean isMap() {
		return false;
	}

	static public class Map extends StructuredType {
		/*
		 * "map" "[" first:TypeArg "," second:TypeArg "]" -> StructuredType
		 * {cons("Map")}
		 */
		private Map() {
			super();
		}

		public Map(INode node, org.meta_environment.rascal.ast.TypeArg first,
				org.meta_environment.rascal.ast.TypeArg second) {
			this.node = node;
			this.first = first;
			this.second = second;
		}

		@Override
		public <T> T accept(IASTVisitor<T> visitor) {
			return visitor.visitStructuredTypeMap(this);
		}

		@Override
		public boolean isMap() {
			return true;
		}

		@Override
		public boolean hasFirst() {
			return true;
		}

		@Override
		public boolean hasSecond() {
			return true;
		}

		private org.meta_environment.rascal.ast.TypeArg first;

		@Override
		public org.meta_environment.rascal.ast.TypeArg getFirst() {
			return first;
		}

		private void $setFirst(org.meta_environment.rascal.ast.TypeArg x) {
			this.first = x;
		}

		public Map setFirst(org.meta_environment.rascal.ast.TypeArg x) {
			Map z = new Map();
			z.$setFirst(x);
			return z;
		}

		private org.meta_environment.rascal.ast.TypeArg second;

		@Override
		public org.meta_environment.rascal.ast.TypeArg getSecond() {
			return second;
		}

		private void $setSecond(org.meta_environment.rascal.ast.TypeArg x) {
			this.second = x;
		}

		public Map setSecond(org.meta_environment.rascal.ast.TypeArg x) {
			Map z = new Map();
			z.$setSecond(x);
			return z;
		}
	}

	public java.util.List<org.meta_environment.rascal.ast.TypeArg> getArguments() {
		throw new UnsupportedOperationException();
	}

	public boolean hasArguments() {
		return false;
	}

	public boolean isRelation() {
		return false;
	}

	static public class Relation extends StructuredType {
		/*
		 * "rel" "[" arguments:{TypeArg ","}+ "]" -> StructuredType
		 * {cons("Relation")}
		 */
		private Relation() {
			super();
		}

		public Relation(
				INode node,
				java.util.List<org.meta_environment.rascal.ast.TypeArg> arguments) {
			this.node = node;
			this.arguments = arguments;
		}

		@Override
		public <T> T accept(IASTVisitor<T> visitor) {
			return visitor.visitStructuredTypeRelation(this);
		}

		@Override
		public boolean isRelation() {
			return true;
		}

		@Override
		public boolean hasArguments() {
			return true;
		}

		private java.util.List<org.meta_environment.rascal.ast.TypeArg> arguments;

		@Override
		public java.util.List<org.meta_environment.rascal.ast.TypeArg> getArguments() {
			return arguments;
		}

		private void $setArguments(
				java.util.List<org.meta_environment.rascal.ast.TypeArg> x) {
			this.arguments = x;
		}

		public Relation setArguments(
				java.util.List<org.meta_environment.rascal.ast.TypeArg> x) {
			Relation z = new Relation();
			z.$setArguments(x);
			return z;
		}
	}

	public boolean isTuple() {
		return false;
	}

	static public class Tuple extends StructuredType {
		/*
		 * "tuple" "[" arguments:{TypeArg ","}+ "]" -> StructuredType
		 * {cons("Tuple")}
		 */
		private Tuple() {
			super();
		}

		public Tuple(
				INode node,
				java.util.List<org.meta_environment.rascal.ast.TypeArg> arguments) {
			this.node = node;
			this.arguments = arguments;
		}

		@Override
		public <T> T accept(IASTVisitor<T> visitor) {
			return visitor.visitStructuredTypeTuple(this);
		}

		@Override
		public boolean isTuple() {
			return true;
		}

		@Override
		public boolean hasArguments() {
			return true;
		}

		private java.util.List<org.meta_environment.rascal.ast.TypeArg> arguments;

		@Override
		public java.util.List<org.meta_environment.rascal.ast.TypeArg> getArguments() {
			return arguments;
		}

		private void $setArguments(
				java.util.List<org.meta_environment.rascal.ast.TypeArg> x) {
			this.arguments = x;
		}

		public Tuple setArguments(
				java.util.List<org.meta_environment.rascal.ast.TypeArg> x) {
			Tuple z = new Tuple();
			z.$setArguments(x);
			return z;
		}
	}

	public boolean isLex() {
		return false;
	}

	static public class Lex extends StructuredType {
		/* "lex" "[" typeArg:TypeArg "]" -> StructuredType {cons("Lex")} */
		private Lex() {
			super();
		}

		public Lex(INode node, org.meta_environment.rascal.ast.TypeArg typeArg) {
			this.node = node;
			this.typeArg = typeArg;
		}

		@Override
		public <T> T accept(IASTVisitor<T> visitor) {
			return visitor.visitStructuredTypeLex(this);
		}

		@Override
		public boolean isLex() {
			return true;
		}

		@Override
		public boolean hasTypeArg() {
			return true;
		}

		private org.meta_environment.rascal.ast.TypeArg typeArg;

		@Override
		public org.meta_environment.rascal.ast.TypeArg getTypeArg() {
			return typeArg;
		}

		private void $setTypeArg(org.meta_environment.rascal.ast.TypeArg x) {
			this.typeArg = x;
		}

		public Lex setTypeArg(org.meta_environment.rascal.ast.TypeArg x) {
			Lex z = new Lex();
			z.$setTypeArg(x);
			return z;
		}
	}
}