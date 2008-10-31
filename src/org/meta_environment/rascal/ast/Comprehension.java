package org.meta_environment.rascal.ast;
import org.eclipse.imp.pdb.facts.ITree;
public abstract class Comprehension extends AbstractAST
{
  static public class Set extends Comprehension
  {
/* "{" result:Expression "|" generators:{Generator ","}+ "}" -> Comprehension {cons("Set")} */
    private Set ()
    {
    }
    /*package */ Set (ITree tree,
		      org.meta_environment.rascal.ast.Expression result,
		      java.util.List <
		      org.meta_environment.rascal.ast.Generator > generators)
    {
      this.tree = tree;
      this.result = result;
      this.generators = generators;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitComprehensionSet (this);
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
      org.meta_environment.rascal.ast.Set z = new Set ();
      z.$setResult (x);
      return z;
    }
    private java.util.List < org.meta_environment.rascal.ast.Generator >
      generators;
    public java.util.List < org.meta_environment.rascal.ast.Generator >
      getGenerators ()
    {
      return generators;
    }
    private void $setGenerators (java.util.List <
				 org.meta_environment.rascal.ast.Generator >
				 x)
    {
      this.generators = x;
    }
    public Set setGenerators (java.util.List <
			      org.meta_environment.rascal.ast.Generator > x)
    {
      org.meta_environment.rascal.ast.Set z = new Set ();
      z.$setGenerators (x);
      return z;
    }
  }
  static public class Ambiguity extends Comprehension
  {
    private final java.util.List <
      org.meta_environment.rascal.ast.Comprehension > alternatives;
    public Ambiguity (java.util.List <
		      org.meta_environment.rascal.ast.Comprehension >
		      alternatives)
    {
      this.alternatives =
	java.util.Collections.unmodifiableList (alternatives);
    }
    public java.util.List < org.meta_environment.rascal.ast.Comprehension >
      getAlternatives ()
    {
      return alternatives;
    }
  }
  static public class List extends Comprehension
  {
/* "[" result:Expression "|" generators:{Generator ","}+ "]" -> Comprehension {cons("List")} */
    private List ()
    {
    }
    /*package */ List (ITree tree,
		       org.meta_environment.rascal.ast.Expression result,
		       java.util.List <
		       org.meta_environment.rascal.ast.Generator > generators)
    {
      this.tree = tree;
      this.result = result;
      this.generators = generators;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitComprehensionList (this);
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
    public List setResult (org.meta_environment.rascal.ast.Expression x)
    {
      org.meta_environment.rascal.ast.List z = new List ();
      z.$setResult (x);
      return z;
    }
    private java.util.List < org.meta_environment.rascal.ast.Generator >
      generators;
    public java.util.List < org.meta_environment.rascal.ast.Generator >
      getGenerators ()
    {
      return generators;
    }
    private void $setGenerators (java.util.List <
				 org.meta_environment.rascal.ast.Generator >
				 x)
    {
      this.generators = x;
    }
    public List setGenerators (java.util.List <
			       org.meta_environment.rascal.ast.Generator > x)
    {
      org.meta_environment.rascal.ast.List z = new List ();
      z.$setGenerators (x);
      return z;
    }
  }
}
