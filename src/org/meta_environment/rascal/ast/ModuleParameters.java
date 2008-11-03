package org.meta_environment.rascal.ast;
import org.eclipse.imp.pdb.facts.ITree;
public abstract class ModuleParameters extends AbstractAST
{
  public java.util.List < org.meta_environment.rascal.ast.TypeVar >
    getParameters ()
  {
    throw new UnsupportedOperationException ();
  }
  static public class Default extends ModuleParameters
  {
/* "[" parameters:{TypeVar ","}+ "]" -> ModuleParameters {cons("Default")} */
    private Default ()
    {
    }
    /*package */ Default (ITree tree,
			  java.util.List <
			  org.meta_environment.rascal.ast.TypeVar >
			  parameters)
    {
      this.tree = tree;
      this.parameters = parameters;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitModuleParametersDefault (this);
    }
    private java.util.List < org.meta_environment.rascal.ast.TypeVar >
      parameters;
    public java.util.List < org.meta_environment.rascal.ast.TypeVar >
      getParameters ()
    {
      return parameters;
    }
    private void $setParameters (java.util.List <
				 org.meta_environment.rascal.ast.TypeVar > x)
    {
      this.parameters = x;
    }
    public Default setParameters (java.util.List <
				  org.meta_environment.rascal.ast.TypeVar > x)
    {
      Default z = new Default ();
      z.$setParameters (x);
      return z;
    }
  }
  static public class Ambiguity extends ModuleParameters
  {
    private final java.util.List <
      org.meta_environment.rascal.ast.ModuleParameters > alternatives;
    public Ambiguity (java.util.List <
		      org.meta_environment.rascal.ast.ModuleParameters >
		      alternatives)
    {
      this.alternatives =
	java.util.Collections.unmodifiableList (alternatives);
    }
    public java.util.List < org.meta_environment.rascal.ast.ModuleParameters >
      getAlternatives ()
    {
      return alternatives;
    }
  }
}
