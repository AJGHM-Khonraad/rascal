/*******************************************************************************
 * Copyright (c) 2009-2015 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   * Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI
 *   * Tijs van der Storm - Tijs.van.der.Storm@cwi.nl
 *   * Paul Klint - Paul.Klint@cwi.nl - CWI
 *   * Mark Hills - Mark.Hills@cwi.nl (CWI)
 *   * Arnold Lankamp - Arnold.Lankamp@cwi.nl
 *   * Michael Steindorfer - Michael.Steindorfer@cwi.nl - CWI
 *******************************************************************************/
package org.rascalmpl.ast;


import org.eclipse.imp.pdb.facts.IConstructor;

public abstract class Sym extends AbstractAST {
  public Sym(IConstructor node) {
    super();
  }

  
  public boolean hasAlternatives() {
    return false;
  }

  public java.util.List<org.rascalmpl.ast.Sym> getAlternatives() {
    throw new UnsupportedOperationException();
  }
  public boolean hasParameters() {
    return false;
  }

  public java.util.List<org.rascalmpl.ast.Sym> getParameters() {
    throw new UnsupportedOperationException();
  }
  public boolean hasSequence() {
    return false;
  }

  public java.util.List<org.rascalmpl.ast.Sym> getSequence() {
    throw new UnsupportedOperationException();
  }
  public boolean hasCistring() {
    return false;
  }

  public org.rascalmpl.ast.CaseInsensitiveStringConstant getCistring() {
    throw new UnsupportedOperationException();
  }
  public boolean hasCharClass() {
    return false;
  }

  public org.rascalmpl.ast.Class getCharClass() {
    throw new UnsupportedOperationException();
  }
  public boolean hasColumn() {
    return false;
  }

  public org.rascalmpl.ast.IntegerLiteral getColumn() {
    throw new UnsupportedOperationException();
  }
  public boolean hasNonterminal() {
    return false;
  }

  public org.rascalmpl.ast.Nonterminal getNonterminal() {
    throw new UnsupportedOperationException();
  }
  public boolean hasLabel() {
    return false;
  }

  public org.rascalmpl.ast.NonterminalLabel getLabel() {
    throw new UnsupportedOperationException();
  }
  public boolean hasString() {
    return false;
  }

  public org.rascalmpl.ast.StringConstant getString() {
    throw new UnsupportedOperationException();
  }
  public boolean hasFirst() {
    return false;
  }

  public org.rascalmpl.ast.Sym getFirst() {
    throw new UnsupportedOperationException();
  }
  public boolean hasMatch() {
    return false;
  }

  public org.rascalmpl.ast.Sym getMatch() {
    throw new UnsupportedOperationException();
  }
  public boolean hasSep() {
    return false;
  }

  public org.rascalmpl.ast.Sym getSep() {
    throw new UnsupportedOperationException();
  }
  public boolean hasSymbol() {
    return false;
  }

  public org.rascalmpl.ast.Sym getSymbol() {
    throw new UnsupportedOperationException();
  }

  

  
  public boolean isAlternative() {
    return false;
  }

  static public class Alternative extends Sym {
    // Production: sig("Alternative",[arg("org.rascalmpl.ast.Sym","first"),arg("java.util.List\<org.rascalmpl.ast.Sym\>","alternatives")])
  
    
    private final org.rascalmpl.ast.Sym first;
    private final java.util.List<org.rascalmpl.ast.Sym> alternatives;
  
    public Alternative(IConstructor node , org.rascalmpl.ast.Sym first,  java.util.List<org.rascalmpl.ast.Sym> alternatives) {
      super(node);
      
      this.first = first;
      this.alternatives = alternatives;
    }
  
    @Override
    public boolean isAlternative() { 
      return true; 
    }
  
    @Override
    public <T> T accept(IASTVisitor<T> visitor) {
      return visitor.visitSymAlternative(this);
    }
  
    @Override
    public boolean equals(Object o) {
      if (!(o instanceof Alternative)) {
        return false;
      }        
      Alternative tmp = (Alternative) o;
      return true && tmp.first.equals(this.first) && tmp.alternatives.equals(this.alternatives) ; 
    }
   
    @Override
    public int hashCode() {
      return 991 + 739 * first.hashCode() + 409 * alternatives.hashCode() ; 
    } 
  
    
    @Override
    public org.rascalmpl.ast.Sym getFirst() {
      return this.first;
    }
  
    @Override
    public boolean hasFirst() {
      return true;
    }
    @Override
    public java.util.List<org.rascalmpl.ast.Sym> getAlternatives() {
      return this.alternatives;
    }
  
    @Override
    public boolean hasAlternatives() {
      return true;
    }	
  
    @Override
    public Object clone()  {
      return newInstance(getClass(), (IConstructor) null , clone(first), clone(alternatives));
    }
  }
  public boolean isCaseInsensitiveLiteral() {
    return false;
  }

  static public class CaseInsensitiveLiteral extends Sym {
    // Production: sig("CaseInsensitiveLiteral",[arg("org.rascalmpl.ast.CaseInsensitiveStringConstant","cistring")])
  
    
    private final org.rascalmpl.ast.CaseInsensitiveStringConstant cistring;
  
    public CaseInsensitiveLiteral(IConstructor node , org.rascalmpl.ast.CaseInsensitiveStringConstant cistring) {
      super(node);
      
      this.cistring = cistring;
    }
  
    @Override
    public boolean isCaseInsensitiveLiteral() { 
      return true; 
    }
  
    @Override
    public <T> T accept(IASTVisitor<T> visitor) {
      return visitor.visitSymCaseInsensitiveLiteral(this);
    }
  
    @Override
    public boolean equals(Object o) {
      if (!(o instanceof CaseInsensitiveLiteral)) {
        return false;
      }        
      CaseInsensitiveLiteral tmp = (CaseInsensitiveLiteral) o;
      return true && tmp.cistring.equals(this.cistring) ; 
    }
   
    @Override
    public int hashCode() {
      return 2 + 139 * cistring.hashCode() ; 
    } 
  
    
    @Override
    public org.rascalmpl.ast.CaseInsensitiveStringConstant getCistring() {
      return this.cistring;
    }
  
    @Override
    public boolean hasCistring() {
      return true;
    }	
  
    @Override
    public Object clone()  {
      return newInstance(getClass(), (IConstructor) null , clone(cistring));
    }
  }
  public boolean isCharacterClass() {
    return false;
  }

  static public class CharacterClass extends Sym {
    // Production: sig("CharacterClass",[arg("org.rascalmpl.ast.Class","charClass")])
  
    
    private final org.rascalmpl.ast.Class charClass;
  
    public CharacterClass(IConstructor node , org.rascalmpl.ast.Class charClass) {
      super(node);
      
      this.charClass = charClass;
    }
  
    @Override
    public boolean isCharacterClass() { 
      return true; 
    }
  
    @Override
    public <T> T accept(IASTVisitor<T> visitor) {
      return visitor.visitSymCharacterClass(this);
    }
  
    @Override
    public boolean equals(Object o) {
      if (!(o instanceof CharacterClass)) {
        return false;
      }        
      CharacterClass tmp = (CharacterClass) o;
      return true && tmp.charClass.equals(this.charClass) ; 
    }
   
    @Override
    public int hashCode() {
      return 743 + 61 * charClass.hashCode() ; 
    } 
  
    
    @Override
    public org.rascalmpl.ast.Class getCharClass() {
      return this.charClass;
    }
  
    @Override
    public boolean hasCharClass() {
      return true;
    }	
  
    @Override
    public Object clone()  {
      return newInstance(getClass(), (IConstructor) null , clone(charClass));
    }
  }
  public boolean isColumn() {
    return false;
  }

  static public class Column extends Sym {
    // Production: sig("Column",[arg("org.rascalmpl.ast.Sym","symbol"),arg("org.rascalmpl.ast.IntegerLiteral","column")])
  
    
    private final org.rascalmpl.ast.Sym symbol;
    private final org.rascalmpl.ast.IntegerLiteral column;
  
    public Column(IConstructor node , org.rascalmpl.ast.Sym symbol,  org.rascalmpl.ast.IntegerLiteral column) {
      super(node);
      
      this.symbol = symbol;
      this.column = column;
    }
  
    @Override
    public boolean isColumn() { 
      return true; 
    }
  
    @Override
    public <T> T accept(IASTVisitor<T> visitor) {
      return visitor.visitSymColumn(this);
    }
  
    @Override
    public boolean equals(Object o) {
      if (!(o instanceof Column)) {
        return false;
      }        
      Column tmp = (Column) o;
      return true && tmp.symbol.equals(this.symbol) && tmp.column.equals(this.column) ; 
    }
   
    @Override
    public int hashCode() {
      return 773 + 577 * symbol.hashCode() + 541 * column.hashCode() ; 
    } 
  
    
    @Override
    public org.rascalmpl.ast.Sym getSymbol() {
      return this.symbol;
    }
  
    @Override
    public boolean hasSymbol() {
      return true;
    }
    @Override
    public org.rascalmpl.ast.IntegerLiteral getColumn() {
      return this.column;
    }
  
    @Override
    public boolean hasColumn() {
      return true;
    }	
  
    @Override
    public Object clone()  {
      return newInstance(getClass(), (IConstructor) null , clone(symbol), clone(column));
    }
  }
  public boolean isEmpty() {
    return false;
  }

  static public class Empty extends Sym {
    // Production: sig("Empty",[])
  
    
  
    public Empty(IConstructor node ) {
      super(node);
      
    }
  
    @Override
    public boolean isEmpty() { 
      return true; 
    }
  
    @Override
    public <T> T accept(IASTVisitor<T> visitor) {
      return visitor.visitSymEmpty(this);
    }
  
    @Override
    public boolean equals(Object o) {
      if (!(o instanceof Empty)) {
        return false;
      }        
      Empty tmp = (Empty) o;
      return true ; 
    }
   
    @Override
    public int hashCode() {
      return 367 ; 
    } 
  
    	
  
    @Override
    public Object clone()  {
      return newInstance(getClass(), (IConstructor) null );
    }
  }
  public boolean isEndOfLine() {
    return false;
  }

  static public class EndOfLine extends Sym {
    // Production: sig("EndOfLine",[arg("org.rascalmpl.ast.Sym","symbol")])
  
    
    private final org.rascalmpl.ast.Sym symbol;
  
    public EndOfLine(IConstructor node , org.rascalmpl.ast.Sym symbol) {
      super(node);
      
      this.symbol = symbol;
    }
  
    @Override
    public boolean isEndOfLine() { 
      return true; 
    }
  
    @Override
    public <T> T accept(IASTVisitor<T> visitor) {
      return visitor.visitSymEndOfLine(this);
    }
  
    @Override
    public boolean equals(Object o) {
      if (!(o instanceof EndOfLine)) {
        return false;
      }        
      EndOfLine tmp = (EndOfLine) o;
      return true && tmp.symbol.equals(this.symbol) ; 
    }
   
    @Override
    public int hashCode() {
      return 479 + 487 * symbol.hashCode() ; 
    } 
  
    
    @Override
    public org.rascalmpl.ast.Sym getSymbol() {
      return this.symbol;
    }
  
    @Override
    public boolean hasSymbol() {
      return true;
    }	
  
    @Override
    public Object clone()  {
      return newInstance(getClass(), (IConstructor) null , clone(symbol));
    }
  }
  public boolean isExcept() {
    return false;
  }

  static public class Except extends Sym {
    // Production: sig("Except",[arg("org.rascalmpl.ast.Sym","symbol"),arg("org.rascalmpl.ast.NonterminalLabel","label")])
  
    
    private final org.rascalmpl.ast.Sym symbol;
    private final org.rascalmpl.ast.NonterminalLabel label;
  
    public Except(IConstructor node , org.rascalmpl.ast.Sym symbol,  org.rascalmpl.ast.NonterminalLabel label) {
      super(node);
      
      this.symbol = symbol;
      this.label = label;
    }
  
    @Override
    public boolean isExcept() { 
      return true; 
    }
  
    @Override
    public <T> T accept(IASTVisitor<T> visitor) {
      return visitor.visitSymExcept(this);
    }
  
    @Override
    public boolean equals(Object o) {
      if (!(o instanceof Except)) {
        return false;
      }        
      Except tmp = (Except) o;
      return true && tmp.symbol.equals(this.symbol) && tmp.label.equals(this.label) ; 
    }
   
    @Override
    public int hashCode() {
      return 601 + 937 * symbol.hashCode() + 773 * label.hashCode() ; 
    } 
  
    
    @Override
    public org.rascalmpl.ast.Sym getSymbol() {
      return this.symbol;
    }
  
    @Override
    public boolean hasSymbol() {
      return true;
    }
    @Override
    public org.rascalmpl.ast.NonterminalLabel getLabel() {
      return this.label;
    }
  
    @Override
    public boolean hasLabel() {
      return true;
    }	
  
    @Override
    public Object clone()  {
      return newInstance(getClass(), (IConstructor) null , clone(symbol), clone(label));
    }
  }
  public boolean isFollow() {
    return false;
  }

  static public class Follow extends Sym {
    // Production: sig("Follow",[arg("org.rascalmpl.ast.Sym","symbol"),arg("org.rascalmpl.ast.Sym","match")])
  
    
    private final org.rascalmpl.ast.Sym symbol;
    private final org.rascalmpl.ast.Sym match;
  
    public Follow(IConstructor node , org.rascalmpl.ast.Sym symbol,  org.rascalmpl.ast.Sym match) {
      super(node);
      
      this.symbol = symbol;
      this.match = match;
    }
  
    @Override
    public boolean isFollow() { 
      return true; 
    }
  
    @Override
    public <T> T accept(IASTVisitor<T> visitor) {
      return visitor.visitSymFollow(this);
    }
  
    @Override
    public boolean equals(Object o) {
      if (!(o instanceof Follow)) {
        return false;
      }        
      Follow tmp = (Follow) o;
      return true && tmp.symbol.equals(this.symbol) && tmp.match.equals(this.match) ; 
    }
   
    @Override
    public int hashCode() {
      return 571 + 683 * symbol.hashCode() + 433 * match.hashCode() ; 
    } 
  
    
    @Override
    public org.rascalmpl.ast.Sym getSymbol() {
      return this.symbol;
    }
  
    @Override
    public boolean hasSymbol() {
      return true;
    }
    @Override
    public org.rascalmpl.ast.Sym getMatch() {
      return this.match;
    }
  
    @Override
    public boolean hasMatch() {
      return true;
    }	
  
    @Override
    public Object clone()  {
      return newInstance(getClass(), (IConstructor) null , clone(symbol), clone(match));
    }
  }
  public boolean isIter() {
    return false;
  }

  static public class Iter extends Sym {
    // Production: sig("Iter",[arg("org.rascalmpl.ast.Sym","symbol")])
  
    
    private final org.rascalmpl.ast.Sym symbol;
  
    public Iter(IConstructor node , org.rascalmpl.ast.Sym symbol) {
      super(node);
      
      this.symbol = symbol;
    }
  
    @Override
    public boolean isIter() { 
      return true; 
    }
  
    @Override
    public <T> T accept(IASTVisitor<T> visitor) {
      return visitor.visitSymIter(this);
    }
  
    @Override
    public boolean equals(Object o) {
      if (!(o instanceof Iter)) {
        return false;
      }        
      Iter tmp = (Iter) o;
      return true && tmp.symbol.equals(this.symbol) ; 
    }
   
    @Override
    public int hashCode() {
      return 691 + 563 * symbol.hashCode() ; 
    } 
  
    
    @Override
    public org.rascalmpl.ast.Sym getSymbol() {
      return this.symbol;
    }
  
    @Override
    public boolean hasSymbol() {
      return true;
    }	
  
    @Override
    public Object clone()  {
      return newInstance(getClass(), (IConstructor) null , clone(symbol));
    }
  }
  public boolean isIterSep() {
    return false;
  }

  static public class IterSep extends Sym {
    // Production: sig("IterSep",[arg("org.rascalmpl.ast.Sym","symbol"),arg("org.rascalmpl.ast.Sym","sep")])
  
    
    private final org.rascalmpl.ast.Sym symbol;
    private final org.rascalmpl.ast.Sym sep;
  
    public IterSep(IConstructor node , org.rascalmpl.ast.Sym symbol,  org.rascalmpl.ast.Sym sep) {
      super(node);
      
      this.symbol = symbol;
      this.sep = sep;
    }
  
    @Override
    public boolean isIterSep() { 
      return true; 
    }
  
    @Override
    public <T> T accept(IASTVisitor<T> visitor) {
      return visitor.visitSymIterSep(this);
    }
  
    @Override
    public boolean equals(Object o) {
      if (!(o instanceof IterSep)) {
        return false;
      }        
      IterSep tmp = (IterSep) o;
      return true && tmp.symbol.equals(this.symbol) && tmp.sep.equals(this.sep) ; 
    }
   
    @Override
    public int hashCode() {
      return 601 + 199 * symbol.hashCode() + 137 * sep.hashCode() ; 
    } 
  
    
    @Override
    public org.rascalmpl.ast.Sym getSymbol() {
      return this.symbol;
    }
  
    @Override
    public boolean hasSymbol() {
      return true;
    }
    @Override
    public org.rascalmpl.ast.Sym getSep() {
      return this.sep;
    }
  
    @Override
    public boolean hasSep() {
      return true;
    }	
  
    @Override
    public Object clone()  {
      return newInstance(getClass(), (IConstructor) null , clone(symbol), clone(sep));
    }
  }
  public boolean isIterStar() {
    return false;
  }

  static public class IterStar extends Sym {
    // Production: sig("IterStar",[arg("org.rascalmpl.ast.Sym","symbol")])
  
    
    private final org.rascalmpl.ast.Sym symbol;
  
    public IterStar(IConstructor node , org.rascalmpl.ast.Sym symbol) {
      super(node);
      
      this.symbol = symbol;
    }
  
    @Override
    public boolean isIterStar() { 
      return true; 
    }
  
    @Override
    public <T> T accept(IASTVisitor<T> visitor) {
      return visitor.visitSymIterStar(this);
    }
  
    @Override
    public boolean equals(Object o) {
      if (!(o instanceof IterStar)) {
        return false;
      }        
      IterStar tmp = (IterStar) o;
      return true && tmp.symbol.equals(this.symbol) ; 
    }
   
    @Override
    public int hashCode() {
      return 751 + 419 * symbol.hashCode() ; 
    } 
  
    
    @Override
    public org.rascalmpl.ast.Sym getSymbol() {
      return this.symbol;
    }
  
    @Override
    public boolean hasSymbol() {
      return true;
    }	
  
    @Override
    public Object clone()  {
      return newInstance(getClass(), (IConstructor) null , clone(symbol));
    }
  }
  public boolean isIterStarSep() {
    return false;
  }

  static public class IterStarSep extends Sym {
    // Production: sig("IterStarSep",[arg("org.rascalmpl.ast.Sym","symbol"),arg("org.rascalmpl.ast.Sym","sep")])
  
    
    private final org.rascalmpl.ast.Sym symbol;
    private final org.rascalmpl.ast.Sym sep;
  
    public IterStarSep(IConstructor node , org.rascalmpl.ast.Sym symbol,  org.rascalmpl.ast.Sym sep) {
      super(node);
      
      this.symbol = symbol;
      this.sep = sep;
    }
  
    @Override
    public boolean isIterStarSep() { 
      return true; 
    }
  
    @Override
    public <T> T accept(IASTVisitor<T> visitor) {
      return visitor.visitSymIterStarSep(this);
    }
  
    @Override
    public boolean equals(Object o) {
      if (!(o instanceof IterStarSep)) {
        return false;
      }        
      IterStarSep tmp = (IterStarSep) o;
      return true && tmp.symbol.equals(this.symbol) && tmp.sep.equals(this.sep) ; 
    }
   
    @Override
    public int hashCode() {
      return 283 + 149 * symbol.hashCode() + 853 * sep.hashCode() ; 
    } 
  
    
    @Override
    public org.rascalmpl.ast.Sym getSymbol() {
      return this.symbol;
    }
  
    @Override
    public boolean hasSymbol() {
      return true;
    }
    @Override
    public org.rascalmpl.ast.Sym getSep() {
      return this.sep;
    }
  
    @Override
    public boolean hasSep() {
      return true;
    }	
  
    @Override
    public Object clone()  {
      return newInstance(getClass(), (IConstructor) null , clone(symbol), clone(sep));
    }
  }
  public boolean isLabeled() {
    return false;
  }

  static public class Labeled extends Sym {
    // Production: sig("Labeled",[arg("org.rascalmpl.ast.Sym","symbol"),arg("org.rascalmpl.ast.NonterminalLabel","label")])
  
    
    private final org.rascalmpl.ast.Sym symbol;
    private final org.rascalmpl.ast.NonterminalLabel label;
  
    public Labeled(IConstructor node , org.rascalmpl.ast.Sym symbol,  org.rascalmpl.ast.NonterminalLabel label) {
      super(node);
      
      this.symbol = symbol;
      this.label = label;
    }
  
    @Override
    public boolean isLabeled() { 
      return true; 
    }
  
    @Override
    public <T> T accept(IASTVisitor<T> visitor) {
      return visitor.visitSymLabeled(this);
    }
  
    @Override
    public boolean equals(Object o) {
      if (!(o instanceof Labeled)) {
        return false;
      }        
      Labeled tmp = (Labeled) o;
      return true && tmp.symbol.equals(this.symbol) && tmp.label.equals(this.label) ; 
    }
   
    @Override
    public int hashCode() {
      return 311 + 673 * symbol.hashCode() + 479 * label.hashCode() ; 
    } 
  
    
    @Override
    public org.rascalmpl.ast.Sym getSymbol() {
      return this.symbol;
    }
  
    @Override
    public boolean hasSymbol() {
      return true;
    }
    @Override
    public org.rascalmpl.ast.NonterminalLabel getLabel() {
      return this.label;
    }
  
    @Override
    public boolean hasLabel() {
      return true;
    }	
  
    @Override
    public Object clone()  {
      return newInstance(getClass(), (IConstructor) null , clone(symbol), clone(label));
    }
  }
  public boolean isLiteral() {
    return false;
  }

  static public class Literal extends Sym {
    // Production: sig("Literal",[arg("org.rascalmpl.ast.StringConstant","string")])
  
    
    private final org.rascalmpl.ast.StringConstant string;
  
    public Literal(IConstructor node , org.rascalmpl.ast.StringConstant string) {
      super(node);
      
      this.string = string;
    }
  
    @Override
    public boolean isLiteral() { 
      return true; 
    }
  
    @Override
    public <T> T accept(IASTVisitor<T> visitor) {
      return visitor.visitSymLiteral(this);
    }
  
    @Override
    public boolean equals(Object o) {
      if (!(o instanceof Literal)) {
        return false;
      }        
      Literal tmp = (Literal) o;
      return true && tmp.string.equals(this.string) ; 
    }
   
    @Override
    public int hashCode() {
      return 709 + 619 * string.hashCode() ; 
    } 
  
    
    @Override
    public org.rascalmpl.ast.StringConstant getString() {
      return this.string;
    }
  
    @Override
    public boolean hasString() {
      return true;
    }	
  
    @Override
    public Object clone()  {
      return newInstance(getClass(), (IConstructor) null , clone(string));
    }
  }
  public boolean isNonterminal() {
    return false;
  }

  static public class Nonterminal extends Sym {
    // Production: sig("Nonterminal",[arg("org.rascalmpl.ast.Nonterminal","nonterminal")])
  
    
    private final org.rascalmpl.ast.Nonterminal nonterminal;
  
    public Nonterminal(IConstructor node , org.rascalmpl.ast.Nonterminal nonterminal) {
      super(node);
      
      this.nonterminal = nonterminal;
    }
  
    @Override
    public boolean isNonterminal() { 
      return true; 
    }
  
    @Override
    public <T> T accept(IASTVisitor<T> visitor) {
      return visitor.visitSymNonterminal(this);
    }
  
    @Override
    public boolean equals(Object o) {
      if (!(o instanceof Nonterminal)) {
        return false;
      }        
      Nonterminal tmp = (Nonterminal) o;
      return true && tmp.nonterminal.equals(this.nonterminal) ; 
    }
   
    @Override
    public int hashCode() {
      return 503 + 241 * nonterminal.hashCode() ; 
    } 
  
    
    @Override
    public org.rascalmpl.ast.Nonterminal getNonterminal() {
      return this.nonterminal;
    }
  
    @Override
    public boolean hasNonterminal() {
      return true;
    }	
  
    @Override
    public Object clone()  {
      return newInstance(getClass(), (IConstructor) null , clone(nonterminal));
    }
  }
  public boolean isNotFollow() {
    return false;
  }

  static public class NotFollow extends Sym {
    // Production: sig("NotFollow",[arg("org.rascalmpl.ast.Sym","symbol"),arg("org.rascalmpl.ast.Sym","match")])
  
    
    private final org.rascalmpl.ast.Sym symbol;
    private final org.rascalmpl.ast.Sym match;
  
    public NotFollow(IConstructor node , org.rascalmpl.ast.Sym symbol,  org.rascalmpl.ast.Sym match) {
      super(node);
      
      this.symbol = symbol;
      this.match = match;
    }
  
    @Override
    public boolean isNotFollow() { 
      return true; 
    }
  
    @Override
    public <T> T accept(IASTVisitor<T> visitor) {
      return visitor.visitSymNotFollow(this);
    }
  
    @Override
    public boolean equals(Object o) {
      if (!(o instanceof NotFollow)) {
        return false;
      }        
      NotFollow tmp = (NotFollow) o;
      return true && tmp.symbol.equals(this.symbol) && tmp.match.equals(this.match) ; 
    }
   
    @Override
    public int hashCode() {
      return 743 + 401 * symbol.hashCode() + 523 * match.hashCode() ; 
    } 
  
    
    @Override
    public org.rascalmpl.ast.Sym getSymbol() {
      return this.symbol;
    }
  
    @Override
    public boolean hasSymbol() {
      return true;
    }
    @Override
    public org.rascalmpl.ast.Sym getMatch() {
      return this.match;
    }
  
    @Override
    public boolean hasMatch() {
      return true;
    }	
  
    @Override
    public Object clone()  {
      return newInstance(getClass(), (IConstructor) null , clone(symbol), clone(match));
    }
  }
  public boolean isNotPrecede() {
    return false;
  }

  static public class NotPrecede extends Sym {
    // Production: sig("NotPrecede",[arg("org.rascalmpl.ast.Sym","match"),arg("org.rascalmpl.ast.Sym","symbol")])
  
    
    private final org.rascalmpl.ast.Sym match;
    private final org.rascalmpl.ast.Sym symbol;
  
    public NotPrecede(IConstructor node , org.rascalmpl.ast.Sym match,  org.rascalmpl.ast.Sym symbol) {
      super(node);
      
      this.match = match;
      this.symbol = symbol;
    }
  
    @Override
    public boolean isNotPrecede() { 
      return true; 
    }
  
    @Override
    public <T> T accept(IASTVisitor<T> visitor) {
      return visitor.visitSymNotPrecede(this);
    }
  
    @Override
    public boolean equals(Object o) {
      if (!(o instanceof NotPrecede)) {
        return false;
      }        
      NotPrecede tmp = (NotPrecede) o;
      return true && tmp.match.equals(this.match) && tmp.symbol.equals(this.symbol) ; 
    }
   
    @Override
    public int hashCode() {
      return 17 + 599 * match.hashCode() + 53 * symbol.hashCode() ; 
    } 
  
    
    @Override
    public org.rascalmpl.ast.Sym getMatch() {
      return this.match;
    }
  
    @Override
    public boolean hasMatch() {
      return true;
    }
    @Override
    public org.rascalmpl.ast.Sym getSymbol() {
      return this.symbol;
    }
  
    @Override
    public boolean hasSymbol() {
      return true;
    }	
  
    @Override
    public Object clone()  {
      return newInstance(getClass(), (IConstructor) null , clone(match), clone(symbol));
    }
  }
  public boolean isOptional() {
    return false;
  }

  static public class Optional extends Sym {
    // Production: sig("Optional",[arg("org.rascalmpl.ast.Sym","symbol")])
  
    
    private final org.rascalmpl.ast.Sym symbol;
  
    public Optional(IConstructor node , org.rascalmpl.ast.Sym symbol) {
      super(node);
      
      this.symbol = symbol;
    }
  
    @Override
    public boolean isOptional() { 
      return true; 
    }
  
    @Override
    public <T> T accept(IASTVisitor<T> visitor) {
      return visitor.visitSymOptional(this);
    }
  
    @Override
    public boolean equals(Object o) {
      if (!(o instanceof Optional)) {
        return false;
      }        
      Optional tmp = (Optional) o;
      return true && tmp.symbol.equals(this.symbol) ; 
    }
   
    @Override
    public int hashCode() {
      return 541 + 491 * symbol.hashCode() ; 
    } 
  
    
    @Override
    public org.rascalmpl.ast.Sym getSymbol() {
      return this.symbol;
    }
  
    @Override
    public boolean hasSymbol() {
      return true;
    }	
  
    @Override
    public Object clone()  {
      return newInstance(getClass(), (IConstructor) null , clone(symbol));
    }
  }
  public boolean isParameter() {
    return false;
  }

  static public class Parameter extends Sym {
    // Production: sig("Parameter",[arg("org.rascalmpl.ast.Nonterminal","nonterminal")])
  
    
    private final org.rascalmpl.ast.Nonterminal nonterminal;
  
    public Parameter(IConstructor node , org.rascalmpl.ast.Nonterminal nonterminal) {
      super(node);
      
      this.nonterminal = nonterminal;
    }
  
    @Override
    public boolean isParameter() { 
      return true; 
    }
  
    @Override
    public <T> T accept(IASTVisitor<T> visitor) {
      return visitor.visitSymParameter(this);
    }
  
    @Override
    public boolean equals(Object o) {
      if (!(o instanceof Parameter)) {
        return false;
      }        
      Parameter tmp = (Parameter) o;
      return true && tmp.nonterminal.equals(this.nonterminal) ; 
    }
   
    @Override
    public int hashCode() {
      return 569 + 61 * nonterminal.hashCode() ; 
    } 
  
    
    @Override
    public org.rascalmpl.ast.Nonterminal getNonterminal() {
      return this.nonterminal;
    }
  
    @Override
    public boolean hasNonterminal() {
      return true;
    }	
  
    @Override
    public Object clone()  {
      return newInstance(getClass(), (IConstructor) null , clone(nonterminal));
    }
  }
  public boolean isParametrized() {
    return false;
  }

  static public class Parametrized extends Sym {
    // Production: sig("Parametrized",[arg("org.rascalmpl.ast.Nonterminal","nonterminal"),arg("java.util.List\<org.rascalmpl.ast.Sym\>","parameters")])
  
    
    private final org.rascalmpl.ast.Nonterminal nonterminal;
    private final java.util.List<org.rascalmpl.ast.Sym> parameters;
  
    public Parametrized(IConstructor node , org.rascalmpl.ast.Nonterminal nonterminal,  java.util.List<org.rascalmpl.ast.Sym> parameters) {
      super(node);
      
      this.nonterminal = nonterminal;
      this.parameters = parameters;
    }
  
    @Override
    public boolean isParametrized() { 
      return true; 
    }
  
    @Override
    public <T> T accept(IASTVisitor<T> visitor) {
      return visitor.visitSymParametrized(this);
    }
  
    @Override
    public boolean equals(Object o) {
      if (!(o instanceof Parametrized)) {
        return false;
      }        
      Parametrized tmp = (Parametrized) o;
      return true && tmp.nonterminal.equals(this.nonterminal) && tmp.parameters.equals(this.parameters) ; 
    }
   
    @Override
    public int hashCode() {
      return 293 + 223 * nonterminal.hashCode() + 389 * parameters.hashCode() ; 
    } 
  
    
    @Override
    public org.rascalmpl.ast.Nonterminal getNonterminal() {
      return this.nonterminal;
    }
  
    @Override
    public boolean hasNonterminal() {
      return true;
    }
    @Override
    public java.util.List<org.rascalmpl.ast.Sym> getParameters() {
      return this.parameters;
    }
  
    @Override
    public boolean hasParameters() {
      return true;
    }	
  
    @Override
    public Object clone()  {
      return newInstance(getClass(), (IConstructor) null , clone(nonterminal), clone(parameters));
    }
  }
  public boolean isPrecede() {
    return false;
  }

  static public class Precede extends Sym {
    // Production: sig("Precede",[arg("org.rascalmpl.ast.Sym","match"),arg("org.rascalmpl.ast.Sym","symbol")])
  
    
    private final org.rascalmpl.ast.Sym match;
    private final org.rascalmpl.ast.Sym symbol;
  
    public Precede(IConstructor node , org.rascalmpl.ast.Sym match,  org.rascalmpl.ast.Sym symbol) {
      super(node);
      
      this.match = match;
      this.symbol = symbol;
    }
  
    @Override
    public boolean isPrecede() { 
      return true; 
    }
  
    @Override
    public <T> T accept(IASTVisitor<T> visitor) {
      return visitor.visitSymPrecede(this);
    }
  
    @Override
    public boolean equals(Object o) {
      if (!(o instanceof Precede)) {
        return false;
      }        
      Precede tmp = (Precede) o;
      return true && tmp.match.equals(this.match) && tmp.symbol.equals(this.symbol) ; 
    }
   
    @Override
    public int hashCode() {
      return 211 + 907 * match.hashCode() + 569 * symbol.hashCode() ; 
    } 
  
    
    @Override
    public org.rascalmpl.ast.Sym getMatch() {
      return this.match;
    }
  
    @Override
    public boolean hasMatch() {
      return true;
    }
    @Override
    public org.rascalmpl.ast.Sym getSymbol() {
      return this.symbol;
    }
  
    @Override
    public boolean hasSymbol() {
      return true;
    }	
  
    @Override
    public Object clone()  {
      return newInstance(getClass(), (IConstructor) null , clone(match), clone(symbol));
    }
  }
  public boolean isSequence() {
    return false;
  }

  static public class Sequence extends Sym {
    // Production: sig("Sequence",[arg("org.rascalmpl.ast.Sym","first"),arg("java.util.List\<org.rascalmpl.ast.Sym\>","sequence")])
  
    
    private final org.rascalmpl.ast.Sym first;
    private final java.util.List<org.rascalmpl.ast.Sym> sequence;
  
    public Sequence(IConstructor node , org.rascalmpl.ast.Sym first,  java.util.List<org.rascalmpl.ast.Sym> sequence) {
      super(node);
      
      this.first = first;
      this.sequence = sequence;
    }
  
    @Override
    public boolean isSequence() { 
      return true; 
    }
  
    @Override
    public <T> T accept(IASTVisitor<T> visitor) {
      return visitor.visitSymSequence(this);
    }
  
    @Override
    public boolean equals(Object o) {
      if (!(o instanceof Sequence)) {
        return false;
      }        
      Sequence tmp = (Sequence) o;
      return true && tmp.first.equals(this.first) && tmp.sequence.equals(this.sequence) ; 
    }
   
    @Override
    public int hashCode() {
      return 929 + 509 * first.hashCode() + 311 * sequence.hashCode() ; 
    } 
  
    
    @Override
    public org.rascalmpl.ast.Sym getFirst() {
      return this.first;
    }
  
    @Override
    public boolean hasFirst() {
      return true;
    }
    @Override
    public java.util.List<org.rascalmpl.ast.Sym> getSequence() {
      return this.sequence;
    }
  
    @Override
    public boolean hasSequence() {
      return true;
    }	
  
    @Override
    public Object clone()  {
      return newInstance(getClass(), (IConstructor) null , clone(first), clone(sequence));
    }
  }
  public boolean isStart() {
    return false;
  }

  static public class Start extends Sym {
    // Production: sig("Start",[arg("org.rascalmpl.ast.Nonterminal","nonterminal")])
  
    
    private final org.rascalmpl.ast.Nonterminal nonterminal;
  
    public Start(IConstructor node , org.rascalmpl.ast.Nonterminal nonterminal) {
      super(node);
      
      this.nonterminal = nonterminal;
    }
  
    @Override
    public boolean isStart() { 
      return true; 
    }
  
    @Override
    public <T> T accept(IASTVisitor<T> visitor) {
      return visitor.visitSymStart(this);
    }
  
    @Override
    public boolean equals(Object o) {
      if (!(o instanceof Start)) {
        return false;
      }        
      Start tmp = (Start) o;
      return true && tmp.nonterminal.equals(this.nonterminal) ; 
    }
   
    @Override
    public int hashCode() {
      return 61 + 359 * nonterminal.hashCode() ; 
    } 
  
    
    @Override
    public org.rascalmpl.ast.Nonterminal getNonterminal() {
      return this.nonterminal;
    }
  
    @Override
    public boolean hasNonterminal() {
      return true;
    }	
  
    @Override
    public Object clone()  {
      return newInstance(getClass(), (IConstructor) null , clone(nonterminal));
    }
  }
  public boolean isStartOfLine() {
    return false;
  }

  static public class StartOfLine extends Sym {
    // Production: sig("StartOfLine",[arg("org.rascalmpl.ast.Sym","symbol")])
  
    
    private final org.rascalmpl.ast.Sym symbol;
  
    public StartOfLine(IConstructor node , org.rascalmpl.ast.Sym symbol) {
      super(node);
      
      this.symbol = symbol;
    }
  
    @Override
    public boolean isStartOfLine() { 
      return true; 
    }
  
    @Override
    public <T> T accept(IASTVisitor<T> visitor) {
      return visitor.visitSymStartOfLine(this);
    }
  
    @Override
    public boolean equals(Object o) {
      if (!(o instanceof StartOfLine)) {
        return false;
      }        
      StartOfLine tmp = (StartOfLine) o;
      return true && tmp.symbol.equals(this.symbol) ; 
    }
   
    @Override
    public int hashCode() {
      return 383 + 659 * symbol.hashCode() ; 
    } 
  
    
    @Override
    public org.rascalmpl.ast.Sym getSymbol() {
      return this.symbol;
    }
  
    @Override
    public boolean hasSymbol() {
      return true;
    }	
  
    @Override
    public Object clone()  {
      return newInstance(getClass(), (IConstructor) null , clone(symbol));
    }
  }
  public boolean isUnequal() {
    return false;
  }

  static public class Unequal extends Sym {
    // Production: sig("Unequal",[arg("org.rascalmpl.ast.Sym","symbol"),arg("org.rascalmpl.ast.Sym","match")])
  
    
    private final org.rascalmpl.ast.Sym symbol;
    private final org.rascalmpl.ast.Sym match;
  
    public Unequal(IConstructor node , org.rascalmpl.ast.Sym symbol,  org.rascalmpl.ast.Sym match) {
      super(node);
      
      this.symbol = symbol;
      this.match = match;
    }
  
    @Override
    public boolean isUnequal() { 
      return true; 
    }
  
    @Override
    public <T> T accept(IASTVisitor<T> visitor) {
      return visitor.visitSymUnequal(this);
    }
  
    @Override
    public boolean equals(Object o) {
      if (!(o instanceof Unequal)) {
        return false;
      }        
      Unequal tmp = (Unequal) o;
      return true && tmp.symbol.equals(this.symbol) && tmp.match.equals(this.match) ; 
    }
   
    @Override
    public int hashCode() {
      return 3 + 211 * symbol.hashCode() + 307 * match.hashCode() ; 
    } 
  
    
    @Override
    public org.rascalmpl.ast.Sym getSymbol() {
      return this.symbol;
    }
  
    @Override
    public boolean hasSymbol() {
      return true;
    }
    @Override
    public org.rascalmpl.ast.Sym getMatch() {
      return this.match;
    }
  
    @Override
    public boolean hasMatch() {
      return true;
    }	
  
    @Override
    public Object clone()  {
      return newInstance(getClass(), (IConstructor) null , clone(symbol), clone(match));
    }
  }
}