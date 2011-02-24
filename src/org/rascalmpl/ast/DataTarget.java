
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


public abstract class DataTarget extends AbstractAST {
  public DataTarget(ISourceLocation loc) {
    super(loc);
  }
  

  public boolean hasLabel() {
    return false;
  }

  public org.rascalmpl.ast.Name getLabel() {
    throw new UnsupportedOperationException();
  }


static public class Ambiguity extends DataTarget {
  private final java.util.List<org.rascalmpl.ast.DataTarget> alternatives;

  public Ambiguity(ISourceLocation loc, java.util.List<org.rascalmpl.ast.DataTarget> alternatives) {
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
  
  public java.util.List<org.rascalmpl.ast.DataTarget> getAlternatives() {
   return alternatives;
  }

  public <T> T accept(IASTVisitor<T> v) {
	return v.visitDataTargetAmbiguity(this);
  }
}





  public boolean isEmpty() {
    return false;
  }
  
static public class Empty extends DataTarget {
  // Production: sig("Empty",[])

  

  
public Empty(ISourceLocation loc) {
  super(loc);
  
}


  @Override
  public boolean isEmpty() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitDataTargetEmpty(this);
  }
  
  	
}


  public boolean isLabeled() {
    return false;
  }
  
static public class Labeled extends DataTarget {
  // Production: sig("Labeled",[arg("org.rascalmpl.ast.Name","label")])

  
     private final org.rascalmpl.ast.Name label;
  

  
public Labeled(ISourceLocation loc, org.rascalmpl.ast.Name label) {
  super(loc);
  
    this.label = label;
  
}


  @Override
  public boolean isLabeled() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitDataTargetLabeled(this);
  }
  
  
     @Override
     public org.rascalmpl.ast.Name getLabel() {
        return this.label;
     }
     
     @Override
     public boolean hasLabel() {
        return true;
     }
  	
}



}
