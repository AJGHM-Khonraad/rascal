package org.meta_environment.rascal.ast;

import org.eclipse.imp.pdb.facts.ITree;
import org.meta_environment.rascal.interpreter.RascalBug;

public class JavaFunctionBody extends FunctionBody {
	private final String string;

	public JavaFunctionBody(ITree tree, String string) {
		this.tree = tree;
		this.string = string;
	}

	@Override
	public <T> T accept(IASTVisitor<T> v) {
		throw new RascalBug("Can not visit JavaFunctionBody");
	}

	public String getString() {
		return string;
	}
}