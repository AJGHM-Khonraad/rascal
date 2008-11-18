package org.meta_environment.rascal.ast;

import org.eclipse.imp.pdb.facts.ITree;

public abstract class Assignable extends AbstractAST {
	static public class Ambiguity extends Assignable {
		private final java.util.List<org.meta_environment.rascal.ast.Assignable> alternatives;

		public Ambiguity(
				java.util.List<org.meta_environment.rascal.ast.Assignable> alternatives) {
			this.alternatives = java.util.Collections
					.unmodifiableList(alternatives);
		}

		public java.util.List<org.meta_environment.rascal.ast.Assignable> getAlternatives() {
			return alternatives;
		}
	}

	static public class Annotation extends Assignable {
		private org.meta_environment.rascal.ast.Expression annotation;
		private org.meta_environment.rascal.ast.Assignable receiver;

		/*
		 * receiver:Assignable "@" annotation:Expression -> Assignable
		 * {cons("Annotation")}
		 */
		private Annotation() {
		}

		/* package */Annotation(ITree tree,
				org.meta_environment.rascal.ast.Assignable receiver,
				org.meta_environment.rascal.ast.Expression annotation) {
			this.tree = tree;
			this.receiver = receiver;
			this.annotation = annotation;
		}

		private void $setAnnotation(org.meta_environment.rascal.ast.Expression x) {
			this.annotation = x;
		}

		private void $setReceiver(org.meta_environment.rascal.ast.Assignable x) {
			this.receiver = x;
		}

		public <T> T accept(IASTVisitor<T> visitor) {
			return visitor.visitAssignableAnnotation(this);
		}

		@Override
		public org.meta_environment.rascal.ast.Expression getAnnotation() {
			return annotation;
		}

		@Override
		public org.meta_environment.rascal.ast.Assignable getReceiver() {
			return receiver;
		}

		@Override
		public boolean hasAnnotation() {
			return true;
		}

		@Override
		public boolean hasReceiver() {
			return true;
		}

		@Override
		public boolean isAnnotation() {
			return true;
		}

		public Annotation setAnnotation(
				org.meta_environment.rascal.ast.Expression x) {
			Annotation z = new Annotation();
			z.$setAnnotation(x);
			return z;
		}

		public Annotation setReceiver(
				org.meta_environment.rascal.ast.Assignable x) {
			Annotation z = new Annotation();
			z.$setReceiver(x);
			return z;
		}
	}

	static public class Constructor extends Assignable {
		private java.util.List<org.meta_environment.rascal.ast.Assignable> arguments;
		private org.meta_environment.rascal.ast.Name name;

		/*
		 * name:Name "(" arguments:{Assignable ","}+ ")" -> Assignable
		 * {cons("Constructor")}
		 */
		private Constructor() {
		}

		/* package */Constructor(
				ITree tree,
				org.meta_environment.rascal.ast.Name name,
				java.util.List<org.meta_environment.rascal.ast.Assignable> arguments) {
			this.tree = tree;
			this.name = name;
			this.arguments = arguments;
		}

		private void $setArguments(
				java.util.List<org.meta_environment.rascal.ast.Assignable> x) {
			this.arguments = x;
		}

		private void $setName(org.meta_environment.rascal.ast.Name x) {
			this.name = x;
		}

		public <T> T accept(IASTVisitor<T> visitor) {
			return visitor.visitAssignableConstructor(this);
		}

		@Override
		public java.util.List<org.meta_environment.rascal.ast.Assignable> getArguments() {
			return arguments;
		}

		@Override
		public org.meta_environment.rascal.ast.Name getName() {
			return name;
		}

		@Override
		public boolean hasArguments() {
			return true;
		}

		@Override
		public boolean hasName() {
			return true;
		}

		@Override
		public boolean isConstructor() {
			return true;
		}

		public Constructor setArguments(
				java.util.List<org.meta_environment.rascal.ast.Assignable> x) {
			Constructor z = new Constructor();
			z.$setArguments(x);
			return z;
		}

		public Constructor setName(org.meta_environment.rascal.ast.Name x) {
			Constructor z = new Constructor();
			z.$setName(x);
			return z;
		}
	}

	static public class FieldAccess extends Assignable {
		private org.meta_environment.rascal.ast.Name field;
		private org.meta_environment.rascal.ast.Assignable receiver;

		/*
		 * receiver:Assignable "." field:Name -> Assignable
		 * {cons("FieldAccess")}
		 */
		private FieldAccess() {
		}

		/* package */FieldAccess(ITree tree,
				org.meta_environment.rascal.ast.Assignable receiver,
				org.meta_environment.rascal.ast.Name field) {
			this.tree = tree;
			this.receiver = receiver;
			this.field = field;
		}

		private void $setField(org.meta_environment.rascal.ast.Name x) {
			this.field = x;
		}

		private void $setReceiver(org.meta_environment.rascal.ast.Assignable x) {
			this.receiver = x;
		}

		public <T> T accept(IASTVisitor<T> visitor) {
			return visitor.visitAssignableFieldAccess(this);
		}

		@Override
		public org.meta_environment.rascal.ast.Name getField() {
			return field;
		}

		@Override
		public org.meta_environment.rascal.ast.Assignable getReceiver() {
			return receiver;
		}

		@Override
		public boolean hasField() {
			return true;
		}

		@Override
		public boolean hasReceiver() {
			return true;
		}

		@Override
		public boolean isFieldAccess() {
			return true;
		}

		public FieldAccess setField(org.meta_environment.rascal.ast.Name x) {
			FieldAccess z = new FieldAccess();
			z.$setField(x);
			return z;
		}

		public FieldAccess setReceiver(
				org.meta_environment.rascal.ast.Assignable x) {
			FieldAccess z = new FieldAccess();
			z.$setReceiver(x);
			return z;
		}
	}

	static public class IfDefined extends Assignable {
		private org.meta_environment.rascal.ast.Expression condition;
		private org.meta_environment.rascal.ast.Assignable receiver;

		/*
		 * receiver:Assignable "?" condition:Expression -> Assignable
		 * {cons("IfDefined")}
		 */
		private IfDefined() {
		}

		/* package */IfDefined(ITree tree,
				org.meta_environment.rascal.ast.Assignable receiver,
				org.meta_environment.rascal.ast.Expression condition) {
			this.tree = tree;
			this.receiver = receiver;
			this.condition = condition;
		}

		private void $setCondition(org.meta_environment.rascal.ast.Expression x) {
			this.condition = x;
		}

		private void $setReceiver(org.meta_environment.rascal.ast.Assignable x) {
			this.receiver = x;
		}

		public <T> T accept(IASTVisitor<T> visitor) {
			return visitor.visitAssignableIfDefined(this);
		}

		@Override
		public org.meta_environment.rascal.ast.Expression getCondition() {
			return condition;
		}

		@Override
		public org.meta_environment.rascal.ast.Assignable getReceiver() {
			return receiver;
		}

		@Override
		public boolean hasCondition() {
			return true;
		}

		@Override
		public boolean hasReceiver() {
			return true;
		}

		@Override
		public boolean isIfDefined() {
			return true;
		}

		public IfDefined setCondition(
				org.meta_environment.rascal.ast.Expression x) {
			IfDefined z = new IfDefined();
			z.$setCondition(x);
			return z;
		}

		public IfDefined setReceiver(
				org.meta_environment.rascal.ast.Assignable x) {
			IfDefined z = new IfDefined();
			z.$setReceiver(x);
			return z;
		}
	}

	static public class Subscript extends Assignable {
		private org.meta_environment.rascal.ast.Assignable receiver;
		private org.meta_environment.rascal.ast.Expression subscript;

		/*
		 * receiver:Assignable "[" subscript:Expression "]" -> Assignable
		 * {cons("Subscript")}
		 */
		private Subscript() {
		}

		/* package */Subscript(ITree tree,
				org.meta_environment.rascal.ast.Assignable receiver,
				org.meta_environment.rascal.ast.Expression subscript) {
			this.tree = tree;
			this.receiver = receiver;
			this.subscript = subscript;
		}

		private void $setReceiver(org.meta_environment.rascal.ast.Assignable x) {
			this.receiver = x;
		}

		private void $setSubscript(org.meta_environment.rascal.ast.Expression x) {
			this.subscript = x;
		}

		public <T> T accept(IASTVisitor<T> visitor) {
			return visitor.visitAssignableSubscript(this);
		}

		@Override
		public org.meta_environment.rascal.ast.Assignable getReceiver() {
			return receiver;
		}

		@Override
		public org.meta_environment.rascal.ast.Expression getSubscript() {
			return subscript;
		}

		@Override
		public boolean hasReceiver() {
			return true;
		}

		@Override
		public boolean hasSubscript() {
			return true;
		}

		@Override
		public boolean isSubscript() {
			return true;
		}

		public Subscript setReceiver(
				org.meta_environment.rascal.ast.Assignable x) {
			Subscript z = new Subscript();
			z.$setReceiver(x);
			return z;
		}

		public Subscript setSubscript(
				org.meta_environment.rascal.ast.Expression x) {
			Subscript z = new Subscript();
			z.$setSubscript(x);
			return z;
		}
	}

	static public class Tuple extends Assignable {
		private org.meta_environment.rascal.ast.Assignable first;
		private java.util.List<org.meta_environment.rascal.ast.Assignable> rest;

		/*
		 * "<" first:Assignable "," rest:{Assignable ","}+ ">" -> Assignable
		 * {cons("Tuple")}
		 */
		private Tuple() {
		}

		/* package */Tuple(ITree tree,
				org.meta_environment.rascal.ast.Assignable first,
				java.util.List<org.meta_environment.rascal.ast.Assignable> rest) {
			this.tree = tree;
			this.first = first;
			this.rest = rest;
		}

		private void $setFirst(org.meta_environment.rascal.ast.Assignable x) {
			this.first = x;
		}

		private void $setRest(
				java.util.List<org.meta_environment.rascal.ast.Assignable> x) {
			this.rest = x;
		}

		public <T> T accept(IASTVisitor<T> visitor) {
			return visitor.visitAssignableTuple(this);
		}

		@Override
		public org.meta_environment.rascal.ast.Assignable getFirst() {
			return first;
		}

		@Override
		public java.util.List<org.meta_environment.rascal.ast.Assignable> getRest() {
			return rest;
		}

		@Override
		public boolean hasFirst() {
			return true;
		}

		@Override
		public boolean hasRest() {
			return true;
		}

		@Override
		public boolean isTuple() {
			return true;
		}

		public Tuple setFirst(org.meta_environment.rascal.ast.Assignable x) {
			Tuple z = new Tuple();
			z.$setFirst(x);
			return z;
		}

		public Tuple setRest(
				java.util.List<org.meta_environment.rascal.ast.Assignable> x) {
			Tuple z = new Tuple();
			z.$setRest(x);
			return z;
		}
	}

	static public class Variable extends Assignable {
		private org.meta_environment.rascal.ast.QualifiedName qualifiedName;

		/* qualifiedName:QualifiedName -> Assignable {cons("Variable")} */
		private Variable() {
		}

		/* package */Variable(ITree tree,
				org.meta_environment.rascal.ast.QualifiedName qualifiedName) {
			this.tree = tree;
			this.qualifiedName = qualifiedName;
		}

		private void $setQualifiedName(
				org.meta_environment.rascal.ast.QualifiedName x) {
			this.qualifiedName = x;
		}

		public <T> T accept(IASTVisitor<T> visitor) {
			return visitor.visitAssignableVariable(this);
		}

		@Override
		public org.meta_environment.rascal.ast.QualifiedName getQualifiedName() {
			return qualifiedName;
		}

		@Override
		public boolean hasQualifiedName() {
			return true;
		}

		@Override
		public boolean isVariable() {
			return true;
		}

		public Variable setQualifiedName(
				org.meta_environment.rascal.ast.QualifiedName x) {
			Variable z = new Variable();
			z.$setQualifiedName(x);
			return z;
		}
	}

	public org.meta_environment.rascal.ast.Expression getAnnotation() {
		throw new UnsupportedOperationException();
	}

	public java.util.List<org.meta_environment.rascal.ast.Assignable> getArguments() {
		throw new UnsupportedOperationException();
	}

	public org.meta_environment.rascal.ast.Expression getCondition() {
		throw new UnsupportedOperationException();
	}

	public org.meta_environment.rascal.ast.Name getField() {
		throw new UnsupportedOperationException();
	}

	public org.meta_environment.rascal.ast.Assignable getFirst() {
		throw new UnsupportedOperationException();
	}

	public org.meta_environment.rascal.ast.Name getName() {
		throw new UnsupportedOperationException();
	}

	public org.meta_environment.rascal.ast.QualifiedName getQualifiedName() {
		throw new UnsupportedOperationException();
	}

	public org.meta_environment.rascal.ast.Assignable getReceiver() {
		throw new UnsupportedOperationException();
	}

	public java.util.List<org.meta_environment.rascal.ast.Assignable> getRest() {
		throw new UnsupportedOperationException();
	}

	public org.meta_environment.rascal.ast.Expression getSubscript() {
		throw new UnsupportedOperationException();
	}

	public boolean hasAnnotation() {
		return false;
	}

	public boolean hasArguments() {
		return false;
	}

	public boolean hasCondition() {
		return false;
	}

	public boolean hasField() {
		return false;
	}

	public boolean hasFirst() {
		return false;
	}

	public boolean hasName() {
		return false;
	}

	public boolean hasQualifiedName() {
		return false;
	}

	public boolean hasReceiver() {
		return false;
	}

	public boolean hasRest() {
		return false;
	}

	public boolean hasSubscript() {
		return false;
	}

	public boolean isAnnotation() {
		return false;
	}

	public boolean isConstructor() {
		return false;
	}

	public boolean isFieldAccess() {
		return false;
	}

	public boolean isIfDefined() {
		return false;
	}

	public boolean isSubscript() {
		return false;
	}

	public boolean isTuple() {
		return false;
	}

	public boolean isVariable() {
		return false;
	}
}
