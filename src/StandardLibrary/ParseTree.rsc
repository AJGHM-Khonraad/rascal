module ParseTree

data ParseTree = 
     parsetree(Tree top, int amb_cnt);

data Tree =
     appl(Production prod, list[Tree] args) |
	 cycle(Symbol symbol, int cycleLength) |
	 amb(set[Tree] alternatives) | 
	 char(int character);

data Production =
     prod(list[Symbol] lhs, Symbol rhs, Attributes attributes) | 
     \list(Symbol rhs);

data Attributes = 
     \no-attrs() | \attrs(list[Attr] attrs);

data Attr =
     assoc(Associativity assoc) | term(value term) |
     id(str moduleName) | bracket() | reject() | prefer() | avoid();

data Associativity =
     \left() | \right() | \assoc() | \non-assoc();

data CharRange =
     single(int start) | range(int start, int end);

data Constructor = cons(str name);

data Symbol =
     \lit(str string) |
     \cilit(str string) | 
     \cf(Symbol symbol)  |
     \lex(Symbol symbol)  |
     \empty()  |
     \seq(list[Symbol] symbols)  |
     \opt(Symbol symbol)  |
     \alt(Symbol lhs, Symbol rhs)  |
     \tuple(Symbol head, list[Symbol] rest)  |
     \sort (str string)  | 
     \iter(Symbol symbol)  | 
     \iter-star(Symbol symbol)  | 
     \iter-sep(Symbol symbol, Symbol separator)  | 
     \iter-star-sep(Symbol symbol, Symbol separator)  | 
     \iter-n(Symbol symbol, int number)  | 
     \iter-sep-n(Symbol symbol, Symbol separator, int number)  | 
     \func(list[Symbol] symbols, Symbol symbol)  | 
     \parameterized-sort(str sort, list[Symbol] parameters)  | 
     \strategy(Symbol lhs, Symbol rhs)  |
     \var-sym(Symbol symbol)  |
     \layout()  | 
     \char-class(list[CharRange] ranges);

