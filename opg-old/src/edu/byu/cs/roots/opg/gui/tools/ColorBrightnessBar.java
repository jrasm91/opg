package edu.byu.cs.roots.opg.gui.tools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ColorBrightnessBar extends javax.swing.JComponent {
	private static final long serialVersionUID = 8026974629168129243L;
	private CopyOnWriteArraySet<ChangeListener> listenerset = new CopyOnWriteArraySet<ChangeListener>();
	private CopyOnWriteArraySet<ActionListener> actionset = new CopyOnWriteArraySet<ActionListener>();
	private float scale;
	private float invscale;
	private float val;
	private float hue;
	private float sat;
	private int displayLum;
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
	private int selectorsize;
	private Selector valSelector;
	private boolean enabled;
	private boolean horizontal;
	
	
	public ColorBrightnessBar(){
		super();
		setScale(.01f);
		hue = 0;
		val = 1;
		sat = 1;
		xbar = 1;
		ybar = 1;
		lbar = 40;
		wbar = 10;
		selectorsize = 10;
		enabled = true;
		horizontal = true;
		BarListener listener = new BarListener(this);
		valSelector = new Selector((int) (xbar*invscale), (int) (ybar*invscale),(int) (selectorsize*invscale));
		displayLum = 30;
		addMouseListener(listener);
		addMouseMotionListener(listener);
		this.addComponentListener(new java.awt.event.ComponentAdapter() {
        	public void componentResized(java.awt.event.ComponentEvent e) {
        		refit();
        	}
        });
	}
	
	
	private void fireActionPerformed(){
		ActionEvent e = new ActionEvent(this,0,"");
		for(ActionListener listener : actionset){
			listener.actionPerformed(e);
		}
	}
	
	public void addActionListener(ActionListener arg0) {
		actionset.add(arg0);
	}
	
	public void removeActionListener(ActionListener arg0) {
		actionset.remove(arg0);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.scale(scale, scale);
		int x = (int) (xbar*invscale);
		int y = (int) (ybar*invscale);
		int width = (int) (wbar*invscale);
		int length = (int) (lbar*invscale);
		if(horizontal) g2d.fillRect(x, y, length, width);
		else g2d.fillRect(x, y, width, length);
		float l = 0;
		
		for(int i = displayLum;i>0;i--){
			l = ((float) i)/displayLum;
			if(enabled) g2d.setColor(Color.getHSBColor(hue, sat, l));
			else g2d.setColor(Color.getHSBColor(hue, .2f, l));
			if(horizontal) g2d.fillRect(x + (int) ((1-l) * length), y, (int) (l * length), width);
			else g2d.fillRect(x, y + (int) ((1-l) * length),width, (int) (l * length));
		}
		g2d.setColor(Color.DARK_GRAY);
		valSelector.draw(g2d);
	}
	
	private void refit(){
		if(horizontal){
			ybar = selectorsize;
			xbar = selectorsize;
			lbar = getWidth()-2*selectorsize;
			wbar = getHeight()-selectorsize;
			valSelector = new Selector((int) (xbar*invscale), (int) (ybar*invscale),(int) (selectorsize*invscale));
			moveValSelector(val);
		}
		else{
			ybar = selectorsize;
			xbar = 0;
			wbar = getWidth()-selectorsize;
			lbar = getHeight()-2*selectorsize;
			valSelector = new Selector((int) ((xbar + wbar)*invscale), (int) (ybar*invscale),(int) (selectorsize*invscale));
			moveValSelector(val);
		}
		repaint();
	}
	
	@Override
	public void setEnabled(boolean enabled){
		this.enabled = enabled;
	}
	
	private void moveValSelector(int x, int y){
		if(horizontal){
			if(x < xbar*invscale){ 
				valSelector.move((int) (xbar*invscale), valSelector.yloc);
				val = 1;
			}
			else if(x > (xbar+lbar)*invscale){
				valSelector.move((int) ((xbar+lbar)*invscale), valSelector.yloc);
				val = 0;
			}
			else{
				valSelector.move(x, valSelector.yloc);
				val = 1 - ((float)x-xbar*invscale)/(lbar*invscale);
			}
//			System.out.println(val);
		}
		else{
			if(y < ybar*invscale){ 
				valSelector.move(valSelector.xloc, ybar);
				val = 1;
			}
			else if(y > (ybar+lbar)*invscale){
				valSelector.move(valSelector.xloc, (int) (invscale*(ybar+lbar)));
				val = 0;
//				System.out.println("val set to 0");
			}
			else{
				valSelector.move(valSelector.xloc, y);
				val = 1 - ((float)y-ybar*invscale)/(lbar*invscale);
			}
		}
	}	
	
	private void moveValSelector(float val){
		int x = valSelector.xloc;
		int y = (int)(invscale* ((1 - val) * lbar + ybar));
		valSelector.move(x, y);
	}
	
	private void setScale(float scale){
		this.scale = scale;
		invscale = 1/scale;
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
	
	private class Selector{
		int xloc;
		int yloc;
		int radius;
		Polygon pointer;
		
		public Selector(int x, int y, int r){
			xloc = x;
			yloc = y;
			radius = r;
			pointer = new Polygon();
			pointer.addPoint(xloc, yloc);
			if(horizontal) {
				pointer.addPoint(xloc + (int)(.4*radius),yloc - (int)(.7*radius) );
				pointer.addPoint(xloc - (int)(.4*radius),yloc - (int)(.7*radius) );
			}
			else{
				pointer.addPoint(xloc + (int) (.7*radius),yloc + (int)(.4*radius) );
				pointer.addPoint(xloc + (int) (.7*radius),yloc - (int)(.4*radius) );
			}
		}
		
		@SuppressWarnings("unused")
		public boolean contains(int x, int y){
			return pointer.contains(x, y);
		}
		
		public void move(int x, int y){
			pointer.translate(x-xloc, y-yloc);
			xloc = x;
			yloc = y;
		}
		public void draw(Graphics g){
			g.fillPolygon(pointer);
//			g.fillOval((xloc-radius), (yloc-radius), 2*radius, 2*radius);
		}
	}
	
	
	private class BarListener implements MouseListener, MouseMotionListener
	{
		 JComponent parent;
//		 boolean dragging = false;
//		 Selector sel = null;
		 int mx, my;
		BarListener(JComponent parent){
			this.parent = parent;
		}

		public void mousePressed(MouseEvent event) {
			if(!enabled) return;
			mx = (int) (event.getPoint().x * invscale);
			my = (int) (event.getPoint().y * invscale);
//			sel = null;
//			if(lumSelector.contains(mx, my)) sel = lumSelector;
			moveValSelector(mx, my);
			fireActionPerformed();
			parent.repaint();
		}
		
		public void mouseDragged (MouseEvent event){
			if(!enabled) return;
			mx = (int) (event.getPoint().x * invscale);
			my = (int) (event.getPoint().y * invscale);
//			if(sel == lumSelector) moveValSelector(x, y);
			moveValSelector(mx, my);
			fireActionPerformed();
			parent.repaint();
		}

		public void mouseClicked (MouseEvent event){}

		public void mouseEntered(MouseEvent arg0) {}

		public void mouseExited(MouseEvent arg0) {}

		

		public void mouseReleased(MouseEvent arg0) {}

		public void mouseMoved(MouseEvent arg0) {}
	}


	public float getVal() {
		return val;
	}


	public void setVal(float val) {
		if(val < 0 ) this.val = 0f;
		else if(val > 1 ) this.val = 1f;
		else {
			this.val = val;
		}
//		System.out.println("val set to " + val);
		moveValSelector(val);
		repaint();
		firePropertyChanged();
	}


	public boolean isHorizontal() {
		return horizontal;
	}


	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
		repaint();
		firePropertyChanged();
	}


	public float getHue() {
		return hue;
	}


	public void setHue(float hue) {
		this.hue = hue;
		repaint();
		firePropertyChanged();
	}


	public float getSat() {
		return sat;
	}


	public void setSat(float sat) {
		this.sat = sat;
		repaint();
		firePropertyChanged();
	}

	public void setHueSat(float hue, float sat){
		this.hue = hue;
		this.sat = sat;
		repaint();
		firePropertyChanged();
	}
	
	public int getSelectorsize() {
		return selectorsize;
	}


	public void setSelectorsize(int selectorsize) {
		this.selectorsize = selectorsize;
		firePropertyChanged();
		repaint();
	}


	public int getDisplayLum() {
		return displayLum;
	}


	public void setDisplayLum(int displayLum) {
		this.displayLum = displayLum;
		repaint();
		firePropertyChanged();
	}
		
}
