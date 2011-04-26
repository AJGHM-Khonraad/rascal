@license{
  Copyright (c) 2009-2011 CWI
  All rights reserved. This program and the accompanying materials
  are made available under the terms of the Eclipse Public License v1.0
  which accompanies this distribution, and is available at
  http://www.eclipse.org/legal/epl-v10.html
}
@contributor{Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI}
@contributor{Bas Basten - Bas.Basten@cwi.nl (CWI)}
@contributor{Tijs van der Storm - Tijs.van.der.Storm@cwi.nl}
@contributor{Paul Klint - Paul.Klint@cwi.nl - CWI}
@contributor{Arnold Lankamp - Arnold.Lankamp@cwi.nl}
module ParseTree

import Message;

@doc{These are the trees normally found after parsing}
data Tree 
  = appl(Production prod, list[Tree] args) 
  | cycle(Symbol symbol, int cycleLength) 
  | amb(set[Tree] alternatives)  
  | char(int character)
  ;
  
@doc{These trees constructors are used additionally in error trees}
data Tree 
  = error(Production prod, list[Tree] args, list[Tree] rest)
  | expected(Symbol symbol)
  | erroramb(set[Tree] alternatives)
  | errorcycle(Symbol symbol, int cycleLength)
  ;
  
data Production =
     prod(list[Symbol] lhs, Symbol rhs, Attributes attributes) | 
     regular(Symbol rhs, Attributes attributes);

data Attributes = \no-attrs() | \attrs(list[Attr] attrs);
  
data Attr =
     \assoc(Associativity \assoc) | 
     \term(value \term) |  
     \bracket() | \reject() |
     \lex() | \literal() | \ciliteral();

data Associativity =
     \left() | \right() | \assoc() | \non-assoc();

data CharRange = range(int start, int end);

alias CharClass = list[CharRange];

data Symbol 
  = \start(Symbol symbol) 
  | \label(str name, Symbol symbol) 
  | \lit(str string) 
  | \cilit(str string)  
  | \empty()  
  | \opt(Symbol symbol)  
  | \sort(str string)   
  | \layouts(str name) 
  | \keywords(str name)
  | \iter(Symbol symbol)   
  | \iter-star(Symbol symbol)   
  | \iter-seps(Symbol symbol, list[Symbol] separators)   
  | \iter-star-seps(Symbol symbol, list[Symbol] separators) 
  | \alt(set[Symbol] alternatives)
  | \seq(list[Symbol] sequence)
  | \parameterized-sort(str sort, list[Symbol] parameters)  
  | \parameter(str name) 
  | \char-class(list[CharRange] ranges) 
  | \at-column(int column) 
  | \start-of-line() 
  | \end-of-line()
  | \follow(Symbol symbol, set[Symbol] follow)
  | \not-follow(Symbol symbol, set[Symbol] follow)
  | \precede(Symbol symbol, set[Symbol] precede)
  | \not-precede(Symbol symbol, set[Symbol] precede)
  | \reserve(Symbol symbol, set[Symbol] reserved)
  ;
     
@doc{provides access to the source location of a parse tree node}
anno loc Tree@\loc;

@doc{Parse the contents of a resource pointed to by the input parameter and return a parse tree.}
@javaClass{org.rascalmpl.library.ParseTree}
@reflect{uses information about syntax definitions at call site}
public &T<:Tree java parse(type[&T<:Tree] start, loc input);

@doc{Parse the contents of a resource pointed to by the input parameter and return a parse tree which can contain error nodes.}
@javaClass{org.rascalmpl.library.ParseTree}
@reflect{uses information about syntax definitions at call site}
public &T<:Tree java parseWithErrorTree(type[&T<:Tree] start, loc input);

@doc{Parse a string and return a parse tree.}
@javaClass{org.rascalmpl.library.ParseTree}
@reflect{uses information about syntax definitions at call site}
public &T<:Tree java parse(type[&T<:Tree] start, str input);

@doc{Parse a string and return a parse tree, which can contain error nodes.}
@javaClass{org.rascalmpl.library.ParseTree}
@reflect{uses information about syntax definitions at call site}
public &T<:Tree java parseWithErrorTree(type[&T<:Tree] start, str input);

@doc{Parse a string and return a parse tree.}
@javaClass{org.rascalmpl.library.ParseTree}
@reflect{uses information about syntax definitions at call site}
public &T<:Tree java parse(type[&T<:Tree] start, str input, loc origin);

@doc{Parse a string and return a parse tree, which can contain error nodes.}
@javaClass{org.rascalmpl.library.ParseTree}
@reflect{uses information about syntax definitions at call site}
public &T<:Tree java parseWithErrorTree(type[&T<:Tree] start, str input, loc origin);

@doc{Yields the string of characters that form the leafs of the given parse tree.}
@javaClass{org.rascalmpl.library.ParseTree}
public str java unparse(Tree tree);

@doc{
Parsetree Implosion
===================

This function implodes a parsetree by simulataneously traversing the 
reified ADT and the parse tree. Meanwhile, an AST is constructed as follows:

- Literals and layout nodes are skipped.

- Regular */+ lists are imploded to list[]s or set[]s depending on what is 
  expected in the ADT.

- Ambiguities are imploded to set[]s.

- If a tree's production has no label and a single AST (i.e. non-layout, non-literal) argument
  (for instance, and injection), the tree node is skipped, and implosion continues 
  with the lone argument. The same applies to bracket productions, even if they
  are labeled.

- If a tree's production has no label, but more than one argument, the tree is imploded 
  to a tuple (provided this conforms to the ADT).
  
  Example
  
    syntax IDTYPE = Id ":" Type;
    
    syntax Decls = decls: "declare" \{IDTYPE ","\}* ";";
    
  Hence, Decls will be imploded as:
    
    data Decls = decls(list[tuple[str,Type]]);
    
  (assuming Id is a lexical non-terminal).   

- Optionals are imploded to booleans if this is expected in the ADT.
  This also works for optional literals, as shown in the following
  example:
  
    syntax Formal = formal: "VAR"? \{Id ","\}+ ":" Type;
  
  The corresponding ADT could be:
  
    data Formal = formal(bool, list[str], Type);
    

- An optional is imploded to a list with zero or one argument, iff a list
  type is expected.

- If the argument of an optional tree has a production with no label, containing
  a single list, then this list is spliced into the optional list.
  
  Example:
  
    syntax Tag = "[" \{Modifier ","\}* "]";
    syntax Decl = decl: Tag? Signature Body;
  
  In this case, a Decl is imploded into the following ADT:
  
    data Decl = decl(list[Modifier], Signature, Body);  
  
- For trees with (cons-)labeled productions, the corresponding constructor
  in the ADT corresponding to the non-terminal of the production is found in
  order to make the AST.
  
  Typical example:
  
    syntax Exp = left add: Exp "+" Exp;
  
  Can be imploded into:
    data Exp = add(Exp, Exp);
  
- Unlabeled lexicals are imploded to str, int, real, bool depending on the expected type in
  the ADT. To implode lexical into types other than str, the PDB parse functions for 
  integers and doubles are used. Boolean lexicals should match "true" or "false". 
  NB: lexicals are imploded this way, even if they are ambiguous.

- If a lexical tree has a cons label, the tree imploded to a constructor with that name
  and a single string-valued argument containing the tree's yield.

IllegalArgument is thrown if during implosion a tree is encountered that cannot be
imploded to the expected type in the ADT. As explained above, this routine assumes the
ADT type names correspond to syntax non-terminal names, and constructor names correspond 
to production labels. Labels of production arguments do not have to match with labels
 in ADT constructors.

Finally, source location annotations are propagated as annotations on constructor ASTs. 
To access them, the user is required to explicitly declare a location annotation on all
ADTs used in implosion. In other words, for every ADT type T, add:

anno loc T@location;

}
@javaClass{org.rascalmpl.library.ParseTree}
public &T<:node java implode(type[&T<:node] t, Tree tree);

@doc{introduces a (error) message related to a certain sub-tree}
public anno Message Tree@message;

@doc{lists all (error) messages relevant for a certain sub-tree}
public anno set[Message] Tree@messages;

@doc{provides a documentation string for this parse tree node}
anno str Tree@doc;

@doc{provides a documentation string for certain locations}
anno map[loc,str] Tree@docs;

@doc{provides the target of a reference}
anno loc Tree@link;

@doc{provides multiple targets of a references}
anno set[loc] Tree@links;

@doc{result type for treeAt()}
public data TreeSearchResult[&T<:Tree] = treeFound(&T tree) | treeNotFound();

@doc{selects the innermost Tree of type t which location encloses l}
public TreeSearchResult[&T<:Tree] treeAt(type[&T<:Tree] t, loc l, a:appl(_, _)) {
	if ((a@\loc)?, al := a@\loc, al.offset <= l.offset, al.offset + al.length >= l.offset + l.length) {
		for (arg <- a.args, r:treeFound(_) := treeAt(t, l, arg)) {
			return r;
		}
		
		if (&T<:Tree tree := a) {
			return treeFound(tree);
		}
	}
	return treeNotFound();
}

public TreeSearchResult[&T<:Tree] default treeAt(type[&T<:Tree] t, loc l, Tree root) {
	return treeNotFound();
}
