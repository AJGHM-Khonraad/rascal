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
import org.rascalmpl.parser.gtd.result.SkippedNode;

public final class SkippingStackNode<P> extends AbstractMatchableStackNode<P>{
	private final int[] until;
	private final SkippedNode result;
	
	public SkippingStackNode(int id, int[] until, int[] input, int location, P parentProduction) {
		super(id, 0);
		
		this.until = until;
		this.result = (SkippedNode) match(input, location);
		setAlternativeProduction(parentProduction);
	}
	
	private SkippingStackNode(SkippingStackNode<P> original, int startLocation){
		super(original, startLocation);
		this.until = original.until;
		this.result = original.result;
	}
	
	private SkippingStackNode(SkippingStackNode<P> original, SkippedNode result, int startLocation){
		super(original, startLocation);
		this.until = original.until;
		assert original.result == result;
		this.result = result;
	}
	
	@Override
	public String getName() {
		return "***recovery***";
	}
	
	@Override
	public boolean isEndNode() {
		return true;
	}
	
	public boolean isEmptyLeafNode(){
		return false;
	}
	
	public AbstractNode match(int[] input, int location) {
		int from = location;
		int to = location;
		
		for ( ; to < input.length; to++) {
			for (int i = 0; i < until.length; i++) {
				if (input[to] == until[i]) {
					return buildResult(input, from, to - 1);
				}
			}
		}
		
		return buildResult(input, from, input.length);
	}
	
	private SkippedNode buildResult(int[] input, int from, int to) {
		if (from >= to) {
			return new SkippedNode(new int[0], to);
		}
		
		int[] chars = new int[to - from];
		System.arraycopy(input, from, chars, 0, to - from);
		
		return new SkippedNode(chars, from);
	}

	public AbstractStackNode<P> getCleanCopy(int startLocation){
		return new SkippingStackNode<P>(this, startLocation);
	}
	
	public AbstractStackNode<P> getCleanCopyWithResult(int startLocation, AbstractNode result){
		return new SkippingStackNode<P>(this, (SkippedNode) result, startLocation);
	}
	
	public int getLength(){
		return result.getLength();
	}
	
	public AbstractNode getResult(){
		return result;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(getId());
		sb.append('(');
		sb.append(startLocation);
		sb.append(')');
		
		return sb.toString();
	}
	
	public int hashCode(){
		return until.hashCode();
	}
	
	public boolean isEqual(AbstractStackNode<P> stackNode){
		if(!(stackNode instanceof SkippingStackNode)) return false;
		
		SkippingStackNode<P> otherNode = (SkippingStackNode<P>) stackNode;
		
		return otherNode.id == id;
	}
}
