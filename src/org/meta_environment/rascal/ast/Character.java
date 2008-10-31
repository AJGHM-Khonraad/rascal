package org.meta_environment.rascal.ast;
import org.eclipse.imp.pdb.facts.ITree;
public abstract class Character extends AbstractAST
{
  public class Numeric extends Character
  {
/* numChar:NumChar -> Character {cons("Numeric")} */
    private Numeric ()
    {
    }
    /*package */ Numeric (ITree tree, NumChar numChar)
    {
      this.tree = tree;
      this.numChar = numChar;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitCharacterNumeric (this);
    }
    private NumChar numChar;
    public NumChar getNumChar ()
    {
      return numChar;
    }
    private void $setNumChar (NumChar x)
    {
      this.numChar = x;
    }
    public Numeric setNumChar (NumChar x)
    {
      Numeric z = new Numeric ();
      z.$setNumChar (x);
      return z;
    }
  }
  public class Ambiguity extends Character
  {
    private final java.util.List < Character > alternatives;
    public Ambiguity (java.util.List < Character > alternatives)
    {
      this.alternatives =
	java.util.Collections.unmodifiableList (alternatives);
    }
    public java.util.List < Character > getAlternatives ()
    {
      return alternatives;
    }
  }
  public class Short extends Character
  {
/* shortChar:ShortChar -> Character {cons("Short")} */
    private Short ()
    {
    }
    /*package */ Short (ITree tree, ShortChar shortChar)
    {
      this.tree = tree;
      this.shortChar = shortChar;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitCharacterShort (this);
    }
    private ShortChar shortChar;
    public ShortChar getShortChar ()
    {
      return shortChar;
    }
    private void $setShortChar (ShortChar x)
    {
      this.shortChar = x;
    }
    public Short setShortChar (ShortChar x)
    {
      Short z = new Short ();
      z.$setShortChar (x);
      return z;
    }
  }
  public class Top extends Character
  {
/* "\\TOP" -> Character {cons("Top")} */
    private Top ()
    {
    }
    /*package */ Top (ITree tree)
    {
      this.tree = tree;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitCharacterTop (this);
    }
  }
  public class EOF extends Character
  {
/* "\\EOF" -> Character {cons("EOF")} */
    private EOF ()
    {
    }
    /*package */ EOF (ITree tree)
    {
      this.tree = tree;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitCharacterEOF (this);
    }
  }
  public class Bottom extends Character
  {
/* "\\BOT" -> Character {cons("Bottom")} */
    private Bottom ()
    {
    }
    /*package */ Bottom (ITree tree)
    {
      this.tree = tree;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitCharacterBottom (this);
    }
  }
  public class LabelStart extends Character
  {
/* "\\LABEL_START" -> Character {cons("LabelStart")} */
    private LabelStart ()
    {
    }
    /*package */ LabelStart (ITree tree)
    {
      this.tree = tree;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitCharacterLabelStart (this);
    }
  }
}
