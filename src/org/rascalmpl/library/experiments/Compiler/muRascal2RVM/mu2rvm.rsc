module experiments::Compiler::muRascal2RVM::mu2rvm

import IO;
import Type;
import List;
import Set;
import Map;
import ListRelation;
import Node;
import Message;
import String;
import ToString;
import experiments::Compiler::RVM::AST;

import experiments::Compiler::muRascal::AST;

import experiments::Compiler::Rascal2muRascal::TypeUtils;
import experiments::Compiler::Rascal2muRascal::TypeReifier;
import experiments::Compiler::muRascal2RVM::ToplevelType;
import experiments::Compiler::muRascal2RVM::PeepHole;
import experiments::Compiler::muRascal2RVM::StackValidator;


alias INS = list[Instruction];

// Unique label generator

private int nlabel = -1;
private str nextLabel() { nlabel += 1; return "L<nlabel>"; }

private str functionScope = "";					// scope name of current function, used to distinguish local and non-local variables
private str surroundingFunctionScope = "";		// scope name of surrounding function

public void setFunctionScope(str scopeId){
	functionScope = scopeId;
}

private map[str,int] nlocal = ();						// number of local per scope

private map[str,int] minNlocal = ();   

private map[str,str] scopeIn = ();						// scope nesting

int get_nlocals() = nlocal[functionScope];		

void set_nlocals(int n) {
	nlocal[functionScope] = n;
}

void init(){
    nlabel = -1;
    functionScope = "";
    surroundingFunctionScope = "";
    nlocal = ();
    minNLOcal = ();
    scopeIn = ();
    localNames = ();
    temporaries = [];
    //temporaries = ();
    tryBlocks = [];
    finallyBlocks = [];
    catchAsPartOfTryBlocks = [];
    catchBlocks = [[]];
    currentCatchBlock = 0;
    finallyBlock = [];
    exceptionTable = [];
}

// Map names of <fuid, pos> pairs to local variable names ; Note this info could also be collected in Rascal2muRascal

private map[int, str] localNames = ();

// Systematic label generation related to loops

str mkContinue(str loopname) = "CONTINUE_<loopname>";
str mkBreak(str loopname) = "BREAK_<loopname>";
str mkFail(str loopname) = "FAIL_<loopname>";
str mkElse(str branchname) = "ELSE_<branchname>";

// Exception handling: labels to mark the start and end of 'try', 'catch' and 'finally' blocks 
str mkTryFrom(str label) = "TRY_FROM_<label>";
str mkTryTo(str label) = "TRY_TO_<label>";
str mkCatchFrom(str label) = "CATCH_FROM_<label>";
str mkCatchTo(str label) = "CATCH_TO_<label>";
str mkFinallyFrom(str label) = "FINALLY_FROM_<label>";
str mkFinallyTo(str label) = "FINALLY_TO_<label>";

// Manage locals

private int newLocal() {
    n = nlocal[functionScope];
    nlocal[functionScope] = n + 1;
    return n;
}

private int newLocal(str fuid) {
    n = nlocal[fuid];
    nlocal[fuid] = n + 1;
    //println("newLocal:  nlocal[<fuid> = <nlocal[fuid]>");
    return n;
}

// Manage temporaries
private lrel[str name, str fuid, int pos] temporaries = [];

private str asUnwrappedThrown(str name) = name + "_unwrapped";

private int createTmp(str name, str fuid){
   newTmp = minNlocal[fuid] + size(temporaries[_,fuid]);
   
   if(newTmp < nlocal[fuid]){ // Can we reuse a tmp?
      temporaries += <name, fuid, newTmp>;
      return newTmp;
   } else {                    // No, create a new tmp
      newTmp = newLocal(fuid);
      temporaries += <name,fuid, newTmp>;
      return -newTmp;  // Return negative to signal a freshly allocated tmp
   }
}

private int createTmpCoRo(str fuid){
    co = createTmp("CORO", fuid);
    return co >= 0 ? co : -co;
}

private void destroyTmpCoRo(str fuid){
    destroyTmp("CORO", fuid);
}

private int getTmp(str name, str fuid){
   if([*int prev, int n] := temporaries[name,fuid]){
      return n;
   }
   println("*** Unknown temp <name>, <fuid> ***");
   n = createTmp(name, fuid);
   return n >= 0 ? n : -n;
   //throw "Unknown temp <name>, <fuid>";    
}

private void destroyTmp(str name, str fuid){
    if(fuid != functionScope) return;
    if([*int prev, int n] := temporaries[name,fuid]){
       temporaries -= <name, fuid, n>; 
       return;
    }
    throw "Non-existing temp <name>, <fuid>";     
}



INS tr(muBlockWithTmps(lrel[str name, str fuid] tmps, lrel[str name, str fuid] tmpRefs, list[MuExp] exps), Dest d) {
    // Create ordinary tmps (they are always initialized in the generated code)
    for(<nm, fd> <- tmps){
        createTmp(nm, fd);
    }
    
    // Create tmps that are used as reference (they may need to be reset here)
    resetCode = [ fd == functionScope ? RESETLOC(pos) : RESETVAR(fd, pos) | <nm, fd> <- tmpRefs, int pos := createTmp(nm, fd) , pos >= 0 ];
    
    code = resetCode +  tr(muBlock(exps), d);
    for(<nm, fd> <- tmps + tmpRefs){
        destroyTmp(nm, fd);
    }
    return code;
}

// Does an expression produce a value? (needed for cleaning up the stack)

//bool producesValue(muLab(_)) = false;

bool producesValue(muWhile(str label, MuExp cond, list[MuExp] body)) = false;

bool producesValue(muBreak(_)) = false;
bool producesValue(muContinue(_)) = false;
bool producesValue(muFail(_)) = false;
bool producesValue(muFailReturn()) = false;

bool producesValue(muReturn0()) = false;
bool producesValue(muGuard(_)) = false;
//bool producesValue(muYield0()) = false;
//bool producesValue(muExhaust()) = false;

//bool producesValue(muNext1(MuExp coro)) = false;
default bool producesValue(MuExp exp) = true;

// Management needed to compute exception tables

// An EEntry's handler is defined for ranges 
// this is needed to inline 'finally' blocks, which may be defined in different 'try' scopes, 
// into 'try', 'catch' and 'finally' blocks
alias EEntry = tuple[lrel[str,str] ranges, Symbol \type, str \catch, MuExp \finally];

// Stack of 'try' blocks (needed as 'try' blocks may be nested)
list[EEntry] tryBlocks = [];
list[EEntry] finallyBlocks = [];

// Functions to manage the stack of 'try' blocks
void enterTry(str from, str to, Symbol \type, str \catch, MuExp \finally) {
	tryBlocks = <[<from, to>], \type, \catch, \finally> + tryBlocks;
	finallyBlocks = <[<from, to>], \type, \catch, \finally> + finallyBlocks;
}
void leaveTry() {
	tryBlocks = tail(tryBlocks);
}
void leaveFinally() {
	finallyBlocks = tail(finallyBlocks);
}

// Get the label of a top 'try' block
EEntry topTry() = top(tryBlocks);

// 'Catch' blocks may also throw an exception, which must be handled by 'catch' blocks of surrounding 'try' block
list[EEntry] catchAsPartOfTryBlocks = [];

void enterCatchAsPartOfTryBlock(str from, str to, Symbol \type, str \catch, MuExp \finally) {
	catchAsPartOfTryBlocks = <[<from, to>], \type, \catch, \finally> + catchAsPartOfTryBlocks;
}
void leaveCatchAsPartOfTryBlocks() {
	catchAsPartOfTryBlocks = tail(catchAsPartOfTryBlocks);
}

EEntry topCatchAsPartOfTryBlocks() = top(catchAsPartOfTryBlocks);


// Instruction block of all the 'catch' blocks within a function body in the same order in which they appear in the code
list[INS] catchBlocks = [[]];
int currentCatchBlock = 0;

INS finallyBlock = [];

// As we use label names to mark try blocks (excluding 'catch' clauses)
list[EEntry] exceptionTable = [];

/*********************************************************************/
/*      Translate a muRascal module                                  */
/*********************************************************************/

// Translate a muRascal module

RVMModule mu2rvm(muModule(str module_name, 
						   map[str,str] tags,
						   set[Message] messages, 
						   list[str] imports,
						   list[str] extends, 
						   map[str,Symbol] types,  
						   map[Symbol, Production] symbol_definitions,
                           list[MuFunction] functions, list[MuVariable] variables, list[MuExp] initializations, 
                           int nlocals_in_initializations,
                           map[str,int] resolver,
                           lrel[str name, Symbol funType, str scope, list[str] ofunctions, list[str] oconstructors] overloaded_functions, 
                           map[Symbol, Production] grammar, 
                           rel[str,str] importGraph,
                           loc src), 
                  bool listing=false,
                  bool verbose=true){
 
  init();
  if(any(m <- messages, error(_,_) := m)){
    return errorRVMModule(module_name, messages, src);
  }
 
  main_fun = getUID(module_name,[],"MAIN",2);
  module_init_fun = getUID(module_name,[],"#<module_name>_init",2);
  ftype = Symbol::func(Symbol::\value(),[Symbol::\list(Symbol::\value())]);
  fun_names = { fun.qname | MuFunction fun <- functions };
  if(main_fun notin fun_names) {
  	 main_fun = getFUID(module_name,"main",ftype,0);
  	 module_init_fun = getFUID(module_name,"#<module_name>_init",ftype,0);
  }
 
  funMap = ();
  nlabel = -1;
  nlocal =   ( fun.qname : fun.nlocals | MuFunction fun <- functions ) 
           + ( module_init_fun : 2 + size(variables) + nlocals_in_initializations); 	// Initialization function, 2 for arguments
  minNlocal = nlocal;
  temporaries = [];
    
  if(verbose) println("mu2rvm: Compiling module <module_name>");
  
  for(fun <- functions) {
    scopeIn[fun.qname] = fun.scopeIn;
  }
 
  for(fun <- functions){
    functionScope = fun.qname;
    surroundingFunctionScope = fun.scopeIn;
    localNames = ();
    exceptionTable = [];
    catchBlocks = [[]];
    
    // Append catch blocks to the end of the function body code
    // code = tr(fun.body) + [ *catchBlock | INS catchBlock <- catchBlocks ];
    
    //println(functionScope);
    //iprintln(fun.body);
    
    code = tr(fun.body, stack());
    
   
    code = peephole(code);

    
    catchBlockCode = [ *catchBlock | INS catchBlock <- catchBlocks ];
    
    code = code /*+ [LABEL("FAIL_<fun.uqname>"), FAILRETURN()]*/ + catchBlockCode;
    
    // Debugging exception handling
    // println("FUNCTION BODY:");
    // for(ins <- code) {
    //	 println("	<ins>");
    // }
     //println("EXCEPTION TABLE:");
     //for(entry <- exceptionTable) {
    	// println("	<entry>");
     //}
    
     lrel[str from, str to, Symbol \type, str target, int fromSP] exceptions = 
    	[ <range.from, range.to, entry.\type, entry.\catch, 0>
    	| tuple[lrel[str,str] ranges, Symbol \type, str \catch, MuExp _] entry <- exceptionTable, 									 
    	  tuple[str from, str to] range <- entry.ranges
    	];
  
    <maxStack, exceptions> = validate(fun.src, code, exceptions);
    required_frame_size = nlocal[functionScope] + maxStack; // estimate_stack_size(fun.body);
    
    funMap += (fun is muCoroutine) ? (fun.qname : COROUTINE(fun.qname, 
                                                            fun.uqname, 
                                                            fun.scopeIn, 
                                                            fun.nformals, 
                                                            nlocal[functionScope], 
                                                            localNames, 
                                                            fun.refs, 
                                                            fun.src, 
                                                            required_frame_size, 
                                                            code, 
                                                            exceptions))
    							   : (fun.qname : FUNCTION(fun.qname, 
    							   						   fun.uqname, 
    							   						   fun.ftype, 
    							   						   fun.scopeIn, 
    							   						   fun.nformals, 
    							   						   nlocal[functionScope], 
    							   						   localNames, 
    							   						   fun.isVarArgs, 
    							   						   fun.isPublic,
    							   						   "default" in fun.modifiers,
    							   						   fun.src, 
    							   						   required_frame_size, 
    							   						   fun.isConcreteArg,
    							   						   fun.abstractFingerprint,
    							   						   fun.concreteFingerprint,
    							   						   code, 
    							   						   exceptions));
  
  	if(listing){
  		println("===================== <fun.qname>");
  		iprintln(fun);
  		println("--------------------- <fun.qname>");
  		iprintln(funMap[fun.qname]);
  	}
  }
  
  functionScope = module_init_fun;
  code = trvoidblock(initializations); // compute code first since it may generate new locals!
  <maxSP, dummy_exceptions> = validate(|init:///|, code, []);
  funMap += ( module_init_fun : FUNCTION(module_init_fun, "init", ftype, "" /*in the root*/, 2, nlocal[module_init_fun], (), false, true, false, src, maxSP + nlocal[module_init_fun],
  										 false, 0, 0,
  								    [*code, 
  								     PUSHCON(true),
  								     RETURN1(1),
  								     HALT()
  								    ],
  								    []));
 
  if(listing){
  	println("===================== INIT: (nlocals_in_initializations = <nlocals_in_initializations>):");
  	iprintln(initializations);
  	println("--------------------- INIT");
  	iprintln(funMap[module_init_fun]);
  }
  
  main_testsuite = getUID(module_name,[],"TESTSUITE",1);
  module_init_testsuite = getUID(module_name,[],"#module_init_testsuite",1);
  if(!funMap[main_testsuite]?) { 						
  	 main_testsuite = getFUID(module_name,"testsuite",ftype,0);
  	 module_init_testsuite = getFUID(module_name,"#module_init_testsuite",ftype,0);
  }
  
  res = rvmModule(module_name, (module_name: tags), messages, imports, extends, types, symbol_definitions, orderedDeclarations(funMap), [], resolver, overloaded_functions, importGraph, src);
  return res;
}

list[RVMDeclaration] orderedDeclarations(map[str,RVMDeclaration] funMap) =
    [ funMap[fname] | fname <- sort(toList(domain(funMap))) ];

/*********************************************************************/
/*      Top of stack optimization framework                          */
/*********************************************************************/
data Dest = accu() | stack() | local(int pos) | var(str fuid, int pos) | con(value v) | nowhere();

INS plug(Dest d1, Dest d2) = [ ] when d1 == d2;

INS plug(accu(), accu()) = [ ];
INS plug(accu(), stack()) = [ PUSHACCU() ];
INS plug(accu(), local(pos)) = [ STORELOC(pos)];
INS plug(accu(), var(fuid, pos)) = [ STOREVAR(fuid, pos)];
INS plug(accu(), nowhere()) = [ ];

INS plug(con(v), accu()) = [ LOADCON(v) ];
INS plug(con(v), stack()) = [ PUSHCON(v) ];
INS plug(con(v), local(pos)) = [ LOADCON(v), STORELOC(pos)];
INS plug(con(v), var(fuid, pos)) = [ LOADCON(v), STOREVAR(fuid, pos)];
INS plug(con(v), nowhere()) = [ ];

INS plug(local(pos), accu()) = [ LOADLOC(pos) ];
INS plug(local(pos), stack()) = [ PUSHLOC(pos) ];
INS plug(local(pos1), local(pos2)) = [ LOADLOC(pos1), STORELOC(pos2) ];
INS plug(local(pos1), var(fuid, pos2)) = [ LOADLOC(pos1), STOREVAR(fuid, pos2)];
INS plug(local(pos), nowhere()) = [ ];

INS plug(var(fuid, pos), accu()) = [ LOADVAR(fuid, pos) ];
INS plug(var(fuid, pos), stack()) = [ LOADVAR(fuid, pos), PUSHACCU() ];
INS plug(var(fuid, pos1), local(pos2)) = [LOADVAR(fuid, pos1), STORELOC(pos2) ];
INS plug(var(fuid, pos), nowhere()) = [ ];

INS plug(stack(), accu()) = [ POPACCU() ];
INS plug(stack(), nowhere()) = [ POP() ];

INS plug(Dest d, nowhere()) = [];

/*********************************************************************/
/*      Translate lists of muRascal expressions                      */
/*********************************************************************/

INS tr(list[MuExp] exps) = [ *tr(exp, stack()) | exp <- exps ];

INS tr_and_pop(muBlock([])) = [];

default INS tr_and_pop(MuExp exp) = tr(exp, nowhere());

INS trblock(list[MuExp] exps, Dest d) {
  if(size(exps) == 0){
     return plug(con(666), d); // TODO: throw "Non void block cannot be empty";
  }
  ins = [*tr_and_pop(exp) | exp <- exps[0..-1]];
  ins += tr(exps[-1], d);
  if(!producesValue(exps[-1])){
  	ins += plug(con(666), d);
  }
  return ins;
}

//default INS trblock(MuExp exp) = tr(exp);

INS trvoidblock(list[MuExp] exps){
  if(size(exps) == 0)
     return [];
  ins = [*tr_and_pop(exp) | exp <- exps];
  return ins;
}

INS tr(muBlock([MuExp exp]), Dest d) = tr(exp, d);

default INS tr(muBlock(list[MuExp] exps), Dest d) = trblock(exps, d);


/*********************************************************************/
/*      Translate a single muRascal expression                       */
/*********************************************************************/

INS tr(MuExp exp) = tr(exp, stack());

// Literals and type constants

INS tr(muBool(bool b), Dest d) = plug(con(b), d); //LOADBOOL(b) + plug(accu(), d);

INS tr(muInt(int n), Dest d) = LOADINT(n) + plug(accu(), d);

/*default*/ INS tr(muCon(value c), Dest d) = plug(con(c), d);

INS tr(muTypeCon(Symbol sym), Dest d) = LOADTYPE(sym) + plug(stack(), d);

// muRascal functions

INS tr(muFun1(str fuid), Dest d) = LOADFUN(fuid) + plug(stack(), d);
INS tr(muFun2(str fuid, str scopeIn), Dest d) = LOAD_NESTED_FUN(fuid, scopeIn) + plug(stack(), d);

// Rascal functions

INS tr(muOFun(str fuid), Dest d) = LOADOFUN(fuid) + plug(stack(), d);

INS tr(muConstr(str fuid), Dest d) = LOADCONSTR(fuid) + plug(stack(), d);

// Variables and assignment

INS tr(muVar(str id, str fuid, int pos), Dest d) {
   
    if(fuid == functionScope){
       localNames[pos] = id;
       return plug(local(pos), d);
    } else {
       return plug(var(fuid, pos), d);
    }
}

INS tr(muLoc(str id, int pos), Dest d) { localNames[pos] = id; return plug(local(pos), d);}

INS tr(muResetLocs(list[int] positions), Dest d) { return [RESETLOCS(positions)];}

INS tr(muTmp(str id, str fuid), Dest d) = fuid == functionScope ? plug(local(getTmp(id,fuid)), d) : plug(var(fuid,getTmp(id,fuid)), d);

INS tr(muLocKwp(str name), Dest d) = [ LOADLOCKWP(name), *plug(stack(), d) ];
INS tr(muVarKwp(str fuid, str name), Dest d) = [ fuid == functionScope ? LOADLOCKWP(name) : LOADVARKWP(fuid, name) ] + plug(stack(), d);

INS tr(muLocDeref(str name, int pos), Dest d) = [ LOADLOCDEREF(pos) ] + plug(stack(), d);
INS tr(muVarDeref(str name, str fuid, int pos), Dest d) = [ fuid == functionScope ? LOADLOCDEREF(pos) : LOADVARDEREF(fuid, pos) ]+ plug(stack(), d);

INS tr(muLocRef(str name, int pos), Dest d) = [ LOADLOCREF(pos) ] + plug(stack(), d);
INS tr(muVarRef(str name, str fuid, int pos), Dest d) = [ fuid == functionScope ? LOADLOCREF(pos) : LOADVARREF(fuid, pos) ] + plug(stack(), d);
INS tr(muTmpRef(str name, str fuid), Dest d) = [ fuid == functionScope ? LOADLOCREF(getTmp(name,fuid)) : LOADVARREF(fuid,getTmp(name,fuid)) ] + plug(stack(), d);

INS tr(muAssignLocDeref(str id, int pos, MuExp exp), Dest d) = [ *tr(exp, stack()), STORELOCDEREF(pos), *plug(stack(), d) ];
INS tr(muAssignVarDeref(str id, str fuid, int pos, MuExp exp), Dest d) = [ *tr(exp, stack()), fuid == functionScope ? STORELOCDEREF(pos) : STOREVARDEREF(fuid, pos),  *plug(stack(), d)  ];

INS tr(muAssign(str id, str fuid, int pos, MuExp exp), Dest d) { 
     if(fuid == functionScope){
        localNames[pos] = id; 
        return [ *tr(exp, accu()), STORELOC(pos), *plug(accu(), d) ];
     } else {
        return [*tr(exp, accu()), STOREVAR(fuid, pos), *plug(accu(), d) ];
     }
}     
INS tr(muAssignLoc(str id, int pos, MuExp exp), Dest d) { 
    localNames[pos] = id;
    return [*tr(exp, accu()), STORELOC(pos), *plug(accu(), d) ];
}
INS tr(muAssignTmp(str id, str fuid, MuExp exp), Dest d) = [*tr(exp, accu()), fuid == functionScope ? STORELOC(getTmp(id,fuid)) : STOREVAR(fuid,getTmp(id,fuid)) ] + plug(accu(), d);

INS tr(muAssignLocKwp(str name, MuExp exp), Dest d) = [ *tr(exp, stack()), STORELOCKWP(name) ];
INS tr(muAssignKwp(str fuid, str name, MuExp exp), Dest d) = [ *tr(exp, stack()), fuid == functionScope ? STORELOCKWP(name) : STOREVARKWP(fuid,name) ] + plug(stack(), d);

// Calls

// Constructor

INS tr(muCallConstr(str fuid, list[MuExp] args), Dest d) = [ *tr(args), CALLCONSTR(fuid, size(args)), *plug(stack(), d) ];

// muRascal functions

INS tr(muCall(muFun1(str fuid), list[MuExp] args), Dest d) = [*tr(args), CALL(fuid, size(args)), *plug(stack(), d)];
INS tr(muCall(muConstr(str fuid), list[MuExp] args), Dest d) = [*tr(args), CALLCONSTR(fuid, size(args)), *plug(stack(), d)];
default INS tr(muCall(MuExp fun, list[MuExp] args), Dest d) = [*tr(args), *tr(fun, stack()), CALLDYN(size(args)), *plug(stack(), d)];

// Partial application of muRascal functions

INS tr(muApply(muFun1(str fuid), []), Dest d) = [ LOADFUN(fuid), *plug(stack(), d) ];
INS tr(muApply(muFun1(str fuid), list[MuExp] args), Dest d) = [ *tr(args), APPLY(fuid, size(args)), *plug(stack(), d) ];
INS tr(muApply(muConstr(str fuid), list[MuExp] args), Dest d) { throw "Partial application is not supported for constructor calls!"; }
INS tr(muApply(muFun2(str fuid, str scopeIn), []), Dest d) = [ LOAD_NESTED_FUN(fuid, scopeIn), *plug(stack(), d) ];
default INS tr(muApply(MuExp fun, list[MuExp] args), Dest d) = [ *tr(args), *tr(fun, stack()), APPLYDYN(size(args)), *plug(stack(), d)  ];

// Rascal functions

INS tr(muOCall3(muOFun(str fuid), list[MuExp] args, loc src), Dest d) = 
    [*tr(args), OCALL(fuid, size(args), src), *plug(stack(), d)];

INS tr(muOCall4(MuExp fun, Symbol types, list[MuExp] args, loc src), Dest d) 
	= [ *tr(args),
	    *tr(fun, stack()), 
		OCALLDYN(types, size(args), src),
		*plug(stack(), d)
      ];
		
// Visit
INS tr(muVisit(bool direction, bool fixedpoint, bool progress, bool rebuild, MuExp descriptor, MuExp phi, MuExp subject, MuExp refHasMatch, MuExp refBeenChanged, MuExp refLeaveVisit, MuExp refBegin, MuExp refEnd), Dest d)
	= [ *tr(phi, stack()),
	    *tr(subject, stack()),
	    *tr(refHasMatch, stack()),
	    *tr(refBeenChanged, stack()),
	    *tr(refLeaveVisit, stack()),
	    *tr(refBegin, stack()),
	    *tr(refEnd, stack()),
	    *tr(descriptor, stack()),
	    VISIT(direction, fixedpoint, progress, rebuild),
	    *plug(stack(), d)
	  ];


// Calls to Rascal primitives that are directly translated to RVM instructions

INS tr(muCallPrim3("println", list[MuExp] args, loc src), Dest d) = [*tr(args), PRINTLN(size(args))];

INS tr(muCallPrim3("subtype", list[MuExp] args, loc src), Dest d) = 
    [*tr(args[0], stack()),*tr(args[1], accu()), SUBTYPE(), *plug(accu(), d)];
    
INS tr(muCallPrim3("typeOf", list[MuExp] args, loc src), Dest d) = 
    [*tr(args[0], accu()), TYPEOF(), *plug(accu(), d)];
    
INS tr(muCallPrim3("check_memo", list[MuExp] args, loc src), Dest d) = 
    [CHECKMEMO(), *plug(accu(), d)];

INS tr(muCallPrim3("subtype_value_type", [exp1,  muTypeCon(Symbol tp)], loc src), Dest d) = 
    [*tr(exp1, accu()), VALUESUBTYPE(tp), *plug(accu(), d)];

default INS tr(muCallPrim3(str name, list[MuExp] args, loc src), Dest d) {
  n = size(args);
  if(name in {"node_create", "list_create", "set_create", "tuple_create", "map_create", 
                "listwriter_add", "setwriter_add", "mapwriter_add", "str_add_str", "template_open",
                "tuple_field_project", "adt_field_update", "rel_field_project", "lrel_field_project", "map_field_project",
                "list_slice_replace", "list_slice_add", "list_slice_subtract", "list_slice_product", "list_slice_divide", 
                "list_slice_intersect", "str_slice_replace", "node_slice_replace", "list_slice", 
                "rel_subscript", "lrel_subscript" }){ // varyadic MuPrimitives
        return [*tr(args), CALLPRIMN(name, n, src), *plug(accu(), d)];
    }
    
    switch(n){
        case 0: return CALLPRIM0(name, src) + plug(accu(), d);
        case 1: return [*tr(args[0], accu()), CALLPRIM1(name,src), *plug(accu(), d)];
        case 2: return [*tr(args[0], stack()), *tr(args[1], accu()), CALLPRIM2(name,src), *plug(accu(), d)];
        default: return [*tr(args), CALLPRIMN(name, n, src), *plug(accu(), d)];
   }
}

// Calls to MuRascal primitives that are directly translated to RVM instructions

INS tr(muCallMuPrim("println", list[MuExp] args), Dest d) = [*tr(args), PRINTLN(size(args))];
INS tr(muCallMuPrim("subscript_array_mint", list[MuExp] args), Dest d) = 
    [*tr(args[0], stack()),*tr(args[1], accu()), SUBSCRIPTARRAY(), *plug(accu(), d)];
    
INS tr(muCallMuPrim("subscript_list_mint", list[MuExp] args), Dest d) = 
    [*tr(args[0], stack()), *tr(args[1], accu()), SUBSCRIPTLIST(), *plug(accu(), d)];
    
//INS tr(muCallMuPrim("less_mint_mint", list[MuExp] args), Dest d) {
//    res = [*tr(args[0], stack()), *tr(args[1], accu()), LESSINT(), *plug(accu(), d)];
//    println(res);
//    return res;
//}    
INS tr(muCallMuPrim("greater_equal_mint_mint", list[MuExp] args), Dest d) = 
    [*tr(args[0], stack()), *tr(args[1], accu()), GREATEREQUALINT(), *plug(accu(), d)];

INS tr(muCallMuPrim("addition_mint_mint", list[MuExp] args), Dest d) = 
    [*tr(args[0], stack()), *tr(args[1], accu()), ADDINT(), *plug(accu(), d)];
    
INS tr(muCallMuPrim("subtraction_mint_mint", list[MuExp] args), Dest d) = 
    [*tr(args[0], stack()), *tr(args[1], accu()), SUBTRACTINT(), *plug(accu(), d)];
    
INS tr(muCallMuPrim("and_mbool_mbool", list[MuExp] args), Dest d) = 
    [*tr(args[0], stack()), *tr(args[1], accu()), ANDBOOL(), *plug(accu(), d)];

INS tr(muCallMuPrim("check_arg_type_and_copy", [muCon(int pos1), muTypeCon(Symbol tp), muCon(int pos2)]), Dest d) = 
    CHECKARGTYPEANDCOPY(pos1, tp, pos2) + plug(accu(), d);
    
INS tr(muCallMuPrim("make_mmap", []), Dest d) =  LOADEMPTYKWMAP() + plug(stack(), d);
    
default INS tr(muCallMuPrim(str name, list[MuExp] args), Dest d) {
   n = size(args);
   if(name in {"make_array", "make_mmap", "copy_and_update_keyword_mmap"}){ // varyadic MuPrimtives
        return [*tr(args), CALLMUPRIMN(name, n), *plug(accu(), d)];
    }
    
    switch(n){
        case 0: return CALLMUPRIM0(name) + plug(accu(), d);
        case 1: return [*tr(args[0], accu()), CALLMUPRIM1(name), *plug(accu(), d)];
        case 2: return [*tr(args[0], stack()), *tr(args[1], accu()), CALLMUPRIM2(name), *plug(accu(), d)];
        default: return [*tr(args), CALLMUPRIMN(name, n), *plug(accu(), d)];
   }
}

default INS tr(muCallJava(str name, str class, Symbol parameterTypes, Symbol keywordTypes, int reflect, list[MuExp] args), Dest d) = 
	[ *tr(args), CALLJAVA(name, class, parameterTypes, keywordTypes, reflect), *plug(stack(), d) ];

// Return

INS tr(muReturn0(), Dest d) = [RETURN0()];

INS tr(muReturn1(MuExp exp), Dest d) {
	if(muTmp(_,_) := exp) {
		inlineMuFinally();
		return [*finallyBlock, *tr(exp, stack()), RETURN1(1)];
	}
	return [*tr(exp, stack()), RETURN1(1)];
}
INS tr(muReturn2(MuExp exp, list[MuExp] exps), Dest d)
	= [*tr(exp, stack()), *tr(exps), RETURN1(size(exps) + 1)];

INS tr(muFailReturn(), Dest d) = [ FAILRETURN() ];

INS tr(muFilterReturn(), Dest d) = [ FILTERRETURN() ];

// Coroutines

INS tr(muCreate1(muFun1(str fuid)), Dest d) = [ CREATE(fuid, 0), *plug(stack(), d) ];
INS tr(muCreate1(MuExp exp), Dest d) = [ *tr(exp, stack()), CREATEDYN(0), *plug(stack(), d) ];
INS tr(muCreate2(muFun1(str fuid), list[MuExp] args), Dest d) = [ *tr(args), CREATE(fuid, size(args)), *plug(stack(), d) ];
INS tr(muCreate2(MuExp coro, list[MuExp] args), Dest d) = [ *tr(args), *tr(coro, stack()),  CREATEDYN(size(args)), *plug(stack(), d) ];  // order! 

INS tr(muNext1(MuExp coro), Dest d) = [*tr(coro, stack()), NEXT0(), *plug(stack(), d)];
INS tr(muNext2(MuExp coro, list[MuExp] args), Dest d) = [*tr(args), *tr(coro, stack()),  NEXT1(), *plug(stack(), d)]; // order!

INS tr(muYield0(), Dest d) = [YIELD0(), *plug(stack(), d)];
INS tr(muYield1(MuExp exp), Dest d) = [*tr(exp, stack()), YIELD1(1), *plug(stack(), d)];
INS tr(muYield2(MuExp exp, list[MuExp] exps), Dest d) = [ *tr(exp, stack()), *tr(exps), YIELD1(size(exps) + 1), *plug(stack(), d) ];

INS tr(experiments::Compiler::muRascal::AST::muExhaust(), Dest d) = [ EXHAUST() ];

INS tr(muGuard(MuExp exp), Dest d) = [ *tr(exp, stack()), GUARD() ];

// Exceptions

INS tr(muThrow(MuExp exp, loc src), Dest d) = [ *tr(exp, stack()), THROW(src) ];

// Temporary fix for Issue #781
Symbol filterExceptionType(Symbol s) = s == adt("RuntimeException",[]) ? Symbol::\value() : s;

INS tr(muTry(MuExp exp, MuCatch \catch, MuExp \finally), Dest d) {
    
	// Mark the begin and end of the 'try' and 'catch' blocks
	str tryLab = nextLabel();
	str catchLab = nextLabel();
	str finallyLab = nextLabel();
	
	str try_from      = mkTryFrom(tryLab);
	str try_to        = mkTryTo(tryLab);
	str catch_from    = mkCatchFrom(catchLab); // used to jump
	str catch_to      = mkCatchTo(catchLab);   // used to mark the end of a 'catch' block and find a handler catch
	
	// Mark the begin of 'catch' blocks that have to be also translated as part of 'try' blocks 
	str catchAsPartOfTry_from = mkCatchFrom(nextLabel()); // used to find a handler catch
	
	// There might be no surrounding 'try' block for a 'catch' block
	if(!isEmpty(tryBlocks)) {
		// Get the outer 'try' block
		EEntry currentTry = topTry();
		// Enter the current 'catch' block as part of the outer 'try' block
		enterCatchAsPartOfTryBlock(catchAsPartOfTry_from, catch_to, currentTry.\type, currentTry.\catch, currentTry.\finally);
	}
	
	// Enter the current 'try' block; also including a 'finally' block
	enterTry(try_from, try_to, \catch.\type, catch_from, \finally); 
	
	// Translate the 'try' block; inlining 'finally' blocks where necessary
	code = [ LABEL(try_from), *tr(exp, d) ];
	
	oldFinallyBlocks = finallyBlocks;
	leaveFinally();
	
	// Fill in the 'try' block entry into the current exception table
	currentTry = topTry();
	exceptionTable += <currentTry.ranges, filterExceptionType(currentTry.\type), currentTry.\catch, currentTry.\finally>;
	
	leaveTry();
	
	// Translate the 'finally' block; inlining 'finally' blocks where necessary
	code = code + [ LABEL(try_to), *trMuFinally(\finally) ];
	
	// Translate the 'catch' block; inlining 'finally' blocks where necessary
	// 'Catch' block may also throw an exception, and if it is part of an outer 'try' block,
	// it has to be handled by the 'catch' blocks of the outer 'try' blocks
	
	oldTryBlocks = tryBlocks;
	tryBlocks = catchAsPartOfTryBlocks;
	finallyBlocks = oldFinallyBlocks;
	
	trMuCatch(\catch, catch_from, catchAsPartOfTry_from, catch_to, try_to, d);
		
	// Restore 'try' block environment
	catchAsPartOfTryBlocks = tryBlocks;
	tryBlocks = oldTryBlocks;
	finallyBlocks = tryBlocks;
	
	// Fill in the 'catch' block entry into the current exception table
	if(!isEmpty(tryBlocks)) {
		EEntry currentCatchAsPartOfTryBlock = topCatchAsPartOfTryBlocks();
		exceptionTable += <currentCatchAsPartOfTryBlock.ranges, filterExceptionType(currentCatchAsPartOfTryBlock.\type), currentCatchAsPartOfTryBlock.\catch, currentCatchAsPartOfTryBlock.\finally>;
		leaveCatchAsPartOfTryBlocks();
	}

	return code;// + plug(stack(), d);
}

void trMuCatch(m: muCatch(str id, str fuid, Symbol \type, MuExp exp), str from, str fromAsPartOfTryBlock, str to, str jmpto, Dest d) {
    
    //println("trMuCatch:");
    //println("catchBlocks = <catchBlocks>");
    //println("currentCatchBlock = <currentCatchBlock>");
    //iprintln(m);
    createTmp(id, fuid);
    createTmp(asUnwrappedThrown(id), fuid);
	oldCatchBlocks = catchBlocks;
	oldCurrentCatchBlock = currentCatchBlock;
	currentCatchBlock = size(catchBlocks);
	catchBlocks = catchBlocks + [[]];
	catchBlock = [];
	
	str catchAsPartOfTryNewLab = nextLabel();
	str catchAsPartOfTryNew_from = mkCatchFrom(catchAsPartOfTryNewLab);
	str catchAsPartOfTryNew_to = mkCatchTo(catchAsPartOfTryNewLab);
	
	// Copy 'try' block environment of the 'catch' block; needed in case of nested 'catch' blocks
	catchAsPartOfTryBlocks = [ < [<catchAsPartOfTryNew_from, catchAsPartOfTryNew_to>],
								 entry.\type, entry.\catch, entry.\finally > | EEntry entry <- catchAsPartOfTryBlocks ];
	
	if(muBlock([]) := exp) {
		catchBlock = [ LABEL(from), POP(), LABEL(to), JMP(jmpto) ];
	} else {
		catchBlock = [ LABEL(from), 
					   // store a thrown value
					   POPACCU(),
					   fuid == functionScope ? STORELOC(getTmp(id,fuid)) : STOREVAR(fuid,getTmp(id,fuid)),
					   // load a thrown value,
					   fuid == functionScope ? LOADLOC(getTmp(id,fuid))  : LOADVAR(fuid,getTmp(id,fuid)), PUSHACCU(),
					   // unwrap it and store the unwrapped one in a separate local variable 
					   fuid == functionScope ? UNWRAPTHROWNLOC(getTmp(asUnwrappedThrown(id),fuid)) : UNWRAPTHROWNVAR(fuid,getTmp(asUnwrappedThrown(id),fuid)),
					   *tr(exp, d), LABEL(to), JMP(jmpto) ];
	}
	
	if(!isEmpty(catchBlocks[currentCatchBlock])) {
		catchBlocks[currentCatchBlock] = [ LABEL(catchAsPartOfTryNew_from), *catchBlocks[currentCatchBlock], LABEL(catchAsPartOfTryNew_to) ];
		for(currentCatchAsPartOfTryBlock <- catchAsPartOfTryBlocks) {
			exceptionTable += <currentCatchAsPartOfTryBlock.ranges, filterExceptionType(currentCatchAsPartOfTryBlock.\type), currentCatchAsPartOfTryBlock.\catch, currentCatchAsPartOfTryBlock.\finally>;
		}
	} else {
		catchBlocks = oldCatchBlocks;
	}
	
	currentCatchBlock = oldCurrentCatchBlock;
	
	// 'catchBlock' is always non-empty 
	//println("currentCatchBlock = <currentCatchBlock>");
	//println("catchBlocks = <catchBlocks>");
	
	catchBlocks[currentCatchBlock] = [ LABEL(fromAsPartOfTryBlock), *catchBlocks[currentCatchBlock], *catchBlock ];
    destroyTmp(id, fuid);
    destroyTmp(asUnwrappedThrown(id), fuid);
}

// TODO: Re-think the way empty 'finally' blocks are translated
INS trMuFinally(MuExp \finally) = (muBlock([]) := \finally) ? [ LOADCON(666), POP() ] : tr(\finally);

void inlineMuFinally() {
	
	finallyBlock = [];

	str finallyLab   = nextLabel();
	str finally_from = mkFinallyFrom(finallyLab);
	str finally_to   = mkFinallyTo(finallyLab);
	
	// Stack of 'finally' blocks to be inlined
	list[MuExp] finallyStack = [ entry.\finally | EEntry entry <- finallyBlocks ];
	
	// Make a space (hole) in the current (potentially nested) 'try' blocks to inline a 'finally' block
	if(isEmpty([ \finally | \finally <- finallyStack, !(muBlock([]) := \finally) ])) {
		return;
	}
	tryBlocks = [ <[ *head, <from,finally_from>, <finally_to + "_<size(finallyBlocks) - 1>",to>], 
				   tryBlock.\type, tryBlock.\catch, tryBlock.\finally> | EEntry tryBlock <- tryBlocks, 
				   														 [ *tuple[str,str] head, <from,to> ] := tryBlock.ranges ];
	
	oldTryBlocks = tryBlocks;
	oldCatchAsPartOfTryBlocks = catchAsPartOfTryBlocks;
	oldFinallyBlocks = finallyBlocks;
	oldCurrentCatchBlock = currentCatchBlock;
	oldCatchBlocks = catchBlocks;
	
	// Translate 'finally' blocks as 'try' blocks: mark them with labels
	tryBlocks = [];	
	for(int i <- [0..size(finallyStack)]) {
		// The last 'finally' does not have an outer 'try' block
		if(i < size(finallyStack) - 1) {
			EEntry outerTry = finallyBlocks[i + 1];
			tryBlocks = tryBlocks + [ <[<finally_from, finally_to + "_<i>">], outerTry.\type, outerTry.\catch, outerTry.\finally> ];
		}
	}
	finallyBlocks = tryBlocks;
	catchAsPartOfTryBlocks = [];
	currentCatchBlock = size(catchBlocks);
	catchBlocks = catchBlocks + [[]];
	
	finallyBlock = [ LABEL(finally_from) ];
	for(int i <- [0..size(finallyStack)]) {
		finallyBlock = [ *finallyBlock, *trMuFinally(finallyStack[i]), LABEL(finally_to + "_<i>") ];
		if(i < size(finallyStack) - 1) {
			EEntry currentTry = topTry();
			// Fill in the 'catch' block entry into the current exception table
			exceptionTable += <currentTry.ranges, filterExceptionType(currentTry.\type), currentTry.\catch, currentTry.\finally>;
			leaveTry();
			leaveFinally();
		}
	}
	
	tryBlocks = oldTryBlocks;
	catchAsPartOfTryBlocks = oldCatchAsPartOfTryBlocks;
	finallyBlocks = oldFinallyBlocks;
	if(isEmpty(catchBlocks[currentCatchBlock])) {
		catchBlocks = oldCatchBlocks;
	}
	currentCatchBlock = oldCurrentCatchBlock;
	
}

// Control flow

// If

INS tr(muIfelse(str label, MuExp cond, list[MuExp] thenPart, list[MuExp] elsePart), Dest d) {
    if(label == "") {
    	label = nextLabel();
    };
    elseLab = mkElse(label);
    continueLab = mkContinue(label);
    coro = needsCoRo(cond) ? createTmpCoRo(functionScope) : -1;
    res = [ *tr_cond(cond, coro, nextLabel(), mkFail(label), elseLab), 
            *(isEmpty(thenPart) ? plug(con(111), d) : trblock(thenPart, d)),
            JMP(continueLab), 
            LABEL(elseLab),
            *(isEmpty(elsePart) ? plug(con(222), d) : trblock(elsePart, d)),
            LABEL(continueLab)
          ];
    if(coro > 0){
       destroyTmpCoRo(functionScope);
    }
    return res;
}

// While

INS tr(muWhile(str label, MuExp cond, list[MuExp] body), Dest d) {
    if(label == ""){
    	label = nextLabel();
    }
    continueLab = mkContinue(label);
    failLab = mkFail(label);
    breakLab = mkBreak(label);
    coro = needsCoRo(cond) ? createTmpCoRo(functionScope) : -1;
    res = [ *tr_cond(cond, coro, continueLab, failLab, breakLab), 	 					
    		*trvoidblock(body),			
    		JMP(continueLab),
    		LABEL(breakLab)		
    	  ];
    if(coro > 0){
       destroyTmpCoRo(functionScope);
    }
    return res;
}

INS tr(muBreak(str label), Dest d) = [ JMP(mkBreak(label)) ];
INS tr(muContinue(str label), Dest d) = [ JMP(mkContinue(label)) ];
INS tr(muFail(str label), Dest d) = [ JMP(mkFail(label)) ];


INS tr(muTypeSwitch(MuExp exp, list[MuTypeCase] cases, MuExp defaultExp), Dest d){
   defaultLab = nextLabel();
   continueLab = mkContinue(defaultLab);
   labels = [defaultLab | i <- index(toplevelTypes) ];
   caseCode =  [];
	for(cs <- cases){
		caseLab = defaultLab + "_" + cs.name;
		labels[getToplevelType(cs.name)] = caseLab;
		caseCode += [ LABEL(caseLab), *tr(cs.exp, stack()), JMP(continueLab) ];
	 };
   caseCode += [LABEL(defaultLab), *tr(defaultExp, stack()), JMP(continueLab) ];
   return [ *tr(exp, stack()), TYPESWITCH(labels), *caseCode, LABEL(continueLab) ];
}
	
INS tr(muSwitch(MuExp exp, bool useConcreteFingerprint, list[MuCase] cases, MuExp defaultExp, MuExp result), Dest d){
   defaultLab = nextLabel();
   continueLab = mkContinue(defaultLab);
   labels = ();
   caseCode =  [];
   for(cs <- cases){
		caseLab = defaultLab + "_<cs.fingerprint>";
		labels[cs.fingerprint] = caseLab;
		caseCode += [ LABEL(caseLab), POP(), *tr(cs.exp, stack()), JMP(defaultLab) ];
   }
   INS defaultCode = tr(defaultExp, stack());
   if(defaultCode == []){
   		defaultCode = [PUSHCON(666)];
   }
   if(size(cases) > 0){ 
   		caseCode += [LABEL(defaultLab), POPACCU(), JMPTRUE(continueLab), *defaultCode, POP() ];
   		return [ PUSHCON(false), *tr(exp, stack()), SWITCH(labels, defaultLab, useConcreteFingerprint), *caseCode, LABEL(continueLab), *tr(result, d) ];
   	} else {
   		return [ *tr(exp, stack()), POP(), *defaultCode, POP(), *tr(result, d) ];
   	}	
}

// Multi/One/All/Or outside conditional context
    
INS tr(e:muMulti(MuExp exp), Dest d) =
     [ *tr(exp, stack()),
       CREATEDYN(0),
       NEXT0(),
       *plug(stack(), d)
     ];

INS tr(e:muOne1(MuExp exp), Dest d) =
    [ *tr(exp, stack()),
       CREATEDYN(0),
       NEXT0(),
      *plug(stack(), d)
     ];

// The above list of muExps is exhaustive, no other cases exist

default INS tr(MuExp e, Dest d) { throw "mu2rvm: Unknown node in the muRascal AST: <e>"; }

/*********************************************************************/
/*      End of muRascal expressions                                  */
/*********************************************************************/


/*********************************************************************/
/*      Translate conditions                                         */
/*********************************************************************/

/*
 * The contract of tr_cond is as follows:
 * - continueLab: continue searching for more solutions for this condition
 *   (is created by the caller, but inserted in the code generated by tr_cond)
 * - failLab: continue searching for more solutions for this condition (multi expressions) or jump to falseLab when no more solutions exist (backtrack-free expressions).
 * - falseLab: location to jump to when no more solutions exist.
 *   (is created by the caller and only jumped to by code generated by tr_cond.)
 *
 * The generated code falls through to subsequent instructions when the condition is true, and jumps to falseLab otherwise.
 */

// muOne: explore one successful evaluation

INS tr_cond(muOne1(MuExp exp), int coro, str continueLab, str failLab, str falseLab) =
      [ LABEL(continueLab), LABEL(failLab) ]
    + [ *tr(exp, stack()), 
        CREATEDYN(0), 
        NEXT0(), 
        *plug(stack(), accu()),
        JMPFALSE(falseLab)
      ];

// muMulti: explore all successful evaluations

INS tr_cond(muMulti(MuExp exp), int coro, str continueLab, str failLab, str falseLab) {
    res =  [ *tr(exp, stack()),
             CREATEDYN(0),
             POPACCU(),
             STORELOC(coro),
            // POP(),
             *[ LABEL(continueLab), LABEL(failLab) ],
             LOADLOC(coro),
             PUSHACCU(),
             NEXT0(),
             *plug(stack(), accu()),
             JMPFALSE(falseLab)
           ];
     return res;
}

default INS tr_cond(MuExp exp, int coro, str continueLab, str failLab, str falseLab) 
	= [ JMP(continueLab), LABEL(failLab), JMP(falseLab), LABEL(continueLab), *tr(exp, accu()), JMPFALSE(falseLab) ];
    

bool needsCoRo(muMulti(MuExp exp)) = true;
default bool needsCoRo(MuExp _) = false;