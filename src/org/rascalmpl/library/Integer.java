/*******************************************************************************
 * Copyright (c) 2009-2011 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:

 *   * Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI
 *   * Arnold Lankamp - Arnold.Lankamp@cwi.nl
*******************************************************************************/
package org.rascalmpl.library;

import java.util.Random;

import org.eclipse.imp.pdb.facts.IInteger;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;

public class Integer {
	private final IValueFactory values;
	private final Random random;
	
	public Integer(IValueFactory values){
		super();
		
		this.values = values;
		random = new Random();
	}

	public IValue arbInt()
	//@doc{arbInt -- return an arbitrary integer value}
	{
	   return values.integer(random.nextInt());
	}

	public IValue arbInt(IInteger limit)
	//@doc{arbInt -- return an arbitrary integer value in the interval [0, limit).}
	{
		// TODO allow big ints
	   return values.integer(random.nextInt(limit.intValue()));
	}

	public IValue toReal(IInteger n)
	//@doc{toReal -- convert an integer value to a real value.}
	{
	  return n.toReal();
	}

	public IValue toString(IInteger n)
	//@doc{toString -- convert an integer value to a string.}
	{
	  return values.string(n.toString());
	}
}
