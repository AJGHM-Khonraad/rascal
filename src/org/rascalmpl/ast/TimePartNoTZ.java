package org.rascalmpl.ast; 
import org.eclipse.imp.pdb.facts.INode; 
public abstract class TimePartNoTZ extends AbstractAST { 
  static public class Lexical extends TimePartNoTZ {
	private final String string;
         public Lexical(INode node, String string) {
		this.node = node;
		this.string = string;
	}
	public String getString() {
		return string;
	}

 	@Override
	public <T> T accept(IASTVisitor<T> v) {
     		return v.visitTimePartNoTZLexical(this);
  	}
} static public class Ambiguity extends TimePartNoTZ {
  private final java.util.List<org.rascalmpl.ast.TimePartNoTZ> alternatives;
  public Ambiguity(INode node, java.util.List<org.rascalmpl.ast.TimePartNoTZ> alternatives) {
	this.alternatives = java.util.Collections.unmodifiableList(alternatives);
         this.node = node;
  }
  public java.util.List<org.rascalmpl.ast.TimePartNoTZ> getAlternatives() {
	return alternatives;
  }
  
  @Override
public <T> T accept(IASTVisitor<T> v) {
     return v.visitTimePartNoTZAmbiguity(this);
  }
} @Override
public abstract <T> T accept(IASTVisitor<T> visitor);
}