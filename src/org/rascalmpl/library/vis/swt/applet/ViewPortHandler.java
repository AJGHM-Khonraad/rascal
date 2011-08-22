package org.rascalmpl.library.vis.swt.applet;

import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ScrollBar;
import org.rascalmpl.library.vis.figure.Figure;
import org.rascalmpl.library.vis.figure.combine.Overlap;
import org.rascalmpl.library.vis.figure.interaction.swtwidgets.SWTWidgetFigure;
import org.rascalmpl.library.vis.graphics.GraphicsContext;
import org.rascalmpl.library.vis.graphics.SWTGraphicsContext;
import org.rascalmpl.library.vis.swt.FigureExecutionEnvironment;
import org.rascalmpl.library.vis.util.FigureMath;
import org.rascalmpl.library.vis.util.vector.BoundingBox;
import org.rascalmpl.library.vis.util.vector.Coordinate;
import org.rascalmpl.library.vis.util.vector.Dimension;
import org.rascalmpl.library.vis.util.vector.TransformMatrix;

import static org.rascalmpl.library.vis.util.vector.Dimension.*;
import org.rascalmpl.library.vis.util.vector.Rectangle;
import org.rascalmpl.library.vis.util.vector.TwoDimensional;

public class ViewPortHandler implements SelectionListener, ControlListener, PaintListener, IFigureChangedListener{
	
	public static final boolean DOUBLE_BUFFERED = true;
	public static final double MIN_SIZE = 50;
	public static BoundingBox scrollableMinSize; 
	public static BoundingBox scrollbarSize; // width of vertical scrollbar, height of horizontal
	private BoundingBox viewPortSize; // the size of the viewport (with the scrollbars, if enabled)
	private BoundingBox parentSize; // the size of the viewport (without the scrollbars)
	private Coordinate viewPortLocation;
	private TwoDimensional<Boolean> scrollBarsVisible;
	private TwoDimensional<ScrollBar> scrollBars;
	private ScrollBar horBar, verBar;
	private Figure figure;
	private FigureSWTApplet parent;
	private List<Overlap> overlapFigures; // this is silently mutated by the FigureSWTApplet
	private Image backbuffer;
	private SWTElementsVisibilityManager swtVisiblityMangager;
	private SWTZOrderManager zorderManager;
	
	public ViewPortHandler(FigureSWTApplet parent, List<Overlap> overlapFigures){
		this.parent = parent;
		this.figure = parent.getFigure();
		this.overlapFigures = overlapFigures;
		parentSize = new BoundingBox();
		viewPortLocation = new Coordinate(0,0);
		viewPortSize = new BoundingBox();
		horBar = parent.getHorizontalBar();
		verBar = parent.getVerticalBar();
		scrollBarsVisible = new TwoDimensional<Boolean>(false, false);
		scrollBars = new TwoDimensional<ScrollBar>(horBar, verBar);
		scrollbarSize = new BoundingBox(verBar.getSize().x, horBar.getSize().y);
		scrollableMinSize = new BoundingBox(MIN_SIZE + verBar.getSize().x, MIN_SIZE+ horBar.getSize().y);
		swtVisiblityMangager = new SWTElementsVisibilityManager();
		zorderManager = new SWTZOrderManager(parent,overlapFigures);
	}
	
	private void resetToMinSize(){
		for(Dimension d: HOR_VER){
			if(viewPortSize.get(d) < figure.minSize.get(d)){
				figure.size.set(d,figure.minSize.get(d));
			} else {
				figure.size.set(d,viewPortSize.get(d));
			}
		}
		Rectangle part = getViewPortRectangle();
		figure.resize(part,new TransformMatrix());
	}
	
	private void distributeExtraSize(){
		figure.size.set(viewPortSize);
		Rectangle part = getViewPortRectangle();
		figure.resize(part,new TransformMatrix());
	}
	
	private void  distributeSizeWidthDependsOnHeight(){
		figure.size.set(viewPortSize);
		Rectangle part = getViewPortRectangle();
		figure.resize(part,new TransformMatrix());
	}

	private Rectangle getViewPortRectangle() {
		Rectangle part = new Rectangle(viewPortLocation, viewPortSize);
		return part;
	}
	
	private void setViewPortSize(){
		org.eclipse.swt.graphics.Rectangle s = parent.getClientArea();
		viewPortSize.set(s.width,s.height);
	}
	
	private void setScrollBarsVisiblity(){
		Point p = parent.getSize();
		parentSize.set(p.x,p.y);
		boolean fitsWidth = parentSize.getX() >=
				figure.getMinViewingSize().getX() ;
		boolean fitsHeight =  parentSize.getY() >= figure.getMinViewingSize().getY();
		if(fitsWidth && fitsHeight){
			scrollBarsVisible.set(false,false);
		} else {
			if(!fitsWidth){
				boolean fitsHeightWithHorizontalScrollBar = parentSize.getY() - scrollbarSize.getY() >= figure.getMinViewingSize().getY();
				scrollBarsVisible.set(true,!fitsHeightWithHorizontalScrollBar);
			} else { // !fitsHeight
				boolean fitsWidthWithVerticalScrollBar = parentSize.getX() - scrollbarSize.getX() >= figure.getMinViewingSize().getX();
				scrollBarsVisible.set(!fitsWidthWithVerticalScrollBar,true);
			}
		}
	}
	
	private void propagateScrollBarVisiblity(){
		for(Dimension d : HOR_VER){
			ScrollBar bar = scrollBars.get(d);
			boolean shouldBeVisible =  scrollBarsVisible.get(d);
			if(bar.isVisible() != shouldBeVisible){
				bar.setVisible(shouldBeVisible);
			}
		}
	}
	
	private void updateScrollBars(){
		for(Dimension d : HOR_VER){
			if(!scrollBarsVisible.get(d)) { continue; }
			ScrollBar bar = scrollBars.get(d);
			double diff = figure.size.get(d) - viewPortSize.get(d);
			viewPortLocation.setMinMax(d, 0, diff);
			bar.setMinimum(0);
			bar.setMaximum(FigureMath.ceil( figure.size.get(d)));
			bar.setIncrement(50);
			int selSize = FigureMath.floor(viewPortSize.get(d));
			bar.setPageIncrement(selSize);
			bar.setThumb(selSize);
			bar.setSelection((int)viewPortLocation.get(d));
		}
	}
	
	private void resizeWidthDependsOnHeight(){
		setViewPortSize();
		distributeSizeWidthDependsOnHeight();
		Dimension major =  figure.getMajorDimension();
		Dimension minor = major.other();
		if(figure.size.get(minor) > viewPortSize.get(minor) && !scrollBars.get(minor).isVisible()){
			scrollBars.get(minor).setVisible(true);
			scrollBarsVisible.set(minor,true);
		} else if( figure.size.get(minor) <= viewPortSize.get(minor) && scrollBars.get(minor).isVisible()){
			scrollBars.get(minor).setVisible(false);
			scrollBarsVisible.set(minor,true);
		}
		scrollBarsVisible.set(major,false);
		if(scrollBars.get(major).isVisible()){
			scrollBars.get(major).setVisible(false);
		}
		updateScrollBars();
		parent.notifyLayoutChanged();
	}
	
	private void resize(){
		if(figure.widthDependsOnHeight()){
			resizeWidthDependsOnHeight();
			return;
		}
		if(parent.isDisposed()) {
			System.out.printf("ignoring resize while parent is disposed\n");
			return;
		}
		setScrollBarsVisiblity();
		if(horBar.isVisible() != scrollBarsVisible.getX() 
			|| verBar.isVisible() != scrollBarsVisible.getY()){
			propagateScrollBarVisiblity();
			//return; // we will get more resize events
		}
		setViewPortSize();
		if(viewPortSize.contains(figure.getMinViewingSize())){
			distributeExtraSize();
		} else {
			resetToMinSize();
		}
		updateScrollBars();
		
		parent.notifyLayoutChanged();
	}
	
	public void translateFromViewPortToFigure(Coordinate mouseLocation) {
		mouseLocation.add(viewPortLocation);
	}
	
	@Override
	public void controlMoved(ControlEvent e) {}

	@Override
	public void controlResized(ControlEvent e) {
		resize();
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		for(Dimension d : HOR_VER){
			ScrollBar bar = scrollBars.get(d);
			viewPortLocation.set(d,bar.getSelection());
		}
		parent.requestRedraw();
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {}

	@Override
	public void paintControl(PaintEvent e) {
		draw(e.gc);
	}
	

	
	public void draw(GC swtGC){
		if(viewPortSize.getX() <= 0 || viewPortSize.getY() <= 0){
			System.out.printf("NOT DRAWING %s\n",this);
			return;
		}
		long startTime = System.nanoTime();
		GraphicsContext gc;
		setBackBuffer();
		try{
			gc = new SWTGraphicsContext(new GC(backbuffer));
		} catch(IllegalArgumentException e){
			makeNewBackBuffer();
			gc = new SWTGraphicsContext(new GC(backbuffer));
		}
		Rectangle part = getViewPortRectangle();
		gc.translate(-viewPortLocation.getX(), -viewPortLocation.getY());
		Coordinate zoom = new Coordinate(1,1);
		figure.draw(zoom, gc, part,swtVisiblityMangager.getVisibleSWTElementsVector());

		for(Overlap f : overlapFigures){
			if(f.over.overlapsWith(part)){
				f.over.draw(zoom, gc, part,swtVisiblityMangager.getVisibleSWTElementsVector());
			}
		}
		gc.dispose();
		swtGC.drawImage(backbuffer, 0, 0);
		
		swtVisiblityMangager.makeOffscreenElementsInvisble();
		zorderManager.draw(part);
		if(FigureExecutionEnvironment.profile) System.out.printf("Drawing (part) took %f\n", ((double)(System.nanoTime() - startTime)) / 1000000.0);
	}
	

	private void setBackBuffer(){
		if(backbuffer == null || backbuffer.isDisposed() || backbuffer.getBounds().width != viewPortSize.getX() || backbuffer.getBounds().height != viewPortSize.getY()){
			makeNewBackBuffer();
		}
	}
	
	private void makeNewBackBuffer(){
		if(backbuffer!=null){
			backbuffer.dispose();
		}
		backbuffer = new Image(parent.getDisplay(), FigureMath.ceil(viewPortSize.getX()), FigureMath.ceil(viewPortSize.getY()));
	}

	public void dispose() {
		if(backbuffer!=null) backbuffer.dispose();
		swtVisiblityMangager.dispose();
		zorderManager.dispose();
		
	}

	@Override
	public void notifyFigureChanged() {
		zorderManager.notifyFigureChanged();
		resize();
		parent.requestRedraw();
	}

	public Image getFigureImage() {
		return backbuffer;
	}

	public void beforeInitialise() {
		zorderManager.clearSWTOrder();
		
	}

	
	public void addSWTElement(Control c) {
		zorderManager.addSWTElement(c);
		
	}

	public void addAboveSWTElement(Figure fig) {
		zorderManager.addAboveSWTElement(fig);
		
	}
}
