@license{
  Copyright (c) 2009-2013 CWI
  All rights reserved. This program and the accompanying materials
  are made available under the terms of the Eclipse Public License v1.0
  which accompanies this distribution, and is available at
  http://www.eclipse.org/legal/epl-v10.html
}
@contributor{Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI}
@contributor{Arnold Lankamp - Arnold.Lankamp@cwi.nl}
@bootstrapParser
module lang::rascal::grammar::definition::Productions
     
import lang::rascal::newsyntax::Rascal;
import lang::rascal::grammar::definition::Characters;
import lang::rascal::grammar::definition::Symbols;
import lang::rascal::grammar::definition::Attributes;
import lang::rascal::grammar::definition::Names;

import Grammar;
import List; 
import String;    
import ParseTree;
import IO;  
import util::Math;


// conversion functions

public Grammar syntax2grammar(set[SyntaxDefinition] defs) {
  set[Production] prods = {prod(empty(),[],{}), prod(layouts("$default$"),[],{})};
  set[Symbol] starts = {};
  
  for (sd <- defs) {
    switch (sd) {
      case \layout(_, nonterminal(Nonterminal n), Prod p) : {
        prods += prod2prod(\layouts("<n>"), p);
      }
      case \language(present() /*start*/, nonterminal(Nonterminal n), Prod p) : {
        prods += prod(\start(sort("<n>")),[label("top", sort("<n>"))],{}); 
        prods += prod2prod(sort("<n>"), p);
        starts += \start(sort("<n>"));
      }
      case \language(absent(), parameterized(Nonterminal l, {Sym ","}+ syms), Prod p) : {
        prods += prod2prod(\parameterized-sort("<n>",separgs2symbols(syms)), p);
      }
      case \language(absent(), nonterminal(Nonterminal n), Prod p) : {
        prods += prod2prod(\sort("<n>"), p);
      }
      case \lexical(absent(), parameterized(Nonterminal l, {Sym ","}+ syms), Prod p) : {
        prods += prod2prod(\parameterized-lex("<n>",separgs2symbols(syms)), p);
      }
      case \lexical(absent(), nonterminal(Nonterminal n), Prod p) : {
        prods += prod2prod(\lex("<n>"), p);
      }
      case \keyword(absent(), nonterminal(Nonterminal n), Prod p) : {
        prods += prod2prod(keywords("<n>"), p);
      }
      default: { rprintln(sd); throw "unsupported kind of syntax definition? <sd> at <sd@\loc>"; }
    }
  }

  return grammar(starts, prods);
} 
   
private Production prod2prod(Symbol nt, Prod p) {
  switch(p) {
    case labeled(ProdModifier* ms, Name n, [empty()]) :
    case (Prod) `<ProdModifier* ms> <Name n> : ()` :
      return prod(label("<n>",nt), [], mods2attrs(ms));
    case (Prod) `<ProdModifier* ms> ()` :
      return prod(nt, [], mods2attrs(ms));
    case (Prod) `<ProdModifier* ms> <Name n> : <Sym* args>` :
      return prod(label(unescape("<n>"),nt),args2symbols(args),mods2attrs(ms));
    case (Prod) `<ProdModifier* ms> <Sym* args>` :
      return prod(nt, args2symbols(args), mods2attrs(ms));
    case (Prod) `<Prod l> | <Prod r>` :
      return choice(nt,{prod2prod(nt, l), prod2prod(nt, r)});
    case (Prod) `<Prod l> \> <Prod r>` : 
      return priority(nt,[prod2prod(nt, l), prod2prod(nt, r)]);
    case (Prod) `left (<Prod q>)` :
      return associativity(nt, \left(), {prod2prod(nt, q)});
    case (Prod) `right (<Prod q>)` :
      return associativity(nt, \right(), {prod2prod(nt, q)});
    case (Prod) `non-assoc (<Prod q>)` :
      return associativity(nt, \non-assoc(), {prod2prod(nt, q)});
    case (Prod) `assoc(<Prod q>)` :
      return associativity(nt, \left(), {prod2prod(nt, q)});
    case (Prod) `...`: return \others(nt);
    case (Prod) `: <Name n>`: return \reference(nt, "<n>");
    default: throw "missed a case <p>";
  } 
}

@doc{"..." in a choice is a no-op}   
public Production choice(Symbol s, {set[Production] a, others(Symbol t)}) {
  if (a == {})
    return others(t);
  else
    return choice(s, a);
}

@doc{This implements the semantics of "..." under a priority group}
public Production choice(Symbol s, {set[Production] a, priority(Symbol t, [list[Production] b, others(Symbol u), list[Production] c])}) 
  = priority(s, b + [choice(s, a)] + c);
