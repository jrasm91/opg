package edu.byu.cs.roots.opg.chart.cmds;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import edu.byu.cs.roots.opg.chart.ChartConversion;

public class DrawCmdFillRect extends DrawCommand implements Serializable {
	private double x, y;
	private double length, width, lineWidth;
	private Color lineColor, fillColor;
	static final long serialVersionUID = 1000L;
	private Rectangle2D.Double boxCoords;
	
	public DrawCmdFillRect(double x, double y, double length, double width, double lineWidth, Color lineColor, Color fillColor, Rectangle2D.Double boxCoords)
	{
		this.x = x;
		this.y = y;
		this.length = length;
		this.width = width;
		this.lineWidth = lineWidth;
		this.lineColor = lineColor;
		this.fillColor = fillColor;
		this.boxCoords = boxCoords;
	}
	
	
	//access methods
	public double getX() { return x; }
	public double getY() { return y; }
	public double getLength() { return length; }
	public double getWidth() { return width; }
	public double getLineWidth() { return lineWidth; }
	public Color getLineColor() { return lineColor; };
	public Color getFillColor() { return fillColor; };
	
	public String toString()
	{
		return super.toString() + " - " + x + y + length + width + lineColor.getRed() + lineColor.getGreen() + lineColor.getBlue() + fillColor.getRed() + fillColor.getGreen() + fillColor.getBlue();
	}

	public void execute(Graphics2D g, DrawState state) {
		//draw rectangle interior
		
		if (state.curColor != fillColor)
		{
			state.curColor = fillColor;
			g.setColor(fillColor);
		}
		
		
		//g.fillRect( (int)x, (int)(state.yExtent - (y + width)),  (int)(length), (int)(width));
		Rectangle2D.Double rect = new Rectangle2D.Double(x, ((double)state.yExtent - (y + width)),  (length), (width)); 
		if (boxCoords != null){
			boxCoords.x = rect.x;
			boxCoords.y = rect.y;
			boxCoords.width = rect.width;
			boxCoords.height = rect.height;
		}
		g.fill(rect);
		
		//draw rectangle border
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

	        //g.drawRect( (int)x, (int)(state.yExtent - (y + width)),  (int)(length), (int)(width));
	        g.draw(rect);
			
			g.setStroke(oldStroke);
		}
		
	}
	@Override
	public void execute(Graphics2D g, DrawState state, int width, int height, double zoom, Point multiChartOffset) {
		
		Point curPos = ChartConversion.convertToScreenCoord(new Point((int)this.x,(int)(state.yExtent - (this.y + this.width ))), zoom, state);
		Rectangle objectBox = new Rectangle(curPos.x, curPos.y, (int)(ChartConversion.convertToScreenSize(this.width + 100, zoom)), (int)(ChartConversion.convertToScreenSize(this.length, zoom)));

		if (viewable(getScreenArray(width, height), objectBox, multiChartOffset))
			execute(g, state);
	}

	public void executeAbsolute(Graphics2D g, DrawState state, int width, int height, double zoom) {
		g.setColor(fillColor);
		g.fillRect( (int)x, (int)(state.yExtent - (y + this.width)),  (int)(length), (int)(this.width));
		
		
		//draw rectangle border
		if (lineWidth > 0)
		{
			//set color
			g.setColor(lineColor);
			
			Stroke oldStroke = g.getStroke();
			
			BasicStroke s = new BasicStroke((float)lineWidth,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_BEVEL);
	        g.setStroke(s);

	        g.drawRect( (int)x, (int)(state.yExtent - (y + this.width)),  (int)(length), (int)(this.width));
			
			g.setStroke(oldStroke);
		}
	}
	


	public Rectangle getShapeBox() {
		return new Rectangle((int)x, (int)(y),  (int)(length), (int)(width));
	}


}
