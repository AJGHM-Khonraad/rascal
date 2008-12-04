package org.meta_environment.uptr;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.imp.pdb.facts.IInteger;
import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IListWriter;
import org.eclipse.imp.pdb.facts.ISet;
import org.eclipse.imp.pdb.facts.INode;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.impl.hash.ValueFactory;
import org.eclipse.imp.pdb.facts.type.FactTypeError;
import org.eclipse.imp.pdb.facts.visitors.BottomUpVisitor;
import org.eclipse.imp.pdb.facts.visitors.VisitorException;
import org.meta_environment.uptr.visitors.IdentityTreeVisitor;

public class TreeAdapter {
	private INode tree;
	private ProductionAdapter prod;
	
	public TreeAdapter(INode tree) {
		if (tree.getType() != Factory.Tree) {
			throw new FactTypeError("TreeWrapper will only wrap UPTR Trees, not " +  tree.getType());
		}
		this.tree = tree;
	}
	
	public boolean isAppl() {
		return tree.getTreeNodeType() == Factory.Tree_Appl;
	}
	
	public boolean isAmb() {
		return tree.getTreeNodeType() == Factory.Tree_Amb;
	}
	
	public boolean isChar() {
		return tree.getTreeNodeType() == Factory.Tree_Char;
	}
	
	public boolean isCycle() {
		return tree.getTreeNodeType() == Factory.Tree_Cycle;
	}
	
	public ProductionAdapter getProduction() {
		if (prod == null) {
		  prod = new ProductionAdapter((INode) tree.get("prod"));
		}
		
		return prod;
	}
	
	public String getSortName() throws FactTypeError {
		return getProduction().getSortName();
	}
	
	public String getConstructorName() {
		return getProduction().getConstructorName();
	}
	
	public boolean isProduction(String sortName, String consName) {
		ProductionAdapter prod = getProduction();
		return prod.getSortName().equals(sortName) &&
		prod.getConstructorName().equals(consName);
	}

	public boolean isLexToCf() {
		return isAppl() ? getProduction().isLexToCf() : false;
	}
	
	public boolean isContextFree() {
		return isAppl() ? getProduction().isContextFree() : false;
	}
	
	public boolean isList() {
		return isAppl() ? getProduction().isList() : false;
	}
	
	public IList getArgs() {
		if (isAppl()) {
		  return (IList) tree.get("args");
		}
		else {
			throw new FactTypeError("this tree has no args");
		}
	}
	
	public boolean isLiteral() {
		return isAppl() ? getProduction().isLiteral() : false;
	}
	
	
	public IList getListASTArgs() {
		if (!isContextFree() || !isList()) {
			throw new FactTypeError("This is not a context-free list production: "
					+ tree);
		}
		IList children = getArgs();
		IListWriter writer = Factory.Args.writer(ValueFactory.getInstance());
		
		for (int i = 0; i < children.length(); i++) {
			IValue kid = children.get(i);
			writer.append(kid);	
			// skip layout and/or separators
			i += (isSeparatedList() ? 3 : 1);
		}
		return writer.done();
	}
	
	public boolean isLexical() {
		return getProduction().isLexical();
	}

	private boolean isSeparatedList() {
		return isList() && getProduction().isSeparatedList();
	}

	public IList getASTArgs() {
		if (!isContextFree()) {
			throw new FactTypeError("This is not a context-free production: "
					+ tree);
		}

		IList children = getArgs();
		IListWriter writer = Factory.Args.writer(ValueFactory.getInstance());

		for (int i = 0; i < children.length(); i++) {
			IValue kid = children.get(i);
			TreeAdapter treeAdapter = new TreeAdapter((INode) kid);
			if (!treeAdapter.isLiteral() && !treeAdapter.isCILiteral()) {
				writer.append(kid);	
			} 
			// skip layout
			i++;
		}
		return writer.done();
	}

	private boolean isCILiteral() {
		return isAppl() ? getProduction().isCILiteral() : false;
	}

	public ISet getAlternatives() {
		if (isAmb()) {
		  return (ISet) tree.get("alternatives");
		}
		else {
			throw new FactTypeError("this tree has no alternatives");
		}
	}
	
	private static class Unparser extends IdentityTreeVisitor {
		private OutputStream fStream;

		public Unparser(OutputStream stream) {
			fStream = stream;
		}

		@Override
		public INode visitTreeAmb(INode arg) throws VisitorException {
			((ISet) arg.get("alternatives")).iterator().next().accept(this);
			return arg;
		}

		@Override
		public INode visitTreeCharacter(INode arg) throws VisitorException {
			try {
				fStream.write(((IInteger) arg.get("character")).getValue());
				return arg;
			} catch (IOException e) {
				throw new VisitorException(e);
			}
		}
	}
	
	public void unparse(OutputStream stream) throws IOException, FactTypeError {
		try {
			if (tree.getTreeNodeType() == Factory.ParseTree_Top) {
				tree.get("top").accept(new BottomUpVisitor<IValue>(new Unparser(stream), ValueFactory.getInstance()));
			} else if (tree.getType() == Factory.Tree) {
				tree.accept(new BottomUpVisitor<IValue>(new Unparser(stream), ValueFactory.getInstance()));
			} else {
				throw new FactTypeError("Can not unparse this "
						+ tree.getType());
			}
		} catch (VisitorException e) {
			Throwable cause = e.getCause();

			if (cause instanceof IOException) {
				throw (IOException) cause;
			}
			else {
				System.err.println("Unexpected error in unparse: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	public String yield() throws FactTypeError {
		try {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			unparse(stream);
			return stream.toString();
		} catch (IOException e) {
			throw new FactTypeError("yield failed", e);
		}
	}


}
