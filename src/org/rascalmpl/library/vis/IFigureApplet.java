/*******************************************************************************
 * Copyright (c) 2009-2011 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:

 *   * Bert Lisser - Bert.Lisser@cwi.nl (CWI)
 *   * Paul Klint - Paul.Klint@cwi.nl - CWI
*******************************************************************************/

package org.rascalmpl.library.vis;

public interface IFigureApplet {
	public Object getComp(); // get Composite. Needed by swt variant
	public void init();
	public void setup();
	public void draw();
	public int getFigureWidth();
	public int getFigureHeight();
	public void incDepth();
	public void decDepth();
	public boolean isVisible(int d);
	public void registerId(String id, Figure fig);
	public Figure getRegisteredId(String id);
	public void registerFocus(Figure f);
	public boolean isRegisteredAsFocus(Figure f);
	public void unRegisterFocus(Figure f);
	public void registerMouseOver(Figure f);
	public boolean isRegisteredAsMouseOver(Figure f);
	public void unRegisterMouseOver(Figure f);
	public void keyPressed();
	public void mouseReleased();
	public void mouseMoved();
	public void mouseDragged();
	public void mousePressed();
	public void setComputedValueChanged();
	public void line(double arg0, double arg1, double arg2, double arg3);
	public void rect(double arg0, double arg1, double arg2, double arg3);
	public void ellipse(double arg0, double arg1, double arg2, double arg3);
	public void rectMode(int arg0);
	public void ellipseMode(int arg0);
	public void fill(int arg0);
	public void stroke(int arg0);
	public void strokeWeight(double arg0);
	public void textSize(double arg0);
	public void textColor(int arg0);
	public void textAlign(int arg0, int arg1);
	public void textAlign(int arg0);
	public void textFont(Object arg0);
	public double textWidth(String txt);
	public double textAscent();
	public double textDescent();
	public void text(String arg0, double arg1, double arg2);
	public void pushMatrix();
	public void popMatrix();
	public void rotate(double arg0);
	public void translate(double arg0, double arg1);
	public void scale(double arg0, double arg1);
	public void bezierVertex(double arg0, double arg1, double arg2, double arg3,
			double arg4, double arg5);
	public void vertex(double arg0, double arg1);
	public void curveVertex(double arg0, double arg1);
	public void noFill();
	public void arc(double arg0, double arg1, double arg2, double arg3, double arg4,
			double arg5);
	public void beginShape();
	public void beginShape(int arg0);
	public void endShape();
	public void endShape(int arg0 );
	public void print();
	// From PApplet 
	public Object createFont(String fontName, double fontSize);
	public void smooth();
	
	// From awt  
	public void setCursor(Object cursor);
	public void add(Object comp);
	public void remove(Object comp);
	public Object getFont();
	public void setBackground(Object color);
	public void setForeground(Object color);	
	public void invalidate();
	public void validate();
	public void stroke(double arg0, double arg1, double arg2);
	public String getName();
	// Needed by SpringGraph
}
