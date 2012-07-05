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

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IValue;
import org.rascalmpl.ast.DateAndTime;
import org.rascalmpl.ast.JustDate;
import org.rascalmpl.ast.JustTime;
import org.rascalmpl.interpreter.IEvaluator;
import org.rascalmpl.interpreter.result.Result;

public abstract class DateTimeLiteral extends org.rascalmpl.ast.DateTimeLiteral {

	static public class DateAndTimeLiteral extends
			org.rascalmpl.ast.DateTimeLiteral.DateAndTimeLiteral {

		public DateAndTimeLiteral(IConstructor __param1, DateAndTime __param2) {
			super(__param1, __param2);
		}

		@Override
		public Result<IValue> interpret(IEvaluator<Result<IValue>> __eval) {
			return this.getDateAndTime().interpret(__eval);
		}

	}

	static public class DateLiteral extends
			org.rascalmpl.ast.DateTimeLiteral.DateLiteral {

		public DateLiteral(IConstructor __param1, JustDate __param2) {
			super(__param1, __param2);
		}

		@Override
		public Result<IValue> interpret(IEvaluator<Result<IValue>> __eval) {
			return this.getDate().interpret(__eval);
		}

	}

	static public class TimeLiteral extends
			org.rascalmpl.ast.DateTimeLiteral.TimeLiteral {

		public TimeLiteral(IConstructor __param1, JustTime __param2) {
			super(__param1, __param2);
		}

		@Override
		public Result<IValue> interpret(IEvaluator<Result<IValue>> __eval) {
			return this.getTime().interpret(__eval);
		}

	}

	public DateTimeLiteral(IConstructor __param1) {
		super(__param1);
	}
}
