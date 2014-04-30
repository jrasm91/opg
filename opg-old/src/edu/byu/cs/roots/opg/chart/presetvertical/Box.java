package edu.byu.cs.roots.opg.chart.presetvertical;

import java.util.ArrayList;

import edu.byu.cs.roots.opg.chart.ChartDrawInfo;
import edu.byu.cs.roots.opg.chart.ChartOptions;
import edu.byu.cs.roots.opg.model.Individual;

/**
 * Represents box of a genealogy chart, either descendent or ancestry
 *
 */
public class Box
{
	Individual indi;
	protected Generation gen;
	protected BoxFormat boxFormat;
	
	int numInGen; //the vertical ordinal number (zero based) of this box in a generation
	protected ArrayList<Double> upperBounds; //list of upper bounding vertical offsets from this individual's center for each generation in the sub tree (0 is this individual's offset)
	protected ArrayList<Double> lowerBounds; //list of lower bounding vertical offsets from this individual's center for each generation in the sub tree (0 is this individual's offset)
	protected int dupIndex;
	

	double upperSubTreeOffset;
	double lowerSubTreeOffset;
	
	public Box(Individual indi) {
		this.indi = indi;
		gen = null;
		upperBounds = new ArrayList<Double>();
		lowerBounds = new ArrayList<Double>();
		dupIndex = 0;
		boxFormat = null;//BoxFormat.getDefault();
	}
	
	/**
	 * Places drawing commands into chart to "draw the box" onto the chart.
	 * Draws names and associated information about Individuals associated with this box.
	 * Draws recursively through the tree.
	 * @param chart The chart to draw on.
	 * @param options The chart options used in determining color, style, etc.
	 * @param x The base x coordinate to draw the box at.
	 * @param y The base y coordinate to draw the box at.
	 */
	void drawBox (ChartDrawInfo chart, ChartOptions options, double x, double y){}
	
	/**
	 * Return the height of the 
	 * @return
	 */
	public double getSubtreeHeight() 
	{
		return upperSubTreeOffset - lowerSubTreeOffset;
	}
	
	/**
	 * Sets index number for duplicate individual. Expects duplicateMap is already be populated
	 * where an id is mapped to -1 if there is a duplicate, and 0 otherwise. 
	 */
	public void setDuplicateIndex(int index)
	{
		dupIndex = index;
	}
	
	/**
	 * Gets index number for duplicate individual.
	 */
	protected int getDuplicateIndex()
	{
		return dupIndex;
	}

	public void setGeneration(Generation gen) {
		this.gen = gen;
	}

	public Generation getGeneration() {
		return gen;
	}

	public double getHeight() {
		return boxFormat.getHeight();
	}
	
	public double getVerticalSpace() {
		return boxFormat.getVerticalSpacing();
		
	}

	public double getWidth() {
		return boxFormat.getWidth();
	}
	
	public double getLineWidth() {
		return boxFormat.getLineWidth();
	}
	
	public void setFormat(BoxFormat bf) {
		boxFormat = bf;
	}
	
	public void addUpperBound(double bound) {
		upperBounds.add(bound);
	}
	
	public void addLowerBound(double bound) {
		lowerBounds.add(bound);
	}
	
}
