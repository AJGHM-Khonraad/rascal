package org.meta_environment.rascal.ast;

import org.eclipse.imp.pdb.facts.ITree;

public abstract class Assignment extends AbstractAST {
	static public class Addition extends Assignment {
		/* package */Addition(ITree tree) {
			this.tree = tree;
		}

		public IVisitable accept(IASTVisitor visitor) {
			return visitor.visitAssignmentAddition(this);
		}
	}

	static public class Ambiguity extends Assignment {
		private final java.util.List<org.meta_environment.rascal.ast.Assignment> alternatives;

		public Ambiguity(
				java.util.List<org.meta_environment.rascal.ast.Assignment> alternatives) {
			this.alternatives = java.util.Collections
					.unmodifiableList(alternatives);
		}

		public java.util.List<org.meta_environment.rascal.ast.Assignment> getAlternatives() {
			return alternatives;
		}
	}

	static public class Default extends Assignment {
		/* package */Default(ITree tree) {
			this.tree = tree;
		}

		public IVisitable accept(IASTVisitor visitor) {
			return visitor.visitAssignmentDefault(this);
		}
	}

	static public class Division extends Assignment {
		/* package */Division(ITree tree) {
			this.tree = tree;
		}

		public IVisitable accept(IASTVisitor visitor) {
			return visitor.visitAssignmentDivision(this);
		}
	}

	static public class Interesection extends Assignment {
		/* package */Interesection(ITree tree) {
			this.tree = tree;
		}

		public IVisitable accept(IASTVisitor visitor) {
			return visitor.visitAssignmentInteresection(this);
		}
	}

	static public class Product extends Assignment {
		/* package */Product(ITree tree) {
			this.tree = tree;
		}

		public IVisitable accept(IASTVisitor visitor) {
			return visitor.visitAssignmentProduct(this);
		}
	}

	static public class Substraction extends Assignment {
		/* package */Substraction(ITree tree) {
			this.tree = tree;
		}

		public IVisitable accept(IASTVisitor visitor) {
			return visitor.visitAssignmentSubstraction(this);
		}
	}
}
