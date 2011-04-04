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
 *   * Arnold Lankamp - Arnold.Lankamp@cwi.nl
*******************************************************************************/
package org.rascalmpl.library.vis;

import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.IString;
import org.rascalmpl.interpreter.IEvaluatorContext;
import org.rascalmpl.library.vis.properties.DefaultPropertyManager;
import org.rascalmpl.library.vis.properties.IPropertyManager;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;

/**
 * 
 * FigurePApplet: wrapper that adapts Processing's PApplet to our needs.
 * 
 * @author paulk
 *
 */
public class FigurePApplet extends PApplet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6074377218243765483L;
	
	private int width;						// Current dimensions of canvas
	private int height;
	
	final private int defaultWidth = 1000;	// Default dimensions of canvas
	final private int defaultHeight = 1000;
	
	private Figure  figure;					// The figure that is drawn on the canvas
	private float figureWidth = defaultWidth;
	private float figureHeight = defaultHeight;
	
	private Figure focus = null;
	private boolean focusSelected = false;
	
	private Figure mouseOver = null;
	private boolean computedValueChanged = true;

	private static boolean debug = true;
	private boolean saveFigure = true;
	private String file;
	private float scale = 1.0f;
	private int left = 0;
	private int top = 0;

	private PGraphics canvas;
	private PFont stdFont;
	
	private int depth = 0;

	@SuppressWarnings("unused")
	private int lastMouseX = 0;
	@SuppressWarnings("unused")
	private int lastMouseY = 0;

	public FigurePApplet(IConstructor fig, ISourceLocation sloc, IEvaluatorContext ctx){
		saveFigure = true;
		try {
			this.file = ctx.getResolverRegistry().getResourceURI(sloc.getURI()).toASCIIString();
			
			if(file.startsWith("file:")){
				file = file.substring(5);
			}
			System.err.println("saveFile = " + file);
			IPropertyManager def = new DefaultPropertyManager(this);
			this.figure = FigureFactory.make(this, fig, def, ctx);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public FigurePApplet(IConstructor fig, IEvaluatorContext ctx){
		saveFigure = false;
		IPropertyManager def = new DefaultPropertyManager(this);
		this.figure = FigureFactory.make(this, fig, def, ctx);
		setName("Figure");
	}
	
	public FigurePApplet(IString name, IConstructor fig, IEvaluatorContext ctx){
		saveFigure = false;
		IPropertyManager def = new DefaultPropertyManager(this);
		this.figure = FigureFactory.make(this, fig, def, ctx);
		setName(name.getValue());
	}

	@Override
	public void setup(){
		System.err.println("setup called");
		System.err.println("name: " + getName());
		if(saveFigure){
			canvas = createGraphics(defaultWidth, defaultHeight, JAVA2D);
			figure.bbox();
			figureWidth = figure.width;
			figureHeight = figure.height;
			canvas = createGraphics(round(figureWidth + 2), round(figureHeight + 2), JAVA2D);
		} else {
			size(defaultWidth, defaultHeight);
		}
		noLoop();
		setLayout(null); // allows more precise position of AWT widgets.
		
		figure.bbox();
		
		computedValueChanged = false;
		figureWidth = figure.width;
		figureHeight = figure.height;
		width = max(defaultWidth, round(figureWidth)+2);
		height = max(defaultHeight, round(figureHeight)+2);
		resize(width, height);
		System.err.println("Figure size: " + figureWidth + ", " + figureHeight);
		System.err.println("resize to: " + width + ", " + height);
	}
	
	@Override
	public void draw(){
		stdFont = createFont("Helvetica", 15);		
		textFont(stdFont);
		if(saveFigure){
			canvas = createGraphics(round(figureWidth + 2), round(figureHeight + 2), JAVA2D);
			canvas.hint(ENABLE_NATIVE_FONTS);
			canvas.beginDraw();
			canvas.background(255);
			canvas.textFont(stdFont);
			canvas.textMode(MODEL);
			canvas.smooth();
			canvas.strokeJoin(MITER);
			figure.draw(1, 1);
			canvas.endDraw();
			canvas.save(file);
		} else {
			
			background(255);
			textFont(stdFont);
			smooth();
			strokeJoin(MITER);
			depth = 0;

			if(computedValueChanged){
				figure.bbox();
				figureWidth = figure.width;
				figureHeight = figure.height;
				computedValueChanged = false;
			}
			figure.draw(left,top);
			if(mouseOver != null)
				mouseOver.drawWithMouseOver(mouseOver.getLeft(), mouseOver.getTop());
			if(focus != null && focusSelected)
				focus.drawFocus();
		}
	}
	
	public int getFigureWidth(){
		//System.err.println("getFigureWidth: " + figureWidth);
		return round(figureWidth);
	}
	
	public int getFigureHeight(){
		//System.err.println("getFigureHeight: " + figureHeight);
		return round(figureHeight);
	}
	
	//-----------------------
	
	private void moveBy(int dx, int dy){
		left += dx;
		top += dy;
		redraw();
	}
	
	/*
	 * Interaction: handle mouse and key events.
	 */
	
	public void incDepth(){
		depth += 1;
	}
	
	public void decDepth(){
		depth -= 1;
	}
	
	public boolean isVisible(int d){
		return depth <= d;
	}
	
	// Id management
	
	HashMap<String, Figure> ids = new HashMap<String, Figure>();
	
	public void registerId(String id, Figure fig){
		ids.put(id, fig);
	}
	
	public Figure getRegisteredId(String id){
		return ids.get(id);
	}
	
	// Focus handling: called during mousePressed
	
	public void registerFocus(Figure f){
		focus = f;
		if(debug)System.err.println("registerFocus:" + f);
	}
	
	public boolean isRegisteredAsFocus(Figure f){
		return focus == f;
	}
	
	public void unRegisterFocus(Figure f){
		if(debug)System.err.println("unRegisterFocus:" + f);
		focus = null;
		focusSelected = false;
	}
	
	// MouseOver handling: called during mouseOver
	
	public void registerMouseOver(Figure f){
		mouseOver = f;
		if(debug)System.err.println("registerMouseOver:" + f);
	}
	
	public boolean isRegisteredAsMouseOver(Figure f){
		return mouseOver == f;
	}
	
	public void unRegisterMouseOver(Figure f){
		if(debug)System.err.println("unRegisterMouseOver:" + f);
		mouseOver = null;
	}
	
	
	@Override
	public void keyPressed(){
		if(debug)System.err.println("FPA, keyPressed: " + key);
		
		if(focus != null //&& focus.mouseInside(lastMouseX, lastMouseY) 
						 && focus.keyPressed(key, keyCode)){
			redraw();
			return;
		}
		
		if(key == CODED){
			if(keyCode == UP)
				moveBy(0, 20);
			if(keyCode == DOWN)
				moveBy(0, -20);
			if(keyCode == LEFT)
				moveBy(20, 0);
			if(keyCode == RIGHT)
				moveBy(-20, 0);
			figure.keyPressed(key, keyCode);
			return;
		} 
		if(key == '+' || key == '=')
			scale += 0.1;
		if(key == '-' || key == '_')
			scale -= 0.1;
		redraw();
	}
	
	
	
//	@Override
//	public void mouseDragged(){
//		
//		if(debug)System.err.println("mouseDragged: " + mouseX + ", " + mouseY);
//		if(keyPressed && key == SHIFT){
//			cursor(HAND);
//			left += mouseX - lastMouseX;
//			top += mouseY - lastMouseY;
//			lastMouseX = mouseX;
//			lastMouseY = mouseY;
//		}
//		if(focus != null){
//			if(debug) System.err.println("update current focus:" + focus);
//			focusSelected = true;
//			focus.drag(mouseX, mouseY);
//			
//		} else {
//			if(debug) System.err.println("searching for new focus");
//			if(figure.mouseDragged(mouseX, mouseY))
//				focusSelected = true;
//			else
//				unRegisterFocus(focus);
//		}
//		redraw();
//		
//	}
	
	@Override
	public void mouseReleased(){
		if(debug)System.err.println("========= mouseReleased");
		//focusSelected = false;
		figure.mouseReleased();
	}
	
	@Override
	public void mouseMoved(){
		if(debug)System.err.println("========= mouseMoved: " + mouseX + ", " + mouseY);
		
		lastMouseX = mouseX;
		lastMouseY = mouseY;
		
		if(!figure.mouseOver(mouseX, mouseY, false))
			unRegisterMouseOver(mouseOver);
		redraw();
	}
	
	@Override
	public void mouseDragged(){
		if (debug) System.err.println("========= mouseDragged: " + mouseX + ", " + mouseY);
		
//		lastMouseX = mouseX;
//		lastMouseY = mouseY;
		
//		figure.mouseOver(mouseX, mouseY, false);
		figure.mouseDragged(mouseX, mouseY);
		redraw();
	}
			
	
	@Override
	public void mousePressed(){
		if (debug) System.err.println("=== FigurePApplet.mousePressed: " + mouseX + ", " + mouseY);
		lastMouseX = mouseX;
		lastMouseY = mouseY;
		unRegisterMouseOver(mouseOver);
		if(figure.mousePressed(mouseX, mouseY, mouseEvent)){
			focusSelected = true;
			if (debug) System.err.println(""+this.getClass()+" "+focusSelected);
		} else
			unRegisterFocus(focus);
		redraw(); 
	}
	
	public void setComputedValueChanged(){
		computedValueChanged = true;
		redraw();
	}

	
	// ---------------------
	
	// Temporary switch for all Processing methods that we use:
	// write to canvas when saving and to parent (PApplet) otherwise.
	// This gives the best quality on the screen.
	// Replace by two version of FigurePApplet:
	// - FigurePApplet (current one)
	// - FigurePAppletSave (the saving version)
	
	@Override
	public void line(float arg0, float arg1, float arg2, float arg3) {
		if(saveFigure)
			canvas.line(arg0, arg1, arg2, arg3);
		else
			super.line(arg0, arg1, arg2, arg3);
	}
	
	@Override
	public void rect(float arg0, float arg1, float arg2, float arg3) {
		if(saveFigure)
			canvas.rect(arg0, arg1, arg2, arg3);
		else
			super.rect(arg0, arg1, arg2, arg3);
	}
	@Override
	public void ellipse(float arg0, float arg1, float arg2, float arg3) {
		if(saveFigure)
			canvas.ellipse(arg0, arg1, arg2, arg3);
		else
			super.ellipse(arg0, arg1, arg2, arg3);
	}
	
	@Override
	public void rectMode(int arg0) {
		if(saveFigure)
			canvas.rectMode(arg0);
		else
			super.rectMode(arg0);
	}
	
	@Override
	public void ellipseMode(int arg0) {
		if(saveFigure)
			canvas.ellipseMode(arg0);
		else
			super.ellipseMode(arg0);
	}
	
	@Override
	public void fill(int arg0) {
		if(saveFigure)
			canvas.fill(arg0);
		else
			super.fill(arg0);
	}
	
	
	@Override public void stroke(int arg0) { 
		if(saveFigure)
			canvas.stroke(arg0);
		else
			super.stroke(arg0);
	}
	
	@Override
	public void strokeWeight(float arg0) {
		if(saveFigure)
			canvas.strokeWeight(arg0);
		else
			super.strokeWeight(arg0);
	}
	
	@Override
	public void textSize(float arg0) {
		if(saveFigure)
			canvas.textSize(arg0);
		else
			super.textSize(arg0);
	}
	
	@Override
	public void textAlign(int arg0, int arg1) {
		if(saveFigure)
			canvas.textAlign(arg0, arg1);
		else
			super.textAlign(arg0, arg1);
	}
	
	@Override
	public void textFont(PFont arg0){
		if(saveFigure)
			canvas.textFont(arg0);
			//return;
		else
			super.textFont(arg0);
	}
	
	@Override
	public void textFont(PFont arg0, float arg1){
		if(saveFigure)
			canvas.textFont(arg0, arg1);
			//return;
		else
			super.textFont(arg0, arg1);
	}
	@Override
	public float textWidth(String txt){
		if(saveFigure)
			return canvas.textWidth(txt);
		
		return super.textWidth(txt);
	}
	
	@Override
	public float textAscent(){
		if(saveFigure)
			return canvas.textAscent();
		
		return super.textAscent();
	}
	
	@Override
	public float textDescent(){
		if(saveFigure)
			return canvas.textDescent();
		
		return super.textDescent();
	}
	
	@Override
	public void text(String arg0, float arg1, float arg2) {
		if(saveFigure)
			canvas.text(arg0, arg1, arg2);
		else
			super.text(arg0, arg1, arg2);
	}
	
	@Override
	public void pushMatrix() {
		if(saveFigure)
			canvas.pushMatrix();
		else
			super.pushMatrix();
	}
	
	@Override
	public void popMatrix() {
		if(saveFigure)
			canvas.popMatrix();
		else
			super.popMatrix();
	}
	
	@Override
	public void rotate(float arg0) {
		if(saveFigure)
			canvas.rotate(arg0);
		else
			super.rotate(arg0);
	}
	
	@Override
	public void translate(float arg0, float arg1) {
		if(saveFigure)
			canvas.translate(arg0, arg1);
		else
			super.translate(arg0, arg1);
	}
	
	@Override
	public void scale(float arg0, float arg1) {
		if(saveFigure)
			canvas.scale(arg0, arg1);
		else
			super.scale(arg0, arg1);
	}
	
	@Override
	public void bezierVertex(float arg0, float arg1, float arg2, float arg3,
			float arg4, float arg5) {
		if(saveFigure)
			canvas.bezierVertex(arg0, arg1, arg2, arg3, arg4, arg5);
		else
			super.bezierVertex(arg0, arg1, arg2, arg3, arg4, arg5);
	}
	
	@Override
	public void vertex(float arg0, float arg1){
		if(saveFigure)
			canvas.vertex(arg0, arg1);
		else
			super.vertex(arg0, arg1);
	}
	
	@Override
	public void curveVertex(float arg0, float arg1){
		if(saveFigure)
			canvas.curveVertex(arg0, arg1);
		else
			super.curveVertex(arg0, arg1);
	}
	
	@Override
	public void noFill(){
		if(saveFigure)
			canvas.noFill();
		else
			super.noFill();
	}
	
	
	@Override
	public void arc(float arg0, float arg1, float arg2, float arg3, float arg4,
			float arg5) {
		if(saveFigure)
			canvas.arc(arg0, arg1, arg2, arg3, arg4, arg5);
		else
			super.arc(arg0, arg1, arg2, arg3, arg4, arg5);
	}
	
	@Override
	public void beginShape() {
		if(saveFigure)
			canvas.beginShape();
		else
			super.beginShape();
	}
	
	@Override
	public void beginShape(int arg0) {
		if(saveFigure)
			canvas.beginShape(arg0);
		else
			super.beginShape(arg0);
	}
	
	@Override
	public void endShape() {
		if(saveFigure)
			canvas.endShape();
		else
			super.endShape();
	}
	
	
	@Override
	public void endShape(int arg0 ) {
		if(saveFigure)
			canvas.endShape(arg0);
		else
			super.endShape(arg0);
	}
	
	public Image getImage() {return this.g.image;}
	
}

/***
//class ZoomEntry {
//	Figure figure;
//	float left;
//	float top;
//	float scale;
//
//	ZoomEntry(Figure f,  float l,  float t,float s){
//		figure = f;
//		left = l;
//		top = t;
//		scale = s;
//	}
//
//}

//int deltah = 0;
//
//if(false){ // !zoomStack.isEmpty()){
//	deltah = 20;
//	fill(255);
//	rect(left, top, left + rootWidth, top + deltah);
//	textAlign(CENTER, CENTER);
//	fill(0);
//	text("Zoom level = " + zoomStack.size(), left + rootWidth/2, top + deltah/2);
//}

//if(!zoomStack.isEmpty()){
//	rootFigure.draw(left, top);
//	float alpha = 0.6f +  0.1f*zoomStack.size();
//	if(alpha > 0.9)
//		alpha = 0.9f;
//	
//	fill(FigureLibrary.figureColor(192,192,192,alpha));
//	stroke(192,192,192);
//	rect(left, top, rootWidth, rootHeight);
//} else

if(controlChanged){
	rootFigure.bbox();
	rootWidth = rootFigure.width;
	rootHeight = rootFigure.height;
	controlChanged = false;
}
rootFigure.draw(left,top);
//if(!zoomStack.isEmpty()){
//	float rescale = (zoomingIn) ? (scale - 0.5f) / scale : scale / (scale - 0.5f) ;
//	float shift = zoomingIn ? 0.2f : 0.6f;
//	float l = max(left + focusLeft * rescale - shift *(scale - 1) *figure.width, 0);
//	float t = max(top + focusTop * rescale - shift * (scale - 1) *figure.height, 0);
//
//	System.err.printf("focusLeft=%d, focusTop=%d, scale=%f, l=%f, t=%f\n", 
//						focusLeft, focusTop, scale, l, t);
//	pushMatrix();
//	scale(scale);
//	figure.draw(l,t) ;

**/


//if(!zoomStack.isEmpty()){
//popMatrix();
//}


//@Override
//public void mousePressed(){
//	if(debug)System.err.println("mousePressed: " + mouseX + ", " + mouseY);
//	lastMouseX = mouseX;
//	lastMouseY = mouseY;
//	if(figure.mousePressed(round(mouseX/scale), round(mouseY/scale))){
//		focusSelected = true;
//		if(keyPressed && key == CODED && keyCode == SHIFT){
//			if(mouseButton == LEFT){
//				if(debug)System.err.println("mousePressed: zoomin, focus=" + focus);
//				if(focus != figure){
//					zoomStack.push(new ZoomEntry(figure, figure.left, figure.top, scale));
//					figure = focus;
//					focusLeft = round(figure.left);
//					focusTop = round(figure.top);
//					scale += 0.5;	
//					zoomingIn = true;
//					}
//			} else if(mouseButton == RIGHT){
//				if(debug)System.err.println("mousePressed: zoomout");
//				if(!zoomStack.isEmpty()){
//					ZoomEntry ze = zoomStack.pop();
//					focus = figure = ze.figure;
//					focusLeft = round(ze.left);
//					focusTop = round(ze.top);
//					scale = ze.scale;
//					zoomingIn = false;
//				}
//			}
//		}
//	} else
//		unRegisterMouseOver(focus);
//	
//	redraw(); 
//}