package org.meta_environment.rascal.interpreter.control_exceptions;

import java.net.URI;

import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.IValue;
import org.meta_environment.rascal.ast.AbstractAST;

/**
 * This class is for representing all run-time exceptions in Rascal.
 * These exceptions can be caught by Rascal code. 
 * These exceptions can be thrown by either the Rascal interpreter - 
 * in case of a run-time error, or by Rascal code - using the throw statement,
 * or by Java "built-in" methods, or by Java "embedded" methods.
 * <br>
 * Note that this class is <code>final</code> to emphasize the fact that
 * different kinds of Rascal exceptions need to be modeled by different kind
 * of exception values, not different kind of Java classes.
 */
public final class Throw extends ControlException {
	private static final long serialVersionUID = -7290501865940548332L;
	private final IValue exception;
	private volatile ISourceLocation loc;
	private volatile String trace;
	
	// It is *not* the idea that these exceptions get references to AbstractAST's!
	public Throw(IValue value, ISourceLocation loc, String trace) {
		super(value.toString());
		this.exception = value;
		this.loc = loc;
		this.trace = trace;
	}
	
	public Throw(IValue value, AbstractAST ast, String trace) {
		this(value, ast != null ? ast.getLocation() : null, trace);
	}
	
	public String getTrace() {
		return trace;
	}
	
	public void setTrace(String trace) {
		this.trace = trace;
	}
	
	public IValue getException() {
		return exception;
	}
	
	public ISourceLocation getLocation() {
		return loc;
	}
	
	public void setLocation(ISourceLocation loc) {
		this.loc = loc;
	}

	@Override
	public String getMessage() {
		if (loc != null) {
			URI url = loc.getURI();
			
			return (url.getScheme().equals("file") ? (url.getAuthority() + url.getPath()) : url) 
					+ ":" + loc.getBeginLine() 
					+ "," + loc.getBeginColumn() 
					+ ": " + super.getMessage();
		}
		
		// TODO remove once all errors have locations
		return super.getMessage();
	}
	
}
