
package org.rascalmpl.ast;


import org.eclipse.imp.pdb.facts.IConstructor;

import org.eclipse.imp.pdb.facts.IConstructor;

import org.eclipse.imp.pdb.facts.IValue;

import org.rascalmpl.interpreter.Evaluator;

import org.rascalmpl.interpreter.asserts.Ambiguous;

import org.rascalmpl.interpreter.env.Environment;

import org.rascalmpl.interpreter.matching.IBooleanResult;

import org.rascalmpl.interpreter.matching.IMatchingResult;

import org.rascalmpl.interpreter.result.Result;


public abstract class Renamings extends AbstractAST {
  public Renamings(IConstructor node) {
    super(node);
  }
  

  public boolean hasRenamings() {
    return false;
  }

  public java.util.List<org.rascalmpl.ast.Renaming> getRenamings() {
    throw new UnsupportedOperationException();
  }


static public class Ambiguity extends Renamings {
  private final java.util.List<org.rascalmpl.ast.Renamings> alternatives;

  public Ambiguity(IConstructor node, java.util.List<org.rascalmpl.ast.Renamings> alternatives) {
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
  
  public java.util.List<org.rascalmpl.ast.Renamings> getAlternatives() {
   return alternatives;
  }

  public <T> T accept(IASTVisitor<T> v) {
	return v.visitRenamingsAmbiguity(this);
  }
}





  public boolean isDefault() {
    return false;
  }
  
static public class Default extends Renamings {
  // Production: sig("Default",[arg("java.util.List\<org.rascalmpl.ast.Renaming\>","renamings")])

  
     private final java.util.List<org.rascalmpl.ast.Renaming> renamings;
  

  
public Default(IConstructor node , java.util.List<org.rascalmpl.ast.Renaming> renamings) {
  super(node);
  
    this.renamings = renamings;
  
}


  @Override
  public boolean isDefault() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitRenamingsDefault(this);
  }
  
  
     @Override
     public java.util.List<org.rascalmpl.ast.Renaming> getRenamings() {
        return this.renamings;
     }
     
     @Override
     public boolean hasRenamings() {
        return true;
     }
  	
}



}
