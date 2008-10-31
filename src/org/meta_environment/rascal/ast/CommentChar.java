package org.meta_environment.rascal.ast;
import org.eclipse.imp.pdb.facts.ITree;
public abstract class CommentChar extends AbstractAST
{
  static public class Lexical extends CommentChar
  {
    /* ~[\*] -> CommentChar  */
  } public class Ambiguity extends CommentChar
  {
    private final java.util.List < CommentChar > alternatives;
    public Ambiguity (java.util.List < CommentChar > alternatives)
    {
      this.alternatives =
	java.util.Collections.unmodifiableList (alternatives);
    }
    public java.util.List < CommentChar > getAlternatives ()
    {
      return alternatives;
    }
  }
}
