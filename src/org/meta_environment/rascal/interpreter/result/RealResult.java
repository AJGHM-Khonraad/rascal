package org.meta_environment.rascal.interpreter.result;

import static org.meta_environment.rascal.interpreter.result.ResultFactory.bool;
import static org.meta_environment.rascal.interpreter.result.ResultFactory.makeResult;

import org.eclipse.imp.pdb.facts.IReal;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;
import org.meta_environment.rascal.interpreter.IEvaluatorContext;

public class RealResult extends ElementResult<IReal> {
	private static final int PRECISION = 80*80; // ONE PAGE OF DIGITS

	public RealResult(IReal real, IEvaluatorContext ctx) {
		this(real.getType(), real, ctx);
	}
	
	public RealResult(Type type, IReal real, IEvaluatorContext ctx) {
		super(type, real, ctx);
	}
	
	@Override
	public <U extends IValue, V extends IValue> Result<U> add(Result<V> result) {
		return result.addReal(this);
	}
	
	@Override
	public <U extends IValue, V extends IValue> Result<U> multiply(Result<V> result) {
		return result.multiplyReal(this);
	}
	
	@Override
	public <U extends IValue, V extends IValue> Result<U> divide(Result<V> result) {
		return result.divideReal(this);
	}
	
	@Override
	public <U extends IValue, V extends IValue> Result<U> subtract(Result<V> result) {
		return result.subtractReal(this);
	}
	
	@Override
	public <U extends IValue, V extends IValue> Result<U> modulo(Result<V> result) {
		return result.moduloReal(this);
	}
	
	@Override
	public <U extends IValue, V extends IValue> Result<U> equals(Result<V> that) {
		return that.equalToReal(this);
	}

	@Override
	public <U extends IValue, V extends IValue> Result<U> nonEquals(Result<V> that) {
		return that.nonEqualToReal(this);
	}

	@Override
	public <U extends IValue, V extends IValue> Result<U> lessThan(Result<V> result) {
		return result.lessThanReal(this);
	}
	
	@Override
	public <U extends IValue, V extends IValue> Result<U> lessThanOrEqual(Result<V> result) {
		return result.lessThanOrEqualReal(this);
	}
	
	@Override
	public <U extends IValue, V extends IValue> Result<U> greaterThan(Result<V> result) {
		return result.greaterThanReal(this);
	}
	
	@Override
	public <U extends IValue, V extends IValue> Result<U> greaterThanOrEqual(Result<V> result) {
		return result.greaterThanOrEqualReal(this);
	}
	
	@Override
	public <U extends IValue, V extends IValue> Result<U> compare(Result<V> result) {
		return result.compareReal(this);
	}
	
	/// real impls start here
	
	@Override
	public <U extends IValue> Result<U> negative() {
		return makeResult(type, getValue().negate(), ctx);
	}
	
	
	@Override
	protected <U extends IValue> Result<U> addInteger(IntegerResult n) {
		return n.widenToReal().add(this);
	}
	
	@Override
	protected <U extends IValue> Result<U> subtractInteger(IntegerResult n) {
		// Note reversed args: we need n - this
		return n.widenToReal().subtract(this);
	}
	
	@Override
	protected <U extends IValue> Result<U> multiplyInteger(IntegerResult n) {
		return n.widenToReal().multiply(this);
	}
	
	@Override
	protected <U extends IValue> Result<U> divideInteger(IntegerResult n) {
		// Note reversed args: we need n / this
		return n.widenToReal().divide(this);
	}
	
	@Override  
	protected <U extends IValue> Result<U> addReal(RealResult n) {
		return makeResult(type, getValue().add(n.getValue()), ctx);
	}
	
	@Override 
	protected <U extends IValue> Result<U> subtractReal(RealResult n) {
		// note the reverse subtraction.
		return makeResult(type, n.getValue().subtract(getValue()), ctx);
	}
	
	@Override
	protected <U extends IValue> Result<U> multiplyReal(RealResult n) {
		return makeResult(type, getValue().multiply(n.getValue()), ctx);
	}

	@Override
	protected <U extends IValue> Result<U> divideReal(RealResult n) {
		// note the reverse division
		return makeResult(type, n.getValue().divide(getValue(), PRECISION), ctx);
	}
	
	@Override
	protected <U extends IValue> Result<U> equalToReal(RealResult that) {
		return that.equalityBoolean(this);
	}

	@Override
	protected <U extends IValue> Result<U> nonEqualToReal(RealResult that) {
		return that.nonEqualityBoolean(this);
	}
	
	@Override
	protected <U extends IValue> Result<U> lessThanReal(RealResult that) {
		// note reversed args: we need that < this
		return bool(that.comparisonInts(this) < 0);
	}
	
	@Override
	protected <U extends IValue> Result<U> lessThanOrEqualReal(RealResult that) {
		// note reversed args: we need that <= this
		return bool(that.comparisonInts(this) <= 0);
	}

	@Override
	protected <U extends IValue> Result<U> greaterThanReal(RealResult that) {
		// note reversed args: we need that > this
		return bool(that.comparisonInts(this) > 0);
	}
	
	@Override
	protected <U extends IValue> Result<U> greaterThanOrEqualReal(RealResult that) {
		// note reversed args: we need that >= this
		return bool(that.comparisonInts(this) >= 0);
	}

	@Override
	protected <U extends IValue> Result<U> equalToInteger(IntegerResult that) {
		return that.widenToReal().equals(this);
	}
	
	@Override
	protected <U extends IValue> Result<U> nonEqualToInteger(IntegerResult that) {
		return that.widenToReal().nonEquals(this);
	}
	
	@Override
	protected <U extends IValue> Result<U> lessThanInteger(IntegerResult that) {
		// note reversed args: we need that < this
		return that.widenToReal().lessThan(this);
	}
	
	@Override
	protected <U extends IValue> Result<U> lessThanOrEqualInteger(IntegerResult that) {
		// note reversed args: we need that <= this
		return that.widenToReal().lessThanOrEqual(this);
	}

	@Override
	protected <U extends IValue> Result<U> greaterThanInteger(IntegerResult that) {
		// note reversed args: we need that > this
		return that.widenToReal().greaterThan(this);
	}
	
	@Override
	protected <U extends IValue> Result<U> greaterThanOrEqualInteger(IntegerResult that) {
		// note reversed args: we need that >= this
		return that.widenToReal().greaterThanOrEqual(this);
	}

	
	@Override
	protected <U extends IValue> Result<U> compareReal(RealResult that) {
		// note reverse arguments
		IReal left = that.getValue();
		IReal right = this.getValue();
		int result = left.compare(right);
		return makeIntegerResult(result);
	}
	
	@Override
	protected <U extends IValue> Result<U> compareInteger(IntegerResult that) {
		return that.widenToReal().compare(this);
	}

	
}
