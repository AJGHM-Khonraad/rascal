package org.rascalmpl.parser.sgll.result;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IInteger;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.rascalmpl.parser.sgll.result.struct.Link;
import org.rascalmpl.parser.sgll.util.IndexedStack;
import org.rascalmpl.values.ValueFactoryFactory;
import org.rascalmpl.values.uptr.Factory;

public class CharNode extends AbstractNode{
	private final static IValueFactory vf = ValueFactoryFactory.getValueFactory();
	
	private final char character;
	
	public CharNode(char character){
		super();
		
		this.character = character;
	}
	
	public void addAlternative(IConstructor production, Link children){
		throw new UnsupportedOperationException();
	}
	
	public boolean isEpsilon(){
		return false;
	}
	
	public boolean isRejected(){
		return false;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		sb.append("char(");
		sb.append(getNumericCharValue(character));
		sb.append(')');
		
		return sb.toString();
	}
	
	public IValue toTerm(IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark, LocationStore locationStore){
		if(character == END_LINE_CHAR){
			locationStore.hitEndLine();
		}else{
			locationStore.hitCharacter();
		}
		
		IInteger characterValue = vf.integer(getNumericCharValue(character));
		return vf.constructor(Factory.Tree_Char, characterValue);
	}
	
	public static int getNumericCharValue(char character){
		return (character > 127) ? Character.getNumericValue(character) : ((int) character); // Just ignore the Unicode garbage when possible.
	}
}
