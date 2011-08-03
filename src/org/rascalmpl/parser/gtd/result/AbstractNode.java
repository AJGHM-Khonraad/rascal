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

import org.eclipse.imp.pdb.facts.IValueFactory;
import org.rascalmpl.values.ValueFactoryFactory;

/**
 * All nodes in the resulting tree are a subtype of this class.
 */
public abstract class AbstractNode{
	protected final static IValueFactory VF = ValueFactoryFactory.getValueFactory();
	
	public AbstractNode(){
		super();
	}
	
	/**
	 * Returns a unique identifier, indicating the type of result node.
	 */
	public abstract int getTypeIdentifier();
	
	/**
	 * Checks whether or not this node is an epsilon node.
	 */
	public final boolean isEpsilon(){
		return (this instanceof EpsilonNode);
	}
	
	/**
	 * Check whether or not this node is empty (where empty means, that it is
	 * associated with a zero length location in the input string).
	 */
	public abstract boolean isEmpty();
	
	/**
	 * Checks whether or not this node represents a separator.
	 */
	public abstract boolean isSeparator();
}
