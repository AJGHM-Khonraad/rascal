
package org.rascalmpl.ast;


import org.eclipse.imp.pdb.facts.INode;


public abstract class BasicType extends AbstractAST {
  public BasicType(INode node) {
    super(node);
  }
  


static public class Ambiguity extends BasicType {
  private final java.util.List<org.rascalmpl.ast.BasicType> alternatives;

  public Ambiguity(INode node, java.util.List<org.rascalmpl.ast.BasicType> alternatives) {
    super(node);
    this.alternatives = java.util.Collections.unmodifiableList(alternatives);
  }

  public java.util.List<org.rascalmpl.ast.BasicType> getAlternatives() {
   return alternatives;
  }

  public <T> T accept(IASTVisitor<T> v) {
	return v.visitBasicTypeAmbiguity(this);
  }
}





  public boolean isMap() {
    return false;
  }
  
static public class Map extends BasicType {
  // Production: sig("Map",[])

  

  
public Map(INode node ) {
  super(node);
  
}


  @Override
  public boolean isMap() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitBasicTypeMap(this);
  }
  
  	
}


  public boolean isRelation() {
    return false;
  }
  
static public class Relation extends BasicType {
  // Production: sig("Relation",[])

  

  
public Relation(INode node ) {
  super(node);
  
}


  @Override
  public boolean isRelation() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitBasicTypeRelation(this);
  }
  
  	
}


  public boolean isReal() {
    return false;
  }
  
static public class Real extends BasicType {
  // Production: sig("Real",[])

  

  
public Real(INode node ) {
  super(node);
  
}


  @Override
  public boolean isReal() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitBasicTypeReal(this);
  }
  
  	
}


  public boolean isList() {
    return false;
  }
  
static public class List extends BasicType {
  // Production: sig("List",[])

  

  
public List(INode node ) {
  super(node);
  
}


  @Override
  public boolean isList() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitBasicTypeList(this);
  }
  
  	
}


  public boolean isLex() {
    return false;
  }
  
static public class Lex extends BasicType {
  // Production: sig("Lex",[])

  

  
public Lex(INode node ) {
  super(node);
  
}


  @Override
  public boolean isLex() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitBasicTypeLex(this);
  }
  
  	
}


  public boolean isReifiedAdt() {
    return false;
  }
  
static public class ReifiedAdt extends BasicType {
  // Production: sig("ReifiedAdt",[])

  

  
public ReifiedAdt(INode node ) {
  super(node);
  
}


  @Override
  public boolean isReifiedAdt() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitBasicTypeReifiedAdt(this);
  }
  
  	
}


  public boolean isReifiedReifiedType() {
    return false;
  }
  
static public class ReifiedReifiedType extends BasicType {
  // Production: sig("ReifiedReifiedType",[])

  

  
public ReifiedReifiedType(INode node ) {
  super(node);
  
}


  @Override
  public boolean isReifiedReifiedType() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitBasicTypeReifiedReifiedType(this);
  }
  
  	
}


  public boolean isDateTime() {
    return false;
  }
  
static public class DateTime extends BasicType {
  // Production: sig("DateTime",[])

  

  
public DateTime(INode node ) {
  super(node);
  
}


  @Override
  public boolean isDateTime() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitBasicTypeDateTime(this);
  }
  
  	
}


  public boolean isVoid() {
    return false;
  }
  
static public class Void extends BasicType {
  // Production: sig("Void",[])

  

  
public Void(INode node ) {
  super(node);
  
}


  @Override
  public boolean isVoid() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitBasicTypeVoid(this);
  }
  
  	
}


  public boolean isReifiedTypeParameter() {
    return false;
  }
  
static public class ReifiedTypeParameter extends BasicType {
  // Production: sig("ReifiedTypeParameter",[])

  

  
public ReifiedTypeParameter(INode node ) {
  super(node);
  
}


  @Override
  public boolean isReifiedTypeParameter() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitBasicTypeReifiedTypeParameter(this);
  }
  
  	
}


  public boolean isReifiedFunction() {
    return false;
  }
  
static public class ReifiedFunction extends BasicType {
  // Production: sig("ReifiedFunction",[])

  

  
public ReifiedFunction(INode node ) {
  super(node);
  
}


  @Override
  public boolean isReifiedFunction() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitBasicTypeReifiedFunction(this);
  }
  
  	
}


  public boolean isString() {
    return false;
  }
  
static public class String extends BasicType {
  // Production: sig("String",[])

  

  
public String(INode node ) {
  super(node);
  
}


  @Override
  public boolean isString() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitBasicTypeString(this);
  }
  
  	
}


  public boolean isReifiedNonTerminal() {
    return false;
  }
  
static public class ReifiedNonTerminal extends BasicType {
  // Production: sig("ReifiedNonTerminal",[])

  

  
public ReifiedNonTerminal(INode node ) {
  super(node);
  
}


  @Override
  public boolean isReifiedNonTerminal() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitBasicTypeReifiedNonTerminal(this);
  }
  
  	
}


  public boolean isValue() {
    return false;
  }
  
static public class Value extends BasicType {
  // Production: sig("Value",[])

  

  
public Value(INode node ) {
  super(node);
  
}


  @Override
  public boolean isValue() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitBasicTypeValue(this);
  }
  
  	
}


  public boolean isReifiedType() {
    return false;
  }
  
static public class ReifiedType extends BasicType {
  // Production: sig("ReifiedType",[])

  

  
public ReifiedType(INode node ) {
  super(node);
  
}


  @Override
  public boolean isReifiedType() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitBasicTypeReifiedType(this);
  }
  
  	
}


  public boolean isInt() {
    return false;
  }
  
static public class Int extends BasicType {
  // Production: sig("Int",[])

  

  
public Int(INode node ) {
  super(node);
  
}


  @Override
  public boolean isInt() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitBasicTypeInt(this);
  }
  
  	
}


  public boolean isBag() {
    return false;
  }
  
static public class Bag extends BasicType {
  // Production: sig("Bag",[])

  

  
public Bag(INode node ) {
  super(node);
  
}


  @Override
  public boolean isBag() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitBasicTypeBag(this);
  }
  
  	
}


  public boolean isTuple() {
    return false;
  }
  
static public class Tuple extends BasicType {
  // Production: sig("Tuple",[])

  

  
public Tuple(INode node ) {
  super(node);
  
}


  @Override
  public boolean isTuple() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitBasicTypeTuple(this);
  }
  
  	
}


  public boolean isBool() {
    return false;
  }
  
static public class Bool extends BasicType {
  // Production: sig("Bool",[])

  

  
public Bool(INode node ) {
  super(node);
  
}


  @Override
  public boolean isBool() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitBasicTypeBool(this);
  }
  
  	
}


  public boolean isNum() {
    return false;
  }
  
static public class Num extends BasicType {
  // Production: sig("Num",[])

  

  
public Num(INode node ) {
  super(node);
  
}


  @Override
  public boolean isNum() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitBasicTypeNum(this);
  }
  
  	
}


  public boolean isLoc() {
    return false;
  }
  
static public class Loc extends BasicType {
  // Production: sig("Loc",[])

  

  
public Loc(INode node ) {
  super(node);
  
}


  @Override
  public boolean isLoc() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitBasicTypeLoc(this);
  }
  
  	
}


  public boolean isSet() {
    return false;
  }
  
static public class Set extends BasicType {
  // Production: sig("Set",[])

  

  
public Set(INode node ) {
  super(node);
  
}


  @Override
  public boolean isSet() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitBasicTypeSet(this);
  }
  
  	
}


  public boolean isReifiedConstructor() {
    return false;
  }
  
static public class ReifiedConstructor extends BasicType {
  // Production: sig("ReifiedConstructor",[])

  

  
public ReifiedConstructor(INode node ) {
  super(node);
  
}


  @Override
  public boolean isReifiedConstructor() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitBasicTypeReifiedConstructor(this);
  }
  
  	
}


  public boolean isNode() {
    return false;
  }
  
static public class Node extends BasicType {
  // Production: sig("Node",[])

  

  
public Node(INode node ) {
  super(node);
  
}


  @Override
  public boolean isNode() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitBasicTypeNode(this);
  }
  
  	
}



}
