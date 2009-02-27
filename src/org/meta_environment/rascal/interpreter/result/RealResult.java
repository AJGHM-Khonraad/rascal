package org.meta_environment.rascal.interpreter.result;

import org.eclipse.imp.pdb.facts.IDouble;
import org.meta_environment.ValueFactoryFactory;

public class RealResult extends ValueResult {

	private IDouble real;
	
	public RealResult(IDouble real) {
		this.real = real;
	}
	
	@Override
	public IDouble getValue() {
		return real;
	}
	
	@Override
	public AbstractResult add(AbstractResult result) {
		return result.addReal(this);
	}
	
	@Override
	public AbstractResult multiply(AbstractResult result) {
		return result.multiplyReal(this);
	}
	
	@Override
	public AbstractResult divide(AbstractResult result) {
		return result.divideReal(this);
	}
	
	@Override
	public AbstractResult subtract(AbstractResult result) {
		return result.subtractReal(this);
	}
	
	@Override
	public AbstractResult modulo(AbstractResult result) {
		return result.moduloReal(this);
	}
	
	
	/// real impls start here
	
	@Override
	public RealResult negative() {
		return new RealResult(ValueFactoryFactory.getValueFactory().dubble(- getValue().getValue()));
	}
	
	
	@Override
	protected RealResult addInteger(IntegerResult n) {
		return addReal(n.widenToReal());
	}
	
	@Override
	protected RealResult subtractInteger(IntegerResult n) {
		return subtractReal(n.widenToReal());
	}
	
	@Override
	protected RealResult multiplyInteger(IntegerResult n) {
		return multiplyReal(n.widenToReal());
	}
	
	@Override
	protected RealResult divideInteger(IntegerResult n) {
		return divideReal(n.widenToReal());
	}
	
	@Override  
	protected RealResult addReal(RealResult n) {
		return new RealResult(getValue().add(n.getValue()));
	}
	
	@Override 
	protected RealResult subtractReal(RealResult n) {
		// note the reverse subtraction.
		return new RealResult(n.getValue().subtract(getValue()));
	}
	
	@Override
	protected RealResult multiplyReal(RealResult n) {
		return new RealResult(getValue().multiply(n.getValue()));
	}

	@Override
	protected RealResult divideReal(RealResult n) {
		// note the reverse division
		return new RealResult(n.getValue().divide(getValue()));
	}
	
	
}
