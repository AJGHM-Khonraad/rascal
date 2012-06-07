@license{
  Copyright (c) 2009-2011 CWI
  All rights reserved. This program and the accompanying materials
  are made available under the terms of the Eclipse Public License v1.0
  which accompanies this distribution, and is available at
  http://www.eclipse.org/legal/epl-v10.html
}
@contributor{Paul Klint - Paul.Klint@cwi.nl - CWI}
@contributor{Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI}
module lang::rsf::IO
import Type;


@doc{Read an RSF file.

Read relations from an RSF file. An RSF file contains tuples of binary relations
in the following format:
	RelationName Arg1 Arg2
where each field is separated by a tabulation character (\t). One file may contain tuples for more than one relation. readRSF takes an RSF file nameRSFFile and generates a map[str,rel[str,str]] that maps each relation name to the actual relation.
}
@javaClass{org.rascalmpl.library.lang.rsf.RSFIO}
public java map[str, rel[str,str]] readRSF(str nameRSFFile);


@javaClass{org.rascalmpl.library.lang.rsf.RSFIO}
public java map[str, rel[str,str]] readRSF(str nameRSFFile);

@javaClass{org.rascalmpl.library.lang.rsf.RSFIO}
@reflect{Uses URI Resolver Registry}
public java map[str, Symbol] getRSFTypes(loc location);

@javaClass{org.rascalmpl.library.lang.rsf.RSFIO}
@reflect{Uses URI Resolver Registry}
public java &T readRSFRelation(type[&T] result, str name, loc location);


