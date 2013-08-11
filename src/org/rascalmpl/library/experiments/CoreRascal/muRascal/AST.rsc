module experiments::CoreRascal::muRascal::AST

import Prelude;

public data MuModule =
            muModule(str name, list[MuFunction] functions, list[MuVariable] variables, list[MuExp] initialization);
          
public data MuFunction =
            muFunction(str name, int scope, int nformal, int nlocal, list[MuExp] body)
          ;
public data MuVariable =
            muVariable(str name)
          ;
          
public data MuType =
            muType(list[Symbol] symbols)  
          ;
          
public data MuExp = 
            muEmpty()
          | muCon(value c)
          | muLab(str name)
          | muFun(str name)
          | muVar(str id, int scope, int pos)
          | muTypeCon(Symbol tp)
          
          | muCall(MuExp fun, list[MuExp] args)
          | muCall(str fname, list[MuExp] args)
          | muCallPrim(str name, MuExp exp1)
          | muReturn()
          | muReturn(MuExp exp)
          | muCallPrim(str name, MuExp exp1, MuExp exp2)
                    
          | muAssign(str id, int scope, int pos, MuExp exp)
          | muIfelse(MuExp cond, list[MuExp] thenPart, list[MuExp] elsePart)
          | muWhile(MuExp cond, list[MuExp] body)
          | muLabeled(str name, list[MuExp] MuExp)
          
          | muCreate(str fname)
          | muCreate(MuExp exp)
          | muInit(MuExp exp)
          | muInit(MuExp exp1, MuExp exp2)
          | muHasNext(MuExp exp)
          | muNext(MuExp exp)
          | muNext(MuExp exp1, MuExp exp2)
          | muYield()
          | muYield(MuExp exp)
       	  ;
