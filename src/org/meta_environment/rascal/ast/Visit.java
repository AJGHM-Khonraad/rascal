package org.meta_environment.rascal.ast;

import org.eclipse.imp.pdb.facts.ITree;

public abstract class Visit extends AbstractAST {
	static public class Ambiguity extends Visit {
		private final java.util.List<org.meta_environment.rascal.ast.Visit> alternatives;

		public Ambiguity(
				java.util.List<org.meta_environment.rascal.ast.Visit> alternatives) {
			this.alternatives = java.util.Collections
					.unmodifiableList(alternatives);
		}

		public java.util.List<org.meta_environment.rascal.ast.Visit> getAlternatives() {
			return alternatives;
		}
	}

	static public class DefaultStrategy extends Visit {
		private java.util.List<org.meta_environment.rascal.ast.Case> cases;
		private org.meta_environment.rascal.ast.Expression subject;

		/*
		 * "visit" "(" subject:Expression ")" "{" cases:Case+ "}" -> Visit
		 * {cons("DefaultStrategy")}
		 */
		private DefaultStrategy() {
		}

		/* package */DefaultStrategy(ITree tree,
				org.meta_environment.rascal.ast.Expression subject,
				java.util.List<org.meta_environment.rascal.ast.Case> cases) {
			this.tree = tree;
			this.subject = subject;
			this.cases = cases;
		}

		private void $setCases(
				java.util.List<org.meta_environment.rascal.ast.Case> x) {
			this.cases = x;
		}

		private void $setSubject(org.meta_environment.rascal.ast.Expression x) {
			this.subject = x;
		}

		public IVisitable accept(IASTVisitor visitor) {
			return visitor.visitVisitDefaultStrategy(this);
		}

		public java.util.List<org.meta_environment.rascal.ast.Case> getCases() {
			return cases;
		}

		public org.meta_environment.rascal.ast.Expression getSubject() {
			return subject;
		}

		public DefaultStrategy setCases(
				java.util.List<org.meta_environment.rascal.ast.Case> x) {
			DefaultStrategy z = new DefaultStrategy();
			z.$setCases(x);
			return z;
		}

		public DefaultStrategy setSubject(
				org.meta_environment.rascal.ast.Expression x) {
			DefaultStrategy z = new DefaultStrategy();
			z.$setSubject(x);
			return z;
		}
	}

	static public class GivenStrategy extends Visit {
		private java.util.List<org.meta_environment.rascal.ast.Case> cases;
		private org.meta_environment.rascal.ast.Strategy strategy;
		private org.meta_environment.rascal.ast.Expression subject;

		/*
		 * strategy:Strategy "visit" "(" subject:Expression ")" "{" cases:Case+
		 * "}" -> Visit {cons("GivenStrategy")}
		 */
		private GivenStrategy() {
		}

		/* package */GivenStrategy(ITree tree,
				org.meta_environment.rascal.ast.Strategy strategy,
				org.meta_environment.rascal.ast.Expression subject,
				java.util.List<org.meta_environment.rascal.ast.Case> cases) {
			this.tree = tree;
			this.strategy = strategy;
			this.subject = subject;
			this.cases = cases;
		}

		private void $setCases(
				java.util.List<org.meta_environment.rascal.ast.Case> x) {
			this.cases = x;
		}

		private void $setStrategy(org.meta_environment.rascal.ast.Strategy x) {
			this.strategy = x;
		}

		private void $setSubject(org.meta_environment.rascal.ast.Expression x) {
			this.subject = x;
		}

		public IVisitable accept(IASTVisitor visitor) {
			return visitor.visitVisitGivenStrategy(this);
		}

		public java.util.List<org.meta_environment.rascal.ast.Case> getCases() {
			return cases;
		}

		public org.meta_environment.rascal.ast.Strategy getStrategy() {
			return strategy;
		}

		public org.meta_environment.rascal.ast.Expression getSubject() {
			return subject;
		}

		public GivenStrategy setCases(
				java.util.List<org.meta_environment.rascal.ast.Case> x) {
			GivenStrategy z = new GivenStrategy();
			z.$setCases(x);
			return z;
		}

		public GivenStrategy setStrategy(
				org.meta_environment.rascal.ast.Strategy x) {
			GivenStrategy z = new GivenStrategy();
			z.$setStrategy(x);
			return z;
		}

		public GivenStrategy setSubject(
				org.meta_environment.rascal.ast.Expression x) {
			GivenStrategy z = new GivenStrategy();
			z.$setSubject(x);
			return z;
		}
	}
}
