@license{
  Copyright (c) 2009-2011 CWI
  All rights reserved. This program and the accompanying materials
  are made available under the terms of the Eclipse Public License v1.0
  which accompanies this distribution, and is available at
  http://www.eclipse.org/legal/epl-v10.html
}
@contributor{Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI}
@contributor{Bert Lisser - Bert.Lisser@cwi.nl (CWI)}
@contributor{Arnold Lankamp - Arnold.Lankamp@cwi.nl}
module Real

@doc{Returns an arbitrary real value in the interval [0.0,1.0).}
@javaClass{org.rascalmpl.library.Real}
public java real arbReal();

@doc{Largest of two reals}
public real max(real n, real m)
{
	return n > m ? n : m;
}

@doc{Smallest of two reals}
public real min(real n, real m)
{
	return n < m ? n : m;
}

@doc{Convert a real to integer.}
@javaClass{org.rascalmpl.library.Real}
public java int toInt(real d);

@doc{Convert a real to a string.}
@javaClass{org.rascalmpl.library.Real}
public java str toString(real d);

@doc{Round to the nearest integer}
@javaClass{org.rascalmpl.library.Real}
public java real round(real d);

@doc{Returns the constant PI}
@javaClass{org.rascalmpl.library.Real}
public java real PI();

@doc{Returns the constant E}
@javaClass{org.rascalmpl.library.Real}
public java real E();

@doc{Computes the power of x by y}
@javaClass{org.rascalmpl.library.Real}
public java real pow(real x, real y);

@doc{Computes exp(x)}
@javaClass{org.rascalmpl.library.Real}
public java real exp(real x);

@doc{Computes sin(x)}
@javaClass{org.rascalmpl.library.Real}
public java real sin(real x);

@doc{Computes cos(x)}
@javaClass{org.rascalmpl.library.Real}
public java real cos(real x);

@doc{Computes tan(x)}
@javaClass{org.rascalmpl.library.Real}
public java real tan(real x);

@doc{Computes sqrt(x)}
@javaClass{org.rascalmpl.library.Real}
public java real sqrt(real x);

@doc{Computes the natural log(x)}
@javaClass{org.rascalmpl.library.Real}
public java real log(real x);

@doc{Computes the 10 based log(x)}
@javaClass{org.rascalmpl.library.Real}
public java real log10(real x);
