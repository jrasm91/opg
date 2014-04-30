package edu.byu.cs.roots.opg.chart.circ;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import edu.byu.cs.roots.opg.chart.cmds.DrawCommand;
import edu.byu.cs.roots.opg.chart.cmds.DrawState;

public class DrawTextBox extends DrawCommand {

	private static final long serialVersionUID = -1339904827605370083L;
	String[] text;
	
	public DrawTextBox(String[] text) {
		super();
		this.text = text;
	}

	public void execute(Graphics2D g, DrawState state) {
		float size = g.getFont().getSize2D();
		for(int i = 0;i<text.length;i++){
			g.drawString(text[i], (float)state.pos.x, (float) (state.pos.y + i*1.1*size));
		}
		
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
