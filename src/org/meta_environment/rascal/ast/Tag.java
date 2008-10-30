package org.meta_environment.rascal.ast;
import org.eclipse.imp.pdb.facts.ITree;
import java.util.List;
import java.util.Collections;
public abstract class Tag extends AbstractAST
{
  public class Default extends Tag
  {
/* "@" name:Name TagString -> Tag {cons("Default")} */
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
      return visitor.visitTagDefault (this);
    }
    private Name name;
    public Name getname ()
    {
      return name;
    }
    private void $setname (Name x)
    {
      this.name = x;
    }
    public Default setname (Name x)
    {
      Default z = new Default ();
      z.$setname (x);
      return z;
    }
  }
  public class Ambiguity extends Tag
  {
    private final List < Tag > alternatives;
    public Ambiguity (List < Tag > alternatives)
    {
      this.alternatives = Collections.unmodifiableList (alternatives);
    }
    public List < Tag > getAlternatives ()
    {
      return alternatives;
    }
  }
}
