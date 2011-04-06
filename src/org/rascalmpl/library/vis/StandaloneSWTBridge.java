/*******************************************************************************
 * Copyright (c) 2009-2011 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   * Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI
 *   * Paul Klint - Paul.Klint@cwi.nl - CWI
*******************************************************************************/
package org.rascalmpl.library.vis;

import java.awt.Frame;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import processing.core.PApplet;

public class StandaloneSWTBridge   {
	
	final private PApplet applet;

	public StandaloneSWTBridge (final PApplet pa){
		this.applet = pa;
		final Display display = new Display();
		final Shell shell = new Shell(display);
		final int defaultWidth = 600;
		final int defaultHeight = 600;

		shell.setSize(defaultWidth, defaultHeight);
		shell.setBounds(0, 0, defaultWidth, defaultHeight);
		shell.setLayout(new FillLayout());
		shell.setText("Rascal Visualization");
		
		ScrolledComposite sc = new ScrolledComposite(shell, SWT.H_SCROLL | SWT.V_SCROLL);
		sc.setBounds(0, 0, defaultWidth, defaultHeight);
		sc.setLayout(new FillLayout());
		sc.setAlwaysShowScrollBars(true);
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		
		// Make a composite to hold an AWT frame and put it in the ScrolledComposite
		Composite awtChild = new Composite(sc, SWT.DOUBLE_BUFFERED | SWT.EMBEDDED | SWT.NO_BACKGROUND);
		sc.setContent(awtChild);
		
		awtChild.setLayout(new FillLayout());
		awtChild.setSize(defaultWidth,defaultHeight);
		awtChild.setBounds(0, 0, defaultWidth, defaultHeight);

		Frame frame = SWT_AWT.new_Frame(awtChild); 
		frame.setLocation(0,0);
		frame.add(pa);
		pa.init();  // Initialize the PApplet
		
		frame.setVisible(true);
		frame.pack();
	
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
		pa.destroy();
	}
	
	public PApplet getApplet(){
		return applet;
	}
}
