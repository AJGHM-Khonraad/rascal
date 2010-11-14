package org.rascalmpl.library.vis.compose;

import org.eclipse.imp.pdb.facts.IList;
import org.rascalmpl.interpreter.IEvaluatorContext;
import org.rascalmpl.library.vis.Figure;
import org.rascalmpl.library.vis.FigurePApplet;
import org.rascalmpl.library.vis.PropertyManager;

/**
 * 
 * Vertical composition of elements using their horizontal anchor for alignment.
 * 
 * @author paulk
 *
 */
public class VCat extends Compose {
	
	float vgap;
	float leftAnchor = 0;
	float rightAnchor = 0;
	private static boolean debug = false;

	public VCat(FigurePApplet fpa, PropertyManager properties, IList elems, IEvaluatorContext ctx) {
		super(fpa, properties, elems, ctx);
	}
	
	@Override
	public
	void bbox(){

		width = 0;
		height = 0;
		leftAnchor = 0;
		rightAnchor = 0;
		vgap = getVGapProperty();
		if(debug)System.err.printf("vertical.bbox: vgap=%f\n", vgap);
		for(Figure ve : figures){
			ve.bbox();
			leftAnchor = max(leftAnchor, ve.leftAnchor());
			rightAnchor = max(rightAnchor, ve.rightAnchor());
			height = height + ve.height;
		}
		
		width = leftAnchor + rightAnchor;
		int ngaps = (figures.length - 1);
		
		height += ngaps * vgap;
		if(debug)System.err.printf("vcat: width=%f, height=%f, leftAnchor=%f, rightAnchor=%f\n", width, height, leftAnchor, rightAnchor);
	}
	
	@Override
	public
	void draw(float left, float top){
		this.left = left;
		this.top = top;
		
		applyProperties();

		float bottom = top + height;

		// Draw from top to bottom
		for(int i = figures.length-1; i >= 0; i--){
			if(debug)System.err.printf("vertical.draw: i=%d, vgap=%f, bottom=%f\n", i, vgap, bottom);
			Figure ve = figures[i];
			float h = ve.height;
			ve.draw(left + leftAnchor - ve.leftAnchor(), bottom - h);
			bottom -= h + vgap;
		}
	}
	
	@Override
	public float leftAnchor(){
		return leftAnchor;
	}
	
	@Override
	public float rightAnchor(){
		return rightAnchor;
	}
}
