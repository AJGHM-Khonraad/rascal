package org.rascalmpl.library.vis.swt.applet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;

import org.eclipse.imp.pdb.facts.IBool;
import org.eclipse.imp.pdb.facts.IMap;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.impl.fast.ValueFactory;
import org.eclipse.imp.pdb.facts.type.TypeFactory;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.rascalmpl.library.vis.KeySym;
import org.rascalmpl.library.vis.figure.Figure;
import org.rascalmpl.library.vis.figure.combine.Overlap;
import org.rascalmpl.library.vis.swt.FigureExecutionEnvironment;
import org.rascalmpl.library.vis.util.BogusList;
import org.rascalmpl.library.vis.util.KeySymTranslate;
import org.rascalmpl.library.vis.util.Util;
import org.rascalmpl.library.vis.util.vector.Coordinate;
import org.rascalmpl.values.ValueFactoryFactory;

public class InputHandler implements MouseListener,MouseMoveListener, MouseTrackListener, KeyListener, IFigureChangedListener{
	
	@SuppressWarnings("unchecked")
	private static final BogusList<Figure> bogusFigureList = (BogusList<Figure>)BogusList.instance;
	private static final IBool pdbTrue = ValueFactory.getInstance().bool(true);
	private static final IBool pdbFalse = ValueFactory.getInstance().bool(false);
	private static IMap keyboardModifierMap = 
			ValueFactoryFactory.getValueFactory().map(KeySym.KeyModifier, TypeFactory.getInstance().boolType()); // there is only 1 keyboard , hence static
	private List<Figure> figuresUnderMouse;  // the figures under mouse from front to back
	private List<Figure> figuresUnderMouseSorted; // figures under mouse sorted in an arbitrary stable ordering (stable as in the order does not change)
	private List<Figure> figuresUnderMouseSortedPrev; // the figures under mouse sorted on the previous mouse location
	private List<Figure> newUnderMouse; 
	private List<Figure> noLongerUnderMouse;
	private Figure figure;
	private Coordinate mouseLocation; // location on figure, not in viewport
	private FigureExecutionEnvironment env;
	private List<Overlap> overlapFigures; // this is silently mutated by the FigureSWTApplet
	private FigureSWTApplet parent;
	
	public InputHandler(FigureSWTApplet parent, List<Overlap> overlapFigures){
		figuresUnderMouse = new ArrayList<Figure>();
		figuresUnderMouseSorted = new ArrayList<Figure>();
		figuresUnderMouseSortedPrev = new ArrayList<Figure>();
		newUnderMouse = new ArrayList<Figure>();
		noLongerUnderMouse = new ArrayList<Figure>();
		
		mouseLocation = new Coordinate(-10,-10);
		this.figure = parent.getFigure();
		this.env = parent.getExectutionEnv();
		this.overlapFigures = overlapFigures;
		this.parent = parent;
		
	}
	
	public void notifyFigureChanged(){
		handleMouseMove();
	}
	
	private void setFiguresUnderMouse(){

		figuresUnderMouse.clear();
		figure.getFiguresUnderMouse(mouseLocation, figuresUnderMouse);
		for(Overlap f : overlapFigures){
			f.over.getFiguresUnderMouse(mouseLocation, figuresUnderMouse);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void handleMouseOvers(){
		List<Figure> swp = figuresUnderMouseSortedPrev;
		figuresUnderMouseSortedPrev = figuresUnderMouseSorted;
		figuresUnderMouseSorted = swp;
		figuresUnderMouseSorted.clear();
		figuresUnderMouseSorted.addAll(figuresUnderMouse);
		Collections.sort(figuresUnderMouseSorted);
		newUnderMouse.clear();
		noLongerUnderMouse.clear();
		// compute the added and removed elements in a fast way
		Util.diffSorted(figuresUnderMouseSortedPrev, figuresUnderMouse, noLongerUnderMouse, BogusList.instance, newUnderMouse);
		/*System.out.printf("Now under mouse:\n");
		 for(Figure fig : figuresUnderMouseSorted){
			System.out.printf("%s \n",fig);
		}
		 */
		 /*
		System.out.printf("Prev under mouse:\n");
		for(Figure fig : figuresUnderMouseSortedPrev){
			System.out.printf("%s \n",fig);
		}
		*/
		/*
		if(!noLongerUnderMouse.isEmpty()){
			System.out.printf("No longer under mouse:\n");
			for(Figure fig : noLongerUnderMouse){
				System.out.printf("%s \n",fig);
			}
		}
		if(!newUnderMouse.isEmpty()){
			System.out.printf("New under mouse:\n");
			for(Figure fig : newUnderMouse){
				System.out.printf("%s \n",fig);
			}
		}
		*/
		
		env.beginCallbackBatch();
		for(Figure fig : noLongerUnderMouse){
			fig.mouseOver = false;
			fig.executeMouseMoveHandlers(env, pdbFalse);
		}
		for(Figure fig : newUnderMouse){
			fig.mouseOver = true;
			fig.executeMouseMoveHandlers(env, pdbTrue);
		}
		env.endCallbackBatch(false);
	
	}
	
	private void handleMouseMove(){
		setFiguresUnderMouse();
		handleMouseOvers();
	}
	

	private boolean handleKey(KeyEvent e,IBool down){
	
		env.beginCallbackBatch();
		IValue keySym = KeySymTranslate.toRascalKey(e, env.getRascalContext());
		keyboardModifierMap = KeySymTranslate.toRascalModifiers(e, keyboardModifierMap, env.getRascalContext());
		boolean captured = false;
		for(Figure fig : figuresUnderMouse){
			if(fig.executeKeyHandlers(env, keySym, down, keyboardModifierMap)){
				captured = true;
				break;
			}
		}
		env.endCallbackBatch();
		return captured;
	}
	

	@Override
	public void keyPressed(KeyEvent e) {
		boolean captured = handleKey(e,pdbTrue);
		if(!captured){
			parent.handleNonCapturedKeyPress(e);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//handleKey(e,pdbFalse);
	}

	@Override
	public void mouseMove(MouseEvent e) {
		mouseLocation.set(e.x,e.y);
		parent.translateFromViewPortToFigure(mouseLocation);
		handleMouseMove();
		
	}

	@Override
	public void mouseDoubleClick(MouseEvent e) {
	}

	@Override
	public void mouseDown(MouseEvent e) {
	}

	@Override
	public void mouseUp(MouseEvent e) {
		env.beginCallbackBatch();
		for(Figure fig : figuresUnderMouse){
			if(fig.executeOnClick(env)){
				break;
			}
		}
		env.endCallbackBatch();
	}
	
	@Override
	public void mouseEnter(MouseEvent e) {
		parent.forceFocus();
	}

	@Override
	public void mouseExit(MouseEvent e) {
		mouseLocation.set(-100,-100); // not on figure!
		handleMouseMove();
	}

	@Override
	public void mouseHover(MouseEvent e) {}

	public void dispose() {
		
	}
}
