package org.meta_environment.rascal.interpreter.matching;

import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.eclipse.imp.pdb.facts.type.Type;
import org.meta_environment.rascal.interpreter.IEvaluatorContext;
import org.meta_environment.rascal.interpreter.env.Environment;
import org.meta_environment.rascal.interpreter.result.Result;

public class VariableBecomesPattern extends AbstractMatchingResult {
	private IMatchingResult pat;
	private IMatchingResult var;

	public VariableBecomesPattern(IValueFactory vf, IEvaluatorContext ctx, 
			IMatchingResult var, IMatchingResult pat){
		super(vf, ctx);
		this.pat = pat;
		this.var = var;
	}
	
	@Override
	public void initMatch(Result<IValue> subject){
		super.initMatch(subject);
		var.initMatch(subject);
		pat.initMatch(subject);
	}
	
	@Override
	public Type getType(Environment env) {
		return pat.getType(env);
	}
	
	@Override
	public boolean hasNext(){
		return pat.hasNext() && var.hasNext();
	}

	@Override
	public boolean next() {
		if (!pat.next()) {
			return false;
		}
		return var.next();
	}

	@Override
	public IValue toIValue(Environment env) {
		return null;
	}
}