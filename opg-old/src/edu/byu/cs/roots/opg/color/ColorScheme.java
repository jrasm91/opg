package edu.byu.cs.roots.opg.color;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

import edu.byu.cs.roots.opg.model.Individual;


/**
 * A color scheme is used to determine what color to paint the indivudual boxes for
 * the chart.
 * Usage: call colorTree on the root, and then when you need to know what color 
 * to paint ancestors/descendants call getColor on the person in question.
 * @author Travix
 *
 */
public abstract class ColorScheme implements Serializable{

	public static final short colorup = 0;
	public static final short colordown = 1;
	protected String displayName = "NO NAME";
	public ArrayList<String> coloroptions = new ArrayList<String>();
	
	static final long serialVersionUID = 1000L;
	
	/**
	 * colorTree traverses the genealogy tree starting at the given
	 * individual and proceeding in the direction specified. 
	 * This function must be called before getColor will return anything
	 * other then black
	 * @param root
	 * @param direction
	 */
	public void colorTree(Individual root, short direction){}
	
	/**
	 * Deletes any/all coloring information created from colorTree
	 *
	 */
	public void clearTree(){}
		
	/**
	 * Returns a color based on the traversal of the tree done in colorTree
	 * 
	 * @param id The unique identifier for the individual
	 * @return The color for that individual determined by this color scheme
	 */
	public Color getColor(String id){
		return Color.black;
	}
	
	public String toString(){
		return displayName;
	}
}
