/*******************************************************************************
 * Copyright (c) 2009-2011 CWI

 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:

 *   * Paul Klint - Paul.Klint@cwi.nl - CWI
 *   * Arnold Lankamp - Arnold.Lankamp@cwi.nl
 *******************************************************************************/
package org.rascalmpl.library.vis.swtwidgets;

import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.TypeFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.rascalmpl.library.vis.properties.PropertyManager;
import org.rascalmpl.library.vis.swt.IFigureConstructionEnv;
import org.rascalmpl.values.ValueFactoryFactory;

public class Checkbox extends Button {

	public Checkbox(IFigureConstructionEnv env, String caption, boolean checked, IValue fun,PropertyManager properties) {
		super(env, caption, fun, properties);
	}
	
	int buttonType(){
		return SWT.CHECK;
	}

	@Override
	public void executeCallback() {
		boolean selected = widget.getSelection();
		cbenv.executeRascalCallBackSingleArgument(callback, TypeFactory
				.getInstance().boolType(), ValueFactoryFactory
				.getValueFactory().bool(selected));
	}
}
