package org.meta_environment.rascal.interpreter.staticErrors;

import org.eclipse.imp.pdb.facts.ISourceLocation;

public class SyntaxError extends StaticError {
	private static final long serialVersionUID = 333331541118811177L;
	 
	public SyntaxError(String inWhat, ISourceLocation loc) {
		super("Syntax error in " + inWhat, loc);
	}
}
