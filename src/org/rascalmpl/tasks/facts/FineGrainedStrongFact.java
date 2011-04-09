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
package org.rascalmpl.tasks.facts;

import java.util.Iterator;

import org.eclipse.imp.pdb.facts.IValue;
import org.rascalmpl.tasks.IDependencyListener;
import org.rascalmpl.tasks.IFact;
import org.rascalmpl.tasks.facts.AbstractDepFact;

import static org.rascalmpl.tasks.IDependencyListener.Change.*;
/**
 * This class implements fact storage for strongly referenced facts (i.e., a fact will never be removed
 * by the garbarge collector unless it is explicitly removed from the database) with fine-grained
 * tracking of dependencies.
 * 
 * @author anya
 *
 */
public class FineGrainedStrongFact<V> extends AbstractDepFact<V,V> {

	public FineGrainedStrongFact(Object key, String keyName) {
		super(key, keyName);
	}

	/* (non-Javadoc)
	 * @see org.rascalmpl.eclipse.db.IFact#setValue(org.eclipse.imp.pdb.facts.T)
	 */
	@Override
	public synchronized boolean setValue(V val) {
		V oldValue = value;
		value = val;
		status = IFact.FACT_OK;
		if(oldValue != null &&
			!(value instanceof IValue ? ((IValue)oldValue).isEqual((IValue)val) : oldValue.equals(value))) {
					notifyChanged();
					return true;
		}
		else
			return false;
	}

	@Override
	public synchronized void changed(IFact<?> fact, Change change, Object moreInfo) {
		switch(change) {
		case CHANGED:
			if(status < IFact.FACT_DEPS_CHANGED) {
				System.out.println("CHANGED: " + fact + " recv by " + this);
				status = IFact.FACT_DEPS_CHANGED;
				notifyInvalidated();
			}
			break;
		case INVALIDATED:
			if(status < IFact.FACT_DEPS_INVALID) {
				System.out.println("INVALID: " + fact + " recv by " + this);
				status = IFact.FACT_DEPS_INVALID;
				notifyInvalidated();
			}
			break;
		case REMOVED:
			if(status < IFact.FACT_DEPS_CHANGED) {
				dependencies.remove(fact);
				status = IFact.FACT_DEPS_CHANGED;
				notifyInvalidated();
			}
			break;
		case MOVED_TO:
			if(dependencies.remove(fact)) {
				dependencies.add((IFact<?>) moreInfo);
			}
			break;
		}
	}



	/**
	 * Disposing of a fact means telling all our dependencies to notify our
	 * dependents instead about any changes. This is done when the fact itself
	 * is still useful, but we don't want to keep it in memory anymore. (We
	 * basically link ourselves out of the dependency chain)
	 */
	public void dispose() {
		for(IFact<?> f : dependencies) {
			f.unregisterListener(this);
		}
		for(IDependencyListener l : listeners) {
			l.changed(this, REMOVED, null);
		}

		dependencies.clear();
		listeners.clear();
		value = null;
	}


	@Override
	public synchronized V getValue() {
		if(status == IFact.FACT_OK)
			return value;
		else
			return null;
	}



	@SuppressWarnings("unchecked")
	@Override
	public synchronized boolean updateFrom(IFact<V> fact) {
		boolean result = false;
		synchronized(fact) {
				if(fact instanceof AbstractFact<?,?>) {
				AbstractFact<?,?> f = (AbstractFact<?,?>)fact;
				status = f.status;
				if(f.value == null)
					value = null;
				else {
					V oldValue = value;
					value = (V)f.value;
					if(oldValue != null &&
							!(value instanceof IValue ? ((IValue)oldValue).isEqual((IValue)value) : oldValue.equals(value))) {
						notifyChanged();
						result= true;
					}
				}
				//else
				//	throw new ImplementationError("Trying to update from fact with incompatible value types");
				Iterator<IFact<?>> iterator = dependencies.iterator();
				while(iterator.hasNext()) {
					IFact<?> df = iterator.next();
					if(!f.dependencies.contains(df)) {
						iterator.remove();
						df.unregisterListener(this);
					}
				}
				for(IFact<?> df : f.dependencies) {
					if(!dependencies.contains(df)) {
						dependencies.add(df);
						df.registerListener(this);
					}
				}
				for(IDependencyListener dl : f.listeners)
					dl.changed(f, MOVED_TO, this);
				f.listeners.addAll(f.listeners);
			}
			

		}
		return result;
	}

}
