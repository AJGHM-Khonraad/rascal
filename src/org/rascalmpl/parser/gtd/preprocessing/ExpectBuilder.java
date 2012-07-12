package org.rascalmpl.parser.gtd.preprocessing;

import java.util.Iterator;

import org.rascalmpl.parser.gtd.stack.AbstractStackNode;
import org.rascalmpl.parser.gtd.util.DoubleArrayList;
import org.rascalmpl.parser.gtd.util.IntegerMap;
import org.rascalmpl.parser.gtd.util.ObjectIntegerKeyedHashMap;
import org.rascalmpl.parser.gtd.util.SortedIntegerObjectList;

/**
 * A preprocessor for building expect matrixes.
 * This preprocessor incorporates prefix-sharing into related alternatives,
 * where possible. It also intialized the 'static' versions of the stack nodes.
 */
public class ExpectBuilder<T>{
	private final IntegerMap resultStoreMappings;
	
	private final SortedIntegerObjectList<DoubleArrayList<T, AbstractStackNode<T>[]>> alternatives;
	
	public ExpectBuilder(IntegerMap resultStoreMappings){
		super();
		
		this.resultStoreMappings = resultStoreMappings;
		
		alternatives = new SortedIntegerObjectList<DoubleArrayList<T, AbstractStackNode<T>[]>>();
	}
	
	/**
	 * Registers the given alternative with this builder.
	 */
	@SuppressWarnings("unchecked")
	public void addAlternative(T production, AbstractStackNode<T>... alternative){
		int alternativeLength = alternative.length;
		DoubleArrayList<T, AbstractStackNode<T>[]> alternativesList = alternatives.findValue(alternativeLength);
		if(alternativesList == null){
			alternativesList = new DoubleArrayList<T, AbstractStackNode<T>[]>();
			alternatives.add(alternativeLength, alternativesList);
		}
		
		// Clone the alternative so we don't get entangled in 'by-reference' related issues.
		AbstractStackNode<T>[] clonedAlternative = (AbstractStackNode<T>[]) new AbstractStackNode[alternativeLength];
		for(int i = alternativeLength - 1; i >= 0; --i){
			clonedAlternative[i] = alternative[i].getCleanCopy(AbstractStackNode.DEFAULT_START_LOCATION);
		}
		
		alternativesList.add(production, clonedAlternative);
	}
	
	/**
	 * Constructs and initializes the expect array and calculates
	 * prefix-sharing.
	 */
	@SuppressWarnings("unchecked")
	public AbstractStackNode<T>[] buildExpectArray(){
		ObjectIntegerKeyedHashMap<AbstractStackNode<T>, AbstractStackNode<T>[]> constructedExpects = new ObjectIntegerKeyedHashMap<AbstractStackNode<T>, AbstractStackNode<T>[]>();
		
		for(int i = alternatives.size() - 1; i >= 0; --i){ // Walk over the list of alternatives, starting at the longest ones (this reduces the complexity of the sharing calculation).
			DoubleArrayList<T, AbstractStackNode<T>[]> alternativesList = alternatives.getValue(i);
			
			for(int j = alternativesList.size() - 1; j >= 0; --j){
				T production = alternativesList.getFirst(j);
				AbstractStackNode<T>[] alternative = alternativesList.getSecond(j);
				
				AbstractStackNode<T> first = alternative[0];
				int firstItemResultStoreId = resultStoreMappings.get(first.getId());
				
				// Check if the first symbol in the alternative, with the same nesting restrictions, has been encountered before in another alternative.
				AbstractStackNode<T>[] sharedExpect = constructedExpects.get(first, firstItemResultStoreId);
				if(sharedExpect == null){ // Not shared.
					// Initialize and register.
					alternative[alternative.length - 1].setProduction(alternative);
					alternative[alternative.length - 1].setAlternativeProduction(production);
					
					for(int k = alternative.length - 2; k >= 0; --k){
						alternative[k].setProduction(alternative);
					}
					
					constructedExpects.putUnsafe(first, firstItemResultStoreId, alternative);
				}else{ // Shared.
					// Find the alternative with which the maximal amount of sharing is possible.
					int k = 1;
					CHAIN: for(; k < alternative.length; ++k){
						AbstractStackNode<T> alternativeItem = alternative[k];
						int alternativeItemResultStoreId = resultStoreMappings.get(alternativeItem.getId());
						
						AbstractStackNode<T> sharedExpectItem = sharedExpect[k];
						
						// Can't share the current alternative's symbol with the shared alternative we are currently matching against; try all other possible continuations to find a potential match.
						if(!alternativeItem.isEqual(sharedExpectItem) || alternativeItemResultStoreId != resultStoreMappings.get(sharedExpectItem.getId())){
							AbstractStackNode<T>[][] otherSharedExpects = sharedExpectItem.getAlternateProductions();
							if(otherSharedExpects != null){
								for(int l = otherSharedExpects.length - 1; l >= 0; --l){
									AbstractStackNode<T>[] otherSharedExpect = otherSharedExpects[l];
									AbstractStackNode<T> otherSharedExpectItem = otherSharedExpect[k];
									if(otherSharedExpectItem.isEqual(alternativeItem) && alternativeItemResultStoreId == resultStoreMappings.get(otherSharedExpectItem.getId())){
										sharedExpect = otherSharedExpect;
										continue CHAIN; // Found a alternative continuation that matched.
									}
								}
							}
							
							break; // Did not find a alternative continuation that matched.
						}
						
						alternative[k] = sharedExpect[k]; // Remove 'garbage'.
					}
					
					if(k < alternative.length){
						sharedExpect[k - 1].addProduction(alternative); // Add the current alternative as alternative continuation at the latest point at which it matched the shared alternative.
						
						 // Initialize the tail of the current alternative.
						for(; k < alternative.length; ++k){
							alternative[k].setProduction(alternative);
						}
						alternative[alternative.length - 1].setAlternativeProduction(production);
					}else{
						sharedExpect[alternative.length - 1].setAlternativeProduction(production); // The current alternative was shorter them the alternative we are sharing with, so mark the appropriate symbol as end node (end nodes can still have next nodes).
					}
				}
			}
		}
		
		// Build the expect array. This array contains all unique 'first' nodes of the registered alternatives.
		int nrOfConstructedExpects = constructedExpects.size();
		AbstractStackNode<T>[] expectArray = (AbstractStackNode<T>[]) new AbstractStackNode[nrOfConstructedExpects];
		Iterator<AbstractStackNode<T>[]> constructedExpectsIterator = constructedExpects.valueIterator();
		int i = nrOfConstructedExpects;
		while(constructedExpectsIterator.hasNext()){
			expectArray[--i] = constructedExpectsIterator.next()[0];
		}
		
		return expectArray;
	}
}
