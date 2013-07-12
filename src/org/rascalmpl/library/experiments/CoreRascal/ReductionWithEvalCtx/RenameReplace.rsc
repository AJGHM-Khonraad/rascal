module experiments::CoreRascal::ReductionWithEvalCtx::RenameReplace

import experiments::CoreRascal::ReductionWithEvalCtx::AST;

@doc{The lambda expression part}
Exp rename(\true(), str y, str z) 					= \true();
Exp rename(\false(), str y, str z) 					= \false();
Exp rename(number(int n), str y, str z) 			= number(n);

Exp rename(id(x), str y, str z) 					= id(x == y ? z : x);
Exp rename(lambda(str x, Exp e), str y, str z) 		= (x == y) ? lambda(x, e) :  lambda(x, rename(e, y, z));
Exp rename(apply(Exp e1, Exp e2), str y, str z) 	= apply(rename(e1, y, z), rename(e2, y, z));

Exp rename(add(Exp e1, Exp e2), str y, str z) 		= add(rename(e1, y, z), rename(e2, y, z));
Exp rename(equ(Exp e1, Exp e2), str y, str z) 		= equ(rename(e1, y, z), rename(e2, y, z));

Exp rename(assign(str x, Exp e), str y, str z) 		= assign(x == y ? z : x, rename(e, y, z)); 
Exp rename(ifelse(Exp e0, Exp e1, Exp e2), str y, str z)  
													= ifelse(rename(e0, y, z), rename(e1, y, z), rename(e1, y, z));

// Exp rename(config(Exp exp, Stire store), str y, str z) 	= ?

@doc{Extension with co-routines}
Exp rename(label(str x), str y, str z) 				= label(x == y ? z : x);
Exp rename(labeled(str x, Exp e), str y, str z) 	= labeled(x == y ? z : x, rename(e, y, z));
Exp rename(create(Exp e), str y, str z) 			= create(rename(e, y, z));
Exp rename(resume(Exp e1, Exp e2), str y, str z) 	= resume(rename(e1, y, z), rename(e2, y, z));
Exp rename(yield(Exp e), str y, str z) 				= yield(rename(e, y, z));

@doc{Extension with continuations}
Exp rename(abort(Exp e), str y, str z) 				= abort(rename(e, y, z));
Exp rename(callcc(Exp e), str y, str z) 			= callcc(rename(e, y, z));

test bool tst() = rename(id("x"), "x", "z") == id("z");
test bool tst() = rename(id("x"), "y", "z") == id("x");
test bool tst() = rename(lambda("x", id("x")), "x", "z") == lambda("x", id("x"));
test bool tst() = rename(lambda("x", id("y")), "y", "z") == lambda("x", id("z"));
test bool tst() = rename(assign("x", id("y")), "y", "z") == assign("x", id("z"));
test bool tst() = rename(assign("x", id("y")), "x", "z") == assign("z", id("y"));
test bool tst() = rename(apply(id("x"), id("y")), "x", "z") == apply(id("z"), id("y"));
test bool tst() = rename(equ(id("x"), id("y")), "x", "z") == equ(id("z"), id("y"));


@doc{The lambda expression part}
Exp replace(\true(), str y, Exp v) 					= \true();
Exp replace(\false(), str y, Exp v) 				= \false();
Exp replace(number(int n), str y, Exp v) 			= number(n);

Exp replace(id(x), str y, Exp v) 					= x == y ?v : id(x);
Exp replace(lambda(str y, Exp e), str y, Exp v) 	= (x == y) ? lambda(x, e) :  lambda(x, replace(e, y, v));
Exp replace(apply(Exp e1, Exp e2), str y, Exp v) 	= apply(replace(e1, y, v), replace(e2, y, v));

Exp replace(add(Exp e1, Exp e2), str y, Exp v) 		= add(replace(e1, y, v), replace(e2, y, v));
Exp replace(equ(Exp e1, Exp e2), str y, Exp v) 		= equ(replace(e1, y, v), replace(e2, y, v));

Exp replace(assign(str x, Exp e), str y, Exp v) 		= assign(x, replace(e, y, v)); 
Exp replace(ifelse(Exp e0, Exp e1, Exp e2), str y, Exp v)  
													= ifelse(replace(e0, y, v), replace(e1, y, v), replace(e1, y, v));

// Exp replace(config(Exp vxp, Stire store), str y, Exp v) 	= ?

@doc{Extension with co-routines}
Exp replace(label(str x), str y, Exp v) 			= label(x);
Exp replace(labeled(str xx, Exp e), str y, Exp v) 	= labeled(x, replace(e, y, v));
Exp replace(create(Exp e), str y, Exp v) 			= create(replace(e, y, v));
Exp replace(resume(Exp e1, Exp e2), str y, Exp v) 	= resume(replace(e1, y, v), replace(e2, y, v));
Exp replace(yield(Exp e), str y, Exp v) 			= yield(replace(e, y, v));

@doc{Extension with continuations}
Exp replace(abort(Exp e), str y, Exp v) 			= abort(replace(e, y, v));
Exp replace(callcc(Exp e), str y, Exp v) 			= callcc(replace(e, y, v));
