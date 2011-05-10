
package org.rascalmpl.ast;


import org.eclipse.imp.pdb.facts.IConstructor;

import org.eclipse.imp.pdb.facts.IConstructor;

import org.eclipse.imp.pdb.facts.IValue;

import org.rascalmpl.interpreter.Evaluator;

import org.rascalmpl.interpreter.asserts.Ambiguous;

import org.rascalmpl.interpreter.env.Environment;

import org.rascalmpl.interpreter.matching.IBooleanResult;

import org.rascalmpl.interpreter.matching.IMatchingResult;

import org.rascalmpl.interpreter.result.Result;


public abstract class ImportedModule extends AbstractAST {
  public ImportedModule(IConstructor node) {
    super(node);
  }
  

  public boolean hasRenamings() {
    return false;
  }

  public org.rascalmpl.ast.Renamings getRenamings() {
    throw new UnsupportedOperationException();
  }

  public boolean hasName() {
    return false;
  }

  public org.rascalmpl.ast.QualifiedName getName() {
    throw new UnsupportedOperationException();
  }

  public boolean hasActuals() {
    return false;
  }

  public org.rascalmpl.ast.ModuleActuals getActuals() {
    throw new UnsupportedOperationException();
  }


static public class Ambiguity extends ImportedModule {
  private final java.util.List<org.rascalmpl.ast.ImportedModule> alternatives;

  public Ambiguity(IConstructor node, java.util.List<org.rascalmpl.ast.ImportedModule> alternatives) {
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
  
  public java.util.List<org.rascalmpl.ast.ImportedModule> getAlternatives() {
   return alternatives;
  }

  public <T> T accept(IASTVisitor<T> v) {
	return v.visitImportedModuleAmbiguity(this);
  }
}





  public boolean isRenamings() {
    return false;
  }
  
static public class Renamings extends ImportedModule {
  // Production: sig("Renamings",[arg("org.rascalmpl.ast.QualifiedName","name"),arg("org.rascalmpl.ast.Renamings","renamings")])

  
     private final org.rascalmpl.ast.QualifiedName name;
  
     private final org.rascalmpl.ast.Renamings renamings;
  

  
public Renamings(IConstructor node , org.rascalmpl.ast.QualifiedName name,  org.rascalmpl.ast.Renamings renamings) {
  super(node);
  
    this.name = name;
  
    this.renamings = renamings;
  
}


  @Override
  public boolean isRenamings() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitImportedModuleRenamings(this);
  }
  
  
     @Override
     public org.rascalmpl.ast.QualifiedName getName() {
        return this.name;
     }
     
     @Override
     public boolean hasName() {
        return true;
     }
  
     @Override
     public org.rascalmpl.ast.Renamings getRenamings() {
        return this.renamings;
     }
     
     @Override
     public boolean hasRenamings() {
        return true;
     }
  	
}


  public boolean isActualsRenaming() {
    return false;
  }
  
static public class ActualsRenaming extends ImportedModule {
  // Production: sig("ActualsRenaming",[arg("org.rascalmpl.ast.QualifiedName","name"),arg("org.rascalmpl.ast.ModuleActuals","actuals"),arg("org.rascalmpl.ast.Renamings","renamings")])

  
     private final org.rascalmpl.ast.QualifiedName name;
  
     private final org.rascalmpl.ast.ModuleActuals actuals;
  
     private final org.rascalmpl.ast.Renamings renamings;
  

  
public ActualsRenaming(IConstructor node , org.rascalmpl.ast.QualifiedName name,  org.rascalmpl.ast.ModuleActuals actuals,  org.rascalmpl.ast.Renamings renamings) {
  super(node);
  
    this.name = name;
  
    this.actuals = actuals;
  
    this.renamings = renamings;
  
}


  @Override
  public boolean isActualsRenaming() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitImportedModuleActualsRenaming(this);
  }
  
  
     @Override
     public org.rascalmpl.ast.QualifiedName getName() {
        return this.name;
     }
     
     @Override
     public boolean hasName() {
        return true;
     }
  
     @Override
     public org.rascalmpl.ast.ModuleActuals getActuals() {
        return this.actuals;
     }
     
     @Override
     public boolean hasActuals() {
        return true;
     }
  
     @Override
     public org.rascalmpl.ast.Renamings getRenamings() {
        return this.renamings;
     }
     
     @Override
     public boolean hasRenamings() {
        return true;
     }
  	
}


  public boolean isDefault() {
    return false;
  }
  
static public class Default extends ImportedModule {
  // Production: sig("Default",[arg("org.rascalmpl.ast.QualifiedName","name")])

  
     private final org.rascalmpl.ast.QualifiedName name;
  

  
public Default(IConstructor node , org.rascalmpl.ast.QualifiedName name) {
  super(node);
  
    this.name = name;
  
}


  @Override
  public boolean isDefault() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitImportedModuleDefault(this);
  }
  
  
     @Override
     public org.rascalmpl.ast.QualifiedName getName() {
        return this.name;
     }
     
     @Override
     public boolean hasName() {
        return true;
     }
  	
}


  public boolean isActuals() {
    return false;
  }
  
static public class Actuals extends ImportedModule {
  // Production: sig("Actuals",[arg("org.rascalmpl.ast.QualifiedName","name"),arg("org.rascalmpl.ast.ModuleActuals","actuals")])

  
     private final org.rascalmpl.ast.QualifiedName name;
  
     private final org.rascalmpl.ast.ModuleActuals actuals;
  

  
public Actuals(IConstructor node , org.rascalmpl.ast.QualifiedName name,  org.rascalmpl.ast.ModuleActuals actuals) {
  super(node);
  
    this.name = name;
  
    this.actuals = actuals;
  
}


  @Override
  public boolean isActuals() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitImportedModuleActuals(this);
  }
  
  
     @Override
     public org.rascalmpl.ast.QualifiedName getName() {
        return this.name;
     }
     
     @Override
     public boolean hasName() {
        return true;
     }
  
     @Override
     public org.rascalmpl.ast.ModuleActuals getActuals() {
        return this.actuals;
     }
     
     @Override
     public boolean hasActuals() {
        return true;
     }
  	
}



}
