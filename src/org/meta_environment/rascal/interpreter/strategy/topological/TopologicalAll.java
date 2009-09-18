package org.meta_environment.rascal.interpreter.strategy.topological;

import org.eclipse.imp.pdb.facts.IRelation;
import org.eclipse.imp.pdb.facts.ISet;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;
import org.meta_environment.rascal.interpreter.IEvaluatorContext;
import org.meta_environment.rascal.interpreter.result.AbstractFunction;
import org.meta_environment.rascal.interpreter.result.ElementResult;
import org.meta_environment.rascal.interpreter.result.OverloadedFunctionResult;
import org.meta_environment.rascal.interpreter.result.Result;
import org.meta_environment.rascal.interpreter.strategy.All;
import org.meta_environment.rascal.interpreter.strategy.VisitableFactory;

public class TopologicalAll extends All {

	public TopologicalAll(AbstractFunction function) {
		super(function);
	}

	private static ISet getRoots(IRelation relation) {
		return relation.domain().subtract(relation.range());
	}

	@Override
	public Result<IValue> call(Type[] argTypes, IValue[] argValues,
			IEvaluatorContext ctx) {
		if (argValues[0] instanceof IRelation) {
			IRelation relation = ((IRelation) argValues[0]);
			//only for binary relations
			if (relation.getType().getArity() == 2) {
				ISet roots = getRoots(relation);
				IRelation tmp = relation;
				for (IValue root: roots) {
					TopologicalVisitable<?> visitableroot = VisitableFactory.makeTopologicalVisitable(tmp,root);
					function.call(new Type[]{visitableroot.getType()}, new IValue[]{visitableroot}, ctx);
					tmp = visitableroot.getRelation();
				}
				return new ElementResult<IValue>(tmp.getType(), tmp, ctx);
			}
		}
		return super.call(argTypes, argValues, ctx);
	}

	public static IValue makeTopologicalAll(IValue arg) {
		if (arg instanceof AbstractFunction) {
			AbstractFunction function = (AbstractFunction) arg;
			if (function.isStrategy()) {
				return new TopologicalAll((AbstractFunction) arg);			}
		} else if (arg instanceof OverloadedFunctionResult) {
			OverloadedFunctionResult res = (OverloadedFunctionResult) arg;
			for (AbstractFunction function: res.iterable()) {
				if (function.isStrategy()) {
					return new TopologicalAll((AbstractFunction) function);
				}
			}
		}
		throw new RuntimeException("Unexpected strategy argument "+arg);
	}

}
