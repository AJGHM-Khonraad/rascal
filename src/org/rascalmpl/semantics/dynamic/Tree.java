/*******************************************************************************
 * Copyright (c) 2009-2011 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   * Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI
*******************************************************************************/
package org.rascalmpl.semantics.dynamic;

import java.util.ArrayList;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IListWriter;
import org.eclipse.imp.pdb.facts.ISetWriter;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;
import org.rascalmpl.ast.Expression;
import org.rascalmpl.interpreter.Evaluator;
import org.rascalmpl.interpreter.IEvaluatorContext;
import org.rascalmpl.interpreter.env.Environment;
import org.rascalmpl.interpreter.matching.BasicBooleanResult;
import org.rascalmpl.interpreter.matching.ConcreteApplicationPattern;
import org.rascalmpl.interpreter.matching.ConcreteListPattern;
import org.rascalmpl.interpreter.matching.ConcreteOptPattern;
import org.rascalmpl.interpreter.matching.IBooleanResult;
import org.rascalmpl.interpreter.matching.IMatchingResult;
import org.rascalmpl.interpreter.matching.LiteralPattern;
import org.rascalmpl.interpreter.matching.NodePattern;
import org.rascalmpl.interpreter.matching.SetPattern;
import org.rascalmpl.interpreter.result.Result;
import org.rascalmpl.interpreter.types.RascalTypeFactory;
import org.rascalmpl.values.uptr.Factory;
import org.rascalmpl.values.uptr.TreeAdapter;

/**
 * These classes special case Expression.CallOrTree for concrete syntax patterns
 */
public abstract class Tree {
	protected static boolean isConstant(java.util.List<Expression> args) {
		boolean tmp = true;
		for (org.rascalmpl.ast.Expression e : args) { 
			if (e.getStats().getNestedMetaVariables() > 0) {
				tmp = false;
				break;
			}
		}
		return tmp;
	}
	
  static public class Appl extends org.rascalmpl.ast.Expression {
	protected final IConstructor production;
	protected final java.util.List<org.rascalmpl.ast.Expression> args;
	protected final Type type;
	protected final boolean constant;

	public Appl(IConstructor node, java.util.List<org.rascalmpl.ast.Expression> args) {
		super(node);
		this.production = TreeAdapter.getProduction(node);
		this.type = RascalTypeFactory.getInstance().nonTerminalType(node);
		this.args = args;
		this.constant = isConstant(args);
	}

	@Override
	public Type _getType() {
		return type;
	}
	
	@Override
	public Type typeOf(Environment env) {
		return type;
	}
	
	@Override
	public Result<IValue> interpret(Evaluator eval) {
		if (constant) {
			return makeResult(type, node, eval);
		}
		
		// TODO add function calling
		IListWriter w = eval.getValueFactory().listWriter(Factory.Tree);
		for (org.rascalmpl.ast.Expression arg : args) {
			w.append(arg.interpret(eval).getValue());
		}
		
		return makeResult(type, Factory.Tree_Appl.make(eval.getValueFactory(), production, w.done()), eval);
	}
	
	@Override
	public IBooleanResult buildBacktracker(IEvaluatorContext eval) {
		return new BasicBooleanResult(this);
	}
	
	@Override
	public IMatchingResult buildMatcher(IEvaluatorContext eval) {
		if (constant) {
			return new LiteralPattern(this,  node);
		}
		
		java.util.List<IMatchingResult> kids = new java.util.ArrayList<IMatchingResult>(args.size());
		for (int i = 0; i < args.size(); i+=2) { // skip layout elements for efficiency
			kids.add(args.get(i).buildMatcher(eval));
		}
		return new ConcreteApplicationPattern(this,  kids);
	}
  }
  
  static public class Lexical extends Appl {
	public Lexical(IConstructor node, java.util.List<org.rascalmpl.ast.Expression> args) {
		super(node, args);
	}
	
	@Override
	public IMatchingResult buildMatcher(IEvaluatorContext eval) {
		if (constant) {
			return new LiteralPattern(this,  node);
		}
		
		java.util.List<IMatchingResult> kids = new java.util.ArrayList<IMatchingResult>(args.size());
		for (org.rascalmpl.ast.Expression arg : args) {
			kids.add(arg.buildMatcher(eval));
		}
		return new ConcreteApplicationPattern(this,  kids);
	}
  }
  
  static public class Optional extends Appl {
	public Optional(IConstructor node, java.util.List<org.rascalmpl.ast.Expression> args) {
		super(node, args);
	}
	

	@Override
	public IMatchingResult buildMatcher(IEvaluatorContext eval) {
		java.util.List<IMatchingResult> kids = new ArrayList<IMatchingResult>(args.size());
		if (args.size() == 1) {
			kids.add(args.get(0).buildMatcher(eval));
		}
		return new ConcreteOptPattern(this,  kids);
	}
  }
  
  static public class List extends Appl {
	public List(IConstructor node, java.util.List<org.rascalmpl.ast.Expression> args) {
		super(node, args);
	}
	
	@Override
	public IMatchingResult buildMatcher(IEvaluatorContext eval) {
		if (constant) {
			return new LiteralPattern(this,  node);
		}
		
		java.util.List<IMatchingResult> kids = new java.util.ArrayList<IMatchingResult>(args.size());
		for (org.rascalmpl.ast.Expression arg : args) {
			kids.add(arg.buildMatcher(eval));
		}
		return new ConcreteListPattern(this,  kids);
	}
  }
  
  static public class Amb extends  org.rascalmpl.ast.Expression {
	private final Type type;
	private final java.util.List<org.rascalmpl.ast.Expression> alts;
	private final boolean constant;

	public Amb(IConstructor node, java.util.List<org.rascalmpl.ast.Expression> alternatives) {
		super(node);
		this.type = RascalTypeFactory.getInstance().nonTerminalType(node);
		this.alts = alternatives;
		this.constant = isConstant(alternatives);
	}
	
	@Override
	public Result<IValue> interpret(Evaluator eval) {
		if (constant) {
			return makeResult(type, node, eval);
		}
		
		// TODO: add filtering semantics, function calling
		ISetWriter w = eval.getValueFactory().setWriter(Factory.Tree);
		for (org.rascalmpl.ast.Expression a : alts) {
			w.insert(a.interpret(eval).getValue());
		}
		return makeResult(type, Factory.Tree_Amb.make(eval.getValueFactory(), (IValue) w.done()), eval);
	}
	
	@Override
	public Type typeOf(Environment env) {
		return type;
	}
	
	@Override
	public IBooleanResult buildBacktracker(IEvaluatorContext __eval) {
		return new BasicBooleanResult(this);
	}
	
	@Override
	public IMatchingResult buildMatcher(IEvaluatorContext eval) {
		if (constant) {
			return new LiteralPattern(this,  node);
		}
		
		java.util.List<IMatchingResult> kids = new java.util.ArrayList<IMatchingResult>(alts.size());
		for (org.rascalmpl.ast.Expression arg : alts) {
			kids.add(arg.buildMatcher(eval));
		}
		
		IMatchingResult setMatcher = new SetPattern(this,  kids);
		java.util.List<IMatchingResult> wrap = new ArrayList<IMatchingResult>(1);
		wrap.add(setMatcher);
		
		Result<IValue> ambCons = eval.getCurrentEnvt().getVariable("amb");
		return new NodePattern(this,  
				new LiteralPattern(this,  ambCons.getValue()), null, wrap);
	} 
  }
  
  static public class Char extends  org.rascalmpl.ast.Expression {
	  public Char(IConstructor node) {
		  super(node);
	  }

	  @Override
	  public Result<IValue> interpret(Evaluator eval) {
		  // TODO allow override
		  return makeResult(Factory.Tree, node, eval);
	  }
	  
	  @Override
	  public IMatchingResult buildMatcher(IEvaluatorContext eval) {
		  return new LiteralPattern(this,  node);
	  }
	  
	  @Override
	  public Type typeOf(Environment env) {
		  return Factory.Tree;
	  }
  }
}
