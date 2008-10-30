package org.meta_environment.rascal.ast;
import org.eclipse.imp.pdb.facts.ITree;
public abstract class Alternative extends AbstractAST
{
  public class NamedType extends Alternative
  {
/* name:Name type:Type -> Alternative {cons("NamedType")} */
    private NamedType ()
    {
    }
    /*package */ NamedType (ITree tree, Name name, Type type)
    {
      this.tree = tree;
      this.name = name;
      this.type = type;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitAlternativeNamedType (this);
    }
    private Name name;
    public Name getname ()
    {
      return name;
    }
    private void privateSetname (Name x)
    {
      this.name = x;
    }
    public NamedType setname (Name x)
    {
      NamedType z = new NamedType ();
      z.privateSetname (x);
      return z;
    }
    private Type type;
    public Type gettype ()
    {
      return type;
    }
    private void privateSettype (Type x)
    {
      this.type = x;
    }
    public NamedType settype (Type x)
    {
      NamedType z = new NamedType ();
      z.privateSettype (x);
      return z;
    }
  }
  public class Ambiguity extends Alternative
  {
    private final List < Alternative > alternatives;
    public Ambiguity (List < Alternative > alternatives)
    {
      this.alternatives = Collections.immutableList (alternatives);
    }
    public List < Alternative > getAlternatives ()
    {
      return alternatives;
    }
  }
}
