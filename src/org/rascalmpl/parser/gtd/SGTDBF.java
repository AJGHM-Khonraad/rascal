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
package org.rascalmpl.parser.gtd;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.rascalmpl.parser.gtd.exception.ParseError;
import org.rascalmpl.parser.gtd.exception.UndeclaredNonTerminalException;
import org.rascalmpl.parser.gtd.location.PositionStore;
import org.rascalmpl.parser.gtd.result.AbstractContainerNode;
import org.rascalmpl.parser.gtd.result.AbstractNode;
import org.rascalmpl.parser.gtd.result.ListContainerNode;
import org.rascalmpl.parser.gtd.result.SortContainerNode;
import org.rascalmpl.parser.gtd.result.action.IActionExecutor;
import org.rascalmpl.parser.gtd.result.action.VoidActionExecutor;
import org.rascalmpl.parser.gtd.result.out.FilteringTracker;
import org.rascalmpl.parser.gtd.result.out.INodeConverter;
import org.rascalmpl.parser.gtd.result.struct.Link;
import org.rascalmpl.parser.gtd.stack.AbstractStackNode;
import org.rascalmpl.parser.gtd.stack.IExpandableStackNode;
import org.rascalmpl.parser.gtd.stack.NonTerminalStackNode;
import org.rascalmpl.parser.gtd.stack.filter.ICompletionFilter;
import org.rascalmpl.parser.gtd.stack.filter.IEnterFilter;
import org.rascalmpl.parser.gtd.util.ArrayList;
import org.rascalmpl.parser.gtd.util.DoubleStack;
import org.rascalmpl.parser.gtd.util.HashMap;
import org.rascalmpl.parser.gtd.util.IndexedLinearIntegerSet;
import org.rascalmpl.parser.gtd.util.IntegerKeyedHashMap;
import org.rascalmpl.parser.gtd.util.IntegerList;
import org.rascalmpl.parser.gtd.util.LinearIntegerKeyedMap;
import org.rascalmpl.parser.gtd.util.ObjectIntegerKeyedHashMap;
import org.rascalmpl.parser.gtd.util.Stack;
import org.rascalmpl.values.ValueFactoryFactory;

public abstract class SGTDBF implements IGTD{
	private final static int DEFAULT_RESULT_STORE_ID = -1;
	
	private final static int DEFAULT_TODOLIST_CAPACITY = 16;
	
	protected final static IValueFactory VF = ValueFactoryFactory.getValueFactory();
	
	private AbstractStackNode startNode;
	private URI inputURI;
	private char[] input;
	private IActionExecutor actionExecutor;
	private INodeConverter converter;
	
	private final PositionStore positionStore;
	
	private DoubleStack<AbstractStackNode, AbstractNode>[] todoLists;
	private int queueIndex;
	
	private final Stack<AbstractStackNode> stacksToExpand;
	private DoubleStack<AbstractStackNode, AbstractNode> stacksWithTerminalsToReduce;
	private final DoubleStack<AbstractStackNode, AbstractContainerNode> stacksWithNonTerminalsToReduce;
	
	private final ArrayList<AbstractStackNode[]> lastExpects;
	private final HashMap<String, ArrayList<AbstractStackNode>> cachedEdgesForExpect;
	
	private final IntegerKeyedHashMap<AbstractStackNode> sharedNextNodes;

	private final IntegerKeyedHashMap<ObjectIntegerKeyedHashMap<String, AbstractContainerNode>> resultStoreCache;
	
	private int location;
	
	protected char lookAheadChar;
	
	private final HashMap<String, Method> methodCache;
	
	private final LinearIntegerKeyedMap<AbstractStackNode> sharedLastExpects;
	private boolean hasValidAlternatives;
	
	private final LinearIntegerKeyedMap<IntegerList> propagatedPrefixes;
	private final LinearIntegerKeyedMap<IntegerList> propagatedReductions; // Note: we can replace this thing, if we pick a more efficient solution.
	
	// Guard
	private boolean invoked;
	
	// Error reporting
	private final Stack<AbstractStackNode> unexpandableNodes;
	private final Stack<AbstractStackNode> unmatchableNodes;
	private final DoubleStack<AbstractStackNode, AbstractNode> filteredNodes;
	
	// Error reporting guards
	private boolean parseErrorOccured;
	private boolean filterErrorOccured;
	
	public SGTDBF(){
		super();
		
		positionStore = new PositionStore();
		
		stacksToExpand = new Stack<AbstractStackNode>();
		stacksWithNonTerminalsToReduce = new DoubleStack<AbstractStackNode, AbstractContainerNode>();
		
		lastExpects = new ArrayList<AbstractStackNode[]>();
		cachedEdgesForExpect = new HashMap<String, ArrayList<AbstractStackNode>>();
		
		sharedNextNodes = new IntegerKeyedHashMap<AbstractStackNode>();
		
		resultStoreCache = new IntegerKeyedHashMap<ObjectIntegerKeyedHashMap<String, AbstractContainerNode>>();
		
		location = 0;
		
		methodCache = new HashMap<String, Method>();
		
		sharedLastExpects = new LinearIntegerKeyedMap<AbstractStackNode>();
		
		propagatedPrefixes = new LinearIntegerKeyedMap<IntegerList>();
		propagatedReductions = new LinearIntegerKeyedMap<IntegerList>();
		
		unexpandableNodes = new Stack<AbstractStackNode>();
		unmatchableNodes = new Stack<AbstractStackNode>();
		filteredNodes = new DoubleStack<AbstractStackNode, AbstractNode>();
	}
	
	protected void expect(IConstructor production, AbstractStackNode... symbolsToExpect){
		lastExpects.add(symbolsToExpect);
		
		AbstractStackNode lastNode = symbolsToExpect[symbolsToExpect.length - 1];
		lastNode.setParentProduction(production);
	}
	
	protected void expectReject(IConstructor production, AbstractStackNode... symbolsToExpect){
		lastExpects.add(symbolsToExpect);
		
		AbstractStackNode lastNode = symbolsToExpect[symbolsToExpect.length - 1];
		lastNode.setParentProduction(production);
		lastNode.markAsReject();
	}
	
	protected void invokeExpects(AbstractStackNode nonTerminal){
		String name = nonTerminal.getName();
		Method method = methodCache.get(name);
		if(method == null){
			try{
				method = getClass().getMethod(name);
				try{
					method.setAccessible(true); // Try to bypass the 'isAccessible' check to save time.
				}catch(SecurityException sex){
					// Ignore this if it happens.
				}
			}catch(NoSuchMethodException nsmex){
				throw new UndeclaredNonTerminalException(name, getClass());
			}
			methodCache.putUnsafe(name, method);
		}
		
		try{
			method.invoke(this);
		}catch(IllegalAccessException iaex){
			throw new RuntimeException(iaex);
		}catch(InvocationTargetException itex){
			throw new RuntimeException(itex.getTargetException());
		} 
	}
	
	private AbstractStackNode updateNextNode(AbstractStackNode next, AbstractStackNode node, AbstractNode result){
		AbstractStackNode alternative = sharedNextNodes.get(next.getId());
		if(alternative != null){
			if(result.isEmpty()){
				if(alternative.getId() != node.getId() && !(alternative.isSeparator() || node.isSeparator())){ // (Separated) list cycle fix.
					if(alternative.isMatchable()){
						if(alternative.isEmptyLeafNode()){
							propagateEdgesAndPrefixes(node, result, alternative, alternative.getResult(), node.getEdges().size());
							return alternative;
						}
					}else{
						ObjectIntegerKeyedHashMap<String, AbstractContainerNode> levelResultStoreMap = resultStoreCache.get(location);
						AbstractContainerNode nextResult = levelResultStoreMap.get(alternative.getName(), getResultStoreId(alternative.getId()));
						if(nextResult != null){
							propagateEdgesAndPrefixes(node, result, alternative, nextResult, node.getEdges().size());
							return alternative;
						}
					}
				}
			}
			
			alternative.updateNode(node, result);
			
			return alternative;
		}
		
		if(next.isMatchable()){
			if((location + next.getLength()) > input.length) return null;
			
			AbstractNode nextResult = next.match(input, location);
			if(nextResult == null) return null;
			
			next = next.getCleanCopyWithResult(nextResult);
		}else{
			next = next.getCleanCopy();
		}
		
		next.setStartLocation(location);
		next.updateNode(node, result);
		
		sharedNextNodes.putUnsafe(next.getId(), next);
		stacksToExpand.push(next);
		
		return next;
	}
	
	private boolean updateAlternativeNextNode(AbstractStackNode node, AbstractStackNode next, AbstractNode result, LinearIntegerKeyedMap<ArrayList<AbstractStackNode>> edgesMap, ArrayList<Link>[] prefixesMap){
		int id = next.getId();
		AbstractStackNode alternative = sharedNextNodes.get(id);
		if(alternative != null){
			if(result.isEmpty()){
				if(alternative.getId() != node.getId() && !(alternative.isSeparator() || node.isSeparator())){ // (Separated) list cycle fix.
					if(alternative.isMatchable()){
						if(alternative.isEmptyLeafNode()){
							// Encountered stack 'overtake'.
							propagateAlternativeEdgesAndPrefixes(node, result, alternative, alternative.getResult(), edgesMap.size(), edgesMap, prefixesMap);
							return true;
						}
					}else{
						ObjectIntegerKeyedHashMap<String, AbstractContainerNode> levelResultStoreMap = resultStoreCache.get(location);
						AbstractContainerNode nextResult = levelResultStoreMap.get(alternative.getName(), getResultStoreId(alternative.getId()));
						if(nextResult != null){
							// Encountered stack 'overtake'.
							propagateAlternativeEdgesAndPrefixes(node, result, alternative, nextResult, edgesMap.size(), edgesMap, prefixesMap);
							return true;
						}
					}
				}
			}
			
			alternative.updatePrefixSharedNode(edgesMap, prefixesMap); // Prevent unnecessary overhead; share whenever possible.
		}else{
			if(next.isMatchable()){
				if((location + next.getLength()) > input.length) return false;
				
				AbstractNode nextResult = next.match(input, location);
				if(nextResult == null) return false;
				
				next = next.getCleanCopyWithResult(nextResult);
			}else{
				next = next.getCleanCopy();
			}
			
			next.updatePrefixSharedNode(edgesMap, prefixesMap); // Prevent unnecessary overhead; share whenever possible.
			next.setStartLocation(location);
			
			sharedNextNodes.putUnsafe(id, next);
			stacksToExpand.push(next);
		}
		return true;
	}
	
	private void propagateReductions(AbstractStackNode node, AbstractNode nodeResultStore, AbstractStackNode next, AbstractNode nextResultStore, int potentialNewEdges){
		IntegerList touched = propagatedReductions.findValue(next.getId());
		if(touched == null){
			touched = new IntegerList();
			propagatedReductions.add(next.getId(), touched);
		}
		
		LinearIntegerKeyedMap<ArrayList<AbstractStackNode>> edgesMap = node.getEdges();
		ArrayList<Link>[] prefixes = node.getPrefixesMap();
		
		IConstructor production = next.getParentProduction();
		String name = edgesMap.getValue(0).get(0).getName();
		
		boolean hasNestingRestrictions = hasNestingRestrictions(name);
		IntegerList filteredParents = null;
		if(hasNestingRestrictions){
			filteredParents = getFilteredParents(next.getId());
		}
		
		int fromIndex = edgesMap.size() - potentialNewEdges;
		for(int i = edgesMap.size() - 1; i >= fromIndex; --i){
			int startLocation = edgesMap.getKey(i);
			
			if(touched.contains(startLocation)) continue;
			touched.add(startLocation);
			
			ArrayList<Link> edgePrefixes = new ArrayList<Link>();
			Link prefix = (prefixes != null) ? new Link(prefixes[i], nodeResultStore) : new Link(null, nodeResultStore);
			edgePrefixes.add(prefix);
			
			Link resultLink = new Link(edgePrefixes, nextResultStore);
			
			if(!hasNestingRestrictions){
				handleEdgeList(edgesMap.getValue(i), name, production, resultLink, startLocation);
			}else{
				handleEdgeListWithPriorities(edgesMap.getValue(i), name, production, resultLink, startLocation, filteredParents);
			}
		}
	}
	
	private void propagateEdgesAndPrefixes(AbstractStackNode node, AbstractNode nodeResult, AbstractStackNode next, AbstractNode nextResult, int potentialNewEdges){
		IntegerList touched = propagatedPrefixes.findValue(node.getId());
		if(touched == null){
			touched = new IntegerList();
			propagatedPrefixes.add(node.getId(), touched);
		}
		
		int nrOfAddedEdges = next.updateOvertakenNode(node, nodeResult, potentialNewEdges, touched);
		if(nrOfAddedEdges == 0) return;
		
		if(next.isEndNode()){
			propagateReductions(node, nodeResult, next, nextResult, nrOfAddedEdges);
		}
		
		if(next.hasNext()){
			// Proceed with the tail of the production.
			int nextDot = next.getDot() + 1;
			AbstractStackNode[] prod = node.getProduction();
			AbstractStackNode nextNext = prod[nextDot];
			AbstractStackNode nextNextAlternative = sharedNextNodes.get(nextNext.getId());
			if(nextNextAlternative == null) return;
	
			ObjectIntegerKeyedHashMap<String, AbstractContainerNode> levelResultStoreMap = resultStoreCache.get(location);
			if(nextNextAlternative.isMatchable()){
				if(nextNextAlternative.isEmptyLeafNode()){
					propagateEdgesAndPrefixes(next, nextResult, nextNextAlternative, nextNextAlternative.getResult(), nrOfAddedEdges);
				}else{
					nextNextAlternative.updateNode(next, nextResult);
				}
			}else{
				AbstractContainerNode nextNextResultStore = levelResultStoreMap.get(nextNextAlternative.getName(), getResultStoreId(nextNextAlternative.getId()));
				if(nextNextResultStore != null){
					propagateEdgesAndPrefixes(next, nextResult, nextNextAlternative, nextNextResultStore, nrOfAddedEdges);
				}else{
					nextNextAlternative.updateNode(next, nextResult);
				}
			}
			
			// Handle alternative nexts (and prefix sharing).
			ArrayList<AbstractStackNode[]> alternateProds = node.getAlternateProductions();
			if(alternateProds != null){
				IndexedLinearIntegerSet sharedPrefixNext = new IndexedLinearIntegerSet();
				
				sharedPrefixNext.add(next.getId());
				
				LinearIntegerKeyedMap<ArrayList<AbstractStackNode>> nextEdgesMap = next.getEdges();
				ArrayList<Link>[] nextPrefixesMap = next.getPrefixesMap();
				
				for(int i = alternateProds.size() - 1; i >= 0; --i){
					prod = alternateProds.get(i);
					if(nextDot == prod.length) continue;
					AbstractStackNode alternativeNext = prod[nextDot];
					int alternativeNextId = alternativeNext.getId();
					
					if(!sharedPrefixNext.contains(alternativeNextId)){
						AbstractStackNode nextNextAltAlternative = sharedNextNodes.get(alternativeNext.getId());
						
						AbstractContainerNode nextAltResultStore = levelResultStoreMap.get(nextNextAltAlternative.getName(), getResultStoreId(nextNextAltAlternative.getId()));
						if(nextNextAltAlternative.isMatchable()){
							if(nextNextAltAlternative.isEmptyLeafNode()){
								propagateAlternativeEdgesAndPrefixes(next, nextResult, nextNextAltAlternative, nextAltResultStore, nrOfAddedEdges, nextEdgesMap, nextPrefixesMap);
							}else{
								nextNextAltAlternative.updatePrefixSharedNode(nextEdgesMap, nextPrefixesMap);
							}
						}else{
							if(nextAltResultStore != null){
								propagateAlternativeEdgesAndPrefixes(next, nextResult, nextNextAltAlternative, nextAltResultStore, nrOfAddedEdges, nextEdgesMap, nextPrefixesMap);
							}else{
								nextNextAltAlternative.updatePrefixSharedNode(nextEdgesMap, nextPrefixesMap);
							}
						}
						
						sharedPrefixNext.add(alternativeNextId);
					}
				}
			}
		}
	}
	
	private void propagateAlternativeEdgesAndPrefixes(AbstractStackNode node, AbstractNode nodeResult, AbstractStackNode next, AbstractNode nextResult, int potentialNewEdges, LinearIntegerKeyedMap<ArrayList<AbstractStackNode>> edgesMap, ArrayList<Link>[] prefixesMap){
		next.updatePrefixSharedNode(edgesMap, prefixesMap);
		
		if(next.isEndNode()){
			propagateReductions(node, nodeResult, next, nextResult, potentialNewEdges);
		}
		
		if(potentialNewEdges != 0 && next.hasNext()){
			// Proceed with the tail of the production.
			int nextDot = next.getDot() + 1;
			AbstractStackNode[] prod = node.getProduction();
			AbstractStackNode nextNext = prod[nextDot];
			AbstractStackNode nextNextAlternative = sharedNextNodes.get(nextNext.getId());
			if(nextNextAlternative == null) return;

			ObjectIntegerKeyedHashMap<String, AbstractContainerNode> levelResultStoreMap = resultStoreCache.get(location);
			if(nextNextAlternative.isMatchable()){
				if(nextNextAlternative.isEmptyLeafNode()){
					propagateEdgesAndPrefixes(next, nextResult, nextNextAlternative, nextNextAlternative.getResult(), potentialNewEdges);
				}else{
					nextNextAlternative.updateNode(next, nextResult);
				}
			}else{
				AbstractContainerNode nextResultStore = levelResultStoreMap.get(nextNextAlternative.getName(), getResultStoreId(nextNextAlternative.getId()));
				if(nextResultStore != null){
					propagateEdgesAndPrefixes(next, nextResult, nextNextAlternative, nextResultStore, potentialNewEdges);
				}else{
					nextNextAlternative.updateNode(next, nextResult);
				}
			}

			// Handle alternative nexts (and prefix sharing).
			ArrayList<AbstractStackNode[]> alternateProds = node.getAlternateProductions();
			if(alternateProds != null){
				IndexedLinearIntegerSet sharedPrefixNext = new IndexedLinearIntegerSet();
				
				sharedPrefixNext.add(next.getId());
				
				LinearIntegerKeyedMap<ArrayList<AbstractStackNode>> nextEdgesMap = next.getEdges();
				ArrayList<Link>[] nextPrefixesMap = next.getPrefixesMap();
				
				for(int i = alternateProds.size() - 1; i >= 0; --i){
					prod = alternateProds.get(i);
					if(nextDot == prod.length) continue;
					AbstractStackNode alternativeNext = prod[nextDot];
					int alternativeNextId = alternativeNext.getId();
					
					if(!sharedPrefixNext.contains(alternativeNextId)){
						AbstractStackNode nextNextAltAlternative = sharedNextNodes.get(alternativeNext.getId());
						
						if(nextNextAltAlternative.isEmptyLeafNode()){
							propagateAlternativeEdgesAndPrefixes(next, nextResult, nextNextAltAlternative, nextNextAltAlternative.getResult(), potentialNewEdges, nextEdgesMap, nextPrefixesMap);
						}else{
							AbstractContainerNode nextAltResultStore = levelResultStoreMap.get(nextNextAltAlternative.getName(), getResultStoreId(nextNextAltAlternative.getId()));
							if(nextAltResultStore != null){
								propagateAlternativeEdgesAndPrefixes(next, nextResult, nextNextAltAlternative, nextAltResultStore, potentialNewEdges, nextEdgesMap, nextPrefixesMap);
							}else{
								nextNextAltAlternative.updatePrefixSharedNode(nextEdgesMap, nextPrefixesMap);
							}
						}
						
						sharedPrefixNext.add(alternativeNextId);
					}
				}
			}
		}
	}
	
	private void updateEdges(AbstractStackNode node, AbstractNode result){
		LinearIntegerKeyedMap<ArrayList<AbstractStackNode>> edgesMap = node.getEdges();
		ArrayList<Link>[] prefixesMap = node.getPrefixesMap();
		
		IConstructor production = node.getParentProduction();
		String name = edgesMap.getValue(0).get(0).getName();
		
		boolean hasNestingRestrictions = hasNestingRestrictions(name);
		IntegerList filteredParents = null;
		if(hasNestingRestrictions){
			filteredParents = getFilteredParents(node.getId());
		}
		
		for(int i = edgesMap.size() - 1; i >= 0; --i){
			Link resultLink = new Link((prefixesMap != null) ? prefixesMap[i] : null, result);
			
			if(!hasNestingRestrictions){
				handleEdgeList(edgesMap.getValue(i), name, production, resultLink, edgesMap.getKey(i));
			}else{
				handleEdgeListWithPriorities(edgesMap.getValue(i), name, production, resultLink, edgesMap.getKey(i), filteredParents);
			}
		}
	}
	
	private void updateNullableEdges(AbstractStackNode node, AbstractNode result){
		IntegerList touched = propagatedReductions.findValue(node.getId());
		if(touched == null){
			touched = new IntegerList();
			propagatedReductions.add(node.getId(), touched);
		}
		
		LinearIntegerKeyedMap<ArrayList<AbstractStackNode>> edgesMap = node.getEdges();
		ArrayList<Link>[] prefixesMap = node.getPrefixesMap();
		
		IConstructor production = node.getParentProduction();
		String name = edgesMap.getValue(0).get(0).getName();
		
		boolean hasNestingRestrictions = hasNestingRestrictions(name);
		IntegerList filteredParents = null;
		if(hasNestingRestrictions){
			filteredParents = getFilteredParents(node.getId());
		}
		
		for(int i = edgesMap.size() - 1; i >= 0; --i){
			int startLocation = edgesMap.getKey(i);
			
			if(touched.contains(startLocation)) continue;
			touched.add(startLocation);
			
			Link resultLink = new Link((prefixesMap != null) ? prefixesMap[i] : null, result);
			
			if(!hasNestingRestrictions){
				handleEdgeList(edgesMap.getValue(i), name, production, resultLink, startLocation);
			}else{
				handleEdgeListWithPriorities(edgesMap.getValue(i), name, production, resultLink, startLocation, filteredParents);
			}
		}
	}
	
	private void updateRejects(AbstractStackNode node){
		IntegerList filteredParents = getFilteredParents(node.getId());
		
		LinearIntegerKeyedMap<ArrayList<AbstractStackNode>> edgesMap = node.getEdges();
		
		for(int i = edgesMap.size() - 1; i >= 0; --i){
			handleRejectedEdgeListWithPriorities(edgesMap.getValue(i), filteredParents, edgesMap.getKey(i));
		}
	}
	
	private void updateNullableRejects(AbstractStackNode node){
		IntegerList touched = propagatedReductions.findValue(node.getId());
		if(touched == null){
			touched = new IntegerList();
			propagatedReductions.add(node.getId(), touched);
		}

		IntegerList filteredParents = getFilteredParents(node.getId());
		
		LinearIntegerKeyedMap<ArrayList<AbstractStackNode>> edgesMap = node.getEdges();
		
		for(int i = edgesMap.size() - 1; i >= 0; --i){
			int startLocation = edgesMap.getKey(i);

			if(touched.contains(startLocation)) continue;
			touched.add(startLocation);
			
			handleRejectedEdgeListWithPriorities(edgesMap.getValue(i), filteredParents, startLocation);
		}
	}
	
	private void handleEdgeList(ArrayList<AbstractStackNode> edgeList, String name, IConstructor production, Link resultLink, int startLocation){
		ObjectIntegerKeyedHashMap<String, AbstractContainerNode> levelResultStoreMap = resultStoreCache.get(startLocation);
		
		if(levelResultStoreMap == null){
			levelResultStoreMap = new ObjectIntegerKeyedHashMap<String, AbstractContainerNode>();
			resultStoreCache.putUnsafe(startLocation, levelResultStoreMap);
		}
		
		AbstractStackNode edge = edgeList.get(0);
		
		AbstractContainerNode resultStore = levelResultStoreMap.get(name, DEFAULT_RESULT_STORE_ID);
		if(resultStore != null){
			if(!resultStore.isRejected()) resultStore.addAlternative(production, resultLink);
		}else{
			resultStore = (!edge.isExpandable()) ? new SortContainerNode(inputURI, startLocation, location, startLocation == location, edge.isSeparator(), edge.isLayout()) : new ListContainerNode(inputURI, startLocation, location, startLocation == location, edge.isSeparator(), edge.isLayout());
			levelResultStoreMap.putUnsafe(name, DEFAULT_RESULT_STORE_ID, resultStore);
			resultStore.addAlternative(production, resultLink);
			
			stacksWithNonTerminalsToReduce.push(edge, resultStore);
			
			for(int j = edgeList.size() - 1; j >= 1; --j){
				edge = edgeList.get(j);
				stacksWithNonTerminalsToReduce.push(edge, resultStore);
			}
		}
	}
	
	// Reuse these structures.
	private final IntegerList firstTimeRegistration = new IntegerList();
	private final IntegerList firstTimeReductions = new IntegerList();
	
	private void handleEdgeListWithPriorities(ArrayList<AbstractStackNode> edgeList, String name, IConstructor production, Link resultLink, int startLocation, IntegerList filteredParents){
		ObjectIntegerKeyedHashMap<String, AbstractContainerNode> levelResultStoreMap = resultStoreCache.get(startLocation);
		
		if(levelResultStoreMap == null){
			levelResultStoreMap = new ObjectIntegerKeyedHashMap<String, AbstractContainerNode>();
			resultStoreCache.putUnsafe(startLocation, levelResultStoreMap);
		}
		
		firstTimeRegistration.clear();
		firstTimeReductions.clear();
		for(int j = edgeList.size() - 1; j >= 0; --j){
			AbstractStackNode edge = edgeList.get(j);
			int resultStoreId = getResultStoreId(edge.getId());
			
			if(!firstTimeReductions.contains(resultStoreId)){
				if(firstTimeRegistration.contains(resultStoreId)) continue;
				firstTimeRegistration.add(resultStoreId);
				
				if(filteredParents == null || !filteredParents.contains(edge.getId())){
					AbstractContainerNode resultStore = levelResultStoreMap.get(name, resultStoreId);
					if(resultStore != null){
						if(!resultStore.isRejected()) resultStore.addAlternative(production, resultLink);
					}else{
						resultStore = (!edge.isExpandable()) ? new SortContainerNode(inputURI, startLocation, location, startLocation == location, edge.isSeparator(), edge.isLayout()) : new ListContainerNode(inputURI, startLocation, location, startLocation == location, edge.isSeparator(), edge.isLayout());
						levelResultStoreMap.putUnsafe(name, resultStoreId, resultStore);
						resultStore.addAlternative(production, resultLink);
						
						stacksWithNonTerminalsToReduce.push(edge, resultStore);
						firstTimeReductions.add(resultStoreId);
					}
				}
			}else{
				AbstractContainerNode resultStore = levelResultStoreMap.get(name, resultStoreId);
				stacksWithNonTerminalsToReduce.push(edge, resultStore);
			}
		}
	}
	
	private void handleRejectedEdgeListWithPriorities(ArrayList<AbstractStackNode> edgeList, IntegerList filteredParents, int startLocation){
		ObjectIntegerKeyedHashMap<String, AbstractContainerNode> levelResultStoreMap = resultStoreCache.get(startLocation);
		
		if(levelResultStoreMap == null){
			levelResultStoreMap = new ObjectIntegerKeyedHashMap<String, AbstractContainerNode>();
			resultStoreCache.putUnsafe(startLocation, levelResultStoreMap);
		}
		
		firstTimeRegistration.clear();
		firstTimeReductions.clear();
		for(int j = edgeList.size() - 1; j >= 0; --j){
			AbstractStackNode edge = edgeList.get(j);
			String nodeName = edge.getName();
			int resultStoreId = getResultStoreId(edge.getId());
			
			if(!firstTimeReductions.contains(resultStoreId)){
				if(firstTimeRegistration.contains(resultStoreId)) continue;
				firstTimeRegistration.add(resultStoreId);
				
				if(filteredParents == null || !filteredParents.contains(edge.getId())){
					AbstractContainerNode resultStore = levelResultStoreMap.get(nodeName, resultStoreId);
					if(resultStore != null){
						resultStore.setRejected();
					}else{
						resultStore = (!edge.isExpandable()) ? new SortContainerNode(inputURI, startLocation, location, startLocation == location, edge.isSeparator(), edge.isLayout()) : new ListContainerNode(inputURI, startLocation, location, startLocation == location, edge.isSeparator(), edge.isLayout());
						levelResultStoreMap.putUnsafe(nodeName, resultStoreId, resultStore);
						resultStore.setRejected();
						
						firstTimeReductions.add(resultStoreId);
					}
					filteredNodes.push(edge, resultStore);
				}
			}
		}
	}
	
	private void moveToNext(AbstractStackNode node, AbstractNode result){
		int nextDot = node.getDot() + 1;

		AbstractStackNode[] prod = node.getProduction();
		AbstractStackNode newNext = prod[nextDot];
		newNext.setProduction(prod);
		AbstractStackNode next = updateNextNode(newNext, node, result);
		
		ArrayList<AbstractStackNode[]> alternateProds = node.getAlternateProductions();
		if(alternateProds != null){
			int nextNextDot = nextDot + 1;
			
			// Handle alternative nexts (and prefix sharing).
			LinearIntegerKeyedMap<AbstractStackNode> sharedPrefixNext = new LinearIntegerKeyedMap<AbstractStackNode>();
			
			LinearIntegerKeyedMap<ArrayList<AbstractStackNode>> edgesMap = null;
			ArrayList<Link>[] prefixesMap = null;
			if(next != null){
				edgesMap = next.getEdges();
				prefixesMap = next.getPrefixesMap();
				
				sharedPrefixNext.add(newNext.getId(), next);
			}else{
				sharedPrefixNext.add(newNext.getId(), null);
			}
			
			for(int i = alternateProds.size() - 1; i >= 0; --i){
				prod = alternateProds.get(i);
				if(nextDot == prod.length) continue;
				AbstractStackNode newAlternativeNext = prod[nextDot];
				int alternativeNextId = newAlternativeNext.getId();
				
				int index = sharedPrefixNext.findKey(alternativeNextId);
				if(index == -1){
					newAlternativeNext.setProduction(prod);
					if(edgesMap != null){
						if(updateAlternativeNextNode(node, newAlternativeNext, result, edgesMap, prefixesMap)){
							sharedPrefixNext.add(alternativeNextId, newAlternativeNext);
						}else{
							sharedPrefixNext.add(alternativeNextId, null);
						}
					}else{
						AbstractStackNode alternativeNext = updateNextNode(newAlternativeNext, node, result);
						
						if(alternativeNext != null){
							edgesMap = alternativeNext.getEdges();
							prefixesMap = alternativeNext.getPrefixesMap();
							
							sharedPrefixNext.add(newAlternativeNext.getId(), alternativeNext);
						}else{
							sharedPrefixNext.add(newAlternativeNext.getId(), null);
						}
					}
				}else if(nextNextDot < prod.length){
					AbstractStackNode sharedNext = sharedPrefixNext.getValue(index);
					
					if(newAlternativeNext.isEndNode()){
						sharedNext.markAsEndNode();
						sharedNext.setParentProduction(newAlternativeNext.getParentProduction());
						sharedNext.setFollowRestriction(newAlternativeNext.getFollowRestriction());
						sharedNext.setReject(newAlternativeNext.isReject());
					}
					
					sharedNext.addProduction(prod);
				}
			}
		}
	}
	
	private void move(AbstractStackNode node, AbstractNode result){
		ICompletionFilter[] completionFilters = node.getCompletionFilters();
		if(completionFilters != null){
			int startLocation = node.getStartLocation();
			for(int i = completionFilters.length - 1; i >= 0; --i){
				if(completionFilters[i].isFiltered(input, startLocation, location, positionStore)){
					filteredNodes.push(node, result);
					return;
				}
			}
		}
		
		if(node.isEndNode()){
			if(!result.isRejected()){
				if(!node.isReject()){
					if(!result.isEmpty() || node.getId() == IExpandableStackNode.DEFAULT_LIST_EPSILON_ID){ // Handle special list case.
						updateEdges(node, result);
					}else{
						updateNullableEdges(node, result);
					}
				}else{
					if(!result.isEmpty() || node.getId() == IExpandableStackNode.DEFAULT_LIST_EPSILON_ID){ // Handle special list case.
						updateRejects(node);
					}else{
						updateNullableRejects(node);
					}
				}
			}
		}
		
		if(node.hasNext()){
			moveToNext(node, result);
		}
	}
	
	private void reduce(){
		// Reduce terminals
		while(!stacksWithTerminalsToReduce.isEmpty()){
			move(stacksWithTerminalsToReduce.peekFirst(), stacksWithTerminalsToReduce.popSecond());
		}
		
		// Reduce non-terminals
		while(!stacksWithNonTerminalsToReduce.isEmpty()){
			AbstractStackNode nonTerminal = stacksWithNonTerminalsToReduce.peekFirst();
			AbstractContainerNode result = stacksWithNonTerminalsToReduce.popSecond();
			
			// Filtering
			if(nonTerminal.isReductionFiltered(input, location)){
				filteredNodes.push(nonTerminal, result);
				return;
			}
			
			move(nonTerminal, result);
		}
	}
	
	private boolean findFirstStackToReduce(){
		for(int i = 0; i < todoLists.length; ++i){
			DoubleStack<AbstractStackNode, AbstractNode> terminalsTodo = todoLists[i];
			if(!(terminalsTodo == null || terminalsTodo.isEmpty())){
				stacksWithTerminalsToReduce = terminalsTodo;
				
				location += i;
				
				queueIndex = i;
				
				return true;
			}
		}
		return false;
	}
	
	private boolean findStacksToReduce(){
		int queueDepth = todoLists.length;
		for(int i = 1; i < queueDepth; ++i){
			queueIndex = (queueIndex + 1) % queueDepth;
			
			DoubleStack<AbstractStackNode, AbstractNode> terminalsTodo = todoLists[queueIndex];
			if(!(terminalsTodo == null || terminalsTodo.isEmpty())){
				stacksWithTerminalsToReduce = terminalsTodo;
				
				location += i;
				
				return true;
			}
		}
		return false;
	}
	
	private boolean shareListNode(int id, AbstractStackNode stack){
		AbstractStackNode sharedNode = sharedNextNodes.get(id);
		if(sharedNode != null){
			sharedNode.addEdgeWithPrefix(stack, null, location);
			return true;
		}
		return false;
	}
	
	private void handleExpects(AbstractStackNode stackBeingWorkedOn){
		sharedLastExpects.dirtyClear();
		
		ArrayList<AbstractStackNode> cachedEdges = null;
		
		int nrOfExpects = lastExpects.size();
		if(nrOfExpects == 0){ // Error reporting.
			unexpandableNodes.push(stackBeingWorkedOn);
			return;
		}
		
		for(int i = nrOfExpects - 1; i >= 0; --i){
			AbstractStackNode[] expectedNodes = lastExpects.get(i);
			
			AbstractStackNode last = expectedNodes[expectedNodes.length - 1];
			last.markAsEndNode();
			
			AbstractStackNode first = expectedNodes[0];
			
			// Handle prefix sharing.
			int firstId = first.getId();
			AbstractStackNode sharedNode;
			if((sharedNode = sharedLastExpects.findValue(firstId)) != null){
				sharedNode.addProduction(expectedNodes);
				if(expectedNodes.length == 1){
					sharedNode.markAsEndNode();
					sharedNode.setParentProduction(last.getParentProduction());
					sharedNode.setFollowRestriction(last.getFollowRestriction());
					sharedNode.setReject(last.isReject());
				}
				continue;
			}
			
			if(first.isMatchable()){
				int length = first.getLength();
				int endLocation = location + length;
				if(endLocation > input.length) continue;
				
				AbstractNode result = first.match(input, location);
				if(result == null) continue;
				
				// Filtering
				IEnterFilter[] enterFilters = first.getEnterFilters();
				if(enterFilters != null){
					for(int j = enterFilters.length - 1; j >= 0; --j){
						if(enterFilters[i].isFiltered(input, location, positionStore)) continue;
					}
				}
				
				if(first.isReductionFiltered(input, endLocation)) continue;
				
				int queueDepth = todoLists.length;
				if(length >= queueDepth){
					DoubleStack<AbstractStackNode, AbstractNode>[] oldTodoLists = todoLists;
					todoLists = new DoubleStack[length + 1];
					System.arraycopy(oldTodoLists, queueIndex, todoLists, 0, queueDepth - queueIndex);
					System.arraycopy(oldTodoLists, 0, todoLists, queueDepth - queueIndex, queueIndex);
					queueDepth = length + 1;
					queueIndex = 0;
				}
				
				int insertLocation = (queueIndex + length) % queueDepth;
				DoubleStack<AbstractStackNode, AbstractNode> terminalsTodo = todoLists[insertLocation];
				if(terminalsTodo == null){
					terminalsTodo = new DoubleStack<AbstractStackNode, AbstractNode>();
					todoLists[insertLocation] = terminalsTodo;
				}
				first = first.getCleanCopyWithResult(result);
				terminalsTodo.push(first, result);
			}else{
				first = first.getCleanCopy();
				stacksToExpand.push(first);
			}
			
			first.setStartLocation(location);
			first.setProduction(expectedNodes);
			first.initEdges();
			if(cachedEdges == null){
				cachedEdges = first.addEdge(stackBeingWorkedOn);
			}else{
				first.addEdges(cachedEdges, location);
			}
			
			sharedLastExpects.add(firstId, first);
			
			hasValidAlternatives = true;
		}
		
		cachedEdgesForExpect.put(stackBeingWorkedOn.getName(), cachedEdges);
	}
	
	protected boolean hasNestingRestrictions(String name){
		return false; // Priority and associativity filtering is off by default.
	}
	
	protected IntegerList getFilteredParents(int childId){
		return null; // Default implementation; intended to be overwritten in sub-classes.
	}
	
	protected int getResultStoreId(int parentId){
		return DEFAULT_RESULT_STORE_ID; // Default implementation; intended to be overwritten in sub-classes.
	}
	
	private void expandStack(AbstractStackNode stack){
		IEnterFilter[] enterFilters = stack.getEnterFilters();
		if(enterFilters != null){
			for(int i = enterFilters.length - 1; i >= 0; --i){
				if(enterFilters[i].isFiltered(input, location, positionStore)){
					unexpandableNodes.push(stack);
					return;
				}
			}
		}
		
		if(stack.isMatchable()){
			int length = stack.getLength();
			AbstractNode result = stack.getResult();
			
			// Filtering
			if(stack.isReductionFiltered(input, location + length)) return;
			
			int queueDepth = todoLists.length;
			if(length >= queueDepth){
				DoubleStack<AbstractStackNode, AbstractNode>[] oldTodoLists = todoLists;
				todoLists = new DoubleStack[length + 1];
				System.arraycopy(oldTodoLists, queueIndex, todoLists, 0, queueDepth - queueIndex);
				System.arraycopy(oldTodoLists, 0, todoLists, queueDepth - queueIndex, queueIndex);
				queueDepth = length + 1;
				queueIndex = 0;
			}
			
			int insertLocation = (queueIndex + length) % queueDepth;
			DoubleStack<AbstractStackNode, AbstractNode> terminalsTodo = todoLists[insertLocation];
			if(terminalsTodo == null){
				terminalsTodo = new DoubleStack<AbstractStackNode, AbstractNode>();
				todoLists[insertLocation] = terminalsTodo;
			}
			terminalsTodo.push(stack, result);
		}else if(!stack.isExpandable()){
			ArrayList<AbstractStackNode> cachedEdges = cachedEdgesForExpect.get(stack.getName());
			if(cachedEdges != null){
				cachedEdges.add(stack);
				
				ObjectIntegerKeyedHashMap<String, AbstractContainerNode> levelResultStoreMap = resultStoreCache.get(location);
				if(levelResultStoreMap != null){
					AbstractContainerNode resultStore = levelResultStoreMap.get(stack.getName(), getResultStoreId(stack.getId()));
					if(resultStore != null){ // Is nullable, add the known results.
						stacksWithNonTerminalsToReduce.push(stack, resultStore);
					}
				}
			}else{
				invokeExpects(stack);
				hasValidAlternatives = false;
				handleExpects(stack);
				if(!hasValidAlternatives){
					unexpandableNodes.push(stack);
				}
			}
		}else{ // List
			AbstractStackNode[] listChildren = stack.getChildren();
			
			for(int i = listChildren.length - 1; i >= 0; --i){
				AbstractStackNode child = listChildren[i];
				int childId = child.getId();
				if(!shareListNode(childId, stack)){
					if(child.isMatchable()){
						int length = child.getLength();
						int endLocation = location + length;
						if(endLocation > input.length) continue;
						
						AbstractNode result = child.match(input, location);
						if(result == null) continue;
						
						// Filtering
						IEnterFilter[] childEnterFilters = child.getEnterFilters();
						if(childEnterFilters != null){
							for(int j = childEnterFilters.length - 1; j >= 0; --j){
								if(childEnterFilters[i].isFiltered(input, location, positionStore)) continue;
							}
						}
						
						if(child.isReductionFiltered(input, endLocation)) continue;
						
						int queueDepth = todoLists.length;
						if(length >= queueDepth){
							DoubleStack<AbstractStackNode, AbstractNode>[] oldTodoLists = todoLists;
							todoLists = new DoubleStack[length + 1];
							System.arraycopy(oldTodoLists, queueIndex, todoLists, 0, queueDepth - queueIndex);
							System.arraycopy(oldTodoLists, 0, todoLists, queueDepth - queueIndex, queueIndex);
							queueDepth = length + 1;
							queueIndex = 0;
						}
						
						int insertLocation = (queueIndex + length) % queueDepth;
						DoubleStack<AbstractStackNode, AbstractNode> terminalsTodo = todoLists[insertLocation];
						if(terminalsTodo == null){
							terminalsTodo = new DoubleStack<AbstractStackNode, AbstractNode>();
							todoLists[insertLocation] = terminalsTodo;
						}
						child = child.getCleanCopyWithResult(result);
						terminalsTodo.push(child, result);
					}else{
						child = child.getCleanCopy();
						stacksToExpand.push(child);
					}
					
					sharedNextNodes.putUnsafe(childId, child);
					
					child.setStartLocation(location);
					child.initEdges();
					child.addEdgeWithPrefix(stack, null, location);
				}
			}
			
			if(stack.canBeEmpty()){ // Star list or optional.
				// This is always epsilon (and unique for this position); so shouldn't be shared.
				AbstractStackNode empty = stack.getEmptyChild().getCleanCopy();
				empty.setStartLocation(location);
				empty.initEdges();
				empty.addEdge(stack);
				
				stacksToExpand.push(empty);
			}
		}
	}
	
	private void expand(){
		while(!stacksToExpand.isEmpty()){
			lastExpects.dirtyClear();
			expandStack(stacksToExpand.pop());
		}
	}
	
	protected AbstractNode parse(AbstractStackNode startNode, URI inputURI, char[] input, IActionExecutor actionExecutor, INodeConverter converter){
		if(invoked){
			throw new RuntimeException("Can only invoke 'parse' once.");
		}
		invoked = true;
		
		// Initialize.
		this.startNode = startNode;
		this.inputURI = inputURI;
		this.input = input;
		this.actionExecutor = actionExecutor;
		this.converter = converter;
		
		positionStore.index(input);
		
		todoLists = new DoubleStack[DEFAULT_TODOLIST_CAPACITY];
		
		AbstractStackNode rootNode = startNode.getCleanCopy();
		rootNode.setStartLocation(0);
		rootNode.initEdges();
		stacksToExpand.push(rootNode);
		lookAheadChar = (input.length > 0) ? input[0] : 0;
		expand();
		
		if(findFirstStackToReduce()){
			boolean shiftedLevel = (location != 0);
			do{
				lookAheadChar = (location < input.length) ? input[location] : 0;
				if(shiftedLevel){ // Nullable fix for the first level.
					sharedNextNodes.clear();
					resultStoreCache.clear();
					cachedEdgesForExpect.clear();
					
					propagatedPrefixes.dirtyClear();
					propagatedReductions.dirtyClear();
					
					unexpandableNodes.dirtyClear();
					unmatchableNodes.dirtyClear();
					filteredNodes.dirtyClear();
				}
				
				do{
					reduce();
					
					expand();
				}while(!stacksWithNonTerminalsToReduce.isEmpty() || !stacksWithTerminalsToReduce.isEmpty());
				shiftedLevel = true;
			}while(findStacksToReduce());
		}
		
		if(location == input.length){
			ObjectIntegerKeyedHashMap<String, AbstractContainerNode> levelResultStoreMap = resultStoreCache.get(0);
			if(levelResultStoreMap != null){
				AbstractContainerNode result = levelResultStoreMap.get(startNode.getName(), getResultStoreId(startNode.getId()));
				if(!(result == null || result.isRejected())){
					return result;
				}
			}
		}
		
		// Parse error.
		parseErrorOccured = true;
		
		int errorLocation = (location == Integer.MAX_VALUE ? 0 : location);
		int line = positionStore.findLine(errorLocation);
		int column = positionStore.getColumn(errorLocation, line);
		throw new ParseError("Parse error", inputURI, errorLocation, 0, line, line, column, column, unexpandableNodes, unmatchableNodes, filteredNodes);
	}
	
	// With post parse filtering.
	public IConstructor parse(String nonterminal, URI inputURI, char[] input, IActionExecutor actionExecutor, INodeConverter converter){
		AbstractNode result = parse(new NonTerminalStackNode(AbstractStackNode.START_SYMBOL_ID, 0, nonterminal), inputURI, input, actionExecutor, converter);
		return buildTree(result);
	}
	
	// Without post parse filtering.
	public IConstructor parse(String nonterminal, URI inputURI, char[] input, INodeConverter converter){
		AbstractNode result = parse(new NonTerminalStackNode(AbstractStackNode.START_SYMBOL_ID, 0, nonterminal), inputURI, input, new VoidActionExecutor(), converter);
		return buildTree(result);
	}
	
	protected IConstructor parse(AbstractStackNode startNode, URI inputURI, char[] input, INodeConverter converter){
		AbstractNode result = parse(startNode, inputURI, input, new VoidActionExecutor(), converter);
		return buildTree(result);
	}
	
	protected IConstructor buildTree(AbstractNode result){
		FilteringTracker filteringTracker = new FilteringTracker();
		// Invoke the forest flattener, a.k.a. "the bulldozer".
		Object rootEnvironment = actionExecutor.createRootEnvironment();
		IConstructor resultTree = null;
		try{
			resultTree = converter.convert(result, positionStore, actionExecutor, rootEnvironment, filteringTracker);
		}finally{
			actionExecutor.completed(rootEnvironment, (resultTree == null));
		}
		if(resultTree != null){
			return resultTree; // Success.
		}
		
		// Filtering error.
		filterErrorOccured = true;
		
		int offset = filteringTracker.getOffset();
		int endOffset = filteringTracker.getEndOffset();
		int length = endOffset - offset;
		int beginLine = positionStore.findLine(offset);
		int beginColumn = positionStore.getColumn(offset, beginLine);
		int endLine = positionStore.findLine(endOffset);
		int endColumn = positionStore.getColumn(endOffset, endLine);
		throw new ParseError("All trees were filtered", inputURI, offset, length, beginLine, endLine, beginColumn, endColumn);
	}
	
	public IConstructor buildErrorTree(){
		if(parseErrorOccured){
			ErrorTreeBuilder errorTreeBuilder = new ErrorTreeBuilder(this, startNode, positionStore, actionExecutor, input, location, inputURI);
			return errorTreeBuilder.buildErrorTree(unexpandableNodes, unmatchableNodes, filteredNodes);
		}
		
		if(filterErrorOccured){
			ObjectIntegerKeyedHashMap<String, AbstractContainerNode> levelResultStoreMap = resultStoreCache.get(0);
			AbstractContainerNode result = levelResultStoreMap.get(startNode.getName(), getResultStoreId(startNode.getId()));
			// Invoke "the bulldozer" that constructs error trees while it's flattening the forest.
			Object rootEnvironment = actionExecutor.createRootEnvironment();
			try{
				return converter.convertWithErrors(result, positionStore, actionExecutor, rootEnvironment);
			}finally{
				actionExecutor.completed(rootEnvironment, true);
			}
		}
		
		throw new RuntimeException("Cannot build an error tree as no parse error occurred.");
	}
}
