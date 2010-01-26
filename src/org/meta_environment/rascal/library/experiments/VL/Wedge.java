package org.meta_environment.rascal.library.experiments.VL;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IList;
import org.meta_environment.rascal.interpreter.IEvaluatorContext;

import processing.core.PApplet;

public class Wedge extends Container {
	private float fromAngle;
	private float toAngle;
	private float radius;
	private int innerRadius;
	private float leftAnchor;
	private float rightAnchor;
	private float topAnchor;
	private float bottomAnchor;
	
	private float centerX;
	private float centerY;
	
	float Ax;	// start of outer arc (relative to center)
	float Ay;
	
	float Bx;	// end of outer arc
	float By;
	
	float Cx;	// start of inner arc
	float Cy;
	
	float Dx;	// end of inner arc
	float Dy;
	
	int qFrom;
	int qTo;
	
	private static boolean debug = true;

	public Wedge(VLPApplet vlp, PropertyManager inheritedProps, IList props, IConstructor inside, IEvaluatorContext ctx) {
		super(vlp, inheritedProps, props, inside, ctx);
	}
	
	// Determine quadrant of angle according to numbering scheme:
	//  3  |  4
	//  2  |  1
	
	private int quadrant(double angle){
		if(debug)System.err.printf("angle 1 = %f\n", angle);
		if(angle < 0)
			return quadrant(angle +2 * PApplet.PI);
		if( angle <=  PApplet.PI/2)
			return 1;
		if( angle <=  PApplet.PI)
			return 2;
		if( angle <=  1.5 * PApplet.PI)
			return 3;
		if(angle <= 2 * PApplet.PI)
			return 4;
		return quadrant(angle - 2 * PApplet.PI);
	}

	@Override
	void bbox(float left, float top){
		this.left = left;
		this.top = top;
		
		radius = getHeightProperty();
		float lw = getLineWidthProperty();
		innerRadius = getInnerRadiusProperty();

		fromAngle = PApplet.radians(getFromAngleProperty());
		toAngle= PApplet.radians(getToAngleProperty());
		
		if(toAngle < fromAngle)
			toAngle += 2 * PApplet.PI;
		
		/*
		 * Consider mouseOver
		 */
		VELEM insideForMouseOver = getInsideForMouseOver();
		if(vlp.isRegisteredAsMouseOver(this) && insideForMouseOver != null){
			insideForMouseOver.bbox(left, top);
			this.width = insideForMouseOver.width;
			this.height = insideForMouseOver.height;
			return;
		}
		
		if(inside != null)	// Compute bounding box of inside object.
			inside.bbox();
		
		float sinFrom = PApplet.sin(fromAngle);
		float cosFrom = PApplet.cos(fromAngle);
		float sinTo = PApplet.sin(toAngle);
		float cosTo = PApplet.cos(toAngle);
		
		float rsinFrom = radius * abs(sinFrom);
		float rcosFrom = radius * abs(cosFrom);
		
		float rsinTo = radius * Math.abs(sinTo);
		float rcosTo = radius * Math.abs(cosTo);
		
		Ax = radius*cosFrom;  // start of outer arc
		Ay = radius*sinFrom;
		
		Bx = radius*cosTo;    // end of outer arc
		By = radius*sinTo;
		
		Cx = innerRadius*cosTo;  // start of inner arc
		Cy = innerRadius*sinTo;
		
		Dx = innerRadius*cosFrom; // end of inner arc
		Dy = innerRadius*sinFrom;
		
		qFrom = quadrant(fromAngle);
		qTo = quadrant(toAngle);
		
		if(debug)System.err.printf("qFrom=%d, qTo=%d\n", qFrom, qTo);
		
		/*
		 * Perform a case analysis to determine the anchor values.
		 * TODO Is there a nicer way to express this?
		 */
		if(qFrom == qTo && qTo < qFrom){
			leftAnchor = rightAnchor = topAnchor = bottomAnchor = radius;
		} else {
		
			switch(qFrom){
			case 1:
				switch(qTo){
				case 1:	leftAnchor = 0; 		rightAnchor = rcosFrom; topAnchor = 0; 		bottomAnchor = rsinTo;
				case 2:	leftAnchor = rcosTo; 	rightAnchor = rcosFrom; topAnchor = 0; 		bottomAnchor = radius; break;
				case 3:	leftAnchor = radius;	rightAnchor = rcosFrom;	topAnchor = rsinTo;	bottomAnchor = radius; break;
				case 4:	leftAnchor = radius;	rightAnchor = Math.max(
															  rcosFrom, 
						                                      rcosTo);	topAnchor = radius;	bottomAnchor = radius; break;
				}
				break;
			case 2:
				switch(qTo){
				case 2:	leftAnchor = rcosTo;	rightAnchor = 0; 		topAnchor = 0; 		bottomAnchor = rsinFrom; break;
				case 3:	leftAnchor = radius; 	rightAnchor = 0; 		topAnchor = rsinTo; bottomAnchor = rsinFrom; break;
				case 4:	leftAnchor = radius;	rightAnchor = rcosTo;	topAnchor = radius;	bottomAnchor = rsinFrom; break;
				case 1:	leftAnchor = radius;	rightAnchor = radius;	topAnchor = radius;	bottomAnchor = Math.max(rsinFrom, rsinTo); break;
				}
				break;
			case 3:
				switch(qTo){
				case 3:	leftAnchor = rcosFrom;	rightAnchor = 0; 		topAnchor = rsinTo; bottomAnchor = 0; break;
				case 4:	leftAnchor = rcosFrom; 	rightAnchor = rcosTo; 	topAnchor = radius; bottomAnchor = 0; break;
				case 1:	leftAnchor = rcosFrom;	rightAnchor = radius;	topAnchor = radius;	bottomAnchor = rsinTo; break;
				case 2:	leftAnchor = Math.max(
									 rcosFrom, 
									 rcosTo);	rightAnchor = radius;	topAnchor = radius;	bottomAnchor = radius; break;
				}
				break;
	
			case 4:
				switch(qTo){
				case 4:	leftAnchor = 0;			rightAnchor = rcosTo; 		topAnchor = rsinFrom; 		bottomAnchor = 0; break;
				case 1:	leftAnchor = 0; 		rightAnchor = radius; 		topAnchor = rsinFrom; 		bottomAnchor = rsinTo; break;
				case 2:	leftAnchor = rcosTo;	rightAnchor = radius;		topAnchor = rsinFrom;		bottomAnchor = radius; break;
				case 3:	leftAnchor = radius;	rightAnchor = radius;		topAnchor = Math.max(rsinFrom, 
																								rsinTo);	bottomAnchor = radius; break;
				}
				break;
			}
		}
			
		leftAnchor += lw/2;
		rightAnchor += lw/2;
		topAnchor += lw/2;
		bottomAnchor += lw/2;
		
		width = leftAnchor + rightAnchor;
		height = topAnchor + bottomAnchor;
		if(debug)System.err.printf("bbox.wedge: fromAngle=%f, toAngle=%f, leftAnChor=%f, rightAnchor=%f, topAnchor=%f, bottomAnchor =%f, %f, %f)\n", 
				fromAngle, toAngle, leftAnchor, rightAnchor, topAnchor, bottomAnchor, width, height);
	}
	
	
	
	/**
	 * arcVertex: draw an arc as a bezierVertex that is part of a beginShape() ... endShape() sequence
	 * @param r			radius
	 * @param fromAngle	begin angle
	 * @param toAngle	end angle
	 */
	void arcVertex(float r, float fromAngle, float toAngle){
		if(debug)System.err.printf("arcVertex: fromAngle=%f, toAngle=%f\n", fromAngle, toAngle);
	    if(abs(toAngle - fromAngle) < PApplet.PI/2){
			float middleAngle = (toAngle - fromAngle)/2;		// fromAngle + middleAngle == middle of sector
			float middleR = abs(r / PApplet.cos(middleAngle));	// radius of control point M
			
			float Mx = centerX + middleR * PApplet.cos(fromAngle + middleAngle);	// coordinates of M
			float My = centerY + middleR * PApplet.sin(fromAngle + middleAngle);
			
			float Fx = centerX + r * PApplet.cos(fromAngle);			// coordinates of start point
			float Fy = centerY + r * PApplet.sin(fromAngle);
			
			float Tx = centerX + r * PApplet.cos(toAngle);			// coordinates of end point
			float Ty = centerY + r * PApplet.sin(toAngle);
			if(debug){
				System.err.printf("arcVertex: fromAngle=%f, middleAngle=%f, toAngle=%f, r=%f, middleR=%f\n", 
								fromAngle, middleAngle, toAngle, r, middleR);
				System.err.printf("arcVertex: Fx=%f, Fy=%f, Mx=%f, My=%f, Tx=%f, Ty=%f\n",
									Fx, Fy, Mx, My, Tx, Ty);
			}
			/*
			 * Add a bezierVertex between (Fx,Fy) and (Tx,Ty) using (Mx,My) as control point
			 */
			vlp.bezierVertex(Fx, Fy, Mx, My, Tx, Ty);
	    } else {
	    	/*
	    	 * Split when difference is larger than PI/2
	    	 */
	    	float medium = (toAngle - fromAngle)/2;
	    	arcVertex(r, fromAngle, fromAngle + medium);
	    	arcVertex(r, fromAngle + medium, toAngle);
	    }
	}
	
	@Override
	void drawContainer() {
		centerX = left + leftAnchor;
		centerY = top + topAnchor;
		
		if(debug)System.err.printf("wedge.drawContainer: %f, %f\n", centerX, centerY);
		
		applyProperties();
			
		vlp.beginShape();
		vlp.vertex(centerX + Ax, centerY + Ay);
		arcVertex(radius, fromAngle, toAngle);
		vlp.vertex(centerX + Cx, centerY + Cy);
		arcVertex(innerRadius, toAngle, fromAngle);
		vlp.vertex(centerX + Ax, centerY + Ay);
		vlp.endShape();
	}
	
	@Override 
	boolean insideFits(){
		System.err.printf("Wedge.insideFits!\n");
		return true;
	}
	
	@Override
	public float leftAnchor(){
		return leftAnchor;
	}
	
	@Override
	public float rightAnchor(){
		return rightAnchor;
	}
	
	@Override
	public float topAnchor(){
		return topAnchor;
	}
	
	@Override
	public float bottomAnchor(){
		return bottomAnchor;
	}
}
