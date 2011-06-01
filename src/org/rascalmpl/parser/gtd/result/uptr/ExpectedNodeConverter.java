package org.rascalmpl.parser.gtd.result.uptr;

import java.net.URI;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IListWriter;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.rascalmpl.parser.gtd.location.PositionStore;
import org.rascalmpl.parser.gtd.result.AbstractNode;
import org.rascalmpl.parser.gtd.result.AbstractNode.CycleMark;
import org.rascalmpl.parser.gtd.result.action.IActionExecutor;
import org.rascalmpl.parser.gtd.result.error.ExpectedNode;
import org.rascalmpl.parser.gtd.util.IndexedStack;
import org.rascalmpl.values.ValueFactoryFactory;
import org.rascalmpl.values.uptr.Factory;

public class ExpectedNodeConverter{
	private final static IValueFactory VF = ValueFactoryFactory.getValueFactory();
	
	private ExpectedNodeConverter(){
		super();
	}
	
	public static IConstructor convertToUPTR(NodeToUPTR converter, ExpectedNode node, IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark, PositionStore positionStore, IActionExecutor actionExecutor, Object environment){
		IListWriter childrenListWriter = VF.listWriter(Factory.Tree);
		AbstractNode[] mismatchedChildren = node.getMismatchedChildren();
		for(int i = mismatchedChildren.length - 1; i >= 0; --i){
			childrenListWriter.insert(converter.convertWithErrors(mismatchedChildren[i], stack, depth, cycleMark, positionStore, actionExecutor, environment));
		}
		
		IConstructor result = VF.constructor(Factory.Tree_Expected, node.getSymbol(), childrenListWriter.done());
		URI input = node.getInput();
		if(!(node.isLayout() || input == null)){
			int offset = node.getOffset();
			int endOffset = node.getEndOffset();
			int beginLine = positionStore.findLine(offset);
			int endLine = positionStore.findLine(endOffset);
			result = result.setAnnotation(Factory.Location, VF.sourceLocation(input, offset, endOffset - offset, beginLine + 1, endLine + 1, positionStore.getColumn(offset, beginLine), positionStore.getColumn(endOffset, endLine)));
		}
		
		return result;
	}
}
