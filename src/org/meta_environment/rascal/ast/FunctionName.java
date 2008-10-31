package org.meta_environment.rascal.ast;
import org.eclipse.imp.pdb.facts.ITree;
public abstract class FunctionName extends AbstractAST
{
  static public class Name extends FunctionName
  {
/* name:Name -> FunctionName {cons("Name")} */
    private Name ()
    {
    }
    /*package */ Name (ITree tree, org.meta_environment.rascal.ast.Name name)
    {
      this.tree = tree;
      this.name = name;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitFunctionNameName (this);
    }
    private org.meta_environment.rascal.ast.Name name;
    public org.meta_environment.rascal.ast.Name getName ()
    {
      return name;
    }
    private void $setName (org.meta_environment.rascal.ast.Name x)
    {
      this.name = x;
    }
    public Name setName (org.meta_environment.rascal.ast.Name x)
    {
      org.meta_environment.rascal.ast.Name z = new Name ();
      z.$setName (x);
      return z;
    }
  }
  static public class Ambiguity extends FunctionName
  {
    public FunctionName.Ambiguity makeFunctionNameAmbiguity (java.util.List <
							     FunctionName >
							     alternatives)
    {
      FunctionName.Ambiguity amb = new FunctionName.Ambiguity (alternatives);
      if (!table.containsKey (amb))
	{
	  table.put (amb, amb);
	}
      return (FunctionName.Ambiguity) table.get (amb);
    }
    private final java.util.List < FunctionName > alternatives;
    public Ambiguity (java.util.List < FunctionName > alternatives)
    {
      this.alternatives =
	java.util.Collections.unmodifiableList (alternatives);
    }
    public java.util.List < FunctionName > getAlternatives ()
    {
      return alternatives;
    }
  }
  static public class Operator extends FunctionName
  {
/* operator:StandardOperator -> FunctionName {cons("Operator")} */
    private Operator ()
    {
    }
    /*package */ Operator (ITree tree,
			   org.meta_environment.rascal.ast.
			   StandardOperator operator)
    {
      this.tree = tree;
      this.operator = operator;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitFunctionNameOperator (this);
    }
    private org.meta_environment.rascal.ast.StandardOperator operator;
    public org.meta_environment.rascal.ast.StandardOperator getOperator ()
    {
      return operator;
    }
    private void $setOperator (org.meta_environment.rascal.ast.
			       StandardOperator x)
    {
      this.operator = x;
    }
    public Operator setOperator (org.meta_environment.rascal.ast.
				 StandardOperator x)
    {
      org.meta_environment.rascal.ast.Operator z = new Operator ();
      z.$setOperator (x);
      return z;
    }
  }
}
