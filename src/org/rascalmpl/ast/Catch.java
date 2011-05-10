
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


public abstract class Catch extends AbstractAST {
  public Catch(IConstructor node) {
    super(node);
  }
  

  public boolean hasBody() {
    return false;
  }

  public org.rascalmpl.ast.Statement getBody() {
    throw new UnsupportedOperationException();
  }

  public boolean hasPattern() {
    return false;
  }

  public org.rascalmpl.ast.Expression getPattern() {
    throw new UnsupportedOperationException();
  }


static public class Ambiguity extends Catch {
  private final java.util.List<org.rascalmpl.ast.Catch> alternatives;

  public Ambiguity(IConstructor node, java.util.List<org.rascalmpl.ast.Catch> alternatives) {
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
  
  public java.util.List<org.rascalmpl.ast.Catch> getAlternatives() {
   return alternatives;
  }

  public <T> T accept(IASTVisitor<T> v) {
	return v.visitCatchAmbiguity(this);
  }
}





  public boolean isDefault() {
    return false;
  }
  
static public class Default extends Catch {
  // Production: sig("Default",[arg("org.rascalmpl.ast.Statement","body")])

  
     private final org.rascalmpl.ast.Statement body;
  

  
public Default(IConstructor node , org.rascalmpl.ast.Statement body) {
  super(node);
  
    this.body = body;
  
}


  @Override
  public boolean isDefault() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitCatchDefault(this);
  }
  
  
     @Override
     public org.rascalmpl.ast.Statement getBody() {
        return this.body;
     }
     
     @Override
     public boolean hasBody() {
        return true;
     }
  	
}


  public boolean isBinding() {
    return false;
  }
  
static public class Binding extends Catch {
  // Production: sig("Binding",[arg("org.rascalmpl.ast.Expression","pattern"),arg("org.rascalmpl.ast.Statement","body")])

  
     private final org.rascalmpl.ast.Expression pattern;
  
     private final org.rascalmpl.ast.Statement body;
  

  
public Binding(IConstructor node , org.rascalmpl.ast.Expression pattern,  org.rascalmpl.ast.Statement body) {
  super(node);
  
    this.pattern = pattern;
  
    this.body = body;
  
}


  @Override
  public boolean isBinding() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitCatchBinding(this);
  }
  
  
     @Override
     public org.rascalmpl.ast.Expression getPattern() {
        return this.pattern;
     }
     
     @Override
     public boolean hasPattern() {
        return true;
     }
  
     @Override
     public org.rascalmpl.ast.Statement getBody() {
        return this.body;
     }
     
     @Override
     public boolean hasBody() {
        return true;
     }
  	
}



}
