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
import org.rascalmpl.interpreter.matching.IBooleanResult;
import org.rascalmpl.interpreter.matching.IMatchingResult;
import org.rascalmpl.interpreter.result.Result;

public abstract class Visibility extends AbstractAST {
  public Visibility(IConstructor node) {
    super(node);
  }

  

  static public class Ambiguity extends Visibility {
    private final java.util.List<org.rascalmpl.ast.Visibility> alternatives;
  
    public Ambiguity(IConstructor node, java.util.List<org.rascalmpl.ast.Visibility> alternatives) {
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
    
    public java.util.List<org.rascalmpl.ast.Visibility> getAlternatives() {
      return alternatives;
    }
    
    public <T> T accept(IASTVisitor<T> v) {
    	return v.visitVisibilityAmbiguity(this);
    }
  }

  

  
  public boolean isPublic() {
    return false;
  }

  static public class Public extends Visibility {
    // Production: sig("Public",[])
  
    
  
    public Public(IConstructor node ) {
      super(node);
      
    }
  
    @Override
    public boolean isPublic() { 
      return true; 
    }
  
    @Override
    public <T> T accept(IASTVisitor<T> visitor) {
      return visitor.visitVisibilityPublic(this);
    }
  
    	
  }
  public boolean isDefault() {
    return false;
  }

  static public class Default extends Visibility {
    // Production: sig("Default",[])
  
    
  
    public Default(IConstructor node ) {
      super(node);
      
    }
  
    @Override
    public boolean isDefault() { 
      return true; 
    }
  
    @Override
    public <T> T accept(IASTVisitor<T> visitor) {
      return visitor.visitVisibilityDefault(this);
    }
  
    	
  }
  public boolean isPrivate() {
    return false;
  }

  static public class Private extends Visibility {
    // Production: sig("Private",[])
  
    
  
    public Private(IConstructor node ) {
      super(node);
      
    }
  
    @Override
    public boolean isPrivate() { 
      return true; 
    }
  
    @Override
    public <T> T accept(IASTVisitor<T> visitor) {
      return visitor.visitVisibilityPrivate(this);
    }
  
    	
  }
}