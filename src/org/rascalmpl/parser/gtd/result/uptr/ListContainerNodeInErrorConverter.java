package org.rascalmpl.parser.gtd.result.uptr;

import java.net.URI;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IListWriter;
import org.eclipse.imp.pdb.facts.ISetWriter;
import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.rascalmpl.parser.gtd.location.PositionStore;
import org.rascalmpl.parser.gtd.result.AbstractNode;
import org.rascalmpl.parser.gtd.result.ListContainerNode;
import org.rascalmpl.parser.gtd.result.AbstractNode.CycleMark;
import org.rascalmpl.parser.gtd.result.action.IActionExecutor;
import org.rascalmpl.parser.gtd.result.action.IEnvironment;
import org.rascalmpl.parser.gtd.result.struct.Link;
import org.rascalmpl.parser.gtd.result.uptr.ListContainerNodeConverter.CycleNode;
import org.rascalmpl.parser.gtd.util.ArrayList;
import org.rascalmpl.parser.gtd.util.DoubleArrayList;
import org.rascalmpl.parser.gtd.util.HashMap;
import org.rascalmpl.parser.gtd.util.IndexedStack;
import org.rascalmpl.values.ValueFactoryFactory;
import org.rascalmpl.values.uptr.Factory;
import org.rascalmpl.values.uptr.ProductionAdapter;

public class ListContainerNodeInErrorConverter{
	private final static IValueFactory VF = ValueFactoryFactory.getValueFactory();
	private final static IList EMPTY_LIST = VF.list();
	
	private ListContainerNodeInErrorConverter(){
		super();
	}
	
	private static IConstructor[] constructPostFix(NodeToUPTR converter, AbstractNode[] postFix, IConstructor production, IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark, PositionStore positionStore, IActionExecutor actionExecutor, IEnvironment environment, IConstructor[] toFill, int fromIndex){
		int postFixLength = postFix.length;
		for(int i = 0; i < postFixLength; ++i){
			AbstractNode node = postFix[i];
			if(!(node instanceof CycleNode)){
				toFill[fromIndex + i] = converter.convertWithErrors(postFix[i], stack, depth, cycleMark, positionStore, actionExecutor, environment);
			}else{
				CycleNode cycleNode = (CycleNode) node;
				IConstructor[] constructedCycle = constructCycle(converter, production, cycleNode, stack, depth, cycleMark, positionStore, actionExecutor, environment);
				
				int constructedCycleLength = constructedCycle.length;
				if(constructedCycleLength == 1){
					toFill[fromIndex + i] = constructedCycle[0];
				}else{
					int currentLength = toFill.length;
					IConstructor[] newToFill = new IConstructor[currentLength + constructedCycleLength];
					System.arraycopy(toFill, 0, newToFill, 0, i);
					System.arraycopy(constructedCycle, 0, newToFill, i, constructedCycleLength);
					
					fromIndex += constructedCycleLength;
					toFill = newToFill;
				}
			}
		}
		
		return toFill;
	}
	
	private static IConstructor[] constructCycle(NodeToUPTR converter, IConstructor production, CycleNode cycleNode, IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark, PositionStore positionStore, IActionExecutor actionExecutor, IEnvironment environment){
		AbstractNode[] cycleElements = cycleNode.cycle;
		
		int nrOfCycleElements = cycleElements.length;
		IConstructor[] convertedCycle;
		if(nrOfCycleElements == 1){
			convertedCycle = new IConstructor[1];
			convertedCycle[0] = converter.convertWithErrors(cycleElements[0], stack, depth, cycleMark, positionStore, actionExecutor, environment);
		}else{
			convertedCycle = new IConstructor[nrOfCycleElements + 1];
			convertedCycle[0] = converter.convertWithErrors(cycleElements[nrOfCycleElements], stack, depth, cycleMark, positionStore, actionExecutor, environment);
			for(int i = 0; i < nrOfCycleElements; ++i){
				convertedCycle[i + 1] = converter.convertWithErrors(cycleElements[i], stack, depth, cycleMark, positionStore, actionExecutor, environment);
			}
		}
		
		IConstructor cycle = VF.constructor(Factory.Tree_Cycle, ProductionAdapter.getRhs(production), VF.integer(1));
		cycle = actionExecutor.filterCycle(cycle, environment);
		if(cycle == null){
			cycle = VF.constructor(Factory.Tree_Error_Cycle, ProductionAdapter.getRhs(production), VF.integer(1));
		}
		
		IConstructor elements = VF.constructor(Factory.Tree_Appl, production, VF.list(convertedCycle));
		
		IConstructor constructedCycle = VF.constructor(Factory.Tree_Amb, VF.set(elements, cycle));
		
		return new IConstructor[]{constructedCycle};
	}
	
	protected static void gatherAlternatives(NodeToUPTR converter, Link child, DoubleArrayList<IConstructor[], IConstructor> gatheredAlternatives, IConstructor production, IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark, HashMap<ArrayList<Link>, IConstructor[]> sharedPrefixCache, PositionStore positionStore, IActionExecutor actionExecutor, IEnvironment environment){
		AbstractNode childNode = child.getNode();
		
		if(!(childNode.isEpsilon() && child.getPrefixes() == null)){
			ArrayList<AbstractNode> blackList = new ArrayList<AbstractNode>();
			if(childNode.isEmpty()){
				CycleNode cycle = gatherCycle(child, new AbstractNode[]{childNode}, blackList);
				if(cycle != null){
					if(cycle.cycle.length == 1){
						gatherProduction(converter, child, new AbstractNode[]{cycle}, gatheredAlternatives, production, stack, depth, cycleMark, sharedPrefixCache, positionStore, blackList, actionExecutor, environment);
					}else{
						gatherProduction(converter, child, new AbstractNode[]{childNode, cycle}, gatheredAlternatives, production, stack, depth, cycleMark, sharedPrefixCache, positionStore, blackList, actionExecutor, environment);
					}
					return;
				}
			}
			gatherProduction(converter, child, new AbstractNode[]{childNode}, gatheredAlternatives, production, stack, depth, cycleMark, sharedPrefixCache, positionStore, blackList, actionExecutor, environment);
		}else{
			gatheredAlternatives.add(new IConstructor[]{}, production);
		}
	}
	
	private static void gatherProduction(NodeToUPTR converter, Link child, AbstractNode[] postFix, DoubleArrayList<IConstructor[], IConstructor> gatheredAlternatives, IConstructor production, IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark, HashMap<ArrayList<Link>, IConstructor[]> sharedPrefixCache, PositionStore positionStore, ArrayList<AbstractNode> blackList, IActionExecutor actionExecutor, IEnvironment environment){
		do{
			ArrayList<Link> prefixes = child.getPrefixes();
			if(prefixes == null){
				IConstructor[] constructedPostFix = new IConstructor[postFix.length];
				constructedPostFix = constructPostFix(converter, postFix, production, stack, depth, cycleMark, positionStore, actionExecutor, environment, constructedPostFix, 0);
				gatheredAlternatives.add(constructedPostFix, production);
				return;
			}
			
			if(prefixes.size() == 1){
				Link prefix = prefixes.get(0);
				
				if(prefix == null){
					IConstructor[] constructedPostFix = new IConstructor[postFix.length];
					constructedPostFix = constructPostFix(converter, postFix, production, stack, depth, cycleMark, positionStore, actionExecutor, environment, constructedPostFix, 0);
					gatheredAlternatives.add(constructedPostFix, production);
					return;
				}
				
				AbstractNode prefixNode = prefix.getNode();
				if(blackList.contains(prefixNode)){
					return;
				}
				
				if(prefixNode.isEmpty() && !prefixNode.isSeparator()){ // Possibly a cycle.
					CycleNode cycle = gatherCycle(prefix, new AbstractNode[]{prefixNode}, blackList);
					if(cycle != null){
						prefixNode = cycle;
					}
				}
				
				int length = postFix.length;
				AbstractNode[] newPostFix = new AbstractNode[length + 1];
				System.arraycopy(postFix, 0, newPostFix, 1, length);
				newPostFix[0] = prefixNode;
				
				child = prefix;
				postFix = newPostFix;
				continue;
			}
			
			gatherAmbiguousProduction(converter, prefixes, postFix, gatheredAlternatives, production, stack, depth, cycleMark, sharedPrefixCache, positionStore, blackList, actionExecutor, environment);
			
			break;
		}while(true);
	}
	
	private static void gatherAmbiguousProduction(NodeToUPTR converter, ArrayList<Link> prefixes, AbstractNode[] postFix, DoubleArrayList<IConstructor[], IConstructor> gatheredAlternatives, IConstructor production, IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark, HashMap<ArrayList<Link>, IConstructor[]> sharedPrefixCache, PositionStore positionStore, ArrayList<AbstractNode> blackList, IActionExecutor actionExecutor, IEnvironment environment){
		IConstructor[] cachedPrefixResult = sharedPrefixCache.get(prefixes);
		if(cachedPrefixResult != null){
			int prefixResultLength = cachedPrefixResult.length;
			IConstructor[] newPostFix = new IConstructor[prefixResultLength + postFix.length];
			System.arraycopy(cachedPrefixResult, 0, newPostFix, 0, prefixResultLength);
			
			newPostFix = constructPostFix(converter, postFix, production, stack, depth, cycleMark, positionStore, actionExecutor, environment, newPostFix, prefixResultLength);
			
			gatheredAlternatives.add(newPostFix, production);
			return;
		}
		
		DoubleArrayList<IConstructor[], IConstructor> gatheredPrefixes = new DoubleArrayList<IConstructor[], IConstructor>();
		
		for(int i = prefixes.size() - 1; i >= 0; --i){
			Link prefix = prefixes.get(i);
			
			if(prefix == null){
				IConstructor[] constructedPostFix = new IConstructor[postFix.length];
				constructedPostFix = constructPostFix(converter, postFix, production, stack, depth, cycleMark, positionStore, actionExecutor, environment, constructedPostFix, 0);
				gatheredAlternatives.add(constructedPostFix, production);
			}else{
				AbstractNode prefixNode = prefix.getNode();
				if(blackList.contains(prefixNode)){
					continue;
				}
				
				if(prefixNode.isEmpty() && !prefixNode.isSeparator()){ // Possibly a cycle.
					CycleNode cycle = gatherCycle(prefix, new AbstractNode[]{prefixNode}, blackList);
					if(cycle != null){
						gatherProduction(converter, prefix, new AbstractNode[]{cycle}, gatheredPrefixes, production, stack, depth, cycleMark, sharedPrefixCache, positionStore, blackList, actionExecutor, environment);
						continue;
					}
				}
				
				gatherProduction(converter, prefix, new AbstractNode[]{prefixNode}, gatheredPrefixes, production, stack, depth, cycleMark, sharedPrefixCache, positionStore, blackList, actionExecutor, environment);
			}
		}
		
		int nrOfGatheredPrefixes = gatheredPrefixes.size();
		
		if(nrOfGatheredPrefixes == 1){
			IConstructor[] prefixAlternative = gatheredPrefixes.getFirst(0);
			
			int prefixLength = prefixAlternative.length;
			IConstructor[] newPostFix = new IConstructor[prefixLength + postFix.length];
			System.arraycopy(prefixAlternative, 0, newPostFix, 0, prefixLength);
			
			newPostFix = constructPostFix(converter, postFix, production, stack, depth, cycleMark, positionStore, actionExecutor, environment, newPostFix, prefixLength);
			
			gatheredAlternatives.add(newPostFix, production);
		}else if(nrOfGatheredPrefixes > 0){
			ISetWriter ambSublist = VF.setWriter(Factory.Tree);
			
			for(int i = nrOfGatheredPrefixes - 1; i >= 0; --i){
				IConstructor alternativeSubList = VF.constructor(Factory.Tree_Appl, production, VF.list(gatheredPrefixes.getFirst(i)));
				ambSublist.insert(alternativeSubList);
			}
			
			IConstructor prefixResult = VF.constructor(Factory.Tree_Amb, ambSublist.done());
			IConstructor[] newPostFix = new IConstructor[1 + postFix.length];
			newPostFix[0] = prefixResult;
			
			newPostFix = constructPostFix(converter, postFix, production, stack, depth, cycleMark, positionStore, actionExecutor, environment, newPostFix, 1);
			
			gatheredAlternatives.add(newPostFix, production);
			
			sharedPrefixCache.put(prefixes, new IConstructor[]{prefixResult});
		}
	}
	
	private static CycleNode gatherCycle(Link child, AbstractNode[] postFix, ArrayList<AbstractNode> blackList){
		AbstractNode originNode = child.getNode();
		
		blackList.add(originNode);
		
		OUTER : do{
			ArrayList<Link> prefixes = child.getPrefixes();
			if(prefixes == null){
				return null;
			}
			
			int nrOfPrefixes = prefixes.size();
			
			for(int i = nrOfPrefixes - 1; i >= 0; --i){
				Link prefix = prefixes.get(i);
				if(prefix == null) continue;
				AbstractNode prefixNode = prefix.getNode();
				
				if(prefixNode == originNode){
					return new CycleNode(postFix);
				}
				
				if(prefixNode.isEmpty()){
					int length = postFix.length;
					AbstractNode[] newPostFix = new AbstractNode[length + 1];
					System.arraycopy(postFix, 0, newPostFix, 1, length);
					newPostFix[0] = prefixNode;
					
					child = prefix;
					postFix = newPostFix;
					continue OUTER;
				}
			}
			break;
		}while(true);
		
		return null;
	}
	
	private static IConstructor buildAlternative(IConstructor production, IConstructor[] children, boolean error){
		IListWriter childrenListWriter = VF.listWriter(Factory.Tree);
		for(int i = children.length - 1; i >= 0; --i){
			childrenListWriter.insert(children[i]);
		}
		
		if(error){
			return VF.constructor(Factory.Tree_Appl, production, childrenListWriter.done());
		}
		return VF.constructor(Factory.Tree_Error, production, childrenListWriter.done(), EMPTY_LIST);
	}
	
	public static IConstructor convertToUPTR(NodeToUPTR converter, ListContainerNode node, IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark, PositionStore positionStore, IActionExecutor actionExecutor, IEnvironment environment){
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
			IConstructor rhsSymbol = ProductionAdapter.getRhs(node.getFirstProduction());
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
		HashMap<ArrayList<Link>, IConstructor[]> sharedPrefixCache = new HashMap<ArrayList<Link>, IConstructor[]>();
		DoubleArrayList<IConstructor[], IConstructor> gatheredAlternatives = new DoubleArrayList<IConstructor[], IConstructor>();
		gatherAlternatives(converter, node.getFirstAlternative(), gatheredAlternatives, node.getFirstProduction(), stack, childDepth, cycleMark, sharedPrefixCache, positionStore, actionExecutor, environment);
		ArrayList<Link> alternatives = node.getAdditionalAlternatives();
		ArrayList<IConstructor> productions = node.getAdditionalProductions();
		if(alternatives != null){
			for(int i = alternatives.size() - 1; i >= 0; --i){
				gatherAlternatives(converter, alternatives.get(i), gatheredAlternatives, productions.get(i), stack, childDepth, cycleMark, sharedPrefixCache, positionStore, actionExecutor, environment);
			}
		}
		
		// Output.
		IConstructor result = null;
		
		int nrOfAlternatives = gatheredAlternatives.size();
		if(nrOfAlternatives == 1){ // Not ambiguous.
			IConstructor production = gatheredAlternatives.getSecond(0);
			IConstructor[] alternative = gatheredAlternatives.getFirst(0);
			result = buildAlternative(production, alternative, node.isRejected());
			if(sourceLocation != null) result = result.setAnnotation(Factory.Location, sourceLocation);
		}else if(nrOfAlternatives > 0){ // Ambiguous.
			ISetWriter ambSetWriter = VF.setWriter(Factory.Tree);
			
			for(int i = nrOfAlternatives - 1; i >= 0; --i){
				IConstructor production = gatheredAlternatives.getSecond(i);
				IConstructor[] alternative = gatheredAlternatives.getFirst(i);
				
				IConstructor alt = buildAlternative(production, alternative, node.isRejected());
				if(sourceLocation != null) alt = alt.setAnnotation(Factory.Location, sourceLocation);
				ambSetWriter.insert(alt);
			}
			
			result = VF.constructor(Factory.Tree_Error_Amb, ambSetWriter.done());
			if(sourceLocation != null) result = result.setAnnotation(Factory.Location, sourceLocation);
		}
		
		stack.dirtyPurge(); // Pop.
		
		return result;
	}
}
