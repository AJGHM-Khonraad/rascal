package org.meta_environment.rascal.interpreter.result;

import static org.meta_environment.rascal.interpreter.result.ResultFactory.bool;
import static org.meta_environment.rascal.interpreter.result.ResultFactory.makeResult;

import org.eclipse.imp.pdb.facts.IBool;
import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.ISet;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;
import org.meta_environment.rascal.interpreter.IEvaluatorContext;
import org.meta_environment.uptr.ProductionAdapter;
import org.meta_environment.uptr.TreeAdapter;

public class ConcreteSyntaxResult extends ConstructorResult {

	public ConcreteSyntaxResult(Type type, IConstructor cons,
			IEvaluatorContext ctx) {
		super(type, cons, ctx);
	}
	
	@Override
	public <U extends IValue, V extends IValue> Result<U> equals(Result<V> that, IEvaluatorContext ctx) {
		return that.equalToConcreteSyntax(this, ctx);
	}

	@Override
	public <U extends IValue, V extends IValue> Result<U> nonEquals(Result<V> that, IEvaluatorContext ctx) {
		return that.nonEqualToConcreteSyntax(this, ctx);
	}
	
	@Override
	protected <U extends IValue> Result<U> equalToConcreteSyntax(
			ConcreteSyntaxResult that, IEvaluatorContext ctx) {
		IConstructor left = this.getValue();
		IConstructor right = that.getValue();
		
		if (TreeAdapter.isLayout(left) && TreeAdapter.isLayout(right)) {
			return bool(true);
		}
		
		if (TreeAdapter.isAppl(left) && TreeAdapter.isAppl(right)) {
			IConstructor p1 = TreeAdapter.getProduction(left);
			IConstructor p2 = TreeAdapter.getProduction(right);
			
			// NB: using ordinary equals here...
			if (!ProductionAdapter.getTree(p1).equals(ProductionAdapter.getTree(p2))) {
				return bool(false);
			}
			
			IList l1 = TreeAdapter.getArgs(left);
			IList l2 = TreeAdapter.getArgs(right);
			
			if (l1.length() != l2.length()) {
				return bool(false);
			}
			for (int i = 0; i < l1.length(); i++) {
				IValue kid1 = l1.get(i);
				IValue kid2 = l2.get(i);
				// Recurse here on kids to reuse layout handling etc.
				Result<IBool> result = makeResult(kid1.getType(), kid1, ctx)
					.equals(makeResult(kid2.getType(), kid2, ctx), ctx);
				if (!result.getValue().getValue()) {
					return bool(false);
				}
				if (TreeAdapter.isContextFree(left)) {
					i++; // skip layout
				}
			}
			return bool(true);
		}
		
		
		if (TreeAdapter.isChar(left) && TreeAdapter.isChar(right)) {
			return bool(TreeAdapter.getCharacter(left) == TreeAdapter.getCharacter(right));
		}
		
		if (TreeAdapter.isAmb(left) && TreeAdapter.isAmb(right)) {
			ISet alts1 = TreeAdapter.getAlternatives(left);
			ISet alts2 = TreeAdapter.getAlternatives(right);

			if (alts1.size() != alts2.size()) {
				return bool(false);
			}
			
			// TODO: this is very inefficient
			again: for (IValue alt1: alts1) {
				for (IValue alt2: alts2) {
					Result<IBool> result = makeResult(alt1.getType(), alt1, ctx)
						.equals(makeResult(alt2.getType(), alt2, ctx), ctx);
					if (result.getValue().getValue()) {
						// As soon an alt1 is equal to an alt2
						// continue the outer loop.
						continue again;
					}
				}
				// If an alt1 is not equal to any of the the alt2's return false;
				return bool(false);
			}
			return bool(true);
		}

		return bool(false);
	}

}
