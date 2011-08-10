/*******************************************************************************
 * Copyright (c) 2009-2011 CWI
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
 *******************************************************************************/
package org.rascalmpl.ast;


import org.eclipse.imp.pdb.facts.IConstructor;
import org.rascalmpl.interpreter.asserts.Ambiguous;
import org.eclipse.imp.pdb.facts.IValue;
import org.rascalmpl.interpreter.Evaluator;
import org.rascalmpl.interpreter.env.Environment;
import org.rascalmpl.interpreter.result.Result;

public abstract class DataTarget extends AbstractAST {
  public DataTarget(IConstructor node) {
    super(node);
  }

  
  public boolean hasLabel() {
    return false;
  }

  public org.rascalmpl.ast.Name getLabel() {
    throw new UnsupportedOperationException();
  }

  static public class Ambiguity extends DataTarget {
    private final java.util.List<org.rascalmpl.ast.DataTarget> alternatives;
  
    public Ambiguity(IConstructor node, java.util.List<org.rascalmpl.ast.DataTarget> alternatives) {
      super(node);
      this.alternatives = java.util.Collections.unmodifiableList(alternatives);
    }
    
    @Override
    public Result<IValue> interpret(Evaluator __eval) {
      throw new Ambiguous(this.getTree());
    }
      
    @Override
    public org.eclipse.imp.pdb.facts.type.Type typeOf(Environment env) {
      throw new Ambiguous(this.getTree());
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
  
    
  
    public Empty(IConstructor node ) {
      super(node);
      
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
  
    public Labeled(IConstructor node , org.rascalmpl.ast.Name label) {
      super(node);
      
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