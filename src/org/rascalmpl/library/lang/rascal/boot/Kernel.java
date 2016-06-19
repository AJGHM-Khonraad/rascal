package org.rascalmpl.library.lang.rascal.boot;

import java.io.IOException;
import java.net.URISyntaxException;

import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.ExecutionTools;
import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.NoSuchRascalFunction;
import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.OverloadedFunction;
import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.RVMCore;
import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.RVMExecutable;
import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.RascalExecutionContext;
import org.rascalmpl.uri.URIUtil;
import org.rascalmpl.value.IBool;
import org.rascalmpl.value.IConstructor;
import org.rascalmpl.value.IList;
import org.rascalmpl.value.IMap;
import org.rascalmpl.value.ISourceLocation;
import org.rascalmpl.value.IString;
import org.rascalmpl.value.IValue;
import org.rascalmpl.value.IValueFactory;

public class Kernel {
	private static final String PATH_TO_LINKED_KERNEL = "lang/rascal/boot/Kernel.rvm.ser.gz";
    IValueFactory vf;
	private OverloadedFunction compile;
	private OverloadedFunction compileN;
	private OverloadedFunction compileAndLink;
	private OverloadedFunction compileAndLinkN;
	private OverloadedFunction compileAndMergeIncremental;
	private OverloadedFunction compileMuLibrary;
	private OverloadedFunction bootstrapRascalParser;
	private OverloadedFunction rascalTests;
	
	private final RVMCore rvm;

	public Kernel(IValueFactory vf, RascalExecutionContext rex) throws IOException, NoSuchRascalFunction, URISyntaxException {
	    this(vf, rex, URIUtil.correctLocation("boot", "", "/"));
	}
        
	public Kernel(IValueFactory vf, RascalExecutionContext rex, ISourceLocation binaryKernelLoc) throws IOException, NoSuchRascalFunction, URISyntaxException {
		this.vf = vf;
	
		if (!binaryKernelLoc.getScheme().startsWith("compressed")) {
		    binaryKernelLoc = URIUtil.changeScheme(binaryKernelLoc, "compressed+" + binaryKernelLoc.getScheme());
		}
		
		if (!binaryKernelLoc.getPath().endsWith(PATH_TO_LINKED_KERNEL)) {
		    binaryKernelLoc = URIUtil.getChildLocation(binaryKernelLoc, PATH_TO_LINKED_KERNEL);
		}
		   
		this.rvm = ExecutionTools.initializedRVM(binaryKernelLoc, rex);

		compile    		= rvm.getOverloadedFunction("RVMModule compile(str qname, list[loc] srcPath, list[loc] libPath, loc bootLoc, loc binLoc)");
		compileN    	= rvm.getOverloadedFunction("list[RVMModule] compile(list[str] qnames, list[loc] srcPath, list[loc] libPath, loc bootLoc, loc binLoc)");
		compileMuLibrary= rvm.getOverloadedFunction("void compileMuLibrary(list[loc] srcPath, list[loc] libPath, loc bootLoc, loc binLoc)");
		compileAndLink  = rvm.getOverloadedFunction("RVMProgram compileAndLink(str qname, list[loc] srcPath, list[loc] libPath, loc bootLoc, loc binLoc)");
		compileAndLinkN = rvm.getOverloadedFunction("list[RVMProgram] compileAndLink(list[str] qnames, list[loc] srcPath, list[loc] libPath, loc bootLoc, loc binLoc)");
		compileAndMergeIncremental 
						= rvm.getOverloadedFunction("RVMProgram compileAndMergeIncremental(str qname, bool reuseConfig, list[loc] srcPath, list[loc] libPath, loc bootLoc, loc binLoc)");
		rascalTests   	= rvm.getOverloadedFunction("value rascalTests(list[str] qnames, list[loc] srcPath, list[loc] libPath, loc bootLoc, loc binLoc)");
//		bootstrapRascalParser = rvm.getOverloadedFunction("void bootstrapRascalParser(loc src)");
	}
	
	/**
	 * Compile a Rascal module
	 * @param qname		Qualified module name
	 * @param srcPath	List of source directories
	 * @param libPath	List of library directories
	 * @param bootLoc	Boot directory
	 * @param binLoc	Binary directory
	 * @param kwArgs	Keyword arguments
	 * @return The result (RVMProgram) of compiling the given module
	 */
	public IConstructor compile(IString qname, IList srcPath, IList libPath, ISourceLocation bootLoc, ISourceLocation binLoc, IMap kwArgs){
	  return (IConstructor) rvm.executeRVMFunction(compile, new IValue[] { qname, srcPath, libPath, bootLoc, binLoc, kwArgs });
	}
	
	/**
	 * Compile a list of Rascal modules
	 * @param qnames	List of qualified module names
	 * @param srcPath	List of source directories
	 * @param libPath	List of library directories
	 * @param bootLoc	Boot directory
	 * @param binLoc	Binary directory
	 * @param kwArgs	Keyword arguments
	 * @return A list of RVMPrograms
	 */
	public IList compile(IList qnames, IList srcPath, IList libPath, ISourceLocation bootLoc, ISourceLocation binLoc, IMap kwArgs){
		return (IList) rvm.executeRVMFunction(compileN, new IValue[] { qnames, srcPath, libPath, bootLoc, binLoc, kwArgs });
	}
	
	/**
	 * Used only in bootstrapping stages to recompile the MuLibrary with a new compiler.
	 * 
	 * @param srcPath
	 * @param libPath
	 * @param bootLoc
	 * @param binLoc
	 * @param kwArgs
	 */
	public void compileMuLibrary(IList srcPath, IList libPath, ISourceLocation bootLoc, ISourceLocation binLoc, IMap kwArgs) {
	    rvm.executeRVMFunction(compileMuLibrary, new IValue[] { srcPath, libPath, bootLoc, binLoc, kwArgs });
	}
	
	/**
	 * Used only in bootstrapping stages to regenerate the parser for Rascal itself. Writes
	 * in a source directory!
	 * @param srcPath
	 */
	public void bootstrapRascalParser(IList srcPath) {
	    rvm.executeRVMFunction(bootstrapRascalParser, new IValue[] { srcPath });
	}
	
	/**
	 * 	Compile and link a Rascal module
	 * @param qname		Qualified module name
	 * @param srcPath	List of source directories
	 * @param libPath	List of library directories
	 * @param bootLoc	Boot directory
	 * @param binLoc	Binary directory
	 * @param kwArgs	Keyword arguments
	 * @return The result (RVMProgram) of compiling the given module. The linked version (RVMExecutable) is stored as file.
	 */
	public IConstructor compileAndLink(IString qname,  IList srcPath, IList libPath, ISourceLocation bootLoc, ISourceLocation binLoc,  IMap kwArgs){
		return (IConstructor) rvm.executeRVMFunction(compileAndLink, new IValue[] { qname, srcPath, libPath, bootLoc, binLoc, kwArgs });
	}
	
	/**
	 * 	Compile and link a list of Rascal modules. The linked version (RVMExecutable) is stored as file.
	 * @param qname		List of qualified module names
	 * @param srcPath	List of source directories
	 * @param libPath	List of library directories
	 * @param bootLoc	Boot directory
	 * @param binLoc	Binary directory
	 * @param kwArgs	Keyword arguments
	 * @return A list of resulting RVMExecutables
	 */
	public IList compileAndLink(IList qnames,  IList srcPath, IList libPath, ISourceLocation bootLoc, ISourceLocation binLoc,  IMap kwArgs){
		return (IList) rvm.executeRVMFunction(compileAndLinkN, new IValue[] { qnames, srcPath, libPath, bootLoc, binLoc, kwArgs });
	}
	
	/**
	 * Incrementally compile and link a Rascal module (used in RascalShell)
	 * @param qname			Qualified module name
	 * @param reuseConfig	true if the previous typechcker configuration should be reused
	 * @param srcPath		List of source directories
	 * @param libPath		List of library directories
	 * @param bootLoc		Boot directory
	 * @param binLoc		Binary directory
	 * @param kwArgs		Keyword arguments
	 * @return The compiled and linked (RVMExecutable) version of the given module
	 * @throws IOException
	 */
	public RVMExecutable compileAndMergeIncremental(IString qname, IBool reuseConfig, IList srcPath, IList libPath, ISourceLocation bootLoc, ISourceLocation binLoc, IMap kwArgs) throws IOException{
		IConstructor rvmProgram = (IConstructor) rvm.executeRVMFunction(compileAndMergeIncremental, new IValue[] { qname, reuseConfig, srcPath, libPath, bootLoc, binLoc, kwArgs });
		return ExecutionTools.link(rvmProgram, vf.bool(true));
	}
	
	/**
	 * 	Run tests in a list of Rascal modules
	 * @param qnames	List of qualified module name
	 * @param srcPath	List of source directories
	 * @param libPath	List of library directories
	 * @param bootLoc	Boot directory
	 * @param binLoc	Binary directory
	 * @param kwArgs	Keyword arguments
	 * @return The outcome of the tests
	 */
	
	public IValue rascalTests(IList qnames, IList srcPath, IList libPath, ISourceLocation bootLoc, ISourceLocation binLoc, IMap kwArgs){
		return rvm.executeRVMFunction(rascalTests, new IValue[] { qnames, srcPath, libPath, bootLoc, binLoc, kwArgs });
	}

  
}
