package edu.byu.cs.roots.opg.chart.presetvertical;

import java.util.ArrayList;
import java.util.List;

public class TreeFormat {
	
	
	private List<BoxFormat> formats;
	private List<Double> generationWidths;
	
	public TreeFormat() {
		formats = new ArrayList<BoxFormat>();
		generationWidths = new ArrayList<Double>();
	}
	
	/**
	 * Gets BoxFormat for the specified generation
	 * @param generation generation number
	 * @return BoxFormat for the specified generation
	 */
	public BoxFormat get(int generation) {
		if(generation >= formats.size())
			return formats.get(formats.size()-1);
		return formats.get(generation);
	}
	
	public double getIntrusion(int generation) {
		if(generation >= generationWidths.size())
			return generationWidths.get(generationWidths.size()-1);
		return generationWidths.get(generation);
	}
		
	/**
	 * Adds generation  and the generationWidth to the tree format,
	 * @param f format to add
	 * @param d double to add for the generationWidth
	 */
	public void add(BoxFormat f, double d) {
		formats.add(f);
		generationWidths.add(d);
	}
	
}
