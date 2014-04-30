package edu.byu.cs.roots.opg.chart.circ;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import edu.byu.cs.roots.opg.chart.cmds.DrawCommand;
import edu.byu.cs.roots.opg.chart.cmds.DrawState;

public class DrawText extends DrawCommand {

	private static final long serialVersionUID = 5065858407055289054L;
	String text;
	
	public DrawText(String text) {
		super();
		this.text = text;
	}

	public void execute(Graphics2D g, DrawState state) {
		g.drawString(text, (float)state.pos.x, (float)state.pos.y);
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
