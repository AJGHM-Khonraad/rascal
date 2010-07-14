package org.rascalmpl.interpreter.staticErrors;

import org.rascalmpl.ast.QualifiedName;
import org.rascalmpl.interpreter.utils.Names;

public class AssignmentToFinalError extends StaticError {
	private static final long serialVersionUID = -899861652329215608L;

	public AssignmentToFinalError(QualifiedName x) {
		super("can not assign to a final, such as a declared function, constructor or a final variable (" + Names.fullName(x)+ ")" , x);
	}

}
