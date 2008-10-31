package org.meta_environment.rascal.ast;
import org.eclipse.imp.pdb.facts.ITree;
public abstract class Rule extends AbstractAST
{
  static public class WithGuard extends Rule
  {
/* "[" type:Type "]" match:Match -> Rule {cons("WithGuard")} */
    private WithGuard ()
    {
    }
    /*package */ WithGuard (ITree tree,
			    org.meta_environment.rascal.ast.Type type,
			    org.meta_environment.rascal.ast.Match match)
    {
      this.tree = tree;
      this.type = type;
      this.match = match;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitRuleWithGuard (this);
    }
    private org.meta_environment.rascal.ast.Type type;
    public org.meta_environment.rascal.ast.Type getType ()
    {
      return type;
    }
    private void $setType (org.meta_environment.rascal.ast.Type x)
    {
      this.type = x;
    }
    public WithGuard setType (org.meta_environment.rascal.ast.Type x)
    {
      org.meta_environment.rascal.ast.WithGuard z = new WithGuard ();
      z.$setType (x);
      return z;
    }
    private org.meta_environment.rascal.ast.Match match;
    public org.meta_environment.rascal.ast.Match getMatch ()
    {
      return match;
    }
    private void $setMatch (org.meta_environment.rascal.ast.Match x)
    {
      this.match = x;
    }
    public WithGuard setMatch (org.meta_environment.rascal.ast.Match x)
    {
      org.meta_environment.rascal.ast.WithGuard z = new WithGuard ();
      z.$setMatch (x);
      return z;
    }
  }
  static public class Ambiguity extends Rule
  {
    public Rule.Ambiguity makeRuleAmbiguity (java.util.List < Rule >
					     alternatives)
    {
      Rule.Ambiguity amb = new Rule.Ambiguity (alternatives);
      if (!table.containsKey (amb))
	{
	  table.put (amb, amb);
	}
      return (Rule.Ambiguity) table.get (amb);
    }
    private final java.util.List < Rule > alternatives;
    public Ambiguity (java.util.List < Rule > alternatives)
    {
      this.alternatives =
	java.util.Collections.unmodifiableList (alternatives);
    }
    public java.util.List < Rule > getAlternatives ()
    {
      return alternatives;
    }
  }
  static public class NoGuard extends Rule
  {
/* match:Match -> Rule {cons("NoGuard")} */
    private NoGuard ()
    {
    }
    /*package */ NoGuard (ITree tree,
			  org.meta_environment.rascal.ast.Match match)
    {
      this.tree = tree;
      this.match = match;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitRuleNoGuard (this);
    }
    private org.meta_environment.rascal.ast.Match match;
    public org.meta_environment.rascal.ast.Match getMatch ()
    {
      return match;
    }
    private void $setMatch (org.meta_environment.rascal.ast.Match x)
    {
      this.match = x;
    }
    public NoGuard setMatch (org.meta_environment.rascal.ast.Match x)
    {
      org.meta_environment.rascal.ast.NoGuard z = new NoGuard ();
      z.$setMatch (x);
      return z;
    }
  }
}
