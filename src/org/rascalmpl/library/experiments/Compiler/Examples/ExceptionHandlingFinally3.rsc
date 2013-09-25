module experiments::Compiler::Examples::ExceptionHandlingFinally3

value f() { throw "Try to catch me!"; }

value main(list[value] args) {
	
	str n = "0";
	
	try { // 'Try-catch-finally'
		
		n = n + " 1";
		
		try { // 'Try-catch'
			
			n = n + " 2";
			// Inline in a 'try' block
			return n + " has been returned!";
			
		} catch 0: {		
			n = n + " 4";
		} catch int i: {
			n = n + " 5";
		}		
		n = n + " 7";
		
	} catch "0": {	
		n = n + " 8";
	} catch str s: {
		n = n + " 9";
	} 
	finally {
		n = n + " 10";
		// Inline in a 'finally' block
		return n + " has been returned from the outer finally!";
	}
	
	return n;
	
}