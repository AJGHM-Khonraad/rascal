package org.rascalmpl.semantics.dynamic;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.INode;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.rascalmpl.ast.ImportedModule;
import org.rascalmpl.ast.Nonterminal;
import org.rascalmpl.ast.Sym;
import org.rascalmpl.ast.SyntaxDefinition;
import org.rascalmpl.interpreter.Evaluator;
import org.rascalmpl.interpreter.env.Environment;
import org.rascalmpl.interpreter.env.GlobalEnvironment;
import org.rascalmpl.interpreter.env.ModuleEnvironment;
import org.rascalmpl.interpreter.result.Result;
import org.rascalmpl.interpreter.staticErrors.ModuleLoadError;
import org.rascalmpl.values.uptr.Factory;

public abstract class Import extends org.rascalmpl.ast.Import {
	
	static public class Extend extends org.rascalmpl.ast.Import.Extend {

		public Extend(INode node, ImportedModule module) {
			super(node, module);
		}
		
		@Override
		public Result<IValue> interpret(Evaluator __eval) {
			String name = __eval.getUnescapedModuleName(this);
			__eval.extendCurrentModule(this, name);
			return org.rascalmpl.interpreter.result.ResultFactory.nothing();
		}
		
		
		@Override
		public String declareSyntax(Evaluator eval, boolean withImports) {
			String name = eval.getUnescapedModuleName(this);

			ModuleEnvironment env = eval.getHeap().getModule(name);
			if (env != null && env.isSyntaxDefined()) {
				// so modules that have been initialized already dont get parsed again and again
				return name;
			}
			
			org.rascalmpl.ast.Module mod = eval.preParseModule(java.net.URI.create("rascal:///" + name), this.getLocation());  
			if (withImports) {
				mod.declareSyntax(eval, false);
			}

			return null;
		}
	}

	static public class Default extends org.rascalmpl.ast.Import.Default {

		public Default(INode __param1, ImportedModule __param2) {
			super(__param1, __param2);
		}

		@Override
		public String declareSyntax(Evaluator eval, boolean withImports) {
			String name = eval.getUnescapedModuleName(this);

			GlobalEnvironment heap = eval.__getHeap();
			if (!heap.existsModule(name)) {
				// deal with a fresh module that needs initialization
				heap.addModule(new ModuleEnvironment(name, heap));
			}

			try {
				org.rascalmpl.ast.Module mod = eval.preParseModule(java.net.URI.create("rascal:///" + name), this.getLocation());  
				
				eval.addImportToCurrentModule(this, name);
				if (withImports) {
					Environment old = eval.getCurrentEnvt();
					try {
						eval.setCurrentEnvt(heap.getModule(name));
						mod.declareSyntax(eval, false);
					}
					finally {
						eval.setCurrentEnvt(old);
					}
				}
			}
			catch (ModuleLoadError e) {
				// when a module does not load, the import should not fail here, rather it will fail when we evaluate the module
				return null;
			}

			return null;
		}
		
		@Override
		public Result<IValue> interpret(Evaluator __eval) {
			// TODO support for full complexity of import declarations
			String name = __eval.getUnescapedModuleName(this);
			GlobalEnvironment heap = __eval.__getHeap();
			if (!heap.existsModule(name)) {
				// deal with a fresh module that needs initialization
				heap.addModule(new ModuleEnvironment(name, heap));
				__eval.evalRascalModule(this, name);
				__eval.addImportToCurrentModule(this, name);
			} else if (__eval.getCurrentEnvt() == __eval.__getRootScope()) {
				// in the root scope we treat an import as a "reload"
				heap.resetModule(name);
				__eval.evalRascalModule(this, name);
				__eval.addImportToCurrentModule(this, name);
			} else {
				// otherwise simply add the current imported name to the imports
				// of the current module
				if (!heap.getModule(name).isInitialized()) {
					__eval.evalRascalModule(this, name);
				}
				__eval.addImportToCurrentModule(this, name);
			}

			return org.rascalmpl.interpreter.result.ResultFactory.nothing();

		}

	}

	static public class Syntax extends org.rascalmpl.ast.Import.Syntax {

		public Syntax(INode __param1, SyntaxDefinition __param2) {
			super(__param1, __param2);
		}

		@Override
		public String declareSyntax(Evaluator __eval, boolean withImports) {
			Sym type = this.getSyntax().getDefined();
			IValueFactory vf = __eval.getValueFactory();
			
			if (type.isNonterminal()) {
				String nt = ((Nonterminal.Lexical) type.getNonterminal()).getString();
				__eval.getCurrentEnvt().concreteSyntaxType(nt, (IConstructor) Factory.Symbol_Sort.make(vf, vf.string(nt)));
			}
			
			__eval.getCurrentEnvt().declareProduction(this);
			return null;
		}
		
		@Override
		public Result<IValue> interpret(Evaluator eval) {
			eval.loadParseTreeModule(this);
			declareSyntax(eval, false);
			return nothing();
		}

	}

	public Import(INode __param1) {
		super(__param1);
	}
}
