/*******************************************************************************
 * Copyright (c) 2009-2013 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:

 *   * Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI
 *   * Arnold Lankamp - Arnold.Lankamp@cwi.nl
*******************************************************************************/
package org.rascalmpl.values.uptr;

import java.util.Iterator;
import java.util.Map;

import org.eclipse.imp.pdb.facts.IAnnotatable;
import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IInteger;
import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IListRelation;
import org.eclipse.imp.pdb.facts.INode;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.eclipse.imp.pdb.facts.IWithKeywordParameters;
import org.eclipse.imp.pdb.facts.exceptions.FactTypeUseException;
import org.eclipse.imp.pdb.facts.exceptions.UndeclaredFieldException;
import org.eclipse.imp.pdb.facts.impl.AbstractDefaultAnnotatable;
import org.eclipse.imp.pdb.facts.impl.AbstractDefaultWithKeywordParameters;
import org.eclipse.imp.pdb.facts.impl.AbstractValueFactoryAdapter;
import org.eclipse.imp.pdb.facts.impl.AnnotatedConstructorFacade;
import org.eclipse.imp.pdb.facts.impl.ConstructorWithKeywordParametersFacade;
import org.eclipse.imp.pdb.facts.impl.persistent.ValueFactory;
import org.eclipse.imp.pdb.facts.io.StandardTextWriter;
import org.eclipse.imp.pdb.facts.type.Type;
import org.eclipse.imp.pdb.facts.type.TypeFactory;
import org.eclipse.imp.pdb.facts.type.TypeStore;
import org.eclipse.imp.pdb.facts.util.AbstractSpecialisedImmutableMap;
import org.eclipse.imp.pdb.facts.util.ImmutableMap;
import org.eclipse.imp.pdb.facts.visitors.IValueVisitor;
import org.rascalmpl.interpreter.types.ReifiedType;

/**
 * UPTR stands for Universal Parse Node Representation (formerly known as AsFix). It is
 * an abstract syntax for Rascal production rules, completed with constructors for parse forests.
 * <p>
 * UPTR is produced by parser implementations (as generated from Rascal grammars for example).
 * <p>
 * UPTR is consumed by tools that manipulate parse trees in general (such as
 * automatic syntax high-lighters) or tools that manipulate specific parse trees (such
 * as the Rascal interpreter).
 * 1
 */
public class Factory extends AbstractValueFactoryAdapter {
	public final static TypeStore uptr = new TypeStore(
			org.rascalmpl.values.errors.Factory.getStore(), 
			org.rascalmpl.values.locations.Factory.getStore());
	private final static TypeFactory tf = TypeFactory.getInstance();
	
	private static final Type str = tf.stringType();
	
	public static final Type TypeParam = tf.parameterType("T");
	public static final Type Type = new ReifiedType(TypeParam);
	
	static {
		uptr.declareAbstractDataType(Type);
	}

	private final IList EMPTY_LIST = listWriter().done();
	
	public static final Type Tree = tf.abstractDataType(uptr, "Tree");
	public static final Type Production = tf.abstractDataType(uptr, "Production");
	public static final Type Attributes = tf.abstractDataType(uptr, "Attributes");
	public static final Type Attr = tf.abstractDataType(uptr, "Attr");
	public static final Type Associativity = tf.abstractDataType(uptr, "Associativity");
	public static final Type Symbol = tf.abstractDataType(uptr, "Symbol");
	public static final Type CharRange = tf.abstractDataType(uptr, "CharRange");
	public static final Type Args = tf.listType(Tree);
	public static final Type Attrs = tf.setType(Attr);
	public static final Type Symbols = tf.listType(Symbol);
	public static final Type CharRanges = tf.listType(CharRange);
	public static final Type Alternatives = tf.setType(Tree);

	public static final Type Type_Reified = tf.constructor(uptr, Type, "type", Symbol, "symbol", tf.mapType(Symbol , Production), "definitions");
					
	public static final Type Tree_Appl = tf.constructor(uptr, Tree, "appl", Production, "prod", tf.listType(Tree), "args");
	public static final Type Tree_Cycle = tf.constructor(uptr, Tree, "cycle", Symbol, "symbol", tf.integerType(), "cycleLength");
	public static final Type Tree_Amb = tf.constructor(uptr, Tree, "amb", Alternatives, "alternatives");
	public static final Type Tree_Char = tf.constructor(uptr, Tree, "char", tf.integerType(), "character");
	
	public static final Type Production_Default = tf.constructor(uptr, Production, "prod", Symbol, "def", tf.listType(Symbol), "symbols",  tf.setType(Attr), "attributes");
	public static final Type Production_Regular = tf.constructor(uptr, Production, "regular", Symbol, "def");
	public static final Type Production_Error = tf.constructor(uptr, Production, "error", Production, "prod", tf.integerType(), "dot");
	public static final Type Production_Skipped = tf.constructor(uptr, Production, "skipped");
	
	public static final Type Production_Cons = tf.constructor(uptr, Production, "cons", Symbol, "def", tf.listType(Symbol), "symbols", tf.listType(Symbol), "kwTypes", tf.setType(Attr), "attributes");
	public static final Type Production_Func = tf.constructor(uptr, Production, "func", Symbol, "def", tf.listType(Symbol), "symbols", tf.listType(Symbol), "kwTypes", tf.setType(Attr), "attributes");
	public static final Type Production_Choice = tf.constructor(uptr, Production, "choice", Symbol, "def", tf.setType(Production), "alternatives");
	public static final Type Production_Priority = tf.constructor(uptr, Production, "priority", Symbol, "def", tf.listType(Production), "choices");
	public static final Type Production_Associativity = tf.constructor(uptr, Production, "associativity", Symbol, "def", Associativity, "assoc", tf.setType(Production), "alternatives");
	

	public static final Type Attr_Assoc = tf.constructor(uptr, Attr, "assoc", Associativity, "assoc");
	public static final Type Attr_Tag = tf.constructor(uptr, Attr, "tag", tf.valueType(), "tag");
	public static final Type Attr_Bracket = tf.constructor(uptr, Attr, "bracket");
	
	public static final Type Associativity_Left = tf.constructor(uptr, Associativity, "left");
	public static final Type Associativity_Right = tf.constructor(uptr, Associativity, "right");
	public static final Type Associativity_Assoc = tf.constructor(uptr, Associativity, "assoc");
	public static final Type Associativity_NonAssoc = tf.constructor(uptr, Associativity, "non-assoc");
	
	public static final Type Condition = tf.abstractDataType(uptr, "Condition");
	public static final Type Condition_Follow = tf.constructor(uptr, Condition, "follow", Symbol, "symbol");
	public static final Type Condition_NotFollow = tf.constructor(uptr, Condition, "not-follow", Symbol, "symbol");
	public static final Type Condition_Precede = tf.constructor(uptr, Condition, "precede", Symbol, "symbol");
	public static final Type Condition_NotPrecede = tf.constructor(uptr, Condition, "not-precede", Symbol, "symbol");
	public static final Type Condition_Delete = tf.constructor(uptr, Condition, "delete", Symbol, "symbol");
	public static final Type Condition_EndOfLine = tf.constructor(uptr, Condition, "end-of-line");
	public static final Type Condition_StartOfLine = tf.constructor(uptr, Condition, "begin-of-line");
	public static final Type Condition_AtColumn = tf.constructor(uptr, Condition, "at-column", tf.integerType(), "column");
	public static final Type Condition_Except = tf.constructor(uptr, Condition, "except", str, "label");
	
	public static final Type Symbol_Label = tf.constructor(uptr, Symbol, "label", str, "name", Symbol, "symbol");
	public static final Type Symbol_Start_Sort = tf.constructor(uptr, Symbol, "start", Symbol, "symbol");
//	public static final Type Symbol_START = tf.constructor(uptr, Symbol, "START");
	public static final Type Symbol_Lit = tf.constructor(uptr, Symbol, "lit", str, "string");
	public static final Type Symbol_CiLit = tf.constructor(uptr, Symbol, "cilit", str, "string");
	public static final Type Symbol_Empty = tf.constructor(uptr, Symbol, "empty");
	public static final Type Symbol_Seq = tf.constructor(uptr, Symbol, "seq", tf.listType(Symbol), "symbols");
	public static final Type Symbol_Opt = tf.constructor(uptr, Symbol, "opt", Symbol, "symbol");
	public static final Type Symbol_Alt = tf.constructor(uptr, Symbol, "alt", tf.setType(Symbol), "alternatives");
	public static final Type Symbol_Sort = tf.constructor(uptr, Symbol, "sort", str, "name");
	public static final Type Symbol_Lex = tf.constructor(uptr, Symbol, "lex", str, "name");
	public static final Type Symbol_Keyword = tf.constructor(uptr, Symbol, "keywords", str, "name");
	public static final Type Symbol_Meta = tf.constructor(uptr, Symbol, "meta", Symbol, "symbol");
	public static final Type Symbol_Conditional = tf.constructor(uptr, Symbol, "conditional", Symbol, "symbol", tf.setType(Condition), "conditions");
	public static final Type Symbol_IterSepX = tf.constructor(uptr, Symbol, "iter-seps", Symbol, "symbol", tf.listType(Symbol), "separators");
	public static final Type Symbol_IterStarSepX = tf.constructor(uptr, Symbol, "iter-star-seps", Symbol, "symbol", tf.listType(Symbol), "separators");
	public static final Type Symbol_IterPlus = tf.constructor(uptr, Symbol, "iter", Symbol, "symbol");
	public static final Type Symbol_IterStar = tf.constructor(uptr, Symbol, "iter-star", Symbol, "symbol");
	public static final Type Symbol_ParameterizedSort = tf.constructor(uptr, Symbol, "parameterized-sort", str, "name", tf.listType(Symbol), "parameters");
	public static final Type Symbol_ParameterizedLex = tf.constructor(uptr, Symbol, "parameterized-lex", str, "name", tf.listType(Symbol), "parameters");
	public static final Type Symbol_Parameter = tf.constructor(uptr, Symbol, "parameter", str, "name", Symbol, "bound");
	
	public static final Type Symbol_LayoutX = tf.constructor(uptr, Symbol, "layouts", str, "name");
	
	public static final Type Symbol_CharClass = tf.constructor(uptr, Symbol, "char-class", tf.listType(CharRange), "ranges");
	
	public static final Type Symbol_Int = tf.constructor(uptr, Symbol, "int");
	public static final Type Symbol_Rat = tf.constructor(uptr, Symbol, "rat");
	public static final Type Symbol_Bool = tf.constructor(uptr, Symbol, "bool");
	public static final Type Symbol_Real = tf.constructor(uptr, Symbol, "real");
	public static final Type Symbol_Str = tf.constructor(uptr, Symbol,  "str");
	public static final Type Symbol_Node = tf.constructor(uptr, Symbol,  "node");
	public static final Type Symbol_Num = tf.constructor(uptr, Symbol,  "num");
	public static final Type Symbol_Void = tf.constructor(uptr, Symbol, "void");
	public static final Type Symbol_Value = tf.constructor(uptr, Symbol,  "value");
	public static final Type Symbol_Loc = tf.constructor(uptr, Symbol,  "loc");
	public static final Type Symbol_Datetime = tf.constructor(uptr, Symbol,  "datetime");
	public static final Type Symbol_Set = tf.constructor(uptr, Symbol, "set", Symbol, "symbol");
	public static final Type Symbol_Rel = tf.constructor(uptr, Symbol, "rel", tf.listType(Symbol), "symbols");
	public static final Type Symbol_ListRel = tf.constructor(uptr, Symbol, "lrel", tf.listType(Symbol), "symbols");
	public static final Type Symbol_Tuple = tf.constructor(uptr, Symbol, "tuple", tf.listType(Symbol), "symbols");
	public static final Type Symbol_List = tf.constructor(uptr, Symbol, "list", Symbol, "symbol");
	public static final Type Symbol_Map = tf.constructor(uptr, Symbol, "map", Symbol, "from", Symbol, "to");
	public static final Type Symbol_Bag = tf.constructor(uptr, Symbol, "bag", Symbol, "symbol");
	public static final Type Symbol_Adt = tf.constructor(uptr, Symbol, "adt", str, "name", tf.listType(Symbol), "parameters");
	public static final Type Symbol_ReifiedType = tf.constructor(uptr, Symbol, "reified", Symbol, "symbol");
	public static final Type Symbol_Func = tf.constructor(uptr, Symbol, "func", Symbol, "ret", tf.listType(Symbol), "parameters");
	public static final Type Symbol_Alias = tf.constructor(uptr, Symbol, "alias", str, "name", tf.listType(Symbol), "parameters", Symbol, "aliased");
	public static final Type Symbol_Cons = tf.constructor(uptr, Symbol, "cons", Symbol, "adt", str, "name", tf.listType(Symbol), "parameters");
	public static final Type Symbol_BoundParameter = tf.constructor(uptr, Symbol, "parameter", str , "name", Symbol, "bound");

	public static final Type CharRange_Single = tf.constructor(uptr, CharRange, "from", tf.integerType()); // TODO: can go when older parser is gone
	public static final Type CharRange_Range = tf.constructor(uptr, CharRange, "range", tf.integerType(), "begin", tf.integerType(), "end");
	
	public static final String Location = "loc";
	public static final String Length = "len";

	private static final IValueFactory bootFactory = ValueFactory.getInstance(); 
	public static final IValue Attribute_Assoc_Left = bootFactory.constructor(Attr_Assoc, bootFactory.constructor(Associativity_Left));
	public static final IValue Attribute_Assoc_Right = bootFactory.constructor(Attr_Assoc, bootFactory.constructor(Associativity_Right));
	public static final IValue Attribute_Assoc_Non_Assoc = bootFactory.constructor(Attr_Assoc, bootFactory.constructor(Associativity_NonAssoc));
	public static final IValue Attribute_Assoc_Assoc = bootFactory.constructor(Attr_Assoc, bootFactory.constructor(Associativity_Assoc));
	public static final IValue Attribute_Bracket = bootFactory.constructor(Attr_Bracket);
	
	public static TypeStore getStore() {
		return uptr;
	}
	
	public Factory() {
		super(bootFactory);
		uptr.declareAnnotation(Tree, Location, tf.sourceLocationType());
		uptr.declareAnnotation(Tree, Length, tf.integerType());
	}

	@Override
	public IConstructor constructor(org.eclipse.imp.pdb.facts.type.Type constructor, IValue... children)
			throws FactTypeUseException {
		assert constructor != null;
		
		if (constructor.getAbstractDataType() == Tree) {
			if (constructor == Tree_Appl) {
				IConstructor prod = (IConstructor) children[0];
				IList args = (IList) children[1];
				return appl(prod, args);
			}
			else if (constructor == Tree_Char) {
				return character(((IInteger) children[0]).intValue());
			}
		}
		
		return super.constructor(constructor, children);
	}
	
	@Override
	public IConstructor constructor(Type constructor, IValue[] children, Map<String, IValue> kwParams)
			throws FactTypeUseException {
		assert constructor != null;
		
		if (constructor.getAbstractDataType() == Tree) {
			if (constructor == Tree_Appl) {
				IConstructor prod = (IConstructor) children[0];
				IList args = (IList) children[1];
				return appl(prod, args);
			}
			else if (constructor == Tree_Char) {
				return character(((IInteger) children[0]).intValue());
			}
		}
		
		return super.constructor(constructor, children, kwParams);
	}
	

	public IConstructor character(int ch) {
		if (ch >= 0 && ch <= Byte.MAX_VALUE) {
			return new TreeByte((byte) ch);
		}
		
		return new TreeInt(ch);
	}

	public IConstructor appl(IConstructor prod, IList args) {
		switch (args.length()) {
		case 0: return new Appl0(prod);
		case 1: return new Appl1(prod, args.get(0));
		case 2: return new Appl2(prod, args.get(0), args.get(1));
		case 3: return new Appl3(prod, args.get(0), args.get(1), args.get(2));
		case 4: return new Appl4(prod, args.get(0), args.get(1), args.get(2), args.get(3));
		case 5: return new Appl5(prod, args.get(0), args.get(1), args.get(2), args.get(3), args.get(4));
		case 6: return new Appl6(prod, args.get(0), args.get(1), args.get(2), args.get(3), args.get(4), args.get(5));
		case 7: return new Appl7(prod, args.get(0), args.get(1), args.get(2), args.get(3), args.get(4), args.get(5), args.get(6));
		default: return new ApplN(prod, args);
		}
	}
	
	private class TreeInt implements IConstructor {
		final int ch;
		
		public TreeInt(int ch) {
			this.ch = ch;
		}

		@Override
		public IValue get(int i) throws IndexOutOfBoundsException {
			switch (i) {
			case 0: return integer(ch);
			default: throw new IndexOutOfBoundsException();
			}
		}

		@Override
		public int arity() {
			return 1;
		}

		@Override
		public String getName() {
			return Tree_Char.getName();
		}

		@Override
		public Iterable<IValue> getChildren() {
			return this;
		}
		
		@Override
		public int hashCode() {
			return ch;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof IValue)) {
				return false;
			}
			return isEqual((IValue) obj);
		}

		@Override
		public Iterator<IValue> iterator() {
			return new Iterator<IValue>() {
				boolean done = false;
				
				@Override
				public boolean hasNext() {
					return !done;
				}

				@Override
				public IValue next() {
					done = true;
					return integer(ch); 
				}

				@Override
				public void remove() {
					throw new UnsupportedOperationException();
				}
			};
		}

		@Override
		public INode replace(int first, int second, int end, IList repl)
				throws FactTypeUseException, IndexOutOfBoundsException {
			throw new UnsupportedOperationException();
		}

		@Override
		public <T, E extends Throwable> T accept(IValueVisitor<T, E> v)
				throws E {
			return v.visitConstructor(this);
		}

		@Override
		public boolean isEqual(IValue other) {
			if (other instanceof TreeInt) {
				TreeInt o = (TreeInt) other;
				return o.ch == ch;
			}
			
			return false;
		}

		@Override
		public boolean isAnnotatable() {
			return false;
		}

		@Override
		public boolean mayHaveKeywordParameters() {
			return false;
		}

		@Override
		public org.eclipse.imp.pdb.facts.type.Type getType() {
			return Tree;
		}

		@Override
		public org.eclipse.imp.pdb.facts.type.Type getConstructorType() {
			return Tree_Char;
		}

		@Override
		public org.eclipse.imp.pdb.facts.type.Type getUninstantiatedConstructorType() {
			return Tree_Char;
		}

		@Override
		public IValue get(String label) {
			return get(Tree_Char.getFieldIndex(label));
		}

		@Override
		public IConstructor set(String label, IValue newChild)
				throws FactTypeUseException {
			return set(Tree_Char.getFieldIndex(label), newChild);
		}

		@Override
		public boolean has(String label) {
			return Tree_Char.hasField(label);
		}

		@Override
		public IConstructor set(int index, IValue newChild)
				throws FactTypeUseException {
			switch (index) {
			case 0: return character(((IInteger) newChild).intValue());
			default: throw new IndexOutOfBoundsException();
			}
		}

		@Override
		public org.eclipse.imp.pdb.facts.type.Type getChildrenTypes() {
			return tf.tupleType(tf.integerType());
		}

		@Override
		public boolean declaresAnnotation(TypeStore store, String label) {
			return false;
		}

		@Override
		public IAnnotatable<? extends IConstructor> asAnnotatable() {
			throw new UnsupportedOperationException();
		}

		@Override
		public IWithKeywordParameters<IConstructor> asWithKeywordParameters() {
			throw new UnsupportedOperationException();
		}
	}
	
	private class TreeByte implements IConstructor {
		final byte ch;
		
		public TreeByte(byte ch) {
			this.ch = ch;
		}

		@Override
		public IValue get(int i) throws IndexOutOfBoundsException {
			switch (i) {
			case 0: return integer(ch);
			default: throw new IndexOutOfBoundsException();
			}
		}

		@Override
		public int arity() {
			return 1;
		}

		@Override
		public String getName() {
			return Tree_Char.getName();
		}

		@Override
		public Iterable<IValue> getChildren() {
			return this;
		}
		
		@Override
		public int hashCode() {
			return ch;
		}

		@Override
		public Iterator<IValue> iterator() {
			return new Iterator<IValue>() {
				boolean done = false;
				
				@Override
				public boolean hasNext() {
					return !done;
				}

				@Override
				public IValue next() {
					done = true;
					return integer(ch); 
				}

				@Override
				public void remove() {
					throw new UnsupportedOperationException();
				}
			};
		}

		@Override
		public INode replace(int first, int second, int end, IList repl)
				throws FactTypeUseException, IndexOutOfBoundsException {
			throw new UnsupportedOperationException();
		}

		@Override
		public <T, E extends Throwable> T accept(IValueVisitor<T, E> v)
				throws E {
			return v.visitConstructor(this);
		}

		@Override
		public boolean isEqual(IValue other) {
			if (other instanceof TreeByte) {
				TreeByte o = (TreeByte) other;
				return o.ch == ch;
			}
			
			return false;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof IValue)) {
				return false;
			}
			return isEqual((IValue) obj);
		}

		@Override
		public boolean isAnnotatable() {
			return false;
		}

		@Override
		public boolean mayHaveKeywordParameters() {
			return false;
		}

		@Override
		public org.eclipse.imp.pdb.facts.type.Type getType() {
			return Tree;
		}

		@Override
		public org.eclipse.imp.pdb.facts.type.Type getConstructorType() {
			return Tree_Char;
		}

		@Override
		public org.eclipse.imp.pdb.facts.type.Type getUninstantiatedConstructorType() {
			return Tree_Char;
		}

		@Override
		public IValue get(String label) {
			return get(Tree_Char.getFieldIndex(label));
		}

		@Override
		public IConstructor set(String label, IValue newChild)
				throws FactTypeUseException {
			return set(Tree_Char.getFieldIndex(label), newChild);
		}

		@Override
		public boolean has(String label) {
			return Tree_Char.hasField(label);
		}

		@Override
		public IConstructor set(int index, IValue newChild)
				throws FactTypeUseException {
			switch (index) {
			case 0: return character(((IInteger) newChild).intValue());
			default: throw new IndexOutOfBoundsException();
			}
		}

		@Override
		public org.eclipse.imp.pdb.facts.type.Type getChildrenTypes() {
			return tf.tupleType(tf.integerType());
		}

		@Override
		public boolean declaresAnnotation(TypeStore store, String label) {
			return false;
		}

		@Override
		public IAnnotatable<? extends IConstructor> asAnnotatable() {
			throw new UnsupportedOperationException();
		}

		@Override
		public IWithKeywordParameters<IConstructor> asWithKeywordParameters() {
			throw new UnsupportedOperationException();
		}
	}
	
	private abstract class AbstractAppl implements IConstructor {
		protected final IConstructor production;

		protected AbstractAppl(IConstructor production) {
			this.production = production;
		}

		@Override
		public String getName() {
			return Tree_Appl.getName();
		}
		
		@Override
		public Iterable<IValue> getChildren() {
			return this;
		}
		
		@Override
		public int hashCode() {
			return 41 + 1331 * production.hashCode() + 13331 * getArguments().hashCode(); 
		}
		
		@Override
		public boolean isEqual(IValue other) {
			if (other instanceof IConstructor) {
				IConstructor cons = (IConstructor) other;
				
				return cons.getConstructorType() == getConstructorType()
						&& cons.get(0).isEqual(get(0))
						&& cons.get(1).isEqual(get(1));
			}
			
			return false;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof IValue)) {
				return false;
			}
			return isEqual((IValue) obj);
		}
		
		
		abstract public IList getArguments();

		@Override
		public Iterator<IValue> iterator() {
			return new Iterator<IValue>() {
				private int count = 0;
				
				@Override
				public boolean hasNext() {
					return count < 2;
				}

				@Override
				public IValue next() {
					count++;
					switch(count) {
					case 1: return production;
					case 2: return getArguments();
					default: return null;
					}
				}
			};
		}

		@Override
		public int arity() {
			return 2;
		}
		
		@Override
		public String toString() {
			return StandardTextWriter.valueToString(this);
		}
		
		@Override
		public INode replace(int first, int second, int end, IList repl)
				throws FactTypeUseException, IndexOutOfBoundsException {
			throw new UnsupportedOperationException("Replace not supported on constructor.");
		}

		@Override
		public <T, E extends Throwable> T accept(IValueVisitor<T, E> v)
				throws E {
			return v.visitConstructor(this);
		}
		
		@Override
		public boolean isAnnotatable() {
			return true;
		}

		@Override
		public boolean mayHaveKeywordParameters() {
			return false;
		}

		@Override
		public org.eclipse.imp.pdb.facts.type.Type getType() {
			return Tree;
		}

		@Override
		public org.eclipse.imp.pdb.facts.type.Type getConstructorType() {
			return Tree_Appl;
		}

		@Override
		public org.eclipse.imp.pdb.facts.type.Type getUninstantiatedConstructorType() {
			return Tree_Appl;
		}

		@Override
		public IValue get(String label) {
			switch (label) {
			case "prod": return production;
			case "args": return getArguments();
			default: throw new UndeclaredFieldException(Tree_Appl, label);
			}
		}

		@Override
		public IConstructor set(String label, IValue newChild)
				throws FactTypeUseException {
			switch (label) {
			case "prod": return appl((IConstructor) newChild, getArguments());
			case "args": return appl(production, (IList) newChild);
			default: throw new UndeclaredFieldException(Tree_Appl, label);
			}
		}

		@Override
		public boolean has(String label) {
			return Tree_Appl.hasField(label);
		}

		@Override
		public IConstructor set(int index, IValue newChild)
				throws FactTypeUseException {
			switch (index) {
			case 0: return constructor(Tree_Appl, newChild, getArguments());
			case 1: return constructor(Tree_Appl, production, newChild);
			default: throw new IndexOutOfBoundsException();
			}
		}

		@Override
		public org.eclipse.imp.pdb.facts.type.Type getChildrenTypes() {
			return tf.tupleType(production.getType(), Args);
		}

		@Override
		public boolean declaresAnnotation(TypeStore store, String label) {
			return store.getAnnotations(Tree).containsKey(label);
		}

		@Override
		public IAnnotatable<? extends IConstructor> asAnnotatable() {
			return new AbstractDefaultAnnotatable<IConstructor>(this) {
				@Override
				protected IConstructor wrap(IConstructor content,
						ImmutableMap<String, IValue> annotations) {
					return new AnnotatedConstructorFacade(content, annotations);
				}
			};
		}

		@Override
		public IWithKeywordParameters<IConstructor> asWithKeywordParameters() {
			 return new AbstractDefaultWithKeywordParameters<IConstructor>(this, AbstractSpecialisedImmutableMap.<String,IValue>mapOf()) {
				    @Override
				    protected IConstructor wrap(IConstructor content, ImmutableMap<String, IValue> parameters) {
				      return new ConstructorWithKeywordParametersFacade(content, parameters);
				    }
			 }; 
		}
		
		@Override
		public IValue get(int i) throws IndexOutOfBoundsException {
			switch (i) {
			case 0: return production;
			case 1: return getArguments();
			default: throw new IndexOutOfBoundsException();
			}
		}
	}
	

	
	private abstract class AbstractArgumentList implements IList {
		protected abstract IList asNormal();
		
		@Override
		public org.eclipse.imp.pdb.facts.type.Type getType() {
			return tf.listType(Tree);
		}

		@Override
		public boolean contains(IValue e) {
			for (int i = 0; i < length(); i++) {
				if (get(0).isEqual(e)) {
					return true;
				}
			}
			
			return false;
		}
		
		@Override
		public String toString() {
			return StandardTextWriter.valueToString(this);
		}
		
		@Override
		public <T, E extends Throwable> T accept(IValueVisitor<T, E> v) throws E {
			return v.visitList(this);
		}
		
		@Override
		public boolean isEqual(IValue other) {
			if (other instanceof IList) {
				IList o = (IList) other;
				if (o.length() == length()) {
					for (int i = 0; i < length(); i++) {
						if (!o.get(i).isEqual(get(i))) {
							return false;
						}
					}
					
					return true;
				}
			}
			
			return false;
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof IValue)) {
				return false;
			}
			return isEqual((IValue) obj);
		}
		
		@Override
		public int hashCode(){
			int hash = 0;

			Iterator<IValue> iterator = iterator();
			while(iterator.hasNext()){
				IValue element = iterator.next();
				hash = (hash << 1) ^ element.hashCode();
			}

			return hash;
		}
		
		@Override
		public Iterator<IValue> iterator() {
			return new Iterator<IValue>() {
				private int count = 0;
				@Override
				public boolean hasNext() {
					return count < length();
				}

				@Override
				public IValue next() {
					count++;
					return get(count - 1);
				}
			};
		}
		
		@Override
		public boolean isAnnotatable() {
			return false;
		}

		@Override
		public IAnnotatable<? extends IValue> asAnnotatable() {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean mayHaveKeywordParameters() {
			throw new UnsupportedOperationException();
		}

		@Override
		public IWithKeywordParameters<? extends IValue> asWithKeywordParameters() {
			throw new UnsupportedOperationException();
		}

		@Override
		public org.eclipse.imp.pdb.facts.type.Type getElementType() {
			return Tree;
		}

		@Override
		public IList reverse() {
			return asNormal().reverse();
		}

		@Override
		public IList append(IValue e) {
			return asNormal().append(e);
		}

		@Override
		public IList insert(IValue e) {
			return asNormal().insert(e);
		}

		@Override
		public IList concat(IList o) {
			return asNormal().concat(o);
		}

		@Override
		public IList put(int i, IValue e) throws FactTypeUseException,
				IndexOutOfBoundsException {
			return asNormal().put(i,e);
		}

		@Override
		public IList replace(int first, int second, int end, IList repl)
				throws FactTypeUseException, IndexOutOfBoundsException {
			return asNormal().replace(first, second, end, repl);
		}


		@Override
		public IList sublist(int offset, int length) {
			return asNormal().sublist(offset,length);
		}

		public boolean isEmpty() {
			return false;
		}

		@Override
		public IList delete(IValue e) {
			return asNormal().delete(e);
		}

		@Override
		public IList delete(int i) {
			return asNormal().delete(i);
		}

		@Override
		public IList product(IList l) {
			return asNormal().product(l);
		}

		@Override
		public IList intersect(IList l) {
			return asNormal().intersect(l);
		}

		@Override
		public IList subtract(IList l) {
			return asNormal().subtract(l);
		}

		@Override
		public boolean isSubListOf(IList l) {
			return asNormal().isSubListOf(l);
		}

		@Override
		public boolean isRelation() {
			return false;
		}

		@Override
		public IListRelation<IList> asRelation() {
			throw new UnsupportedOperationException();
		}
	}
	
	private class Appl0 extends AbstractAppl {
		public Appl0(IConstructor production) {
			super(production);
		}

		@Override
		public IList getArguments() {
			return EMPTY_LIST;
		}
	}
	
	private class ApplN extends AbstractAppl {
		private final IList args;

		public ApplN(IConstructor production, IList args) {
			super(production);
			this.args = args;
		}

		@Override
		public IList getArguments() {
			return args;
		}
	}
	
	private class Appl1 extends AbstractAppl {
		private final IValue arg0;

		public Appl1(IConstructor production, IValue arg) {
			super(production);
			this.arg0 = arg;
		}

		@Override
		public IList getArguments() {
			return new AbstractArgumentList() {
				@Override
				public int length() {
					return 1;
				}
				
				@Override
				public IValue get(int i) throws IndexOutOfBoundsException {
					switch(i) {
					case 0: return arg0;
					default: throw new IndexOutOfBoundsException();
					}
				}
				
				@Override
				protected IList asNormal() {
					return list(arg0);
				}
			};
		}
	}
	
	private class Appl2 extends AbstractAppl {
		private final IValue arg0;
		private final IValue arg1;

		public Appl2(IConstructor production, IValue arg0, IValue arg1) {
			super(production);
			this.arg0 = arg0;
			this.arg1 = arg1;
		}

		@Override
		public IList getArguments() {
			return new AbstractArgumentList() {
				@Override
				public int length() {
					return 2;
				}
				
				@Override
				public IValue get(int i) throws IndexOutOfBoundsException {
					switch(i) {
					case 0: return arg0;
					case 1: return arg1;
					default: throw new IndexOutOfBoundsException();
					}
				}
				
				@Override
				protected IList asNormal() {
					return list(arg0, arg1);
				}
			};
		}
	}
	
	private class Appl3 extends AbstractAppl {
		private final IValue arg0;
		private final IValue arg1;
		private final IValue arg2;

		public Appl3(IConstructor production, IValue arg0, IValue arg1, IValue arg2) {
			super(production);
			this.arg0 = arg0;
			this.arg1 = arg1;
			this.arg2 = arg2;
		}

		@Override
		public IList getArguments() {
			return new AbstractArgumentList() {
				@Override
				public int length() {
					return 3;
				}
				
				@Override
				public IValue get(int i) throws IndexOutOfBoundsException {
					switch(i) {
					case 0: return arg0;
					case 1: return arg1;
					case 2: return arg2;
					default: throw new IndexOutOfBoundsException();
					}
				}
				
				@Override
				protected IList asNormal() {
					return list(arg0, arg1, arg2);
				}
			};
		}
	}
	
	private class Appl4 extends AbstractAppl {
		private final IValue arg0;
		private final IValue arg1;
		private final IValue arg2;
		private final IValue arg3;

		public Appl4(IConstructor production, IValue arg0, IValue arg1, IValue arg2, IValue arg3) {
			super(production);
			this.arg0 = arg0;
			this.arg1 = arg1;
			this.arg2 = arg2;
			this.arg3 = arg3;
		}

		@Override
		public IList getArguments() {
			return new AbstractArgumentList() {
				@Override
				public int length() {
					return 4;
				}
				
				@Override
				public IValue get(int i) throws IndexOutOfBoundsException {
					switch(i) {
					case 0: return arg0;
					case 1: return arg1;
					case 2: return arg2;
					case 3: return arg3;
					default: throw new IndexOutOfBoundsException();
					}
				}
				
				@Override
				protected IList asNormal() {
					return list(arg0, arg1, arg2, arg3);
				}
			};
		}
	}
	
	private class Appl5 extends AbstractAppl {
		private final IValue arg0;
		private final IValue arg1;
		private final IValue arg2;
		private final IValue arg3;
		private final IValue arg4;

		public Appl5(IConstructor production, IValue arg0, IValue arg1, IValue arg2, IValue arg3, IValue arg4) {
			super(production);
			this.arg0 = arg0;
			this.arg1 = arg1;
			this.arg2 = arg2;
			this.arg3 = arg3;
			this.arg4 = arg4;
		}

		@Override
		public IList getArguments() {
			return new AbstractArgumentList() {
				@Override
				public int length() {
					return 5;
				}
				
				@Override
				public IValue get(int i) throws IndexOutOfBoundsException {
					switch(i) {
					case 0: return arg0;
					case 1: return arg1;
					case 2: return arg2;
					case 3: return arg3;
					case 4: return arg4;
					default: throw new IndexOutOfBoundsException();
					}
				}
				
				@Override
				protected IList asNormal() {
					return list(arg0, arg1, arg2, arg3, arg4);
				}
			};
		}
	}
	
	private class Appl6 extends AbstractAppl {
		private final IValue arg0;
		private final IValue arg1;
		private final IValue arg2;
		private final IValue arg3;
		private final IValue arg4;
		private final IValue arg5;

		public Appl6(IConstructor production, IValue arg0, IValue arg1, IValue arg2, IValue arg3, IValue arg4, IValue arg5) {
			super(production);
			this.arg0 = arg0;
			this.arg1 = arg1;
			this.arg2 = arg2;
			this.arg3 = arg3;
			this.arg4 = arg4;
			this.arg5 = arg5;
		}

		@Override
		public IList getArguments() {
			return new AbstractArgumentList() {
				@Override
				public int length() {
					return 6;
				}
				
				@Override
				public IValue get(int i) throws IndexOutOfBoundsException {
					switch(i) {
					case 0: return arg0;
					case 1: return arg1;
					case 2: return arg2;
					case 3: return arg3;
					case 4: return arg4;
					case 5: return arg5;
					default: throw new IndexOutOfBoundsException();
					}
				}
				
				@Override
				protected IList asNormal() {
					return list(arg0, arg1, arg2, arg3, arg4, arg5);
				}
			};
		}
	}

	private class Appl7 extends AbstractAppl {
		private final IValue arg0;
		private final IValue arg1;
		private final IValue arg2;
		private final IValue arg3;
		private final IValue arg4;
		private final IValue arg5;
		private final IValue arg6;

		public Appl7(IConstructor production, IValue arg0, IValue arg1, IValue arg2, IValue arg3, IValue arg4, IValue arg5, IValue arg6) {
			super(production);
			this.arg0 = arg0;
			this.arg1 = arg1;
			this.arg2 = arg2;
			this.arg3 = arg3;
			this.arg4 = arg4;
			this.arg5 = arg5;
			this.arg6 = arg6;
		}

		@Override
		public IList getArguments() {
			return new AbstractArgumentList() {
				@Override
				public int length() {
					return 7;
				}
				
				@Override
				public IValue get(int i) throws IndexOutOfBoundsException {
					switch(i) {
					case 0: return arg0;
					case 1: return arg1;
					case 2: return arg2;
					case 3: return arg3;
					case 4: return arg4;
					case 5: return arg5;
					case 6: return arg6;
					default: throw new IndexOutOfBoundsException();
					}
				}
				
				@Override
				protected IList asNormal() {
					return list(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
				}
			};
		}
	}

}
