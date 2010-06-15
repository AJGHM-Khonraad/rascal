module rascal::conversion::sdf2::SDF2Grammar

// Convert SDF2 grammars to the Rascal internal grammar representation format

// Todo List:
// - aliases
// - (lexical) variables

import IO;
import String;
import Integer;
import ParseTree;
//import List;
import rascal::parser::Grammar;
import rascal::parser::Definition;
import rascal::conversion::sdf2::Load;

//import languages::sdf2::syntax::\Sdf2;
//import languages::sdf2::syntax::\Sdf2-Syntax;

import languages::sdf2::syntax::Sdf2ForRascal;

// Resolve name clashes between the ParseTree and Grammar datatypes.

// Unfortunately we cannot yet use these aliases since they lead to ambiguities.
// Reason: aliases are not resolved in concrete syntax fragments

//alias SDFSymbol = languages::sdf2::syntax::Sdf2ForRascal::Symbol;
//alias SDFProduction = languages::sdf2::syntax::Sdf2ForRascal::Production;
//alias SDFStrCon = languages::sdf2::syntax::Sdf2ForRascal::StrCon;
//alias SDFSingleQuotesStrCon = languages::sdf2::syntax::Sdf2ForRascal::SingleQuotedStrCon;
//alias SDFCharRange = languages::sdf2::syntax::Sdf2ForRascal::CharRange;
//alias SDFCharClass =languages::sdf2::syntax::Sdf2ForRascal::CharClass;

private bool debug = false;

public Grammar sdf2grammar(loc input) {
  return sdf2grammar(parse(#SDF, input)); 
}

public void print(Grammar G){
  println("grammar(<G.start>, {");
  for(P <- G.productions){
  	println("\t<P>,");
  }
  println("})");
}

// test sdf2grammar(|stdlib:///org/rascalmpl/library/rascal/conversion/sdf2/Pico.def|);
// test print(sdf2grammar(|stdlib:///org/rascalmpl/library/rascal/conversion/sdf2/Rascal.def|));
// test print(sdf2grammar(|stdlib:///org/rascalmpl/library/rascal/conversion/sdf2/java111.def|));
// test print(sdf2grammar(|stdlib:///org/rascalmpl/library/rascal/conversion/sdf2/C.def|));

public Grammar sdf2module2grammar(str name, list[loc] path) {
  return sdf2grammar(loadSDF2Module(name, path));
}

//loadSDF2Module("Names", [|stdlib:///org/rascalimpl/library/rascal/syntax/Names.sdf|]);

public Grammar sdf2grammar(SDF definition) {
  return grammar(getStartSymbols(definition), getProductions(definition));
}

// ----- getProductions, getProduction -----

public set[ParseTree::Production] getProductions(languages::sdf2::syntax::Sdf2ForRascal::SDF definition) {
 res = {};
 visit (definition) {
    case (Grammar) `syntax <languages::sdf2::syntax::Sdf2ForRascal::Production* prods>`:
    	res += getProductions(prods, true);
    	
    case (Grammar) `lexical syntax <languages::sdf2::syntax::Sdf2ForRascal::Production* prods>`:
    	res += getProductions(prods, true); 
    	
    case (Grammar) `context-free syntax <languages::sdf2::syntax::Sdf2ForRascal::Production* prods>`:
    	res += getProductions(prods, false); 
    	
    case (Grammar) `restrictions <languages::sdf2::syntax::Sdf2ForRascal::Restriction* rests>`:
    	res += getRestrictions(rests, true);
    	
    case (Grammar) `lexical restrictions <languages::sdf2::syntax::Sdf2ForRascal::Restriction* rests>`:
    	res += getRestrictions(rests, true);
    	
    case (Grammar) `context-free restrictions <languages::sdf2::syntax::Sdf2ForRascal::Restriction* rests>` :
    	res += getRestrictions(rests, false);
    	
    case (Grammar) `priorities <{languages::sdf2::syntax::Sdf2ForRascal::Priority ","}* prios>`:
    	res += getPriorities(prios,true);
    	
    case (Grammar) `lexical priorities <{languages::sdf2::syntax::Sdf2ForRascal::Priority ","}* prios>`:
    	res += getPriorities(prios,true);
    	
    case (Grammar) `context-free priorities <{languages::sdf2::syntax::Sdf2ForRascal::Priority ","}* prios>`:
    	res += getPriorities(prios,false);
  };
  return res;
}

test getProductions((SDF) `definition module A exports syntax A -> B`) ==
     {prod([sort("A")],sort("B"),\no-attrs())};
     
test getProductions((SDF) `definition module A exports lexical syntax A -> B`) ==
     {prod([sort("A")],sort("B"),\no-attrs())};
     
test getProductions((SDF) `definition module A exports lexical syntax A -> B B -> C`) ==
     {prod([sort("B")],sort("C"),\no-attrs()),prod([sort("A")],sort("B"),\no-attrs())};
     
test getProductions((SDF) `definition module A exports context-free syntax A -> B`) ==
     {prod([sort("A")],sort("B"),\no-attrs())};
     
test getProductions((SDF) `definition module A exports restrictions ID -/- [a-z]`) ==
     {restrict(sort("ID"),others(sort("ID")),[\char-class([range(97,122)])])};
    
test getProductions((SDF) `definition module A exports priorities A -> B > C -> D`) ==
     {first(sort("B"),[prod([sort("A")],sort("B"),\no-attrs()),prod([sort("C")],sort("D"),\no-attrs())])};


public set[Production] getProductions(languages::sdf2::syntax::Sdf2ForRascal::Production* prods, bool isLex){
	return {getProduction(prod, isLex) | languages::sdf2::syntax::Sdf2ForRascal::Production prod <- prods};
}

public Production getProduction(languages::sdf2::syntax::Sdf2ForRascal::Production P, bool isLex) {
  if(debug) println("getProduction: <P>");
  switch (P) {
    case (Production) `<languages::sdf2::syntax::Sdf2ForRascal::Symbol* syms> -> <languages::sdf2::syntax::Sdf2ForRascal::Symbol sym>`:
    	return prod(getSymbols(syms, isLex),getSymbol(sym, isLex),\no-attrs());
    	
    case (Production) `<languages::sdf2::syntax::Sdf2ForRascal::Symbol* syms> -> <languages::sdf2::syntax::Sdf2ForRascal::Symbol sym> {<{languages::sdf2::syntax::Sdf2ForRascal::Attribute ","}* attrs>}` :
    	return prod(getSymbols(syms, isLex),getSymbol(sym, isLex),getAttributes(attrs));
    	
    case (Production) `<IdCon id> (<{languages::sdf2::syntax::Sdf2ForRascal::Symbol ","}* syms>) -> <languages::sdf2::syntax::Sdf2ForRascal::Symbol sym>`:
    	throw "prefix functions not supported by SDF import yet";
    	
    case (Production) `<IdCon id> (<{languages::sdf2::syntax::Sdf2ForRascal::Symbol ","}* syms>) -> <languages::sdf2::syntax::Sdf2ForRascal::Symbol sym> {<{languages::sdf2::syntax::Sdf2ForRascal::Attribute ","}* attrs>}` :
    	throw "prefix functions not supported by SDF import yet";
    	
    case (Production) `<StrCon s> (<{languages::sdf2::syntax::Sdf2ForRascal::Symbol ","}* syms>) -> <languages::sdf2::syntax::Sdf2ForRascal::Symbol sym>`:
    	throw "prefix functions not supported by SDF import yet";
    	
    case (Production) `<StrCon s> (<{languages::sdf2::syntax::Sdf2ForRascal::Symbol ","}* syms>) -> <languages::sdf2::syntax::Sdf2ForRascal::Symbol sym> {<{Attribute ","}* attrs>}` :
    	throw "prefix functions not supported by SDF import yet";
    	
    default:
    	throw "missing case <P>";
  }
}

test getProduction((Production) `PICO-ID ":" TYPE -> ID-TYPE`, true) == 
     prod([sort("PICO-ID"),lit(":"),sort("TYPE")],sort("ID-TYPE"),\no-attrs());

test getProduction((Production) `PICO-ID ":" TYPE -> ID-TYPE {cons("decl"), left}`, true) ==
     prod([sort("PICO-ID"), lit(":"), sort("TYPE")],
               sort("ID-TYPE"),
               attrs([term(cons("decl")),\assoc(left())]));
               
test getProduction((Production) `[\ \t\n\r]	-> LAYOUT {cons("whitespace")}`, true) == 
     prod([\char-class([range(32,32),range(110,110),range(114,114),range(116,116)])],sort("LAYOUT"),attrs([term(cons("whitespace"))]));

//test getProduction((Production) `{~[\n]* [\n]}* -> Rest`, true);


// ----- getRestrictions, getRestriction -----

public set[Production] getRestrictions(languages::sdf2::syntax::Sdf2ForRascal::Restriction* restrictions, bool isLex) {
  return { getRestriction(r, isLex) | languages::sdf2::syntax::Sdf2ForRascal::Restriction r <- restrictions };
}

public set[Production] getRestriction(languages::sdf2::syntax::Sdf2ForRascal::Restriction restriction, bool isLex) {
  switch (restriction) {
    case (Restriction) `-/- <languages::sdf2::syntax::Sdf2ForRascal::Lookaheads ls>` :
    	return {};
    	
    case (Restriction) `<languages::sdf2::syntax::Sdf2ForRascal::Symbol s1> <languages::sdf2::syntax::Sdf2ForRascal::Symbol s2> <languages::sdf2::syntax::Sdf2ForRascal::Symbol* rest> -/- <languages::sdf2::syntax::Sdf2ForRascal::Lookaheads ls>` : 
      return getRestriction((Restriction) `<languages::sdf2::syntax::Sdf2ForRascal::Symbol s1> -/- <languages::sdf2::syntax::Sdf2ForRascal::Lookaheads ls>`, isLex) 
           + getRestriction((Restriction) `<languages::sdf2::syntax::Sdf2ForRascal::Symbol s2> <languages::sdf2::syntax::Sdf2ForRascal::Symbol* rest> -/- <languages::sdf2::syntax::Sdf2ForRascal::Lookaheads ls>`, isLex);
           
    case (Restriction) `<languages::sdf2::syntax::Sdf2ForRascal::Symbol s1> -/- <languages::sdf2::syntax::Sdf2ForRascal::Lookaheads ls>` :
      return {restrict(getSymbol(s1, isLex), others(getSymbol(s1, isLex)), r) | r <- getLookaheads(ls)};
      
    default:
    	throw "missing case <restriction>";
  }
}

test getRestriction((Restriction) `-/- [a-z]`, true) == {};

test getRestriction((Restriction) `ID -/- [a-z]`, true) == 
     {restrict(sort("ID"),others(sort("ID")),[\char-class([range(97,122)])])};
   

// ----- getLookaheads, getLookahead -----

public set[list[Symbol]] getLookaheads(languages::sdf2::syntax::Sdf2ForRascal::Lookaheads ls) {
   switch (ls) {
     case (Lookaheads) `<languages::sdf2::syntax::Sdf2ForRascal::CharClass c>` :
     	return {[getCharClass(c)]};
     	
     case (Lookaheads) `<languages::sdf2::syntax::Sdf2ForRascal::CharClass c>.<languages::sdf2::syntax::Sdf2ForRascal::Lookaheads ls>` :
     	return {[getCharClass(c)] + other | other <- getLookaheads(ls)};
     	
     case (Lookaheads) `<languages::sdf2::syntax::Sdf2ForRascal::Lookaheads l> | <languages::sdf2::syntax::Sdf2ForRascal::Lookaheads r>` :
     	return getLookaheads(l) + getLookaheads(r);
     	
     case (Lookaheads) `(<languages::sdf2::syntax::Sdf2ForRascal::Lookaheads(l)>)` :
     	return getLookaheads(l);
     	
     case (Lookaheads) `[[<{languages::sdf2::syntax::Sdf2ForRascal::Lookahead ","}* _>]]`:
     	throw "unsupported lookahead construction <ls>";
     	
     default: throw "unsupported lookahead construction <ls>";
   }
}

test getLookaheads((Lookaheads) `[a-z]`) == 
     {[\char-class([range(97,122)])]};
     
test getLookaheads((Lookaheads) `[a-z] . [0-9]`) ==
     {[\char-class([range(97,122)]),\char-class([range(48,57)])]};
     
test getLookaheads((Lookaheads) `[a-z] . [0-9] | [\"]`) ==
     {[\char-class([range(97,122)]),\char-class([range(48,57)])],[\char-class([range(34,34)])]};
     
test getLookaheads((Lookaheads) `([a-z])`) == 
     {[\char-class([range(97,122)])]};

// ----- getPriorities, getPriority -----

public set[Production] getPriorities({languages::sdf2::syntax::Sdf2ForRascal::Priority ","}* priorities, bool isLex) {
  return {getPriority(p, isLex) | languages::sdf2::syntax::Sdf2ForRascal::Priority p <- priorities};
}

public Production getPriority(languages::sdf2::syntax::Sdf2ForRascal::Group group, bool isLex) {
	if(debug) println("getPriority: <group>");
	switch (group) {
    case (Group) `<languages::sdf2::syntax::Sdf2ForRascal::Production p>` :   
      	return getProduction(p, isLex);
       
    case (Group) `<languages::sdf2::syntax::Sdf2ForRascal::Group g> .` :
     	return getPriority(g, isLex); // we ignore non-transitivity here!
     	
    case (Group) `<languages::sdf2::syntax::Sdf2ForRascal::Group g> <languages::sdf2::syntax::Sdf2ForRascal::ArgumentIndicator i>` : 
     	return getPriority(g, isLex); // we ignore argument indicators here!
     	
    case (Group) `{<languages::sdf2::syntax::Sdf2ForRascal::Production* ps>}` : 
       return choice(definedSymbol(ps,isLex), {getProduction(p,isLex) | languages::sdf2::syntax::Sdf2ForRascal::Production p <- ps});
       
    case (Group) `{<languages::sdf2::syntax::Sdf2ForRascal::Associativity a> : <languages::sdf2::syntax::Sdf2ForRascal::Production* ps>}` : 
       return \assoc(definedSymbol(ps, isLex), getAssociativity(a), {getProduction(p,isLex) | languages::sdf2::syntax::Sdf2ForRascal::Production p <- ps});
    
    default:
    	throw "missing case <group>";}
}
     	
test getPriority((Group) `A -> B`, true) == 
     prod([sort("A")],sort("B"),\no-attrs());
     
test getPriority((Group) `A -> B .`, true) == 
     prod([sort("A")],sort("B"),\no-attrs());
     
test getPriority((Group) `A -> B <1>`, true) == 
     prod([sort("A")],sort("B"),\no-attrs());
     
test getPriority((Group) `{A -> B C -> D}`, true) == 
     choice(sort("B"),{prod([sort("C")],sort("D"),\no-attrs()),prod([sort("A")],sort("B"),\no-attrs())});
     
test getPriority((Group) `{left: A -> B}`, true) == 
     \assoc(sort("B"),\left(),{prod([sort("A")],sort("B"),\no-attrs())});
     
test getPriority((Group) `{left: A -> B B -> C}`, true) ==
     \assoc(sort("B"),\left(),{prod([sort("B")],sort("C"),\no-attrs()),prod([sort("A")],sort("B"),\no-attrs())});

public Production getPriority(languages::sdf2::syntax::Sdf2ForRascal::Priority priority, bool isLex) {
   switch (priority) {
   
     case (Group) `<languages::sdf2::syntax::Sdf2ForRascal::Group g>`:
     	return getPriority(g, isLex);
         
     case (Priority) `<languages::sdf2::syntax::Sdf2ForRascal::Group g1> <languages::sdf2::syntax::Sdf2ForRascal::Associativity a> <languages::sdf2::syntax::Sdf2ForRascal::Group g2>` : 
       return \assoc(definedSymbol(g1, isLex), getAssociativity(a), {getPriority((Priority) `<languages::sdf2::syntax::Sdf2ForRascal::Group g1>`, isLex), getPriority((Priority) `<languages::sdf2::syntax::Sdf2ForRascal::Group g2>`,isLex)});
 
     case (Priority) `<{languages::sdf2::syntax::Sdf2ForRascal::Group ">"}+ groups>` : 
       return first(definedSymbol(groups,isLex), [getPriority(group,isLex) | languages::sdf2::syntax::Sdf2ForRascal::Group group <- groups]);
   }
}

test getPriority((Priority) `A -> B`, true) == 
     prod([sort("A")],sort("B"),\no-attrs());
     
test getPriority((Priority) `A -> B .`, true) == 
     prod([sort("A")],sort("B"),\no-attrs());
     
test getPriority((Priority) `A -> B <1>`, true) == 
     prod([sort("A")],sort("B"),\no-attrs());
     
test getPriority((Priority) `{A -> B C -> D}`, true) == 
     choice(sort("B"),{prod([sort("C")],sort("D"),\no-attrs()),prod([sort("A")],sort("B"),\no-attrs())});
     
test getPriority((Priority) `A -> B > C -> D`, true) ==
     first(sort("B"),[prod([sort("A")],sort("B"),\no-attrs()),prod([sort("C")],sort("D"),\no-attrs())]);
     
test getPriority((Priority) `A -> B > C -> D > E -> F`, true) ==
     first(sort("B"),[prod([sort("A")],sort("B"),\no-attrs()),prod([sort("C")],sort("D"),\no-attrs()),prod([sort("E")],sort("F"),\no-attrs())]);


public Symbol definedSymbol((&T <: Tree) v, bool isLex) {
  // Note that this might not work if there are different right-hand sides in the group...
  // I don't know what to do about this yet.
  if (/(Production) `<languages::sdf2::syntax::Sdf2ForRascal::Symbol* _> -> <languages::sdf2::syntax::Sdf2ForRascal::Symbol s> <languages::sdf2::syntax::Sdf2ForRascal::Attributes _>` := v) {
    return getSymbol(s, isLex);
  } else if (/(Production) `<languages::sdf2::syntax::Sdf2ForRascal::Symbol* _> -> LAYOUT <languages::sdf2::syntax::Sdf2ForRascal::Attributes _>` := v) {
    return sort("LAYOUT");
  }
  throw "could not find a defined symbol in <v>";
}

// ----- getStartSymbols -----

public set[ParseTree::Symbol] getStartSymbols(SDF definition) {
  result = {};
  visit(definition) {
    case (Grammar) `context-free start-symbols <languages::sdf2::syntax::Sdf2ForRascal::Symbol* syms>` :
    	result += { getSymbol(sym, true) | sym <- syms };
    case (Grammar) `lexical start-symbols <languages::sdf2::syntax::Sdf2ForRascal::Symbol* syms>`      :
    	result += { getSymbol(sym, true) | sym <- syms };
    case (Grammar) `start-symbols <languages::sdf2::syntax::Sdf2ForRascal::Symbol* syms>`              :
    	result += { getSymbol(sym, true) | sym <- syms };
  }
  return result;
}

test getStartSymbols((SDF) `definition module M exports context-free start-symbols A B C`) == 
     {sort("A"), sort("B"), sort("C")};
     
test getStartSymbols((SDF) `definition module M exports lexical start-symbols A B C`) == 
     {sort("A"), sort("B"), sort("C")};
     
test getStartSymbols((SDF) `definition module M exports start-symbols A B C`) == 
     {sort("A"), sort("B"), sort("C")};

// ----- getSymsols, getSymbol -----
     
public list[Symbol] getSymbols(languages::sdf2::syntax::Sdf2ForRascal::Symbol* syms, bool isLex){
    return [getSymbol(sym, isLex) | sym <- syms];
}

test getSymbols((Symbol*) `A B "ab"`, true) == [sort("A"), sort("B"), lit("ab")];
     

public ParseTree::Symbol getSymbol(languages::sdf2::syntax::Sdf2ForRascal::Symbol sym, bool isLex) {
  if(debug) println("getSymbol: <sym>");
  switch (sym) {
    case (Symbol) `<languages::sdf2::syntax::Sdf2ForRascal::StrCon l> : <languages::sdf2::syntax::Sdf2ForRascal::Symbol s>`:
		return label(unescape(l), getSymbol(s,isLex));
		
    case (Symbol) `<languages::sdf2::syntax::Sdf2ForRascal::IdCon i> : <languages::sdf2::syntax::Sdf2ForRascal::Symbol s>`:
    	return label("<i>", getSymbol(s, isLex));
    	
    case (Symbol) `<languages::sdf2::syntax::Sdf2ForRascal::Sort n>`:
    	return sort("<n>");
    	
   	case (Symbol) `LAYOUT`:
    	return sort("LAYOUT"); 
    	
    case (Symbol) `<languages::sdf2::syntax::Sdf2ForRascal::StrCon l>`:
    	return lit(unescape(l));
    	
    case (Symbol) `<languages::sdf2::syntax::Sdf2ForRascal::SingleQuotedStrCon l>`:
    	return cilit(unescape(l));  
    	
    case (Symbol) `<languages::sdf2::syntax::Sdf2ForRascal::Sort n>[[<{languages::sdf2::syntax::Sdf2ForRascal::Symbol ","}+ syms>]]`:
    	return \parameterized-sort("<n>",separgs2symbols(syms,isLex));
    	
    case (Symbol) `<languages::sdf2::syntax::Sdf2ForRascal::Symbol s> ?`:
    	return opt(getSymbol(s,isLex));
    	
    case (Symbol) `<languages::sdf2::syntax::Sdf2ForRascal::CharClass cc>`:
    	return getCharClass(cc);
    	
    case (Symbol) `( <languages::sdf2::syntax::Sdf2ForRascal::Symbol sym> <languages::sdf2::syntax::Sdf2ForRascal::Symbol+ syms> )`:
    	return \seq(getSymbols((Symbol*) `<sym> <syms>`, isLex));
    	
    case (Symbol) `< <languages::sdf2::syntax::Sdf2ForRascal::Symbol sym> -LEX >`:
        return \lex(getSymbol(sym, isLex));
        
    case (Symbol) `< <languages::sdf2::syntax::Sdf2ForRascal::Symbol sym> -CF >`:
        return \cf(getSymbol(sym, isLex));
       
    case (Symbol) `< <languages::sdf2::syntax::Sdf2ForRascal::Symbol sym> -VAR >`:
        throw "-VAR symbols not supported: <sym>";
       
  }  
  
  if (isLex) switch (sym) {
    case (Symbol) `<languages::sdf2::syntax::Sdf2ForRascal::Symbol s> *`: 
    	return \iter-star(getSymbol(s,isLex));
    	
    case (Symbol) `<languages::sdf2::syntax::Sdf2ForRascal::Symbol s> +`  :
    	return \iter(getSymbol(s,isLex));
    	
    case (Symbol) `<languages::sdf2::syntax::Sdf2ForRascal::Symbol s> *?` :
    	return \iter-star(getSymbol(s,isLex));
    	
    case (Symbol) `<languages::sdf2::syntax::Sdf2ForRascal::Symbol s> +?` :
    	return \iter(getSymbol(s,isLex));
    	
    case (Symbol) `{<languages::sdf2::syntax::Sdf2ForRascal::Symbol s> <languages::sdf2::syntax::Sdf2ForRascal::Symbol sep>} *`  :
    	return \iter-star-seps(getSymbol(s,isLex), [getSymbol(sep, isLex)]);
    	
    case (Symbol) `{<languages::sdf2::syntax::Sdf2ForRascal::Symbol s> <languages::sdf2::syntax::Sdf2ForRascal::Symbol sep>} +`  :
    	return \iter-seps(getSymbol(s,isLex), [getSymbol(sep, isLex)]);
    	
    case (Symbol) `{<languages::sdf2::syntax::Sdf2ForRascal::Symbol s> <languages::sdf2::syntax::Sdf2ForRascal::Symbol sep>} *?` :
    	return \iter-star-seps(getSymbol(s,isLex),  [getSymbol(sep, isLex)]);
    	
    case (Symbol) `{<languages::sdf2::syntax::Sdf2ForRascal::Symbol s> <languages::sdf2::syntax::Sdf2ForRascal::Symbol sep>} +?` :
    	return \opt(\iter-seps(getSymbol(s,isLex),  [getSymbol(sep, isLex)]));
    	
    default: throw "missed a case <sym>";
  } 
  else switch (sym) {  
    case (Symbol) `<languages::sdf2::syntax::Sdf2ForRascal::Symbol s> *`:
    	return \iter-star-seps(getSymbol(s,isLex),[\layout()]);
    	
    case (Symbol) `<languages::sdf2::syntax::Sdf2ForRascal::Symbol s> +` :
    	return \iter-seps(getSymbol(s,isLex),[\layout()]);
    	
    case (Symbol) `<languages::sdf2::syntax::Sdf2ForRascal::Symbol s> *?`:
    	return \iter-star-seps(getSymbol(s,isLex),[\layout()]);
    	
    case (Symbol) `<languages::sdf2::syntax::Sdf2ForRascal::Symbol s> +?`:
    	return \iter-seps(getSymbol(s,isLex),[\layout()]);
    	
    case (Symbol) `{<languages::sdf2::syntax::Sdf2ForRascal::Symbol s> <languages::sdf2::syntax::Sdf2ForRascal::Symbol sep>} *` :
    	return \iter-star-seps(getSymbol(s,isLex), [\layout(),getSymbol(sep, isLex),\layout()]);
    	
    case (Symbol) `{<languages::sdf2::syntax::Sdf2ForRascal::Symbol s> <languages::sdf2::syntax::Sdf2ForRascal::Symbol sep>} +` :
    	return \iter-seps(getSymbol(s,isLex), [\layout(),getSymbol(sep, isLex),\layout()]);
    	
    case (Symbol) `{<languages::sdf2::syntax::Sdf2ForRascal::Symbol s> <languages::sdf2::syntax::Sdf2ForRascal::Symbol sep>} *?`:
    	return \iter-star-seps(getSymbol(s,isLex), [\layout(),getSymbol(sep, isLex),\layout()]);
    	
    case (Symbol) `{<languages::sdf2::syntax::Sdf2ForRascal::Symbol s> <languages::sdf2::syntax::Sdf2ForRascal::Symbol sep>} +?`:
    	return \iter-seps(getSymbol(s,isLex), [\layout(),getSymbol(sep, isLex),\layout()]);
    default: throw "missed a case <sym>";  
  }
}

test getSymbol((Symbol) `"abc"`, false) 		== lit("abc");
test getSymbol((Symbol) `ABC`, false) 			== sort("ABC");
test getSymbol((Symbol) `'abc'`, false) 		== cilit("abc");
test getSymbol((Symbol) `abc : ABC`, false) 	== label("abc",sort("ABC"));
test getSymbol((Symbol) `"abc" : ABC`, false) 	== label("abc",sort("ABC"));
//test getSymbol((Symbol) `A[[B]]`, false) 		== \parameterized-sort([sort("B")]);   // <== parameterized sort missing
test getSymbol((Symbol) `A?`, false) 			== opt(sort("A"));
test getSymbol((Symbol) `[a]`, false) 			== \char-class([range(97,97)]);
test getSymbol((Symbol) `A*`, false) 			== \iter-star-seps(sort("A"),[\layout()]);
test getSymbol((Symbol) `A+`, false) 			== \iter-seps(sort("A"),[\layout()]);
test getSymbol((Symbol) `A*?`, false) 			== opt(\iter-star-seps(sort("A"),[\layout()]));
test getSymbol((Symbol) `A+?`, false) 			== opt(\iter-seps(sort("A"),[\layout()]));
test getSymbol((Symbol) `{A "x"}*`, false) 		== \iter-star-seps(sort("A"),[\layout(),lit("x"),\layout()]);
test getSymbol((Symbol) `{A "x"}+`, false) 		== \iter-seps(sort("A"),[\layout(),lit("x"),\layout()]);
test getSymbol((Symbol) `{A "x"}*?`, false) 	== opt(\iter-star-seps(sort("A"),[\layout(),lit("x"),\layout()]));
test getSymbol((Symbol) `{A "x"}+?`, false) 	== opt(\iter-seps(sort("A"),[\layout(),lit("x"),\layout()]));
test getSymbol((Symbol) `A*`, true) 			== \iter-star(sort("A"));
test getSymbol((Symbol) `A+`, true) 			== \iter(sort("A"));
test getSymbol((Symbol) `A*?`, true) 			== opt(\iter-star(sort("A")));
test getSymbol((Symbol) `A+?`, true) 			== opt(\iter(sort("A")));
test getSymbol((Symbol) `{A "x"}*`, true) 		== \iter-star-seps(sort("A"),[lit("x")]);
test getSymbol((Symbol) `{A "x"}+`, true) 		== \iter-seps(sort("A"),[lit("x")]);
test getSymbol((Symbol) `{A "x"}*?`, true) 		== opt(\iter-star-seps(sort("A"),[lit("x")]));
test getSymbol((Symbol) `{A "x"}+?`, true) 		== opt(\iter-seps(sort("A"),[lit("x")]));

//test getSymbol((Symbol) `<LAYOUT? -CF>`, true) ==
//test getSymbol((Symbol) `<RegExp -LEX>`, true) ==

// ----- unescape -----

private str unescape(languages::sdf2::syntax::Sdf2ForRascal::StrCon s) {
   if ([StrCon] /\"<rest:.*>\"/ := s) {
     return visit (rest) {
       case /\\b/           => "\b"
       case /\\f/           => "\f"
       case /\\n/           => "\n"
       case /\\t/           => "\t"
       case /\\r/           => "\r"  
       case /\\\"/          => "\""  
       case /\\\\/          => "\\"
       case /\\TOP/         => "\255"
       case /\\EOF/         => "\256"
       case /\\BOT/         => "\0"
       case /\\LABEL_START/ => "\257"
     };      
   }
   throw "unexpected string format: <s>";
}

test unescape((StrCon) `"abc"`)  == "abc";
test unescape((StrCon) `"a\nc"`) == "a\nc";
test unescape((StrCon) `"a\"c"`) == "a\"c";
test unescape((StrCon) `"a\\c"`) == "a\\c";

private str unescape(languages::sdf2::syntax::Sdf2ForRascal::SingleQuotedStrCon s) {
   if ([SingleQuotedStrCon] /\'<rest:.*>\'/ := s) {
     return visit (rest) {
       case /\\b/           => "\b"
       case /\\f/           => "\f"
       case /\\n/           => "\n"
       case /\\t/           => "\t"
       case /\\r/           => "\r"  
       case /\\\'/           => "\'"  
       case /\\\\/          => "\\"
     };      
   }
   throw "unexpected string format: <s>";
}


test unescape((SingleQuotedStrCon) `'abc'`)  == "abc";
test unescape((SingleQuotedStrCon) `'a\nc'`) == "a\nc";
test unescape((SingleQuotedStrCon) `'a\'c'`) == "a\'c";
test unescape((SingleQuotedStrCon) `'a\\c'`) == "a\\c";


// Unescape on Symbols. Note that the function below can currently coexist with the above to unescape functions
// since StrCon and SingleQuotedStrCons are *not* a subtype of Symbol (which they should be).
// Do a deep match (/) to skip the chain function that syntactically includes both subtypes in Symbol.

private str unescape(languages::sdf2::syntax::Sdf2ForRascal::Symbol s) {
  if(debug) println("unescape: <s>");
  if(/languages::sdf2::syntax::Sdf2ForRascal::SingleQuotedStrCon scon := s)
  	return unescape(scon);
  if(/languages::sdf2::syntax::Sdf2ForRascal::StrCon scon := s)
  	return unescape(scon);
  throw "unexpected string format: <s>";
}

// ----- getCharClas -----

public Symbol getCharClass(languages::sdf2::syntax::Sdf2ForRascal::CharClass cc) {
   if(debug) println("getCharClass: <cc>");
   switch(cc) {
     case (CharClass) `[]` :
     	return \char-class([]);
     	
     case (CharClass) `[<languages::sdf2::syntax::Sdf2ForRascal::OptCharRanges ranges>]` : 
     	return \char-class([getCharRange(r) | /languages::sdf2::syntax::Sdf2ForRascal::CharRange r := ranges]);
     	
     case (CharClass) `(<languages::sdf2::syntax::Sdf2ForRascal::CharClass c>)`: 
     	return getCharClass(c);
     	
     case (CharClass) `~ <languages::sdf2::syntax::Sdf2ForRascal::CharClass c>`:
     	return complement(getCharClass(c));
     	
     case (CharClass) `<languages::sdf2::syntax::Sdf2ForRascal::CharClass l> /\ <languages::sdf2::syntax::Sdf2ForRascal::CharClass r>`:
     	return intersection(getCharClass(l),getCharClass(r));
     	
     case (CharClass) `<languages::sdf2::syntax::Sdf2ForRascal::CharClass l> \/ <languages::sdf2::syntax::Sdf2ForRascal::CharClass r>`: 
     	return union(getCharClass(l),getCharClass(r));
     	
     case (CharClass) `<languages::sdf2::syntax::Sdf2ForRascal::CharClass l> / <languages::sdf2::syntax::Sdf2ForRascal::CharClass r>`:
     	return difference(getCharClass(l),getCharClass(r));
     	
     default: throw "missed a case <cc>";
   }
}

//  (CharClass) `[]` == \char-class([]);  // ===> gives unsupported operation

test getCharClass((CharClass) `[]`)         == \char-class([]);
test getCharClass((CharClass) `[a]`)        == \char-class([range(97,97)]);
test getCharClass((CharClass) `[a-z]`)      == \char-class([range(97,122)]);
test getCharClass((CharClass) `[a-z0-9]`)   == \char-class([range(97,122), range(48,57)]);
test getCharClass((CharClass) `([a])`)      == \char-class([range(97,97)]);
test getCharClass((CharClass) `~[a]`)       == complement(\char-class([range(97,97)]));
test getCharClass((CharClass) `[a] /\ [b]`) == intersection(\char-class([range(97,97)]), \char-class([range(98,98)]));
test getCharClass((CharClass) `[a] \/ [b]`) == union(\char-class([range(97,97)]), \char-class([range(98,98)]));
test getCharClass((CharClass) `[a] / [b]`)  == difference(\char-class([range(97,97)]), \char-class([range(98,98)]));
test getCharClass((CharClass) `[\n]`)       == \char-class([range(110,110)]);
test getCharClass((CharClass) `~[\0-\31\n\t\"\\]`) ==
     complement(\char-class([range(0,25),range(34,34),range(92,92),range(110,110),range(116,116)]));

// ----- getCharRange -----
      
public CharRange getCharRange(languages::sdf2::syntax::Sdf2ForRascal::CharRange r) {
  if(debug) println("getCharRange: <r>");
  switch (r) {
    case (CharRange) `<Character c>` : return range(getCharacter(c),getCharacter(c));
    
    case (CharRange) `<Character l> - <Character r>`: return range(getCharacter(l),getCharacter(r));
    
    default: throw "missed a case <r>";
  }
}

test getCharRange((CharRange) `a`)   == range(97,97);

test getCharRange((CharRange) `a-z`) == range(97,122);

test getCharRange((CharRange) `\n`)  ==  range(110,110);

// ----- getCharacter -----

private int getCharacter(Character c) {
  if(debug) println("getCharacter: <c>");
  switch (c) {
    case [Character] /\\<oct:[0-3][0-7][0-7]>/ : return toInt("0<oct>");
    
    case [Character] /\\<oct:[0-7][0-7]>/      : return toInt("0<oct>");
    
    case [Character] /\\<oct:[0-7]>/           : return toInt("0<oct>");
    
    case [Character] /\\<esc:["'\-\[\]\\ ]>/   : return charAt(esc, 0);
    
    case [Character] /<ch:[^"'\-\[\]\\ ]>/     : return charAt(ch, 0);
    
    default: throw "missed a case <c>";
  }
}

test getCharacter((Character) `a`)    == charAt("a", 0);
test getCharacter((Character) `\\`)   == charAt("\\", 0);
test getCharacter((Character) `\'`)   == charAt("\'", 0);
test getCharacter((Character) `\1`)   == toInt("01");
test getCharacter((Character) `\12`)  == toInt("012");
test getCharacter((Character) `\123`) == toInt("0123");
test getCharacter((Character) `\n`)   == 110;

// ----- getAttributes, getAttribute, getAssociativity -----

public Attributes getAttributes({languages::sdf2::syntax::Sdf2ForRascal::Attribute ","}* mods) {
  return attrs([getAttribute(m) | languages::sdf2::syntax::Sdf2ForRascal::Attribute m <- mods]);
}

test getAttributes(({Attribute ","}*) `left, cons("decl")`) == attrs([\assoc(\left()),term(cons("decl"))]);

public Attr getAttribute(languages::sdf2::syntax::Sdf2ForRascal::Attribute m) {
  switch (m) {
    case (Attribute) `<languages::sdf2::syntax::Sdf2ForRascal::Associativity as>`:
     	return \assoc(getAssociativity(as));
     	
    case (Attribute) `bracket`:
    	return \bracket();
    
    case (Attribute) `cons(<StrCon c>)` :
    	return term("cons"(unescape(c)));
    	
    case (Attribute) `memo`:
    	return \memo();
    	
    default: throw "missed a case <m>";
  }
}

test getAttribute((Attribute) `left`)        == \assoc(left());
test getAttribute((Attribute) `cons("abc")`) == term("cons"("abc"));

private Associativity getAssociativity(languages::sdf2::syntax::Sdf2ForRascal::Associativity as){
  switch (as) {
    case (Associativity) `left`: return left();
    case (Associativity) `right`: return right();
    case (Associativity) `non-assoc`: return \non-assoc();
    case (Associativity) `assoc`: return \assoc();
    default: throw "missed a case <as>";
  }
}
 
test getAssociativity((Associativity) `left`) == left();

private list[Symbol] separgs2symbols({languages::sdf2::syntax::Sdf2ForRascal::Symbol ","}+ args, bool isLex) {
  return [ getSymbol(s, isLex) | languages::sdf2::syntax::Sdf2ForRascal::Symbol s <- args ];
}
