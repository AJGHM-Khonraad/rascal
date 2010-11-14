package org.rascalmpl.library.vis.graph.layered;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.rascalmpl.interpreter.IEvaluatorContext;
import org.rascalmpl.interpreter.utils.RuntimeExceptionFactory;
import org.rascalmpl.library.vis.Figure;
import org.rascalmpl.library.vis.FigureFactory;
import org.rascalmpl.library.vis.FigurePApplet;
import org.rascalmpl.library.vis.PropertyManager;
import org.rascalmpl.values.ValueFactoryFactory;

import processing.core.PApplet;

/**

 * Graph layout. Given a list of nodes and edges a graph layout is computed with given size.
 * 
 * We use two different methods (triggered by the hint attribute):
 * 
 * hint("spring"): We use a spring layout approach as described in 
 * 
 * 		Fruchterman, T. M. J., & Reingold, E. M. (1991). 
 * 		Graph Drawing by Force-Directed Placement. 
 * 		Software: Practice and Experience, 21(11).
 * 
 * hint("lattice"): we use a layered drawing method as described in
 * 		Battista, et. al Graph Drawing, Prentice Hall, 1999
 * 
 * 
 * @author paulk
 * 
 */
public class LayeredGraph extends Figure {
	protected ArrayList<LayeredGraphNode> nodes;
	protected ArrayList<LayeredGraphEdge> edges;
	protected HashMap<String, LayeredGraphNode> registered;
	IEvaluatorContext ctx;
	
	private static boolean debug = false;
	public LayeredGraph(FigurePApplet fpa, PropertyManager properties, IList nodes,
			IList edges, IEvaluatorContext ctx) {
		super(fpa, properties, ctx);
		this.nodes = new ArrayList<LayeredGraphNode>();
		this.ctx = ctx;
		width = getWidthProperty();
		height = getHeightProperty();

		registered = new HashMap<String,LayeredGraphNode>();
		for(IValue v : nodes){
			IConstructor c = (IConstructor) v;
			Figure ve = FigureFactory.make(fpa, c, properties, ctx);
			String name = ve.getIdProperty();

			if(name.length() == 0)
				throw RuntimeExceptionFactory.figureException("Id property should be defined", v, ctx.getCurrentAST(), ctx.getStackTrace());

			LayeredGraphNode node = new LayeredGraphNode(this, name, ve);
			this.nodes.add(node);
			register(name, node);
		}

		this.edges = new ArrayList<LayeredGraphEdge>();
		for (IValue v : edges) {
			IConstructor c = (IConstructor) v;
			LayeredGraphEdge e = FigureFactory.makeLayeredGraphEdge(this, fpa, c, properties,
					ctx);
			this.edges.add(e);
			e.getFrom().addOut(e.getTo());
			e.getTo().addIn(e.getFrom());
		}

		this.nodes = new ArrayList<LayeredGraphNode>();
		this.ctx = ctx;
		width = getWidthProperty();
		height = getHeightProperty();

		registered = new HashMap<String,LayeredGraphNode>();
		for(IValue v : nodes){

			IConstructor c = (IConstructor) v;
			Figure ve = FigureFactory.make(fpa, c, properties, ctx);
			String name = ve.getIdProperty();

			if(name.length() == 0)
				throw RuntimeExceptionFactory.figureException("Id property should be defined", v, ctx.getCurrentAST(), ctx.getStackTrace());

			LayeredGraphNode node = new LayeredGraphNode(this, name, ve);
			this.nodes.add(node);
			register(name, node);
		}

		this.edges = new ArrayList<LayeredGraphEdge>();
		for (IValue v : edges) {
			IConstructor c = (IConstructor) v;
			LayeredGraphEdge e = FigureFactory.makeLayeredGraphEdge(this, fpa, c, properties,
					ctx);
			this.edges.add(e);
			e.getFrom().addOut(e.getTo());
			e.getTo().addIn(e.getFrom());
		}
	}
	
	public void register(String name, LayeredGraphNode nd){
		registered.put(name, nd);
	}

	public LayeredGraphNode getRegistered(String name) {
		return registered.get(name);
	}
	
	private void print(String msg, LinkedList<LinkedList<LayeredGraphNode>> layers){
		System.err.println("---- " + msg);
		for(int i = 0; i < layers.size(); i++){
			LinkedList<LayeredGraphNode> layer = layers.get(i);
			if(i > 0) System.err.println("");
			System.err.print("Layer " + i + ": ");
			for(int j = 0; j < layer.size(); j++){
				LayeredGraphNode g = layer.get(j);
				System.err.print("(" + j + ", " + g.name + ") ");
			}
		}
		System.err.println("\n----");
	}
	
	private void initialPlacement(){

		int W = PApplet.round(1.5f * PApplet.sqrt(nodes.size()) + 1);
		LinkedList<LinkedList<LayeredGraphNode>> layers = assignLayers(1000); print("assignLayers", layers);
		layers = insertVirtualNodes(layers);print("insertVirtualNodes", layers);
		layers = reduceCrossings(layers);print("reduceCrossings", layers);
		
		int n = layers.size();
		float wlayer[] = new float[n];
		float hlayer[] = new float[n];
		
		float hgap = getHGapProperty();
		float vgap = getVGapProperty();
		
		int l = 0;
		for(LinkedList<LayeredGraphNode> layer : layers){
			wlayer[l] = hlayer[l] = 0;
			for(LayeredGraphNode g : layer){
				g.bbox();
				wlayer[l] += g.width() + hgap;
				hlayer[l] = max(hlayer[l], g.height());
			}
			hlayer[l] += vgap;
			l++;
		}
		l = 0;
		float y = 0;
		for(LinkedList<LayeredGraphNode> layer : layers){
			
			float deltax = (width - wlayer[l])/(layer.size() + 1);
			//float deltax = 50;
			float x = deltax;
			for(LayeredGraphNode g : layer){
				g.x = x + g.width()/2;
				g.y = y + hlayer[l]/2;
				x += g.width() + deltax;
			}
			y += hlayer[l];
			l++;
		}
		

	}

	@Override
	public
	void bbox() {
		
	 initialPlacement();
		

		// Now scale (back or up) to the desired width x height frame
		float minx = Float.MAX_VALUE;
		float maxx = Float.MIN_VALUE;
		float miny = Float.MAX_VALUE;
		float maxy = Float.MIN_VALUE;
		
		for(LayeredGraphNode n : nodes){
			float w2 = n.width()/2;
			float h2 = n.height()/2;
			if(n.x - w2 < minx)

				minx = n.x - w2;
			if (n.x + w2 > maxx)
				maxx = n.x + w2;

			if (n.y - h2 < miny)
				miny = n.y - h2;
			if (n.y + h2 > maxy)
				maxy = n.y + h2;
		}

		float scalex = width / (maxx - minx);
		float scaley = height / (maxy - miny);

		for (LayeredGraphNode n : nodes) {
			n.x = n.x - minx;
			n.x *= scalex;
			n.y = n.y - miny;
			n.y *= scaley;
		}
	}

	@Override
	public
	void draw(float left, float top) {
		this.left = left;
		this.top = top;

		applyProperties();
		for (LayeredGraphEdge e : edges)
			e.draw(left, top);
		for (LayeredGraphNode n : nodes) {
			n.draw(left, top);
		}
	}

	@Override
	public boolean mouseOver(int mousex, int mousey) {
		for (LayeredGraphNode n : nodes) {
			if (n.mouseOver(mousex, mousey))
				return true;
		}
		return super.mouseOver(mousex, mousey);
	}

	@Override
	public boolean mousePressed(int mousex, int mousey) {
		for (LayeredGraphNode n : nodes) {
			if (n.mousePressed(mousex, mousey))
				return true;
		}
		return super.mouseOver(mousex, mousey);
	}
	
	// Methods for Layered layout
	
	private LinkedList<LinkedList<LayeredGraphNode>>assignLayers(int W){
		if(nodes.size() == 0)
			return new LinkedList<LinkedList<LayeredGraphNode>>();
		
		// Compute Topological ordering
		LinkedList<LayeredGraphNode> worklist = new LinkedList<LayeredGraphNode>();
		LinkedList<LayeredGraphNode> rootlist = new LinkedList<LayeredGraphNode>();
	
		int label = 0;
		for(LayeredGraphNode g : nodes){
			g.label = -1;
			if(g.in.size() == 0)
				rootlist.addLast(g);
		}
		
		if(rootlist.size() == 0)
			rootlist.add(nodes.get(0));
		
		for(LayeredGraphNode g : rootlist){
			g.label = label++;
			worklist.addLast(g);
		}
		
		while(!worklist.isEmpty()){
			LayeredGraphNode current = worklist.remove();
			System.err.println("current = " + current.name + "label = " + current.label);
			for(LayeredGraphNode child : current.sortedOut()){
				if(child.label < 0){
					child.label = label++;
					worklist.addLast(child);
				}
			}
		}
		
	
	
		for(LayeredGraphEdge e : edges){
			if(e.getFrom().label >  e.getTo().label){
				System.err.println("Inverting " + e.getFrom().name + " => " + e.getTo().name);
				
				LayeredGraphNode g  = e.getFrom();
				LayeredGraphNode o = e.getTo();
				e.invert();
				g.out.remove(o);
				o.in.remove(g);
				g.in.add(o);
				o.out.add(g);
			}
		}
		
		for(LayeredGraphNode g : rootlist){
			worklist.addLast(g);
		}
		
		LinkedList<LinkedList<LayeredGraphNode>> layers = new LinkedList<LinkedList<LayeredGraphNode>>();
		
		LinkedList<LayeredGraphNode> currentLayer = new LinkedList<LayeredGraphNode>();
		
		while(!worklist.isEmpty()){
			LayeredGraphNode current = null;
			for(LayeredGraphNode g : worklist){
				if(g.AllInAssignedToLayers(layers.size())){
					current = g;
					worklist.remove(g);
					break;	
				}
			}
			if(current == null)
				current = worklist.remove();
			//System.err.println("current = " + current.name);
			if(layers.size() == 0 && currentLayer.size() == 0){
				if(current.layer < 0){
					currentLayer.addLast(current);
					current.layer = layers.size();
				}
			} else if (currentLayer.size() < W && current.AllInAssignedToLayers(layers.size() - 1)){
				//System.err.println("case 1");
				if(current.layer < 0){
					currentLayer.addLast(current);
					current.layer = layers.size();
				}
			} else {
				//System.err.println("case 2");
				if(current.layer < 0){
					layers.addLast(currentLayer);
					currentLayer = new LinkedList<LayeredGraphNode>();
					currentLayer.addLast(current);
					current.layer = layers.size();
				}
			}
			System.err.println("Assign " + current.name + " to layer " + current.layer);
			for(LayeredGraphNode child : current.sortedOut()){
					if(child.layer < 0)
						worklist.addLast(child);
			}
		}	
		layers.addLast(currentLayer);
		

		return layers; 
	}
	
	private void insertVirtualNode(LinkedList<LinkedList<LayeredGraphNode>> layers, LayeredGraphNode from, LayeredGraphNode to){
		if(to.layer - from.layer > 1){
			// Create virtual node
			String vname =  from.name + "_" + to.name + "[" + (from.layer + 1) + "]";
			System.err.println("Creating virtual node " + vname + " between " + from.name + " and " + to.name);
			LayeredGraphNode v = new LayeredGraphNode(this, vname, null);
			IValueFactory vf = ValueFactoryFactory.getValueFactory();
			IString vfVname = vf.string(vname);
			nodes.add(v);
			register(vname, v);
			
				v.in.add(from);
				v.out.add(to);
				v.layer = from.layer + 1;
				from.out.set(from.out.indexOf(to), v);
				to.in.set(to.in.indexOf(from), v);
			
			
			IString vfGname = vf.string(from.name);
			IString vfOname = vf.string(to.name);
			LayeredGraphEdge e1 = new LayeredGraphEdge(this, fpa, properties, vfGname, vfVname, ctx);
			LayeredGraphEdge e2 = new LayeredGraphEdge(this, fpa, properties, vfVname, vfOname, ctx);
			edges.add(e1);
			edges.add(e2);
			LayeredGraphEdge old = null;
			for(LayeredGraphEdge e : edges){
				//System.err.println("Consider edge " + e.getFrom().name + " -> " + e.getTo().name);
				if(e.getFrom() == from && e.getTo() == to){
					old = e;
					System.err.println("Removing old edge " + from.name + " -> " + to.name);
					break;
				}
			}
			edges.remove(old);
			layers.get(from.layer+1).addFirst(v);
		}
	}

	private LinkedList<LinkedList<LayeredGraphNode>>  insertVirtualNodes(LinkedList<LinkedList<LayeredGraphNode>> layers){
		for(LinkedList<LayeredGraphNode> layer : layers){
			for(LayeredGraphNode from : layer){
				for(int i = 0; i < from.out.size(); i++){
					insertVirtualNode(layers, from, from.out.get(i));
				}
				for(int i = 0; i < from.in.size(); i++){
					insertVirtualNode(layers, from, from.in.get(i));
				}
			}
		}
		return layers;
	}
	
	private int cn(boolean down, LinkedList<LayeredGraphNode> L1, LinkedList<LayeredGraphNode> L2, LayeredGraphNode u, LayeredGraphNode v){
		int n = 0;
		//LinkedList<GraphNode> L = down ? L1 : L2;
		for(LayeredGraphNode iu : down ? u.in : u.out){
			for(LayeredGraphNode iv : down ? v.in : v.out){
				if(L1.indexOf(iu) > L1.indexOf(iv)){
					System.err.printf("Crossing for %s (%d) and %s (%d)\n", 
							           iu.name, L1.indexOf(iu), iv.name, L1.indexOf(iv));
					n++;
				}
			}
		}
		return n;
	}
	
	private LinkedList<LinkedList<LayeredGraphNode>>  reduceCrossings(LinkedList<LinkedList<LayeredGraphNode>> layers){
		// Place each node in each layer at its barycenter
		for(int i = layers.size() - 1; i > 0; i--){
			System.err.println("Layer " + i);
			LinkedList<LayeredGraphNode> P = layers.get(i-1);
			LinkedList<LayeredGraphNode> L = layers.get(i);
			
			LayeredGraphNode[] A = new LayeredGraphNode[L.size()];
			for(int j = 0; j < L.size(); j++){
				LayeredGraphNode g = L.get(j);
				System.err.println("Node " + g.name);
				int sum = 0;
				for(int k = 0; k < g.in.size(); k++){
					sum += P.indexOf(g.in.get(k));
				}
				int degree = g.in.size() + g.out.size();
				int median= PApplet.round(sum/degree) % L.size();
				System.err.println("median = " + median);
				for(int l = median; ; l = (l + 1) % L.size()){
					if(A[l] == null){
						A[l] = g;
						break;
					}
				}
				System.err.println("copying A to LR");
				LinkedList<LayeredGraphNode> LR = new LinkedList<LayeredGraphNode>();
				for(int l = 0; l < L.size(); l++){
					LR.add(l, A[l]);
				}
				layers.set(i, LR);
			}
			
		}
		
		print("First phase reduceCrossings", layers);

		// Iteratively exchange in each layer nodes until no more crossings can be removed.
		
		boolean reducingLayers = true;
		boolean down = false;
		
        int mincrossings = 1000000;
        int crossings = mincrossings - 1;
		for(int iter = 0; crossings < mincrossings; iter++){
			mincrossings = crossings;
			crossings = 0;
			down = iter % 2 == 0;
			System.err.println("down = " + down + " mincrossings = " + mincrossings);
			reducingLayers = false;
			
			for(int i = down ? 0 : layers.size()-1; down ? (i < layers.size()-2) : (i > 0); i += (down ? 1 : -1)){
				System.err.println("i = " + i);
				LinkedList<LayeredGraphNode> L1 = layers.get(i);
				LinkedList<LayeredGraphNode> L2 = layers.get(down ? i+1 : i-1);
				boolean reducing = true;
				int ncross = 0;
				while(reducing){
					reducing = false;
					for(int j = 0; j <= L2.size() - 2; j++){
						LayeredGraphNode u = L2.get(j);
						LayeredGraphNode v = L2.get(j+1);
						int cnbefore = cn(down, L1, L2, u, v);
						L2.set(j, v);
						L2.set(j+1, u);
						int cnafter = cn(down, L1, L2, v, u);
						System.err.printf("i=%d, j=%d, u=%s, v=%s, cnb=%d, cna=%d\n", i, j, u.name, v.name, cnbefore, cnafter);
						if(cnbefore > cnafter){
							ncross += cnafter;
							reducing = true;
							System.err.println("Exchange " + u.name + " and " + v.name);
						} else {
							ncross += cnbefore;
							L2.set(j, u);
							L2.set(j+1, v);
						}
					}
				}
				crossings += ncross;
			}
		}
		return layers;
	}
}
