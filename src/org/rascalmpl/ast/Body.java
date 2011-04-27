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

import org.rascalmpl.interpreter.Evaluator;

import org.rascalmpl.interpreter.PatternEvaluator;

import org.rascalmpl.interpreter.env.Environment;

import org.rascalmpl.interpreter.matching.IBooleanResult;

import org.rascalmpl.interpreter.matching.IMatchingResult;

import org.rascalmpl.interpreter.result.Result;


public abstract class Body extends AbstractAST {
  public Body(IConstructor node) {
    super(node);
  }
  

  public boolean hasToplevels() {
    return false;
  }

  public java.util.List<org.rascalmpl.ast.Toplevel> getToplevels() {
    throw new UnsupportedOperationException();
  }


static public class Ambiguity extends Body {
  private final java.util.List<org.rascalmpl.ast.Body> alternatives;

  public Ambiguity(IConstructor node, java.util.List<org.rascalmpl.ast.Body> alternatives) {
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
  
  public java.util.List<org.rascalmpl.ast.Body> getAlternatives() {
   return alternatives;
  }

  public <T> T accept(IASTVisitor<T> v) {
	return v.visitBodyAmbiguity(this);
  }
}





  public boolean isToplevels() {
    return false;
  }
  
static public class Toplevels extends Body {
  // Production: sig("Toplevels",[arg("java.util.List\<org.rascalmpl.ast.Toplevel\>","toplevels")])

  
     private final java.util.List<org.rascalmpl.ast.Toplevel> toplevels;
  

  
public Toplevels(IConstructor node , java.util.List<org.rascalmpl.ast.Toplevel> toplevels) {
  super(node);
  
    this.toplevels = toplevels;
  
}


  @Override
  public boolean isToplevels() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitBodyToplevels(this);
  }
  
  
     @Override
     public java.util.List<org.rascalmpl.ast.Toplevel> getToplevels() {
        return this.toplevels;
     }
     
     @Override
     public boolean hasToplevels() {
        return true;
     }
  	
}



}
