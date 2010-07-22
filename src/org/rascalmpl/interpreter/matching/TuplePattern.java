package org.rascalmpl.interpreter.matching;

import java.util.List;

import org.eclipse.imp.pdb.facts.ITuple;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;
import org.eclipse.imp.pdb.facts.type.TypeFactory;
import org.rascalmpl.interpreter.IEvaluatorContext;
import org.rascalmpl.interpreter.env.Environment;
import org.rascalmpl.interpreter.result.Result;
import org.rascalmpl.interpreter.result.ResultFactory;

public class TuplePattern extends AbstractMatchingResult {
	private List<IMatchingResult> children;
	private ITuple treeSubject;
	private boolean firstMatch = false;
	private final TypeFactory tf = TypeFactory.getInstance();
	private int nextChild;
	
	public TuplePattern(IEvaluatorContext ctx, List<IMatchingResult> list){
		super(ctx);
		this.children = list;
	}
	
	@Override
	public void initMatch(Result<IValue> subject){
		super.initMatch(subject);
		hasNext = false;
		
		if (!subject.getValue().getType().isTupleType()) {
			return;
		}
		treeSubject = (ITuple) subject.getValue();

		if (treeSubject.arity() != children.size()){
			return;
		}
		
		hasNext = true;
		firstMatch = true;
		
		for (int i = 0; i < children.size(); i += 1){
			IValue childValue = treeSubject.get(i);
			IMatchingResult child = children.get(i);
			child.initMatch(ResultFactory.makeResult(childValue.getType(), childValue, ctx));
			hasNext &= child.hasNext();
		}
		
		nextChild = children.size() - 1;
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
	public IValue toIValue(Environment env){
		IValue[] vals = new IValue[children.size()];
		for (int i = 0; i < children.size(); i++) {
			 vals[i] =  children.get(i).toIValue(env);
		 }
		return ctx.getValueFactory().tuple(vals);
	}

	@Override
	public java.util.List<String> getVariables(){
		java.util.LinkedList<String> res = new java.util.LinkedList<String> ();
		for (int i = 0; i < children.size(); i += 1) {
			res.addAll(children.get(i).getVariables());
		 }
		return res;
	}
	
	@Override
	public boolean hasNext(){
		if (!initialized) {
			return false;
		}
		
		if (firstMatch) {
			return true;
		}
		
		if (!hasNext) {
			return false;
		}

		while (nextChild >= 0) {
			IMatchingResult child = children.get(nextChild);

			if (child.hasNext()) {
				for (int i = nextChild + 1; i < children.size(); i++) {
					IValue childValue = treeSubject.get(i);
					IMatchingResult tailChild = children.get(i);
					tailChild.initMatch(ResultFactory.makeResult(childValue.getType(), childValue, ctx));
				}
				return true;
			}
			nextChild--;
		}
		
		hasNext = false;
		return false;
	}
	
	@Override
	public boolean next(){
		checkInitialized();
		
		if(!(firstMatch || hasNext))
			return false;

		if (firstMatch) {
			firstMatch = false;
			
			for (IMatchingResult child : children) {
				if (!child.next()) {
					return false;
				}
			}
			
			nextChild = children.size() - 1;
			return true;
		}
		else {
			// redo the current child and the suffix
			for (int i = nextChild; i < children.size(); i++) {
				if (!children.get(i).next()) {
					return false;
				}
			}
			nextChild = children.size() - 1;
			return true;
		}
	}
	
	@Override
	public String toString(){
		StringBuilder res = new StringBuilder();
		res.append("<");
		String sep = "";
		for (IBooleanResult mp : children){
			res.append(sep);
			sep = ", ";
			res.append(mp.toString());
		}
		res.append(">");
		
		return res.toString();
	}
}
