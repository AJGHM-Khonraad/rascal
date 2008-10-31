package org.meta_environment.rascal.ast;
import org.eclipse.imp.pdb.facts.ITree;
public abstract class NamedRegExp extends AbstractAST
{
  public class Lexical extends NamedRegExp
  {
    /* ~[\>\\] -> NamedRegExp  */
  }
  public class Ambiguity extends NamedRegExp
  {
    private final java.util.List < NamedRegExp > alternatives;
    public Ambiguity (java.util.List < NamedRegExp > alternatives)
    {
      this.alternatives =
	java.util.Collections.unmodifiableList (alternatives);
    }
    public java.util.List < NamedRegExp > getAlternatives ()
    {
      return alternatives;
    }
  }
}
