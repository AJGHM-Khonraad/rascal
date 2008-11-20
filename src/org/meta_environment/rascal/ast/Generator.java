package org.meta_environment.rascal.ast; 
import org.eclipse.imp.pdb.facts.ITree; 
public abstract class Generator extends AbstractAST { 
  public org.meta_environment.rascal.ast.Expression getExpression() { throw new UnsupportedOperationException(); }
public boolean hasExpression() { return false; }
public boolean isExpression() { return false; }
static public class Expression extends Generator {
/* expression:Expression -> Generator {cons("Expression")} */
	private Expression() { }
	/*package*/ Expression(ITree tree, org.meta_environment.rascal.ast.Expression expression) {
		this.tree = tree;
		this.expression = expression;
	}
	@Override
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitGeneratorExpression(this);
	}

	@Override
	public boolean isExpression() { return true; }

	@Override
	public boolean hasExpression() { return true; }

private org.meta_environment.rascal.ast.Expression expression;
	@Override
	public org.meta_environment.rascal.ast.Expression getExpression() { return expression; }
	private void $setExpression(org.meta_environment.rascal.ast.Expression x) { this.expression = x; }
	public Expression setExpression(org.meta_environment.rascal.ast.Expression x) { 
		Expression z = new Expression();
 		z.$setExpression(x);
		return z;
	}	
}
static public class Ambiguity extends Generator {
  private final java.util.List<org.meta_environment.rascal.ast.Generator> alternatives;
  public Ambiguity(java.util.List<org.meta_environment.rascal.ast.Generator> alternatives) {
	this.alternatives = java.util.Collections.unmodifiableList(alternatives);
  }
  public java.util.List<org.meta_environment.rascal.ast.Generator> getAlternatives() {
	return alternatives;
  }
  
  @Override
public <T> T accept(IASTVisitor<T> v) {
     return v.visitGeneratorAmbiguity(this);
  }
} 
public org.meta_environment.rascal.ast.ValueProducer getProducer() { throw new UnsupportedOperationException(); }
public boolean hasProducer() { return false; }
public boolean isProducer() { return false; }
static public class Producer extends Generator {
/* producer:ValueProducer -> Generator {cons("Producer")} */
	private Producer() { }
	/*package*/ Producer(ITree tree, org.meta_environment.rascal.ast.ValueProducer producer) {
		this.tree = tree;
		this.producer = producer;
	}
	@Override
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitGeneratorProducer(this);
	}

	@Override
	public boolean isProducer() { return true; }

	@Override
	public boolean hasProducer() { return true; }

private org.meta_environment.rascal.ast.ValueProducer producer;
	@Override
	public org.meta_environment.rascal.ast.ValueProducer getProducer() { return producer; }
	private void $setProducer(org.meta_environment.rascal.ast.ValueProducer x) { this.producer = x; }
	public Producer setProducer(org.meta_environment.rascal.ast.ValueProducer x) { 
		Producer z = new Producer();
 		z.$setProducer(x);
		return z;
	}	
}
 @Override
public abstract <T> T accept(IASTVisitor<T> visitor);
}