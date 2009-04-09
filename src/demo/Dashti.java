package demo;

import java.util.Iterator;

import org.eclipse.imp.pdb.facts.IInteger;
import org.eclipse.imp.pdb.facts.impl.fast.ValueFactory;
import org.eclipse.imp.pdb.facts.impl.util.collections.ShareableValuesList;
import org.eclipse.imp.pdb.facts.util.ShareableHashSet;

// NOTE: This thing may be incorrect.
public class Dashti{
	private final static ValueFactory vf = ValueFactory.getInstance();

	private final IInteger[] integers;
	private final int n;

	private ShareableHashSet<ShareableValuesList> permutations;
	
	public Dashti(int n){
		super();

		this.n = n;
		
		permutations = new ShareableHashSet<ShareableValuesList>();
		
		integers = new IInteger[n];
		for(int i = n - 1; i >= 0; i--){
			integers[i] = vf.integer(i);
		}
	}
	
	private ShareableHashSet<ShareableValuesList> cutNumber(ShareableHashSet<ShareableValuesList> permutations, int i){
		System.out.print(i+" ");
		
		ShareableHashSet<ShareableValuesList> newPermutations = new ShareableHashSet<ShareableValuesList>();
		
		Iterator<ShareableValuesList> permutationsIterator = permutations.iterator();
		while(permutationsIterator.hasNext()){
			ShareableValuesList permutation = permutationsIterator.next();
			
			IInteger integer = integers[i];
			if(permutation.get(0).isEqual(integer)){
				permutation.remove(0);
			}
			if(!permutation.isEmpty()) newPermutations.add(permutation);
		}
		
		return newPermutations;
	}

	public void generatePermutations(){
		PermutationGenerator pg = new PermutationGenerator(n);
		for(int j = pg.getTotal() - 1; j >= 0; j--){
			int[] next = pg.getNext();
			
			ShareableValuesList permutation = new ShareableValuesList();
			for(int i = n - 1; i >= 0; i--){
				permutation.insert(vf.integer(next[i]));
			}
			
			permutations.add(permutation);
		}
	}
	
	public void solve(){
		int iterations = 0;
		
		for(int i = 0; i < n; i++){
			permutations = cutNumber(permutations, i);
			iterations++;
		}
		permutations = cutNumber(permutations, 0);
		
		
		int shift = n - 1;
		
		OUTER: do{
			for(int i = 1; i < shift; i++){
				permutations = cutNumber(permutations, i);
				iterations++;
				if(permutations.isEmpty()) break OUTER;
			}
			
			permutations = cutNumber(permutations, 0);
			iterations++;
			if(permutations.isEmpty()) break OUTER;
			
			for(int i = shift; i < n; i++){
				permutations = cutNumber(permutations, i);
				iterations++;
				if(permutations.isEmpty()) break OUTER;
			}
			
			shift--;
		}while(true);
		
		System.out.println();
		System.out.println("Solved in "+iterations+" iterations, for: "+n);
	}

	public static void main(String[] args){
		int n = Integer.parseInt(args[0]);

		Dashti dashti = new Dashti(n);
		long start = System.currentTimeMillis();
		dashti.generatePermutations();
		long generationDone = System.currentTimeMillis();
		dashti.solve();
		long solvingDone = System.currentTimeMillis();
		
		System.out.println("Generation: "+(generationDone - start)+"ms");
		System.out.println("Solving: "+(solvingDone - generationDone)+"ms");
		System.out.println("------------------");
		System.out.println("Total: "+(solvingDone - start)+"ms");
	}
}

class PermutationGenerator{
	private final int[] permutation;
	private final int total;
	private int left;

	public PermutationGenerator(int n){
		super();
		
		permutation = new int[n];
		total = calcTotal(n);
		
		for(int i = 0; i < permutation.length; i++){
			permutation[i] = i;
		}
		left = total;
	}

	private static int calcTotal(int n){
		int fact = 1;
		for(int i = n; i > 1; i--){
			fact *= i;
		}
		return fact;
	}

	public int getTotal(){
		return total;
	}

	public int[] getNext(){
		if (left == total){
			left--;
			return permutation;
		}

		int j = permutation.length - 2;
		while(permutation[j] > permutation[j + 1]){
			j--;
		}

		int k = permutation.length;
		while(permutation[j] > permutation[--k]){}

		int temp = permutation[k];
		permutation[k] = permutation[j];
		permutation[j] = temp;

		int r = permutation.length - 1;
		int s = j + 1;

		while(r > s){
			temp = permutation[s];
			permutation[s++] = permutation[r];
			permutation[r--] = temp;
		}

		left--;
		
		return permutation;
	}
}
