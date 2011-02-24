
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


public abstract class Replacement extends AbstractAST {
  public Replacement(ISourceLocation loc) {
    super(loc);
  }
  

  public boolean hasReplacementExpression() {
    return false;
  }

  public org.rascalmpl.ast.Expression getReplacementExpression() {
    throw new UnsupportedOperationException();
  }

  public boolean hasConditions() {
    return false;
  }

  public java.util.List<org.rascalmpl.ast.Expression> getConditions() {
    throw new UnsupportedOperationException();
  }


static public class Ambiguity extends Replacement {
  private final java.util.List<org.rascalmpl.ast.Replacement> alternatives;

  public Ambiguity(ISourceLocation loc, java.util.List<org.rascalmpl.ast.Replacement> alternatives) {
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
  
  public java.util.List<org.rascalmpl.ast.Replacement> getAlternatives() {
   return alternatives;
  }

  public <T> T accept(IASTVisitor<T> v) {
	return v.visitReplacementAmbiguity(this);
  }
}





  public boolean isUnconditional() {
    return false;
  }
  
static public class Unconditional extends Replacement {
  // Production: sig("Unconditional",[arg("org.rascalmpl.ast.Expression","replacementExpression")])

  
     private final org.rascalmpl.ast.Expression replacementExpression;
  

  
public Unconditional(ISourceLocation loc, org.rascalmpl.ast.Expression replacementExpression) {
  super(loc);
  
    this.replacementExpression = replacementExpression;
  
}


  @Override
  public boolean isUnconditional() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitReplacementUnconditional(this);
  }
  
  
     @Override
     public org.rascalmpl.ast.Expression getReplacementExpression() {
        return this.replacementExpression;
     }
     
     @Override
     public boolean hasReplacementExpression() {
        return true;
     }
  	
}


  public boolean isConditional() {
    return false;
  }
  
static public class Conditional extends Replacement {
  // Production: sig("Conditional",[arg("org.rascalmpl.ast.Expression","replacementExpression"),arg("java.util.List\<org.rascalmpl.ast.Expression\>","conditions")])

  
     private final org.rascalmpl.ast.Expression replacementExpression;
  
     private final java.util.List<org.rascalmpl.ast.Expression> conditions;
  

  
public Conditional(ISourceLocation loc, org.rascalmpl.ast.Expression replacementExpression,  java.util.List<org.rascalmpl.ast.Expression> conditions) {
  super(loc);
  
    this.replacementExpression = replacementExpression;
  
    this.conditions = conditions;
  
}


  @Override
  public boolean isConditional() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitReplacementConditional(this);
  }
  
  
     @Override
     public org.rascalmpl.ast.Expression getReplacementExpression() {
        return this.replacementExpression;
     }
     
     @Override
     public boolean hasReplacementExpression() {
        return true;
     }
  
     @Override
     public java.util.List<org.rascalmpl.ast.Expression> getConditions() {
        return this.conditions;
     }
     
     @Override
     public boolean hasConditions() {
        return true;
     }
  	
}



}
