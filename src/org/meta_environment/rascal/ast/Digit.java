package org.meta_environment.rascal.ast; 
import org.eclipse.imp.pdb.facts.INode; 
public abstract class Digit extends AbstractAST { 
static public class Lexical extends Digit {
	private String string;
	/*package*/ Lexical(INode node, String string) {
		this.node = node;
		this.string = string;
	}
	public String getString() {
		return string;
	}

 	public <T> T accept(IASTVisitor<T> v) {
     		return v.visitDigitLexical(this);
  	}
}
static public class Ambiguity extends Digit {
  private final java.util.List<org.meta_environment.rascal.ast.Digit> alternatives;
  public Ambiguity(INode node, java.util.List<org.meta_environment.rascal.ast.Digit> alternatives) {
	this.alternatives = java.util.Collections.unmodifiableList(alternatives);
         this.node = node;
  }
  public java.util.List<org.meta_environment.rascal.ast.Digit> getAlternatives() {
	return alternatives;
  }
  
  public <T> T accept(IASTVisitor<T> v) {
     return v.visitDigitAmbiguity(this);
  }
}
}