package edu.byu.cs.roots.opg.chart;

import java.awt.geom.Rectangle2D;

import edu.byu.cs.roots.opg.model.Individual;

public abstract class IndividualNode {
	
	protected Individual indi;
	protected DisplayOptions displayOptions;
	protected Rectangle2D.Double boundsRect;
	
	public Individual getIndi() {
		return indi;
	}
	public DisplayOptions getDisplayOptions() {
		return displayOptions;
	}
	public Rectangle2D.Double getBoundsRect() {
		return boundsRect;
	}
	
}
