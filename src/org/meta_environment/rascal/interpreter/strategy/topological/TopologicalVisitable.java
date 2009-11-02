package org.meta_environment.rascal.interpreter.strategy.topological;

import java.util.List;

import org.eclipse.imp.pdb.facts.IRelation;
import org.eclipse.imp.pdb.facts.IValue;
import org.meta_environment.rascal.interpreter.result.AbstractFunction;
import org.meta_environment.rascal.interpreter.strategy.IContextualVisitable;
import org.meta_environment.rascal.interpreter.strategy.IStrategyContext;

public class TopologicalVisitable implements IContextualVisitable {
	private final AbstractFunction function;

	public TopologicalVisitable(AbstractFunction function) {
		this.function = function;
	}

	public IValue getChildAt(IValue v, int i) throws IndexOutOfBoundsException {
		return getContext().getChildren(v).get(i);
	}

	public <T extends IValue> T setChildAt(T v, int i, IValue newchild) throws IndexOutOfBoundsException {
		if (v instanceof IRelation) return (T) getContext().getValue();
		IValue oldchild = getChildAt(v,i);
		getContext().update(oldchild, newchild);
		return v;
	}


	public int getChildrenNumber(IValue v) {
		List<IValue> children = getContext().getChildren(v);
		if (children == null) {
			throw new RuntimeException("Unexpected value "+v+" in the context "+getContext().getValue());
		}
		return children.size();
	}


	public <T extends IValue> T setChildren(T v, List<IValue> children) throws IndexOutOfBoundsException {
		if (v instanceof IRelation) return (T) getContext().getValue();
		return v;
	}

	public IStrategyContext getContext() {
		return function.getEvaluatorContext().getStrategyContext();
	}

	public void setContext(IStrategyContext context) {
		function.getEvaluatorContext().setStrategyContext(context);		
	}

	public void init(IValue v) {
		if (v instanceof IRelation) {
			//initialize a new context
			//TODO: manage a stack of contexts
			IStrategyContext context = new TopologicalContext();
			IRelation relation = ((IRelation) v);
			context.setValue(relation);
			setContext(context);
		}
		((TopologicalContext)getContext()).mark(v);
	}
}
