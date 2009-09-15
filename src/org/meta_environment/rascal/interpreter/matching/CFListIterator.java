package org.meta_environment.rascal.interpreter.matching;

import java.util.Iterator;

import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IValue;

class CFListIterator implements Iterator<IValue> {
	private IList list;
	private int index;
	private int delta;
	
	CFListIterator(IList l, int delta){
		this.list = l;
		this.index = 0;
		this.delta = delta;
	}

	public boolean hasNext() {
		return index < list.length();
	}

	public IValue next() {
		IValue v = list.get(index);
		System.err.println("index = " + index + ": " + v);
		index += delta;
		return v;
	}

	public void remove() {
		throw new UnsupportedOperationException("remove in CFListIterator");
	}
}