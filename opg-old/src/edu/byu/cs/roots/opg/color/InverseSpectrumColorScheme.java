package edu.byu.cs.roots.opg.color;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import edu.byu.cs.roots.opg.model.Family;
import edu.byu.cs.roots.opg.model.Individual;

public class InverseSpectrumColorScheme extends ColorScheme {
	private static final long serialVersionUID = -2108399971530615037L;

private HashMap<String, Color> colorMap;
	
	private Color rootColor;
	private float[] hsb = {.5f, 1, 1};
	
	
	public InverseSpectrumColorScheme(){
		super();
		colorMap = new HashMap<String, Color>();
		//setRootColor(new Color(131,208,255)); //more saturated blue
		setRootColor(new Color(196,211,255));
		
		displayName = "Spectrum";
		coloroptions.add("RootColor");
	}

	@Override
	public String toString() {
		return "Inverse Spectrum";
	}
	
	@Override
	public void clearTree() {
		colorMap.clear();
	}

	@Override
	public void colorTree(Individual root, short direction) {
		switch( direction ){
			case colorup:
				colorup(0, 1, root);
				break;
			case colordown:
				colordown(hsb[0] - .5f, hsb[0] + .5f, root);
				break;
			default:
		}
		
	}

	private void colorup(float huetop, float huebottom, Individual indi){
		float hue = .5f*(huebottom+huetop);
	//	System.out.println("Indi: " + indi);
		colorMap.put(indi.id, Color.getHSBColor(hue-.5f+hsb[0], hsb[1], hsb[2]));
		if(indi.father != null && !colorMap.containsKey(indi.father.id))
			colorup(huetop, hue, indi.father);
		if(indi.mother != null && !colorMap.containsKey(indi.mother.id))
			colorup(huebottom, hue, indi.mother);
	}
	
	private void colordown(float huetop, float huebottom, Individual indi){
//		System.out.println("Coloring down");
		ArrayList<Family> fams = indi.fams;
		float hue = .5f*(huebottom+huetop);
		colorMap.put(indi.id, Color.getHSBColor(hue, hsb[1], hsb[2]));
//		int fake;
//		if(fams == null)
//			fake = 0;
		for(Family fam:fams){
			float desc = fam.children.size();
			float delta = huetop-huebottom;
			for(int i = 0; i < desc;i++){
				try{
				colordown(huebottom + (i/desc)*delta, huebottom + ((i+1)/desc)*delta, fam.children.get(i) );
				} catch (StackOverflowError e){System.out.println("******" + indi);}
			}
//			System.out.println("Putting color for " + indi.id);
			
		}
	}
	
	
	@Override
	public Color getColor(String id) {
		Color color = colorMap.get(id);
		return (color != null) ? color : Color.black;
	}

	/**
	 * @return the rootColor
	 */
	public Color getRootColor() {
		return rootColor;
	}

	/**
	 * @param rootColor the rootColor to set
	 */
	public void setRootColor(Color rootColor) {
		this.rootColor = rootColor;
		hsb = Color.RGBtoHSB(rootColor.getRed(), rootColor.getGreen(), rootColor.getBlue(), hsb);
	
	}

}
