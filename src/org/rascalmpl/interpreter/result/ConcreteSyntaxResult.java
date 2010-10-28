package org.rascalmpl.interpreter.result;

import static org.rascalmpl.interpreter.result.ResultFactory.bool;
import static org.rascalmpl.interpreter.result.ResultFactory.makeResult;

import org.eclipse.imp.pdb.facts.IBool;
import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.ISet;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;
import org.rascalmpl.interpreter.IEvaluatorContext;
import org.rascalmpl.values.uptr.TreeAdapter;

public class ConcreteSyntaxResult extends ConstructorResult {

	public ConcreteSyntaxResult(Type type, IConstructor cons,
			IEvaluatorContext ctx) {
		super(type, cons, ctx);
	}
	
	@Override
	public <U extends IValue, V extends IValue> Result<U> equals(Result<V> that) {
		return that.equalToConcreteSyntax(this);
	}

	@Override
	public <U extends IValue, V extends IValue> Result<U> nonEquals(Result<V> that) {
		return that.nonEqualToConcreteSyntax(this);
	}
	
	@Override
	protected <U extends IValue> Result<U> nonEqualToConcreteSyntax(
			ConcreteSyntaxResult that) {
		return equalToConcreteSyntax(that).negate();
	}
	
	@Override
	protected <U extends IValue> Result<U> equalToConcreteSyntax(ConcreteSyntaxResult that) {
		IConstructor left = this.getValue();
		IConstructor right = that.getValue();
		
		if (TreeAdapter.isLayout(left) && TreeAdapter.isLayout(right)) {
			return bool(true, ctx);
		}
		
		if (TreeAdapter.isAppl(left) && TreeAdapter.isAppl(right)) {
			IConstructor p1 = TreeAdapter.getProduction(left);
			IConstructor p2 = TreeAdapter.getProduction(right);
			
			// NB: using ordinary equals here...
			if (!p1.equals(p2)) {
				return bool(false, ctx);
			}
			
			IList l1 = TreeAdapter.getArgs(left);
			IList l2 = TreeAdapter.getArgs(right);
			
			if (l1.length() != l2.length()) {
				return bool(false, ctx);
			}
			for (int i = 0; i < l1.length(); i++) {
				IValue kid1 = l1.get(i);
				IValue kid2 = l2.get(i);
				// Recurse here on kids to reuse layout handling etc.
				Result<IBool> result = makeResult(kid1.getType(), kid1, ctx).equals(makeResult(kid2.getType(), kid2, ctx));
				if (!result.getValue().getValue()) {
					return bool(false, ctx);
				}
				if (TreeAdapter.isContextFree(left)) {
					i++; // skip layout
				}
			}
			return bool(true, ctx);
		}
		
		
		if (TreeAdapter.isChar(left) && TreeAdapter.isChar(right)) {
			return bool((TreeAdapter.getCharacter(left) == TreeAdapter.getCharacter(right)), ctx);
		}
		
		if (TreeAdapter.isAmb(left) && TreeAdapter.isAmb(right)) {
			ISet alts1 = TreeAdapter.getAlternatives(left);
			ISet alts2 = TreeAdapter.getAlternatives(right);

			if (alts1.size() != alts2.size()) {
				return bool(false, ctx);
			}
			
			// TODO: this is very inefficient
			again: for (IValue alt1: alts1) {
				for (IValue alt2: alts2) {
					Result<IBool> result = makeResult(alt1.getType(), alt1, ctx).equals(makeResult(alt2.getType(), alt2, ctx));
					if (result.getValue().getValue()) {
						// As soon an alt1 is equal to an alt2
						// continue the outer loop.
						continue again;
					}
				}
				// If an alt1 is not equal to any of the the alt2's return false;
				return bool(false, ctx);
			}
			return bool(true, ctx);
		}

		return bool(false, ctx);
	}

}
