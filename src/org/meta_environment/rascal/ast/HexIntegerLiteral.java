package org.meta_environment.rascal.ast;
import org.eclipse.imp.pdb.facts.ITree;
public abstract class HexIntegerLiteral extends AbstractAST
{
  static public class Lexical extends HexIntegerLiteral
  {
    /* [0] [xX] [0-9a-fA-F]+ -> HexIntegerLiteral  */
  }
  static public class Ambiguity extends HexIntegerLiteral
  {
    public HexIntegerLiteral.Ambiguity makeHexIntegerLiteralAmbiguity (java.
								       util.
								       List <
								       HexIntegerLiteral
								       >
								       alternatives)
    {
      HexIntegerLiteral.Ambiguity amb =
	new HexIntegerLiteral.Ambiguity (alternatives);
      if (!table.containsKey (amb))
	{
	  table.put (amb, amb);
	}
      return (HexIntegerLiteral.Ambiguity) table.get (amb);
    }
    private final java.util.List < HexIntegerLiteral > alternatives;
    public Ambiguity (java.util.List < HexIntegerLiteral > alternatives)
    {
      this.alternatives =
	java.util.Collections.unmodifiableList (alternatives);
    }
    public java.util.List < HexIntegerLiteral > getAlternatives ()
    {
      return alternatives;
    }
  }
}
