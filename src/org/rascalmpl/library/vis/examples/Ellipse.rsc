@license{
  Copyright (c) 2009-2011 CWI
  All rights reserved. This program and the accompanying materials
  are made available under the terms of the Eclipse Public License v1.0
  which accompanies this distribution, and is available at
  http://www.eclipse.org/legal/epl-v10.html
}
@contributor{Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI}
@contributor{Paul Klint - Paul.Klint@cwi.nl - CWI}
module vis::examples::Ellipse

import vis::Figure;
import vis::Render;
import Number;

import List;
import Set;
import IO;

// Ellipse of 50x100

public void e1(){
	render(ellipse(width(50), height(100)));
}

// Unsized blue ellipse with sized white inner box
public void e2(){
	render(ellipse( box(size(40), fillColor("white")),
	                fillColor("mediumblue"), gap(20)));
}

// Unsized blue ellipse with sized white inner text
public void e3(){
	render(ellipse(text("een label", fontColor("white")),
	               fillColor("mediumblue"), gap(10)));
}

// Sized ellipse with inner text that appears on mouseOver.
public void e4(){
	render(ellipse( text("een label", fontColor("white")),
	                width(40), height(20), fillColor("mediumblue"), gap(10)));
}
