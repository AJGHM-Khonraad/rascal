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
package org.rascalmpl.library.vis.interaction;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;
import org.eclipse.imp.pdb.facts.type.TypeFactory;
import org.rascalmpl.interpreter.IEvaluatorContext;
import org.rascalmpl.interpreter.result.RascalFunction;
import org.rascalmpl.interpreter.utils.RuntimeExceptionFactory;
import org.rascalmpl.library.vis.Figure;
import org.rascalmpl.library.vis.IFigureApplet;
import org.rascalmpl.library.vis.properties.PropertyManager;

import org.rascalmpl.library.vis.FigureApplet;

public class Choice extends Figure {
	
	final private RascalFunction callback;
	
	final Type[] argTypes = new Type[1];			// Argument types of callback: [str]
	final IValue[] argVals = new IValue[1];		    // Argument values of callback: [str]
	
	final java.awt.Choice choice = new java.awt.Choice();

	public Choice(IFigureApplet fpa, PropertyManager properties, IList choices, IValue fun, IEvaluatorContext ctx) {
		super(fpa, properties);
		
		if(fun.getType().isExternalType() && (fun instanceof RascalFunction)){
			this.callback = (RascalFunction) fun;
		} else {
			 RuntimeExceptionFactory.illegalArgument(fun, ctx.getCurrentAST(), ctx.getStackTrace());
			 this.callback = null;
		}
		
		TypeFactory tf = TypeFactory.getInstance();
		argTypes[0] = tf.stringType();
		argVals[0] = vf.string("");
		
		for(IValue val : choices){
			choice.add(((IString)val).getValue());
		}
		
	    choice.addItemListener(
	    	      new ItemListener() {
	    	        public void itemStateChanged(ItemEvent e) {
	    	          try {
	    	        	  choice.getParent().invalidate();
	    	        	  doCallBack((String) e.getItem());
	    	          } catch (Exception ex) {
	    	        	  System.err.println("EXCEPTION");
	    	            ex.printStackTrace();
	    	          }
	    	        }
	    	      });
	    choice.setBackground(new Color(0));
	    fpa.add(choice);
	}

	@Override
	public void bbox(double desiredWidth, double desiredHeight) {
		width = choice.getWidth();
		height = choice.getHeight();
	}
	
	public void doCallBack(String s){
		argVals[0] = vf.string(s);
		fpa.setCursor(new Cursor(java.awt.Cursor.WAIT_CURSOR));
		synchronized(fpa){
			callback.call(argTypes, argVals);
		}
		fpa.setCursor(new Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		fpa.setComputedValueChanged();
	}

	@Override
	public void draw(double left, double top) {
		this.setLeft(left);
		this.setTop(top);
		fpa.setBackground(new Color(getFillColorProperty()));
		choice.setBackground(new Color(getFillColorProperty()));
		choice.setLocation(FigureApplet.round(left), FigureApplet.round(top));
	}
	
	@Override
	public void destroy(){
		fpa.remove(choice);
		fpa.invalidate();
		fpa.setComputedValueChanged();
	}

}
