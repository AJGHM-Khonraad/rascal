@license{
  Copyright (c) 2009-2011 CWI
  All rights reserved. This program and the accompanying materials
  are made available under the terms of the Eclipse Public License v1.0
  which accompanies this distribution, and is available at
  http://www.eclipse.org/legal/epl-v10.html
}
@contributor{Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI}
@contributor{Arnold Lankamp - Arnold.Lankamp@cwi.nl}
@doc{
  In this module character classes are normalized.
  
  It also provides a number of convenience functions on character classes.
}
@bootstrapParser
module lang::rascal::grammar::definition::Characters

import lang::rascal::\syntax::RascalRascal;
import ParseTree;
import String;
import Grammar;
import List;
import IO;

public data CharRange = \empty-range();    
  
public CharRange range(int from, int to) {
  if (to < from)
    return \empty-range();
  else
    fail;
}

public Symbol \char-class([list[CharRange] a,\empty-range(),list[CharRange] b]) 
  = \char-class(a+b);

public Symbol \char-class([list[CharRange] a,range(int from1, int to1),list[CharRange] b,range(int from2, int to2),list[CharRange] c]) {
  if ((from1 <= from2 && to1 >= from2 - 1) 
     || (from2 <= from1 && to2 >= from1 - 1)
     || (from1 >= from2 && to1 <= to2)
     || (from2 >= from1 && to2 <= to1)) 
       return \char-class(a+[range(min([from1,from2]),max([to1,to2]))]+b+c);
    else 
      fail;
}
 
public Symbol \char-class([list[CharRange] a,range(int n,int m),list[CharRange] b, range(int o, int p), list[CharRange] c]) {
  if (p < n) 
    return \char-class(a + [range(o,p)]+b+[range(n,m)]+c);
  else 
    fail;
}
     
test bool testFlip() = \char-class([range(2,2), range(1,1)]) == \char-class([range(1,2)]);
test bool testMerge() = \char-class([range(3,4), range(2,2), range(1,1)]) == \char-class([range(1,4)]);
test bool testEnvelop() = \char-class([range(10,20), range(15,20), range(20,30)]) == \char-class([range(10,30)]);
test bool testEnvelop2() = \char-class([range(10,20), range(10,19), range(20,30)]) == \char-class([range(10,30)]);

public Symbol complement(\char-class(list[CharRange] r1)) 
  = \char-class(complement(r1));
  
public default Symbol  complement(Symbol s) {
  throw "unsupported symbol for character class complement: <s>";
}
  
public Symbol difference(\char-class(list[CharRange] r1), \char-class(list[CharRange] r2)) 	
  = \char-class(difference(r1,r2));

public default Symbol  difference(Symbol s, Symbol t) {
  throw "unsupported symbols for  character class difference: <s> and <t>";
}

public Symbol union(\char-class(list[CharRange] r1), \char-class(list[CharRange] r2))
 = \char-class(union(r1,r2));
 
public default Symbol  union(Symbol s, Symbol t) {
  throw "unsupported symbols for union: <s> and <t>";
}

public Symbol intersection(\char-class(list[CharRange] r1), \char-class(list[CharRange] r2)) 
 = \char-class(intersection(r1,r2));

public default Symbol  intersection(Symbol s, Symbol t) {
  throw "unsupported symbols for intersection: <s> and <t>";
}

public bool lessThan(CharRange r1, CharRange r2) {
  if (range(s1,e1) := r1, range(s2,e2) := r2) {
    return e1 < s2;
  }
  throw "unexpected ranges <r1> and <r2>";
}

public CharRange difference(CharRange l, CharRange r) {
  if (l == \empty-range() || r == \empty-range()) return l;
  
  if (\char-range(ls,le) := l, \char-range(rs,re) := r) {
    // left beyond right
    // <-right-> --------
    // --------- <-left->
    if (ls > re) 
      return l; 

    // left before right
    // <-left-> ----------
    // -------- <-right->
    if (le < rs) 
      return l;

    // inclusion of left into right
    // <--------right------->
    // ---------<-left->-----
    if (ls >= rs && le <= re) 
      return \empty-range(); 

    // inclusion of right into left
    // -------<-right->------->
    // <---------left--------->
    if (rs >= ls && re <= le) 
      return range(ls,rs-1);

    // overlap on left side of right
    // <--left-------->----------
    // ---------<-----right----->
    if (le < re) 
      return range(ls,rs-1); 
    
    // overlap on right side of right
    // -------------<---left---->
    // <----right------->--------
    if (ls > rs)
      return range(re+1,le);

  }
  
  throw "did not expect to end up here! <l> - <r>"; 
}

public CharRange intersect(CharRange r1, CharRange r2) {
  if (r1 == \empty-range() || r2 == \empty-range()) return \empty-range();
  
  if (range(s1,e1) := r1, range(s2,e2) := r2) {
    // left beyond right
    // <-right-> --------
    // --------- <-left->
    if (s1 > e2) 
      return \empty-range(); 

    // left before right
    // <-left-> ----------
    // -------- <-right->
    if (e1 < s2) 
      return \empty-range();

    // inclusion of left into right
    // <--------right------->
    // ---------<-left->-----
    if (s1 >= s2 && e1 <= e2) 
      return r1; 

    // inclusion of right into left
    // -------<-right->------->
    // <---------left--------->
    if (s2 >= s1 && e2 <= e1) 
      return r2; 

    // overlap on left side of right
    // <--left-------->----------
    // ---------<-----right----->
    if (e1 < e2) 
      return range(s2,e1); 
    
    // overlap on right side of right
    // -------------<---left---->
    // <----right------->--------
    if (s1 > s2)
      return range(s1,e2); 
  }
  
  throw "unexpected ranges <r1> and <r2>";
}

public list[CharRange] complement(list[CharRange] s) {
  return difference([range(0,0xFFFF)],s);
}

public list[CharRange] intersection(list[CharRange] l, list[CharRange] r) {
  if (l == r) return l;
  if (l == [] || r == []) return [];
  
  <lhead,ltail> = <head(l), tail(l)>;
  <rhead,rtail> = <head(r), tail(r)>;

  if (lhead == \empty-range()) 
    return intersection(ltail, r);

  if (rhead == \empty-range()) 
    return intersection(l, rtail);

  // left beyond right
  // <-right-> --------
  // --------- <-left->
  if (lhead.begin > rhead.end) 
    return intersection(l,rtail); 

  // left before right
  // <-left-> ----------
  // -------- <-right->
  if (lhead.end < rhead.begin) 
    return intersection(ltail,r);

  // inclusion of left into right
  // <--------right------->
  // ---------<-left->-----
  if (lhead.begin >= rhead.begin && lhead.end <= rhead.end) 
    return lhead + intersection(ltail,r); 

  // inclusion of right into left
  // -------<-right->------->
  // <---------left--------->
  if (rhead.begin >= lhead.begin && rhead.end <= lhead.end) 
    return rhead + intersection(l,rtail); 

  // overlap on left side of right
  // <--left-------->----------
  // ---------<-----right----->
  if (lhead.end < rhead.end) 
    return range(rhead.begin,lhead.end) + intersection(ltail,r); 
    
  // overlap on right side of right
  // -------------<---left---->
  // <----right------->--------
  if (lhead.begin > rhead.begin)
    return range(lhead.begin,rhead.end) + intersection(l,rtail); 
    
  throw "did not expect to end up here! <l> - <r>";
  
} 

public list[CharRange] union(list[CharRange] l, list[CharRange] r) {
  if (l == r) return l;
  if (l == []) return r;
  if (r == []) return l;
  
  <lhead,ltail> = <head(l), tail(l)>;
  <rhead,rtail> = <head(r), tail(r)>;

  if (lhead == \empty-range()) 
    return union(ltail, r);

  if (rhead == \empty-range()) 
    return union(l, rtail);

  // left beyond right
  // <-right-> --------
  // --------- <-left->
  if (lhead.begin > rhead.end) 
    return rhead + union(l,rtail); 

  // left before right
  // <-left-> ----------
  // -------- <-right->
  if (lhead.end < rhead.begin) 
    return lhead + union(ltail,r);

  // inclusion of left into right
  // <--------right------->
  // ---------<-left->-----
  if (lhead.begin >= rhead.begin && lhead.end <= rhead.end) 
    return union(ltail,r); 

  // inclusion of right into left
  // -------<-right->------->
  // <---------left--------->
  if (rhead.begin >= lhead.begin && rhead.end <= lhead.end) 
    return union(l,rtail); 

  // overlap on left side of right
  // <--left-------->----------
  // ---------<-----right----->
  if (lhead.end < rhead.end) 
    return union(range(lhead.begin,rhead.end) + ltail, rtail); 
    
  // overlap on right side of right
  // -------------<---left---->
  // <----right------->--------
  if (lhead.begin > rhead.begin)
    return union(ltail,range(rhead.begin,lhead.end) + rtail); 
    
  throw "did not expect to end up here! <l> - <r>";
  
}

// Take difference of two lists of ranges
// Precondition: both lists are ordered
// Postcondition: resulting list is ordered

public list[CharRange] difference(list[CharRange] l, list[CharRange] r) {
  if (l == [] || r == []) return l;
  if (l == r) return [];
  
  <lhead,ltail> = <head(l), tail(l)>;
  <rhead,rtail> = <head(r), tail(r)>;

  if (lhead == \empty-range()) 
    return difference(ltail, r);

  if (rhead == \empty-range()) 
    return difference(l, rtail);

  // left beyond right
  // <-right-> --------
  // --------- <-left->
  if (lhead.begin > rhead.end) 
    return difference(l,rtail); 

  // left before right
  // <-left-> ----------
  // -------- <-right->
  if (lhead.end < rhead.begin) 
    return lhead + difference(ltail,r);

  // inclusion of left into right
  // <--------right------->
  // ---------<-left->-----
  if (lhead.begin >= rhead.begin && lhead.end <= rhead.end) 
    return difference(ltail,r); 

  // inclusion of right into left
  // -------<-right->------->
  // <---------left--------->
  if (rhead.begin >= lhead.begin && rhead.end <= lhead.end) 
    return range(lhead.begin,rhead.begin-1) 
         + difference(range(rhead.end+1,lhead.end)+ltail,rtail);

  // overlap on left side of right
  // <--left-------->----------
  // ---------<-----right----->
  if (lhead.end < rhead.end) 
    return range(lhead.begin,rhead.begin-1) + difference(ltail,r); 
    
  // overlap on right side of right
  // -------------<---left---->
  // <----right------->--------
  if (lhead.begin > rhead.begin)
    return difference(range(rhead.end+1,lhead.end)+ltail, rtail);

  throw "did not expect to end up here! <l> - <r>";
}

test bool comp() = complement(\char-class([])) == \char-class([range(0,65535)]);
test bool comp2() = complement(\char-class([range(0,0)])) == \char-class([range(1,65535)]);
test bool comp3() = complement(\char-class([range(1,1)])) == \char-class([range(0,0),range(2,65535)]);
test bool comp4() = complement(\char-class([range(10,20), range(30,40)])) == \char-class([range(0,9),range(21,29),range(41,65535)]);
test bool comp5() = complement(\char-class([range(10,35), range(30,40)])) == \char-class([range(0,9),range(41,65535)]);

test bool union1() = union(\char-class([range(10,20)]), \char-class([range(30, 40)])) == \char-class([range(10,20), range(30,40)]);
test bool union2() = union(\char-class([range(10,25)]), \char-class([range(20, 40)])) == \char-class([range(10,40)]);
 
test bool inter1() = intersection(\char-class([range(10,20)]), \char-class([range(30, 40)])) == \char-class([]);
test bool inter2() = intersection(\char-class([range(10,25)]), \char-class([range(20, 40)])) == \char-class([range(20, 25)]);

test bool diff1() = difference(\char-class([range(10,30)]), \char-class([range(20,25)])) == \char-class([range(10,19), range(26,30)]);
test bool diff2() = difference(\char-class([range(10,30), range(40,50)]), \char-class([range(25,45)])) ==\char-class( [range(10,24), range(46,50)]);

public Symbol cc2ranges(Class cc) {
   switch(cc) {
     case (Class) `[<Range* ranges>]` : return \char-class([range(r) | r <- ranges]);
     case (Class) `(<Class c>)`: return cc2ranges(c);
     case (Class) `! <Class c>`: return complement(cc2ranges(c));
     case (Class) `<Class l> && <Class r>`: return intersection(cc2ranges(l), cc2ranges(r));
     case (Class) `<Class l> || <Class r>`: return union(cc2ranges(l), cc2ranges(r));
     case (Class) `<Class l> - <Class r>`: return difference(cc2ranges(l), cc2ranges(r));
     default: throw "missed a case <cc>";
   }
}
      
private CharRange range(Range r) {
  switch (r) {
    case (Range) `<Char c>` : return range(character(c),character(c));
    case (Range) `<Char l> - <Char r>`: return range(character(l),character(r));
    default: throw "missed a case <r>";
  }
} 
 
private int character(Char c) {
  switch (c) {
    case [Char] /\\n/ : return charAt("\n", 0);
    case [Char] /\\t/ : return charAt("\t", 0);
    case [Char] /\\b/ : return charAt("\b", 0);
    case [Char] /\\r/ : return charAt("\r", 0);
    case [Char] /\\f/ : return charAt("\f", 0);
    case [Char] /\\\>/ : return charAt("\>", 0);
    case [Char] /\\\</ : return charAt("\<", 0);
    case [Char] /<ch:[^"'\-\[\]\\\>\< ]>/        : return charAt(ch, 0); 
    case [Char] /\\<esc:["'\-\[\]\\ ]>/        : return charAt(esc, 0);
    case [Char] /\\[u]+<hex:[0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F]>/ : return toInt("0x<hex>");
    case [Char] /\\<oct:[0-3][0-7][0-7]>/ : return toInt("0<oct>");
    case [Char] /\\<oct:[0-7][0-7]>/      : return toInt("0<oct>");
    case [Char] /\\<oct:[0-7]>/           : return toInt("0<oct>");
    default: throw "missed a case <c>";
  }
}
