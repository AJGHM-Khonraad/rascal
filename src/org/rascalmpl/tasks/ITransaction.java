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

import java.util.Collection;
import org.rascalmpl.interpreter.IRascalMonitor;
import org.rascalmpl.tasks.IFact;


public interface ITransaction<K,N,V> {

	IFact<V> setFact(K key, N name, V value);

	IFact<V> setFact(K key, N name, V value, Collection<IFact<V>> deps);

	IFact<V> setFact(K key, N name, V value, Collection<IFact<V>> deps, IFactFactory factory);
	
	//V getFact(K key, N name);

	V getFact(IRascalMonitor monitor, K key, N name);

	V queryFact(K key, N name);
	
	IFact<V> findFact(K key, N name);

	void removeFact(K key, N name);

	void abandon();
	
	void commit();

	void commit(Collection<IFact<V>> deps);

	void registerListener(IDependencyListener listener, K key);

	void unregisterListener(IDependencyListener listener, K key);
}
