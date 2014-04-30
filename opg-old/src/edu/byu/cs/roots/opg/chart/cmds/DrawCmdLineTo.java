package edu.byu.cs.roots.opg.chart.cmds;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.io.Serializable;

import edu.byu.cs.roots.opg.chart.ChartConversion;


public class DrawCmdLineTo extends DrawCommand implements Serializable
{
	private double x;
	private double y;
	private double width;
	private Color color;
	private Point coords;
	static final long serialVersionUID = 1000L;
		
	public DrawCmdLineTo(double x, double y, double width, Color color)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.color = color;
		coords = new Point(DrawCommand.curPos);
		DrawCommand.curPos.x = (int)Math.round(x);
		DrawCommand.curPos.y = (int)Math.round(y);
	}
	
	public double getX() { return x; }
	public double getY() { return y; }
	public double getWidth() { return width;}
	public Color getColor() { return color; }
	
	public String toString()
	{
		return super.toString() + " - " + x + y + color.getRed() + color.getGreen() + color.getBlue();
	}

	public void execute(Graphics2D g, DrawState state) {
		if (state.curColor != color)
			{
				state.curColor = color;
				g.setColor(color);
			}
			
			double x1,x2,y1,y2;
			//converting from chart coordinates (origin lower left) to panel(origin upper left)
			x1 =  state.pos.getX();
			x2 =  x;
			y1 =  ((double)state.yExtent - state.pos.y);
			y2 =  ((double)state.yExtent - y);
			//x1 = (int)(position.getX() * scaleFactor);
			//x2 = (int)(cmd.getX() * scaleFactor);
			//y1 = (int)((chartInfo.getYExtent() - position.getY()) * scaleFactor);
			//y2 = (int)((chartInfo.getYExtent() - cmd.getY()) * scaleFactor);
			
			Stroke oldStroke = g.getStroke();
			BasicStroke s = new BasicStroke((float)width,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_BEVEL);
            g.setStroke(s);
            
			//g.drawLine(x1,y1,x2,y2);
            g.draw(new Line2D.Double(x1,y1,x2,y2));
			
			g.setStroke(oldStroke);
			
			state.pos.setLocation(x, y);
	}
	@Override
	public void execute(Graphics2D g, DrawState state, int width, int height, double zoom, Point multiChartOffset) {
		Point curPos = ChartConversion.convertToScreenCoord(new Point((int)state.pos.getX(),(int)(state.yExtent - state.pos.y - this.width / 2)), zoom, state);
		Rectangle objectBox = new Rectangle(curPos.x, curPos.y, (int)(ChartConversion.convertToScreenSize(state.pos.getX() - this.x, zoom)), (int)(ChartConversion.convertToScreenSize(state.yExtent - this.y + this.width / 2, zoom)));

		if (viewable(getScreenArray(width, height), objectBox, multiChartOffset))
			execute(g, state);
	}

	@Override
	public Rectangle getShapeBox() {
		return new Rectangle(coords.x, coords.y, (int)Math.abs(coords.x - x), (int)Math.abs(coords.y - y));
	}

	@Override
	public void executeAbsolute(Graphics2D g, DrawState state, int width, int height, double zoom) {
		g.setColor(color);
		
		int x1,x2,y1,y2;
		//converting from chart coordinates (origin lower left) to panel(origin upper left)
		x1 = (int) coords.getX();
		x2 = (int) x;
		y1 = (int) (state.yExtent - coords.y);
		y2 = (int) (state.yExtent - y);
		//x1 = (int)(position.getX() * scaleFactor);
		//x2 = (int)(cmd.getX() * scaleFactor);
		//y1 = (int)((chartInfo.getYExtent() - position.getY()) * scaleFactor);
		//y2 = (int)((chartInfo.getYExtent() - cmd.getY()) * scaleFactor);
		
		Stroke oldStroke = g.getStroke();
		BasicStroke s = new BasicStroke((float)width,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_BEVEL);
        g.setStroke(s);
        
		g.drawLine(x1,y1,x2,y2);
		
		g.setStroke(oldStroke);
	}

}
