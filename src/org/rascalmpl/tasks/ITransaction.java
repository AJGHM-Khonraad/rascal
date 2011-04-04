/*******************************************************************************
 * Copyright (c) 2009-2011 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:

 *   * Anya Helene Bagge - A.H.S.Bagge@cwi.nl (Univ. Bergen)
*******************************************************************************/
package org.rascalmpl.tasks;

import org.rascalmpl.interpreter.IRascalMonitor;
import org.rascalmpl.tasks.IFact;


public interface ITransaction<K,N,V> {

	public abstract IFact<V> setFact(K key, N name, V value);

	//public abstract V getFact(K key, N name);

	public abstract V getFact(IRascalMonitor monitor, K key, N name);

	public abstract V queryFact(K key, N name);
	
	public abstract IFact<V> findFact(K key, N name);

	public abstract void removeFact(K key, N name);

	public abstract void abandon();
	
	public abstract void commit();
}
