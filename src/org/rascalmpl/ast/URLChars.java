package org.rascalmpl.ast; 
import org.eclipse.imp.pdb.facts.INode; 
public abstract class URLChars extends AbstractAST { 
static public class Lexical extends URLChars {
	private final String string;
         protected Lexical(INode node, String string) {
		this.node = node;
		this.string = string;
	}
	public String getString() {
		return string;
	}

 	public <T> T accept(IASTVisitor<T> v) {
     		return v.visitURLCharsLexical(this);
  	}
}
static public class Ambiguity extends URLChars {
  private final java.util.List<org.rascalmpl.ast.URLChars> alternatives;
  protected Ambiguity(INode node, java.util.List<org.rascalmpl.ast.URLChars> alternatives) {
	this.alternatives = java.util.Collections.unmodifiableList(alternatives);
         this.node = node;
  }
  public java.util.List<org.rascalmpl.ast.URLChars> getAlternatives() {
	return alternatives;
  }
  
  public <T> T accept(IASTVisitor<T> v) {
     return v.visitURLCharsAmbiguity(this);
  }
}
}