package org.meta_environment.rascal.ast;
import org.eclipse.imp.pdb.facts.ITree;
public abstract class Formals extends AbstractAST
{
  static public class Default extends Formals
  {
/* formals:{Formal ","}* -> Formals {cons("Default")} */
    private Default ()
    {
    }
    /*package */ Default (ITree tree, java.util.List < Formal > formals)
    {
      this.tree = tree;
      this.formals = formals;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitFormalsDefault (this);
    }
    private java.util.List < org.meta_environment.rascal.ast.Formal > formals;
    public java.util.List < org.meta_environment.rascal.ast.Formal >
      getFormals ()
    {
      return formals;
    }
    private void $setFormals (java.util.List <
			      org.meta_environment.rascal.ast.Formal > x)
    {
      this.formals = x;
    }
    public org.meta_environment.rascal.ast.Default setFormals (java.util.
							       List <
							       org.
							       meta_environment.
							       rascal.ast.
							       Formal > x)
    {
      org.meta_environment.rascal.ast.Default z = new Default ();
      z.$setFormals (x);
      return z;
    }
  }
  static public class Ambiguity extends Formals
  {
    private final java.util.List < Formals > alternatives;
    public Ambiguity (java.util.List < Formals > alternatives)
    {
      this.alternatives =
	java.util.Collections.unmodifiableList (alternatives);
    }
    public java.util.List < Formals > getAlternatives ()
    {
      return alternatives;
    }
  }
}
