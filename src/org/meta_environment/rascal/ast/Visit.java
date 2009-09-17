package org.meta_environment.rascal.ast; 
import org.eclipse.imp.pdb.facts.INode; 
public abstract class Visit extends AbstractAST { 
  public org.meta_environment.rascal.ast.Expression getSubject() { throw new UnsupportedOperationException(); } public java.util.List<org.meta_environment.rascal.ast.Case> getCases() { throw new UnsupportedOperationException(); } public boolean hasSubject() { return false; } public boolean hasCases() { return false; } public boolean isDefaultStrategy() { return false; }
static public class DefaultStrategy extends Visit {
/** "visit" "(" subject:Expression ")" "{" cases:Case+ "}" -> Visit {cons("DefaultStrategy")} */
	public DefaultStrategy(INode node, org.meta_environment.rascal.ast.Expression subject, java.util.List<org.meta_environment.rascal.ast.Case> cases) {
		this.node = node;
		this.subject = subject;
		this.cases = cases;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitVisitDefaultStrategy(this);
	}

	public boolean isDefaultStrategy() { return true; }

	public boolean hasSubject() { return true; }
	public boolean hasCases() { return true; }

private final org.meta_environment.rascal.ast.Expression subject;
	public org.meta_environment.rascal.ast.Expression getSubject() { return subject; }
	private final java.util.List<org.meta_environment.rascal.ast.Case> cases;
	public java.util.List<org.meta_environment.rascal.ast.Case> getCases() { return cases; }	
}
static public class Ambiguity extends Visit {
  private final java.util.List<org.meta_environment.rascal.ast.Visit> alternatives;
  public Ambiguity(INode node, java.util.List<org.meta_environment.rascal.ast.Visit> alternatives) {
	this.alternatives = java.util.Collections.unmodifiableList(alternatives);
         this.node = node;
  }
  public java.util.List<org.meta_environment.rascal.ast.Visit> getAlternatives() {
	return alternatives;
  }
  
  public <T> T accept(IASTVisitor<T> v) {
     return v.visitVisitAmbiguity(this);
  }
} 
public org.meta_environment.rascal.ast.Strategy getStrategy() { throw new UnsupportedOperationException(); } public boolean hasStrategy() { return false; } public boolean isGivenStrategy() { return false; }
static public class GivenStrategy extends Visit {
/** strategy:Strategy "visit" "(" subject:Expression ")" "{" cases:Case+ "}" -> Visit {cons("GivenStrategy")} */
	public GivenStrategy(INode node, org.meta_environment.rascal.ast.Strategy strategy, org.meta_environment.rascal.ast.Expression subject, java.util.List<org.meta_environment.rascal.ast.Case> cases) {
		this.node = node;
		this.strategy = strategy;
		this.subject = subject;
		this.cases = cases;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitVisitGivenStrategy(this);
	}

	public boolean isGivenStrategy() { return true; }

	public boolean hasStrategy() { return true; }
	public boolean hasSubject() { return true; }
	public boolean hasCases() { return true; }

private final org.meta_environment.rascal.ast.Strategy strategy;
	public org.meta_environment.rascal.ast.Strategy getStrategy() { return strategy; }
	private final org.meta_environment.rascal.ast.Expression subject;
	public org.meta_environment.rascal.ast.Expression getSubject() { return subject; }
	private final java.util.List<org.meta_environment.rascal.ast.Case> cases;
	public java.util.List<org.meta_environment.rascal.ast.Case> getCases() { return cases; }	
}
 public abstract <T> T accept(IASTVisitor<T> visitor);
}