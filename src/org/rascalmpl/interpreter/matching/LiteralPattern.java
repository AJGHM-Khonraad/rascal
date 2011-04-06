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
 *   * Arnold Lankamp - Arnold.Lankamp@cwi.nl
*******************************************************************************/
package org.rascalmpl.interpreter.matching;

import static org.rascalmpl.interpreter.result.ResultFactory.makeResult;

import java.util.Collections;
import java.util.List;

import org.eclipse.imp.pdb.facts.IBool;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;
import org.rascalmpl.ast.AbstractAST;
import org.rascalmpl.interpreter.IEvaluatorContext;
import org.rascalmpl.interpreter.env.Environment;
import org.rascalmpl.interpreter.result.Result;
import org.rascalmpl.interpreter.staticErrors.UnexpectedTypeError;

public class LiteralPattern extends AbstractMatchingResult {
	private IValue literal;
	private boolean isPattern = false;
	
	public LiteralPattern(IEvaluatorContext ctx, AbstractAST x, IValue literal){
		super(ctx, x);
		this.literal = literal;
	}
	
	@Override
	public List<String> getVariables() {
		return Collections.emptyList();
	}
	
	@Override
	public Type getType(Environment env) {
			return literal.getType();
	}
	
	@Override
	public void initMatch(Result<IValue> subject) {
		super.initMatch(subject);
		isPattern = true;
	}
	
	@Override
	public boolean next(){
		checkInitialized();
		if(!hasNext)
			return false;
		hasNext = false;
	
		if (isPattern && subject.getValue().getType().comparable(literal.getType())) {
			return subject.equals(makeResult(literal.getType(), literal, ctx)).isTrue();
		}
		else if (!isPattern) {
			if (literal.getType().isBoolType()) {
				return ((IBool) literal).getValue(); 
			}
			
			throw new UnexpectedTypeError(tf.boolType(), literal.getType(), ctx.getCurrentAST());
		}
		
		
		return false;
	}
	
	public IValue toIValue(Environment env){
		return literal;
	}
	
	@Override
	public String toString(){
		return "pattern: " + literal;
	}
	
	
}
