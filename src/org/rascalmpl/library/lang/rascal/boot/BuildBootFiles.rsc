module lang::rascal::boot::BuildBootFiles

/********************************************************************************/
/*          Build Boot files for Rascal deployment                              */
/*                                                                              */
/* Compile and serialize:                                                       */
/* - (Selected parts of the) Rascal standard library                            */
/* - MuLibrary: part of the runtime system written in muRascal                  */
/* - ParserGenerator                                                            */
/* - Kernel: Rascal's compile and execute kernel                                */
/*                                                                              */
/* Usage:                                                                       */
/* - Adjust BOOT, where the compiled boot files will be stored                  */
/* - Adjust SHELLSCRIPT, a simple shell script that will overwrite the current  */
/*   boot files for Kernel, MuLibrary and ParserGenerator                       */
/* - main()                                                                     */
/* - if all went well: execute SHELLSCRIPT                                      */
/*                                                                              */
/* The final structure of BOOT will be:                                         */
/* BOOT/Kernel.rvm.ser.gz                                                       */
/* BOOT/MuLibrary.rvm.gz                                                        */
/* BOOT/ParserGenerator.rvm.ser.gz                                              */
/* BOOT/stdlib/*                                                                */
/*                                                                              */
/*                                                                              */
/* TODO:                                                                        */
/* - Add generated Java code for Rascal Parser (at the moment we reuse          */
/*   org.rascalmpl.library.lang.rascal.syntax.RascalParser                      */
/* - Better handling of BOOT and SHELLSCRIPT (keyword parameters of main?       */
/********************************************************************************/

import IO;
import String;
import util::Reflective;
import util::FileSystem;
import experiments::Compiler::Execute;
import experiments::Compiler::Compile;
import experiments::Compiler::CompileMuLibrary; 

loc BOOT = |file:///Users/paulklint/git/rascal/src/boot/|;
loc SHELLSCRIPT = |file:///Users/paulklint/install.sh|;

// Library modules that will be included in boot/stdlib

list[str] libraryModules =
[
    //"APIGen",
    "Ambiguity",
    "Boolean",
    "DateTime",
    "Exception",
    "Grammar",
    "IO",
    "List",
    "ListRelation",
    "Map",
    "Message",
    "Node",
    //"Origins",
    "ParseTree",
    "Prelude",
    "Relation",
    "Set",
    "String",
    "ToString",
    //"Traversal",
    "Tuple",
    "Type",
    "ValueIO",

    "util::Benchmark",
    "util::Brackets",
    "util::Cursor",
    "util::Eval",
    "util::FileSystem",
    "util::Highlight",
//    "util::LOC", data type name Output conflicts with Rascal.rsc
    "util::Math",
    "util::Maybe",
    "util::Monitor",
    "util::PriorityQueue",
    "util::REPL",
    "util::Reflective",
    "util::ShellExec",
    "util::SystemAPI",
    "util::UUID",
    "util::Webserver"    
];

// Compile and serialize a module and generate a command to move the result to the root of the BOOT directory

str serialize(str moduleName, PathConfig pcfg){
     report("Compiling <moduleName>");
     execute(moduleName, pcfg, recompile=true, serialize=true);
     serialized = getDerivedWriteLoc(moduleName, "rvm.ser.gz", pcfg);
     return "mv <serialized.path> <(pcfg.binDir.parent + serialized.file).path>\n";
}

// Fancy reporting

void report(str msg){
    println("**** <msg> ****");
}

// Where happiness begins

value main(){
     BOOTSTDLIB = BOOT + "stdlib";
     pcfg = pathConfig(srcPath=[|std:///|], binDir=BOOTSTDLIB, libPath=[BOOTSTDLIB]);
     
     report("Removing current compiled standard library <BOOTSTDLIB>");
     remove(BOOTSTDLIB);
     
     commands = "#!/bin/sh\n";
     
     report("Compiling MuLibrary");
     compileMuLibrary(pcfg);
     muLib = getMuLibraryCompiledWriteLoc(pcfg);
     commands += "mv <muLib.path> <(pcfg.binDir.parent + muLib.file).path>\n";
     
     report("Compiling standard library modules");
     for(moduleName <- libraryModules){
         compile(moduleName, pcfg, recompile=true, verbose=true);
     }
     
     commands += serialize("lang::rascal::grammar::ParserGenerator", pcfg);
     commands += serialize("lang::rascal::boot::Kernel", pcfg);
    
    writeFile(SHELLSCRIPT, commands);
    report("Commands written to <SHELLSCRIPT>");
    return true;
}
