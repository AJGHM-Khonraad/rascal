package org.rascalmpl.ast; 
import org.eclipse.imp.pdb.facts.INode; 
public abstract class MidPathChars extends AbstractAST { 
static public class Lexical extends MidPathChars {
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
     		return v.visitMidPathCharsLexical(this);
  	}
}
static public class Ambiguity extends MidPathChars {
  private final java.util.List<org.rascalmpl.ast.MidPathChars> alternatives;
  public Ambiguity(INode node, java.util.List<org.rascalmpl.ast.MidPathChars> alternatives) {
	this.alternatives = java.util.Collections.unmodifiableList(alternatives);
         this.node = node;
  }
  public java.util.List<org.rascalmpl.ast.MidPathChars> getAlternatives() {
	return alternatives;
  }
  
  @Override
public <T> T accept(IASTVisitor<T> v) {
     return v.visitMidPathCharsAmbiguity(this);
  }
}
}