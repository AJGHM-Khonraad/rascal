@bootstrapParser
module experiments::CoreRascal::Translation::RascalModule

import experiments::CoreRascal::ReductionWithEvalCtx::AST;
import lang::rascal::\syntax::Rascal;
import Prelude;
import util::Reflective;
import util::ValueUI;
import ParseTree;

import lang::rascal::types::TestChecker;
import lang::rascal::types::CheckTypes;
import experiments::CoreRascal::Translation::RascalExpression;
import experiments::CoreRascal::Translation::RascalStatement;

import experiments::CoreRascal::muRascal::AST;

list[loc] libSearchPath = [|std:///|, |eclipse-std:///|];

loc Example1 = |std:///experiments/CoreRascal/Translation/Examples/Example1.rsc|;

//map[str name, MuDeclaration] functions;

MuModule compile(){
   try {
   	Module M = parseModule(Example1, libSearchPath);
   	config = checkModule(M.top, newConfiguration());  // .top is needed to remove start! Ugly!
   	//text(config);
   	extractScopes();
   	errors = [ e | e:error(_,_) <- config.messages];
   	if(size(errors) > 0)
   	  println("Module contains errors:\n<for(e <- errors){><e>\n<}>");
   	else {
   	  println("GENERATED:");
   	  defs = translate(M.top);
   	  return muModule("XXX", defs, empty());
   	  }
   	} catch Java("ParseError","Parse error"): {
   	    println("Syntax errors in module <Example1>");
   	} 
}

list[MuDefinition] translate(m: (Module) `<Header header> <Body body>`) =
    [ *translate(tl) | tl <- body.toplevels];
  
list[MuDefinition] translate(t: (Toplevel) `<Declaration decl>`) = translate(decl);

// Toplevel Declaration: variable

list[MuExp] translate(d: (Declaration) `<Tags tags> <Visibility visibility> <Type tp> <{Variable ","}+ variables> ;`) {
	return
   		for(var <- variables){
   			if(var is initialized) 
   				append mkAssign("<var.name>",var@\loc, translate(var.initial));
   		}
}   	

str translate(d: (Declaration) `<Tags tags> <Visibility visibility> anno <Type annoType> <Type onType> @ <Name name> ;`) { throw("annotation"); }
str translate(d: (Declaration) `<Tags tags> <Visibility visibility> alias <UserType user> = <Type base> ;`)   { throw("alias"); }
str translate(d: (Declaration) `<Tags tags> <Visibility visibility> tag <Kind kind> <Name name> on <{Type ","}+ types> ;`)  { throw("tag"); }
str translate(d: (Declaration) `<Tags tags> <Visibility visibility> data <UserType user> ;`)  { throw("dataAbstract"); }
/*
	| @Foldable \data : Tags tags Visibility visibility "data" UserType user CommonKeywordParameters commonKeywordParameters"=" {Variant "|"}+ variants ";"
*/

list[MuDefinition] translate(d: (Declaration) `<FunctionDeclaration functionDeclaration>`) = translate(functionDeclaration);

// FunctionDeclaration

str translate(fd: (FunctionDeclaration) `<Tags tags> <Visibility visibility> <Signature signature> ;`)   { throw("abstract"); }

list[MuDefinition] translate(fd: (FunctionDeclaration) `<Tags tags> <Visibility visibility> <Signature signature> = <Expression expression> ;`){
  ftypes = getFunctionType("<signature.name>");
  if({ ftype } := ftypes){
	  formals = signature.parameters.formals.formals;
	  lformals = [f | f <- formals];
	  return [fun("<signature.name>", size(lformals), 0, translate(expression))];
  } else
      throw "overloaded function <signature.name>: <ftypes>";
}

str translate(fd: (FunctionDeclaration) `<Tags tags>  <Visibility visibility> <Signature signature> <FunctionBody body>`){
  ftypes = getFunctionType("<signature.name>");
  if({ ftype } := ftypes){
	  formals = signature.parameters.formals.formals;
	  lformals = [f | f <- formals];
	  tbody = [translate(stat) | stat <- body.statements ];
	  return MuDeclaration(signature.name, size(lformals), 0, tbody);
  } else
      throw "overloaded function <signature.name>: <ftypes>"; 
}

str translateFun( Signature signature, str body){
  ftypes = getFunctionType("<signature.name>");
  if({ ftype } := ftypes){
	  formals = signature.parameters.formals.formals;
	  lformals = [f | f <- formals];
	  tformals = [(Pattern) `<Type tp> <Name nm>` := lformals[i] ? mkVar("<nm>",nm@\loc) : "pattern"  | i <- index(lformals)];
	  tbody = "<for(stat <- body.statements){><translate(stat)>;<}>";
	  return "\n// <fd>\n<mkVar("<signature.name>",fd@\loc)> = lambda([<intercalate(", ", tformals)>]){<tbody>}";
  } else
      throw "overloaded function <signature.name>: <ftypes>"; 

}

str translate(fd: (FunctionDeclaration) `<Tags tags> <Visibility visibility> <Signature signature> = <Expression expression> when <{Expression ","}+ conditions> ;`)   { throw("conditional"); }


/* withThrows: FunctionModifiers modifiers Type type  Name name Parameters parameters "throws" {Type ","}+ exceptions */

str translate(Signature s:(Signature) `<FunctionModifiers modifiers> <Type \type> <Name name> <Parameters parameters>`){
  formals = parameters.formals.formals;
  //keywordFormals = parameters.keywordFormals;
  return intercalate(", ", [(Pattern) `<Type tp> <Name nm>` := f ? "var(\"<nm>\", <tp>)" : "pattern" | f <- formals]);
}
