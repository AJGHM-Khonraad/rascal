package org.meta_environment.rascal.interpreter.matching;

import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.eclipse.imp.pdb.facts.type.Type;
import org.meta_environment.rascal.interpreter.EvaluatorContext;
import org.meta_environment.rascal.interpreter.env.Environment;
import org.meta_environment.rascal.interpreter.result.Result;

public class AntiPattern extends AbstractMatchingResult {
	private IMatchingResult pat;

	public AntiPattern(IValueFactory vf, EvaluatorContext ctx, IMatchingResult pat) {
		super(vf, ctx);
		this.pat = pat;
	}

	@Override 
	public Type getType(Environment env) {
		return pat.getType(env);
	}
	
	@Override
	public void initMatch(Result<IValue> subject){
		super.initMatch(subject);
		pat.initMatch(subject);
	}
	
	@Override
	public boolean mayMatch(Type subjectType, Environment env){
		return pat.mayMatch(subjectType, env);
	}
	
	@Override
	public boolean hasNext() {
		return pat.hasNext();
	}

	@Override
	public boolean next() {
		Environment old = ctx.getCurrentEnvt();
		try {
			ctx.goodPushEnv();
			return !pat.next();
		}
		finally {
			ctx.unwind(old);
		}
	}

	@Override
	public IValue toIValue(Environment env) {
		// TODO Auto-generated method stub
		return null;
	}
}
