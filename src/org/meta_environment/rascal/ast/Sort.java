package org.meta_environment.rascal.ast;

public abstract class Sort extends AbstractAST {
	static public class Ambiguity extends Sort {
		private final java.util.List<org.meta_environment.rascal.ast.Sort> alternatives;

		public Ambiguity(
				java.util.List<org.meta_environment.rascal.ast.Sort> alternatives) {
			this.alternatives = java.util.Collections
					.unmodifiableList(alternatives);
		}

		public java.util.List<org.meta_environment.rascal.ast.Sort> getAlternatives() {
			return alternatives;
		}
	}

	static public class Lexical extends Sort {
		/* head:[A-Z] -> Sort */
	}
}
