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

public abstract class DataTypeSelector extends AbstractAST {
  public DataTypeSelector(IConstructor node) {
    super();
  }

  
  public boolean hasProduction() {
    return false;
  }

  public org.rascalmpl.ast.Name getProduction() {
    throw new UnsupportedOperationException();
  }
  public boolean hasSort() {
    return false;
  }

  public org.rascalmpl.ast.QualifiedName getSort() {
    throw new UnsupportedOperationException();
  }

  

  
  public boolean isSelector() {
    return false;
  }

  static public class Selector extends DataTypeSelector {
    // Production: sig("Selector",[arg("org.rascalmpl.ast.QualifiedName","sort"),arg("org.rascalmpl.ast.Name","production")])
  
    
    private final org.rascalmpl.ast.QualifiedName sort;
    private final org.rascalmpl.ast.Name production;
  
    public Selector(IConstructor node , org.rascalmpl.ast.QualifiedName sort,  org.rascalmpl.ast.Name production) {
      super(node);
      
      this.sort = sort;
      this.production = production;
    }
  
    @Override
    public boolean isSelector() { 
      return true; 
    }
  
    @Override
    public <T> T accept(IASTVisitor<T> visitor) {
      return visitor.visitDataTypeSelectorSelector(this);
    }
  
    
    @Override
    public org.rascalmpl.ast.QualifiedName getSort() {
      return this.sort;
    }
  
    @Override
    public boolean hasSort() {
      return true;
    }
    @Override
    public org.rascalmpl.ast.Name getProduction() {
      return this.production;
    }
  
    @Override
    public boolean hasProduction() {
      return true;
    }	
  }
}