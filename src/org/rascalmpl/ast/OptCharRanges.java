package org.rascalmpl.ast; 
import org.eclipse.imp.pdb.facts.INode; 
public abstract class OptCharRanges extends AbstractAST { 
  public boolean isAbsent() { return false; }
static public class Absent extends OptCharRanges {
/**  -> OptCharRanges {cons("Absent")} */
	protected Absent(INode node) {
		this.node = node;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitOptCharRangesAbsent(this);
	}

	public boolean isAbsent() { return true; }	
}
static public class Ambiguity extends OptCharRanges {
  private final java.util.List<org.rascalmpl.ast.OptCharRanges> alternatives;
  protected Ambiguity(INode node, java.util.List<org.rascalmpl.ast.OptCharRanges> alternatives) {
	this.alternatives = java.util.Collections.unmodifiableList(alternatives);
         this.node = node;
  }
  public java.util.List<org.rascalmpl.ast.OptCharRanges> getAlternatives() {
	return alternatives;
  }
  
  public <T> T accept(IASTVisitor<T> v) {
     return v.visitOptCharRangesAmbiguity(this);
  }
} 
public org.rascalmpl.ast.CharRanges getRanges() { throw new UnsupportedOperationException(); }
public boolean hasRanges() { return false; }
public boolean isPresent() { return false; }
static public class Present extends OptCharRanges {
/** ranges:CharRanges -> OptCharRanges {cons("Present")} */
	protected Present(INode node, org.rascalmpl.ast.CharRanges ranges) {
		this.node = node;
		this.ranges = ranges;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitOptCharRangesPresent(this);
	}

	public boolean isPresent() { return true; }

	public boolean hasRanges() { return true; }

private final org.rascalmpl.ast.CharRanges ranges;
	public org.rascalmpl.ast.CharRanges getRanges() { return ranges; }	
}
 public abstract <T> T accept(IASTVisitor<T> visitor);
}