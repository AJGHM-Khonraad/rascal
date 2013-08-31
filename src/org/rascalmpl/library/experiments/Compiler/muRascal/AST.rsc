module experiments::Compiler::muRascal::AST

import Prelude;

/*
 * Abstract syntax for muRascal.
 * 
 * Position in the compiler pipeline: Rascal -> muRascal -> RVM
 */

// All information related to one Rascal module

public data MuModule =											
              muModule(str name, list[Symbol] types, 
                                 list[MuFunction] functions, 
                                 list[MuVariable] variables, 
                                 list[MuExp] initialization)
            ;
          
// All information related to a function declaration. This can be a top-level
// function, or a nested or anomyous function inside a top level function. 
         
public data MuFunction =					
              muFunction(str name, int scope, int nformal, int nlocal, loc source, list[str] modifiers, map[str,str] tags, list[MuExp] body)
          ;
          
// A global (module level) variable.
          
public data MuVariable =
            muVariable(str name)
          ;
          
// A declared Rascal type
          
//public data MuType =
//            muType(list[Symbol] symbols)  
//          ;

// All executable Rascal code is tranlated to the following muExps.
          
public data MuExp = 
			// Elementary expressions
			muBool(bool b)										// muRascal Boolean constant
		  | muInt(int n)										// muRascal integer constant
          | muCon(value c)										// Rascal Constant: an arbitrary IValue
            													// Some special cases are handled by preprocessor, see below.
          | muLab(str name)										// Label
          | muFun(str name)										// Function constant: functions at the root
          | muFun(str name, int scope)                          // Function constant: nested functions and closures
          | muConstr(str name) 									// Constructors
          
          	// Variables
          | muLoc(str name, int pos)							// Local variable, with position in current scope
          | muVar(str id, int scope, int pos)					// Variable: retrieve its value
          | muTmp(str name)										// Temporary variable introduced by front-end
          
          | muLocDeref(str name, int pos) 				        // Call-by-reference: a variable that refers to a value location
          | muVarDeref(str name, int scope, int pos)
          
          | muLocRef(str name, int pos) 				        // Call-by-reference: expression that returns a value location
          | muVarRef(str name, int scope, int pos)
             
          | muTypeCon(Symbol tp)								// Type constant
          
          // Function overloading
          | muFunAddition(MuExp lhs, MuExp rhs)
          | muCall(list[MuExp] funs, list[MuExp] args)
     
     		// Call/return
     		
          | muCall(MuExp fun, list[MuExp] args)					// Call a function
          | muCallConstr(str cname, list[MuExp] args) 			// Call a constructor
          | muCallPrim(str name, list[MuExp] exps)				// Call a Rascal primitive function
          | muCallMuPrim(str name, list[MuExp] exps)			// Call a muRascal primitive function
 
          | muReturn()											// Return from function without value
          | muReturn(MuExp exp)									// Return from function with value
              
           // Assignment, If and While
              
          | muAssignLoc(str id, int pos, MuExp exp)				// Assign a value to a local variable
          | muAssign(str id, int scope, int pos, MuExp exp)		// Assign a value to a variable
          | muAssignTmp(str id, MuExp exp)						// Assign to temporary variable introduced by front-end
          
          | muAssignLocDeref(str id, int pos, MuExp exp)          // Call-by-reference assignment:
          | muAssignVarDeref(str id, int scope, int pos, MuExp exp) 	// the left-hand side is a variable that refers to a value location
          														
          | muIfelse(MuExp cond, list[MuExp] thenPart,			// If-then-else expression
          						 list[MuExp] elsePart)
          						 
          | muWhile(str label, MuExp cond, list[MuExp] body)	// While-Do expression
          | muDo(str label, list[MuExp] body, MuExp cond)		// Do-While expression
          
		  | muBreak(str label)									// Break statement
		  | muContinue(str label)								// Continue statement
		  | muFail(str label)									// Fail statement
		  | muFailReturn()										// Failure from function body
          
            // Coroutines
            
          | muCreate(MuExp fun)									// Create a coroutine
          | muCreate(MuExp fun, list[MuExp] args)
          
          | muInit(MuExp coro)									// Initialize a coroutine, no arguments
          | muInit(MuExp coro, list[MuExp] args)				// Initialize a coroutine, with arguments
          
          | muHasNext(MuExp exp)								// HasNext on a coroutine
          
          | muNext(MuExp exp)									// Next on coroutine, no arguments
          | muNext(MuExp exp1, list[MuExp] args)				// Next on coroutine, with arguments
          
          | muYield()											// Yield from coroutine, without value
          | muYield(MuExp exp)									// Yield from coroutine, with value
          
           // Multi-expressions
          
          | muExpList(list[MuExp] exps)  						// A list of expressions
          | muMulti(MuExp exp)		 							// Expression that can produce multiple values
          | muOne(list[MuExp] exps)								// Compute one result for a list of boolean expressions
          | muAll(list[MuExp] exps)								// Compute all results for a list of boolean expressions
       	  ;
       	  
       	  
// Auxiliary constructors that are removed by the preprocessor: parse tree -> AST.
// They will never be seen by later stages of the compiler.

public data Module =
            preMod(str name, list[Function] functions)
          ;

public data Function =				
             preFunction(str name, int scopeId, int nformal, 
                         list[str] locals, list[MuExp] body)
          ;

public data MuExp =
              preIntCon(str txt)
            | preStrCon(str txt)  
            | preTypeCon(str txt)
            | preVar(str name)
            | preList(list[MuExp] exps)
            | preSubscript(MuExp lst, MuExp idx)
            | preAssignLoc(str name, MuExp exp)
            | preAssignLocList(str name1, str name2, MuExp exp)
            | preAssignSubscript(MuExp lst, MuExp idx, MuExp exp)
            | preIfthen(MuExp cond, list[MuExp] thenPart)
            
            | preAddition(MuExp lhs, MuExp rhs)
            | preSubtraction(MuExp lhs, MuExp rhs)
                 
            | preLess(MuExp lhs, MuExp rhs)
            | preLessEqual(MuExp lhs, MuExp rhs)
            | preEqual(MuExp lhs, MuExp rhs)
            | preNotEqual(MuExp lhs, MuExp rhs)
            | preGreater(MuExp lhs, MuExp rhs)
            | preGreaterEqual(MuExp lhs, MuExp rhs)
            | preAnd(MuExp lhs, MuExp rhs)
       
            | preIs(MuExp, str typeName)
            
            | preLocDeref(str name)
            | preLocRef(str name)
            
            | preAssignLocDeref(str name, MuExp exp)
           ;