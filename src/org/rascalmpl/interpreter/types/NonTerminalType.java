/*******************************************************************************
 * Copyright (c) 2009-2013 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:

 *   * Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI
 *   * Tijs van der Storm - Tijs.van.der.Storm@cwi.nl
 *   * Arnold Lankamp - Arnold.Lankamp@cwi.nl
*******************************************************************************/
package org.rascalmpl.interpreter.types;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.type.Type;
import org.rascalmpl.interpreter.asserts.ImplementationError;
import org.rascalmpl.interpreter.utils.Symbols;
import org.rascalmpl.values.uptr.Factory;
import org.rascalmpl.values.uptr.ProductionAdapter;
import org.rascalmpl.values.uptr.SymbolAdapter;
import org.rascalmpl.values.uptr.TreeAdapter;

/**
 * This is an "extension" of the PDB's type system with a special kind of type
 * that implements the connection between Rascal's non-terminals and Rascal types. 
 */
public class NonTerminalType extends RascalType {
	private IConstructor symbol;

	/*package*/ NonTerminalType(IConstructor cons) {
		if (cons.getType() == Factory.Symbol) {
			this.symbol = cons;
		}
		else if (cons.getType() == Factory.Production) {
			this.symbol = ProductionAdapter.getType(cons);
		}
		else if (cons.getConstructorType() == Factory.Tree_Appl) {
			this.symbol = TreeAdapter.getType(cons);
		}
		else if (cons.getConstructorType() == Factory.Tree_Amb) {
			IConstructor first = (IConstructor) TreeAdapter.getAlternatives(cons).iterator().next();
			this.symbol = TreeAdapter.getType(first);
		}
		else {
			throw new ImplementationError("Invalid concrete syntax type constructor:" + cons);
		}
	}
	
    /*package*/ NonTerminalType(org.rascalmpl.ast.Type type, boolean lex, String layout) {
		this(Symbols.typeToSymbol(type, lex, layout));
	}
	
	public IConstructor getSymbol() {
		return symbol;
	}
	
	public boolean isConcreteListType() {
		return SymbolAdapter.isAnyList(getSymbol());
	}
	
	@Override
	public boolean hasField(String fieldName) {
		// safe over-approximation
		return true;
	}
	
	@Override
	public String getName() {
		return Factory.Tree.getName();
	}
	
	@Override
	public Type getTypeParameters() {
		return Factory.Tree.getTypeParameters();
	}
	
	@Override
	public <T, E extends Throwable> T accept(IRascalTypeVisitor<T,E> visitor) throws E {
		return visitor.visitNonTerminal(this);
	}
	
	@Override
	protected boolean isSubtypeOfAbstractData(Type type) {
	  return type.equivalent(Factory.Tree);
	}
	
	@Override
	protected boolean isSubtypeOfNode(Type type) {
	  return true;
	}
	
	@Override
	protected Type lubWithNode(Type type) {
	  return type;
	}
	
	@Override
	protected Type lubWithAbstractData(Type type) {
	  return type.equivalent(Factory.Tree) ? type : TF.nodeType(); 
	}
	
	@Override
	protected boolean isSupertypeOf(RascalType type) {
	  return isSubtypeOfNonTerminal(this);
	}
	
	@Override
	protected Type lub(RascalType type) {
	  return type.lubWithNonTerminal(this);
	}
	
	@Override
	public boolean isSubtypeOfNonTerminal(RascalType other) {
	  IConstructor otherSym = ((NonTerminalType)other).symbol;
	  if (SymbolAdapter.isIterPlus(symbol) && SymbolAdapter.isIterStar(otherSym)) {
	    return SymbolAdapter.isEqual(SymbolAdapter.getSymbol(symbol), SymbolAdapter.getSymbol(otherSym));
	  }

	  if (SymbolAdapter.isIterPlusSeps(symbol) && SymbolAdapter.isIterStarSeps(otherSym)) {
	    return SymbolAdapter.isEqual(SymbolAdapter.getSymbol(symbol), SymbolAdapter.getSymbol(otherSym))
	        && SymbolAdapter.isEqual(SymbolAdapter.getSeparators(symbol), SymbolAdapter.getSeparators(otherSym));
	  }

	  return SymbolAdapter.isEqual(otherSym, symbol);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if (obj.getClass() == getClass()) {
			NonTerminalType other = (NonTerminalType) obj;
			return symbol.equals(other.symbol);
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return symbol.hashCode();
	}
	
	@Override
	public String toString() {
		return SymbolAdapter.toString(symbol);
	}
}
