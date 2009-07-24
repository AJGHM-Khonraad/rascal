package org.meta_environment.rascal.interpreter;

import org.eclipse.imp.pdb.facts.IValueFactory;
import org.eclipse.imp.pdb.facts.type.TypeFactory;
import org.meta_environment.rascal.ast.NullASTVisitor;
import org.meta_environment.rascal.ast.Expression.Addition;
import org.meta_environment.rascal.ast.Expression.All;
import org.meta_environment.rascal.ast.Expression.And;
import org.meta_environment.rascal.ast.Expression.Anti;
import org.meta_environment.rascal.ast.Expression.Any;
import org.meta_environment.rascal.ast.Expression.Bracket;
import org.meta_environment.rascal.ast.Expression.CallOrTree;
import org.meta_environment.rascal.ast.Expression.Closure;
import org.meta_environment.rascal.ast.Expression.Composition;
import org.meta_environment.rascal.ast.Expression.Comprehension;
import org.meta_environment.rascal.ast.Expression.Descendant;
import org.meta_environment.rascal.ast.Expression.Enumerator;
import org.meta_environment.rascal.ast.Expression.EnumeratorWithStrategy;
import org.meta_environment.rascal.ast.Expression.Equals;
import org.meta_environment.rascal.ast.Expression.Equivalence;
import org.meta_environment.rascal.ast.Expression.FieldProject;
import org.meta_environment.rascal.ast.Expression.FieldUpdate;
import org.meta_environment.rascal.ast.Expression.GetAnnotation;
import org.meta_environment.rascal.ast.Expression.GreaterThan;
import org.meta_environment.rascal.ast.Expression.GreaterThanOrEq;
import org.meta_environment.rascal.ast.Expression.Guarded;
import org.meta_environment.rascal.ast.Expression.IfThenElse;
import org.meta_environment.rascal.ast.Expression.Implication;
import org.meta_environment.rascal.ast.Expression.In;
import org.meta_environment.rascal.ast.Expression.LessThan;
import org.meta_environment.rascal.ast.Expression.LessThanOrEq;
import org.meta_environment.rascal.ast.Expression.List;
import org.meta_environment.rascal.ast.Expression.Literal;
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
import org.meta_environment.rascal.ast.Expression.QualifiedName;
import org.meta_environment.rascal.ast.Expression.Range;
import org.meta_environment.rascal.ast.Expression.Set;
import org.meta_environment.rascal.ast.Expression.SetAnnotation;
import org.meta_environment.rascal.ast.Expression.StepRange;
import org.meta_environment.rascal.ast.Expression.TransitiveClosure;
import org.meta_environment.rascal.ast.Expression.TransitiveReflexiveClosure;
import org.meta_environment.rascal.ast.Expression.Tuple;
import org.meta_environment.rascal.ast.Expression.TypedVariable;
import org.meta_environment.rascal.ast.Expression.TypedVariableBecomes;
import org.meta_environment.rascal.ast.Expression.VariableBecomes;
import org.meta_environment.rascal.ast.Expression.Visit;
import org.meta_environment.rascal.ast.Expression.VoidClosure;
import org.meta_environment.rascal.interpreter.asserts.ImplementationError;
import org.meta_environment.rascal.interpreter.matching.AndResult;
import org.meta_environment.rascal.interpreter.matching.BasicBooleanResult;
import org.meta_environment.rascal.interpreter.matching.EnumeratorResult;
import org.meta_environment.rascal.interpreter.matching.EquivalenceResult;
import org.meta_environment.rascal.interpreter.matching.IBooleanResult;
import org.meta_environment.rascal.interpreter.matching.IMatchingResult;
import org.meta_environment.rascal.interpreter.matching.MatchResult;
import org.meta_environment.rascal.interpreter.matching.NotResult;
import org.meta_environment.rascal.interpreter.matching.OrResult;
import org.meta_environment.rascal.interpreter.staticErrors.SyntaxError;
import org.meta_environment.rascal.interpreter.staticErrors.UnexpectedTypeError;
import org.meta_environment.rascal.interpreter.staticErrors.UninitializedVariableError;

public class BooleanEvaluator extends NullASTVisitor<IBooleanResult> {
	private final IValueFactory vf;
	private final EvaluatorContext ctx;
	private final TypeFactory tf = TypeFactory.getInstance();
	private final PatternEvaluator pe;

	public BooleanEvaluator(IValueFactory vf, EvaluatorContext ctx) {
		this.vf = vf;
		this.ctx = ctx;
		this.pe = new PatternEvaluator(vf, ctx);
	}

	@Override
	public IBooleanResult visitExpressionLiteral(Literal x) {
		if (x.getLiteral().isBoolean()) {
			return new BasicBooleanResult(vf, ctx, x);
		} else {
			throw new UnexpectedTypeError(tf.boolType(), x.accept(ctx.getEvaluator()).getType(), x);
		}
	}

	@Override
	public IBooleanResult visitExpressionCallOrTree(CallOrTree x) {
		return new BasicBooleanResult(vf, ctx, x);
	}

	@Override
	public IBooleanResult visitExpressionList(List x) {
		throw new UnexpectedTypeError(tf.boolType(), x.accept(
				ctx.getEvaluator()).getType(), x);
	}

	@Override
	public IBooleanResult visitExpressionSet(Set x) {
		throw new UnexpectedTypeError(tf.boolType(), x.accept(
				ctx.getEvaluator()).getType(), x);
	}

	@Override
	public IBooleanResult visitExpressionTuple(Tuple x) {
		throw new UnexpectedTypeError(tf.boolType(), x.accept(
				ctx.getEvaluator()).getType(), x);
	}

	@Override
	public IBooleanResult visitExpressionMap(Map x) {
		throw new UnexpectedTypeError(tf.boolType(), x.accept(
				ctx.getEvaluator()).getType(), x);
	}

	@Override
	public IBooleanResult visitExpressionQualifiedName(QualifiedName x) {
		return new BasicBooleanResult(vf, ctx, x);
	}

	@Override
	public IBooleanResult visitExpressionTypedVariable(TypedVariable x) {
		throw new UninitializedVariableError(x.toString(), x);
	}

	@Override
	public IBooleanResult visitExpressionTypedVariableBecomes(
			TypedVariableBecomes x) {
		throw new SyntaxError(x.toString(), x.getLocation());
	}

	@Override
	public IMatchingResult visitExpressionVariableBecomes(VariableBecomes x) {
		throw new SyntaxError(x.toString(), x.getLocation());
	}

	@Override
	public IMatchingResult visitExpressionGuarded(Guarded x) {
		throw new SyntaxError(x.toString(), x.getLocation());
	}

	@Override
	public IMatchingResult visitExpressionAnti(Anti x) {
		throw new SyntaxError(x.toString(), x.getLocation());
	}

	@Override
	public IBooleanResult visitExpressionMultiVariable(MultiVariable x) {
		throw new SyntaxError(x.toString(), x.getLocation());
	}

	@Override
	public IMatchingResult visitExpressionDescendant(Descendant x) {
		throw new SyntaxError(x.toString(), x.getLocation());
	}

	@Override
	public IBooleanResult visitExpressionAddition(Addition x) {
		throw new UnexpectedTypeError(tf.boolType(), x.accept(
				ctx.getEvaluator()).getType(), x);
	}

	@Override
	public IBooleanResult visitExpressionAll(All x) {
		return new BasicBooleanResult(vf, ctx, x);
	}

	@Override
	public IBooleanResult visitExpressionAmbiguity(
			org.meta_environment.rascal.ast.Expression.Ambiguity x) {
		throw new ImplementationError("Ambiguity in expression: " + x);
	}

	@Override
	public IBooleanResult visitExpressionAnd(And x) {
		return new AndResult(vf, ctx, x.getLhs().accept(this), x.getRhs()
				.accept(this));
	}

	@Override
	public IBooleanResult visitExpressionAny(Any x) {
		return new BasicBooleanResult(vf, ctx, x);
	}

	@Override
	public IBooleanResult visitExpressionBracket(Bracket x) {
		return x.getExpression().accept(this);
	}

	@Override
	public IBooleanResult visitExpressionClosure(Closure x) {
		throw new UnexpectedTypeError(tf.boolType(), x.accept(
				ctx.getEvaluator()).getType(), x);
	}

	@Override
	public IBooleanResult visitExpressionComposition(Composition x) {
		throw new UnexpectedTypeError(tf.boolType(), x.accept(
				ctx.getEvaluator()).getType(), x);
	}

	@Override
	public IBooleanResult visitExpressionComprehension(Comprehension x) {
		throw new UnexpectedTypeError(tf.boolType(), x.accept(
				ctx.getEvaluator()).getType(), x);
	}

	@Override
	public IBooleanResult visitExpressionDivision(
			org.meta_environment.rascal.ast.Expression.Division x) {
		throw new UnexpectedTypeError(tf.boolType(), x.accept(
				ctx.getEvaluator()).getType(), x);
	}

	@Override
	public IBooleanResult visitExpressionEquals(Equals x) {
		return new BasicBooleanResult(vf, ctx, x);
	}

	@Override
	public IBooleanResult visitExpressionEquivalence(Equivalence x) {
		return new EquivalenceResult(vf, ctx, x.getLhs().accept(this), x.getRhs().accept(this));
	}

	@Override
	public IBooleanResult visitExpressionFieldAccess(
			org.meta_environment.rascal.ast.Expression.FieldAccess x) {
		return new BasicBooleanResult(vf, ctx, x);
	}

	@Override
	public IBooleanResult visitExpressionFieldProject(FieldProject x) {
		throw new UnexpectedTypeError(tf.boolType(), x.accept(
				ctx.getEvaluator()).getType(), x);
	}

	@Override
	public IBooleanResult visitExpressionFieldUpdate(FieldUpdate x) {
		throw new UnexpectedTypeError(tf.boolType(), x.accept(
				ctx.getEvaluator()).getType(), x);
	}

	@Override
	public IBooleanResult visitExpressionGetAnnotation(GetAnnotation x) {
		return new BasicBooleanResult(vf, ctx, x);
	}

	@Override
	public IBooleanResult visitExpressionGreaterThan(GreaterThan x) {
		return new BasicBooleanResult(vf, ctx, x);
	}

	@Override
	public IBooleanResult visitExpressionGreaterThanOrEq(GreaterThanOrEq x) {
		return new BasicBooleanResult(vf, ctx, x);
	}

	@Override
	public IBooleanResult visitExpressionIfThenElse(IfThenElse x) {
		return new BasicBooleanResult(vf, ctx, x);
	}

	@Override
	public IBooleanResult visitExpressionImplication(Implication x) {
		return new OrResult(vf, ctx, new NotResult(vf, ctx, x.getLhs().accept(
				this)), x.getRhs().accept(this));
	}

	@Override
	public IBooleanResult visitExpressionIn(In x) {
		return new BasicBooleanResult(vf, ctx, x);
	}

	@Override
	public IBooleanResult visitExpressionIntersection(
			org.meta_environment.rascal.ast.Expression.Intersection x) {
		throw new UnexpectedTypeError(tf.boolType(), x.accept(
				ctx.getEvaluator()).getType(), x);
	}

	@Override
	public IBooleanResult visitExpressionLessThan(LessThan x) {
		return new BasicBooleanResult(vf, ctx, x);
	}

	@Override
	public IBooleanResult visitExpressionLessThanOrEq(LessThanOrEq x) {
		return new BasicBooleanResult(vf, ctx, x);
	}

	@Override
	public IBooleanResult visitExpressionLexical(
			org.meta_environment.rascal.ast.Expression.Lexical x) {
		throw new UnexpectedTypeError(tf.boolType(), x.accept(
				ctx.getEvaluator()).getType(), x);
	}

	@Override
	public IBooleanResult visitExpressionMatch(Match x) {
		return new MatchResult(vf, ctx, (IMatchingResult) x.getPattern()
				.accept(pe), true, x.getExpression());
	}

	@Override
	public IBooleanResult visitExpressionModulo(Modulo x) {
		throw new UnexpectedTypeError(tf.boolType(), x.accept(
				ctx.getEvaluator()).getType(), x);
	}

	@Override
	public IBooleanResult visitExpressionNegation(Negation x) {
		return new NotResult(vf, ctx, x.getArgument().accept(this));
	}

	@Override
	public IBooleanResult visitExpressionNegative(Negative x) {
		throw new UnexpectedTypeError(tf.boolType(), x.accept(
				ctx.getEvaluator()).getType(), x);
	}

	@Override
	public IBooleanResult visitExpressionNoMatch(NoMatch x) {
		return new MatchResult(vf, ctx, (IMatchingResult) x.getPattern()
				.accept(pe), false, x.getExpression());
	}

	@Override
	public IBooleanResult visitExpressionNonEmptyBlock(NonEmptyBlock x) {
		throw new UnexpectedTypeError(tf.boolType(), x.accept(
				ctx.getEvaluator()).getType(), x);
	}

	@Override
	public IBooleanResult visitExpressionNonEquals(NonEquals x) {
		return new BasicBooleanResult(vf, ctx, x);
	}

	@Override
	public IBooleanResult visitExpressionNotIn(NotIn x) {
		return new BasicBooleanResult(vf, ctx, x);
	}

	@Override
	public IBooleanResult visitExpressionOperatorAsValue(OperatorAsValue x) {
		throw new UnexpectedTypeError(tf.boolType(), x.accept(
				ctx.getEvaluator()).getType(), x);
	}

	@Override
	public IBooleanResult visitExpressionOr(Or x) {
		return new OrResult(vf, ctx, x.getLhs().accept(this), x.getRhs()
				.accept(this));
	}

	@Override
	public IBooleanResult visitExpressionProduct(
			org.meta_environment.rascal.ast.Expression.Product x) {
		throw new UnexpectedTypeError(tf.boolType(), x.accept(
				ctx.getEvaluator()).getType(), x);
	}

	@Override
	public IBooleanResult visitExpressionRange(Range x) {
		throw new UnexpectedTypeError(tf.boolType(), x.accept(
				ctx.getEvaluator()).getType(), x);
	}

	@Override
	public IBooleanResult visitExpressionSetAnnotation(SetAnnotation x) {
		throw new UnexpectedTypeError(tf.boolType(), x.accept(
				ctx.getEvaluator()).getType(), x);
	}

	@Override
	public IBooleanResult visitExpressionStepRange(StepRange x) {
		throw new UnexpectedTypeError(tf.boolType(), x.accept(
				ctx.getEvaluator()).getType(), x);
	}

	@Override
	public IBooleanResult visitExpressionSubscript(
			org.meta_environment.rascal.ast.Expression.Subscript x) {
		return new BasicBooleanResult(vf, ctx, x);
	}

	@Override
	public IBooleanResult visitExpressionSubtraction(
			org.meta_environment.rascal.ast.Expression.Subtraction x) {
		throw new UnexpectedTypeError(tf.boolType(), x.accept(
				ctx.getEvaluator()).getType(), x);
	}

	@Override
	public IBooleanResult visitExpressionTransitiveClosure(TransitiveClosure x) {
		throw new UnexpectedTypeError(tf.boolType(), x.accept(
				ctx.getEvaluator()).getType(), x);}

	@Override
	public IBooleanResult visitExpressionTransitiveReflexiveClosure(
			TransitiveReflexiveClosure x) {
		throw new UnexpectedTypeError(tf.boolType(), x.accept(
				ctx.getEvaluator()).getType(), x);
		}

	@Override
	public IBooleanResult visitExpressionEnumerator(Enumerator x) {
		return new EnumeratorResult(vf, ctx, (IMatchingResult) x.getPattern()
				.accept(pe), null, x.getExpression());
	}

	@Override
	public IBooleanResult visitExpressionEnumeratorWithStrategy(
			EnumeratorWithStrategy x) {
		return new EnumeratorResult(vf, ctx, (IMatchingResult) x.getPattern()
				.accept(pe), x.getStrategy(), x.getExpression());
	}

	@Override
	public IBooleanResult visitExpressionVisit(Visit x) {
		return new BasicBooleanResult(vf, ctx, x);
	}

	@Override
	public IBooleanResult visitExpressionVoidClosure(VoidClosure x) {
		throw new UnexpectedTypeError(tf.boolType(), x.accept(
				ctx.getEvaluator()).getType(), x);
	}
}
