
package org.rascalmpl.ast;


import org.eclipse.imp.pdb.facts.INode;


public abstract class ImportedModule extends AbstractAST {
  public ImportedModule(INode node) {
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

  public Ambiguity(INode node, java.util.List<org.rascalmpl.ast.ImportedModule> alternatives) {
    super(node);
    this.alternatives = java.util.Collections.unmodifiableList(alternatives);
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
  

  
public Renamings(INode node , org.rascalmpl.ast.QualifiedName name,  org.rascalmpl.ast.Renamings renamings) {
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
  

  
public ActualsRenaming(INode node , org.rascalmpl.ast.QualifiedName name,  org.rascalmpl.ast.ModuleActuals actuals,  org.rascalmpl.ast.Renamings renamings) {
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
  

  
public Default(INode node , org.rascalmpl.ast.QualifiedName name) {
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
  

  
public Actuals(INode node , org.rascalmpl.ast.QualifiedName name,  org.rascalmpl.ast.ModuleActuals actuals) {
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
