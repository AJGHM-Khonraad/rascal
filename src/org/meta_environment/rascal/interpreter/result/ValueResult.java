package org.meta_environment.rascal.interpreter.result;

import java.util.Iterator;

import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;
import org.meta_environment.rascal.interpreter.IEvaluatorContext;
import org.meta_environment.rascal.interpreter.staticErrors.UnsupportedOperationError;

public class ValueResult extends ElementResult<IValue> {

	public ValueResult(Type type, IValue value, IEvaluatorContext ctx) {
		super(type, value, ctx);
	}
	
	public ValueResult(Type type, IValue value, Iterator<Result<IValue>> iter, IEvaluatorContext ctx) {
		super(type, value, iter, ctx);
	}
	
	@Override
	public <U extends IValue, V extends IValue> Result<U> equals(Result<V> that) {
		return that.equalToValue(this);
	}
	

	@Override
	public <U extends IValue, V extends IValue> Result<U> nonEquals(Result<V> that) {
		return equals(that).negate();
	}

	@Override
	public <U extends IValue, V extends IValue> Result<U> compare(Result<V> that) {
		// the default fall back implementation for IValue-based results
		// Note the use of runtime types here. 
		return dynamicCompare(getValue(), that.getValue());
	}
	
	@Override
	protected <U extends IValue> Result<U> equalToInteger(IntegerResult that) {
		return equalityBoolean(that);
	}

	@Override
	protected <U extends IValue> Result<U> equalToReal(RealResult that) {
		return equalityBoolean(that);
	}

	@Override
	protected <U extends IValue> Result<U> equalToString(StringResult that) {
		return equalityBoolean(that);
	}
	
	@Override
	protected <U extends IValue> Result<U> equalToList(ListResult that) {
		return equalityBoolean(that);
	}
	
	@Override
	protected <U extends IValue> Result<U> equalToSet(SetResult that) {
		return equalityBoolean(that);
	}
	
	@Override
	protected <U extends IValue> Result<U> equalToMap(MapResult that) {
		return equalityBoolean(that);
	}
	
	@Override
	protected <U extends IValue> Result<U> equalToNode(NodeResult that) {
		return equalityBoolean(that);
	}
	
	@Override
	protected <U extends IValue> Result<U> equalToSourceLocation(SourceLocationResult that) {
		return equalityBoolean(that);
	}
	
	@Override
	protected <U extends IValue> Result<U> equalToRelation(RelationResult that) {
		return equalityBoolean(that);
	}
	
	@Override
	protected <U extends IValue> Result<U> equalToTuple(TupleResult that) {
		return equalityBoolean(that);
	}
	
	@Override
	protected <U extends IValue> Result<U> equalToBool(BoolResult that) {
		return equalityBoolean(that);
	}
	
	@Override
	protected <U extends IValue> Result<U> equalToValue(ValueResult that) {
		return that.equalityBoolean(this);
	}

	
	@Override
	protected <U extends IValue> Result<U> nonEqualToInteger(IntegerResult that) {
		return nonEqualityBoolean(that);
	}

	@Override
	protected <U extends IValue> Result<U> nonEqualToReal(RealResult that) {
		return nonEqualityBoolean(that);
	}

	@Override
	protected <U extends IValue> Result<U> nonEqualToString(StringResult that) {
		return nonEqualityBoolean(that);
	}
	
	@Override
	protected <U extends IValue> Result<U> nonEqualToList(ListResult that) {
		return nonEqualityBoolean(that);
	}
	
	@Override
	protected <U extends IValue> Result<U> nonEqualToSet(SetResult that) {
		return nonEqualityBoolean(that);
	}
	
	@Override
	protected <U extends IValue> Result<U> nonEqualToMap(MapResult that) {
		return nonEqualityBoolean(that);
	}
	
	@Override
	protected <U extends IValue> Result<U> nonEqualToNode(NodeResult that) {
		return nonEqualityBoolean(that);
	}
	
	@Override
	protected <U extends IValue> Result<U> nonEqualToSourceLocation(SourceLocationResult that) {
		return nonEqualityBoolean(that);
	}
	
	@Override
	protected <U extends IValue> Result<U> nonEqualToRelation(RelationResult that) {
		return nonEqualityBoolean(that);
	}
	
	@Override
	protected <U extends IValue> Result<U> nonEqualToTuple(TupleResult that) {
		return nonEqualityBoolean(that);
	}
	
	@Override
	protected <U extends IValue> Result<U> nonEqualToBool(BoolResult that) {
		return nonEqualityBoolean(that);
	}
	
	@Override
	protected <U extends IValue> Result<U> nonEqualToValue(ValueResult that) {
		return nonEqualityBoolean(this);
	}

	
	
	@Override
	protected <U extends IValue> Result<U> compareInteger(IntegerResult that) {
		return typeCompare(that);
	}
	
	@Override
	protected <U extends IValue> Result<U> compareReal(RealResult that) {
		return typeCompare(that);
	}
	
	@Override
	protected <U extends IValue> Result<U> compareString(StringResult that) {
		return typeCompare(that);
	}
	
	@Override
	protected <U extends IValue> Result<U> compareBool(BoolResult that) {
		return typeCompare(that);
	}
	
	@Override
	protected <U extends IValue> Result<U> compareTuple(TupleResult that) {
		return typeCompare(that);
	}
	
	@Override
	protected <U extends IValue> Result<U> compareList(ListResult that) {
		return typeCompare(that);
	}
	
	@Override
	protected <U extends IValue> Result<U> compareSet(SetResult that) {
		return typeCompare(that);
	}
	
	@Override
	protected <U extends IValue> Result<U> compareMap(MapResult that) {
		return typeCompare(that);
	}
	
	@Override
	protected <U extends IValue> Result<U> compareRelation(RelationResult that) {
		return typeCompare(that);
	}
	
	@Override
	protected <U extends IValue> Result<U> compareSourceLocation(SourceLocationResult that) {
		return typeCompare(that);
	}
	
	
	
	/* Utilities  */
	
	private <U extends IValue, V extends IValue> Result<U> typeCompare(Result<V> that) {
		int result = getType().toString().compareTo(that.getType().toString());
		return makeIntegerResult(result);
	}
	
	private <U extends IValue> Result<U> dynamicCompare(IValue a, IValue b) {
		// Since equals and compare must be total on all values, we are allowed
		// to lift dynamic types here to static types. This makes the dynamic compare the 
		// same as the static compare (to prevent surprises). However, if the static types
		// do not implement a comparison (like [] == 1 compares a list to an int), 
		// then we fall back to the comparison of type names.
		try {
			Result<?> aResult = ResultFactory.makeResult(a.getType(), a, ctx);
			Result<?> bResult = ResultFactory.makeResult(b.getType(), b, ctx);
			return aResult.compare(bResult);
		}
		catch (UnsupportedOperationError e) {
			return makeIntegerResult(a.getType().toString().compareTo(b.getType().toString()));
		}
	}
}
