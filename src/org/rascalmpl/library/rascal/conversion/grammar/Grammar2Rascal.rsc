module rascal::conversion::grammar::Grammar2Rascal

// Convert the Rascal internal grammar representation format (Grammar) to 
// a syntax definition in Rascal source code

// TODO:
// - Howto translate a lexical rule/restriction/priority? Where do we obtain this info?
// - Escaping is not yet fullproof

import rascal::parser::Grammar;
import IO;
import Set;
import List;
import String;
import ParseTree;

public str grammar2rascal(Grammar g, str name) {
  return "module <name> <grammar2rascal(g)>";
}

public str grammar2rascal(Grammar g) {
  return ( "" | it + topProd2rascal(p) | Production p <- g.productions);
}

public Grammar Pico =
grammar({sort("PROGRAM")}, {
	prod([sort("EXP"),lit("||"),sort("EXP")],sort("EXP"),\no-attrs()),
	prod([sort("PICO-ID"),lit(":"),sort("TYPE")],sort("ID-TYPE"),\no-attrs()),
	prod([lit("\\\t")],sort("StrChar"),attrs([term(cons("tab"))])),
	prod([lit("nil-type")],sort("TYPE"),\no-attrs()),
	prod([lit("string")],sort("TYPE"),\no-attrs()),
	prod([lit("\\\\")],sort("StrChar"),attrs([term(cons("backslash"))])),
	first(sort("EXP"),[prod([sort("EXP"),lit("||"),sort("EXP")],sort("EXP"),\no-attrs()),prod([sort("EXP"),lit("-"),sort("EXP")],sort("EXP"),\no-attrs()),prod([sort("EXP"),lit("+"),sort("EXP")],sort("EXP"),\no-attrs())]),
	prod([sort("StrCon")],sort("EXP"),\no-attrs()),
	prod([complement(\char-class([range(0,25),range(34,34),range(49,49),range(92,92)]))],sort("StrChar"),attrs([term(cons("normal"))])),
	prod([lit("if"),sort("EXP"),lit("then"),\iter-star-seps(sort("STATEMENT"),[\layout(),lit(";"),\layout()]),lit("else"),\iter-star-seps(sort("STATEMENT"),[\layout(),lit(";"),\layout()]),lit("fi")],sort("STATEMENT"),\no-attrs()),
	prod([iter(\char-class([range(48,57)]))],sort("NatCon"),attrs([term(cons("digits"))])),
	prod([\char-class([range(34,34)]),label("chars",\iter-star(sort("StrChar"))),\char-class([range(34,34)])],sort("StrCon"),attrs([term(cons("default"))])),
	prod([lit("\\\n")],sort("StrChar"),attrs([term(cons("newline"))])),
	prod([lit("\\\\\"")],sort("StrChar"),attrs([term(cons("quote"))])),
//	restrict(sort("NatCon"),others(sort("NatCon")),[\char-class([range(48,57)])]),
	prod([\char-class([range(9,10),range(13,13),range(32,32)])],sort("LAYOUT"),attrs([term(cons("whitespace"))])),
	prod([lit("\\"),label("a",\char-class([range(48,57)])),label("b",\char-class([range(48,57)])),label("c",\char-class([range(48,57)]))],sort("StrChar"),attrs([term(cons("decimal"))])),
	prod([lit("while"),sort("EXP"),lit("do"),\iter-star-seps(sort("STATEMENT"),[\layout(),lit(";"),\layout()]),lit("od")],sort("STATEMENT"),\no-attrs()),
	prod([lit("declare"),\iter-star-seps(sort("ID-TYPE"),[\layout(),lit(","),\layout()]),lit(";")],sort("DECLS"),\no-attrs()),
//	restrict(opt(sort("LAYOUT")),others(opt(sort("LAYOUT"))),[\char-class([range(9,10),range(13,13),range(32,32)])]),
	prod([sort("PICO-ID")],sort("EXP"),\no-attrs()),
	prod([sort("EXP"),lit("+"),sort("EXP")],sort("EXP"),\no-attrs()),
	prod([lit("("),sort("EXP"),lit(")")],sort("EXP"),\no-attrs()),
	prod([sort("NatCon")],sort("EXP"),\no-attrs()),
	prod([sort("EXP"),lit("-"),sort("EXP")],sort("EXP"),\no-attrs()),
//	restrict(sort("PICO-ID"),others(sort("PICO-ID")),[\char-class([range(48,57),range(97,122)])]),
	prod([sort("PICO-ID"),lit(":="),sort("EXP")],sort("STATEMENT"),\no-attrs()),
	prod([\char-class([range(97,122)]),\iter-star(\char-class([range(48,57),range(97,122)]))],sort("PICO-ID"),\no-attrs()),
	prod([lit("natural")],sort("TYPE"),\no-attrs()),
	prod([lit("begin"),sort("DECLS"),\iter-star-seps(sort("STATEMENT"),[\layout(),lit(";"),\layout()]),lit("end")],sort("PROGRAM"),\no-attrs())
});

public str topProd2rascal(Production p) {
  if (/prod(_,lit(_),_) := p) return ""; // ignore generated productions

  if (/prod(_,rhs,_) := p) {
    return "<(start(_) := rhs) ? "start ":"">syntax <symbol2rascal(rhs)> = <prod2rascal(p)>;\n\n";
  }
  if (regular(_,_) := p) {
    return ""; // ignore generated stubs
  }
  throw "could not find out defined symbol for <p>";
}

public str prod2rascal(Production p) {
  println("prod2rascal: <p>");
  switch (p) {
    case choice(s, alts) :
		return ( prod2rascal(head(alts)) | "<it>\n\t|<prod2rascal(pr)>" | pr <- tail(alts) );
    	
    case first(s, alts) :
      	return ( prod2rascal(head(alts)) | "<it>\n\t\> <prod2rascal(pr)>" | pr <- tail(alts) );
      
    case \assoc(s, a, alts) :
    	return ( "<attr2mod(\assoc(a))> : <prod2rascal(head(alts))>" | "<it>\n\t\> <prod2rascal(pr)>" | pr <- tail(alts) );
 
    case diff(s,p,alts) :
       	return ( "<prod2rascal(p)>\n\t- <prod2rascal(head(alts))>" | "<it>\n\t- <prod2rascal(pr)>" | pr <- tail(alts) );
 
 // restrict
 // others
    case prod(_,lit(_),_) : return "";
    
    case prod(list[Symbol] lhs,Symbol rhs,Attributes attrs) :
      	return "<attrs2mods(attrs)><for(s <- lhs){><symbol2rascal(s)> <}>";
 
    case regular(_,_) :
    	return "";
    
    default: throw "missed a case <p>";
  }
}

test prod2rascal(prod([sort("PICO-ID"),lit(":"),sort("TYPE")],sort("ID-TYPE"),\no-attrs()))
     == "PICO-ID \":\" TYPE ";

test prod2rascal(
     prod([sort("PICO-ID"), lit(":"), sort("TYPE")],
               sort("ID-TYPE"),
              attrs([term(cons("decl")),\assoc(left())]))) ==
               "left decl:PICO-ID \":\" TYPE ";
               
test prod2rascal(
	prod([\char-class([range(9,10),range(13,13),range(32,32)])],sort("LAYOUT"),attrs([term(cons("whitespace"))]))) == " whitespace:[\\ \\n\\r\\t] ";

test prod2rascal(
	first(sort("EXP"),[prod([sort("EXP"),lit("||"),sort("EXP")],sort("EXP"),\no-attrs()),
	                   prod([sort("EXP"),lit("-"),sort("EXP")],sort("EXP"),\no-attrs()),
	                   prod([sort("EXP"),lit("+"),sort("EXP")],sort("EXP"),\no-attrs())])) ==
	"EXP \"||\" EXP \n\t\> EXP \"-\" EXP \n\t\> EXP \"+\" EXP ";

public str attrs2mods(Attributes as) {
  switch (as) {
    case \no-attrs(): 
      return "";
    case \attrs([list[Attr] a,term(cons(c)),list[Attr] b]) : 
      return attrs2mods(\attrs([a,b])) + " <c>:";
    case \attrs([a,b*]): {
        if(size(b) == 0)
           return "<attr2mod(a)>";
        return "<attr2mod(a)> <attrs2mods(\attrs(b))>"; 
      }
    case \attrs([]):
    	return "";   
    default:   throw "attrs2rascal: missing case <attrs>";
  }
}

test attrs2mods(\attrs([\assoc(\left())])) == "left";
test attrs2mods(\attrs([\assoc(\left()), \assoc(\right())])) == "left right";
test attrs2mods(\attrs([\assoc(\left()), term(cons("C")), \assoc(\right())])) == "left right C:";
test attrs2mods(\attrs([term(cons("C"))])) == " C:";

public str attr2mod(Attr a) {
  switch(a) {
    case \assoc(\left()): return "left";
    case \assoc(\right()): return "right";
    case \assoc(\non-assoc()): return "non-assoc";
    case \assoc(\assoc()): return "assoc";
    case term("lex"()): return "lex";
    case term(t): return "<t>";
    case \bracket(): return "bracket";
    default: throw "attr2mod: missing case <a>";
  }
}

test attr2mod(\assoc(\left())) == "left";

public str symbol2rascal(Symbol sym) {
  switch (sym) {
    case label(str l, x) :
    	return "<symbol2rascal(x)> <l>";  
    case sort(x) :
    	return replaceAll(x, "-", "_");
    case lit(x) :
    	return "\"<escape(x)>\"";
    case cilit(x) :
    	return "\"<escape(x)>\"";
    case \parameterized-sort(str name, list[Symbol] parameters):
        return "<name>[[<params2rascal(parameters)>]]";
    case \char-class(x) : 
    	return cc2rascal(x);
    case opt(x) : 
    	return "<symbol2rascal(x)>?";
    case iter(x) : 
    	return "<symbol2rascal(x)>+";
    case \iter-star(x) : 
    	return "<symbol2rascal(x)>*";
    case \iter-seps(x,seps) :
        return iterseps2rascal(x, seps, "+");
    case \iter-star-seps(x,seps) : 
    	return iterseps2rascal(x, seps, "*");
    case \layout(): 
    	return "";
    case \start(x):
    	return symbol2rascal(x);
    case intersection(lhs, rhs):
        return "<symbol2rascal(lhs)>/\\<symbol2rascal(rhs)>";
    case union(lhs,rhs):
     	return "<symbol2rascal(lhs)>\\/<symbol2rascal(rhs)>";
    case difference(lhs,rhs):
     	return "<symbol2rascal(lhs)>-<symbol2rascal(rhs)>";
    case complement(lhs):
     	return "~<symbol2rascal(lhs)>";

  }
  throw "symbol2rascal: missing case <sym>";
}

test symbol2rascal(lit("abc")) == "\"abc\"";
test symbol2rascal(lit("\\\n")) == "\"\\\n\"";
test symbol2rascal(sort("ABC")) == "ABC";
test symbol2rascal(cilit("abc")) == "\"abc\"";
test symbol2rascal(label("abc",sort("ABC"))) == "ABC abc";
test symbol2rascal(\parameterized-sort("A", [sort("B")])) == "A[[B]]";
test symbol2rascal(\parameterized-sort("A", [sort("B"), sort("C")])) == "A[[B, C]]";
test symbol2rascal(opt(sort("A"))) == "A?";
test symbol2rascal(\char-class([range(97,97)])) == "[a]";
test symbol2rascal(\iter-star-seps(sort("A"),[\layout()])) == "A*";
test symbol2rascal(\iter-seps(sort("A"),[\layout()])) == "A+";
test symbol2rascal(opt(\iter-star-seps(sort("A"),[\layout()]))) == "A*?";
test symbol2rascal(opt(\iter-seps(sort("A"),[\layout()]))) == "A+?";
test symbol2rascal(\iter-star-seps(sort("A"),[\layout(),lit("x"),\layout()])) == "{A \"x\"}*";
test symbol2rascal(\iter-seps(sort("A"),[\layout(),lit("x"),\layout()])) == "{A \"x\"}+";
test symbol2rascal(opt(\iter-star-seps(sort("A"),[\layout(),lit("x"),\layout()]))) == "{A \"x\"}*?";
test symbol2rascal(opt(\iter-seps(sort("A"),[\layout(),lit("x"),\layout()]))) == "{A \"x\"}+?";
test symbol2rascal(\iter-star(sort("A"))) == "A*";
test symbol2rascal(\iter(sort("A"))) == "A+";
test symbol2rascal(opt(\iter-star(sort("A")))) == "A*?";
test symbol2rascal(opt(\iter(sort("A")))) == "A+?";
test symbol2rascal(\iter-star-seps(sort("A"),[lit("x")])) == "{A \"x\"}*";
test symbol2rascal(\iter-seps(sort("A"),[lit("x")])) == "{A \"x\"}+";
test symbol2rascal(opt(\iter-star-seps(sort("A"),[lit("x")]))) == "{A \"x\"}*?";
test symbol2rascal(opt(\iter-seps(sort("A"),[lit("x")]))) == "{A \"x\"}+?";

public str iterseps2rascal(Symbol sym, list[Symbol] seps, str iter){
  separators = "<for(sp <- seps){><symbol2rascal(sp)><}>";
  if (separators != "")
     return "{<symbol2rascal(sym)> <separators>}<iter>";
  else
    return "<symbol2rascal(sym)><separators><iter>";
}

public str params2rascal(list[Symbol] params){
  len = size(params);
  if(len == 0)
  	return "";
  if(len == 1)
  	return symbol2rascal(params[0]);
  sep = "";
  res = "";
  for(Symbol p <- params){
      res += sep + symbol2rascal(p);
      sep = ", ";
  }
  return res;	
}

public str escape(str s){
  res = "";
  for(int i <- [0 .. size(s)-1])
     res += char2rascal(charAt(s, i));
  return res;
}

public str cc2rascal(list[CharRange] ranges) {
  return "[<for (r <- ranges){><range2rascal(r)><}>]";
}

public str range2rascal(CharRange r) {
  switch (r) {
    case range(c,c) : return char2rascal(c);
    case range(c,d) : return "<char2rascal(c)>-<char2rascal(d)>";
    default: throw "range2rascal: missing case <range>";
  }
}

test range2rascal(range(97,97)) == "a";
test range2rascal(range(97,122)) == "a-z";
test range2rascal(range(10,10)) == "\n";

// A good old ASCII table in order to convert numbers < 128 to readable characters.

private list[str] ascii =
[

// Decimal Value   Description
//-------  -----   --------------------------------
/* 000 */  "\000", // NUL    (Null char.)
/* 001 */  "\001", // SOH    (Start of Header)
/* 002 */  "\002", // STX    (Start of Text)
/* 003 */  "\003", // ETX    (End of Text)
/* 004 */  "\004", // EOT    (End of Transmission)
/* 005 */  "\005", // ENQ    (Enquiry)
/* 006 */  "\006", // ACK    (Acknowledgment)
/* 007 */  "\007", // BEL    (Bell)
/* 008 */  "\010", // BS    (Backspace)
/* 009 */   "\\t", // HT    (Horizontal Tab)
/* 010 */   "\\n", // LF    (Line Feed)
/* 011 */   "\\r", // VT    (Vertical Tab)
/* 012 */  "\014", // FF    (Form Feed)
/* 013 */  "\015", // CR    (Carriage Return)
/* 014 */  "\016", // SO    (Shift Out)
/* 015 */  "\017", // SI    (Shift In)
/* 016 */  "\020", // DLE   (Data Link Escape)
/* 017 */  "\021", // DC1   (Device Control 1)
/* 018 */  "\022", // DC2   (Device Control 2)
/* 019 */  "\023", // DC3   (Device Control 3)
/* 020 */  "\024", // DC4   (Device Control 4)
/* 021 */  "\025", // NAK   (Negative Acknowledgemnt)
/* 022 */  "\026", // SYN   (Synchronous Idle)
/* 023 */  "\027", // ETB   (End of Trans. Block)
/* 024 */  "\030", // CAN   (Cancel)
/* 025 */  "\031", // EM    (End of Medium)
/* 026 */  "\032", // SUB   (Substitute)
/* 027 */  "\033", // ESC   (Escape)
/* 028 */  "\034", // FS    (File Separator)
/* 029 */  "\035", // GS    (Group Separator)
/* 030 */  "\036", // RS    (Reqst to Send)(Rec. Sep.)
/* 031 */  "\037", // US    (Unit Separator)
/* 032 */   "\\ ", // SP    (Space)
/* 033 */     "!", //  !    (exclamation mark)
/* 034 */    "\"", //  "    (double quote)
/* 035 */     "#", //  #    (number sign)
/* 036 */     "$", //  $    (dollar sign)
/* 037 */     "%", //  %    (percent)
/* 038 */     "&", //  &    (ampersand)
/* 039 */    "\'", //  '    (single quote)
/* 040 */     "(", //  (  (left/open parenthesis)
/* 041 */     ")", //  )  (right/closing parenth.)
/* 042 */     "*", //  *    (asterisk)
/* 043 */     "+", //  +    (plus)
/* 044 */     ",", //  ,    (comma)
/* 045 */     "-", //  -    (minus or dash)
/* 046 */     ".", //  .    (dot)
/* 047 */     "/", //  /    (forward slash)
/* 048 */     "0", //  0
/* 049 */     "1", //  1
/* 050 */     "2", //  2
/* 051 */     "3", //  3
/* 052 */     "4", //  4
/* 053 */     "5", //  5
/* 054 */     "6", //  6
/* 055 */     "7", //  7
/* 056 */     "8", //  8
/* 057 */     "9", //  9
/* 058 */     ":", //  :    (colon)
/* 059 */     ";", //  ;    (semi-colon)
/* 060 */    "\<", //  <    (less than)
/* 061 */     "=", //  =    (equal sign)
/* 062 */    "\>", //  >    (greater than)
/* 063 */     "?", //  ?    (question mark)
/* 064 */     "@", //  @    (AT symbol)
/* 065 */     "A", //  A
/* 066 */     "B", //  B
/* 067 */     "C", //  C
/* 068 */     "D", //  D
/* 069 */     "E", //  E
/* 070 */     "F", //  F
/* 071 */     "G", //  G
/* 072 */     "H", //  H
/* 073 */     "I", //  I
/* 074 */     "J", //  J
/* 075 */     "K", //  K
/* 076 */     "L", //  L
/* 077 */     "M", //  M
/* 078 */     "N", //  N
/* 079 */     "O", //  O
/* 080 */     "P", //  P
/* 081 */     "Q", //  Q
/* 082 */     "R", //  R
/* 083 */     "S", //  S
/* 084 */     "T", //  T
/* 085 */     "U", //  U
/* 086 */     "V", //  V
/* 087 */     "W", //  W
/* 088 */     "X", //  X
/* 089 */     "Y", //  Y
/* 090 */     "Z", //  Z
/* 091 */     "[", //  [    (left/opening bracket)
/* 092 */    "\\", //  \    (back slash)
/* 093 */     "]", //  ]    (right/closing bracket)
/* 094 */     "^", //  ^    (caret/circumflex)
/* 095 */     "_", //  _    (underscore)
/* 096 */     "`", //  `    (backquote)
/* 097 */     "a", //  a
/* 098 */     "b", //  b
/* 099 */     "c", //  c
/* 100 */     "d", //  d
/* 101 */     "e", //  e
/* 102 */     "f", //  f
/* 103 */     "g", //  g
/* 104 */     "h", //  h
/* 105 */     "i", //  i
/* 106 */     "j", //  j
/* 107 */     "k", //  k
/* 108 */     "l", //  l
/* 109 */     "m", //  m
/* 110 */     "n", //  n
/* 111 */     "o", //  o
/* 112 */     "p", //  p
/* 113 */     "q", //  q
/* 114 */     "r", //  r
/* 115 */     "s", //  s
/* 116 */     "t", //  t
/* 117 */     "u", //  u
/* 118 */     "v", //  v
/* 119 */     "w", //  w
/* 120 */     "x", //  x
/* 121 */     "y", //  y
/* 122 */     "z", //  z
/* 123 */     "{", //  {    (left/opening brace)
/* 124 */     "|", //  |    (vertical bar)
/* 125 */     "}", //  }    (right/closing brace)
/* 126 */     "~", //  ~    (tilde)
/* 127 */  "\177"  //DEL    (delete)
];

public str char2rascal(int ch) {
  if(ch < 128)
     return ascii[ch];
  if (ch < 256) {
    d1 = ch % 8; r1 = ch / 8;
    d2 = r1 % 8; r2 = r1 / 8;
    d3 = r2;
    return "\\<d3><d2><d1>";
  }
  else {
    d1 = ch % 16; r1 = ch / 16;
    d2 = r1 % 16; r2 = r1 / 16;
    d3 = r2 % 16; r3 = r2 / 16;
    d4 = r3;
    return "\\u<d4><d3><d2><d1>";
//  return "\\u<ch % 65536><ch % 4096><ch % 256><ch % 16>";
  }
}

test char2rascal(97) == "a";
test char2rascal(255) == "\\<377>";	//TODO "\\377" does not parse

