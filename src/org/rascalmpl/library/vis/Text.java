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

import org.rascalmpl.library.vis.graphics.GraphicsContext;
import org.rascalmpl.library.vis.properties.Properties;
import org.rascalmpl.library.vis.properties.PropertyManager;
import org.rascalmpl.library.vis.properties.PropertyValue;

/**
 * Text element.
 * 
 * @author paulk
 *
 */
public class Text extends Figure {
	private static boolean debug = false;
	private double topAnchor = 0;
	private double bottomAnchor = 0;
	private double leftAnchor;
	private double rightAnchor;
	private double hfill = 0;
	private double vfill = 0;
	private PropertyValue<String> txt;
	private int textAlignH = FigureApplet.CENTER;	


	public Text(IFigureExecutionEnvironment fpa, PropertyManager properties,PropertyValue<String> txt) {
		super(fpa, properties);
		this.txt = txt;
		//if(debug)System.err.printf("Text: %s\n", txt.getValue());
	}
	
	public void computeFiguresAndProperties() {
		super.computeFiguresAndProperties();
		txt.compute();
	}
	
	
	@Override
	public
	void bbox(){
		double halign = getHAlignProperty();
		textAlignH = (halign < 0.5f) ? FigureApplet.LEFT : (halign > 0.5f) ? FigureApplet.RIGHT : FigureApplet.CENTER;
		topAnchor = getTextAscent();
		bottomAnchor = getTextDescent();
		
		String [] lines = txt.getValue().split("\n");
		int nlines = lines.length;
		minSize.setWidth(0);
		for(int i = 0; i < nlines; i++)
			minSize.setWidth(Math.max(minSize.getWidth(), getTextWidth(lines[i])));
		
		if(nlines > 1){
			minSize.setHeight(nlines * (topAnchor + bottomAnchor) + bottomAnchor);
			topAnchor = bottomAnchor =  minSize.getHeight();
		} else {
			minSize.setHeight(topAnchor + bottomAnchor);
		}
		hfill = textAlignH == FigureApplet.LEFT ? 0 : textAlignH == FigureApplet.RIGHT ? minSize.getWidth() : minSize.getWidth()/2;
		/*
		if(debug){
			System.err.printf("text.bbox: font=%s, ascent=%f, descent=%f\n", fpa.getFont(), fpa.textAscent(), fpa.textDescent() );
			System.err.printf("text.bbox: txt=\"%s\", width=%f, height=%f angle =%f\n", txt, width, height, getTextAngleProperty());
		}
		*/
		if(getTextAngleProperty() != 0){
			double angle = FigureApplet.radians(getTextAngleProperty());
			double sina = FigureApplet.sin(angle);
			double cosa = FigureApplet.cos(angle);
			double h1 = Math.abs(minSize.getWidth() * sina);
			double w1 = Math.abs(minSize.getWidth() * cosa);
			double h2 = Math.abs(minSize.getHeight() *  cosa);
			double w2 = Math.abs(minSize.getHeight() *  sina);
			
			minSize.setWidth(w1 + w2);
			minSize.setHeight(h1 + h2);
			
			leftAnchor = w1/minSize.getWidth();
			rightAnchor = w2/minSize.getWidth();
			topAnchor = h1/minSize.getHeight();
			bottomAnchor = h2/minSize.getHeight();
			
			hfill = minSize.getWidth()/2;
			if(nlines > 1){
				vfill = textAlignH == FigureApplet.LEFT ? minSize.getHeight() : textAlignH == FigureApplet.RIGHT ? 0 : minSize.getHeight()/2;
			} else {
				vfill = minSize.getHeight()/2;
			}
			
			if(debug)System.err.printf("bbox text: height=%f, width=%f, h1=%f h2=%f w1=%f w2=%f\n", minSize.getHeight(), minSize.getWidth(), h1, h2, w1, w2);
		}
		setNonResizable();
		//super.bbox();
	}
	
	@Override
	public
	void draw(double left, double top, GraphicsContext gc) {
		this.setLeft(left);
		this.setTop(top);
		
		applyProperties(gc);
	
		//if(debug)System.err.printf("text.draw: %s, font=%s, left=%f, top=%f, width=%f, height=%f\n", txt, fpa.getFont(), left, top, minSize.getWidth(), minSize.getHeight());
		if(minSize.getHeight() > 0 && minSize.getWidth() > 0){
			double angle = getTextAngleProperty();

			if(angle != 0){
				gc.pushMatrix();
				gc.translate(left + hfill, top + vfill);
				gc.rotate((FigureApplet.radians(angle)));
				gc.text(txt.getValue(), 0, 0);
				gc.popMatrix();
			} else {
				gc.text(txt.getValue(), left, top);
//				vlp.rectMode(FigureApplet.CORNERS);
//				vlp.text(txt, left, top, left+width, top+height);
			}
		}
	}
	
//	@Override
//	public double leftAnchor(){
//		double res= leftAnchor;
//		System.err.println(this + ".leftAnchor = " + res);
//		return res;
//	}
//	
//	@Override
//	public double rightAnchor(){
//		double res = rightAnchor;
//		System.err.println(this + ".rightAnchor = " + res);
//		return res;
//	}
	
	@Override
	public double topAlign(){
		return topAnchor;
	}
	
	@Override
	public double bottomAlign(){
		return bottomAnchor;
	}
	
	@Override
	public
	String toString(){
		return new StringBuffer("text").append("(").append("\"").append(txt.getValue()).append("\",").
		append(getLeft()).append(",").
		append(getTop()).append(",").
		append(minSize.getWidth()).append(",").
		append(minSize.getHeight()).append(")").toString();
	}

	@Override
	public void layout() {
		size.set(minSize);
		
	}
}
