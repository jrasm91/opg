package edu.byu.cs.roots.opg.gui.tools;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ColorWheel extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3463665518782190638L;
	private float hue;
	private float lum;
	private float sat;
	private float scale;
	private int centerX;
	private int centerY;
	private int radius;
	private int selectorRadius;
	private int displayColors;
	private int displaySaturations;
	private int selectorLineThickness;
	private float selectorLineFactor;
	private int borderThickness;
	private Selector hueSelector;
	private boolean enabled;
	private float invscale;
	private BufferedImage cachedWheel;
	CopyOnWriteArraySet<ChangeListener> listenerset = new CopyOnWriteArraySet<ChangeListener>();  //  @jve:decl-index=0:
	CopyOnWriteArraySet<ActionListener> actionset = new CopyOnWriteArraySet<ActionListener>();  //  @jve:decl-index=0:
	
	/**
	 * data structures for hue only color wheel
	 * NOT CURRENTLY USED
	 */
//	private ArrayList<Point> radialpts; 
//	private ArrayList<Polygon> colorGons;
	/**
	 * data structures for hue and saturation wheel
	 */
	private ArrayList<PointScalar> radialscl; 
	private ArrayList<ArrayList<Polygon>> wheelgons;  
	
	
	public ColorWheel(){
		super();
		setScale(.01f);
		hue = 0;
		lum = 1;
		sat = 0;
		enabled = true;
		radius = 60;
		selectorRadius = 5;
		selectorLineFactor = .2f;
		selectorLineThickness = (int) (selectorLineFactor*selectorRadius*invscale);
		centerY = radius + selectorRadius;
		centerX = radius + selectorRadius;
		borderThickness = 0;
		displayColors = 50;
		displaySaturations = 20;
//		radialpts = null;
		hueSelector = new Selector(0, 0, (int) invscale*selectorRadius);
		cachedWheel = null;
		this.setValues(hue, sat);
		WheelListener listener = new WheelListener(this);
		addMouseListener(listener);
		addMouseMotionListener(listener);
		this.addComponentListener(new java.awt.event.ComponentAdapter() {
        	public void componentResized(java.awt.event.ComponentEvent e) {
				refit();
        	}
        });
	}
		


	
	public void addChangeListener(ChangeListener arg0) {
		listenerset.add(arg0);
	}
	
	public void removeChangeListener(ChangeListener arg0) {
		listenerset.remove(arg0);
	}
	
	private void firePropertyChanged(){
		ChangeEvent e = new ChangeEvent(this);
		for(ChangeListener listener : listenerset){
			listener.stateChanged(e);
		}
	}
	
	public void addActionListener(ActionListener arg0) {
		actionset.add(arg0);
	}
	
	public void removeActionListener(ActionListener arg0) {
		actionset.remove(arg0);
	}
	 
	private void fireActionPerformed(){
		ActionEvent e = new ActionEvent(this,0,"");
		for(ActionListener listener : actionset){
			listener.actionPerformed(e);
		}
	}
	
	private void setScale(float scale){
		this.scale = scale;
		invscale = 1/scale;
	}
	
	
	@Override
	public void setEnabled(boolean enabled){
		this.enabled = enabled;
	}
	
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
//		Code for  making a hue only color wheel
//		if(colorGons == null) calculateColorGons();
//		float h = 0;
//		for(int i = 0;i<displayColors;i++){
//			h = ((float) i)/displayColors;
//			g2d.setColor(Color.getHSBColor(h, sat, 1));
//			g2d.fillPolygon(colorGons.get(i));
//		}
		
		if(cachedWheel == null){
//			System.out.println(2*borderThickness*scale);
			cachedWheel = new BufferedImage((2*radius) + (int) (2*selectorRadius+selectorLineThickness*scale),(2*radius) + (int) (2*selectorRadius+selectorLineThickness*scale),BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D gr = cachedWheel.createGraphics();
//			gr.setColor(this.getBackground());
//			gr.drawRect(0,0,cachedWheel.getHeight(), cachedWheel.getWidth());
			gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			gr.scale(scale, scale);
			gr.setColor(Color.gray);
			gr.fillOval((int) invscale*(centerX-radius)-borderThickness, (int) invscale*(centerY-radius)-borderThickness,(int) invscale*(2*radius) + 2*borderThickness, (int) invscale*(2*radius) + 2*borderThickness );
			if(wheelgons == null) calculateWheelgons();
			float h = 0;
			float saturation = .2f;
			//code for making a hue and saturation wheel
			ArrayList<Polygon> templist;
			for(float j = displaySaturations;j>=0;j-- ){
				if(enabled) saturation = (j)/displaySaturations;
				templist = wheelgons.get((int) j);
				for(int i = 0;i<displayColors;i++){
					h = ((float) i)/displayColors;
					gr.setColor(Color.getHSBColor(h, saturation, lum));
					gr.fillPolygon(templist.get(i));
				}
			}
		}
		g2d.drawImage(cachedWheel,0, 0,this);
		g2d.scale(scale, scale);
		g2d.setStroke(new BasicStroke(selectorLineThickness));
		g2d.setColor(Color.DARK_GRAY);
		hueSelector.draw(g2d);
	}
	/**
	 * Method for computing the polygons nessecary to draw the Hue and saturation color wheel
	 * the number of polygons is equal to displayColors * displaySaturations
	 *
	 */
	private void calculateWheelgons(){
		float scaledradius = radius * invscale;
		float scaledx = centerX * invscale;
		float scaledy = centerY * invscale;
		int centerx = (int) scaledx;
		int centery = (int) scaledy;
		wheelgons = new ArrayList<ArrayList<Polygon>>();
		if(radialscl == null) calculateRadialscl();
		
		for(float j = 0;j<=displaySaturations;j++){
			ArrayList<Polygon> templist = new ArrayList<Polygon>();
			Point cur = null;
			Point next = new Point((int)(radialscl.get(0).x*scaledradius*(j/displaySaturations)+scaledx), (int)( radialscl.get(0).y*scaledradius*(j/displaySaturations)+scaledy));
			for(int i = 1;i<displayColors;i++){
				cur = next;
				next = new Point((int)(radialscl.get(i).x*scaledradius*(j/displaySaturations)+scaledx), (int)( radialscl.get(i).y*scaledradius*(j/displaySaturations)+scaledy));
				//System.out.println("next is "+next.x+","+next.y);
				Polygon pol = new Polygon();
				pol.addPoint(cur.x, cur.y);
				pol.addPoint(next.x, next.y);
				pol.addPoint(centerx, centery);
				templist.add(pol);
			}
			Point last = new Point((int)(radialscl.get(0).x*scaledradius*(j/displaySaturations)+scaledx), (int)( radialscl.get(0).y*scaledradius*(j/displaySaturations)+scaledy));
			Polygon pol = new Polygon();
			pol.addPoint(next.x, next.y);
			pol.addPoint(last.x, last.y);
			pol.addPoint(centerx, centery);
			templist.add(pol);
			wheelgons.add(templist);
		}
	}
	/**
	 * Method for computing a list of point scalars, this is basicaly just a list
	 * of unit vectors in in varying directions
	 * The number of point scalars is equal to displayColors
	 *
	 */
	private void calculateRadialscl(){
		radialscl = new ArrayList<PointScalar>();
		for(double i = 0;i<displayColors;i++){
			double angle1 = (i/displayColors)*360;
			radialscl.add(new PointScalar( Math.cos(Math.toRadians(angle1)),(float) Math.sin(Math.toRadians(angle1))));
		}
	}
	
	/**
	 * Method for generating a list of points which fall on the outer edge of the circle
	 * The number of points is equal to displayColors
	 *
	 *NOT CURRENTLY BEING USED
	 */
/*	private void calculateRadialPts(){
		radialpts = new ArrayList<Point>();
		float scaledradius = radius * invscale;
		float scaledx = centerX * invscale;
		float scaledy = centerY * invscale;
		for(int i = 0;i<displayColors;i++){
			double angle1 = (((float)i)/displayColors)*360;
			radialpts.add(new Point((int) (scaledx+scaledradius*Math.cos(Math.toRadians(angle1))), (int) (scaledy + scaledradius*Math.sin(Math.toRadians(angle1)))));
		}
	}*/
	/**
	 * Method for generating a list of all polygons which are triangles with
	 * one point at the center of the wheel and the other two points on the outer 
	 * edge, the number of polygons created is equal to displayColors
	 * 
	 * NOT CURRENTLY BEING USED
	 *
	 */
/*	private void calculateColorGons(){
		if(radialpts == null) calculateRadialPts();
		colorGons = new ArrayList<Polygon>();
		int scaledx = (int) ( centerX * invscale);
		int scaledy = (int) ( centerY * invscale);
		for(int i = 0;i<displayColors-1;i++){
			Polygon pol = new Polygon();
			pol.addPoint(radialpts.get(i).x, radialpts.get(i).y);
			pol.addPoint(radialpts.get(i+1).x, radialpts.get(i+1).y);
			pol.addPoint(scaledx, scaledy);
			colorGons.add(pol);
		}
		Polygon pol = new Polygon();
		pol.addPoint(radialpts.get(displayColors-1).x, radialpts.get(displayColors-1).y);
		pol.addPoint(radialpts.get(0).x, radialpts.get(0).y);
		pol.addPoint(scaledx, scaledy);
		colorGons.add(pol);
		
	}*/
	
	private void refit(){
		radius = (int) ((Math.min(getHeight(), getWidth())*.5) - (selectorRadius+selectorLineThickness*scale));
		centerY = radius + selectorRadius + (int) (selectorLineThickness*scale);
		centerX = radius + selectorRadius + (int) (selectorLineThickness*scale);
		cachedWheel = null;
		//functions for hue only color wheel
//		calculateRadialPts();
//		calculateColorGons();
		//functions for hue/saturation color wheel
		calculateRadialscl();
		calculateWheelgons();
		moveHueSelector(hue, sat);
		repaint();
		
	}

	
	private void moveHueSelector(int x, int y){
		float scaledx = centerX * invscale;
		float scaledy = centerY * invscale;
		float scaledradius = radius * invscale;
		double distance = Math.sqrt(Math.pow(((double)x-scaledx), 2) + Math.pow(((double)y-scaledy), 2));
		double angle = Math.atan(((double)x-scaledx)/((double)y-scaledy)) + .5*Math.PI;
		if(Double.isNaN(angle)){
			hue = 0;
			sat = 0;
			hueSelector.move((int)scaledx, (int)scaledy);
		}else{
			if(y >= scaledy) angle += Math.PI;
			//System.out.println(angle);
			if(distance > scaledradius) distance = scaledradius;
			hue = (float) (1.0 - angle/(2*Math.PI));
			sat = (float) (distance/scaledradius);
			//System.out.println(hue);
			hueSelector.move((int) (scaledx + (Math.cos(angle)*distance)), (int) (scaledy - (Math.sin(angle) * distance)));
			//System.out.println(hueSelector.xloc + " " + hueSelector.yloc);
		}
	}
	
	private void moveHueSelector(float hue, float saturation){
		float scaledx = centerX * invscale;
		float scaledy = centerY * invscale;
		float scaledradius = radius * invscale;
		double angle = (1-hue)*(2*Math.PI);
		double distance = sat*scaledradius;
		//System.out.println("setting selector location " + angle + " " + distance);
		hueSelector.move((int) (scaledx + (Math.cos(angle)*distance)), (int) (scaledy - (Math.sin(angle) * distance)));
		//System.out.println(hueSelector.xloc + " " + hueSelector.yloc);
	}
	
	
	
	
	
	
	private class WheelListener implements MouseListener, MouseMotionListener
	{
		 JComponent parent;
//		 boolean dragging = false;
//		 Selector sel = null;
		 
		WheelListener(JComponent parent){
			this.parent = parent;
		}

		public void mousePressed(MouseEvent event) {
			if(!enabled) return;
			int mx, my;
			mx = (int) (event.getPoint().x * invscale);
			my = (int) (event.getPoint().y * invscale);
//			if(hueSelector.contains(mx, my)) sel = hueSelector;
//			else sel = null;
			moveHueSelector(mx, my);
			fireActionPerformed();
			parent.repaint();
		}
		
		public void mouseDragged (MouseEvent event){
			if(!enabled) return;
			int x = (int) (event.getPoint().x * invscale);
			int y = (int) (event.getPoint().y * invscale);
			//if(sel == hueSelector) moveHueSelector(x, y);
			moveHueSelector(x, y);
			fireActionPerformed();
			parent.repaint();
		}

		public void mouseClicked (MouseEvent event){}

		public void mouseEntered(MouseEvent arg0) {}

		public void mouseExited(MouseEvent arg0) {}

		

		public void mouseReleased(MouseEvent arg0) {}

		public void mouseMoved(MouseEvent arg0) {}
	}
	
	
	private class Selector{
		int xloc;
		int yloc;
		int radius;
		
		public Selector(int x, int y, int r){
			xloc = x;
			yloc = y;
			radius = r;
		}
		
		@SuppressWarnings("unused")
		public boolean contains(int x, int y){
			double dist = Math.sqrt(Math.pow(x-xloc, 2)+Math.pow(y-yloc, 2));
			if(dist <= radius) return true;
			else return false;
		}
		
		public void move(int x, int y){
			xloc = x;
			yloc = y;
		}
		public void draw(Graphics g){
			g.drawLine(xloc, yloc-radius, xloc, yloc+radius);
			g.drawLine((xloc-radius), yloc, (xloc+radius), yloc);
			g.drawOval((xloc-radius), (yloc-radius), 2*radius, 2*radius);
		}
	}
	
	private class PointScalar{
		public double x;
		public double y;
		
		public PointScalar(double x, double y){
			this.x = x;
			this.y = y;
		}
	}

	public float getHue() {
		return hue;
	}




	public void setHue(float hue) {
		this.hue = hue;
		this.moveHueSelector(hue, sat);
		firePropertyChanged();
	}




	public float getSat() {
		return sat;
	}


	public void setSat(float sat) {
		this.sat = sat;
		this.moveHueSelector(hue, sat);
		firePropertyChanged();
	}
	
	public void setValues(float hue, float sat){
		this.hue = hue;
		if(0 <= sat && sat <= 1) this.sat = sat;
		else this.sat = 1;
		this.moveHueSelector(hue, sat);
		firePropertyChanged();
	}

	/**
	 * @return the displayColors
	 */
	public int getDisplayColors() {
		return displayColors;
	}

	/**
	 * @param displayColors the displayColors to set
	 */
	public void setDisplayColors(int displayColors) {
		this.displayColors = displayColors;
		calculateRadialscl();
		calculateWheelgons();
		firePropertyChanged();
	}

	/**
	 * @return the displaySaturations
	 */
	public int getDisplaySaturations() {
		return displaySaturations;
	}

	/**
	 * @param displaySaturations the displaySaturations to set
	 */
	public void setDisplaySaturations(int displaySaturations) {
		this.displaySaturations = displaySaturations;
		calculateRadialscl();
		calculateWheelgons();
		firePropertyChanged();
	}




	/**
	 * @return the borderThickness
	 */
	public int getBorderThickness() {
		return borderThickness;
	}




	/**
	 * @param borderThickness the borderThickness to set
	 */
	public void setBorderThickness(int borderThickness) {
		this.borderThickness = borderThickness;
		firePropertyChanged();
	}
}
