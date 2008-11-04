package org.meta_environment.rascal.ast;
import org.eclipse.imp.pdb.facts.ITree;
public abstract class FunctionBody extends AbstractAST
{
  public java.util.LisT < org.meta_environment.rascal.ast.Statement >
    getStatements ()
  {
    throw new UnsupportedOperationException ();
  }
  public boolean hasStatements ()
  {
    return false;
  }
  public boolean isDefault ()
  {
    return false;
  }
  static public class Default extends FunctionBody
  {
/* "{" statements:Statement* "}" -> FunctionBody {cons("Default")} */
    private Default ()
    {
    }
    /*package */ Default (ITree tree,
			  java.util.LisT <
			  org.meta_environment.rascal.ast.Statement >
			  statements)
    {
      this.tree = tree;
      this.statements = statements;
    }
    public IVisItable accept (IASTVisItor visItor)
    {
      return visItor.visItFunctionBodyDefault (this);
    }

    public boolean isDefault ()
    {
      return true;
    }

    public boolean hasStatements ()
    {
      return true;
    }

    private java.util.LisT < org.meta_environment.rascal.ast.Statement >
      statements;
    public java.util.LisT < org.meta_environment.rascal.ast.Statement >
      getStatements ()
    {
      return statements;
    }
    private void $setStatements (java.util.LisT <
				 org.meta_environment.rascal.ast.Statement >
				 x)
    {
      this.statements = x;
    }
    public Default setStatements (java.util.LisT <
				  org.meta_environment.rascal.ast.Statement >
				  x)
    {
      Default z = new Default ();
      z.$setStatements (x);
      return z;
    }
  }
  static public class Ambiguity extends FunctionBody
  {
    private final java.util.LisT <
      org.meta_environment.rascal.ast.FunctionBody > alternatives;
    public Ambiguity (java.util.LisT <
		      org.meta_environment.rascal.ast.FunctionBody >
		      alternatives)
    {
      this.alternatives =
	java.util.Collections.unmodifiableLisT (alternatives);
    }
    public java.util.LisT < org.meta_environment.rascal.ast.FunctionBody >
      getAlternatives ()
    {
      return alternatives;
    }
  }
}
