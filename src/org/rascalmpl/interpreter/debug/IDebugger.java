package org.rascalmpl.interpreter.debug;

import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.rascalmpl.interpreter.control_exceptions.QuitException;

public interface IDebugger {

	public boolean isTerminated();

	public void notifySuspend(DebugSuspendMode mode) throws QuitException;
	
	public boolean hasEnabledBreakpoint(ISourceLocation sourceLocation);
	
	public boolean isStepping();

	public void stopStepping();

	public void destroy();

}
