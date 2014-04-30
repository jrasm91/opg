package edu.byu.cs.roots.opg.chart.preset.templates;

import java.io.Serializable;
import java.util.ArrayList;

import edu.byu.cs.roots.opg.chart.ChartDrawInfo;
import edu.byu.cs.roots.opg.chart.ChartOptions;
import edu.byu.cs.roots.opg.chart.preset.templates.StylingBox;
import edu.byu.cs.roots.opg.model.Individual;

/**
 * 
 * 
 * @author derek
 *
 */
public abstract class Box implements Serializable
{
	
	
	private static final long serialVersionUID = 1L;

	/**
	 * All the preset styling options
	 */
	public StylingBox styleBox;
	
	private Individual indi;
	/**
	 * A list of the upper bounds of this box, and all paternal ancestors, 
	 * offset from this Boxes centre (The highest point of the subtree at each gen)
	 */
	private ArrayList<Double> upperBounds; 	
	/**
	 * A list of the lower bounds of this box, and all maternal ancestors, 
	 * offset from this Boxes centre (The lowest point of the subtree at each gen)
	 */
	private ArrayList<Double> lowerBounds; 
	
	/**
	 * Places drawing commands into chart to "draw the box" onto the chart.
	 * Draws names and associated information about Individuals associated with this box.
	 * Draws recursively through the tree.
	 * @param chart The chart to draw on.
	 * @param fontSize The size of the font to draw the information in.
	 * @param options The chart options used in determining color, style, etc.
	 * @param x The base x coordinate to draw the box at.
	 * @param y The base y coordinate to draw the box at.
	 * @param width The width of the bounding rectangle to draw the box in.
	 * @param height The height of the bounding rectangle to draw the box in.
	 */
	void drawBox (ChartDrawInfo chart, double fontSize, ChartOptions options, double x, double y, double width, double height){}

	public void setLowerBounds(ArrayList<Double> lowerBounds) {
		this.lowerBounds = lowerBounds;
	}

	public ArrayList<Double> getLowerBounds() {
		return lowerBounds;
	}

	public void setIndi(Individual indi) {
		this.indi = indi;
	}

	public Individual getIndi() {
		return indi;
	}

	public void setUpperBounds(ArrayList<Double> upperBounds) {
		this.upperBounds = upperBounds;
	}

	public ArrayList<Double> getUpperBounds() {
		return upperBounds;
	}
	
	

}
