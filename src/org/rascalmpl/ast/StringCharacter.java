package org.rascalmpl.ast; 
import org.eclipse.imp.pdb.facts.INode; 
public abstract class StringCharacter extends AbstractAST { 
  static public class Lexical extends StringCharacter {
	private final String string;
         protected Lexical(INode node, String string) {
		this.node = node;
		this.string = string;
	}
	public String getString() {
		return string;
	}

 	public <T> T accept(IASTVisitor<T> v) {
     		return v.visitStringCharacterLexical(this);
  	}
} static public class Ambiguity extends StringCharacter {
  private final java.util.List<org.rascalmpl.ast.StringCharacter> alternatives;
  protected Ambiguity(INode node, java.util.List<org.rascalmpl.ast.StringCharacter> alternatives) {
	this.alternatives = java.util.Collections.unmodifiableList(alternatives);
         this.node = node;
  }
  public java.util.List<org.rascalmpl.ast.StringCharacter> getAlternatives() {
	return alternatives;
  }
  
  public <T> T accept(IASTVisitor<T> v) {
     return v.visitStringCharacterAmbiguity(this);
  }
} public abstract <T> T accept(IASTVisitor<T> visitor);
}