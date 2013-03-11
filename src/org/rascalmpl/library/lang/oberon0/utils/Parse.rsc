@license{
  Copyright (c) 2009-2013 CWI
  All rights reserved. This program and the accompanying materials
  are made available under the terms of the Eclipse Public License v1.0
  which accompanies this distribution, and is available at
  http://www.eclipse.org/legal/epl-v10.html
}
@contributor{Tijs van der Storm - Tijs.van.der.Storm@cwi.nl}
module lang::oberon0::utils::Parse

import lang::oberon0::\syntax::Layout;
import lang::oberon0::\syntax::Lexical;
import lang::oberon0::\syntax::Expressions;
import lang::oberon0::\syntax::Types;
import lang::oberon0::\syntax::Statements;
import lang::oberon0::\syntax::Declarations;
import lang::oberon0::\syntax::Modules;

import ParseTree;

public Module parse(loc l) {
	return parse(#Module, l);
}







