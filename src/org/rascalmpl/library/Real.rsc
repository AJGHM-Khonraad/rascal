/*****************************/
/* DEPRECATED                */
/* Use util::Math       */
/* DO NOT EDIT               */
/*****************************/

@license{
  Copyright (c) 2009-2013 CWI
  All rights reserved. This program and the accompanying materials
  are made available under the terms of the Eclipse Public License v1.0
  which accompanies this distribution, and is available at
  http://www.eclipse.org/legal/epl-v10.html
}
@contributor{Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI}
@contributor{Bert Lisser - Bert.Lisser@cwi.nl (CWI)}
@contributor{Arnold Lankamp - Arnold.Lankamp@cwi.nl}

@deprecated{Use "import util::Math;" instead.}
module Real

@doc{Returns an arbitrary real value in the interval [0.0,1.0).}
@javaClass{org.rascalmpl.library.util.Math}
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
@javaClass{org.rascalmpl.library.util.Math}
public java int toInt(num d);

@doc{Convert a real to a string.}
@javaClass{org.rascalmpl.library.util.Math}
public java str toString(num d);

@doc{Round to the nearest integer}
@javaClass{org.rascalmpl.library.util.Math}
public java int round(num d);

@doc{Returns the constant PI}
@javaClass{org.rascalmpl.library.util.Math}
public java real PI();

@doc{Returns the constant E}
@javaClass{org.rascalmpl.library.util.Math}
public java real E();

@doc{Computes the power of x by y}
@javaClass{org.rascalmpl.library.util.Math}
public java real pow(num x, int y);

@doc{Computes exp(x)}
@javaClass{org.rascalmpl.library.util.Math}
public java real exp(num x);

@doc{Computes sin(x)}
@javaClass{org.rascalmpl.library.util.Math}
public java real sin(num x);

@doc{Computes cos(x)}
@javaClass{org.rascalmpl.library.util.Math}
public java real cos(num x);

@doc{Computes tan(x)}
@javaClass{org.rascalmpl.library.util.Math}
public java real tan(num x);

@doc{Computes sqrt(x)}
@javaClass{org.rascalmpl.library.util.Math}
public java real sqrt(num x);

@doc{Computes the n-th root of x}
@javaClass{org.rascalmpl.library.util.Math}
public java real nroot(num x, int n);

@doc{Computes the natural ln(x)}
@javaClass{org.rascalmpl.library.util.Math}
public java real ln(num x);

@doc{Computes the log_base(x)}
@javaClass{org.rascalmpl.library.util.Math}
public java real log(num x, num base);

@doc{Computes the 10 based log(x)}
public real log10(num x) = log(x, 10.0);

@doc{Computes the 2 based log(x)}
public real log2(num x) = log(x, 2.0);

