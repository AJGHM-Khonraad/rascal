package org.meta_environment.rascal.parser;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.INode;
import org.eclipse.imp.pdb.facts.ISet;
import org.eclipse.imp.pdb.facts.ISetWriter;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.exceptions.FactTypeUseException;
import org.meta_environment.ValueFactoryFactory;
import org.meta_environment.rascal.ast.ASTFactory;
import org.meta_environment.rascal.ast.AbstractAST;
import org.meta_environment.rascal.ast.Command;
import org.meta_environment.rascal.ast.DecimalIntegerLiteral;
import org.meta_environment.rascal.ast.Expression;
import org.meta_environment.rascal.ast.IntegerLiteral;
import org.meta_environment.rascal.ast.JavaFunctionBody;
import org.meta_environment.rascal.ast.Literal;
import org.meta_environment.rascal.ast.Module;
import org.meta_environment.rascal.ast.Name;
import org.meta_environment.rascal.ast.QualifiedName;
import org.meta_environment.rascal.ast.Statement;
import org.meta_environment.rascal.ast.StringLiteral;
import org.meta_environment.rascal.interpreter.asserts.Ambiguous;
import org.meta_environment.rascal.interpreter.asserts.ImplementationError;
import org.meta_environment.uptr.Factory;
import org.meta_environment.uptr.ParsetreeAdapter;
import org.meta_environment.uptr.TreeAdapter;


/**
 * Uses reflection to construct an AST hierarchy from a 
 * UPTR parse node of a rascal program.
 *
 */
public class ASTBuilder {
	private ASTFactory factory;
    private Class<? extends ASTFactory> clazz;
    
	public ASTBuilder(ASTFactory factory) {
		this.factory = factory;
		this.clazz = factory.getClass();
	}
	
	public Module buildModule(IConstructor parseTree) throws FactTypeUseException {
		TreeAdapter tree = new ParsetreeAdapter(parseTree).getTop();
		if (tree.isAppl()) {
			return buildSort(parseTree, "Module");
		}
		else {
			throw new ImplementationError("Ambiguous module?");
		}
	}
	
	public Expression buildExpression(IConstructor parseTree) {
		return buildSort(parseTree, "Expression");
	}
	
	public Statement buildStatement(IConstructor parseTree) {
		return buildSort(parseTree, "Statement");
	}
	
	public Command buildCommand(IConstructor parseTree) {
		return buildSort(parseTree, "Command");
	}
	
	@SuppressWarnings("unchecked")
	private <T extends AbstractAST> T buildSort(IConstructor parseTree, String sort) {
		IConstructor top = (IConstructor) parseTree.get("top");
		TreeAdapter start = new TreeAdapter(top);
		IConstructor tree = (IConstructor) start.getArgs().get(1);
		TreeAdapter treeAdapter = new TreeAdapter(tree); 

		if (sortName(treeAdapter).equals(sort)) {
			return (T) buildValue(tree);
		}
		throw new ImplementationError("This is not a" + sort +  ": " + new TreeAdapter(tree).yield());
	}
	
	private String sortName(TreeAdapter tree) {
		String sortName = tree.getSortName();
		
		if (sortName.startsWith("_")) {
			sortName = sortName.substring(1);
		}
		
		return sortName;
	}
	
	private List<AbstractAST> buildList(IConstructor in)  {
		IList args = new TreeAdapter(in).getListASTArgs();
		List<AbstractAST> result = new ArrayList<AbstractAST>(args.length());
		for (IValue arg: args) {
			result.add(buildValue(arg));
		}
		return result;
	}

	private AbstractAST buildContextFreeNode(IConstructor in)  {
		try {
			TreeAdapter tree = new TreeAdapter(in);

			String cons = tree.getConstructorName();
			String sort = sortName(tree);
			sort = sort.equalsIgnoreCase("pattern") ? "Expression" : capitalize(sort); 
			cons = capitalize(cons);
			
			IList args = tree.getASTArgs();
			int arity = args.length() + 1;
			Class<?> formals[] = new Class<?>[arity];
			Object actuals[] = new Object[arity];

			formals[0] = INode.class;
			actuals[0] = in;

			int i = 1;
			for (IValue arg : args) {
				TreeAdapter argTree = new TreeAdapter((IConstructor) arg);
				if (argTree.isList()) {
					actuals[i] = buildList((IConstructor) arg);
					formals[i] = List.class;
				}
				else {
					actuals[i] = buildValue(arg);
					formals[i] = actuals[i].getClass().getSuperclass();
				}
				i++;
			}

			Method make = clazz.getMethod("make" + sort + cons, formals);
			return (AbstractAST) make.invoke(factory, actuals);
		} catch (SecurityException e) {
			throw unexpectedError(e);
		} catch (NoSuchMethodException e) {
			throw unexpectedError(e);
		} catch (IllegalArgumentException e) {
			throw unexpectedError(e);
		} catch (IllegalAccessException e) {
			throw unexpectedError(e);
		} catch (InvocationTargetException e) {
			throw unexpectedError(e);
		}
	}
	
	private AbstractAST buildAmbNode(INode node, ISet alternatives) {
		try {
			String sort = null;
			List<AbstractAST> alts = new ArrayList<AbstractAST>(alternatives.size());

			for (IValue elem : alternatives) {
				TreeAdapter alt = new TreeAdapter((IConstructor) elem);

				if (alt.isList()) {
					// TODO add support for ambiguous lists
					throw new Ambiguous("Can not deal with ambiguous list: " + 
							node);
				}
				else if (sort == null) {
					sort = sortName(alt);
				}
				
				alts.add(buildValue(elem));
			}
			
			if (alts.size() == 0) {
				throw new ImplementationError("bug: Ambiguity without children!?! " + node);
			}

			sort = capitalize(sort);
			Class<?> formals[] = new Class<?>[]  { INode.class, List.class };
			Object actuals[] = new Object[] { node, alts };

			Method make = clazz.getMethod("make" + sort + "Ambiguity", formals);
			return (AbstractAST) make.invoke(factory, actuals);
		} catch (SecurityException e) {
			throw unexpectedError(e);
		} catch (NoSuchMethodException e) {
			throw unexpectedError(e);
		} catch (IllegalArgumentException e) {
			throw unexpectedError(e);
		} catch (IllegalAccessException e) {
			throw unexpectedError(e);
		} catch (InvocationTargetException e) {
			throw unexpectedError(e);
		}
	}
	
	private AbstractAST buildLexicalNode(IConstructor in) {
		try {
			TreeAdapter tree = new TreeAdapter(in);

			String sort = sortName(tree);
			String Sort = capitalize(sort);

			Class<?> formals[] = new Class<?>[] { INode.class, String.class };
			Object actuals[] = new Object[] { in, new String(new TreeAdapter(in).yield()) };

			Method make = clazz.getMethod("make" + Sort + "Lexical", formals);
			return (AbstractAST) make.invoke(factory, actuals);
		} catch (SecurityException e) {
			throw unexpectedError(e);
		} catch (NoSuchMethodException e) {
			throw unexpectedError(e);
		} catch (IllegalArgumentException e) {
			throw unexpectedError(e);
		} catch (IllegalAccessException e) {
			throw unexpectedError(e);
		} catch (InvocationTargetException e) {
			throw unexpectedError(e);
		}
	}
	
	private ImplementationError unexpectedError(Throwable e) {
		return new ImplementationError("Unexpected error in AST construction: " + e, e);
	}

	private boolean isEmbedding(TreeAdapter tree) {
		String name = tree.getConstructorName();
		return name.equals("ConcreteQuoted") 
		|| name.equals("ConcreteUnquoted") 
		|| name.equals("ConcreteTypedQuoted");
	}
		
	private AbstractAST buildValue(IValue arg)  {
		TreeAdapter tree = new TreeAdapter((IConstructor) arg);
		
		if (tree.isAmb()) {
			return filter(arg, tree);
		}
		
		if (!tree.isAppl()) {
			throw new UnsupportedOperationException();
		}	
		
		if (tree.isLexToCf()) {
			return buildLexicalNode((IConstructor) ((IList) ((IConstructor) arg).get("args")).get(0));
		}
		else if (sortName(tree).equals("FunctionBody") && tree.getConstructorName().equals("Java")) {
			return new JavaFunctionBody((INode) arg, tree.yield());
		}
		else if (sortName(tree).equals("Pattern") && isEmbedding(tree)) {
			return lift(tree, true);
		}
		else if (sortName(tree).equals("Expression") && isEmbedding(tree)) {
			return lift(tree, false);
		}
			
		return buildContextFreeNode((IConstructor) arg);
	}

	private AbstractAST filter(IValue arg, TreeAdapter tree) {
		ISet alts = tree.getAlternatives();
		ISetWriter winners = ValueFactoryFactory.getValueFactory().setWriter(Factory.Tree);
		int winnerConcreteSize = -1;
		
		for (IValue alt : alts) {
		   int size = countConcretePatternsSize(alt);
		   if (size > winnerConcreteSize) {
			   winners = ValueFactoryFactory.getValueFactory().setWriter(Factory.Tree);
			   winners.insert(alt);
			   winnerConcreteSize = size;
		   }
		   else if (size == winnerConcreteSize) {
			   winners.insert(alt);
		   }
		}
		
		return buildAmbNode((INode) arg, winners.done());
	}

	private int countConcretePatternsSize(IValue alt) {
		return 1;
	}

	private AbstractAST lift(TreeAdapter tree, boolean match) {
		IConstructor pattern = getConcretePattern(tree);
		return lift(pattern, pattern, match);
	}

	private Expression lift(IValue pattern, IConstructor source, boolean match) {
		if (pattern.getType().isNodeType()) {
			INode node = (INode) pattern;
			
			if (pattern.getType().isAbstractDataType()) {
				IConstructor constr = (IConstructor) pattern;

				if (constr.getConstructorType() == Factory.Tree_Appl) {
					TreeAdapter tree = new TreeAdapter(constr);
					String cons = tree.getConstructorName();
					if (cons != null && (cons.equals("MetaVariable") || cons.equals("TypedMetaVariable"))) {
						return liftVariable(tree);
					}

					if (match && tree.getProduction().getRhs().isLayout()) {
						return wildCard(constr);
					}

					source = constr;
				}
			}

			String name = node.getName();
			List<Expression> args = new LinkedList<Expression>();

			for (IValue child : node) {
				args.add(lift(child, source, match));
			}

			return new Expression.CallOrTree(source, makeQualifiedName(source, name), args);
		}
		else if (pattern.getType().isListType()) {
			IList list = (IList) pattern;
			List<Expression> result = new ArrayList<Expression>(list.length());
			for (IValue arg: list) {
				result.add(lift(arg, source, match));
			}
			return new Expression.List(source, result);
		}
		else if (pattern.getType().isStringType()) {
			return new Expression.Literal(source, new Literal.String(source, new StringLiteral.Lexical(source, pattern.toString())));
		}
		else if (pattern.getType().isIntegerType()) {
			return new Expression.Literal(source, new Literal.Integer(source, new IntegerLiteral.DecimalIntegerLiteral(source, new DecimalIntegerLiteral.Lexical(source, pattern.toString()))));
		}
		else {
			throw new ImplementationError("Illegal value encountered while lifting a concrete syntax pattern:" + pattern);
		}
	}

	private Expression wildCard(IConstructor node) {
		return new Expression.QualifiedName(node, makeQualifiedName(node, "_"));
	}

	private QualifiedName makeQualifiedName(IConstructor node, String name) {
		Name simple = new Name.Lexical(node, name);
		List<Name> list = new ArrayList<Name>(1);
		list.add(simple);
		return new QualifiedName.Default(node, list);
	}

	private Expression liftVariable(TreeAdapter tree) {
		String cons = tree.getConstructorName();
		String varName;
		
		if (cons.equals("MetaVariable")) {
			varName = new TreeAdapter((IConstructor) tree.getASTArgs().get(0)).yield();
		}
		else if (cons.equals("TypedMetaVariable")) {
			varName = new TreeAdapter((IConstructor) tree.getASTArgs().get(1)).yield();
		}
		else {
			throw new ImplementationError("Unexpected meta variable while lifting pattern");
		}
		
		return new Expression.QualifiedName(tree.getTree(), makeQualifiedName(tree.getTree(), varName));
	}

	private IConstructor getConcretePattern(TreeAdapter tree) {
		String cons = tree.getConstructorName();
		IConstructor pattern;
		
		if (cons.equals("ConcreteQuoted")) {
			pattern = (IConstructor) tree.getASTArgs().get(0);
		}
		else if (cons.equals("ConcreteUnquoted")) {
			pattern = (IConstructor) tree.getASTArgs().get(0);
		}
		else if (cons.equals("ConcreteTypedQuoted")) {
			pattern = (IConstructor) tree.getASTArgs().get(1);
		}
		else {
			throw new ImplementationError("Unexpected embedding syntax");
		}
		
		return pattern;
	}

	private String capitalize(String sort) {
		if (sort.length() > 1) {
			return Character.toUpperCase(sort.charAt(0)) + sort.substring(1);
		}
		
		return sort.toUpperCase();
	}

}
