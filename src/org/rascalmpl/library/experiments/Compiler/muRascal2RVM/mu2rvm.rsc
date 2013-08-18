module experiments::Compiler::muRascal2RVM::mu2rvm

import Prelude;

import experiments::Compiler::RVM::AST;

import experiments::Compiler::muRascal::Syntax;
import experiments::Compiler::muRascal::AST;
import experiments::Compiler::muRascal::Implode;


//import experiments::Compiler::muRascal2RVM::Library;

alias INS = list[Instruction];


// Unique label generator

int nlabel = -1;
str nextLabel() { nlabel += 1; return "L<nlabel>"; }

int functionScope = 0;

public loc Library = |std:///experiments/Compiler/muRascal2RVM/Library.mu|;

// Translate a muRascal module

RVMProgram mu2rvm(muModule(str name, list[Symbol] types, list[MuFunction] functions, list[MuVariable] variables, list[MuExp] initializations)){
  funMap = ();
  nLabel = -1;
  
  libModule = parse(Library);
  
  for(fun <-libModule.functions){
     funMap += (fun.name : FUNCTION(fun.name, fun.scope, fun.nformal, fun.nlocal, 10, trblock(fun.body)));
  }
 
  for(fun <- functions){
    functionScope = fun.scope;
    funMap += (fun.name : FUNCTION(fun.name, fun.scope, fun.nformal, fun.nlocal, 10, trblock(fun.body)));
  }
  
  funMap += ("#module_init" : FUNCTION("#module_init", 0, 0, size(variables) + 1, 10, 
  									[*tr(initializations), 
  									 LOADLOC(size(variables)), 
  									 CALL("main"), 
  									 RETURN1(),
  									 HALT()
  									]));
  res = rvm(types, funMap, []);
  iprintln( res);
  return res;
}


// Translate a muRascal function

// Translate lists of muRascal expressions

INS  tr(list[MuExp] exps) = [ *tr(exp) | exp <- exps ];

INS tr_and_pop(MuExp exp) = producesValue(exp) ? [*tr(exp), POP()] : tr(exp);


INS trvoidblock(list[MuExp] exps) {
  if(size(exps) == 0)
     return [];
  ins = [*tr_and_pop(exp) | exp <- exps];
  return ins;
}

INS trblock(list[MuExp] exps) {
  if(size(exps) == 0){
     return [LOADCON(666)]; // TODO: throw "Non void block cannot be empty";
  }
  ins = [*tr_and_pop(exp) | exp <- exps[0..-1]];
  return ins + tr(exps[-1]);
}

// Translate a single muRascal expression

INS tr(muCon("true")) = [LOADCON(true)];
INS tr(muCon("false")) = [LOADCON(false)];
default INS tr(muCon(value c)) = [LOADCON(c)];

INS tr(muTypeCon(Symbol sym)) = [LOADTYPE(sym)];

INS tr(muFun(str name)) = [LOADFUN(name)];

INS tr(muFun(str name, int scope)) = [LOAD_NESTED_FUN(name, scope)];

INS tr(muConstr(str name)) = [LOADCONSTR(name)];

INS tr(muVar(str id, int scope, int pos)) = [scope == functionScope ? LOADLOC(pos) : LOADVAR(scope, pos)];
INS tr(muLoc(str id, int pos)) = [LOADLOC(pos)];

Instruction mkCall(str name) = CALL(name); 

INS tr(muCallConstr(str cname, list[MuExp] args)) = [ *tr(args), CALLCONSTR(cname) ];

INS tr(muCall(str fname, list[MuExp] args)) = [*tr(args), CALL(fname)];

INS tr(muCall(MuExp fun, list[MuExp] args)) = [*tr(args), *tr(fun), CALLDYN()];

INS tr(muCallPrim(str name, MuExp arg)) = (name == "println") ? [*tr(arg), PRINTLN()] : [*tr(arg), CALLPRIM(name, 1)];

INS tr(muCallPrim(str name, MuExp arg1, MuExp arg2)) = [*tr(arg1), *tr(arg2), CALLPRIM(name, 2)];

INS tr(muCallPrim(str name, list[MuExp] args)) = [*tr(args), CALLPRIM(name, size(args))];

INS tr(muAssign(str id, int scope, int pos, MuExp exp)) { println("Translating muAssign: <id>::<scope>::<pos> = <exp>; fun_scope: <functionScope>"); return [*tr(exp), scope == functionScope ? STORELOC(pos) : STOREVAR(scope, pos)]; }
INS tr(muAssignLoc(str id, int pos, MuExp exp)) = [*tr(exp), STORELOC(pos) ];
INS tr(muAssignDyn(MuExp idExp, MuExp scopeExp, MuExp posExp, MuExp exp)) = [ *tr(exp), *tr(scopeExp), *tr(posExp), STOREVARDYN() ];

INS tr(muIfelse(MuExp cond, list[MuExp] thenPart, list[MuExp] elsePart)) {
    lab_else = nextLabel();
    lab_after = nextLabel();		
    return [ *tr_cond(cond), 
             JMPFALSE(lab_else),
             *trblock(thenPart), 
             JMP(lab_after), 
             LABEL(lab_else),
             *trblock(elsePart),
             LABEL(lab_after)
            ];
}

default INS tr(muWhile(MuExp cond, list[MuExp] body)) {
    lab_while = nextLabel();
    lab_after = nextLabel();
    return [LABEL(lab_while),
    		*tr_cond(cond), 	 	
    		JMPFALSE(lab_after),				
    		*trvoidblock(body),			
    		JMP(lab_while),
    		LABEL(lab_after)		
    		];
}

INS tr(muCreate(str name)) = [CREATE(name)];

INS tr(muCreate(str name, list[MuExp] args)) = [ *tr(args), CREATE(name, size(args))];
INS tr(muCreate(MuExp exp)) = [*tr(exp),CREATEDYN()];

INS tr(muInit(MuExp exp)) = [*tr(exp), INIT(1)];
INS tr(muInit(MuExp coro, list[MuExp] args)) = [*tr(args), *tr(coro),  INIT(size(args) + 1)];  // order!

INS tr(muNext(MuExp coro)) = [*tr(coro), NEXT0()];
INS tr(muNext(MuExp coro, list[MuExp] args)) = [*tr(args), *tr(coro),  NEXT1()]; // order!

INS tr(muYield()) = [YIELD0()];
INS tr(muYield(MuExp exp)) = [*tr(exp), YIELD1()];

INS tr(muReturn()) = [RETURN0()];
INS tr(muReturn(MuExp exp)) = [*tr(exp), RETURN1()];

INS tr(muHasNext(MuExp coro)) = [*tr(coro), HASNEXT()];

INS tr(muMulti(MuExp exp)) = 
	 [ *tr(exp),
       INIT(1),
       NEXT0()
    ];

default INS tr(e) { throw "Unknown node in the muRascal AST: <e>"; }

bool producesValue(muWhile(MuExp cond, list[MuExp] body)) = false;
default bool producesValue(MuExp exp) = true;

// Translate a condition. 

INS tr_cond(muOne(list[MuExp] args)){
    return [*tr_cond(a) | a <- args];
}


INS tr_cond(muAll(list[MuExp] args)){
    return [*tr_cond(a) | a <- args];
}

INS tr_cond(muMulti(MuExp exp), INS body) {
    [ *tr(exp),
       INIT(1),
       NEXT()
    ];
}

default INS tr_cond(MuExp exp) = tr(exp);
