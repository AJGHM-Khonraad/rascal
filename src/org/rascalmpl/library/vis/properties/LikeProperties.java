/*******************************************************************************
 * Copyright (c) 2009-2011 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   * Paul Klint - Paul.Klint@cwi.nl - CWI
 *   * Atze van der Ploeg - Atze.van.der.Ploeg@cwi.nl - CWI
*******************************************************************************/

package org.rascalmpl.library.vis.properties;

import org.rascalmpl.interpreter.IEvaluatorContext;
import org.rascalmpl.library.vis.Figure;
import org.rascalmpl.library.vis.IFigureExecutionEnvironment;
import org.rascalmpl.library.vis.util.NameResolver;

public class LikeProperties {

	private static abstract class LikeProperty<PropType> extends PropertyValue<PropType> {
		Figure fig;
		Properties property;
		String path;
		
		public LikeProperty(Properties property,String path, IFigureExecutionEnvironment fpa, IEvaluatorContext ctx){
			super(property);
			this.path = path;
			this.property = property;
		}
		
		public void getLikes(NameResolver resolver){
			fig = resolver.resolve(path);
		}
		
		public abstract PropType getValue() ;
	}
	
	
	static class LikeBooleanProperty extends LikeProperty<Boolean>{
		
		public LikeBooleanProperty(Properties property, String id,
				IFigureExecutionEnvironment fpa, IEvaluatorContext ctx) {
			super(property, id, fpa, ctx);
		}


		Properties property;
		

		@Override
		public Boolean getValue() {
			return fig.properties.getBooleanProperty(property);
		}
	}
	
	static class LikeIntegerProperty extends LikeProperty<Integer>{
		
		public LikeIntegerProperty(Properties property, String id, IFigureExecutionEnvironment fpa,
				IEvaluatorContext ctx) {
			super(property, id, fpa, ctx);
		}

		@Override
		public Integer getValue() {
			return fig.properties.getIntegerProperty(property);
		}
	}
	
	static class LikeColorProperty extends LikeProperty<Integer>{

		public LikeColorProperty(Properties property, String id, IFigureExecutionEnvironment fpa,
				IEvaluatorContext ctx) {
			super(property, id, fpa, ctx);
		}
		
		@Override
		public Integer getValue() {
			return fig.properties.getColorProperty(property);
		}
	}
	
	static class LikeRealProperty extends LikeProperty<Double>{
		
		public LikeRealProperty(Properties property, String id, IFigureExecutionEnvironment fpa,
				IEvaluatorContext ctx) {
			super(property, id, fpa, ctx);
		}

		@Override
		public Double getValue() {
			return fig.properties.getRealProperty(property);
		}
	}
	
	static class LikeStringProperty extends LikeProperty<String>{
		
		public LikeStringProperty(Properties property, String id, IFigureExecutionEnvironment fpa,
				IEvaluatorContext ctx) {
			super(property, id, fpa, ctx);
		}

		@Override
		public String getValue() {
			return fig.properties.getStringProperty(property);
		}
	}
	
	static class LikeFigureProperty extends LikeProperty<Figure>{
		
		public LikeFigureProperty(Properties property,String id, IFigureExecutionEnvironment fpa,
				IEvaluatorContext ctx) {
			super(property, id, fpa, ctx);
		}

		@Override
		public Figure getValue() {
			Figure res = fig.getFigureProperty(property);
			return res;
		}
	}

}
