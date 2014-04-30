package edu.byu.cs.roots.opg.chart.selectvertical;

import java.util.ArrayList;

import edu.byu.cs.roots.opg.chart.ChartDrawInfo;
import edu.byu.cs.roots.opg.chart.ChartOptions;
import edu.byu.cs.roots.opg.model.Individual;

/**
 * 
 * 
 * @author derek
 *
 */
public abstract class Box
{
	Individual indi;
	protected ArrayList<Double> upperBounds; //list of upper bounding vertical offsets from this individual's center for each generation in the sub tree (0 is this individual's offset)
	protected ArrayList<Double> lowerBounds; //list of lower bounding vertical offsets from this individual's center for each generation in the sub tree (0 is this individual's offset)

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
}
