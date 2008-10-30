package org.meta_environment.rascal.ast;
import org.eclipse.imp.pdb.facts.ITree;
import java.util.Collections;
public abstract class StringLiteral extends AbstractAST
{
  public class Lexical extends StringLiteral
  {
    /* "\"" StringCharacter* "\"" -> StringLiteral  */
  }
  public class Ambiguity extends StringLiteral
  {
    private final java.util.List < StringLiteral > alternatives;
    public Ambiguity (java.util.List < StringLiteral > alternatives)
    {
      this.alternatives = Collections.unmodifiableList (alternatives);
    }
    public java.util.List < StringLiteral > getAlternatives ()
    {
      return alternatives;
    }
  }
}
