module Reflective

import ParseTree;
import lang::rascal::syntax::Grammar;

@javaClass{org.rascalmpl.library.Reflective}
@reflect{Uses Evaluator to get back the parse tree for the given path}
public Tree java getModuleParseTree(str modulePath);

@javaClass{org.rascalmpl.library.Reflective}
@reflect{Uses Evaluator to get back the grammars imported by mod}
public Grammar java getModuleGrammar(loc mod);

@javaClass{org.rascalmpl.library.Reflective}
@reflect{Uses Evaluator to get back the parse tree for the given command}
public Tree java parseCommand(str command, loc location);
