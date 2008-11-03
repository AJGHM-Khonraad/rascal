package org.meta_environment.rascal.ast;
import org.eclipse.imp.pdb.facts.ITree;
public abstract class Case extends AbstractAST
{
  static public class Rule extends Case
  {
/* "case" rule:Rule -> Case {cons("Rule")} */
    private Rule ()
    {
    }
    /*package */ Rule (ITree tree, org.meta_environment.rascal.ast.Rule rule)
    {
      this.tree = tree;
      this.rule = rule;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitCaseRule (this);
    }
    private org.meta_environment.rascal.ast.Rule rule;
    public org.meta_environment.rascal.ast.Rule getRule ()
    {
      return rule;
    }
    private void $setRule (org.meta_environment.rascal.ast.Rule x)
    {
      this.rule = x;
    }
    public Rule setRule (org.meta_environment.rascal.ast.Rule x)
    {
      Rule z = new Rule ();
      z.$setRule (x);
      return z;
    }
  }
  static public class Ambiguity extends Case
  {
    private final java.util.List < org.meta_environment.rascal.ast.Case >
      alternatives;
    public Ambiguity (java.util.List < org.meta_environment.rascal.ast.Case >
		      alternatives)
    {
      this.alternatives =
	java.util.Collections.unmodifiableList (alternatives);
    }
    public java.util.List < org.meta_environment.rascal.ast.Case >
      getAlternatives ()
    {
      return alternatives;
    }
  }
  static public class Default extends Case
  {
/* "default" ":" statement:Statement -> Case {cons("Default")} */
    private Default ()
    {
    }
    /*package */ Default (ITree tree,
			  org.meta_environment.rascal.ast.Statement statement)
    {
      this.tree = tree;
      this.statement = statement;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitCaseDefault (this);
    }
    private org.meta_environment.rascal.ast.Statement statement;
    public org.meta_environment.rascal.ast.Statement getStatement ()
    {
      return statement;
    }
    private void $setStatement (org.meta_environment.rascal.ast.Statement x)
    {
      this.statement = x;
    }
    public Default setStatement (org.meta_environment.rascal.ast.Statement x)
    {
      Default z = new Default ();
      z.$setStatement (x);
      return z;
    }
  }
}
