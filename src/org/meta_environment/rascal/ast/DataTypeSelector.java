package org.meta_environment.rascal.ast; 
import org.eclipse.imp.pdb.facts.INode; 
public abstract class DataTypeSelector extends AbstractAST { 
public org.meta_environment.rascal.ast.Name getSort() { throw new UnsupportedOperationException(); }
	public org.meta_environment.rascal.ast.Name getProduction() { throw new UnsupportedOperationException(); }
public boolean hasSort() { return false; }
	public boolean hasProduction() { return false; }
public boolean isSelector() { return false; }
static public class Selector extends DataTypeSelector {
/** sort:Name "." production:Name -> DataTypeSelector {cons("Selector")} */
	public Selector(INode node, org.meta_environment.rascal.ast.Name sort, org.meta_environment.rascal.ast.Name production) {
		this.node = node;
		this.sort = sort;
		this.production = production;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitDataTypeSelectorSelector(this);
	}

	public boolean isSelector() { return true; }

	public boolean hasSort() { return true; }
	public boolean hasProduction() { return true; }

private final org.meta_environment.rascal.ast.Name sort;
	public org.meta_environment.rascal.ast.Name getSort() { return sort; }
	private final org.meta_environment.rascal.ast.Name production;
	public org.meta_environment.rascal.ast.Name getProduction() { return production; }	
}
static public class Ambiguity extends DataTypeSelector {
  private final java.util.List<org.meta_environment.rascal.ast.DataTypeSelector> alternatives;
  public Ambiguity(INode node, java.util.List<org.meta_environment.rascal.ast.DataTypeSelector> alternatives) {
	this.alternatives = java.util.Collections.unmodifiableList(alternatives);
         this.node = node;
  }
  public java.util.List<org.meta_environment.rascal.ast.DataTypeSelector> getAlternatives() {
	return alternatives;
  }
  
  public <T> T accept(IASTVisitor<T> v) {
     return v.visitDataTypeSelectorAmbiguity(this);
  }
}
}