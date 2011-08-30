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
package org.rascalmpl.library.vis.figure;



import static org.rascalmpl.library.vis.properties.Properties.ASPECT_RATIO;
import static org.rascalmpl.library.vis.properties.Properties.FILL_COLOR;
import static org.rascalmpl.library.vis.properties.Properties.FONT;
import static org.rascalmpl.library.vis.properties.Properties.FONT_COLOR;
import static org.rascalmpl.library.vis.properties.Properties.FONT_SIZE;
import static org.rascalmpl.library.vis.properties.Properties.HALIGN;
import static org.rascalmpl.library.vis.properties.Properties.HGROW;
import static org.rascalmpl.library.vis.properties.Properties.HRESIZABLE;
import static org.rascalmpl.library.vis.properties.Properties.HSHADOWPOS;
import static org.rascalmpl.library.vis.properties.Properties.LINE_COLOR;
import static org.rascalmpl.library.vis.properties.Properties.LINE_STYLE;
import static org.rascalmpl.library.vis.properties.Properties.LINE_WIDTH;
import static org.rascalmpl.library.vis.properties.Properties.MOUSE_CLICK;
import static org.rascalmpl.library.vis.properties.Properties.ON_KEY;
import static org.rascalmpl.library.vis.properties.Properties.ON_MOUSEMOVE;
import static org.rascalmpl.library.vis.properties.Properties.SHADOW;
import static org.rascalmpl.library.vis.properties.Properties.SHADOW_COLOR;
import static org.rascalmpl.library.vis.properties.Properties.VALIGN;
import static org.rascalmpl.library.vis.properties.Properties.VGROW;
import static org.rascalmpl.library.vis.properties.Properties.VRESIZABLE;
import static org.rascalmpl.library.vis.properties.Properties.VSHADOWPOS;
import static org.rascalmpl.library.vis.properties.TwoDProperties.ALIGN;
import static org.rascalmpl.library.vis.properties.TwoDProperties.RESIZABLE;
import static org.rascalmpl.library.vis.properties.TwoDProperties.SIZE;
import static org.rascalmpl.library.vis.util.vector.Dimension.HOR_VER;
import static org.rascalmpl.library.vis.util.vector.Dimension.X;
import static org.rascalmpl.library.vis.util.vector.Dimension.Y;

import java.util.List;

import org.eclipse.imp.pdb.facts.IBool;
import org.eclipse.imp.pdb.facts.IMap;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;
import org.rascalmpl.library.vis.figure.interaction.MouseOver;
import org.rascalmpl.library.vis.graphics.FontStyle;
import org.rascalmpl.library.vis.graphics.GraphicsContext;
import org.rascalmpl.library.vis.properties.Properties;
import org.rascalmpl.library.vis.properties.PropertyManager;
import org.rascalmpl.library.vis.swt.ICallbackEnv;
import org.rascalmpl.library.vis.swt.IFigureConstructionEnv;
import org.rascalmpl.library.vis.swt.SWTFontsAndColors;
import org.rascalmpl.library.vis.swt.applet.IHasSWTElement;
import org.rascalmpl.library.vis.util.FigureMath;
import org.rascalmpl.library.vis.util.NameResolver;
import org.rascalmpl.library.vis.util.vector.BoundingBox;
import org.rascalmpl.library.vis.util.vector.Coordinate;
import org.rascalmpl.library.vis.util.vector.Dimension;
import org.rascalmpl.library.vis.util.vector.Rectangle;
import org.rascalmpl.library.vis.util.vector.TransformMatrix;
import org.rascalmpl.library.vis.util.vector.TwoDimensional;


/**
 * Figures are the foundation of Rascal visualization. They are based on a
 * bounding box + anchor model. The bounding box defines the maximal dimensions
 * of the element. The anchor defines its alignment properties.
 * 
 * Each figure has an associated property manager whose values can be accessed
 * via this class.
 * 
 * @author paulk
 */

public abstract class Figure implements Comparable<Figure> {
	
	public static enum ResizeMode{
		RESIZE, ZOOM;
	}
	
	public static enum RecomputeStatus{
		DID_NOT_CHANGE,
		CHANGED;
	}
	public static final Figure[] childless = new Figure[0];
	public static final Type[] noTypes = new Type[0];
	public static final IValue[] noArgs = new IValue[0];
	
	public static int sequencer = 0; // to impose arbitrary ordering on figures
	@SuppressWarnings("unused")
	private static final boolean debug = false;
	
	public int sequenceNr;
	public RecomputeStatus status;
	public Figure[] children;

	public boolean mouseOver; // set externally, only set when relevant (see MouseAndKeyboardHandler)
	public PropertyManager prop;
	public BoundingBox minSize;
	public BoundingBox size;
	public Coordinate location; // the location of the left, top corner of this figure the global coordinate systenm
	public TwoDimensional<Boolean> resizable;

	public Figure(PropertyManager properties) {
		this.prop = properties;
		minSize = new BoundingBox();
		size = new BoundingBox();
		location = new Coordinate();
		resizable = new TwoDimensional<Boolean>(true, true);
		sequenceNr = sequencer;
		sequencer++;
	}
	
	// init, compute registerNames, registerValues, bbox
	/* First phase:
	 * on down: 
	 * initialize (computefigure, register controls and other stuff) *
	 * setVisibleChildren *
	 * registerNames
	 * on up:
	 * register measures
	 * compute min size *
	 * finalize *
	 * 
	 * (*) : client code
	 * 
	 * 
	 */
	
	/**
	 * 
	 * @param env		the callback environment for computing stuff when initializing
	 * @param resolver  the name resolver
	 * @param swtSeen TODO
	 * @param visible TODO
	 * @param overlaps  the current set of overlaps (figure not abiding to strict inclusive layout), add overlapping figures here
	 * @param zparent   the parent in the zorder tree, this is a partial view of the figure tree describing only the swt elements and hence their z order
	 * @return TODO
	 */
	public final boolean init(IFigureConstructionEnv env,NameResolver resolver, MouseOver mparent, boolean swtSeen, boolean visible){
		prop.registerMeasures(resolver);
		
		resolver.register(this);
		resizable.set(prop.getBool(HRESIZABLE), prop.getBool(VRESIZABLE));
		initElem(env, mparent, swtSeen, visible, resolver);
		swtSeen = initChildren(env, resolver, mparent, swtSeen, visible);
		swtSeen = swtSeen || containsSWTElement();
		computeMinSize();
		adjustMinSize();
		finalize(true);
		return swtSeen;
	}

	public boolean containsSWTElement() {
		return false;
	}

	public boolean initChildren(IFigureConstructionEnv env,
			NameResolver resolver, MouseOver mparent, boolean swtSeen, boolean visible) {
		boolean swtSeenResult = false;
		for(int i = 0; i < children.length ; i++){
			boolean here =  children[i].init(env, resolver,mparent, swtSeen, visible);
			swtSeenResult = swtSeenResult || here;
		}
		return swtSeenResult;
	}

	public void initElem(IFigureConstructionEnv env, MouseOver mparent, boolean swtSeen, boolean visible, NameResolver resolver){}
	
	public abstract void computeMinSize() ;
	
	public void finalize(boolean needsRecompute){}
	
	// resizephase
	/* 
	 * on down: 
	 * getTransformation *
	 * resize * (setting the globallocation of the children to their local location, setting their size to their desired size) 
	 * register the zorder elements *
	 * set the global location of children (transform globallocation)
	 * on up:
	 * on resizeup * (do some stuff if the clients wants to)
	 * 
	 * (*) : client code
	 */
	public final void resize(Rectangle view,TransformMatrix transform){
		adjustSizeAndLocation();
		resizeElement(view);
		resizeChildren(view, transform);
		onResizeUp();
	}

	public void resizeChildren(Rectangle view, TransformMatrix transform) {
		for(Figure child : children){
			child.location.add(location);
			child.resize(view,transform);
		}
	}
	
	public abstract void resizeElement(Rectangle view) ;

	public void applyTransformation(TransformMatrix transform) {}
	public void reverseTransformation(TransformMatrix transform) {}
	public void onResizeUp() { }

	public final void draw(Coordinate zoom, GraphicsContext gc,Rectangle part, List<IHasSWTElement> visibleSWTElements) {
		// TODO: iets met rotate en transformaties
//		Coordinate offset = new Coordinate();
//		double oldZoomX, oldZoomY;
//		oldZoomX = zoom.getX();
//		oldZoomY = zoom.getY();
//		for(Dimension d : HOR_VER){
//			if(!prop.get2DBool(d, ZOOMABLE)){
//				offset.set(d, size.get(d) * (zoom.get(d) - 1.0) * prop.get2DReal(d, ALIGN) );
//				zoom.set(d, 1.0);
//			}
//		}
//		if(zoom.getX() != zoom.getY() && prop.isSet(ASPECT_RATIO)){
//			double minZoom = Math.min(zoom.getX(),zoom.getY());
//			zoom.set(minZoom,minZoom);
//		}
//		gc.translate(offset.getX(), offset.getY());
		
		beforeDraw(zoom);
		applyProperties(gc);
		drawElement(gc, visibleSWTElements);
		drawChildren(zoom, gc, part, visibleSWTElements);
		// gc.translate(-offset.getX(), -offset.getY());
		//zoom.set(oldZoomX, oldZoomY);
	}

	private void drawChildren(Coordinate zoom, GraphicsContext gc,
			Rectangle part, List<IHasSWTElement> visibleSWTElements) {
		for(Figure f : children){
			if(f.overlapsWith(part)){
				Rectangle npart = f.isContainedIn(part) ? null : part;
				f.draw(zoom, gc, npart, visibleSWTElements);
			}
		}
	}
	

	public void beforeDraw(Coordinate zoom) {} 

	public void drawElement(GraphicsContext gc, List<IHasSWTElement> visibleSWTElements){}
	

	public boolean widthDependsOnHeight(){
		return false;
	}

	public Dimension getMajorDimension(){
		return X;
	}
	
	/**
	 * Give a figure the opportunity to remove allocated components, etc.
	 */
	public void destroy(IFigureConstructionEnv env) {
		
		for(Figure child : children){
			child.destroy(env);
		}
		destroyElement(env);
	}
	
	public void destroyElement(IFigureConstructionEnv env) { }
	
	public void hide(IFigureConstructionEnv env) {
		for(Figure child : children){
			child.hide(env);
		}
		hideElement(env);
	}
	
	public void hideElement(IFigureConstructionEnv env) {
		destroyElement(env);
	}


	
	public void getFiguresUnderMouse(Coordinate c,List<Figure> result){
		if(!mouseInside(c)){
			return;
		}
		for(Figure child : children){
			child.getFiguresUnderMouse(c, result); // TODO: overlap
		}
		if(handlesInput()){
			result.add(this);
		}
	}

	public boolean handlesInput(){
		return prop.isSet(ON_KEY) || prop.isSet(ON_MOUSEMOVE) || prop.isSet(MOUSE_CLICK);
	}
	

	private void adjustMinSize() {
		if(prop.isSet(ASPECT_RATIO)){
			double ar = prop.getReal(ASPECT_RATIO);
			minSize.setMax(X, minSize.getY() * ar);
			minSize.setMax(Y, minSize.getX() / ar);
		}
		for(Dimension d : HOR_VER){
			if(!prop.get2DBool(d, RESIZABLE)){
				minSize.setMax(d,prop.get2DReal(d, SIZE));
			}
		}
	}

	public void adjustSizeAndLocation(){
		for(Dimension d : HOR_VER){
			if(!resizable.get(d)){
				location.add(d, (size.get(d) - minSize.get(d)) * prop.get2DReal(d, ALIGN) );
				size.set(d,minSize.get(d));
			}
		}
		if(prop.isSet(ASPECT_RATIO)){
			double ar = prop.getReal(Properties.ASPECT_RATIO);
			double car = size.getX() / size.getY();
			if(car > ar) { // too wide
				double newWidth = size.getY() * ar;
				location.add(X, (size.getX() - newWidth) * prop.getReal(HALIGN));
				size.setX(newWidth);
			} else if( car < ar){ // too tall
				double newHeight = size.getX() / ar;
				location.add(Y, (size.getY() - newHeight) * prop.getReal(VALIGN));
				size.setY(newHeight);
			}
		}
	}
	

	/*
	 * Compare two Figures using an arbitrary ordering
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Figure o) {
		return sequenceNr - o.sequenceNr;
	}

	public BoundingBox getMinViewingSize() {
		BoundingBox minViewSize = new BoundingBox();
		minViewSize.set(minSize.getX() / prop.getReal(HGROW), minSize.getY() /prop.getReal(VGROW) );
		return minViewSize;
	}
	
	public boolean overlapsWith(Rectangle r){
		return r == null || r.overlapsWith(location, size);
	}
	
	public boolean isContainedIn(Rectangle r){
		return  r == null || r.contains(location,size);
	}
	
	public Rectangle getRectangle(){
		return new Rectangle(location, size);
	}
	
	public Rectangle getRectangleIncludingOuterLines(){
		double hlw = 0.5 * prop.getReal(LINE_WIDTH);
		return new Rectangle(location.getX() - hlw, location.getY() -hlw , size.getX() + 2*hlw, size.getY() + 2*hlw);
	}
	
	public boolean executeKeyHandlers(ICallbackEnv env,IValue keySym, IBool keyDown, IMap modifiers){
		Type[] types = {keySym.getType(),keyDown.getType(),modifiers.getType()};
		IValue[] args = {keySym,keyDown,modifiers};
		return executeHandlerProperty(env,Properties.ON_KEY,types,args);
	}

	public void executeMouseMoveHandlers(ICallbackEnv env, IBool enter) {
		Type[] types = {enter.getType()};
		IValue[] args = {enter};
		executeHandlerProperty(env,Properties.ON_MOUSEMOVE,types,args);
	}


	public boolean mouseInside(Coordinate c) {
		return c.getX() >= location.getX() && c.getX() <= location.getX() + size.getX()
				&& c.getY() >= location.getY() && c.getY() <= location.getY()+ size.getY();
	}

	public boolean executeOnClick(ICallbackEnv env) {
		return executeHandlerProperty(env, Properties.MOUSE_CLICK, noTypes, noArgs);
	}
	
	// returns if the event is captured (i.e. not propagated further)
	public boolean executeHandlerProperty(ICallbackEnv env, Properties property, Type[] types, IValue[] args){
		if(prop.isSet(property)){
			IValue v = prop.executeHandler(env, property, types, args);
			if(v instanceof IBool){
				return ((IBool)v).getValue();
			} else {
				return false;
			}
		} else {
			return false; // event not consumed, so not captured
		}
	}
	

	public void applyProperties(GraphicsContext gc) {
		gc.fill(prop.getColor(FILL_COLOR));
		gc.stroke(prop.getColor(LINE_COLOR));
		gc.strokeWeight(prop.getReal(LINE_WIDTH));
		gc.strokeStyle(prop.getStr(LINE_STYLE));
		gc.textSize(prop.getInt(FONT_SIZE));
		boolean shadow = prop.getBool(SHADOW);
		gc.setShadow(shadow);
		if (shadow) {
			gc.setShadowColor(prop.getColor(SHADOW_COLOR));
			gc.setShadowLeft(prop.getReal(HSHADOWPOS));
			gc.setShadowTop(prop.getReal(VSHADOWPOS));
		}
		gc.setFont(prop.getStr(FONT), prop.getInt(FONT_SIZE), FontStyle.NORMAL);
		gc.font(prop.getColor(FONT_COLOR));
	}
	
	public double getTextAscent(){
		return SWTFontsAndColors.textAscent(
				prop.getStr(FONT),
				prop.getInt(FONT_SIZE));
	}
	
	public double getTextDescent(){
		return SWTFontsAndColors.textDescent(
				prop.getStr(FONT),
				prop.getInt(FONT_SIZE));
	}
	
	public double getTextHeight(){
		return getTextAscent() + getTextDescent();
	}
	
	public double getTextWidth(String s){
		return SWTFontsAndColors.textWidth(s, 
				prop.getStr(FONT),
				prop.getInt(FONT_SIZE));
	}
	
	
	/**
	 * Draw an arrow from an external position (fromX, fromY) directed to the
	 * center (X,Y) of the current figure. The arrow is placed at At the
	 * intersection with the border of the current figure and it is
	 * appropriately rotated.
	 * 
	 * @param left
	 *            X of left corner
	 * @param top
	 *            Y of left corner
	 * @param X
	 *            X of center of current figure
	 * @param Y
	 *            Y of center of current figure
	 * @param fromX
	 *            X of center of figure from which connection is to be drawn
	 * @param fromY
	 *            Y of center of figure from which connection is to be drawn
	 * @param toArrow
	 *            the figure to be used as arrow
	 */
	public void connectArrowFrom(double left, double top, double X, double Y,
			double fromX, double fromY, Figure toArrow, GraphicsContext gc, List<IHasSWTElement> visibleSWTElements ) {
		if (fromX == X)
			fromX += 0.00001;
		double s = (fromY - Y) / (fromX - X);

		double theta = Math.atan(s);
		if (theta < 0) {
			if (fromX < X)
				theta += Math.PI;
		} else {
			if (fromX < X)
				theta += Math.PI;
		}
		double IX;
		double IY;

		double h2 = minSize.getY() / 2;
		double w2 = minSize.getX() / 2;

		if ((-h2 <= s * w2) && (s * w2 <= h2)) {
			if (fromX > X) { // right
				IX = X + w2;
				IY = Y + s * w2;
			} else { // left
				IX = X - w2;
				IY = Y - s * w2;
			}
		} else {
			if (fromY > Y) { // bottom
				IX = X + h2 / s;
				IY = Y + h2;
			} else { // top
				IX = X - h2 / s;
				IY = Y - h2;
			}
		}
		/*
		 * //fpa.line(left + fromX, top + fromY, left + IX, top + IY);
		 */
		if (toArrow != null) {
			gc.pushMatrix();
			gc.translate(left + IX, top + IY);
			gc.rotate(FigureMath.radians(-90) + theta);
			//toArrow.drawElemen(gc,visibleSWTElements); TODO: fixme!!!
			gc.popMatrix();
		}
	}
	
	
}
