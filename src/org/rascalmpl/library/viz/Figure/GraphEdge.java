package org.rascalmpl.library.viz.Figure;

import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IString;
import org.rascalmpl.interpreter.IEvaluatorContext;

import processing.core.PApplet;

/**
 * A GraphEdge is created for each "edge" constructor that occurs in the graph.
 * 
 * @author paulk
 *
 */
public class GraphEdge extends Figure {
	GraphNode from;
	GraphNode to;
	private static boolean debug = false;
	
	public GraphEdge(Graph G, FigurePApplet vlp, PropertyManager inheritedProps, IList props, IString fromName, IString toName, IEvaluatorContext ctx) {
		super(vlp, inheritedProps, props, ctx);
		from = vlp.getRegistered(fromName.getValue());
		// TODO Generate exceptions for null cases
		if(from == null){
			System.err.println("No node " + fromName.getValue());
		}
		to = vlp.getRegistered(toName.getValue());
		if(to == null){
			System.err.println("No node " + toName.getValue());
		}
		
		if(debug)System.err.println("edge: " + fromName.getValue() + " -> " + toName.getValue());
	}
	
	void relax(Graph G){
		float vx = to.xdistance(from);
		float vy = to.ydistance(from);
		
	
		float dlen = PApplet.mag(vx, vy);
		dlen = (dlen == 0) ? .0001f : dlen;

		//float attract = G.attract(dlen);
		float attract = dlen * dlen / G.springConstant;
		float dx = (vx / dlen) * attract;
		float dy = (vy / dlen) * attract;

		to.dispx += -dx;
		to.dispy += -dy;
		from.dispx += dx;
		from.dispy += dy;
		
		if(debug)System.err.printf("edge: %s -> %s: dx=%f, dy=%f\n", from.name, to.name, dx, dy);
	}

	@Override
	void draw(float leftDragged, float topDragged) {
		applyProperties();
		if(debug) System.err.println("edge: (" + from.name + ": " + from.x + "," + from.y + ") -> (" + 
								                 to.name + ": " + to.x + "," + to.y + ")");
		vlp.line(leftDragged + from.figX(), topDragged + from.figY(), 
				 leftDragged + to.figX(), topDragged + to.figY());
	}

	@Override
	void bbox() {
		// TODO Auto-generated method stub
		
	}

}
