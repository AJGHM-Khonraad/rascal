package org.rascalmpl.library.experiments.Compiler.RVM.Interpreter;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IInteger;
import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IListWriter;
import org.eclipse.imp.pdb.facts.IMap;
import org.eclipse.imp.pdb.facts.INode;
import org.eclipse.imp.pdb.facts.ISet;
import org.eclipse.imp.pdb.facts.ISetWriter;
import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.eclipse.imp.pdb.facts.type.Type;
import org.rascalmpl.debug.IRascalMonitor;
import org.rascalmpl.interpreter.IEvaluatorContext;				// TODO: remove import: YES
import org.rascalmpl.interpreter.types.NonTerminalType;			// remove import: NO
import org.rascalmpl.interpreter.types.ReifiedType;				// remove import: NO
import org.rascalmpl.library.lang.rascal.syntax.RascalParser;
import org.rascalmpl.parser.gtd.IGTD;
import org.rascalmpl.parser.gtd.exception.ParseError;
import org.rascalmpl.parser.gtd.exception.UndeclaredNonTerminalException;
import org.rascalmpl.parser.gtd.io.InputConverter;
import org.rascalmpl.parser.gtd.recovery.IRecoverer;
import org.rascalmpl.parser.gtd.result.action.IActionExecutor;
import org.rascalmpl.parser.gtd.result.out.DefaultNodeFlattener;
import org.rascalmpl.parser.uptr.UPTRNodeFactory;
import org.rascalmpl.uri.URIResolverRegistry;
import org.rascalmpl.uri.URIUtil;
import org.rascalmpl.values.uptr.ITree;
import org.rascalmpl.values.uptr.ProductionAdapter;
import org.rascalmpl.values.uptr.RascalValueFactory;
import org.rascalmpl.values.uptr.SymbolAdapter;
import org.rascalmpl.values.uptr.TreeAdapter;
import org.rascalmpl.values.uptr.visitors.IdentityTreeVisitor;

public class ParsingTools {

	private IValueFactory vf;
	private IRascalMonitor monitor;
	private List<ClassLoader> classLoaders;
	private PrintWriter stdout;
	private PrintWriter stderr;
	private HashMap<IValue,  Class<IGTD<IConstructor, ITree, ISourceLocation>>> parsers;
	private RascalExecutionContext rex;
	
	public ParsingTools(IValueFactory vf){
		super();
		this.vf = vf;
		stdout = new PrintWriter(System.out);
		stderr = new PrintWriter(System.err);
		parsers = new HashMap<IValue,  Class<IGTD<IConstructor, ITree, ISourceLocation>>>();
	}
	
	public void setContext(RascalExecutionContext rex){
		this.rex = rex;
		monitor = rex.getMonitor();
		stdout = rex.getStdOut();
		stderr = rex.getStdErr();
		parsers = new HashMap<IValue,  Class<IGTD<IConstructor, ITree, ISourceLocation>>>();
		classLoaders = rex.getClassLoaders();
	}
	
	public ParsingTools(RascalExecutionContext rex){
		super();
		this.rex = rex;
		vf = rex.getValueFactory();
		monitor = rex.getMonitor();
		stdout = rex.getStdOut();
		stderr = rex.getStdErr();
		parsers = new HashMap<IValue,  Class<IGTD<IConstructor, ITree, ISourceLocation>>>();
		classLoaders = rex.getClassLoaders();
	}
	
	public void reset(){
		parsers = new HashMap<IValue,  Class<IGTD<IConstructor, ITree, ISourceLocation>>>();
	}
	
//	public void setContext(RascalExecutionContext rex){
//		this.rex = rex;
//		monitor = rex.getMonitor();
//		stdout = rex.getStdOut();
//		stderr = rex.getStdErr();
//		parsers = new HashMap<IValue,  Class<IGTD<IConstructor, ITree, ISourceLocation>>>();
//		classLoaders = rex.getClassLoaders();
//	}
	
	private IRascalMonitor setMonitor(IRascalMonitor monitor2) {
		monitor = monitor2;
		return monitor2;
	}
	
	private void startJob(String name, int totalWork) {
		if (monitor != null)
			monitor.startJob(name, totalWork);
	}
	
	private int endJob(boolean succeeded) {
		if (monitor != null)
			return monitor.endJob(succeeded);
		return 0;
	}
	
	/** 
	 * Store a generated and compiled parser.
	 * @param moduleName	Name of module in which grammar is defined
	 * @param start TODO
	 * @param parser		The generated parser class
	 */
	private void storeObjectParser(String moduleName, IValue start, Class<IGTD<IConstructor, ITree, ISourceLocation>> parser) {
		stderr.println("Compiled -- Storing parser for " + moduleName /*+ "/" + start*/);
		parsers.put(start, parser);
	}

	/**
	 * Retrieve a generated and compiled parser
	 * @param moduleName	Name of module in which grammar is defined
	 * @param start TODO
	 * @return				The generated parser class or NULL
	 */
	private Class<IGTD<IConstructor, ITree, ISourceLocation>> getObjectParser(String moduleName, IValue start) {
		Class<IGTD<IConstructor, ITree, ISourceLocation>> parser = parsers.get(start);
		stderr.println("Compiled -- Retrieving parser for " + moduleName + /* "/" + start + */ ((parser == null) ? " fails" : " succeeds"));
		return parser;
	}
	
	private IGTD<IConstructor, ITree, ISourceLocation> getObjectParser(IString moduleName, IValue start, ISourceLocation loc, IMap syntax){
		return getParser(moduleName.getValue(), start, loc, false, syntax);
	}

	private boolean isBootstrapper() {
		return false;
	}
	
	/**
	 * Parse text from a string
	 * @param start		Start symbol
	 * @param input		Text to be parsed as string
	 * @param currentFrame TODO
	 * @return ParseTree or Exception
	 */
	public IValue parse(IString moduleName, IValue start, IString input, Frame currentFrame) {
		return parse(moduleName, start, vf.mapWriter().done(), URIUtil.invalidLocation(), input.getValue().toCharArray(), currentFrame);
	}
	
	/**
	 * Parse text from a string
	 * @param start		Start symbol
	 * @param input		Text to be parsed as string
	 * @param location	Location of that text
	 * @param currentFrame TODO
	 * @return ParseTree or Exception
	 */
	public IValue parse(IString moduleName, IValue start, IString input, ISourceLocation location, Frame currentFrame) {
		return parse(moduleName, start, vf.mapWriter().done(), location, input.getValue().toCharArray(), currentFrame);
	}
	
	/**
	 * Parse text at a location
	 * @param moduleName Name of module in which grammar is defined
	 * @param start		Start symbol
	 * @param currentFrame TODO
	 * @param input		To be parsed as location
	 * @return ParseTree or Exception
	 */
	public IValue parse(IString moduleName, IValue start, ISourceLocation location, Frame currentFrame) {
		IRascalMonitor old = setMonitor(monitor);
		
		try{
			char[] input = getResourceContent(location);
			return parse(moduleName, start,  vf.mapWriter().done(), location, input, currentFrame);
		}catch(IOException ioex){
			throw RascalRuntimeException.io(vf.string(ioex.getMessage()), currentFrame);
		} finally{
			setMonitor(old);
		}
	}

	/**
	 * The actual parse work horse
	 * @param moduleName	Name of module in which grammar is defined
	 * @param start			Start symbol
	 * @param robust		Error recovery map
	 * @param location		Location where input text comes from
	 * @param input			Input text as char array
	 * @param currentFrame 	Stacktrace of calling context
	 * @return
	 */
	public IValue parse(IString moduleName, IValue start, IMap robust, ISourceLocation location, char[] input, Frame currentFrame) {
		Type reified = start.getType();
		IConstructor startSort = checkPreconditions(start, reified, currentFrame);
		
		IMap syntax = (IMap) ((IConstructor) start).get(1);
		try {
			IConstructor pt = parseObject(moduleName, startSort, robust, location, input, syntax);
			return pt;
		}
		catch (ParseError pe) {
			ISourceLocation errorLoc = vf.sourceLocation(vf.sourceLocation(pe.getLocation()), pe.getOffset(), pe.getLength(), pe.getBeginLine() + 1, pe.getEndLine() + 1, pe.getBeginColumn(), pe.getEndColumn());
			throw RascalRuntimeException.parseError(errorLoc, currentFrame);
		}
		catch (UndeclaredNonTerminalException e){
			throw new CompilerError("Undeclared non-terminal: " + e.getName() + ", " + e.getClassName(), currentFrame);
		}
		catch (Exception e) {
			throw new CompilerError("Unexpected exception:" + e, currentFrame);
		}
	}
	
	public IString unparse(IConstructor tree) {
		return vf.string(TreeAdapter.yield(tree));
	}
	
	/**
	 * Chek that start symbol is valid
	 * @param start			Start symbol, as IValue
	 * @param reified		Reified type, that shoud represent a non-terminal type
	 * @return Start symbol represented as Symbol
	 */
	private static IConstructor checkPreconditions(IValue start, Type reified, Frame currentFrame) {
		if (!(reified instanceof ReifiedType)) {
		   throw RascalRuntimeException.illegalArgument(start, currentFrame, "A reified type is required instead of " + reified);
		}
		
		Type nt = reified.getTypeParameters().getFieldType(0);
		
		if (!(nt instanceof NonTerminalType)) {
			throw RascalRuntimeException.illegalArgument(start, currentFrame, "A non-terminal type is required instead of  " + nt);
		}
		
		IConstructor symbol = ((NonTerminalType) nt).getSymbol();
		
		return symbol;
	}
	
	/**
	 * The actual parse object that is connected to a generated parser
	 * @param moduleName	Name of module in which grammar is defined
	 * @param startSort		Start symbol
	 * @param robust		Error recovery map
	 * @param location		Location where input text comes from
	 * @param input			Actual input text as char array
	 * @param syntax		Syntax as map[Symbol,Production]
	 * @return				ParseTree or Exception
	 */
	@SuppressWarnings("unchecked")
	public ITree parseObject(IString moduleName, IConstructor startSort, IMap robust, ISourceLocation location, char[] input, IMap syntax){
		IGTD<IConstructor, ITree, ISourceLocation> parser = getObjectParser(moduleName, startSort, location, syntax);
		String name = ""; moduleName.getValue();
		if (SymbolAdapter.isStartSort(startSort)) {
			name = "start__";
			startSort = SymbolAdapter.getStart(startSort);
		}
		
		if (SymbolAdapter.isSort(startSort) || SymbolAdapter.isLex(startSort) || SymbolAdapter.isLayouts(startSort)) {
			name += SymbolAdapter.getName(startSort);
		}

		int[][] lookaheads = new int[robust.size()][];
		IConstructor[] robustProds = new IConstructor[robust.size()];
		initializeRecovery(robust, lookaheads, robustProds);
		
		//__setInterrupt(false);
		IActionExecutor<ITree> exec = new RascalFunctionActionExecutor(rex);  // TODO: remove CTX
		
	      String className = name;
	      Class<?> clazz;
	      for (ClassLoader cl: classLoaders) {
	        try {
	          //stderr.println("Trying classloader: " + cl);
	          clazz = cl.loadClass(className);
	          parser =  (IGTD<IConstructor, ITree, ISourceLocation>) clazz.newInstance();
	          //stderr.println("succeeded!");
	          break;
	        } catch (ClassNotFoundException e) {
	          continue;
	        } catch (InstantiationException e) {
	          throw new CompilerError("could not instantiate " + className + " to valid IGTD parser: " + e);
	        } catch (IllegalAccessException e) {
	          throw new CompilerError("not allowed to instantiate " + className + " to valid IGTD parser: " + e);
	        } catch (LinkageError e){
	        	continue;
	        }
	        //throw new ImplementationError("class for cached parser " + className + " could not be found");
	      }
	     
		return (ITree) parser.parse(name, location.getURI(), input, exec, new DefaultNodeFlattener<IConstructor, ITree, ISourceLocation>(), new UPTRNodeFactory(), (IRecoverer<IConstructor>) null);
	}
	
	/**
	 * This converts a map from productions to character classes to
	 * two pair-wise arrays, with char-classes unfolded as lists of ints.
	 */
	private void initializeRecovery(IMap robust, int[][] lookaheads, IConstructor[] robustProds) {
		int i = 0;
		
		for (IValue prod : robust) {
			robustProds[i] = (IConstructor) prod;
			List<Integer> chars = new LinkedList<Integer>();
			IList ranges = (IList) robust.get(prod);
			
			for (IValue range : ranges) {
				int from = ((IInteger) ((IConstructor) range).get("begin")).intValue();
				int to = ((IInteger) ((IConstructor) range).get("end")).intValue();
				
				for (int j = from; j <= to; j++) {
					chars.add(j);
				}
			}
			
			lookaheads[i] = new int[chars.size()];
			for (int k = 0; k < chars.size(); k++) {
				lookaheads[i][k] = chars.get(k);
			}
			
			i++;
		}
	}
	
	private ParserGenerator parserGenerator;
	
	public ParserGenerator getParserGenerator() {
		startJob("Compiled -- Loading parser generator", 40);
		if(parserGenerator == null ){
		  if (isBootstrapper()) {
		     throw new CompilerError("Cyclic bootstrapping is occurring, probably because a module in the bootstrap dependencies is using the concrete syntax feature.");
		  }
		 
		  parserGenerator = new ParserGenerator(rex.getMonitor(), rex.getStdErr(), classLoaders, vf, rex.getConfiguration());
		}
		endJob(true);
		return parserGenerator;
	}
	
	private char[] getResourceContent(ISourceLocation location) throws IOException{
		try (Reader in = URIResolverRegistry.getInstance().getCharacterReader(location)) {
			return InputConverter.toChar(in);
		}
	}
	  
	  public IGTD<IConstructor, ITree, ISourceLocation> getParser(String name, IValue start, ISourceLocation loc, boolean force, IMap syntax) {

		if(getBootstrap(name)){
			//stderr.println("Compiled -- getParser: " + name + " returns RascalParser");
			return new RascalParser();
		}
	    ParserGenerator pg = getParserGenerator();
	    IMap definitions = syntax;
	    
	    Class<IGTD<IConstructor, ITree, ISourceLocation>> parser = getObjectParser(name, start);

	    if (parser == null || force) {
	      String parserName = name; // .replaceAll("::", ".");
	     stderr.println("Compiled -- getParser: name = " + name);
	      parser = pg.getNewParser(rex.getMonitor(), loc, parserName, definitions);
	      storeObjectParser(name, start, parser);
	    }

	    try {
	      return parser.newInstance();
	    } catch (InstantiationException e) {
	      throw new CompilerError(e.getMessage() + e);
	    } catch (IllegalAccessException e) {
	      throw new CompilerError(e.getMessage() + e);
	    } catch (ExceptionInInitializerError e) {
	      throw new CompilerError(e.getMessage() + e);
	    }
	  }
	  
	  private boolean getBootstrap(String moduleName) { 
		  return rex.bootstrapParser(moduleName); 
	  }
	 
	  // Rascal library function (interpreter version)
	  public ITree parseFragment(IString name, IValue start, IConstructor tree, ISourceLocation loc, IMap grammar, IEvaluatorContext ctx){
		  if(rex == null){
			  System.err.println("*** parseFragment (interpeter version) creates new rex");
			  rex = new RascalExecutionContext(vf, new PrintWriter(stdout), new PrintWriter(stderr), null, null, null, false, false, false, false, false, false, null, ctx.getEvaluator().getRascalResolver());
		  }
		  return parseFragment(name, start, tree, loc, grammar);
	  }
		
	  // Rascal library function (compiler version)
	  public ITree parseFragment(IString name, IValue start, IConstructor tree, ISourceLocation loc, IMap grammar, RascalExecutionContext rex){ 
		  if(this.rex == null){
			  System.err.println("*** parseFragment (compiler version) sets rex");
			  this.rex = rex;
		  }
		  return parseFragment(name, start, tree, loc, grammar);
	  }
	
	/**
	 * This function will reconstruct a parse tree of a single nested concrete syntax fragment
	 * that has been parsed and its original flat literal string is replaced by a fully structured parse tree.
	 * 
	 */

	ITree parseFragment(IString name, IValue start, IConstructor tree, ISourceLocation uri, IMap grammar) {
		IConstructor prod = (IConstructor) tree.get("prod");
		IConstructor def = (IConstructor) prod.get("def");
		if(def.getName().equals("label")){
			String defName = ((IString) def.get("name")).getValue();
			boolean b = defName.equals("$parsed");
			if(b) return (ITree) tree;
		}
		
	    ITree symTree = TreeAdapter.getArg((ITree) tree, "symbol");
	    ITree lit = TreeAdapter.getArg((ITree) tree, "parts");
	    Map<String, ITree> antiquotes = new HashMap<String,ITree>();
	    
	    IGTD<IConstructor, ITree, ISourceLocation> parser = getBootstrap(name.getValue()) ? new RascalParser() : getParser(name.getValue(), start, TreeAdapter.getLocation((ITree) tree), false, grammar);
	    
	    try {
	      String parserMethodName = getParserGenerator().getParserMethodName(symTree);
	      DefaultNodeFlattener<IConstructor, ITree, ISourceLocation> converter = new DefaultNodeFlattener<IConstructor, ITree, ISourceLocation>();
	      UPTRNodeFactory nodeFactory = new UPTRNodeFactory();
	    
	      char[] input = replaceAntiQuotesByHoles(lit, antiquotes);
	      
	      ITree fragment = (ITree) parser.parse(parserMethodName, uri.getURI(), input, converter, nodeFactory);
	      fragment = replaceHolesByAntiQuotes(fragment, antiquotes);
	      return fragment;
	    }
	    catch (ParseError e) {
	      ISourceLocation loc = TreeAdapter.getLocation((ITree) tree);
	      ISourceLocation src = vf.sourceLocation(loc, loc.getOffset() + e.getOffset(), loc.getLength(), loc.getBeginLine() + e.getBeginLine() - 1, loc.getEndLine() + e.getEndLine() - 1, loc.getBeginColumn() + e.getBeginColumn(), loc.getBeginColumn() + e.getEndColumn());
	      rex.getStdErr().println("***** WARNING: parseFragment, parse error at " + src);
	      //getMonitor().warning("parse error in concrete syntax", src);
	      return (ITree) tree.asAnnotatable().setAnnotation("parseError", src);
	    }
	  }
	  
	  private char[] replaceAntiQuotesByHoles(ITree lit, Map<String, ITree> antiquotes) {
	    IList parts = TreeAdapter.getArgs(lit);
	    StringBuilder b = new StringBuilder();
	    
	    for (IValue elem : parts) {
	    	ITree part = (ITree) elem;
	      String cons = TreeAdapter.getConstructorName(part);
	      
	      if (cons.equals("text")) {
	        b.append(TreeAdapter.yield(part));
	      }
	      else if (cons.equals("newline")) {
	        b.append('\n');
	      }
	      else if (cons.equals("lt")) {
	        b.append('<');
	      }
	      else if (cons.equals("gt")) {
	        b.append('>');
	      }
	      else if (cons.equals("bq")) {
	        b.append('`');
	      }
	      else if (cons.equals("bs")) {
	        b.append('\\');
	      }
	      else if (cons.equals("hole")) {
	        b.append(createHole(part, antiquotes));
	      }
	    }
	    return b.toString().toCharArray();
	  }

	  private String createHole(ITree part, Map<String, ITree> antiquotes) {
	    String ph = getParserGenerator().createHole(part, antiquotes.size());
	    antiquotes.put(ph, part);
	    return ph;
	  }

	  private ITree replaceHolesByAntiQuotes(ITree fragment, final Map<String, ITree> antiquotes) {
		  return (ITree) fragment.accept(new IdentityTreeVisitor<CompilerError>() {

			  @Override
			  public ITree visitTreeAppl(ITree tree)  {
				  String cons = TreeAdapter.getConstructorName(tree);
				  if (cons == null || !cons.equals("$MetaHole") ) {
					  IListWriter w = vf.listWriter();
					  IList args = TreeAdapter.getArgs(tree);
					  for (IValue elem : args) {
						  w.append(elem.accept(this));
					  }
					  args = w.done();

					  return TreeAdapter.setArgs(tree, args);
				  }

				  IConstructor type = retrieveHoleType(tree);
				  return (ITree) antiquotes.get(TreeAdapter.yield(tree)).asAnnotatable().setAnnotation("holeType", type);
			  }

			  private IConstructor retrieveHoleType(ITree tree) {
				  IConstructor prod = TreeAdapter.getProduction(tree);
				  ISet attrs = ProductionAdapter.getAttributes(prod);

				  for (IValue attr : attrs) {
					  if (((IConstructor) attr).getConstructorType() == RascalValueFactory.Attr_Tag) {
						  IValue arg = ((IConstructor) attr).get(0);

						  if (arg.getType().isNode() && ((INode) arg).getName().equals("holeType")) {
							  return (IConstructor) ((INode) arg).get(0);
						  }
					  }
				  }

				  throw new CompilerError("expected to find a holeType, but did not: " + tree);
			  }

			  @Override
			  public ITree visitTreeAmb(ITree arg)  {
				  ISetWriter w = vf.setWriter();
				  for (IValue elem : TreeAdapter.getAlternatives(arg)) {
					  w.insert(elem.accept(this));
				  }
				  return (ITree) arg.set("alternatives", w.done());
			  }
		  });
	  }
}
