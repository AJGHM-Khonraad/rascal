@license{
  Copyright (c) 2009-2013 CWI
  All rights reserved. This program and the accompanying materials
  are made available under the terms of the Eclipse Public License v1.0
  which accompanies this distribution, and is available at
  http://www.eclipse.org/legal/epl-v10.html
}
module lang::rascal::grammar::definition::Attributes

import lang::rascal::newsyntax::Rascal;
import lang::rascal::grammar::definition::Literals;
import ParseTree;
import IO;

@doc{adds an attribute to all productions it can find}
public Production attribute(Production p, Attr a) = p[attributes=p.attributes+{a}];

public set[Attr] mods2attrs(ProdModifier* mods) = {mod2attr(m) | ProdModifier m <- mods};
 
public Attr mod2attr(ProdModifier m) {
  switch (m) {
    case \associativity(\left())        : return \assoc(\left());
    case \associativity(\right())       : return \assoc(\right());
    case \associativity(\nonAssociative())   : return \assoc(\non-assoc());
    case \associativity(\associative())       : return \assoc(\assoc());
    case \bracket()                     : return \bracket();
    case \tag(\default(Name n, TagString s))    : return \tag("<n>"("<s>"));
    case \tag(\empty(Name n))                   : return \tag("<n>"()); 
    case \tag(\expression(Name n, literal(string(nonInterpolated(StringConstant l)))))  : return \tag("<n>"("<l>"));
    case \tag(\expression(Name n, literal(Literal l)))                                  : return \tag("<n>"("<l>"));
    default: { rprintln(m); throw "missed a case <m>"; }
  }
}
