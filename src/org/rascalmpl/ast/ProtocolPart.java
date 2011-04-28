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

import org.rascalmpl.interpreter.IEvaluatorContext;

import org.rascalmpl.interpreter.Evaluator;


import org.rascalmpl.interpreter.env.Environment;

import org.rascalmpl.interpreter.matching.IBooleanResult;

import org.rascalmpl.interpreter.matching.IMatchingResult;

import org.rascalmpl.interpreter.result.Result;


public abstract class ProtocolPart extends AbstractAST {
  public ProtocolPart(IConstructor node) {
    super(node);
  }
  

  public boolean hasTail() {
    return false;
  }

  public org.rascalmpl.ast.ProtocolTail getTail() {
    throw new UnsupportedOperationException();
  }

  public boolean hasExpression() {
    return false;
  }

  public org.rascalmpl.ast.Expression getExpression() {
    throw new UnsupportedOperationException();
  }

  public boolean hasProtocolChars() {
    return false;
  }

  public org.rascalmpl.ast.ProtocolChars getProtocolChars() {
    throw new UnsupportedOperationException();
  }

  public boolean hasPre() {
    return false;
  }

  public org.rascalmpl.ast.PreProtocolChars getPre() {
    throw new UnsupportedOperationException();
  }


static public class Ambiguity extends ProtocolPart {
  private final java.util.List<org.rascalmpl.ast.ProtocolPart> alternatives;

  public Ambiguity(IConstructor node, java.util.List<org.rascalmpl.ast.ProtocolPart> alternatives) {
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
  public IBooleanResult buildBacktracker(IEvaluatorContext __eval) {
    throw new Ambiguous((IConstructor) this.getTree());
  }

  @Override
  public IMatchingResult buildMatcher(IEvaluatorContext __eval) {
    throw new Ambiguous((IConstructor) this.getTree());
  }
  
  public java.util.List<org.rascalmpl.ast.ProtocolPart> getAlternatives() {
   return alternatives;
  }

  public <T> T accept(IASTVisitor<T> v) {
	return v.visitProtocolPartAmbiguity(this);
  }
}





  public boolean isNonInterpolated() {
    return false;
  }
  
static public class NonInterpolated extends ProtocolPart {
  // Production: sig("NonInterpolated",[arg("org.rascalmpl.ast.ProtocolChars","protocolChars")])

  
     private final org.rascalmpl.ast.ProtocolChars protocolChars;
  

  
public NonInterpolated(IConstructor node , org.rascalmpl.ast.ProtocolChars protocolChars) {
  super(node);
  
    this.protocolChars = protocolChars;
  
}


  @Override
  public boolean isNonInterpolated() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitProtocolPartNonInterpolated(this);
  }
  
  
     @Override
     public org.rascalmpl.ast.ProtocolChars getProtocolChars() {
        return this.protocolChars;
     }
     
     @Override
     public boolean hasProtocolChars() {
        return true;
     }
  	
}


  public boolean isInterpolated() {
    return false;
  }
  
static public class Interpolated extends ProtocolPart {
  // Production: sig("Interpolated",[arg("org.rascalmpl.ast.PreProtocolChars","pre"),arg("org.rascalmpl.ast.Expression","expression"),arg("org.rascalmpl.ast.ProtocolTail","tail")])

  
     private final org.rascalmpl.ast.PreProtocolChars pre;
  
     private final org.rascalmpl.ast.Expression expression;
  
     private final org.rascalmpl.ast.ProtocolTail tail;
  

  
public Interpolated(IConstructor node , org.rascalmpl.ast.PreProtocolChars pre,  org.rascalmpl.ast.Expression expression,  org.rascalmpl.ast.ProtocolTail tail) {
  super(node);
  
    this.pre = pre;
  
    this.expression = expression;
  
    this.tail = tail;
  
}


  @Override
  public boolean isInterpolated() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitProtocolPartInterpolated(this);
  }
  
  
     @Override
     public org.rascalmpl.ast.PreProtocolChars getPre() {
        return this.pre;
     }
     
     @Override
     public boolean hasPre() {
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
  
     @Override
     public org.rascalmpl.ast.ProtocolTail getTail() {
        return this.tail;
     }
     
     @Override
     public boolean hasTail() {
        return true;
     }
  	
}



}
