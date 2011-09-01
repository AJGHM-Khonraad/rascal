package org.rascalmpl.library.vis.swt.applet;

import static org.rascalmpl.library.vis.util.vector.Dimension.HOR_VER;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.ScrollBar;
import org.rascalmpl.library.vis.figure.Figure;
import org.rascalmpl.library.vis.figure.combine.Overlap;
import org.rascalmpl.library.vis.graphics.SWTGraphicsContext;
import org.rascalmpl.library.vis.swt.FigureExecutionEnvironment;
import org.rascalmpl.library.vis.util.FigureMath;
import org.rascalmpl.library.vis.util.vector.BoundingBox;
import org.rascalmpl.library.vis.util.vector.Coordinate;
import org.rascalmpl.library.vis.util.vector.Dimension;
import org.rascalmpl.library.vis.util.vector.Rectangle;
import org.rascalmpl.library.vis.util.vector.TransformMatrix;
import org.rascalmpl.library.vis.util.vector.TwoDimensional;

public class ViewPortHandler implements SelectionListener, ControlListener, PaintListener, IFigureChangedListener{
	
	public static final boolean DOUBLE_BUFFERED = true;
	public static final double MIN_SIZE = 50;
	public static BoundingBox scrollableMinSize; 
	public static BoundingBox scrollbarSize; // width of vertical scrollbar, height of horizontal
	private BoundingBox viewPortSize; // the size of the viewport (with the scrollbars, if enabled)
	private BoundingBox parentSize; // the size of the viewport (without the scrollbars)
	private Coordinate viewPortLocation;
	Coordinate zoom ;
	private TwoDimensional<Boolean> scrollBarsVisible;
	private TwoDimensional<ScrollBar> scrollBars;
	private ScrollBar horBar, verBar;
	private Figure figure;
	private FigureSWTApplet parent;
	private List<Overlap> overlapFigures; // this is silently mutated by the FigureSWTApplet
	private Image backbuffer;
	private SWTElementsVisibilityManager swtVisiblityMangager;
	private SWTZOrderManager zorderManager;
	private SWTGraphicsContext gc;
	private TransformMatrix topLevel;
	private Rectangle viewPortRectangle;
	
	public ViewPortHandler(FigureSWTApplet parent, List<Overlap> overlapFigures){
		this.parent = parent;
		this.figure = parent.getFigure();
		this.overlapFigures = overlapFigures;
		parentSize = new BoundingBox();
		viewPortLocation = new Coordinate(0,0);
		zoom = new Coordinate(1,1);
		viewPortSize = new BoundingBox();
		horBar = parent.getHorizontalBar();
		verBar = parent.getVerticalBar();
		horBar.setVisible(false);
		verBar.setVisible(false);
		scrollBarsVisible = new TwoDimensional<Boolean>(false, false);
		scrollBars = new TwoDimensional<ScrollBar>(horBar, verBar);
		scrollbarSize = new BoundingBox(verBar.getSize().x, horBar.getSize().y);
		scrollableMinSize = new BoundingBox(MIN_SIZE + verBar.getSize().x, MIN_SIZE+ horBar.getSize().y);
		swtVisiblityMangager = new SWTElementsVisibilityManager();
		zorderManager = new SWTZOrderManager(parent,overlapFigures);
		gc = new SWTGraphicsContext();
		topLevel = new TransformMatrix();
		viewPortRectangle = new Rectangle(viewPortLocation, viewPortSize);
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
		figure.resize(part,topLevel);
	}
	
	private void distributeExtraSize(){
		figure.size.set(viewPortSize);
		Rectangle part = getViewPortRectangle();
		figure.resize(part,topLevel);
	}
	
	private void  distributeSizeWidthDependsOnHeight(){
		figure.size.set(viewPortSize);
		Rectangle part = getViewPortRectangle();
		figure.resize(part,topLevel);
	}

	private Rectangle getViewPortRectangle() {
		viewPortRectangle.update();
		return viewPortRectangle;
	}
	
	private void setViewPortSize(){
		if(parent.isDisposed()) return;
		org.eclipse.swt.graphics.Rectangle s = parent.getClientArea();
		viewPortSize.set(s.width-1,s.height-1);
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
		if(scrollBars.get(Dimension.X).isDisposed() || scrollBars.get(Dimension.Y).isDisposed()){
			return;
		}
		for(Dimension d : HOR_VER){
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
		if(viewPortSize.getX() == 0 || viewPortSize.getY() == 0 ) return;
		distributeSizeWidthDependsOnHeight();
		Dimension major =  figure.getMajorDimension();
		Dimension minor = major.other();
		if(!scrollBars.get(minor).isDisposed() && figure.size.get(minor) > viewPortSize.get(minor) && !scrollBars.get(minor).isVisible()){
			scrollBars.get(minor).setVisible(true);
			scrollBarsVisible.set(minor,true);
		} else if(!scrollBars.get(minor).isDisposed() && figure.size.get(minor) <= viewPortSize.get(minor) && scrollBarsVisible.get(minor)){
			scrollBarsVisible.set(minor,false);
			scrollBars.get(minor).setVisible(false);
		}
		scrollBarsVisible.set(major,false);
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
		setBackBuffer();
		try{
			gc.setGC(new GC(backbuffer));
		} catch(IllegalArgumentException e){
			makeNewBackBuffer();
			gc.setGC(new GC(backbuffer));
		}
		
		gc.getGC().setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		
		Rectangle part = getViewPortRectangle();
		gc.getGC().fillRectangle(0, 0, FigureMath.ceil(part.getSize().getX()), FigureMath.ceil(part.getSize().getY()));
		gc.translate(-part.getLocation().getX(), -part.getLocation().getY());

		
		figure.draw(zoom, gc, part,swtVisiblityMangager.getVisibleSWTElementsVector());
		gc.translate(part.getLocation().getX(), part.getLocation().getY());
		
		drawOverlaps(part);
		
		gc.dispose();
		swtGC.drawImage(backbuffer, 0, 0);
	
		swtVisiblityMangager.makeOffscreenElementsInvisble();
		zorderManager.draw(part);
		
		if(FigureExecutionEnvironment.profile) {
			long rascalTime = parent.getCallBackEnv().getAndResetRascalTime();
			rascalTime/=1000000;
			long drawTime = System.nanoTime() - startTime;
			drawTime/=1000000;
			System.out.printf("Drawing (part) took %d rascalTime %d %f\n", drawTime,rascalTime,(double)rascalTime / (double) drawTime);
		}
	}

	public void drawOverlaps(Rectangle part) {
		for(Overlap f : overlapFigures){
			if(f.innerFig.overlapsWith(part)){
				drawOverlap(part,  f);
			}
		}
	}
	


	private void drawOverlap(Rectangle part, Overlap f) {

		Coordinate left = new Coordinate(part.getLocation());
		Figure over = f.over;
		Rectangle realPart = new Rectangle(left,part.getSize());
		for(Dimension d : HOR_VER){
			if(over.location.get(d) < part.getLocation().get(d)){
				left.set(d,over.location.get(d));
			}
			double overRight = over.location.get(d) + over.size.get(d);
			if(over.location.get(d) + over.size.get(d) > part.getRightDown().get(d)){
				left.set(d,part.getLocation().get(d) + (overRight - part.getRightDown().get(d)));
			}
		}
		realPart.update();
		gc.translate(-realPart.getLocation().getX(), -realPart.getLocation().getY());
		over.draw(zoom, gc, realPart,swtVisiblityMangager.getVisibleSWTElementsVector());
		gc.translate(realPart.getLocation().getX(), realPart.getLocation().getY());
	}
	

	private void setBackBuffer(){
		if(backbuffer == null || backbuffer.isDisposed() || backbuffer.getBounds().width != viewPortSize.getX() +1 || backbuffer.getBounds().height != viewPortSize.getY()+1){
			makeNewBackBuffer();
		}
	}
	
	private void makeNewBackBuffer(){
		if(backbuffer!=null){
			backbuffer.dispose();
		}
		backbuffer = new Image(parent.getDisplay(), FigureMath.ceil(viewPortSize.getX())+1, FigureMath.ceil(viewPortSize.getY())+1);
	}

	public void dispose() {
		if(backbuffer!=null) backbuffer.dispose();
		swtVisiblityMangager.dispose();
		zorderManager.dispose();
		
	}

	@Override
	public void notifyFigureChanged() {
		resize();
		zorderManager.notifyFigureChanged();
		
		parent.requestRedraw();
	}

	public Image getFigureImage() {
		return backbuffer;
	}

	public void beforeInitialise() {
		zorderManager.clearSWTOrder();	
	}
	
	public void writeScreenShot(OutputStream to){
		Image screenShot = new Image(parent.getDisplay(), (int)viewPortSize.getX()+1 ,(int)viewPortSize.getY()+1);
		GC gc = new GC(parent);
		gc.copyArea(screenShot, 0,0);
		gc.dispose();
		ImageLoader il = new ImageLoader();
		il.data = new ImageData[] {screenShot.getImageData()};
		il.save(to, SWT.IMAGE_PNG);
	}
	
	public void makeScreenShot(){
		FileDialog f = new FileDialog(parent.getShell(), SWT.SAVE);
		f.setText("Select where to save your screenshot.");
		String filepath = f.open();
		if(filepath == null){
			return;
		}

		
		if(!filepath.endsWith(".png")){
			filepath+=".png";
		}
		try{
			OutputStream to = new FileOutputStream(filepath);
			writeScreenShot(to);
		} catch(FileNotFoundException e){
			System.err.printf("Could not write to " + filepath + "\n Reason " + e.getMessage());
		}
	}

	
	public void addSWTElement(Control c) {
		zorderManager.addSWTElement(c);
		
	}

	public void addAboveSWTElement(Figure fig) {
		zorderManager.addAboveSWTElement(fig);
		
	}
}
