/*******************************************************************************
 * Copyright (c) 2009-2011 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:

 *   * Bert Lisser - Bert.Lisser@cwi.nl (CWI)
 *   * Paul Klint - Paul.Klint@cwi.nl - CWI
 *******************************************************************************/
package org.rascalmpl.library.vis.interaction;

import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.impl.reference.ValueFactory;
import org.eclipse.imp.pdb.facts.type.TypeFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.rascalmpl.interpreter.IEvaluatorContext;
import org.rascalmpl.interpreter.result.Result;
import org.rascalmpl.library.vis.Figure;
import org.rascalmpl.library.vis.FigureApplet;
import org.rascalmpl.library.vis.IFigureApplet;
import org.rascalmpl.library.vis.properties.PropertyManager;

public class Combo extends Figure {
	// Function of type Figure (list[str]) to compute new figure
	private final IValue callback; // Function of type void() to inform backend
									// about entered text
	private final IValue validate; // Function of type bool(str) to validate
									// input sofar

	private boolean validated = true;

	private final Color trueColor;
	private final Color falseColor;

	final org.eclipse.swt.widgets.Combo combo;

	private int tLimit;

	public Combo(IFigureApplet fpa, PropertyManager properties,
			final IString text, IList choices, IValue cb, IValue validate,
			IEvaluatorContext ctx) {
		super(fpa, properties);
		// trueColor = fpa.getColor(SWT.COLOR_GREEN);
		trueColor = fpa.getRgbColor(getFontColorProperty());
		falseColor = fpa.getColor(SWT.COLOR_RED);
		combo = new org.eclipse.swt.widgets.Combo(fpa.getComp(), SWT.DROP_DOWN
				| SWT.BORDER);
		fpa.checkIfIsCallBack(cb, ctx);
		this.callback = cb;
		if (validate != null) {
			fpa.checkIfIsCallBack(validate, ctx);
		}
		this.validate = validate;
		System.err.println("callback = " + callback);

		combo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				doValidate();
			}
		});
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				try {
					doCallBack(true);
				} catch (Exception ex) {
					System.err.println("EXCEPTION");
					ex.printStackTrace();
				}
			}

			public void widgetSelected(SelectionEvent e) {
				try {
					doCallBack(false);
				} catch (Exception ex) {
					System.err.println("EXCEPTION");
					ex.printStackTrace();
				}
			}
		});
		combo.setText(text.getValue());
		double m = getWidthProperty();
		tLimit = FigureApplet.round(m / fpa.textWidth("m"));
		for (IValue val : choices) {
			String s = ((IString) val).getValue();
			combo.add(s);
			double d = fpa.textWidth(s);
			if (d > m)
				m = d;
			if (s.length()>tLimit) tLimit = s.length();
		}
		width = m + 40;

	}

	@Override
	public void bbox(double desiredWidth, double desiredHeight) {
		// Point p = combo.computeSize(FigureApplet.round(getWidthProperty()),
		// SWT.DEFAULT, true);
		Point p = combo.computeSize(FigureApplet.round(width), SWT.DEFAULT,
				true);
		width = p.x;
		height = p.y;
		combo.setTextLimit(tLimit);
		combo.setSize(FigureApplet.round(width), FigureApplet.round(height));
	}

	public boolean doValidate() {
		if (validate != null) {
			Result<IValue> res = fpa.executeRascalCallBackSingleArgument(
					validate, TypeFactory.getInstance().stringType(),
					ValueFactory.getInstance().string(combo.getText()));
			validated = res.getValue().equals(ValueFactory.getInstance().bool(true));
			return validated;
		}
		return true;
	}

	public void doCallBack(boolean isTextfield) {
		if (!validated) {
			combo.setForeground(falseColor);
			combo.redraw();
			return;
		}
		combo.setForeground(trueColor);
		if (isTextfield)
			fpa.executeRascalCallBackSingleArgument(callback, TypeFactory
					.getInstance().stringType(), ValueFactory.getInstance().string(combo.getText()));
		else {
			int s = combo.getSelectionIndex();
			if (s < 0)
				return;
			fpa.executeRascalCallBackSingleArgument(callback, TypeFactory
					.getInstance().stringType(), ValueFactory.getInstance().string(combo.getItem(s)));
		}
		fpa.setComputedValueChanged();
		fpa.redraw();
	}

	@Override
	public void draw(double left, double top) {
		this.setLeft(left);
		this.setTop(top);
		combo.setForeground(validated ? trueColor : falseColor);
		// combo.setSize(FigureApplet.round(width), FigureApplet.round(height));
		combo.setBackground(fpa.getRgbColor(getFillColorProperty()));
		combo.setLocation(FigureApplet.round(left), FigureApplet.round(top));
	}

	@Override
	public void destroy() {
		combo.dispose();
	}
}
