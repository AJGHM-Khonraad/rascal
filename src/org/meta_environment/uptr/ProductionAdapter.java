package org.meta_environment.uptr;

import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.impl.reference.ValueFactory;
import org.eclipse.imp.pdb.facts.type.FactTypeError;

public class ProductionAdapter {
	private IConstructor tree;

	public ProductionAdapter(IConstructor tree) {
		if (tree.getType() != Factory.Production) {
			throw new FactTypeError("ProductionWrapper only wraps UPTR productions, not " + tree.getType());
		}
		this.tree = tree;
	}
	
	public String getConstructorName() {
		for (IValue attr : getAttributes()) {
			if (attr.getType().isAbstractDataType() && ((IConstructor) attr).getConstructorType() == Factory.Attr_Term) {
				IValue value = ((IConstructor)attr).get("value");
				if (value.getType().isAbstractDataType() && ((IConstructor) value).getConstructorType() == Factory.Constructor_Name) {
					return ((IString) ((IConstructor) value).get("name")).getValue();
				}
			}
		}
		throw new FactTypeError("Production does not have constructor name: " + this.tree);
	}
	
	public SymbolAdapter getRhs() {
		return new SymbolAdapter((IConstructor) tree.get("rhs"));
	}
	
	public IList getLhs() {
		return (IList) tree.get("lhs");
	}
	
	public boolean isContextFree() {
		return getRhs().isCf();
	}
	
	
	
	public String getSortName() {
		SymbolAdapter rhs = getRhs();
		
		if (rhs.isCf() || rhs.isLex()) {
			rhs = rhs.getSymbol();
			if (rhs.isSort() || rhs.isParameterizedSort()) {
				return rhs.getName();
			}
		}
			
		throw new FactTypeError("Production does not have a sort name: " + tree);
	}
	
	public IList getAttributes() {
		IConstructor attributes = (IConstructor) tree.get("attributes");
		
		if (attributes.getConstructorType() == Factory.Attributes_Attrs) {
			return (IList) attributes.get("attrs");
		}
		else {
			return (IList) Factory.Attrs.make(ValueFactory.getInstance());
		}
	}

	public boolean isLiteral() {
		return getRhs().isLiteral();
	}

	public boolean isCILiteral() {
		return getRhs().isCILiteral();
	}

	public boolean isList() {
		return tree.getConstructorType() == Factory.Production_List;
	}

	public boolean isSeparatedList() {
		SymbolAdapter rhsSym = getRhs();
		if (rhsSym.isLex() || rhsSym.isCf()) {
			rhsSym = rhsSym.getSymbol();
		}
		return rhsSym.isIterPlusSep() || rhsSym.isIterStarSep();
	}

	public boolean isLexical() {
		return getRhs().isLex();
	}

	public boolean isLexToCf() {
		if (!isContextFree()) {
			return false;
		}
		IList lhs = getLhs();
		if (lhs.length() != 1) {
			return false;
		}
		SymbolAdapter lhsSym = new SymbolAdapter((IConstructor)lhs.get(0));
		if (!lhsSym.isLex()) {
			return false;
		}
		SymbolAdapter rhsSym = getRhs();
		return lhsSym.getSymbol().equals(rhsSym.getSymbol());
	}

}
