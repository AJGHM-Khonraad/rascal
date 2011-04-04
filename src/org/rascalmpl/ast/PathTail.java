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


public abstract class PathTail extends AbstractAST {
  public PathTail(INode node) {
    super(node);
  }
  

  public boolean hasExpression() {
    return false;
  }

  public org.rascalmpl.ast.Expression getExpression() {
    throw new UnsupportedOperationException();
  }

  public boolean hasTail() {
    return false;
  }

  public org.rascalmpl.ast.PathTail getTail() {
    throw new UnsupportedOperationException();
  }

  public boolean hasMid() {
    return false;
  }

  public org.rascalmpl.ast.MidPathChars getMid() {
    throw new UnsupportedOperationException();
  }

  public boolean hasPost() {
    return false;
  }

  public org.rascalmpl.ast.PostPathChars getPost() {
    throw new UnsupportedOperationException();
  }


static public class Ambiguity extends PathTail {
  private final java.util.List<org.rascalmpl.ast.PathTail> alternatives;

  public Ambiguity(INode node, java.util.List<org.rascalmpl.ast.PathTail> alternatives) {
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
  
  public java.util.List<org.rascalmpl.ast.PathTail> getAlternatives() {
   return alternatives;
  }

  public <T> T accept(IASTVisitor<T> v) {
	return v.visitPathTailAmbiguity(this);
  }
}





  public boolean isMid() {
    return false;
  }
  
static public class Mid extends PathTail {
  // Production: sig("Mid",[arg("org.rascalmpl.ast.MidPathChars","mid"),arg("org.rascalmpl.ast.Expression","expression"),arg("org.rascalmpl.ast.PathTail","tail")])

  
     private final org.rascalmpl.ast.MidPathChars mid;
  
     private final org.rascalmpl.ast.Expression expression;
  
     private final org.rascalmpl.ast.PathTail tail;
  

  
public Mid(INode node , org.rascalmpl.ast.MidPathChars mid,  org.rascalmpl.ast.Expression expression,  org.rascalmpl.ast.PathTail tail) {
  super(node);
  
    this.mid = mid;
  
    this.expression = expression;
  
    this.tail = tail;
  
}


  @Override
  public boolean isMid() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitPathTailMid(this);
  }
  
  
     @Override
     public org.rascalmpl.ast.MidPathChars getMid() {
        return this.mid;
     }
     
     @Override
     public boolean hasMid() {
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
     public org.rascalmpl.ast.PathTail getTail() {
        return this.tail;
     }
     
     @Override
     public boolean hasTail() {
        return true;
     }
  	
}


  public boolean isPost() {
    return false;
  }
  
static public class Post extends PathTail {
  // Production: sig("Post",[arg("org.rascalmpl.ast.PostPathChars","post")])

  
     private final org.rascalmpl.ast.PostPathChars post;
  

  
public Post(INode node , org.rascalmpl.ast.PostPathChars post) {
  super(node);
  
    this.post = post;
  
}


  @Override
  public boolean isPost() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitPathTailPost(this);
  }
  
  
     @Override
     public org.rascalmpl.ast.PostPathChars getPost() {
        return this.post;
     }
     
     @Override
     public boolean hasPost() {
        return true;
     }
  	
}



}
