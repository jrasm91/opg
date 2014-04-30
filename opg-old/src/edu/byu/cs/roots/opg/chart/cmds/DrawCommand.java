package edu.byu.cs.roots.opg.chart.cmds;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/*
 * The DrawCommand interface is to be extended by classes representing commands for drawing
 */
public abstract class DrawCommand implements Serializable {

	protected static Point curPos = new Point(0,0);
	protected static String curFontName;
	protected static int curFontSize;
	protected static int curFontStyle;
	protected static Color curColor;
	protected static Map<String, Font> fonts = new HashMap<String, Font>();
	
	protected Point2D.Double offset;
	
	static final long serialVersionUID = 1000L;
	
	public abstract Rectangle getShapeBox();
	
	public abstract void execute(Graphics2D g, DrawState state);
	
	public abstract void execute(Graphics2D g, DrawState state, int width, int height, double zoom, Point multiChartOffset);

	protected Point getClosestPoint(Point source, Point target, double range)
	{
		double distance = Math.sqrt(Math.pow(source.x - target.x, 2) + Math.pow(source.y - target.y, 2));
		double calcRange = range >= distance ? distance - 1 : range;
		double angle = Math.atan2(target.y - source.y, target.x - source.x);
		return new Point((int)(source.x + (calcRange * Math.cos(angle))), 
				(int)(source.y + (calcRange * Math.sin(angle))));
	}
	
	public static void resetCoords() {
		curPos = new Point(0,0);
	}
	
	public static void resetFont(Font specialFont)
	{
		fonts = new HashMap<String, Font>();
		Font curFont = null;
		if (specialFont == null)
		{
			if (GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts().length > 0)
				curFont = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts()[0];
				
			else
				System.err.println("No fonts installed.");
		}
		else
		{
			curFont = specialFont.deriveFont(specialFont.getSize2D());
		}
		if (curFont != null)
		{
			curFontName = curFont.getName();
			fonts.put(curFont.getName(), curFont);
		}
	}
	
	public static void resetColor()
	{
		curColor = Color.BLACK;
	}
	
	public static void reset()
	{
		resetColor();
		resetCoords();
		resetFont(null);
	}
	
	protected Rectangle getScreenArray(double width, double height) {
		return new Rectangle(0,0,(int)width,(int)height);
	}
	
	//TODO this is where it stops drawing stuff if it's half off the page. This needs to be fixed, The Multisheet chart offset
	//wasn't working, and wasn't calculating the offset right.  Thus, this will just always say it fits on screen.
	protected boolean viewable(Rectangle pointsScreenBox, Rectangle pointsObjectBox, Point multichartOffset)
	{
		boolean retValue = false;
		pointsScreenBox.x-=multichartOffset.x;
		pointsScreenBox.y-=multichartOffset.y;
		retValue = pointsScreenBox.intersects(pointsObjectBox) || pointsObjectBox.intersects(pointsScreenBox);
//		return retValue;
		return true;
	}

	public abstract void executeAbsolute(Graphics2D g, DrawState state, int width, int height, double zoom);
	
	@Override 
	public String toString() 
	{
		Rectangle rect = this.getShapeBox();
		return this.getClass().toString() + (rect != null ? 
				("(" + rect.x + ", " + rect.y + ") [" + rect.width + ", " + rect.height + "]") : 
					"");
	}
	
	public void setOffset(double x, double y){
		offset = new Point2D.Double(x, y);
	}

	public int getZOrder() {
		return 0;
	}
}
