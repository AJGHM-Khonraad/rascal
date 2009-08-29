package org.meta_environment.rascal.interpreter.result;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.imp.pdb.facts.IExternalValue;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;
import org.eclipse.imp.pdb.facts.type.TypeFactory;
import org.eclipse.imp.pdb.facts.visitors.IValueVisitor;
import org.eclipse.imp.pdb.facts.visitors.VisitorException;
import org.meta_environment.rascal.interpreter.IEvaluatorContext;
import org.meta_environment.rascal.interpreter.staticErrors.RedeclaredFunctionError;
import org.meta_environment.rascal.interpreter.staticErrors.UndeclaredFunctionError;
import org.meta_environment.rascal.interpreter.types.RascalTypeFactory;

public class OverloadedFunctionResult extends Result<IValue> implements IExternalValue {
	private static final TypeFactory TF = TypeFactory.getInstance();
	private final Set<AbstractFunction> candidates;
	private final String name;

	public OverloadedFunctionResult(String name, Type type, List<AbstractFunction> candidates) {
		super(type, null, null);
		this.candidates = new HashSet<AbstractFunction>();
		this.candidates.addAll(candidates);
		this.name = name;
	}
	
	public OverloadedFunctionResult(String name) {
		this(name, RascalTypeFactory.getInstance().functionType(TF.voidType(), TF.voidType()), Collections.<AbstractFunction>emptyList());
	}
	
	@Override
	public IValue getValue() {
		return this;
	}

	public int size() {
		return candidates.size();
	}
	
	private Type lub(List<AbstractFunction> candidates) {
		Type lub = TF.voidType();
		
		for (AbstractFunction l : candidates) {
			lub = lub.lub(l.getType());
		}
		
		return lub;
	}

	@Override
	public Result<?> call(Type[] argTypes, IValue[] argValues,
			IEvaluatorContext ctx) {
		Type tuple = getTypeFactory().tupleType(argTypes);
		
		for (AbstractFunction candidate : candidates) {
			if (candidate.match(tuple)) {
				return candidate.call(argTypes, argValues, ctx);
			}
		}
		
		throw new UndeclaredFunctionError(name, ctx.getCurrentAST());
	}
	
	public OverloadedFunctionResult join(OverloadedFunctionResult other) {
		List<AbstractFunction> joined = new LinkedList<AbstractFunction>();
		joined.addAll(candidates);
		joined.addAll(0, other.candidates);
		return new OverloadedFunctionResult(name, lub(joined), joined);
	}
	
	public OverloadedFunctionResult add(AbstractFunction candidate) {
		for (AbstractFunction other : iterable()) {
			if (!other.equals(candidate) && candidate.isAmbiguous(other)) {
				throw new RedeclaredFunctionError(candidate.getHeader(), other.getHeader(), candidate.getAst());
			}
		}
		
		List<AbstractFunction> joined = new LinkedList<AbstractFunction>();
		joined.addAll(candidates);
		joined.add(0, candidate);
		return new OverloadedFunctionResult(name, lub(joined), joined);
	}

	public Iterable<AbstractFunction> iterable() {
		return new Iterable<AbstractFunction>() {
			public Iterator<AbstractFunction> iterator() {
				return candidates.iterator();
			}
		};
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof OverloadedFunctionResult) {
			return candidates.equals(((OverloadedFunctionResult) obj).candidates);
		}
		return false;
	}
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		for (AbstractFunction l : iterable()) {
			b.append(l.toString());
			b.append(' ');
		}
		
		return b.toString();
	}

	public <T> T accept(IValueVisitor<T> v) throws VisitorException {
		return v.visitExternal(this);
	}

	public boolean isEqual(IValue other) {
		if (other instanceof OverloadedFunctionResult) {
			return candidates.equals(((OverloadedFunctionResult) other).candidates);
		}
		return false;
	}
	
	@Override
	public <U extends IValue, V extends IValue> Result<U> equals(
			Result<V> that, IEvaluatorContext ctx) {
		return that.equalToOverloadedFunction(this, ctx);
	}
	
	@Override
	public <U extends IValue> Result<U> equalToOverloadedFunction(
			OverloadedFunctionResult that, IEvaluatorContext ctx) {
		return ResultFactory.bool(candidates.equals(that.candidates));
	}
	
	
}
