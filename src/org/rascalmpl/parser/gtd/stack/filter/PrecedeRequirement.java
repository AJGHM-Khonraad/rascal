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

public class PrecedeRequirement implements IExpansionFilter{
	private final char[] required;
	
	public PrecedeRequirement(char[] required){
		super();
		
		this.required = required;
	}
	
	public boolean isFiltered(char[] input, int location){
		if((location - required.length) >= 0){
			int startLocation = location - required.length;
			for(int i = required.length - 1; i >= 0; --i){
				if(input[startLocation + i] != required[i]) return true;
			}
			return false;
		}
		
		return true;
	}
}
