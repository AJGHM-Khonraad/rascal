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
package org.rascalmpl.library.vis.tree;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.rascalmpl.interpreter.IEvaluatorContext;
import org.rascalmpl.interpreter.utils.RuntimeExceptionFactory;
import org.rascalmpl.library.vis.Figure;
import org.rascalmpl.library.vis.FigureFactory;
import org.rascalmpl.library.vis.IFigureApplet;
import org.rascalmpl.library.vis.properties.PropertyManager;
import org.rascalmpl.library.vis.util.Coordinate;
import org.rascalmpl.library.vis.util.NameResolver;
import org.rascalmpl.values.ValueFactoryFactory;

/**
 * Tree map layout. Given a tree consisting of a list of nodes and edges, place them in a space conserving layout.
 * 
 * @author paulk
 *
 */
public class TreeMap extends Figure {
	protected HashMap<String,TreeMapNode> nodeMap;
	private HashSet<TreeMapNode> hasParent;
	TreeMapNode root = null;
	
	public TreeMap(IFigureApplet fpa, PropertyManager properties, IList nodes, IList edges, IEvaluatorContext ctx) {
		super(fpa, properties);		
		nodeMap = new HashMap<String,TreeMapNode>();
		hasParent = new HashSet<TreeMapNode>();
		
		// Construct TreeMapNodes
		for(IValue v : nodes){
			IConstructor c = (IConstructor) v;
			Figure fig = FigureFactory.make(fpa, c, properties, null, ctx);
			String name = fig.getIdProperty();
			if(name.length() == 0)
				throw RuntimeExceptionFactory.figureException("TreeMap: Missing id property in node", v, ctx.getCurrentAST(), ctx.getStackTrace());
			TreeMapNode tn = new TreeMapNode(fpa, this, properties, fig);
			nodeMap.put(name, tn);
		}
		
		// Construct Edges
		IValueFactory vf = ValueFactoryFactory.getValueFactory();
		IList emptyList = vf.list();

		for(IValue v : edges){
			IConstructor c = (IConstructor) v;
			int iFrom = 0;
			int iTo = 1;
			IList edgeProperties = c.arity() == 3 ?  (IList) c.get(2) : emptyList;
			String from = ((IString)c.get(iFrom)).getValue();

			TreeMapNode fromNode = nodeMap.get(from);
			if(fromNode == null)
				throw RuntimeExceptionFactory.figureException("TreeMap: edge uses non-existing node id " + from, v, ctx.getCurrentAST(), ctx.getStackTrace());
			String to = ((IString)c.get(iTo)).getValue();
			TreeMapNode toNode = nodeMap.get(to);
			if(toNode == null)
				throw RuntimeExceptionFactory.figureException("TreeMap: edge uses non-existing node id " + to, v, ctx.getCurrentAST(), ctx.getStackTrace());
			if(hasParent.contains(toNode))
				throw RuntimeExceptionFactory.figureException("TreeMap: node " + to + " has multiple parents", v, ctx.getCurrentAST(), ctx.getStackTrace());
			hasParent.add(toNode);
			fromNode.addChild(properties, edgeProperties, toNode, ctx);
		}
		
		root = null;
		for(TreeMapNode n : nodeMap.values())
			if(!hasParent.contains(n)){
				if(root != null)
				 throw RuntimeExceptionFactory.figureException("TreeMap: multiple roots found: " + root.rootFigure.getIdProperty() + " and " + n.rootFigure.getIdProperty(),
						  edges, ctx.getCurrentAST(), ctx.getStackTrace());
				root = n;
			}
		if(root == null)
			throw RuntimeExceptionFactory.figureException("TreeMap: no root found", edges, ctx.getCurrentAST(), ctx.getStackTrace());
	}
	
	@Override
	public
	void bbox() {
		System.err.printf("TreeMapNode.bbox(), left=%f, top=%f\n", getLeft(), getTop());
		minSize.setWidth(getWidthProperty());
		if(minSize.getWidth() == 0) 
			minSize.setWidth(400);
		minSize.setHeight(getHeightProperty());
		if(minSize.getHeight() == 0)
			minSize.setHeight(400);
		root.place(minSize.getWidth(), minSize.getHeight(), true);
		setNonResizable();
	}
	
	@Override
	public
	void draw(double left, double top) {
		this.setLeft(left);
		this.setTop(top);
		
		System.err.printf("Tree.draw(%f,%f)\n", left, top);
		applyProperties();
		root.draw(left, top);
	}
	
	@Override
	public boolean mouseInside(double mousex, double mousey){
		return root.mouseInside(mousex, mousey) || 
		        super.mouseInside(mousex, mousey);
	}
	

	public boolean getFiguresUnderMouse(Coordinate c,Vector<Figure> result){
		boolean ret = false;
		if(root!=null){
			ret = root.getFiguresUnderMouse(c, result);
		}
		if(mouseInside(c.getX(), c.getY())){
			result.add(this);
			ret=true;
		}
		return ret;
	}
	

	public void registerNames(NameResolver resolver){
		super.registerNames(resolver);
		if(root!=null) root.registerNames(resolver);
	}

	@Override
	public void layout() {
		size.set(minSize);
		if(root!=null) {
			root.setToMinSize();
			root.layout();
		}
		
	}

}
