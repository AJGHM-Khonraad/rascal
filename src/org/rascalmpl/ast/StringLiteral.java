
package org.rascalmpl.ast;


import org.eclipse.imp.pdb.facts.INode;


public abstract class StringLiteral extends AbstractAST {
  public StringLiteral(INode node) {
    super(node);
  }
  

  public boolean hasExpression() {
    return false;
  }

  public org.rascalmpl.ast.Expression getExpression() {
    throw new UnsupportedOperationException();
  }

  public boolean hasPre() {
    return false;
  }

  public org.rascalmpl.ast.PreStringChars getPre() {
    throw new UnsupportedOperationException();
  }

  public boolean hasTail() {
    return false;
  }

  public org.rascalmpl.ast.StringTail getTail() {
    throw new UnsupportedOperationException();
  }

  public boolean hasConstant() {
    return false;
  }

  public org.rascalmpl.ast.StringConstant getConstant() {
    throw new UnsupportedOperationException();
  }

  public boolean hasTemplate() {
    return false;
  }

  public org.rascalmpl.ast.StringTemplate getTemplate() {
    throw new UnsupportedOperationException();
  }


static public class Ambiguity extends StringLiteral {
  private final java.util.List<org.rascalmpl.ast.StringLiteral> alternatives;

  public Ambiguity(INode node, java.util.List<org.rascalmpl.ast.StringLiteral> alternatives) {
    super(node);
    this.alternatives = java.util.Collections.unmodifiableList(alternatives);
  }

  public java.util.List<org.rascalmpl.ast.StringLiteral> getAlternatives() {
   return alternatives;
  }

  public <T> T accept(IASTVisitor<T> v) {
	return v.visitStringLiteralAmbiguity(this);
  }
}





  public boolean isNonInterpolated() {
    return false;
  }
  
static public class NonInterpolated extends StringLiteral {
  // Production: sig("NonInterpolated",[arg("org.rascalmpl.ast.StringConstant","constant")])

  
     private final org.rascalmpl.ast.StringConstant constant;
  

  
public NonInterpolated(INode node , org.rascalmpl.ast.StringConstant constant) {
  super(node);
  
    this.constant = constant;
  
}


  @Override
  public boolean isNonInterpolated() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitStringLiteralNonInterpolated(this);
  }
  
  
     @Override
     public org.rascalmpl.ast.StringConstant getConstant() {
        return this.constant;
     }
     
     @Override
     public boolean hasConstant() {
        return true;
     }
  	
}


  public boolean isTemplate() {
    return false;
  }
  
static public class Template extends StringLiteral {
  // Production: sig("Template",[arg("org.rascalmpl.ast.PreStringChars","pre"),arg("org.rascalmpl.ast.StringTemplate","template"),arg("org.rascalmpl.ast.StringTail","tail")])

  
     private final org.rascalmpl.ast.PreStringChars pre;
  
     private final org.rascalmpl.ast.StringTemplate template;
  
     private final org.rascalmpl.ast.StringTail tail;
  

  
public Template(INode node , org.rascalmpl.ast.PreStringChars pre,  org.rascalmpl.ast.StringTemplate template,  org.rascalmpl.ast.StringTail tail) {
  super(node);
  
    this.pre = pre;
  
    this.template = template;
  
    this.tail = tail;
  
}


  @Override
  public boolean isTemplate() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitStringLiteralTemplate(this);
  }
  
  
     @Override
     public org.rascalmpl.ast.PreStringChars getPre() {
        return this.pre;
     }
     
     @Override
     public boolean hasPre() {
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
     public org.rascalmpl.ast.StringTail getTail() {
        return this.tail;
     }
     
     @Override
     public boolean hasTail() {
        return true;
     }
  	
}


  public boolean isInterpolated() {
    return false;
  }
  
static public class Interpolated extends StringLiteral {
  // Production: sig("Interpolated",[arg("org.rascalmpl.ast.PreStringChars","pre"),arg("org.rascalmpl.ast.Expression","expression"),arg("org.rascalmpl.ast.StringTail","tail")])

  
     private final org.rascalmpl.ast.PreStringChars pre;
  
     private final org.rascalmpl.ast.Expression expression;
  
     private final org.rascalmpl.ast.StringTail tail;
  

  
public Interpolated(INode node , org.rascalmpl.ast.PreStringChars pre,  org.rascalmpl.ast.Expression expression,  org.rascalmpl.ast.StringTail tail) {
  super(node);
  
    this.pre = pre;
  
    this.expression = expression;
  
    this.tail = tail;
  
}


  @Override
  public boolean isInterpolated() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitStringLiteralInterpolated(this);
  }
  
  
     @Override
     public org.rascalmpl.ast.PreStringChars getPre() {
        return this.pre;
     }
     
     @Override
     public boolean hasPre() {
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
     public org.rascalmpl.ast.StringTail getTail() {
        return this.tail;
     }
     
     @Override
     public boolean hasTail() {
        return true;
     }
  	
}



}
