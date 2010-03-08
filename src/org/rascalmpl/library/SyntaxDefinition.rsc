module SyntaxDefinition

import languages::\new-rascal::syntax::Rascal;
import Grammar;

// these rule flatten complex productions and ignore ordering under diff and assoc
rule or     or({a*, or({b*})})            => or({a*, b*}); 
rule xor    xor([a*,xor([b*],c*)])        => xor([a*,b*,c*]); 
rule xor    xor([a*,or({b}),c*])          => xor([a*,b,c*]); 
rule or     or({a*, xor([b])})            => or({a*, b}); 
rule assoc  assoc(a, {a*, or({b*})})      => assoc(a, {a*, b*}); 
rule assoc  assoc(a, {a*, xor([b*])})     => assoc(a, {a*, b*}); // ordering does not work under assoc
rule diff   diff(p, {a*, or({b*})})       => diff(p, {a*, b*});   
rule diff   diff(p, {a*, xor(b*)})        => diff(p, {a*, b*});  // ordering is irrelevant under diff
rule diff   diff(p, {a*, assoc(a, {b*})}) => diff(p, {a*, b*});  // assoc is irrelevant under diff

// move diff outwards
rule or     or({a*, diff(b, {c*})})       => diff(or({a*, b}), {c*});
rule xor    xor([a*, diff(b, {c*})])      => diff(or({xor([a*]), b}), {c*});
rule assoc  assoc(a, {a*, diff(b, {c*})}) => diff(assoc(a, {a*, b}), {c*});
rule diff   diff(p, {a*, diff(q, b*)})    => diff(or({p,q}), {a*, b*}); 
rule diff   diff(diff(a, {b*}), {c*})     => diff(a, {b*, c*});

public set[SyntaxDefinition] collect(Module mod) {
  set[Module] result = {};
  visit (mod) { case SyntaxDefinition s : result += s; }
  return result;
}  

public Grammar syntax2grammar(set[SyntaxDefinition] def) {
  return grammar({},{ def2prod(p) | sd <- def});
}

public Grammar def2prod(SyntaxDefinition def) {
  set[Production] prods = {};
  set[Symbol] starts = {};
  set[Production] layouts = {};

  switch (def) {
    case `<Tags t> <Visibility v> start syntax <UserType u> = <Prod p>`  : 
      prods += prod2prod(user2symbol(u), p);
    case `<Tags t> <Visibility v> start layout <UserType u> = <Prod p>`  : 
      layouts += prod2prod(user2symbol(u), p);
    case `<Tags t> <Visibility v> syntax <UserType u> = <Prod p>`  : { 
      starts += user2symbol(u);
      prods += prod2prod(user2symbol(u), p);
    }
    default: throw "missed case: <def>";
  }

  // todo: deal with layout 

  return grammar({},prods);
}



public Production prod2prod(Symbol nt, Prod p) {
  switch(p) {
    case `<ProdModifier* ms> <Name n> : <Sym* args>` :
      return prod(args2symbols(args), nt, mods2attrs(n, ms));
    case `<ProdModifier* ms> <Symbol* args>` :
      return prod(args2symbols(args), nt, mods2attrs(ms));
    case `<Prod l> | <Prod r>` :
      return or({prod2prod(nt, l), prod2prod(nt, r)});
    case `<Prod l> < <Prod r>` :
      return xor([prod2prod(nt, l), prod2prod(nr, r)]);
    case `<Prod l> - <Prod r>` :
      return diff(prod2prod(nt, l), {prod2prod(nt, r)});
    case `left ( <Prod p> )` :
      return assoc(left(), prod2prod(nt, p));
    case `right ( <Prod p>)` :
      return assoc(right(), prod2prod(nt, p));
    case `non-assoc (<Prod p>)` :
      return assoc(\non-assoc(), prod2prod(nt, p));
    case `assoc(<Prod p>)` :
      return assoc(left(), prod2prod(nt, p));
    case `...`: throw "... operator is not yet implemented";
    case `: <Name n>`: throw "prod referencing is not yet implemented";
    default: throw "missed a case <p>";
  } 
}

public list[Symbol] args2symbols(Sym* args) {
  return [ arg2symbol(s) | `<Sym* f> <Sym s> <Sym* t>` := args ];
}

public Symbol arg2symbol(Sym sym) {
  switch(sym) {
    case `<Name n>` : return sort("<n>");
    case `<StringLiteral l>` : return lit("<l>");
    case `<<Sym s>>` : return arg2symbol(s);
    case `<<Sym s> <Name n>` : return label("<n>", arg2symbol(s));
    case `<Sym s>?` : return opt(arg2symbol(s));
    case `<Sym s>??` : return opt(arg2symbol(s));
    case `<Sym s>*` : return iter-star(arg2symbol(s));
    case `<Sym s>+` : return iter(arg2symbol(s));
    case `<Sym s>*?` : return iter-star(arg2symbol(s));
    case `<Sym s>+?` : return iter(arg2symbol(s));
    case `<{<Sym s> <StringLiteral sep>}*>` : return \iter-star-sep(arg2symbol(s), lit("<sep>"));
    case `<{<Sym s> <StringLiteral sep>}+>` : return \iter-sep(arg2symbol(s), lit("<sep>"));
    case `<{<Sym s> <StringLiteral sep>}*?>` : return \iter-star-sep(arg2symbol(s), lit("<sep>"));
    case `<{<Sym s> <StringLiteral sep>}+?>` : return \iter-sep(arg2symbol(s), lit("<sep>"));
    default: throw "missed a case <sym>";
  }
}

public Attributes mods2attrs(Name cons, ProdModifier* mods) {
  return attrs([term(cons("<cons>"))]);
}

public Attributes mods2attrs(ProdModifier* mods) {
  return attrs([mod2attr(m) | `<ProdModifier* p1> <ProdModifier m> <ProdModifer* p2>` := mods]);
}

public Attribute mod2attr(ProdModifier m) {
  switch(m) {
    case `lex`: return term(lex());
    case `left`: return assoc(left());
    case `right`: return assoc(right());
    case `non-assoc`: return assoc(\non-assoc());
    case `assoc`: return assoc(assoc());
    case `bracket`: return bracket();
    default: throw "missed a case <m>";
  }
}

public Symbol user2symbol(UserType u) {
  switch (u) {
   case (UserType) `<Name n>` : return sort("<n>");
   default: throw "missed case: <u>";
  } 
}
