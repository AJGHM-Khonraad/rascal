package org.rascalmpl.ast; 
import org.eclipse.imp.pdb.facts.INode; 
public abstract class Parameters extends AbstractAST { 
  public org.rascalmpl.ast.Formals getFormals() { throw new UnsupportedOperationException(); } public boolean hasFormals() { return false; } public boolean isDefault() { return false; }
static public class Default extends Parameters {
/** "(" formals:Formals ")" -> Parameters {cons("Default")} */
	public Default(INode node, org.rascalmpl.ast.Formals formals) {
		this.node = node;
		this.formals = formals;
	}
	@Override
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitParametersDefault(this);
	}

	@Override
	public boolean isDefault() { return true; }

	@Override
	public boolean hasFormals() { return true; }

private final org.rascalmpl.ast.Formals formals;
	@Override
	public org.rascalmpl.ast.Formals getFormals() { return formals; }	
}
static public class Ambiguity extends Parameters {
  private final java.util.List<org.rascalmpl.ast.Parameters> alternatives;
  public Ambiguity(INode node, java.util.List<org.rascalmpl.ast.Parameters> alternatives) {
	this.alternatives = java.util.Collections.unmodifiableList(alternatives);
         this.node = node;
  }
  public java.util.List<org.rascalmpl.ast.Parameters> getAlternatives() {
	return alternatives;
  }
  
  @Override
public <T> T accept(IASTVisitor<T> v) {
     return v.visitParametersAmbiguity(this);
  }
} public boolean isVarArgs() { return false; }
static public class VarArgs extends Parameters {
/** "(" formals:Formals "..." ")" -> Parameters {cons("VarArgs")} */
	public VarArgs(INode node, org.rascalmpl.ast.Formals formals) {
		this.node = node;
		this.formals = formals;
	}
	@Override
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitParametersVarArgs(this);
	}

	@Override
	public boolean isVarArgs() { return true; }

	@Override
	public boolean hasFormals() { return true; }

private final org.rascalmpl.ast.Formals formals;
	@Override
	public org.rascalmpl.ast.Formals getFormals() { return formals; }	
}
 @Override
public abstract <T> T accept(IASTVisitor<T> visitor);
}