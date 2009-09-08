package org.meta_environment.rascal.interpreter.result;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.eclipse.imp.pdb.facts.type.Type;
import org.meta_environment.ValueFactoryFactory;
import org.meta_environment.errors.SubjectAdapter;
import org.meta_environment.errors.SummaryAdapter;
import org.meta_environment.rascal.ast.FunctionDeclaration.Abstract;
import org.meta_environment.rascal.interpreter.Evaluator;
import org.meta_environment.rascal.interpreter.IEvaluatorContext;
import org.meta_environment.rascal.interpreter.control_exceptions.Throw;
import org.meta_environment.rascal.interpreter.env.Environment;
import org.meta_environment.rascal.interpreter.env.ModuleEnvironment;
import org.meta_environment.rascal.interpreter.load.ModuleLoader;
import org.meta_environment.rascal.interpreter.staticErrors.SyntaxError;
import org.meta_environment.rascal.interpreter.utils.RuntimeExceptionFactory;
import org.meta_environment.uptr.ParsetreeAdapter;
import org.meta_environment.uptr.TreeAdapter;

public class FileParserFunction extends ParserFunction {

	private Environment env;


	public FileParserFunction(Evaluator eval, Abstract func, Environment env, ModuleLoader loader) {
		super(eval, func, env, loader, loader.getParser());
		this.env = env;
	}

	
	@Override
	public Result<IValue> call(Type[] actualTypes, IValue[] actuals,
			IEvaluatorContext ctx) {
		if (callTracing) {
			printStartTrace();
		}

		checkParameters(actuals, actualTypes);
		
		String source = ((IString)(actuals[0])).getValue();
		
		List<String> sdfSearchPath = loader.getSdfSearchPath();
		Set<String> sdfImports = ((ModuleEnvironment)this.getEnv()).getSDFImports();
		
		
		try {
			IConstructor ptree = parser.parseObjectLanguageFile(sdfSearchPath, sdfImports, source); 
			ptree = ParsetreeAdapter.addPositionInformation(ptree, source);
			
			if (ptree.getConstructorType() == org.meta_environment.uptr.Factory.ParseTree_Summary) {
				throw parseError(ptree, source, ctx);
			}
			else {
				IConstructor tree = (IConstructor) TreeAdapter.getArgs(ParsetreeAdapter.getTop(ptree)).get(1);
				Type resultType = getReturnType().instantiate(env.getStore(), env.getTypeBindings());
				return ResultFactory.makeResult(resultType, tree, eval);
			}
		}
		catch (IllegalArgumentException e) {
			throw RuntimeExceptionFactory.io(VF.string(e.getMessage()), getAst(),
					eval.getStackTrace());
		}
		catch (IOException e) {
			throw RuntimeExceptionFactory.io(VF.string(e.getMessage()), getAst(), eval.getStackTrace());
		}
		catch (SyntaxError e) {
			throw RuntimeExceptionFactory.parseError(e.getLocation(), getAst(), eval.getStackTrace());
		}
		finally {
			if (callTracing) {
				printEndTrace();
			}
		}
	}
	
	private Throw parseError(IConstructor tree, String file, IEvaluatorContext ctx) {
		SubjectAdapter subject = new SummaryAdapter(tree).getInitialSubject();
		IValueFactory vf = ValueFactoryFactory.getValueFactory();
		ISourceLocation loc = vf.sourceLocation(file, subject.getOffset(), subject.getLength(), subject.getBeginLine(), subject.getEndLine(), subject.getBeginColumn(), subject.getEndColumn());

		return RuntimeExceptionFactory.parseError(loc, ctx.getCurrentAST(), ctx.getStackTrace());
	}
	
	@Override
	public String toString() {
		return getHeader() + " @fileParser";
	}

}
