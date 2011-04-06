/*******************************************************************************
 * Copyright (c) 2009-2011 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   * Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI
 *   * Mark Hills - Mark.Hills@cwi.nl (CWI)
 *   * Arnold Lankamp - Arnold.Lankamp@cwi.nl
*******************************************************************************/
package org.rascalmpl.semantics.dynamic;

import org.eclipse.imp.pdb.facts.INode;
import org.eclipse.imp.pdb.facts.IValue;
import org.joda.time.DateTime;
import org.rascalmpl.interpreter.Evaluator;
import org.rascalmpl.interpreter.result.Result;
import org.rascalmpl.interpreter.staticErrors.DateTimeParseError;

public abstract class JustDate extends org.rascalmpl.ast.JustDate {

	static public class Lexical extends org.rascalmpl.ast.JustDate.Lexical {

		public Lexical(INode __param1, String __param2) {
			super(__param1, __param2);
		}

		@Override
		public Result<IValue> interpret(Evaluator __eval) {
			// Date is of the form $<date>
			String datePart = this.getString().substring(1);
			return createVisitedDate(__eval, datePart, this);
		}
		
		private Result<IValue> createVisitedDate(Evaluator eval, String datePart, org.rascalmpl.ast.JustDate.Lexical x) {
			String isoDate = datePart;
			if (-1 == datePart.indexOf("-")) {
				isoDate = datePart.substring(0, 4) + "-" + datePart.substring(4, 6) + "-" + datePart.substring(6);
			}
			try {
				DateTime justDate = org.joda.time.format.ISODateTimeFormat.dateParser().parseDateTime(isoDate);
				return makeResult(TF.dateTimeType(),
						VF.date(justDate.getYear(), justDate.getMonthOfYear(), justDate.getDayOfMonth()), eval);
			} catch (IllegalArgumentException iae) {
				throw new DateTimeParseError("$" + datePart, x.getLocation());
			}
		}

	}

	public JustDate(INode __param1) {
		super(__param1);
	}

}
