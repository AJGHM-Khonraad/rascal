module rascal::parser::Regular

import rascal::parser::Grammar;
import ParseTree;

public Grammar expandRegularSymbols(Grammar G) {
  for (regular(rhs,_) <- G.productions) {
    G.productions += expand(rhs);
  }
  return G;
}

private set[Production] expand(Symbol s) {
  switch (s) {
    case \opt(t) : return {choice({prod([],s,\no-attrs()),prod([t],s,\no-attrs())})};
    case \iter(t) : return {choice({prod([t],s,\no-attrs()),prod([t,s],s,\no-attrs())})};
    case \iter-star(t) : return {choice({prod([],s,\no-attrs()),prod([iter(t)],s,\no-attrs())})} + expand(iter(t));
    case \iter-sep(t,list[Symbol] seps) : return {choice({prod([t],s,\no-attrs()),prod([t,seps,s],s,\no-attrs())})};
    case \iter-star-sep(t, list[Symbol] seps) : return {choice({prod([],s,\no-attrs()),prod([\iter-sep(t,seps)],s,\no-attrs())})} + expand(\iter-sep(t,seps));
   }   

   throw "missed a case <s>";                   
}

public set[Production] makeRegularStubs(Grammar g) {
  return makeRegularStubs(g.productions);
}

public set[Production] makeRegularStubs(set[Production] prods) {
  return {regular(reg,\no-attrs()) | /Production p:prod(_,_,_) <- prods, sym <- p.lhs, reg <- regular(sym) };
}

private set[Symbol] regular(Symbol s) {
  result = {};
  visit (s) {
     case t:\opt(Symbol n) : 
       result += {t};
     case t:\iter(Symbol n) : 
       result += {t};
     case t:\iter-star(Symbol n) : 
       result += {t};
     case t:\iter-sep(Symbol n, list[Symbol] sep) : 
       result += {t};
     case t:\iter-star-sep(Symbol n,list[Symbol] sep) : 
       result += {t};
  }
  return result;
}  
