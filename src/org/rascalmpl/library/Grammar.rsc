@license{
  Copyright (c) 2009-2011 CWI
  All rights reserved. This program and the accompanying materials
  are made available under the terms of the Eclipse Public License v1.0
  which accompanies this distribution, and is available at
  http://www.eclipse.org/legal/epl-v10.html
}
@contributor{Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI}
@contributor{Arnold Lankamp - Arnold.Lankamp@cwi.nl - CWI}
@contributor{Vadim Zaytsev - Vadim.Zaytsev@cwi.nl - CWI}
@doc{
  This modules defines an simple but effective internal format for the representation of context-free grammars.
}
module Grammar

import ParseTree;
import Set;
import IO;

@doc{
  Grammar is the internal representation (AST) of syntax definitions used in Rascal.
  A grammar is a set of productions and set of start symbols. The productions are 
  stored in a map for efficient access.
}
data Grammar 
  = \grammar(set[Symbol] starts, map[Symbol sort, Production def] rules)
  ;

data GrammarModule
  = \module(str name, set[str] imports, set[str] extends, Grammar grammar);
 
data GrammarDefinition
  = \definition(str main, map[str name, GrammarModule mod] modules);

anno loc Production@\loc;
 
public Grammar grammar(set[Symbol] starts, set[Production] prods) {
  rules = ();
  for (p <- prods)
    rules[p.rhs] = p.rhs in rules ? choice(p.rhs, {p, rules[p.rhs]}) : choice(p.rhs, {p}); 
  return grammar(starts, rules);
} 
           
@doc{
Here we extend productions with basic combinators allowing to
construct ordered and un-ordered compositions, and associativity groups.

The intended semantics are that 
 	'choice' means unordered choice,
 	'priority'  means ordered choice, where alternatives are tried from left to right,
    'assoc'  means all alternatives are acceptible, but nested on the declared side
    'others' means '...', which is substituted for a choice among the other definitions
    'reference' means a reference to another production rule which should be substituted there,
                for extending priority chains and such.
} 
data Production 
  = \choice(Symbol rhs, set[Production] alternatives)
  | \priority(Symbol rhs, list[Production] choices)
  | \associativity(Symbol rhs, Associativity \assoc, set[Production] alternatives)
  | \others(Symbol rhs)
  | \reference(Symbol rhs, str cons)
  ;

@doc{
  These combinators are defined on Symbol, but it is checked (elsewhere) that only char-classes are passed in.
}
data Symbol 
  = intersection(Symbol lhs, Symbol rhs)
  | union(Symbol lhs, Symbol rhs)
  | difference(Symbol lhs, Symbol rhs)
  | complement(Symbol cc)
  ;
  
@doc{
  An item is an index into the symbol list of a production rule
}  
data Item = item(Production production, int index);

// The following normalization rules canonicalize grammars to prevent arbitrary case distinctions later

@doc{Nested choice is flattened}
public Production choice(Symbol s, {set[Production] a, choice(Symbol t, set[Production] b)})
  = choice(s, a+b);
  
@doc{Nested priority is flattened}
public Production priority(Symbol s, [list[Production] a, priority(Symbol t, list[Production] b),list[Production] c])
  = priority(s,a+b+c);
   
@doc{Choice under associativity is flattened}
public Production associativity(Symbol s, Associativity as, {set[Production] a, choice(Symbol t, set[Production] b)}) 
  = associativity(s, as, a+b); 
  
@doc{Nested (equal) associativity is flattened}             
public Production associativity(Symbol rhs, Associativity a, {associativity(Symbol rhs2, Associativity b, set[Production] alts), set[Production] rest}) {
  if (a == b)  
    return associativity(rhs, a, rest + alts) ;
  else
    fail;
}

public Production associativity(Symbol rhs, Associativity a, {prod(list[Symbol] lhs,Symbol rhs,\no-attrs()), set[Production] rest})  
  = \associativity(rhs, a, rest + {prod(lhs, rhs, attrs([\assoc(a)]))});

public Production associativity(Symbol rhs, Associativity a, {prod(list[Symbol] lhs,Symbol rhs,attrs(list[Attr] as)), set[Production] rest}) {
 if (\assoc(_) <- as) 
   fail;
 return \associativity(rhs, a, rest + {prod(lhs, rhs, attrs(as + [\assoc(a)]))});
}

@doc{Priority under an associativity group defaults to choice}
public Production associativity(Symbol s, Associativity as, {set[Production] a, priority(Symbol t, list[Production] b)}) 
  = associativity(s, as, a + { e | e <- b}); 

@doc{Empty attrs default to \no-attrs()}
public Attributes  attrs([]) 
  = \no-attrs();
