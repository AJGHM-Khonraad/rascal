package org.meta_environment.rascal.interpreter.env;

import java.util.Iterator;

import org.eclipse.imp.pdb.facts.impl.reference.ValueFactory;
import org.eclipse.imp.pdb.facts.type.TypeFactory;
import org.meta_environment.rascal.interpreter.errors.RascalImplementationException;

public class IterableEvalResult extends Result implements Iterator<Result>{
	Iterator<Result> iterator;
	private Result last;
	
	IterableEvalResult(Iterator<Result> beval){
		super(TypeFactory.getInstance().boolType(), null);
		this.iterator = beval;
	}
	
	public IterableEvalResult(Iterator<Result> beval, boolean b){
		super(TypeFactory.getInstance().boolType(), ValueFactory.getInstance().bool(b));
		this.iterator = beval;
	}
	
	public boolean isTrue(){
		return (last == null) || last.isTrue(); //TODO is this ok?
	}
	
	@Override
	public boolean hasNext(){
		return iterator.hasNext();
	}
	
	@Override
	public Result next(){
		return last = iterator.next();
	}

	public void remove() {
		throw new RascalImplementationException("remove() not implemented for IterableEvalResult");
		
	}
}