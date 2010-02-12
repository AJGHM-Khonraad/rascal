package org.rascalmpl.interpreter.control_exceptions;

public class Failure extends ControlException{
	private static final long serialVersionUID = 2774285953244945424L;
	
	private final String label;
	
	public Failure() {
		super();
		
		label = null;
	}
	
	public Failure(String label) {
		super();
		
		this.label = label;
	}
	
	public boolean hasLabel() {
		return label != null;
	}
	
	public String getLabel() {
		return label;
	}
}
