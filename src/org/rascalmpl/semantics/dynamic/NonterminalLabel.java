package org.rascalmpl.semantics.dynamic;

import java.lang.String;
import java.util.List;
import org.eclipse.imp.pdb.facts.INode;
import org.rascalmpl.ast.NullASTVisitor;

public abstract class NonterminalLabel extends org.rascalmpl.ast.NonterminalLabel {

	public NonterminalLabel(INode __param1) {
		super(__param1);
	}

	static public class Lexical extends org.rascalmpl.ast.NonterminalLabel.Lexical {

		public Lexical(INode __param1, String __param2) {
			super(__param1, __param2);
		}

		@Override
		public <T> T __evaluate(NullASTVisitor<T> __eval) {
			return null;
		}

	}

	static public class Ambiguity extends org.rascalmpl.ast.NonterminalLabel.Ambiguity {

		public Ambiguity(INode __param1, List<org.rascalmpl.ast.NonterminalLabel> __param2) {
			super(__param1, __param2);
		}

		@Override
		public <T> T __evaluate(NullASTVisitor<T> __eval) {
			return null;
		}

	}
}