package org.meta_environment.rascal.interpreter.matching;

import java.util.List;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.eclipse.imp.pdb.facts.type.Type;
import org.meta_environment.rascal.ast.Expression.CallOrTree;
import org.meta_environment.rascal.interpreter.IEvaluatorContext;
import org.meta_environment.rascal.interpreter.asserts.ImplementationError;
import org.meta_environment.rascal.interpreter.asserts.NotYetImplemented;
import org.meta_environment.rascal.interpreter.env.Environment;
import org.meta_environment.rascal.interpreter.result.Result;
import org.meta_environment.rascal.interpreter.result.ResultFactory;
import org.meta_environment.rascal.interpreter.types.NonTerminalType;
import org.meta_environment.rascal.interpreter.types.RascalTypeFactory;
import org.meta_environment.rascal.interpreter.utils.IUPTRAstToSymbolConstructor;
import org.meta_environment.rascal.interpreter.utils.IUPTRAstToSymbolConstructor.NonGroundSymbolException;
import org.meta_environment.uptr.Factory;
import org.meta_environment.uptr.SymbolAdapter;
import org.meta_environment.uptr.TreeAdapter;

public class ConcreteListPattern extends AbstractMatchingResult {
	private ListPattern pat;
	private CallOrTree callOrTree;

	public ConcreteListPattern(IValueFactory vf, IEvaluatorContext ctx, CallOrTree x, List<IMatchingResult> list) {
		super(vf, ctx);
		callOrTree = x;
		initListPatternDelegate(vf, list);
	}

	private void initListPatternDelegate(IValueFactory vf, List<IMatchingResult> list) {
		Type type = getType(null);
		
		if (type instanceof NonTerminalType) {
			IConstructor rhs = ((NonTerminalType) type).getSymbol();

			if (SymbolAdapter.isCf(rhs)) {	
				IConstructor cfSym = SymbolAdapter.getSymbol(rhs);
				if (SymbolAdapter.isIterPlus(cfSym) || SymbolAdapter.isIterStar(cfSym)) {
					pat = new ListPattern(vf, ctx, list, 2);
				}
				else if (SymbolAdapter.isIterPlusSep(cfSym) || SymbolAdapter.isIterStarSep(cfSym)) {
					pat = new ListPattern(vf, ctx, list, 4);
				}
			}
			else if (SymbolAdapter.isLex(rhs)){
				IConstructor lexSym = SymbolAdapter.getSymbol(rhs);
				if (SymbolAdapter.isIterPlus(lexSym) || SymbolAdapter.isIterStar(lexSym)) {
					pat = new ListPattern(vf, ctx, list, 1);
				}
				else if (SymbolAdapter.isIterPlusSep(lexSym) || SymbolAdapter.isIterStarSep(lexSym)) {
					pat = new ListPattern(vf, ctx, list, 2);
				}
			}
			else {
				throw new ImplementationError("crooked production: non (cf or lex) list symbol: " + rhs);
			}
			return;
		}
		throw new ImplementationError("should not get here if we don't know that its a proper list");
	}

	@Override
	public void initMatch(Result<IValue> subject) {
		super.initMatch(subject);
		if (!subject.getType().isSubtypeOf(Factory.Tree)) {
			hasNext = false;
			return;
		}
		IConstructor tree = (IConstructor) subject.getValue();
		pat.initMatch(ResultFactory.makeResult(Factory.Args, TreeAdapter.getArgs(tree), ctx));
		hasNext = true;
	}
	
	@Override
	public Type getType(Environment env) {
		CallOrTree prod = (CallOrTree) callOrTree.getArguments().get(0);
		CallOrTree rhs = (CallOrTree) prod.getArguments().get(0);
		
		try {
			return RascalTypeFactory.getInstance().nonTerminalType(rhs.accept(new IUPTRAstToSymbolConstructor(vf)));
		}
		catch (NonGroundSymbolException e) {
			return Factory.Tree;
		}
	}

	@Override
	public boolean hasNext() {
		if (!hasNext) {
			return false;
		}
		return pat.hasNext();
	}
	
	@Override
	public boolean next() {
		if (!hasNext()) {
			return false;
		}
		return pat.next();
		
	}

	@Override
	public IValue toIValue(Environment env) {
		throw new NotYetImplemented("is this dead?");
	}
	
	@Override
	public java.util.List<String> getVariables() {
		return pat.getVariables();
	}
}