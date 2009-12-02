package org.meta_environment.rascal.library.experiments.VL;

import java.util.HashMap;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IInteger;
import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.meta_environment.rascal.interpreter.IEvaluatorContext;
import org.meta_environment.rascal.interpreter.utils.RuntimeExceptionFactory;
import org.meta_environment.values.ValueFactoryFactory;

@SuppressWarnings("serial")
public class VELEMFactory {
	static IValueFactory vf = ValueFactoryFactory.getValueFactory();
	static IList emptyList = vf.list();
	
	enum Primitives {COMBINE, OVERLAY, GRID, SHAPE, PACK, GRAPH, TREE, SPACE,
					  RECT, ELLIPSE, LABEL, EDGE, VERTEX, PIE}
					  
    static HashMap<String,Primitives> pmap = new HashMap<String,Primitives>() {
    	{
    		put("combine", 	Primitives.COMBINE);
    		put("overlay",	Primitives.OVERLAY);	
    		put("grid",		Primitives.GRID);	
    		put("shape",	Primitives.SHAPE);
    		put("pack",		Primitives.PACK);	
    		put("rect",		Primitives.RECT);	
    		put("ellipse",	Primitives.ELLIPSE);	
    		put("label",	Primitives.LABEL);	
    		put("edge",		Primitives.EDGE);	
    		put("graph",	Primitives.GRAPH);
    		put("tree",		Primitives.TREE);
    		put("vertex",	Primitives.VERTEX);
    		put("space",	Primitives.SPACE);
    		put("pie",		Primitives.PIE);
    		
    	}
    };
    
    static IList props;
	static IList elems;
	
	private static void getOneOrTwoArgs(IConstructor c){
		if(c.arity() >= 2){
			props = (IList) c.get(0);
			elems = (IList) c.get(1);
		} else {
			props = emptyList;
			elems = (IList) c.get(0);
		}
	}
	public static VELEM make(VLPApplet vlp, IConstructor c, PropertyManager inheritedProps, IEvaluatorContext ctx){
		String ename = c.getName();
		System.err.println("ename = " + ename);
	
		switch(pmap.get(ename)){
			case COMBINE:
				getOneOrTwoArgs(c);
				return new Combine(vlp, inheritedProps, props, elems, ctx);
			case OVERLAY: 
				getOneOrTwoArgs(c); 
				return new Overlay(vlp, inheritedProps, props, elems, ctx);
			case GRID: 
				getOneOrTwoArgs(c); 
				return new Grid(vlp, inheritedProps, props, elems, ctx);
			case SHAPE: 
				getOneOrTwoArgs(c); 
				return new Shape(vlp, inheritedProps, props, elems, ctx);
			case PACK: 
				getOneOrTwoArgs(c); 
				return new Pack(vlp, inheritedProps, props, elems, ctx);
			case PIE: 
				getOneOrTwoArgs(c); 
				return new Pie(vlp, inheritedProps, props, elems, ctx);
			case GRAPH: 
				if(c.arity() == 3)
					return new Graph(vlp,inheritedProps, (IList) c.get(0), (IList) c.get(1), (IList)c.get(2), ctx);
				
				return new Graph(vlp,inheritedProps, emptyList, (IList) c.get(0), (IList)c.get(1), ctx);
				
			case TREE: 
				if(c.arity() == 3)
					return new SimpleTree(vlp,inheritedProps, (IList) c.get(0), (IList) c.get(1), (IList)c.get(2), ctx);
				
				return new SimpleTree(vlp,inheritedProps, emptyList, (IList) c.get(0), (IList)c.get(1), ctx);
				
			case VERTEX:
				if(c.arity() == 3)
					return new Vertex(vlp, (IInteger) c.get(0), (IInteger) c.get(1), (IConstructor) c.get(2), ctx);
				
				return new Vertex(vlp, (IInteger) c.get(0), (IInteger) c.get(1), ctx);
			case SPACE:
				return new Space(vlp, (IInteger) c.get(0), ctx);
			case RECT:
				return new Rect(vlp, inheritedProps, (IList) c.get(0), ctx);
			case ELLIPSE:
				return new Ellipse(vlp, inheritedProps, (IList) c.get(0), ctx);
			case LABEL:
				return new Label(vlp, inheritedProps, (IList) c.get(0), ctx);
			case EDGE:
				if(c.arity() == 3)
					return new GraphEdge(vlp,inheritedProps, (IList) c.get(0), (IString)c.get(1), (IString)c.get(2), ctx);
				
				return new GraphEdge(vlp,inheritedProps, emptyList, (IString)c.get(0), (IString)c.get(1), ctx);
		}
		throw RuntimeExceptionFactory.illegalArgument(c, ctx.getCurrentAST(), ctx.getStackTrace());
	}
	
	public static GraphEdge makeGraphEdge(VLPApplet vlp, IConstructor c,
			PropertyManager properties, IEvaluatorContext ctx) {
		return new GraphEdge(vlp, properties, (IList) c.get(0), (IString)c.get(1), (IString)c.get(2), ctx);
	}

}
