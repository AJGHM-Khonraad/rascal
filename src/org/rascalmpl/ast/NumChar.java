
package org.rascalmpl.ast;


import org.eclipse.imp.pdb.facts.INode;


public abstract class NumChar extends AbstractAST {
  public NumChar(INode node) {
    super(node);
  }
  


static public class Ambiguity extends NumChar {
  private final java.util.List<org.rascalmpl.ast.NumChar> alternatives;

  public Ambiguity(INode node, java.util.List<org.rascalmpl.ast.NumChar> alternatives) {
    super(node);
    this.alternatives = java.util.Collections.unmodifiableList(alternatives);
  }

  public java.util.List<org.rascalmpl.ast.NumChar> getAlternatives() {
   return alternatives;
  }

  public <T> T accept(IASTVisitor<T> v) {
	return v.visitNumCharAmbiguity(this);
  }
}



 
static public class Lexical extends NumChar {
  private final java.lang.String string;
  public Lexical(INode node, java.lang.String string) {
    super(node);
    this.string = string;
  }
  public java.lang.String getString() {
    return string;
  }
  public <T> T accept(IASTVisitor<T> v) {
    return v.visitNumCharLexical(this);
  }
}





}
