package org.meta_environment.errors;

import java.util.Iterator;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.ISourceRange;
import org.eclipse.imp.pdb.facts.IValue;

public class SummaryAdapter implements Iterable<ErrorAdapter> {
	private IConstructor summary;
	
	public SummaryAdapter(IValue summary) {
		this.summary = (IConstructor) summary;
	}
	
	public IValue getProducer() {
		return summary.get("producer");
	}
	
	public IValue getId() {
		return summary.get("id");
	}

	/**
	 * Iterates over errors in summary
	 */
	public Iterator<ErrorAdapter> iterator() {
		return new Iterator<ErrorAdapter>() {
			Iterator<IValue> errors = ((IList) summary.get("errors")).iterator();

			public boolean hasNext() {
				return errors.hasNext();
			}

			public ErrorAdapter next() {
				return new ErrorAdapter(errors.next());
			}

			public void remove() {
				errors.remove();
			}
		};
	}
	
	public ISourceRange getInitialErrorRange() {
		for (ErrorAdapter error : this) {
			for (SubjectAdapter subject : error) {
				if (subject.isLocalized()) {
					return subject.getRange();
				}
			}
		}
		
		return null;
	}
}
