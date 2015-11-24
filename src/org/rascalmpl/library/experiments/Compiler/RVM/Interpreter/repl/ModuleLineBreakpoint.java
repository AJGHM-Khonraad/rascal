package org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.repl;

import java.io.PrintWriter;

import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.Frame;

public class ModuleLineBreakpoint extends BreakPoint {
	private final String modulePath;
	private final int lino;
	
	ModuleLineBreakpoint(int id, String modulePath, int lino){
		super(id);
		this.modulePath = modulePath;
		this.lino = lino;
	}
	
	@Override
	void println(PrintWriter stdout){
		stdout.println(id + "\t" + isEnabled() + "\tModule\t" + modulePath + ":" + lino);
	}
	
	@Override
	public boolean matchOnObserve(Frame frame) {		
		return frame.src.getPath().equals(modulePath) &&
		       shouldBreakAt(lino, frame.src);
	}

	@Override
	public boolean matchOnEnter(Frame frame) {
		return matchOnObserve(frame);
	}

	@Override
	public boolean matchOnLeave(Frame frame) {
		return matchOnObserve(frame);
	}
}
