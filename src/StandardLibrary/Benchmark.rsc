module Benchmark

import IO;

public real java currentTimeMillis()
@doc{currentTimeMillis -- current time in milliseconds since January 1, 1970 GMT.}
@javaImport{import java.lang.System;}
{
	double ctm = System.currentTimeMillis();
	return values.dubble(ctm);
}

public void benchmark(map[str, void()] Cases)
@doc{benchmark -- measure and report the execution time of name:void-closure pairs}
{
	measurements = ();
	for(str Name : Cases){
		real ctm1 = currentTimeMillis();
		Fun = Cases[Name];
		#Fun();
		real ctm2 = currentTimeMillis();
		measurements[Name] = ctm2 - ctm1;
	}
	
	println(measurements);
	
}

