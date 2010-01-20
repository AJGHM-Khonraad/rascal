package org.meta_environment.rascal.library.experiments.VL;

import org.eclipse.imp.pdb.facts.IList;
import org.meta_environment.rascal.interpreter.IEvaluatorContext;

import processing.core.PApplet;


public class HCat extends Compose {
	
	int hgap;
	float topAnchor = 0;
	float bottomAnchor = 0;
	private static boolean debug = false;

	HCat(VLPApplet vlp, PropertyManager inheritedProps, IList props, IList elems, IEvaluatorContext ctx) {
		super(vlp, inheritedProps, props, elems, ctx);
	}
	
	@Override
	void bbox(int left, int top){
		this.left = left;
		this.top = top;
		width = 0;
		height = 0;
		topAnchor = 0;
		bottomAnchor = 0;
		hgap = getHGapProperty();
		for(VELEM ve : velems){
			ve.bbox();
			width += ve.width;
			topAnchor = max(topAnchor, ve.topAnchor());
			bottomAnchor = max(bottomAnchor, ve.bottomAnchor());
		} 
		int ngaps = (velems.length - 1);
		width += ngaps * hgap;
		height = topAnchor + bottomAnchor;
		if(debug)System.err.printf("hcat: width=%f, height=%f, topAnchor=%f, bottomAnchor=%f\n", width, height, topAnchor, bottomAnchor);
	}				
	@Override
	void draw(){

		applyProperties();

//		float bottom = top + height;
//		float veTop;

		// Draw from left to right
		for(VELEM ve : velems){
//			if(ve.isTopAligned())
//				veTop = top;
//			else if(ve.isBottomAligned())
//				veTop = bottom - ve.height;
//			else
//				veTop = top + (height - ve.height)/2;
			
			ve.draw(left, top + topAnchor - ve.topAnchor());
			left += ve.width + hgap;
		}
	}

	@Override
	void bbox() {
		bbox(0,0);
	}

	@Override
	void draw(float left, float top) {
		this.left = PApplet.round(left);
		this.top =  PApplet.round(top);
		draw();
	}
	

}
