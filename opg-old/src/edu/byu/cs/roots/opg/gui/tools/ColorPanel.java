package edu.byu.cs.roots.opg.gui.tools;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

public class ColorPanel extends JPanel {

/**
	 * 
	 */
	private static final long serialVersionUID = -3908612575908461601L;
//	private ArrayList<Color> selectedColors;
	private float hue;
	private float lum;
	private float sat;
	private float scale;
	private int xwheel;
	private int ywheel;
	private int rwheel;
	/**
	 * xlocation of the lum bar
	 */
	private int xbar;
	/**
	 * ylocation of the lum bar
	 */
	private int ybar;
	/**
	 * width of the lum  bar
	 */
	private int wbar;
	/**
	 * length of the lum bar
	 */
	private int lbar;
	private int displayColors;
	private int displayLum;
	private int colorSelections;
	private int selectorsize;
	private int mx;
	private int my;
	private ArrayList<Point> radialpts;
	private ArrayList<Polygon> colorGons;
	//private ArrayList<Point> selectorLocations;
	private Selector hueSelector;
	private Selector lumSelector;
	private boolean enabled;
	
	private class Selector{
		int xloc;
		int yloc;
		int radius;
		
		public Selector(int x, int y, int r){
			xloc = x;
			yloc = y;
			radius = r;
		}
		
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
			g.fillOval(xloc-radius, yloc-radius, 2*radius, 2*radius);
		}
	}
	
	public ColorPanel(int colorSelections){
		scale = .01f;
		hue = 0;
		lum = 1;
		sat = 1;
		enabled = true;
		rwheel = (int) (60/scale);
		ywheel = (int) (120/scale);
		xwheel = (int) (85/scale);
		xbar = (int) (180/scale);
		ybar = (int) (20/scale);
		wbar = (int) (15/scale);
		lbar = (int) (150/scale);
		displayLum = 200;
		displayColors = 100;
		selectorsize = (int) (10/scale);
		this.colorSelections = colorSelections;
		radialpts = null;
		initSelectors();
		hueSelector = new Selector(xwheel+rwheel, ywheel, selectorsize);
		lumSelector = new Selector(xbar+wbar + (int)(6/scale), ybar, (int) (5/scale));
		System.out.println(hueSelector.xloc + " " + hueSelector.yloc);
		ColorListener listener = new ColorListener(this);
		addMouseListener(listener);
		addMouseMotionListener(listener);
		
	}
	
	private void initSelectors() {
		for(int i = 0;i<colorSelections;i++){
			//selectorLocations
		}
		
	}

	private void calculateRadialPts(){
		radialpts = new ArrayList<Point>();
		for(int i = 0;i<displayColors;i++){
			double angle1 = (((float)i)/displayColors)*360;
			//double angle2 = (((float)(i+1))/displayColors)*360;
			radialpts.add(new Point((int) (xwheel+rwheel*Math.cos(Math.toRadians(angle1))), (int) (ywheel + rwheel*Math.sin(Math.toRadians(angle1)))));
		}
	}
	
	private void calculateColorGons(){
		if(radialpts == null) calculateRadialPts();
		colorGons = new ArrayList<Polygon>();
		for(int i = 0;i<displayColors-1;i++){
			Polygon pol = new Polygon();
			pol.addPoint(radialpts.get(i).x, radialpts.get(i).y);
			pol.addPoint(radialpts.get(i+1).x, radialpts.get(i+1).y);
			pol.addPoint(xwheel, ywheel);
			colorGons.add(pol);
		}
		Polygon pol = new Polygon();
		pol.addPoint(radialpts.get(displayColors-1).x, radialpts.get(displayColors-1).y);
		pol.addPoint(radialpts.get(0).x, radialpts.get(0).y);
		pol.addPoint(xwheel, ywheel);
		colorGons.add(pol);
		
	}
	private void moveHueSelector(int x, int y){
		double angle = Math.atan(((double)x-xwheel)/((double)y-ywheel)) + .5*Math.PI;
		if(y >= ywheel) angle += Math.PI;
		//System.out.println(angle);
		hue = (float) (1.0 - angle/(2*Math.PI));
		//System.out.println(hue);
		hueSelector.move(xwheel + (int) (Math.cos(angle)*rwheel), ywheel - (int) (Math.sin(angle) * rwheel));
	}
	private void moveLumSelector(int x, int y){
		if(y < ybar){ 
			lumSelector.move(lumSelector.xloc, ybar);
			lum = 1;
		}
		else if(y > (ybar+lbar)){
			lumSelector.move(lumSelector.xloc, ybar+lbar);
			lum = 0;
		}
		else{
			lumSelector.move(lumSelector.xloc, y);
			lum = 1 - ((float)y-ybar)/(lbar);
		}
	}
	
	@Override
	public void setEnabled(boolean enabled){
		this.enabled = enabled;
		sat = (enabled)? 1 : .4f;
	}
	
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.scale(scale, scale);
		if(colorGons == null) calculateColorGons();
		float h = 0;
		for(int i = 0;i<displayColors;i++){
			h = ((float) i)/displayColors;
			g2d.setColor(Color.getHSBColor(h, sat, 1));
			g2d.fillPolygon(colorGons.get(i));
		}
		g2d.setColor(Color.gray);
		g2d.setStroke(new BasicStroke(2/scale));
		g2d.drawOval(xwheel-(rwheel-1), ywheel-(rwheel-1), 2*rwheel - 2, 2*rwheel-2);
		g2d.setColor(Color.DARK_GRAY);
		hueSelector.draw(g2d);
		float l = 0;
		for(int i = displayLum;i>0;i--){
			l = ((float) i)/displayLum;
			g2d.setColor(Color.getHSBColor(hue, sat, l));
			g2d.fillRect(xbar, ybar + (int) ((1-l) * lbar),wbar, (int) ((1.0/displayLum) * lbar));
		}
		g2d.setColor(Color.DARK_GRAY);
		lumSelector.draw(g2d);
		g2d.setColor(Color.getHSBColor(hue, sat, lum));
		g2d.fillRect(1000, 1000, 1000, 1000);
		g2d.setColor(Color.black);
		g2d.setFont(Font.decode("Dialog").deriveFont(Font.PLAIN, 12/scale));
		g2d.drawString("Text", 1000, 1000);
		g2d.drawString("Color 1", 20, 10);
		g2d.drawString("Color 2", 35, 10);
		g2d.drawString("Color 3", 50, 10);
		
//		for(int i = 0;i<selectorLocations.size();i++){
//			g2d.drawOval(x, y, width, height)
//		}
		
		
	}

	 private class ColorListener implements MouseListener, MouseMotionListener
		{
			 JPanel parent;
			 //boolean dragging = false;
			 Selector sel = null;
			 
			ColorListener(JPanel parent){
				this.parent = parent;
			}

			public void mousePressed(MouseEvent event) {
				if(!enabled) return;
				mx = (int) (event.getPoint().x / scale);
				my = (int) (event.getPoint().y / scale);
				System.out.println("mouseClicked " + mx + " " + my);
				sel = null;
				if(hueSelector.contains(mx, my)) sel = hueSelector;
				if(lumSelector.contains(mx, my)) sel = lumSelector;
				System.out.println(sel);
			}
			
			public void mouseDragged (MouseEvent event){
				if(!enabled || sel == null) return;
				int x = (int) (event.getPoint().x / scale);
				int y = (int) (event.getPoint().y / scale);
				if(sel == hueSelector) moveHueSelector(x, y);
				if(sel == lumSelector) moveLumSelector(x, y);
				parent.repaint();
			}

			public void mouseClicked (MouseEvent event){
//				mx = (int) (event.getPoint().x / scale);
//				my = (int) (event.getPoint().y / scale);
//				System.out.println("mouseClicked " + mx + " " + my);
//				sel = null;
//				if(hueSelector.contains(mx, my)) sel = hueSelector;
//				if(lumSelector.contains(mx, my)) sel = lumSelector;
//				System.out.println(sel);
				
			}

			public void mouseEntered(MouseEvent arg0) {}

			public void mouseExited(MouseEvent arg0) {}

			

			public void mouseReleased(MouseEvent arg0) {}

			public void mouseMoved(MouseEvent arg0) {}
		}
	
}
