/*******************************************************************************
 * Copyright (c) 2009-2013 CWI
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
import java.util.Collections;
import java.util.HashMap;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IListWriter;
import org.eclipse.imp.pdb.facts.ISetWriter;
import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;
import org.rascalmpl.interpreter.IEvaluator;
import org.rascalmpl.interpreter.IEvaluatorContext;
import org.rascalmpl.interpreter.env.Environment;
import org.rascalmpl.interpreter.matching.BasicBooleanResult;
import org.rascalmpl.interpreter.matching.ConcreteApplicationPattern;
import org.rascalmpl.interpreter.matching.ConcreteListPattern;
import org.rascalmpl.interpreter.matching.ConcreteListVariablePattern;
import org.rascalmpl.interpreter.matching.ConcreteOptPattern;
import org.rascalmpl.interpreter.matching.IBooleanResult;
import org.rascalmpl.interpreter.matching.IMatchingResult;
import org.rascalmpl.interpreter.matching.LiteralPattern;
import org.rascalmpl.interpreter.matching.NodePattern;
import org.rascalmpl.interpreter.matching.SetPattern;
import org.rascalmpl.interpreter.matching.TypedVariablePattern;
import org.rascalmpl.interpreter.result.Result;
import org.rascalmpl.interpreter.staticErrors.UndeclaredVariable;
import org.rascalmpl.interpreter.staticErrors.UninitializedVariable;
import org.rascalmpl.interpreter.types.NonTerminalType;
import org.rascalmpl.interpreter.types.RascalTypeFactory;
import org.rascalmpl.values.uptr.Factory;
import org.rascalmpl.values.uptr.ProductionAdapter;
import org.rascalmpl.values.uptr.SymbolAdapter;
import org.rascalmpl.values.uptr.TreeAdapter;

/**
 * These classes special case Expression.CallOrTree for concrete syntax patterns
 */
public abstract class Tree {
  static public class MetaVariable extends org.rascalmpl.ast.Expression {
	private final String name;
	private final Type type;

	public MetaVariable(IConstructor node, IConstructor symbol, String name) {
		super(node);
		this.name = name;
		this.type = RTF.nonTerminalType(symbol);
	}
	
	@Override
	public Type typeOf(Environment env, boolean instantiateTypeParameters) {
		return type;
	}
	
	@Override
	public Result<IValue> interpret(IEvaluator<Result<IValue>> eval) {
		Result<IValue> variable = eval.getCurrentEnvt().getVariable(name);

		if (variable == null) {
			throw new UndeclaredVariable(name, this);
		}

		if (variable.getValue() == null) {
			throw new UninitializedVariable(name, this);
		}
		
		return variable;
	}
	
	@Override
	public IMatchingResult buildMatcher(IEvaluatorContext ctx) {
	  IConstructor symbol = ((NonTerminalType) type).getSymbol();
    if (SymbolAdapter.isStarList(symbol) || SymbolAdapter.isPlusList(symbol)) {
	    return new ConcreteListVariablePattern(ctx, this, type, name);
	  }
	  else {
	    return new TypedVariablePattern(ctx, this, type, name);
	  }
	}
	  
  }
  
  static public class Appl extends org.rascalmpl.ast.Expression {
	protected final IConstructor production;
	protected final java.util.List<org.rascalmpl.ast.Expression> args;
	protected final Type type;
	protected final boolean constant;
	protected final IConstructor node;

	public Appl(IConstructor node, java.util.List<org.rascalmpl.ast.Expression> args) {
		super(node);
		this.production = TreeAdapter.getProduction(node);
		this.type = RascalTypeFactory.getInstance().nonTerminalType(production);
		this.args = args;
		this.constant = false; // TODO! isConstant(args);
		this.node = this.constant ? node : null;
		ISourceLocation src = TreeAdapter.getLocation(node);
		
		if (src != null) {
		  this.setSourceLocation(src);
		}
	}

	public IConstructor getProduction() {
		return production;
	}
	
	@Override
	public Type _getType() {
		return type;
	}
	
	@Override
	public Type typeOf(Environment env, boolean instantiateTypeParameters) {
		return type;
	}
	
	@Override
	public Result<IValue> interpret(IEvaluator<Result<IValue>> eval) {
		if (constant) {
			return makeResult(type, node, eval);
		}
		
		// TODO add function calling
		IListWriter w = eval.getValueFactory().listWriter(Factory.Tree);
		for (org.rascalmpl.ast.Expression arg : args) {
			w.append(arg.interpret(eval).getValue());
		}
		
		ISourceLocation location = getLocation();
		
		if (location != null) {
		  java.util.Map<String,IValue> annos = new HashMap<String,IValue>();
		  annos.put("loc", location);
		  return makeResult(type, eval.getValueFactory().constructor(Factory.Tree_Appl, annos, production, w.done()), eval);
		}
		else {
		  return makeResult(type, eval.getValueFactory().constructor(Factory.Tree_Appl, production, w.done()), eval);
		}
	}
	
	@Override
	public IBooleanResult buildBacktracker(IEvaluatorContext eval) {
		return new BasicBooleanResult(eval, this);
	}
	
	@Override
	public IMatchingResult buildMatcher(IEvaluatorContext eval) {
		if (constant) {
			return new LiteralPattern(eval, this,  node);
		}
		
		java.util.List<IMatchingResult> kids = new java.util.ArrayList<IMatchingResult>(args.size());
		for (int i = 0; i < args.size(); i+=2) { // skip layout elements for efficiency
			kids.add(args.get(i).buildMatcher(eval));
		}
		return new ConcreteApplicationPattern(eval, this,  kids);
	}
  }
  
  static public class Lexical extends Appl {
	public Lexical(IConstructor node, java.util.List<org.rascalmpl.ast.Expression> args) {
		super(node, args);
	}
	
	@Override
	public IMatchingResult buildMatcher(IEvaluatorContext eval) {
		if (constant) {
			return new LiteralPattern(eval, this,  node);
		}
		
		java.util.List<IMatchingResult> kids = new java.util.ArrayList<IMatchingResult>(args.size());
		for (org.rascalmpl.ast.Expression arg : args) {
			kids.add(arg.buildMatcher(eval));
		}
		return new ConcreteApplicationPattern(eval, this,  kids);
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
		return new ConcreteOptPattern(eval, this,  kids);
	}
  }
  
  static public class List extends Appl {
	private final int delta;

	public List(IConstructor node, java.util.List<org.rascalmpl.ast.Expression> args) {
		super(node, args);
		this.delta = getDelta(production);
	}
	
	@Override
	public IMatchingResult buildMatcher(IEvaluatorContext eval) {
		if (constant) {
			return new LiteralPattern(eval, this,  node);
		}
		
		java.util.List<IMatchingResult> kids = new java.util.ArrayList<IMatchingResult>(args.size());
		for (org.rascalmpl.ast.Expression arg : args) {
			kids.add(arg.buildMatcher(eval));
		}
		return new ConcreteListPattern(eval, this,  kids);
	}
	
	public Result<IValue> interpret(IEvaluator<Result<IValue>> eval) {
		if (constant) {
			return makeResult(type, node, eval);
		}
		
		// TODO add function calling
		IListWriter w = eval.getValueFactory().listWriter(Factory.Tree);
		for (org.rascalmpl.ast.Expression arg : args) {
			w.append(arg.interpret(eval).getValue());
		}
		
		ISourceLocation location = getLocation();
    
    if (location != null) {
      java.util.Map<String,IValue> annos = new HashMap<String,IValue>();
      annos.put("loc", location);
      return makeResult(type, eval.getValueFactory().constructor(Factory.Tree_Appl, annos, production, flatten(w.done())), eval);
    }
    else {
      return makeResult(type, eval.getValueFactory().constructor(Factory.Tree_Appl, production, flatten(w.done())), eval);
    }
	}

	private void appendPreviousSeparators(IList args, IListWriter result, int delta, int i, boolean previousWasEmpty) {
	  if (!previousWasEmpty) {
	    for (int j = i - delta; j > 0 && j < i; j++) {
	      result.append(args.get(j));
	    }
	  }
	}

	private IList flatten(IList args) {
		IListWriter result = VF.listWriter(Factory.Args.getElementType());
		boolean previousWasEmpty = false;
		
		for (int i = 0; i < args.length(); i+=(delta+1)) {
		  IConstructor tree = (IConstructor) args.get(i);
			
			if (TreeAdapter.isList(tree) && ProductionAdapter.shouldFlatten(production, TreeAdapter.getProduction(tree))) {
			  IList nestedArgs = TreeAdapter.getArgs(tree);
			  if (nestedArgs.length() > 0) {
			    appendPreviousSeparators(args, result, delta, i, previousWasEmpty);
			    result.appendAll(nestedArgs);
			  }
			  else {
			    previousWasEmpty = true;
			  }
			}
			else {
			  appendPreviousSeparators(args, result, delta, i, previousWasEmpty);
			  result.append(tree);
			  previousWasEmpty = false;
			}
		}
		
		return result.done();
	}

	private int getDelta(IConstructor prod) {
		IConstructor rhs = ProductionAdapter.getType(prod);
		
		if (SymbolAdapter.isIterPlusSeps(rhs) || SymbolAdapter.isIterStarSeps(rhs)) {
			return SymbolAdapter.getSeparators(rhs).length();
		}
		
		return 0;
	}
  }
  
  static public class Amb extends  org.rascalmpl.ast.Expression {
	private final Type type;
	private final java.util.List<org.rascalmpl.ast.Expression> alts;
	private final boolean constant;
	protected final IConstructor node;

	public Amb(IConstructor node, java.util.List<org.rascalmpl.ast.Expression> alternatives) {
		super(node);
		this.type = RascalTypeFactory.getInstance().nonTerminalType(node);
		this.alts = alternatives;
		this.constant = false; // TODO! isConstant(alternatives);
		this.node = this.constant ? node : null;
	}
	
	@Override
	public Result<IValue> interpret(IEvaluator<Result<IValue>> eval) {
		if (constant) {
			return makeResult(type, node, eval);
		}
		
		// TODO: add filtering semantics, function calling
		ISetWriter w = eval.getValueFactory().setWriter(Factory.Tree);
		for (org.rascalmpl.ast.Expression a : alts) {
			w.insert(a.interpret(eval).getValue());
		}
		return makeResult(type, eval.getValueFactory().constructor(Factory.Tree_Amb, (IValue) w.done()), eval);
	}
	
	@Override
	public Type typeOf(Environment env, boolean instantiateTypeParameters) {
		return type;
	}
	
	@Override
	public IBooleanResult buildBacktracker(IEvaluatorContext eval) {
		return new BasicBooleanResult(eval, this);
	}
	
	@Override
	public IMatchingResult buildMatcher(IEvaluatorContext eval) {
		if (constant) {
			return new LiteralPattern(eval, this,  node);
		}
		
		java.util.List<IMatchingResult> kids = new java.util.ArrayList<IMatchingResult>(alts.size());
		for (org.rascalmpl.ast.Expression arg : alts) {
			kids.add(arg.buildMatcher(eval));
		}
		
		IMatchingResult setMatcher = new SetPattern(eval, this,  kids);
		java.util.List<IMatchingResult> wrap = new ArrayList<IMatchingResult>(1);
		wrap.add(setMatcher);
		
		Result<IValue> ambCons = eval.getCurrentEnvt().getVariable("amb");
		return new NodePattern(eval, this, new LiteralPattern(eval, this,  ambCons.getValue()), null, Factory.Tree_Amb, wrap, Collections.<String,IMatchingResult>emptyMap());
	} 
  }
  
  static public class Char extends  org.rascalmpl.ast.Expression {
	  private final IConstructor node;

	  public Char(IConstructor node) {
		  super(node);
		  this.node = node;
	  }

	  @Override
	  public Result<IValue> interpret(IEvaluator<Result<IValue>> eval) {
		  // TODO allow override
		  return makeResult(Factory.Tree, node, eval);
	  }

	  @Override
	  public IMatchingResult buildMatcher(IEvaluatorContext eval) {
		  return new LiteralPattern(eval, this,  node);
	  }

	  @Override
	  public Type typeOf(Environment env, boolean instantiateTypeParameters) {
		  return Factory.Tree;
	  }
  }
  
  static public class Cycle extends  org.rascalmpl.ast.Expression {
	  private final int length;
	  private final IConstructor node;

	  public Cycle(IConstructor node, int length) {
		  super(node);
		  this.length = length;
		  this.node = node;
	  }

	  @Override
	  public Result<IValue> interpret(IEvaluator<Result<IValue>> eval) {
		  return makeResult(Factory.Tree, VF.constructor(Factory.Tree_Cycle, node, VF.integer(length)), eval);
	  }

	  @Override
	  public IMatchingResult buildMatcher(IEvaluatorContext eval) {
		  return new LiteralPattern(eval, this, VF.constructor(Factory.Tree_Cycle, node, VF.integer(length)));
	  }

	  @Override
	  public Type typeOf(Environment env, boolean instantiateTypeParameters) {
		  return Factory.Tree;
	  }
  }
}
