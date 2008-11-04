package org.meta_environment.rascal.ast;
import org.eclipse.imp.pdb.facts.ITree;
public abstract class ShortChar extends AbstractAST
{
  static public class Lexical extends ShortChar
  {
    private String string;
    /*package */ Lexical (ITree tree, String string)
    {
      this.tree = tree;
      this.string = string;
    }
    public String getString ()
    {
      return string;
    }
  }
  static public class Ambiguity extends ShortChar
  {
    private final java.util.LisT < org.meta_environment.rascal.ast.ShortChar >
      alternatives;
    public Ambiguity (java.util.LisT <
		      org.meta_environment.rascal.ast.ShortChar >
		      alternatives)
    {
      this.alternatives =
	java.util.Collections.unmodifiableLisT (alternatives);
    }
    public java.util.LisT < org.meta_environment.rascal.ast.ShortChar >
      getAlternatives ()
    {
      return alternatives;
    }
  }
}
