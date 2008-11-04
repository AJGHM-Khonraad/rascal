package org.meta_environment.rascal.ast; 
import org.eclipse.imp.pdb.facts.ITree; 
public abstract class StructuredType extends AbstractAST { 
public org.meta_environment.rascal.ast.TypeArg getTypeArg() { throw new UnsupportedOperationException(); } public boolean hasTypeArg() { return false; } public boolean isList() { return false; }
static public class List extends StructuredType {
/* "list" "[" typeArg:TypeArg "]" -> StructuredType {cons("List")} */
	private List() { }
	/*package*/ List(ITree tree, org.meta_environment.rascal.ast.TypeArg typeArg) {
		this.tree = tree;
		this.typeArg = typeArg;
	}
	public IVisitable accept(IASTVisitor visitor) {
		return visitor.visitStructuredTypeList(this);
	}

	public boolean isList() { return true; }

	public boolean hasTypeArg() { return true; }

private org.meta_environment.rascal.ast.TypeArg typeArg;
	public org.meta_environment.rascal.ast.TypeArg getTypeArg() { return typeArg; }
	private void $setTypeArg(org.meta_environment.rascal.ast.TypeArg x) { this.typeArg = x; }
	public List setTypeArg(org.meta_environment.rascal.ast.TypeArg x) { 
		List z = new List();
 		z.$setTypeArg(x);
		return z;
	}	
}
static public class Ambiguity extends StructuredType {
  private final java.util.List<org.meta_environment.rascal.ast.StructuredType> alternatives;
  public Ambiguity(java.util.List<org.meta_environment.rascal.ast.StructuredType> alternatives) {
	this.alternatives = java.util.Collections.unmodifiableList(alternatives);
  }
  public java.util.List<org.meta_environment.rascal.ast.StructuredType> getAlternatives() {
	return alternatives;
  }
} public boolean isSet() { return false; }
static public class Set extends StructuredType {
/* "set" "[" typeArg:TypeArg "]" -> StructuredType {cons("Set")} */
	private Set() { }
	/*package*/ Set(ITree tree, org.meta_environment.rascal.ast.TypeArg typeArg) {
		this.tree = tree;
		this.typeArg = typeArg;
	}
	public IVisitable accept(IASTVisitor visitor) {
		return visitor.visitStructuredTypeSet(this);
	}

	public boolean isSet() { return true; }

	public boolean hasTypeArg() { return true; }

private org.meta_environment.rascal.ast.TypeArg typeArg;
	public org.meta_environment.rascal.ast.TypeArg getTypeArg() { return typeArg; }
	private void $setTypeArg(org.meta_environment.rascal.ast.TypeArg x) { this.typeArg = x; }
	public Set setTypeArg(org.meta_environment.rascal.ast.TypeArg x) { 
		Set z = new Set();
 		z.$setTypeArg(x);
		return z;
	}	
} public org.meta_environment.rascal.ast.TypeArg getFirst() { throw new UnsupportedOperationException(); } public org.meta_environment.rascal.ast.TypeArg getSecond() { throw new UnsupportedOperationException(); } public boolean hasFirst() { return false; } public boolean hasSecond() { return false; }
public boolean isMap() { return false; }
static public class Map extends StructuredType {
/* "map" "[" first:TypeArg "," second:TypeArg "]" -> StructuredType {cons("Map")} */
	private Map() { }
	/*package*/ Map(ITree tree, org.meta_environment.rascal.ast.TypeArg first, org.meta_environment.rascal.ast.TypeArg second) {
		this.tree = tree;
		this.first = first;
		this.second = second;
	}
	public IVisitable accept(IASTVisitor visitor) {
		return visitor.visitStructuredTypeMap(this);
	}

	public boolean isMap() { return true; }

	public boolean hasFirst() { return true; }
	public boolean hasSecond() { return true; }

private org.meta_environment.rascal.ast.TypeArg first;
	public org.meta_environment.rascal.ast.TypeArg getFirst() { return first; }
	private void $setFirst(org.meta_environment.rascal.ast.TypeArg x) { this.first = x; }
	public Map setFirst(org.meta_environment.rascal.ast.TypeArg x) { 
		Map z = new Map();
 		z.$setFirst(x);
		return z;
	}
	private org.meta_environment.rascal.ast.TypeArg second;
	public org.meta_environment.rascal.ast.TypeArg getSecond() { return second; }
	private void $setSecond(org.meta_environment.rascal.ast.TypeArg x) { this.second = x; }
	public Map setSecond(org.meta_environment.rascal.ast.TypeArg x) { 
		Map z = new Map();
 		z.$setSecond(x);
		return z;
	}	
} public java.util.List<org.meta_environment.rascal.ast.TypeArg> getRest() { throw new UnsupportedOperationException(); } public boolean hasRest() { return false; } public boolean isRelation() { return false; }
static public class Relation extends StructuredType {
/* "rel" "[" first:TypeArg "," rest:{TypeArg ","}+ "]" -> StructuredType {cons("Relation")} */
	private Relation() { }
	/*package*/ Relation(ITree tree, org.meta_environment.rascal.ast.TypeArg first, java.util.List<org.meta_environment.rascal.ast.TypeArg> rest) {
		this.tree = tree;
		this.first = first;
		this.rest = rest;
	}
	public IVisitable accept(IASTVisitor visitor) {
		return visitor.visitStructuredTypeRelation(this);
	}

	public boolean isRelation() { return true; }

	public boolean hasFirst() { return true; }
	public boolean hasRest() { return true; }

private org.meta_environment.rascal.ast.TypeArg first;
	public org.meta_environment.rascal.ast.TypeArg getFirst() { return first; }
	private void $setFirst(org.meta_environment.rascal.ast.TypeArg x) { this.first = x; }
	public Relation setFirst(org.meta_environment.rascal.ast.TypeArg x) { 
		Relation z = new Relation();
 		z.$setFirst(x);
		return z;
	}
	private java.util.List<org.meta_environment.rascal.ast.TypeArg> rest;
	public java.util.List<org.meta_environment.rascal.ast.TypeArg> getRest() { return rest; }
	private void $setRest(java.util.List<org.meta_environment.rascal.ast.TypeArg> x) { this.rest = x; }
	public Relation setRest(java.util.List<org.meta_environment.rascal.ast.TypeArg> x) { 
		Relation z = new Relation();
 		z.$setRest(x);
		return z;
	}	
} public boolean isTuple() { return false; }
static public class Tuple extends StructuredType {
/* "tuple" "[" first:TypeArg "," rest:{TypeArg ","}+ "]" -> StructuredType {cons("Tuple")} */
	private Tuple() { }
	/*package*/ Tuple(ITree tree, org.meta_environment.rascal.ast.TypeArg first, java.util.List<org.meta_environment.rascal.ast.TypeArg> rest) {
		this.tree = tree;
		this.first = first;
		this.rest = rest;
	}
	public IVisitable accept(IASTVisitor visitor) {
		return visitor.visitStructuredTypeTuple(this);
	}

	public boolean isTuple() { return true; }

	public boolean hasFirst() { return true; }
	public boolean hasRest() { return true; }

private org.meta_environment.rascal.ast.TypeArg first;
	public org.meta_environment.rascal.ast.TypeArg getFirst() { return first; }
	private void $setFirst(org.meta_environment.rascal.ast.TypeArg x) { this.first = x; }
	public Tuple setFirst(org.meta_environment.rascal.ast.TypeArg x) { 
		Tuple z = new Tuple();
 		z.$setFirst(x);
		return z;
	}
	private java.util.List<org.meta_environment.rascal.ast.TypeArg> rest;
	public java.util.List<org.meta_environment.rascal.ast.TypeArg> getRest() { return rest; }
	private void $setRest(java.util.List<org.meta_environment.rascal.ast.TypeArg> x) { this.rest = x; }
	public Tuple setRest(java.util.List<org.meta_environment.rascal.ast.TypeArg> x) { 
		Tuple z = new Tuple();
 		z.$setRest(x);
		return z;
	}	
}
}