/*******************************************************************************
 * Copyright (c) 2009-2011 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:

 *   * Arnold Lankamp - Arnold.Lankamp@cwi.nl
*******************************************************************************/
package org.rascalmpl.parser.gtd.result;

import java.net.URI;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IListWriter;
import org.eclipse.imp.pdb.facts.ISetWriter;
import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.IValue;
import org.rascalmpl.parser.gtd.location.PositionStore;
import org.rascalmpl.parser.gtd.result.action.IActionExecutor;
import org.rascalmpl.parser.gtd.result.action.IEnvironment;
import org.rascalmpl.parser.gtd.result.struct.Link;
import org.rascalmpl.parser.gtd.util.ArrayList;
import org.rascalmpl.parser.gtd.util.DoubleArrayList;
import org.rascalmpl.parser.gtd.util.HashMap;
import org.rascalmpl.parser.gtd.util.IndexedStack;
import org.rascalmpl.values.uptr.Factory;
import org.rascalmpl.values.uptr.ProductionAdapter;

public class ListContainerNode extends AbstractContainerNode{
	
	public ListContainerNode(URI input, int offset, int endOffset, boolean isNullable, boolean isSeparator, boolean isLayout){
		super(input, offset, endOffset, isNullable, isSeparator, isLayout);
	}
	
	protected static class CycleNode extends AbstractNode{
		public final AbstractNode[] cycle;
		
		public CycleNode(AbstractNode[] cycle){
			super();
			
			this.cycle = cycle;
		}
		
		public void addAlternative(IConstructor production, Link children){
			throw new UnsupportedOperationException();
		}
		
		public boolean isEmpty(){
			throw new UnsupportedOperationException();
		}
		
		public boolean isEpsilon(){
			throw new UnsupportedOperationException();
		}
		
		public boolean isRejected(){
			throw new UnsupportedOperationException();
		}
		
		public boolean isSeparator(){
			throw new UnsupportedOperationException();
		}
		
		public void setRejected(){
			throw new UnsupportedOperationException();
		}
		
		public IConstructor toTree(IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark, PositionStore positionStore, FilteringTracker filteringTracker, IActionExecutor actionExecutor, IEnvironment environment){
			throw new UnsupportedOperationException();
		}
		
		public IConstructor toErrorTree(IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark, PositionStore positionStore, IActionExecutor actionExecutor, IEnvironment environment){
			throw new UnsupportedOperationException();
		}
	}
	
	private static IConstructor[] constructPostFix(AbstractNode[] postFix, IConstructor production, IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark, PositionStore positionStore, FilteringTracker filteringTracker, IActionExecutor actionExecutor, IEnvironment environment){
		int postFixLength = postFix.length;
		int offset = 0;
		IConstructor[] constructedPostFix = new IConstructor[postFixLength];
		for(int i = 0; i < postFixLength; ++i){
			AbstractNode node = postFix[i];
			if(!(node instanceof CycleNode)){
				IConstructor constructedNode = postFix[i].toTree(stack, depth, cycleMark, positionStore, filteringTracker, actionExecutor, environment);
				if(constructedNode == null) return null;
				constructedPostFix[offset + i] = constructedNode;
			}else{
				CycleNode cycleNode = (CycleNode) node;
				IConstructor[] convertedCycle = convertCycle(cycleNode, stack, depth, cycleMark, positionStore, filteringTracker, actionExecutor, environment);
				if(convertedCycle == null) return null;
				
				IConstructor[] constructedCycle = constructCycle(convertedCycle, production, actionExecutor, environment);
				if(constructedCycle == null) return null;
				
				int constructedCycleLength = constructedCycle.length;
				if(constructedCycleLength == 1){
					constructedPostFix[offset + i] = constructedCycle[0];
				}else{
					offset += constructedCycleLength;
					int currentLength = constructedPostFix.length;
					IConstructor[] newConstructedPostFix = new IConstructor[currentLength + constructedCycleLength];
					System.arraycopy(constructedPostFix, 0, newConstructedPostFix, 0, i);
					System.arraycopy(constructedCycle, 0, newConstructedPostFix, i, constructedCycleLength);
				}
			}
		}
		
		return constructedPostFix;
	}
	
	private static IConstructor[] convertCycle(CycleNode cycleNode, IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark, PositionStore positionStore, FilteringTracker filteringTracker, IActionExecutor actionExecutor, IEnvironment environment){
		AbstractNode[] cycleElements = cycleNode.cycle;
		
		int nrOfCycleElements = cycleElements.length;
		IConstructor[] convertedCycle;
		if(nrOfCycleElements == 1){
			convertedCycle = new IConstructor[1];
			IConstructor element = cycleElements[0].toTree(stack, depth, cycleMark, positionStore, filteringTracker, actionExecutor, environment);
			if(element == null) return null;
			convertedCycle[0] = element;
		}else{
			convertedCycle = new IConstructor[nrOfCycleElements + 1];
			for(int i = 0; i < nrOfCycleElements; ++i){
				IConstructor element = cycleElements[i].toTree(stack, depth, cycleMark, positionStore, filteringTracker, actionExecutor, environment);
				if(element == null) return null;
				convertedCycle[i + 1] = element;
			}
			convertedCycle[0] = convertedCycle[nrOfCycleElements];
		}
		
		return convertedCycle;
	}
	
	private static IConstructor[] constructCycle(IConstructor[] convertedCycle, IConstructor production, IActionExecutor actionExecutor, IEnvironment environment){
		IConstructor cycle = VF.constructor(Factory.Tree_Cycle, ProductionAdapter.getRhs(production), VF.integer(1));
		cycle = actionExecutor.filterCycle(cycle, environment);
		if(cycle == null){
			return convertedCycle;
		}
		
		IConstructor elements = VF.constructor(Factory.Tree_Appl, production, VF.list(convertedCycle));
		
		IConstructor constructedCycle = VF.constructor(Factory.Tree_Amb, VF.set(elements, cycle));
		
		return new IConstructor[]{constructedCycle};
	}
	
	protected static void gatherAlternatives(Link child, DoubleArrayList<IConstructor[], IConstructor> gatheredAlternatives, IConstructor production, IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark, HashMap<ArrayList<Link>, IConstructor[]> sharedPrefixCache, PositionStore positionStore, FilteringTracker filteringTracker, IActionExecutor actionExecutor, IEnvironment environment){
		AbstractNode childNode = child.node;
		
		if(!(childNode.isEpsilon() && child.prefixes == null)){
			ArrayList<AbstractNode> blackList = new ArrayList<AbstractNode>();
			if(childNode.isEmpty()){
				CycleNode cycle = gatherCycle(child, new AbstractNode[]{childNode}, blackList);
				if(cycle != null){
					if(cycle.cycle.length == 1){
						gatherProduction(child, new AbstractNode[]{cycle}, gatheredAlternatives, production, stack, depth, cycleMark, sharedPrefixCache, positionStore, blackList, filteringTracker, actionExecutor, environment);
					}else{
						gatherProduction(child, new AbstractNode[]{childNode, cycle}, gatheredAlternatives, production, stack, depth, cycleMark, sharedPrefixCache, positionStore, blackList, filteringTracker, actionExecutor, environment);
					}
					return;
				}
			}
			gatherProduction(child, new AbstractNode[]{childNode}, gatheredAlternatives, production, stack, depth, cycleMark, sharedPrefixCache, positionStore, blackList, filteringTracker, actionExecutor, environment);
		}else{
			gatheredAlternatives.add(new IConstructor[]{}, production);
		}
	}
	
	private static void gatherProduction(Link child, AbstractNode[] postFix, DoubleArrayList<IConstructor[], IConstructor> gatheredAlternatives, IConstructor production, IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark, HashMap<ArrayList<Link>, IConstructor[]> sharedPrefixCache, PositionStore positionStore, ArrayList<AbstractNode> blackList, FilteringTracker filteringTracker, IActionExecutor actionExecutor, IEnvironment environment){
		do{
			ArrayList<Link> prefixes = child.prefixes;
			if(prefixes == null){
				IConstructor[] constructedPostFix = constructPostFix(postFix, production, stack, depth, cycleMark, positionStore, filteringTracker, actionExecutor, environment);
				if(constructedPostFix == null) return;
				
				gatheredAlternatives.add(constructedPostFix, production);
				return;
			}
			
			if(prefixes.size() == 1){
				Link prefix = prefixes.get(0);
				
				if(prefix == null){
					IConstructor[] constructedPostFix = constructPostFix(postFix, production, stack, depth, cycleMark, positionStore, filteringTracker, actionExecutor, environment);
					if(constructedPostFix == null) return;
					
					gatheredAlternatives.add(constructedPostFix, production);
					return;
				}
				
				AbstractNode prefixNode = prefix.node;
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
			
			gatherAmbiguousProduction(prefixes, postFix, gatheredAlternatives, production, stack, depth, cycleMark, sharedPrefixCache, positionStore, blackList, filteringTracker, actionExecutor, environment);
			
			break;
		}while(true);
	}
	
	private static void gatherAmbiguousProduction(ArrayList<Link> prefixes, AbstractNode[] postFix, DoubleArrayList<IConstructor[], IConstructor> gatheredAlternatives, IConstructor production, IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark, HashMap<ArrayList<Link>, IConstructor[]> sharedPrefixCache, PositionStore positionStore, ArrayList<AbstractNode> blackList, FilteringTracker filteringTracker, IActionExecutor actionExecutor, IEnvironment environment){
		IConstructor[] cachedPrefixResult = sharedPrefixCache.get(prefixes);
		if(cachedPrefixResult != null){
			int prefixResultLength = cachedPrefixResult.length;
			IConstructor[] constructedPostFix = constructPostFix(postFix, production, stack, depth, cycleMark, positionStore, filteringTracker, actionExecutor, environment);
			int length = constructedPostFix.length;
			IConstructor[] newPostFix;
			if(prefixResultLength == 1){
				newPostFix = new IConstructor[length + 1];
				System.arraycopy(constructedPostFix, 0, newPostFix, 1, length);
				newPostFix[0] = cachedPrefixResult[0];
			}else{
				newPostFix = new IConstructor[prefixResultLength + length];
				System.arraycopy(cachedPrefixResult, 0, newPostFix, 0, prefixResultLength);
				System.arraycopy(constructedPostFix, 0, newPostFix, prefixResultLength, length);
			}
			
			gatheredAlternatives.add(newPostFix, production);
			return;
		}
		
		DoubleArrayList<IConstructor[], IConstructor> gatheredPrefixes = new DoubleArrayList<IConstructor[], IConstructor>();
		
		for(int i = prefixes.size() - 1; i >= 0; --i){
			Link prefix = prefixes.get(i);
			
			if(prefix == null){
				IConstructor[] constructedPostFix = constructPostFix(postFix, production, stack, depth, cycleMark, positionStore, filteringTracker, actionExecutor, environment);
				if(constructedPostFix == null) return;
				
				gatheredAlternatives.add(constructedPostFix, production);
			}else{
				AbstractNode prefixNode = prefix.node;
				if(blackList.contains(prefixNode)){
					continue;
				}
				
				if(prefixNode.isEmpty() && !prefixNode.isSeparator()){ // Possibly a cycle.
					CycleNode cycle = gatherCycle(prefix, new AbstractNode[]{prefixNode}, blackList);
					if(cycle != null){
						gatherProduction(prefix, new AbstractNode[]{cycle}, gatheredPrefixes, production, stack, depth, cycleMark, sharedPrefixCache, positionStore, blackList, filteringTracker, actionExecutor, environment);
						continue;
					}
				}
				
				gatherProduction(prefix, new AbstractNode[]{prefixNode}, gatheredPrefixes, production, stack, depth, cycleMark, sharedPrefixCache, positionStore, blackList, filteringTracker, actionExecutor, environment);
			}
		}
		
		int nrOfGatheredPrefixes = gatheredPrefixes.size();
		if(nrOfGatheredPrefixes ==  0) return;
		
		if(nrOfGatheredPrefixes == 1){
			IConstructor[] prefixAlternative = gatheredPrefixes.getFirst(0);
			
			IConstructor[] constructedPrefix = constructPostFix(postFix, production, stack, depth, cycleMark, positionStore, filteringTracker, actionExecutor, environment);
			if(constructedPrefix == null) return;
			
			int length = constructedPrefix.length;
			int prefixLength = prefixAlternative.length;
			IConstructor[] newPostFix = new IConstructor[length + prefixLength];
			System.arraycopy(constructedPrefix, 0, newPostFix, prefixLength, length);
			System.arraycopy(prefixAlternative, 0, newPostFix, 0, prefixLength);
			
			gatheredAlternatives.add(newPostFix, production);
		}else{
			ISetWriter ambSublist = VF.setWriter(Factory.Tree);
			
			for(int i = nrOfGatheredPrefixes - 1; i >= 0; --i){
				IConstructor alternativeSubList = VF.constructor(Factory.Tree_Appl, production, VF.list(gatheredPrefixes.getFirst(i)));
				ambSublist.insert(alternativeSubList);
			}
			
			IConstructor prefixResult = VF.constructor(Factory.Tree_Amb, ambSublist.done());
			
			IConstructor[] constructedPrefix = constructPostFix(postFix, production, stack, depth, cycleMark, positionStore, filteringTracker, actionExecutor, environment);
			if(constructedPrefix == null) return;
			
			int length = constructedPrefix.length;
			IConstructor[] newPostFix = new IConstructor[length + 1];
			System.arraycopy(constructedPrefix, 0, newPostFix, 1, length);
			newPostFix[0] = prefixResult;
			
			gatheredAlternatives.add(newPostFix, production);
			
			sharedPrefixCache.put(prefixes, new IConstructor[]{prefixResult});
		}
	}
	
	private static CycleNode gatherCycle(Link child, AbstractNode[] postFix, ArrayList<AbstractNode> blackList){
		AbstractNode originNode = child.node;
		
		blackList.add(originNode);
		
		OUTER : do{
			ArrayList<Link> prefixes = child.prefixes;
			if(prefixes == null){
				return null;
			}
			
			int nrOfPrefixes = prefixes.size();
			
			for(int i = nrOfPrefixes - 1; i >= 0; --i){
				Link prefix = prefixes.get(i);
				if(prefix == null) continue;
				AbstractNode prefixNode = prefix.node;
				
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
	
	private static IConstructor buildAlternative(IConstructor production, IValue[] children){
		IListWriter childrenListWriter = VF.listWriter(Factory.Tree);
		for(int i = children.length - 1; i >= 0; --i){
			childrenListWriter.insert(children[i]);
		}
		
		return VF.constructor(Factory.Tree_Appl, production, childrenListWriter.done());
	}
	
	public IConstructor toTree(IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark, PositionStore positionStore, FilteringTracker filteringTracker, IActionExecutor actionExecutor, IEnvironment environment){
		if(depth <= cycleMark.depth){
			cycleMark.reset();
		}
		
		if(rejected){
			filteringTracker.setLastFilered(offset, endOffset);
			return null;
		}
		
		ISourceLocation sourceLocation = null;
		if(!(isLayout || input == null)){
			int beginLine = positionStore.findLine(offset);
			int endLine = positionStore.findLine(endOffset);
			sourceLocation = VF.sourceLocation(input, offset, endOffset - offset, beginLine + 1, endLine + 1, positionStore.getColumn(offset, beginLine), positionStore.getColumn(endOffset, endLine));
		}
		
		int index = stack.contains(this);
		if(index != -1){ // Cycle found.
			IConstructor cycle = VF.constructor(Factory.Tree_Cycle, ProductionAdapter.getRhs(firstProduction), VF.integer(depth - index));
			cycle = actionExecutor.filterCycle(cycle, environment);
			if(cycle != null && sourceLocation != null) cycle = cycle.setAnnotation(Factory.Location, sourceLocation);
			
			cycleMark.setMark(index);
			
			return cycle;
		}
		
		int childDepth = depth + 1;
		
		stack.push(this, depth); // Push.
		
		// Gather
		HashMap<ArrayList<Link>, IConstructor[]> sharedPrefixCache = new HashMap<ArrayList<Link>, IConstructor[]>();
		DoubleArrayList<IConstructor[], IConstructor> gatheredAlternatives = new DoubleArrayList<IConstructor[], IConstructor>();
		gatherAlternatives(firstAlternative, gatheredAlternatives, firstProduction, stack, childDepth, cycleMark, sharedPrefixCache, positionStore, filteringTracker, actionExecutor, environment);
		if(alternatives != null){
			for(int i = alternatives.size() - 1; i >= 0; --i){
				gatherAlternatives(alternatives.get(i), gatheredAlternatives, productions.get(i), stack, childDepth, cycleMark, sharedPrefixCache, positionStore, filteringTracker, actionExecutor, environment);
			}
		}
		
		// Output.
		IConstructor result = null;
		
		int nrOfAlternatives = gatheredAlternatives.size();
		if(nrOfAlternatives == 1){ // Not ambiguous.
			IConstructor production = gatheredAlternatives.getSecond(0);
			IValue[] alternative = gatheredAlternatives.getFirst(0);
			result = buildAlternative(production, alternative);
			if(sourceLocation != null) result = result.setAnnotation(Factory.Location, sourceLocation);
		}else if(nrOfAlternatives > 0){ // Ambiguous.
			ISetWriter ambSetWriter = VF.setWriter(Factory.Tree);
			
			for(int i = nrOfAlternatives - 1; i >= 0; --i){
				IConstructor production = gatheredAlternatives.getSecond(i);
				IValue[] alternative = gatheredAlternatives.getFirst(i);
				
				IConstructor alt = buildAlternative(production, alternative);
				if(sourceLocation != null) alt = alt.setAnnotation(Factory.Location, sourceLocation);
				ambSetWriter.insert(alt);
			}
			
			result = VF.constructor(Factory.Tree_Amb, ambSetWriter.done());
			if(sourceLocation != null) result = result.setAnnotation(Factory.Location, sourceLocation);
		}
		
		stack.dirtyPurge(); // Pop.
		
		return result;
	}
	
	/*public IConstructor toErrorTree(IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark, PositionStore positionStore, IActionExecutor actionExecutor){
		if(depth <= cycleMark.depth){
			cycleMark.reset();
		}
		
		return ErrorListBuilder.toErrorListTree(this, stack, depth, cycleMark, positionStore, actionExecutor);
	}*/
	
	public IConstructor toErrorTree(IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark, PositionStore positionStore, IActionExecutor actionExecutor, IEnvironment environment){
		return toTree(stack, depth, cycleMark, positionStore, new FilteringTracker(), actionExecutor, environment);
	}
}
