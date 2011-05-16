package org.rascalmpl.library.vis.util;

public class Coordinate {
	
	private  double x,y;
	
	public Coordinate(double x, double y,boolean flip){
		if(flip){
			this.x = y;
			this.y = x;
		} else {
			this.x = x;
			this.y = y;
		}
	}
	
	public Coordinate(double x, double y){
		this(x,y,false);
	}
	
	public double getX(){ return x; }
	public double getY() { return y; }
	public double getX(boolean flip) {
		if(flip) return y;
		else return x;
	}
	public double getY(boolean flip) {
		if(flip) return x;
		else return y;
	}
	
	public double getDimension(Dimension d){
		switch(d){
		case X: return x;
		case Y: return y;
		default: return 0;
		}
	}
	

}
