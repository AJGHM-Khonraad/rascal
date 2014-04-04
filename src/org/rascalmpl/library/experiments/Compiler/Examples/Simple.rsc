module experiments::Compiler::Examples::Simple
//
int pad() {
	return 3 ;
}
//int fud(int vo) {
//	return vo * 2  ;
//}
//
//int ocallStress(int j) {
//	if ( j == 1 ) return 1;
//	return 1 + ocallStress(j-1) ;
//}
//
int fac(int n) = (n <= 1) ? 1 : n * fac(n-1);
//int fib(int n) = (n == 0) ? 0 : (n == 1) ? 1 : (fib(n-1) + fib(n-2));

//value main(list[value] args){
//   res = [];
//   for(i <- [0,1,2,3,4,5,6,7,8,9,10,11,12,13], i % 2 == 1)
//      res = res + [i];
//    return res;
//}

//public set[list[int]] sendMoreMoney(){
//   ds = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
//
//   res = {[S,E,N,D,M,O,R,Y] | 
//   		   int S <- ds, 
//   		   int E <- ds - {S}, 
//   		   int N <- ds - {S, E},
//   		   int D <- ds - {S, E, N},
//   		   int M <- ds - {S, E, N, D},
//   		   int O <- ds - {S, E, N, D, M},
//   		   int R <- ds - {S, E, N, D, M, O},
//   		   int Y <- ds - {S, E, N, D, M, O, R},
//   		   S != 0, M != 0,
//   		               (S * 1000 + E * 100 + N * 10 + D) +
//   		               (M * 1000 + O * 100 + R * 10 + E) ==
//   		   (M * 10000 + O * 1000 + N * 100 + E * 10 + Y)};
//    return res;
//}
//
//value main(list[value] args)  = sendMoreMoney();

//
//value main(list[value] args){
//   res = [];
//   for(i <- [0..30], i % 2 != 0 , i % 3 != 0, i % 5 != 0  )
//      res = res + [i];
//    return res;
//}

//value main(list[value] args){
//   res = 0;
//   for(i <- [1,2,3,4,5], i % 2 == 1)
//      res = res + i;
//    return res;
//}
//
//value ocallmain(list[value] args){
// 	return ocallStress(10) ; // Kills the jvm version with a stackoverflow.
//}
//
value main(list[value] args){
 	int j = 0 ;
 	j = fac(8) ;
 	int p = pad() ;
 	while ( j > 0 ) {
 		p = (j * p) +  (j - p) / j ;  
 		j =  j - 1 ;
 	}
	return p ;
}
//
//value main(list[value] args){	
// 	return fib(25) ;
//}
//value main(list[value] args){	
// 	return fac(24) ;
//}