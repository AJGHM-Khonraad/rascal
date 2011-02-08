package org.rascalmpl.semantics.dynamic;

import java.util.List;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.INode;
import org.eclipse.imp.pdb.facts.type.TypeFactory;
import org.rascalmpl.ast.BasicType;
import org.rascalmpl.ast.DataTypeSelector;
import org.rascalmpl.ast.FunctionType;
import org.rascalmpl.ast.StructuredType;
import org.rascalmpl.ast.Sym;
import org.rascalmpl.ast.TypeVar;
import org.rascalmpl.ast.UserType;
import org.rascalmpl.interpreter.asserts.Ambiguous;
import org.rascalmpl.interpreter.env.Environment;
import org.rascalmpl.interpreter.types.RascalTypeFactory;
import org.rascalmpl.interpreter.utils.Names;

public abstract class Type extends org.rascalmpl.ast.Type {
	private static final TypeFactory TF = TypeFactory.getInstance();

	public Type(INode __param1) {
		super(__param1);
	}

	static public class Structured extends org.rascalmpl.ast.Type.Structured {

		public Structured(INode __param1, StructuredType __param2) {
			super(__param1, __param2);
		}


		@Override
		public org.eclipse.imp.pdb.facts.type.Type typeOf(Environment __eval) {
			return getStructured().typeOf(__eval);
		}

	}

	static public class Selector extends org.rascalmpl.ast.Type.Selector {

		public Selector(INode __param1, DataTypeSelector __param2) {
			super(__param1, __param2);
		}

		@Override
		public org.eclipse.imp.pdb.facts.type.Type typeOf(Environment __eval) {

			return this.getSelector().typeOf(__eval);

		}


	}

	static public class Basic extends org.rascalmpl.ast.Type.Basic {

		public Basic(INode __param1, BasicType __param2) {
			super(__param1, __param2);
		}


		@Override
		public org.eclipse.imp.pdb.facts.type.Type typeOf(Environment __eval) {
			return this.getBasic().typeOf(__eval);
		}

	}

	static public class Ambiguity extends org.rascalmpl.ast.Type.Ambiguity {

		public Ambiguity(INode __param1, List<org.rascalmpl.ast.Type> __param2) {
			super(__param1, __param2);
		}


		@Override
		public org.eclipse.imp.pdb.facts.type.Type typeOf(Environment __eval) {

			throw new Ambiguous((IConstructor) this.getTree());

		}

	}

	static public class Variable extends org.rascalmpl.ast.Type.Variable {

		public Variable(INode __param1, TypeVar __param2) {
			super(__param1, __param2);
		}

		@Override
		public org.eclipse.imp.pdb.facts.type.Type typeOf(Environment env) {
			TypeVar var = this.getTypeVar();
			org.eclipse.imp.pdb.facts.type.Type param;

			if (var.isBounded()) {
				param = TF.parameterType(Names.name(var.getName()), var.getBound().typeOf(env));
			} else {
				param = TF.parameterType(Names.name(var.getName()));
			}
			
			if (env != null) {
				return param.instantiate(env.getTypeBindings());
			}
			return param;

		}


	}

	static public class User extends org.rascalmpl.ast.Type.User {

		public User(INode __param1, UserType __param2) {
			super(__param1, __param2);
		}


		@Override
		public org.eclipse.imp.pdb.facts.type.Type typeOf(Environment __eval) {

			return this.getUser().typeOf(__eval);

		}

	}

	static public class Bracket extends org.rascalmpl.ast.Type.Bracket {

		public Bracket(INode __param1, org.rascalmpl.ast.Type __param2) {
			super(__param1, __param2);
		}


		@Override
		public org.eclipse.imp.pdb.facts.type.Type typeOf(Environment __eval) {

			return this.getType().typeOf(__eval);

		}

	}

	static public class Function extends org.rascalmpl.ast.Type.Function {

		public Function(INode __param1, FunctionType __param2) {
			super(__param1, __param2);
		}


		@Override
		public org.eclipse.imp.pdb.facts.type.Type typeOf(Environment __eval) {

			return this.getFunction().typeOf(__eval);

		}

	}

	static public class Symbol extends org.rascalmpl.ast.Type.Symbol {

		public Symbol(INode __param1, Sym __param2) {
			super(__param1, __param2);
		}


		@Override
		public org.eclipse.imp.pdb.facts.type.Type typeOf(Environment __eval) {
			RascalTypeFactory RTF = org.rascalmpl.interpreter.types.RascalTypeFactory.getInstance();
			return RTF.nonTerminalType(this);

		}

	}
}
