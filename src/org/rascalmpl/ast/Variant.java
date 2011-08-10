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

public abstract class Variant extends AbstractAST {
  public Variant(IConstructor node) {
    super(node);
  }

  
  public boolean hasArguments() {
    return false;
  }

  public java.util.List<org.rascalmpl.ast.TypeArg> getArguments() {
    throw new UnsupportedOperationException();
  }
  public boolean hasName() {
    return false;
  }

  public org.rascalmpl.ast.Name getName() {
    throw new UnsupportedOperationException();
  }

  static public class Ambiguity extends Variant {
    private final java.util.List<org.rascalmpl.ast.Variant> alternatives;
  
    public Ambiguity(IConstructor node, java.util.List<org.rascalmpl.ast.Variant> alternatives) {
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
    
    public java.util.List<org.rascalmpl.ast.Variant> getAlternatives() {
      return alternatives;
    }
    
    public <T> T accept(IASTVisitor<T> v) {
    	return v.visitVariantAmbiguity(this);
    }
  }

  

  
  public boolean isNAryConstructor() {
    return false;
  }

  static public class NAryConstructor extends Variant {
    // Production: sig("NAryConstructor",[arg("org.rascalmpl.ast.Name","name"),arg("java.util.List\<org.rascalmpl.ast.TypeArg\>","arguments")])
  
    
    private final org.rascalmpl.ast.Name name;
    private final java.util.List<org.rascalmpl.ast.TypeArg> arguments;
  
    public NAryConstructor(IConstructor node , org.rascalmpl.ast.Name name,  java.util.List<org.rascalmpl.ast.TypeArg> arguments) {
      super(node);
      
      this.name = name;
      this.arguments = arguments;
    }
  
    @Override
    public boolean isNAryConstructor() { 
      return true; 
    }
  
    @Override
    public <T> T accept(IASTVisitor<T> visitor) {
      return visitor.visitVariantNAryConstructor(this);
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
    public java.util.List<org.rascalmpl.ast.TypeArg> getArguments() {
      return this.arguments;
    }
  
    @Override
    public boolean hasArguments() {
      return true;
    }	
  }
}