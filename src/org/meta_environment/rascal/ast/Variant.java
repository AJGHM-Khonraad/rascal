package org.meta_environment.rascal.ast;
import org.eclipse.imp.pdb.facts.ITree;
import java.util.List;
import java.util.Collections;
public abstract class Variant extends AbstractAST
{
  public class Type extends Variant
  {
/* type:Type name:Name -> Variant {cons("Type")} */
    private Type ()
    {
    }
    /*package */ Type (ITree tree, Type type, Name name)
    {
      this.tree = tree;
      this.type = type;
      this.name = name;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitVariantType (this);
    }
    private Type type;
    public Type gettype ()
    {
      return type;
    }
    private void $settype (Type x)
    {
      this.type = x;
    }
    public Type settype (Type x)
    {
      Type z = new Type ();
      z.$settype (x);
      return z;
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
    public Type setname (Name x)
    {
      Type z = new Type ();
      z.$setname (x);
      return z;
    }
  }
  public class Ambiguity extends Variant
  {
    private final List < Variant > alternatives;
    public Ambiguity (List < Variant > alternatives)
    {
      this.alternatives = Collections.unmodifiableList (alternatives);
    }
    public List < Variant > getAlternatives ()
    {
      return alternatives;
    }
  }
  public class NAryConstructor extends Variant
  {
/* name:Name "(" arguments:{TypeArg ","}+ ")" -> Variant {cons("NAryConstructor")} */
    private NAryConstructor ()
    {
    }
    /*package */ NAryConstructor (ITree tree, Name name,
				  List < TypeArg > arguments)
    {
      this.tree = tree;
      this.name = name;
      this.arguments = arguments;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitVariantNAryConstructor (this);
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
    public NAryConstructor setname (Name x)
    {
      NAryConstructor z = new NAryConstructor ();
      z.$setname (x);
      return z;
    }
    private List < TypeArg > arguments;
    public List < TypeArg > getarguments ()
    {
      return arguments;
    }
    private void $setarguments (List < TypeArg > x)
    {
      this.arguments = x;
    }
    public NAryConstructor setarguments (List < TypeArg > x)
    {
      NAryConstructor z = new NAryConstructor ();
      z.$setarguments (x);
      return z;
    }
  }
  public class NillaryConstructor extends Variant
  {
/* name:Name -> Variant {cons("NillaryConstructor")} */
    private NillaryConstructor ()
    {
    }
    /*package */ NillaryConstructor (ITree tree, Name name)
    {
      this.tree = tree;
      this.name = name;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitVariantNillaryConstructor (this);
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
    public NillaryConstructor setname (Name x)
    {
      NillaryConstructor z = new NillaryConstructor ();
      z.$setname (x);
      return z;
    }
  }
}
