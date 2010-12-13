package org.rascalmpl.semantics.dynamic;

import java.util.List;
import org.eclipse.imp.pdb.facts.INode;
import org.eclipse.imp.pdb.facts.IValue;
import org.rascalmpl.ast.DateAndTime;
import org.rascalmpl.ast.JustDate;
import org.rascalmpl.ast.JustTime;
import org.rascalmpl.ast.NullASTVisitor;
import org.rascalmpl.interpreter.Evaluator;
import org.rascalmpl.interpreter.result.Result;

public abstract class DateTimeLiteral extends org.rascalmpl.ast.DateTimeLiteral {

	public DateTimeLiteral(INode __param1) {
		super(__param1);
	}

	static public class DateAndTimeLiteral extends org.rascalmpl.ast.DateTimeLiteral.DateAndTimeLiteral {

		public DateAndTimeLiteral(INode __param1, DateAndTime __param2) {
			super(__param1, __param2);
		}

		@Override
		public Result<IValue> __evaluate(Evaluator __eval) {

			return this.getDateAndTime().__evaluate(__eval);

		}

		@Override
		public <T> T __evaluate(NullASTVisitor<T> __eval) {
			return null;
		}

	}

	static public class Ambiguity extends org.rascalmpl.ast.DateTimeLiteral.Ambiguity {

		public Ambiguity(INode __param1, List<org.rascalmpl.ast.DateTimeLiteral> __param2) {
			super(__param1, __param2);
		}

		@Override
		public <T> T __evaluate(NullASTVisitor<T> __eval) {
			return null;
		}

	}

	static public class TimeLiteral extends org.rascalmpl.ast.DateTimeLiteral.TimeLiteral {

		public TimeLiteral(INode __param1, JustTime __param2) {
			super(__param1, __param2);
		}

		@Override
		public <T> T __evaluate(NullASTVisitor<T> __eval) {
			return null;
		}

		@Override
		public Result<IValue> __evaluate(Evaluator __eval) {

			return this.getTime().__evaluate(__eval);

		}

	}

	static public class DateLiteral extends org.rascalmpl.ast.DateTimeLiteral.DateLiteral {

		public DateLiteral(INode __param1, JustDate __param2) {
			super(__param1, __param2);
		}

		@Override
		public <T> T __evaluate(NullASTVisitor<T> __eval) {
			return null;
		}

		@Override
		public Result<IValue> __evaluate(Evaluator __eval) {

			return this.getDate().__evaluate(__eval);

		}

	}
}