
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


public abstract class StringMiddle extends AbstractAST {
  public StringMiddle(INode node) {
    super(node);
  }
  

  public boolean hasTail() {
    return false;
  }

  public org.rascalmpl.ast.StringMiddle getTail() {
    throw new UnsupportedOperationException();
  }

  public boolean hasExpression() {
    return false;
  }

  public org.rascalmpl.ast.Expression getExpression() {
    throw new UnsupportedOperationException();
  }

  public boolean hasMid() {
    return false;
  }

  public org.rascalmpl.ast.MidStringChars getMid() {
    throw new UnsupportedOperationException();
  }

  public boolean hasTemplate() {
    return false;
  }

  public org.rascalmpl.ast.StringTemplate getTemplate() {
    throw new UnsupportedOperationException();
  }


static public class Ambiguity extends StringMiddle {
  private final java.util.List<org.rascalmpl.ast.StringMiddle> alternatives;

  public Ambiguity(INode node, java.util.List<org.rascalmpl.ast.StringMiddle> alternatives) {
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
  
  public java.util.List<org.rascalmpl.ast.StringMiddle> getAlternatives() {
   return alternatives;
  }

  public <T> T accept(IASTVisitor<T> v) {
	return v.visitStringMiddleAmbiguity(this);
  }
}





  public boolean isInterpolated() {
    return false;
  }
  
static public class Interpolated extends StringMiddle {
  // Production: sig("Interpolated",[arg("org.rascalmpl.ast.MidStringChars","mid"),arg("org.rascalmpl.ast.Expression","expression"),arg("org.rascalmpl.ast.StringMiddle","tail")])

  
     private final org.rascalmpl.ast.MidStringChars mid;
  
     private final org.rascalmpl.ast.Expression expression;
  
     private final org.rascalmpl.ast.StringMiddle tail;
  

  
public Interpolated(INode node , org.rascalmpl.ast.MidStringChars mid,  org.rascalmpl.ast.Expression expression,  org.rascalmpl.ast.StringMiddle tail) {
  super(node);
  
    this.mid = mid;
  
    this.expression = expression;
  
    this.tail = tail;
  
}


  @Override
  public boolean isInterpolated() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitStringMiddleInterpolated(this);
  }
  
  
     @Override
     public org.rascalmpl.ast.MidStringChars getMid() {
        return this.mid;
     }
     
     @Override
     public boolean hasMid() {
        return true;
     }
  
     @Override
     public org.rascalmpl.ast.Expression getExpression() {
        return this.expression;
     }
     
     @Override
     public boolean hasExpression() {
        return true;
     }
  
     @Override
     public org.rascalmpl.ast.StringMiddle getTail() {
        return this.tail;
     }
     
     @Override
     public boolean hasTail() {
        return true;
     }
  	
}


  public boolean isTemplate() {
    return false;
  }
  
static public class Template extends StringMiddle {
  // Production: sig("Template",[arg("org.rascalmpl.ast.MidStringChars","mid"),arg("org.rascalmpl.ast.StringTemplate","template"),arg("org.rascalmpl.ast.StringMiddle","tail")])

  
     private final org.rascalmpl.ast.MidStringChars mid;
  
     private final org.rascalmpl.ast.StringTemplate template;
  
     private final org.rascalmpl.ast.StringMiddle tail;
  

  
public Template(INode node , org.rascalmpl.ast.MidStringChars mid,  org.rascalmpl.ast.StringTemplate template,  org.rascalmpl.ast.StringMiddle tail) {
  super(node);
  
    this.mid = mid;
  
    this.template = template;
  
    this.tail = tail;
  
}


  @Override
  public boolean isTemplate() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitStringMiddleTemplate(this);
  }
  
  
     @Override
     public org.rascalmpl.ast.MidStringChars getMid() {
        return this.mid;
     }
     
     @Override
     public boolean hasMid() {
        return true;
     }
  
     @Override
     public org.rascalmpl.ast.StringTemplate getTemplate() {
        return this.template;
     }
     
     @Override
     public boolean hasTemplate() {
        return true;
     }
  
     @Override
     public org.rascalmpl.ast.StringMiddle getTail() {
        return this.tail;
     }
     
     @Override
     public boolean hasTail() {
        return true;
     }
  	
}


  public boolean isMid() {
    return false;
  }
  
static public class Mid extends StringMiddle {
  // Production: sig("Mid",[arg("org.rascalmpl.ast.MidStringChars","mid")])

  
     private final org.rascalmpl.ast.MidStringChars mid;
  

  
public Mid(INode node , org.rascalmpl.ast.MidStringChars mid) {
  super(node);
  
    this.mid = mid;
  
}


  @Override
  public boolean isMid() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitStringMiddleMid(this);
  }
  
  
     @Override
     public org.rascalmpl.ast.MidStringChars getMid() {
        return this.mid;
     }
     
     @Override
     public boolean hasMid() {
        return true;
     }
  	
}



}
