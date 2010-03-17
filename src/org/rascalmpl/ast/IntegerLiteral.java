package org.rascalmpl.ast; 
import org.eclipse.imp.pdb.facts.INode; 
public abstract class IntegerLiteral extends AbstractAST { 
  public org.rascalmpl.ast.DecimalIntegerLiteral getDecimal() { throw new UnsupportedOperationException(); }
public boolean hasDecimal() { return false; }
public boolean isDecimalIntegerLiteral() { return false; }
static public class DecimalIntegerLiteral extends IntegerLiteral {
/** decimal:DecimalIntegerLiteral -> IntegerLiteral {prefer, cons("DecimalIntegerLiteral")} */
	public DecimalIntegerLiteral(INode node, org.rascalmpl.ast.DecimalIntegerLiteral decimal) {
		this.node = node;
		this.decimal = decimal;
	}
	@Override
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitIntegerLiteralDecimalIntegerLiteral(this);
	}

	@Override
	public boolean isDecimalIntegerLiteral() { return true; }

	@Override
	public boolean hasDecimal() { return true; }

private final org.rascalmpl.ast.DecimalIntegerLiteral decimal;
	@Override
	public org.rascalmpl.ast.DecimalIntegerLiteral getDecimal() { return decimal; }	
}
static public class Ambiguity extends IntegerLiteral {
  private final java.util.List<org.rascalmpl.ast.IntegerLiteral> alternatives;
  public Ambiguity(INode node, java.util.List<org.rascalmpl.ast.IntegerLiteral> alternatives) {
	this.alternatives = java.util.Collections.unmodifiableList(alternatives);
         this.node = node;
  }
  public java.util.List<org.rascalmpl.ast.IntegerLiteral> getAlternatives() {
	return alternatives;
  }
  
  @Override
public <T> T accept(IASTVisitor<T> v) {
     return v.visitIntegerLiteralAmbiguity(this);
  }
} 
public org.rascalmpl.ast.HexIntegerLiteral getHex() { throw new UnsupportedOperationException(); }
public boolean hasHex() { return false; }
public boolean isHexIntegerLiteral() { return false; }
static public class HexIntegerLiteral extends IntegerLiteral {
/** hex:HexIntegerLiteral -> IntegerLiteral {prefer, cons("HexIntegerLiteral")} */
	public HexIntegerLiteral(INode node, org.rascalmpl.ast.HexIntegerLiteral hex) {
		this.node = node;
		this.hex = hex;
	}
	@Override
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitIntegerLiteralHexIntegerLiteral(this);
	}

	@Override
	public boolean isHexIntegerLiteral() { return true; }

	@Override
	public boolean hasHex() { return true; }

private final org.rascalmpl.ast.HexIntegerLiteral hex;
	@Override
	public org.rascalmpl.ast.HexIntegerLiteral getHex() { return hex; }	
} @Override
public abstract <T> T accept(IASTVisitor<T> visitor); public org.rascalmpl.ast.OctalIntegerLiteral getOctal() { throw new UnsupportedOperationException(); }
public boolean hasOctal() { return false; }
public boolean isOctalIntegerLiteral() { return false; }
static public class OctalIntegerLiteral extends IntegerLiteral {
/** octal:OctalIntegerLiteral -> IntegerLiteral {prefer, cons("OctalIntegerLiteral")} */
	public OctalIntegerLiteral(INode node, org.rascalmpl.ast.OctalIntegerLiteral octal) {
		this.node = node;
		this.octal = octal;
	}
	@Override
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitIntegerLiteralOctalIntegerLiteral(this);
	}

	@Override
	public boolean isOctalIntegerLiteral() { return true; }

	@Override
	public boolean hasOctal() { return true; }

private final org.rascalmpl.ast.OctalIntegerLiteral octal;
	@Override
	public org.rascalmpl.ast.OctalIntegerLiteral getOctal() { return octal; }	
}
}