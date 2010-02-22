module experiments::VL::VLCore
import Integer;
import List;
import Set;
import IO;

/*
 * Declarations and library functions for Rascal Visualization
 */
 
 /*
  * Colors and color management
  */

alias Color = int;

@doc{Gray color (0-255)}
@javaClass{org.rascalmpl.library.experiments.VL.VL}
public Color java gray(int gray);

@doc{Gray color (0-255) with transparency}
@javaClass{org.rascalmpl.library.experiments.VL.VL}
public Color java gray(int gray, real alpha);

@doc{Gray color as percentage (0.0-1.0)}
@javaClass{org.rascalmpl.library.experiments.VL.VL}
public Color java gray(real perc);

@doc{Gray color with transparency}
@javaClass{org.rascalmpl.library.experiments.VL.VL}
public Color java gray(real perc, real alpha);

@doc{Named color}
@reflect{Needs calling context when generating an exception}
@javaClass{org.rascalmpl.library.experiments.VL.VL}
public Color java color(str colorName);

@doc{Named color with transparency}
@reflect{Needs calling context when generating an exception}
@javaClass{org.rascalmpl.library.experiments.VL.VL}
public Color java color(str colorName, real alpha);

@doc{RGB color}
@javaClass{org.rascalmpl.library.experiments.VL.VL}
public Color java rgb(int r, int g, int b);

@doc{RGB color with transparency}
@javaClass{org.rascalmpl.library.experiments.VL.VL}
public Color java rgb(int r, int g, int b, real alpha);

@doc{Interpolate two colors (in RGB space)}
@javaClass{org.rascalmpl.library.experiments.VL.VL}
public list[Color] java interpolateColor(Color from, Color to, real percentage);

@doc{Create a list of interpolated colors}
@javaClass{org.rascalmpl.library.experiments.VL.VL}
public list[Color] java colorSteps(Color from, Color to, int steps);

@doc{Create a colorscale}
public Color(int) colorScale(list[int] values, Color from, Color to){
   mn = min(values);
   range = max(values) - mn;
   sc = colorSteps(from, to, 10);
   return Color(int v) { return sc[(9 * (v - mn)) / range]; };
}

@doc{Create a fixed color palette}
private list[str] p12 = [ "navy", "violet", "yellow", "aqua", 
                          "red", "darkviolet", "maroon", "green",
                          "teal", "blue", "olive", "lime"];

@doc{Return named color from fixed palette}
public str palette(int n){
  try 
  	return p12[n];
  catch:
    return "black";
}

/*
 * VPROP -- visual properties of visual elements
 */
 
 public VPROP left(){
   return hanchor(0.0);
 }
 
 public VPROP hcenter(){
   return hanchor(0.5);
 }
 
 public VPROP right(){
   return hanchor(1.0);
 }
 
 public VPROP top(){
   return vanchor(0.0);
 }
 
 public VPROP vcenter(){
   return vanchor(0.5);
 }
 
 public VPROP bottom(){
   return vanchor(1.0);
 }
 
 public VPROP center(){
   return anchor(0.5, 0.5);
 }

data VPROP =
/* sizes */
     width(real width)                  // sets width of element
   | width(int iwidth)
   | height(real height)                // sets height of element
   | height(int iheight)
   | size(real size)					// sets width and height to same value
   | size(int isize)
   | size(real hor, real vert)          // sets width and height to separate values
   | size(int ihor, int ivert)
   | gap(real amount)                   // sets hor and vert gap between elements in composition to same value
   | gap(int iamount)
   | gap(real hor, real vert) 			// sets hor and vert gap between elements in composition to separate values
   | gap(int ihor, int ivert)
   
/* alignment */
   | anchor(real h, real v)				// horizontal (0=left; 1=right) & vertical anchor (0=top,1=bottom)
   | hanchor(real h)
   | vanchor(real v)
   
/* line and border properties */
   | lineWidth(int lineWidth)			// line width
   | lineColor(Color lineColor)		    // line color
   | lineColor(str colorName)           // named line color
   
   | fillColor(Color fillColor)			// fill color of shapes and text
   | fillColor(str colorName)           // named fill color
   
/* wedge properties */
   | fromAngle(real angle)
   | fromAngle(int iangle)
   | toAngle(real angle)
   | toAngle(int iangle)
   | innerRadius(real radius)
   | innerRadius(int iradius)

   
/* font and text properties */
   | font(str fontName)             	// named font
   | fontSize(int isize)                // font size
   | fontColor(Color textColor)         // font color
   | fontColor(str colorName)
   | textAngle(real angle)              // text rotation
   | textAngle(int iangle) 
   
/* interaction properties */
   | mouseOver(list[VPROP] props)       // switch to new properties when mouse is over element
   | mouseOver(list[VPROP] props, VELEM inner)
                                        // display new inner element when mouse is over current element
   
/* other properties */
   | id(str name)                       // name of elem (used in edges and various layouts)
   | connected()                        // shapes consist of connected points
   | closed()    						// closed shapes
   | curved()                           // use curves instead of straight lines
   ;

/*
 * Vertex and Edge: auxiliary data types
 */

data Vertex = 
     vertex(real x, real y)             // vertex in a shape
   | vertex(int ix, int iy) 
   | vertex(int ix, real y)  
   | vertex(real x, int iy)           
   | vertex(real x, real y, VELEM marker)  // vertex with marker
   | vertex(int ix, int iy, VELEM marker)
   | vertex(int ix, real y, VELEM marker)
   | vertex(real x, int iy, VELEM marker)
   ;
   
data Edge =
     edge(str from, str to) 			 // edge between between two elements in complex shapes like tree or graph
   | edge(list[VPROP], str from, str to) // 
   ;

/*
 * VELEM: a visual element, the principal visualization datatype
 */
 
data VELEM = 
/* atomic primitives */

     text(list[VPROP] props, str s)		  		// text label
   | text(str s)			              		// text label
   
/* primitives/containers */

   | box(list[VPROP] props)			          	// rectangular box
   | box(list[VPROP] props, VELEM inner)        // rectangular box with inner element
   
   | ellipse(list[VPROP] props)			      	// ellipse
   | ellipse(list[VPROP] props, VELEM inner)    // ellipse with inner element
   
   | wedge(list[VPROP] props)			      	// wedge
   | wedge(list[VPROP] props, VELEM inner)      // wedge with inner element
   
   | space(list[VPROP] props)			      	// invisible box (used for spacing)
   | space(list[VPROP] props, VELEM inner)      // invisible box with visible inner element
 
/* composition */
   
   | use(VELEM elem)                           		        // use another elem
   | use(list[VPROP] props, VELEM elem)
 
   | hcat(list[VELEM] elems)                                // horizontal concatenation
   | hcat(list[VPROP] props, list[VELEM] elems)
   
   | vcat(list[VELEM] elems)                                // vertical concatenation
   | vcat(list[VPROP] props, list[VELEM] elems)
   
   | align(list[VELEM] elems)                               // horizontal and vertical composition
   | align(list[VPROP] props, list[VELEM] elems)
   
   | overlay(list[VELEM] elems)                             // overlay (stacked) composition
   | overlay(list[VPROP] props, list[VELEM] elems)
   
   | shape(list[Vertex] points)                            // shape of to be connected vertices
   | shape(list[VPROP] props,list[Vertex] points)
   
   | grid(list[VELEM] elems)                                // placement on fixed grid
   | grid(list[VPROP] props, list[VELEM] elems)
   
   | pack(list[VELEM] elems)                                // composition by 2D packing
   | pack(list[VPROP] props, list[VELEM] elems)
   
   | pie(list[VELEM] elems)                                 // composition as pie chart
   | pie(list[VPROP] props, list[VELEM] elems)
   
   | graph(list[VELEM] nodes, list[Edge] edges)             // composition of nodes and edges as graph
   | graph(list[VPROP], list[VELEM] nodes, list[Edge] edges)
   
   | tree(list[VELEM] nodes, list[Edge] edges, str root)              // composition of nodes and edges as tree
   | tree(list[VPROP], list[VELEM] nodes, list[Edge] edges, str root)
   
/* transformation */

   | rotate(real angle, VELEM elem)							// Rotate element around its anchor point
   | scale(real perc, VELEM)								// Scale element (same for h and v)
   | scale(real xperc, real yperc, VELEM elem)				// Scale element (different for h and v)
   ;
   
/*
 * Wishlist:
 * - arrows
 * - textures
 * - boxes with round corners
 * - dashed/dotted lines
 * - ngons
 * - svg/png/pdf export
 */

