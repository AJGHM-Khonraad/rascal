package org.rascalmpl.semantics.dynamic;

import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IListWriter;
import org.eclipse.imp.pdb.facts.IValue;
import org.rascalmpl.ast.Expression;
import org.rascalmpl.interpreter.result.Result;
import org.rascalmpl.interpreter.staticErrors.NonVoidTypeRequired;
import org.rascalmpl.interpreter.staticErrors.UnexpectedTypeError;

public class ListComprehensionWriter extends ComprehensionWriter {

	private boolean splicing[];
	private Result<IValue> rawElements[];

	@SuppressWarnings("unchecked")
	public ListComprehensionWriter(java.util.List<Expression> resultExprs,
			org.rascalmpl.interpreter.Evaluator ev) {
		super(resultExprs, ev);
		this.splicing = new boolean[resultExprs.size()];
		this.rawElements = new Result[resultExprs.size()];
	}

	@Override
	public void append() {
		// the first time we need to find out the type of the elements
		// first, and whether or not to splice them, and evaluate them
		if (this.writer == null) {
			int k = 0;
			this.elementType1 = TF.voidType();

			for (Expression resExpr : this.resultExprs) {
			
				org.eclipse.imp.pdb.facts.type.Type elementType;
				this.splicing[k] = false;
				
				if(resExpr.isSplice() || resExpr.isSplicePlus()){
					this.rawElements[k] = resExpr.getArgument().interpret(this.ev);
					elementType = this.rawElements[k].getType();
					if(elementType.isListType()){
						elementType = elementType.getElementType();
						this.splicing[k] = true;
					}
				} else {
					this.rawElements[k] = resExpr.interpret(this.ev);
					elementType = this.rawElements[k].getType();
				}
				this.elementType1 = this.elementType1.lub(elementType);
				k++;
			}

			this.resultType = TF.listType(this.elementType1);
			this.writer = this.resultType.writer(VF);
		}
		// the second time we only need to evaluate and add the elements
		else {
			int k = 0;
			for (Expression resExpr : this.resultExprs) {
				this.rawElements[k++] = (resExpr.isSplice() || resExpr.isSplicePlus()) ? resExpr.getArgument().interpret(this.ev) : resExpr.interpret(this.ev);
			}
		}

		// here we finally add the elements
		int k = 0;
		for (Expression resExpr : this.resultExprs) {
			if (this.splicing[k]) {
				/*
				 * Splice elements of the value of the result expression in
				 * the result list
				 */
				if (!this.rawElements[k].getType().getElementType()
						.isSubtypeOf(this.elementType1)) {
					throw new UnexpectedTypeError(this.elementType1,
							this.rawElements[k].getType().getElementType(),
							resExpr);
				}

				Result<?> list = this.rawElements[k];
				if (list.getType().isVoidType()) {
					throw new NonVoidTypeRequired(resExpr);
				}
				for (IValue val : (IList) list.getValue()) {
					((IListWriter) this.writer).append(val);
				}
			} else {
				this.check(this.rawElements[k], this.elementType1, "list",
						resExpr);
				((IListWriter) this.writer).append(this.rawElements[k]
						.getValue());
			}
			k++;
		}
	}

	@Override
	public Result<IValue> done() {
		return (this.writer == null) ? Comprehension.makeResult(TF
				.listType(TF.voidType()), VF.list(), this
				.getContext(this.resultExprs.get(0))) : Comprehension.makeResult(TF
				.listType(this.elementType1), this.writer.done(), this
				.getContext(this.resultExprs.get(0)));
	}
}