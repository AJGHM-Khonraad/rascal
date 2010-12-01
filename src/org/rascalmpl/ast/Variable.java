
package org.rascalmpl.ast;


import org.eclipse.imp.pdb.facts.INode;


public abstract class Variable extends AbstractAST {
  public Variable(INode node) {
    super(node);
  }
  

  public boolean hasName() {
    return false;
  }

  public org.rascalmpl.ast.Name getName() {
    throw new UnsupportedOperationException();
  }

  public boolean hasInitial() {
    return false;
  }

  public org.rascalmpl.ast.Expression getInitial() {
    throw new UnsupportedOperationException();
  }


static public class Ambiguity extends Variable {
  private final java.util.List<org.rascalmpl.ast.Variable> alternatives;

  public Ambiguity(INode node, java.util.List<org.rascalmpl.ast.Variable> alternatives) {
    super(node);
    this.alternatives = java.util.Collections.unmodifiableList(alternatives);
  }

  public java.util.List<org.rascalmpl.ast.Variable> getAlternatives() {
   return alternatives;
  }

  public <T> T accept(IASTVisitor<T> v) {
	return v.visitVariableAmbiguity(this);
  }
}





  public boolean isInitialized() {
    return false;
  }
  
static public class Initialized extends Variable {
  // Production: sig("Initialized",[arg("org.rascalmpl.ast.Name","name"),arg("org.rascalmpl.ast.Expression","initial")])

  
     private final org.rascalmpl.ast.Name name;
  
     private final org.rascalmpl.ast.Expression initial;
  

  
public Initialized(INode node , org.rascalmpl.ast.Name name,  org.rascalmpl.ast.Expression initial) {
  super(node);
  
    this.name = name;
  
    this.initial = initial;
  
}


  @Override
  public boolean isInitialized() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitVariableInitialized(this);
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
     public org.rascalmpl.ast.Expression getInitial() {
        return this.initial;
     }
     
     @Override
     public boolean hasInitial() {
        return true;
     }
  	
}


  public boolean isUnInitialized() {
    return false;
  }
  
static public class UnInitialized extends Variable {
  // Production: sig("UnInitialized",[arg("org.rascalmpl.ast.Name","name")])

  
     private final org.rascalmpl.ast.Name name;
  

  
public UnInitialized(INode node , org.rascalmpl.ast.Name name) {
  super(node);
  
    this.name = name;
  
}


  @Override
  public boolean isUnInitialized() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitVariableUnInitialized(this);
  }
  
  
     @Override
     public org.rascalmpl.ast.Name getName() {
        return this.name;
     }
     
     @Override
     public boolean hasName() {
        return true;
     }
  	
}



}
