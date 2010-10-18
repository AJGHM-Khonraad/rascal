package org.rascalmpl.ast; 
import org.eclipse.imp.pdb.facts.INode; 
public abstract class DateAndTime extends AbstractAST { 
static public class Lexical extends DateAndTime {
	private final String string;
         protected Lexical(INode node, String string) {
		this.node = node;
		this.string = string;
	}
	public String getString() {
		return string;
	}

 	public <T> T accept(IASTVisitor<T> v) {
     		return v.visitDateAndTimeLexical(this);
  	}
}
static public class Ambiguity extends DateAndTime {
  private final java.util.List<org.rascalmpl.ast.DateAndTime> alternatives;
  protected Ambiguity(INode node, java.util.List<org.rascalmpl.ast.DateAndTime> alternatives) {
	this.alternatives = java.util.Collections.unmodifiableList(alternatives);
         this.node = node;
  }
  public java.util.List<org.rascalmpl.ast.DateAndTime> getAlternatives() {
	return alternatives;
  }
  
  public <T> T accept(IASTVisitor<T> v) {
     return v.visitDateAndTimeAmbiguity(this);
  }
}
}