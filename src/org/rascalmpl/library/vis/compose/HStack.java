package org.rascalmpl.library.vis.compose;

import org.eclipse.imp.pdb.facts.IValue;
import org.rascalmpl.interpreter.IEvaluatorContext;
import org.rascalmpl.interpreter.utils.RuntimeExceptionFactory;
import org.rascalmpl.library.vis.Figure;
import org.rascalmpl.library.vis.properties.Properties;
import org.rascalmpl.library.vis.properties.PropertyManager;
import org.rascalmpl.library.vis.properties.PropertyParsers;
import org.rascalmpl.library.vis.swt.IFigureConstructionEnv;
import org.rascalmpl.library.vis.util.Key;
import org.rascalmpl.library.vis.util.NameResolver;
import org.rascalmpl.values.ValueFactoryFactory;

public class HStack extends Compose implements Key{

	boolean flip;
	IEvaluatorContext ctx;
	Key actualKey;
	double stackState;
	
	public HStack(IFigureConstructionEnv env,boolean flip, Figure[] figures,PropertyManager properties) {
		super( figures, properties);
		this.flip = flip;
		this.ctx = env.getRascalContext();
	}
	
	public void init(){
		stackState = 0.0;
		super.init();
	}
	
	public void bbox(){
		minSize.clear();
		for(Figure fig : figures){
			fig.bbox();
			minSize.setWidth(flip, Math.max(minSize.getWidth(flip),fig.minSize.getWidth(flip)));
			minSize.addHeight(flip, minSize.getHeight(flip));
		}
		setResizable();
	}
	
	public void layout(){
		
		double left = 0;
		if(flip){
			left = size.getWidth(flip);
		}
		for(int i = 0 ; i < figures.length ; i++){
			if(flip){
				left -= figures[i].getWidthProperty(flip);
			}
			//System.out.printf("Jahoor2 %f..\n",left);
			pos[i].setX(flip, left);
			pos[i].setY(flip,0);
			figures[i].globalLocation.setX(flip,globalLocation.getX(flip) + left);
			figures[i].globalLocation.setY(flip,globalLocation.getX(flip) + pos[i].getY());
			//System.out.printf("Setting height to %f\n", size.getHeight(flip));
			figures[i].takeDesiredHeight(flip, size.getHeight(flip));
			//System.out.printf("Got height to %f\n", figures[i].size.getHeight(flip));
			figures[i].takeDesiredWidth(flip,  size.getWidth(flip));
			figures[i].layout();
			if(!flip){
				left+= figures[i].size.getWidth(flip);
			}
		}
	}
	

	public void registerValues(NameResolver resolver){
		
		properties.registerMeasures(resolver);
		if(figures.length > 0){
			String actualKeyId = figures[0].getKeyIdForWidth(flip);
			if(actualKeyId == null){
				System.out.print("CHRASSHH!!!\n");
				throw RuntimeExceptionFactory.figureException("Stack: height/width of first element does not convert to key!", ctx.getValueFactory().string(""), ctx.getCurrentAST(),
						ctx.getStackTrace());
			} 
			//System.out.printf("Changing key %s..\n",actualKeyId);
			actualKey = (Key)resolver.resolve(actualKeyId);
			resolver.register(actualKeyId,this);
			for(Figure fig : figures){
				fig.registerValues(resolver);
			}
			//System.out.printf("Restoring key %s..\n",actualKeyId);
			resolver.register(actualKeyId,(Figure)actualKey);
		}
		
		
	}
	
	public void registerValue(Properties prop,IValue val) {
		if((val.getType().isNumberType() || val.getType().isIntegerType() || (val).getType().isRealType())){
			
			double pval = PropertyParsers.parseNum((IValue)val);
			stackState+=pval;
			//System.out.printf("Registering at stack %f\n",stackState);
			actualKey.registerValue(prop,ValueFactoryFactory.getValueFactory().real(stackState));
		}
	}

	public IValue scaleValue(IValue val) {
		//System.out.printf("Scaling %f\n",actualKey.scaleValue(val));
		return actualKey.scaleValue(val);
	}

	public String getId() {
		return getIdProperty();
	}

	public void registerOffset(double offset) {
		actualKey.registerOffset(offset);
	}

}
