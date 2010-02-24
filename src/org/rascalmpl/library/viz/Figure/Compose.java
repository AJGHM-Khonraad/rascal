package org.rascalmpl.library.viz.Figure;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IValue;
import org.rascalmpl.interpreter.IEvaluatorContext;

/**
 * Abstract class for the composition of a list of visual elements.
 * 
 * @author paulk
 *
 */
public abstract class Compose extends Figure {

	protected Figure[] figures;
	private static boolean debug = false;

	Compose(FigurePApplet vlp,PropertyManager inheritedProps, IList props, IList elems, IEvaluatorContext ctx) {
		super(vlp, inheritedProps, props, ctx);	
		int n = elems.length();
		figures = new Figure[n];
		for(int i = 0; i < n; i++){
			IValue v = elems.get(i);
			IConstructor c = (IConstructor) v;
			if(debug)System.err.println("Compose, elem = " + c.getName());
			figures[i] = FigureFactory.make(vlp, c, properties, ctx);
		}
	}
	
	@Override
	public boolean mouseOver(int mousex, int mousey){
		for(Figure ve : figures)
			if(ve.mouseOver(mousex, mousey))
				return true;
		return false;
	}
}
