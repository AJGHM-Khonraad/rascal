package org.meta_environment.rascal.ast; 
import org.eclipse.imp.pdb.facts.INode; 
public abstract class URL extends AbstractAST { 
public org.meta_environment.rascal.ast.URLLiteral getUrlliteral() { throw new UnsupportedOperationException(); }
public boolean hasUrlliteral() { return false; }
public boolean isDefault() { return false; }
static public class Default extends URL {
/* urlliteral:URLLiteral -> URL {cons("Default")} */
	private Default() { }
	/*package*/ Default(INode node, org.meta_environment.rascal.ast.URLLiteral urlliteral) {
		this.node = node;
		this.urlliteral = urlliteral;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitURLDefault(this);
	}

	public boolean isDefault() { return true; }

	public boolean hasUrlliteral() { return true; }

private org.meta_environment.rascal.ast.URLLiteral urlliteral;
	public org.meta_environment.rascal.ast.URLLiteral getUrlliteral() { return urlliteral; }
	private void $setUrlliteral(org.meta_environment.rascal.ast.URLLiteral x) { this.urlliteral = x; }
	public Default setUrlliteral(org.meta_environment.rascal.ast.URLLiteral x) { 
		Default z = new Default();
 		z.$setUrlliteral(x);
		return z;
	}	
}
static public class Ambiguity extends URL {
  private final java.util.List<org.meta_environment.rascal.ast.URL> alternatives;
  public Ambiguity(INode node, java.util.List<org.meta_environment.rascal.ast.URL> alternatives) {
	this.alternatives = java.util.Collections.unmodifiableList(alternatives);
         this.node = node;
  }
  public java.util.List<org.meta_environment.rascal.ast.URL> getAlternatives() {
	return alternatives;
  }
  
  public <T> T accept(IASTVisitor<T> v) {
     return v.visitURLAmbiguity(this);
  }
}
}