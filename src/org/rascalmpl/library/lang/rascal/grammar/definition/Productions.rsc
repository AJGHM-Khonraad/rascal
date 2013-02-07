@license{
  Copyright (c) 2009-2013 CWI
  All rights reserved. This program and the accompanying materials
  are made available under the terms of the Eclipse Public License v1.0
  which accompanies this distribution, and is available at
  http://www.eclipse.org/legal/epl-v10.html
}
@contributor{Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI}
@contributor{Arnold Lankamp - Arnold.Lankamp@cwi.nl}
module lang::rascal::grammar::definition::Productions
     
import lang::rascal::syntax::Rascal;
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
      case \language(absent(), parametrized(Nonterminal l, {Sym ","}+ syms), Prod p) : {
        prods += prod2prod(\parameterized-sort("<l>",separgs2symbols(syms)), p);
      }
      case \language(absent(), nonterminal(Nonterminal n), Prod p) : {
        prods += prod2prod(\sort("<n>"), p);
      }
      case \lexical(parametrized(Nonterminal l, {Sym ","}+ syms), Prod p) : {
        prods += prod2prod(\parameterized-lex("<l>",separgs2symbols(syms)), p);
      }
      case \lexical(nonterminal(Nonterminal n), Prod p) : {
        prods += prod2prod(\lex("<n>"), p);
      }
      case \keyword(nonterminal(Nonterminal n), Prod p) : {
        prods += prod2prod(keywords("<n>"), p);
      }
      default: { iprintln(sd); throw "unsupported kind of syntax definition? <sd> at <sd@\loc>"; }
    }
  }

  return grammar(starts, prods);
} 
   
private Production prod2prod(Symbol nt, Prod p) {
  switch(p) {
    case labeled(ProdModifier* ms, Name n, Sym* args) : 
      if ([Sym x] := args.args, x is empty) {
        return prod(label("<n>",nt), [], mods2attrs(ms));
      }
      else {
        return prod(label(unescape("<n>"),nt),args2symbols(args),mods2attrs(ms));
      }
    case unlabeled(ProdModifier* ms, Sym* args) :
      if ([Sym x] := args.args, x is empty) {
        return prod(nt, [], mods2attrs(ms));
      }
      else {
        return prod(nt,args2symbols(args),mods2attrs(ms));
      }     
    case \all(Prod l, Prod r) :
      return choice(nt,{prod2prod(nt, l), prod2prod(nt, r)});
    case \first(Prod l, Prod r) : 
      return priority(nt,[prod2prod(nt, l), prod2prod(nt, r)]);
    case associativityGroup(\left(), Prod q) :
      return associativity(nt, \left(), {prod2prod(nt, q)});
    case associativityGroup(\right(), Prod q) :
      return associativity(nt, \right(), {prod2prod(nt, q)});
    case associativityGroup(\nonAssociative(), Prod q) :      
      return associativity(nt, \non-assoc(), {prod2prod(nt, q)});
    case associativityGroup(\associative(), Prod q) :      
      return associativity(nt, \left(), {prod2prod(nt, q)});
    case others(): return \others(nt);
    case reference(Name n): return \reference(nt, "<n>");
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
