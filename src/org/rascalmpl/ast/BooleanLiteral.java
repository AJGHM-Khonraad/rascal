package org.rascalmpl.ast; 
import org.eclipse.imp.pdb.facts.INode; 
public abstract class BooleanLiteral extends AbstractAST { 
  static public class Lexical extends BooleanLiteral {
	private final String string;
         protected Lexical(INode node, String string) {
		this.node = node;
		this.string = string;
	}
	public String getString() {
		return string;
	}

 	public <T> T accept(IASTVisitor<T> v) {
     		return v.visitBooleanLiteralLexical(this);
  	}
} static public class Ambiguity extends BooleanLiteral {
  private final java.util.List<org.rascalmpl.ast.BooleanLiteral> alternatives;
  protected Ambiguity(INode node, java.util.List<org.rascalmpl.ast.BooleanLiteral> alternatives) {
	this.alternatives = java.util.Collections.unmodifiableList(alternatives);
         this.node = node;
  }
  public java.util.List<org.rascalmpl.ast.BooleanLiteral> getAlternatives() {
	return alternatives;
  }
  
  public <T> T accept(IASTVisitor<T> v) {
     return v.visitBooleanLiteralAmbiguity(this);
  }
} public abstract <T> T accept(IASTVisitor<T> visitor);
}