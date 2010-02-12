package org.rascalmpl.interpreter.staticErrors;

import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.type.Type;
import org.rascalmpl.ast.AbstractAST;

public class UnexpectedTypeError extends StaticError {
	private static final long serialVersionUID = -9009407553448884728L;
	
	public UnexpectedTypeError(Type expected, Type got, AbstractAST ast) {
		super("Expected " + expected + ", but got " + got, ast);
//		printStackTrace();
	}
	
	public UnexpectedTypeError(Type expected, Type got, ISourceLocation loc) {
		super("Expected " + expected + ", but got " + got, loc);
//		printStackTrace();
	}
}
