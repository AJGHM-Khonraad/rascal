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


import org.rascalmpl.parser.gtd.result.AbstractNode;
import org.rascalmpl.parser.gtd.result.StartOfLineNode;
import org.rascalmpl.parser.gtd.util.specific.PositionStore;

public class StartOfLineStackNode extends AbstractStackNode implements IMatchableStackNode, ILocatableStackNode{
	private final static StartOfLineNode result = new StartOfLineNode();
	
	private PositionStore positionStore;
	
	public StartOfLineStackNode(int id, int dot){
		super(id, dot);
	}
	
	private StartOfLineStackNode(StartOfLineStackNode original){
		super(original);
	}
	
	public boolean isEmptyLeafNode(){
		return true;
	}
	
	public String getName(){
		throw new UnsupportedOperationException();
	}
	
	public void setPositionStore(PositionStore positionStore){
		this.positionStore = positionStore;
	}
	
	public boolean match(char[] input){
		return positionStore.startsLine(startLocation);
	}
	
	public boolean matchWithoutResult(char[] input, int location){
		return positionStore.startsLine(location);
	}
	
	public AbstractStackNode getCleanCopy(){
		return new StartOfLineStackNode(this);
	}
	
	public int getLength(){
		return 0;
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
		sb.append('^');
		sb.append('(');
		sb.append(startLocation);
		sb.append(')');
		
		return sb.toString();
	}
}
