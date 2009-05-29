package org.meta_environment.rascal.interpreter;

import java.io.Writer;

import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.meta_environment.rascal.ast.ASTFactory;
import org.meta_environment.rascal.ast.Expression.Addition;
import org.meta_environment.rascal.ast.Expression.All;
import org.meta_environment.rascal.ast.Expression.Ambiguity;
import org.meta_environment.rascal.ast.Expression.And;
import org.meta_environment.rascal.ast.Expression.Anti;
import org.meta_environment.rascal.ast.Expression.Any;
import org.meta_environment.rascal.ast.Expression.Bracket;
import org.meta_environment.rascal.ast.Expression.CallOrTree;
import org.meta_environment.rascal.ast.Expression.Closure;
import org.meta_environment.rascal.ast.Expression.ClosureCall;
import org.meta_environment.rascal.ast.Expression.Composition;
import org.meta_environment.rascal.ast.Expression.Comprehension;
import org.meta_environment.rascal.ast.Expression.ConcreteQuoted;
import org.meta_environment.rascal.ast.Expression.ConcreteUnquoted;
import org.meta_environment.rascal.ast.Expression.Descendant;
import org.meta_environment.rascal.ast.Expression.Division;
import org.meta_environment.rascal.ast.Expression.Enumerator;
import org.meta_environment.rascal.ast.Expression.EnumeratorWithStrategy;
import org.meta_environment.rascal.ast.Expression.Equals;
import org.meta_environment.rascal.ast.Expression.Equivalence;
import org.meta_environment.rascal.ast.Expression.FieldAccess;
import org.meta_environment.rascal.ast.Expression.FieldProject;
import org.meta_environment.rascal.ast.Expression.FieldUpdate;
import org.meta_environment.rascal.ast.Expression.FunctionAsValue;
import org.meta_environment.rascal.ast.Expression.GetAnnotation;
import org.meta_environment.rascal.ast.Expression.GreaterThan;
import org.meta_environment.rascal.ast.Expression.GreaterThanOrEq;
import org.meta_environment.rascal.ast.Expression.Guarded;
import org.meta_environment.rascal.ast.Expression.IfDefinedOtherwise;
import org.meta_environment.rascal.ast.Expression.IfThenElse;
import org.meta_environment.rascal.ast.Expression.Implication;
import org.meta_environment.rascal.ast.Expression.In;
import org.meta_environment.rascal.ast.Expression.Intersection;
import org.meta_environment.rascal.ast.Expression.IsDefined;
import org.meta_environment.rascal.ast.Expression.Join;
import org.meta_environment.rascal.ast.Expression.LessThan;
import org.meta_environment.rascal.ast.Expression.LessThanOrEq;
import org.meta_environment.rascal.ast.Expression.Lexical;
import org.meta_environment.rascal.ast.Expression.List;
import org.meta_environment.rascal.ast.Expression.Literal;
import org.meta_environment.rascal.ast.Expression.Location;
import org.meta_environment.rascal.ast.Expression.Map;
import org.meta_environment.rascal.ast.Expression.Match;
import org.meta_environment.rascal.ast.Expression.Modulo;
import org.meta_environment.rascal.ast.Expression.MultiVariable;
import org.meta_environment.rascal.ast.Expression.Negation;
import org.meta_environment.rascal.ast.Expression.Negative;
import org.meta_environment.rascal.ast.Expression.NoMatch;
import org.meta_environment.rascal.ast.Expression.NonEmptyBlock;
import org.meta_environment.rascal.ast.Expression.NonEquals;
import org.meta_environment.rascal.ast.Expression.NotIn;
import org.meta_environment.rascal.ast.Expression.OperatorAsValue;
import org.meta_environment.rascal.ast.Expression.Or;
import org.meta_environment.rascal.ast.Expression.Product;
import org.meta_environment.rascal.ast.Expression.QualifiedName;
import org.meta_environment.rascal.ast.Expression.Range;
import org.meta_environment.rascal.ast.Expression.Set;
import org.meta_environment.rascal.ast.Expression.SetAnnotation;
import org.meta_environment.rascal.ast.Expression.StepRange;
import org.meta_environment.rascal.ast.Expression.Subscript;
import org.meta_environment.rascal.ast.Expression.Subtraction;
import org.meta_environment.rascal.ast.Expression.TransitiveClosure;
import org.meta_environment.rascal.ast.Expression.TransitiveReflexiveClosure;
import org.meta_environment.rascal.ast.Expression.Tuple;
import org.meta_environment.rascal.ast.Expression.TypedVariable;
import org.meta_environment.rascal.ast.Expression.TypedVariableBecomes;
import org.meta_environment.rascal.ast.Expression.VariableBecomes;
import org.meta_environment.rascal.ast.Expression.Visit;
import org.meta_environment.rascal.ast.Expression.VoidClosure;
import org.meta_environment.rascal.ast.Statement.Assert;
import org.meta_environment.rascal.ast.Statement.AssertWithMessage;
import org.meta_environment.rascal.ast.Statement.Assignment;
import org.meta_environment.rascal.ast.Statement.Block;
import org.meta_environment.rascal.ast.Statement.Break;
import org.meta_environment.rascal.ast.Statement.Continue;
import org.meta_environment.rascal.ast.Statement.DoWhile;
import org.meta_environment.rascal.ast.Statement.EmptyStatement;
import org.meta_environment.rascal.ast.Statement.Expression;
import org.meta_environment.rascal.ast.Statement.Fail;
import org.meta_environment.rascal.ast.Statement.For;
import org.meta_environment.rascal.ast.Statement.FunctionDeclaration;
import org.meta_environment.rascal.ast.Statement.GlobalDirective;
import org.meta_environment.rascal.ast.Statement.IfThen;
import org.meta_environment.rascal.ast.Statement.Insert;
import org.meta_environment.rascal.ast.Statement.Return;
import org.meta_environment.rascal.ast.Statement.Solve;
import org.meta_environment.rascal.ast.Statement.Switch;
import org.meta_environment.rascal.ast.Statement.Throw;
import org.meta_environment.rascal.ast.Statement.Try;
import org.meta_environment.rascal.ast.Statement.TryFinally;
import org.meta_environment.rascal.ast.Statement.VariableDeclaration;
import org.meta_environment.rascal.ast.Statement.While;
import org.meta_environment.rascal.interpreter.env.ModuleEnvironment;
import org.meta_environment.rascal.interpreter.result.Result;

public class DebuggableEvaluator extends Evaluator {

	protected IDebugger debugger;
	private boolean suspendRequest;

	public DebuggableEvaluator(IValueFactory f, ASTFactory astFactory,
			Writer errorWriter, ModuleEnvironment scope, IDebugger debugger) {
		super(f, astFactory, errorWriter, scope);
		this.debugger = debugger;
	}

	@Override
	public Result<IValue> visitExpressionAnti(Anti x) {
		suspendExpression();
		return super.visitExpressionAnti(x);
	}

	@Override
	public Result<IValue> visitExpressionAddition(Addition x) {
		suspendExpression();
		return super.visitExpressionAddition(x);
	}

	@Override
	public Result visitExpressionAll(All x) {
		suspendExpression();
		return super.visitExpressionAll(x);
	}

	@Override
	public Result<IValue> visitExpressionAmbiguity(Ambiguity x) {
		suspendExpression();
		return super.visitExpressionAmbiguity(x);
	}

	@Override
	public Result<IValue> visitExpressionAnd(And x) {
		suspendExpression();
		return super.visitExpressionAnd(x);
	}


	@Override
	public Result visitExpressionAny(Any x) {
		suspendExpression();
		return super.visitExpressionAny(x);
	}

	@Override
	public Result<IValue> visitExpressionBracket(Bracket x) {
		suspendExpression();
		return super.visitExpressionBracket(x);
	}

	@Override
	public Result<IValue> visitExpressionCallOrTree(CallOrTree x) {
		suspendExpression();
		return super.visitExpressionCallOrTree(x);
	}

	@Override
	public Result visitExpressionClosure(Closure x) {
		suspendExpression();
		return super.visitExpressionClosure(x);
	}

	@Override
	public Result<IValue> visitExpressionClosureCall(ClosureCall x) {
		suspendExpression();
		return super.visitExpressionClosureCall(x);
	}

	@Override
	public Result<IValue> visitExpressionComposition(Composition x) {
		suspendExpression();
		return super.visitExpressionComposition(x);
	}

	@Override
	public Result<IValue> visitExpressionComprehension(Comprehension x) {
		suspendExpression();
		return super.visitExpressionComprehension(x);
	}

	@Override
	public Result<IValue> visitExpressionConcreteQuoted(ConcreteQuoted x) {
		suspendExpression();
		return super.visitExpressionConcreteQuoted(x);
	}


	@Override
	public Result<IValue> visitExpressionConcreteUnquoted(ConcreteUnquoted x) {
		suspendExpression();
		return super.visitExpressionConcreteUnquoted(x);
	}

	@Override
	public Result<IValue> visitExpressionDescendant(Descendant x) {
		suspendExpression();
		return super.visitExpressionDescendant(x);
	}


	@Override
	public Result<IValue> visitExpressionDivision(Division x) {
		suspendExpression();
		return super.visitExpressionDivision(x);
	}

	@Override
	public Result<IValue> visitExpressionEnumerator(Enumerator x) {
		suspendExpression();
		return super.visitExpressionEnumerator(x);
	}

	@Override
	public Result<IValue> visitExpressionEnumeratorWithStrategy(
			EnumeratorWithStrategy x) {
		suspendExpression();
		return super.visitExpressionEnumeratorWithStrategy(x);
	}

	@Override
	public Result<IValue> visitExpressionEquals(Equals x) {
		suspendExpression();
		return super.visitExpressionEquals(x);
	}

	@Override
	public Result<IValue> visitExpressionEquivalence(Equivalence x) {
		suspendExpression();
		return super.visitExpressionEquivalence(x);
	}

	@Override
	public Result<IValue> visitExpressionFieldAccess(FieldAccess x) {
		suspendExpression();
		return super.visitExpressionFieldAccess(x);
	}

	@Override
	public Result<IValue> visitExpressionFieldProject(FieldProject x) {
		suspendExpression();
		return super.visitExpressionFieldProject(x);
	}

	@Override
	public Result<IValue> visitExpressionFieldUpdate(FieldUpdate x) {
		suspendExpression();
		return super.visitExpressionFieldUpdate(x);
	}

	@Override
	public Result<IValue> visitExpressionFunctionAsValue(FunctionAsValue x) {
		suspendExpression();
		return super.visitExpressionFunctionAsValue(x);
	}


	@Override
	public Result<IValue> visitExpressionGetAnnotation(GetAnnotation x) {
		suspendExpression();
		return super.visitExpressionGetAnnotation(x);
	}


	@Override
	public Result<IValue> visitExpressionGreaterThan(GreaterThan x) {
		suspendExpression();
		return super.visitExpressionGreaterThan(x);
	}

	@Override
	public Result<IValue> visitExpressionGreaterThanOrEq(GreaterThanOrEq x) {
		suspendExpression();
		return super.visitExpressionGreaterThanOrEq(x);
	}


	@Override
	public Result<IValue> visitExpressionGuarded(Guarded x) {
		suspendExpression();
		return super.visitExpressionGuarded(x);
	}

	@Override
	public Result<IValue> visitExpressionIfDefinedOtherwise(IfDefinedOtherwise x) {
		suspendExpression();
		return super.visitExpressionIfDefinedOtherwise(x);
	}




	@Override
	public Result<IValue> visitExpressionIfThenElse(IfThenElse x) {
		suspendExpression();
		return super.visitExpressionIfThenElse(x);
	}


	@Override
	public Result<IValue> visitExpressionImplication(Implication x) {
		suspendExpression();
		return super.visitExpressionImplication(x);
	}

	@Override
	public Result<IValue> visitExpressionIn(In x) {
		suspendExpression();
		return super.visitExpressionIn(x);
	}

	@Override
	public Result<IValue> visitExpressionIntersection(Intersection x) {
		suspendExpression();
		return super.visitExpressionIntersection(x);
	}

	@Override
	public Result<IValue> visitExpressionIsDefined(IsDefined x) {
		suspendExpression();
		return super.visitExpressionIsDefined(x);
	}

	@Override
	public Result<IValue> visitExpressionJoin(Join x) {
		suspendExpression();
		return super.visitExpressionJoin(x);
	}

	@Override
	public Result<IValue> visitExpressionLessThan(LessThan x) {
		suspendExpression();
		return super.visitExpressionLessThan(x);
	}

	@Override
	public Result<IValue> visitExpressionLessThanOrEq(LessThanOrEq x) {
		suspendExpression();
		return super.visitExpressionLessThanOrEq(x);
	}

	@Override
	public Result<IValue> visitExpressionLexical(Lexical x) {
		suspendExpression();
		return super.visitExpressionLexical(x);
	}

	@Override
	public Result<IValue> visitExpressionList(List x) {
		suspendExpression();
		return super.visitExpressionList(x);
	}

	@Override
	public Result<IValue> visitExpressionLiteral(Literal x) {
		suspendExpression();
		return super.visitExpressionLiteral(x);
	}

	@Override
	public Result<IValue> visitExpressionLocation(Location x) {
		suspendExpression();
		return super.visitExpressionLocation(x);
	}

	@Override
	public Result<IValue> visitExpressionMap(Map x) {
		suspendExpression();
		return super.visitExpressionMap(x);
	}

	@Override
	public Result<IValue> visitExpressionMatch(Match x) {
		suspendExpression();
		return super.visitExpressionMatch(x);
	}

	@Override
	public Result<IValue> visitExpressionModulo(Modulo x) {
		suspendExpression();
		return super.visitExpressionModulo(x);
	}

	@Override
	public Result<IValue> visitExpressionMultiVariable(MultiVariable x) {
		suspendExpression();
		return super.visitExpressionMultiVariable(x);
	}

	@Override
	public Result<IValue> visitExpressionNegation(Negation x) {
		suspendExpression();
		return super.visitExpressionNegation(x);
	}

	@Override
	public Result<IValue> visitExpressionNegative(Negative x) {
		suspendExpression();
		return super.visitExpressionNegative(x);
	}

	@Override
	public Result<IValue> visitExpressionNoMatch(NoMatch x) {
		suspendExpression();
		return super.visitExpressionNoMatch(x);
	}

	@Override
	public Result visitExpressionNonEmptyBlock(NonEmptyBlock x) {
		suspendExpression();
		return super.visitExpressionNonEmptyBlock(x);
	}

	@Override
	public Result<IValue> visitExpressionNonEquals(NonEquals x) {
		suspendExpression();
		return super.visitExpressionNonEquals(x);
	}

	@Override
	public Result<IValue> visitExpressionNotIn(NotIn x) {
		suspendExpression();
		return super.visitExpressionNotIn(x);
	}

	@Override
	public Result<IValue> visitExpressionOperatorAsValue(OperatorAsValue x) {
		suspendExpression();
		return super.visitExpressionOperatorAsValue(x);
	}
	@Override
	public Result<IValue> visitExpressionOr(Or x) {
		suspendExpression();
		return super.visitExpressionOr(x);
	}

	@Override
	public Result<IValue> visitExpressionProduct(Product x) {
		suspendExpression();
		return super.visitExpressionProduct(x);
	}

	@Override
	public Result<IValue> visitExpressionQualifiedName(QualifiedName x) {
		suspendExpression();
		return super.visitExpressionQualifiedName(x);
	}

	@Override
	public Result<IValue> visitExpressionRange(Range x) {
		suspendExpression();
		return super.visitExpressionRange(x);
	}

	@Override
	public Result<IValue> visitExpressionSet(Set x) {
		suspendExpression();
		return super.visitExpressionSet(x);
	}

	@Override
	public Result<IValue> visitExpressionSetAnnotation(SetAnnotation x) {
		suspendExpression();
		return super.visitExpressionSetAnnotation(x);
	}

	@Override
	public Result<IValue> visitExpressionStepRange(StepRange x) {
		suspendExpression();
		return super.visitExpressionStepRange(x);
	}

	@Override
	public Result<IValue> visitExpressionSubscript(Subscript x) {
		suspendExpression();
		return super.visitExpressionSubscript(x);
	}

	@Override
	public Result<IValue> visitExpressionSubtraction(Subtraction x) {
		suspendExpression();
		return super.visitExpressionSubtraction(x);
	}

	@Override
	public Result<IValue> visitExpressionTransitiveClosure(TransitiveClosure x) {
		suspendExpression();
		return super.visitExpressionTransitiveClosure(x);
	}

	@Override
	public Result<IValue> visitExpressionTransitiveReflexiveClosure(
			TransitiveReflexiveClosure x) {
		suspendExpression();
		return super.visitExpressionTransitiveReflexiveClosure(x);
	}


	@Override
	public Result<IValue> visitExpressionTuple(Tuple x) {
		suspendExpression();
		return super.visitExpressionTuple(x);
	}

	@Override
	public Result<IValue> visitExpressionTypedVariable(TypedVariable x) {
		suspendExpression();
		return super.visitExpressionTypedVariable(x);
	}

	@Override
	public Result<IValue> visitExpressionTypedVariableBecomes(
			TypedVariableBecomes x) {
		suspendExpression();
		return super.visitExpressionTypedVariableBecomes(x);
	}

	@Override
	public Result<IValue> visitExpressionVariableBecomes(VariableBecomes x) {
		suspendExpression();
		return super.visitExpressionVariableBecomes(x);
	}

	@Override
	public Result<IValue> visitEscapedNameAmbiguity(
			org.meta_environment.rascal.ast.EscapedName.Ambiguity x) {
		suspendExpression();
		return super.visitEscapedNameAmbiguity(x);
	}

	@Override
	public Result<IValue> visitExpressionVisit(Visit x) {
		suspendExpression();
		return super.visitExpressionVisit(x);
	}

	@Override
	public Result visitExpressionVoidClosure(VoidClosure x) {
		suspendExpression();
		return super.visitExpressionVoidClosure(x);
	}

	@Override
	public Result<IValue> visitStatementAmbiguity(
			org.meta_environment.rascal.ast.Statement.Ambiguity x) {
		suspendStatement();
		return super.visitStatementAmbiguity(x);
	}

	@Override
	public Result<IValue> visitStatementAssert(Assert x) {
		suspendStatement();
		return super.visitStatementAssert(x);
	}

	@Override
	public Result<IValue> visitStatementAssertWithMessage(AssertWithMessage x) {
		suspendStatement();
		return super.visitStatementAssertWithMessage(x);
	}

	@Override
	public Result<IValue> visitStatementAssignment(Assignment x) {
		suspendStatement();
		return super.visitStatementAssignment(x);
	}
	@Override
	public Result<IValue> visitStatementBlock(Block x) {
		suspendStatement();
		return super.visitStatementBlock(x);
	}
	@Override
	public Result<IValue> visitStatementBreak(Break x) {
		suspendStatement();
		return super.visitStatementBreak(x);
	}

	@Override
	public Result<IValue> visitStatementContinue(Continue x) {
		suspendStatement();
		return super.visitStatementContinue(x);
	}

	@Override
	public Result<IValue> visitStatementDoWhile(DoWhile x) {
		suspendStatement();
		return super.visitStatementDoWhile(x);
	}

	@Override
	public Result<IValue> visitStatementEmptyStatement(EmptyStatement x) {
		suspendStatement();
		return super.visitStatementEmptyStatement(x);
	}

	@Override
	public Result<IValue> visitStatementExpression(Expression x) {
		//do not need to call suspendStatement if expressionMode is enabled
		if (! expressionModeEnabled()) {
			suspendStatement();
		}
		return super.visitStatementExpression(x);
	}

	@Override
	public Result<IValue> visitStatementFail(Fail x) {
		suspendStatement();
		return super.visitStatementFail(x);
	}

	@Override
	public Result<IValue> visitStatementFor(For x) {
		suspendStatement();
		return super.visitStatementFor(x);
	}

	@Override
	public Result<IValue> visitStatementFunctionDeclaration(
			FunctionDeclaration x) {
		suspendStatement();
		return super.visitStatementFunctionDeclaration(x);
	}

	@Override
	public Result<IValue> visitStatementGlobalDirective(GlobalDirective x) {
		suspendStatement();
		return super.visitStatementGlobalDirective(x);
	}

	@Override
	public Result<IValue> visitStatementIfThen(IfThen x) {
		suspendStatement();
		return super.visitStatementIfThen(x);
	}

	@Override
	public Result<IValue> visitStatementIfThenElse(
			org.meta_environment.rascal.ast.Statement.IfThenElse x) {
		suspendStatement();
		return super.visitStatementIfThenElse(x);
	}

	@Override
	public Result<IValue> visitStatementInsert(Insert x) {
		suspendStatement();
		return super.visitStatementInsert(x);
	}

	@Override
	public Result<IValue> visitStatementReturn(Return x) {
		suspendStatement();
		return super.visitStatementReturn(x);
	}

	@Override
	public Result<IValue> visitStatementSolve(Solve x) {
		suspendStatement();
		return super.visitStatementSolve(x);
	}

	@Override
	public Result<IValue> visitStatementSwitch(Switch x) {
		suspendStatement();
		return super.visitStatementSwitch(x);
	}

	@Override
	public Result<IValue> visitStatementThrow(Throw x) {
		suspendStatement();
		return super.visitStatementThrow(x);
	}

	@Override
	public Result<IValue> visitStatementTry(Try x) {
		suspendStatement();
		return super.visitStatementTry(x);
	}

	@Override
	public Result<IValue> visitStatementTryFinally(TryFinally x) {
		suspendStatement();
		return super.visitStatementTryFinally(x);
	}

	@Override
	public Result<IValue> visitStatementVariableDeclaration(
			VariableDeclaration x) {
		suspendStatement();
		return super.visitStatementVariableDeclaration(x);
	}

	@Override
	public Result<IValue> visitStatementVisit(
			org.meta_environment.rascal.ast.Statement.Visit x) {
		suspendStatement();
		return super.visitStatementVisit(x);
	}

	@Override
	public Result<IValue> visitStatementWhile(While x) {
		suspendStatement();
		return super.visitStatementWhile(x);
	}

	private void suspendExpression() {
		if(suspendRequest) {
			debugger.notifySuspend();
			suspendRequest = false;
		} else if (expressionModeEnabled() && debugger.isStepping()) {
			debugger.notifySuspend();
		}	
	}

	private void suspendStatement() {
		if(suspendRequest) {
			debugger.notifySuspend();
			suspendRequest = false;
		} else if (statementModeEnabled()) {
			if (debugger.isStepping() || debugger.hasEnabledBreakpoint(getCurrentAST().getLocation())) {
				debugger.notifySuspend();
			}
		}
	}

	/** 
	 * this method is called when the debugger send a suspend request 
	 * correspond to a suspend event from the client
	 * 
	 * */
	public void suspendRequest() {
		// the evaluator will suspend itself at the next call of suspendStatement or suspend Expression
		suspendRequest = true;
	}

	private boolean expressionModeEnabled() {
		// TODO: To implement
		return false;
	}

	private boolean statementModeEnabled() {
		// TODO: To implement
		return true;
	}

}
