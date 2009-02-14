package org.meta_environment.rascal.interpreter;

/*
 * Implementation of all operators that provide backtracking:
 * - Boolean operators and (&&), or (||), not (!), implies (==>), equivalence (<===>).
 * - Match (~=) and NoMatch (~!)
 */

import java.util.Iterator;
import org.eclipse.imp.pdb.facts.IBool;
import org.meta_environment.rascal.ast.Expression;
import org.meta_environment.rascal.ast.Expression.And;
import org.meta_environment.rascal.ast.Expression.Equivalence;
import org.meta_environment.rascal.ast.Expression.Implication;
import org.meta_environment.rascal.ast.Expression.Negation;
import org.meta_environment.rascal.ast.Expression.Or;
import org.meta_environment.rascal.interpreter.env.Environment;
import org.meta_environment.rascal.interpreter.env.Result;
import org.meta_environment.rascal.interpreter.env.IterableEvalResult;
import org.meta_environment.rascal.interpreter.errors.RascalImplementationException;
import org.meta_environment.rascal.interpreter.errors.RascalTypeException;

/*
 * Base class for iterating over the values of the arguments of the Boolean operators.
 */

public abstract class BooleanEvaluator implements Iterator<Result> {
	static final int LEFT = 0;
	static final int RIGHT = 1;
	Expression expr[];
	Result result[];
	Evaluator ev;
	
	BooleanEvaluator(Expression leftExpr, Expression rightExpr, Evaluator ev){
		expr = new Expression[] { leftExpr, rightExpr };
		this.ev = ev;
		result = new Result[] { null, null };
	}
	
	void defArg(int i){
		Result argResult = expr[i].accept(ev);
		if(!argResult.type.isBoolType()){
			throw new RascalTypeException("Operand of boolean operator should be of type bool and not " + argResult.type, expr[i]);
		}
		result[i] = argResult;
	};
	
	void def(int i){
		if(result[i] == null){
			defArg(i);
		}
	}
	
	void redef(int i){
		result[i] = null;
	}
	
	public boolean hasNext(){
		return (result[LEFT] != null && result[LEFT].hasNext()) || 
		        (result[RIGHT] != null && result[RIGHT].hasNext());
	}
	
	public Result next(){
		return null;
	}
	
	public void remove(){
		throw new RascalImplementationException("remove() in BooleanEvaluator not implemented", expr[LEFT]);
	}
	
	public boolean getNextResult(int i){
		if(result[i] == null){
			defArg(i);
			return true;
		}
		while(result[i].hasNext()){
			result[i] = result[i].next();
			return true;
		}
		return false;
	}
	
	public boolean getNextResult(int i, boolean expected){
		if(result[i] == null){
			defArg(i);
			if(((IBool)result[i].value).getValue() == expected){
				return true;
			}
		}
		while(result[i].hasNext()){
			result[i] = result[i].next();
			if(((IBool)result[i].value).getValue() == expected){
				return true;
			}
		}
		return false;
	}
	
	public boolean is(int i, boolean expected){
		if(result[i] == null){
			defArg(i);
		}
		return ((IBool)result[i].value).getValue() == expected;
	}

}

/*
 * Evaluate and expression
 */

class AndEvaluator extends BooleanEvaluator {
	
	AndEvaluator(And x, Evaluator ev){
		super(x.getLhs(), x.getRhs(), ev);
	}

	@Override
	public Result next() {
		if(is(LEFT, false)){
			if(!getNextResult(LEFT,true)){
				return new IterableEvalResult(this, false);
			}
		}
		if(is(LEFT, true)){
			if(getNextResult(RIGHT,true)){
				return new IterableEvalResult(this, true);
			}
			if(getNextResult(LEFT,true)){
				redef(RIGHT);
				return next();
			}
		}
		return new IterableEvalResult(this, false);
	}
}

/*
 * Evaluate or expression
 */

class OrEvaluator extends BooleanEvaluator {
	
	OrEvaluator(Or x, Evaluator ev){
		super(x.getLhs(), x.getRhs(), ev);
	}

	@Override
	public Result next() {	
		if(getNextResult(LEFT, true)){
			return new IterableEvalResult(this, true);
		}
		if(getNextResult(RIGHT,true)){
			return new IterableEvalResult(this, true);
		}
		return new IterableEvalResult(this, false);
			
	}
}

/*
 * Evaluate negation expression
 */

class NegationEvaluator extends BooleanEvaluator {
	
	NegationEvaluator(Negation x, Evaluator ev){
		super(x.getArgument(), null, ev);
	}

	@Override
	public Result next() {		
		if(getNextResult(LEFT)){
			return new IterableEvalResult(this, !((IBool)result[LEFT].value).getValue());
		}
		return new IterableEvalResult(this, false);
	}
}

/*
 * Evaluate implication expression
 */
class ImplicationEvaluator extends BooleanEvaluator {
	
	ImplicationEvaluator(Implication x, Evaluator ev){
		super(x.getLhs(), x.getRhs(), ev);
	}

	@Override
	public Result next() {
		if(is(LEFT,false)){
			if(getNextResult(RIGHT)){
				return new IterableEvalResult(this, true);
			}
			if(getNextResult(LEFT)){
				redef(RIGHT);
				return next();
			} 
			return new IterableEvalResult(this, false);
		}
		if(is(LEFT, true)){
			if(getNextResult(RIGHT, true)){
				return new IterableEvalResult(this, true);
			}
			if(getNextResult(LEFT)){
				redef(RIGHT);
				return next();
			} 
			return new IterableEvalResult(this, false);
		}
	
		return  new IterableEvalResult(this, false);
	}
}

/*
 * Evaluate equivalence operator
 */

class EquivalenceEvaluator extends BooleanEvaluator {
	
	EquivalenceEvaluator(Equivalence x, Evaluator ev){
		super(x.getLhs(), x.getRhs(), ev);
	}

	@Override
	public Result next() {
		if(is(LEFT,false)){
			if(getNextResult(RIGHT,false)){
				return new IterableEvalResult(this, true);
			}
			if(getNextResult(LEFT)){
				redef(RIGHT);
				return next();
			} 
			return new IterableEvalResult(this, false);
		}
		if(is(LEFT, true)){
			if(getNextResult(RIGHT, true)){
				return new IterableEvalResult(this, true);
			}
			if(getNextResult(LEFT)){
				redef(RIGHT);
				return next();
			} 
			return new IterableEvalResult(this, false);
		}
	
		return  new IterableEvalResult(this, false);
	}
}

/*
 * Evaluate match and nomatch expression
 */

class MatchEvaluator implements Iterator<Result> {
	private boolean positive;
	private MatchPattern mp;
	
	// TODO: remove use of evaluator here! it's not good to have this dependency and the use
	// of the "global" variable lastPattern complicates things a lot.
	MatchEvaluator(Expression pat, Expression subject, boolean positive, Environment env, Evaluator ev){
    	this.positive = positive;
    	mp = ev.evalPattern(pat);
    	ev.lastPattern = mp;
    	mp.initMatch(subject.accept(ev).value, env);
	}

	public boolean hasNext() {
		return mp.hasNext();
	}

	public Result next() {
		boolean result = positive ? mp.next() : !mp.next();
		return new IterableEvalResult(this,result);
	}

	public void remove() {
		throw new RascalImplementationException("remove() not implemented for MatchEvaluator");
	}
}