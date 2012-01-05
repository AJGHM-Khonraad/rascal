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
import org.rascalmpl.ast.Expression;
import org.rascalmpl.ast.QualifiedName;
import org.rascalmpl.interpreter.Evaluator;
import org.rascalmpl.interpreter.control_exceptions.QuitException;
import org.rascalmpl.interpreter.env.ModuleEnvironment;
import org.rascalmpl.interpreter.result.Result;
import org.rascalmpl.interpreter.utils.Names;

public abstract class ShellCommand extends org.rascalmpl.ast.ShellCommand {

	static public class Edit extends org.rascalmpl.ast.ShellCommand.Edit {
		public Edit(IConstructor __param1, QualifiedName __param2) {
			super(__param1, __param2);
		}

		@Override
		public Result<IValue> interpret(Evaluator __eval) {
			return org.rascalmpl.interpreter.result.ResultFactory.nothing();
		}
	}

	static public class Help extends org.rascalmpl.ast.ShellCommand.Help {

		public Help(IConstructor __param1) {
			super(__param1);
		}

		@Override
		public Result<IValue> interpret(Evaluator __eval) {

			__eval.setCurrentAST(this);
			__eval.printHelpMessage(__eval.getStdOut());
			return org.rascalmpl.interpreter.result.ResultFactory.nothing();

		}

	}

	static public class History extends org.rascalmpl.ast.ShellCommand.History {

		public History(IConstructor __param1) {
			super(__param1);
		}

	}

	static public class ListDeclarations extends
			org.rascalmpl.ast.ShellCommand.ListDeclarations {

		public ListDeclarations(IConstructor __param1) {
			super(__param1);
		}

		@Override
		public Result<IValue> interpret(Evaluator __eval) {
			return org.rascalmpl.interpreter.result.ResultFactory.nothing();
		}

	}

	static public class Quit extends org.rascalmpl.ast.ShellCommand.Quit {

		public Quit(IConstructor __param1) {
			super(__param1);
		}

		@Override
		public Result<IValue> interpret(Evaluator __eval) {
			throw new QuitException();
		}

	}

	static public class SetOption extends
			org.rascalmpl.ast.ShellCommand.SetOption {

		public SetOption(IConstructor __param1, QualifiedName __param2,
				Expression __param3) {
			super(__param1, __param2, __param3);
		}

		@Override
		public Result<IValue> interpret(Evaluator __eval) {

			String name = "rascal." + ((org.rascalmpl.semantics.dynamic.QualifiedName.Default) this.getName()).fullName();
			String value = this.getExpression().interpret(__eval).getValue()
					.toString();

			java.lang.System.setProperty(name, value);

			__eval.updateProperties();

			return org.rascalmpl.interpreter.result.ResultFactory.nothing();

		}

	}

	static public class Test extends org.rascalmpl.ast.ShellCommand.Test {

		public Test(IConstructor __param1) {
			super(__param1);
		}

		@Override
		public Result<IValue> interpret(Evaluator __eval) {
			return org.rascalmpl.interpreter.result.ResultFactory.bool(__eval.runTests(__eval.getMonitor()), __eval);
		}
	}

	static public class Unimport extends
			org.rascalmpl.ast.ShellCommand.Unimport {

		public Unimport(IConstructor __param1, QualifiedName __param2) {
			super(__param1, __param2);
		}

		@Override
		public Result<IValue> interpret(Evaluator __eval) {

			((ModuleEnvironment) __eval.getCurrentEnvt().getRoot())
					.unImport(this.getName().toString());
			return org.rascalmpl.interpreter.result.ResultFactory.nothing();

		}

	}

	public ShellCommand(IConstructor __param1) {
		super(__param1);
	}
}
