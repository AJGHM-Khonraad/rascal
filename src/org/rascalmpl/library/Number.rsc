module Number

import Real;

@doc{Absolute value of integer.}
public &T <: num abs(&T <: num N)
{
	return N >= 0 ? N : -N;
}

@doc{Return an arbitrary integer value.}
@javaClass{org.rascalmpl.library.Number}
public int java arbInt();

@doc{Return an arbitrary integer value in the interval [0, limit).}
@javaClass{org.rascalmpl.library.Integer}
public int java arbInt(int limit);

@doc{Returns an arbitrary real value in the interval [0.0,1.0).}
@javaClass{org.rascalmpl.library.Real}
public real java arbReal();

@doc{Round to the nearest integer}
public num round(num d) {
    return toInt(round(toReal(d)));
    }

@doc{Largest of two numbers.}
public &T <: num max(&T <: num N, &T <: num M)
{
	return N > M ? N : M;
}

@doc{Smallest of two numbers.}
public &T <: num min(&T <: num N, &T <: num M)
{
	return N < M ? N : M;
}

@doc{Convert a number to an integer.}
@javaClass{org.rascalmpl.library.Number}
public int java toInt(num N);

@doc{Convert a number value to a real value.}
@javaClass{org.rascalmpl.library.Number}
public real java toReal(num N);

@doc{Convert a number value to a string.}
@javaClass{org.rascalmpl.library.Number}
public str java toString(num N);

@doc{pi -- returns the constant PI}
@javaClass{org.rascalmpl.library.Real}
public real java PI();

@doc{e -- returns the constant E}
@javaClass{org.rascalmpl.library.Real}
public real java E();

@doc{computes the power of x by y}
public real pow(num x, num y) {
    return Real::pow(toReal(x), toReal(y));
    }


@doc{computes exp(x)}
public real exp(num x) {
    return Real::exp(toReal(x));
    }

@doc{computes sin(x)}
public real sin(num x) {
    return Real::sin(toReal(x));
    }

@doc{computes cos(x)}
public real cos(num x) {
    return Real::cos(toReal(x));
    }

@doc{computes tan(x)}
public real tan(num x) {
    return Real::tan(toReal(x));
    }

@doc{computes sqrt(x)}
public real sqrt(num x) {
    return Real::sqrt(toReal(x));
    }

