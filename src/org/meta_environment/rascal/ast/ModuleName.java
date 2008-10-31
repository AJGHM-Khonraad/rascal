package org.meta_environment.rascal.ast;
import org.eclipse.imp.pdb.facts.ITree;
public abstract class ModuleName extends AbstractAST
{
  static public class Lexical extends ModuleName
  {
    /* ModuleWord -> ModuleName  */
  } static public class Ambiguity extends ModuleName
  {
    private final java.util.List <
      org.meta_environment.rascal.ast.ModuleName > alternatives;
    public Ambiguity (java.util.List <
		      org.meta_environment.rascal.ast.ModuleName >
		      alternatives)
    {
      this.alternatives =
	java.util.Collections.unmodifiableList (alternatives);
    }
    public java.util.List < org.meta_environment.rascal.ast.ModuleName >
      getAlternatives ()
    {
      return alternatives;
    }
  }
}
