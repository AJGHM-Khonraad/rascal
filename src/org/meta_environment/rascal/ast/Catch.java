package org.meta_environment.rascal.ast; 
import org.eclipse.imp.pdb.facts.INode; 
public abstract class Catch extends AbstractAST { 
  public org.meta_environment.rascal.ast.Statement getBody() { throw new UnsupportedOperationException(); } public boolean hasBody() { return false; } public boolean isDefault() { return false; }
static public class Default extends Catch {
/* "catch" body:Statement -> Catch {cons("Default")} */
	private Default() { }
	/*package*/ Default(INode node, org.meta_environment.rascal.ast.Statement body) {
		this.node = node;
		this.body = body;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitCatchDefault(this);
	}

	public boolean isDefault() { return true; }

	public boolean hasBody() { return true; }

private org.meta_environment.rascal.ast.Statement body;
	public org.meta_environment.rascal.ast.Statement getBody() { return body; }
	private void $setBody(org.meta_environment.rascal.ast.Statement x) { this.body = x; }
	public Default setBody(org.meta_environment.rascal.ast.Statement x) { 
		Default z = new Default();
 		z.$setBody(x);
		return z;
	}	
}
static public class Ambiguity extends Catch {
  private final java.util.List<org.meta_environment.rascal.ast.Catch> alternatives;
  public Ambiguity(INode node, java.util.List<org.meta_environment.rascal.ast.Catch> alternatives) {
	this.alternatives = java.util.Collections.unmodifiableList(alternatives);
         this.node = node;
  }
  public java.util.List<org.meta_environment.rascal.ast.Catch> getAlternatives() {
	return alternatives;
  }
  
  public <T> T accept(IASTVisitor<T> v) {
     return v.visitCatchAmbiguity(this);
  }
} 
public org.meta_environment.rascal.ast.Type getType() { throw new UnsupportedOperationException(); }
	public org.meta_environment.rascal.ast.Name getName() { throw new UnsupportedOperationException(); } public boolean hasType() { return false; }
	public boolean hasName() { return false; } public boolean isBinding() { return false; }
static public class Binding extends Catch {
/* "catch" "(" type:Type name:Name ")" body:Statement -> Catch {cons("Binding")} */
	private Binding() { }
	/*package*/ Binding(INode node, org.meta_environment.rascal.ast.Type type, org.meta_environment.rascal.ast.Name name, org.meta_environment.rascal.ast.Statement body) {
		this.node = node;
		this.type = type;
		this.name = name;
		this.body = body;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitCatchBinding(this);
	}

	public boolean isBinding() { return true; }

	public boolean hasType() { return true; }
	public boolean hasName() { return true; }
	public boolean hasBody() { return true; }

private org.meta_environment.rascal.ast.Type type;
	public org.meta_environment.rascal.ast.Type getType() { return type; }
	private void $setType(org.meta_environment.rascal.ast.Type x) { this.type = x; }
	public Binding setType(org.meta_environment.rascal.ast.Type x) { 
		Binding z = new Binding();
 		z.$setType(x);
		return z;
	}
	private org.meta_environment.rascal.ast.Name name;
	public org.meta_environment.rascal.ast.Name getName() { return name; }
	private void $setName(org.meta_environment.rascal.ast.Name x) { this.name = x; }
	public Binding setName(org.meta_environment.rascal.ast.Name x) { 
		Binding z = new Binding();
 		z.$setName(x);
		return z;
	}
	private org.meta_environment.rascal.ast.Statement body;
	public org.meta_environment.rascal.ast.Statement getBody() { return body; }
	private void $setBody(org.meta_environment.rascal.ast.Statement x) { this.body = x; }
	public Binding setBody(org.meta_environment.rascal.ast.Statement x) { 
		Binding z = new Binding();
 		z.$setBody(x);
		return z;
	}	
}
 public abstract <T> T accept(IASTVisitor<T> visitor);
}