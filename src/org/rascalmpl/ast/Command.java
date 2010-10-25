package org.rascalmpl.ast; 
import org.eclipse.imp.pdb.facts.INode; 
public abstract class Command extends AbstractAST { 
  public org.rascalmpl.ast.ShellCommand getCommand() { throw new UnsupportedOperationException(); }
public boolean hasCommand() { return false; }
public boolean isShell() { return false; }
static public class Shell extends Command {
/** ":" command:ShellCommand -> Command {cons("Shell")} */
	protected Shell(INode node, org.rascalmpl.ast.ShellCommand command) {
		this.node = node;
		this.command = command;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitCommandShell(this);
	}

	public boolean isShell() { return true; }

	public boolean hasCommand() { return true; }

private final org.rascalmpl.ast.ShellCommand command;
	public org.rascalmpl.ast.ShellCommand getCommand() { return command; }	
}
static public class Ambiguity extends Command {
  private final java.util.List<org.rascalmpl.ast.Command> alternatives;
  protected Ambiguity(INode node, java.util.List<org.rascalmpl.ast.Command> alternatives) {
	this.alternatives = java.util.Collections.unmodifiableList(alternatives);
         this.node = node;
  }
  public java.util.List<org.rascalmpl.ast.Command> getAlternatives() {
	return alternatives;
  }
  
  public <T> T accept(IASTVisitor<T> v) {
     return v.visitCommandAmbiguity(this);
  }
} 
public org.rascalmpl.ast.Statement getStatement() { throw new UnsupportedOperationException(); }
public boolean hasStatement() { return false; }
public boolean isStatement() { return false; }
static public class Statement extends Command {
/** statement:Statement -> Command {cons("Statement")} */
	protected Statement(INode node, org.rascalmpl.ast.Statement statement) {
		this.node = node;
		this.statement = statement;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitCommandStatement(this);
	}

	public boolean isStatement() { return true; }

	public boolean hasStatement() { return true; }

private final org.rascalmpl.ast.Statement statement;
	public org.rascalmpl.ast.Statement getStatement() { return statement; }	
} public abstract <T> T accept(IASTVisitor<T> visitor); public org.rascalmpl.ast.Expression getExpression() { throw new UnsupportedOperationException(); } public boolean hasExpression() { return false; } public boolean isExpression() { return false; } static public class Expression extends Command {
/** expression:Expression -> Command {prefer, cons("Expression")} */
	protected Expression(INode node, org.rascalmpl.ast.Expression expression) {
		this.node = node;
		this.expression = expression;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitCommandExpression(this);
	}

	public boolean isExpression() { return true; }

	public boolean hasExpression() { return true; }

private final org.rascalmpl.ast.Expression expression;
	public org.rascalmpl.ast.Expression getExpression() { return expression; }	
} public org.rascalmpl.ast.Declaration getDeclaration() { throw new UnsupportedOperationException(); }
public boolean hasDeclaration() { return false; }
public boolean isDeclaration() { return false; }
static public class Declaration extends Command {
/** declaration:Declaration -> Command {avoid, cons("Declaration")} */
	protected Declaration(INode node, org.rascalmpl.ast.Declaration declaration) {
		this.node = node;
		this.declaration = declaration;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitCommandDeclaration(this);
	}

	public boolean isDeclaration() { return true; }

	public boolean hasDeclaration() { return true; }

private final org.rascalmpl.ast.Declaration declaration;
	public org.rascalmpl.ast.Declaration getDeclaration() { return declaration; }	
} 
public org.rascalmpl.ast.Import getImported() { throw new UnsupportedOperationException(); }
public boolean hasImported() { return false; }
public boolean isImport() { return false; }
static public class Import extends Command {
/** imported:Import -> Command {cons("Import")} */
	protected Import(INode node, org.rascalmpl.ast.Import imported) {
		this.node = node;
		this.imported = imported;
	}
	public <T> T accept(IASTVisitor<T> visitor) {
		return visitor.visitCommandImport(this);
	}

	public boolean isImport() { return true; }

	public boolean hasImported() { return true; }

private final org.rascalmpl.ast.Import imported;
	public org.rascalmpl.ast.Import getImported() { return imported; }	
}
}