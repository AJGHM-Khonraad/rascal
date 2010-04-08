package org.rascalmpl.library.viz.Figure;

import java.util.ArrayList;

import org.eclipse.imp.pdb.facts.IList;
import org.rascalmpl.interpreter.IEvaluatorContext;

import processing.core.PApplet;

/**
 * A TreeNode is created for each "node" constructor that occurs in the tree.
 * 
 * @author paulk
 *
 */
public class TreeNode extends Figure {
	
	Figure figureNode;
	private ArrayList<TreeNode> children;
	private ArrayList<PropertyManager> edgeProperties;
	private static boolean debug = true;
	private boolean visible = true;
	
	public TreeNode(FigurePApplet vlp, PropertyManager inheritedProps, IList props,
			Figure ve, IEvaluatorContext ctx) {
		super(vlp, inheritedProps, props, ctx);
		figureNode = ve;
		children = new ArrayList<TreeNode>();
		edgeProperties = new ArrayList<PropertyManager>();
	}
	
	public void addChild(PropertyManager inheritedProps, IList props,
			TreeNode toNode, IEvaluatorContext ctx) {
		children.add(toNode);
		edgeProperties.add(new PropertyManager(null, inheritedProps, props, ctx));
	}
	
	
	float shapeTree(float left, float top, TreeNodeRaster raster) {
		this.left = left;
		this.top = top;
		figureNode.bbox(left, top);
		float hgap = getHGapProperty();
		float vgap = getVGapProperty();
		float position = left + figureNode.width/2; // x position of center of node!
		position = raster.leftMostPosition(position, top, figureNode.width, figureNode.height, hgap);
		
		int nChildren = children.size();
		height = figureNode.height;
		width = figureNode.width;
		float heightChildren = 0;
		if(nChildren > 0){
			for(TreeNode child : children){
				child.figureNode.bbox();
				heightChildren = max(heightChildren, child.height);
			}
			height += heightChildren + vgap;
			if(nChildren > 1){
				width = (children.get(0).figureNode.width + children.get(nChildren-1).figureNode.width)/2 +
				        (nChildren-1) * hgap;
				for(int i = 1; i < nChildren - 1; i++){
					width += children.get(i).figureNode.width;
				}
			} else {
				width = 0;
			}
			float branchPosition = position - width/2;
			
			float leftPosition = children.get(0).shapeTree(branchPosition, top + figureNode.height + hgap, raster);
			
			float rightPosition = leftPosition;
			
			width = children.get(0).width;
			height = children.get(0).height;
			for(int i = 1; i < nChildren; i++){
				branchPosition += hgap + (children.get(i-1).figureNode.width + 
						                  children.get(i).figureNode.width)/2;
				rightPosition = children.get(i).shapeTree(branchPosition, top + figureNode.height + hgap, raster);
				width += hgap + children.get(i).width;
				height = max(height, children.get(i).height);
			}
			height += vgap + figureNode.height;
			
			position = (leftPosition + rightPosition)/2;
		}
	
		raster.add(position, top, figureNode.width, figureNode.height);
		this.left = figureNode.left = position - figureNode.width/2;
		return position;
	}
	
	@Override
	void bbox() {
		// TODO Auto-generated method stub
	}

	@Override
	void draw(float left, float top) {
		boolean squareStyle = true;
		
		this.left = left;
		this.top = top;
		left += leftDragged;
		top += topDragged;
		applyProperties();
		figureNode.draw(left, top);
		
		int n = children.size();
		
		if(n > 0 && visible){
			float nodeBottomX = left + figureNode.width/2;
			float nodeBottomY = top + figureNode.height;
			float vgap = getVGapProperty();
			final float childTop = nodeBottomY + vgap;
			float horLineY = nodeBottomY + vgap/2;
		
			if(squareStyle){
				
				vlp.line(nodeBottomX, nodeBottomY, nodeBottomX, horLineY);
				
				if(n > 1){
					Figure leftFig = children.get(0).figureNode;
					Figure rightFig = children.get(n-1).figureNode;
					vlp.line(leftFig.getCurrentLeft() + leftFig.width/2, horLineY, rightFig.getCurrentLeft() + rightFig.width/2, horLineY);
				}
			
			// TODO line style!
		
				for(TreeNode child : children){
					if(!squareStyle)
						vlp.line(nodeBottomX, nodeBottomY, child.figureNode.left + child.figureNode.width/2, childTop);
					float midChild = child.figureNode.left + child.figureNode.width/2;
					
					vlp.line(midChild, topDragged + child.top, midChild, horLineY);
					child.drag(child.left + leftDragged, child.top + topDragged);
					child.draw(child.left, child.top);
				}
			}

		}
	}
	
	@Override
	public boolean mouseInside(int mousex, int mousey){
		float l = left + leftDragged + figureNode.width/2 - width/2;
		System.err.printf("TreeNode.mouseInside(%d,%d)\n", mousex, mousey);
		System.err.printf("left = %f, leftDragged = %f, top = %f, topDragged = %f\n", left, leftDragged, top, topDragged);
		return mousex > l && 
		        mousex < l + width && 
		        mousey > top + topDragged && 
		        mousey < top + topDragged  + height;
	}
	
	@Override
	public void drawFocus(){
		vlp.stroke(255, 0,0);
		vlp.noFill();
		vlp.rect(left + leftDragged + figureNode.width/2 - width/2, top + topDragged, width, height);
	}
	
	@Override
	public boolean mouseOver(int mousex, int mousey){
		if(debug)System.err.printf("TreeNode.mouseover: %d, %d\n", mousex, mousey);
		if(debug)System.err.printf("TreeNode.mouseover: left=%f, top=%f\n", left, top);
		if(figureNode.mouseOver(mousex, mousey))
			return true;
		for(TreeNode child : children)
			if(child.mouseOver(mousex, mousey))
				return true;
		return false;
	}
	
	@Override
	public boolean mousePressed(int mousex, int mousey){
		for(TreeNode child : children)
			if(child.mousePressed(mousex, mousey))
				return true;
		if(mouseInside(mousex, mousey)){
			vlp.registerFocus(this);
			if(vlp.mouseButton == vlp.RIGHT)
				visible = false;
			else
				visible = true;
			return true;
		}
		return false;
	}
	
	@Override
	public boolean mouseDragged(int mousex, int mousey){
		if(debug)System.err.printf("TreeNode.mouseDragged: %d, %d\n", mousex, mousey);
		for(TreeNode child : children)
			if(child.mouseDragged(mousex, mousey))
				return true;
		if(debug)System.err.println("TreeNode.mouseDragged: children do not match\n");
		if(mouseInside(mousex, mousey)){
			vlp.registerFocus(this);
			drag(mousex, mousey);
			return true;
		}
		return false;
	}
}
