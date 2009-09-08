package org.meta_environment.uptr;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IInteger;
import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IListWriter;
import org.eclipse.imp.pdb.facts.ISet;
import org.eclipse.imp.pdb.facts.ISetWriter;
import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.eclipse.imp.pdb.facts.exceptions.FactTypeUseException;
import org.eclipse.imp.pdb.facts.visitors.VisitorException;
import org.meta_environment.ValueFactoryFactory;
import org.meta_environment.rascal.interpreter.asserts.ImplementationError;
import org.meta_environment.rascal.parser.MappingsCache;
import org.meta_environment.uptr.visitors.IdentityTreeVisitor;


public class TreeAdapter{
	
	private TreeAdapter(){
		super();
	}
	
	public static boolean isAppl(IConstructor tree) {
		return tree.getConstructorType() == Factory.Tree_Appl;
	}
	
	public static boolean isAmb(IConstructor tree) {
		return tree.getConstructorType() == Factory.Tree_Amb;
	}
	
	public static boolean isChar(IConstructor tree) {
		return tree.getConstructorType() == Factory.Tree_Char;
	}
	
	public static boolean isCycle(IConstructor tree) {
		return tree.getConstructorType() == Factory.Tree_Cycle;
	}
	
	public static IConstructor getProduction(IConstructor tree) {
		return (IConstructor) tree.get("prod");
	}
	
	public static boolean hasSortName(IConstructor tree) {
		return ProductionAdapter.hasSortName(getProduction(tree));
	}
	
	public static String getSortName(IConstructor tree) throws FactTypeUseException {
		return ProductionAdapter.getSortName(getProduction(tree));
	}
	
	public static String getConstructorName(IConstructor tree) {
		return ProductionAdapter.getConstructorName(getProduction(tree));
	}
	
	public static boolean isProduction(IConstructor tree, String sortName, String consName) {
		IConstructor prod = getProduction(tree);
		return ProductionAdapter.getSortName(prod).equals(sortName) && ProductionAdapter.getConstructorName(prod).equals(consName);
	}

	public static boolean isLexToCf(IConstructor tree) {
		return isAppl(tree) ? ProductionAdapter.isLexToCf(getProduction(tree)) : false;
	}
	
	public static boolean isContextFree(IConstructor tree) {
		return isAppl(tree) ? ProductionAdapter.isContextFree(getProduction(tree)) : false;
	}
	
	public static boolean isList(IConstructor tree) {
		return isAppl(tree) ? ProductionAdapter.isList(getProduction(tree)) : false;
	}
	
	public static IList getArgs(IConstructor tree) {
		if (isAppl(tree)) {
		  return (IList) tree.get("args");
		}
		
		throw new ImplementationError("Node has no args");
	}
	
	public static boolean isLiteral(IConstructor tree) {
		return isAppl(tree) ? ProductionAdapter.isLiteral(getProduction(tree)) : false;
	}
	
	public static IList getListASTArgs(IConstructor tree) {
		if (!isContextFree(tree) || !isList(tree)) {
			throw new ImplementationError("This is not a context-free list production: " + tree);
		}
		IList children = getArgs(tree);
		IListWriter writer = Factory.Args.writer(ValueFactoryFactory.getValueFactory());
		
		for (int i = 0; i < children.length(); i++) {
			IValue kid = children.get(i);
			writer.append(kid);	
			// skip layout and/or separators
			i += (isSeparatedList(tree) ? 3 : 1);
		}
		return writer.done();
	}
	
	public static boolean isLexical(IConstructor tree) {
		return isAppl(tree) ? ProductionAdapter.isLexical(getProduction(tree)) : false;
	}
	
	public static boolean isLayout(IConstructor tree) {
		return isAppl(tree) ? ProductionAdapter.isLayout(getProduction(tree)) : false;
	}

	private static boolean isSeparatedList(IConstructor tree) {
		return isAppl(tree) ? isList(tree) && ProductionAdapter.isSeparatedList(getProduction(tree)) : false;
	}

	public static IList getASTArgs(IConstructor tree) {
		if (!isContextFree(tree)) {
			throw new ImplementationError("This is not a context-free production: " + tree);
		}

		IList children = getArgs(tree);
		IListWriter writer = Factory.Args.writer(ValueFactoryFactory.getValueFactory());

		for (int i = 0; i < children.length(); i++) {
			IConstructor kid = (IConstructor) children.get(i);
			if (!isLiteral(kid) && !isCILiteral(kid)) {
				writer.append(kid);	
			} 
			// skip layout
			i++;
		}
		return writer.done();
	}

	public static boolean isCILiteral(IConstructor tree) {
		return isAppl(tree) ? ProductionAdapter.isCILiteral(getProduction(tree)) : false;
	}
	
	public static ISet getAlternatives(IConstructor tree) {
		if (isAmb(tree)) {
		  return (ISet) tree.get("alternatives");
		}
		
		throw new ImplementationError("Node has no alternatives");
	}
	
	public static ISourceLocation getLocation(IConstructor tree) {
		return (ISourceLocation) tree.getAnnotation(Factory.Location);
	}
	
	public static int getCharacter(IConstructor tree) {
		return ((IInteger) tree.get("character")).intValue();
	}
	
	protected static class PositionAnnotator{
		private final IConstructor tree;
		private final MappingsCache<PositionNode, IConstructor> cache;
		
		private boolean inLayout = false;
		private boolean labelLayout = false;
		
		public PositionAnnotator(IConstructor tree){
			super();
			
			this.tree = tree;
			this.cache = new MappingsCache<PositionNode, IConstructor>();
		}
		
		public IConstructor addPositionInformation(String filename) {
			Factory.getInstance(); // make sure everything is declared
			try {
				return addPosInfo(tree, filename, new Position());
			} catch (MalformedURLException e) {
				throw new RuntimeException(e);
			}
		}
		
		private IConstructor addPosInfo(IConstructor tree, String filename, Position cur) throws MalformedURLException{
			IValueFactory factory = ValueFactoryFactory.getValueFactory();
			
			int startLine = cur.line;
			int startCol = cur.col;
			int startOffset = cur.offset;
			PositionNode positionNode = new PositionNode(tree, cur.offset);
			IConstructor result = cache.get(positionNode);

			if(result != null){
				ISourceLocation loc = getLocation(result);
				cur.col = loc.getEndColumn();
				cur.line = loc.getEndLine();
				cur.offset += loc.getLength();
				return result;
			}

			if(isChar(tree)){
				cur.offset++;
				if(((char) getCharacter(tree)) == '\n'){
					cur.col = 0;
					cur.line++;
				}else{
					cur.col++;
				}
				return tree;
			}

			if(isAppl(tree)){
				boolean outermostLayout = false;
				IList args = getArgs(tree);

				if(isLayout(tree)){
					inLayout = true;
					outermostLayout = true;
				}

				IListWriter newArgs = factory.listWriter(Factory.Tree);
				for(IValue arg : args){
					newArgs.append(addPosInfo((IConstructor) arg, filename, cur));
				}
				tree = tree.set("args", newArgs.done());

				if(!labelLayout && outermostLayout){
					inLayout = false;
					return tree;
				}else if(!labelLayout && inLayout){
					return tree;
				}

			}else if(isAmb(tree)){
				ISet alts = getAlternatives(tree);
				ISetWriter newAlts = ValueFactoryFactory.getValueFactory().setWriter(Factory.Tree);
				Position save = cur;
				Position newPos = save;
				ISetWriter cycles = ValueFactoryFactory.getValueFactory().setWriter(Factory.Tree);

				for(IValue arg : alts){
					cur = save.clone();

					IValue newArg = addPosInfo((IConstructor) arg, filename, cur);

					if(cur.offset != save.offset){
						newPos = cur;
						newAlts.insert(newArg);
					}else if(newPos.offset == save.offset){
						cycles.insert(arg);
					}else{
						newAlts.insert(newArg);
					}
				}

				cur.col = newPos.col;
				cur.line = newPos.line;
				cur.offset = newPos.offset;

				for(IValue arg : cycles.done()){
					IValue newArg = addPosInfo((IConstructor) arg, filename, cur);
					newAlts.insert(newArg);
				}

				tree = tree.set("alternatives", newAlts.done());
			}else if(!isCycle(tree)){
				System.err.println("unhandled tree: " + tree + "\n");
			}

			ISourceLocation loc = factory.sourceLocation(filename, startOffset, cur.offset - startOffset, startLine, cur.line, startCol, cur.col);
			result = tree.setAnnotation(Factory.Location, loc);
			
			cache.putUnsafe(positionNode, result);

			return result;
		}

		private static class Position{
			public int col = 0;
			public int line = 1;
			public int offset = 0;
			
			public Position clone() {
				Position tmp = new Position();
				tmp.col = col;
				tmp.line = line;
				tmp.offset = offset;
				return tmp;
			}
		}
		
		private static class PositionNode{
			private final IConstructor tree;
			private final int offset;
			
			public PositionNode(IConstructor tree, int offset){
				super();
				
				this.tree = tree;
				this.offset = offset;
			}
			
			public int hashCode(){
				return ((offset << 32) ^ tree.hashCode());
			}
			
			public boolean equals(Object o){
				if(o.getClass() != getClass()) return false;
				
				PositionNode other = (PositionNode) o;
				
				return (offset == other.offset && tree == other.tree); // NOTE: trees are shared, so they are pointer equal.
			}
		}
	}
	
	private static class Unparser extends IdentityTreeVisitor {
		private final OutputStream fStream;

		public Unparser(OutputStream stream) {
			fStream = stream;
		}

		@Override
		public IConstructor visitTreeAmb(IConstructor arg) throws VisitorException {
			((ISet) arg.get("alternatives")).iterator().next().accept(this);
			return arg;
		}

		@Override
		public IConstructor visitTreeChar(IConstructor arg) throws VisitorException {
			try {
				fStream.write(((IInteger) arg.get("character")).intValue());
				return arg;
			} catch (IOException e) {
				throw new VisitorException(e);
			}
		}
		
		@Override
		public IConstructor visitTreeAppl(IConstructor arg) throws VisitorException {
			IList children = (IList) arg.get("args");
			for (IValue child : children) {
				child.accept(this);
			}
			return arg;
		}

	}
	
	public static void unparse(IConstructor tree, OutputStream stream) throws IOException, FactTypeUseException {
		try {
			if (tree.getConstructorType() == Factory.ParseTree_Top) {
				tree.get("top").accept(new Unparser(stream));
			} else if (tree.getType() == Factory.Tree) {
				tree.accept(new Unparser(stream));
			} else {
				throw new ImplementationError("Can not unparse this "
						+ tree.getType());
			}
		} catch (VisitorException e) {
			Throwable cause = e.getCause();

			if (cause instanceof IOException) {
				throw (IOException) cause;
			}
			
			System.err.println("Unexpected error in unparse: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static String yield(IConstructor tree) throws FactTypeUseException {
		try {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			unparse(tree, stream);
			return stream.toString();
		} catch (IOException e) {
			throw new ImplementationError("Method yield failed", e);
		}
	}

	public static boolean isContextFreeInjectionOrSingleton(IConstructor tree) {
		IConstructor prod = getProduction(tree);
		if (isAppl(tree)) {
			if (!ProductionAdapter.isList(prod) && ProductionAdapter.getLhs(prod).length() == 1) {
				IConstructor rhs = ProductionAdapter.getRhs(prod);
				if (SymbolAdapter.isCf(rhs)) {
					rhs = SymbolAdapter.getSymbol(rhs);
					if (SymbolAdapter.isSort(rhs)) {
						return true;
					}
				}
			}
		}
		else if (isList(tree) && SymbolAdapter.isCf(ProductionAdapter.getRhs(prod))) {
			if (getArgs(tree).length() == 1) {
				return true;
			}
		}
		return false;
	}

	public static boolean isAmbiguousList(IConstructor tree) {
		if (isAmb(tree)) {
			IConstructor first = (IConstructor) getAlternatives(tree).iterator().next();
			if (isList(first)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isNonEmptyStarList(IConstructor tree) {
		if (isAppl(tree)) {
			IConstructor prod = getProduction(tree);
			
			if (ProductionAdapter.isList(prod)) {
				IConstructor sym = ProductionAdapter.getRhs(prod);
				
				if (SymbolAdapter.isCf(sym) || SymbolAdapter.isLex(sym)) {
					sym = SymbolAdapter.getSymbol(sym);
				}
				
				if (SymbolAdapter.isIterStar(sym) || SymbolAdapter.isIterStarSep(sym)) {
					return getArgs(tree).length() > 0;
				}
			}
		}
		return false;
	}

	public static boolean isCFList(IConstructor tree) {
		return isAppl(tree) && isContextFree(tree) && (SymbolAdapter.isPlusList(ProductionAdapter.getRhs(getProduction(tree))) ||
				SymbolAdapter.isStarList(ProductionAdapter.getRhs(getProduction(tree))));
	}

	/**
	 * @return true if the tree does not have any characters, it's just an empty derivation
	 */
	public static boolean isEpsilon(IConstructor tree) {
		if (isAppl(tree)) {
			for (IValue arg : getArgs(tree)) {
				boolean argResult = isEpsilon((IConstructor) arg);
				
				if (argResult == false) {
					return false;
				}
			}
			
			return true;
		}
	
		if (isAmb(tree)) {
			return isEpsilon((IConstructor) getAlternatives(tree).iterator().next());
		}
		
		if (isCycle(tree)) {
			return true;
		}

		// is a character
		return false;
	}

	public static boolean hasPreferAttribute(IConstructor tree) {
		return ProductionAdapter.hasPreferAttribute(getProduction(tree));
	}

	public static boolean hasAvoidAttribute(IConstructor tree) {
		return ProductionAdapter.hasAvoidAttribute(getProduction(tree));
	}
}
