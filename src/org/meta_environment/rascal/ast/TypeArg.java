package org.meta_environment.rascal.ast; 
import org.eclipse.imp.pdb.facts.INode; 
public abstract class TypeArg extends AbstractAST { 
  public org.meta_environment.rascal.ast.Type getType() { throw new UnsupportedOperationException(); } public boolean hasType() { return false; } public boolean isDefault() { return false; }
static public class Default extends TypeArg {
/** type:Type -> TypeArg {cons("Default")} */
	public Default(INode node, org.meta_environment.rascal.ast.Type type) {
		this.node = node;
		this.type = type;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitTypeArgDefault(this);
	}

	public boolean isDefault() { return true; }

	public boolean hasType() { return true; }

private final org.meta_environment.rascal.ast.Type type;
	public org.meta_environment.rascal.ast.Type getType() { return type; }	
}
static public class Ambiguity extends TypeArg {
  private final java.util.List<org.meta_environment.rascal.ast.TypeArg> alternatives;
  public Ambiguity(INode node, java.util.List<org.meta_environment.rascal.ast.TypeArg> alternatives) {
	this.alternatives = java.util.Collections.unmodifiableList(alternatives);
         this.node = node;
  }
  public java.util.List<org.meta_environment.rascal.ast.TypeArg> getAlternatives() {
	return alternatives;
  }
  
  public <T> T accept(IASTVisitor<T> v) {
     return v.visitTypeArgAmbiguity(this);
  }
} public org.meta_environment.rascal.ast.Name getName() { throw new UnsupportedOperationException(); } public boolean hasName() { return false; }
public boolean isNamed() { return false; }
static public class Named extends TypeArg {
/** type:Type name:Name -> TypeArg {cons("Named")} */
	public Named(INode node, org.meta_environment.rascal.ast.Type type, org.meta_environment.rascal.ast.Name name) {
		this.node = node;
		this.type = type;
		this.name = name;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitTypeArgNamed(this);
	}

	public boolean isNamed() { return true; }

	public boolean hasType() { return true; }
	public boolean hasName() { return true; }

private final org.meta_environment.rascal.ast.Type type;
	public org.meta_environment.rascal.ast.Type getType() { return type; }
	private final org.meta_environment.rascal.ast.Name name;
	public org.meta_environment.rascal.ast.Name getName() { return name; }	
}
 public abstract <T> T accept(IASTVisitor<T> visitor);
}