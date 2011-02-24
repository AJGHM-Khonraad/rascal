package org.rascalmpl.semantics.dynamic;

import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.IValue;
import org.rascalmpl.ast.DecimalIntegerLiteral.Lexical;
import org.rascalmpl.interpreter.Evaluator;
import org.rascalmpl.interpreter.result.Result;

public abstract class IntegerLiteral extends org.rascalmpl.ast.IntegerLiteral {

	static public class DecimalIntegerLiteral extends
			org.rascalmpl.ast.IntegerLiteral.DecimalIntegerLiteral {

		public DecimalIntegerLiteral(ISourceLocation __param1,
				org.rascalmpl.ast.DecimalIntegerLiteral __param2) {
			super(__param1, __param2);
		}

		@Override
		public Result<IValue> interpret(Evaluator __eval) {
			String str = ((Lexical) this.getDecimal()).getString();
			return org.rascalmpl.interpreter.result.ResultFactory
					.makeResult(org.rascalmpl.interpreter.Evaluator.__getTf()
							.integerType(), __eval.__getVf().integer(str),
							__eval);
		}

	}

	static public class HexIntegerLiteral extends
			org.rascalmpl.ast.IntegerLiteral.HexIntegerLiteral {

		public HexIntegerLiteral(ISourceLocation __param1,
				org.rascalmpl.ast.HexIntegerLiteral __param2) {
			super(__param1, __param2);
		}

		@Override
		public Result<IValue> interpret(Evaluator __eval) {
			return this.getHex().interpret(__eval);
		}

	}

	static public class OctalIntegerLiteral extends
			org.rascalmpl.ast.IntegerLiteral.OctalIntegerLiteral {

		public OctalIntegerLiteral(ISourceLocation __param1,
				org.rascalmpl.ast.OctalIntegerLiteral __param2) {
			super(__param1, __param2);
		}

		@Override
		public Result<IValue> interpret(Evaluator __eval) {
			return this.getOctal().interpret(__eval);
		}

	}

	public IntegerLiteral(ISourceLocation __param1) {
		super(__param1);
	}
}
