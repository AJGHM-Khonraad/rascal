package org.meta_environment.rascal.ast;
public abstract class Alternative extends AbstractAST
{
  public class NamedType extends Alternative
  {
    private Name name;
    private Type type;

    private NamedType ()
    {
    }
    /*package */ NamedType (ITree tree, Name name, Type type)
    {
      this.tree = tree;
      this.name = name;
      this.type = type;
    }
    public IVisitable accept (IVisitor visitor)
    {
      return visitor.visitNamedTypeAlternative (this);
    }
    private final Name name;
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
      z = new NamedType ();
      z.privateSetname (x);
      return z;
    }
    private final Type type;
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
      z = new NamedType ();
      z.privateSettype (x);
      return z;
    }
  }
}
