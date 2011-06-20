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
package org.rascalmpl.library.vis.compose;


import org.eclipse.imp.pdb.facts.IValue;
import org.rascalmpl.interpreter.IEvaluatorContext;
import org.rascalmpl.interpreter.utils.RuntimeExceptionFactory;
import org.rascalmpl.library.vis.Figure;
import org.rascalmpl.library.vis.FigureApplet;
import org.rascalmpl.library.vis.IFigureApplet;
import org.rascalmpl.library.vis.properties.Properties;
import org.rascalmpl.library.vis.properties.PropertyManager;
import org.rascalmpl.library.vis.properties.PropertyParsers;
import org.rascalmpl.library.vis.util.ForBothDimensions;
import org.rascalmpl.library.vis.util.Key;
import org.rascalmpl.library.vis.util.NameResolver;
import org.rascalmpl.values.ValueFactoryFactory;


/**
 * 
 * Overlay elements by stacking them:
 * - when alignAnchors==true aligned around their anchor point
 * - otherwise aligned according to current alignment settings.
 * 
 * @author paulk
 *
 */
public class Overlay extends Compose{
	
	private static boolean debug = false;
	IEvaluatorContext ctx;

	int where; 
	
	public Overlay(IFigureApplet fpa, Figure[] figures, PropertyManager properties,IEvaluatorContext ctx) {
		super(fpa, figures, properties);
		this.ctx = ctx;
	}
	
	@Override
	public void bbox(){
		minSize.clear();
		ForBothDimensions<Double> minLocs = new ForBothDimensions<Double>(Double.MAX_VALUE);
		for(Figure fig : figures){
			for(boolean flip: BOTH_DIMENSIONS){
				if(!fig.isHLocPropertyConverted(flip)){
					minLocs.setForX(flip, Math.min(minLocs.getForX(flip),fig.getHLocProperty(flip)));
				} 
			}
		}
		for(Figure fig : figures){
			fig.bbox();
			for(boolean flip: BOTH_DIMENSIONS){
				double h = fig.minSize.getWidth(flip) / fig.getHShrinkProperty(flip);
				if(!fig.isHLocPropertyConverted(flip)){
					h+=fig.getHLocProperty(flip) - minLocs.getForX(flip);
				}
				minSize.setWidth(flip,Math.max(minSize.getWidth(flip),h));
			}
		}
		
		setResizable();
		super.bbox();
	}

	public void layout(){
		System.out.printf("osize : %s\n",size);
		ForBothDimensions<Double> minLocs = new ForBothDimensions<Double>(Double.MAX_VALUE);
		for(Figure fig : figures){
			for(boolean flip : BOTH_DIMENSIONS){
				minLocs.setForX(flip, Math.min(minLocs.getForX(flip),fig.getHLocProperty(flip)));
			}	
		}
		for(int i = 0 ; i < figures.length ; i++){
			for(boolean flip : BOTH_DIMENSIONS){
				double desWidth = size.getWidth(flip) * figures[i].getHShrinkProperty(flip);
				figures[i].takeDesiredWidth(flip, desWidth);
				if(!flip){
					if(figures[i].isHLocPropertySet()){
						pos[i].setX(figures[i].getHLocProperty() - minLocs.getForX() - (figures[i].getHAlignProperty()* figures[i].size.getWidth()));
						if(figures[i].isHLocPropertyConverted()){
							figures[i].properties.getKey(Properties.HLOC).registerOffset(globalLocation.getX() + pos[i].getX());
						}
					} else {
						pos[i].setX((figures[i].getHAlignProperty()* (size.getWidth() - figures[i].size.getWidth())));
					}
					
				} else {
					if(figures[i].isVLocPropertySet()){
						pos[i].setY(size.getHeight() - (figures[i].getVLocProperty() - minLocs.getForY()) - (figures[i].getVAlignProperty()* figures[i].size.getHeight()));
						if(figures[i].isVLocPropertyConverted()){
							figures[i].properties.getKey(Properties.VLOC).registerOffset(globalLocation.getY() + pos[i].getY());
						}
					} else {
						pos[i].setY((figures[i].getVAlignProperty()* (size.getHeight() - figures[i].size.getHeight())));
					}
				}
				
				figures[i].globalLocation.setX(flip, globalLocation.getY() + pos[i].getX(flip));
			}
			figures[i].layout();
		}
	}
	
	public void draw(double left, double top){
		setLeft(left);
		setTop(top);
		applyProperties();
        boolean closed = getClosedProperty();
        boolean curved = getCurvedProperty();
        boolean connected = getConnectedProperty() || closed || curved;
        // TODO: this curved stuff is unclear to me...
        if(connected){
            fpa.beginShape();
        }
        if(!closed){
        	fpa.noFill();
        }
        
        if(closed && connected && figures.length >= 0){
        	fpa.vertex(left + pos[0].getX() + figures[0].getHConnectProperty() * figures[0].size.getWidth(),
    				top + pos[0].getY()  + figures[0].getVConnectProperty() * figures[0].size.getHeight()  );
        }
        if(connected && curved  && figures.length >= 0){
        	fpa.curveVertex(left + pos[0].getX() + figures[0].getHConnectProperty() * figures[0].size.getWidth(),
    				top + pos[0].getY()  + figures[0].getVConnectProperty() * figures[0].size.getHeight()  );
		}	
        if(connected){
	        for(int i = 0 ; i < figures.length ; i++){
	        	if(curved ){
	        		fpa.curveVertex(left + pos[i].getX() + figures[i].getHConnectProperty() * figures[i].size.getWidth(),
	        				top + pos[i].getY()  + figures[i].getVConnectProperty() * figures[i].size.getHeight()  );
	        	} else {
	        		fpa.vertex(left + pos[i].getX() + figures[i].getHConnectProperty() * figures[i].size.getWidth(),
	        				top + pos[i].getY()  + figures[i].getVConnectProperty() * figures[i].size.getHeight()  );
	        	} 
	        }
        }
        
        if(connected){
			if(curved){
				fpa.curveVertex(left + pos[figures.length-1].getX() + figures[figures.length-1].getHConnectProperty() * figures[figures.length-1].size.getWidth(),
        				top + pos[figures.length-1].getY()  + figures[figures.length-1].getVConnectProperty() * figures[figures.length-1].size.getHeight()  );
			}
			if(closed){
				fpa.vertex(left + pos[figures.length-1].getX() + figures[figures.length-1].getHConnectProperty() * figures[figures.length-1].size.getWidth(),
        				top + pos[figures.length-1].getY()  + figures[figures.length-1].getVConnectProperty() * figures[figures.length-1].size.getHeight()  );
				fpa.endShape(FigureApplet.CLOSE);
			} else 
				fpa.endShape();
		}

		for(int i = 0; i < figures.length; i++){
			figures[i].draw(left + pos[i].getX(), top + pos[i].getY());
		}
	}
		
	public void registerValues(NameResolver resolver){
			
			properties.registerMeasures(resolver);
			ForBothDimensions<Key<Double>> actualKeys = new ForBothDimensions<Key<Double>>(null);
			if(figures.length > 0){
				for(boolean flip : BOTH_DIMENSIONS){
					String actualKeyId = figures[0].getKeyIdForHLoc(flip);
					if(actualKeyId != null){
						actualKeys.setForX(flip, (Key<Double>)resolver.resolve(actualKeyId));
						resolver.register(actualKeyId,new LocalOffsetKey(flip, actualKeys.getForX(flip)));
					}
				}
				for(where = 0 ; where < figures.length ; where++){
					figures[where].registerValues(resolver);
				}
				for(boolean flip : BOTH_DIMENSIONS){
					if(actualKeys.getForX(flip)!=null){
						resolver.register(actualKeys.getForX(flip).getId(),(Figure)actualKeys.getForX(flip));
					}
				}
			}
			
			
	}
	
	public class LocalOffsetKey extends Figure implements Key<Double>{
		// TODO: this is no figure...
		Key<Double> actualKey;
		boolean flip;
		
		public LocalOffsetKey(boolean flip,Key<Double> actualKey) {
			this.flip= flip;
			this.actualKey = actualKey;
		}
		@Override
		public void registerValue(Properties prop,Object val) {
			// TODO: do not pass rascal value around..
			
			if(prop == Properties.HLOC || prop == Properties.VLOC) {
				actualKey.registerValue(prop,val);
				return;
			}
			
			if(val instanceof IValue && (((IValue)val).getType().isNumberType() || ((IValue)val).getType().isIntegerType() || ((IValue)val).getType().isRealType())){
				
				double pval = PropertyParsers.parseNum((IValue)val);
				if(figures[where].isHLocPropertyConverted(flip)){
					double ppval = PropertyParsers.parseNum((IValue)figures[where].getHLocPropertyUnconverted(flip));
					//System.out.printf("add %f %f\n",ppval,pval);
					pval+=ppval;
				}
				actualKey.registerValue(prop,ValueFactoryFactory.getValueFactory().real(pval));
			}
		}

		@Override
		public void registerOffset(double offset) {
			//System.out.printf("Registering offset %f\n",offset);
			actualKey.registerOffset(offset);
			
		}

		@Override
		public Double scaleValue(Object val) {
			return actualKey.scaleValue(val);
		}

		@Override
		public String getId() {
			return actualKey.getId();
		}

		@Override
		public void layout() {
			
		}

		@Override
		public void draw(double left, double top) {
			
		}
		
	}


	
}
