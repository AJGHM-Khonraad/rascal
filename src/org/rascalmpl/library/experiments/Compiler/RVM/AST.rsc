module experiments::Compiler::RVM::AST

import Type;

public data Declaration = 
		  FUNCTION(str qname, Symbol ftype, str scopeIn, int nformals, int nlocals, int maxStack, list[Instruction] instructions)
		;

public data RVMProgram = rvm(list[Symbol] types, map[str, Declaration] declarations, list[Instruction] instructions, map[str,int] resolver, lrel[str,set[str]] overloaded_functions);

data Instruction =
          LOADBOOL(bool bval)						// Push a (Java) boolean
        | LOADINT(int nval)  						// Push a (Java) integer
	   	| LOADCON(value val)						// Push an IValue
	   	| LOADTYPE(Symbol \type)					// Push a type constant
	   	
	   	| LOADFUN(str fuid)                         // Push a named *muRascal function
		| LOAD_NESTED_FUN(str fuid, str scopeIn)    // Push a named nested *muRascal function of a named inner *muRascal function
		| LOADCONSTR(str fuid)						// Push a constructor function
		
		| LOADOFUN(str fuid)                        // Push a named *Rascal function
		//| LOAD_NESTED_OFUN(str fuid, str scopeIn)   // Push a named, nested *Rascal function
		
		| LOADLOC(int pos)							// Push value of local variable
		| STORELOC(int pos)							// Store value on top-of-stack in local variable (value remains on stack)
	   	
		| LOADVAR(str fuid, int pos)                // Push a variable from an outer scope
		| STOREVAR(str fuid, int pos)               // Store value on  top-of-stack in variable in outer scope (value remains on stack)
		
		| LOADLOCREF(int pos)						// Push a reference to a local variable
		| LOADLOCDEREF(int pos)						// Push value of a local variable identified by reference on stack 
		| STORELOCDEREF(int pos)					// Store value at stack[sp - 2] in local variable identified by reference at stack[sp -1] (value remains on stack)
			
		| LOADVARREF(str fuid, int pos)			    // Push a reference to a variable in anouter scope
		| LOADVARDEREF(str fuid, int pos)           // Push value of a variable in outer scope identified by reference on stack 
		| STOREVARDEREF(str fuid, int pos)          // Store value at stack[sp - 2] in outer variable identified by reference at stack[sp -1] (value remains on stack)
		
		| CALL(str fuid, int arity)					// Call a named *muRascal function
		| CALLDYN(int arity)						// Call a *muRascal function on stack
		| CALLCONSTR(str fuid, int arity)			// Call a constructor
		
		| OCALL(str fuid, int arity)				// Call a named *Rascal function
		| OCALLDYN(int arity)						// Call a *Rascal function on stack
		
		| CALLMUPRIM(str name, int arity)			// Call a muRascal primitive (see Compiler.RVM.Interpreter.MuPrimitive)
		| CALLPRIM(str name, int arity)				// Call a Rascal primitive (see Compiler.RVM.Interpreter.RascalPrimitive)
		
		| RETURN0()									// Return from function without value
		| RETURN1()									// Return from function with value
		| FAILRETURN()								// Failure return from function
		
		| LABEL(str label)							// Define a label (is associated with next instruction)
		| JMP(str label)							// Jump to a labelled instruction
		| JMPTRUE(str label)						// Jump to labelled instruction when top-of-stack is true (stack is popped)
		| JMPFALSE(str label)						// Jump to labelled instruction when top-of-stack is false (stack is popped)
													// TODO: JMPTRUE and JMPFALSE currently act on Java booleans and Rascal booleans; this has to be split
		
		| CREATE(str fuid, int arity)				// Create a co-routine from a named function
		| CREATEDYN(int arity)						// Create a co-routine from a function on the stack
		| INIT(int arity)							// Initialize co-routine on top-of-stack.
		| HASNEXT()									// HasNext operation on co-routine on top-of-stack
		| NEXT0()									// Next operation (without argument) on co-routine on top-of-stack
		| NEXT1()									// Next operation (with argument) on co-routine on top-of-stack
		| YIELD0()									// Yield from co-routine without value
		| YIELD1()									// Yield from co-routine with value
		
		| PRINTLN(int arity)						// Print arity values on the stack (TODO: may disappear)
		
		| POP()										// Pop one value from the stack
		| DUP()										// Duplicate the top-of-stack
		
		| HALT()									// Halt execution of the RVM program
;
	
