package org.meta_environment.rascal.interpreter.matching;

import java.util.List;

import org.eclipse.imp.pdb.facts.ITuple;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.eclipse.imp.pdb.facts.type.Type;
import org.meta_environment.rascal.interpreter.EvaluatorContext;
import org.meta_environment.rascal.interpreter.env.Environment;
import org.meta_environment.rascal.interpreter.result.Result;
import org.meta_environment.rascal.interpreter.result.ResultFactory;

/* package */ class TuplePattern extends AbstractMatchingResult {
	private List<IMatchingResult> children;
	private boolean firstMatch;
	
	TuplePattern(IValueFactory vf, EvaluatorContext ctx, List<IMatchingResult> list){
		super(vf, ctx);
		this.children = list;
	}
	
	@Override
	public java.util.List<String> getVariables(){
		java.util.LinkedList<String> res = new java.util.LinkedList<String> ();
		for (int i = 0; i < children.size(); i++) {
			res.addAll(children.get(i).getVariables());
		 }
		return res;
	}
	
	@Override
	public IValue toIValue(Environment env){
		IValue[] vals = new IValue[children.size()];
		for (int i = 0; i < children.size(); i++) {
			 vals[i] =  children.get(i).toIValue(env);
		 }
		return vf.tuple(vals);
	}
	
	@Override
	public void initMatch(Result<IValue> subject){
		super.initMatch(subject);
		hasNext = false;
		if (!subject.getType().isTupleType()) {
			return;
		}
		ITuple tupleSubject = (ITuple) subject.getValue();
		Type tupleType = subject.getType();
		if(tupleSubject.arity() != children.size()){
			return;
		}
		for(int i = 0; i < children.size(); i++){
			// see here we use a static type for the children...
			children.get(i).initMatch(ResultFactory.makeResult(tupleType.getFieldType(i), tupleSubject.get(i), ctx));
		}
		firstMatch = hasNext = true;
	}
	
	@Override
	public Type getType(Environment env) {
		Type fieldTypes[] = new Type[children.size()];
		for(int i = 0; i < children.size(); i++){
			fieldTypes[i] = children.get(i).getType(env);
		}
		return tf.tupleType(fieldTypes);
	}
	
	@Override
	public boolean hasNext(){
		if(firstMatch)
			return true;
		if(!hasNext)
			return false;
		hasNext = false;
		for(int i = 0; i < children.size(); i++){
			if(children.get(i).hasNext()){
				hasNext = true;
				break;
			}	
		}
		return hasNext;
	}
	
	@Override
	public boolean next() {
		checkInitialized();
		
		if(!(firstMatch || hasNext))
			return false;
		firstMatch = false;
		
		hasNext =  matchChildren(((ITuple) subject).iterator(), children.iterator());
			
		return hasNext;
	}
}