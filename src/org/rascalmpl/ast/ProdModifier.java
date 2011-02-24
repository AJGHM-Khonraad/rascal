
package org.rascalmpl.ast;


import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.INode;
import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;
import org.rascalmpl.interpreter.BooleanEvaluator;
import org.rascalmpl.interpreter.Evaluator;
import org.rascalmpl.interpreter.PatternEvaluator;
import org.rascalmpl.interpreter.asserts.Ambiguous;
import org.rascalmpl.interpreter.env.Environment;
import org.rascalmpl.interpreter.matching.IBooleanResult;
import org.rascalmpl.interpreter.matching.IMatchingResult;
import org.rascalmpl.interpreter.result.Result;


public abstract class ProdModifier extends AbstractAST {
  public ProdModifier(ISourceLocation loc) {
    super(loc);
  }
  

  public boolean hasTag() {
    return false;
  }

  public org.rascalmpl.ast.Tag getTag() {
    throw new UnsupportedOperationException();
  }

  public boolean hasAssociativity() {
    return false;
  }

  public org.rascalmpl.ast.Assoc getAssociativity() {
    throw new UnsupportedOperationException();
  }


static public class Ambiguity extends ProdModifier {
  private final java.util.List<org.rascalmpl.ast.ProdModifier> alternatives;

  public Ambiguity(ISourceLocation loc, java.util.List<org.rascalmpl.ast.ProdModifier> alternatives) {
    super(loc);
    this.alternatives = java.util.Collections.unmodifiableList(alternatives);
  }

  @Override
  public Result<IValue> interpret(Evaluator __eval) {
    throw new Ambiguous((IConstructor) this.getTree());
  }
  
  @Override
  public Type typeOf(Environment env) {
    throw new Ambiguous((IConstructor) this.getTree());
  }
  
  @Override
  public IBooleanResult buildBooleanBacktracker(BooleanEvaluator __eval) {
    throw new Ambiguous((IConstructor) this.getTree());
  }

  @Override
  public IMatchingResult buildMatcher(PatternEvaluator __eval) {
    throw new Ambiguous((IConstructor) this.getTree());
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
  

  
public Associativity(ISourceLocation loc, org.rascalmpl.ast.Assoc associativity) {
  super(loc);
  
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


  public boolean isTag() {
    return false;
  }
  
static public class Tag extends ProdModifier {
  // Production: sig("Tag",[arg("org.rascalmpl.ast.Tag","tag")])

  
     private final org.rascalmpl.ast.Tag tag;
  

  
public Tag(ISourceLocation loc, org.rascalmpl.ast.Tag tag) {
  super(loc);
  
    this.tag = tag;
  
}


  @Override
  public boolean isTag() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitProdModifierTag(this);
  }
  
  
     @Override
     public org.rascalmpl.ast.Tag getTag() {
        return this.tag;
     }
     
     @Override
     public boolean hasTag() {
        return true;
     }
  	
}


  public boolean isBracket() {
    return false;
  }
  
static public class Bracket extends ProdModifier {
  // Production: sig("Bracket",[])

  

  
public Bracket(ISourceLocation loc) {
  super(loc);
  
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

  

  
public Lexical(ISourceLocation loc) {
  super(loc);
  
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
