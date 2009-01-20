package org.meta_environment.rascal.ast; 
import org.eclipse.imp.pdb.facts.ITree; 
public abstract class StringLiteral extends AbstractAST { 
static public class Lexical extends StringLiteral {
	private String string;
	/*package*/ Lexical(ITree tree, String string) {
		this.tree = tree;
		this.string = string;
	}
	public String getString() {
		return string;
	}

 	public <T> T accept(IASTVisitor<T> v) {
     		return v.visitStringLiteralLexical(this);
  	}
}
static public class Ambiguity extends StringLiteral {
  private final java.util.List<org.meta_environment.rascal.ast.StringLiteral> alternatives;
  public Ambiguity(ITree tree, java.util.List<org.meta_environment.rascal.ast.StringLiteral> alternatives) {
	this.alternatives = java.util.Collections.unmodifiableList(alternatives);
         this.tree = tree;
  }
  public java.util.List<org.meta_environment.rascal.ast.StringLiteral> getAlternatives() {
	return alternatives;
  }
  
  public <T> T accept(IASTVisitor<T> v) {
     return v.visitStringLiteralAmbiguity(this);
  }
}
}