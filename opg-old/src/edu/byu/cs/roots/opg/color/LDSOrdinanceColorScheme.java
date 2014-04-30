package edu.byu.cs.roots.opg.color;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import edu.byu.cs.roots.opg.model.Family;
import edu.byu.cs.roots.opg.model.Individual;

public class LDSOrdinanceColorScheme extends ColorScheme {
	private static final long serialVersionUID = 2925791403399844528L;

	private HashMap<String, Color> colorMap;
	
	private Color none = new Color (255,124,124);
	private Color baptized = new Color (196,211,255);
	private Color endowed = new Color (182,255,171);
	private Color bep = new Color (230,195,255);
	private Color bes = new Color (255,255,203);
	private Color beps = new Color (255,255,255);
	
	public LDSOrdinanceColorScheme(){
		colorMap = new HashMap<String, Color>();
		
		coloroptions.add("None");
		coloroptions.add("Baptized");
		coloroptions.add("Endowed");
		coloroptions.add("BEP");
		coloroptions.add("BES");
		coloroptions.add("BEPS");
	}
	
	@Override
	public String toString() {
		return "LDS Ordinances";
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
		colorIndi(indi);
		if(indi.father != null && !colorMap.containsKey(indi.father.id))
			colorup(indi.father);
		if(indi.mother != null && !colorMap.containsKey(indi.mother.id))
			colorup(indi.mother);
		
	}
	
	
	private void colordown(Individual indi){
		ArrayList<Family> fams = indi.fams;
		colorIndi(indi);
		for(Family fam:fams){
			float desc = fam.children.size();
			for(int i = 0; i < desc;i++){
				colordown(fam.children.get(i) );
			}
		}
	}
	
	private void colorIndi(Individual indi)
	{
		if (indi.baptism && indi.endowment && indi.sealingToParents && indi.sealingToSpouse)
			colorMap.put(indi.id, beps);
		else if (indi.baptism && indi.endowment && indi.sealingToSpouse)
			colorMap.put(indi.id, bes);
		else if (indi.baptism && indi.endowment && indi.sealingToParents)
			colorMap.put(indi.id, bep);
		else if (indi.baptism && indi.endowment)
			colorMap.put(indi.id, endowed);
		else if (indi.baptism)
			colorMap.put(indi.id, baptized);
		else
			colorMap.put(indi.id, none);
	}

	@Override
	public Color getColor(String id) {
		return colorMap.get(id);
	}

	public Color getBaptized() {
		return baptized;
	}

	public void setBaptized(Color baptized) {
		this.baptized = baptized;
	}

	public Color getBEP() {
		return bep;
	}

	public void setBEP(Color bep) {
		this.bep = bep;
	}

	public Color getBEPS() {
		return beps;
	}

	public void setBEPS(Color beps) {
		this.beps = beps;
	}

	public Color getBES() {
		return bes;
	}

	public void setBES(Color bes) {
		this.bes = bes;
	}

	public HashMap<String, Color> getColorMap() {
		return colorMap;
	}

	public void setColorMap(HashMap<String, Color> colorMap) {
		this.colorMap = colorMap;
	}

	public Color getEndowed() {
		return endowed;
	}

	public void setEndowed(Color endowed) {
		this.endowed = endowed;
	}

	public Color getNone() {
		return none;
	}

	public void setNone(Color none) {
		this.none = none;
	}
}
