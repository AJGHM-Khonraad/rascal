package org.meta_environment.rascal.ast; 
import org.eclipse.imp.pdb.facts.INode; 
public abstract class PreStringChars extends AbstractAST { 
static public class Lexical extends PreStringChars {
	private final String string;
         public Lexical(INode node, String string) {
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
  private final java.util.List<org.meta_environment.rascal.ast.PreStringChars> alternatives;
  public Ambiguity(INode node, java.util.List<org.meta_environment.rascal.ast.PreStringChars> alternatives) {
	this.alternatives = java.util.Collections.unmodifiableList(alternatives);
         this.node = node;
  }
  public java.util.List<org.meta_environment.rascal.ast.PreStringChars> getAlternatives() {
	return alternatives;
  }
  
  public <T> T accept(IASTVisitor<T> v) {
     return v.visitPreStringCharsAmbiguity(this);
  }
}
}