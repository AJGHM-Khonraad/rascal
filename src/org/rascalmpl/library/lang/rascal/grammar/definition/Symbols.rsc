@bootstrapParser
module lang::rascal::grammar::definition::Symbols

import lang::rascal::grammar::definition::Literals;
import lang::rascal::grammar::definition::Characters;
import lang::rascal::\syntax::RascalRascal;
import ParseTree;

public bool match(Symbol checked, Symbol referenced) {
  while (checked is condition || checked is label)
    checked = checked.symbol;
  while (referenced is condition || referenced is label)
    referenced = referenced.symbol;
    
  return referenced == checked;
} 


public Symbol sym2symbol(Sym sym) {
  switch (sym) {
    case (Sym) `<Nonterminal n>`          : 
      return sort("<n>");
    case (Sym) `start[<Nonterminal n>]` : 
      return \start(sort("<n>"));
    case (Sym) `<StringConstant l>` : 
      return lit(unescape(l));
    case (Sym) `<CaseInsensitiveStringConstant l>`: 
      return cilit(unescape(l));
    case (Sym) `<Nonterminal n>[<{Sym ","}+ syms>]` : 
      return \parameterized-sort("<n>",separgs2symbols(syms)); 
    case (Sym) `<Sym s> <NonterminalLabel n>` : 
      return label("<n>", sym2symbol(s));
    case (Sym) `<Sym s> ?`  : 
      return opt(sym2symbol(s));
    case (Sym) `<Class cc>` : 
      return cc2ranges(cc);
    case (Sym) `&<Nonterminal n>` : 
      return \parameter("<n>");
    case (Sym) `()` : 
      return \empty();
    case (Sym) `( <Sym first> | <{Sym "|"}+ alts>)` : 
      return alt({sym2symbol(first)} + {sym2symbol(elem) | elem <- alts});
    case (Sym) `<Sym s>*`  : 
      return \iter-star(sym2symbol(s));
    case (Sym) `<Sym s>+`  : 
      return \iter(sym2symbol(s));
    case (Sym) `<Sym s> *?` : 
      return \iter-star(sym2symbol(s));
    case (Sym) `<Sym s> +?` : 
      return \iter(sym2symbol(s));
    case (Sym) `{<Sym s> <Sym sep>}*`  : 
      return \iter-star-seps(sym2symbol(s), [sym2symbol(sep)]);
    case (Sym) `{<Sym s> <Sym sep>}+`  : 
      return \iter-seps(sym2symbol(s), [sym2symbol(sep)]);
    case (Sym) `(<Sym first> <Sym+ sequence>)` : 
      return seq([sym2symbol(first)] + [sym2symbol(elem) | elem <- sequence]);
    case (Sym) `^ <Sym s>` : 
      return conditional(sym2symbol(s), {\start-of-line()});
    case (Sym) `<Sym s> $` : 
      return conditional(sym2symbol(s), {\end-of-line()});
    case (Sym) `<Sym s> @ <IntegerLiteral i>` : 
      return conditional(sym2symbol(s), {\at-column(toInt("<i>"))}); 
    case (Sym) `<Sym s> >> <Sym r>` : 
      return conditional(sym2symbol(s), {follow(sym2symbol(r))});
    case (Sym) `<Sym s> !>> <Sym r>` : 
      return conditional(sym2symbol(s), {\not-follow(sym2symbol(r))});
    case (Sym) `<Sym s> << <Sym r>` : 
      return conditional(sym2symbol(r), {precede(sym2symbol(s))});
    case (Sym) `<Sym s> !<< <Sym r>` : 
      return conditional(sym2symbol(r), {\not-precede(sym2symbol(s))});
    case (Sym) `<Sym s> \ <Sym r>` : 
      return conditional(sym2symbol(s), {\delete(sym2symbol(r))});
    default: 
      throw "missed a case <sym>";
  }
}

public list[Symbol] args2symbols(Sym* args) {
  return [sym2symbol(s) | Sym s <- args];
}

private list[Symbol] separgs2symbols({Sym ","}+ args) {
  return [sym2symbol(s) | Sym s <- args];
}

// flattening rules for regular expressions
public Symbol \seq([list[Symbol] a, \seq(list[Symbol] b), list[Symbol] c]) = \seq(a + b + c);

public Symbol \alt({set[Symbol] a, \alt(set[Symbol] b)}) = \alt(a + b);

// flattening for conditionals

public Symbol \conditional(\conditional(Symbol s, set[Condition] cs1), set[Condition] cs2) 
  = \conditional(s, cs1 + cs2);
