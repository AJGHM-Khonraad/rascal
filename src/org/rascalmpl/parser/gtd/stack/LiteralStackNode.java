/*******************************************************************************
 * Copyright (c) 2009-2011 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:

 *   * Arnold Lankamp - Arnold.Lankamp@cwi.nl
*******************************************************************************/
package org.rascalmpl.parser.gtd.stack;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.rascalmpl.parser.gtd.result.AbstractNode;
import org.rascalmpl.parser.gtd.result.LiteralNode;
import org.rascalmpl.parser.gtd.stack.filter.ICompletionFilter;
import org.rascalmpl.parser.gtd.stack.filter.IEnterFilter;

public final class LiteralStackNode extends AbstractStackNode implements IMatchableStackNode{
	private final char[] literal;
	private final IConstructor production;
	
	private final LiteralNode result;
	
	public LiteralStackNode(int id, int dot, IConstructor production, char[] literal){
		super(id, dot);
		
		this.literal = literal;
		this.production = production;
		
		result = new LiteralNode(production, literal);
	}
	
	public LiteralStackNode(int id, int dot, IConstructor production, char[] literal, IEnterFilter[] enterFilters, ICompletionFilter[] completionFilters){
		super(id, dot, enterFilters, completionFilters);
		
		this.literal = literal;
		this.production = production;
		
		result = new LiteralNode(production, literal);
	}
	
	private LiteralStackNode(LiteralStackNode original, int startLocation){
		super(original, startLocation);
		
		literal = original.literal;
		production = original.production;
		
		result = original.result;
	}
	
	public boolean isEmptyLeafNode(){
		return false;
	}
	
	public String getName(){
		throw new UnsupportedOperationException();
	}
	
	public AbstractNode match(char[] input, int location){
		for(int i = literal.length - 1; i >= 0; --i){
			if(literal[i] != input[location + i]) return null; // Did not match.
		}
		
		return result;
	}
	
	public AbstractStackNode getCleanCopy(int startLocation){
		return new LiteralStackNode(this, startLocation);
	}
	
	public AbstractStackNode getCleanCopyWithResult(int startLocation, AbstractNode result){
		return new LiteralStackNode(this, startLocation);
	}
	
	public int getLength(){
		return literal.length;
	}
	
	public AbstractStackNode[] getChildren(){
		throw new UnsupportedOperationException();
	}
	
	public boolean canBeEmpty(){
		throw new UnsupportedOperationException();
	}
	
	public AbstractStackNode getEmptyChild(){
		throw new UnsupportedOperationException();
	}
	
	public AbstractNode getResult(){
		return result;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(new String(literal));
		sb.append(getId());
		sb.append('(');
		sb.append(startLocation);
		sb.append(')');
		
		return sb.toString();
	}
	
	public int hashCode(){
		return production.hashCode();
	}
	
	public boolean isEqual(AbstractStackNode stackNode){
		if(!(stackNode instanceof LiteralStackNode)) return false;
		
		LiteralStackNode otherNode = (LiteralStackNode) stackNode;
		
		if(!production.isEqual(otherNode.production)) return false;
		
		return hasEqualFilters(stackNode);
	}
}
