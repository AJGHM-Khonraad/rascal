package org.rascalmpl.library.vis.figure.combine;

import static org.rascalmpl.library.vis.properties.TwoDProperties.ALIGN;
import static org.rascalmpl.library.vis.properties.TwoDProperties.GROW;
import static org.rascalmpl.library.vis.properties.TwoDProperties.SHRINK;
import static org.rascalmpl.library.vis.util.vector.Dimension.HOR_VER;

import java.util.List;
import java.util.Vector;

import org.rascalmpl.library.vis.figure.Figure;
import org.rascalmpl.library.vis.figure.interaction.MouseOver;
import org.rascalmpl.library.vis.properties.PropertyManager;
import org.rascalmpl.library.vis.swt.IFigureConstructionEnv;
import org.rascalmpl.library.vis.util.vector.Coordinate;
import org.rascalmpl.library.vis.util.vector.Dimension;
import org.rascalmpl.library.vis.util.vector.Rectangle;

public class Overlap extends LayoutProxy{
	
	public Figure over;
	
	public Overlap(Figure under, Figure over, PropertyManager properties){
		super(under,properties);
		children = new Figure[2];
		children[0] = under;
		children[1] = over;
		this.over = over;
	}
	
	
	@Override
	public void initElem(IFigureConstructionEnv env, MouseOver mparent, boolean swtSeen, boolean visible){
		super.initElem(env, mparent, swtSeen, visible);
		env.registerOverlap(this);
	}
	
	public void setOverlap(Figure fig){
		children[1] = fig;
		over = fig;
	}
	
	@Override
	public void resizeElement(Rectangle view) {
		super.resizeElement(view);
		for(Dimension d : HOR_VER){
			if(over.prop.is2DPropertySet(d, SHRINK)){
				double sizeLeft = Math.max(0,location.get(d)  - view.getLocation().get(d));
				double sizeRight = 
					Math.max(0,view.getSize().get(d) - ((location.get(d) - view.getLocation().get(d)) + size.get(d)));;
				
				double align = over.prop.get2DReal(d, ALIGN);
				double sizeMiddle = size.get(d) * (0.5 - Math.abs(align - 0.5 ));
				if(align > 0.5){
					sizeLeft*= 1.0 - (align - 0.5)*2.0;
				}
				if(align < 0.5){
					sizeRight*= 1.0 - (0.5 - align)*2.0;
				}
				over.size.set(d,over.prop.get2DReal(d, SHRINK) * (sizeLeft + sizeMiddle + sizeRight));
				
			} else {
				over.size.set(d,innerFig.size.get(d) * over.prop.get2DReal(d, GROW));
			}
			if(over.size.get(d) > view.getSize().get(d)){
				over.size.set(d,view.getSize().get(d));
			}
			if(over.minSize.get(d) > over.size.get(d)){
				over.size.set(d,over.minSize.get(d));
			}
			over.location.set(d, 
					(over.prop.get2DReal(d, ALIGN)  * (innerFig.size.get(d) - over.size.get(d))) + 
					(over.prop.get2DReal(d,ALIGN) -0.5)*2.0 * over.size.get(d));	
			if(over.location.get(d) + location.get(d) < view.getLocation().get(d)){
				//over.location.set(d,view.getLocation().get(d));
			}
			if(location.get(d) + over.location.get(d) + over.size.get(d) > view.getRightDown().get(d)){
				//over.location.set(d,view.getRightDown().get(d) - over.size.get(d));
			}
		}
	}
	
	@Override	
	public void destroyElement(IFigureConstructionEnv env) {
		env.unregisterOverlap(this);
	}

	public Vector<Figure> getVisibleChildren(Rectangle r) {
		Vector<Figure> visChildren = new Vector<Figure>();
		visChildren.add(innerFig); // overlap is drawed from elsewhere
		return visChildren;
	}
	
	public void getFiguresUnderMouse(Coordinate c,List<Figure> result){
		if(!mouseInside(c)){
			return;
		}
		innerFig.getFiguresUnderMouse(c, result);
		if(handlesInput()){
			result.add(this);
		}
	}
	
}
