package org.rascalmpl.ast; 
import org.eclipse.imp.pdb.facts.INode; 
public abstract class FunctionType extends AbstractAST { 
public org.rascalmpl.ast.Type getType() { throw new UnsupportedOperationException(); }
	public java.util.List<org.rascalmpl.ast.TypeArg> getArguments() { throw new UnsupportedOperationException(); }
public boolean hasType() { return false; }
	public boolean hasArguments() { return false; }
public boolean isTypeArguments() { return false; }
static public class TypeArguments extends FunctionType {
/** type:Type "(" arguments:{TypeArg ","}* ")" -> FunctionType {cons("TypeArguments")} */
	public TypeArguments(INode node, org.rascalmpl.ast.Type type, java.util.List<org.rascalmpl.ast.TypeArg> arguments) {
		this.node = node;
		this.type = type;
		this.arguments = arguments;
	}
	@Override
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitFunctionTypeTypeArguments(this);
	}

	@Override
	public boolean isTypeArguments() { return true; }

	@Override
	public boolean hasType() { return true; }
	@Override
	public boolean hasArguments() { return true; }

private final org.rascalmpl.ast.Type type;
	@Override
	public org.rascalmpl.ast.Type getType() { return type; }
	private final java.util.List<org.rascalmpl.ast.TypeArg> arguments;
	@Override
	public java.util.List<org.rascalmpl.ast.TypeArg> getArguments() { return arguments; }	
}
static public class Ambiguity extends FunctionType {
  private final java.util.List<org.rascalmpl.ast.FunctionType> alternatives;
  public Ambiguity(INode node, java.util.List<org.rascalmpl.ast.FunctionType> alternatives) {
	this.alternatives = java.util.Collections.unmodifiableList(alternatives);
         this.node = node;
  }
  public java.util.List<org.rascalmpl.ast.FunctionType> getAlternatives() {
	return alternatives;
  }
  
  @Override
public <T> T accept(IASTVisitor<T> v) {
     return v.visitFunctionTypeAmbiguity(this);
  }
}
}