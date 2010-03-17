package org.rascalmpl.ast; 
import org.eclipse.imp.pdb.facts.INode; 
public abstract class Import extends AbstractAST { 
  public org.rascalmpl.ast.ImportedModule getModule() { throw new UnsupportedOperationException(); } public boolean hasModule() { return false; } public boolean isDefault() { return false; }
static public class Default extends Import {
/** "import" module:ImportedModule ";" -> Import {cons("Default")} */
	public Default(INode node, org.rascalmpl.ast.ImportedModule module) {
		this.node = node;
		this.module = module;
	}
	@Override
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitImportDefault(this);
	}

	@Override
	public boolean isDefault() { return true; }

	@Override
	public boolean hasModule() { return true; }

private final org.rascalmpl.ast.ImportedModule module;
	@Override
	public org.rascalmpl.ast.ImportedModule getModule() { return module; }	
}
static public class Ambiguity extends Import {
  private final java.util.List<org.rascalmpl.ast.Import> alternatives;
  public Ambiguity(INode node, java.util.List<org.rascalmpl.ast.Import> alternatives) {
	this.alternatives = java.util.Collections.unmodifiableList(alternatives);
         this.node = node;
  }
  public java.util.List<org.rascalmpl.ast.Import> getAlternatives() {
	return alternatives;
  }
  
  @Override
public <T> T accept(IASTVisitor<T> v) {
     return v.visitImportAmbiguity(this);
  }
} public boolean isExtend() { return false; }
static public class Extend extends Import {
/** "extend" module:ImportedModule ";" -> Import {cons("Extend")} */
	public Extend(INode node, org.rascalmpl.ast.ImportedModule module) {
		this.node = node;
		this.module = module;
	}
	@Override
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitImportExtend(this);
	}

	@Override
	public boolean isExtend() { return true; }

	@Override
	public boolean hasModule() { return true; }

private final org.rascalmpl.ast.ImportedModule module;
	@Override
	public org.rascalmpl.ast.ImportedModule getModule() { return module; }	
}
 @Override
public abstract <T> T accept(IASTVisitor<T> visitor);
}