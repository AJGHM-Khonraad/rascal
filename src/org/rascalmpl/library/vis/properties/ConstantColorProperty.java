package org.rascalmpl.library.vis.properties;

public class ConstantColorProperty implements IColorPropertyValue {
	Property property;
	int value;

	public ConstantColorProperty(Property prop, int val){
		this.property = prop;
		this.value = val;
	}
	
	public Property getProperty(){
		return property;
	}
	
	public int getValue() {
		return value;
	}

	public boolean usesTrigger() {
		return false;
	}

}
