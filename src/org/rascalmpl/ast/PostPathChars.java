package org.rascalmpl.ast; 
import org.eclipse.imp.pdb.facts.INode; 
public abstract class PostPathChars extends AbstractAST { 
static public class Lexical extends PostPathChars {
	private final String string;
         protected Lexical(INode node, String string) {
		this.node = node;
		this.string = string;
	}
	public String getString() {
		return string;
	}

 	public <T> T accept(IASTVisitor<T> v) {
     		return v.visitPostPathCharsLexical(this);
  	}
}
static public class Ambiguity extends PostPathChars {
  private final java.util.List<org.rascalmpl.ast.PostPathChars> alternatives;
  protected Ambiguity(INode node, java.util.List<org.rascalmpl.ast.PostPathChars> alternatives) {
	this.alternatives = java.util.Collections.unmodifiableList(alternatives);
         this.node = node;
  }
  public java.util.List<org.rascalmpl.ast.PostPathChars> getAlternatives() {
	return alternatives;
  }
  
  public <T> T accept(IASTVisitor<T> v) {
     return v.visitPostPathCharsAmbiguity(this);
  }
}
}