package edu.byu.cs.roots.opg.chart.circ;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;

import edu.byu.cs.roots.opg.chart.cmds.DrawCommand;
import edu.byu.cs.roots.opg.chart.cmds.DrawState;

public class DrawLine extends DrawCommand {
	private static final long serialVersionUID = 898311235412145402L;
	double x, y;
	Color c;
	
	
	public DrawLine(double x, double y, Color c) {
		super();
		this.x = x;
		this.y = y;
		this.c = c;
	}



	public void execute(Graphics2D g, DrawState state) {
		g.setStroke(new BasicStroke(5));
		g.setColor(c);
		g.draw(new Line2D.Double(state.pos.x, state.pos.y, x, y));
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
