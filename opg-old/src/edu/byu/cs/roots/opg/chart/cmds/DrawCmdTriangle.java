package edu.byu.cs.roots.opg.chart.cmds;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

public class DrawCmdTriangle extends DrawCommand implements Serializable {
	private double length, width, lineWidth;
	private Color lineColor, fillColor;
	private Rectangle2D.Double boxInfo;
	static final long serialVersionUID = 1000L;
	
	
	private Point coords;
	
	public DrawCmdTriangle(double length, double width, double lineWidth, Color lineColor, Color fillColor)
	{
		
		this.length = length;
		this.width = width;
		this.lineWidth = lineWidth;
		this.lineColor = lineColor;
		this.fillColor = fillColor;
		coords = new Point(DrawCommand.curPos); 
	}
	public DrawCmdTriangle(double length, double width, double lineWidth, Color lineColor, Color fillColor, Rectangle2D.Double boxCoords)
	{
		
		this.length = length;
		this.width = width;
		this.lineWidth = lineWidth;
		this.lineColor = lineColor;
		this.fillColor = fillColor;
		coords = new Point(DrawCommand.curPos);
		boxInfo = boxCoords;
	}
	
	
	//access methods
	public double getLength() { return length; }
	public double getWidth() { return width; }
	public double getLineWidth() { return lineWidth; }
	public Color getLineColor() { return lineColor; };
	public Color getFillColor() { return fillColor; };
	
	public String toString()
	{
		return super.toString() + " - " + length + width + lineColor.getRed() + lineColor.getGreen() + lineColor.getBlue() + fillColor.getRed() + fillColor.getGreen() + fillColor.getBlue();
	}

	public void execute(Graphics2D g, DrawState state) {
		if (state.curColor != fillColor)
		{
			state.curColor = fillColor;
			g.setColor(fillColor);
		}
		g.fillPolygon(new int[]{(int) state.pos.x, (int) state.pos.x, ((int) state.pos.x + (int)(width))},new int[]{ (int)(state.yExtent - (state.pos.y + width)), (int)(state.yExtent - (state.pos.y - (length) + width)), (int)(state.yExtent - (state.pos.y - (length/2) + width))}, 3);
		if (state.curColor != lineColor)
		{
			state.curColor = lineColor;
			g.setColor(lineColor);
		}
		g.drawPolygon(new int[]{(int) state.pos.x, (int) state.pos.x, ((int) state.pos.x + (int)(width))},new int[]{ (int)(state.yExtent - (state.pos.y + width)), (int)(state.yExtent - (state.pos.y - (length) + width)), (int)(state.yExtent - (state.pos.y - (length/2) + width))}, 3);
		
		//g.fillRect( (int) state.pos.x, (int) (state.yExtent - (state.pos.y + width)), (int)(length), (int)(width) );
			
		
		//set color
		if (state.curColor != lineColor)
		{
			state.curColor = lineColor;
			//g.setColor(new Color( (float)curColor.getRed() / 255.0f, (float)curColor.getGreen() / 255.0f, (float)curColor.getBlue() / 255.0f, (float)scaleFactor ) );
			g.setColor(lineColor);
		}
		
		g.drawRect( (int) state.pos.x, (int) (state.yExtent -(state.pos.y + width)), (int)(length), (int)(width) );
		if (boxInfo != null)
		{
			boxInfo.x = state.pos.x;
			boxInfo.y = state.yExtent - (state.pos.y + width);
			boxInfo.width = length;
			boxInfo.height = width;
		}
		
		
	}
	@Override
	public void execute(Graphics2D g, DrawState state, int width, int height, double zoom, Point multiChartOffset) {
		execute(g, state);
	}

	@Override
	public Rectangle getShapeBox() {
		
		return new Rectangle(coords.x, (int)(coords.y - width / 2), (int)length, (int)width);
	}

	@Override
	public void executeAbsolute(Graphics2D g, DrawState state, int width, int height, double zoom) {
		g.setColor(fillColor);
		g.drawPolygon(new int[]{(int) coords.x, (int) coords.x, ((int) coords.x + (int)(width))},new int[]{ 5, 5 ,5 , 5 }, 3);
		//set color
		g.setColor(lineColor);
		g.drawRect( (int) coords.x, (int) (state.yExtent -(coords.y + width)), (int)(length), (int)(width) );
		
	}

}


