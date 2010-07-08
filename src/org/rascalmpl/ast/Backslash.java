package org.rascalmpl.ast; 
import org.eclipse.imp.pdb.facts.INode; 
public abstract class Backslash extends AbstractAST { 
static public class Lexical extends Backslash {
	private final String string;
         public Lexical(INode node, String string) {
		this.node = node;
		this.string = string;
	}
	public String getString() {
		return string;
	}

 	public <T> T accept(IASTVisitor<T> v) {
     		return v.visitBackslashLexical(this);
  	}
}
static public class Ambiguity extends Backslash {
  private final java.util.List<org.rascalmpl.ast.Backslash> alternatives;
  public Ambiguity(INode node, java.util.List<org.rascalmpl.ast.Backslash> alternatives) {
	this.alternatives = java.util.Collections.unmodifiableList(alternatives);
         this.node = node;
  }
  public java.util.List<org.rascalmpl.ast.Backslash> getAlternatives() {
	return alternatives;
  }
  
  public <T> T accept(IASTVisitor<T> v) {
     return v.visitBackslashAmbiguity(this);
  }
}
}