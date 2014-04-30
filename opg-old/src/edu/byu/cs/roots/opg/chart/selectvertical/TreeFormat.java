package edu.byu.cs.roots.opg.chart.selectvertical;

import java.util.ArrayList;
import java.util.List;

public class TreeFormat {
	
	
	private List<BoxFormat> formats;
	
	public TreeFormat() {
		formats = new ArrayList<BoxFormat>();
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
	
	/**
	 * Adds generation to the tree format
	 * @param f format to add
	 */
	public void add(BoxFormat f) {
		formats.add(f);
	}

}
