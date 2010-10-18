package org.rascalmpl.ast; 
import org.eclipse.imp.pdb.facts.INode; 
public abstract class Target extends AbstractAST { 
  public boolean isEmpty() { return false; }
static public class Empty extends Target {
/**  -> Target {cons("Empty")} */
	protected Empty(INode node) {
		this.node = node;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitTargetEmpty(this);
	}

	public boolean isEmpty() { return true; }	
}
static public class Ambiguity extends Target {
  private final java.util.List<org.rascalmpl.ast.Target> alternatives;
  protected Ambiguity(INode node, java.util.List<org.rascalmpl.ast.Target> alternatives) {
	this.alternatives = java.util.Collections.unmodifiableList(alternatives);
         this.node = node;
  }
  public java.util.List<org.rascalmpl.ast.Target> getAlternatives() {
	return alternatives;
  }
  
  public <T> T accept(IASTVisitor<T> v) {
     return v.visitTargetAmbiguity(this);
  }
} 
public org.rascalmpl.ast.Name getName() { throw new UnsupportedOperationException(); }
public boolean hasName() { return false; }
public boolean isLabeled() { return false; }
static public class Labeled extends Target {
/** name:Name -> Target {cons("Labeled")} */
	protected Labeled(INode node, org.rascalmpl.ast.Name name) {
		this.node = node;
		this.name = name;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitTargetLabeled(this);
	}

	public boolean isLabeled() { return true; }

	public boolean hasName() { return true; }

private final org.rascalmpl.ast.Name name;
	public org.rascalmpl.ast.Name getName() { return name; }	
}
 public abstract <T> T accept(IASTVisitor<T> visitor);
}