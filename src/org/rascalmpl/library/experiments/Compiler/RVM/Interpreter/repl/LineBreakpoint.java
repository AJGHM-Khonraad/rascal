package org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.repl;

import java.io.PrintWriter;

import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.Frame;

public class LineBreakpoint extends BreakPoint {
	private final String path;
	private final int lino;
	
	LineBreakpoint(int id, String path, int lino){
		super(id);
		this.path = path;
		this.lino = lino;
	}
	
	@Override
	void println(PrintWriter stdout){
		stdout.println(id + "\t" + isEnabled() + "\tLine\t" + path + ":" + lino);
	}
	
	@Override
	public boolean matchOnObserve(Frame frame) {
		return shouldBreakAt(path, lino, frame.src);
	}

	@Override
	public boolean matchOnEnter(Frame frame) {
		return shouldBreakAt(path, lino, frame.src);
	}

	@Override
	public boolean matchOnLeave(Frame frame) {
		return shouldBreakAt(path, lino, frame.src);
	}
}
