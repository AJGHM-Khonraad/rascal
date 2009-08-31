package org.meta_environment.rascal.interpreter.result;

import static org.meta_environment.rascal.interpreter.result.ResultFactory.bool;
import static org.meta_environment.rascal.interpreter.result.ResultFactory.makeResult;

import org.eclipse.imp.pdb.facts.IMap;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;
import org.meta_environment.rascal.interpreter.IEvaluatorContext;
import org.meta_environment.rascal.interpreter.staticErrors.UnexpectedTypeError;
import org.meta_environment.rascal.interpreter.staticErrors.UnsupportedSubscriptArityError;
import org.meta_environment.rascal.interpreter.utils.RuntimeExceptionFactory;

public class MapResult extends ElementResult<IMap> {
	
	public MapResult(Type type, IMap map, IEvaluatorContext ctx) {
		super(type, map, ctx);
	}
	
	@Override 
	public <U extends IValue, V extends IValue> Result<U> add(Result<V> result, IEvaluatorContext ctx) {
		return result.addMap(this, ctx);
		
	}

	@Override 
	public <U extends IValue, V extends IValue> Result<U> subtract(Result<V> result, IEvaluatorContext ctx) {
		return result.subtractMap(this, ctx);
		
	}
	

	@Override
	public <U extends IValue, V extends IValue> Result<U> intersect(Result<V> result, IEvaluatorContext ctx) {
		return result.intersectMap(this, ctx);
	}
	

	@Override
	@SuppressWarnings("unchecked")
	public <U extends IValue, V extends IValue> Result<U> subscript(Result<?>[] subscripts, IEvaluatorContext ctx) {
		if (subscripts.length != 1) {
			throw new UnsupportedSubscriptArityError(getType(), subscripts.length, ctx.getCurrentAST());
		}
		Result<IValue> key = (Result<IValue>) subscripts[0];
		if (!getType().getKeyType().comparable(key.getType())) {
			throw new UnexpectedTypeError(getType().getKeyType(), key.getType(), ctx.getCurrentAST());
		}
		IValue v = getValue().get(key.getValue());
		if (v == null){
			throw RuntimeExceptionFactory.noSuchKey(key.getValue(), ctx.getCurrentAST(), ctx.getStackTrace());
		}
		return makeResult(getType().getValueType(), v, ctx);
	}
	
	@Override
	public <U extends IValue, V extends IValue> Result<U> equals(Result<V> that, IEvaluatorContext ctx) {
		return that.equalToMap(this, ctx);
	}

	@Override
	public <U extends IValue, V extends IValue> Result<U> nonEquals(Result<V> that, IEvaluatorContext ctx) {
		return that.nonEqualToMap(this, ctx);
	}

	@Override
	public <U extends IValue, V extends IValue> Result<U> lessThan(Result<V> that, IEvaluatorContext ctx) {
		return that.lessThanMap(this, ctx);
	}
	
	@Override
	public <U extends IValue, V extends IValue> Result<U> lessThanOrEqual(Result<V> that, IEvaluatorContext ctx) {
		return that.lessThanOrEqualMap(this, ctx);
	}

	@Override
	public <U extends IValue, V extends IValue> Result<U> greaterThan(Result<V> that, IEvaluatorContext ctx) {
		return that.greaterThanMap(this, ctx);
	}
	
	@Override
	public <U extends IValue, V extends IValue> Result<U> greaterThanOrEqual(Result<V> that, IEvaluatorContext ctx) {
		return that.greaterThanOrEqualMap(this, ctx);
	}

	
	@Override
	public <U extends IValue, V extends IValue> Result<U> compare(Result<V> result, IEvaluatorContext ctx) {
		return result.compareMap(this, ctx);
	}
	
	@Override
	public <U extends IValue, V extends IValue> Result<U> in(Result<V> result, IEvaluatorContext ctx) {
		return result.inMap(this, ctx);
	}	
	
	@Override
	public <U extends IValue, V extends IValue> Result<U> notIn(Result<V> result, IEvaluatorContext ctx) {
		return result.notInMap(this, ctx);
	}	
	
	////
	
	protected <U extends IValue, V extends IValue> Result<U> elementOf(ElementResult<V> elementResult, IEvaluatorContext ctx) {
		return bool(getValue().containsValue(elementResult.getValue()));
	}

	protected <U extends IValue, V extends IValue> Result<U> notElementOf(ElementResult<V> elementResult, IEvaluatorContext ctx) {
		return bool(!getValue().containsValue(elementResult.getValue()));
	}
	
	@Override
	protected <U extends IValue> Result<U> addMap(MapResult m, IEvaluatorContext ctx) {
		// Note the reverse
		return makeResult(getType().lub(m.getType()), m.value.join(value), ctx);
	}
	
	@Override
	protected <U extends IValue> Result<U> subtractMap(MapResult m, IEvaluatorContext ctx) {
		// Note the reverse
		return makeResult(m.getType(), m.getValue().remove(getValue()), ctx);
	}
	
	@Override
	protected <U extends IValue> Result<U> intersectMap(MapResult m, IEvaluatorContext ctx) {
		// Note the reverse
		return makeResult(m.getType(), m.getValue().common(getValue()), ctx);
	}

	
	@Override
	protected <U extends IValue> Result<U> equalToMap(MapResult that, IEvaluatorContext ctx) {
		return that.equalityBoolean(this, ctx);
	}
	
	@Override
	protected <U extends IValue> Result<U> nonEqualToMap(MapResult that, IEvaluatorContext ctx) {
		return that.nonEqualityBoolean(this);
	}
	
	@Override
	protected <U extends IValue> Result<U> lessThanMap(MapResult that, IEvaluatorContext ctx) {
		// note reversed args: we need that < this
		return bool(that.getValue().isSubMap(getValue()) && !that.getValue().isEqual(getValue()));
	}
	
	@Override
	protected <U extends IValue> Result<U> lessThanOrEqualMap(MapResult that, IEvaluatorContext ctx) {
		// note reversed args: we need that <= this
		return bool(that.getValue().isSubMap(getValue()));
	}

	@Override
	protected <U extends IValue> Result<U> greaterThanMap(MapResult that, IEvaluatorContext ctx) {
		// note reversed args: we need that > this
		return bool(getValue().isSubMap(that.getValue()) && !getValue().isEqual(that.getValue()));
	}
	
	@Override
	protected <U extends IValue> Result<U> greaterThanOrEqualMap(MapResult that, IEvaluatorContext ctx) {
		// note reversed args: we need that >= this
		return bool(getValue().isSubMap(that.getValue()));
	}
	
	@Override
	protected <U extends IValue> Result<U> compareMap(MapResult that, IEvaluatorContext ctx) {
		// Note reversed args
		IMap left = that.getValue();
		IMap right = this.getValue();
		// TODO: this is not right; they can be disjoint
		if (left.isEqual(right)) {
			return makeIntegerResult(0, ctx);
		}
		if (left.isSubMap(left)) {
			return makeIntegerResult(-1, ctx);
		}
		return makeIntegerResult(1, ctx);
	}
	
	@Override
	public <U extends IValue, V extends IValue> Result<U> compose(
			Result<V> right, IEvaluatorContext ctx) {
		return right.composeMap(this, ctx);
	}
	
	@Override
	public <U extends IValue> Result<U> composeMap(MapResult left,
			IEvaluatorContext ctx) {
		if (left.getType().getValueType().isSubtypeOf(getType().getKeyType())) {
			Type mapType = getTypeFactory().mapType(left.getType().getKeyType(), getType().getValueType());
			return ResultFactory.makeResult(mapType, left.getValue().compose(getValue()), ctx);
		}
		
		return undefinedError("composition", left, ctx);
	}
	
}
