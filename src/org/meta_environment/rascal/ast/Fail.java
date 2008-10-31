package org.meta_environment.rascal.ast;
import org.eclipse.imp.pdb.facts.ITree;
import java.util.Collections;
public abstract class Fail extends AbstractAST
{
  public class WithLabel extends Fail
  {
/* "fail" label:Name ";" -> Fail {cons("WithLabel")} */
    private WithLabel ()
    {
    }
    /*package */ WithLabel (ITree tree, Name label)
    {
      this.tree = tree;
      this.label = label;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitFailWithLabel (this);
    }
    private Name label;
    public Name getLabel ()
    {
      return label;
    }
    private void $setLabel (Name x)
    {
      this.label = x;
    }
    public WithLabel setLabel (Name x)
    {
      WithLabel z = new WithLabel ();
      z.$setLabel (x);
      return z;
    }
  }
  public class Ambiguity extends Fail
  {
    private final java.util.List < Fail > alternatives;
    public Ambiguity (java.util.List < Fail > alternatives)
    {
      this.alternatives = Collections.unmodifiableList (alternatives);
    }
    public java.util.List < Fail > getAlternatives ()
    {
      return alternatives;
    }
  }
  public class NoLabel extends Fail
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
