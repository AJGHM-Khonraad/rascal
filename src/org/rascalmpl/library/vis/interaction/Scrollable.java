package org.rascalmpl.library.vis.interaction;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.rascalmpl.interpreter.IEvaluatorContext;
import org.rascalmpl.library.vis.properties.PropertyManager;
import org.rascalmpl.library.vis.swt.FigureSWTApplet;
import org.rascalmpl.library.vis.swt.IFigureConstructionEnv;

public class Scrollable extends SWTWidgetFigure<FigureSWTApplet> {

	IConstructor inner;
	IFigureConstructionEnv env;
	
	public Scrollable(IFigureConstructionEnv env, IConstructor inner, PropertyManager properties) {
		super(env,  properties);
		this.inner = inner;
		this.env = env;
	}
	@Override
	FigureSWTApplet makeWidget(Composite comp) {
		return new FigureSWTApplet(comp, inner,env.getFigureExecEnv());
	}

}
