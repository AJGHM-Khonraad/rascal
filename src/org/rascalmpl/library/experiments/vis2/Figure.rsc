module experiments::vis2::Figure

import util::Math;
import List;
import Set;
import IO;
import String;
import ToString;

import  experiments::vis2::Properties;

/*
 * Figure: a visual element, the principal visualization datatype
 * Note: for experimentation purposes this is a small extract from the real thing: vis/Figure.rsc
 */
 
public alias Figures = list[Figure];

public data Figure = 
/* atomic primitives */
	
     _text(value v, FProperties props)		    // text label
   
/* primitives/containers */

   | _box(FProperties props)			        // rectangular box
   | _box(Figure inner, FProperties props)      // rectangular box with inner element
   
   | _ellipse(FProperties props)                // ellipse with inner element
   | _ellipse(Figure inner, FProperties props)  // ellipse with inner element
                   
   | _hcat(Figures figs, FProperties props) 	// horizontal and vertical concatenation
   | _vcat(Figures figs, FProperties props) 	// horizontal and vertical concatenation
                   
   | _overlay(Figures figs, FProperties props)	// overlay (stacked) composition

// charts
   
   | _barchart(FProperties props)
   | _scatterplot(FProperties props)
   
 // graph
   | _graph(Figures nodes, Edges edges, FProperties props)
   | _texteditor(FProperties props)
   
// interaction

   | _buttonInput(str trueText, str falseText, FProperties props)
   
   | _checkboxInput(FProperties props)

   | _strInput(FProperties props)
   
   | _numInput(FProperties props)
   
   | _colorInput(FProperties props)
   
   | _rangeInput(int low, int high, int step, FProperties props)
   

// visibility control

   | _visible(bool yes, Figure fig, FProperties props)
   
   | _fswitch(int sel, Figures figs, FProperties props)
   
   

// TODO   
/*
   | _mouseOver(Figure under, Figure over,FProperties props)   
       
   | _computeFigure(bool() recomp,Figure () computeFig, FProperties props)
 
   | _combo(list[str] choices, Def d, FProperties props)
   
   | _choice(list[str] choices, Def d, FProperties props)
   
   | _checkbox(str text, bool checked, Def d, FProperties props)
*/
   ;
 
data Edge =			 							// edge between between two elements in complex shapes like tree or graph
     _edge(int from, int to, FProperties props)
   ;
   
public alias Edges = list[Edge];
   
public Edge edge(int from, int to, FProperty props ...){
  return _edge(from, to, props);
}

public Figure text(value v, FProperty props ...){
  return _text(v, props);
}

public Figure box(FProperty props ...){
  return _box(props);
}

public Figure box(Figure fig, FProperty props ...){
  return _box(fig, props);
}

public Figure hcat(Figures figs, FProperty props ...){
  return _hcat(figs,props);
}

public Figure vcat(Figures figs, FProperty props ...){
  return _vcat(figs,props);
}

public Figure graph(Figures nodes, Edges edges, FProperty props...){
	return _graph(nodes, edges, props);
}

public Figure hvcat(Figures figs, FProperty props ...){
  return _widthDepsHeight(_hvcat(figs, props),[]);
}

public Figure barchart(FProperty props ...){
  return _barchart(props);
}

public Figure scatterplot(FProperty props ...){
  return _scatterplot(props);
}

public Figure texteditor(FProperty props ...){
  return _texteditor(props);
}

public Figure strInput(FProperty props ...){
  return _strInput(props);
}

public Figure numInput(FProperty props ...){
  return _numInput(props);
}

public Figure colorInput(FProperty props ...){
  return _colorInput(props);
}

public Figure buttonInput(str trueText, str falseText, FProperty props ...){
  return _buttonInput(trueText, falseText, props);
}

public Figure checkboxInput(FProperty props ...){
  return _checkboxInput(props);
}

public Figure fswitch(int sel, Figures figs, FProperty props ...){
 	return _fswitch(sel, figs, props);
}

public Figure visible(bool vis, Figure fig, FProperty props ...){
 	return _visible(vis, fig, props);
}

public Figure rangeInput(int low, int high, int step, FProperty props...){
   return _rangeInput(low, high, step, props);
}
