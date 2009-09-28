package org.meta_environment.rascal.interpreter.strategy.topological;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.exceptions.FactTypeUseException;
import org.eclipse.imp.pdb.facts.type.Type;
import org.eclipse.imp.pdb.facts.type.TypeStore;
import org.eclipse.imp.pdb.facts.visitors.IValueVisitor;
import org.eclipse.imp.pdb.facts.visitors.VisitorException;

public class TopologicalVisitableConstructor extends TopologicalVisitable<IConstructor> implements IConstructor  {

	public TopologicalVisitableConstructor(RelationContext context, IConstructor value,
			List<TopologicalVisitable<?>> children) {
		super(context, value, children);
	}

	public TopologicalVisitableConstructor(RelationContext context, IConstructor value) {
		super(context, value);
	}

	public <T> T accept(IValueVisitor<T> v) throws VisitorException {
		return value.accept(v);
	}

	public boolean declaresAnnotation(TypeStore store, String label) {
		return value.declaresAnnotation(store, label);
	}

	public IValue get(int i) throws IndexOutOfBoundsException {
		return value.get(i);
	}

	public IValue get(String label) {
		return value.get(label);
	}

	public IValue getAnnotation(String label) throws FactTypeUseException {
		return value.getAnnotation(label);
	}

	public Map<String, IValue> getAnnotations() {
		return value.getAnnotations();
	}

	public Iterable<IValue> getChildren() {
		return value.getChildren();
	}

	public Type getChildrenTypes() {
		return value.getChildrenTypes();
	}

	public Type getConstructorType() {
		return value.getConstructorType();
	}

	public String getName() {
		return value.getName();
	}

	public Type getType() {
		return value.getType();
	}

	public boolean has(String label) {
		return value.has(label);
	}

	public boolean hasAnnotation(String label) throws FactTypeUseException {
		return value.hasAnnotation(label);
	}

	public boolean hasAnnotations() {
		return value.hasAnnotations();
	}

	public boolean isEqual(IValue other) {
		if (other instanceof TopologicalVisitableConstructor) {
			return value.isEqual(((TopologicalVisitableConstructor) other).getValue());	
		}
		return value.isEqual(other);
	}

	public Iterator<IValue> iterator() {
		return value.iterator();
	}

	public IConstructor joinAnnotations(Map<String, IValue> annotations) {
		return value.joinAnnotations(annotations);
	}

	public IConstructor removeAnnotation(String key) {
		return value.removeAnnotation(key);
	}

	public IConstructor removeAnnotations() {
		return value.removeAnnotations();
	}	

	public IConstructor set(int i, IValue newChild) throws IndexOutOfBoundsException {
		return value.set(i, newChild);
	}

	public IConstructor set(String label, IValue newChild)
	throws FactTypeUseException {
		return value.set(label, newChild);
	}

	public IConstructor setAnnotation(String label, IValue newValue)
	throws FactTypeUseException {
		return value.setAnnotation(label, newValue);
	}

	public IConstructor setAnnotations(Map<String, IValue> annotations) {
		return value.setAnnotations(annotations);
	}

	public String toString() {
		return value.toString();
	}

	public int arity() {
		return value.arity();
	}

}
