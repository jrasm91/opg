package edu.byu.cs.roots.opg.chart.circ;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Arc2D;

import edu.byu.cs.roots.opg.chart.cmds.DrawCommand;
import edu.byu.cs.roots.opg.chart.cmds.DrawState;

public class DrawSemiCircle extends DrawCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1163955222685019426L;
	private double radius, lineWidth;
	private Color lineColor, fillColor;
	float startang, sweep;
	public DrawSemiCircle( double radius, double lineWidth, Color lineColor, Color fillColor, float startang, float sweep) {
		super();
		this.radius = radius;
		this.lineWidth = lineWidth;
		this.lineColor = lineColor;
		this.fillColor = fillColor;
		this.startang = startang;
		this.sweep = sweep;
	}
	
	public void execute(Graphics2D g, DrawState state) {
		double x = state.pos.x;
		double y = state.pos.y;
		g.setStroke(new BasicStroke((float) lineWidth));
		g.setColor(fillColor);
		g.fill(new Arc2D.Double( (x-radius),  (y-radius),  (2*radius),  ( 2*radius), startang,  sweep, Arc2D.OPEN));
		g.setColor(lineColor);
		g.draw(new Arc2D.Double(  (x-radius), (y-radius), (2*radius), (2*radius),  startang,  sweep, Arc2D.PIE));
//		g.draw(new Line2D.Double( x,  y,   (x + radius * Math.cos(Math.toRadians(startang))),  (y + radius * Math.sin(Math.toRadians(startang)))));
//		g.draw(new Line2D.Double( x,  y,   (x + radius * Math.cos(Math.toRadians(startang+sweep))),   (y + radius * Math.sin(Math.toRadians(startang+sweep)))));
	}
	
	public void execute(Graphics2D g, DrawState state, int width, int height, double zoom, Point multiChartOffset) {
		execute(g, state);
	}

	@Override
	public void executeAbsolute(Graphics2D g, DrawState state, int width,
			int height, double zoom) {
		
	}

	@Override
	public Rectangle getShapeBox() {
		return null;
	}

}
