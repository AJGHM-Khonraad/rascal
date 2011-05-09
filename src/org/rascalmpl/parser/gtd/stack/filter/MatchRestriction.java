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
package org.rascalmpl.parser.gtd.stack.filter;

public class MatchRestriction implements ICompletionFilter{
	private final char[] restricted;
	
	public MatchRestriction(char[] restricted){
		super();
		
		this.restricted = restricted;
	}
	
	public boolean isFiltered(char[] input, int start, int end){
		if((end - start) != restricted.length) return false;
		
		for(int i = restricted.length - 1; i >= 0; --i){
			if(input[start + i] != restricted[i]) return false;
		}
		
		return true;
	}
}
