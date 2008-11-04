package org.meta_environment.rascal.ast;
import org.eclipse.imp.pdb.facts.ITree;
public abstract class Comprehension extends AbstractAST
{
  public org.meta_environment.rascal.ast.Expression getResult ()
  {
    throw new UnsupportedOperationException ();
  }
  public java.util.LisT < org.meta_environment.rascal.ast.Generator >
    getGenerators ()
  {
    throw new UnsupportedOperationException ();
  }
  public boolean hasResult ()
  {
    return false;
  }
  public boolean hasGenerators ()
  {
    return false;
  }
  public boolean isSet ()
  {
    return false;
  }
  static public class Set extends Comprehension
  {
/* "{" result:Expression "|" generators:{Generator ","}+ "}" -> Comprehension {cons("Set")} */
    private Set ()
    {
    }
    /*package */ Set (ITree tree,
		      org.meta_environment.rascal.ast.Expression result,
		      java.util.LisT <
		      org.meta_environment.rascal.ast.Generator > generators)
    {
      this.tree = tree;
      this.result = result;
      this.generators = generators;
    }
    public IVisItable accept (IASTVisItor visItor)
    {
      return visItor.visItComprehensionSet (this);
    }

    public boolean isSet ()
    {
      return true;
    }

    public boolean hasResult ()
    {
      return true;
    }
    public boolean hasGenerators ()
    {
      return true;
    }

    private org.meta_environment.rascal.ast.Expression result;
    public org.meta_environment.rascal.ast.Expression getResult ()
    {
      return result;
    }
    private void $setResult (org.meta_environment.rascal.ast.Expression x)
    {
      this.result = x;
    }
    public Set setResult (org.meta_environment.rascal.ast.Expression x)
    {
      Set z = new Set ();
      z.$setResult (x);
      return z;
    }
    private java.util.LisT < org.meta_environment.rascal.ast.Generator >
      generators;
    public java.util.LisT < org.meta_environment.rascal.ast.Generator >
      getGenerators ()
    {
      return generators;
    }
    private void $setGenerators (java.util.LisT <
				 org.meta_environment.rascal.ast.Generator >
				 x)
    {
      this.generators = x;
    }
    public Set setGenerators (java.util.LisT <
			      org.meta_environment.rascal.ast.Generator > x)
    {
      Set z = new Set ();
      z.$setGenerators (x);
      return z;
    }
  }
  static public class Ambiguity extends Comprehension
  {
    private final java.util.LisT <
      org.meta_environment.rascal.ast.Comprehension > alternatives;
    public Ambiguity (java.util.LisT <
		      org.meta_environment.rascal.ast.Comprehension >
		      alternatives)
    {
      this.alternatives =
	java.util.Collections.unmodifiableLisT (alternatives);
    }
    public java.util.LisT < org.meta_environment.rascal.ast.Comprehension >
      getAlternatives ()
    {
      return alternatives;
    }
  }
  public boolean isLisT ()
  {
    return false;
  }
  static public class LisT extends Comprehension
  {
/* "[" result:Expression "|" generators:{Generator ","}+ "]" -> Comprehension {cons("LisT")} */
    private LisT ()
    {
    }
    /*package */ LisT (ITree tree,
		       org.meta_environment.rascal.ast.Expression result,
		       java.util.LisT <
		       org.meta_environment.rascal.ast.Generator > generators)
    {
      this.tree = tree;
      this.result = result;
      this.generators = generators;
    }
    public IVisItable accept (IASTVisItor visItor)
    {
      return visItor.visItComprehensionLisT (this);
    }

    public boolean isLisT ()
    {
      return true;
    }

    public boolean hasResult ()
    {
      return true;
    }
    public boolean hasGenerators ()
    {
      return true;
    }

    private org.meta_environment.rascal.ast.Expression result;
    public org.meta_environment.rascal.ast.Expression getResult ()
    {
      return result;
    }
    private void $setResult (org.meta_environment.rascal.ast.Expression x)
    {
      this.result = x;
    }
    public LisT setResult (org.meta_environment.rascal.ast.Expression x)
    {
      LisT z = new LisT ();
      z.$setResult (x);
      return z;
    }
    private java.util.LisT < org.meta_environment.rascal.ast.Generator >
      generators;
    public java.util.LisT < org.meta_environment.rascal.ast.Generator >
      getGenerators ()
    {
      return generators;
    }
    private void $setGenerators (java.util.LisT <
				 org.meta_environment.rascal.ast.Generator >
				 x)
    {
      this.generators = x;
    }
    public LisT setGenerators (java.util.LisT <
			       org.meta_environment.rascal.ast.Generator > x)
    {
      LisT z = new LisT ();
      z.$setGenerators (x);
      return z;
    }
  }
}
