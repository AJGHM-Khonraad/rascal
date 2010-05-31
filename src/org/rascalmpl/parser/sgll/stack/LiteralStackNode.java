package org.rascalmpl.parser.sgll.stack;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.rascalmpl.parser.sgll.result.INode;
import org.rascalmpl.parser.sgll.result.LiteralNode;

public final class LiteralStackNode extends AbstractStackNode implements IReducableStackNode{
	private final IConstructor symbol;
	private final char[] literal;
	
	private final LiteralNode result;
	
	public LiteralStackNode(int id, IConstructor symbol, char[] literal){
		super(id);
		
		this.symbol = symbol;
		this.literal = literal;
		
		result = new LiteralNode(symbol, literal);
	}
	
	private LiteralStackNode(LiteralStackNode literalParseStackNode){
		super(literalParseStackNode);
		
		symbol = literalParseStackNode.symbol;
		literal = literalParseStackNode.literal;
		
		result = literalParseStackNode.result;
	}
	
	public String getMethodName(){
		throw new UnsupportedOperationException();
	}
	
	public boolean reduce(char[] input){
		return reduce(input, startLocation);
	}
	
	public boolean reduce(char[] input, int location){
		for(int i = literal.length - 1; i >= 0; i--){
			if(literal[i] != input[location + i]) return false; // Did not match.
		}
		return true;
	}
	
	public boolean isClean(){
		return true;
	}
	
	public AbstractStackNode getCleanCopy(){
		return new LiteralStackNode(this);
	}
	
	public AbstractStackNode getCleanCopyWithPrefix(){
		LiteralStackNode lpsn = new LiteralStackNode(this);
		lpsn.prefixes = prefixes;
		lpsn.prefixStartLocations = prefixStartLocations;
		return lpsn;
	}
	
	public int getLength(){
		return literal.length;
	}
	
	public AbstractStackNode[] getChildren(){
		throw new UnsupportedOperationException();
	}
	
	public void addResult(IConstructor production, INode[] children){
		throw new UnsupportedOperationException();
	}
	
	public INode getResult(){
		return result;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(new String(literal));
		sb.append(getId());
		sb.append('(');
		sb.append(startLocation);
		sb.append(',');
		sb.append(startLocation + getLength());
		sb.append(')');
		
		return sb.toString();
	}
}
