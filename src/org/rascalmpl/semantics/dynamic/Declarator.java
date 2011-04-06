/*******************************************************************************
 * Copyright (c) 2009-2011 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   * Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI
 *   * Mark Hills - Mark.Hills@cwi.nl (CWI)
*******************************************************************************/
package org.rascalmpl.semantics.dynamic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.imp.pdb.facts.INode;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;
import org.rascalmpl.ast.Variable;
import org.rascalmpl.interpreter.Evaluator;
import org.rascalmpl.interpreter.env.Environment;
import org.rascalmpl.interpreter.result.Result;
import org.rascalmpl.interpreter.staticErrors.RedeclaredVariableError;
import org.rascalmpl.interpreter.staticErrors.UnexpectedTypeError;

public abstract class Declarator extends org.rascalmpl.ast.Declarator {

	static public class Default extends org.rascalmpl.ast.Declarator.Default {

		public Default(INode __param1, org.rascalmpl.ast.Type __param2,
				List<Variable> __param3) {
			super(__param1, __param2, __param3);
		}

		@Override
		public Result<IValue> interpret(Evaluator __eval) {
			Result<IValue> r = org.rascalmpl.interpreter.result.ResultFactory
					.nothing();

			for (Variable var : this.getVariables()) {
				String varAsString = org.rascalmpl.interpreter.utils.Names
						.name(var.getName());

				if (var.isInitialized()) { // variable declaration without
					// initialization
					// first evaluate the initialization, in case the left hand
					// side will shadow something
					// that is used on the right hand side.
					Result<IValue> v = var.getInitial().interpret(__eval);

					Type declaredType = typeOf(__eval.getCurrentEnvt());

					if (!__eval.getCurrentEnvt().declareVariable(declaredType,
							var.getName())) {
						throw new RedeclaredVariableError(varAsString, var);
					}

					if (v.getType().isSubtypeOf(declaredType)) {
						// TODO: do we actually want to instantiate the locally
						// bound type parameters?
						Map<Type, Type> bindings = new HashMap<Type, Type>();
						declaredType.match(v.getType(), bindings);
						declaredType = declaredType.instantiate(bindings);
						// Was: r = makeResult(declaredType,
						// applyRules(v.getValue()));
						r = org.rascalmpl.interpreter.result.ResultFactory
								.makeResult(declaredType, v.getValue(), __eval);
						__eval.getCurrentEnvt().storeVariable(var.getName(), r);
					} else {
						throw new UnexpectedTypeError(declaredType,
								v.getType(), var);
					}
				} else {
					Type declaredType = typeOf(__eval.getCurrentEnvt());

					if (!__eval.getCurrentEnvt().declareVariable(declaredType,
							var.getName())) {
						throw new RedeclaredVariableError(varAsString, var);
					}
				}
			}

			return r;

		}

		@Override
		public Type typeOf(Environment env) {
			return getType().typeOf(env);
		}

	}

	public Declarator(INode __param1) {
		super(__param1);
	}
}
