/*******************************************************************************
 * Copyright (c) 2009-2011 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:

 *   * Paul Klint - Paul.Klint@cwi.nl - CWI
*******************************************************************************/
package org.rascalmpl.library.vis;

import org.rascalmpl.library.vis.properties.IPropertyValue;
import org.rascalmpl.library.vis.properties.PropertyManager;
import org.rascalmpl.library.vis.FigureApplet;

/**
 * Text element.
 * 
 * @author paulk
 *
 */
public class Text extends Figure {
	private static boolean debug = false;
	private float topAnchor = 0;
	private float bottomAnchor = 0;
	private float leftAnchor;
	private float rightAnchor;
	private float hfill = 0;
	private float vfill = 0;
	private IPropertyValue<String> txt;
	private int textAlignH = FigureApplet.CENTER;	

	public Text(IFigureApplet fpa, PropertyManager properties,IPropertyValue<String> txt) {
		super(fpa, properties);
		this.txt = txt;
		if(debug)System.err.printf("Text: %s\n", txt.getValue());
	}
	
	@Override
	public
	void bbox(float desiredWidth, float desiredHeight){
		float halign = getHAlignProperty();
		textAlignH = (halign < 0.5f) ? FigureApplet.LEFT : (halign > 0.5f) ? FigureApplet.RIGHT : FigureApplet.CENTER;

		applyFontProperties();
		topAnchor = fpa.textAscent() ;
		bottomAnchor = fpa.textDescent();
		
		String [] lines = txt.getValue().split("\n");
		int nlines = lines.length;
		width = 0;
		for(int i = 0; i < nlines; i++)
			width = max(width, fpa.textWidth(lines[i]));
		
		if(nlines > 1){
			hfill = textAlignH == FigureApplet.LEFT ? 0 : textAlignH == FigureApplet.RIGHT ? width : width/2;
			height = nlines * (topAnchor + bottomAnchor) + bottomAnchor;
			topAnchor = bottomAnchor = getVAlignProperty() * height;
		} else {
			hfill = width/2;
			height = topAnchor + bottomAnchor;
		}
		/*
		if(debug){
			System.err.printf("text.bbox: font=%s, ascent=%f, descent=%f\n", fpa.getFont(), fpa.textAscent(), fpa.textDescent() );
			System.err.printf("text.bbox: txt=\"%s\", width=%f, height=%f angle =%f\n", txt, width, height, getTextAngleProperty());
		}
		*/
		if(getTextAngleProperty() != 0){
			float angle = FigureApplet.radians(getTextAngleProperty());
			float sina = FigureApplet.sin(angle);
			float cosa = FigureApplet.cos(angle);
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
			
			hfill = width/2;
			if(nlines > 1){
				vfill = textAlignH == FigureApplet.LEFT ? height : textAlignH == FigureApplet.RIGHT ? 0 : height/2;
			} else {
				vfill = height/2;
			}
			
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
	
		// if(debug)System.err.printf("text.draw: %s, font=%s, left=%f, top=%f, width=%f, height=%f\n", txt, fpa.getFont(), left, top, width, height);
		if(height > 0 && width > 0){
			float angle = getTextAngleProperty();

			fpa.textAlign(textAlignH,FigureApplet.CENTER);
			if(angle != 0){
				fpa.pushMatrix();
				fpa.translate(left + hfill, top + vfill);
				fpa.rotate(FigureApplet.radians(angle));
				fpa.text(txt.getValue(), 0, 0);
				fpa.popMatrix();
			} else {
				fpa.text(txt.getValue(), left + hfill, top + height/2);
//				vlp.rectMode(FigureApplet.CORNERS);
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
	public float topAlign(){
		return topAnchor;
	}
	
	@Override
	public float bottomAlign(){
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
