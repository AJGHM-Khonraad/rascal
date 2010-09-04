module rascal::parser::Generator

import rascal::parser::Grammar;
import rascal::parser::Parameters;
import rascal::parser::Regular;
import rascal::parser::Normalization;
import rascal::parser::Lookahead;
import ParseTree;
import String;
import List;
import Node;
import Set;
import IO;
import Exception;

private data Item = item(Production production, int index);

// TODO: replace this complex data structure with several simple ones
private alias Items = map[Symbol,map[Item item, tuple[str new, int itemId] new]];

public str generate(str package, str name, Grammar gr){
    println("expanding parameterized symbols");
    gr = expandParameterizedSymbols(gr);
    
    println("generating stubs for regular");
    gr = makeRegularStubs(gr);
    
    println("generating item allocations");
    newItems = generateNewItems(gr);
    
    println("computing priority and associativity filter");
    dontNest = computeDontNests(newItems, gr);
    
    println("computing lookahead sets");
    gr = computeLookaheads(gr);
    
    println("optimizing lookahead automaton");
    gr = compiledLookaheads(gr);
    
    println("printing the source code of the parser class");
    uniqueProductions = {p | /Production p := gr, prod(_,_,_) := p || regular(_,_) := p};
    
    return 
"
package <package>;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.exceptions.FactTypeUseException;
import org.eclipse.imp.pdb.facts.io.StandardTextReader;
import org.rascalmpl.parser.sgll.SGLL;
import org.rascalmpl.parser.sgll.stack.*;
import org.rascalmpl.parser.sgll.util.IntegerKeyedHashMap;
import org.rascalmpl.parser.sgll.util.IntegerList;
import org.rascalmpl.values.uptr.Factory;

public class <name> extends SGLL{
	private static IConstructor read(java.lang.String s, org.eclipse.imp.pdb.facts.type.Type type){
		try{
			return (IConstructor) new StandardTextReader().read(vf, org.rascalmpl.values.uptr.Factory.uptr, type, new ByteArrayInputStream(s.getBytes()));
		}catch(FactTypeUseException e){
			throw new RuntimeException(\"unexpected exception in generated parser\", e);  
		}catch(IOException e){
			throw new RuntimeException(\"unexpected exception in generated parser\", e);  
		}
	}
	
	private static final IntegerKeyedHashMap\<IntegerList\> dontNest;
	
    private static void putDontNest(int i, int j) {
    	IntegerList donts = dontNest.get(i);
    	if(donts == null){
    		donts = new IntegerList();
    		dontNest.put(i, donts);
    	}
    	donts.add(j);
    }
    
    static{
      dontNest = new IntegerKeyedHashMap\<IntegerList\>();
      <for(<int parent, int child> <- dontNest) {>
      putDontNest(<parent>,<child>); <}>
    }
    
	protected boolean isPrioFiltered(int parentItem, int child) {
		IntegerList donts = dontNest.get(parentItem);
		if(donts == null) return false;
		return donts.contains(child);
	}
	
    // Production declarations
	<for (p <- uniqueProductions) {>
	switch(p){
	case prod(_,lit(_),\no-attrs()):
	default:	
	private static final IConstructor <value2id(p)> = read(\"<esc("<p>")>\", Factory.Production);
	}
	<}>
    
	// Item declarations
	<for (Symbol s <- newItems, lit(_) !:= s) { items = newItems[s]; >
	private static class <value2id(s)>{
		<for (Item i <- items) {>
		private static final AbstractStackNode <value2id(i.production)>_<value2id(i.index)> = <items[i].new>;
		<}>
	}
	<}>
	
	public <name>(){
		super();
	}
	
	// Parse methods    
	<for (Symbol nont <- gr.rules, isNonterminal(nont)) { >
		<generateParseMethod(newItems, choice(nont, gr.rules[nont]))>
	<}>
}
";
}  

@doc{This function generates Java code to allocate a new item for each position in the grammar.
We first collect these in a map, such that we can generate static fields. It's a simple matter of caching
constants to improve run-time efficiency of the generated parser}
private map[Symbol,map[Item,tuple[str new, int itemId]]] generateNewItems(Grammar g) {
  map[Symbol,map[Item,tuple[str new, int itemId]]] items = ();
  int counter = 0;
  map[Item,tuple[str new, int itemId]] fresh = ();
  int newItem() { counter += 1; return counter; }
  
  visit (g) {
    case Production p:prod([],Symbol s,_) : 
       items[s]?fresh += (item(p, 0):<"new EpsilonStackNode(<newItem()>)", counter>);
    case Production p:prod(list[Symbol] lhs, Symbol s,_) : 
      for (int i <- index(lhs)) 
        items[s]?fresh += (item(p, i): sym2newitem(g, lhs[i], newItem));
    case Production p:regular(Symbol s, _) :
      switch(s) {
        case \iter(Symbol elem) : 
          items[s]?fresh += (item(p,0):sym2newitem(g, elem, newItem));
        case \iter-star(Symbol elem) : 
          items[s]?fresh += (item(p,0):sym2newitem(g, elem, newItem));
        case \iter-sep(Symbol elem, list[Symbol] seps) : {
          items[s]?fresh += (item(p,0):sym2newitem(g, elem, newItem));
          for (int i <- index(seps)) 
            items[s]?fresh += (item(p,i+1):sym2newitem(g, seps[i], newItem));
        }
        case \iter-star-sep(Symbol elem, list[Symbol] seps) : {
          items[s]?fresh += (item(p,0):sym2newitem(g, elem, newItem));
          for (int i <- index(seps)) 
            items[s]?fresh += (item(p,i+1):sym2newitem(g, seps[i], newItem));
        } 
     }
  }
  return items;
}

@doc{this function selects all symbols for which a parse method should be generated}
private bool isNonterminal(Symbol s) {
  switch (s) {
    case \sort(_) : return true;
    case \prime(sort(_),_,_) : return true;
    case \parameterized-sort(_,_) : return true;
    case \prime(\parameterized-sort(_,_)) : return true;
    case \start(_) : return true;
    case \layouts(_) : return true;
    default: return false;
  }
}

public str generateParseMethod(Items items, Production p) {
  return "public void <sym2name(p.rhs)>() {
            char next = input[location];
            <generateExpect(items, p)>
          }";
}

public str generateExpect(Items items, Production p){
    // note that this code heavily leans on the fact that production combinators are normalized 
    // (distribution and factoring laws have been applied to put a production expression in canonical form)
    
    switch (p) {
      case prod(_,_,_) :
        return "// <p>\n\texpect(<value2id(p)>, <generateSymbolItemExpects(p)>);"; 
      case lookahead(_, classes, Production p) :
        return "if (<generateClassConditional(classes)>) {
                  <generateExpect(items, p)>
               }";
      case choice(_, {lookahead(_, classes, Production p), set[Production] rest}) :
        return "if (<generateClassConditional(classes)>) {
                  <generateExpect(items, p)>
                } else {
                  <generateExpect(items, choice(p.rhs, rest))>
                }";
      case choice(_, set[Production] ps) :
        return "<for (Production q <- ps){><generateExpect(items, q)>
                <}>";
      case restrict(_, Production p, set[Production] restrictions) : 
        return generateExpect(items, p);
      case diff(_, Production n, set[Production] rejects) :
        return "<for (Production q <- rejects){>expectReject(<value2id(q)>, <generateSymbolItemExpects(q)>);
                <}>
                <generateExpect(items, n)>";
      case first(_, list[Production] ps) : 
        throw "unexpected first at generation time";
        // return generateExpect(items, choice(p.rhs, { q | q <- ps }));
      case \assoc(_,_,set[Production] ps) :
        throw "unexpected assoc at generation time";
        // return generateExpect(items, choice(p.rhs, ps)); 
    }
    
    throw "not implemented <p>";
}

str generateClassConditional(set[Symbol] classes) {
  return (eoi() in classes ? "location == input.length()" : "true" 
         | " || <generateRangeConditional(range)>"
         | \char-class(list[CharRange] ranges) <- classes, CharRange range <- ranges); 
}

str generateRangeConditional(CharRange range) {
  switch (range) {
    case single(int i) : return "(next == (char) <i>)";
    case range(1,65535) : return "(true /*every char*/)";
    case range(int i, i) : return "(next == (char) <i>)";
    case range(int i, int j) : return "((next \>= (char) <i>) && (next \<= (char) <j>))";
    default: throw "unexpected range type: <range>";
  }
}

rel[int,int] computeDontNests(Items items, Grammar grammar) {
  // first we compute a map from productions to their first items
  prodItems = ( p:items[rhs][item(p,0)].itemId | /Production p:prod(_,Symbol rhs, _) := grammar);
   
  return {computeDontNests(items, prodItems, p) | Symbol s <- grammar.rules, Production p <- grammar.rules[s]};
}

rel[int,int] computeDontNests(Items items, map[Production, int] prodItems, Production p) {
  switch (p) {
    case prod(_,_,attrs([_*,\assoc(Associativity a),_*])) : 
      return computeAssociativities(items, prodItems, a, {p});
    case prod(_,_,_) : return {};
    case regular(_,_) : return {};
    case choice(_, set[Production] alts) : 
      return { computeDontNests(items, prodItems, a) | a <- alts };
    case diff(_,Production a,_) :
      return computeDontNests(items, prodItems, a);
    case restrict(_,Production a,_) :
      return computeDontNests(items, prodItems,a);
    case first(_, list[Production] levels) : 
      return computePriorities(items, prodItems, levels);
    case \assoc(_, Associativity a, set[Production] alts) :
      return computeAssociativities(items, prodItems, a, alts);
    case \lookahead(_,_,q) :
      return computeDontNests(items,prodItems,q); 
    case \others(_) : return {};
    default:
      throw "missed a case <p>";
  }
}

rel[int,int] computeAssociativities(Items items, map[Production, int] prodItems, Associativity a, set[Production] alts) {
  result = {};
  // assoc is not transitive, but it is reflexive
  
  switch (a) {
    case \left(): {
      for (Production p1 <- alts, Production p2:prod(lhs:[_*,Symbol r],Symbol rhs,_) <- alts, symbolMatch(r,rhs)) {
        result += {<items[rhs][item(p2,size(lhs)-1)].itemId,prodItems[p1]>};
      }  
    }
    case \assoc():
      for (Production p1 <- alts, Production p2:prod(lhs:[_*,Symbol r],Symbol rhs,_) <- alts, symbolMatch(r,rhs)) {
        result += {<items[rhs][item(p2,size(lhs)-1)].itemId,prodItems[p1]>};
      }
    case \right():
      for (Production p1 <- alts, Production p2:prod(lhs:[Symbol l,_*],Symbol rhs,_) <- alts, symbolMatch(l,rhs)) {
        result += {<items[rhs][item(p2,0)].itemId,prodItems[p1]>};
      }
    case \non-assoc(): {
      for (Production p1 <- alts, Production p2:prod(lhs:[_*,Symbol r],Symbol rhs,_) <- alts, symbolMatch(r,rhs)) {
        result += {<items[rhs][item(p2,size(lhs)-1)].itemId,prodItems[p1]>};
      }
      for (Production p1 <- alts, Production p2:prod(lhs:[Symbol l,_*],Symbol rhs,_) <- alts, symbolMatch(l,rhs)) {
        result += {<items[rhs][item(p2,0)].itemId,prodItems[p1]>};
      }
    }
  }
  
  return result;
}

rel[int,int] computePriorities(Items items, map[Production, int] prodItems, list[Production] levels) {
  // collect basic filter
  ordering = { <p1,p2> | [pre*,Production p1, Production p2, post*] := levels };

  // flatten nested structure to obtain direct relations
  todo = ordering;
  ordering = {};
  while (todo != {}) {
    <prio,todo> = takeOneFrom(todo);
    switch (prio) {
      case <choice(_,set[Production] alts),Production p2> :
        todo += alts * {p2};
      case <Production p1, choice(_,set[Production] alts)> :
        todo += {p1} * alts;
      case <\assoc(_,_,set[Production] alts),Production p2> :
        todo += alts * {p2};
      case <Production p1, \assoc(_,_,set[Production] alts)> :
        todo += {p1} * alts;
      default:
        ordering += prio;
    }
  }
  
  ordering = ordering+; // priority is transitive
  result = {};
  for (<Production p1, Production p2> <- ordering) {
    switch (p1) {
      case prod(lhs:[Symbol l,_*,Symbol r],Symbol rhs,_) :
        if (symbolMatch(l,rhs) && symbolMatch(l,rhs)) {
          result += {<items[rhs][item(p1,0)].itemId,prodItems[p2]>,<items[rhs][item(p1,size(lhs) - 1)].itemId,prodItems[p2]>};   
        }
        else fail;
      case prod(lhs:[Symbol l,_*],Symbol rhs,_) :
        if (symbolMatch(l,rhs)) {
          result += {<items[rhs][item(p1,0)].itemId,prodItems[p2]>};   
        }
        else fail;
      case prod(lhs:[_*,Symbol r],Symbol rhs,_) :
        if (symbolMatch(r,rhs)) {
          result += {<items[rhs][item(p1,size(lhs) - 1)].itemId,prodItems[p2]>};   
        }
        else fail;
    }
  }
  
  // and we recurse to find the nested associativity declarations
  return result + { computeDontNests(items, prodItems, l) | l <- levels };
}                     

private bool symbolMatch(Symbol checked, Symbol referenced) {
  return referenced == checked || label(_, referenced) := checked;
}

@doc{
  generate stack nodes for the restrictions. Note that although the abstract grammar for restrictions
  may seem pretty general (i.e. any symbol can be a restriction), we actually only allow finite languages
  defined as either sequences of character classes or literals.
}
public str generateRestrictions(Grammar grammar, int() id, set[Production] restrictions) {
  result = "new IMatchableStackNode[] {"; 
  
  // not that only single symbol restrictions are allowed at the moment.
  // the run-time only supports character-classes and literals BTW, which should
  // be validated by a static checker for Rascal.  
  las = [ l | /Production p:prod([Symbol l],_,_) <- restrictions];
 
  if (las != []) {
    result += ("<sym2newitem(grammar, head(las), id).new>" | it + ", <sym2newitem(grammar,l,id).new>" | l <- tail(las));
  }
  
  result += "}";
  return result;
}

@doc{
  generate stack nodes for the restrictions. Note that although the abstract grammar for restrictions
  may seem pretty general (i.e. any symbol can be a restriction), we actually only allow finite languages
  defined as either sequences of character classes or literals.
}
public str generateRestrictions(set[Production] restrictions) {
  result = "new IMatchableStackNode[] {"; 
  
  // not that only single symbol restrictions are allowed at the moment.
  // the run-time only supports character-classes and literals BTW, which should
  // be validated by a static checker for Rascal.  
  las = [ p | /Production p:prod([Symbol l],_,_) <- restrictions];
 
  if (las != []) {
    result += ("<value2id(item(head(las),0))>" | it + ", <value2id(item(l,0))>" | l <- tail(las));
  }
  
  result += "}";
  return result;
}

public str generateSymbolItemExpects(Production prod){
    if(prod.lhs == []){
        return value2id(item(prod, 0));
    }
    
    return ("<value2id(item(prod, 0))>" | it + ",\n\t\t" + value2id(item(prod, i+1)) | int i <- index(tail(prod.lhs)));
}

public str generateSeparatorExpects(Production reg, list[Symbol] seps) {
   if (seps == []) {
     return "";
   }
   
   return (value2id(item(reg, 1)) | it + ", <value2id(item(reg, i+2))>" | int i <- index(seps));
}

public str generateSeparatorExpects(Grammar grammar, int() id, list[Symbol] seps) {
   if (seps == []) {
     return "";
   }
   
   return (sym2newitem(grammar, head(seps), id).new | it + ", <sym2newitem(grammar,s,id).new>" | s <- seps);
}

public str literals2ints(list[Symbol] chars){
    if(chars == []) return "";
    
    str result = "<head(head(chars).ranges).start>";
    
    for(ch <- tail(chars)){
        result += ",<head(ch.ranges).start>";
    }
    
    return result;
}

// TODO
public str ciliterals2ints(list[Symbol] chars){
    throw "case insensitive literals not yet implemented by parser generator";
}

public tuple[str new, int itemId] sym2newitem(Grammar grammar, Symbol sym, int() id){
    itemId = id();
    
    switch(sym){
        case \label(_,s) : return sym2newitem(grammar, s,id); // ignore labels
        case \prime(_,_,_) : 
            return <"new NonTerminalStackNode(<itemId>, \"<sym2name(sym)>\")", itemId>;
        case \sort(n) : 
            return <"new NonTerminalStackNode(<itemId>, \"<sym2name(sym)>\")", itemId>;
        case \layouts(_) :
            return <"new NonTerminalStackNode(<itemId>, \"<sym2name(sym)>\")", itemId>;  
        case \parameterized-sort(n,args): 
            return <"new NonTerminalStackNode(<itemId>, \"<sym2name(sym)>\")", itemId>;
        case \parameter(n) :
            throw "all parameters should have been instantiated by now";
        case \start(s) : 
            return <"new NonTerminalStackNode(<itemId>, \"<sym2name(sym)>\")", itemId>;
        case \lit(l) : {
            Production litProd = choice(sym, grammar.rules[sym]);
            
            if (restrict(\lit(l), choice(\lit(l), {p:prod(list[Symbol] chars,\lit(l),_)}), restrictions) := litProd) { 
                return <"new LiteralStackNode(<itemId>, <value2id(p)>, <generateRestrictions(grammar,id,restrictions)>, new char[] {<literals2ints(chars)>})",itemId>;
            }
            else if (p:prod(chars,\lit(l),_) := litProd) {  
                return <"new LiteralStackNode(<itemId>, <value2id(p)>, new char[] {<literals2ints(chars)>})",itemId>;
            }
            else if (choice(lit(l),{p:prod(chars,\lit(l),_)}) := litProd) {  
                return <"new LiteralStackNode(<itemId>, <value2id(p)>, new char[] {<literals2ints(chars)>})",itemId>;
            }
            throw "literal not found in <grammer>??";
        }
        case \cilit(l) : {
            throw "ci lits not supported yet";
        }
        case \iter(s) : 
            return <"new ListStackNode(<itemId>, <value2id(regular(sym,\no-attrs()))>, <sym2newitem(grammar, s, id).new>, true)",itemId>;
        case \iter-star(s) :
            return <"new ListStackNode(<itemId>, <value2id(regular(sym,\no-attrs()))>, <sym2newitem(grammar, s, id).new>, false)", itemId>;
        case \iter-seps(Symbol s,list[Symbol] seps) : {
            reg = regular(sym,\no-attrs());
            return <"new SeparatedListStackNode(<itemId>, <value2id(reg)>, <sym2newitem(grammar, s, id).new>, new AbstractStackNode[]{<generateSeparatorExpects(grammar,id,seps)>}, true)",itemId>;
        }
        case \iter-star-seps(Symbol s,list[Symbol] seps) : {
            reg = regular(sym,\no-attrs());
            return <"new SeparatedListStackNode(<itemId>, <value2id(reg)>, <sym2newitem(grammar, s, id).new>, new AbstractStackNode[]{<generateSeparatorExpects(grammar,id,seps)>}, false)",itemId>;
        }
        case \opt(s) : {
            reg =  regular(sym,\no-attrs());
            return <"new OptionalStackNode(<itemId>, <value2id(reg)>, <sym2newitem(grammar, s, id).new>)", itemId>;
        }
        case \char-class(list[CharRange] ranges) : 
            return <"new CharStackNode(<itemId>, new char[][]{<generateCharClassArrays(ranges)>})", itemId>;
        case \start-of-line() : 
            return <"new StartOfLineStackNode(<itemId>)", itemId>;
        case \end-of-line() :
            return <"new EndOfLineStackNode(<itemId>)", itemId>;
        case \at-column(int column) :
            return <"new AtColumnStackNode(<itemId>, <column>)",itemId>; 
        default: 
            throw "not yet implemented <sym>";
    }
}

public str generateCharClassArrays(list[CharRange] ranges){
    if(ranges == []) return "";
    result = "";
    if(range(from, to) := head(ranges)) 
        result += "{<from>,<to>}";
    for(range(from, to) <- tail(ranges))
        result += ",{<from>,<to>}";
    return result;
}

public str esc(Symbol s){
    return esc("<s>");
}

private map[str,str] javaStringEscapes = ( "\n":"\\n", "\"":"\\\"", "\t":"\\t", "\r":"\\r","\\u":"\\\\u","\\":"\\\\");

public str esc(str s){
    return escape(s, javaStringEscapes);
}

private map[str,str] javaIdEscapes = javaStringEscapes + ("-":"_");

public str escId(str s){
    return escape(s, javaIdEscapes);
}

public str sym2name(Symbol s){
    switch(s){
        case sort(x) : return x;
        default      : return value2id(s);
    }
}

public str sym2id(Symbol s){
    return "symbol_<value2id(s)>";
}

map[value,str] idCache = ();

public str value2id(value v) {
  return idCache[v]? str () { idCache[v] = v2i(v); return idCache[v]; }();
}

str v2i(value v) {
    switch (v) {
        case item(p:prod(_,Symbol u,_), int i) : return "<value2id(u)>.<value2id(p)>_<value2id(i)>";
        // case prod(_,Symbol u,attrs([a*,term(cons(str name)),b*])) : return "prod_<value2id(u)>_<escId(name)>_<value2id(a)>_<value2id(b)>";
        case label(str x,Symbol u) : return escId(x) + "_" + value2id(u);
        case prime(Symbol s, str reason, list[int] indexes) : return "<value2id(s)>_<reason>_<value2id(indexes)>";
        case sort(str s)   : return s;
        case \parameterized-sort(str s, list[Symbol] args) : return ("<s>_" | it + "_<value2id(arg)>" | arg <- args);
        case cilit(str s)  : return "cilit_<s>";
	    case lit(/<s:^[A-Za-z0-9]+$>/) : return "lit_<s>"; 
        case int i         : return i < 0 ? "min_<-i>" : "<i>";
        case str s         : return ("" | it + "_<charAt(s,i)>" | i <- [0..size(s)-1]);
        case str s()       : return escId(s);
        case node n        : return "<escId(getName(n))>_<("" | it + "_" + value2id(c) | c <- getChildren(n))>";
        case list[value] l : return ("" | it + "_" + value2id(e) | e <- l);
        default            : throw "value not supported <v>";
    }
}
