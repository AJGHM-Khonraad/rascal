@doc{
Synopsis: a symbolic representation for types that occur in programming languages.

Description:

M3 provides a general mechanism to associate types, symbolically, with source code artifacts or even run-time artifacts.

The `TypeSymbol` type is a general concept which needs to be extended for specific programming languages. One language will 
class and interface definitions which coincide with types, another may have higher order function types etc.

We cater for languages to have a subtype relation to be defined, and a least upper bound computation. 

You will find more interesting information in the M3 module for Java.

Benefits:

* symbolic types can be analyzed and manipulated symbolically, i.e. to instatiate parametrized types.
* symbolic types can be used directly as constraint variables.

Pitfalls:

* If you import extensions to this M3 model for two different languages, ambiguity and other confusion may arise 
because the subtype and lub functions of the two languages will merge.
}
module analysis::m3::TypeSymbol

data TypeSymbol = \any();

bool subtype(\any(), \any()) = true; 
bool lub(\any(), \any()) = \any();
