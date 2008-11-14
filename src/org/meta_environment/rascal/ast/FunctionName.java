package org.meta_environment.rascal.ast;
import org.eclipse.imp.pdb.facts.ITree;
public abstract class FunctionName extends AbstractAST
{
  public org.meta_environment.rascal.ast.Name getName ()
  {
    throw new UnsupportedOperationException ();
  }
  public boolean hasName ()
  {
    return false;
  }
  public boolean isName ()
  {
    return false;
  }
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

    public boolean isName ()
    {
      return true;
    }

    public boolean hasName ()
    {
      return true;
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
      Name z = new Name ();
      z.$setName (x);
      return z;
    }
  }
  static public class Ambiguity extends FunctionName
  {
    private final java.util.List <
      org.meta_environment.rascal.ast.FunctionName > alternatives;
    public Ambiguity (java.util.List <
		      org.meta_environment.rascal.ast.FunctionName >
		      alternatives)
    {
      this.alternatives =
	java.util.Collections.unmodifiableList (alternatives);
    }
    public java.util.List < org.meta_environment.rascal.ast.FunctionName >
      getAlternatives ()
    {
      return alternatives;
    }
  }
  public org.meta_environment.rascal.ast.StandardOperator getOperator ()
  {
    throw new UnsupportedOperationException ();
  }
  public boolean hasOperator ()
  {
    return false;
  }
  public boolean isOperator ()
  {
    return false;
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

    public boolean isOperator ()
    {
      return true;
    }

    public boolean hasOperator ()
    {
      return true;
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
      Operator z = new Operator ();
      z.$setOperator (x);
      return z;
    }
  }
}
