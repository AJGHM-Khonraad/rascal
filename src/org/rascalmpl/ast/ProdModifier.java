
package org.rascalmpl.ast;


import org.eclipse.imp.pdb.facts.INode;


public abstract class ProdModifier extends AbstractAST {
  public ProdModifier(INode node) {
    super(node);
  }
  

  public boolean hasAssociativity() {
    return false;
  }

  public org.rascalmpl.ast.Assoc getAssociativity() {
    throw new UnsupportedOperationException();
  }


static public class Ambiguity extends ProdModifier {
  private final java.util.List<org.rascalmpl.ast.ProdModifier> alternatives;

  public Ambiguity(INode node, java.util.List<org.rascalmpl.ast.ProdModifier> alternatives) {
    super(node);
    this.alternatives = java.util.Collections.unmodifiableList(alternatives);
  }

  public java.util.List<org.rascalmpl.ast.ProdModifier> getAlternatives() {
   return alternatives;
  }

  public <T> T accept(IASTVisitor<T> v) {
	return v.visitProdModifierAmbiguity(this);
  }
}





  public boolean isAssociativity() {
    return false;
  }
  
static public class Associativity extends ProdModifier {
  // Production: sig("Associativity",[arg("org.rascalmpl.ast.Assoc","associativity")])

  
     private final org.rascalmpl.ast.Assoc associativity;
  

  
public Associativity(INode node , org.rascalmpl.ast.Assoc associativity) {
  super(node);
  
    this.associativity = associativity;
  
}


  @Override
  public boolean isAssociativity() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitProdModifierAssociativity(this);
  }
  
  
     @Override
     public org.rascalmpl.ast.Assoc getAssociativity() {
        return this.associativity;
     }
     
     @Override
     public boolean hasAssociativity() {
        return true;
     }
  	
}


  public boolean isBracket() {
    return false;
  }
  
static public class Bracket extends ProdModifier {
  // Production: sig("Bracket",[])

  

  
public Bracket(INode node ) {
  super(node);
  
}


  @Override
  public boolean isBracket() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitProdModifierBracket(this);
  }
  
  	
}


  public boolean isLexical() {
    return false;
  }
  
static public class Lexical extends ProdModifier {
  // Production: sig("Lexical",[])

  

  
public Lexical(INode node ) {
  super(node);
  
}


  @Override
  public boolean isLexical() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitProdModifierLexical(this);
  }
  
  	
}



}
