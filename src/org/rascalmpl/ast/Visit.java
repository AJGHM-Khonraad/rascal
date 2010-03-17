package org.rascalmpl.ast; 
import org.eclipse.imp.pdb.facts.INode; 
public abstract class Visit extends AbstractAST { 
  public org.rascalmpl.ast.Expression getSubject() { throw new UnsupportedOperationException(); } public java.util.List<org.rascalmpl.ast.Case> getCases() { throw new UnsupportedOperationException(); } public boolean hasSubject() { return false; } public boolean hasCases() { return false; } public boolean isDefaultStrategy() { return false; }
static public class DefaultStrategy extends Visit {
/** "visit" "(" subject:Expression ")" "{" cases:Case+ "}" -> Visit {cons("DefaultStrategy")} */
	public DefaultStrategy(INode node, org.rascalmpl.ast.Expression subject, java.util.List<org.rascalmpl.ast.Case> cases) {
		this.node = node;
		this.subject = subject;
		this.cases = cases;
	}
	@Override
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitVisitDefaultStrategy(this);
	}

	@Override
	public boolean isDefaultStrategy() { return true; }

	@Override
	public boolean hasSubject() { return true; }
	@Override
	public boolean hasCases() { return true; }

private final org.rascalmpl.ast.Expression subject;
	@Override
	public org.rascalmpl.ast.Expression getSubject() { return subject; }
	private final java.util.List<org.rascalmpl.ast.Case> cases;
	@Override
	public java.util.List<org.rascalmpl.ast.Case> getCases() { return cases; }	
}
static public class Ambiguity extends Visit {
  private final java.util.List<org.rascalmpl.ast.Visit> alternatives;
  public Ambiguity(INode node, java.util.List<org.rascalmpl.ast.Visit> alternatives) {
	this.alternatives = java.util.Collections.unmodifiableList(alternatives);
         this.node = node;
  }
  public java.util.List<org.rascalmpl.ast.Visit> getAlternatives() {
	return alternatives;
  }
  
  @Override
public <T> T accept(IASTVisitor<T> v) {
     return v.visitVisitAmbiguity(this);
  }
} 
public org.rascalmpl.ast.Strategy getStrategy() { throw new UnsupportedOperationException(); } public boolean hasStrategy() { return false; } public boolean isGivenStrategy() { return false; }
static public class GivenStrategy extends Visit {
/** strategy:Strategy "visit" "(" subject:Expression ")" "{" cases:Case+ "}" -> Visit {cons("GivenStrategy")} */
	public GivenStrategy(INode node, org.rascalmpl.ast.Strategy strategy, org.rascalmpl.ast.Expression subject, java.util.List<org.rascalmpl.ast.Case> cases) {
		this.node = node;
		this.strategy = strategy;
		this.subject = subject;
		this.cases = cases;
	}
	@Override
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitVisitGivenStrategy(this);
	}

	@Override
	public boolean isGivenStrategy() { return true; }

	@Override
	public boolean hasStrategy() { return true; }
	@Override
	public boolean hasSubject() { return true; }
	@Override
	public boolean hasCases() { return true; }

private final org.rascalmpl.ast.Strategy strategy;
	@Override
	public org.rascalmpl.ast.Strategy getStrategy() { return strategy; }
	private final org.rascalmpl.ast.Expression subject;
	@Override
	public org.rascalmpl.ast.Expression getSubject() { return subject; }
	private final java.util.List<org.rascalmpl.ast.Case> cases;
	@Override
	public java.util.List<org.rascalmpl.ast.Case> getCases() { return cases; }	
}
 @Override
public abstract <T> T accept(IASTVisitor<T> visitor);
}