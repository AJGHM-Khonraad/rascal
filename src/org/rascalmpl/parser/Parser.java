/*******************************************************************************
 * Copyright (c) 2009-2011 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:

 *   * Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI
 *   * Tijs van der Storm - Tijs.van.der.Storm@cwi.nl
 *   * Arnold Lankamp - Arnold.Lankamp@cwi.nl
*******************************************************************************/
package org.rascalmpl.parser;

import org.rascalmpl.library.lang.rascal.syntax.RascalRascal;

public class Parser{
	public static final String START_COMMAND = "start__$Command";
	public static final String START_COMMANDS = "start__$Commands";
	public static final String START_MODULE = "start__$Module";
	public static final String START_PRE_MODULE = "start__$PreModule";
	
	private final static IParserInfo info = new RascalRascal();
	
	private Parser(){
		super();
	}

	public static IParserInfo getInfo() {
		return info;
	}
}
