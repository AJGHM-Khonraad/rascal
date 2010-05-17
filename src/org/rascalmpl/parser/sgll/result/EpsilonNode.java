package org.rascalmpl.parser.sgll.result;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.rascalmpl.values.ValueFactoryFactory;
import org.rascalmpl.values.uptr.Factory;

public class EpsilonNode implements INode{
	private final static IValueFactory vf = ValueFactoryFactory.getValueFactory();
	
	private final static String EPSILON_STRING = "appl(prod([],empty()))";
	
	public EpsilonNode(){
		super();
	}
	
	public void addAlternative(IConstructor production, INode[] children){
		throw new UnsupportedOperationException();
	}
	
	public boolean isEpsilon(){
		return true;
	}
	
	public String toString(){
		return EPSILON_STRING;
	}
	
	public IValue toTerm(){
		IConstructor empty = vf.constructor(Factory.Symbol_Empty);
		IConstructor production = vf.constructor(Factory.Production_Default, vf.list(Factory.Symbol), empty);
		return vf.constructor(Factory.Tree_Appl, production);
	}
}
