package org.rascalmpl.ast; 
import org.eclipse.imp.pdb.facts.INode; 
public abstract class StrChar extends AbstractAST { 
  public boolean isnewline() { return false; }
static public class newline extends StrChar {
/** "\\n" -> StrChar {cons("newline")} */
	public newline(INode node) {
		this.node = node;
	}
	@Override
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitStrCharnewline(this);
	}

	@Override
	public boolean isnewline() { return true; }	
}
static public class Ambiguity extends StrChar {
  private final java.util.List<org.rascalmpl.ast.StrChar> alternatives;
  public Ambiguity(INode node, java.util.List<org.rascalmpl.ast.StrChar> alternatives) {
	this.alternatives = java.util.Collections.unmodifiableList(alternatives);
         this.node = node;
  }
  public java.util.List<org.rascalmpl.ast.StrChar> getAlternatives() {
	return alternatives;
  }
  
  @Override
public <T> T accept(IASTVisitor<T> v) {
     return v.visitStrCharAmbiguity(this);
  }
} static public class Lexical extends StrChar {
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
     		return v.visitStrCharLexical(this);
  	}
} @Override
public abstract <T> T accept(IASTVisitor<T> visitor);
}