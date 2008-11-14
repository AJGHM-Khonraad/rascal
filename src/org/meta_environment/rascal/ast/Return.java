package org.meta_environment.rascal.ast;
import org.eclipse.imp.pdb.facts.ITree;
public abstract class Return extends AbstractAST
{
  public org.meta_environment.rascal.ast.Expression getExpression ()
  {
    throw new UnsupportedOperationException ();
  }
  public boolean hasExpression ()
  {
    return false;
  }
  public boolean isWithExpression ()
  {
    return false;
  }
  static public class WithExpression extends Return
  {
/* "return" expression:Expression ";" -> Return {cons("WithExpression")} */
    private WithExpression ()
    {
    }
    /*package */ WithExpression (ITree tree,
				 org.meta_environment.rascal.ast.
				 Expression expression)
    {
      this.tree = tree;
      this.expression = expression;
    }
    public < T > T accept (IASTVisitor < T > visitor)
    {
      return visitor.visitReturnWithExpression (this);
    }

    public boolean isWithExpression ()
    {
      return true;
    }

    public boolean hasExpression ()
    {
      return true;
    }

    private org.meta_environment.rascal.ast.Expression expression;
    public org.meta_environment.rascal.ast.Expression getExpression ()
    {
      return expression;
    }
    private void $setExpression (org.meta_environment.rascal.ast.Expression x)
    {
      this.expression = x;
    }
    public WithExpression setExpression (org.meta_environment.rascal.ast.
					 Expression x)
    {
      WithExpression z = new WithExpression ();
      z.$setExpression (x);
      return z;
    }
  }
  static public class Ambiguity extends Return
  {
    private final java.util.List < org.meta_environment.rascal.ast.Return >
      alternatives;
    public Ambiguity (java.util.List <
		      org.meta_environment.rascal.ast.Return > alternatives)
    {
      this.alternatives =
	java.util.Collections.unmodifiableList (alternatives);
    }
    public java.util.List < org.meta_environment.rascal.ast.Return >
      getAlternatives ()
    {
      return alternatives;
    }
  }
  public boolean isNoExpression ()
  {
    return false;
  }
  static public class NoExpression extends Return
  {
/* "return" ";" -> Return {cons("NoExpression")} */
    private NoExpression ()
    {
    }
    /*package */ NoExpression (ITree tree)
    {
      this.tree = tree;
    }
    public < T > T accept (IASTVisitor < T > visitor)
    {
      return visitor.visitReturnNoExpression (this);
    }

    public boolean isNoExpression ()
    {
      return true;
    }
  }
}
