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

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IListWriter;
import org.eclipse.imp.pdb.facts.ISetWriter;
import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.rascalmpl.parser.gtd.result.AbstractNode.CycleMark;
import org.rascalmpl.parser.gtd.result.action.IActionExecutor;
import org.rascalmpl.parser.gtd.result.action.IEnvironment;
import org.rascalmpl.parser.gtd.result.struct.Link;
import org.rascalmpl.parser.gtd.util.ArrayList;
import org.rascalmpl.parser.gtd.util.IndexedStack;
import org.rascalmpl.parser.gtd.util.specific.PositionStore;
import org.rascalmpl.values.uptr.Factory;
import org.rascalmpl.values.uptr.ProductionAdapter;

public class ErrorSortBuilder{
	private final static IValueFactory VF = AbstractNode.VF;
	
	private ErrorSortBuilder(){
		super();
	}
	
	private static class IsInError{
		public boolean inError;
	}
	
	protected static void gatherAlternatives(Link child, ArrayList<IConstructor> gatheredAlternatives, IConstructor production, IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark, PositionStore positionStore, ISourceLocation sourceLocation, IActionExecutor actionExecutor, IEnvironment environment, boolean isInError, IsInError isInTotalError){
		AbstractNode resultNode = child.node;
		
		if(!(resultNode.isEpsilon() && child.prefixes == null)){
			AbstractNode[] postFix = new AbstractNode[]{resultNode};
			gatherProduction(child, postFix, gatheredAlternatives, production, stack, depth, cycleMark, positionStore, sourceLocation, actionExecutor, environment, isInError, isInTotalError);
		}else{
			IEnvironment newEnvironment = actionExecutor.enteredProduction(production, environment);
			buildAlternative(production, new IConstructor[]{}, gatheredAlternatives, isInError, sourceLocation, actionExecutor, newEnvironment);
		}
	}
	
	private static void gatherProduction(Link child, AbstractNode[] postFix, ArrayList<IConstructor> gatheredAlternatives, IConstructor production, IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark, PositionStore positionStore, ISourceLocation sourceLocation, IActionExecutor actionExecutor, IEnvironment environment, boolean isInError, IsInError isInTotalError){
		ArrayList<Link> prefixes = child.prefixes;
		if(prefixes == null){
			IEnvironment newEnvironment = actionExecutor.enteredProduction(production, environment);
			
			int postFixLength = postFix.length;
			IConstructor[] constructedPostFix = new IConstructor[postFixLength];
			
			// We don't need to split for the first iteration, so do the work for index 0 separately.
			IConstructor node = postFix[0].toErrorTree(stack, depth, cycleMark, positionStore, actionExecutor, newEnvironment);
			if(node == null){
				actionExecutor.exitedProduction(production, newEnvironment, true);
				return;
			}
			constructedPostFix[0] = node;
			
			for(int i = 1; i < postFixLength; ++i){
				newEnvironment = actionExecutor.split(newEnvironment, production, i);
				
				node = postFix[i].toErrorTree(stack, depth, cycleMark, positionStore, actionExecutor, newEnvironment);
				if(node == null){
					actionExecutor.exitedProduction(production, newEnvironment, true);
					return;
				}
				constructedPostFix[i] = node;
			}
			
			buildAlternative(production, constructedPostFix, gatheredAlternatives, isInError, sourceLocation, actionExecutor, newEnvironment);
			return;
		}
		
		for(int i = prefixes.size() - 1; i >= 0; --i){
			Link prefix = prefixes.get(i);
			
			AbstractNode resultNode = prefix.node;
			if(!resultNode.isRejected()){
				isInError = true;
			}
			
			int length = postFix.length;
			AbstractNode[] newPostFix = new AbstractNode[length + 1];
			System.arraycopy(postFix, 0, newPostFix, 1, length);
			newPostFix[0] = resultNode;
			gatherProduction(prefix, newPostFix, gatheredAlternatives, production, stack, depth, cycleMark, positionStore, sourceLocation, actionExecutor, environment, isInError, isInTotalError);
		}
	}
	
	private static void buildAlternative(IConstructor production, IValue[] children, ArrayList<IConstructor> gatheredAlternatives, boolean error, ISourceLocation sourceLocation, IActionExecutor actionExecutor, IEnvironment environment){
		IListWriter childrenListWriter = VF.listWriter(Factory.Tree);
		for(int i = children.length - 1; i >= 0; --i){
			childrenListWriter.insert(children[i]);
		}
		
		IConstructor result = null;
		if(!error){
			result = VF.constructor(Factory.Tree_Appl, production, childrenListWriter.done());
			result = actionExecutor.filterProduction(result, environment);
		}
		if(result == null){
			result = VF.constructor(Factory.Tree_Error, production, childrenListWriter.done(), AbstractContainerNode.EMPTY_LIST);
			actionExecutor.exitedProduction(production, environment, true);
		}else{
			actionExecutor.exitedProduction(production, environment, false);
		}
		
		if(sourceLocation != null) result = result.setAnnotation(Factory.Location, sourceLocation);
		
		gatheredAlternatives.add(result);
	}
	
	public static IConstructor toErrorSortTree(SortContainerNode node, IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark, PositionStore positionStore, IActionExecutor actionExecutor, IEnvironment environment){
		ISourceLocation sourceLocation = null;
		if(!(node.isLayout || node.input == null)){
			int beginLine = positionStore.findLine(node.offset);
			int endLine = positionStore.findLine(node.endOffset);
			sourceLocation = VF.sourceLocation(node.input, node.offset, node.endOffset - node.offset, beginLine + 1, endLine + 1, positionStore.getColumn(node.offset, beginLine), positionStore.getColumn(node.endOffset, endLine));
		}
		
		int index = stack.contains(node);
		if(index != -1){ // Cycle found.
			IConstructor cycle = VF.constructor(Factory.Tree_Cycle, ProductionAdapter.getRhs(node.firstProduction), VF.integer(depth - index));
			cycle = actionExecutor.filterCycle(cycle, environment);
			if(cycle == null){
				cycle = VF.constructor(Factory.Tree_Error_Cycle, ProductionAdapter.getRhs(node.firstProduction), VF.integer(depth - index));
			}
			
			if(sourceLocation != null) cycle = cycle.setAnnotation(Factory.Location, sourceLocation);
			
			cycleMark.setMark(index);
			
			return cycle;
		}
		
		int childDepth = depth + 1;
		
		stack.push(node, depth); // Push.
		
		// Gather
		IsInError isInTotalError = new IsInError();
		isInTotalError.inError = node.rejected;
		ArrayList<IConstructor> gatheredAlternatives = new ArrayList<IConstructor>();
		gatherAlternatives(node.firstAlternative, gatheredAlternatives, node.firstProduction, stack, childDepth, cycleMark, positionStore, sourceLocation, actionExecutor, environment, false, isInTotalError);
		if(node.alternatives != null){
			for(int i = node.alternatives.size() - 1; i >= 0; --i){
				gatherAlternatives(node.alternatives.get(i), gatheredAlternatives, node.productions.get(i), stack, childDepth, cycleMark, positionStore, sourceLocation, actionExecutor, environment, false, isInTotalError);
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
			
			if(isInTotalError.inError){
				result = VF.constructor(Factory.Tree_Error_Amb, ambSetWriter.done());
				// Don't filter error ambs.
			}else{
				result = VF.constructor(Factory.Tree_Amb, ambSetWriter.done());
				result = actionExecutor.filterAmbiguity(result, environment);
				if(result == null){
					// Build error amb.
					result = VF.constructor(Factory.Tree_Error_Amb, ambSetWriter.done());
				}
			}
			
			if(sourceLocation != null) result = result.setAnnotation(Factory.Location, sourceLocation);
		}
		
		stack.dirtyPurge(); // Pop.
		
		return result;
	}
}
