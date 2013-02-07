@license{
  Copyright (c) 2009-2013 CWI
  All rights reserved. This program and the accompanying materials
  are made available under the terms of the Eclipse Public License v1.0
  which accompanies this distribution, and is available at
  http://www.eclipse.org/legal/epl-v10.html
}
@bootstrapParser
@doc{
In this test module we collect test cases that are associated with bugs from the past.
This is just to make sure the bugs are not re-introduced accidentally.
}
module lang::rascal::syntax::tests::SolvedIssues

import lang::rascal::syntax::Rascal;
import ParseTree;

public bool notAmb(Tree t) = /amb(_) !:= t;

public test bool amb1() = notAmb(parse(#Expression, "1 + -1"));

public test bool amb2() = notAmb(parse(#Command,"\"1\" + 2"));

public test bool amb3() = notAmb(parse(#Command,"true ? [1]"));

