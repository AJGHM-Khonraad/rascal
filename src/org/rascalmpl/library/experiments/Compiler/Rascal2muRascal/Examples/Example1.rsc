module Example1
//module experiments::Compiler::Rascal2muRascal::Examples::Example1

//int x = 1;
//int z =  x + 1;
//int q = 3;
// int inc(int n) = n + 1;

// int fac(int n) = (n <= 1) ? 1 : n * fac(n - 1);

//int f(int n) = n;
//int g(int n) = 2 * n;

value main(list[value] args) { 
//	return inc(3);
//    int n = 57;
//	return n + 2;
 //   n = 0;
//    res = 0;
//  return   while(n < 10){ res = res + n; n = n + 1; }
//	return if(1 == 2, 3 == 3){res = 20;} else {res = 30;}
    res = [];
    for([*int x, 2] := [1, 2]){ res = res + [x]; }
    return res;
}