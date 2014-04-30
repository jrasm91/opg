package edu.byu.cs.roots.opg.chart.circ;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import edu.byu.cs.roots.opg.chart.cmds.DrawCommand;
import edu.byu.cs.roots.opg.chart.cmds.DrawState;

public class CmdSetColor extends DrawCommand {
	private static final long serialVersionUID = -7727897887233534279L;
	Color c;
	
	public CmdSetColor(Color c) {
		super();
		this.c = c;
	}

	public void execute(Graphics2D g, DrawState state) {
		state.curColor = c;
		g.setColor(c);
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
