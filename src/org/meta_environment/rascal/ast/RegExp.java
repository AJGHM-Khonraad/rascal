package org.meta_environment.rascal.ast;
import org.eclipse.imp.pdb.facts.ITree;
import java.util.Collections;
public abstract class RegExp extends AbstractAST
{
  public class Lexical extends RegExp
  {
    /* Backslash -> RegExp  */
  }
  public class Ambiguity extends RegExp
  {
    private final java.util.List < RegExp > alternatives;
    public Ambiguity (java.util.List < RegExp > alternatives)
    {
      this.alternatives = Collections.unmodifiableList (alternatives);
    }
    public java.util.List < RegExp > getAlternatives ()
    {
      return alternatives;
    }
  }
  public class Lexical extends RegExp
  {
    /* [\\][\/\<\\] -> RegExp  */
  }
  public class Lexical extends RegExp
  {
    /* ~[\/\<\\] -> RegExp  */
  }
  public class Lexical extends RegExp
  {
    /* "<" Name ":" NamedRegExp* ">" -> RegExp  */
  }
}
