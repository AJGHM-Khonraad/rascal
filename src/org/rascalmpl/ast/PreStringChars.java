package org.rascalmpl.ast; 
import org.eclipse.imp.pdb.facts.INode; 
public abstract class PreStringChars extends AbstractAST { 
static public class Lexical extends PreStringChars {
	private final String string;
         protected Lexical(INode node, String string) {
		this.node = node;
		this.string = string;
	}
	public String getString() {
		return string;
	}

 	public <T> T accept(IASTVisitor<T> v) {
     		return v.visitPreStringCharsLexical(this);
  	}
}
static public class Ambiguity extends PreStringChars {
  private final java.util.List<org.rascalmpl.ast.PreStringChars> alternatives;
  protected Ambiguity(INode node, java.util.List<org.rascalmpl.ast.PreStringChars> alternatives) {
	this.alternatives = java.util.Collections.unmodifiableList(alternatives);
         this.node = node;
  }
  public java.util.List<org.rascalmpl.ast.PreStringChars> getAlternatives() {
	return alternatives;
  }
  
  public <T> T accept(IASTVisitor<T> v) {
     return v.visitPreStringCharsAmbiguity(this);
  }
}
}