package edu.byu.cs.roots.opg.chart.cmds;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

import edu.byu.cs.roots.opg.chart.ChartConversion;

public class DrawCmdFilledCircle extends DrawCommand {

	private static final long serialVersionUID = 5680929513099969019L;
	private double radius, lineWidth;
	private Color lineColor, fillColor;
	private Point coords;
	public DrawCmdFilledCircle( double radius, double lineWidth, Color lineColor, Color fillColor) {
		super();
		this.radius = radius;
		this.lineWidth = lineWidth;
		this.lineColor = lineColor;
		this.fillColor = fillColor;
		coords = new Point(DrawCommand.curPos);
	}
	
	public void execute(Graphics2D g, DrawState state) {
		double x = state.pos.x;
		double y = state.pos.y;
		g.setStroke(new BasicStroke((float) lineWidth));
		g.setColor(fillColor);
		Ellipse2D.Double oval = new Ellipse2D.Double(x-radius, y-radius, 2.0*radius, 2.0*radius);  
		
		//g.fillOval((int) (x-radius), (int) (y-radius), (int) (2*radius), (int) ( 2*radius));
		g.fill(oval);
		g.setColor(lineColor);
		//g.drawOval((int) (x-radius), (int) (y-radius), (int) (2*radius), (int)(2*radius));
		g.draw(oval);
	}
	
	@Override
	public void execute(Graphics2D g, DrawState state, int viewWidth, int viewHeight, double zoom, Point multiChartOffset) {
		double x = state.pos.x;
		double y = state.pos.y;
		// The radius of the circle converted to screen size
		double screenRadius = ChartConversion.convertToScreenSize(radius, zoom);
		Point curPos = ChartConversion.convertToScreenCoord(new Point((int)x,(int)y), zoom, state);

		Rectangle objectBox = new Rectangle((int)(curPos.x - screenRadius), (int)(curPos.y - screenRadius), (int)(screenRadius * 2), (int)(screenRadius * 2));
		
		if (!viewable(getScreenArray(viewWidth, viewHeight), objectBox, multiChartOffset)) return;
		
		execute(g, state);
	}

	@Override
	public Rectangle getShapeBox() {
		return new Rectangle((int)(coords.x-radius),(int)(coords.y-radius), (int)(radius*2),(int)(radius*2));
	}

	@Override
	public void executeAbsolute(Graphics2D g, DrawState state, int width, int height, double zoom) {
		double x = coords.x;
		double y = coords.y;
		g.setStroke(new BasicStroke((float) lineWidth));
		g.setColor(fillColor);
		g.fillOval((int) (x-radius), (int) (y-radius), (int) (2*radius), (int) ( 2*radius));
		g.setColor(lineColor);
		g.drawOval((int) (x-radius), (int) (y-radius), (int) (2*radius), (int)(2*radius));
	}
	
	
	
	
	
	
	
	
}
