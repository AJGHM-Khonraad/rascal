/*******************************************************************************
 * Copyright (c) 2009-2011 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:

 *   * Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI
 *   * Tijs van der Storm - Tijs.van.der.Storm@cwi.nl
 *   * Paul Klint - Paul.Klint@cwi.nl - CWI
 *   * Mark Hills - Mark.Hills@cwi.nl (CWI)
 *   * Arnold Lankamp - Arnold.Lankamp@cwi.nl
*******************************************************************************/
package org.rascalmpl.interpreter.result;

import static org.rascalmpl.interpreter.result.ResultFactory.bool;
import static org.rascalmpl.interpreter.result.ResultFactory.makeResult;

import java.util.Iterator;

import org.eclipse.imp.pdb.facts.IBool;
import org.eclipse.imp.pdb.facts.INode;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;
import org.rascalmpl.interpreter.IEvaluatorContext;
import org.rascalmpl.interpreter.env.Environment;
import org.rascalmpl.interpreter.staticErrors.UndeclaredAnnotationError;
import org.rascalmpl.interpreter.staticErrors.UnexpectedTypeError;

public class ElementResult<T extends IValue> extends Result<T> {
	public ElementResult(Type type, T value, IEvaluatorContext ctx) {
		super(type, value, ctx);
	}
	
	public ElementResult(Type type, T value, Iterator<Result<IValue>> iter, IEvaluatorContext ctx) {
		super(type, value, iter, ctx);
	}
	
	@Override
	public <U extends IValue, V extends IValue> Result<U> add(Result<V> that) {
		return that.insertElement(this);
	}
	
	@Override
	protected <U extends IValue> Result<U> inSet(SetResult s) {
		return s.elementOf(this);
	}
	
	
	@Override
	protected <U extends IValue> Result<U> notInSet(SetResult s) {
		return s.notElementOf(this);
	}
	
	@Override
	protected <U extends IValue> Result<U> inRelation(RelationResult s) {
		return s.elementOf(this);
	}
	
	@Override
	protected <U extends IValue> Result<U> notInRelation(RelationResult s) {
		return s.notElementOf(this);
	}
	
	@Override
	protected <U extends IValue> Result<U> inList(ListResult s) {
		return s.elementOf(this);
	}
	
	@Override
	protected <U extends IValue> Result<U> notInList(ListResult s) {
		return s.notElementOf(this);
	}
	
	@Override
	protected <U extends IValue> Result<U> inMap(MapResult s) {
		return s.elementOf(this);
	}
	
	@Override
	protected <U extends IValue> Result<U> notInMap(MapResult s) {
		return s.notElementOf(this);
	}
	
	@Override
	protected <U extends IValue> Result<U> addSet(SetResult s) {
		return s.addElement(this);
	}
	
	@Override
	protected <U extends IValue> Result<U> subtractSet(SetResult s) {
		return s.removeElement(this);
	}

	@Override
	protected <U extends IValue> Result<U> addList(ListResult s) {
		return s.appendElement(this);
	}
	
	@Override
	protected <U extends IValue> Result<U> subtractList(ListResult s) {
		return s.removeElement(this);
	}
	
	@Override
	protected <U extends IValue> Result<U> addRelation(RelationResult that) {
		if (that.getValue().getElementType().isVoidType()) {
			return makeResult(getTypeFactory().setType(this.getType()), that.getValue().insert(this.getValue()), ctx);
		}
		return super.addRelation(that);
	}

	@Override
	public <U extends IValue, V extends IValue> Result<U> setAnnotation(String annoName, Result<V> anno, Environment env) {
		Type annoType = env.getAnnotationType(getType(), annoName);

		if (getType() != getTypeFactory().nodeType()) {
			if (getType() != getTypeFactory().nodeType() && annoType == null) {
				throw new UndeclaredAnnotationError(annoName, getType(), ctx.getCurrentAST());
			}
			if (!anno.getType().isSubtypeOf(annoType)){
				throw new UnexpectedTypeError(annoType, anno.getType(), ctx.getCurrentAST());
			}
		}

		IValue annotatedBase = ((INode)getValue()).setAnnotation(annoName, anno.getValue());

		return makeResult(getType(), annotatedBase, ctx);
	}

	
	@Override
  public <U extends IValue, V extends IValue> Result<U> lessThan(Result<V> that) {
	  // TODO: it would be more efficient to directly implement lessThan and equals and forward lessThanOrEqual than 
	  // the way we have it now. This is slow because we compute equality twice.
    boolean eq = ((IBool) equals(that).getValue()).getValue();
    
    return bool(!eq && ((IBool) lessThanOrEqual(that).getValue()).getValue(), ctx);
  }
  
  @Override
  public <U extends IValue, V extends IValue> Result<U> greaterThan(
      Result<V> that) {
    return that.lessThan(this);
  }
  
  @Override
  public <U extends IValue, V extends IValue> Result<U> greaterThanOrEqual(
      Result<V> that) {
    return that.lessThanOrEqual(this);
  }
  
	@Override
	protected <U extends IValue> Result<U> equalToValue(ValueResult that) {
		return that.equalityBoolean(this);
	}

	protected <U extends IValue, V extends IValue> Result<U> equalityBoolean(ElementResult<V> that) {
		return bool(that.getValue().isEqual(this.getValue()), ctx);
	}

	protected <U extends IValue, V extends IValue> Result<U> nonEqualityBoolean(ElementResult<V> that) {
		return bool((!that.getValue().isEqual(this.getValue())), ctx);
	}

}
