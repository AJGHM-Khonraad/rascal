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


public abstract class Comprehension extends AbstractAST {
  public Comprehension(IConstructor node) {
    super(node);
  }
  

  public boolean hasResults() {
    return false;
  }

  public java.util.List<org.rascalmpl.ast.Expression> getResults() {
    throw new UnsupportedOperationException();
  }

  public boolean hasTo() {
    return false;
  }

  public org.rascalmpl.ast.Expression getTo() {
    throw new UnsupportedOperationException();
  }

  public boolean hasFrom() {
    return false;
  }

  public org.rascalmpl.ast.Expression getFrom() {
    throw new UnsupportedOperationException();
  }

  public boolean hasGenerators() {
    return false;
  }

  public java.util.List<org.rascalmpl.ast.Expression> getGenerators() {
    throw new UnsupportedOperationException();
  }


static public class Ambiguity extends Comprehension {
  private final java.util.List<org.rascalmpl.ast.Comprehension> alternatives;

  public Ambiguity(IConstructor node, java.util.List<org.rascalmpl.ast.Comprehension> alternatives) {
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
  
  public java.util.List<org.rascalmpl.ast.Comprehension> getAlternatives() {
   return alternatives;
  }

  public <T> T accept(IASTVisitor<T> v) {
	return v.visitComprehensionAmbiguity(this);
  }
}





  public boolean isSet() {
    return false;
  }
  
static public class Set extends Comprehension {
  // Production: sig("Set",[arg("java.util.List\<org.rascalmpl.ast.Expression\>","results"),arg("java.util.List\<org.rascalmpl.ast.Expression\>","generators")])

  
     private final java.util.List<org.rascalmpl.ast.Expression> results;
  
     private final java.util.List<org.rascalmpl.ast.Expression> generators;
  

  
public Set(IConstructor node , java.util.List<org.rascalmpl.ast.Expression> results,  java.util.List<org.rascalmpl.ast.Expression> generators) {
  super(node);
  
    this.results = results;
  
    this.generators = generators;
  
}


  @Override
  public boolean isSet() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitComprehensionSet(this);
  }
  
  
     @Override
     public java.util.List<org.rascalmpl.ast.Expression> getResults() {
        return this.results;
     }
     
     @Override
     public boolean hasResults() {
        return true;
     }
  
     @Override
     public java.util.List<org.rascalmpl.ast.Expression> getGenerators() {
        return this.generators;
     }
     
     @Override
     public boolean hasGenerators() {
        return true;
     }
  	
}


  public boolean isMap() {
    return false;
  }
  
static public class Map extends Comprehension {
  // Production: sig("Map",[arg("org.rascalmpl.ast.Expression","from"),arg("org.rascalmpl.ast.Expression","to"),arg("java.util.List\<org.rascalmpl.ast.Expression\>","generators")])

  
     private final org.rascalmpl.ast.Expression from;
  
     private final org.rascalmpl.ast.Expression to;
  
     private final java.util.List<org.rascalmpl.ast.Expression> generators;
  

  
public Map(IConstructor node , org.rascalmpl.ast.Expression from,  org.rascalmpl.ast.Expression to,  java.util.List<org.rascalmpl.ast.Expression> generators) {
  super(node);
  
    this.from = from;
  
    this.to = to;
  
    this.generators = generators;
  
}


  @Override
  public boolean isMap() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitComprehensionMap(this);
  }
  
  
     @Override
     public org.rascalmpl.ast.Expression getFrom() {
        return this.from;
     }
     
     @Override
     public boolean hasFrom() {
        return true;
     }
  
     @Override
     public org.rascalmpl.ast.Expression getTo() {
        return this.to;
     }
     
     @Override
     public boolean hasTo() {
        return true;
     }
  
     @Override
     public java.util.List<org.rascalmpl.ast.Expression> getGenerators() {
        return this.generators;
     }
     
     @Override
     public boolean hasGenerators() {
        return true;
     }
  	
}


  public boolean isList() {
    return false;
  }
  
static public class List extends Comprehension {
  // Production: sig("List",[arg("java.util.List\<org.rascalmpl.ast.Expression\>","results"),arg("java.util.List\<org.rascalmpl.ast.Expression\>","generators")])

  
     private final java.util.List<org.rascalmpl.ast.Expression> results;
  
     private final java.util.List<org.rascalmpl.ast.Expression> generators;
  

  
public List(IConstructor node , java.util.List<org.rascalmpl.ast.Expression> results,  java.util.List<org.rascalmpl.ast.Expression> generators) {
  super(node);
  
    this.results = results;
  
    this.generators = generators;
  
}


  @Override
  public boolean isList() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitComprehensionList(this);
  }
  
  
     @Override
     public java.util.List<org.rascalmpl.ast.Expression> getResults() {
        return this.results;
     }
     
     @Override
     public boolean hasResults() {
        return true;
     }
  
     @Override
     public java.util.List<org.rascalmpl.ast.Expression> getGenerators() {
        return this.generators;
     }
     
     @Override
     public boolean hasGenerators() {
        return true;
     }
  	
}



}
