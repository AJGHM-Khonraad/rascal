/*******************************************************************************
 * Copyright (c) 2009-2015 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   * Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI
 *   * Tijs van der Storm - Tijs.van.der.Storm@cwi.nl
 *   * Paul Klint - Paul.Klint@cwi.nl - CWI
 *   * Mark Hills - Mark.Hills@cwi.nl (CWI)
 *   * Arnold Lankamp - Arnold.Lankamp@cwi.nl
 *   * Michael Steindorfer - Michael.Steindorfer@cwi.nl - CWI
 *******************************************************************************/
package org.rascalmpl.ast;


import org.eclipse.imp.pdb.facts.IConstructor;

public abstract class Tag extends AbstractAST {
  public Tag(IConstructor node) {
    super();
  }

  
  public boolean hasExpression() {
    return false;
  }

  public org.rascalmpl.ast.Expression getExpression() {
    throw new UnsupportedOperationException();
  }
  public boolean hasName() {
    return false;
  }

  public org.rascalmpl.ast.Name getName() {
    throw new UnsupportedOperationException();
  }
  public boolean hasContents() {
    return false;
  }

  public org.rascalmpl.ast.TagString getContents() {
    throw new UnsupportedOperationException();
  }

  

  
  public boolean isDefault() {
    return false;
  }

  static public class Default extends Tag {
    // Production: sig("Default",[arg("org.rascalmpl.ast.Name","name"),arg("org.rascalmpl.ast.TagString","contents")])
  
    
    private final org.rascalmpl.ast.Name name;
    private final org.rascalmpl.ast.TagString contents;
  
    public Default(IConstructor node , org.rascalmpl.ast.Name name,  org.rascalmpl.ast.TagString contents) {
      super(node);
      
      this.name = name;
      this.contents = contents;
    }
  
    @Override
    public boolean isDefault() { 
      return true; 
    }
  
    @Override
    public <T> T accept(IASTVisitor<T> visitor) {
      return visitor.visitTagDefault(this);
    }
  
    @Override
    public boolean equals(Object o) {
      if (!(o instanceof Default)) {
        return false;
      }        
      Default tmp = (Default) o;
      return true && tmp.name.equals(this.name) && tmp.contents.equals(this.contents) ; 
    }
   
    @Override
    public int hashCode() {
      return 109 + 929 * name.hashCode() + 607 * contents.hashCode() ; 
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
    public org.rascalmpl.ast.TagString getContents() {
      return this.contents;
    }
  
    @Override
    public boolean hasContents() {
      return true;
    }	
  }
  public boolean isEmpty() {
    return false;
  }

  static public class Empty extends Tag {
    // Production: sig("Empty",[arg("org.rascalmpl.ast.Name","name")])
  
    
    private final org.rascalmpl.ast.Name name;
  
    public Empty(IConstructor node , org.rascalmpl.ast.Name name) {
      super(node);
      
      this.name = name;
    }
  
    @Override
    public boolean isEmpty() { 
      return true; 
    }
  
    @Override
    public <T> T accept(IASTVisitor<T> visitor) {
      return visitor.visitTagEmpty(this);
    }
  
    @Override
    public boolean equals(Object o) {
      if (!(o instanceof Empty)) {
        return false;
      }        
      Empty tmp = (Empty) o;
      return true && tmp.name.equals(this.name) ; 
    }
   
    @Override
    public int hashCode() {
      return 397 + 409 * name.hashCode() ; 
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
  public boolean isExpression() {
    return false;
  }

  static public class Expression extends Tag {
    // Production: sig("Expression",[arg("org.rascalmpl.ast.Name","name"),arg("org.rascalmpl.ast.Expression","expression")])
  
    
    private final org.rascalmpl.ast.Name name;
    private final org.rascalmpl.ast.Expression expression;
  
    public Expression(IConstructor node , org.rascalmpl.ast.Name name,  org.rascalmpl.ast.Expression expression) {
      super(node);
      
      this.name = name;
      this.expression = expression;
    }
  
    @Override
    public boolean isExpression() { 
      return true; 
    }
  
    @Override
    public <T> T accept(IASTVisitor<T> visitor) {
      return visitor.visitTagExpression(this);
    }
  
    @Override
    public boolean equals(Object o) {
      if (!(o instanceof Expression)) {
        return false;
      }        
      Expression tmp = (Expression) o;
      return true && tmp.name.equals(this.name) && tmp.expression.equals(this.expression) ; 
    }
   
    @Override
    public int hashCode() {
      return 571 + 773 * name.hashCode() + 67 * expression.hashCode() ; 
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
    public org.rascalmpl.ast.Expression getExpression() {
      return this.expression;
    }
  
    @Override
    public boolean hasExpression() {
      return true;
    }	
  }
}