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

package org.rascalmpl.library.vis.figure.tree;

import java.util.ArrayList;
import java.util.Vector;

import org.eclipse.imp.pdb.facts.IList;
import org.rascalmpl.interpreter.IEvaluatorContext;
import org.rascalmpl.library.vis.figure.Figure;
import org.rascalmpl.library.vis.graphics.GraphicsContext;
import org.rascalmpl.library.vis.properties.PropertyManager;
import org.rascalmpl.library.vis.swt.IFigureConstructionEnv;
import org.rascalmpl.library.vis.util.Coordinate;
import org.rascalmpl.library.vis.util.NameResolver;

/**
 * A TreeNode is created for each "node" constructor that occurs in a Tree.
 * After creation, shapeTree is called to determine position and dimensions.
 * 
 * @author paulk
 *
 */
public class TreeNode extends Figure {
	
	Figure rootFigure;                        // Figure associated with this TreeNode
	private ArrayList<TreeNode> children;     // Child nodes
	private ArrayList<PropertyManager> edgeProperties;
	private double[] childRoot;                // Root position of each child
	private double rootPosition;               // Root position of this TreeNode (= middle of rootFigure)
	private static boolean debug = false;
	
	public TreeNode(IFigureConstructionEnv fpa, PropertyManager properties, Figure fig) {
		super(properties);
		rootFigure = fig;
		children = new ArrayList<TreeNode>();
		edgeProperties = new ArrayList<PropertyManager>();
	}
	
	public void addChild(PropertyManager inheritedProps, IList props,
			TreeNode toNode, IEvaluatorContext ctx) {
		children.add(toNode);
		//TODO
		edgeProperties.add(new PropertyManager(null, inheritedProps, props));
	}
	
	/*
	 * Distance between rootPosition and leftmost border of this node
	 */
	
	public double leftExtent(){
		return rootPosition;
	}
	
	/*
	 * Distance between rootPosition and rightmost border of this node
	 */
	
	public double rightExtent(){
		return minSize.getWidth() - rootPosition;
	}
	
	/**
	 * shapeTree places the current subtree (rooted in this TreeNode)  on the raster
	 * 
	 * @param rootMidX	x coordinate of center of the root figure
	 * @param rootTop	y coordinate of getTop() of root figure
	 * @param raster	NodeRaster to be used
	 * @return the x position of the center of the root
	 */
	double shapeTree(double rootMidX, double rootTop, TreeNodeRaster raster) {
        String id = rootFigure.getIdProperty();
		if(debug)System.err.printf("shapeTree: id=%s, rootMidX=%f, rootTop=%f\n", id, rootMidX, rootTop);
		rootFigure.bbox();
		double hgap = getHGapProperty();
		double vgap = getVGapProperty();
		
		// Initial placement of figure of this TreeNode
		double position = raster.leftMostPosition(rootMidX, rootTop, rootFigure.minSize.getWidth(), rootFigure.minSize.getHeight(), hgap);
		rootPosition = position;
		minSize.setHeight(rootFigure.minSize.getHeight());
		minSize.setWidth(rootFigure.minSize.getWidth());
		
		int nChildren = children.size();
		
		if(nChildren == 0){
			rootPosition = minSize.getWidth()/2;
		} else {
			for(TreeNode child : children){
				child.rootFigure.bbox();
			}
			
			// Compute position of leftmost child
			
			double branchPosition = position;
			
			if(nChildren > 1){
				double widthDirectChildren = (children.get(0).rootFigure.minSize.getWidth() + children.get(nChildren-1).rootFigure.minSize.getWidth())/2 +
				                            (nChildren-1) * hgap;
				for(int i = 1; i < nChildren - 1; i++){
					widthDirectChildren += children.get(i).rootFigure.minSize.getWidth();
				}
				branchPosition = position - widthDirectChildren/2; 		// Position of leftmost child
			}
			
			double childTop = rootTop + rootFigure.minSize.getHeight() + vgap;         // getTop() of all children
			 
			childRoot = new double[nChildren];
			
			// Place leftmost child
			double leftPosition = childRoot[0] = children.get(0).shapeTree(branchPosition, childTop, raster);
			double rightPosition = leftPosition;
			double heightChildren = children.get(0).minSize.getHeight();
			double rightExtentChildren = leftPosition + children.get(0).rightExtent();
			
			for(int i = 1; i < nChildren; i++){
				TreeNode childi = children.get(i);
				branchPosition += hgap + (children.get(i-1).rootFigure.minSize.getWidth() + childi.rootFigure.minSize.getWidth())/2;
				rightPosition = childi.shapeTree(branchPosition, childTop, raster);
				rightExtentChildren = Math.max(rightExtentChildren, rightPosition + childi.rightExtent());
				heightChildren = Math.max(heightChildren, childi.minSize.getHeight());
				childRoot[i] = rightPosition;
			}
			position = (leftPosition + rightPosition)/2;
			minSize.setHeight(minSize.getHeight() + vgap + heightChildren);
			minSize.setWidth(Math.max(rootFigure.minSize.getWidth(), rightExtentChildren - (leftPosition - children.get(0).rootPosition)));

			// Make child positions and rootPosition relative to this parent
			// TODO: fixme!
			//setLeft(leftPosition - children.get(0).rootPosition);
			
			for(int i = 0; i < nChildren; i++){
				childRoot[i] -= getLeft();
			}
			rootPosition = position - getLeft();
		}
	
		// After placing all children, we can finally add the current root figure to the raster.
		raster.add(position, rootTop, rootFigure.minSize.getWidth(), rootFigure.minSize.getHeight());
		if(debug)System.err.printf("shapeTree(%s, %f, %f) => position=%f, getLeft()=%f, getTop()=%f, width=%f, height=%f\n", id, rootMidX, rootTop, position, getLeft(), getTop(), minSize.getWidth(), minSize.getHeight());
		return position;
	}
	
	@Override
	public
	void bbox() {
		setNonResizable();
	}
	
	@Override
	public
	void draw(GraphicsContext gc){
		
		String id = rootFigure.getIdProperty();
		int nChildren = children.size();
		
		applyProperties(gc);
		
		double positionRoot = getLeft() + rootPosition;
		double leftRootFig = positionRoot - rootFigure.minSize.getWidth()/2;
		
		if(debug)System.err.printf("draw %s, %f, %f, rootFig at %f, %f\n", id, getLeft(), getTop(), leftRootFig, getTop());
		
		// Draw the root figure
		rootFigure.draw(gc);
		
		if(nChildren == 0)
			return;
		
		double bottomRootFig = getTop() + rootFigure.minSize.getHeight();
		double vgap          = getVGapProperty();
		double childTop      = bottomRootFig + vgap; 
		double horLine       = bottomRootFig + vgap/2;
		
		// Vertical line from bottom of root figure to horizontal line
		gc.line(positionRoot, bottomRootFig, positionRoot, horLine);
		
		// Horizontal line connecting all the children
		if(nChildren > 1)
			gc.line(getLeft() + childRoot[0], horLine, getLeft() + childRoot[nChildren-1], horLine);
	
		// TODO line style!
		
		for(int i = 0; i < nChildren; i++){
			TreeNode child = children.get(i);
			double positionChild = getLeft() + childRoot[i];
			if(debug)System.err.printf("draw %s, child %d at posChild=%f, widthChild=%f, posRoot=%f\n", id, i, positionChild, child.minSize.getWidth(), child.rootPosition, childTop);

			// Vertical line from horizontal line to getTop() of this child
			gc.line(positionChild, horLine, positionChild, childTop);
			child.draw(gc);
		}
		
	}
	
	
	@Override
	public boolean mouseInside(double mousex, double mousey){
		return rootFigure.mouseInside(mousex, mousey);
	}

	public boolean getFiguresUnderMouse(Coordinate c,Vector<Figure> result){
		boolean ret = false;
		for(TreeNode child : children){
			if(child.getFiguresUnderMouse(c, result)){
				ret = true;
				break;
			}
		}
		if(rootFigure.getFiguresUnderMouse(c, result)) {
			ret = true;
		}
		return ret;
	}
	
	public void registerNames(NameResolver resolver){
		super.registerNames(resolver);
		for(int i = children.size()-1 ; i >= 0 ; i--){
			children.get(i).registerNames(resolver);
		}
	}

	@Override
	public void layout() {
		size.set(minSize);
		for(int i = children.size()-1 ; i >= 0 ; i--){
			children.get(i).setToMinSize();
			children.get(i).layout();
		}
		
	}
}
