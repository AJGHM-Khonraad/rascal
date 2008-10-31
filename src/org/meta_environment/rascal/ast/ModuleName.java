package org.meta_environment.rascal.ast;
import org.eclipse.imp.pdb.facts.ITree;
public abstract class ModuleName extends AbstractAST
{
  public class Lexical extends ModuleName
  {
    /* ModuleWord -> ModuleName  */
  }
  public class Ambiguity extends ModuleName
  {
    private final java.util.List < ModuleName > alternatives;
    public Ambiguity (java.util.List < ModuleName > alternatives)
    {
      this.alternatives =
	java.util.Collections.unmodifiableList (alternatives);
    }
    public java.util.List < ModuleName > getAlternatives ()
    {
      return alternatives;
    }
  }
}
