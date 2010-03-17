package org.rascalmpl.ast; 
import org.eclipse.imp.pdb.facts.INode; 
public abstract class Case extends AbstractAST { 
  public org.rascalmpl.ast.PatternWithAction getPatternWithAction() { throw new UnsupportedOperationException(); }
public boolean hasPatternWithAction() { return false; }
public boolean isPatternWithAction() { return false; }
static public class PatternWithAction extends Case {
/** "case" patternWithAction:PatternWithAction -> Case {cons("PatternWithAction")} */
	public PatternWithAction(INode node, org.rascalmpl.ast.PatternWithAction patternWithAction) {
		this.node = node;
		this.patternWithAction = patternWithAction;
	}
	@Override
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitCasePatternWithAction(this);
	}

	@Override
	public boolean isPatternWithAction() { return true; }

	@Override
	public boolean hasPatternWithAction() { return true; }

private final org.rascalmpl.ast.PatternWithAction patternWithAction;
	@Override
	public org.rascalmpl.ast.PatternWithAction getPatternWithAction() { return patternWithAction; }	
}
static public class Ambiguity extends Case {
  private final java.util.List<org.rascalmpl.ast.Case> alternatives;
  public Ambiguity(INode node, java.util.List<org.rascalmpl.ast.Case> alternatives) {
	this.alternatives = java.util.Collections.unmodifiableList(alternatives);
         this.node = node;
  }
  public java.util.List<org.rascalmpl.ast.Case> getAlternatives() {
	return alternatives;
  }
  
  @Override
public <T> T accept(IASTVisitor<T> v) {
     return v.visitCaseAmbiguity(this);
  }
} 
public org.rascalmpl.ast.Statement getStatement() { throw new UnsupportedOperationException(); }
public boolean hasStatement() { return false; }
public boolean isDefault() { return false; }
static public class Default extends Case {
/** "default" ":" statement:Statement -> Case {cons("Default")} */
	public Default(INode node, org.rascalmpl.ast.Statement statement) {
		this.node = node;
		this.statement = statement;
	}
	@Override
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitCaseDefault(this);
	}

	@Override
	public boolean isDefault() { return true; }

	@Override
	public boolean hasStatement() { return true; }

private final org.rascalmpl.ast.Statement statement;
	@Override
	public org.rascalmpl.ast.Statement getStatement() { return statement; }	
}
 @Override
public abstract <T> T accept(IASTVisitor<T> visitor);
}