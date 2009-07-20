package org.meta_environment.rascal.interpreter.matching;

import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.eclipse.imp.pdb.facts.type.Type;
import org.meta_environment.rascal.ast.Expression;
import org.meta_environment.rascal.ast.Expression.CallOrTree;
import org.meta_environment.rascal.interpreter.EvaluatorContext;
import org.meta_environment.rascal.interpreter.IUPTRAstToSymbolConstructor;
import org.meta_environment.rascal.interpreter.Names;
import org.meta_environment.rascal.interpreter.IUPTRAstToSymbolConstructor.NonGroundSymbolException;
import org.meta_environment.rascal.interpreter.env.ConcreteSyntaxType;
import org.meta_environment.rascal.interpreter.env.Environment;
import org.meta_environment.uptr.Factory;

class ConcreteApplicationPattern extends AbstractPattern {
	private AbstractPattern pat;
	private Expression.CallOrTree callOrTree;

	public ConcreteApplicationPattern(IValueFactory vf,
			EvaluatorContext ctx, CallOrTree x,
			java.util.List<AbstractPattern> args) {
		super(vf, ctx);
		org.meta_environment.rascal.ast.QualifiedName N = x.getExpression().getQualifiedName();
		pat = new NodePattern(vf, new EvaluatorContext(ctx.getEvaluator(), x), null, N, args);
		callOrTree = x;
	}

	@Override
	public void initMatch(IValue subject, Environment arg1) {
		super.initMatch(subject, arg1);
		pat.initMatch(subject, arg1);
	}
	
	@Override
	public Type getType(Environment env) {
		CallOrTree prod = (CallOrTree) callOrTree.getArguments().get(0);
		
		String name = Names.name(Names.lastName(prod.getExpression().getQualifiedName()));
		CallOrTree rhs;
		
		if (name.equals("prod")) {
			rhs = (CallOrTree) prod.getArguments().get(1);
		}
		else if (name.equals("list")) {
			rhs = (CallOrTree) prod.getArguments().get(0);
		}
		else {
			return Factory.Tree;
		}
		
		try {
			return new ConcreteSyntaxType(rhs.accept(new IUPTRAstToSymbolConstructor(vf)));
		}
		catch (NonGroundSymbolException e) {
			return Factory.Tree;
		}
	}

	@Override
	public boolean next() {
		return pat.next();
	}

	@Override
	public IValue toIValue(Environment env) {
		return pat.toIValue(env);
	}

	
	
}
