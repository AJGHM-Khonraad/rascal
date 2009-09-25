package org.meta_environment.rascal.interpreter;

import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IListWriter;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.meta_environment.rascal.interpreter.result.Result;

public class Accumulator {

	private String label = null;
	private IListWriter writer = null;
	private IValueFactory factory;
	
	public Accumulator(IValueFactory factory, String label) {
		this.factory = factory;
		this.label = label;
	}
	
	public Accumulator(IValueFactory factory) {
		this(factory, null);
	}
	
	public boolean hasLabel(String label) {
		if (this.label == null) {
			return false;
		}
		return this.label.equals(label);
	}
	
	public void append(Result<IValue> value) {
		if (writer == null) {
			// Init the type here; static checkers should
			// ensure that appends all produces the same type.
			writer = factory.listWriter(value.getType()); 
		}
		writer.append(value.getValue());
	}
	
	public IList done() {
		if (writer == null) {
			return factory.list();
		}
		return writer.done();
	}
	
	
}
