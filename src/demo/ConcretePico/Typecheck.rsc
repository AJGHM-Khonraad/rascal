module demo::ConcretePico::Typecheck

//import languages::pico::syntax::Pico;
import languages::pico::syntax::Identifiers;
import languages::pico::syntax::Types;
import basic::NatCon;
import basic::StrCon;

import demo::AbstractPico::Message;
import IO;
import UnitTest;

/*
 * Typechecker for Pico.
 */
 
alias PICO_ID = \PICO-ID;

PICO_ID P = [|x|];

/***********************************

alias Env = map[PICO_ID, TYPE];

public list[Message] tcp(PROGRAM P) {
    if([| begin declare <{\ID-TYPE "," }* Decls>; <{STATEMENT ";"}* Stats> end |] := P){
           Env Env = (Id : Type | [| <\PICO-ID Id> : <TYPE Type> |] <- Decls);
         return tcs(Stats, Env);
 /  }
    return [message("Malformed Pico program")];
}



public list[Message] tcs(list[STATEMENT] Stats, Env Env){
    list[Message] messages = [];
    for(STATEMENT S <- Stats){
    	messages = [messages, tcst(S, Env)];
    }
    return messages;
}

public list[Message] tcst(STATEMENT Stat, Env Env) {
    switch (Stat) {
      case [| <\PICO-ID Id> : <EXP Exp> |]:
        return requireType(Exp, Env[Id], Env);  // TODO: undefined variable

      case [| if <EXP Exp> then <{STATEMENT ";"}* Stats1> 
                           else <{STATEMENT ";"}* Stats1>
              fi |]:
        return requireType(Exp, natural, Env) + tcs(Stats1, Env) + tcs(Stats2, Env);

      case [| while <EXP Exp> do <{STATEMENT ";"}* Stats1> od |]:
        return requireType(Exp, natural, Env) + tcs(Stats, Env);
    }
    return [message("Unknown statement: <Stat>")];
}
 
public list[Message] requireType(EXP E, TYPE Type, Env Env) {
    switch (E) {
      case NatCon N: if(Type == natural){ return []; } else fail;

      case StrCon S: if(Type == string) { return []; } else fail;

      case \PICO-ID Id: {
         TYPE Type2 = Env[Id];
         if(Type2 == Type) { return []; } else fail;
      }

      case [| <EXP E1> + <EXP E2> |]:
        if(Type == natural){
           return requireType(E1, natural, Env) + 
                  requireType(E2, natural, Env);
        } else fail;

      case [| <EXP E1> - <EXP E2> |]:
        if(Type == natural){
           return requireType(E1, natural, Env) + 
                  requireType(E2, natural, Env);
        } else fail;

      case [| <EXP E1> || <EXP E2> |]: 
        if(Type == string){
          return requireType(E1, string, Env) + 
                 requireType(E2, string, Env);
        } else fail;
      
      }
    
      return [message("Type error: expected <Type> got <E>")];
}

*************************/

public bool test(){
    println("P = <P>");
	return true;
}

