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

package org.rascalmpl.library.vis.properties.descriptions;

import java.util.HashMap;

import org.rascalmpl.library.vis.properties.PropertySetters;

public enum RealProp {
	HALIGN(0.5f),


	HGAP_FACTOR(0.0f),

	LINE_WIDTH(1.0f),
	TEXT_ANGLE(0.0f), 	
	FROM_ANGLE(0.0f),
	TO_ANGLE(0.0f),			
	VALIGN(0.5f),		
	
	VGAP_FACTOR(0.0f);
	
	float stdDefault;
	
	RealProp(float stdDefault){
		this.stdDefault = stdDefault;
	}

	@SuppressWarnings("serial")
	public static final HashMap<String, PropertySetters.PropertySetter<RealProp,Float>> propertySetters = new HashMap<String, PropertySetters.PropertySetter<RealProp,Float>>() {{
	put("halign", new PropertySetters.SingleRealPropertySetter(HALIGN));
	put("hgapFactor", new PropertySetters.SingleIntOrRealPropertySetter(HGAP_FACTOR));
	put("lineWidth", new PropertySetters.SingleIntOrRealPropertySetter(LINE_WIDTH));
	put("textAngle", new PropertySetters.SingleIntOrRealPropertySetter(TEXT_ANGLE));
	put("fromAngle", new PropertySetters.SingleIntOrRealPropertySetter(FROM_ANGLE));
	put("toAngle", new PropertySetters.SingleIntOrRealPropertySetter(TO_ANGLE));
	put("valign", new PropertySetters.SingleRealPropertySetter(VALIGN));
	put("vgapFactor", new PropertySetters.SingleIntOrRealPropertySetter(VGAP_FACTOR));
	// below: aliases
	put("align", new PropertySetters.DualOrRepeatSingleRealPropertySetter(HALIGN, VALIGN));
	put("gapFactor", new PropertySetters.DualOrRepeatSingleIntOrRealPropertySetter(HGAP_FACTOR, VGAP_FACTOR));
	}};

	public Float getStdDefault() {
		return stdDefault;
	}	
}