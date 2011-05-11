package org.rascalmpl.parser.gtd.stack.filter;

public class CharMatchRestriction implements ICompletionFilter{
	private final char[][] ranges;
	
	public CharMatchRestriction(char[][] ranges){
		super();
		
		this.ranges = ranges;
	}
	
	public boolean isFiltered(char[] input, int start, int end){
		if((end - start) != 1) return false;
		
		char character = input[start];
		for(int i = ranges.length - 1; i >= 0; --i){
			char[] range = ranges[i];
			if(character >= range[0] && character <= range[1]){
				return true;
			}
		}
		
		return true;
	}
}
