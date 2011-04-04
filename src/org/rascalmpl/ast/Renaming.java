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

import org.rascalmpl.interpreter.asserts.Ambiguous;

import org.rascalmpl.interpreter.env.Environment;

import org.rascalmpl.interpreter.matching.IBooleanResult;

import org.rascalmpl.interpreter.matching.IMatchingResult;

import org.rascalmpl.interpreter.result.Result;


public abstract class Renaming extends AbstractAST {
  public Renaming(INode node) {
    super(node);
  }
  

  public boolean hasFrom() {
    return false;
  }

  public org.rascalmpl.ast.Name getFrom() {
    throw new UnsupportedOperationException();
  }

  public boolean hasTo() {
    return false;
  }

  public org.rascalmpl.ast.Name getTo() {
    throw new UnsupportedOperationException();
  }


static public class Ambiguity extends Renaming {
  private final java.util.List<org.rascalmpl.ast.Renaming> alternatives;

  public Ambiguity(INode node, java.util.List<org.rascalmpl.ast.Renaming> alternatives) {
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
  
  public java.util.List<org.rascalmpl.ast.Renaming> getAlternatives() {
   return alternatives;
  }

  public <T> T accept(IASTVisitor<T> v) {
	return v.visitRenamingAmbiguity(this);
  }
}





  public boolean isDefault() {
    return false;
  }
  
static public class Default extends Renaming {
  // Production: sig("Default",[arg("org.rascalmpl.ast.Name","from"),arg("org.rascalmpl.ast.Name","to")])

  
     private final org.rascalmpl.ast.Name from;
  
     private final org.rascalmpl.ast.Name to;
  

  
public Default(INode node , org.rascalmpl.ast.Name from,  org.rascalmpl.ast.Name to) {
  super(node);
  
    this.from = from;
  
    this.to = to;
  
}


  @Override
  public boolean isDefault() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitRenamingDefault(this);
  }
  
  
     @Override
     public org.rascalmpl.ast.Name getFrom() {
        return this.from;
     }
     
     @Override
     public boolean hasFrom() {
        return true;
     }
  
     @Override
     public org.rascalmpl.ast.Name getTo() {
        return this.to;
     }
     
     @Override
     public boolean hasTo() {
        return true;
     }
  	
}



}
