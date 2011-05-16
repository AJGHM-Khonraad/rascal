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
import org.rascalmpl.parser.gtd.result.struct.Link;

public class CharNode extends AbstractNode{
	public final static int ID = 2;
	
	private final static CharNode[] charNodeConstants = new CharNode[128];
	
	private final char character;
	
	public CharNode(char character){
		super();
		
		this.character = character;
	}
	
	public int getID(){
		return ID;
	}
	
	public int getNumericCharValue(){
		return getNumericCharValue(character);
	}
	
	public void addAlternative(IConstructor production, Link children){
		throw new UnsupportedOperationException();
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
	
	public static int getNumericCharValue(char theChar){
		return (theChar < 128) ? theChar : Character.getNumericValue(theChar); // Character.getNumericValue doesn't return sensible values for 7-bit ascii characters.
	}
	
	// Cache the results for all 7-bit ascii characters.
	public static CharNode createCharNode(char character){
		if(character < charNodeConstants.length){
			CharNode charNode = charNodeConstants[character];
			if(charNode != null) return charNode;
			
			return (charNodeConstants[character] = new CharNode(character));
		}
		
		return new CharNode(character);
	}
}
