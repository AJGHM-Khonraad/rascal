package org.meta_environment.rascal.ast; 
import org.eclipse.imp.pdb.facts.INode; 
public abstract class PostProtocolChars extends AbstractAST { 
static public class Lexical extends PostProtocolChars {
	private final String string;
         public Lexical(INode node, String string) {
		this.node = node;
		this.string = string;
	}
	public String getString() {
		return string;
	}

 	public <T> T accept(IASTVisitor<T> v) {
     		return v.visitPostProtocolCharsLexical(this);
  	}
}
static public class Ambiguity extends PostProtocolChars {
  private final java.util.List<org.meta_environment.rascal.ast.PostProtocolChars> alternatives;
  public Ambiguity(INode node, java.util.List<org.meta_environment.rascal.ast.PostProtocolChars> alternatives) {
	this.alternatives = java.util.Collections.unmodifiableList(alternatives);
         this.node = node;
  }
  public java.util.List<org.meta_environment.rascal.ast.PostProtocolChars> getAlternatives() {
	return alternatives;
  }
  
  public <T> T accept(IASTVisitor<T> v) {
     return v.visitPostProtocolCharsAmbiguity(this);
  }
}
}