
package org.rascalmpl.ast;


import org.eclipse.imp.pdb.facts.INode;

import org.rascalmpl.interpreter.asserts.Ambiguous;

import org.eclipse.imp.pdb.facts.IConstructor;

import org.eclipse.imp.pdb.facts.IValue;

import org.rascalmpl.interpreter.BooleanEvaluator;

import org.rascalmpl.interpreter.Evaluator;

import org.rascalmpl.interpreter.PatternEvaluator;

import org.rascalmpl.interpreter.asserts.Ambiguous;

import org.rascalmpl.interpreter.env.Environment;

import org.rascalmpl.interpreter.matching.IBooleanResult;

import org.rascalmpl.interpreter.matching.IMatchingResult;

import org.rascalmpl.interpreter.result.Result;


public abstract class Assoc extends AbstractAST {
  public Assoc(INode node) {
    super(node);
  }
  


static public class Ambiguity extends Assoc {
  private final java.util.List<org.rascalmpl.ast.Assoc> alternatives;

  public Ambiguity(INode node, java.util.List<org.rascalmpl.ast.Assoc> alternatives) {
    super(node);
    this.alternatives = java.util.Collections.unmodifiableList(alternatives);
  }

  @Override
  public Result<IValue> interpret(Evaluator __eval) {
    throw new Ambiguous((IConstructor) this.getTree());
  }
  
  @Override
  public org.eclipse.imp.pdb.facts.type.Type typeOf(Environment env) {
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
  
  public java.util.List<org.rascalmpl.ast.Assoc> getAlternatives() {
   return alternatives;
  }

  public <T> T accept(IASTVisitor<T> v) {
	return v.visitAssocAmbiguity(this);
  }
}





  public boolean isRight() {
    return false;
  }
  
static public class Right extends Assoc {
  // Production: sig("Right",[])

  

  
public Right(INode node ) {
  super(node);
  
}


  @Override
  public boolean isRight() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitAssocRight(this);
  }
  
  	
}


  public boolean isNonAssociative() {
    return false;
  }
  
static public class NonAssociative extends Assoc {
  // Production: sig("NonAssociative",[])

  

  
public NonAssociative(INode node ) {
  super(node);
  
}


  @Override
  public boolean isNonAssociative() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitAssocNonAssociative(this);
  }
  
  	
}


  public boolean isLeft() {
    return false;
  }
  
static public class Left extends Assoc {
  // Production: sig("Left",[])

  

  
public Left(INode node ) {
  super(node);
  
}


  @Override
  public boolean isLeft() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitAssocLeft(this);
  }
  
  	
}


  public boolean isAssociative() {
    return false;
  }
  
static public class Associative extends Assoc {
  // Production: sig("Associative",[])

  

  
public Associative(INode node ) {
  super(node);
  
}


  @Override
  public boolean isAssociative() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitAssocAssociative(this);
  }
  
  	
}



}
