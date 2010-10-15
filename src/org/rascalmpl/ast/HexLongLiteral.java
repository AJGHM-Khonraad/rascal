package org.rascalmpl.ast; 
import org.eclipse.imp.pdb.facts.INode; 
public abstract class HexLongLiteral extends AbstractAST { 
static public class Lexical extends HexLongLiteral {
	private final String string;
         protected Lexical(INode node, String string) {
		this.node = node;
		this.string = string;
	}
	public String getString() {
		return string;
	}

 	public <T> T accept(IASTVisitor<T> v) {
     		return v.visitHexLongLiteralLexical(this);
  	}
}
static public class Ambiguity extends HexLongLiteral {
  private final java.util.List<org.rascalmpl.ast.HexLongLiteral> alternatives;
  protected Ambiguity(INode node, java.util.List<org.rascalmpl.ast.HexLongLiteral> alternatives) {
	this.alternatives = java.util.Collections.unmodifiableList(alternatives);
         this.node = node;
  }
  public java.util.List<org.rascalmpl.ast.HexLongLiteral> getAlternatives() {
	return alternatives;
  }
  
  public <T> T accept(IASTVisitor<T> v) {
     return v.visitHexLongLiteralAmbiguity(this);
  }
}
}