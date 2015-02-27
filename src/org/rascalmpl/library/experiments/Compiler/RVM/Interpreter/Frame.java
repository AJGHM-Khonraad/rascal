package org.rascalmpl.library.experiments.Compiler.RVM.Interpreter;

import java.io.PrintWriter;

import org.eclipse.imp.pdb.facts.IListWriter;
import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;
import org.rascalmpl.interpreter.types.FunctionType;  // TODO: remove import: NO
import org.rascalmpl.values.ValueFactoryFactory;

public class Frame {
	int scopeId;
    Frame previousCallFrame;
    final Frame previousScope;
	public final Object[] stack;
	public int sp;
	int pc;
	ISourceLocation src;
	final Function function;
	
	final boolean isCoroutine;
	public int hotEntryPoint;
	public Frame nextFrame;
		
	public Frame(int scopeId, Frame previousCallFrame, int stackSize, Function function){
		this(scopeId, previousCallFrame, previousCallFrame, stackSize, function);
	}
	
	public Frame(int scopeId, Frame previousCallFrame, Frame previousScope, int stackSize, Function function){
		this(scopeId, previousCallFrame, previousScope, function, new Object[stackSize]);
	}
	
	private Frame(int scopeId, Frame previousCallFrame, Frame previousScope, Function function, final Object[] stack) {
		this.scopeId = scopeId;
		this.previousCallFrame = previousCallFrame;
		this.previousScope = previousScope;
		this.stack = stack;
		this.pc = 0;
		this.sp = 0;
		this.src = function.src;
		this.function = function;
		this.isCoroutine = function.isCoroutine;
	}
	
	/**
	 * Assumption: scopeIn != -1; 
	 */
	public Frame getCoroutineFrame(Function f, int scopeIn, int arity, int sp) {
		for(Frame env = this; env != null; env = env.previousCallFrame) {
			if (env.scopeId == f.scopeIn) {
				return getCoroutineFrame(f, env, arity, sp);
			}
		}
		throw new CompilerError("Could not find a matching scope when computing a nested coroutine instance: " + f.scopeIn, this);
	}
	
	/**
	 * Given a current frame (this), 
	 * creates a new frame (frame) to be wrapped inside a coroutine object,
	 * pushes arguments from the current frame's stack to the stack of the new frame (given an arity),
	 * (re)setting the stack pointer of both the current and new frame, and
	 * returns the new frame.
	 */
	public Frame getCoroutineFrame(Function f, Frame previousScope, int arity, int sp) {
		Frame frame = new Frame(f.scopeId, null, previousScope, f.maxstack, f);
		if(arity != f.nformals) {
			throw new CompilerError("Incorrect number of arguments has been passed to create a coroutine instance, expected: " + f.nformals, previousScope);
		}
		for (int i = 0; i < arity; i++) {
			frame.stack[i] = stack[sp - arity + i];
		}
		this.sp = sp - arity;
		frame.sp = f.nlocals;
		return frame;
	}
	
	/**
	 * Accounts for cases of partial parameter binding,
	 * creates a new frame (frame) (similar to the method above) to be wrapped inside a coroutine object,
	 * that gives a coroutine instance to be immediately initialized (i.e., all the coroutine arguments are assumed to be provided)
	 */
	public Frame getCoroutineFrame(FunctionInstance fun_instance, int arity, int sp) {
		Function f = fun_instance.function;
		Object[] args = fun_instance.args;
		Frame frame = new Frame(f.scopeId, this, fun_instance.env, f.maxstack, f);
		assert fun_instance.next + arity == f.nformals;
		if(args != null) {
			for(Object arg : args) {
				if(arg == null) {
					break;
				}
				frame.stack[frame.sp++] = arg;
			}
		}
		for(int i = 0; i < arity; i++) {
			frame.stack[frame.sp++] = stack[sp - arity + i];
		}
		this.sp = sp - arity;
		frame.sp = f.nlocals;
		return frame;
	}
	
	/**
	 * Given a current frame (this), 
	 * creates a new frame (frame), 
	 * pushes arguments from the current frame's stack to the stack of the new frame,
	 * (re)setting the stack pointer of both the current and the new frame, and 
	 * returns the new frame to the caller.
	 */
	public Frame getFrame(Function f, Frame previousScope, int arity, int sp) {
		Frame frame = new Frame(f.scopeId, this, previousScope, f.maxstack, f);
		this.sp = frame.pushFunctionArguments(arity, this.stack, sp);
		return frame;
	}
		
	public Frame getFrame(Function f, Frame previousScope, Object[] args) {
		Frame frame = new Frame(f.scopeId, this, previousScope, f.maxstack, f);
		this.sp = frame.pushFunctionArguments(args.length, args, args.length);
		return frame;
	}
	
	public Frame getFrame(Function f, Frame previousScope, Object[] args, int arity, int sp) {
		Frame frame = new Frame(f.scopeId, this, previousScope, f.maxstack, f);
		if(args != null) {
			for(Object arg : args) {
				if(arg == null) {
					break;
				}
				frame.stack[frame.sp++] = arg;
			}
		}
		if(arity == 0) {
			this.sp = sp;
			frame.sp = frame.function.nlocals;
			return frame;
		}
		this.sp = frame.pushFunctionArguments(arity, this.stack, sp);
		return frame;
	}
	
	/**
	 * Given a current frame (this), 
	 * pushes arguments from a given stack (stack) to the current frame's stack (given an arity and the number of formal parameters of a function),
	 * resetting the current frame's stack pointer to the number of local variables of its function, and
	 * returns a new stack pointer for the given stack
	 * (in case of the given stack being an array of arguments: stack.length == arity == sp && start == 0).
	 */
	private int pushFunctionArguments(int arity, Object[] stack, int sp) {
		int start = sp - arity;
		if(!function.isVarArgs) {
			assert this.sp + arity == function.nformals;
			for(int i = 0; i < arity; i++){
				this.stack[this.sp++] = stack[start + i]; 
			}
		} else {
			int posArityMinusOne = function.nformals - 2; // The number of positional arguments minus one
			for(int i = 0; i < posArityMinusOne; i++) {
				this.stack[this.sp++] = stack[start + i];
			}
			Type argTypes = ((FunctionType) function.ftype).getArgumentTypes();
			if(function.nformals == arity && ((IValue) stack[start + posArityMinusOne]).getType().isSubtypeOf(argTypes.getFieldType(posArityMinusOne))) {
				this.stack[this.sp++] = stack[start + posArityMinusOne];
			} else {
				IListWriter writer = ValueFactoryFactory.getValueFactory().listWriter();
				for(int i = posArityMinusOne; i < arity - 1; i++) {
					writer.append((IValue) stack[start + i]);
				}
				this.stack[this.sp++] = writer.done();
			}
			this.stack[this.sp++] = stack[start + arity - 1]; // The keyword arguments
		}		
		this.sp = function.nlocals;
		return start;
	}
	
	public Frame copy() {
		if(pc != 0)
			throw new CompilerError("Copying the frame with certain instructions having been already executed.");
		Frame newFrame = new Frame(scopeId, previousCallFrame, previousScope, function, stack.clone());
		newFrame.sp = sp; 
		return newFrame;
	}
	
	private int MAXLEN = 40;
	
	private String abbrev(String repr) {
		return (repr.length() < MAXLEN) ? repr : repr.substring(0, 40) + "...";
	}
	
	public String toString(){
		StringBuilder s = new StringBuilder();
		if(src != null){
			s.append(" \uE007[](").append(src);
	    }
		s.append(this.function.getPrintableName()).append("(");
		for(int i = 0; i < function.nformals; i++){
			if(i > 0) s.append(", ");
			String repr;
			if(stack[i] instanceof IValue ) {
					repr = ((IValue) stack[i]).toString();
			} else {
				repr = (stack[i] == null) ? "null" : stack[i].toString();
				int n = repr.lastIndexOf(".");
				if(n >= 0){
					repr = repr.substring(n + 1, repr.length());
				}
			}
			
			s.append(abbrev(repr));
		}
	
//		if(function.nformals-1 > 0 && stack[function.nformals-1] instanceof HashMap<?, ?>){
//			@SuppressWarnings("unchecked")
//			HashMap<String, IValue> m = (HashMap<String, IValue>) stack[function.nformals-1];
//			if(m.size() > 0){
//				for(String key : m.keySet()){
//					s.append(", ").append(key).append("=").append(m.get(key));
//				}
//			}
//		}
		
		s.append(")");
		return s.toString();
	}
	
	private StringBuilder indent(){
		int n = 0;
		Frame prev = previousCallFrame;
		while(prev != null){
			n++;
			prev = prev.previousCallFrame;
		}
		StringBuilder b = new StringBuilder(2 * n + 10);
		for (int i = 0; i < n; i += 1) {
		    b.append("  ");
		}
		return b;
	}
	
	public void printEnter(PrintWriter stdout){
		stdout.println(indent().append(this.toString())); stdout.flush();
		//stdout.println(indent().append("--> ").append(function.getPrintableName()).append(":").append(src)); stdout.flush();
	}
	
	public void printBack(PrintWriter stdout){
		stdout.println(indent().append(this.toString())); stdout.flush();
		//stdout.println(indent().append("--- ").append(function.getPrintableName()).append(":").append(src)); stdout.flush();
	}
	
	public void printLeave(PrintWriter stdout){
		stdout.println(indent().append(this.toString())); stdout.flush();
		//stdout.println(indent().append("<-- ").append(function.getPrintableName()).append(":").append(src)); stdout.flush();
	}
	
}
