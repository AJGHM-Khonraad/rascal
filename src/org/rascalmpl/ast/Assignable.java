
package org.rascalmpl.ast;


import org.eclipse.imp.pdb.facts.INode;

import org.rascalmpl.interpreter.asserts.Ambiguous;

import org.eclipse.imp.pdb.facts.IConstructor;

import org.eclipse.imp.pdb.facts.IValue;

import org.eclipse.imp.pdb.facts.type.Type;

import org.rascalmpl.interpreter.BooleanEvaluator;

import org.rascalmpl.interpreter.Evaluator;

import org.rascalmpl.interpreter.PatternEvaluator;

import org.rascalmpl.interpreter.asserts.Ambiguous;

import org.rascalmpl.interpreter.env.Environment;

import org.rascalmpl.interpreter.matching.IBooleanResult;

import org.rascalmpl.interpreter.matching.IMatchingResult;

import org.rascalmpl.interpreter.result.Result;


public abstract class Assignable extends AbstractAST {
  public Assignable(INode node) {
    super(node);
  }
  

  public boolean hasArg() {
    return false;
  }

  public org.rascalmpl.ast.Assignable getArg() {
    throw new UnsupportedOperationException();
  }

  public boolean hasAnnotation() {
    return false;
  }

  public org.rascalmpl.ast.Name getAnnotation() {
    throw new UnsupportedOperationException();
  }

  public boolean hasArguments() {
    return false;
  }

  public java.util.List<org.rascalmpl.ast.Assignable> getArguments() {
    throw new UnsupportedOperationException();
  }

  public boolean hasQualifiedName() {
    return false;
  }

  public org.rascalmpl.ast.QualifiedName getQualifiedName() {
    throw new UnsupportedOperationException();
  }

  public boolean hasSubscript() {
    return false;
  }

  public org.rascalmpl.ast.Expression getSubscript() {
    throw new UnsupportedOperationException();
  }

  public boolean hasElements() {
    return false;
  }

  public java.util.List<org.rascalmpl.ast.Assignable> getElements() {
    throw new UnsupportedOperationException();
  }

  public boolean hasName() {
    return false;
  }

  public org.rascalmpl.ast.Name getName() {
    throw new UnsupportedOperationException();
  }

  public boolean hasField() {
    return false;
  }

  public org.rascalmpl.ast.Name getField() {
    throw new UnsupportedOperationException();
  }

  public boolean hasReceiver() {
    return false;
  }

  public org.rascalmpl.ast.Assignable getReceiver() {
    throw new UnsupportedOperationException();
  }

  public boolean hasDefaultExpression() {
    return false;
  }

  public org.rascalmpl.ast.Expression getDefaultExpression() {
    throw new UnsupportedOperationException();
  }


static public class Ambiguity extends Assignable {
  private final java.util.List<org.rascalmpl.ast.Assignable> alternatives;

  public Ambiguity(INode node, java.util.List<org.rascalmpl.ast.Assignable> alternatives) {
    super(node);
    this.alternatives = java.util.Collections.unmodifiableList(alternatives);
  }

  @Override
  public Result<IValue> interpret(Evaluator __eval) {
    throw new Ambiguous((IConstructor) this.getTree());
  }
  
  @Override
  public Type typeOf(Environment env) {
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
  
  public java.util.List<org.rascalmpl.ast.Assignable> getAlternatives() {
   return alternatives;
  }

  public <T> T accept(IASTVisitor<T> v) {
	return v.visitAssignableAmbiguity(this);
  }
}





  public boolean isTuple() {
    return false;
  }
  
static public class Tuple extends Assignable {
  // Production: sig("Tuple",[arg("java.util.List\<org.rascalmpl.ast.Assignable\>","elements")])

  
     private final java.util.List<org.rascalmpl.ast.Assignable> elements;
  

  
public Tuple(INode node , java.util.List<org.rascalmpl.ast.Assignable> elements) {
  super(node);
  
    this.elements = elements;
  
}


  @Override
  public boolean isTuple() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitAssignableTuple(this);
  }
  
  
     @Override
     public java.util.List<org.rascalmpl.ast.Assignable> getElements() {
        return this.elements;
     }
     
     @Override
     public boolean hasElements() {
        return true;
     }
  	
}


  public boolean isVariable() {
    return false;
  }
  
static public class Variable extends Assignable {
  // Production: sig("Variable",[arg("org.rascalmpl.ast.QualifiedName","qualifiedName")])

  
     private final org.rascalmpl.ast.QualifiedName qualifiedName;
  

  
public Variable(INode node , org.rascalmpl.ast.QualifiedName qualifiedName) {
  super(node);
  
    this.qualifiedName = qualifiedName;
  
}


  @Override
  public boolean isVariable() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitAssignableVariable(this);
  }
  
  
     @Override
     public org.rascalmpl.ast.QualifiedName getQualifiedName() {
        return this.qualifiedName;
     }
     
     @Override
     public boolean hasQualifiedName() {
        return true;
     }
  	
}


  public boolean isIfDefinedOrDefault() {
    return false;
  }
  
static public class IfDefinedOrDefault extends Assignable {
  // Production: sig("IfDefinedOrDefault",[arg("org.rascalmpl.ast.Assignable","receiver"),arg("org.rascalmpl.ast.Expression","defaultExpression")])

  
     private final org.rascalmpl.ast.Assignable receiver;
  
     private final org.rascalmpl.ast.Expression defaultExpression;
  

  
public IfDefinedOrDefault(INode node , org.rascalmpl.ast.Assignable receiver,  org.rascalmpl.ast.Expression defaultExpression) {
  super(node);
  
    this.receiver = receiver;
  
    this.defaultExpression = defaultExpression;
  
}


  @Override
  public boolean isIfDefinedOrDefault() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitAssignableIfDefinedOrDefault(this);
  }
  
  
     @Override
     public org.rascalmpl.ast.Assignable getReceiver() {
        return this.receiver;
     }
     
     @Override
     public boolean hasReceiver() {
        return true;
     }
  
     @Override
     public org.rascalmpl.ast.Expression getDefaultExpression() {
        return this.defaultExpression;
     }
     
     @Override
     public boolean hasDefaultExpression() {
        return true;
     }
  	
}


  public boolean isSubscript() {
    return false;
  }
  
static public class Subscript extends Assignable {
  // Production: sig("Subscript",[arg("org.rascalmpl.ast.Assignable","receiver"),arg("org.rascalmpl.ast.Expression","subscript")])

  
     private final org.rascalmpl.ast.Assignable receiver;
  
     private final org.rascalmpl.ast.Expression subscript;
  

  
public Subscript(INode node , org.rascalmpl.ast.Assignable receiver,  org.rascalmpl.ast.Expression subscript) {
  super(node);
  
    this.receiver = receiver;
  
    this.subscript = subscript;
  
}


  @Override
  public boolean isSubscript() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitAssignableSubscript(this);
  }
  
  
     @Override
     public org.rascalmpl.ast.Assignable getReceiver() {
        return this.receiver;
     }
     
     @Override
     public boolean hasReceiver() {
        return true;
     }
  
     @Override
     public org.rascalmpl.ast.Expression getSubscript() {
        return this.subscript;
     }
     
     @Override
     public boolean hasSubscript() {
        return true;
     }
  	
}


  public boolean isBracket() {
    return false;
  }
  
static public class Bracket extends Assignable {
  // Production: sig("Bracket",[arg("org.rascalmpl.ast.Assignable","arg")])

  
     private final org.rascalmpl.ast.Assignable arg;
  

  
public Bracket(INode node , org.rascalmpl.ast.Assignable arg) {
  super(node);
  
    this.arg = arg;
  
}


  @Override
  public boolean isBracket() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitAssignableBracket(this);
  }
  
  
     @Override
     public org.rascalmpl.ast.Assignable getArg() {
        return this.arg;
     }
     
     @Override
     public boolean hasArg() {
        return true;
     }
  	
}


  public boolean isFieldAccess() {
    return false;
  }
  
static public class FieldAccess extends Assignable {
  // Production: sig("FieldAccess",[arg("org.rascalmpl.ast.Assignable","receiver"),arg("org.rascalmpl.ast.Name","field")])

  
     private final org.rascalmpl.ast.Assignable receiver;
  
     private final org.rascalmpl.ast.Name field;
  

  
public FieldAccess(INode node , org.rascalmpl.ast.Assignable receiver,  org.rascalmpl.ast.Name field) {
  super(node);
  
    this.receiver = receiver;
  
    this.field = field;
  
}


  @Override
  public boolean isFieldAccess() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitAssignableFieldAccess(this);
  }
  
  
     @Override
     public org.rascalmpl.ast.Assignable getReceiver() {
        return this.receiver;
     }
     
     @Override
     public boolean hasReceiver() {
        return true;
     }
  
     @Override
     public org.rascalmpl.ast.Name getField() {
        return this.field;
     }
     
     @Override
     public boolean hasField() {
        return true;
     }
  	
}


  public boolean isConstructor() {
    return false;
  }
  
static public class Constructor extends Assignable {
  // Production: sig("Constructor",[arg("org.rascalmpl.ast.Name","name"),arg("java.util.List\<org.rascalmpl.ast.Assignable\>","arguments")])

  
     private final org.rascalmpl.ast.Name name;
  
     private final java.util.List<org.rascalmpl.ast.Assignable> arguments;
  

  
public Constructor(INode node , org.rascalmpl.ast.Name name,  java.util.List<org.rascalmpl.ast.Assignable> arguments) {
  super(node);
  
    this.name = name;
  
    this.arguments = arguments;
  
}


  @Override
  public boolean isConstructor() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitAssignableConstructor(this);
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
     public java.util.List<org.rascalmpl.ast.Assignable> getArguments() {
        return this.arguments;
     }
     
     @Override
     public boolean hasArguments() {
        return true;
     }
  	
}


  public boolean isAnnotation() {
    return false;
  }
  
static public class Annotation extends Assignable {
  // Production: sig("Annotation",[arg("org.rascalmpl.ast.Assignable","receiver"),arg("org.rascalmpl.ast.Name","annotation")])

  
     private final org.rascalmpl.ast.Assignable receiver;
  
     private final org.rascalmpl.ast.Name annotation;
  

  
public Annotation(INode node , org.rascalmpl.ast.Assignable receiver,  org.rascalmpl.ast.Name annotation) {
  super(node);
  
    this.receiver = receiver;
  
    this.annotation = annotation;
  
}


  @Override
  public boolean isAnnotation() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitAssignableAnnotation(this);
  }
  
  
     @Override
     public org.rascalmpl.ast.Assignable getReceiver() {
        return this.receiver;
     }
     
     @Override
     public boolean hasReceiver() {
        return true;
     }
  
     @Override
     public org.rascalmpl.ast.Name getAnnotation() {
        return this.annotation;
     }
     
     @Override
     public boolean hasAnnotation() {
        return true;
     }
  	
}



}
