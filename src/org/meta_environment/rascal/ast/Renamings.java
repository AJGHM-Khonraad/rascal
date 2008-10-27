package org.meta_environment.rascal.ast;
import org.eclipse.imp.pdb.facts.ITree;
public abstract class Renamings extends AbstractAST
{
  public class Renamings extends Renamings
  {
/* "renaming" renamings:{Renaming ","}+ -> Renamings {cons("Renamings")} */
    private Renamings ()
    {
    }
    /*package */ Renamings (ITree tree, List < Renaming > renamings)
    {
      this.tree = tree;
      this.renamings = renamings;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitRenamingsRenamings (this);
    }
    private List < Renaming > renamings;
    public List < Renaming > getrenamings ()
    {
      return renamings;
    }
    private void privateSetrenamings (List < Renaming > x)
    {
      this.renamings = x;
    }
    public Renamings setrenamings (List < Renaming > x)
    {
      Renamings z = new Renamings ();
      z.privateSetrenamings (x);
      return z;
    }
  }
}
