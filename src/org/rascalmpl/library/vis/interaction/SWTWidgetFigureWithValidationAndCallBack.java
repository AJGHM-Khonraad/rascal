package org.rascalmpl.library.vis.interaction;

import org.eclipse.imp.pdb.facts.IBool;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.swt.widgets.Control;
import org.rascalmpl.interpreter.result.Result;
import org.rascalmpl.library.vis.properties.PropertyManager;
import org.rascalmpl.library.vis.swt.IFigureConstructionEnv;

public abstract class SWTWidgetFigureWithValidationAndCallBack<WidgetType extends Control> extends SWTWidgetFigureWithSingleCallBack<WidgetType> {

	IValue validate;
	boolean validated;
	
	SWTWidgetFigureWithValidationAndCallBack(IFigureConstructionEnv env, IValue callback, IValue validate, PropertyManager properties) {
		super(env, callback, properties);
		if (validate != null) {
			cbenv.checkIfIsCallBack(validate);
		}
		this.validate = validate;
		validated = true;
		doValidate();
	}
	
	public void doValidate(){
		if (validate != null) {
			validated = ((IBool)executeValidate()).getValue();
		}
	}
	
	public void doCallback(){
		if(validated){
			executeCallback();
		}
	}
	
	abstract Result<IValue>  executeValidate();

}
