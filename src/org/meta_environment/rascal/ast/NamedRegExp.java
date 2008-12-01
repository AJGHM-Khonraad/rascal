package org.meta_environment.rascal.ast; 
import org.eclipse.imp.pdb.facts.ITree; 
public abstract class NamedRegExp extends AbstractAST { 
  static public class Lexical extends NamedRegExp {
	private String string;
	/*package*/ Lexical(ITree tree, String string) {
		this.tree = tree;
		this.string = string;
	}
	public String getString() {
		return string;
	}

 	public <T> T accept(IASTVisitor<T> v) {
     		return v.visitNamedRegExpLexical(this);
  	}
} static public class Ambiguity extends NamedRegExp {
  private final java.util.List<org.meta_environment.rascal.ast.NamedRegExp> alternatives;
  public Ambiguity(ITree tree, java.util.List<org.meta_environment.rascal.ast.NamedRegExp> alternatives) {
	this.alternatives = java.util.Collections.unmodifiableList(alternatives);
         this.tree = tree;
  }
  public java.util.List<org.meta_environment.rascal.ast.NamedRegExp> getAlternatives() {
	return alternatives;
  }
  
  public <T> T accept(IASTVisitor<T> v) {
     return v.visitNamedRegExpAmbiguity(this);
  }
} public abstract <T> T accept(IASTVisitor<T> visitor);
}