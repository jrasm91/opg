package edu.byu.cs.roots.opg.chart.preset.templates;


import java.io.Serializable;
import java.util.ArrayList;






public class BoxLayoutParent implements Serializable{

	private static final long serialVersionUID = 1L;
	public ArrayList<LineLayout> lines = new ArrayList<LineLayout>();
	public boolean parallelCouple = false;
	public boolean rotateSingleDescendants = false;
	public String displayName = "";
	
	public void convertToSpecificLayout(BoxLayoutParent spec){
		spec.lines = lines;
		spec.parallelCouple = parallelCouple;
		spec.displayName = displayName;
		spec.rotateSingleDescendants = rotateSingleDescendants;
	}
	
	public String toString(){
		return displayName;
	}
}



