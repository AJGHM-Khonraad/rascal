package org.rascalmpl.semantics.dynamic;

import java.util.List;
import org.eclipse.imp.pdb.facts.INode;
import org.rascalmpl.ast.Name;
import org.rascalmpl.ast.NullASTVisitor;
import org.rascalmpl.ast.TypeArg;

public abstract class Variant extends org.rascalmpl.ast.Variant {

	public Variant(INode __param1) {
		super(__param1);
	}

	static public class Ambiguity extends org.rascalmpl.ast.Variant.Ambiguity {

		public Ambiguity(INode __param1, List<org.rascalmpl.ast.Variant> __param2) {
			super(__param1, __param2);
		}

		@Override
		public <T> T __evaluate(NullASTVisitor<T> __eval) {
			return null;
		}

	}

	static public class NAryConstructor extends org.rascalmpl.ast.Variant.NAryConstructor {

		public NAryConstructor(INode __param1, Name __param2, List<TypeArg> __param3) {
			super(__param1, __param2, __param3);
		}

		@Override
		public <T> T __evaluate(NullASTVisitor<T> __eval) {
			return null;
		}

	}
}