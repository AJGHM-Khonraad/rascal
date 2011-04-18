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

import org.eclipse.imp.pdb.facts.IList;
import org.rascalmpl.interpreter.IEvaluatorContext;
import org.rascalmpl.library.vis.compose.Compose;
import org.rascalmpl.library.vis.properties.PropertyManager;
import processing.core.PConstants;


/**
 * Arbitrary shape built from Vertices.
 * 
 * Relevant properties:
 * connected:	connect vertices with lines
 * closed:		make a closed shape
 * curved:		connect vertices with a spline
 * 
 * @author paulk
 *
 */
public class Shape extends Compose {
	static boolean debug = false;
	float leftAnchor;
	float rightAnchor;
	float topAnchor;
	float bottomAnchor;

	Shape(IFigureApplet fpa, PropertyManager properties, IList elems,  IList childProps, IEvaluatorContext ctx) {
		super(fpa, properties, elems, childProps, ctx);
	}
	
	@Override
	public
	void bbox(float desiredWidth, float desiredHeight){
		leftAnchor = rightAnchor = topAnchor = bottomAnchor = 0;

		for (Figure fig : figures){
			fig.bbox(AUTO_SIZE, AUTO_SIZE);
			leftAnchor = max(leftAnchor, fig.leftAlign());
			rightAnchor = max(rightAnchor, fig.rightAlign());
			topAnchor = max(topAnchor, fig.topAlign());
			bottomAnchor = max(bottomAnchor, fig.bottomAlign());
		}
		width = leftAnchor + rightAnchor;
		height = topAnchor + bottomAnchor;
		if(debug)System.err.printf("bbox.shape: width = %f (%f, %f), height = %f (%f, %f)\n", 
				width, leftAnchor, rightAnchor, height, topAnchor, bottomAnchor);
	}
	
	@Override
	public
	void draw(float left, float top){
		
		this.setLeft(left);
		this.setTop(top);
		
		applyProperties();
		float bottom = top + height - bottomAnchor;
		boolean closed = getClosedProperty();
		boolean curved = getCurvedProperty();
		boolean connected = closed ||  getConnectedProperty() || curved;
		
		if(connected){
			fpa.noFill();
			fpa.beginShape();
		}
		
		/*
		 * We present to the user a coordinate system
		 * with origin at the bottom left corner!
		 * Therefore we subtract deltay from bottom
		 */
		
		Vertex next = (Vertex)figures[0];
		float nextLeft = left + leftAnchor + next.deltax;
		float nextTop = bottom - next.deltay;
		if(connected && closed){
			// Add a vertex at origin
			fpa.vertex(left + leftAnchor, bottom);
			fpa.vertex(nextLeft, nextTop);
		}
		if(connected && curved)
			fpa.curveVertex(nextLeft, nextTop);
		
		for(Figure fig : figures){
			next = (Vertex)fig;
			nextLeft = left + leftAnchor + next.deltax;
			nextTop = bottom - next.deltay;
			if(debug)System.err.printf("vertex(%f,%f)\n", nextLeft, nextTop);
			applyProperties();
			if(connected){
				if(!closed)
						fpa.noFill();
				if(curved)
					fpa.curveVertex(nextLeft,nextTop);
				else
					fpa.vertex(nextLeft,nextTop);
			}
		}
		if(connected){
			if(curved){
				next = (Vertex)figures[figures.length-1];
				fpa.curveVertex(left + leftAnchor + next.deltax, bottom - next.deltay);
			}
			if(closed){
				fpa.vertex(nextLeft, bottom);
				fpa.endShape(PConstants.CLOSE);
			} else 
				fpa.endShape();
		}
		
		for(Figure fig : figures){
			Vertex p = (Vertex) fig;
			p.draw(left + leftAnchor + p.deltax, bottom - p.deltay);
		}
	}
	
	@Override
	public float leftAlign(){
		return leftAnchor;
	}
	
	@Override
	public float rightAlign(){
		return rightAnchor;
	}
	
	@Override
	public float topAlign(){
		return topAnchor;
	}
	
	@Override
	public float bottomAlign(){
		return bottomAnchor;
	}
}
