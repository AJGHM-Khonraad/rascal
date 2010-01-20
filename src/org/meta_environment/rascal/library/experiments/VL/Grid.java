package org.meta_environment.rascal.library.experiments.VL;

import org.eclipse.imp.pdb.facts.IList;
import org.meta_environment.rascal.interpreter.IEvaluatorContext;


public class Grid extends Compose {
	
	float xElem[];
	float yElem[];
	
	float extTop = 0;
	float extBot = 0;
	float extLeft = 0;
	float extRight = 0;
	private static boolean debug = true;

	Grid(VLPApplet vlp, PropertyManager inheritedProps, IList props, IList elems, IEvaluatorContext ctx) {
		super(vlp, inheritedProps, props, elems, ctx);
		xElem = new float[elems.length()];
		yElem = new float[elems.length()];
	}
	
	@Override
	void bbox(int left, int top){
		this.left = left;
		this.top = top;
		width = getWidthProperty();
		height = 0;
		float w = 0;
		int nrow = 0;
		
		int hgap = getHGapProperty();
		int vgap = getVGapProperty();
		
		int lastRow = (hgap == 0) ? 0 : velems.length / (1 + (int) (width / hgap));
		if(debug)System.err.printf("lastRow = %d\n", lastRow);
		
		extTop = 0;
		extBot = 0;
		extLeft = 0;
		extRight = 0;
		
		for(int i = 0; i < velems.length; i++){
			
			if(w > width){
				nrow++;
				height += vgap;
				w = 0;
			}
			
			VELEM ve = velems[i];
			ve.bbox();
			
			if(w == 0)
				//extLeft = max(extLeft, ve.isLeftAligned() ? 0 : ve.isRightAligned() ? ve.width : ve.width/2);
				extLeft = max(extLeft, ve.leftAnchor());
			if(w + hgap >= width)
				//extRight = max(extRight, ve.isRightAligned() ? 0 : ve.isLeftAligned() ? ve.width : ve.width/2);
				extRight = max(extRight, ve.rightAnchor());
			if(nrow == 0)
				//extTop = max(extTop, ve.isTopAligned() ? 0 : ve.isBottomAligned() ? ve.height : ve.height/2);
				extTop = max(extTop, ve.topAnchor());
			if(nrow == lastRow){
				if(debug)System.err.printf("nrow == lastRow!, isBottomAligned=%b\n", ve.isBottomAligned());
				//extBot = max(extBot, ve.isBottomAligned() ? 0 : ve.isTopAligned() ? ve.height : ve.height/2);
				extBot = max(extBot, ve.bottomAnchor());
			}
			
			if(debug)System.err.printf("i=%d, row=%d, w=%f, extLeft=%f, extRight=%f, extTop=%f, extBot=%f\n", i, nrow, w, extLeft, extRight, extTop, extBot);
			
			xElem[i] = w;
			yElem[i] = height;
			w += hgap;
		}
		width += extLeft + extRight;
		height += extTop + extBot;
		if(debug)System.err.printf("grid.bbox: %f, %f\n", width, height);
	}
	
	@Override
	void draw(){
		
		applyProperties();

		for(int i = 0; i < velems.length; i++){
			
			VELEM ve = velems[i];
			
			if(debug)System.err.printf("i=%d: %f, %f, left=%d, top=%d\n", i, xElem[i], yElem[i], left, top);

//			float veLeft;
//			if(ve.isLeftAligned())
//				veLeft = left + extLeft + xElem[i];
//			else if(ve.isRightAligned())
//				veLeft = left + extLeft + xElem[i] - ve.width;
//			else
//				veLeft = left + extLeft + xElem[i] - ve.width/2;
//			
//			float veTop;
//			if(ve.isTopAligned())
//				veTop = top + extTop + yElem[i];
//			else if(ve.isBottomAligned())
//				veTop = top + extTop + yElem[i] - ve.height;
//			else
//				veTop = top + extTop + yElem[i] - ve.height/2;
//			
//			ve.draw(veLeft, veTop);
			
			ve.draw(left + extLeft + xElem[i] - ve.leftAnchor(), top + extTop + yElem[i] - ve.topAnchor());
		}
	}
}
