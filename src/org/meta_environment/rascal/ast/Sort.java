package org.meta_environment.rascal.ast; 
import org.eclipse.imp.pdb.facts.INode; 
public abstract class Sort extends AbstractAST { 
  static public class Lexical extends Sort {
	private String string;
	/*package*/ Lexical(INode node, String string) {
		this.node = node;
		this.string = string;
	}
	public String getString() {
		return string;
	}

 	public <T> T accept(IASTVisitor<T> v) {
     		return v.visitSortLexical(this);
  	}
} static public class Ambiguity extends Sort {
  private final java.util.List<org.meta_environment.rascal.ast.Sort> alternatives;
  public Ambiguity(INode node, java.util.List<org.meta_environment.rascal.ast.Sort> alternatives) {
	this.alternatives = java.util.Collections.unmodifiableList(alternatives);
         this.node = node;
  }
  public java.util.List<org.meta_environment.rascal.ast.Sort> getAlternatives() {
	return alternatives;
  }
  
  public <T> T accept(IASTVisitor<T> v) {
     return v.visitSortAmbiguity(this);
  }
} public abstract <T> T accept(IASTVisitor<T> visitor);
}