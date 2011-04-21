@license{
  Copyright (c) 2009-2011 CWI
  All rights reserved. This program and the accompanying materials
  are made available under the terms of the Eclipse Public License v1.0
  which accompanies this distribution, and is available at
  http://www.eclipse.org/legal/epl-v10.html
}
@contributor{Paul Klint - Paul.Klint@cwi.nl - CWI}
module vis::Figure

import Integer;
import Real;
import List;
import Set;
import IO;

/*
 * Declarations and library functions for Rascal Visualization
 *
 * There are several sources of ugliness in the following definitions:
 * - data declarations cannot have varyadic parameters, hence we need a wrapper function for each constructor.
 * - Alternatives of a data declaration always need a constructor, hence many constructors have to be duplicated.
 * - We are awaiting the intro of key word parameters.
 */
 
/*
 * Wishlist:
 * - textures
 * - boxes with round corners
 * - drop shadows
 * - dashed/dotted lines
 * - ngons
 * - bitmap import and display
 * - new layouts (circular) treemap, icecle
 * - interaction
 */
 
 /*
  * Colors and color management
  */

alias Color = int;

/*
 * Decorations for source code lines:
 * - info, warning and error
 * - highlights (levels [0 .. 4] currently supported)
 * Used by:
 * - Outline
 * - editor
 */

public data LineDecoration = 
    info(int lineNumber, str msg)
  | warning(int lineNumber, str msg)
  | error(int lineNumber, str msg)
  | highlight(int lineNumber, str msg)
  | highlight(int lineNumber, str msg, int level)
  ;

@doc{Gray color (0-255)}
@javaClass{org.rascalmpl.library.vis.FigureColorUtils}
public Color java gray(int gray);

@doc{Gray color (0-255) with transparency}
@javaClass{org.rascalmpl.library.vis.FigureColorUtils}
public Color java gray(int gray, real alpha);

@doc{Gray color as percentage (0.0-1.0)}
@javaClass{org.rascalmpl.library.vis.FigureColorUtils}
public Color java gray(real perc);

@doc{Gray color with transparency}
@javaClass{org.rascalmpl.library.vis.FigureColorUtils}
public Color java gray(real perc, real alpha);

@doc{Named color}
@reflect{Needs calling context when generating an exception}
@javaClass{org.rascalmpl.library.vis.FigureColorUtils}
public Color java color(str colorName);

@doc{Named color with transparency}
@reflect{Needs calling context when generating an exception}
@javaClass{org.rascalmpl.library.vis.FigureColorUtils}
public Color java color(str colorName, real alpha);

@doc{Sorted list of all color names}
@javaClass{org.rascalmpl.library.vis.FigureColorUtils}
public list[str] java colorNames();

@doc{RGB color}
@javaClass{org.rascalmpl.library.vis.FigureColorUtils}
public Color java rgb(int r, int g, int b);

@doc{RGB color with transparency}
@javaClass{org.rascalmpl.library.vis.FigureColorUtils}
public Color java rgb(int r, int g, int b, real alpha);

@doc{Interpolate two colors (in RGB space)}
@javaClass{org.rascalmpl.library.vis.FigureColorUtils}
public Color java interpolateColor(Color from, Color to, real percentage);

@doc{Create a list of interpolated colors}
@javaClass{org.rascalmpl.library.vis.FigureColorUtils}
public list[Color] java colorSteps(Color from, Color to, int steps);

@doc{Create a colorscale from a list of numbers}
public Color(&T <: num) colorScale(list[&T <: num] values, Color from, Color to){
   mn = min(values);
   range = max(values) - mn;
   sc = colorSteps(from, to, 10);
   return Color(int v) { return sc[(9 * (v - mn)) / range]; };
}

@doc{Create a fixed color palette}
public list[str] p12 = [ "navy", "violet", "yellow", "aqua", 
                          "red", "darkviolet", "maroon", "green",
                          "teal", "blue", "olive", "lime"];
                          
public list[Color] p12Colors = [color(s) | s <- p12];

@doc{Return named color from fixed palette}
public str palette(int n){
  try 
  	return p12[n];
  catch:
    return "black";
}



@doc{Create a list of font names}
@javaClass{org.rascalmpl.library.vis.FigureLibrary}
public list[str] java fontNames();

/*
 * FProperty -- visual properties of visual elements
 */
 
 public FProperty left(){
   return halign(0.0);
 }
 
 public FProperty hcenter(){
   return halign(0.5);
 }
 
 public FProperty right(){
   return halign(1.0);
 }
 
 public FProperty top(){
   return valign(0.0);
 }
 
 public FProperty vcenter(){
   return valign(0.5);
 }
 
 public FProperty bottom(){
   return valign(1.0);
 }
 
 public FProperty center(){
   return align(0.5, 0.5);
}


 public FProperty stdLeft(){
   return stdHalign(0.0);
 }
 
 public FProperty stdHcenter(){
   return stdHalign(0.5);
 }
 
 public FProperty stdRight(){
   return stdHalign(1.0);
 }
 
 public FProperty stdTop(){
   return stdValign(0.0);
 }
 
 public FProperty stdVcenter(){
   return stdValign(0.5);
 }
 
 public FProperty stdBottom(){
   return stdValign(1.0);
 }
 
 public FProperty stdCenter(){
   return stdAlign(0.5, 0.5);
}


 public FProperty projectLeft(){
   return projectHalign(0.0);
 }
 
 public FProperty projectHcenter(){
   return projectHalign(0.5);
 }
 
 public FProperty projectRight(){
   return projectHalign(1.0);
 }
 
 public FProperty projectTop(){
   return projectValign(0.0);
 }
 
 public FProperty projectVcenter(){
   return projectValign(0.5);
 }
 
 public FProperty projectBottom(){
   return projectValign(1.0);
 }
 
 public FProperty projectCenter(){
   return projectValign(0.5, 0.5);
}


 public FProperty stdProjectLeft(){
   return stdProjectHalign(0.0);
 }
 
 public FProperty stdProjectHcenter(){
   return stdProjectHalign(0.5);
 }
 
 public FProperty stdProjectRight(){
   return stdProjectHalign(1.0);
 }
 
 public FProperty stdProjectTop(){
   return stdProjectValign(0.0);
 }
 
 public FProperty stdProjectVcenter(){
   return stdProjectValign(0.5);
 }
 
 public FProperty stdProjectBottom(){
   return stdProjectValign(1.0);
 }
 
 public FProperty stdProjectCenter(){
   return stdProjectValign(0.5, 0.5);
}

   
 
 alias computedBool = bool();
 alias computedInt	= int();
 alias computedReal = real();
 alias computedNum 	= num();
 alias computedStr 	= str();
 alias computedColor = Color();
 alias computedFigure = Figure();
 
 data Like = like(str id);
 
public alias FProperties = list[FProperty];


data FProperty =
/* sizes */
     width(num width)                   // sets width of element
   | width(computedNum cWidth)         // sets width of element
   | width(Like other)
   | height(num height)                 // sets height of element
   | height(computedNum cHeight)       // sets height of element
   | height(Like other)
   | size(num size)					    // sets width and height to same value
   | size(computedNum cSize)			// sets width and height to same value
   | size(Like other)
   
   | size(num width, num height)            // sets width and height to separate values
//   | size(computedNum cWidth, computedNum cHeight)  // sets width and height to separate values
   
   | gap(num amount)                    // sets hor and vert gap between elements in composition to same value
   | gap(computedNum cAmount) 
   | gap(Like other) 
   
   | gap(num width, num height) 			// sets hor and vert gap between elements in composition to separate values
 //  | gap(computedNum cWidth, computedNum cHeight) 
   
   | gapFactor(num amount)
   | gapFactor(computedNum cAmount)
   | gapFactor(Like other)
   | gapFactor(num hfactor,num vfactor)
   
   | hgap(num width)                      // sets hor gap
   | hgap(computedNum cWidth)
   | hgap(Like other)
   
   | hgapFactor(num factor)                 // the factor of the total width which is whitespace (i.e. 0.2 means 20% whitespace)
   | hgapFactor(computedNum cFactor)   
   | hgapFactor(Like other)   
   
   | vgap(num height)                     // set vert gap
   | vgap(computedNum cHeight)
   | vgap(Like other)
   
   | vgapFactor(num factor)                 //  the factor of the total height which is whitespace (i.e. 0.2 means 20% whitespace)
   | vgapRatio(computedNum cFactor)   
   | vgapRatio(Like other)   
   
   | startGap(bool b)                    // a (half) gap at the beginning of the (for example) hcat?
   | startGap(computedBool cAlg)
   | startGap(Like other)
   | endGap(bool b)                      // a (half) gap at the end of the (for example) hcat?
   | endGap(computedBool cAlg)
   | endGap(Like other)   
   | capGaps(bool b)                      // shorthand for setting both startGap and endGap
   | capGaps(computedBool cAlg)
   | capGaps(Like other)   
   | capGaps(bool b,bool b2)                     
   
/* alignment -- used by composition operators hcat, vcat, etc. */
   | align(num hor, num vert)
   
   | halign(num hor)
   | halign(computedNum cHor)
   | halign(Like other)
   
   | valign(num vert)
   | valign(computedNum cVert)
   | valign(Like other)
   
/* line and border properties */
   | lineWidth(num lineWidth)			// line width
   | lineWidth(computedNum cLineWidth)		// line width
   | lineWidth(Like other)
   
   | lineColor(Color lineColor)		    // line color
   | lineColor(str colorName)           // named line color
   | lineColor(computedColor cColorName)    // named line color
   | lineColor(Like other)
   
   | fillColor(Color fillColor)			// fill color of shapes and text
   | fillColor(str colorName)           // named fill color
   | fillColor(computedColor cColorName)    // named fill color
   | fillColor(Like other)
   
/* wedge properties */
   | fromAngle(num angle)
   | fromAngle(computedNum cAngle)
   | fromAngle(Like other)
   
   | toAngle(num angle)
   | toAngle(computedNum cAngle)
   | toAngle(Like other)
   
   | innerRadius(num radius)
   | innerRadius(computedNum cRadius)
   | innerRadius(Like other)

/* shape properties */
   | shapeConnected(bool b)              // shapes consist of connected points
   | shapeConnected(computedBool cB)
   | shapeConnected(Like other)
   
   | shapeClosed(bool b)    		 	// closed shapes
   | shapeClosed(computedBool cB)
   | shapeClosed(Like other)
   
   | shapeCurved(bool b)                // use curves instead of straight lines
   | shapeCurved(computedBool cB)
   | shapeCurved(Like other)
 
/* font and text properties */
   | font(str fontName)             	// named font
   | font(computedStr cFontName)    
   | font(Like other)
    
   | fontSize(int isize)                // font size
   | fontSize(computedInt ciSize)
   | fontSize(Like other)
   
   | fontColor(Color textColor)         // font color
   | fontColor(str colorName)
   | fontColor(computedColor cColorName)  
   | fontColor(Like other)
   
   | textAngle(num angle)               // text rotation
   | textAngle(computedNum cAngle) 
   | textAngle(Like other)
   
/* interaction properties */  
   | mouseOver(Figure inner)            // add figure when mouse is over current figure
   | mouseOver(computedFigure cInner)           
   | mouseOver(Like other)
               
   | onClick(void() handler)            // handler for mouse clicks
   
   | doi(int d)                         // limit visibility to nesting level d
   | doi(computedInt ciD) 
   
/* other properties */
   | id(str name)                       // name of elem (used in edges and various layouts)
   | id(computedStr cName)
   | id(Like other)
   
   | hint(str name)                     // hint for various compositions
   | hint(computedStr cName)
   | hint(Like other)
   
   | layer(str name)                     // define named layer for nodes
   | layer(computedStr cName)
   | layer(Like other)
   
   | direction(str name)
   | direction(computedStr cname)
   | direction(Like other)
   
   | projectX(Figure inner)            // projection on chart x axis
   | projectX(computedFigure cInner)           
   | projectX(Like other)
   
   | projectY(Figure inner)            // projection on chart x axis
   | projectY(computedFigure cInner)           
   | projectY(Like other)
   
   | projectXGap(num gap)
   | projectXGap(computedNum cGap)
   | projectXGap(Like other)
   
   | projectYGap(num gap)
   | projectYGap(computedNum cGap)
   | projectYGap(Like other)
   
   | scaleAll(bool b)                      // scale evertything? when not set, text and linewidth are not scaled
   | scaleAll(computedBool cAlg)
   | scaleAll(Like other)   

   | projectHalign(num align) // the alignment of the projection (i.e. 0.5f means project from center)
   | projectHalign(computedNum cAlign)
   | projectHalign(Like other)
   
   | projectValign(num align) // the alignment of the projection (i.e. 0.5f means project from center)
   | projectValign(computedNum cAlign)
   | projectValign(Like other)
   
   | projectAlign(num align)
   | projectAlign(num alignH,num alignV)
   
   | _child(FProperties props)           // define properties for the children of a composition (one level deep)
/* Standard properties: all the properties again! */
/* sizes */
   | stdWidth(num width)                   // sets width of element
   | stdWidth(computedNum cWidth)         // sets width of element
   | stdWidth(Like other)
   | stdHeight(num height)                 // sets height of element
   | stdHeight(computedNum cHeight)       // sets height of element
   | stdHeight(Like other)
   | stdSize(num size)					    // sets width and height to same value
   | stdSize(computedNum cSize)			// sets width and height to same value
   | stdSize(Like other)
   
   | stdSize(num width, num height)            // sets width and height to separate values
//   | stdsize(computedNum cWidth, computedNum cHeight)  // sets width and height to separate values
   
   | stdGap(num amount)                    // sets hor and vert gap between elements in composition to same value
   | stdGap(computedNum cAmount) 
   | stdGap(Like other) 
   
   | stdGap(num width, num height) 			// sets hor and vert gap between elements in composition to separate values
 //  | stdGap(computedNum cWidth, computedNum cHeight) 
   
   | stdGapFactor(num amount)
   | stdGapFactor(computedNum cAmount)
   | stdGapFactor(Like other)
   | stdGapFactor(num hfactor,num vfactor)
   
   | stdHgap(num width)                      // sets hor gap
   | stdHgap(computedNum cWidth)
   | stdHgap(Like other)
   
   | stdHgapFactor(num factor)                 // the factor of the total width which is whitespace (i.e. 0.2 means 20% whitespace)
   | stdHgapFactor(computedNum cFactor)   
   | stdHgapFactor(Like other)      
   
   | stdVgap(num height)                     // set vert gap
   | stdVgap(computedNum cHeight)
   | stdVgap(Like other)

   | stdVgapFactor(num factor)                 // the factor of the total width which is whitespace (i.e. 0.2 means 20% whitespace)
   | stdVgapFactor(computedNum cFactor)   
   | stdVgapFactor(Like other)   

   | stdStartGap(bool b)                    // a (half) gap at the beginning of the (for example) hcat?
   | stdStartGap(computedBool cAlg)
   | stdStartGap(Like other)
   | stdEndGap(bool b)                      // a (half) gap at the end of the (for example) hcat?
   | stdEndGap(computedBool cAlg)
   | stdEndGap(Like other)   
   | stdCapGaps(bool b)                      // shorthand for setting both startGap and endGap
   | stdCapGaps(computedBool cAlg)
   | stdCapGaps(Like other)   
   | stdCapGaps(bool b,bool b2)      
   
/* alignment -- used by composition operators hcat, vcat, etc. */
   | stdAlign(num hor, num vert)
   
   | stdHalign(num hor)
   | stdHalign(computedNum cHor)
   | stdHalign(Like other)
   
   | stdValign(num vert)
   | stdValign(computedNum cVert)
   | stdValign(Like other)
   
/* line and border properties */
   | stdLineWidth(num lineWidth)			// line width
   | stdLineWidth(computedNum cLineWidth)		// line width
   | stdLineWidth(Like other)
   
   | stdLineColor(Color lineColor)		    // line color
   | stdLineColor(str colorName)           // named line color
   | stdLineColor(computedColor cColorName)    // named line color
   | stdLineColor(Like other)
   
   | stdFillColor(Color fillColor)			// fill color of shapes and text
   | stdFillColor(str colorName)           // named fill color
   | stdFillColor(computedColor cColorName)    // named fill color
   | stdFillColor(Like other)
  
/* wedge properties */
   | stdFromAngle(num angle)
   | stdFromAngle(computedNum cAngle)
   | stdFromAngle(Like other)
   
   | stdToAngle(num angle)
   | stdToAngle(computedNum cAngle)
   | stdToAngle(Like other)
   
   | stdInnerRadius(num radius)
   | stdInnerRadius(computedNum cRadius)
   | stdInnerRadius(Like other)

/* shape properties */
   | stdShapeConnected(bool b)              // shapes consist of connected points
   | stdShapeConnected(computedBool cB)
   | stdShapeConnected(Like other)
   
   | stdShapeClosed(bool b)    		 	// closed shapes
   | stdShapeClosed(computedBool cB)
   | stdShapeClosed(Like other)
   
   | stdShapeCurved(bool b)                // use curves instead of straight lines
   | stdShapeCurved(computedBool cB)
   | stdShapeCurved(Like other)
 
/* font and text properties */
   | stdFont(str fontName)             	// named font
   | stdFont(computedStr cFontName)    
   | stdFont(Like other)
    
   | stdFontSize(int isize)                // font size
   | stdFontSize(computedInt ciSize)
   | stdFontSize(Like other)
   
   | stdFontColor(Color textColor)         // font color
   | stdFontColor(str colorName)
   | stdFontColor(computedColor cColorName)  
   | stdFontColor(Like other)
   
   | stdTextAngle(num angle)               // text rotation
   | stdTextAngle(computedNum cAngle) 
   | stdTextAngle(Like other)
   
/* interaction properties */  
   | stdMouseOver(Figure inner)            // add figure when mouse is over current figure
   | stdMouseOver(computedFigure cInner)           
   | stdMouseOver(Like other)
   
   | stdOnClick(void() handler)            // handler for mouse clicks
   
   | stdDoi(int d)                         // limit visibility to nesting level d
   | stdDoi(computedInt ciD) 
   
/* other properties */
   | stdId(str name)                       // name of elem (used in edges and various layouts)
   | stdId(computedStr cName)
   | stdId(Like other)
   
   | stdHint(str name)                     // hint for various compositions
   | stdHint(computedStr cName)
   | stdHint(Like other)
   
   | stdLayer(str name)                     // define named layer for nodes
   | stdLayer(computedStr cName)
   | stdLayer(Like other)
   
   | stdDirection(str name)
   | stdDirection(computedStr cname)
   | stdDirection(Like other)
   
   | stdProjectX(Figure inner)            // projection on chart x axis
   | stdProjectX(computedFigure cInner)           
   | stdProjectX(Like other)
   
   | stdProjectY(Figure inner)            // projection on chart x axis
   | stdProjectY(computedFigure cInner)           
   | stdProjectY(Like other)
   
   | stdProjectXGap(num radius)
   | stdProjectXGap(computedNum cRadius)
   | stdProjectXGap(Like other)
   
   | stdProjectYGap(num radius)
   | stdProjectYGap(computedNum cRadius)
   | stdProjectYGap(Like other)
   
   | stdProjectHalign(num align) // the alignment of the projection (i.e. 0.5f means project from center)
   | stdProjectHalign(computedNum cAlign)
   | stdProjectHalign(Like other)
   
   | stdProjectValign(num align) // the alignment of the projection (i.e. 0.5f means project from center)
   | stdProjectValign(computedNum cAlign)
   | stdProjectValign(Like other)
   
   | stdScaleAll(bool b)                      // scale evertything? when not set, text and linewidth are not scaled
   | stdScaleAll(computedBool cAlg)
   | stdScaleAll(Like other)   
   
   ;   

public FProperty child(FProperty props ...){
	return _child(props);
}

public FProperty grandChild(FProperty props ...){
	return _child([_child(props)]);
}

/*
 * Vertex and Edge: auxiliary data types
 */

data Vertex = 
     _vertex(num x, num y, FProperties props)             	    // vertex in a shape          
   | _vertex(num x, num y, Figure marker, FProperties props)    // vertex with marker
   ;
   
public Vertex vertex(num x, num y, FProperty props ...){
   return _vertex(x, y, props);
}

public Vertex vertex(num x, num y, Figure marker, FProperty props ...){
   return _vertex(x, y, marker, props);
}
   
data Edge =			 							// edge between between two elements in complex shapes like tree or graph
     _edge(str from, str to, FProperties prop)
   | _edge(str from, str to, Figure toArrow, FProperties prop)
   | _edge(str from, str to, Figure toArrow, Figure fromArrow, FProperties prop)
   ;
   
public alias Edges = list[Edge];
   
public Edge edge(str from, str to, FProperty props ...){
  return _edge(from, to, props);
}

public Edge edge(str from, str to, Figure toArrow, FProperty props ...){
  return _edge(from, to, toArrow, props);
}

public Edge edge(str from, str to, Figure toArrow, Figure fromArrow, FProperty props ...){
  return _edge(from, to, toArrow, fromArrow, props);
}

/*
 * Figure: a visual element, the principal visualization datatype
 */
 
public alias Figures = list[Figure];
 
data Figure = 
/* atomic primitives */

     _text(str s, FProperties props)		    // text label
   | _text(computedStr sv, FProperties props)
   
   												// file outline
   | _outline(list[LineDecoration] lineInfo, int maxLine, FProperties props)
   
   
/* primitives/containers */

   | _box(FProperties props)			          // rectangular box
   | _box(Figure inner, FProperties props)       // rectangular box with inner element
   
   | _ellipse(FProperties props)                 // ellipse with inner element
   | _ellipse(Figure inner, FProperties props)   // ellipse with inner element
   
   | _wedge(FProperties props)			      	// wedge
   | _wedge(Figure inner, FProperties props)     // wedge with inner element
   
   | _space(FProperties props)			      	// invisible box (used for spacing)
   | _space(Figure inner, FProperties props)     // invisible box with visible inner element

   | _chart(FProperties props)                  // chart (work in progress)      
   | _chart(Figure inner, FProperties props)   
 
/* composition */
   
   | _use(Figure elem)                           // use another elem
   | _use(Figure elem, FProperties props)
   
   | _place(Figure onTop, str at, Figure onBottom, FProperties props)
                       
   | _hcat(Figures figs, FProperties props)     // horizontal concatenation
                     
   | _vcat(Figures figs, FProperties props)     // vertical concatenation
                   
   | _hvcat(Figures figs, FProperties props) // horizontal and vertical concatenation
                   
   | _overlay(Figures figs, FProperties props)// overlay (stacked) composition
   
                								// shape of to be connected vertices
   | _shape(list[Vertex] points, FProperties props)
                         
   | _grid(Figures figs, FProperties props)// placement on fixed grid
   
  								                // composition by 2D packing
   | _pack(Figures figs, FProperties props)
   
  												 // composition of nodes and edges as graph
   | _graph(Figures nodes, Edges edges, FProperties props)
   
                							    // composition of nodes and edges as tree
   | _tree(Figures nodes, Edges edges, FProperties props)
   
   | _treemap(Figures nodes, Edges edges, FProperties props)
   
/* transformation */

   | _rotate(num angle, Figure fig, FProperties props)			    // Rotate element around its anchor point
   | _scale(num perc, Figure fig, FProperties props)	   		    // Scale element (same for h and v)
   | _scale(num xperc, num yperc, Figure fig, FProperties props)	// Scale element (different for h and v)

/* interaction */

   | _computeFigure(Figure () computeFig, FProperties props)
   | _button(str label, void () vcallback, FProperties props)
   | _textfield(str text, void (str) scallback, FProperties props)
   | _textfield(str text, void (str) scallback, bool (str) validate, FProperties props)
   | _choice(list[str] choices, void(str s) ccallback, FProperties props)
   | _checkbox(str text, void(bool) vbcallback, FProperties props)
   ;

public Figure text(str s, FProperty props ...){
  return _text(s, props);
}

public Figure text(computedStr sv, FProperty props ...){
  return _text(sv, props);
}

public Figure outline (list[LineDecoration] lineInfo, int maxLine, FProperty props ...){
  return _outline(lineInfo, maxLine, props);
}

public Figure box(FProperty props ...){
  return _box(props);
}

public Figure box(Figure fig, FProperty props ...){
  return _box(fig, props);
}

public Figure ellipse(FProperty props ...){
  return _ellipse(props);
}

public Figure ellipse(Figure fig, FProperty props ...){
  return _ellipse(fig, props);
}

public Figure wedge(FProperty props ...){
  return _wedge(props);
}

public Figure wedge(Figure fig, FProperty props ...){
  return _wedge(fig, props);
}  

public Figure space(FProperty props ...){
  return _space(props);
}

public Figure space(Figure fig, FProperty props ...){
  return _space(fig, props);
}

public Figure chart(FProperty props ...){
  return _chart(props);
}

public Figure chart(Figure fig, FProperty props ...){
  return _chart(fig, props);
}

public Figure place(Figure fig, str at, Figure base, FProperty props ...){
  return _place(fig, at, base, props);
}


public Figure use(Figure fig, FProperty props ...){
  return _use(fig, props);
}

public Figure hcat(Figures figs, FProperty props ...){
  return _hcat(figs, props);
}

public Figure vcat(Figures figs, FProperty props ...){
  return _vcat(figs, props);
}

public Figure hvcat(Figures figs, FProperty props ...){
  return _hvcat(figs, props);
}

public Figure overlay(Figures figs, FProperty props ...){
  return _overlay(figs, props);
}

public Figure shape(list[Vertex] points, FProperty props ...){
  return _shape(points, props);
}

public Figure grid(Figures figs, FProperty props ...){
  return _grid(figs, props);
}

public Figure pack(Figures figs, FProperty props ...){
  return _pack(figs, props);
}

public Figure graph(Figures nodes, Edges edges, FProperty props...){
  return _graph(nodes, edges, props);
}

public Figure tree(Figures nodes, Edges edges, FProperty props...){
  return _tree(nodes, edges, props);
}

public Figure treemap(Figures nodes, Edges edges, FProperty props...){
  return _treemap(nodes, edges, props);
}

public Figure rotate(num angle, Figure fig, FProperty props...){
  return _rotate(angle, fig, props);
}

public Figure scale(num perc, Figure fig, FProperty props...){
  return _scale(perc, fig, props);
}

public Figure scale(num xperc, num yperc, Figure fig, FProperty props...){
  return _scale(xperc, yperc, fig, props);
}

public Figure boolControl(str name, Figure figOn, Figure figOff, FProperty props...){
  return _boolControl(name, figOn, figOff, props);
}

public Figure controlOn(str name, Figure fig, FProperty props...){
  return _controlOn(name, fig, props);
}

public Figure controlOff(str name, Figure fig, FProperty props...){
  return _controlOff(name, fig, props);
}

public Figure strControl(str name, str initial, FProperty props...){
  return _strControl(name, initial, props);
}

public Figure intControl(str name, int initial, FProperty props...){
  return _intControl(name, initial, props);
}

public Figure colorControl(str name, int initial, FProperty props...){
  return _colorControl(name, initial, props);
}

public Figure colorControl(str name, str initial, FProperty props...){
  return _colorControl(name, initial, props);
}

public Figure computeFigure(Figure () computeFig, FProperty props...){
 	return _computeFigure(computeFig, props);
}
  
public Figure button(str label, void () callback, FProperty props...){
 	return _button(label, callback, props);
}
 
public Figure textfield(str text, void (str) callback, FProperty props...){
 	return _textfield(text, callback, props);
}
 
public Figure textfield(str text,  void (str) callback, bool (str) validate, FProperty props...){
 	return _textfield(text, callback, validate, props);
}
  
public Figure choice(list[str] choices, void(str s) ccallback, FProperty props...){
   return _choice(choices, ccallback, props);
}

public Figure checkbox(str text, void(bool) vcallback, FProperty props...){
   return _checkbox(text, vcallback, props);
}  
  


