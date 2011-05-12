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

import org.rascalmpl.parser.gtd.location.PositionStore;

public class StringFollowRestriction implements ICompletionFilter{
	private final char[] string;
	
	public StringFollowRestriction(char[] string){
		super();
		
		this.string = string;
	}
	
	public boolean isFiltered(char[] input, int start, int end, PositionStore positionStore){
		if((end + string.length) <= input.length){
			for(int i = string.length - 1; i >= 0; --i){
				if(input[end + i] != string[i]) return false;
			}
			return true;
		}
		
		return false;
	}
}
