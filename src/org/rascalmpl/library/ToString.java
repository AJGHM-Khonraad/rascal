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

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.rascalmpl.values.uptr.Factory;
import org.rascalmpl.values.uptr.TreeAdapter;

public class ToString {
	private final IValueFactory values;
	
	public ToString(IValueFactory values){
		super();
		
		this.values = values;
	}
	
	public IString toString(IValue value)
	{
		if (value.getType() == Factory.Tree) {
			return values.string(TreeAdapter.yield((IConstructor) value));
		}
		if (value.getType().isStringType()) {
			return (IString) value;
		}
		return values.string(value.toString());
	}
}
