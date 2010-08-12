module rascal::parser::Generator

import rascal::parser::Grammar;
import rascal::parser::Priority;
import rascal::parser::Parameters;
import rascal::parser::Regular;
import rascal::parser::Normalization;
import rascal::parser::Definition;
import ParseTree;
import String;
import List;
import Node;
import Set;
import IO;

private int itemId = 0;
private Grammar grammar = grammar({},{});

private int nextItem(){
    int id = itemId;
    itemId += 1;
    return id;
}

public str generate(str package, str name, loc mod) {
  return generate(package, name, module2grammar(parse(#Module, mod)));
}

public str generate(str package, str name, Grammar gr){
    itemId = 0;
    
    grammar = expandParameterizedSymbols(gr);
    grammar.productions += makeRegularStubs(grammar);
    grammar = factorize(grammar);
    g = grammar;
    return 
"
package <package>;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.exceptions.FactTypeUseException;
import org.eclipse.imp.pdb.facts.io.StandardTextReader;
import org.eclipse.imp.pdb.facts.type.Type;
import org.rascalmpl.parser.sgll.SGLL;
import org.rascalmpl.parser.sgll.stack.*;
import org.rascalmpl.values.uptr.Factory;
import org.rascalmpl.values.uptr.SymbolAdapter;

public class <name> extends SGLL {
    private static IConstructor read(java.lang.String s, Type type) {
        try {
          return (IConstructor) new StandardTextReader().read(vf, org.rascalmpl.values.uptr.Factory.uptr, type, new ByteArrayInputStream(s.getBytes()));
        }
        catch(FactTypeUseException e){
          throw new RuntimeException(\"unexpected exception in generated parser\", e);  
        }
        catch(IOException e){
          throw new RuntimeException(\"unexpected exception in generated parser\", e);  
      }
    }
  
    // Production declarations
    <for (p <- { p | /Production p := g, prod(_,_,_) := p || regular(_,_) := p}) {>private static final IConstructor <value2id(p)> = read(\"<esc("<removePrimes(p)>")>\", Factory.Production);
    <}>
    
    public <name>(){
        super();
    }
    
    // Parse methods    
    <for (Production p <- g.productions){>
        <generateParseMethod(p)>
    <}>
    
    public IValue parse(IConstructor start, URI inputURI, char[] sentence){
        if(SymbolAdapter.isSort(start)){
            return parse(new NonTerminalStackNode(-1, SymbolAdapter.getName(start)), inputURI, sentence);
        }
        if(SymbolAdapter.isStartSort(start)){
            return parse(SymbolAdapter.getStart(start), inputURI, sentence);
        }
        throw new IllegalArgumentException(start.toString());
    }
    
    public IValue parse(IConstructor start, URI inputURI, java.lang.String sentence){
        if(SymbolAdapter.isSort(start)){
            return parseFromString(new NonTerminalStackNode(-1, SymbolAdapter.getName(start)), inputURI, sentence);
        }
        if(SymbolAdapter.isStartSort(start)){
            return parse(SymbolAdapter.getStart(start), inputURI, sentence);
        }
        throw new IllegalArgumentException(start.toString());
    }
    
    public IValue parse(IConstructor start, URI inputURI, File inputFile) throws IOException{
        if(SymbolAdapter.isSort(start)){
            return parseFromFile(new NonTerminalStackNode(-1, SymbolAdapter.getName(start)), inputURI, inputFile);
        }
        if(SymbolAdapter.isStartSort(start)){
            return parse(SymbolAdapter.getStart(start), inputURI, inputFile);
        }
        throw new IllegalArgumentException(start.toString());
    }
    
    public IValue parse(IConstructor start, URI inputURI, InputStream in) throws IOException{
        if(SymbolAdapter.isSort(start)){
            return parseFromStream(new NonTerminalStackNode(-1, SymbolAdapter.getName(start)), inputURI, in);
        }
        if(SymbolAdapter.isStartSort(start)){
            return parse(SymbolAdapter.getStart(start), inputURI, in);
        }
        throw new IllegalArgumentException(start.toString());
    }
    
    public IValue parse(IConstructor start, URI inputURI, Reader in) throws IOException{
        if(SymbolAdapter.isSort(start)){
            return parseFromReader(new NonTerminalStackNode(-1, SymbolAdapter.getName(start)), inputURI, in);
        }
        if(SymbolAdapter.isStartSort(start)){
            return parse(SymbolAdapter.getStart(start), inputURI, in);
        }
        throw new IllegalArgumentException(start.toString());
    }
}
";
}  


public str generateParseMethod(Production p){
    // note that this code heavily leans on the fact that production combinators are normalized 
    // (distribution and factoring laws have been applied to put a production expression in canonical form)
    
    if(prod(_,Symbol rhs,_) := p){
        return "public void <sym2name(rhs)>(){
            // <p>
            expect(<value2id(p)>,
            <generateSymbolItemExpects(p.lhs)>);  
        }";
    }

    if(choice(Symbol rhs, set[Production] ps) := p){
    println("choice prod");
        return "public void <sym2name(rhs)>(){
            <for (Production q:prod(_,_,_) <- ps){>
                // <q>
                expect(<value2id(q)>,
                <generateSymbolItemExpects(q.lhs)>);
            <}>  
        }";
    }
    
    if (restrict(Symbol rhs, choice(rhs, set[Production] ps), set[list[Symbol]] restrictions) := p) {
       str lookaheads = generateLookaheads(restrictions);
       return "public void <sym2name(rhs)>() {
             <for (Production q:prod(_,_,_) <- ps){>
                // <q>
                expect(<value2id(q)>, <lookaheads>,
                <generateSymbolItemExpects(q.lhs)>);
            <}>         
       }";
    }
    
    if (diff(Symbol rhs, choice(rhs, set[Production] choices), set[Production] rejects) := p) {
       return "public void <sym2name(rhs)>() {
            <for (Production q:prod(_,_,_) <- rejects){>
                // <q>
                expectReject(<value2id(q)>,
                <generateSymbolItemExpects(q.lhs)>);
            <}>
            <for (Production q:prod(_,_,_) <- choices){>
                // <q>
                expect(<value2id(q)>,
                <generateSymbolItemExpects(q.lhs)>);
            <}>
       }";
    }
    
    if (diff(Symbol rhs, restrict(Symbol rhs, choice(rhs, set[Production] choices), set[list[Symbol]] restrictions), set[Production] rejects) := p) {
       str lookaheads = generateLookaheads(restrictions);
       return "public void <sym2name(rhs)>() {
            <for (Production q:prod(_,_,_) <- rejects){>
                // <q>
                expectReject(<value2id(q)>, <lookaheads>, 
                <generateSymbolItemExpects(q.lhs)>);
            <}>
            <for (Production q:prod(_,_,_) <- choices){>
                // <q>
                expect(<value2id(q)>, <lookaheads>, 
                <generateSymbolItemExpects(q.lhs)>);
            <}>
       }";
    }
    
    if (regular(_,_) := p) {
        // do not occur as defined symbols
        return "";
    }
    
    throw "not implemented <p>";
}

@doc{
  generate stack nodes for the restrictions. Note that although the abstract grammar for restrictions
  may seem pretty general (i.e. any symbol can be a restriction), we actually only allow finite languages
  defined as either sequences of character classes or literals.
}
public str generateLookaheads(set[list[Symbol]] restrictions) {
  result = "new IReducableStackNode[] {"; 

  // not that only single symbol restrictions are allowed at the moment.
  // the run-time only supports character-classes and literals BTW, which should
  // be validated by a static checker for Rascal.  
  for ([Symbol l] <- restrictions) {
     result += sym2newitem(l);
  }
  
  result += "}";
  return result;
}

public str generateSymbolItemExpects(list[Symbol] syms){
    if(syms == []){
        return "new EpsilonStackNode(<nextItem()>)";
    }
    
    return ("<sym2newitem(head(syms))>" | it + ",\n\t\t" + sym2newitem(sym) | sym <- tail(syms));
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

public str sym2newitem(Symbol sym){
    int id = nextItem();
    switch(sym){
        case \label(_,s) : return sym2newitem(s); // ignore labels
        case \prime(_,_,_) : 
            return "new NonTerminalStackNode(<id>, \"<sym2name(sym)>\")";
        case \sort(n) : 
            return "new NonTerminalStackNode(<id>, \"<sym2name(sym)>\")";
        case \parameterized-sort(n,args): 
            return "new NonTerminalStackNode(<id>, \"<sym2name(sym)>\")";
        case \parameter(n) :
            throw "all parameters should have been instantiated by now";
        case \start(s) : 
            return "new NonTerminalStackNode(<id>, \"<sym2name(sym)>\")";
        case \lit(l) : {
            if (/p:prod(chars,\lit(l),_) := grammar)  
                return "new LiteralStackNode(<id>, <value2id(p)>, new char[] {<literals2ints(chars)>})";
            throw "literal not found in <g>??";
        }
        case \cilit(l) : {
            throw "ci lits not supported yet";
        }
        case \iter(s) : 
            return "new ListStackNode(<id>, <value2id(regular(sym,\no-attrs()))>, <sym2newitem(s)>, true)";
        case \iter-star(s) :
            return "new ListStackNode(<id>, <value2id(regular(sym,\no-attrs()))>, <sym2newitem(s)>, false)";
        case \iter-seps(Symbol s,list[Symbol] seps) : 
            return "new SeparatedListStackNode(<id>, <value2id(regular(sym,\no-attrs()))>, <sym2newitem(s)>, new AbstractStackNode[]{<generateSymbolItemExpects(seps)>}, true)";
        case \iter-star-seps(Symbol s,list[Symbol] seps) : 
            return "new SeparatedListStackNode(<id>, <value2id(regular(sym,\no-attrs()))>, <sym2newitem(s)>, new AbstractStackNode[]{<generateSymbolItemExpects(seps)>}, false)";
        case \opt(s) : 
            return "new OptionalStackNode(<id>, <value2id(regular(sym,\no-attrs()))>, <sym2newitem(s)>)";
        case \char-class(list[CharRange] ranges) : 
            return "new CharStackNode(<id>, new char[][]{<generateCharClassArrays(ranges)>})";
        case \layout() :
            return "new NonTerminalStackNode(<id>, \"<sym2name(sym)>\")";   
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

public Production removePrimes(Production p) {
  return visit(p) {
    case prime(Symbol s,_,_) => s
  }
}

public str value2id(value v){
    switch(v){
        case label(_,v)    : return value2id(v);
        case prime(Symbol s, str reason, list[int] indexes) : return "<value2id(s)>_<reason>_<value2id(indexes)>";
        case sort(str s)   : return s;
        case \parameterized-sort(str s, list[Symbol] args) : return ("<s>_" | it + "_<value2id(arg)>" | arg <- args);
        case cilit(str s)  : return "cilit_<s>";
	    case lit(/<s:^[A-Za-z0-9]+$>/) : return "lit_<s>"; 
        case int i         : return "<i>";
        case str s         : return ("" | it + "_<charAt(s,i)>" | i <- [0..size(s)-1]);
        case node n        : return "<escId(getName(n))>_<("" | it + "_" + value2id(c) | c <- getChildren(n))>";
        case list[value] l : return ("" | it + "_" + value2id(e) | e <- l);
        default            : throw "value not supported <v>";
    }
}
