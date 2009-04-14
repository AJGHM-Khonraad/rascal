/**
 * 
 */
package org.meta_environment.rascal.interpreter;

import java.io.IOException;

import jline.ConsoleReader;

import org.eclipse.imp.pdb.facts.IValue;
import org.meta_environment.rascal.ast.Command;
import org.meta_environment.rascal.ast.NullASTVisitor;
import org.meta_environment.rascal.ast.Command.Declaration;
import org.meta_environment.rascal.ast.Command.Import;
import org.meta_environment.rascal.ast.Command.Shell;
import org.meta_environment.rascal.ast.ShellCommand.Edit;
import org.meta_environment.rascal.ast.ShellCommand.History;
import org.meta_environment.rascal.ast.ShellCommand.Quit;
import org.meta_environment.rascal.interpreter.control_exceptions.QuitException;
import org.meta_environment.rascal.interpreter.result.Result;

/*package*/ class CommandEvaluator extends NullASTVisitor<IValue> {
	private final ConsoleReader console;
	private final Evaluator evaluator;

	CommandEvaluator(Evaluator evaluator, ConsoleReader console) {
		this.console = console;
		this.evaluator = evaluator;
	}
	
	public CommandEvaluator(Evaluator evaluator) {
		this.evaluator = evaluator;
		this.console = null;
	}

	public IValue eval(Command command) {
		return command.accept(this);
	}

	@Override
	public IValue visitCommandShell(Shell x) {
		return x.getCommand().accept(this);
	}

	@Override
	public IValue visitCommandDeclaration(Declaration x) {
		return evaluator.eval(x.getDeclaration());
	}

	@Override
	public IValue visitCommandStatement(
			org.meta_environment.rascal.ast.Command.Statement x) {
		return evaluator.eval(x.getStatement());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public IValue visitCommandImport(Import x) {
		Result r = x.getImported().accept(evaluator);
		return r.getValue();
	}

	@Override
	public IValue visitShellCommandQuit(Quit x) {
		throw new QuitException();
	}

	@Override
	public IValue visitShellCommandEdit(Edit x) {
		return null;
	}

	@Override
	public IValue visitShellCommandHistory(History x) {
		try {
			console.printString(console.getHistory().toString());
		} catch (IOException e) {
			// should not happen
		}
		
		return null;
	}
}