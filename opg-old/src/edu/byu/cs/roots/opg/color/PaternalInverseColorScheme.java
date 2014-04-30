package edu.byu.cs.roots.opg.color;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import edu.byu.cs.roots.opg.model.Family;
import edu.byu.cs.roots.opg.model.Individual;

public class PaternalInverseColorScheme extends ColorScheme {
	private static final long serialVersionUID = -2108399971530615037L;

	private HashMap<String, Color> colorMap;
		
	private float[] mothers_mother;
	private float[] mothers_father;
	private float[] fathers_mother;
	private float[] fathers_father;
		
		
		public PaternalInverseColorScheme(){
			super();
			colorMap = new HashMap<String, Color>();
			//setRootColor(new Color(131,208,255)); //more saturated blue
			coloroptions.add("Fathers_father");
			coloroptions.add("Fathers_mother");
			coloroptions.add("Mothers_father");
			coloroptions.add("Mothers_mother");
			
			mothers_mother = Color.RGBtoHSB(255, 255, 203, null);
			mothers_father = Color.RGBtoHSB(255, 190, 190, null);
			fathers_mother = Color.RGBtoHSB(182, 255, 171, null);
			fathers_father = Color.RGBtoHSB(196, 211, 255, null);
			
			displayName = "Spectrum";
		}

		@Override
		public String toString() {
			return "Paternal Inverse Spectrum";
		}
		
		@Override
		public void clearTree() {
			colorMap.clear();
		}

		@Override
		public void colorTree(Individual root, short direction) {
			switch( direction ){			
				case colorup:
					
					if(root.mother != null && root.mother.mother != null) colorup(0,1,root.mother.mother, 2, false, mothers_mother);
					if(root.mother != null && root.mother.father != null) colorup(0,1,root.mother.father, 2, true, mothers_father);
					if(root.father != null && root.father.mother != null) colorup(0,1,root.father.mother, 2, false, fathers_mother);
					if(root.father != null && root.father.father != null) colorup(0,1,root.father.father, 2, true, fathers_father);
					colorMap.put(root.id, Color.getHSBColor(fathers_father[0], fathers_father[1], fathers_father[2]));
					if(root.father != null) colorMap.put(root.father.id, Color.getHSBColor(fathers_father[0],fathers_father[1],fathers_father[2]));
					if(root.mother != null) colorMap.put(root.mother.id, Color.getHSBColor(mothers_father[0], mothers_father[1], mothers_father[2]));
					//colorup(0, 1, root, 0, true, Color.getHSBColor(hsb[0], hsb[1], hsb[2]));
					break;
				case colordown:
					System.out.println("Not meant for Descendancy coloring");
					break;
				default:
			}
			
		}

		private void colorup(float huebottom, float huetop, Individual indi, int gen, boolean paternal, float[] prevColor){
			colorMap.put(indi.id, Color.getHSBColor((float) (prevColor[0]), prevColor[1], prevColor[2]));
//			if (paternal){
//				if(indi.father != null && !colorMap.containsKey(indi.father.id))
//					colorup(0, 0, indi.father, gen+1, true, prevColor);
//				if(indi.mother != null && !colorMap.containsKey(indi.mother.id))
//					colorup(huebottom, 0, indi.mother, gen+1, false, prevColor);
//			}
//			else{
//				if(indi.father != null && !colorMap.containsKey(indi.father.id))
//					colorup(0, 0, indi.father, gen+1, true, new float[] {prevColor[0], prevColor[1]-gen<=4?0.25f:0f, prevColor[2]});
//				if(indi.mother != null && !colorMap.containsKey(indi.mother.id))
//					colorup(huebottom, 0, indi.mother, gen+1, false, new float[] {prevColor[0], prevColor[1]-gen<=4?0.25f:0f, prevColor[2]});
//			}
			
			if (gen == 2){
				if(indi.father != null && !colorMap.containsKey(indi.father.id))
					colorup(0, 0, indi.father, gen+1, true, new float[] {prevColor[0], prevColor[1], prevColor[2]});
				if(indi.mother != null && !colorMap.containsKey(indi.mother.id))
					colorup(huebottom, 0, indi.mother, gen+1, false, new float[] {prevColor[0], Math.max(prevColor[1], 1f-prevColor[1])/2.0f, prevColor[2]});
		
			}
			else{
				if(indi.father != null && !colorMap.containsKey(indi.father.id))
					colorup(0, 0, indi.father, gen+1, true, prevColor);
				if(indi.mother != null && !colorMap.containsKey(indi.mother.id))
					colorup(huebottom, 0, indi.mother, gen+1, false, prevColor);
			}
				
			
		}
		
		private void colordown(float huetop, float huebottom, Individual indi){
//			System.out.println("Coloring down");
			ArrayList<Family> fams = indi.fams;
			float hue = .5f*(huebottom+huetop);
			//colorMap.put(indi.id, Color.getHSBColor(hue, hsb[1], hsb[2]));
//			int fake;
//			if(fams == null)
//				fake = 0;
			for(Family fam:fams){
				float desc = fam.children.size();
				float delta = huetop-huebottom;
				for(int i = 0; i < desc;i++){
					try{
					colordown(huebottom + (i/desc)*delta, huebottom + ((i+1)/desc)*delta, fam.children.get(i) );
					} catch (StackOverflowError e){System.out.println("******" + indi);}
				}
//				System.out.println("Putting color for " + indi.id);
				
			}
		}
		
		
		@Override
		public Color getColor(String id) {
			Color color = colorMap.get(id);
			return (color != null) ? color : Color.black;
		}

		/**
		 * @return the fathers_father
		 */
		public Color getFathers_father() {
			return Color.getHSBColor(fathers_father[0], fathers_father[1], fathers_father[2]);
		}

		/**
		 * @param fathers_father the fathers_father to set
		 */
		public void setFathers_father(Color fathers_father) {
			int[] rgb = {fathers_father.getRed(), fathers_father.getGreen(), fathers_father.getBlue()};
			this.fathers_father = Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], null);
		}

		/**
		 * @return the fathers_mother
		 */
		public Color getFathers_mother() {
			return Color.getHSBColor(fathers_mother[0], fathers_mother[1], fathers_mother[2]);
		}

		/**
		 * @param fathers_mother the fathers_mother to set
		 */
		public void setFathers_mother(Color fathers_mother) {
			int[] rgb = {fathers_mother.getRed(), fathers_mother.getGreen(), fathers_mother.getBlue()};
			this.fathers_mother = Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], null);
		}

		/**
		 * @return the mothers_father
		 */
		public Color getMothers_father() {
			return Color.getHSBColor(mothers_father[0], mothers_father[1], mothers_father[2]);
		}

		/**
		 * @param mothers_father the mothers_father to set
		 */
		public void setMothers_father(Color mothers_father) {
			int[] rgb = {mothers_father.getRed(), mothers_father.getGreen(), mothers_father.getBlue()};
			this.mothers_father = Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], null);
		}

		/**
		 * @return the mothers_mother
		 */
		public Color getMothers_mother() {
			return Color.getHSBColor(mothers_mother[0], mothers_mother[1], mothers_mother[2]);
		}

		/**
		 * @param mothers_mother the mothers_mother to set
		 */
		public void setMothers_mother(Color mothers_mother) {
			int[] rgb = {mothers_mother.getRed(), mothers_mother.getGreen(), mothers_mother.getBlue()};
			this.mothers_mother = Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], null);
		}


}
