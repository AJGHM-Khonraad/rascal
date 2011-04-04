/*******************************************************************************
 * Copyright (c) 2009-2011 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:

 *   * Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI
 *   * Tijs van der Storm - Tijs.van.der.Storm@cwi.nl
 *   * Emilie Balland - emilie.balland@inria.fr (INRIA)
 *   * Paul Klint - Paul.Klint@cwi.nl - CWI
 *   * Mark Hills - Mark.Hills@cwi.nl (CWI)
 *   * Arnold Lankamp - Arnold.Lankamp@cwi.nl
*******************************************************************************/
package org.rascalmpl.interpreter;

import java.io.PrintWriter;
import java.util.Stack;

import org.eclipse.imp.pdb.facts.IValueFactory;
import org.rascalmpl.ast.AbstractAST;
import org.rascalmpl.interpreter.env.Environment;
import org.rascalmpl.interpreter.env.GlobalEnvironment;
import org.rascalmpl.interpreter.strategy.IStrategyContext;
import org.rascalmpl.parser.ASTBuilder;
import org.rascalmpl.uri.URIResolverRegistry;

// TODO: this interface needs to be split into an external interface, for clients
// which want to call Rascal from Java, and an internal interface for managing the global
// state of the interpreter between its different components.
public interface IEvaluatorContext extends IRascalMonitor {
	/** for error messaging */
	public AbstractAST getCurrentAST();
	public void setCurrentAST(AbstractAST ast);
	public String getStackTrace();
	
	/** for standard IO */
	public PrintWriter getStdOut();
	public PrintWriter getStdErr();
	
	/** for "internal use" */
	public Evaluator getEvaluator();
	public ASTBuilder getBuilder();
	public boolean isInterrupted();
	public void interrupt();
	public Environment getCurrentEnvt();
	public void setCurrentEnvt(Environment environment);
	
	public void pushEnv();
	public void unwind(Environment old);
	
	public GlobalEnvironment getHeap();
	
	public boolean runTests(IRascalMonitor monitor);
	
	public IValueFactory getValueFactory();
	
	// strategy related
	public IStrategyContext getStrategyContext();
	public void pushStrategyContext(IStrategyContext strategyContext);
	public void popStrategyContext();
	public void setAccumulators(Stack<Accumulator> accumulators);
	public Stack<Accumulator> getAccumulators();
	
	// URI resolver related
	public URIResolverRegistry getResolverRegistry();
	
}