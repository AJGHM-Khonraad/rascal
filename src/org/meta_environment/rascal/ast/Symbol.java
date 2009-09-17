package org.meta_environment.rascal.ast; 
import org.eclipse.imp.pdb.facts.INode; 
public abstract class Symbol extends AbstractAST { 
  public boolean isEmpty() { return false; }
static public class Empty extends Symbol {
/** "(" ")" -> Symbol {cons("Empty")} */
	public Empty(INode node) {
		this.node = node;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitSymbolEmpty(this);
	}

	public boolean isEmpty() { return true; }	
}
static public class Ambiguity extends Symbol {
  private final java.util.List<org.meta_environment.rascal.ast.Symbol> alternatives;
  public Ambiguity(INode node, java.util.List<org.meta_environment.rascal.ast.Symbol> alternatives) {
	this.alternatives = java.util.Collections.unmodifiableList(alternatives);
         this.node = node;
  }
  public java.util.List<org.meta_environment.rascal.ast.Symbol> getAlternatives() {
	return alternatives;
  }
  
  public <T> T accept(IASTVisitor<T> v) {
     return v.visitSymbolAmbiguity(this);
  }
} 
public org.meta_environment.rascal.ast.Symbol getHead() { throw new UnsupportedOperationException(); }
	public java.util.List<org.meta_environment.rascal.ast.Symbol> getTail() { throw new UnsupportedOperationException(); }
public boolean hasHead() { return false; }
	public boolean hasTail() { return false; }
public boolean isSequence() { return false; }
static public class Sequence extends Symbol {
/** "(" head:Symbol tail:Symbol+ ")" -> Symbol {cons("Sequence")} */
	public Sequence(INode node, org.meta_environment.rascal.ast.Symbol head, java.util.List<org.meta_environment.rascal.ast.Symbol> tail) {
		this.node = node;
		this.head = head;
		this.tail = tail;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitSymbolSequence(this);
	}

	public boolean isSequence() { return true; }

	public boolean hasHead() { return true; }
	public boolean hasTail() { return true; }

private final org.meta_environment.rascal.ast.Symbol head;
	public org.meta_environment.rascal.ast.Symbol getHead() { return head; }
	private final java.util.List<org.meta_environment.rascal.ast.Symbol> tail;
	public java.util.List<org.meta_environment.rascal.ast.Symbol> getTail() { return tail; }	
} public abstract <T> T accept(IASTVisitor<T> visitor); public org.meta_environment.rascal.ast.Symbol getSymbol() { throw new UnsupportedOperationException(); } public boolean hasSymbol() { return false; } public boolean isOptional() { return false; } static public class Optional extends Symbol {
/** symbol:Symbol "?" -> Symbol {cons("Optional")} */
	public Optional(INode node, org.meta_environment.rascal.ast.Symbol symbol) {
		this.node = node;
		this.symbol = symbol;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitSymbolOptional(this);
	}

	public boolean isOptional() { return true; }

	public boolean hasSymbol() { return true; }

private final org.meta_environment.rascal.ast.Symbol symbol;
	public org.meta_environment.rascal.ast.Symbol getSymbol() { return symbol; }	
} public boolean isIter() { return false; } static public class Iter extends Symbol {
/** symbol:Symbol "+" -> Symbol {cons("Iter")} */
	public Iter(INode node, org.meta_environment.rascal.ast.Symbol symbol) {
		this.node = node;
		this.symbol = symbol;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitSymbolIter(this);
	}

	public boolean isIter() { return true; }

	public boolean hasSymbol() { return true; }

private final org.meta_environment.rascal.ast.Symbol symbol;
	public org.meta_environment.rascal.ast.Symbol getSymbol() { return symbol; }	
} public boolean isIterStar() { return false; } static public class IterStar extends Symbol {
/** symbol:Symbol "*" -> Symbol {cons("IterStar")} */
	public IterStar(INode node, org.meta_environment.rascal.ast.Symbol symbol) {
		this.node = node;
		this.symbol = symbol;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitSymbolIterStar(this);
	}

	public boolean isIterStar() { return true; }

	public boolean hasSymbol() { return true; }

private final org.meta_environment.rascal.ast.Symbol symbol;
	public org.meta_environment.rascal.ast.Symbol getSymbol() { return symbol; }	
} public org.meta_environment.rascal.ast.StrCon getSep() { throw new UnsupportedOperationException(); } public boolean hasSep() { return false; } public boolean isIterSep() { return false; }
static public class IterSep extends Symbol {
/** "{" symbol:Symbol sep:StrCon "}" "+" -> Symbol {cons("IterSep")} */
	public IterSep(INode node, org.meta_environment.rascal.ast.Symbol symbol, org.meta_environment.rascal.ast.StrCon sep) {
		this.node = node;
		this.symbol = symbol;
		this.sep = sep;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitSymbolIterSep(this);
	}

	public boolean isIterSep() { return true; }

	public boolean hasSymbol() { return true; }
	public boolean hasSep() { return true; }

private final org.meta_environment.rascal.ast.Symbol symbol;
	public org.meta_environment.rascal.ast.Symbol getSymbol() { return symbol; }
	private final org.meta_environment.rascal.ast.StrCon sep;
	public org.meta_environment.rascal.ast.StrCon getSep() { return sep; }	
} public boolean isIterStarSep() { return false; }
static public class IterStarSep extends Symbol {
/** "{" symbol:Symbol sep:StrCon "}" "*" -> Symbol {cons("IterStarSep")} */
	public IterStarSep(INode node, org.meta_environment.rascal.ast.Symbol symbol, org.meta_environment.rascal.ast.StrCon sep) {
		this.node = node;
		this.symbol = symbol;
		this.sep = sep;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitSymbolIterStarSep(this);
	}

	public boolean isIterStarSep() { return true; }

	public boolean hasSymbol() { return true; }
	public boolean hasSep() { return true; }

private final org.meta_environment.rascal.ast.Symbol symbol;
	public org.meta_environment.rascal.ast.Symbol getSymbol() { return symbol; }
	private final org.meta_environment.rascal.ast.StrCon sep;
	public org.meta_environment.rascal.ast.StrCon getSep() { return sep; }	
} public org.meta_environment.rascal.ast.Symbol getLhs() { throw new UnsupportedOperationException(); } public org.meta_environment.rascal.ast.Symbol getRhs() { throw new UnsupportedOperationException(); } public boolean hasLhs() { return false; } public boolean hasRhs() { return false; } public boolean isAlternative() { return false; } static public class Alternative extends Symbol {
/** lhs:Symbol "|" rhs:Symbol -> Symbol {right, cons("Alternative")} */
	public Alternative(INode node, org.meta_environment.rascal.ast.Symbol lhs, org.meta_environment.rascal.ast.Symbol rhs) {
		this.node = node;
		this.lhs = lhs;
		this.rhs = rhs;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitSymbolAlternative(this);
	}

	public boolean isAlternative() { return true; }

	public boolean hasLhs() { return true; }
	public boolean hasRhs() { return true; }

private final org.meta_environment.rascal.ast.Symbol lhs;
	public org.meta_environment.rascal.ast.Symbol getLhs() { return lhs; }
	private final org.meta_environment.rascal.ast.Symbol rhs;
	public org.meta_environment.rascal.ast.Symbol getRhs() { return rhs; }	
} public org.meta_environment.rascal.ast.CharClass getCharClass() { throw new UnsupportedOperationException(); }
public boolean hasCharClass() { return false; }
public boolean isCharacterClass() { return false; }
static public class CharacterClass extends Symbol {
/** charClass:CharClass -> Symbol {cons("CharacterClass")} */
	public CharacterClass(INode node, org.meta_environment.rascal.ast.CharClass charClass) {
		this.node = node;
		this.charClass = charClass;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitSymbolCharacterClass(this);
	}

	public boolean isCharacterClass() { return true; }

	public boolean hasCharClass() { return true; }

private final org.meta_environment.rascal.ast.CharClass charClass;
	public org.meta_environment.rascal.ast.CharClass getCharClass() { return charClass; }	
} 
public org.meta_environment.rascal.ast.StrCon getString() { throw new UnsupportedOperationException(); }
public boolean hasString() { return false; }
public boolean isLiteral() { return false; }
static public class Literal extends Symbol {
/** string:StrCon -> Symbol {cons("Literal")} */
	public Literal(INode node, org.meta_environment.rascal.ast.StrCon string) {
		this.node = node;
		this.string = string;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitSymbolLiteral(this);
	}

	public boolean isLiteral() { return true; }

	public boolean hasString() { return true; }

private final org.meta_environment.rascal.ast.StrCon string;
	public org.meta_environment.rascal.ast.StrCon getString() { return string; }	
} 
public org.meta_environment.rascal.ast.SingleQuotedStrCon getSingelQuotedString() { throw new UnsupportedOperationException(); }
public boolean hasSingelQuotedString() { return false; }
public boolean isCaseInsensitiveLiteral() { return false; }
static public class CaseInsensitiveLiteral extends Symbol {
/** singelQuotedString:SingleQuotedStrCon -> Symbol {cons("CaseInsensitiveLiteral")} */
	public CaseInsensitiveLiteral(INode node, org.meta_environment.rascal.ast.SingleQuotedStrCon singelQuotedString) {
		this.node = node;
		this.singelQuotedString = singelQuotedString;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitSymbolCaseInsensitiveLiteral(this);
	}

	public boolean isCaseInsensitiveLiteral() { return true; }

	public boolean hasSingelQuotedString() { return true; }

private final org.meta_environment.rascal.ast.SingleQuotedStrCon singelQuotedString;
	public org.meta_environment.rascal.ast.SingleQuotedStrCon getSingelQuotedString() { return singelQuotedString; }	
} 
public org.meta_environment.rascal.ast.Name getName() { throw new UnsupportedOperationException(); }
public boolean hasName() { return false; }
public boolean isSort() { return false; }
static public class Sort extends Symbol {
/** name:Name -> Symbol {cons("Sort")} */
	public Sort(INode node, org.meta_environment.rascal.ast.Name name) {
		this.node = node;
		this.name = name;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitSymbolSort(this);
	}

	public boolean isSort() { return true; }

	public boolean hasName() { return true; }

private final org.meta_environment.rascal.ast.Name name;
	public org.meta_environment.rascal.ast.Name getName() { return name; }	
}
}