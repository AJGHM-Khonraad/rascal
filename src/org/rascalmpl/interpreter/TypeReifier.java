/*******************************************************************************
 * Copyright (c) 2009-2013 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:

 *   * Mark Hills - Mark.Hills@cwi.nl (CWI)
 *   * Arnold Lankamp - Arnold.Lankamp@cwi.nl
*******************************************************************************/
package org.rascalmpl.interpreter;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IListWriter;
import org.eclipse.imp.pdb.facts.IMap;
import org.eclipse.imp.pdb.facts.IMapWriter;
import org.eclipse.imp.pdb.facts.ISet;
import org.eclipse.imp.pdb.facts.ISetWriter;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.eclipse.imp.pdb.facts.type.ITypeVisitor;
import org.eclipse.imp.pdb.facts.type.Type;
import org.eclipse.imp.pdb.facts.type.TypeFactory;
import org.eclipse.imp.pdb.facts.type.TypeStore;
import org.rascalmpl.interpreter.asserts.ImplementationError;
import org.rascalmpl.interpreter.asserts.NotYetImplemented;
import org.rascalmpl.interpreter.env.Environment;
import org.rascalmpl.interpreter.env.ModuleEnvironment;
import org.rascalmpl.interpreter.result.Result;
import org.rascalmpl.interpreter.result.ResultFactory;
import org.rascalmpl.interpreter.types.FunctionType;
import org.rascalmpl.interpreter.types.NonTerminalType;
import org.rascalmpl.interpreter.types.RascalTypeFactory;
import org.rascalmpl.interpreter.types.ReifiedType;
import org.rascalmpl.values.uptr.Factory;
import org.rascalmpl.values.uptr.SymbolAdapter;

/**
 * TypeReifier maps types to values that represent these types and their definitions. These values have
 * very specific types, namely 'type[&T]' where &T is bound to the type the value represents. 
<pre>
data type[&T] = type(Symbol symbol, map[Symbol, Production] definitions);
</pre>
 */
public class TypeReifier {
	private final IValueFactory vf;
	private final TypeFactory tf;
	
	public TypeReifier(IValueFactory valueFactory) {
		this.vf = valueFactory;
		this.tf = TypeFactory.getInstance();
	}
	
	public Result<IValue> typeToValue(Type t, IEvaluatorContext ctx) {
		Environment env = ctx.getCurrentEnvt();
		env.getStore().declareAbstractDataType(Factory.Type);
		env.getStore().declareConstructor(Factory.Type_Reified);
		TypeStore store = constructCompleteTypeStore(env);
		
		Map<IConstructor, IConstructor> definitions = new HashMap<IConstructor, IConstructor>();
		IConstructor symbol = reify(t, definitions, ctx, store);
		
		Map<Type,Type> bindings = new HashMap<Type,Type>();
		bindings.put(Factory.TypeParam, t);
		Type typeType = Factory.Type.instantiate(bindings);
		
		IMapWriter defs = vf.mapWriter(Factory.Symbol, Factory.Production);
		for (Map.Entry<IConstructor, IConstructor> entry : definitions.entrySet()) {
			defs.put(entry.getKey(), entry.getValue());
		}
		IValue result = vf.constructor(Factory.Type_Reified.instantiate(bindings), symbol, defs.done());
		
		return ResultFactory.makeResult(typeType, result, ctx);
	}
	
	public Type valueToType(IConstructor typeValue) {
		return valueToType(typeValue, new TypeStore());
	}
	
	/**
	 * Reconstruct a type from a reified type value and declare all types used.
	 * @param typeValue the type value to restore
	 * @param store     a possibly empty store that will be filled with the relevant type declarations.
	 * @return the type corresponding to the reified type value 
	 */
	public Type valueToType(IConstructor typeValue, TypeStore store) {
		if (typeValue.getType() instanceof ReifiedType) {
			/* Although the type of this value contains already a type, it may be more general
			 * then the type represented by the symbol. So, we do have to map the symbol to
			 * a type recursively.
			 * 
			 * We also need to construct a TypeStore from the declarations, such that the 
			 * appropriate definitions for ADT's and aliases can be found.
			 */
			IMap definitions = (IMap) typeValue.get("definitions");
			declareAbstractDataTypes(definitions, store);
			return symbolToType((IConstructor) typeValue.get("symbol"), store);
		}

		throw new IllegalArgumentException(typeValue + " is not a reified type");
	}
	
	/**
	 * Reconstruct a type from a reified type value, but declare all types used first using the given definitions 
	 * @param typeValue the type value to restore
	 * @param definitions the definition
	 */
	public Type symbolToType(IConstructor symbol, IMap definitions) {
		TypeStore store = new TypeStore();
		declareAbstractDataTypes(definitions, store);
		return symbolToType(symbol, store);
	}
	
	/**
	 * This method assumes that all types that are used have been defined.
	 */
	public void declareAbstractDataTypes(IMap definitions, TypeStore store) {
		for (IValue key : definitions) {
			IConstructor def = (IConstructor) definitions.get(key);
			
			
			
			if (def.getConstructorType() == Factory.Production_Choice) {
				IConstructor defined = (IConstructor) def.get("def");
				
				if (defined.getConstructorType() == Factory.Symbol_Adt) {
					Type adt = adtToType(defined, store);
				
					for (IValue alt : (ISet) def.get("alternatives")) {
						declareConstructor(adt, (IConstructor) alt, store);
					}
				}
			}
		}
	}

	private Type declareConstructor(Type adt, IConstructor alt, TypeStore store) {
		IConstructor defined = (IConstructor) alt.get("def");
		String name = ((IString) defined.get("name")).getValue();
		return tf.constructorFromTuple(store, adt, name, symbolsToTupleType((IList) alt.get("symbols"), store));
	}

	private Type symbolToType(IConstructor symbol, TypeStore store) {
		Type cons = symbol.getConstructorType();
		
		if (cons == Factory.Symbol_Int) {
			return tf.integerType();
		}
		else if (cons == Factory.Symbol_Real) {
			return tf.realType();
		}
		else if (cons == Factory.Symbol_Rat) {
			return tf.rationalType();
		}
		else if (cons == Factory.Symbol_Bool) {
			return tf.boolType();
		}
		else if (cons == Factory.Symbol_Datetime) {
			return tf.dateTimeType();
		}
		else if (cons == Factory.Symbol_Num) {
			return tf.numberType();
		}
		else if (cons == Factory.Symbol_Loc) {
			return tf.sourceLocationType();
		}
		else if (cons == Factory.Symbol_Adt) {
			return adtToType(symbol, store);
		}
		else if (cons == Factory.Symbol_Alias){
			return aliasToType(symbol, store);
		}
		else if (cons == Factory.Symbol_Bag) {
			throw new NotYetImplemented("bags are not implemented yet");
		}
		else if (cons == Factory.Symbol_Cons) {
			return consToType(symbol, store);
		}
		else if (cons == Factory.Symbol_Func) {
			return funcToType(symbol, store);
		}
		else if (cons == Factory.Symbol_Label) {
			return symbolToType((IConstructor) symbol.get("symbol"), store);
		}
		else if (cons == Factory.Symbol_Map) {
			return mapToType(symbol, store);
		}
		else if (cons == Factory.Symbol_Node) {
			return tf.nodeType();
		}
		else if (cons == Factory.Symbol_Parameter) {
			return tf.parameterType(((IString) symbol.get("name")).getValue());
		}
		else if (cons == Factory.Symbol_BoundParameter) {
			return tf.parameterType(((IString) symbol.get("name")).getValue(), symbolToType((IConstructor) symbol.get("bound"), store));
		}
		else if (cons == Factory.Symbol_ReifiedType) {
			return RascalTypeFactory.getInstance().reifiedType(symbolToType((IConstructor) symbol.get("symbol"), store));
		}
		else if (cons == Factory.Symbol_Rel) {
			return tf.relTypeFromTuple(symbolsToTupleType((IList) symbol.get("symbols"), store));
		}
		else if (cons == Factory.Symbol_ListRel) {
			return tf.lrelTypeFromTuple(symbolsToTupleType((IList) symbol.get("symbols"), store));
		}
		else if (cons == Factory.Symbol_Set) {
			return tf.setType(symbolToType((IConstructor) symbol.get("symbol"), store));
		}
		else if (cons == Factory.Symbol_List) {
			return tf.listType(symbolToType((IConstructor) symbol.get("symbol"), store));
		}
		else if (cons == Factory.Symbol_Str) {
			return tf.stringType();
		}
		else if (cons == Factory.Symbol_Tuple) {
			return tupleToType(symbol, store);
		}
		else if (cons == Factory.Symbol_Void) {
			return tf.voidType();
		}
		else if (cons == Factory.Symbol_Value) {
			return tf.valueType();
		}
		else {
			// We assume the other types are one of the non-terminal symbols
			return RascalTypeFactory.getInstance().nonTerminalType(symbol);
		}
	}

	private Type tupleToType(IConstructor symbol, TypeStore store) {
		return symbolsToTupleType((IList) symbol.get("symbols"), store);
	}

	private Type symbolsToTupleType(IList symbols, TypeStore store) {
		boolean allLabels = true;
		Type[] types = new Type[symbols.length()];
		String[] labels = new String[symbols.length()];
		
		for (int i = 0; i < symbols.length(); i++) {
			IConstructor elem = (IConstructor) symbols.get(i);
			if (elem.getConstructorType() == Factory.Symbol_Label) {
				labels[i] = ((IString) elem.get("name")).getValue();
				elem = (IConstructor) elem.get("symbol");
			}
			else {
				allLabels = false;
			}
			
			types[i] = symbolToType(elem, store);
		}
		
		if (allLabels) {
			return tf.tupleType(types, labels);
		}
		else {
			return tf.tupleType(types);
		}
	}
	
	private Type mapToType(IConstructor symbol, TypeStore store) {
		IConstructor from = (IConstructor) symbol.get("from");
		IConstructor to = (IConstructor) symbol.get("to");
		String fromLabel = null;
		String toLabel = null;
		
		if (SymbolAdapter.isLabel(from)) {
			fromLabel = SymbolAdapter.getLabel(from);
			from = (IConstructor) from.get("symbol");
		}
		if (SymbolAdapter.isLabel(to)) {
			toLabel = SymbolAdapter.getLabel(to);
			to = (IConstructor) to.get("symbol");
		}
		if (fromLabel != null && toLabel != null) {
			return tf.mapType(symbolToType(from, store), fromLabel, symbolToType(to, store), toLabel);
		}
		else {
			return tf.mapType(symbolToType(from, store), symbolToType(to, store));
		}
	}

	private Type funcToType(IConstructor symbol, TypeStore store) {
		Type returnType = symbolToType((IConstructor) symbol.get("ret"), store);
		Type parameters = symbolsToTupleType((IList) symbol.get("parameters"), store);
		// TODO: add support for reifying keyword parameters!
		return RascalTypeFactory.getInstance().functionType(returnType, parameters, Collections.<String,Type>emptyMap(), Collections.<String,IValue>emptyMap());
	}

	private Type consToType(IConstructor symbol, TypeStore store) {
		Type adt = symbolToType((IConstructor) symbol.get("adt"), store);
		IList parameters = (IList) symbol.get("parameters");
		String name = ((IString) symbol.get("name")).getValue();
		System.err.println("Cons in: " + symbol);
		// here we assume the store has the declaration already
		Type t = store.lookupConstructor(adt, name, symbolsToTupleType(parameters, store));
		System.err.println("Cons out: " + t);
		return t;
		
	}

	private Type aliasToType(IConstructor symbol, TypeStore store) {
		String name = ((IString) symbol.get("name")).getValue();
		Type aliased = symbolToType((IConstructor) symbol.get("aliased"), store);
		IList parameters = (IList) symbol.get("parameters");
		
		if (parameters.isEmpty()) {
			return tf.aliasType(store, name, aliased);
		}
		else {
			return tf.aliasTypeFromTuple(store, name, aliased,  symbolsToTupleType(parameters, store));
		}
	}

	private Type adtToType(IConstructor symbol, TypeStore store) {
		String name = ((IString) symbol.get("name")).getValue();
		Type adt = store.lookupAbstractDataType(name);
		
		if (adt == null) {
			Type params = symbolsToTupleType((IList) symbol.get("parameters"), store);
			if (params.isBottom() || params.getArity() == 0) {
				adt = tf.abstractDataType(store, name);
			}
			else {
				adt = tf.abstractDataTypeFromTuple(store, name, params);
			}
		}
		
		return adt;
	}

	private IConstructor reify(Type t, final Map<IConstructor, IConstructor> definitions, final IEvaluatorContext ctx, final TypeStore store) {
		return (IConstructor) t.accept(new ITypeVisitor<IValue, RuntimeException>() {
			private Map<Type,IValue> cache = new HashMap<Type, IValue>();
			
			@Override
			public IValue visitReal(Type type) {
				return vf.constructor(Factory.Symbol_Real);
			}

			@Override
			public IValue visitInteger(Type type) {
				return vf.constructor(Factory.Symbol_Int);
			}

			@Override
			public IValue visitRational(Type type) {
				return vf.constructor(Factory.Symbol_Rat);
			}

			@Override
			public IValue visitList(Type type) {
				if(type.isListRelation()) {
					IListWriter w = vf.listWriter();

					if (type.hasFieldNames()) {
						for (int i = 0; i < type.getArity(); i++) {
							w.append(vf.constructor(Factory.Symbol_Label, vf.string(type.getFieldName(i)), type.getFieldType(i).accept(this)));
						}
					}
					else {
						if (type.getFieldTypes().isBottom()) {
							return vf.constructor(Factory.Symbol_List, vf.constructor(Factory.Symbol_Void));
						}
				  
						for (Type f : type.getFieldTypes()) {
							w.append(f.accept(this));
						}
					}
				
					return vf.constructor(Factory.Symbol_ListRel, w.done());
				}
				return vf.constructor(Factory.Symbol_List, type.getElementType().accept(this));
			}

			@Override
			public IValue visitMap(Type type) {
				if (type.hasFieldNames()) {
					return vf.constructor(Factory.Symbol_Map, vf.constructor(Factory.Symbol_Label, vf.string(type.getKeyLabel()), type.getKeyType().accept(this)), vf.constructor(Factory.Symbol_Label, vf.string(type.getValueLabel()), type.getValueType().accept(this)));
				}
				else {
					return vf.constructor(Factory.Symbol_Map, type.getKeyType().accept(this), type.getValueType().accept(this));
				}
			}

			@Override
			public IValue visitNumber(Type type) {
				return vf.constructor(Factory.Symbol_Num);
			}

			@Override
			public IValue visitAlias(Type type) {
				IListWriter w = vf.listWriter();
				Type params = type.getTypeParameters();
				
				if (params.getArity() > 0) {
					for (Type t : params) {
						w.append(t.accept(this));
					}
				}
				
				return vf.constructor(Factory.Symbol_Alias, vf.string(type.getName()), w.done(), type.getAliased().accept(this));
			}

			@Override
			public IValue visitSet(Type type) {
				if(type.isRelation()) {
					IListWriter w = vf.listWriter();

					if (type.hasFieldNames()) {
						for (int i = 0; i < type.getArity(); i++) {
							w.append(vf.constructor(Factory.Symbol_Label, vf.string(type.getFieldName(i)), type.getFieldType(i).accept(this)));
						}
					}
					else {
						if (type.getFieldTypes().isBottom()) {
							return vf.constructor(Factory.Symbol_Set, vf.constructor(Factory.Symbol_Void));
						}
						for (Type f : type.getFieldTypes()) {
							w.append(f.accept(this));
						}
					}
				
					return vf.constructor(Factory.Symbol_Rel, w.done());
				}
				return vf.constructor(Factory.Symbol_Set, type.getElementType().accept(this));
			}

			@Override
			public IValue visitSourceLocation(Type type) {
				return vf.constructor(Factory.Symbol_Loc);
			}

			@Override
			public IValue visitString(Type type) {
				return vf.constructor(Factory.Symbol_Str);
			}

			@Override
			public IValue visitNode(Type type) {
				return vf.constructor(Factory.Symbol_Node);
			}

			@Override
			public IValue visitConstructor(Type type) {
				IValue adt = cache.get(type.getAbstractDataType());
				
				if (adt == null) {
					visitAbstractData(type.getAbstractDataType());
				}
				
				IValue result = cache.get(type);
				
				if (result == null) {
					IListWriter w = vf.listWriter();

					if (type.hasFieldNames()) {
						for (int i = 0; i < type.getArity(); i++) {
							w.append(vf.constructor(Factory.Symbol_Label, vf.string(type.getFieldName(i)), type.getFieldType(i).accept(this)));
						}
					}
					else {
						for (Type field : type.getFieldTypes()) {
							w.append(field.accept(this));
						}
					}
					result = vf.constructor(Factory.Symbol_Cons, vf.constructor(Factory.Symbol_Label, vf.string(type.getName()), adt), w.done());

					cache.put(type, result);
					addConstructorDefinition((IConstructor) result, type);
				}
				
				return result;
			}

			private void addConstructorDefinition(IConstructor result, Type type) {
				IConstructor adt = (IConstructor) type.getAbstractDataType().accept(this);
				
				IConstructor choice = definitions.get(adt);
				ISetWriter alts = vf.setWriter();
				
				if (choice != null) {
					alts.insertAll((ISet) choice.get("alternatives"));
				}
				
				IListWriter w = vf.listWriter();
				if (type.hasFieldNames()) {
					for(int i = 0; i < type.getArity(); i++) {
						w.append(vf.constructor(Factory.Symbol_Label, vf.string(type.getFieldName(i)), type.getFieldType(i).accept(this)));
					}
				}
				else {
					for (Type field : type.getFieldTypes()) {
						w.append(field.accept(this));
					}
				}
				
				alts.insert(vf.constructor(Factory.Production_Cons, vf.constructor(Factory.Symbol_Label,  vf.string(type.getName()), adt), w.done(), vf.set()));
				choice = vf.constructor(Factory.Production_Choice, adt, alts.done());
				definitions.put(adt, choice);
			}

			@Override
			public IValue visitAbstractData(Type type) {
				IValue sym = cache.get(type);
				
				if (sym == null) {
					IListWriter w = vf.listWriter();
					Type params = type.getTypeParameters();
					if (params.getArity() > 0) {
						for (Type param : params) {
							w.append(param.accept(this));
						}
					}
					
					sym = vf.constructor(Factory.Symbol_Adt, vf.string(type.getName()), w.done());
					cache.put(type, sym);
				

					// make sure to find the type by the uninstantiated adt
					Type adt = store.lookupAbstractDataType(type.getName());
					for (Type cons : store.lookupAlternatives(adt)) {
						cons.accept(this);
					}
				}
				
				return sym;
			}

			@Override
			public IValue visitTuple(Type type) {
				IListWriter w = vf.listWriter();
				
				if (type.hasFieldNames()) {
					for (int i = 0; i < type.getArity(); i++) {
						w.append(vf.constructor(Factory.Symbol_Label, vf.string(type.getFieldName(i)), type.getFieldType(i).accept(this)));
					}
				}
				else {
					for (Type f : type) {
						w.append(f.accept(this));
					}
				}

				return vf.constructor(Factory.Symbol_Tuple, w.done());
			}

			@Override
			public IValue visitValue(Type type) {
				return vf.constructor(Factory.Symbol_Value);
			}

			@Override
			public IValue visitVoid(Type type) {
				return vf.constructor(Factory.Symbol_Void);
			}

			@Override
			public IValue visitBool(Type boolType) {
				return vf.constructor(Factory.Symbol_Bool);
			}

			@Override
			public IValue visitParameter(Type parameterType) {
				return vf.constructor(Factory.Symbol_BoundParameter, vf.string(parameterType.getName()), parameterType.getBound().accept(this));
			}

			@Override
			public IValue visitExternal(Type externalType) {
				if (externalType instanceof NonTerminalType) {
					return visitNonTerminalType((NonTerminalType) externalType);
				}
				else if (externalType instanceof ReifiedType) {
					return visitReifiedType((ReifiedType) externalType);
				}
				else if (externalType instanceof FunctionType) {
					return visitFunctionType((FunctionType) externalType);
				}
				
				throw new ImplementationError("unable to reify " + externalType);
			}

			private IValue visitFunctionType(FunctionType externalType) {
				IListWriter w = vf.listWriter();
				for (Type arg : externalType.getArgumentTypes()) {
					w.append(arg.accept(this));
				}
				
				return vf.constructor(Factory.Symbol_Func, externalType.getReturnType().accept(this), w.done());
			}

			private IValue visitReifiedType(ReifiedType externalType) {
				return vf.constructor(Factory.Symbol_ReifiedType, externalType.getTypeParameters().getFieldType(0).accept(this));
			}

			private IValue visitNonTerminalType(NonTerminalType externalType) {
				IConstructor gr = ctx.getEvaluator().getGrammar(ctx.getCurrentEnvt());
				IMap rules = (IMap) gr.get("rules");
				for (IValue sym : rules) {
					definitions.put((IConstructor) sym, (IConstructor) rules.get(sym));
				}
				return externalType.getSymbol();
			}

			@Override
			public IValue visitDateTime(Type type) {
				return vf.constructor(Factory.Symbol_Datetime);
			}
		}); 
	}

	private static TypeStore constructCompleteTypeStore(Environment env) {
	  	TypeStore complete = new TypeStore();
	  	ModuleEnvironment mod = (ModuleEnvironment) env.getRoot();
		constructCompleteTypeStoreRec(complete, mod, new HashSet<java.lang.String>());
		return complete;
	}
	
	private static void constructCompleteTypeStoreRec(TypeStore complete, ModuleEnvironment env, java.util.Set<java.lang.String> done) {
		if (done.contains(env.getName())) {
			return;
		}
		done.add(env.getName());
		
		complete.importStore(env.getStore());
		
		for (java.lang.String i : env.getImports()) {
			constructCompleteTypeStoreRec(complete, env.getImport(i), done);
		}
	}
}
