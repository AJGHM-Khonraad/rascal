@bootstrapParser
module experiments::Compiler::Rascal2muRascal::RascalModule

import lang::rascal::\syntax::Rascal;
import Prelude;
import util::Reflective;
import util::ValueUI;
import ParseTree;

import lang::rascal::types::TestChecker;
import lang::rascal::types::CheckTypes;
import experiments::Compiler::Rascal2muRascal::RascalExpression;
import experiments::Compiler::Rascal2muRascal::RascalStatement;

import experiments::Compiler::muRascal::AST;

list[loc] libSearchPath = [|std:///|, |eclipse-std:///|];

public loc Example1 = |std:///experiments/Compiler/Rascal2muRascal/Examples/Example1.rsc|;
public loc Example2 = |std:///experiments/Compiler/Rascal2muRascal/Examples/Example2.rsc|;

public list[MuFunction] functions_in_module = [];
public list[MuVariable] variables_in_module = [];
public list[MuExp] variable_initializations = [];

MuModule r2mu(loc moduleLoc){
   try {
   	//Module M = parseModule(moduleLoc, libSearchPath);
   	lang::rascal::\syntax::Rascal::Module M = parse(#start[Module], moduleLoc);
   	Configuration c = newConfiguration();
   	config = checkModule(M.top, c);  // .top is needed to remove start! Ugly!
   	extractScopes();
   	errors = [ e | e:error(_,_) <- config.messages];
   	if(size(errors) > 0) {
   	  for(e <- errors) {
   	  	println(e);
   	  }
   	  throw "Module contains errors!";
   	} else {
   	  functions_in_module = [];
   	  variables_in_module = [];
   	  variable_initializations = [];
   	  list[Symbol] types = [ \type | int uid <- config.store, constructor(name, Symbol \type, containedIn, at) := config.store[uid] ];
   	  translate(M.top);
   	  return muModule("<M.top.header.name>", types, functions_in_module, variables_in_module, variable_initializations);
   	  }
   	} catch Java("ParseError","Parse error"): {
   	    throw "Syntax errors in module <Example1>";
   	} 
}

void translate(m: (Module) `<Header header> <Body body>`) {
	for( tl <- body.toplevels) translate(tl);
}
	
void translate(t: (Toplevel) `<Declaration decl>`) = translate(decl);

// Toplevel Declaration: variable

void translate(d: (Declaration) `<Tags tags> <Visibility visibility> <Type tp> <{Variable ","}+ variables> ;`) {
   	for(var <- variables){
   		variables_in_module += [muVariable("<var.name>")];
   		if(var is initialized) 
   		   variable_initializations +=  mkAssign("<var.name>", var@\loc, translate(var.initial)[0]);
   	}
}   	

void translate(d: (Declaration) `<Tags tags> <Visibility visibility> anno <Type annoType> <Type onType> @ <Name name> ;`) { throw("annotation"); }
void translate(d: (Declaration) `<Tags tags> <Visibility visibility> alias <UserType user> = <Type base> ;`)   { /* skip: translation has nothing to do here */ }
void translate(d: (Declaration) `<Tags tags> <Visibility visibility> tag <Kind kind> <Name name> on <{Type ","}+ types> ;`)  { throw("tag"); }
void translate(d: (Declaration) `<Tags tags> <Visibility visibility> data <UserType user> ;`)  { /* skip: translation has nothing to do here */ }
void translate(d: (Declaration) `<Tags tags> <Visibility visibility> data <UserType user> <CommonKeywordParameters commonKeywordParameters> = <{Variant "|"}+ variants> ;`) { /* skip: translation has nothing to do here */ }

void translate(d: (Declaration) `<FunctionDeclaration functionDeclaration>`) = translate(functionDeclaration);

// FunctionDeclaration

void translate(fd: (FunctionDeclaration) `<Tags tags> <Visibility visibility> <Signature signature> ;`)   { throw("abstract"); }

void translate(fd: (FunctionDeclaration) `<Tags tags> <Visibility visibility> <Signature signature> = <Expression expression> ;`){
  //ftypes = getFunctionType("<signature.name>");
  //if({ ftype } := ftypes){
  ftype = getFunctionType(fd@\loc);
  formals = signature.parameters.formals.formals;
  lformals = [f | f <- formals];
  scope = loc2uid[fd@\loc]; //getFunctionScope("<signature.name>");
	    
  functions_in_module += [muFunction("<signature.name>", scope, size(lformals), getScopeSize(scope), [muReturn(translate(expression)[0])])];
  //} else
  //    throw "overloaded function <signature.name>: <ftypes>";
}

void translate(fd: (FunctionDeclaration) `<Tags tags>  <Visibility visibility> <Signature signature> <FunctionBody body>`){
  //ftypes = getFunctionType("<signature.name>");
  //if({ ftype } := ftypes){
  ftype = getFunctionType(fd@\loc);    
  formals = signature.parameters.formals.formals;
  lformals = [f | f <- formals];
  tbody = [*translate(stat) | stat <- body.statements ];
  scope = loc2uid[fd@\loc]; //getFunctionScope("<signature.name>");
  functions_in_module += [muFunction("<signature.name>", scope, size(lformals), getScopeSize(scope), tbody)];
  //} else
  //    throw "overloaded function <signature.name>: <ftypes>"; 
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
