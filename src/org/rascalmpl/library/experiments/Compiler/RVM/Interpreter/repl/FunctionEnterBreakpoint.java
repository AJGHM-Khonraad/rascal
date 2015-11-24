package org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.repl;

import java.io.PrintWriter;

import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.Frame;

public class FunctionEnterBreakpoint extends BreakPoint {
	private final String functionName;
	
	FunctionEnterBreakpoint(int id, String functionName){
		super(id);
		
		this.functionName = functionName;
	}
	
	@Override void reset(){
	}
	
	@Override
	void println(PrintWriter stdout){
		stdout.println(id + "\t" + isEnabled() + "\tEnter\t" + functionName);
	}
	
	@Override
	public boolean matchOnObserve(Frame frame) {
		return false;
	}

	@Override
	public boolean matchOnEnter(Frame frame) {
		return frame.function.getPrintableName().equals(functionName);
	}

	@Override
	public boolean matchOnLeave(Frame frame) {
		return false;
	}
}
