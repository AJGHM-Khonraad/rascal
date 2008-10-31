package org.meta_environment.rascal.ast;
import org.eclipse.imp.pdb.facts.ITree;
public abstract class UnicodeEscape extends AbstractAST
{
  static public class Lexical extends UnicodeEscape
  {
    /* "\\" [u]+ [0-9a-fA-F] [0-9a-fA-F] [0-9a-fA-F] [0-9a-fA-F] -> UnicodeEscape  */
  }
  static public class Ambiguity extends UnicodeEscape
  {
    public UnicodeEscape.Ambiguity makeUnicodeEscapeAmbiguity (java.util.
							       List <
							       UnicodeEscape >
							       alternatives)
    {
      UnicodeEscape.Ambiguity amb =
	new UnicodeEscape.Ambiguity (alternatives);
      if (!table.containsKey (amb))
	{
	  table.put (amb, amb);
	}
      return (UnicodeEscape.Ambiguity) table.get (amb);
    }
    private final java.util.List < UnicodeEscape > alternatives;
    public Ambiguity (java.util.List < UnicodeEscape > alternatives)
    {
      this.alternatives =
	java.util.Collections.unmodifiableList (alternatives);
    }
    public java.util.List < UnicodeEscape > getAlternatives ()
    {
      return alternatives;
    }
  }
}
