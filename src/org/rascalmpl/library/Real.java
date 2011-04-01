package org.rascalmpl.library;

import java.util.Random;

import org.eclipse.imp.pdb.facts.IReal;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;

public class Real {
	private final IValueFactory values;
	private final Random random;
	private final double pi, e;
	
	public Real(IValueFactory values){
		super();
		
		this.values = values;
		random = new Random();
		pi = Math.PI;
		e = Math.E;
	}

	public IValue arbReal()
	//@doc{arbReal -- returns an arbitrary real value in the interval [0.0,1.0).}
	{
	  return values.real(random.nextDouble());
	}

	public IValue toInt(IReal d)
	//@doc{toInteger -- convert a real to integer.}
	{
	  return d.toInteger();
	}

	public IValue toString(IReal d)
	//@doc{toString -- convert a real to a string.}
	{
	  return values.string(d.toString());
	}
	
	public IValue round(IReal d) {
		return d.round();
	}
	
	public IValue PI()
	//@doc{pi -- returns the constant PI}
	{
	  return values.real(pi);
	}
	
	public IValue E()
	//@doc{e -- returns the constant E}
	{
	  return values.real(e);
	}
	
	public IValue pow(IReal x, IReal y){
	  return values.real(Math.pow(x.doubleValue(), y.doubleValue()));
	}
	
	public IValue exp(IReal x){
		  return values.real(Math.exp(x.doubleValue()));
		}
	
	public IValue sin(IReal x){
		  return values.real(Math.sin(x.doubleValue()));
		}
	
	public IValue cos(IReal x){
		  return values.real(Math.cos(x.doubleValue()));
		}
	
	public IValue tan(IReal x){
		  return values.real(Math.tan(x.doubleValue()));
		}
	
	public IValue sqrt(IReal x){
		  return values.real(Math.sqrt(x.doubleValue()));
		}
}
