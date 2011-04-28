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

import org.eclipse.imp.pdb.facts.IConstructor;

import org.eclipse.imp.pdb.facts.IValue;

import org.rascalmpl.interpreter.BooleanEvaluator;
import org.rascalmpl.interpreter.IEvaluatorContext;

import org.rascalmpl.interpreter.Evaluator;


import org.rascalmpl.interpreter.env.Environment;

import org.rascalmpl.interpreter.matching.IBooleanResult;

import org.rascalmpl.interpreter.matching.IMatchingResult;

import org.rascalmpl.interpreter.result.Result;


public abstract class StructuredType extends AbstractAST {
  public StructuredType(IConstructor node) {
    super(node);
  }
  

  public boolean hasArguments() {
    return false;
  }

  public java.util.List<org.rascalmpl.ast.TypeArg> getArguments() {
    throw new UnsupportedOperationException();
  }

  public boolean hasBasicType() {
    return false;
  }

  public org.rascalmpl.ast.BasicType getBasicType() {
    throw new UnsupportedOperationException();
  }


static public class Ambiguity extends StructuredType {
  private final java.util.List<org.rascalmpl.ast.StructuredType> alternatives;

  public Ambiguity(IConstructor node, java.util.List<org.rascalmpl.ast.StructuredType> alternatives) {
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
  
  @Override
  public IBooleanResult buildBooleanBacktracker(BooleanEvaluator __eval) {
    throw new Ambiguous((IConstructor) this.getTree());
  }

  @Override
  public IMatchingResult buildMatcher(IEvaluatorContext __eval) {
    throw new Ambiguous((IConstructor) this.getTree());
  }
  
  public java.util.List<org.rascalmpl.ast.StructuredType> getAlternatives() {
   return alternatives;
  }

  public <T> T accept(IASTVisitor<T> v) {
	return v.visitStructuredTypeAmbiguity(this);
  }
}





  public boolean isDefault() {
    return false;
  }
  
static public class Default extends StructuredType {
  // Production: sig("Default",[arg("org.rascalmpl.ast.BasicType","basicType"),arg("java.util.List\<org.rascalmpl.ast.TypeArg\>","arguments")])

  
     private final org.rascalmpl.ast.BasicType basicType;
  
     private final java.util.List<org.rascalmpl.ast.TypeArg> arguments;
  

  
public Default(IConstructor node , org.rascalmpl.ast.BasicType basicType,  java.util.List<org.rascalmpl.ast.TypeArg> arguments) {
  super(node);
  
    this.basicType = basicType;
  
    this.arguments = arguments;
  
}


  @Override
  public boolean isDefault() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitStructuredTypeDefault(this);
  }
  
  
     @Override
     public org.rascalmpl.ast.BasicType getBasicType() {
        return this.basicType;
     }
     
     @Override
     public boolean hasBasicType() {
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
