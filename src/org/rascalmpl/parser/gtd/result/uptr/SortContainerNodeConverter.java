package org.rascalmpl.parser.gtd.result.uptr;

import java.net.URI;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IListWriter;
import org.eclipse.imp.pdb.facts.ISetWriter;
import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.rascalmpl.parser.gtd.location.PositionStore;
import org.rascalmpl.parser.gtd.result.AbstractNode;
import org.rascalmpl.parser.gtd.result.SortContainerNode;
import org.rascalmpl.parser.gtd.result.AbstractNode.CycleMark;
import org.rascalmpl.parser.gtd.result.AbstractNode.FilteringTracker;
import org.rascalmpl.parser.gtd.result.action.IActionExecutor;
import org.rascalmpl.parser.gtd.result.action.IEnvironment;
import org.rascalmpl.parser.gtd.result.struct.Link;
import org.rascalmpl.parser.gtd.util.ArrayList;
import org.rascalmpl.parser.gtd.util.IndexedStack;
import org.rascalmpl.parser.gtd.util.IntegerKeyedHashMap;
import org.rascalmpl.parser.gtd.util.ObjectIntegerKeyedHashSet;
import org.rascalmpl.values.ValueFactoryFactory;
import org.rascalmpl.values.uptr.Factory;
import org.rascalmpl.values.uptr.ProductionAdapter;

public class SortContainerNodeConverter{
	private final static IValueFactory VF = ValueFactoryFactory.getValueFactory();
	
	private final IntegerKeyedHashMap<ObjectIntegerKeyedHashSet<IConstructor>> cache;
	
	public SortContainerNodeConverter(){
		super();
		
		cache = new IntegerKeyedHashMap<ObjectIntegerKeyedHashSet<IConstructor>>();
	}
	
	private void gatherAlternatives(NodeToUPTR converter, Link child, ArrayList<IConstructor> gatheredAlternatives, IConstructor production, IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark, PositionStore positionStore, ISourceLocation sourceLocation, FilteringTracker filteringTracker, IActionExecutor actionExecutor, IEnvironment environment){
		AbstractNode resultNode = child.node;
		
		if(!(resultNode.isEpsilon() && child.prefixes == null)){
			AbstractNode[] postFix = new AbstractNode[]{resultNode};
			gatherProduction(converter, child, postFix, gatheredAlternatives, production, stack, depth, cycleMark, positionStore, sourceLocation, filteringTracker, actionExecutor, environment);
		}else{
			IEnvironment newEnvironment = actionExecutor.enteringProduction(production, environment);
			buildAlternative(production, new IConstructor[]{}, gatheredAlternatives, sourceLocation, actionExecutor, newEnvironment);
		}
	}
	
	private void gatherProduction(NodeToUPTR converter, Link child, AbstractNode[] postFix, ArrayList<IConstructor> gatheredAlternatives, IConstructor production, IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark, PositionStore positionStore, ISourceLocation sourceLocation, FilteringTracker filteringTracker, IActionExecutor actionExecutor, IEnvironment environment){
		ArrayList<Link> prefixes = child.prefixes;
		if(prefixes == null){
			IEnvironment newEnvironment = actionExecutor.enteringProduction(production, environment);
			
			int postFixLength = postFix.length;
			IConstructor[] constructedPostFix = new IConstructor[postFixLength];
			for(int i = 0; i < postFixLength; ++i){
				newEnvironment = actionExecutor.enteringNode(production, i, newEnvironment);
				
				IConstructor node = converter.convert(postFix[i], stack, depth, cycleMark, positionStore, filteringTracker, actionExecutor, environment);
				if(node == null){
					actionExecutor.exitedProduction(production, true, newEnvironment);
					return;
				}
				constructedPostFix[i] = node;
			}
			
			buildAlternative(production, constructedPostFix, gatheredAlternatives, sourceLocation, actionExecutor, newEnvironment);
			return;
		}
		
		for(int i = prefixes.size() - 1; i >= 0; --i){
			Link prefix = prefixes.get(i);
			
			AbstractNode resultNode = prefix.node;
			if(!resultNode.isRejected()){
				int length = postFix.length;
				AbstractNode[] newPostFix = new AbstractNode[length + 1];
				System.arraycopy(postFix, 0, newPostFix, 1, length);
				newPostFix[0] = resultNode;
				gatherProduction(converter, prefix, newPostFix, gatheredAlternatives, production, stack, depth, cycleMark, positionStore, sourceLocation, filteringTracker, actionExecutor, environment);
			}
		}
	}
	
	private void buildAlternative(IConstructor production, IValue[] children, ArrayList<IConstructor> gatheredAlternatives, ISourceLocation sourceLocation, IActionExecutor actionExecutor, IEnvironment environment){
		IListWriter childrenListWriter = VF.listWriter(Factory.Tree);
		for(int i = children.length - 1; i >= 0; --i){
			childrenListWriter.insert(children[i]);
		}
		
		IConstructor result = VF.constructor(Factory.Tree_Appl, production, childrenListWriter.done());
		
		result = actionExecutor.filterProduction(result, environment);
		if(result == null){
			actionExecutor.exitedProduction(production, true, environment);
			return;
		}
		
		if(sourceLocation != null) result = result.setAnnotation(Factory.Location, sourceLocation);
		
		gatheredAlternatives.add(result);
		actionExecutor.exitedProduction(production, false, environment);
	}
	
	public IConstructor convertToUPTR(NodeToUPTR converter, SortContainerNode node, IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark, PositionStore positionStore, FilteringTracker filteringTracker, IActionExecutor actionExecutor, IEnvironment environment){
		int offset = node.getOffset();
		int endOffset = node.getEndOffset();
		
		if(depth <= cycleMark.depth){
			cycleMark.reset();
		}
		
		if(node.isRejected()){
			filteringTracker.setLastFilered(node.getOffset(), node.getEndOffset());
			return null;
		}
		
		ISourceLocation sourceLocation = null;
		URI input = node.getInput();
		if(!(node.isLayout() || input == null)){
			int beginLine = positionStore.findLine(offset);
			int endLine = positionStore.findLine(endOffset);
			sourceLocation = VF.sourceLocation(input, offset, endOffset - offset, beginLine + 1, endLine + 1, positionStore.getColumn(offset, beginLine), positionStore.getColumn(endOffset, endLine));
		}
		
		int index = stack.contains(node);
		if(index != -1){ // Cycle found.
			IConstructor cycle = VF.constructor(Factory.Tree_Cycle, ProductionAdapter.getRhs(node.getFirstProduction()), VF.integer(depth - index));
			cycle = actionExecutor.filterCycle(cycle, environment);
			if(cycle != null && sourceLocation != null) cycle = cycle.setAnnotation(Factory.Location, sourceLocation);
			
			cycleMark.setMark(index);
			
			return cycle;
		}
		
		int childDepth = depth + 1;
		
		stack.push(node, depth); // Push.
		
		// Gather
		ArrayList<IConstructor> gatheredAlternatives = new ArrayList<IConstructor>();
		gatherAlternatives(converter, node.getFirstAlternative(), gatheredAlternatives, node.getFirstProduction(), stack, childDepth, cycleMark, positionStore, sourceLocation, filteringTracker, actionExecutor, environment);
		ArrayList<Link> alternatives = node.getAdditionalAlternatives();
		ArrayList<IConstructor> productions = node.getAdditionalProductions();
		if(alternatives != null){
			for(int i = alternatives.size() - 1; i >= 0; --i){
				gatherAlternatives(converter, alternatives.get(i), gatheredAlternatives, productions.get(i), stack, childDepth, cycleMark, positionStore, sourceLocation, filteringTracker, actionExecutor, environment);
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
				IConstructor alt = gatheredAlternatives.get(i);
				ambSetWriter.insert(alt);
			}
			
			result = VF.constructor(Factory.Tree_Amb, ambSetWriter.done());
			result = actionExecutor.filterAmbiguity(result, environment);
			if(result != null && sourceLocation != null){
				result = result.setAnnotation(Factory.Location, sourceLocation);
			}
		}
		
		stack.dirtyPurge(); // Pop.
		
		if(result != null && depth < cycleMark.depth){
			ObjectIntegerKeyedHashSet<IConstructor> levelCache = cache.get(offset);
			if(levelCache != null){
				IConstructor cachedResult = levelCache.getEquivalent(result, endOffset);
				if(cachedResult != null){
					return cachedResult;
				}
				
				levelCache.put(result, endOffset);
				return result;
			}
			
			levelCache = new ObjectIntegerKeyedHashSet<IConstructor>();
			levelCache.put(result, endOffset);
			cache.put(offset, levelCache);
		}
		
		return result;
	}
}
