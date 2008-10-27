package org.meta_environment.rascal.ast;
import org.eclipse.imp.pdb.facts.ITree;
public abstract class ModuleActuals extends AbstractAST
{
  public class Actuals extends ModuleActuals
  {
/* "[" types:{Type ","}+ "]" -> ModuleActuals {cons("Actuals")} */
    private Actuals ()
    {
    }
    /*package */ Actuals (ITree tree, List < Type > types)
    {
      this.tree = tree;
      this.types = types;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitActualsModuleActuals (this);
    }
    private List < Type > types;
    public List < Type > gettypes ()
    {
      return types;
    }
    private void privateSettypes (List < Type > x)
    {
      this.types = x;
    }
    public Actuals settypes (List < Type > x)
    {
      Actuals z = new Actuals ();
      z.privateSettypes (x);
      return z;
    }
  }
}
