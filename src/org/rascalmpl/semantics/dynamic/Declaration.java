/*******************************************************************************
 * Copyright (c) 2009-2013 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:

 *   * Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI
 *   * Mark Hills - Mark.Hills@cwi.nl (CWI)
 *   * Arnold Lankamp - Arnold.Lankamp@cwi.nl
*******************************************************************************/
package org.rascalmpl.semantics.dynamic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;
import org.rascalmpl.ast.CommonKeywordParameters;
import org.rascalmpl.ast.FunctionDeclaration;
import org.rascalmpl.ast.Name;
import org.rascalmpl.ast.Tags;
import org.rascalmpl.ast.UserType;
import org.rascalmpl.ast.Variant;
import org.rascalmpl.ast.Visibility;
import org.rascalmpl.interpreter.IEvaluator;
import org.rascalmpl.interpreter.result.Result;
import org.rascalmpl.interpreter.staticErrors.RedeclaredVariable;
import org.rascalmpl.interpreter.staticErrors.UnexpectedType;
import org.rascalmpl.interpreter.staticErrors.UnsupportedOperation;

public abstract class Declaration extends org.rascalmpl.ast.Declaration {

	static public class Alias extends org.rascalmpl.ast.Declaration.Alias {

		public Alias(IConstructor __param1, Tags __param2, Visibility __param3,
				UserType __param4, org.rascalmpl.ast.Type __param5) {
			super(__param1, __param2, __param3, __param4, __param5);
		}

		@Override
		public Result<IValue> interpret(IEvaluator<Result<IValue>> __eval) {

			__eval.__getTypeDeclarator().declareAlias(this,
					__eval.getCurrentEnvt());
			return org.rascalmpl.interpreter.result.ResultFactory.nothing();

		}

	}

	static public class Annotation extends
			org.rascalmpl.ast.Declaration.Annotation {

		public Annotation(IConstructor __param1, Tags __param2, Visibility __param3,
				org.rascalmpl.ast.Type __param4,
				org.rascalmpl.ast.Type __param5, Name __param6) {
			super(__param1, __param2, __param3, __param4, __param5, __param6);
		}

		@Override
		public Result<IValue> interpret(IEvaluator<Result<IValue>> __eval) {
			Type annoType = getAnnoType().typeOf(__eval.getCurrentEnvt());
			String name = org.rascalmpl.interpreter.utils.Names.name(this
					.getName());

			Type onType = getOnType().typeOf(__eval.getCurrentEnvt());
			
			if (onType.isAbstractDataType() || onType.isConstructorType() || onType.isNodeType()) {
				__eval.getCurrentModuleEnvironment().declareAnnotation(onType,
						name, annoType);
			} else {
				throw new UnsupportedOperation("Can only declare annotations on node and ADT types",getOnType());
			}

			return org.rascalmpl.interpreter.result.ResultFactory.nothing();
		}

	}

	static public class Data extends org.rascalmpl.ast.Declaration.Data {

		public Data(IConstructor __param1, Tags __param2, Visibility __param3,
				UserType __param4, CommonKeywordParameters __param5, List<Variant> __param6) {
			super(__param1, __param2, __param3, __param4, __param5, __param6);
		}

		@Override
		public Result<IValue> interpret(IEvaluator<Result<IValue>> __eval) {

			__eval.__getTypeDeclarator().declareConstructor(this,
					__eval.getCurrentEnvt());
			return org.rascalmpl.interpreter.result.ResultFactory.nothing();

		}

	}

	static public class DataAbstract extends
			org.rascalmpl.ast.Declaration.DataAbstract {

		public DataAbstract(IConstructor __param1, Tags __param2, Visibility __param3,
				UserType __param4) {
			super(__param1, __param2, __param3, __param4);
		}

		@Override
		public Result<IValue> interpret(IEvaluator<Result<IValue>> __eval) {

			__eval.__getTypeDeclarator().declareAbstractADT(this,
					__eval.getCurrentEnvt());
			return org.rascalmpl.interpreter.result.ResultFactory.nothing();

		}

	}

	static public class Function extends org.rascalmpl.ast.Declaration.Function {

		public Function(IConstructor __param1, FunctionDeclaration __param2) {
			super(__param1, __param2);
		}

		@Override
		public Result<IValue> interpret(IEvaluator<Result<IValue>> __eval) {

			return this.getFunctionDeclaration().interpret(__eval);

		}

	}

	static public class Variable extends org.rascalmpl.ast.Declaration.Variable {

		public Variable(IConstructor __param1, Tags __param2, Visibility __param3,
				org.rascalmpl.ast.Type __param4,
				List<org.rascalmpl.ast.Variable> __param5) {
			super(__param1, __param2, __param3, __param4, __param5);
		}

		@Override
		public Result<IValue> interpret(IEvaluator<Result<IValue>> __eval) {

			Result<IValue> r = org.rascalmpl.interpreter.result.ResultFactory
					.nothing();
			__eval.setCurrentAST(this);

			for (org.rascalmpl.ast.Variable var : this.getVariables()) {
				Type declaredType = getType().typeOf(__eval.getCurrentEnvt());

				if (var.isInitialized()) {
					Result<IValue> v = var.getInitial().interpret(__eval);

					if (!__eval.getCurrentEnvt().declareVariable(declaredType,
							var.getName())) {
						throw new RedeclaredVariable(
								org.rascalmpl.interpreter.utils.Names.name(var
										.getName()), var);
					}

					if (v.getType().isSubtypeOf(declaredType)) {
						// TODO: do we actually want to instantiate the locally
						// bound type parameters?
						Map<Type, Type> bindings = new HashMap<Type, Type>();
						declaredType.match(v.getType(), bindings);
						declaredType = declaredType.instantiate(bindings);
						r = org.rascalmpl.interpreter.result.ResultFactory
								.makeResult(declaredType, v.getValue(), __eval);
						__eval.getCurrentModuleEnvironment().storeVariable(
								var.getName(), r);
					} else {
						throw new UnexpectedType(declaredType,
								v.getType(), var);
					}
				} else {
					__eval.getCurrentModuleEnvironment().storeVariable(
							var.getName(),
							org.rascalmpl.interpreter.result.ResultFactory
									.nothing(declaredType));
				}
			}

			r.setPublic(this.getVisibility().isPublic());
			return r;

		}

	}

	public Declaration(IConstructor __param1) {
		super(__param1);
	}
}
