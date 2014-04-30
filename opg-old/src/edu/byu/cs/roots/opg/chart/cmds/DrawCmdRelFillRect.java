package edu.byu.cs.roots.opg.chart.cmds;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

public class DrawCmdRelFillRect extends DrawCommand implements Serializable {
	private double length, width, lineWidth;
	private Color lineColor, fillColor;
	private Point coord;
	static final long serialVersionUID = 1000L;
	private Rectangle2D.Double boxCoords;
	
	public DrawCmdRelFillRect(double length, double width, double lineWidth, Color lineColor, Color fillColor, Rectangle2D.Double boxCoords)
	{
		this.length = length;
		this.width = width;
		this.lineWidth = lineWidth;
		this.lineColor = lineColor;
		this.fillColor = fillColor;
		this.boxCoords = boxCoords;
		coord = new Point(DrawCommand.curPos);
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
		//g.fillRect( (int) state.pos.x, (int) (state.yExtent - (state.pos.y + width)), (int)(length), (int)(width) );
		Rectangle2D.Double rect = new Rectangle2D.Double(state.pos.x, ((double)state.yExtent - (state.pos.y + width)),  (length), (width)); 
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

	        g.draw(rect);
			
			g.setStroke(oldStroke);
		}
	}
	@Override
	public void execute(Graphics2D g, DrawState state, int width, int height, double zoom, Point multiChartOffset) {
		execute(g, state);
	}

	@Override
	public Rectangle getShapeBox() {
		return new Rectangle(coord.x, coord.y + (int)width, (int)length, (int)width);
	}

	@Override
	public void executeAbsolute(Graphics2D g, DrawState state, int width, int height, double zoom) {
		g.setColor(fillColor);
		g.fillRect( (int) coord.x, (int) (state.yExtent - (coord.y + this.width)), (int)(length), (int)(this.width) );
			
		
		//set color
		g.setColor(lineColor);
		
		g.drawRect( (int) coord.x, (int) (state.yExtent -(coord.y + this.width)), (int)(length), (int)(this.width) );
		
	}

}
