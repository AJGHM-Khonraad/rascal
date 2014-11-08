@bootstrapParser
module experiments::Compiler::Compile

import Prelude;
import Message;

import lang::rascal::\syntax::Rascal;
import experiments::Compiler::muRascal::AST;
import experiments::Compiler::RVM::AST;

import experiments::Compiler::Rascal2muRascal::RascalModule;
import experiments::Compiler::muRascal2RVM::mu2rvm;

import lang::rascal::types::TestChecker;
import lang::rascal::types::CheckTypes;

str basename(loc l) = l.file[ .. findFirst(l.file, ".")];  // TODO: for library

loc compiledVersion(loc src, loc bindir) = (bindir + src.path)[extension="rvm"];

RVMProgram compile(str rascalSource, bool listing=false, bool recompile=false, loc bindir = |home:///bin|){
   muMod  = r2mu(parse(#start[Module], rascalSource).top);
   for(imp <- muMod.imports){
   	    println("Compiling import <imp>");
   	    compile(imp);
   	}
   rvmProgram = mu2rvm(muMod, listing=listing);
   return rvmProgram;
}

@doc{Compile a Rascal source module (given at a location) to RVM}

RVMProgram compile(loc moduleLoc,  bool listing=false, bool recompile=false, loc bindir = |home:///bin|){
    rvmProgram = compile(moduleLoc, {moduleLoc}, listing=listing, recompile=recompile,bindir=bindir);
    for(msg <- rvmProgram.messages){
        println(msg);
    }
    return rvmProgram;
}

private RVMProgram compile(loc moduleLoc,  set[loc] worklist, bool listing=false, bool recompile=false, loc bindir = |home:///bin|){
    println("compile: <moduleLoc>");
    rvmProgramLoc = compiledVersion(moduleLoc, bindir);
    if(!recompile && exists(rvmProgramLoc) && lastModified(rvmProgramLoc) > lastModified(moduleLoc)){
       try {
  	       rvmProgram = readTextValueFile(#RVMProgram, rvmProgramLoc);
  	       
  	       // Temporary work around related to issue #343
  	       rvmProgram = visit(rvmProgram) { case type[value] t: { insert type(t.symbol,t.definitions); }}
  	       
  	       println("rascal2rvm: Using compiled version <rvmProgramLoc>");
  	       return rvmProgram;
  	   } catch x: println("rascal2rvm: Reading <rvmProgramLoc> did not succeed: <x>");
  	}
    println("compile: parsing and compiling <moduleLoc>");
   
   	muMod = r2mu(parse(#start[Module], moduleLoc).top); // .top is needed to remove start! Ugly!
   	messages = muMod.messages;
   	
   	RVMProgram rvmProgram;
   	
   	if(any(msg <- messages, error(_,_) := msg)){
   	    println("compile: errors in <muMod.name>");
   	    for(e:error(_,_) <- messages){
   	        println(e);
   	    }
   	    rvmProgram = errorRVMProgram(muMod.name, messages);
   	} else {
       	for(imp <- muMod.imports, imp notin worklist){
       	    println("Compiling import <imp>");
       	    worklist += imp;
       	    rvm_imp = compile(imp, worklist);
       	    messages += rvm_imp.messages;
       	}
   	
       	if(any(msg <- messages, error(_,_) := msg)){
       	    println("compile: errors in imports of <muMod.name>");
       	    for(e:error(_,_) <- messages){
                println(e);
            }
       	    rvmProgram = errorRVMProgram(muMod.name, messages);
       	} else {
       	    println("compile: generate rvm <moduleLoc>");
       	    rvmProgram = mu2rvm(muMod, listing=listing); 
       	}                         
    }
   	println("rascal2rvm: Writing compiled version <rvmProgramLoc>");
   	writeTextValueFile(rvmProgramLoc, rvmProgram);
   	
   	return rvmProgram;
}

void listing(loc moduleLoc, str name = "", bool recompile=false){

	rvmProgram = compile(moduleLoc, recompile=recompile);
	
	if(name != ""){
		for(decl <- rvmProgram.declarations){
			if(findFirst(decl, name) >= 0){
				iprintln(rvmProgram.declarations[decl]);
			}
		}
		return;
	}
	
	println("MODULE\t<rvmProgram.name>");
	
	println("IMPORTS\t<rvmProgram.imports>");
	
	println("DECLARATIONS");
	
	for(decl <- rvmProgram.declarations){
		iprintln(rvmProgram.declarations[decl]);
	}
	
	println("INITIALIZATION");
	iprintln(rvmProgram.initialization);
	
	println("RESOLVER");
	print("\t"); iprintln(rvmProgram.resolver);
		
	println("OVERLOADED FUNCTIONS");
	print("\t"); iprintln(rvmProgram.overloaded_functions);
}