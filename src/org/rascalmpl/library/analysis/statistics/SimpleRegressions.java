/*******************************************************************************
 * Copyright (c) 2009-2013 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/
package org.rascalmpl.library.analysis.statistics;

import org.apache.commons.math.MathException;
import org.apache.commons.math.stat.regression.SimpleRegression;
import org.eclipse.imp.pdb.facts.IListRelation;
import org.eclipse.imp.pdb.facts.INumber;
import org.eclipse.imp.pdb.facts.ITuple;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.rascalmpl.interpreter.utils.RuntimeExceptionFactory;

public class SimpleRegressions {
	
	private final IValueFactory values;
	
	public SimpleRegressions(IValueFactory values){
		super();
		this.values = values;
	}
	
	SimpleRegression make(IListRelation dataValues){
		if(dataValues.length() <= 2)
			throw RuntimeExceptionFactory.illegalArgument(dataValues, null, null, "SimpleRegression data should have more than 2 elements");
		SimpleRegression simple = new SimpleRegression();
		for(IValue v : dataValues){
			ITuple t = (ITuple) v;
			INumber x = (INumber) t.get(0);
			INumber y = (INumber) t.get(1);
			simple.addData(x.toReal().doubleValue(), y.toReal().doubleValue());
		}
		return simple;
	}

	
	public IValue intercept(IListRelation dataValues) {
		try {
			return values.real(make(dataValues).getIntercept());
		} catch(NumberFormatException e){
			throw RuntimeExceptionFactory.illegalArgument(dataValues, null, null, "Not enough variation in x values");
		}
	}
	
	public IValue interceptStdErr(IListRelation dataValues) {
		try {
			return values.real(make(dataValues).getInterceptStdErr());
		} catch(NumberFormatException e){
			throw RuntimeExceptionFactory.illegalArgument(dataValues, null, null, "Not enough variation in x values");
		}
	}
	
	public IValue meanSquareError(IListRelation dataValues) {
		try {
			return values.real(make(dataValues).getMeanSquareError());
		} catch(NumberFormatException e){
			throw RuntimeExceptionFactory.illegalArgument(dataValues, null, null, "Not enough variation in x values");
		}
	}

	public IValue R(IListRelation dataValues) {
		try {
			return values.real(make(dataValues).getR());
		} catch(NumberFormatException e){
			throw RuntimeExceptionFactory.illegalArgument(dataValues, null, null, "Not enough variation in x values");
		}
	}
	
	public IValue RSquare(IListRelation dataValues) {
		try {
			return values.real(make(dataValues).getRSquare());
		} catch(NumberFormatException e){
			throw RuntimeExceptionFactory.illegalArgument(dataValues, null, null, "Not enough variation in x values");
		}
	}
	
	public IValue regressionSumSquares(IListRelation dataValues) {
		try {
			return values.real(make(dataValues).getRegressionSumSquares());
		} catch(NumberFormatException e){
			throw RuntimeExceptionFactory.illegalArgument(dataValues, null, null, "Not enough variation in x values");
		}
	}
	
	public IValue significance(IListRelation dataValues) {
		try {
			return values.real(make(dataValues).getSignificance());
		} catch (MathException e) {
			throw RuntimeExceptionFactory.illegalArgument(dataValues, null, null, e.getMessage());
		} catch(NumberFormatException e){
			throw RuntimeExceptionFactory.illegalArgument(dataValues, null, null, "Not enough variation in x values");
		}
	}
	
	public IValue slope(IListRelation dataValues) {
		try {
			return values.real(make(dataValues).getSlope());
		} catch(NumberFormatException e){
			throw RuntimeExceptionFactory.illegalArgument(dataValues, null, null, "Not enough variation in x values");
		}
	}
	
	public IValue slopeConfidenceInterval(IListRelation dataValues) {
		try {
			return values.real(make(dataValues).getSlopeConfidenceInterval());
		} catch (MathException e) {
			throw RuntimeExceptionFactory.illegalArgument(dataValues, null, null, e.getMessage());
		} catch(NumberFormatException e){
			throw RuntimeExceptionFactory.illegalArgument(dataValues, null, null, "Not enough variation in x values");
		}
	}
	
	public IValue slopeConfidenceInterval(IListRelation dataValues, INumber alpha) {
		try {
			return values.real(make(dataValues).getSlopeConfidenceInterval(alpha.toReal().doubleValue()));
		} catch (MathException e) {
			throw RuntimeExceptionFactory.illegalArgument(dataValues, null, null, e.getMessage());
		} catch(NumberFormatException e){
			throw RuntimeExceptionFactory.illegalArgument(dataValues, null, null, "Not enough variation in x values");
		}
	}
	
	
	public IValue slopeStdErr(IListRelation dataValues) {
		try {
			return values.real(make(dataValues).getSlopeStdErr());
		} catch(NumberFormatException e){
			throw RuntimeExceptionFactory.illegalArgument(dataValues, null, null, "Not enough variation in x values");
		}
	}
	
	public IValue sumOfCrossProducts(IListRelation dataValues) {
		try {
			return values.real(make(dataValues).getSumOfCrossProducts());
		} catch(NumberFormatException e){
			throw RuntimeExceptionFactory.illegalArgument(dataValues, null, null, "Not enough variation in x values");
		}
	}
	
	public IValue sumSquaredErrors(IListRelation dataValues) {
		try {
			return values.real(make(dataValues).getSumSquaredErrors());
		} catch(NumberFormatException e){
			throw RuntimeExceptionFactory.illegalArgument(dataValues, null, null, "Not enough variation in x values");
		}
	}
	
	public IValue totalSumSquares(IListRelation dataValues) {
		try {
			return values.real(make(dataValues).getTotalSumSquares());
		} catch(NumberFormatException e){
			throw RuntimeExceptionFactory.illegalArgument(dataValues, null, null, "Not enough variation in x values");
		}
	}
	
	public IValue XSumSquares(IListRelation dataValues) {
		try {
			return values.real(make(dataValues).getXSumSquares());
		} catch(NumberFormatException e){
			throw RuntimeExceptionFactory.illegalArgument(dataValues, null, null, "Not enough variation in x values");
		}
	}
	
	public IValue predict(IListRelation dataValues, INumber x) {
		try {
			return values.real(make(dataValues).predict(x.toReal().doubleValue()));
		} catch(NumberFormatException e){
			throw RuntimeExceptionFactory.illegalArgument(dataValues, null, null, "Not enough variation in x values");
		}
	}
	
}
