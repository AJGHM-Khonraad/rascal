package org.meta_environment.rascal.ast;
import org.eclipse.imp.pdb.facts.ITree;
public abstract class Assignment extends AbstractAST
{
  public boolean isDefault ()
  {
    return false;
  }
  static public class Default extends Assignment
  {
/* "=" -> Assignment {cons("Default")} */
    private Default ()
    {
    }
    /*package */ Default (ITree tree)
    {
      this.tree = tree;
    }
    public IVisItable accept (IASTVisItor visItor)
    {
      return visItor.visItAssignmentDefault (this);
    }

    public boolean isDefault ()
    {
      return true;
    }
  }
  static public class Ambiguity extends Assignment
  {
    private final java.util.LisT <
      org.meta_environment.rascal.ast.Assignment > alternatives;
    public Ambiguity (java.util.LisT <
		      org.meta_environment.rascal.ast.Assignment >
		      alternatives)
    {
      this.alternatives =
	java.util.Collections.unmodifiableLisT (alternatives);
    }
    public java.util.LisT < org.meta_environment.rascal.ast.Assignment >
      getAlternatives ()
    {
      return alternatives;
    }
  }
  public boolean isAddition ()
  {
    return false;
  }
  static public class Addition extends Assignment
  {
/* "+=" -> Assignment {cons("Addition")} */
    private Addition ()
    {
    }
    /*package */ Addition (ITree tree)
    {
      this.tree = tree;
    }
    public IVisItable accept (IASTVisItor visItor)
    {
      return visItor.visItAssignmentAddition (this);
    }

    public boolean isAddition ()
    {
      return true;
    }
  }
  public boolean isSubstraction ()
  {
    return false;
  }
  static public class Substraction extends Assignment
  {
/* "-=" -> Assignment {cons("Substraction")} */
    private Substraction ()
    {
    }
    /*package */ Substraction (ITree tree)
    {
      this.tree = tree;
    }
    public IVisItable accept (IASTVisItor visItor)
    {
      return visItor.visItAssignmentSubstraction (this);
    }

    public boolean isSubstraction ()
    {
      return true;
    }
  }
  public boolean isProduct ()
  {
    return false;
  }
  static public class Product extends Assignment
  {
/* "*=" -> Assignment {cons("Product")} */
    private Product ()
    {
    }
    /*package */ Product (ITree tree)
    {
      this.tree = tree;
    }
    public IVisItable accept (IASTVisItor visItor)
    {
      return visItor.visItAssignmentProduct (this);
    }

    public boolean isProduct ()
    {
      return true;
    }
  }
  public boolean isDivisIon ()
  {
    return false;
  }
  static public class DivisIon extends Assignment
  {
/* "/=" -> Assignment {cons("DivisIon")} */
    private DivisIon ()
    {
    }
    /*package */ DivisIon (ITree tree)
    {
      this.tree = tree;
    }
    public IVisItable accept (IASTVisItor visItor)
    {
      return visItor.visItAssignmentDivisIon (this);
    }

    public boolean isDivisIon ()
    {
      return true;
    }
  }
  public boolean isInteresection ()
  {
    return false;
  }
  static public class Interesection extends Assignment
  {
/* "&=" -> Assignment {cons("Interesection")} */
    private Interesection ()
    {
    }
    /*package */ Interesection (ITree tree)
    {
      this.tree = tree;
    }
    public IVisItable accept (IASTVisItor visItor)
    {
      return visItor.visItAssignmentInteresection (this);
    }

    public boolean isInteresection ()
    {
      return true;
    }
  }
}
