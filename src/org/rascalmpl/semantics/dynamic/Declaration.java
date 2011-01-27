package org.rascalmpl.semantics.dynamic;

import java.lang.String;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.imp.pdb.facts.INode;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;
import org.rascalmpl.ast.Alternative;
import org.rascalmpl.ast.FunctionDeclaration;
import org.rascalmpl.ast.Kind;
import org.rascalmpl.ast.Name;
import org.rascalmpl.ast.NullASTVisitor;
import org.rascalmpl.ast.PatternWithAction;
import org.rascalmpl.ast.Tags;
import org.rascalmpl.ast.UserType;
import org.rascalmpl.ast.Variant;
import org.rascalmpl.ast.Visibility;
import org.rascalmpl.interpreter.Evaluator;
import org.rascalmpl.interpreter.TypeEvaluator;
import org.rascalmpl.interpreter.asserts.NotYetImplemented;
import org.rascalmpl.interpreter.result.Result;
import org.rascalmpl.interpreter.staticErrors.RedeclaredVariableError;
import org.rascalmpl.interpreter.staticErrors.UnexpectedTypeError;
import org.rascalmpl.interpreter.staticErrors.UninitializedVariableError;

public abstract class Declaration extends org.rascalmpl.ast.Declaration {

	public Declaration(INode __param1) {
		super(__param1);
	}

	static public class View extends org.rascalmpl.ast.Declaration.View {

		public View(INode __param1, Tags __param2, Visibility __param3, Name __param4, Name __param5, List<Alternative> __param6) {
			super(__param1, __param2, __param3, __param4, __param5, __param6);
		}

		@Override
		public <T> T __evaluate(NullASTVisitor<T> __eval) {
			return null;
		}

		@Override
		public Result<IValue> __evaluate(Evaluator __eval) {

			// TODO implement
			throw new NotYetImplemented("Views");

		}

	}

	static public class Test extends org.rascalmpl.ast.Declaration.Test {

		public Test(INode __param1, org.rascalmpl.ast.Test __param2) {
			super(__param1, __param2);
		}

		@Override
		public <T> T __evaluate(NullASTVisitor<T> __eval) {
			return null;
		}

		@Override
		public Result<IValue> __evaluate(Evaluator __eval) {

			return this.getTest().__evaluate(__eval);

		}

	}

	static public class Alias extends org.rascalmpl.ast.Declaration.Alias {

		public Alias(INode __param1, Tags __param2, Visibility __param3, UserType __param4, org.rascalmpl.ast.Type __param5) {
			super(__param1, __param2, __param3, __param4, __param5);
		}

		@Override
		public Result<IValue> __evaluate(Evaluator __eval) {

			__eval.__getTypeDeclarator().declareAlias(this, __eval.getCurrentEnvt());
			return org.rascalmpl.interpreter.result.ResultFactory.nothing();

		}

		@Override
		public <T> T __evaluate(NullASTVisitor<T> __eval) {
			return null;
		}

	}

	static public class Annotation extends org.rascalmpl.ast.Declaration.Annotation {

		public Annotation(INode __param1, Tags __param2, Visibility __param3, org.rascalmpl.ast.Type __param4, org.rascalmpl.ast.Type __param5, Name __param6) {
			super(__param1, __param2, __param3, __param4, __param5, __param6);
		}

		@Override
		public <T> T __evaluate(NullASTVisitor<T> __eval) {
			return null;
		}

		@Override
		public Result<IValue> __evaluate(Evaluator __eval) {

			Type annoType = new TypeEvaluator(__eval.getCurrentModuleEnvironment(), __eval.__getHeap()).eval(this.getAnnoType());
			String name = org.rascalmpl.interpreter.utils.Names.name(this.getName());

			Type onType = new TypeEvaluator(__eval.getCurrentModuleEnvironment(), __eval.__getHeap()).eval(this.getOnType());
			__eval.getCurrentModuleEnvironment().declareAnnotation(onType, name, annoType);

			return org.rascalmpl.interpreter.result.ResultFactory.nothing();

		}

	}

	static public class Ambiguity extends org.rascalmpl.ast.Declaration.Ambiguity {

		public Ambiguity(INode __param1, List<org.rascalmpl.ast.Declaration> __param2) {
			super(__param1, __param2);
		}

		@Override
		public <T> T __evaluate(NullASTVisitor<T> __eval) {
			return null;
		}

	}

	static public class DataAbstract extends org.rascalmpl.ast.Declaration.DataAbstract {

		public DataAbstract(INode __param1, Tags __param2, Visibility __param3, UserType __param4) {
			super(__param1, __param2, __param3, __param4);
		}

		@Override
		public Result<IValue> __evaluate(Evaluator __eval) {

			__eval.__getTypeDeclarator().declareAbstractADT(this, __eval.getCurrentEnvt());
			return org.rascalmpl.interpreter.result.ResultFactory.nothing();

		}

		@Override
		public <T> T __evaluate(NullASTVisitor<T> __eval) {
			return null;
		}

	}

	static public class Variable extends org.rascalmpl.ast.Declaration.Variable {

		public Variable(INode __param1, Tags __param2, Visibility __param3, org.rascalmpl.ast.Type __param4, List<org.rascalmpl.ast.Variable> __param5) {
			super(__param1, __param2, __param3, __param4, __param5);
		}

		@Override
		public <T> T __evaluate(NullASTVisitor<T> __eval) {
			return null;
		}

		@Override
		public Result<IValue> __evaluate(Evaluator __eval) {

			Result<IValue> r = org.rascalmpl.interpreter.result.ResultFactory.nothing();
			__eval.setCurrentAST(this);

			for (org.rascalmpl.ast.Variable var : this.getVariables()) {
				Type declaredType = new TypeEvaluator(__eval.getCurrentModuleEnvironment(), __eval.__getHeap()).eval(this.getType());

				if (var.isInitialized()) {
					Result<IValue> v = var.getInitial().__evaluate(__eval);

					if (!__eval.getCurrentEnvt().declareVariable(declaredType, var.getName())) {
						throw new RedeclaredVariableError(org.rascalmpl.interpreter.utils.Names.name(var.getName()), var);
					}

					if (v.getType().isSubtypeOf(declaredType)) {
						// TODO: do we actually want to instantiate the locally
						// bound type parameters?
						Map<Type, Type> bindings = new HashMap<Type, Type>();
						declaredType.match(v.getType(), bindings);
						declaredType = declaredType.instantiate(bindings);
						r = org.rascalmpl.interpreter.result.ResultFactory.makeResult(declaredType, v.getValue(), __eval);
						__eval.getCurrentModuleEnvironment().storeVariable(var.getName(), r);
					} else {
						throw new UnexpectedTypeError(declaredType, v.getType(), var);
					}
				} else {
					throw new UninitializedVariableError(org.rascalmpl.interpreter.utils.Names.name(var.getName()), var);
				}
			}

			r.setPublic(this.getVisibility().isPublic());
			return r;

		}

	}

	static public class Rule extends org.rascalmpl.ast.Declaration.Rule {

		public Rule(INode __param1, Tags __param2, Name __param3, PatternWithAction __param4) {
			super(__param1, __param2, __param3, __param4);
		}

		@Override
		public Result<IValue> __evaluate(Evaluator __eval) {

			return this.getPatternAction().__evaluate(__eval);

		}

		@Override
		public <T> T __evaluate(NullASTVisitor<T> __eval) {
			return null;
		}

	}

	static public class Tag extends org.rascalmpl.ast.Declaration.Tag {

		public Tag(INode __param1, Tags __param2, Visibility __param3, Kind __param4, Name __param5, List<org.rascalmpl.ast.Type> __param6) {
			super(__param1, __param2, __param3, __param4, __param5, __param6);
		}

		@Override
		public <T> T __evaluate(NullASTVisitor<T> __eval) {
			return null;
		}

		@Override
		public Result<IValue> __evaluate(Evaluator __eval) {

			throw new NotYetImplemented("tags");

		}

	}

	static public class Data extends org.rascalmpl.ast.Declaration.Data {

		public Data(INode __param1, Tags __param2, Visibility __param3, UserType __param4, List<Variant> __param5) {
			super(__param1, __param2, __param3, __param4, __param5);
		}

		@Override
		public <T> T __evaluate(NullASTVisitor<T> __eval) {
			return null;
		}

		@Override
		public Result<IValue> __evaluate(Evaluator __eval) {

			__eval.__getTypeDeclarator().declareConstructor(this, __eval.getCurrentEnvt());
			__eval.notifyConstructorDeclaredListeners();
			return org.rascalmpl.interpreter.result.ResultFactory.nothing();

		}

	}

	static public class Function extends org.rascalmpl.ast.Declaration.Function {

		public Function(INode __param1, FunctionDeclaration __param2) {
			super(__param1, __param2);
		}

		@Override
		public <T> T __evaluate(NullASTVisitor<T> __eval) {
			return null;
		}

		@Override
		public Result<IValue> __evaluate(Evaluator __eval) {

			return this.getFunctionDeclaration().__evaluate(__eval);

		}

	}
}