package org.meta_environment.rascal.ast;

import org.eclipse.imp.pdb.facts.INode;

public abstract class ModuleParameters extends AbstractAST {
	public java.util.List<org.meta_environment.rascal.ast.TypeVar> getParameters() {
		throw new UnsupportedOperationException();
	}

	public boolean hasParameters() {
		return false;
	}

	public boolean isDefault() {
		return false;
	}

	static public class Default extends ModuleParameters {
		/*
		 * "[" parameters:{TypeVar ","}+ "]" -> ModuleParameters
		 * {cons("Default")}
		 */
		private Default() {
			super();
		}

		public Default(
				INode node,
				java.util.List<org.meta_environment.rascal.ast.TypeVar> parameters) {
			this.node = node;
			this.parameters = parameters;
		}

		@Override
		public <T> T accept(IASTVisitor<T> visitor) {
			return visitor.visitModuleParametersDefault(this);
		}

		@Override
		public boolean isDefault() {
			return true;
		}

		@Override
		public boolean hasParameters() {
			return true;
		}

		private java.util.List<org.meta_environment.rascal.ast.TypeVar> parameters;

		@Override
		public java.util.List<org.meta_environment.rascal.ast.TypeVar> getParameters() {
			return parameters;
		}

		private void $setParameters(
				java.util.List<org.meta_environment.rascal.ast.TypeVar> x) {
			this.parameters = x;
		}

		public Default setParameters(
				java.util.List<org.meta_environment.rascal.ast.TypeVar> x) {
			Default z = new Default();
			z.$setParameters(x);
			return z;
		}
	}

	static public class Ambiguity extends ModuleParameters {
		private final java.util.List<org.meta_environment.rascal.ast.ModuleParameters> alternatives;

		public Ambiguity(
				INode node,
				java.util.List<org.meta_environment.rascal.ast.ModuleParameters> alternatives) {
			this.alternatives = java.util.Collections
					.unmodifiableList(alternatives);
			this.node = node;
		}

		public java.util.List<org.meta_environment.rascal.ast.ModuleParameters> getAlternatives() {
			return alternatives;
		}

		@Override
		public <T> T accept(IASTVisitor<T> v) {
			return v.visitModuleParametersAmbiguity(this);
		}
	}
}