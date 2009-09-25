package org.meta_environment.rascal.interpreter.result;

import static org.meta_environment.rascal.interpreter.result.ResultFactory.bool;
import static org.meta_environment.rascal.interpreter.result.ResultFactory.makeResult;

import org.eclipse.imp.pdb.facts.ISet;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;
import org.meta_environment.rascal.interpreter.IEvaluatorContext;

public class SetOrRelationResult<T extends ISet> extends CollectionResult<T> {

	SetOrRelationResult(Type type, T value, IEvaluatorContext ctx) {
		super(type, value, ctx);
	}

	protected <U extends IValue, V extends IValue> Result<U> elementOf(
			ElementResult<V> elementResult) {
				return bool(getValue().contains(elementResult.getValue()));
			}

	protected <U extends IValue, V extends IValue> Result<U> notElementOf(
			ElementResult<V> elementResult) {
				return bool(!getValue().contains(elementResult.getValue()));
			}

	@Override
	protected <U extends IValue> Result<U> addSet(SetResult s) {
		return makeResult(type.lub(s.type), getValue().union(s.getValue()), ctx);
	}

	@Override
	protected <U extends IValue> Result<U> addRelation(RelationResult s) {
		return makeResult(type.lub(s.type), getValue().union(s.getValue()), ctx);
	}

	@Override
	protected <U extends IValue> Result<U> subtractSet(SetResult s) {
		// note the reverse subtract
		return makeResult(getType().lub(s.getType()), s.getValue().subtract(getValue()), ctx);
	}

	@Override
	protected <U extends IValue> Result<U> subtractRelation(RelationResult s) {
				// note the reverse subtract
				return makeResult(getType().lub(s.getType()), s.getValue().subtract(getValue()), ctx);
			}

	@Override
	protected <U extends IValue> Result<U> multiplyRelation(RelationResult that) {
				Type tupleType = getTypeFactory().tupleType(that.type.getElementType(), type.getElementType());
				// Note the reverse in .product
				return makeResult(getTypeFactory().relTypeFromTuple(tupleType), that.getValue().product(getValue()), ctx);
			}

	@Override
	protected <U extends IValue> Result<U> multiplySet(SetResult s) {
		Type tupleType = getTypeFactory().tupleType(s.type.getElementType(), type.getElementType());
		// Note the reverse in .product
		return makeResult(getTypeFactory().relTypeFromTuple(tupleType), s.getValue().product(getValue()), ctx);
	}


	
	@Override
	protected <U extends IValue> Result<U> intersectSet(SetResult s) {
		return makeResult(type.lub(s.type), getValue().intersect(s.getValue()), ctx);
	}

	@Override
	protected <U extends IValue> Result<U> intersectRelation(RelationResult s) {
				return makeResult(type.lub(s.type), getValue().intersect(s.getValue()), ctx);
			}

	@Override
	protected <U extends IValue, V extends IValue> Result<U> insertElement(
			ElementResult<V> valueResult) {
				return addElement(valueResult);
			}

	protected <U extends IValue, V extends IValue> Result<U> addElement(
			ElementResult<V> that) {
				Type newType = getTypeFactory().setType(that.getType().lub(getType().getElementType()));
				return makeResult(newType, getValue().insert(that.getValue()), ctx);
			}

	protected <U extends IValue, V extends IValue> Result<U> removeElement(
			ElementResult<V> valueResult) {
				return makeResult(type, getValue().delete(valueResult.getValue()), ctx);
			}

	@Override
	protected <U extends IValue> Result<U> equalToRelation(RelationResult that) {
				return that.equalityBoolean(this);
			}

	@Override
	protected <U extends IValue> Result<U> nonEqualToRelation(RelationResult that) {
				return that.nonEqualityBoolean(this);
			}

	@Override
	protected <U extends IValue> Result<U> equalToSet(SetResult that) {
		return that.equalityBoolean(this);
	}

	@Override
	protected <U extends IValue> Result<U> nonEqualToSet(SetResult that) {
		return that.nonEqualityBoolean(this);
	}

	@Override
	protected <U extends IValue> Result<U> lessThanSet(SetResult that) {
		// note reversed args: we need that < this
		return bool(that.getValue().isSubsetOf(getValue()) && !that.getValue().isEqual(getValue()));
	}

	@Override
	protected <U extends IValue> Result<U> lessThanOrEqualSet(SetResult that) {
				// note reversed args: we need that <= this
				return bool(that.getValue().isSubsetOf(getValue()));
			}

	@Override
	protected <U extends IValue> Result<U> greaterThanSet(SetResult that) {
		// note reversed args: we need that > this
		return bool(getValue().isSubsetOf(that.getValue()) && !getValue().isEqual(that.getValue()));
	}

	@Override
	protected <U extends IValue> Result<U> greaterThanOrEqualSet(SetResult that) {
				// note reversed args: we need that >= this
				return bool(getValue().isSubsetOf(that.getValue()));
			}

	@Override
	protected <U extends IValue> Result<U> lessThanRelation(RelationResult that) {
				// note reversed args: we need that < this
				return bool(that.getValue().isSubsetOf(getValue()) && !that.getValue().isEqual(getValue()));
			}

	@Override
	protected <U extends IValue> Result<U> lessThanOrEqualRelation(RelationResult that) {
				// note reversed args: we need that <= this
				return bool(that.getValue().isSubsetOf(getValue()));
			}

	@Override
	protected <U extends IValue> Result<U> greaterThanRelation(RelationResult that) {
				// note reversed args: we need that > this
				return bool(getValue().isSubsetOf(that.getValue()) && !getValue().isEqual(that.getValue()));
			}

	@Override
	protected <U extends IValue> Result<U> greaterThanOrEqualRelation(
			RelationResult that) {
				// note reversed args: we need that >= this
				return bool(getValue().isSubsetOf(that.getValue()));
			}

	@Override
	protected <U extends IValue> Result<U> compareSet(SetResult that) {
		// Note reversed args.
		return makeIntegerResult(compareISets(that.getValue(), this.getValue(), ctx));
	}

	@Override
	protected <U extends IValue> Result<U> compareRelation(RelationResult that) {
				// Note reversed args.
				return makeIntegerResult(compareISets(that.getValue(), this.getValue(), ctx));
			}



}
