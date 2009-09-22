package org.meta_environment.rascal.interpreter.strategy;

import java.util.Iterator;
import java.util.List;

import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IListWriter;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.exceptions.FactTypeUseException;
import org.eclipse.imp.pdb.facts.impl.fast.ValueFactory;
import org.eclipse.imp.pdb.facts.type.Type;
import org.eclipse.imp.pdb.facts.visitors.IValueVisitor;
import org.eclipse.imp.pdb.facts.visitors.VisitorException;

public class VisitableList implements IVisitable, IList {
	
	private IList list;

	public VisitableList(IList list) {
		super();
		
		this.list = list;
	}

	public int getChildrenNumber() {
		return list.length();
	}

	public IVisitable getChildAt(int i) throws IndexOutOfBoundsException {
		return VisitableFactory.makeVisitable(list.get(i));
	}

	public void setChildAt(int i, IVisitable newChild) throws IndexOutOfBoundsException {
		list = list.put(i, newChild.getValue());
	}

	public IValue getValue() {
		return list;
	}

	public <T> T accept(IValueVisitor<T> v) throws VisitorException {
		return list.accept(v);
	}

	public IList append(IValue e) {
		return list.append(e);
	}

	public IList concat(IList o) {
		return list.concat(o);
	}

	public boolean contains(IValue e) {
		return list.contains(e);
	}

	public IList delete(int i) {
		return list.delete(i);
	}

	public IList delete(IValue e) {
		return list.delete(e);
	}

	public boolean equals(Object other) {
		return list.equals(other);
	}

	public IValue get(int i) throws IndexOutOfBoundsException {
		return list.get(i);
	}

	public Type getElementType() {
		return list.getElementType();
	}

	public Type getType() {
		return list.getType();
	}

	public IList insert(IValue e) {
		return list.insert(e);
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public boolean isEqual(IValue other) {
		return list.isEqual(other);
	}

	public Iterator<IValue> iterator() {
		return list.iterator();
	}

	public int length() {
		return list.length();
	}

	public IList put(int i, IValue e) throws FactTypeUseException,
			IndexOutOfBoundsException {
		return list.put(i, e);
	}

	public IList reverse() {
		return list.reverse();
	}

	public IList sublist(int offset, int length) {
		return list.sublist(offset, length);
	}

	public String toString() {
		return list.toString();
	}

	public void setChildren(List<IVisitable> newchildren)
			throws IndexOutOfBoundsException {
		IListWriter writer = ValueFactory.getInstance().listWriter(list.getElementType());
		for (int j = 0; j < list.length(); j++) {
			writer.append(newchildren.get(j).getValue());
		}
		list = writer.done();
	}

	public void update(IValue oldvalue, IValue newvalue) {}
	
}
