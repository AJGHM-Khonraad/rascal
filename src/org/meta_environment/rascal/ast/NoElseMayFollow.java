package org.meta_environment.rascal.ast; 
import org.eclipse.imp.pdb.facts.ITree; 
public abstract class NoElseMayFollow extends AbstractAST { 
public boolean isDefault() { return false; }
static public class Default extends NoElseMayFollow {
/*  -> NoElseMayFollow {cons("Default")} */
	private Default() { }
	/*package*/ Default(ITree tree) {
		this.tree = tree;
	}
	public IVisitable accept(IASTVisitor visitor) {
		return visitor.visitNoElseMayFollowDefault(this);
	}

	public boolean isDefault() { return true; }	
}
static public class Ambiguity extends NoElseMayFollow {
  private final java.util.List<org.meta_environment.rascal.ast.NoElseMayFollow> alternatives;
  public Ambiguity(java.util.List<org.meta_environment.rascal.ast.NoElseMayFollow> alternatives) {
	this.alternatives = java.util.Collections.unmodifiableList(alternatives);
  }
  public java.util.List<org.meta_environment.rascal.ast.NoElseMayFollow> getAlternatives() {
	return alternatives;
  }
}
}