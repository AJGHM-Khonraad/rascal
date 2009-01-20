package org.meta_environment.rascal.ast; 
import org.eclipse.imp.pdb.facts.ITree; 
public abstract class RegExpModifier extends AbstractAST { 
static public class Lexical extends RegExpModifier {
	private String string;
	/*package*/ Lexical(ITree tree, String string) {
		this.tree = tree;
		this.string = string;
	}
	public String getString() {
		return string;
	}

 	public <T> T accept(IASTVisitor<T> v) {
     		return v.visitRegExpModifierLexical(this);
  	}
}
static public class Ambiguity extends RegExpModifier {
  private final java.util.List<org.meta_environment.rascal.ast.RegExpModifier> alternatives;
  public Ambiguity(ITree tree, java.util.List<org.meta_environment.rascal.ast.RegExpModifier> alternatives) {
	this.alternatives = java.util.Collections.unmodifiableList(alternatives);
         this.tree = tree;
  }
  public java.util.List<org.meta_environment.rascal.ast.RegExpModifier> getAlternatives() {
	return alternatives;
  }
  
  public <T> T accept(IASTVisitor<T> v) {
     return v.visitRegExpModifierAmbiguity(this);
  }
}
}