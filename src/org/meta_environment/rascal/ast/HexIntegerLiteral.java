package org.meta_environment.rascal.ast; 
import org.eclipse.imp.pdb.facts.ITree; 
public abstract class HexIntegerLiteral extends AbstractAST { 
static public class Lexical extends HexIntegerLiteral {
	private String string;
	/*package*/ Lexical(ITree tree, String string) {
		this.tree = tree;
		this.string = string;
	}
	public String getString() {
		return string;
	}

 	public <T> T accept(IASTVisitor<T> v) {
     		return v.visitHexIntegerLiteralLexical(this);
  	}
}
static public class Ambiguity extends HexIntegerLiteral {
  private final java.util.List<org.meta_environment.rascal.ast.HexIntegerLiteral> alternatives;
  public Ambiguity(java.util.List<org.meta_environment.rascal.ast.HexIntegerLiteral> alternatives) {
	this.alternatives = java.util.Collections.unmodifiableList(alternatives);
  }
  public java.util.List<org.meta_environment.rascal.ast.HexIntegerLiteral> getAlternatives() {
	return alternatives;
  }
  
  public <T> T accept(IASTVisitor<T> v) {
     return v.visitHexIntegerLiteralAmbiguity(this);
  }
}
}