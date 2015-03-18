@doc{
Synopsis: Intermediate Language and Basic Algorithms for object flow analysis
  
Description:
  
The object flow language from the Tonella and Potrich book 
"Reverse Engineering Object Oriented Code" [tonella] is an intermediate
representation for object flow. We may translate for example
Java to this intermediate language and then analyze object flow
based on the simpler language.
  
The implementation in this file is intended to work with [M3] models
}
@bibliography{
@book{tonella,
 author = {Tonella, Paolo and Potrich, Alessandra},
 title = {Reverse Engineering of Object Oriented Code (Monographs in Computer Science)},
 year = {2004},
 isbn = {0387402950},
 publisher = {Springer-Verlag New York, Inc.},
 address = {Secaucus, NJ, USA},
} 
}
module analysis::flow::ObjectFlow

import List;

data FlowProgram = flowProgram(set[FlowDecl] decls, set[FlowStm] statements);

public loc emptyId = |id:///|;

@doc{Figure 2.1 [tonella]}
data FlowDecl 
	= attribute(loc id)
	| method(loc id, list[loc] formalParameters)
	| constructor(loc id, list[loc] formalParameters)
	;

@doc{Figure 2.1 [tonella]}
data FlowStm
	= newAssign(loc target, loc class, loc ctor, list[loc] actualParameters)
	| assign(loc target, loc cast, loc source)
	| call(loc target, loc cast, loc receiver, loc method, list[loc] actualParameters)
	;
	
alias OFG = rel[loc from, loc to];

@doc{Figure 2.2 [tonella]}
OFG buildFlowGraph(FlowProgram p)
  = { <as[i], fps[i]> | newAssign(x, cl, c, as) <- p.statements, constructor(c, fps) <- p.decls, i <- index(as) }
  + { <cl + "this", x> | newAssign(x, cl, _, _) <- p.statements }
  + { <y, x> | assign(x, _, y) <- p.statements}
  + { <as[i], fps[i]> | call(x, _, y, m, as) <- p.statements, method(m, fps) <- p.decls, i <- index(as) }
  + { <y, m + "this"> | call(_, _, y, m, _) <- p.statements }
  + { <m + "return", x> | call(x, _, _, m, _) <- p.statements, x != emptyId}
  ;

@doc{Section 2.4 [tonella]}
OFG propagate(OFG g, rel[loc,loc] gen, rel[loc,loc] kill, bool back) {
  OFG IN = { };
  OFG OUT = gen + (IN - kill);
  gi = g<to,from>;
  set[loc] pred(loc n) = gi[n];
  set[loc] succ(loc n) = g[n];

  solve (IN, OUT) {
    IN = { <n,\o> | n <- carrier(g), p <- (back ? pred(n) : succ(n)), \o <- OUT[p] };
    OUT = gen + (IN - kill);
  }

  return OUT;
}
	
	
	