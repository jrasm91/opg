package edu.byu.cs.roots.opg.chart.cmds;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Point2D;

import edu.byu.cs.roots.opg.model.OpgSession;

public class DrawState {
	
	public Point2D.Double pos = null;
	public Font font = null;
	public Color curColor = null;
	public Color fontColor = null;
	//public double scaleFactor = 1.0;
	public int xExtent;
	public int yExtent;
	public OpgSession session = null;
	
	// Positive means the chart is to the left of the viewing area
	// Negative means that the chart is to the right of the viewing area
	// Value is the distance to the chart edge.
	public double chartLeftToDisplay = 0;
	public double chartTopToDisplay = 0;
	
	public void reset(){
		pos = new Point2D.Double(0.0,0.0);
		fontColor = curColor = Color.gray;
	}
}
