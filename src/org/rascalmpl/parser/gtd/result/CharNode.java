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
package org.rascalmpl.parser.gtd.result;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.rascalmpl.parser.gtd.result.action.IActionExecutor;
import org.rascalmpl.parser.gtd.result.action.IEnvironment;
import org.rascalmpl.parser.gtd.result.struct.Link;
import org.rascalmpl.parser.gtd.util.IndexedStack;
import org.rascalmpl.parser.gtd.util.specific.PositionStore;
import org.rascalmpl.values.ValueFactoryFactory;
import org.rascalmpl.values.uptr.Factory;

public class CharNode extends AbstractNode{
	private final static IValueFactory vf = ValueFactoryFactory.getValueFactory();
	
	private final char character;
	
	private IConstructor cachedResult;
	
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
	
	public boolean isEmpty(){
		return false;
	}
	
	public boolean isSeparator(){
		return false;
	}
	
	public void setRejected(){
		throw new UnsupportedOperationException();
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
	
	public IConstructor toTree(IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark, PositionStore positionStore, FilteringTracker filteringTracker, IActionExecutor actionExecutor, IEnvironment environment){
		if(cachedResult != null) return cachedResult;
		
		IConstructor result = vf.constructor(Factory.Tree_Char, vf.integer(getNumericCharValue(character)));
		cachedResult = result;
		return result;
	}
	
	public IConstructor toErrorTree(IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark, PositionStore positionStore, IActionExecutor actionExecutor, IEnvironment environment){
		return toTree(stack, depth, cycleMark, positionStore, null, actionExecutor, environment);
	}
	
	public static int getNumericCharValue(char character){
		return (character < 128) ? character : Character.getNumericValue(character); // Just ignore the Unicode garbage when possible.
	}
}
