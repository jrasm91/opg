package edu.byu.cs.roots.opg.chart.cmds;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.io.Serializable;

public class DrawCmdRoundRect extends DrawCommand implements Serializable {
	private double x, y;
	private double length, width, lineWidth, roundness; //roundness is point value of arcs to be drawn for corners; may not exceed the smallest of height and width and must be zero or above
	private Color lineColor, fillColor;
	private Point coord;
	static final long serialVersionUID = 1000L;
	private Rectangle2D.Double boxCoords;
	
	/**
	 * 
	 * @param x x position of rectangle
	 * @param y y position of rectangle
	 * @param length width of the rectangle
	 * @param width height of the rectangle
	 * @param lineWidth border line width
	 * @param roundness how round it is
	 * @param lineColor border line color
	 * @param fillColor fill color
	 */
	public DrawCmdRoundRect(double x, double y, double length, double width, double lineWidth , double roundness, Color lineColor, Color fillColor, Rectangle2D.Double boxCoords)
	{
		this.x = x;
		this.y = y;
		this.length = length;
		this.width = width;
		this.lineWidth = lineWidth;
		this.roundness = roundness;
		this.lineColor = lineColor;
		this.fillColor = fillColor;
		this.boxCoords = boxCoords;
		
		offset = new Point2D.Double(0,0);
		coord = new Point(DrawCommand.curPos);
	}
	
	//access methods
	public double getX() { return x; }
	public double getY() { return y; }
	public double getLength() { return length; }
	public double getWidth() { return width; }
	public double getLineWidth() { return lineWidth; }
	public double getRoundness() { return roundness; }
	public Color getLineColor() { return lineColor; };
	public Color getFillColor() { return fillColor; };
	
	public String toString()
	{
		return super.toString() + " - " + x + y + length + width + roundness + lineColor.getRed() + lineColor.getGreen() + lineColor.getBlue() + fillColor.getRed() + fillColor.getGreen() + fillColor.getBlue();
	}

	public void execute(Graphics2D g, DrawState state) {
		//draw rounded rectangle interior
		
		//this stores the data for where the box is drawn
		RoundRectangle2D.Double rect = new RoundRectangle2D.Double(x + offset.x, (double)state.yExtent - (y + width) + offset.y,  length, width, roundness, roundness);
		if (boxCoords != null){
			boxCoords.x = x;
			boxCoords.y = (double)state.yExtent - (y + width);
			boxCoords.width = length;
			boxCoords.height = width;
		}
		if (state.curColor != fillColor)
		{
			state.curColor = fillColor;
			g.setColor(fillColor);
			
		}
		
		
		
		g.fillRoundRect((int)rect.x, (int)rect.y, (int)rect.width, (int)rect.height, (int)rect.arcwidth, (int)rect.archeight);
		
		
		//draw rounded rectangle border
		if (lineWidth > 0)
		{
			//set color
			if (state.curColor != lineColor)
			{
				state.curColor = lineColor;
				g.setColor(lineColor);
			}
			
			Stroke oldStroke = g.getStroke();
			
			BasicStroke s = new BasicStroke((float)lineWidth,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_BEVEL);
	        g.setStroke(s);

	        //g.drawRoundRect( (int)x, (int)(state.yExtent - (y + width)),  (int)(length), (int)(width), (int)(roundness), (int)(roundness) );
	        g.draw(rect);
			
			g.setStroke(oldStroke);
		}
	}
	@Override
	public void execute(Graphics2D g, DrawState state, int width, int height, double zoom, Point multiChartOffset) {
		execute(g, state);
	}

	public void executeAbsolute(Graphics2D g, DrawState state, int width, int height, double zoom) {
		g.setColor(fillColor);
		g.fillRoundRect( (int)x + (int)offset.x, (int)(state.yExtent - (y + this.width) + offset.y),  (int)(length), (int)(this.width), (int)(roundness), (int)(roundness) );
		
		
		//draw rounded rectangle border
		if (lineWidth > 0)
		{
			//set color
			g.setColor(lineColor);
			
			Stroke oldStroke = g.getStroke();
			
			BasicStroke s = new BasicStroke((float)lineWidth,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_BEVEL);
	        g.setStroke(s);

	        g.drawRoundRect( (int)x + (int)offset.x, (int)(state.yExtent - (y + this.width) + offset.y),  (int)(length), (int)(this.width), (int)(roundness), (int)(roundness) );
			
			g.setStroke(oldStroke);
		}
	}

	@Override
	public Rectangle getShapeBox() {
		return new Rectangle(coord.x, (int)(coord.y + width), (int)length, (int)width);
	}



}
