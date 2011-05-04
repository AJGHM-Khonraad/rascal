package org.rascalmpl.library.vis.compose;

import org.eclipse.imp.pdb.facts.IList;
import org.rascalmpl.interpreter.IEvaluatorContext;
import org.rascalmpl.library.vis.Extremes;
import org.rascalmpl.library.vis.Figure;
import org.rascalmpl.library.vis.IFigureApplet;
import org.rascalmpl.library.vis.properties.PropertyManager;
import org.rascalmpl.library.vis.properties.descriptions.MeasureProp;
import org.rascalmpl.library.vis.properties.descriptions.RealProp;

public class HCat extends Compose {

	float gapSize;
	float numberOfGaps;
	float minTopAnchor = Float.MAX_VALUE;
	private float maxTopAnchor;
	private static boolean debug = true;
	
	boolean isWidthPropertySet, isHeightPropertySet, isHGapPropertySet, isHGapFactorPropertySet;
	float getWidthProperty, getHeightProperty, getHGapProperty, getHGapFactorProperty, getValignProperty;

	
	public HCat(IFigureApplet fpa, PropertyManager properties, IList elems,  IList childProps,  IEvaluatorContext ctx) {
		super(fpa, properties, elems, childProps, ctx);
	}
	
	void setProperties(){
		isWidthPropertySet = isWidthPropertySet();
		isHeightPropertySet = isHeightPropertySet();
		isHGapPropertySet = isHGapPropertySet();
		isHGapFactorPropertySet = isHGapFactorPropertySet();
		
		getWidthProperty = getWidthProperty();
		getHeightProperty = getHeightProperty();
		getHGapProperty = getHGapProperty();
		getHGapFactorProperty = getHGapFactorProperty();
	}
	
	float getFigureWidth(Figure fig){ return fig.width; }
	float getFigureHeight(Figure fig){return fig.height;}
	float getTopAnchor(Figure fig){return fig.topAlign();}
	float getTopAnchorProperty(Figure fig){return fig.getRealProperty(RealProp.VALIGN);}
	float getBottomAnchor(Figure fig){return fig.bottomAlign();}
	void  drawFigure(Figure fig,float left,float top,float leftBase,float topBase){
		fig.draw(leftBase + left, topBase + top);
	}
	void  bboxOfFigure(Figure fig,float desiredWidth,float desiredHeight){ fig.bbox(desiredWidth, desiredHeight);}
	float getHeight(){return height;}
	
	public
	void bbox(float desiredWidth, float desiredHeight){
		width = height = 0;
		gapSize = getHGapProperty;
		numberOfGaps = (figures.length - 1);
		setProperties();
		if(getStartGapProperty()){numberOfGaps+=0.5f;} 
		if(getEndGapProperty()){numberOfGaps+=0.5f;} 
		if(isWidthPropertySet) desiredWidth = getWidthProperty ;
		if(isHeightPropertySet) desiredHeight = getHeightProperty ;
		float desiredWidthPerElement, desiredWidthOfElements, desiredHeightPerElement, gapsSize;
		gapsSize = 0.0f; // stops compiler from whining
		if(isHGapPropertySet && !isHGapFactorPropertySet){
			gapsSize = getHGapProperty ;
		}
		// determine desired width of elements 
		if(desiredWidth == Figure.AUTO_SIZE){
			desiredWidthOfElements = desiredWidthPerElement = Figure.AUTO_SIZE;
		} else {
			if(isHGapFactorPropertySet || !isHGapPropertySet){
				gapsSize = desiredWidth * getHGapFactorProperty;
			}
			desiredWidthOfElements = desiredWidth - gapsSize;
			desiredWidthPerElement = desiredWidthOfElements / figures.length;
		}
		// deterine desired height of elements
		if(desiredHeight == Figure.AUTO_SIZE){
			desiredHeightPerElement = Figure.AUTO_SIZE;
		} else {
			float minTopAnchorProp, maxTopAnchorProp;
			minTopAnchorProp = Float.MAX_VALUE;
			maxTopAnchorProp = 0.0f;
			for(Figure fig : figures){
				minTopAnchorProp = min(minTopAnchorProp,getTopAnchorProperty(fig));
				maxTopAnchorProp = max(maxTopAnchorProp,getTopAnchorProperty(fig));
			}
			float maxDiffTopAnchorProp = maxTopAnchorProp - minTopAnchorProp;
			// first assume all elements will get desiredheight
			// rewrote : desiredHeight = desiredHeightPerElement + maxDiffTopAnchorProp* desiredHeightPerElement
			desiredHeightPerElement = desiredHeight / (1 + maxDiffTopAnchorProp);
		}
		// determine width and bounding box elements and which are non-resizeable
		int numberOfNonResizeableWidthElements = 0;
		float totalNonResizeableWidth = 0;
		boolean[] mayBeResized = new boolean[figures.length];
		for(int i = 0 ; i < figures.length ; i++){
			mayBeResized[i] = true;
		}
		
		// assume width and height are independent(!)
		boolean fixPointReached;
		// fixpoint computation to set width of figures...
		do{
			fixPointReached = true;
			for(int i = 0 ; i < figures.length; i++){
				if(mayBeResized[i]){
					bboxOfFigure(figures[i],desiredWidthPerElement,desiredHeightPerElement);
					if(desiredWidthPerElement != Figure.AUTO_SIZE 
							&& getFigureWidth(figures[i]) != desiredWidthPerElement ){
						totalNonResizeableWidth+=getFigureWidth(figures[i]);
						numberOfNonResizeableWidthElements++;
						mayBeResized[i]=false;
						fixPointReached = false;
					} 
				}
			}
			// recompute width of resizeable elements
			if(numberOfNonResizeableWidthElements > 0 && desiredWidth!=Figure.AUTO_SIZE){
				desiredWidthPerElement = (desiredWidthOfElements - totalNonResizeableWidth) 
				                              / (figures.length - numberOfNonResizeableWidthElements);
			}
		} while(!fixPointReached);
		// Fixpoint for height depending on alignment?
		if(desiredHeight != AUTO_SIZE){
			float maxTopAnchor, maxBottomAnchor;
			float maxTopAnchorR, maxBottomAnchorR;
			maxTopAnchor = maxBottomAnchor = maxTopAnchorR = maxBottomAnchorR =  0.0f;
			fixPointReached = true;
			for(int i = 0 ; i < figures.length ; i++){
				mayBeResized[i] = figures[i].height == desiredHeightPerElement;
				if(!mayBeResized[i]){
					maxTopAnchor = max(maxTopAnchor,getTopAnchor(figures[i]));
					maxBottomAnchor = max(maxBottomAnchor,getBottomAnchor(figures[i]));
					fixPointReached = false;
				} else {
					maxTopAnchorR =  max(maxTopAnchorR,getTopAnchor(figures[i]));
					maxBottomAnchorR = max(maxBottomAnchorR,getBottomAnchor(figures[i]));
				}
			}
			while(!fixPointReached){
				fixPointReached = true;
				float spaceForResize = desiredHeight - (maxTopAnchor + maxBottomAnchor);
				float totalHeightNow = max(maxTopAnchor,maxTopAnchorR) + max(maxBottomAnchor,maxBottomAnchorR);
				float topExtraSpacePart, bottomExtraSpacePart;
				
				topExtraSpacePart = max(0,(maxTopAnchorR - maxTopAnchor) / totalHeightNow);
				bottomExtraSpacePart = max(0,(maxBottomAnchorR - maxBottomAnchor) / totalHeightNow);
				if(topExtraSpacePart + bottomExtraSpacePart == 0){
					// cannot fit!
					break;
				}
				float topCap = (topExtraSpacePart / (topExtraSpacePart + bottomExtraSpacePart)) * spaceForResize + maxTopAnchor;
				float bottomCap = (bottomExtraSpacePart / (topExtraSpacePart + bottomExtraSpacePart)) * spaceForResize + maxBottomAnchor;
				for(int i = 0 ; i < figures.length ; i++){
					if(mayBeResized[i]){
						float topAdjust = min(getTopAnchor(figures[i]), topCap);
						float bottomAdjust = min(getBottomAnchor(figures[i]), bottomCap);
						float desiredHeightNow;
						if(getTopAnchorProperty(figures[i]) == 0.0f){
							desiredHeightNow = bottomAdjust /( 1 - getTopAnchorProperty(figures[i]));
						} else if (getTopAnchorProperty(figures[i]) == 1.0f){
							desiredHeightNow = topAdjust / getTopAnchorProperty(figures[i]);
						} else {
							desiredHeightNow = min(topAdjust / getTopAnchorProperty(figures[i]),
												  bottomAdjust /( 1 - getTopAnchorProperty(figures[i])));
						}
						bboxOfFigure(figures[i],desiredWidthPerElement,desiredHeightNow);
						mayBeResized[i] = figures[i].height == desiredHeightNow;
						if(!mayBeResized[i]){
							maxTopAnchor = max(maxTopAnchor,getTopAnchor(figures[i]));
							maxBottomAnchor = max(maxBottomAnchor,getBottomAnchor(figures[i]));
							fixPointReached = false;
						} else {
							maxTopAnchorR =  max(maxTopAnchorR,getTopAnchor(figures[i]));
							maxBottomAnchorR = max(maxBottomAnchorR,getBottomAnchor(figures[i]));
						}
					}
				}
				
			}
		}
		float totalElementsWidth = 0;
		float maxBottomAnchor = 0.0f;
		maxTopAnchor = 0.0f;
		minTopAnchor = Float.MAX_VALUE;
		for(Figure fig : figures){
			totalElementsWidth += getFigureWidth(fig);
			maxBottomAnchor = max(maxBottomAnchor,getBottomAnchor(fig));
			maxTopAnchor = max(maxTopAnchor,getTopAnchor(fig));
			minTopAnchor = min(minTopAnchor,getTopAnchor(fig));
		}
		if(desiredWidth != Figure.AUTO_SIZE && 
		   (int)(totalElementsWidth + gapsSize + 0.5f) > (int)(desiredWidth + 0.5f)){ // prevent round-off cannot fit error
			if(debug) if(debug)System.err.printf("Cannot fit!");
			gapsSize = max(desiredWidth -totalElementsWidth,0.0f);
		}
		// compute gap if auto-size and ratio
		if(desiredWidth==Figure.AUTO_SIZE && (isHGapFactorPropertySet || !isHGapPropertySet)){
			// the next formula can be obtained by rewriting hGapFactor = gapsSize / (totalElementsWidth + gapsSize) 
			gapsSize = totalElementsWidth /  (1/getHGapFactorProperty - 1);
		}
		width = totalElementsWidth + gapsSize;
		gapSize = gapsSize / numberOfGaps;
		
		height =  maxTopAnchor + maxBottomAnchor;
		System.out.printf("height %f maxTopAnchor %f maxBottomAnchor %f\n", height,maxTopAnchor, maxBottomAnchor);
		determinePlacement();
		System.out.printf("Done bbox!\n");
	}

	private void determinePlacement() {
		float left, top;
		left = top = 0.0f;
		if(getStartGapProperty()){
			left+=0.5*gapSize;
		}
			// Draw from left to right
		for(int i = 0 ; i < figures.length ; i++){
			xPos[i] = left;
			yPos[i] = top - getTopAnchor(figures[i]) + maxTopAnchor;
			left += getFigureWidth(figures[i]) + gapSize;
		}
	}	
	
	public Extremes getExtremesForAxis(String axisId, float offset, boolean horizontal){
		if(horizontal && getMeasureProperty(MeasureProp.WIDTH).axisName.equals(axisId)){
			float val = getMeasureProperty(MeasureProp.WIDTH).value;
			return new Extremes(offset - getHAlignProperty() * val, offset + (1-getHAlignProperty()) * val);
		} else if( !horizontal && getMeasureProperty(MeasureProp.HEIGHT).axisName.equals(axisId)){
			float val = getMeasureProperty(MeasureProp.HEIGHT).value;
			return new Extremes(offset - getVAlignProperty() * val, offset + (1-getVAlignProperty()) * val);
		} else {
			Extremes[] extremesList = new Extremes[figures.length];
			for(int i = 0 ; i < figures.length ; i++){
				extremesList[i] = figures[i].getExtremesForAxis(axisId, offset, horizontal);
				System.out.printf("Got extreme %s %f %f\n", extremesList[i], extremesList[i].getMinimum(), extremesList[i].getMaximum());
				if(correctOrientation(horizontal) && gapSize == 0 && extremesList[i].gotData()){
					offset += extremesList[i].getMaximum();
				}
			}
			return Extremes.merge(extremesList);
		}
	}
	
	protected boolean correctOrientation(boolean horizontal){
		return horizontal;
	}
	

	
}
