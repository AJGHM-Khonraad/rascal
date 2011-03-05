
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


public abstract class Strategy extends AbstractAST {
  public Strategy(INode node) {
    super(node);
  }
  


static public class Ambiguity extends Strategy {
  private final java.util.List<org.rascalmpl.ast.Strategy> alternatives;

  public Ambiguity(INode node, java.util.List<org.rascalmpl.ast.Strategy> alternatives) {
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
  
  public java.util.List<org.rascalmpl.ast.Strategy> getAlternatives() {
   return alternatives;
  }

  public <T> T accept(IASTVisitor<T> v) {
	return v.visitStrategyAmbiguity(this);
  }
}





  public boolean isOutermost() {
    return false;
  }
  
static public class Outermost extends Strategy {
  // Production: sig("Outermost",[])

  

  
public Outermost(INode node ) {
  super(node);
  
}


  @Override
  public boolean isOutermost() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitStrategyOutermost(this);
  }
  
  	
}


  public boolean isTopDownBreak() {
    return false;
  }
  
static public class TopDownBreak extends Strategy {
  // Production: sig("TopDownBreak",[])

  

  
public TopDownBreak(INode node ) {
  super(node);
  
}


  @Override
  public boolean isTopDownBreak() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitStrategyTopDownBreak(this);
  }
  
  	
}


  public boolean isInnermost() {
    return false;
  }
  
static public class Innermost extends Strategy {
  // Production: sig("Innermost",[])

  

  
public Innermost(INode node ) {
  super(node);
  
}


  @Override
  public boolean isInnermost() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitStrategyInnermost(this);
  }
  
  	
}


  public boolean isBottomUpBreak() {
    return false;
  }
  
static public class BottomUpBreak extends Strategy {
  // Production: sig("BottomUpBreak",[])

  

  
public BottomUpBreak(INode node ) {
  super(node);
  
}


  @Override
  public boolean isBottomUpBreak() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitStrategyBottomUpBreak(this);
  }
  
  	
}


  public boolean isBottomUp() {
    return false;
  }
  
static public class BottomUp extends Strategy {
  // Production: sig("BottomUp",[])

  

  
public BottomUp(INode node ) {
  super(node);
  
}


  @Override
  public boolean isBottomUp() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitStrategyBottomUp(this);
  }
  
  	
}


  public boolean isTopDown() {
    return false;
  }
  
static public class TopDown extends Strategy {
  // Production: sig("TopDown",[])

  

  
public TopDown(INode node ) {
  super(node);
  
}


  @Override
  public boolean isTopDown() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitStrategyTopDown(this);
  }
  
  	
}



}
