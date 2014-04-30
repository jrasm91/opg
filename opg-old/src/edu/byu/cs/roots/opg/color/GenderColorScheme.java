package edu.byu.cs.roots.opg.color;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import edu.byu.cs.roots.opg.model.Family;
import edu.byu.cs.roots.opg.model.Gender;
import edu.byu.cs.roots.opg.model.Individual;

public class GenderColorScheme extends ColorScheme {
	
	private static final long serialVersionUID = 541130166163577671L;

	private HashMap<String, Color> colorMap;
	
	private Color male;
	private Color female;
	
	
	public GenderColorScheme(){
		colorMap = new HashMap<String, Color>();
		//male = new Color(117, 213, 255);
		male = new Color(179, 235, 255);
		//female = new Color(255, 141, 255);
		female = new Color(255, 206, 253);
		displayName = "Gender Color";
		coloroptions.add("Male");
		coloroptions.add("Female");
	}
	
	@Override
	public void clearTree() {
		colorMap.clear();
	}

	@Override
	public void colorTree(Individual root, short direction) {
		switch( direction ){
			case colorup:
				colorup(root);
				break;
			case colordown:
				colordown(root);
				break;
			default:
		}
		
	}

	private void colorup(Individual indi){
		if(indi == null) return;
		if(indi.father != null && !colorMap.containsKey(indi.father.id))
			colorup(indi.father);
		if(indi.mother != null && !colorMap.containsKey(indi.mother.id))
			colorup(indi.mother);
		colorMap.put(indi.id, (indi.gender == Gender.MALE) ? male : female);
	}
	
	
	private void colordown(Individual indi){
		ArrayList<Family> fams = indi.fams;
		colorMap.put(indi.id, (indi.gender == Gender.MALE) ? male : female);
		for(Family fam:fams){
			float desc = fam.children.size();
			for(int i = 0; i < desc;i++){
				colordown(fam.children.get(i) );
			}
		}
	}
	
	
	@Override
	public Color getColor(String id) {
		Color color = colorMap.get(id);
		return (color != null) ? color : Color.black;
	}

	/**
	 * @return the female
	 */
	public Color getFemale() {
		return female;
	}

	/**
	 * @param female the female to set
	 */
	public void setFemale(Color female) {
		this.female = female;
	}

	/**
	 * @return the male
	 */
	public Color getMale() {
		return male;
	}

	/**
	 * @param male the male to set
	 */
	public void setMale(Color male) {
		this.male = male;
	}
}
