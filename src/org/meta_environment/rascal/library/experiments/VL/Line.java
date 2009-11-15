package org.meta_environment.rascal.library.experiments.VL;

import java.util.HashMap;

import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IValue;
import org.meta_environment.rascal.interpreter.IEvaluatorContext;

import processing.core.PApplet;

public class Line extends VELEM {

	public Line(HashMap<String,IValue> inheritedProps, IList props, IEvaluatorContext ctx) {
		super(inheritedProps, props, ctx);
	}

	@Override
	BoundingBox draw(PApplet pa, int valueIndex, int left, int bottom) {
		if(valueIndex < getNumberOfValues() -1){
			pa.fill(getFillStyle(valueIndex));
			pa.stroke(getStrokeStyle(valueIndex));
			pa.strokeWeight(getLineWidth(valueIndex));
			int h1 = getHeight(valueIndex);
			int h2 = getHeight(valueIndex + 1);
			int w = getWidth(valueIndex);
			//System.err.println("line: h =" + h + ", w = " + w);
			//System.err.println("line: " + left + ", " + (bottom-h) + ", " + (left+w) + ", " + bottom);
			pa.line(left, bottom - h1, left + w, bottom - h2);
			return new BoundingBox(w, max(h1,h2));  // TODO not right since extends to the left
		} else
			return new BoundingBox(0,0);
	}

}
