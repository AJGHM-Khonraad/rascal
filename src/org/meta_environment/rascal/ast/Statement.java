package org.meta_environment.rascal.ast;
import org.eclipse.imp.pdb.facts.ITree;
public abstract class Statement extends AbstractAST
{
  static public class Solve extends Statement
  {
/* "with" declarations:{Declarator ";"}+ ";" "solve" body:Statement -> Statement {cons("Solve")} */
    private Solve ()
    {
    }
    /*package */ Solve (ITree tree,
			java.util.List < Declarator > declarations,
			org.meta_environment.rascal.ast.Statement body)
    {
      this.tree = tree;
      this.declarations = declarations;
      this.body = body;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitStatementSolve (this);
    }
    private java.util.List < org.meta_environment.rascal.ast.Declarator >
      declarations;
    public java.util.List < org.meta_environment.rascal.ast.Declarator >
      getDeclarations ()
    {
      return declarations;
    }
    private void $setDeclarations (java.util.List <
				   org.meta_environment.rascal.ast.
				   Declarator > x)
    {
      this.declarations = x;
    }
    public org.meta_environment.rascal.ast.Solve setDeclarations (java.util.
								  List <
								  org.
								  meta_environment.
								  rascal.ast.
								  Declarator >
								  x)
    {
      org.meta_environment.rascal.ast.Solve z = new Solve ();
      z.$setDeclarations (x);
      return z;
    }
    private org.meta_environment.rascal.ast.Statement body;
    public org.meta_environment.rascal.ast.Statement getBody ()
    {
      return body;
    }
    private void $setBody (org.meta_environment.rascal.ast.Statement x)
    {
      this.body = x;
    }
    public org.meta_environment.rascal.ast.Solve setBody (org.
							  meta_environment.
							  rascal.ast.
							  Statement x)
    {
      org.meta_environment.rascal.ast.Solve z = new Solve ();
      z.$setBody (x);
      return z;
    }
  }
  static public class Ambiguity extends Statement
  {
    private final java.util.List < Statement > alternatives;
    public Ambiguity (java.util.List < Statement > alternatives)
    {
      this.alternatives =
	java.util.Collections.unmodifiableList (alternatives);
    }
    public java.util.List < Statement > getAlternatives ()
    {
      return alternatives;
    }
  }
  static public class For extends Statement
  {
/* label:Label "for" "(" generators:{Generator ","}+ ")" body:Statement -> Statement {cons("For")} */
    private For ()
    {
    }
    /*package */ For (ITree tree, org.meta_environment.rascal.ast.Label label,
		      java.util.List < Generator > generators,
		      org.meta_environment.rascal.ast.Statement body)
    {
      this.tree = tree;
      this.label = label;
      this.generators = generators;
      this.body = body;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitStatementFor (this);
    }
    private org.meta_environment.rascal.ast.Label label;
    public org.meta_environment.rascal.ast.Label getLabel ()
    {
      return label;
    }
    private void $setLabel (org.meta_environment.rascal.ast.Label x)
    {
      this.label = x;
    }
    public org.meta_environment.rascal.ast.For setLabel (org.meta_environment.
							 rascal.ast.Label x)
    {
      org.meta_environment.rascal.ast.For z = new For ();
      z.$setLabel (x);
      return z;
    }
    private java.util.List < org.meta_environment.rascal.ast.Generator >
      generators;
    public java.util.List < org.meta_environment.rascal.ast.Generator >
      getGenerators ()
    {
      return generators;
    }
    private void $setGenerators (java.util.List <
				 org.meta_environment.rascal.ast.Generator >
				 x)
    {
      this.generators = x;
    }
    public org.meta_environment.rascal.ast.For setGenerators (java.util.List <
							      org.
							      meta_environment.
							      rascal.ast.
							      Generator > x)
    {
      org.meta_environment.rascal.ast.For z = new For ();
      z.$setGenerators (x);
      return z;
    }
    private org.meta_environment.rascal.ast.Statement body;
    public org.meta_environment.rascal.ast.Statement getBody ()
    {
      return body;
    }
    private void $setBody (org.meta_environment.rascal.ast.Statement x)
    {
      this.body = x;
    }
    public org.meta_environment.rascal.ast.For setBody (org.meta_environment.
							rascal.ast.
							Statement x)
    {
      org.meta_environment.rascal.ast.For z = new For ();
      z.$setBody (x);
      return z;
    }
  }
  static public class While extends Statement
  {
/* label:Label "while" "(" condition:Expression ")" body:Statement -> Statement {cons("While")} */
    private While ()
    {
    }
    /*package */ While (ITree tree,
			org.meta_environment.rascal.ast.Label label,
			org.meta_environment.rascal.ast.Expression condition,
			org.meta_environment.rascal.ast.Statement body)
    {
      this.tree = tree;
      this.label = label;
      this.condition = condition;
      this.body = body;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitStatementWhile (this);
    }
    private org.meta_environment.rascal.ast.Label label;
    public org.meta_environment.rascal.ast.Label getLabel ()
    {
      return label;
    }
    private void $setLabel (org.meta_environment.rascal.ast.Label x)
    {
      this.label = x;
    }
    public org.meta_environment.rascal.ast.While setLabel (org.
							   meta_environment.
							   rascal.ast.Label x)
    {
      org.meta_environment.rascal.ast.While z = new While ();
      z.$setLabel (x);
      return z;
    }
    private org.meta_environment.rascal.ast.Expression condition;
    public org.meta_environment.rascal.ast.Expression getCondition ()
    {
      return condition;
    }
    private void $setCondition (org.meta_environment.rascal.ast.Expression x)
    {
      this.condition = x;
    }
    public org.meta_environment.rascal.ast.While setCondition (org.
							       meta_environment.
							       rascal.ast.
							       Expression x)
    {
      org.meta_environment.rascal.ast.While z = new While ();
      z.$setCondition (x);
      return z;
    }
    private org.meta_environment.rascal.ast.Statement body;
    public org.meta_environment.rascal.ast.Statement getBody ()
    {
      return body;
    }
    private void $setBody (org.meta_environment.rascal.ast.Statement x)
    {
      this.body = x;
    }
    public org.meta_environment.rascal.ast.While setBody (org.
							  meta_environment.
							  rascal.ast.
							  Statement x)
    {
      org.meta_environment.rascal.ast.While z = new While ();
      z.$setBody (x);
      return z;
    }
  }
  static public class DoWhile extends Statement
  {
/* label:Label "do" body:Statement "while" "(" condition:Expression ")" ";" -> Statement {cons("DoWhile")} */
    private DoWhile ()
    {
    }
    /*package */ DoWhile (ITree tree,
			  org.meta_environment.rascal.ast.Label label,
			  org.meta_environment.rascal.ast.Statement body,
			  org.meta_environment.rascal.ast.
			  Expression condition)
    {
      this.tree = tree;
      this.label = label;
      this.body = body;
      this.condition = condition;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitStatementDoWhile (this);
    }
    private org.meta_environment.rascal.ast.Label label;
    public org.meta_environment.rascal.ast.Label getLabel ()
    {
      return label;
    }
    private void $setLabel (org.meta_environment.rascal.ast.Label x)
    {
      this.label = x;
    }
    public org.meta_environment.rascal.ast.DoWhile setLabel (org.
							     meta_environment.
							     rascal.ast.
							     Label x)
    {
      org.meta_environment.rascal.ast.DoWhile z = new DoWhile ();
      z.$setLabel (x);
      return z;
    }
    private org.meta_environment.rascal.ast.Statement body;
    public org.meta_environment.rascal.ast.Statement getBody ()
    {
      return body;
    }
    private void $setBody (org.meta_environment.rascal.ast.Statement x)
    {
      this.body = x;
    }
    public org.meta_environment.rascal.ast.DoWhile setBody (org.
							    meta_environment.
							    rascal.ast.
							    Statement x)
    {
      org.meta_environment.rascal.ast.DoWhile z = new DoWhile ();
      z.$setBody (x);
      return z;
    }
    private org.meta_environment.rascal.ast.Expression condition;
    public org.meta_environment.rascal.ast.Expression getCondition ()
    {
      return condition;
    }
    private void $setCondition (org.meta_environment.rascal.ast.Expression x)
    {
      this.condition = x;
    }
    public org.meta_environment.rascal.ast.DoWhile setCondition (org.
								 meta_environment.
								 rascal.ast.
								 Expression x)
    {
      org.meta_environment.rascal.ast.DoWhile z = new DoWhile ();
      z.$setCondition (x);
      return z;
    }
  }
  static public class IfThenElse extends Statement
  {
/* label:Label "if" "(" conditions:{Expression ","}+ ")" thenStatement:Statement "else" elseStatement:Statement -> Statement {cons("IfThenElse")} */
    private IfThenElse ()
    {
    }
    /*package */ IfThenElse (ITree tree,
			     org.meta_environment.rascal.ast.Label label,
			     java.util.List < Expression > conditions,
			     org.meta_environment.rascal.ast.
			     Statement thenStatement,
			     org.meta_environment.rascal.ast.
			     Statement elseStatement)
    {
      this.tree = tree;
      this.label = label;
      this.conditions = conditions;
      this.thenStatement = thenStatement;
      this.elseStatement = elseStatement;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitStatementIfThenElse (this);
    }
    private org.meta_environment.rascal.ast.Label label;
    public org.meta_environment.rascal.ast.Label getLabel ()
    {
      return label;
    }
    private void $setLabel (org.meta_environment.rascal.ast.Label x)
    {
      this.label = x;
    }
    public org.meta_environment.rascal.ast.IfThenElse setLabel (org.
								meta_environment.
								rascal.ast.
								Label x)
    {
      org.meta_environment.rascal.ast.IfThenElse z = new IfThenElse ();
      z.$setLabel (x);
      return z;
    }
    private java.util.List < org.meta_environment.rascal.ast.Expression >
      conditions;
    public java.util.List < org.meta_environment.rascal.ast.Expression >
      getConditions ()
    {
      return conditions;
    }
    private void $setConditions (java.util.List <
				 org.meta_environment.rascal.ast.Expression >
				 x)
    {
      this.conditions = x;
    }
    public org.meta_environment.rascal.ast.IfThenElse setConditions (java.
								     util.
								     List <
								     org.
								     meta_environment.
								     rascal.
								     ast.
								     Expression
								     > x)
    {
      org.meta_environment.rascal.ast.IfThenElse z = new IfThenElse ();
      z.$setConditions (x);
      return z;
    }
    private org.meta_environment.rascal.ast.Statement thenStatement;
    public org.meta_environment.rascal.ast.Statement getThenStatement ()
    {
      return thenStatement;
    }
    private void $setThenStatement (org.meta_environment.rascal.ast.
				    Statement x)
    {
      this.thenStatement = x;
    }
    public org.meta_environment.rascal.ast.IfThenElse setThenStatement (org.
									meta_environment.
									rascal.
									ast.
									Statement
									x)
    {
      org.meta_environment.rascal.ast.IfThenElse z = new IfThenElse ();
      z.$setThenStatement (x);
      return z;
    }
    private org.meta_environment.rascal.ast.Statement elseStatement;
    public org.meta_environment.rascal.ast.Statement getElseStatement ()
    {
      return elseStatement;
    }
    private void $setElseStatement (org.meta_environment.rascal.ast.
				    Statement x)
    {
      this.elseStatement = x;
    }
    public org.meta_environment.rascal.ast.IfThenElse setElseStatement (org.
									meta_environment.
									rascal.
									ast.
									Statement
									x)
    {
      org.meta_environment.rascal.ast.IfThenElse z = new IfThenElse ();
      z.$setElseStatement (x);
      return z;
    }
  }
  static public class IfThen extends Statement
  {
/* label:Label "if" "(" conditions:{Expression ","}+ ")" thenStatement:Statement NoElseMayFollow -> Statement {cons("IfThen")} */
    private IfThen ()
    {
    }
    /*package */ IfThen (ITree tree,
			 org.meta_environment.rascal.ast.Label label,
			 java.util.List < Expression > conditions,
			 org.meta_environment.rascal.ast.
			 Statement thenStatement)
    {
      this.tree = tree;
      this.label = label;
      this.conditions = conditions;
      this.thenStatement = thenStatement;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitStatementIfThen (this);
    }
    private org.meta_environment.rascal.ast.Label label;
    public org.meta_environment.rascal.ast.Label getLabel ()
    {
      return label;
    }
    private void $setLabel (org.meta_environment.rascal.ast.Label x)
    {
      this.label = x;
    }
    public org.meta_environment.rascal.ast.IfThen setLabel (org.
							    meta_environment.
							    rascal.ast.
							    Label x)
    {
      org.meta_environment.rascal.ast.IfThen z = new IfThen ();
      z.$setLabel (x);
      return z;
    }
    private java.util.List < org.meta_environment.rascal.ast.Expression >
      conditions;
    public java.util.List < org.meta_environment.rascal.ast.Expression >
      getConditions ()
    {
      return conditions;
    }
    private void $setConditions (java.util.List <
				 org.meta_environment.rascal.ast.Expression >
				 x)
    {
      this.conditions = x;
    }
    public org.meta_environment.rascal.ast.IfThen setConditions (java.util.
								 List <
								 org.
								 meta_environment.
								 rascal.ast.
								 Expression >
								 x)
    {
      org.meta_environment.rascal.ast.IfThen z = new IfThen ();
      z.$setConditions (x);
      return z;
    }
    private org.meta_environment.rascal.ast.Statement thenStatement;
    public org.meta_environment.rascal.ast.Statement getThenStatement ()
    {
      return thenStatement;
    }
    private void $setThenStatement (org.meta_environment.rascal.ast.
				    Statement x)
    {
      this.thenStatement = x;
    }
    public org.meta_environment.rascal.ast.IfThen setThenStatement (org.
								    meta_environment.
								    rascal.
								    ast.
								    Statement
								    x)
    {
      org.meta_environment.rascal.ast.IfThen z = new IfThen ();
      z.$setThenStatement (x);
      return z;
    }
  }
  static public class Switch extends Statement
  {
/* label:Label "switch" "(" expression:Expression ")" "{" cases:Case+ "}" -> Statement {cons("Switch")} */
    private Switch ()
    {
    }
    /*package */ Switch (ITree tree,
			 org.meta_environment.rascal.ast.Label label,
			 org.meta_environment.rascal.ast.
			 Expression expression, java.util.List < Case > cases)
    {
      this.tree = tree;
      this.label = label;
      this.expression = expression;
      this.cases = cases;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitStatementSwitch (this);
    }
    private org.meta_environment.rascal.ast.Label label;
    public org.meta_environment.rascal.ast.Label getLabel ()
    {
      return label;
    }
    private void $setLabel (org.meta_environment.rascal.ast.Label x)
    {
      this.label = x;
    }
    public org.meta_environment.rascal.ast.Switch setLabel (org.
							    meta_environment.
							    rascal.ast.
							    Label x)
    {
      org.meta_environment.rascal.ast.Switch z = new Switch ();
      z.$setLabel (x);
      return z;
    }
    private org.meta_environment.rascal.ast.Expression expression;
    public org.meta_environment.rascal.ast.Expression getExpression ()
    {
      return expression;
    }
    private void $setExpression (org.meta_environment.rascal.ast.Expression x)
    {
      this.expression = x;
    }
    public org.meta_environment.rascal.ast.Switch setExpression (org.
								 meta_environment.
								 rascal.ast.
								 Expression x)
    {
      org.meta_environment.rascal.ast.Switch z = new Switch ();
      z.$setExpression (x);
      return z;
    }
    private java.util.List < org.meta_environment.rascal.ast.Case > cases;
    public java.util.List < org.meta_environment.rascal.ast.Case > getCases ()
    {
      return cases;
    }
    private void $setCases (java.util.List <
			    org.meta_environment.rascal.ast.Case > x)
    {
      this.cases = x;
    }
    public org.meta_environment.rascal.ast.Switch setCases (java.util.List <
							    org.
							    meta_environment.
							    rascal.ast.Case >
							    x)
    {
      org.meta_environment.rascal.ast.Switch z = new Switch ();
      z.$setCases (x);
      return z;
    }
  }
  static public class All extends Statement
  {
/* label:Label "all" "(" conditions:{Expression ","}+ ")" body:Statement -> Statement {cons("All")} */
    private All ()
    {
    }
    /*package */ All (ITree tree, org.meta_environment.rascal.ast.Label label,
		      java.util.List < Expression > conditions,
		      org.meta_environment.rascal.ast.Statement body)
    {
      this.tree = tree;
      this.label = label;
      this.conditions = conditions;
      this.body = body;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitStatementAll (this);
    }
    private org.meta_environment.rascal.ast.Label label;
    public org.meta_environment.rascal.ast.Label getLabel ()
    {
      return label;
    }
    private void $setLabel (org.meta_environment.rascal.ast.Label x)
    {
      this.label = x;
    }
    public org.meta_environment.rascal.ast.All setLabel (org.meta_environment.
							 rascal.ast.Label x)
    {
      org.meta_environment.rascal.ast.All z = new All ();
      z.$setLabel (x);
      return z;
    }
    private java.util.List < org.meta_environment.rascal.ast.Expression >
      conditions;
    public java.util.List < org.meta_environment.rascal.ast.Expression >
      getConditions ()
    {
      return conditions;
    }
    private void $setConditions (java.util.List <
				 org.meta_environment.rascal.ast.Expression >
				 x)
    {
      this.conditions = x;
    }
    public org.meta_environment.rascal.ast.All setConditions (java.util.List <
							      org.
							      meta_environment.
							      rascal.ast.
							      Expression > x)
    {
      org.meta_environment.rascal.ast.All z = new All ();
      z.$setConditions (x);
      return z;
    }
    private org.meta_environment.rascal.ast.Statement body;
    public org.meta_environment.rascal.ast.Statement getBody ()
    {
      return body;
    }
    private void $setBody (org.meta_environment.rascal.ast.Statement x)
    {
      this.body = x;
    }
    public org.meta_environment.rascal.ast.All setBody (org.meta_environment.
							rascal.ast.
							Statement x)
    {
      org.meta_environment.rascal.ast.All z = new All ();
      z.$setBody (x);
      return z;
    }
  }
  static public class First extends Statement
  {
/* label:Label "first" "(" conditions:{Expression ","}+ ")" body:Statement -> Statement {cons("First")} */
    private First ()
    {
    }
    /*package */ First (ITree tree,
			org.meta_environment.rascal.ast.Label label,
			java.util.List < Expression > conditions,
			org.meta_environment.rascal.ast.Statement body)
    {
      this.tree = tree;
      this.label = label;
      this.conditions = conditions;
      this.body = body;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitStatementFirst (this);
    }
    private org.meta_environment.rascal.ast.Label label;
    public org.meta_environment.rascal.ast.Label getLabel ()
    {
      return label;
    }
    private void $setLabel (org.meta_environment.rascal.ast.Label x)
    {
      this.label = x;
    }
    public org.meta_environment.rascal.ast.First setLabel (org.
							   meta_environment.
							   rascal.ast.Label x)
    {
      org.meta_environment.rascal.ast.First z = new First ();
      z.$setLabel (x);
      return z;
    }
    private java.util.List < org.meta_environment.rascal.ast.Expression >
      conditions;
    public java.util.List < org.meta_environment.rascal.ast.Expression >
      getConditions ()
    {
      return conditions;
    }
    private void $setConditions (java.util.List <
				 org.meta_environment.rascal.ast.Expression >
				 x)
    {
      this.conditions = x;
    }
    public org.meta_environment.rascal.ast.First setConditions (java.util.
								List <
								org.
								meta_environment.
								rascal.ast.
								Expression >
								x)
    {
      org.meta_environment.rascal.ast.First z = new First ();
      z.$setConditions (x);
      return z;
    }
    private org.meta_environment.rascal.ast.Statement body;
    public org.meta_environment.rascal.ast.Statement getBody ()
    {
      return body;
    }
    private void $setBody (org.meta_environment.rascal.ast.Statement x)
    {
      this.body = x;
    }
    public org.meta_environment.rascal.ast.First setBody (org.
							  meta_environment.
							  rascal.ast.
							  Statement x)
    {
      org.meta_environment.rascal.ast.First z = new First ();
      z.$setBody (x);
      return z;
    }
  }
  static public class Expression extends Statement
  {
/* expression:Expression ";" -> Statement {cons("Expression")} */
    private Expression ()
    {
    }
    /*package */ Expression (ITree tree,
			     org.meta_environment.rascal.ast.
			     Expression expression)
    {
      this.tree = tree;
      this.expression = expression;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitStatementExpression (this);
    }
    private org.meta_environment.rascal.ast.Expression expression;
    public org.meta_environment.rascal.ast.Expression getExpression ()
    {
      return expression;
    }
    private void $setExpression (org.meta_environment.rascal.ast.Expression x)
    {
      this.expression = x;
    }
    public org.meta_environment.rascal.ast.Expression setExpression (org.
								     meta_environment.
								     rascal.
								     ast.
								     Expression
								     x)
    {
      org.meta_environment.rascal.ast.Expression z = new Expression ();
      z.$setExpression (x);
      return z;
    }
  }
  static public class Visit extends Statement
  {
/* visit:Visit -> Statement {cons("Visit")} */
    private Visit ()
    {
    }
    /*package */ Visit (ITree tree,
			org.meta_environment.rascal.ast.Visit visit)
    {
      this.tree = tree;
      this.visit = visit;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitStatementVisit (this);
    }
    private org.meta_environment.rascal.ast.Visit visit;
    public org.meta_environment.rascal.ast.Visit getVisit ()
    {
      return visit;
    }
    private void $setVisit (org.meta_environment.rascal.ast.Visit x)
    {
      this.visit = x;
    }
    public org.meta_environment.rascal.ast.Visit setVisit (org.
							   meta_environment.
							   rascal.ast.Visit x)
    {
      org.meta_environment.rascal.ast.Visit z = new Visit ();
      z.$setVisit (x);
      return z;
    }
  }
  static public class Assignment extends Statement
  {
/* assignables:{Assignable ","}+ operator:Assignment expressions:{Expression ","}+ ";" -> Statement {cons("Assignment")} */
    private Assignment ()
    {
    }
    /*package */ Assignment (ITree tree,
			     java.util.List < Assignable > assignables,
			     org.meta_environment.rascal.ast.
			     Assignment operator,
			     java.util.List < Expression > expressions)
    {
      this.tree = tree;
      this.assignables = assignables;
      this.operator = operator;
      this.expressions = expressions;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitStatementAssignment (this);
    }
    private java.util.List < org.meta_environment.rascal.ast.Assignable >
      assignables;
    public java.util.List < org.meta_environment.rascal.ast.Assignable >
      getAssignables ()
    {
      return assignables;
    }
    private void $setAssignables (java.util.List <
				  org.meta_environment.rascal.ast.Assignable >
				  x)
    {
      this.assignables = x;
    }
    public org.meta_environment.rascal.ast.Assignment setAssignables (java.
								      util.
								      List <
								      org.
								      meta_environment.
								      rascal.
								      ast.
								      Assignable
								      > x)
    {
      org.meta_environment.rascal.ast.Assignment z = new Assignment ();
      z.$setAssignables (x);
      return z;
    }
    private org.meta_environment.rascal.ast.Assignment operator;
    public org.meta_environment.rascal.ast.Assignment getOperator ()
    {
      return operator;
    }
    private void $setOperator (org.meta_environment.rascal.ast.Assignment x)
    {
      this.operator = x;
    }
    public org.meta_environment.rascal.ast.Assignment setOperator (org.
								   meta_environment.
								   rascal.ast.
								   Assignment
								   x)
    {
      org.meta_environment.rascal.ast.Assignment z = new Assignment ();
      z.$setOperator (x);
      return z;
    }
    private java.util.List < org.meta_environment.rascal.ast.Expression >
      expressions;
    public java.util.List < org.meta_environment.rascal.ast.Expression >
      getExpressions ()
    {
      return expressions;
    }
    private void $setExpressions (java.util.List <
				  org.meta_environment.rascal.ast.Expression >
				  x)
    {
      this.expressions = x;
    }
    public org.meta_environment.rascal.ast.Assignment setExpressions (java.
								      util.
								      List <
								      org.
								      meta_environment.
								      rascal.
								      ast.
								      Expression
								      > x)
    {
      org.meta_environment.rascal.ast.Assignment z = new Assignment ();
      z.$setExpressions (x);
      return z;
    }
  }
  static public class Break extends Statement
  {
/* Break -> Statement {cons("Break")} */
    private Break ()
    {
    }
    /*package */ Break (ITree tree)
    {
      this.tree = tree;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitStatementBreak (this);
    }
  }
  static public class Fail extends Statement
  {
/* Fail -> Statement {cons("Fail")} */
    private Fail ()
    {
    }
    /*package */ Fail (ITree tree)
    {
      this.tree = tree;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitStatementFail (this);
    }
  }
  static public class Return extends Statement
  {
/* Return -> Statement {cons("Return")} */
    private Return ()
    {
    }
    /*package */ Return (ITree tree)
    {
      this.tree = tree;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitStatementReturn (this);
    }
  }
  static public class Continue extends Statement
  {
/* "continue" ";" -> Statement {cons("Continue")} */
    private Continue ()
    {
    }
    /*package */ Continue (ITree tree)
    {
      this.tree = tree;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitStatementContinue (this);
    }
  }
  static public class Assert extends Statement
  {
/* "assert" label:StringLiteral ":" expression:Expression ";" -> Statement {cons("Assert")} */
    private Assert ()
    {
    }
    /*package */ Assert (ITree tree,
			 org.meta_environment.rascal.ast.StringLiteral label,
			 org.meta_environment.rascal.ast.
			 Expression expression)
    {
      this.tree = tree;
      this.label = label;
      this.expression = expression;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitStatementAssert (this);
    }
    private org.meta_environment.rascal.ast.StringLiteral label;
    public org.meta_environment.rascal.ast.StringLiteral getLabel ()
    {
      return label;
    }
    private void $setLabel (org.meta_environment.rascal.ast.StringLiteral x)
    {
      this.label = x;
    }
    public org.meta_environment.rascal.ast.Assert setLabel (org.
							    meta_environment.
							    rascal.ast.
							    StringLiteral x)
    {
      org.meta_environment.rascal.ast.Assert z = new Assert ();
      z.$setLabel (x);
      return z;
    }
    private org.meta_environment.rascal.ast.Expression expression;
    public org.meta_environment.rascal.ast.Expression getExpression ()
    {
      return expression;
    }
    private void $setExpression (org.meta_environment.rascal.ast.Expression x)
    {
      this.expression = x;
    }
    public org.meta_environment.rascal.ast.Assert setExpression (org.
								 meta_environment.
								 rascal.ast.
								 Expression x)
    {
      org.meta_environment.rascal.ast.Assert z = new Assert ();
      z.$setExpression (x);
      return z;
    }
  }
  static public class Insert extends Statement
  {
/* "insert" expression:Expression ";" -> Statement {cons("Insert")} */
    private Insert ()
    {
    }
    /*package */ Insert (ITree tree,
			 org.meta_environment.rascal.ast.
			 Expression expression)
    {
      this.tree = tree;
      this.expression = expression;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitStatementInsert (this);
    }
    private org.meta_environment.rascal.ast.Expression expression;
    public org.meta_environment.rascal.ast.Expression getExpression ()
    {
      return expression;
    }
    private void $setExpression (org.meta_environment.rascal.ast.Expression x)
    {
      this.expression = x;
    }
    public org.meta_environment.rascal.ast.Insert setExpression (org.
								 meta_environment.
								 rascal.ast.
								 Expression x)
    {
      org.meta_environment.rascal.ast.Insert z = new Insert ();
      z.$setExpression (x);
      return z;
    }
  }
  static public class Throw extends Statement
  {
/* "throw" expression:Expression ";" -> Statement {cons("Throw")} */
    private Throw ()
    {
    }
    /*package */ Throw (ITree tree,
			org.meta_environment.rascal.ast.Expression expression)
    {
      this.tree = tree;
      this.expression = expression;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitStatementThrow (this);
    }
    private org.meta_environment.rascal.ast.Expression expression;
    public org.meta_environment.rascal.ast.Expression getExpression ()
    {
      return expression;
    }
    private void $setExpression (org.meta_environment.rascal.ast.Expression x)
    {
      this.expression = x;
    }
    public org.meta_environment.rascal.ast.Throw setExpression (org.
								meta_environment.
								rascal.ast.
								Expression x)
    {
      org.meta_environment.rascal.ast.Throw z = new Throw ();
      z.$setExpression (x);
      return z;
    }
  }
  static public class Try extends Statement
  {
/* "try" body:Statement handlers:Catch+ -> Statement {non-assoc, cons("Try")} */
    private Try ()
    {
    }
    /*package */ Try (ITree tree,
		      org.meta_environment.rascal.ast.Statement body,
		      java.util.List < Catch > handlers)
    {
      this.tree = tree;
      this.body = body;
      this.handlers = handlers;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitStatementTry (this);
    }
    private org.meta_environment.rascal.ast.Statement body;
    public org.meta_environment.rascal.ast.Statement getBody ()
    {
      return body;
    }
    private void $setBody (org.meta_environment.rascal.ast.Statement x)
    {
      this.body = x;
    }
    public org.meta_environment.rascal.ast.Try setBody (org.meta_environment.
							rascal.ast.
							Statement x)
    {
      org.meta_environment.rascal.ast.Try z = new Try ();
      z.$setBody (x);
      return z;
    }
    private java.util.List < org.meta_environment.rascal.ast.Catch > handlers;
    public java.util.List < org.meta_environment.rascal.ast.Catch >
      getHandlers ()
    {
      return handlers;
    }
    private void $setHandlers (java.util.List <
			       org.meta_environment.rascal.ast.Catch > x)
    {
      this.handlers = x;
    }
    public org.meta_environment.rascal.ast.Try setHandlers (java.util.List <
							    org.
							    meta_environment.
							    rascal.ast.Catch >
							    x)
    {
      org.meta_environment.rascal.ast.Try z = new Try ();
      z.$setHandlers (x);
      return z;
    }
  }
  static public class TryFinally extends Statement
  {
/* "try" body:Statement handlers:Catch+ "finally" finallyBody:Statement -> Statement {cons("TryFinally")} */
    private TryFinally ()
    {
    }
    /*package */ TryFinally (ITree tree,
			     org.meta_environment.rascal.ast.Statement body,
			     java.util.List < Catch > handlers,
			     org.meta_environment.rascal.ast.
			     Statement finallyBody)
    {
      this.tree = tree;
      this.body = body;
      this.handlers = handlers;
      this.finallyBody = finallyBody;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitStatementTryFinally (this);
    }
    private org.meta_environment.rascal.ast.Statement body;
    public org.meta_environment.rascal.ast.Statement getBody ()
    {
      return body;
    }
    private void $setBody (org.meta_environment.rascal.ast.Statement x)
    {
      this.body = x;
    }
    public org.meta_environment.rascal.ast.TryFinally setBody (org.
							       meta_environment.
							       rascal.ast.
							       Statement x)
    {
      org.meta_environment.rascal.ast.TryFinally z = new TryFinally ();
      z.$setBody (x);
      return z;
    }
    private java.util.List < org.meta_environment.rascal.ast.Catch > handlers;
    public java.util.List < org.meta_environment.rascal.ast.Catch >
      getHandlers ()
    {
      return handlers;
    }
    private void $setHandlers (java.util.List <
			       org.meta_environment.rascal.ast.Catch > x)
    {
      this.handlers = x;
    }
    public org.meta_environment.rascal.ast.TryFinally setHandlers (java.util.
								   List <
								   org.
								   meta_environment.
								   rascal.ast.
								   Catch > x)
    {
      org.meta_environment.rascal.ast.TryFinally z = new TryFinally ();
      z.$setHandlers (x);
      return z;
    }
    private org.meta_environment.rascal.ast.Statement finallyBody;
    public org.meta_environment.rascal.ast.Statement getFinallyBody ()
    {
      return finallyBody;
    }
    private void $setFinallyBody (org.meta_environment.rascal.ast.Statement x)
    {
      this.finallyBody = x;
    }
    public org.meta_environment.rascal.ast.TryFinally setFinallyBody (org.
								      meta_environment.
								      rascal.
								      ast.
								      Statement
								      x)
    {
      org.meta_environment.rascal.ast.TryFinally z = new TryFinally ();
      z.$setFinallyBody (x);
      return z;
    }
  }
  static public class Block extends Statement
  {
/* label:Label "{" statements:Statement* "}" -> Statement {cons("Block")} */
    private Block ()
    {
    }
    /*package */ Block (ITree tree,
			org.meta_environment.rascal.ast.Label label,
			java.util.List < Statement > statements)
    {
      this.tree = tree;
      this.label = label;
      this.statements = statements;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitStatementBlock (this);
    }
    private org.meta_environment.rascal.ast.Label label;
    public org.meta_environment.rascal.ast.Label getLabel ()
    {
      return label;
    }
    private void $setLabel (org.meta_environment.rascal.ast.Label x)
    {
      this.label = x;
    }
    public org.meta_environment.rascal.ast.Block setLabel (org.
							   meta_environment.
							   rascal.ast.Label x)
    {
      org.meta_environment.rascal.ast.Block z = new Block ();
      z.$setLabel (x);
      return z;
    }
    private java.util.List < org.meta_environment.rascal.ast.Statement >
      statements;
    public java.util.List < org.meta_environment.rascal.ast.Statement >
      getStatements ()
    {
      return statements;
    }
    private void $setStatements (java.util.List <
				 org.meta_environment.rascal.ast.Statement >
				 x)
    {
      this.statements = x;
    }
    public org.meta_environment.rascal.ast.Block setStatements (java.util.
								List <
								org.
								meta_environment.
								rascal.ast.
								Statement > x)
    {
      org.meta_environment.rascal.ast.Block z = new Block ();
      z.$setStatements (x);
      return z;
    }
  }
  static public class FunctionDeclaration extends Statement
  {
/* functionDeclaration:FunctionDeclaration -> Statement {cons("FunctionDeclaration")} */
    private FunctionDeclaration ()
    {
    }
    /*package */ FunctionDeclaration (ITree tree,
				      org.meta_environment.rascal.ast.
				      FunctionDeclaration functionDeclaration)
    {
      this.tree = tree;
      this.functionDeclaration = functionDeclaration;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitStatementFunctionDeclaration (this);
    }
    private org.meta_environment.rascal.ast.
      FunctionDeclaration functionDeclaration;
    public org.meta_environment.rascal.ast.
      FunctionDeclaration getFunctionDeclaration ()
    {
      return functionDeclaration;
    }
    private void $setFunctionDeclaration (org.meta_environment.rascal.ast.
					  FunctionDeclaration x)
    {
      this.functionDeclaration = x;
    }
    public org.meta_environment.rascal.ast.
      FunctionDeclaration setFunctionDeclaration (org.meta_environment.rascal.
						  ast.FunctionDeclaration x)
    {
      org.meta_environment.rascal.ast.FunctionDeclaration z =
	new FunctionDeclaration ();
      z.$setFunctionDeclaration (x);
      return z;
    }
  }
  static public class VariableDeclaration extends Statement
  {
/* declaration:LocalVariableDeclaration ";" -> Statement {cons("VariableDeclaration")} */
    private VariableDeclaration ()
    {
    }
    /*package */ VariableDeclaration (ITree tree,
				      org.meta_environment.rascal.ast.
				      LocalVariableDeclaration declaration)
    {
      this.tree = tree;
      this.declaration = declaration;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitStatementVariableDeclaration (this);
    }
    private org.meta_environment.rascal.ast.
      LocalVariableDeclaration declaration;
    public org.meta_environment.rascal.ast.
      LocalVariableDeclaration getDeclaration ()
    {
      return declaration;
    }
    private void $setDeclaration (org.meta_environment.rascal.ast.
				  LocalVariableDeclaration x)
    {
      this.declaration = x;
    }
    public org.meta_environment.rascal.ast.
      VariableDeclaration setDeclaration (org.meta_environment.rascal.ast.
					  LocalVariableDeclaration x)
    {
      org.meta_environment.rascal.ast.VariableDeclaration z =
	new VariableDeclaration ();
      z.$setDeclaration (x);
      return z;
    }
  }
  static public class GlobalDirective extends Statement
  {
/* "global" type:Type names:{QualifiedName ","}+ ";" -> Statement {cons("GlobalDirective")} */
    private GlobalDirective ()
    {
    }
    /*package */ GlobalDirective (ITree tree,
				  org.meta_environment.rascal.ast.Type type,
				  java.util.List < QualifiedName > names)
    {
      this.tree = tree;
      this.type = type;
      this.names = names;
    }
    public IVisitable accept (IASTVisitor visitor)
    {
      return visitor.visitStatementGlobalDirective (this);
    }
    private org.meta_environment.rascal.ast.Type type;
    public org.meta_environment.rascal.ast.Type getType ()
    {
      return type;
    }
    private void $setType (org.meta_environment.rascal.ast.Type x)
    {
      this.type = x;
    }
    public org.meta_environment.rascal.ast.GlobalDirective setType (org.
								    meta_environment.
								    rascal.
								    ast.
								    Type x)
    {
      org.meta_environment.rascal.ast.GlobalDirective z =
	new GlobalDirective ();
      z.$setType (x);
      return z;
    }
    private java.util.List < org.meta_environment.rascal.ast.QualifiedName >
      names;
    public java.util.List < org.meta_environment.rascal.ast.QualifiedName >
      getNames ()
    {
      return names;
    }
    private void $setNames (java.util.List <
			    org.meta_environment.rascal.ast.QualifiedName > x)
    {
      this.names = x;
    }
    public org.meta_environment.rascal.ast.GlobalDirective setNames (java.
								     util.
								     List <
								     org.
								     meta_environment.
								     rascal.
								     ast.
								     QualifiedName
								     > x)
    {
      org.meta_environment.rascal.ast.GlobalDirective z =
	new GlobalDirective ();
      z.$setNames (x);
      return z;
    }
  }
}
