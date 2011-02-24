
package org.rascalmpl.ast;


import org.eclipse.imp.pdb.facts.INode;

import org.rascalmpl.interpreter.asserts.Ambiguous;

import org.eclipse.imp.pdb.facts.IConstructor;

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


public abstract class Alternative extends AbstractAST {
  public Alternative(INode node) {
    super(node);
  }
  

  public boolean hasName() {
    return false;
  }

  public org.rascalmpl.ast.Name getName() {
    throw new UnsupportedOperationException();
  }

  public boolean hasType() {
    return false;
  }

  public org.rascalmpl.ast.Type getType() {
    throw new UnsupportedOperationException();
  }


static public class Ambiguity extends Alternative {
  private final java.util.List<org.rascalmpl.ast.Alternative> alternatives;

  public Ambiguity(INode node, java.util.List<org.rascalmpl.ast.Alternative> alternatives) {
    super(node);
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
  
  public java.util.List<org.rascalmpl.ast.Alternative> getAlternatives() {
   return alternatives;
  }

  public <T> T accept(IASTVisitor<T> v) {
	return v.visitAlternativeAmbiguity(this);
  }
}





  public boolean isNamedType() {
    return false;
  }
  
static public class NamedType extends Alternative {
  // Production: sig("NamedType",[arg("org.rascalmpl.ast.Name","name"),arg("org.rascalmpl.ast.Type","type")])

  
     private final org.rascalmpl.ast.Name name;
  
     private final org.rascalmpl.ast.Type type;
  

  
public NamedType(INode node , org.rascalmpl.ast.Name name,  org.rascalmpl.ast.Type type) {
  super(node);
  
    this.name = name;
  
    this.type = type;
  
}


  @Override
  public boolean isNamedType() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitAlternativeNamedType(this);
  }
  
  
     @Override
     public org.rascalmpl.ast.Name getName() {
        return this.name;
     }
     
     @Override
     public boolean hasName() {
        return true;
     }
  
     @Override
     public org.rascalmpl.ast.Type getType() {
        return this.type;
     }
     
     @Override
     public boolean hasType() {
        return true;
     }
  	
}



}
