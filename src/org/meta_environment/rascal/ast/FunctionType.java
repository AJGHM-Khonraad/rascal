package org.meta_environment.rascal.ast;
public abstract class FunctionType extends AbstractAST
{
  public class TypeArguments extends FunctionType
  {
    private Type type;
    private List < TypeArg > arguments;

    private TypeArguments ()
    {
    }
    /*package */ TypeArguments (ITree tree, Type type,
				List < TypeArg > arguments)
    {
      this.tree = tree;
      this.type = type;
      this.arguments = arguments;
    }
    public IVisitable accept (IVisitor visitor)
    {
      return visitor.visitTypeArgumentsFunctionType (this);
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
    public TypeArguments settype (Type x)
    {
      z = new TypeArguments ();
      z.privateSettype (x);
      return z;
    }
    private final List < TypeArg > arguments;
    public List < TypeArg > getarguments ()
    {
      return arguments;
    }
    private void privateSetarguments (List < TypeArg > x)
    {
      this.arguments = x;
    }
    public TypeArguments setarguments (List < TypeArg > x)
    {
      z = new TypeArguments ();
      z.privateSetarguments (x);
      return z;
    }
  }
}
