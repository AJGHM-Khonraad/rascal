/**
 * 
 */
package org.rascalmpl.interpreter;

import java.io.IOException;
import java.io.PrintWriter;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.rascalmpl.ast.Command;
import org.rascalmpl.ast.Import.Default;
import org.rascalmpl.interpreter.env.GlobalEnvironment;
import org.rascalmpl.interpreter.env.ModuleEnvironment;
import org.rascalmpl.interpreter.result.Result;
import org.rascalmpl.parser.ConsoleParser;
import org.rascalmpl.uri.FileURIResolver;

public class CommandEvaluator extends Evaluator {
	private ConsoleParser parser;

	
	public CommandEvaluator(IValueFactory f, PrintWriter stderr, PrintWriter stdout,
			ModuleEnvironment scope, GlobalEnvironment heap) {
		this(f, stderr, stdout, scope, heap, new ConsoleParser(scope));
	}
	 
	public CommandEvaluator(IValueFactory vf, PrintWriter stderr, PrintWriter stdout,
			ModuleEnvironment root, GlobalEnvironment heap,
			ConsoleParser consoleParser) {
		super(vf, stderr, stdout, root, heap, consoleParser);
		this.parser = consoleParser;
	}
	
	public IConstructor parseCommand(String command) throws IOException {
		return parser.parseCommand(command);
	}
	
	public Result<IValue> eval(Command command) {
		return command.accept(this);
	}
	
	@Override
	protected void evalSDFModule(Default x) {
		if (currentEnvt == rootScope) {
			parser.addSdfImportForImportDefault(x);
		}
		super.evalSDFModule(x);
	}
	
	public IConstructor parseModule(String module, ModuleEnvironment env) throws IOException {
		return loader.parseModule(FileURIResolver.STDIN_URI, module, env);
	}


}