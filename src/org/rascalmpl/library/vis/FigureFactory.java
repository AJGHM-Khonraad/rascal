package org.rascalmpl.library.vis;

import java.util.HashMap;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IMap;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.rascalmpl.interpreter.IEvaluatorContext;
import org.rascalmpl.interpreter.utils.RuntimeExceptionFactory;
import org.rascalmpl.library.vis.compose.Grid;
import org.rascalmpl.library.vis.compose.HCat;
import org.rascalmpl.library.vis.compose.HVCat;
import org.rascalmpl.library.vis.compose.Overlay;
import org.rascalmpl.library.vis.compose.Pack;
import org.rascalmpl.library.vis.compose.VCat;
import org.rascalmpl.library.vis.containers.Box;
import org.rascalmpl.library.vis.containers.Ellipse;
import org.rascalmpl.library.vis.containers.Space;
import org.rascalmpl.library.vis.containers.Wedge;
import org.rascalmpl.library.vis.graph.lattice.LatticeGraph;
import org.rascalmpl.library.vis.graph.lattice.LatticeGraphEdge;
import org.rascalmpl.library.vis.graph.layered.LayeredGraph;
import org.rascalmpl.library.vis.graph.layered.LayeredGraphEdge;
import org.rascalmpl.library.vis.graph.spring.SpringGraph;
import org.rascalmpl.library.vis.graph.spring.SpringGraphEdge;
import org.rascalmpl.library.vis.interaction.BoolControl;
import org.rascalmpl.library.vis.interaction.BoolControlEffect;
import org.rascalmpl.library.vis.interaction.StrControl;
import org.rascalmpl.library.vis.properties.DefaultPropertyManager;
import org.rascalmpl.library.vis.properties.IPropertyManager;
import org.rascalmpl.library.vis.properties.IStringPropertyValue;
import org.rascalmpl.library.vis.properties.PropertyManager;
import org.rascalmpl.library.vis.properties.Utils;
import org.rascalmpl.library.vis.tree.Tree;
import org.rascalmpl.library.vis.tree.TreeMap;
import org.rascalmpl.values.ValueFactoryFactory;
import org.rascalmpl.library.vis.properties.Property;

/**
 * 
 * FigureFactory: factory for creating visual elements.
 * 
 * @author paulk
 *
 */
@SuppressWarnings("serial")
public class FigureFactory {
	static IValueFactory vf = ValueFactoryFactory.getValueFactory();
	static IList emptyList = vf.list();
	
	enum Primitives {
		BOX, 
		BOOLCONTROL,
		CONTROLON,
		CONTROLOFF,
		EDGE, 
		ELLIPSE, 
		GRAPH, 
		GRID,
		HCAT, 
		HVCAT,
		OUTLINE,
		OVERLAY, 
		PACK, 
		ROTATE,
		SCALE,
		SHAPE,
		SPACE,
		STRCONTROL,
		TEXT, 
		TREE,
		TREEMAP,
		USE,
		VCAT,
		VERTEX,
		WEDGE
		}
					  
    static HashMap<String,Primitives> pmap = new HashMap<String,Primitives>() {
    {
    	put("_box",			Primitives.BOX);
    	put("_boolControl",	Primitives.BOOLCONTROL);
    	put("_controlOff",	Primitives.CONTROLOFF);
    	put("_controlOn",	Primitives.CONTROLON);
    	put("_edge",		Primitives.EDGE);
    	put("_ellipse",		Primitives.ELLIPSE);
    	put("_graph",		Primitives.GRAPH);
    	put("_grid",		Primitives.GRID);
    	put("_hcat",		Primitives.HCAT);
    	put("_hvcat",		Primitives.HVCAT);	
      	put("_outline",		Primitives.OUTLINE);	
    	put("_overlay",		Primitives.OVERLAY);	
    	put("_pack",		Primitives.PACK);	
    	put("_rotate",      Primitives.ROTATE);
    	put("_scale",		Primitives.SCALE);
    	put("_shape",		Primitives.SHAPE);
    	put("_space",		Primitives.SPACE);
    	put("_strControl",	Primitives.STRCONTROL);
    	put("_text",		Primitives.TEXT);	    		
    	put("_tree",		Primitives.TREE);
       	put("_treemap",		Primitives.TREEMAP);
    	put("_use",			Primitives.USE);
    	put("_vcat",		Primitives.VCAT);
    	put("_vertex",		Primitives.VERTEX);
    	put("_wedge",		Primitives.WEDGE);
    }};
	
	private static IPropertyManager extendProperties(FigurePApplet fpa, IConstructor c, IPropertyManager pm, IEvaluatorContext ctx){		
		IList props = (IList) c.get(c.arity()-1);
		return pm == null ? new DefaultPropertyManager(fpa)
		                  : ((props == null || props.equals(emptyList)) ? pm
								                          : new PropertyManager(fpa, pm, props, ctx));
	}
	
	public static Figure make(FigurePApplet fpa, IConstructor c, IPropertyManager properties, IEvaluatorContext ctx){
		String ename = c.getName();
		properties = extendProperties(fpa, c, properties, ctx);
		
		switch(pmap.get(ename)){
			
		case BOX:
			return new Box(fpa, properties, c.arity() == 2 ? (IConstructor) c.get(0) : null, ctx);
		
		case BOOLCONTROL:
			return new BoolControl(fpa, properties, (IString)c.get(0), (IConstructor) c.get(1), (IConstructor) c.get(2), ctx);
		
		case CONTROLON:
			return new BoolControlEffect(fpa,properties, (IString)c.get(0),  true, (IConstructor) c.get(1), ctx);

		case CONTROLOFF:
			return new BoolControlEffect(fpa,properties, (IString)c.get(0),  false, (IConstructor) c.get(1), ctx);
			
			//		case EDGE:			
//			return new GraphEdge(null,fpa, properties, (IString)c.get(0), (IString)c.get(1), ctx);
		
		case ELLIPSE:
			return new Ellipse(fpa, properties, c.arity() == 2 ? (IConstructor) c.get(0) : null, ctx);
		
		case GRAPH:
			if(properties.getHint().contains("lattice"))
				return new LatticeGraph(fpa, properties, (IList) c.get(0), (IList)c.get(1), ctx);
			if(properties.getHint().contains("layered"))
				return new LayeredGraph(fpa, properties, (IList) c.get(0), (IList)c.get(1), ctx);
			return new SpringGraph(fpa, properties, (IList) c.get(0), (IList)c.get(1), ctx);
			
		case GRID: 
			return new Grid(fpa, properties, (IList) c.get(0), ctx);

		case HCAT:
			return new HCat(fpa, properties, (IList) c.get(0), ctx);
			
		case HVCAT: 
			return new HVCat(fpa, properties, (IList) c.get(0), ctx);
			
		case OUTLINE: 
			return new Outline(fpa, properties, (IMap)c.get(0), ctx);
			
		case OVERLAY: 
			return new Overlay(fpa, properties, (IList) c.get(0), ctx);
			
		case PACK:  
			return new Pack(fpa, properties, (IList) c.get(0), ctx);
			
		case ROTATE:
			//TODO
			return new Rotate(fpa, properties, c.get(0), (IConstructor) c.get(1), ctx);
			
		case SCALE:
			//TODO
			if(c.arity() == 3)
				return new Scale(fpa, properties, c.get(0), c.get(0), (IConstructor) c.get(1), ctx);
			
			return new Scale(fpa, properties, c.get(0), c.get(1), (IConstructor) c.get(2), ctx);
			
		case SHAPE: 
			return new Shape(fpa, properties, (IList) c.get(0), ctx);
			
		case SPACE:
			return new Space(fpa, properties, c.arity() == 2 ? (IConstructor) c.get(0) : null, ctx);
			
		case STRCONTROL:
			return new StrControl(fpa, properties, (IString) c.get(0), ctx);
			
		case TEXT:
			//return new Text(fpa, properties,  (IString) c.get(0), ctx);
			IStringPropertyValue txt = Utils.getStrArg(Property.TEXT, c.get(0), fpa, ctx);
			return new Text(fpa, properties,  txt, ctx);
			
		case TREE: 			
			return new Tree(fpa,properties, (IList) c.get(0), (IList)c.get(1), ctx);

		case TREEMAP: 			
			return new TreeMap(fpa,properties, (IList) c.get(0), (IList)c.get(1), ctx);
			
		case USE:			
			return new Use(fpa, properties, (IConstructor) c.get(0), ctx);
			
		case VCAT:
			return new VCat(fpa, properties, (IList) c.get(0), ctx);
			
		case VERTEX:			
			return new Vertex(fpa, properties, c.get(0), c.get(1), c.arity() == 4 ? (IConstructor) c.get(2) : null, ctx);
			
		case WEDGE:			
			return new Wedge(fpa, properties, c.arity() == 2 ? (IConstructor) c.get(0) : null, ctx);						
		}
		throw RuntimeExceptionFactory.illegalArgument(c, ctx.getCurrentAST(), ctx.getStackTrace());
	}
	
	public static SpringGraphEdge makeSpringGraphEdge(SpringGraph G, FigurePApplet fpa, IConstructor c,
			IPropertyManager properties, IEvaluatorContext ctx) {
		IString from = (IString)c.get(0);
		IString to = (IString)c.get(1);
		IConstructor toArrow = c.arity() > 3 ? (IConstructor) c.get(2) : null;
		IConstructor fromArrow = c.arity() > 4 ? (IConstructor)  c.get(3) : null;
		return new SpringGraphEdge(G, fpa, properties, from, to, toArrow, fromArrow,ctx);
	}
	
	public static LayeredGraphEdge makeLayeredGraphEdge(LayeredGraph G, FigurePApplet fpa, IConstructor c,
			IPropertyManager properties, IEvaluatorContext ctx) {
		IString from = (IString)c.get(0);
		IString to = (IString)c.get(1);
		IConstructor toArrow = c.arity() > 3 ? (IConstructor) c.get(2) : null;
		IConstructor fromArrow = c.arity() > 4 ? (IConstructor)  c.get(3) : null;
		return new LayeredGraphEdge(G, fpa, properties, from, to, toArrow, fromArrow, ctx);
	}
	
	public static LatticeGraphEdge makeLatticeGraphEdge(LatticeGraph G, FigurePApplet fpa, IConstructor c,
			IPropertyManager properties, IEvaluatorContext ctx) {
		IString from = (IString)c.get(0);
		IString to = (IString)c.get(1);
		return new LatticeGraphEdge(G, fpa, properties, from, to,  ctx);
	}

}
