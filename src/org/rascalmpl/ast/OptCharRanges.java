package org.rascalmpl.ast; 
import org.eclipse.imp.pdb.facts.INode; 
public abstract class OptCharRanges extends AbstractAST { 
  public boolean isAbsent() { return false; }
static public class Absent extends OptCharRanges {
/**  -> OptCharRanges {cons("Absent")} */
	public Absent(INode node) {
		this.node = node;
	}
	@Override
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitOptCharRangesAbsent(this);
	}

	@Override
	public boolean isAbsent() { return true; }	
}
static public class Ambiguity extends OptCharRanges {
  private final java.util.List<org.rascalmpl.ast.OptCharRanges> alternatives;
  public Ambiguity(INode node, java.util.List<org.rascalmpl.ast.OptCharRanges> alternatives) {
	this.alternatives = java.util.Collections.unmodifiableList(alternatives);
         this.node = node;
  }
  public java.util.List<org.rascalmpl.ast.OptCharRanges> getAlternatives() {
	return alternatives;
  }
  
  @Override
public <T> T accept(IASTVisitor<T> v) {
     return v.visitOptCharRangesAmbiguity(this);
  }
} 
public org.rascalmpl.ast.CharRanges getRanges() { throw new UnsupportedOperationException(); }
public boolean hasRanges() { return false; }
public boolean isPresent() { return false; }
static public class Present extends OptCharRanges {
/** ranges:CharRanges -> OptCharRanges {cons("Present")} */
	public Present(INode node, org.rascalmpl.ast.CharRanges ranges) {
		this.node = node;
		this.ranges = ranges;
	}
	@Override
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitOptCharRangesPresent(this);
	}

	@Override
	public boolean isPresent() { return true; }

	@Override
	public boolean hasRanges() { return true; }

private final org.rascalmpl.ast.CharRanges ranges;
	@Override
	public org.rascalmpl.ast.CharRanges getRanges() { return ranges; }	
}
 @Override
public abstract <T> T accept(IASTVisitor<T> visitor);
}