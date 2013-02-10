/*******************************************************************************
 * Copyright (c) 2009-2013 CWI
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

public abstract class KeywordArguments extends AbstractAST {
  public KeywordArguments(IConstructor node) {
    super();
  }

  
  public boolean hasKeywordArgumentList() {
    return false;
  }

  public java.util.List<org.rascalmpl.ast.KeywordArgument> getKeywordArgumentList() {
    throw new UnsupportedOperationException();
  }
  public boolean hasOptionalComma() {
    return false;
  }

  public org.rascalmpl.ast.OptionalComma getOptionalComma() {
    throw new UnsupportedOperationException();
  }

  

  
  public boolean isDefault() {
    return false;
  }

  static public class Default extends KeywordArguments {
    // Production: sig("Default",[arg("org.rascalmpl.ast.OptionalComma","optionalComma"),arg("java.util.List\<org.rascalmpl.ast.KeywordArgument\>","keywordArgumentList")])
  
    
    private final org.rascalmpl.ast.OptionalComma optionalComma;
    private final java.util.List<org.rascalmpl.ast.KeywordArgument> keywordArgumentList;
  
    public Default(IConstructor node , org.rascalmpl.ast.OptionalComma optionalComma,  java.util.List<org.rascalmpl.ast.KeywordArgument> keywordArgumentList) {
      super(node);
      
      this.optionalComma = optionalComma;
      this.keywordArgumentList = keywordArgumentList;
    }
  
    @Override
    public boolean isDefault() { 
      return true; 
    }
  
    @Override
    public <T> T accept(IASTVisitor<T> visitor) {
      return visitor.visitKeywordArgumentsDefault(this);
    }
  
    
    @Override
    public org.rascalmpl.ast.OptionalComma getOptionalComma() {
      return this.optionalComma;
    }
  
    @Override
    public boolean hasOptionalComma() {
      return true;
    }
    @Override
    public java.util.List<org.rascalmpl.ast.KeywordArgument> getKeywordArgumentList() {
      return this.keywordArgumentList;
    }
  
    @Override
    public boolean hasKeywordArgumentList() {
      return true;
    }	
  }
  public boolean isNone() {
    return false;
  }

  static public class None extends KeywordArguments {
    // Production: sig("None",[])
  
    
  
    public None(IConstructor node ) {
      super(node);
      
    }
  
    @Override
    public boolean isNone() { 
      return true; 
    }
  
    @Override
    public <T> T accept(IASTVisitor<T> visitor) {
      return visitor.visitKeywordArgumentsNone(this);
    }
  
    	
  }
}