package org.meta_environment.rascal.ast; 
import org.eclipse.imp.pdb.facts.INode; 
public abstract class FunctionAsValue extends AbstractAST { 
  public org.meta_environment.rascal.ast.Name getName() { throw new UnsupportedOperationException(); } public boolean hasName() { return false; } public boolean isDefault() { return false; }
static public class Default extends FunctionAsValue {
/* "#" name:Name -> FunctionAsValue {cons("Default")} */
	private Default() { }
	/*package*/ Default(INode node, org.meta_environment.rascal.ast.Name name) {
		this.node = node;
		this.name = name;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitFunctionAsValueDefault(this);
	}

	public boolean isDefault() { return true; }

	public boolean hasName() { return true; }

private org.meta_environment.rascal.ast.Name name;
	public org.meta_environment.rascal.ast.Name getName() { return name; }
	private void $setName(org.meta_environment.rascal.ast.Name x) { this.name = x; }
	public Default setName(org.meta_environment.rascal.ast.Name x) { 
		Default z = new Default();
 		z.$setName(x);
		return z;
	}	
}
static public class Ambiguity extends FunctionAsValue {
  private final java.util.List<org.meta_environment.rascal.ast.FunctionAsValue> alternatives;
  public Ambiguity(INode node, java.util.List<org.meta_environment.rascal.ast.FunctionAsValue> alternatives) {
	this.alternatives = java.util.Collections.unmodifiableList(alternatives);
         this.node = node;
  }
  public java.util.List<org.meta_environment.rascal.ast.FunctionAsValue> getAlternatives() {
	return alternatives;
  }
  
  public <T> T accept(IASTVisitor<T> v) {
     return v.visitFunctionAsValueAmbiguity(this);
  }
} 
public org.meta_environment.rascal.ast.Type getType() { throw new UnsupportedOperationException(); } public boolean hasType() { return false; } public boolean isTyped() { return false; }
static public class Typed extends FunctionAsValue {
/* "#" type:Type name:Name -> FunctionAsValue {cons("Typed")} */
	private Typed() { }
	/*package*/ Typed(INode node, org.meta_environment.rascal.ast.Type type, org.meta_environment.rascal.ast.Name name) {
		this.node = node;
		this.type = type;
		this.name = name;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitFunctionAsValueTyped(this);
	}

	public boolean isTyped() { return true; }

	public boolean hasType() { return true; }
	public boolean hasName() { return true; }

private org.meta_environment.rascal.ast.Type type;
	public org.meta_environment.rascal.ast.Type getType() { return type; }
	private void $setType(org.meta_environment.rascal.ast.Type x) { this.type = x; }
	public Typed setType(org.meta_environment.rascal.ast.Type x) { 
		Typed z = new Typed();
 		z.$setType(x);
		return z;
	}
	private org.meta_environment.rascal.ast.Name name;
	public org.meta_environment.rascal.ast.Name getName() { return name; }
	private void $setName(org.meta_environment.rascal.ast.Name x) { this.name = x; }
	public Typed setName(org.meta_environment.rascal.ast.Name x) { 
		Typed z = new Typed();
 		z.$setName(x);
		return z;
	}	
}
 public abstract <T> T accept(IASTVisitor<T> visitor);
}