package org.rascalmpl.library.vis.util;

import java.util.Arrays;

public class Util {
	
	public static <T> void makeRectangular(T[][] source, T pad){
		int nrRows = source.length;
		int nrColumns = 0;
		for(T[] row : source){
			nrColumns = Math.max(nrColumns, row.length);
		}
		for(int row = 0 ; row < nrRows ; row++){
			int oldLength = source[row].length;
			if(oldLength == nrColumns) continue;
			T[] newRow = Arrays.copyOf(source[row], nrColumns);
			Arrays.fill(newRow, oldLength, nrColumns,pad);
			source[row]=newRow;
		}
	}
	
	
	/*
	 * Given n borders of interval returns the index of the interval in which the value lies
	 * -1 means (-infinity, intervalBorders[0]);
	 * 0 means  (intervalBorders[0],intervalBorders[1])
	 * ...
	 * intervalBorders.length - 1 means (intervalBorders[intervalBorders.length -1], infinity) 
	 */
	public static int binaryIntervalSearch(double[] intervalBorders, double toFind){
		int minIndex = -1; // lowest index of an intervalBorder <= toFind
		int maxIndex = intervalBorders.length ;// highest index of an intervalBorder > toFind
		while(maxIndex - minIndex > 1){
			int mid = ( maxIndex + minIndex ) / 2;
			if(toFind <= intervalBorders[mid]) maxIndex = mid;
			else minIndex = mid; 
		}
		return minIndex;
	}
	
	public static boolean any(boolean[] vals){
		for(boolean b : vals) { if(b) return true; }
		return false;
	}

	public static double sum(double[] vals){
		double result = 0.0;
		for(double b : vals) { result+=b; }
		return result;
	}
	
	public static int count(double[] vals,double toCount){
		int result = 0;
		for(double b : vals) { if(b == toCount) result++; }
		return result;
	}
	
	public static void mapMul(double[] vals,double factor){
		for(int i = 0 ; i  < vals.length ; i++){
			vals[i]*=factor;
		}
	}
	
	public static void replaceVal(double[] vals, double from, double to){
		for(int i = 0 ; i  < vals.length ; i++){
			if(vals[i]==from){
				vals[i] = to;
			}
		}
	}
	
	public static class QuadraticRoots{
		public int nrRoots;
		public double firstRoot, secondRoot;
		QuadraticRoots(){
			nrRoots = 0;
		}
		QuadraticRoots(double root){
			nrRoots = 1;
			firstRoot = root;
		}
		QuadraticRoots(double firstRoot,double secondRoot){
			nrRoots = 2;
			this.firstRoot = firstRoot;
			this.secondRoot = secondRoot;
		}
	}
	
	static public QuadraticRoots getQuadraticRoots(double a, double b, double c){
		double discriminant = b*b - 4 * a * c;
		if(discriminant < 0){
			return new QuadraticRoots();
		} else if(discriminant == 0){
			return new QuadraticRoots(-b / (2*a));
		} else {
			return new QuadraticRoots(
					(-b + Math.sqrt(discriminant))/(2*a),
					(-b - Math.sqrt(discriminant))/(2*a));
		}	
	}
}
