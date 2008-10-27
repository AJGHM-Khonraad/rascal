package org.meta_environment.rascal.ast;
public abstract class Header extends AbstractAST
{
  public class Default extends Header
  {
    private ModuleName name;
    private Annotations annos;
    private List < Import > imports;

    private Default ()
    {
    }
    /*package */ Default (ITree tree, ModuleName name, Annotations annos,
			  List < Import > imports)
    {
      this.tree = tree;
      this.name = name;
      this.annos = annos;
      this.imports = imports;
    }
    public IVisitable accept (IVisitor visitor)
    {
      return visitor.visitDefaultHeader (this);
    }
    private final ModuleName name;
    public ModuleName getname ()
    {
      return name;
    }
    private void privateSetname (ModuleName x)
    {
      this.name = x;
    }
    public Default setname (ModuleName x)
    {
      z = new Default ();
      z.privateSetname (x);
      return z;
    }
    private final Annotations annos;
    public Annotations getannos ()
    {
      return annos;
    }
    private void privateSetannos (Annotations x)
    {
      this.annos = x;
    }
    public Default setannos (Annotations x)
    {
      z = new Default ();
      z.privateSetannos (x);
      return z;
    }
    private final List < Import > imports;
    public List < Import > getimports ()
    {
      return imports;
    }
    private void privateSetimports (List < Import > x)
    {
      this.imports = x;
    }
    public Default setimports (List < Import > x)
    {
      z = new Default ();
      z.privateSetimports (x);
      return z;
    }
  }
  public class Parameters extends Header
  {
    private ModuleName name;
    private ModuleParameters params;
    private Annotations annos;
    private List < Import > imports;

    private Parameters ()
    {
    }
    /*package */ Parameters (ITree tree, ModuleName name,
			     ModuleParameters params, Annotations annos,
			     List < Import > imports)
    {
      this.tree = tree;
      this.name = name;
      this.params = params;
      this.annos = annos;
      this.imports = imports;
    }
    public IVisitable accept (IVisitor visitor)
    {
      return visitor.visitParametersHeader (this);
    }
    private final ModuleName name;
    public ModuleName getname ()
    {
      return name;
    }
    private void privateSetname (ModuleName x)
    {
      this.name = x;
    }
    public Parameters setname (ModuleName x)
    {
      z = new Parameters ();
      z.privateSetname (x);
      return z;
    }
    private final ModuleParameters params;
    public ModuleParameters getparams ()
    {
      return params;
    }
    private void privateSetparams (ModuleParameters x)
    {
      this.params = x;
    }
    public Parameters setparams (ModuleParameters x)
    {
      z = new Parameters ();
      z.privateSetparams (x);
      return z;
    }
    private final Annotations annos;
    public Annotations getannos ()
    {
      return annos;
    }
    private void privateSetannos (Annotations x)
    {
      this.annos = x;
    }
    public Parameters setannos (Annotations x)
    {
      z = new Parameters ();
      z.privateSetannos (x);
      return z;
    }
    private final List < Import > imports;
    public List < Import > getimports ()
    {
      return imports;
    }
    private void privateSetimports (List < Import > x)
    {
      this.imports = x;
    }
    public Parameters setimports (List < Import > x)
    {
      z = new Parameters ();
      z.privateSetimports (x);
      return z;
    }
  }
}
