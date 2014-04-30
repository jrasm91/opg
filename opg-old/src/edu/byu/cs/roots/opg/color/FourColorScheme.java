package edu.byu.cs.roots.opg.color;

import java.awt.Color;
import java.util.HashMap;

import edu.byu.cs.roots.opg.model.Individual;

public class FourColorScheme extends ColorScheme {

	private static final long serialVersionUID = -9128034388385893906L;

	private HashMap<String, Color> colorMap;
	
	private Color mothers_mother;
	private Color mothers_father;
	private Color fathers_mother;
	private Color fathers_father;
	
	public FourColorScheme(){
		colorMap = new HashMap<String, Color>();
		
		coloroptions.add("Fathers_father");
		coloroptions.add("Fathers_mother");
		coloroptions.add("Mothers_father");
		coloroptions.add("Mothers_mother");
		
		
		mothers_mother = new Color(255,255,203);
		mothers_father = new Color(255,190,190);
		fathers_mother = new Color(182,255,171);
		fathers_father = new Color(196,211,255);
	}
	
	@Override
	public String toString() {
		return "4 Color";
	}
	
	@Override
	public void clearTree() {
		colorMap.clear();
	}
	
	@Override
	public void colorTree(Individual root, short direction) {
		switch( direction ){
		case colorup:

			if(root.mother != null && root.mother.mother != null) colorup(root.mother.mother, mothers_mother);
			if(root.mother != null && root.mother.father != null) colorup(root.mother.father, mothers_father);
			if(root.father != null && root.father.mother != null) colorup(root.father.mother, fathers_mother);
			if(root.father != null && root.father.father != null) colorup(root.father.father, fathers_father);
			colorMap.put(root.id, fathers_father);
			if(root.father != null) colorMap.put(root.father.id, fathers_father);
			if(root.mother != null) colorMap.put(root.mother.id, mothers_father);
			
			
			break;
		case colordown:
			System.out.println("Coloring descendance is not possible with a 4 color scheme");
			break;
		default:
	}
	
	}
	
	private void colorup(Individual indi, Color color) {
		colorMap.put(indi.id, color);
		if(indi.father != null && !colorMap.containsKey(indi.father.id))
			colorup(indi.father, color);
		if(indi.mother != null && !colorMap.containsKey(indi.mother.id))
			colorup(indi.mother, color);
	}

	@Override
	public Color getColor(String id) {
		return colorMap.get(id);
	}
	

	/**
	 * @return the fathers_father
	 */
	public Color getFathers_father() {
		return fathers_father;
	}

	/**
	 * @param fathers_father the fathers_father to set
	 */
	public void setFathers_father(Color fathers_father) {
		this.fathers_father = fathers_father;
	}

	/**
	 * @return the fathers_mother
	 */
	public Color getFathers_mother() {
		return fathers_mother;
	}

	/**
	 * @param fathers_mother the fathers_mother to set
	 */
	public void setFathers_mother(Color fathers_mother) {
		this.fathers_mother = fathers_mother;
	}

	/**
	 * @return the mothers_father
	 */
	public Color getMothers_father() {
		return mothers_father;
	}

	/**
	 * @param mothers_father the mothers_father to set
	 */
	public void setMothers_father(Color mothers_father) {
		this.mothers_father = mothers_father;
	}

	/**
	 * @return the mothers_mother
	 */
	public Color getMothers_mother() {
		return mothers_mother;
	}

	/**
	 * @param mothers_mother the mothers_mother to set
	 */
	public void setMothers_mother(Color mothers_mother) {
		this.mothers_mother = mothers_mother;
	}
	
	
	
	
	
	
	
	
	
	
	
}
