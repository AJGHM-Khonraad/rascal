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


import org.eclipse.imp.pdb.facts.INode;

import org.rascalmpl.interpreter.asserts.Ambiguous;

import org.eclipse.imp.pdb.facts.IConstructor;

import org.eclipse.imp.pdb.facts.IValue;

import org.rascalmpl.interpreter.BooleanEvaluator;

import org.rascalmpl.interpreter.Evaluator;

import org.rascalmpl.interpreter.PatternEvaluator;

import org.rascalmpl.interpreter.env.Environment;

import org.rascalmpl.interpreter.matching.IBooleanResult;

import org.rascalmpl.interpreter.matching.IMatchingResult;

import org.rascalmpl.interpreter.result.Result;


public abstract class FunctionModifiers extends AbstractAST {
  public FunctionModifiers(INode node) {
    super(node);
  }
  

  public boolean hasModifiers() {
    return false;
  }

  public java.util.List<org.rascalmpl.ast.FunctionModifier> getModifiers() {
    throw new UnsupportedOperationException();
  }


static public class Ambiguity extends FunctionModifiers {
  private final java.util.List<org.rascalmpl.ast.FunctionModifiers> alternatives;

  public Ambiguity(INode node, java.util.List<org.rascalmpl.ast.FunctionModifiers> alternatives) {
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
  public IMatchingResult buildMatcher(PatternEvaluator __eval) {
    throw new Ambiguous((IConstructor) this.getTree());
  }
  
  public java.util.List<org.rascalmpl.ast.FunctionModifiers> getAlternatives() {
   return alternatives;
  }

  public <T> T accept(IASTVisitor<T> v) {
	return v.visitFunctionModifiersAmbiguity(this);
  }
}





  public boolean isList() {
    return false;
  }
  
static public class List extends FunctionModifiers {
  // Production: sig("List",[arg("java.util.List\<org.rascalmpl.ast.FunctionModifier\>","modifiers")])

  
     private final java.util.List<org.rascalmpl.ast.FunctionModifier> modifiers;
  

  
public List(INode node , java.util.List<org.rascalmpl.ast.FunctionModifier> modifiers) {
  super(node);
  
    this.modifiers = modifiers;
  
}


  @Override
  public boolean isList() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitFunctionModifiersList(this);
  }
  
  
     @Override
     public java.util.List<org.rascalmpl.ast.FunctionModifier> getModifiers() {
        return this.modifiers;
     }
     
     @Override
     public boolean hasModifiers() {
        return true;
     }
  	
}



}
