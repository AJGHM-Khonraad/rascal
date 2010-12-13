package org.rascalmpl.semantics.dynamic;

import java.util.List;
import org.eclipse.imp.pdb.facts.INode;
import org.rascalmpl.ast.Name;
import org.rascalmpl.ast.NullASTVisitor;

public abstract class Target extends org.rascalmpl.ast.Target {

	public Target(INode __param1) {
		super(__param1);
	}

	static public class Labeled extends org.rascalmpl.ast.Target.Labeled {

		public Labeled(INode __param1, Name __param2) {
			super(__param1, __param2);
		}

		@Override
		public <T> T __evaluate(NullASTVisitor<T> __eval) {
			return null;
		}

	}

	static public class Empty extends org.rascalmpl.ast.Target.Empty {

		public Empty(INode __param1) {
			super(__param1);
		}

		@Override
		public <T> T __evaluate(NullASTVisitor<T> __eval) {
			return null;
		}

	}

	static public class Ambiguity extends org.rascalmpl.ast.Target.Ambiguity {

		public Ambiguity(INode __param1, List<org.rascalmpl.ast.Target> __param2) {
			super(__param1, __param2);
		}

		@Override
		public <T> T __evaluate(NullASTVisitor<T> __eval) {
			return null;
		}

	}
}