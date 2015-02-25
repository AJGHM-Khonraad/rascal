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

public abstract class FunctionType extends AbstractAST {
  public FunctionType(IConstructor node) {
    super();
  }

  
  public boolean hasArguments() {
    return false;
  }

  public java.util.List<org.rascalmpl.ast.TypeArg> getArguments() {
    throw new UnsupportedOperationException();
  }
  public boolean hasType() {
    return false;
  }

  public org.rascalmpl.ast.Type getType() {
    throw new UnsupportedOperationException();
  }

  

  
  public boolean isTypeArguments() {
    return false;
  }

  static public class TypeArguments extends FunctionType {
    // Production: sig("TypeArguments",[arg("org.rascalmpl.ast.Type","type"),arg("java.util.List\<org.rascalmpl.ast.TypeArg\>","arguments")])
  
    
    private final org.rascalmpl.ast.Type type;
    private final java.util.List<org.rascalmpl.ast.TypeArg> arguments;
  
    public TypeArguments(IConstructor node , org.rascalmpl.ast.Type type,  java.util.List<org.rascalmpl.ast.TypeArg> arguments) {
      super(node);
      
      this.type = type;
      this.arguments = arguments;
    }
  
    @Override
    public boolean isTypeArguments() { 
      return true; 
    }
  
    @Override
    public <T> T accept(IASTVisitor<T> visitor) {
      return visitor.visitFunctionTypeTypeArguments(this);
    }
  
    @Override
    public boolean equals(Object o) {
      if (!(o instanceof TypeArguments)) {
        return false;
      }        
      TypeArguments tmp = (TypeArguments) o;
      return true && tmp.type.equals(this.type) && tmp.arguments.equals(this.arguments) ; 
    }
   
    @Override
    public int hashCode() {
      return 211 + 599 * type.hashCode() + 463 * arguments.hashCode() ; 
    } 
  
    
    @Override
    public org.rascalmpl.ast.Type getType() {
      return this.type;
    }
  
    @Override
    public boolean hasType() {
      return true;
    }
    @Override
    public java.util.List<org.rascalmpl.ast.TypeArg> getArguments() {
      return this.arguments;
    }
  
    @Override
    public boolean hasArguments() {
      return true;
    }	
  }
}