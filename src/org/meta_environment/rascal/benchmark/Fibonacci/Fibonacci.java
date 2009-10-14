package org.meta_environment.rascal.benchmark.Fibonacci;

/**
 * NOTE: You may not be testing what you thing you are testing (HotSpot may mess the test up).
 */
public class Fibonacci {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n = 20;
		int iterations = 100000;
		
		// Warmup
		for(int i = 0; i < 20000; i++){
			fib(n);
		}
		
		// Test
		long start = System.currentTimeMillis();
		for(int i = 0; i < iterations; i++){
			fib(n);
		}
		long used = System.currentTimeMillis() - start;
		
		System.err.println(iterations+"x fib(" + n + ") = " + fib(n) + " (" + used + " millis)");
	}
	
	public static int fib(int n)
	{
	   if(n == 0)
	   		return 0;
	   if(n == 1)
	    	return 1;
	   return fib(n - 1) + fib(n - 2);
	}

}
