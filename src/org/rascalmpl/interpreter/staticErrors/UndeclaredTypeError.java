package org.rascalmpl.interpreter.staticErrors;

import org.rascalmpl.ast.AbstractAST;

public class UndeclaredTypeError extends StaticError {
	private static final long serialVersionUID = -2394719759439179575L;
	private String name;

	public UndeclaredTypeError(String name, AbstractAST ast) {
		super("Undeclared type: " + name, ast);
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
