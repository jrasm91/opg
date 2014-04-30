package edu.byu.cs.roots.opg.chart.preset.templates;

import java.io.Serializable;
import java.util.ArrayList;

import edu.byu.cs.roots.opg.chart.portrait.PortraitChartMaker;
import edu.byu.cs.roots.opg.model.PaperWidth;

public class StylingBoxScheme implements Serializable{

	private static final long serialVersionUID = 1L;
	public ArrayList<StylingBox> AncesByGenList;
	public ArrayList<StylingBox> DescByGenList;
	public String styleName;
	public PaperWidth preferredWidth;
	/**
	 * Initializes the arrays with style boxes chosen depending on the 
	 * amount of Ancestor and Descendant generations
	 * @param ancGens Amount of visible ancestors
	 * @param descGens Amount of visible descendants
	 */
	public StylingBoxScheme(String name){
		styleName = name;
		AncesByGenList = new ArrayList<StylingBox>();
		DescByGenList = new ArrayList<StylingBox>();
		preferredWidth = PaperWidth.findClosestSimpleFit(36);
	}
	
	public StylingBoxScheme(String name, double width){
		this(name);
		preferredWidth = PaperWidth.findClosestSimpleFit(width);
	}
	
	/**
	 * Gets the Ancestor Styling Box for the given generation
	 * Increases the array with the 'final' box, if too small
	 * @param gen Desired generation
	 * @return The Styling Box
	 */
	public StylingBox getAncesStyle(int gen){
		if (gen >= AncesByGenList.size())
			increaseAncesList(gen);
		return AncesByGenList.get(gen);
	}
	
	/**
	 * Gets the Descendant Styling Box for the given generation
	 * Increases the array with the 'final' box, if too small
	 * @param gen Desired generation
	 * @return The Styling Box
	 */
	public StylingBox getDescStyle(int gen){
		if (gen >= DescByGenList.size())
			increaseDescList(gen);
		return DescByGenList.get(gen);
	}
	
	public double getTotalDescWidth(int startGen, int maxGen){
		double retVal = 0.0;
		for (int i = startGen; i <= maxGen; i++){
			if (i >= DescByGenList.size())
				increaseDescList(i);
			retVal += DescByGenList.get(i).getBoxWidth();
		}
		return retVal;
	}
	
	public double getTotalAncesWidth(int startGen, int maxGen){
		double retVal = 0.0;
		for (int i = startGen; i <= maxGen; i++){
			if (i >= AncesByGenList.size())
				increaseAncesList(i);
			retVal += AncesByGenList.get(i).getBoxWidth();
		}
		return retVal;
	}
	
	public double getTotalDescHeight(int startGen, int maxGen){
		double retVal = 0.0;
		for (int i = startGen; i <= maxGen; i++){
			if (i >= DescByGenList.size())
				increaseDescList(i);
			retVal += DescByGenList.get(i).boxHeight;
		}
		return retVal;
	}
	
	public double getTotalAncesHeight(int startGen, int maxGen){
		double retVal = 0.0;
		for (int i = startGen; i <= maxGen; i++){
			if (i >= AncesByGenList.size())
				increaseAncesList(i);
			retVal += AncesByGenList.get(i).boxHeight;
		}
		return retVal;
	}
	
	public double getTotalDescOffset(int startGen, int maxGen){
		double retVal = 0.0;
		for (int i = startGen; i <= maxGen; i++){
			if (i >= DescByGenList.size())
				increaseDescList(i);
			retVal += DescByGenList.get(i).getRelativeOffset();
		}
		return retVal;
	}
	
	public double getTotalAncesOffset(int startGen, int maxGen){
		double retVal = 0.0;
		for (int i = startGen; i <= maxGen; i++){
			if (i >= AncesByGenList.size())
				increaseAncesList(i);
			retVal += AncesByGenList.get(i).getRelativeOffset();
		}			
		return retVal;
	}
		
	public void increaseAncesList(int desiredGen){
		while (AncesByGenList.size() <= desiredGen){
			try {
				AncesByGenList.add(AncesByGenList.get(AncesByGenList.size() - 1).getClass().newInstance());
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void increaseDescList(int desiredGen){
		while (DescByGenList.size() <= desiredGen){
			try {
				DescByGenList.add(DescByGenList.get(DescByGenList.size() - 1).getClass().newInstance());
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * Used only for the Portrait style
	 * @param maker
	 * @return
	 */
	public double getEffectiveRootDescendantHeight(PortraitChartMaker maker){
		StylingBox descRoot = DescByGenList.get(0);
		DescBoxParent descBox = maker.descTrees.get(0).descBox;
		double effectiveHeight = descRoot.boxHeight * ((descBox.single)? 1.2 : descBox.innerBoxes.size()*(descRoot.layout.parallelCouple?1:2)*1.2);
		return effectiveHeight + (descRoot.paddingAmount*2);
	}
	
	public String toString(){
		return styleName;
	}
}
