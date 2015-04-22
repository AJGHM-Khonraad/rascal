/*******************************************************************************
 * Copyright (c) 2015 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   * Jurgen J. Vinju - Jurgen.Vinju@cwi.nl
*******************************************************************************/
package org.rascalmpl.values.uptr;

import java.util.Map;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IMap;
import org.eclipse.imp.pdb.facts.ISet;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.eclipse.imp.pdb.facts.type.TypeStore;
import org.rascalmpl.parser.gtd.util.ArrayList;

/**
 * See {@link RascalValueFactory} for documentation.
 */
public interface IRascalValueFactory extends IValueFactory {
	IConstructor reifiedType(IConstructor symbol, IMap definitions);
	
	IConstructor appl(Map<String,IValue> annos, IConstructor prod, IList args);
	IConstructor appl(IConstructor prod, IList args);
	IConstructor appl(IConstructor prod, IValue... args);
	@Deprecated IConstructor appl(IConstructor prod, ArrayList<IConstructor> args);
	
	IConstructor cycle(IConstructor symbol, int cycleLength);

	IConstructor amb(ISet alternatives);
	
	IConstructor character(int ch);
	
	IConstructor character(byte ch);
	
	static IRascalValueFactory getInstance() {
		return RascalValueFactory.getInstance();
	}
}
