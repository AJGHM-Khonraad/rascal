package org.rascalmpl.semantics.dynamic;

import java.util.List;

import org.eclipse.imp.pdb.facts.INode;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;
import org.rascalmpl.interpreter.Evaluator;
import org.rascalmpl.interpreter.env.Environment;
import org.rascalmpl.interpreter.result.Result;

public abstract class DateAndTime extends org.rascalmpl.ast.DateAndTime {

	static public class Lexical extends org.rascalmpl.ast.DateAndTime.Lexical {

		public Lexical(INode __param1, String __param2) {
			super(__param1, __param2);
		}

		@Override
		public Result<IValue> interpret(Evaluator __eval) {
			// Split into date and time components; of the form $<date>T<time>
			String dtPart = this.getString().substring(1);
			String datePart = dtPart.substring(0, dtPart.indexOf("T"));
			String timePart = dtPart.substring(dtPart.indexOf("T") + 1);

			return __eval.createVisitedDateTime(datePart, timePart, this);
		}

		@Override
		public Type typeOf(Environment env) {
			return TF.dateTimeType();
		}

	}

	public DateAndTime(INode __param1) {
		super(__param1);
	}
}
