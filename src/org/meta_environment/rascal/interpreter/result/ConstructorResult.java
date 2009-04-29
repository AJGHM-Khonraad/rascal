package org.meta_environment.rascal.interpreter.result;

import static org.meta_environment.rascal.interpreter.result.ResultFactory.makeResult;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.INode;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;
import org.eclipse.imp.pdb.facts.type.TypeStore;
import org.meta_environment.rascal.ast.AbstractAST;
import org.meta_environment.rascal.interpreter.EvaluatorContext;
import org.meta_environment.rascal.interpreter.RuntimeExceptionFactory;
import org.meta_environment.rascal.interpreter.staticErrors.UndeclaredFieldError;
import org.meta_environment.rascal.interpreter.staticErrors.UnexpectedTypeError;

public class ConstructorResult extends NodeResult {

	public ConstructorResult(Type type, IConstructor cons, EvaluatorContext ctx) {
		super(type, cons, ctx);
	}
	
	@Override
	public IConstructor getValue() {
		return (IConstructor)super.getValue();
	}

	@Override
	public <U extends IValue> Result<U> fieldAccess(String name, TypeStore store, EvaluatorContext ctx) {
		if (!getType().hasField(name, store)) {
			throw new UndeclaredFieldError(name, getType(), ctx.getCurrentAST());
		}
		Type nodeType = getValue().getConstructorType();
		if (!nodeType.hasField(name)) {
			throw RuntimeExceptionFactory.noSuchField(name, ctx.getCurrentAST(), null);
		}				
		int index = nodeType.getFieldIndex(name);
		return makeResult(nodeType.getFieldType(index), getValue().get(index), ctx);
	}
	
	@Override
	public <U extends IValue, V extends IValue> Result<U> fieldUpdate(String name, Result<V> repl, TypeStore store, EvaluatorContext ctx) {
		if (!getType().hasField(name, store)) {
			throw new UndeclaredFieldError(name, getType(), ctx.getCurrentAST());
		}
		Type nodeType = getValue().getConstructorType();
		if (!nodeType.hasField(name)) {
			throw RuntimeExceptionFactory.noSuchField(name, ctx.getCurrentAST(), null);
		}				
		int index = nodeType.getFieldIndex(name);
		Type fieldType = nodeType.getFieldType(index);
		if (!repl.getType().isSubtypeOf(fieldType)) {
			throw new UnexpectedTypeError(fieldType, repl.getType(), ctx.getCurrentAST());
		}
		return makeResult(getType(), getValue().set(index, repl.getValue()), ctx);
	}

	
	@Override
	public <U extends IValue, V extends IValue> Result<U> compare(Result<V> result, EvaluatorContext ctx) {
		return result.compareConstructor(this, ctx);
	}
	
	//
	
	@Override
	protected <U extends IValue> Result<U> compareConstructor(NodeResult that, EvaluatorContext ctx) {
		// Note reversed args
		INode left = that.getValue();
		INode right = this.getValue();
		return makeIntegerResult(compareNodes(left, right, ctx), ctx);
	}
	
	private int compareNodes(INode left, INode right, EvaluatorContext ctx) {
		// NOTE: left and right are in normal (non-reversed) order
		int compare = left.getName().compareTo(right.getName());
		if (compare != 0){
			return compare;
		}
		compare = new Integer(left.arity()).compareTo(right.arity());
		if (compare != 0) {
			return compare;
		}
		return compareChildren(left, right, ctx);
	}
	
	private int compareChildren(INode left, INode right, EvaluatorContext ctx) {
		// NOTE: left and right are in normal (non-reversed) order
		int i = 0;
		for (IValue leftKid: left.getChildren()) {
			IValue rightKid = right.get(i);
			int compare = compareIValues(leftKid, rightKid, ctx);
			if (compare != 0) {
				return compare;
			}
			i++;
		}
		return 0;
	}
	
}
