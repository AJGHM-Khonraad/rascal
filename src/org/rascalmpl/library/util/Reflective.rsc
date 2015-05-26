@license{
  Copyright (c) 2009-2015 CWI
  All rights reserved. This program and the accompanying materials
  are made available under the terms of the Eclipse Public License v1.0
  which accompanies this distribution, and is available at
  http://www.eclipse.org/legal/epl-v10.html
}
@contributor{Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI}
@contributor{Tijs van der Storm - Tijs.van.der.Storm@cwi.nl}
@contributor{Mark Hills - Mark.Hills@cwi.nl (CWI)}
@contributor{Paul Klint - Paul.Klint@cwi.nl (CWI)}

module util::Reflective

import Exception;
import Message;
import ParseTree;
import IO;

public Tree getModuleParseTree(str modulePath) {
    mloc = getModuleLocation(modulePath);
    return parseModule(mloc);
}

@javaClass{org.rascalmpl.library.util.Reflective}
@reflect{Uses Evaluator to get back the parse tree for the given command}
public java Tree parseCommand(str command, loc location);

@javaClass{org.rascalmpl.library.util.Reflective}
@reflect{Uses Evaluator to get back the parse tree for the given commands}
public java Tree parseCommands(str commands, loc location);

@javaClass{org.rascalmpl.library.util.Reflective}
@reflect{Uses Evaluator to access the Rascal module parser}
@doc{This parses a module from a string, in its own evaluator context}
public java Tree parseModule(str moduleContent, loc location);

@javaClass{org.rascalmpl.library.util.Reflective}
@reflect{Uses Evaluator to access the Rascal module parser}
@doc{This parses a module on the search path, and loads it into the current evaluator including all of its imported modules}
public java Tree parseModule(loc location);

@javaClass{org.rascalmpl.library.util.Reflective}
@reflect{Uses Evaluator to access the Rascal module parser}
public java Tree parseModule(loc location, list[loc] searchPath);

@javaClass{org.rascalmpl.library.util.Reflective}
@reflect{Uses Evaluator to resolve a module name in the Rascal search path}
public java loc getModuleLocation(str modulePath);

@javaClass{org.rascalmpl.library.util.Reflective}
@reflect{Uses Evaluator to resolve a path name in the Rascal search path}
public java loc getSearchPathLocation(str filePath);

@doc{
Synopsis: Derive a location from a given location

Description:
Given a location, a file name extension, and a target directory,
a new location is constructed that is located in the target directory, with a
path that is derived from the authority and path in the given module location, and has the new file name extension.

The derived location points to a subdirectory named after either their authority or their scheme (in that order),
followed by the original path.

Examples:
<screen>
import util::Reflective;
getDerivedLocation(|std:///List.rsc|, "rvm");
getDerivedLocation(|project://rascal/src/org/rascalmpl/library/experiments/Compiler/Compile.rsc|, "rvm");
getDerivedLocation(|std:///experiments/Compiler/muRascal2RVM/LibraryGamma.mu|);
</screen>

Benefits:
This function is useful for type checking and compilation tasks, when derived information has to be stored
for source files in a separate directory.

}

loc getDerivedLocation(loc src, str extension, loc bindir = |home:///bin|, bool compressed = false){
	loc res;
	if(compressed){
		bindir.scheme = "compressed+" + bindir.scheme;
	}
	phys = getSearchPathLocation(src.path);
    if(exists(phys)){
		//println("phys = <phys>, src.path = <src.path>");
		if(phys.scheme == "std"){
			res = (bindir + "rascal/src/org/rascalmpl/library/" + phys.path)[extension=extension];
		} else {
			subdir = phys.authority;
			if(subdir == ""){
				subdir = phys.scheme;
			}
			res = (bindir + subdir + phys.path)[extension=extension];
		}
	} else {
	    if(src.scheme == "std")
	    	res = (bindir + "rascal/src/org/rascalmpl/library/" + src.path)[extension=extension];
	    else if(src.scheme == "project"){
	    	subdir = src.authority;
			if(subdir == ""){
				subdir = src.scheme;
			}
	    	res = (bindir + subdir + src.path)[extension=extension];
	    } else {
			res = (bindir + "rascal" + src.path)[extension=extension];
		}	
	}
	
	//println("getDerivedLocation: <src>, <extension>, <bindir> =\> <res>");
	return res;
}

@doc{Is the current Rascal code executed by the compiler or the interpreter?}
@javaClass{org.rascalmpl.library.util.Reflective}
public java bool inCompiledMode();

@doc{Give a textual diff between two values.}
@javaClass{org.rascalmpl.library.util.Reflective}
public java str diff(value old, value new);

@doc{Watch value val: 
- running in interpreted mode: write val to a file, 
- running in compiled mode: compare val with previously written value}
@javaClass{org.rascalmpl.library.util.Reflective}
@reflect{Uses Evaluator to resolve a module name in the Rascal search path}
public java &T watch(type[&T] tp, &T val, str name);

@javaClass{org.rascalmpl.library.util.Reflective}
@reflect{Uses Evaluator to resolve a module name in the Rascal search path}
public java &T watch(type[&T] tp, &T val, str name, value suffix);

@doc{Compute a fingerprint of a value for the benefit of the compiler and the compiler runtime}
@javaClass{org.rascalmpl.library.util.Reflective}
public java int getFingerprint(value val, bool concretePatterns);

@doc{Compute a fingerprint of a value and arity modifier for the benefit of the compiler and the compiler runtime}
@javaClass{org.rascalmpl.library.util.Reflective}
public java int getFingerprint(value val, int arity, bool concretePatterns);

@doc{Compute a fingerprint of a complete node for the benefit of the compiler and the compiler runtime}
@javaClass{org.rascalmpl.library.util.Reflective}
public java int getFingerprintNode(node nd);