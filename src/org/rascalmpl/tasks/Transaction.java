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

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.imp.pdb.facts.IExternalValue;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.ExternalType;
import org.eclipse.imp.pdb.facts.type.Type;
import org.eclipse.imp.pdb.facts.visitors.IValueVisitor;
import org.eclipse.imp.pdb.facts.visitors.VisitorException;
import org.rascalmpl.interpreter.IRascalMonitor;
import org.rascalmpl.tasks.FactFactory;
import org.rascalmpl.tasks.IFact;
import org.rascalmpl.tasks.INameFormatter;
import org.rascalmpl.tasks.ITransaction;
import org.rascalmpl.tasks.TaskRegistry;
import org.rascalmpl.tasks.Transaction;

public class Transaction  implements ITransaction<Type,IValue,IValue>, IExternalValue {
	public static final Type TransactionType = new ExternalType() {};
	private final Transaction parent;
	private final Map<Key, IFact<IValue>> map = new HashMap<Key, IFact<IValue>>();
	private final ITaskRegistry<Type,IValue,IValue> registry;
	private final Set<IFact<IValue>> deps = new HashSet<IFact<IValue>>();
	private final Set<Key> removed = new HashSet<Key>();
	private INameFormatter format;
	private final PrintWriter stderr;
	public Transaction(PrintWriter stderr) {
		this(null, null, stderr);
	}

	public Transaction(INameFormatter format, PrintWriter stderr) {
		this(null, format, stderr);
	}

	public Transaction(Transaction parent, PrintWriter stderr) {
		this(parent, null, stderr);
	}
	
	public Transaction(Transaction parent, INameFormatter format, PrintWriter stderr) {
		this.parent = (Transaction) parent;
		if(parent != null)
			this.registry = parent.registry;
		else
			this.registry = PDBValueTaskRegistry.getRegistry();
		this.format = format;
		if(stderr == null)
			this.stderr = new PrintWriter(System.err);
		else
			this.stderr = stderr;
	}
	@Override
	public Type getType() {
		return TransactionType;
	}

	@Override
	public <T> T accept(IValueVisitor<T> v) throws VisitorException {
		return null;
	}

	@Override
	public boolean isEqual(IValue other) {
		return false;
	}

	/*@Override
	public IValue getFact(Type key, IValue name) {
		return getFact(new NullRascalMonitor(), key, name);
	}*/
	@Override
	public IValue getFact(IRascalMonitor monitor, Type key, IValue name) {
		IFact<IValue> fact = null;
		synchronized (this) {
			Key k = new Key(key, name);
			fact = query(k);

			if (fact != null) {
				IValue value = fact.getValue();
				if (value != null) {
					deps.add(fact);
					return value;
				}
			}
			monitor.startJob("Producing fact " + formatKey(key, name));
			Transaction tr = new Transaction(this, stderr);
			registry.produce(monitor, tr, key, name);
			monitor.endJob(true);
			fact = tr.query(k);
			if (fact != null) {
				tr.commit();
				deps.add(fact);
			}
//			else
	//			tr.abandon();
		}
		if (fact != null)
			return fact.getValue();
		else
			return null;
	}

	@Override
	public IValue queryFact(Type key, IValue name) {
		Key k = new Key(key, name);
		IFact<IValue> fact = query(k);
		
		if(fact != null)
			return fact.getValue();
		else
			return null;
	}

	protected IFact<IValue> query(Key k) {
		IFact<IValue> fact = map.get(k);
		if(fact == null && parent != null)
			return parent.query(k);
		else
			return fact;
		
	}
	
	@Override
	public synchronized void removeFact(Type key, IValue name) {
		Key k = new Key(key, name);
		IFact<IValue> fact = map.get(k);
		map.remove(k);
		if(fact != null) {
			fact.remove();
			deps.remove(fact);
			removed.add(k);
		}
	}

	@Override
	public synchronized IFact<IValue> setFact(Type key, IValue name, IValue value) {
		Key k = new Key(key, name);
		IFact<IValue> fact = map.get(k);
		if(fact == null) {
			fact = FactFactory.fact(IValue.class, k, registry.getDepPolicy(key), registry.getRefPolicy(key));
		}
		fact.setValue(value);
		fact.setDepends(deps);
		map.put(k, fact);
		removed.remove(k);

		stderr.printf("Set fact %s = %s\n    <- ", formatKey(key, name), abbrev(value.toString(), 40));
		for(IFact<IValue> d : deps)
			stderr.print(formatKey(d.getKey()) + " ");
		stderr.println();
		stderr.flush();

		return fact;
	}

	private String abbrev(String s, int len) {
		if(s.length() > len)
			s = s.substring(0, len) + "...";
		return s;
	}
		
	@Override
	public void abandon() {
		for(IFact<IValue> fact : map.values()) {
			fact.remove(); // TODO: dispose?
		}
		map.clear();
		removed.clear();
		deps.clear();
	}

	@Override
	public void commit() {
		if(parent != null) {
			for(Key k : removed) {
				parent.removeFact(k.type, k.name);
			}
			for(Key k : map.keySet()) {
				IFact<IValue> fact = parent.query(k);
				if(fact != null) {
					fact.updateFrom(map.get(k));
				}
				else {
					parent.map.put(k, map.get(k));
				}
			}
			// TODO: update fact references
		}
	}

	private String formatKey(Object key) {
		if(key instanceof Key) {
			Key k = (Key)key;
			return formatKey(k.type, k.name);
		}
		else
			return key.toString();
	}
	
	private String formatKey(Type key, IValue name) {
		String n;
		if(format == null && parent != null)
			return parent.formatKey(key, name);
		else if(format != null)
			n = format.format(name);
		else
			n = name.toString();
		
		return key.getName() + "(" + n + ")";
	}

	@Override
	public IFact<IValue> findFact(Type key, IValue name) {
		return query(new Key(key, name));
	}


	class Key {
		public final Type type;
		public final IValue name;
		
		Key(Type type, IValue name) {
			this.type = type;
			this.name = name;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + ((type == null) ? 0 : type.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Key other = (Key) obj;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.isEqual(other.name))
				return false;
			if (type == null) {
				if (other.type != null)
					return false;
			} else if (!type.equals(other.type))
				return false;
			return true;
		}
	}


}
