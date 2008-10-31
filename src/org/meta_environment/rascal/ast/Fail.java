package org.meta_environment.rascal.ast;
import org.eclipse.imp.pdb.facts.ITree;
public abstract class Fail extends AbstractAST
{
  static public class WithLabel extends Fail
  {
/* "fail" label:Name ";" -> Fail {cons("WithLabel")} */
    private WithLabel ()
    {
    }
    /*package */ WithLabel (ITree tree,
			    org.meta_environment.rascal.ast.Name label)
    {
      this.tree = tree;
      this.label = label;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitFailWithLabel (this);
    }
    private org.meta_environment.rascal.ast.Name label;
    public org.meta_environment.rascal.ast.Name getLabel ()
    {
      return label;
    }
    private void $setLabel (org.meta_environment.rascal.ast.Name x)
    {
      this.label = x;
    }
    public WithLabel setLabel (org.meta_environment.rascal.ast.Name x)
    {
      WithLabel z = new WithLabel ();
      z.$setLabel (x);
      return z;
    }
  }
  static public class Ambiguity extends Fail
  {
    private final java.util.List < org.meta_environment.rascal.ast.Fail >
      alternatives;
    public Ambiguity (java.util.List < org.meta_environment.rascal.ast.Fail >
		      alternatives)
    {
      this.alternatives =
	java.util.Collections.unmodifiableList (alternatives);
    }
    public java.util.List < org.meta_environment.rascal.ast.Fail >
      getAlternatives ()
    {
      return alternatives;
    }
  }
  static public class NoLabel extends Fail
  {
/* "fail" ";" -> Fail {cons("NoLabel")} */
    private NoLabel ()
    {
    }
    /*package */ NoLabel (ITree tree)
    {
      this.tree = tree;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitFailNoLabel (this);
    }
  }
}
