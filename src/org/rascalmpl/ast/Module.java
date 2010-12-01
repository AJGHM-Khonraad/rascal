
package org.rascalmpl.ast;


import org.eclipse.imp.pdb.facts.INode;


public abstract class Module extends AbstractAST {
  public Module(INode node) {
    super(node);
  }
  

  public boolean hasBody() {
    return false;
  }

  public org.rascalmpl.ast.Body getBody() {
    throw new UnsupportedOperationException();
  }

  public boolean hasHeader() {
    return false;
  }

  public org.rascalmpl.ast.Header getHeader() {
    throw new UnsupportedOperationException();
  }


static public class Ambiguity extends Module {
  private final java.util.List<org.rascalmpl.ast.Module> alternatives;

  public Ambiguity(INode node, java.util.List<org.rascalmpl.ast.Module> alternatives) {
    super(node);
    this.alternatives = java.util.Collections.unmodifiableList(alternatives);
  }

  public java.util.List<org.rascalmpl.ast.Module> getAlternatives() {
   return alternatives;
  }

  public <T> T accept(IASTVisitor<T> v) {
	return v.visitModuleAmbiguity(this);
  }
}





  public boolean isDefault() {
    return false;
  }
  
static public class Default extends Module {
  // Production: sig("Default",[arg("org.rascalmpl.ast.Header","header"),arg("org.rascalmpl.ast.Body","body")])

  
     private final org.rascalmpl.ast.Header header;
  
     private final org.rascalmpl.ast.Body body;
  

  
public Default(INode node , org.rascalmpl.ast.Header header,  org.rascalmpl.ast.Body body) {
  super(node);
  
    this.header = header;
  
    this.body = body;
  
}


  @Override
  public boolean isDefault() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitModuleDefault(this);
  }
  
  
     @Override
     public org.rascalmpl.ast.Header getHeader() {
        return this.header;
     }
     
     @Override
     public boolean hasHeader() {
        return true;
     }
  
     @Override
     public org.rascalmpl.ast.Body getBody() {
        return this.body;
     }
     
     @Override
     public boolean hasBody() {
        return true;
     }
  	
}



}
