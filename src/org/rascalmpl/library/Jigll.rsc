module Jigll

import Grammar;
import ParseTree;
import lang::rascal::grammar::definition::Literals;
import lang::rascal::grammar::definition::Priorities;
import IO;

@javaClass{org.rascalmpl.parser.GrammarToJigll}
public java void generate(str name, Grammar grammar);

public void generate(str name, type[&T <: Tree] nont) {
  generate(name, grammar({nont.symbol}, nont.definitions));
}

public &T jparse(type[&T <: Tree] nont, str input) {
  return jparse(nont, nont.symbol, addNotAllowedSets(literals(grammar({nont.symbol}, nont.definitions))), input);
}

@javaClass{org.rascalmpl.parser.GrammarToJigll}
public java &T jparse(type[&T <: Tree] nont, Symbol nonterminal, Grammar grammar, str input);