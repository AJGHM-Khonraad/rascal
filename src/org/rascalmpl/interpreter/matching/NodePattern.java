/*******************************************************************************
 * Copyright (c) 2009-2011 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:

 *   * Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI
 *   * Emilie Balland - emilie.balland@inria.fr (INRIA)
 *   * Paul Klint - Paul.Klint@cwi.nl - CWI
 *   * Mark Hills - Mark.Hills@cwi.nl (CWI)
 *   * Arnold Lankamp - Arnold.Lankamp@cwi.nl
*******************************************************************************/
package org.rascalmpl.interpreter.matching;

import java.util.Iterator;
import java.util.List;

import org.eclipse.imp.pdb.facts.INode;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.ITuple;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.exceptions.FactTypeUseException;
import org.eclipse.imp.pdb.facts.exceptions.UndeclaredConstructorException;
import org.eclipse.imp.pdb.facts.type.Type;
import org.eclipse.imp.pdb.facts.type.TypeFactory;
import org.eclipse.imp.pdb.facts.visitors.IValueVisitor;
import org.eclipse.imp.pdb.facts.visitors.VisitorException;
import org.rascalmpl.ast.Expression;
import org.rascalmpl.ast.QualifiedName;
import org.rascalmpl.interpreter.IEvaluatorContext;
import org.rascalmpl.interpreter.env.Environment;
import org.rascalmpl.interpreter.result.AbstractFunction;
import org.rascalmpl.interpreter.result.OverloadedFunctionResult;
import org.rascalmpl.interpreter.result.Result;
import org.rascalmpl.interpreter.result.ResultFactory;
import org.rascalmpl.interpreter.staticErrors.UndeclaredConstructorError;
import org.rascalmpl.interpreter.utils.Names;

public class NodePattern extends AbstractMatchingResult {
	private final TypeFactory tf = TypeFactory.getInstance();
	private final TuplePattern tuple;
	private INode subject;
	private final NodeWrapperTuple tupleSubject;
	private boolean isGenericNodeType;
	private QualifiedName qName;
	private Type type;
	private Result<IValue> cachedConstructors = null;
	
	public NodePattern(IEvaluatorContext ctx, Expression x, IMatchingResult matchPattern, QualifiedName name, List<IMatchingResult> list){
		super(ctx, x);
		
		if (matchPattern != null) {
			list.add(0, matchPattern);
			isGenericNodeType = true;
		}
		else if (name != null) {
			IString nameVal = ctx.getValueFactory().string(Names.name(Names.lastName(name)));
			list.add(0, new ValuePattern(ctx, x, ResultFactory.makeResult(tf.stringType(), nameVal, ctx)));
			isGenericNodeType = false;
			qName = name;
		}
		
		this.tuple = new TuplePattern(ctx, x, list);
		this.tupleSubject = new NodeWrapperTuple();
	}
	
	private class NodeWrapperTuple implements ITuple {
		private Type type;
		
		public int arity() {
			return 1 + subject.arity();
		}

		public IValue get(int i) throws IndexOutOfBoundsException {
			if (i == 0) {
				return ctx.getValueFactory().string(subject.getName());
			}
			return subject.get(i - 1);
		}

		public Type getType() {
			if (type == null) {
				Type[] kids = new Type[1 + subject.arity()];
				kids[0] = tf.stringType();
				for (int i = 0; i < subject.arity(); i++) {
					kids[i+1] = subject.get(i).getType();
				}
				type = tf.tupleType(kids);
			}
			return type;
		}
		
		public boolean isEqual(IValue other) {
			if (!other.getType().isTupleType()) {
				return false;
			}
			if (other.getType().getArity() != subject.arity()) {
				return false;
			}
			for (int i = 0; i < arity(); i++) {
				if (!get(i).isEqual(((ITuple)other).get(i))) {
					return false;
				}
			}
			return true;
		}
		
		public Iterator<IValue> iterator() {
			return new Iterator<IValue>() {

				boolean first = true;
				Iterator<IValue> subjectIter = subject.iterator();
				
				public boolean hasNext() {
					return first || subjectIter.hasNext(); 
				}

				public IValue next() {
					if (first) {
						first = false;
						return get(0);
					}
					return subjectIter.next();
				}

				public void remove() {
					throw new UnsupportedOperationException();
				}
				
			};
		}
		
		public IValue get(String label) throws FactTypeUseException {
			throw new UnsupportedOperationException();
		}

		public IValue select(int... fields) throws IndexOutOfBoundsException {
			throw new UnsupportedOperationException();
		}

		public IValue select(String... fields) throws FactTypeUseException {
			throw new UnsupportedOperationException();
		}

		public ITuple set(int i, IValue arg) throws IndexOutOfBoundsException {
			throw new UnsupportedOperationException();
		}

		public ITuple set(String label, IValue arg) throws FactTypeUseException {
			throw new UnsupportedOperationException();
		}

		public <T> T accept(IValueVisitor<T> v) throws VisitorException {
			throw new UnsupportedOperationException();
		}
	}
	
	@Override
	public void initMatch(Result<IValue> subject) {
		if (!subject.getValue().getType().isNodeType()) {
			hasNext = false;
			return;
		}
		this.subject = (INode) subject.getValue();
		
		// We should only call initMatch if the node types line up, otherwise the tuple matcher might throw a "static error" exception.
		// The following decision code decides whether it is worth it and safe to call initMatch on the tuple matcher.
		Type patternType = getConstructorType(ctx.getCurrentEnvt());
		if (patternType.isConstructorType()) {
			patternType = patternType.getAbstractDataType();
		}
		Type subjectType = subject.getType();
		if (patternType.comparable(subjectType)) {
			tuple.initMatch(ResultFactory.makeResult(tupleSubject.getType(), tupleSubject, ctx));
			hasNext = tuple.hasNext;
		}
		else {
			hasNext = false;
		}
	}
	
	@Override
	public Type getType(Environment env) {
		if (type == null) {
			type = getConstructorType(env);

			if (type.isConstructorType()) {
				type = getConstructorType(env).getAbstractDataType();
			}
		}
		return type;
	}

	private Type getSignatureType(Environment env) {
		int arity = tuple.getType(env).getArity() - 1;

		Type[] types = new Type[arity];

		for (int i = 1; i < arity + 1; i += 1) {
			types[i - 1] =  tuple.getType(env).getFieldType(i);
		}

		return tf.tupleType(types);
	}
	
	
	public Type getConstructorType(Environment env) {
		 Type signature = getSignatureType(env);

		 if (!isGenericNodeType) {
			 Result<IValue> constructors = this.cachedConstructors;
			
			 if (constructors == null) {
				 this.cachedConstructors = constructors = env.getVariable(qName);
			 }
			 
			 if (constructors != null && constructors instanceof OverloadedFunctionResult) {
				 for (AbstractFunction d : ((OverloadedFunctionResult) constructors).iterable()) {
					 if (d.match(signature)) {
						 String cons = Names.name(Names.lastName(qName));
						Type constructor = env.getConstructor(d.getReturnType(), cons, signature);
						 
						 if (constructor == null) {
							 // it was a function, not a constructor!
							 throw new UndeclaredConstructorError(cons, signature, ctx, qName);
						 }
						return constructor;
					 }
				 }
			 }
		 }
	     return tf.nodeType();
	}
	
	@Override
	public java.util.List<String> getVariables() {
		return tuple.getVariables();
	}
	
	@Override
	public boolean hasNext(){
		if (!hasNext) {
			return false;
		}
		return tuple.hasNext();
	}
	
	@Override
	public boolean next() {
		if (hasNext) {
			return tuple.next();
		}
		return false;
	}
	
	@Override
	public String toString(){
		return "nodeAsTuple:" + tuple.toString();
	}
}
