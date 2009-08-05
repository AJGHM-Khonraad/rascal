package org.meta_environment.rascal.interpreter.control_exceptions;

import java.net.URL;

import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.meta_environment.rascal.ast.Test;

public class FailedTestError extends ControlException {
	private static final long serialVersionUID = 8282771874859604692L;
	private String trace = null;
	private ISourceLocation loc;

	public FailedTestError(Test.Labeled t) {
		super("test " + t.getLabeled().toString() + " failed.");
		this.loc = t.getLocation();
	}
	
	public FailedTestError(Test.Unlabeled t) {
		super("test failed");
		this.loc = t.getLocation();
	}

	public FailedTestError(Test.Labeled t, Throw tr) {
		super("test " + t.getLabeled() + " failed due to unexpected Rascal exception: " + tr.getMessage());
		this.loc = tr.getLocation();
	}
	
	public FailedTestError(Test.Unlabeled t, Throw tr) {
		super("test failed due to unexpected Rascal exception: " + tr.getMessage());
		this.loc = tr.getLocation();
	}
	
	public FailedTestError(Test.Labeled t, Throwable e) {
		super("test " + t.getLabeled() + " failed due to unexpected Java exception: " + e.getMessage(), e);
		this.loc = t.getLocation();
	}
	
	public FailedTestError(Test.Unlabeled t, Throwable e) {
		super("test failed due to unexpected Java exception: " + e.getMessage(), e);
		this.loc = t.getLocation();
	}
	
	@Override
	public String getMessage() {
		URL url = loc.getURL();

		return (url.getProtocol().equals("file") ? (url.getAuthority() + url.getPath()) : url) 
		+ ":" + loc.getBeginLine() 
		+ "," + loc.getBeginColumn() 
		+ ": " + super.getMessage()
		+ ((trace != null) ? trace : "");
	}
}
