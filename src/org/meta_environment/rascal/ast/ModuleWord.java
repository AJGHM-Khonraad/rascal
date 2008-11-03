package org.meta_environment.rascal.ast;
import org.eclipse.imp.pdb.facts.ITree;
public abstract class ModuleWord extends AbstractAST
{
  static public class Lexical extends ModuleWord
  {
    /* letters:[A-Za-z0-9\_\-]+ -> ModuleWord  */
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
  static public class Ambiguity extends ModuleWord
  {
    private final java.util.List <
      org.meta_environment.rascal.ast.ModuleWord > alternatives;
    public Ambiguity (java.util.List <
		      org.meta_environment.rascal.ast.ModuleWord >
		      alternatives)
    {
      this.alternatives =
	java.util.Collections.unmodifiableList (alternatives);
    }
    public java.util.List < org.meta_environment.rascal.ast.ModuleWord >
      getAlternatives ()
    {
      return alternatives;
    }
  }
}
