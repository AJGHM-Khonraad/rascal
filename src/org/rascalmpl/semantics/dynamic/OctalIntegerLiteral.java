package org.rascalmpl.semantics.dynamic;

import java.lang.String;
import java.math.BigInteger;
import java.util.List;
import org.eclipse.imp.pdb.facts.INode;
import org.eclipse.imp.pdb.facts.IValue;
import org.rascalmpl.interpreter.Evaluator;
import org.rascalmpl.interpreter.result.Result;

public abstract class OctalIntegerLiteral extends org.rascalmpl.ast.OctalIntegerLiteral {

	public OctalIntegerLiteral(INode __param1) {
		super(__param1);
	}

	static public class Ambiguity extends org.rascalmpl.ast.OctalIntegerLiteral.Ambiguity {

		public Ambiguity(INode __param1, List<org.rascalmpl.ast.OctalIntegerLiteral> __param2) {
			super(__param1, __param2);
		}


	}

	static public class Lexical extends org.rascalmpl.ast.OctalIntegerLiteral.Lexical {

		public Lexical(INode __param1, String __param2) {
			super(__param1, __param2);
		}


		@Override
		public Result<IValue> interpret(Evaluator __eval) {

			return org.rascalmpl.interpreter.result.ResultFactory.makeResult(org.rascalmpl.interpreter.Evaluator.__getTf().integerType(),
					__eval.__getVf().integer(new BigInteger(this.getString(), 8).toString()), __eval);

		}

	}
}
