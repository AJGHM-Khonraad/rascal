package org.rascalmpl.parser.sgll.result;

import java.net.URI;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IListWriter;
import org.eclipse.imp.pdb.facts.ISetWriter;
import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.IValue;
import org.rascalmpl.parser.sgll.result.struct.Link;
import org.rascalmpl.parser.sgll.util.ArrayList;
import org.rascalmpl.parser.sgll.util.DoubleArrayList;
import org.rascalmpl.parser.sgll.util.IndexedStack;
import org.rascalmpl.parser.sgll.util.Stack;
import org.rascalmpl.parser.sgll.util.specific.PositionStore;
import org.rascalmpl.values.uptr.Factory;
import org.rascalmpl.values.uptr.ProductionAdapter;

public class ContainerNode extends AbstractNode{
	private final URI input;
	private final int offset;
	private final int endOffset;
	
	private final boolean isListContainer;
	
	private boolean rejected;

	private Link firstAlternative;
	private IConstructor firstProduction;
	private ArrayList<Link> alternatives;
	private ArrayList<IConstructor> productions;
	
	private IConstructor cachedResult;
	
	public ContainerNode(URI input, int offset, int endOffset, boolean isListContainer){
		super();
		
		this.input = input;
		this.offset = offset;
		this.endOffset = endOffset;
		
		this.isListContainer = isListContainer;
	}
	
	public void addAlternative(IConstructor production, Link children){
		if(firstAlternative == null){
			firstAlternative = children;
			firstProduction = production;
		}else{
			if(alternatives == null){
				alternatives = new ArrayList<Link>(1);
				productions = new ArrayList<IConstructor>(1);
			}
			alternatives.add(children);
			productions.add(production);
		}
	}
	
	public boolean isEpsilon(){
		return false;
	}
	
	public void setRejected(){
		rejected = true;
		
		// Clean up.
		firstAlternative = null;
		alternatives = null;
		productions = null;
	}
	
	public boolean isRejected(){
		return rejected;
	}
	
	private void gatherAlternatives(Link child, DoubleArrayList<IConstructor[], IConstructor> gatheredAlternatives, IConstructor production, IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark, PositionStore positionStore, int beginLine){
		AbstractNode resultNode = child.node;
		
		if(!(resultNode.isEpsilon() && child.prefixes == null)){
			IConstructor result = resultNode.toTerm(stack, depth, cycleMark, positionStore);
			if(result == null) return; // Rejected.
			
			IConstructor[] postFix = new IConstructor[]{result};
			gatherProduction(child, postFix, gatheredAlternatives, production, stack, depth, cycleMark, positionStore, beginLine);
		}else{
			gatheredAlternatives.add(new IConstructor[]{}, production);
		}
	}
	
	private void gatherProduction(Link child, IConstructor[] postFix, DoubleArrayList<IConstructor[], IConstructor> gatheredAlternatives, IConstructor production, IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark, PositionStore positionStore, int beginLine){
		ArrayList<Link> prefixes = child.prefixes;
		if(prefixes == null){
			gatheredAlternatives.add(postFix, production);
			return;
		}
		
		for(int i = prefixes.size() - 1; i >= 0; --i){
			Link prefix = prefixes.get(i);
			
			AbstractNode resultNode = prefix.node;
			if(!resultNode.isRejected()){
				if(input != null) positionStore.setCursorTo(beginLine); // Reduce search time.
				IConstructor result = resultNode.toTerm(stack, depth, cycleMark, positionStore);
				if(result == null) return; // Rejected.
				
				int length = postFix.length;
				IConstructor[] newPostFix = new IConstructor[length + 1];
				System.arraycopy(postFix, 0, newPostFix, 1, length);
				newPostFix[0] = result;
				gatherProduction(prefix, newPostFix, gatheredAlternatives, production, stack, depth, cycleMark, positionStore, beginLine);
			}
		}
	}
	
	private void gatherListAlternatives(Link child, DoubleArrayList<IConstructor[], IConstructor> gatheredAlternatives, IConstructor production, IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark, PositionStore positionStore, int beginLine){
		AbstractNode resultNode = child.node;
		
		if(!(resultNode.isEpsilon() && child.prefixes == null)){
			IConstructor result = resultNode.toTerm(stack, depth, cycleMark, positionStore);
			if(result == null) return; // Rejected.
			
			IndexedStack<AbstractNode> listElementStack = new IndexedStack<AbstractNode>();
			
			if(resultNode.isContainer()) listElementStack.push(resultNode, 0);
			gatherList(child, new IConstructor[]{result}, gatheredAlternatives, production, stack, depth, cycleMark, listElementStack, 1, new Stack<AbstractNode>(), positionStore, beginLine);
			if(resultNode.isContainer()) listElementStack.dirtyPurge();
		}else{
			gatheredAlternatives.add(new IConstructor[]{}, production);
		}
	}
	
	private void gatherList(Link child, IConstructor[] postFix, DoubleArrayList<IConstructor[], IConstructor> gatheredAlternatives, IConstructor production, IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark, IndexedStack<AbstractNode> listElementStack, int elementNr, Stack<AbstractNode> blackList, PositionStore positionStore, int beginLine){
		ArrayList<Link> prefixes = child.prefixes;
		if(prefixes == null){
			gatheredAlternatives.add(postFix, production);
			return;
		}
		
		for(int i = prefixes.size() - 1; i >= 0; --i){
			Link prefix = prefixes.get(i);
			
			if(prefix == null){
				gatheredAlternatives.add(postFix, production);
				continue;
			}
			
			AbstractNode prefixNode = prefix.node;
			
			if(!prefixNode.isRejected()){
				if(blackList.contains(prefixNode)) continue;
				
				int index = listElementStack.contains(prefixNode);
				if(index == -1){
					int length = postFix.length;
					IConstructor[] newPostFix = new IConstructor[length + 1];
					System.arraycopy(postFix, 0, newPostFix, 1, length);
					
					if(prefixNode.isContainer()) listElementStack.push(prefixNode, elementNr);
					
					if(input != null) positionStore.setCursorTo(beginLine); // Reduce search time.
					IConstructor result = prefixNode.toTerm(stack, depth, cycleMark, positionStore);
					if(result == null) return; // Rejected.
					
					newPostFix[0] = result;
					gatherList(prefix, newPostFix, gatheredAlternatives, production, stack, depth, cycleMark, listElementStack, elementNr + 1, blackList, positionStore, beginLine);
					
					if(prefixNode.isContainer()) listElementStack.dirtyPurge();
				}else{
					int length = postFix.length;
					int repeatLength = elementNr - index;
					
					IConstructor[] newPostFix = new IConstructor[length - repeatLength + 1];
					System.arraycopy(postFix, repeatLength, newPostFix, 1, length - repeatLength);
					
					IListWriter subList = vf.listWriter(Factory.Tree);
					for(int j = repeatLength - 1; j >= 0; --j){
						subList.insert(postFix[j]);
					}
					
					ISourceLocation positionAnnotation = null;
					if(input != null){
						int endLine = positionStore.findLine(endOffset);
						positionAnnotation = vf.sourceLocation(input, offset, endOffset - offset, beginLine, endLine, positionStore.getColumn(offset, beginLine), positionStore.getColumn(endOffset, endLine));
					}
					
					ISetWriter cycleChildren = vf.setWriter(Factory.Tree);
					IConstructor subListNode = vf.constructor(Factory.Tree_Appl, production, subList.done());
					if(input != null) subListNode = subListNode.setAnnotation(Factory.Location, positionAnnotation);
					cycleChildren.insert(subListNode);
					IConstructor cycleNode = vf.constructor(Factory.Tree_Cycle, ProductionAdapter.getRhs(production), vf.integer(1));
					if(input != null) cycleNode = cycleNode.setAnnotation(Factory.Location, positionAnnotation);
					cycleChildren.insert(cycleNode);
					IConstructor ambSubListNode = vf.constructor(Factory.Tree_Amb, cycleChildren.done());
					newPostFix[0] = ambSubListNode;
					
					blackList.push(prefixNode);
					gatherList(prefix, newPostFix, gatheredAlternatives, production, stack, depth, cycleMark, listElementStack, elementNr + 1, blackList, positionStore, beginLine);
					blackList.pop();
				}
			}
		}
	}
	
	private IConstructor buildAlternative(IConstructor production, IValue[] children){
		IListWriter childrenListWriter = vf.listWriter(Factory.Tree);
		for(int i = children.length - 1; i >= 0; --i){
			childrenListWriter.insert(children[i]);
		}
		
		return vf.constructor(Factory.Tree_Appl, production, childrenListWriter.done());
	}
	
	public IConstructor toTerm(IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark, PositionStore positionStore){
		if(cachedResult != null && (depth <= cycleMark.depth)){
			if(depth == cycleMark.depth){
				cycleMark.reset();
			}
			return cachedResult;
		}
		
		if(rejected) return null;
		
		int beginLine = -1;
		ISourceLocation sourceLocation = null;
		if(input != null){
			beginLine = positionStore.findLine(offset);
			int endLine = positionStore.findLine(endOffset);
			sourceLocation = vf.sourceLocation(input, offset, endOffset - offset, beginLine, endLine, positionStore.getColumn(offset, beginLine), positionStore.getColumn(endOffset, endLine));
		}
		
		int index = stack.contains(this);
		if(index != -1){ // Cycle found.
			IConstructor cycle = vf.constructor(Factory.Tree_Cycle, ProductionAdapter.getRhs(firstProduction), vf.integer(depth - index));
			if(input != null) cycle = cycle.setAnnotation(Factory.Location, sourceLocation);
			
			cycleMark.setMark(index);
			
			return cycle;
		}
		
		int childDepth = depth + 1;
		
		stack.push(this, depth); // Push.
		
		// Gather
		DoubleArrayList<IConstructor[], IConstructor> gatheredAlternatives = new DoubleArrayList<IConstructor[], IConstructor>();
		
		if(!isListContainer){
			gatherAlternatives(firstAlternative, gatheredAlternatives, firstProduction, stack, childDepth, cycleMark, positionStore, beginLine);
			if(alternatives != null){
				for(int i = alternatives.size() - 1; i >= 0; --i){
					gatherAlternatives(alternatives.get(i), gatheredAlternatives, productions.get(i), stack, childDepth, cycleMark, positionStore, beginLine);
				}
			}
		}else{
			gatherListAlternatives(firstAlternative, gatheredAlternatives, firstProduction, stack, childDepth, cycleMark, positionStore, beginLine);
			if(alternatives != null){
				for(int i = alternatives.size() - 1; i >= 0; --i){
					gatherListAlternatives(alternatives.get(i), gatheredAlternatives, productions.get(i), stack, childDepth, cycleMark, positionStore, beginLine);
				}
			}
		}
		
		// Output.
		IConstructor result;
		
		int nrOfAlternatives = gatheredAlternatives.size();
		if(nrOfAlternatives == 1){ // Not ambiguous.
			IConstructor production = gatheredAlternatives.getSecond(0);
			IValue[] alternative = gatheredAlternatives.getFirst(0);
			result = buildAlternative(production, alternative);
			if(input != null) result = result.setAnnotation(Factory.Location, sourceLocation);
		}else if(nrOfAlternatives == 0){ // Filtered.
			result = null;
		}else{ // Ambiguous.
			ISetWriter ambSetWriter = vf.setWriter(Factory.Tree);
			
			for(int i = nrOfAlternatives - 1; i >= 0; --i){
				IConstructor production = gatheredAlternatives.getSecond(i);
				IValue[] alternative = gatheredAlternatives.getFirst(i);
				
				IConstructor alt = buildAlternative(production, alternative);
				if(input != null) alt = alt.setAnnotation(Factory.Location, sourceLocation);
				ambSetWriter.insert(alt);
			}
			
			result = vf.constructor(Factory.Tree_Amb, ambSetWriter.done());
		}
		
		stack.dirtyPurge(); // Pop.
		
		return (depth <= cycleMark.depth) ? (cachedResult = result) : result;
	}
}
