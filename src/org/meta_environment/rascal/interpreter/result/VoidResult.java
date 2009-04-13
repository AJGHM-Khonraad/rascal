package org.meta_environment.rascal.interpreter.result;

import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;
import org.meta_environment.rascal.ast.AbstractAST;


public class VoidResult extends Result<VoidResult.Void> {
	abstract class Void implements IValue { }

	public VoidResult(Type type, AbstractAST ast) {
		super(type, null, ast);
	}
	

}
