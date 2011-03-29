package org.rascalmpl.library;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IListWriter;
import org.eclipse.imp.pdb.facts.ISetWriter;
import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.eclipse.imp.pdb.facts.type.Type;
import org.eclipse.imp.pdb.facts.type.TypeStore;
import org.rascalmpl.interpreter.IEvaluatorContext;
import org.rascalmpl.interpreter.Typeifier;
import org.rascalmpl.interpreter.staticErrors.SyntaxError;
import org.rascalmpl.interpreter.types.NonTerminalType;
import org.rascalmpl.interpreter.types.ReifiedType;
import org.rascalmpl.interpreter.utils.RuntimeExceptionFactory;
import org.rascalmpl.values.uptr.Factory;
import org.rascalmpl.values.uptr.ProductionAdapter;
import org.rascalmpl.values.uptr.SymbolAdapter;
import org.rascalmpl.values.uptr.TreeAdapter;

public class ParseTree {
	private final IValueFactory values;
	
	public ParseTree(IValueFactory values){
		super();
		
		this.values = values;
	}

	public IValue parse(IConstructor start, ISourceLocation input, IEvaluatorContext ctx) {
		Type reified = start.getType();
		IConstructor startSort = checkPreconditions(start, reified);
		
		try {
			IConstructor pt = ctx.getEvaluator().parseObject(ctx.getEvaluator().getMonitor(), startSort, input.getURI());

			if (TreeAdapter.isAppl(pt)) {
				if (SymbolAdapter.isStart(ProductionAdapter.getRhs(TreeAdapter.getProduction(pt)))) {
					pt = (IConstructor) TreeAdapter.getArgs(pt).get(1);
				}
			}
			return pt;
		}
		catch (SyntaxError e) {
			throw RuntimeExceptionFactory.parseError(e.getLocation(), ctx.getCurrentAST(), ctx.getStackTrace());
		}
	}

	public IValue parseWithErrorTree(IConstructor start, ISourceLocation input, IEvaluatorContext ctx){
		Type reified = start.getType();
		IConstructor startSort = checkPreconditions(start, reified);
		
		IConstructor pt = ctx.getEvaluator().parseObjectWithErrorTree(ctx.getEvaluator().getMonitor(), startSort, input.getURI());

		if(SymbolAdapter.isStart(ProductionAdapter.getRhs(TreeAdapter.getProduction(pt)))){
			pt = (IConstructor) TreeAdapter.getArgs(pt).get(1);
		}
		return pt;
	}
	
	public IValue parse(IConstructor start, IString input, IEvaluatorContext ctx) {
		Type reified = start.getType();
		IConstructor startSort = checkPreconditions(start, reified);
		try {
			IConstructor pt = ctx.getEvaluator().parseObject(ctx.getEvaluator().getMonitor(), startSort, input.getValue());

			if (TreeAdapter.isAppl(pt)) {
				if (SymbolAdapter.isStart(ProductionAdapter.getRhs(TreeAdapter.getProduction(pt)))) {
					pt = (IConstructor) TreeAdapter.getArgs(pt).get(1);
				}
			}

			return pt;
		}
		catch (SyntaxError e) {
			throw RuntimeExceptionFactory.parseError(e.getLocation(), ctx.getCurrentAST(), ctx.getStackTrace());
		}
	}
	
	public IValue parseWithErrorTree(IConstructor start, IString input, IEvaluatorContext ctx){
		Type reified = start.getType();
		IConstructor startSort = checkPreconditions(start, reified);
		
		IConstructor pt = ctx.getEvaluator().parseObjectWithErrorTree(ctx.getEvaluator().getMonitor(), startSort, input.getValue());

		if(SymbolAdapter.isStart(ProductionAdapter.getRhs(TreeAdapter.getProduction(pt)))){
			pt = (IConstructor) TreeAdapter.getArgs(pt).get(1);
		}

		return pt;
	}
	
	public IValue parse(IConstructor start, IString input, ISourceLocation loc, IEvaluatorContext ctx) {
		Type reified = start.getType();
		IConstructor startSort = checkPreconditions(start, reified);
		try {
			IConstructor pt = ctx.getEvaluator().parseObject(ctx.getEvaluator().getMonitor(), startSort, input.getValue(), loc);

			if (TreeAdapter.isAppl(pt)) {
				if (SymbolAdapter.isStart(ProductionAdapter.getRhs(TreeAdapter.getProduction(pt)))) {
					pt = (IConstructor) TreeAdapter.getArgs(pt).get(1);
				}
			}

			return pt;
		}
		catch (SyntaxError e) {
			throw RuntimeExceptionFactory.parseError(e.getLocation(), ctx.getCurrentAST(), ctx.getStackTrace());
		}
	}
	
	public IValue parseWithErrorTree(IConstructor start, IString input, ISourceLocation loc, IEvaluatorContext ctx) {
		Type reified = start.getType();
		IConstructor startSort = checkPreconditions(start, reified);
		
		IConstructor pt = ctx.getEvaluator().parseObjectWithErrorTree(ctx.getEvaluator().getMonitor(), startSort, input.getValue(), loc);
		
		if(SymbolAdapter.isStart(ProductionAdapter.getRhs(TreeAdapter.getProduction(pt)))){
			pt = (IConstructor) TreeAdapter.getArgs(pt).get(1);
		}
		
		return pt;
	}
	
	public IString unparse(IConstructor tree) {
		return values.string(TreeAdapter.yield(tree));
	}
	
	private IValue implode(TypeStore store, Type type, IConstructor tree, boolean splicing) {
		while (type.isAliasType()) {
			type = type.getAliased();
		}
		
		if (TreeAdapter.isLexical(tree)) {
			java.lang.String constructorName = unescapedConsName(tree);
			java.lang.String yield = TreeAdapter.yield(tree);
			if (type.isAbstractDataType() && constructorName != null) {
				// make a single argument constructor  with yield as argument
				// if there is a singleton constructor with a str argument
				Type cons = findConstructor(type, constructorName, 1, store);
				if (cons != null && cons.getFieldType(0).isStringType()) {
					ISourceLocation loc = TreeAdapter.getLocation(tree);
					IConstructor ast = values.constructor(cons, values.string(yield));
					return ast.setAnnotation("location", loc);
				}
				
			}
			if (type.isIntegerType()) {
				return values.integer(yield);
			}
			if (type.isRealType()) {
				return values.real(yield);
			}
			if (type.isBoolType()) {
				if (yield.equals("true")) {
					return values.bool(true);
				}
				if (yield.equals("false")) {
					return values.bool(false);
				}
				throw RuntimeExceptionFactory.illegalArgument(tree, null, null);
			}
			if (type.isStringType()) {
				return values.string(yield);
			}
			
			throw RuntimeExceptionFactory.illegalArgument(tree, null, null);
		}
		
		if (TreeAdapter.isList(tree)) {
			if (!type.isListType() && !splicing) {
				throw RuntimeExceptionFactory.illegalArgument(tree, null, null);
			}
			Type elementType = splicing ? type : type.getElementType();
			IListWriter w = values.listWriter(elementType);
			for (IValue arg: TreeAdapter.getListASTArgs(tree)) {
				w.append(implode(store, elementType, (IConstructor) arg, false));
			}
			return w.done();
		}
		
		if (TreeAdapter.isOpt(tree) && type.isBoolType()) {
			IList args = TreeAdapter.getArgs(tree);
			if (args.isEmpty()) {
				return values.bool(false);
			}
			return values.bool(true);
		}
		
		if (TreeAdapter.isOpt(tree)) {
			if (!type.isListType()) {
				throw RuntimeExceptionFactory.illegalArgument(tree, null, null);
			}
			Type elementType = type.getElementType();
			IListWriter w = values.listWriter(elementType);
			for (IValue arg: TreeAdapter.getASTArgs(tree)) {
				IValue implodedArg = implode(store, elementType, (IConstructor) arg, true);
				if (implodedArg instanceof IList) {
					// splicing
					for (IValue nextArg: (IList)implodedArg) {
						w.append(nextArg);
					}
				}
				else {
					w.append(implodedArg);
				}
				// opts should have one argument (if any at all)
				break;
			}
			return w.done();
		}
		
		if (TreeAdapter.isAmb(tree)) {
			if (!type.isSetType()) {
				throw RuntimeExceptionFactory.illegalArgument(tree, null, null);
			}
			Type elementType = type.getElementType();
			ISetWriter w = values.setWriter(elementType);
			for (IValue arg: TreeAdapter.getAlternatives(tree)) {
				w.insert(implode(store, elementType, (IConstructor) arg, false));
			}
			return w.done();
		}
		
		if (ProductionAdapter.hasAttribute(TreeAdapter.getProduction(tree), Factory.Attribute_Bracket)) {
			return implode(store, type, (IConstructor) TreeAdapter.getASTArgs(tree).get(0), false);
		}
		
		if (TreeAdapter.isAppl(tree)) {
			IList args = TreeAdapter.getASTArgs(tree);
			int length = args.length();

			java.lang.String constructorName = unescapedConsName(tree);			
			
			if (constructorName == null) {
				if (length == 1) {
					// jump over injection
					return implode(store, type, (IConstructor) args.get(0), splicing);
				}
				
				// make a tuple
				if (!type.isTupleType()) {
					throw RuntimeExceptionFactory.illegalArgument(tree, null, null);
				}
				
				if (length != type.getArity()) {
					throw RuntimeExceptionFactory.arityMismatch(type.getArity(), length, null, null);
				}

				return values.tuple(implodeArgs(store, type, args));
			}
			
			
			// make a constructor
			if (!type.isAbstractDataType()) {
				throw RuntimeExceptionFactory.illegalArgument(tree, null, null);
			}

			Type cons = findConstructor(type, constructorName, length, store);
			if (cons != null) {
				ISourceLocation loc = TreeAdapter.getLocation(tree);
				IConstructor ast = values.constructor(cons, implodeArgs(store, cons, args));
				return ast.setAnnotation("location", loc);
			}
			
		}
		
		throw RuntimeExceptionFactory.illegalArgument(tree, null, null);
	}

	private java.lang.String unescapedConsName(IConstructor tree) {
		java.lang.String x = TreeAdapter.getConstructorName(tree);
		if (x != null) {
			x = x.replaceAll("\\\\", "");
		}
		return x;
	}
	
	private Type findConstructor(Type type, java.lang.String constructorName, int arity,  TypeStore store) {
		for (Type candidate: store.lookupConstructor(type, constructorName)) {
			// It finds the first with suitable arity, so this is inaccurate
			// if there are overloaded constructors with the same arity
			if (arity == candidate.getArity()) {
				return candidate;
			}
		}
		return null;
	}

	private IValue[] implodeArgs(TypeStore store, Type type, IList args) {
		int length = args.length();
		IValue implodedArgs[] = new IValue[length];
		for (int i = 0; i < length; i++) {
			implodedArgs[i] = implode(store, type.getFieldType(i), (IConstructor)args.get(i), false);
		}
		return implodedArgs;
	}
	
	public IValue implode(IConstructor reifiedType, IConstructor tree) {
		TypeStore store = new TypeStore();
		Type type = Typeifier.declare(reifiedType, store);
		return implode(store, type, tree, false);
	}
	
	private static IConstructor checkPreconditions(IConstructor start, Type reified) {
		if (!(reified instanceof ReifiedType)) {
		   throw RuntimeExceptionFactory.illegalArgument(start, null, null);
		}
		
		Type nt = reified.getTypeParameters().getFieldType(0);
		
		if (!(nt instanceof NonTerminalType)) {
			throw RuntimeExceptionFactory.illegalArgument(start, null, null);
		}
		
		IConstructor symbol = ((NonTerminalType) nt).getSymbol();
		
		return symbol;
	}
}
