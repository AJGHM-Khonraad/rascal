package org.meta_environment.rascal.ast;
import org.eclipse.imp.pdb.facts.ITree;
import java.util.Collections;
public abstract class Label extends AbstractAST
{
  public class Empty extends Label
  {
/*  -> Label {cons("Empty")} */
    private Empty ()
    {
    }
    /*package */ Empty (ITree tree)
    {
      this.tree = tree;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitLabelEmpty (this);
    }
  }
  public class Ambiguity extends Label
  {
    private final java.util.List < Label > alternatives;
    public Ambiguity (java.util.List < Label > alternatives)
    {
      this.alternatives = Collections.unmodifiableList (alternatives);
    }
    public java.util.List < Label > getAlternatives ()
    {
      return alternatives;
    }
  }
  public class Default extends Label
  {
/* name:Name ":" -> Label {cons("Default")} */
    private Default ()
    {
    }
    /*package */ Default (ITree tree, Name name)
    {
      this.tree = tree;
      this.name = name;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitLabelDefault (this);
    }
    private Name name;
    public Name getName ()
    {
      return name;
    }
    private void $setName (Name x)
    {
      this.name = x;
    }
    public Default setName (Name x)
    {
      Default z = new Default ();
      z.$setName (x);
      return z;
    }
  }
}
