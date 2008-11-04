package org.meta_environment.rascal.ast;
import org.eclipse.imp.pdb.facts.ITree;
public abstract class Literal extends AbstractAST
{
  public org.meta_environment.rascal.ast.RegExpLiteral getRegExpLiteral ()
  {
    throw new UnsupportedOperationException ();
  }
  public boolean hasRegExpLiteral ()
  {
    return false;
  }
  public boolean isRegExp ()
  {
    return false;
  }
  static public class RegExp extends Literal
  {
/* regExpLiteral:RegExpLiteral -> Literal {cons("RegExp")} */
    private RegExp ()
    {
    }
    /*package */ RegExp (ITree tree,
			 org.meta_environment.rascal.ast.
			 RegExpLiteral regExpLiteral)
    {
      this.tree = tree;
      this.regExpLiteral = regExpLiteral;
    }
    public IVisItable accept (IASTVisItor visItor)
    {
      return visItor.visItLiteralRegExp (this);
    }

    public boolean isRegExp ()
    {
      return true;
    }

    public boolean hasRegExpLiteral ()
    {
      return true;
    }

    private org.meta_environment.rascal.ast.RegExpLiteral regExpLiteral;
    public org.meta_environment.rascal.ast.RegExpLiteral getRegExpLiteral ()
    {
      return regExpLiteral;
    }
    private void $setRegExpLiteral (org.meta_environment.rascal.ast.
				    RegExpLiteral x)
    {
      this.regExpLiteral = x;
    }
    public RegExp setRegExpLiteral (org.meta_environment.rascal.ast.
				    RegExpLiteral x)
    {
      RegExp z = new RegExp ();
      z.$setRegExpLiteral (x);
      return z;
    }
  }
  static public class Ambiguity extends Literal
  {
    private final java.util.LisT < org.meta_environment.rascal.ast.Literal >
      alternatives;
    public Ambiguity (java.util.LisT <
		      org.meta_environment.rascal.ast.Literal > alternatives)
    {
      this.alternatives =
	java.util.Collections.unmodifiableLisT (alternatives);
    }
    public java.util.LisT < org.meta_environment.rascal.ast.Literal >
      getAlternatives ()
    {
      return alternatives;
    }
  }
  public org.meta_environment.rascal.ast.SymbolLiteral getSymbolLiteral ()
  {
    throw new UnsupportedOperationException ();
  }
  public boolean hasSymbolLiteral ()
  {
    return false;
  }
  public boolean isSymbol ()
  {
    return false;
  }
  static public class Symbol extends Literal
  {
/* symbolLiteral:SymbolLiteral -> Literal {cons("Symbol")} */
    private Symbol ()
    {
    }
    /*package */ Symbol (ITree tree,
			 org.meta_environment.rascal.ast.
			 SymbolLiteral symbolLiteral)
    {
      this.tree = tree;
      this.symbolLiteral = symbolLiteral;
    }
    public IVisItable accept (IASTVisItor visItor)
    {
      return visItor.visItLiteralSymbol (this);
    }

    public boolean isSymbol ()
    {
      return true;
    }

    public boolean hasSymbolLiteral ()
    {
      return true;
    }

    private org.meta_environment.rascal.ast.SymbolLiteral symbolLiteral;
    public org.meta_environment.rascal.ast.SymbolLiteral getSymbolLiteral ()
    {
      return symbolLiteral;
    }
    private void $setSymbolLiteral (org.meta_environment.rascal.ast.
				    SymbolLiteral x)
    {
      this.symbolLiteral = x;
    }
    public Symbol setSymbolLiteral (org.meta_environment.rascal.ast.
				    SymbolLiteral x)
    {
      Symbol z = new Symbol ();
      z.$setSymbolLiteral (x);
      return z;
    }
  }
  public org.meta_environment.rascal.ast.BooleanLiteral getBooleanLiteral ()
  {
    throw new UnsupportedOperationException ();
  }
  public boolean hasBooleanLiteral ()
  {
    return false;
  }
  public boolean isBoolean ()
  {
    return false;
  }
  static public class Boolean extends Literal
  {
/* booleanLiteral:BooleanLiteral -> Literal {cons("Boolean")} */
    private Boolean ()
    {
    }
    /*package */ Boolean (ITree tree,
			  org.meta_environment.rascal.ast.
			  BooleanLiteral booleanLiteral)
    {
      this.tree = tree;
      this.booleanLiteral = booleanLiteral;
    }
    public IVisItable accept (IASTVisItor visItor)
    {
      return visItor.visItLiteralBoolean (this);
    }

    public boolean isBoolean ()
    {
      return true;
    }

    public boolean hasBooleanLiteral ()
    {
      return true;
    }

    private org.meta_environment.rascal.ast.BooleanLiteral booleanLiteral;
    public org.meta_environment.rascal.ast.BooleanLiteral getBooleanLiteral ()
    {
      return booleanLiteral;
    }
    private void $setBooleanLiteral (org.meta_environment.rascal.ast.
				     BooleanLiteral x)
    {
      this.booleanLiteral = x;
    }
    public Boolean setBooleanLiteral (org.meta_environment.rascal.ast.
				      BooleanLiteral x)
    {
      Boolean z = new Boolean ();
      z.$setBooleanLiteral (x);
      return z;
    }
  }
  public org.meta_environment.rascal.ast.IntegerLiteral getIntegerLiteral ()
  {
    throw new UnsupportedOperationException ();
  }
  public boolean hasIntegerLiteral ()
  {
    return false;
  }
  public boolean isInteger ()
  {
    return false;
  }
  static public class Integer extends Literal
  {
/* integerLiteral:IntegerLiteral -> Literal {cons("Integer")} */
    private Integer ()
    {
    }
    /*package */ Integer (ITree tree,
			  org.meta_environment.rascal.ast.
			  IntegerLiteral integerLiteral)
    {
      this.tree = tree;
      this.integerLiteral = integerLiteral;
    }
    public IVisItable accept (IASTVisItor visItor)
    {
      return visItor.visItLiteralInteger (this);
    }

    public boolean isInteger ()
    {
      return true;
    }

    public boolean hasIntegerLiteral ()
    {
      return true;
    }

    private org.meta_environment.rascal.ast.IntegerLiteral integerLiteral;
    public org.meta_environment.rascal.ast.IntegerLiteral getIntegerLiteral ()
    {
      return integerLiteral;
    }
    private void $setIntegerLiteral (org.meta_environment.rascal.ast.
				     IntegerLiteral x)
    {
      this.integerLiteral = x;
    }
    public Integer setIntegerLiteral (org.meta_environment.rascal.ast.
				      IntegerLiteral x)
    {
      Integer z = new Integer ();
      z.$setIntegerLiteral (x);
      return z;
    }
  }
  public org.meta_environment.rascal.ast.DoubleLiteral getDoubleLiteral ()
  {
    throw new UnsupportedOperationException ();
  }
  public boolean hasDoubleLiteral ()
  {
    return false;
  }
  public boolean isDouble ()
  {
    return false;
  }
  static public class Double extends Literal
  {
/* doubleLiteral:DoubleLiteral -> Literal {cons("Double")} */
    private Double ()
    {
    }
    /*package */ Double (ITree tree,
			 org.meta_environment.rascal.ast.
			 DoubleLiteral doubleLiteral)
    {
      this.tree = tree;
      this.doubleLiteral = doubleLiteral;
    }
    public IVisItable accept (IASTVisItor visItor)
    {
      return visItor.visItLiteralDouble (this);
    }

    public boolean isDouble ()
    {
      return true;
    }

    public boolean hasDoubleLiteral ()
    {
      return true;
    }

    private org.meta_environment.rascal.ast.DoubleLiteral doubleLiteral;
    public org.meta_environment.rascal.ast.DoubleLiteral getDoubleLiteral ()
    {
      return doubleLiteral;
    }
    private void $setDoubleLiteral (org.meta_environment.rascal.ast.
				    DoubleLiteral x)
    {
      this.doubleLiteral = x;
    }
    public Double setDoubleLiteral (org.meta_environment.rascal.ast.
				    DoubleLiteral x)
    {
      Double z = new Double ();
      z.$setDoubleLiteral (x);
      return z;
    }
  }
  public org.meta_environment.rascal.ast.StringLiteral getStringLiteral ()
  {
    throw new UnsupportedOperationException ();
  }
  public boolean hasStringLiteral ()
  {
    return false;
  }
  public boolean isString ()
  {
    return false;
  }
  static public class String extends Literal
  {
/* stringLiteral:StringLiteral -> Literal {cons("String")} */
    private String ()
    {
    }
    /*package */ String (ITree tree,
			 org.meta_environment.rascal.ast.
			 StringLiteral stringLiteral)
    {
      this.tree = tree;
      this.stringLiteral = stringLiteral;
    }
    public IVisItable accept (IASTVisItor visItor)
    {
      return visItor.visItLiteralString (this);
    }

    public boolean isString ()
    {
      return true;
    }

    public boolean hasStringLiteral ()
    {
      return true;
    }

    private org.meta_environment.rascal.ast.StringLiteral stringLiteral;
    public org.meta_environment.rascal.ast.StringLiteral getStringLiteral ()
    {
      return stringLiteral;
    }
    private void $setStringLiteral (org.meta_environment.rascal.ast.
				    StringLiteral x)
    {
      this.stringLiteral = x;
    }
    public String setStringLiteral (org.meta_environment.rascal.ast.
				    StringLiteral x)
    {
      String z = new String ();
      z.$setStringLiteral (x);
      return z;
    }
  }
}
