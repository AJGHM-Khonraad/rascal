package org.rascalmpl.parser.uptr;

import java.net.URI;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IListWriter;
import org.eclipse.imp.pdb.facts.ISetWriter;
import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.rascalmpl.parser.gtd.location.PositionStore;
import org.rascalmpl.parser.gtd.result.AbstractNode;
import org.rascalmpl.parser.gtd.result.SortContainerNode;
import org.rascalmpl.parser.gtd.result.AbstractNode.CycleMark;
import org.rascalmpl.parser.gtd.result.action.IActionExecutor;
import org.rascalmpl.parser.gtd.result.struct.Link;
import org.rascalmpl.parser.gtd.util.ArrayList;
import org.rascalmpl.parser.gtd.util.IndexedStack;
import org.rascalmpl.values.ValueFactoryFactory;
import org.rascalmpl.values.uptr.Factory;
import org.rascalmpl.values.uptr.ProductionAdapter;

public class SortContainerNodeInErrorConverter{
	private final static IValueFactory VF = ValueFactoryFactory.getValueFactory();
	private final static AbstractNode[] NO_NODES = new AbstractNode[]{};
	
	private SortContainerNodeInErrorConverter(){
		super();
	}
	
	protected static void gatherAlternatives(NodeToUPTR converter, Link child, ArrayList<IConstructor> gatheredAlternatives, IConstructor production, IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark, PositionStore positionStore, ISourceLocation sourceLocation, IActionExecutor actionExecutor, Object environment){
		AbstractNode resultNode = child.getNode();
		
		if(!(resultNode.isEpsilon() && child.getPrefixes() == null)){
			AbstractNode[] postFix = new AbstractNode[]{resultNode};
			gatherProduction(converter, child, postFix, gatheredAlternatives, production, stack, depth, cycleMark, positionStore, sourceLocation, actionExecutor, environment);
		}else{
			buildAlternative(converter, NO_NODES, gatheredAlternatives, production, stack, depth, cycleMark, positionStore, sourceLocation, actionExecutor, environment);
		}
	}
	
	private static void gatherProduction(NodeToUPTR converter, Link child, AbstractNode[] postFix, ArrayList<IConstructor> gatheredAlternatives, IConstructor production, IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark, PositionStore positionStore, ISourceLocation sourceLocation, IActionExecutor actionExecutor, Object environment){
		ArrayList<Link> prefixes = child.getPrefixes();
		if(prefixes == null){
			buildAlternative(converter, postFix, gatheredAlternatives, production, stack, depth, cycleMark, positionStore, sourceLocation, actionExecutor, environment);
			return;
		}
		
		for(int i = prefixes.size() - 1; i >= 0; --i){
			Link prefix = prefixes.get(i);
			
			AbstractNode resultNode = prefix.getNode();
			
			int length = postFix.length;
			AbstractNode[] newPostFix = new AbstractNode[length + 1];
			System.arraycopy(postFix, 0, newPostFix, 1, length);
			newPostFix[0] = resultNode;
			gatherProduction(converter, prefix, newPostFix, gatheredAlternatives, production, stack, depth, cycleMark, positionStore, sourceLocation, actionExecutor, environment);
		}
	}
	
	private static void buildAlternative(NodeToUPTR converter, AbstractNode[] postFix, ArrayList<IConstructor> gatheredAlternatives, IConstructor production, IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark, PositionStore positionStore, ISourceLocation sourceLocation, IActionExecutor actionExecutor, Object environment){
		IConstructor result = null;
		
		Object newEnvironment = actionExecutor.enteringProduction(production, environment);
		
		int postFixLength = postFix.length;
		IListWriter childrenListWriter = VF.listWriter(Factory.Tree);
		for(int i = 0; i < postFixLength; ++i){
			newEnvironment = actionExecutor.enteringNode(production, i, newEnvironment);
			
			IConstructor node = converter.convertWithErrors(postFix[i], stack, depth, cycleMark, positionStore, actionExecutor, newEnvironment);
			if(node == null){
				actionExecutor.exitedProduction(production, true, newEnvironment);
				return;
			}
			childrenListWriter.append(node);
		}
		
		result = VF.constructor(Factory.Tree_Appl, production, childrenListWriter.done());
		result = actionExecutor.filterProduction(result, environment);
		
		if(sourceLocation != null) result = result.setAnnotation(Factory.Location, sourceLocation);
		
		gatheredAlternatives.add(result);
	}
	
	public static IConstructor convertToUPTR(NodeToUPTR converter, SortContainerNode node, IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark, PositionStore positionStore, IActionExecutor actionExecutor, Object environment){
		if(depth <= cycleMark.depth){
			cycleMark.reset();
		}
		
		ISourceLocation sourceLocation = null;
		URI input = node.getInput();
		if(!(node.isLayout() || input == null)){
			int offset = node.getOffset();
			int endOffset = node.getEndOffset();
			int beginLine = positionStore.findLine(offset);
			int endLine = positionStore.findLine(endOffset);
			sourceLocation = VF.sourceLocation(input, offset, endOffset - offset, beginLine + 1, endLine + 1, positionStore.getColumn(offset, beginLine), positionStore.getColumn(endOffset, endLine));
		}
		
		int index = stack.contains(node);
		if(index != -1){ // Cycle found.
			IConstructor rhsSymbol = ProductionAdapter.getType(node.getFirstProduction());
			IConstructor cycle = VF.constructor(Factory.Tree_Cycle, rhsSymbol, VF.integer(depth - index));
			cycle = actionExecutor.filterCycle(cycle, environment);
			if(cycle == null){
				cycle = VF.constructor(Factory.Tree_Error_Cycle, rhsSymbol, VF.integer(depth - index));
			}
			
			if(sourceLocation != null) cycle = cycle.setAnnotation(Factory.Location, sourceLocation);
			
			cycleMark.setMark(index);
			
			return cycle;
		}
		
		int childDepth = depth + 1;
		
		stack.push(node, depth); // Push.
		
		// Gather
		ArrayList<IConstructor> gatheredAlternatives = new ArrayList<IConstructor>();
		gatherAlternatives(converter, node.getFirstAlternative(), gatheredAlternatives, node.getFirstProduction(), stack, childDepth, cycleMark, positionStore, sourceLocation, actionExecutor, environment);
		ArrayList<Link> alternatives = node.getAdditionalAlternatives();
		ArrayList<IConstructor> productions = node.getAdditionalProductions();
		if(alternatives != null){
			for(int i = alternatives.size() - 1; i >= 0; --i){
				gatherAlternatives(converter, alternatives.get(i), gatheredAlternatives, productions.get(i), stack, childDepth, cycleMark, positionStore, sourceLocation, actionExecutor, environment);
			}
		}
		
		// Output.
		IConstructor result = null;
		
		int nrOfAlternatives = gatheredAlternatives.size();
		if(nrOfAlternatives == 1){ // Not ambiguous.
			result = gatheredAlternatives.get(0);
		}else if(nrOfAlternatives > 0){ // Ambiguous.
			ISetWriter ambSetWriter = VF.setWriter(Factory.Tree);
			for(int i = nrOfAlternatives - 1; i >= 0; --i){
				ambSetWriter.insert(gatheredAlternatives.get(i));
			}
			
			result = VF.constructor(Factory.Tree_Amb, ambSetWriter.done());
			result = actionExecutor.filterAmbiguity(result, environment);
			if(result == null){
				// Build error amb.
				result = VF.constructor(Factory.Tree_Error_Amb, ambSetWriter.done());
			}
			
			if(sourceLocation != null) result = result.setAnnotation(Factory.Location, sourceLocation);
		}
		
		stack.dirtyPurge(); // Pop.
		
		return result;
	}
}
