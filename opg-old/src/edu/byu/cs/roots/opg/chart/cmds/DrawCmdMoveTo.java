package edu.byu.cs.roots.opg.chart.cmds;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;

public class DrawCmdMoveTo extends DrawCommand implements Serializable
{
	private double x;
	private double y;
	private Point coord;
	static final long serialVersionUID = 1000L;
		
	public DrawCmdMoveTo(double x, double y)
	{
		this.x = x;
		this.y = y;
		coord = new Point((int)Math.round(x),(int)Math.round(y));
		DrawCommand.curPos = new Point(coord);
	}
	
	public double getX() { return x; }
	public double getY() { return y; }	
	
	public String toString()
	{
		return super.toString() + " - " + x + y;
	}

	public void execute(Graphics2D g, DrawState state) {
		state.pos.setLocation(x, y);
	}
	@Override
	public void execute(Graphics2D g, DrawState state, int width, int height, double zoom, Point multiChartOffset) {
		execute(g, state);
	}

	@Override
	// Null means to include this every time.
	public Rectangle getShapeBox() {
		return null;
	}

	@Override
	public void executeAbsolute(Graphics2D g, DrawState state, int width, int height, double zoom) {
		return;
	}

}
