package org.meta_environment.rascal.interpreter.strategy;

import java.util.Iterator;

import org.eclipse.imp.pdb.facts.IRelation;
import org.eclipse.imp.pdb.facts.IRelationWriter;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.impl.fast.ValueFactory;

public class VisitableRelation implements Visitable {

	private IRelation relation;

	public VisitableRelation(IRelation relation) {
		this.relation = relation;
	}

	public int arity() {
		return relation.size();
	}

	public Visitable getChildAt(int i) throws IndexOutOfBoundsException {
		int index = 0;
		for (IValue v : relation) {
			if (index == i) {
				return VisitableFactory.make(v);
			}
			index++;
		}
		throw new IndexOutOfBoundsException();
	}

	public IValue getValue() {
		return relation;
	}

	public Visitable setChildAt(int i, Visitable newChild)
			throws IndexOutOfBoundsException {
		if (i >= arity()) {
			throw new IndexOutOfBoundsException();
		}
		int index = 0;
		IRelationWriter writer = ValueFactory.getInstance().relationWriter(relation.getFieldTypes());
		Iterator<IValue> elts = relation.iterator();
		while (elts.hasNext()) {
			IValue e = elts.next();
			if (index == i) {
				writer.insert(newChild.getValue());
			} else {
				writer.insert(e);
			}
			index++;
		}
		return new VisitableRelation(writer.done());
	}


}
