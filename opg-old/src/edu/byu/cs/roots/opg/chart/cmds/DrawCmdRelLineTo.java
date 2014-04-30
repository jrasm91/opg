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

public class DrawCmdRelLineTo extends DrawCommand implements Serializable {
	private double dx;
	private double dy;
	private double width;
	private Color color;
	static final long serialVersionUID = 1000L;
	private Point coord;
	
	/**
	 * 
	 * @param x x position
	 * @param y y position
	 * @param width width of line
	 * @param color color of line
	 */
	public DrawCmdRelLineTo(double x, double y, double width, Color color)
	{
		dx = x;
		dy = y;
		this.width = width; 
		this.color = color;
		coord = new Point(DrawCommand.curPos);
		DrawCommand.curPos.x += Math.round(x);
		DrawCommand.curPos.y += Math.round(y);
	}
	
	public double getX() { return dx; }
	public double getY() { return dy; }
	public double getWidth() { return width; }
	public Color getColor() { return color; }
	
	public String toString()
	{
		return super.toString() + " - " + dx + dy + color.getRed() + color.getGreen() + color.getBlue();
	}

	public void execute(Graphics2D g, DrawState state) {
		if (state.curColor != color)
		{
			state.curColor = color;
			g.setColor(color);
		}
						
		//int yTop = chartInfo.getYExtent();
		
		double x1,x2,y1,y2;
		x1 = (state.pos.x);
		x2 = (state.pos.x + dx);
		y1 = ((double)state.yExtent - state.pos.y);
		y2 = ((double)state.yExtent - (state.pos.y + dy));
		
		//x1 = (int)(position.getX() * scaleFactor);
		//x2 = (int)((position.getX() + cmd.getX()) * scaleFactor);
		//y1 = (int)((yTop - position.getY()) * scaleFactor);
		//y2 = (int)( (yTop - (position.getY() + cmd.getY()) ) * scaleFactor);
		
		Stroke oldStroke = g.getStroke();
		BasicStroke s = new BasicStroke((float)width,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_BEVEL);
        g.setStroke(s);
		
		//g.drawLine(x1,y1,x2,y2);
		g.draw(new Line2D.Double(x1,y1,x2,y2));
		
		g.setStroke(oldStroke);
						
		state.pos.setLocation(state.pos.x + dx, (state.pos.y + dy) );

		
	}
	@Override
	public void execute(Graphics2D g, DrawState state, int width, int height, double zoom, Point multiChartOffset) {
		int x1,x2,y1,y2;
		x1 = (int)(state.pos.x);
		x2 = (int)(state.pos.x + dx);
		y1 = (int)(state.yExtent - state.pos.y);
		y2 = (int)(state.yExtent - (state.pos.y + dy));
		Point curPos = ChartConversion.convertToScreenCoord(new Point(x1 < x2 ? x1 : x2, y1 < y2 ? y1 : y2), zoom, state);
		Rectangle objectBox = new Rectangle(curPos.x, curPos.y, 
				(int)(ChartConversion.convertToScreenSize(x1 < x2 ? x2 - x1 + this.width / 2: x1 - x2 + this.width / 2, zoom) + 1), 
				(int)(ChartConversion.convertToScreenSize(y1 < y2 ? y2 - y1 + this.width / 2: y1 - y2 + this.width / 2, zoom) + 1));

		if (viewable(getScreenArray(width, height), objectBox, multiChartOffset))
			execute(g, state);
		else
			state.pos.setLocation(state.pos.x + dx, (state.pos.y + dy) );
	}

	@Override
	public Rectangle getShapeBox() {
		Rectangle retValue = new Rectangle(coord.x, coord.y, (int)dx, (int)dy);
		DrawCommand.curPos.x += dx;
		DrawCommand.curPos.y += dy;
		return retValue;
	}

	@Override
	public void executeAbsolute(Graphics2D g, DrawState state, int width, int height, double zoom) {
		g.setColor(color);
						
		//int yTop = chartInfo.getYExtent();
		
		int x1,x2,y1,y2;
		x1 = (int)(coord.x);
		x2 = (int)(coord.x + dx);
		y1 = (int)(state.yExtent - coord.y);
		y2 = (int)(state.yExtent - (coord.y + dy));
		
		//x1 = (int)(position.getX() * scaleFactor);
		//x2 = (int)((position.getX() + cmd.getX()) * scaleFactor);
		//y1 = (int)((yTop - position.getY()) * scaleFactor);
		//y2 = (int)( (yTop - (position.getY() + cmd.getY()) ) * scaleFactor);
		
		Stroke oldStroke = g.getStroke();
		BasicStroke s = new BasicStroke((float)this.width,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_BEVEL);
        g.setStroke(s);
		
		g.drawLine(x1,y1,x2,y2);
		
		g.setStroke(oldStroke);
	}

}
