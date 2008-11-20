package org.meta_environment.rascal.ast; 
import org.eclipse.imp.pdb.facts.ITree; 
public abstract class UnicodeEscape extends AbstractAST { 
static public class Lexical extends UnicodeEscape {
	private String string;
	/*package*/ Lexical(ITree tree, String string) {
		this.tree = tree;
		this.string = string;
	}
	public String getString() {
		return string;
	}

 	public <T> T accept(IASTVisitor<T> v) {
     		return v.visitUnicodeEscapeLexical(this);
  	}
}
static public class Ambiguity extends UnicodeEscape {
  private final java.util.List<org.meta_environment.rascal.ast.UnicodeEscape> alternatives;
  public Ambiguity(java.util.List<org.meta_environment.rascal.ast.UnicodeEscape> alternatives) {
	this.alternatives = java.util.Collections.unmodifiableList(alternatives);
  }
  public java.util.List<org.meta_environment.rascal.ast.UnicodeEscape> getAlternatives() {
	return alternatives;
  }
  
  public <T> T accept(IASTVisitor<T> v) {
     return v.visitUnicodeEscapeAmbiguity(this);
  }
}
}