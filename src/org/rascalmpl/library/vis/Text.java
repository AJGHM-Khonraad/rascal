package org.rascalmpl.library.vis;

import org.rascalmpl.interpreter.IEvaluatorContext;
import org.rascalmpl.library.vis.properties.IPropertyManager;
import org.rascalmpl.library.vis.properties.IStringPropertyValue;

import processing.core.PApplet;
import processing.core.PConstants;

/**
 * Text element.
 * 
 * @author paulk
 *
 */
public class Text extends Figure {

	private IStringPropertyValue txt;
	private static boolean debug = false;
	private float topAnchor = 0;
	private float bottomAnchor = 0;
	private float leftAnchor;
	private float rightAnchor;

	public Text(FigurePApplet fpa, IPropertyManager properties, IStringPropertyValue text, IEvaluatorContext ctx) {
		super(fpa, properties, ctx);
		this.txt = text;
		if(debug)System.err.printf("Text: %s\n", txt);
	}
	
	@Override
	public
	void bbox(){
		applyFontProperties();
		topAnchor = fpa.textAscent() ;
		bottomAnchor = fpa.textDescent();
		
		height = topAnchor + bottomAnchor;
		width = fpa.textWidth(txt.getValue());
		if(debug){
			System.err.printf("text.bbox: font=%s, ascent=%f, descent=%f\n", fpa.getFont(), fpa.textAscent(), fpa.textDescent() );
			System.err.printf("text.bbox: txt=\"%s\", width=%f, height=%f angle =%f\n", txt, width, height, getTextAngleProperty());
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
			
			leftAnchor = w1/width;
			rightAnchor = w2/width;
			topAnchor = h1/height;
			bottomAnchor = h2/height;
			
			
			if(debug)System.err.printf("bbox text: height=%f, width=%f, h1=%f h2=%f w1=%f w2=%f\n", height, width, h1, h2, w1, w2);
		}
	}
	
	@Override
	public
	void draw(float left, float top) {
		this.setLeft(left);
		this.setTop(top);
		
		applyProperties();
		applyFontProperties();
	
		if(debug)System.err.printf("text.draw: %s, font=%s, left=%f, top=%f, width=%f, height=%f\n", txt, fpa.getFont(), left, top, width, height);
		if(height > 0 && width > 0){
			float angle = getTextAngleProperty();

			fpa.textAlign(PConstants.CENTER,PConstants.CENTER);
			if(angle != 0){
				fpa.pushMatrix();
				fpa.translate(left + width/2, top + height/2);
				fpa.rotate(PApplet.radians(angle));
				fpa.text(txt.getValue(), 0, 0);
				fpa.popMatrix();
			} else {
				fpa.text(txt.getValue(), left + width/2, top + height/2);
//				vlp.rectMode(PConstants.CORNERS);
//				vlp.text(txt, left, top, left+width, top+height);
			}
		}
	}
	
//	@Override
//	public float leftAnchor(){
//		float res= leftAnchor;
//		System.err.println(this + ".leftAnchor = " + res);
//		return res;
//	}
//	
//	@Override
//	public float rightAnchor(){
//		float res = rightAnchor;
//		System.err.println(this + ".rightAnchor = " + res);
//		return res;
//	}
	
	@Override
	public float topAnchor(){
		return topAnchor;
	}
	
	@Override
	public float bottomAnchor(){
		return bottomAnchor;
	}
	
	@Override
	public
	String toString(){
		return new StringBuffer("text").append("(").append("\"").append(txt.getValue()).append("\",").
		append(getLeft()).append(",").
		append(getTop()).append(",").
		append(width).append(",").
		append(height).append(")").toString();
	}
}
