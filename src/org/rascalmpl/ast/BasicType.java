package org.rascalmpl.ast; 
import org.eclipse.imp.pdb.facts.INode; 
public abstract class BasicType extends AbstractAST { 
  public boolean isBool() { return false; }
static public class Bool extends BasicType {
/** "bool" -> BasicType {cons("Bool")} */
	public Bool(INode node) {
		this.node = node;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitBasicTypeBool(this);
	}

	public boolean isBool() { return true; }	
}
static public class Ambiguity extends BasicType {
  private final java.util.List<org.rascalmpl.ast.BasicType> alternatives;
  public Ambiguity(INode node, java.util.List<org.rascalmpl.ast.BasicType> alternatives) {
	this.alternatives = java.util.Collections.unmodifiableList(alternatives);
         this.node = node;
  }
  public java.util.List<org.rascalmpl.ast.BasicType> getAlternatives() {
	return alternatives;
  }
  
  public <T> T accept(IASTVisitor<T> v) {
     return v.visitBasicTypeAmbiguity(this);
  }
} 
public boolean isInt() { return false; }
static public class Int extends BasicType {
/** "int" -> BasicType {cons("Int")} */
	public Int(INode node) {
		this.node = node;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitBasicTypeInt(this);
	}

	public boolean isInt() { return true; }	
} public abstract <T> T accept(IASTVisitor<T> visitor); public boolean isReal() { return false; }
static public class Real extends BasicType {
/** "real" -> BasicType {cons("Real")} */
	public Real(INode node) {
		this.node = node;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitBasicTypeReal(this);
	}

	public boolean isReal() { return true; }	
} 
public boolean isString() { return false; }
static public class String extends BasicType {
/** "str" -> BasicType {cons("String")} */
	public String(INode node) {
		this.node = node;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitBasicTypeString(this);
	}

	public boolean isString() { return true; }	
} 
public boolean isValue() { return false; }
static public class Value extends BasicType {
/** "value" -> BasicType {cons("Value")} */
	public Value(INode node) {
		this.node = node;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitBasicTypeValue(this);
	}

	public boolean isValue() { return true; }	
} 
public boolean isNode() { return false; }
static public class Node extends BasicType {
/** "node" -> BasicType {cons("Node")} */
	public Node(INode node) {
		this.node = node;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitBasicTypeNode(this);
	}

	public boolean isNode() { return true; }	
} 
public boolean isVoid() { return false; }
static public class Void extends BasicType {
/** "void" -> BasicType {cons("Void")} */
	public Void(INode node) {
		this.node = node;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitBasicTypeVoid(this);
	}

	public boolean isVoid() { return true; }	
} 
public boolean isLoc() { return false; }
static public class Loc extends BasicType {
/** "loc" -> BasicType {cons("Loc")} */
	public Loc(INode node) {
		this.node = node;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitBasicTypeLoc(this);
	}

	public boolean isLoc() { return true; }	
} 
public boolean isList() { return false; }
static public class List extends BasicType {
/** "list" -> BasicType {cons("List")} */
	public List(INode node) {
		this.node = node;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitBasicTypeList(this);
	}

	public boolean isList() { return true; }	
} 
public boolean isSet() { return false; }
static public class Set extends BasicType {
/** "set" -> BasicType {cons("Set")} */
	public Set(INode node) {
		this.node = node;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitBasicTypeSet(this);
	}

	public boolean isSet() { return true; }	
} 
public boolean isBag() { return false; }
static public class Bag extends BasicType {
/** "bag" -> BasicType {cons("Bag")} */
	public Bag(INode node) {
		this.node = node;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitBasicTypeBag(this);
	}

	public boolean isBag() { return true; }	
} 
public boolean isMap() { return false; }
static public class Map extends BasicType {
/** "map" -> BasicType {cons("Map")} */
	public Map(INode node) {
		this.node = node;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitBasicTypeMap(this);
	}

	public boolean isMap() { return true; }	
} 
public boolean isRelation() { return false; }
static public class Relation extends BasicType {
/** "rel" -> BasicType {cons("Relation")} */
	public Relation(INode node) {
		this.node = node;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitBasicTypeRelation(this);
	}

	public boolean isRelation() { return true; }	
} 
public boolean isTuple() { return false; }
static public class Tuple extends BasicType {
/** "tuple" -> BasicType {cons("Tuple")} */
	public Tuple(INode node) {
		this.node = node;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitBasicTypeTuple(this);
	}

	public boolean isTuple() { return true; }	
} 
public boolean isLex() { return false; }
static public class Lex extends BasicType {
/** "lex" -> BasicType {cons("Lex")} */
	public Lex(INode node) {
		this.node = node;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitBasicTypeLex(this);
	}

	public boolean isLex() { return true; }	
} 
public boolean isReifiedType() { return false; }
static public class ReifiedType extends BasicType {
/** "type" -> BasicType {cons("ReifiedType")} */
	public ReifiedType(INode node) {
		this.node = node;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitBasicTypeReifiedType(this);
	}

	public boolean isReifiedType() { return true; }	
} 
public boolean isReifiedAdt() { return false; }
static public class ReifiedAdt extends BasicType {
/** "adt" -> BasicType {cons("ReifiedAdt")} */
	public ReifiedAdt(INode node) {
		this.node = node;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitBasicTypeReifiedAdt(this);
	}

	public boolean isReifiedAdt() { return true; }	
} 
public boolean isReifiedConstructor() { return false; }
static public class ReifiedConstructor extends BasicType {
/** "constructor" -> BasicType {cons("ReifiedConstructor")} */
	public ReifiedConstructor(INode node) {
		this.node = node;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitBasicTypeReifiedConstructor(this);
	}

	public boolean isReifiedConstructor() { return true; }	
} 
public boolean isReifiedFunction() { return false; }
static public class ReifiedFunction extends BasicType {
/** "fun" -> BasicType {cons("ReifiedFunction")} */
	public ReifiedFunction(INode node) {
		this.node = node;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitBasicTypeReifiedFunction(this);
	}

	public boolean isReifiedFunction() { return true; }	
} 
public boolean isReifiedNonTerminal() { return false; }
static public class ReifiedNonTerminal extends BasicType {
/** "non-terminal" -> BasicType {cons("ReifiedNonTerminal")} */
	public ReifiedNonTerminal(INode node) {
		this.node = node;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitBasicTypeReifiedNonTerminal(this);
	}

	public boolean isReifiedNonTerminal() { return true; }	
} 
public boolean isReifiedReifiedType() { return false; }
static public class ReifiedReifiedType extends BasicType {
/** "reified" -> BasicType {cons("ReifiedReifiedType")} */
	public ReifiedReifiedType(INode node) {
		this.node = node;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitBasicTypeReifiedReifiedType(this);
	}

	public boolean isReifiedReifiedType() { return true; }	
} 
public boolean isDateTime() { return false; }
static public class DateTime extends BasicType {
/** "datetime" -> BasicType {cons("DateTime")} */
	public DateTime(INode node) {
		this.node = node;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitBasicTypeDateTime(this);
	}

	public boolean isDateTime() { return true; }	
}
}