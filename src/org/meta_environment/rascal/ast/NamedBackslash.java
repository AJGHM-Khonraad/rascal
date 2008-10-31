package org.meta_environment.rascal.ast;
import org.eclipse.imp.pdb.facts.ITree;
public abstract class NamedBackslash extends AbstractAST
{
  static public class Lexical extends NamedBackslash
  {
    /* [\\] -> NamedBackslash  */
  }
  static public class Ambiguity extends NamedBackslash
  {
    private final java.util.List <
      org.meta_environment.rascal.ast.NamedBackslash > alternatives;
    public Ambiguity (java.util.List <
		      org.meta_environment.rascal.ast.NamedBackslash >
		      alternatives)
    {
      this.alternatives =
	java.util.Collections.unmodifiableList (alternatives);
    }
    public java.util.List < org.meta_environment.rascal.ast.NamedBackslash >
      getAlternatives ()
    {
      return alternatives;
    }
  }
}
