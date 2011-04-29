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

import java.util.List;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;
import org.rascalmpl.ast.Expression;
import org.rascalmpl.interpreter.IEvaluatorContext;
import org.rascalmpl.interpreter.env.Environment;
import org.rascalmpl.interpreter.result.Result;
import org.rascalmpl.interpreter.result.ResultFactory;
import org.rascalmpl.interpreter.types.NonTerminalType;
import org.rascalmpl.values.uptr.Factory;
import org.rascalmpl.values.uptr.TreeAdapter;

public class GuardedPattern extends AbstractMatchingResult {
	private Type type;
	private IMatchingResult pat;
	
	public GuardedPattern(Expression.Guarded x, Type type, IMatchingResult pat){
		super(x);
		this.type = type;
		this.pat = pat;
	}

	public Type getType(Environment env) {
		return type;
	}
	
	@Override
	public boolean hasNext() {
		return pat.hasNext();
	}
	
	@Override
	public List<String> getVariables() {
		return pat.getVariables();
	}
	
	@Override
	public void initMatch(IEvaluatorContext ctx, Result<IValue> subject){
		super.initMatch(ctx, subject);
		
		Environment env = ctx.getCurrentEnvt();
		
		if (type instanceof NonTerminalType && pat.getType(env).isSubtypeOf(tf.stringType()) && subject.getValue().getType().isSubtypeOf(Factory.Tree)) {
			if (subject.getValue().getType().isSubtypeOf(type)) {
				subject = ResultFactory.makeResult(tf.stringType(), ctx.getValueFactory().string(TreeAdapter.yield((IConstructor) subject.getValue())), ctx);
				pat.initMatch(ctx, subject);
				this.hasNext = pat.hasNext();
			}
			else {
				this.hasNext = false;
			}
		}
		else {
			pat.initMatch(ctx, subject);
			// this code triggers during a visit which might encounter other stuff that would never match
//			if (!mayMatch(pat.getType(env), type)) {
//				throw new UnexpectedTypeError(pat.getType(env), type, ctx.getCurrentAST());
//			}
			this.hasNext = pat.getType(env).equivalent(type);
		}
		
	}

	@Override
	public boolean next() {
		return pat.next();
	}
}