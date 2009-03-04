package org.meta_environment.rascal.ast; 
import org.eclipse.imp.pdb.facts.INode; 
public abstract class HostName extends AbstractAST { 
static public class Lexical extends HostName {
	private String string;
	/*package*/ Lexical(INode node, String string) {
		this.node = node;
		this.string = string;
	}
	public String getString() {
		return string;
	}

 	public <T> T accept(IASTVisitor<T> v) {
     		return v.visitHostNameLexical(this);
  	}
}
static public class Ambiguity extends HostName {
  private final java.util.List<org.meta_environment.rascal.ast.HostName> alternatives;
  public Ambiguity(INode node, java.util.List<org.meta_environment.rascal.ast.HostName> alternatives) {
	this.alternatives = java.util.Collections.unmodifiableList(alternatives);
         this.node = node;
  }
  public java.util.List<org.meta_environment.rascal.ast.HostName> getAlternatives() {
	return alternatives;
  }
  
  public <T> T accept(IASTVisitor<T> v) {
     return v.visitHostNameAmbiguity(this);
  }
}
}