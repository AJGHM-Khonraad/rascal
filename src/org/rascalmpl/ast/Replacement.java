
package org.rascalmpl.ast;


import org.eclipse.imp.pdb.facts.INode;


public abstract class Replacement extends AbstractAST {
  public Replacement(INode node) {
    super(node);
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

  public Ambiguity(INode node, java.util.List<org.rascalmpl.ast.Replacement> alternatives) {
    super(node);
    this.alternatives = java.util.Collections.unmodifiableList(alternatives);
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
  

  
public Unconditional(INode node , org.rascalmpl.ast.Expression replacementExpression) {
  super(node);
  
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
  

  
public Conditional(INode node , org.rascalmpl.ast.Expression replacementExpression,  java.util.List<org.rascalmpl.ast.Expression> conditions) {
  super(node);
  
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
