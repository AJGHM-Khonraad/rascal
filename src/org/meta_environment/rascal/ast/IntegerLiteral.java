package org.meta_environment.rascal.ast;
public abstract class IntegerLiteral extends AbstractAST
{
  public class DecimalIntegerLiteral extends IntegerLiteral
  {
/* DecimalIntegerLiteral -> IntegerLiteral {prefer, cons("DecimalIntegerLiteral")} */
    private DecimalIntegerLiteral ()
    {
    }
    /*package */ DecimalIntegerLiteral (ITree tree)
    {
      this.tree = tree;
    }
    public IVisitable accept (IVisitor visitor)
    {
      return visitor.visitDecimalIntegerLiteralIntegerLiteral (this);
    }
  }
  public class HexIntegerLiteral extends IntegerLiteral
  {
/* HexIntegerLiteral -> IntegerLiteral {prefer, cons("HexIntegerLiteral")} */
    private HexIntegerLiteral ()
    {
    }
    /*package */ HexIntegerLiteral (ITree tree)
    {
      this.tree = tree;
    }
    public IVisitable accept (IVisitor visitor)
    {
      return visitor.visitHexIntegerLiteralIntegerLiteral (this);
    }
  }
  public class OctalIntegerLiteral extends IntegerLiteral
  {
/* OctalIntegerLiteral -> IntegerLiteral {prefer, cons("OctalIntegerLiteral")} */
    private OctalIntegerLiteral ()
    {
    }
    /*package */ OctalIntegerLiteral (ITree tree)
    {
      this.tree = tree;
    }
    public IVisitable accept (IVisitor visitor)
    {
      return visitor.visitOctalIntegerLiteralIntegerLiteral (this);
    }
  }
}
