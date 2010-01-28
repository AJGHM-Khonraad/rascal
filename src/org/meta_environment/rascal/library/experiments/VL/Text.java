package org.meta_environment.rascal.library.experiments.VL;

import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IString;
import org.meta_environment.rascal.interpreter.IEvaluatorContext;

import processing.core.PApplet;
import processing.core.PConstants;

/**
 * Text element.
 * 
 * @author paulk
 *
 */
public class Text extends VELEM {

	private String txt;
	private static boolean debug = false;
	private float topAnchor = 0;
	private float bottomAnchor = 0;

	public Text(VLPApplet vlp, PropertyManager inheritedProps, IList props, IString text, IEvaluatorContext ctx) {
		super(vlp, inheritedProps, props, ctx);
		this.txt = text.getValue();
		if(debug)System.err.printf("Text: %s\n", txt);
	}
	
	@Override
	void bbox(){
	
		vlp.textSize(getFontSizeProperty());
		topAnchor = vlp.textAscent() ;
		bottomAnchor = vlp.textDescent();
		
		height = topAnchor + bottomAnchor;
		width = vlp.textWidth(txt);
		if(debug){
			System.err.printf("text.bbox: font=%s, ascent=%f, descent=%f\n", vlp.getFont(), vlp.textAscent(), vlp.textDescent() );
			System.err.printf("text.bbox: txt=\"%s\", width=%f, height=%f angle =%d\n", txt, width, height, getTextAngleProperty());
		}
		if(getTextAngleProperty() != 0){
			float angle = PApplet.radians(getTextAngleProperty());
			float sina = PApplet.sin(angle);
			float cosa = PApplet.cos(angle);
			float h1 = abs(width * sina);
			float w1 = abs(width * cosa);
			float h2 = abs(height *  cosa);
			float w2 = abs(height *  sina);
			
			width = w1 + w2;
			height = h1 + h2;
			//TODO adjust anchors
			
			if(debug)System.err.printf("bbox text: height=%f, width=%f, h1=%f h2=%f w1=%f w2=%f\n", height, width, h1, h2, w1, w2);
		}
	}
	
	@Override
	void draw(float left, float top) {
		this.left = left;
		this.top = top;
		applyProperties();
		applyFontColorProperty();
	
		if(debug)System.err.printf("text.draw: %s, left=%d, top=%d, width=%f, height=%f\n", txt, left, top, width, height);
		if(height > 0 && width > 0){
			float angle = getTextAngleProperty();

			vlp.textAlign(PConstants.CENTER,PConstants.CENTER);
			if(angle != 0){
				vlp.pushMatrix();
				vlp.translate(left + width/2, top + height/2);
				vlp.rotate(PApplet.radians(angle));
				vlp.text(txt, 0, 0);
				vlp.popMatrix();
			} else {
				vlp.text(txt, left + width/2, top + height/2);
//				vlp.rectMode(PConstants.CORNERS);
//				vlp.text(txt, left, top, left+width, top+height);
			}
		}
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
