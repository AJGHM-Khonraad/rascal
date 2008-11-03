package org.meta_environment.rascal.ast;
import org.eclipse.imp.pdb.facts.ITree;
public abstract class HexLongLiteral extends AbstractAST
{
  static public class Lexical extends HexLongLiteral
  {
    /* [0] [xX] [0-9a-fA-F]+ [lL] -> HexLongLiteral  */
    private String string;
    /*package */ Lexical (ITree tree, String string)
    {
      this.tree = tree;
      this.string = arg;
    }
    public String getString ()
    {
      return string;
    }
  }
  static public class Ambiguity extends HexLongLiteral
  {
    private final java.util.List <
      org.meta_environment.rascal.ast.HexLongLiteral > alternatives;
    public Ambiguity (java.util.List <
		      org.meta_environment.rascal.ast.HexLongLiteral >
		      alternatives)
    {
      this.alternatives =
	java.util.Collections.unmodifiableList (alternatives);
    }
    public java.util.List < org.meta_environment.rascal.ast.HexLongLiteral >
      getAlternatives ()
    {
      return alternatives;
    }
  }
}
