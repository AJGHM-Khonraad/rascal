module experiments::Compiler::Examples::Simple

int pad() {
	return 3 ;
}
int fud(int vo) {
	return vo * 2  ;
}

int ocallStress(int j) {
	if ( j == 1 ) return 1;
	return 1 + ocallStress(j-1) ;
}

int fac(int n) = (n <= 1) ? 1 : n * fac(n-1);
int fib(int n) = (n == 0) ? 0 : (n == 1) ? 1 : (fib(n-1) + fib(n-2));

value main(list[value] args){
   res = 0;
   for(i <- [444,555])
      res = res + i;
    return res;
}

value nonomain(list[value] args){
 	return ocallStress(10) ; // Kills the jvm version with a stackoverflow.
}

value nononomain(list[value] args){
 	int j = 0 ;
 	j = fac(8) ;
 	int p = pad() ;
 	while ( j > 0 ) {
 		p = (j * p) +  (j - p) / j ;  
 		j =  j - 1 ;
 	}
	return p ;
}

value pppppmain(list[value] args){	
 	return fib(25) ;
}