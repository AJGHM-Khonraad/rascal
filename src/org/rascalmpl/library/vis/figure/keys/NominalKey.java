
package org.rascalmpl.library.vis.figure.keys;

import java.util.Vector;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.impl.fast.ValueFactory;
import org.eclipse.imp.pdb.facts.type.TypeFactory;
import org.rascalmpl.library.vis.figure.FigureFactory;
import org.rascalmpl.library.vis.figure.combine.LayoutProxy;
import org.rascalmpl.library.vis.figure.interaction.MouseOver;
import org.rascalmpl.library.vis.properties.Properties;
import org.rascalmpl.library.vis.properties.PropertyManager;
import org.rascalmpl.library.vis.swt.IFigureConstructionEnv;
import org.rascalmpl.library.vis.util.Key;
import org.rascalmpl.library.vis.util.NameResolver;


public class NominalKey extends LayoutProxy implements Key{

	IValue whole;
	IList possibilities;
	Vector<IValue> originals;
	private IList childProps;
	IValue[] tmpArray ;
	String id;
	IFigureConstructionEnv env;
	
	public NominalKey(IFigureConstructionEnv env, IList possibilties, IValue whole, PropertyManager properties,IList childProps){
		super(null,properties);
		this.env = env;
		this.childProps = childProps;
		this.whole = whole;
		this.possibilities = possibilties;
		this.originals = new Vector<IValue>(possibilties.length());
		tmpArray = new IValue[originals.size()];
		id = prop.getStr(Properties.ID);
	}
	
	public void setChildren(IFigureConstructionEnv env, NameResolver resolver) {
		super.setChildren(env,resolver);
		originals.clear();
	}
	

	public void initElem(IFigureConstructionEnv env, MouseOver mparent, boolean swtSeen, boolean visible, NameResolver resolver){
		if(innerFig != null){
			innerFig.destroy(env);
		}
		TypeFactory tf = TypeFactory.getInstance();
		IList originalsL = ValueFactory.getInstance().list(originals.toArray(tmpArray));
		IConstructor figureCons = (IConstructor) env.getCallBackEnv().executeRascalCallBackSingleArgument(whole,tf.listType(tf.valueType()),originalsL).getValue();
		innerFig = FigureFactory.make(env, figureCons, prop, childProps);
		innerFig.registerIds(env, resolver);
		innerFig.registerConverts(resolver);
		setInnerFig( innerFig);
		prop.stealExternalPropertiesFrom(innerFig.prop);
	}

	public void registerValue(IValue val) {
		for(int i = 0 ; i < originals.size() ; i++ ){
			if(originals.get(i).isEqual(val)){
				return ;
			}
		}
		if(originals.size()  < possibilities.length()){
			originals.add(val);
		} 
	}
	
	public IValue scaleValue(IValue val) {
		
		for(int i = 0 ; i < originals.size()  ; i++){
			if(originals.get(i).isEqual((IValue)val)){
				return possibilities.get(i);
			}
		}
		return possibilities.get(0);
	}
	
	public String getId() {
		return id;
	}
}
