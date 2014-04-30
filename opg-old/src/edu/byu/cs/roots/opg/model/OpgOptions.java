package edu.byu.cs.roots.opg.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.byu.cs.roots.opg.chart.ChartMarginData;
import edu.byu.cs.roots.opg.chart.ChartType;
import edu.byu.cs.roots.opg.chart.preset.templates.StylingBoxScheme;
import edu.byu.cs.roots.opg.fonts.OpgFont;

public class OpgOptions extends Observable implements Serializable
{
	
	
	private static final long serialVersionUID = 1L;
	transient CopyOnWriteArraySet<ChangeListener> listenerset;
	
	boolean changed;
	
	protected float minFontSize;
	
	protected float maxFontSize;
	
	protected boolean italic;
	
	protected boolean bold;
	
	/**
	 * Should the scheme list be refreshed
	 */
	protected boolean refreshSchemeList;
	
	protected OpgFont font;
	
	ArrayList<StylingBoxScheme> chartSchemes;
	
	protected ChartMarginData marginData;
	
	/**
	 * Represents the kind of chart currently selected
	 */
	protected ChartType chartType;
	
	/**
	 * Has a different chart style been selected
	 */
	protected boolean newChartScheme;
	
	private boolean isPreferredLength = false;
	
	private double preferredLength = 0d;
	
	/**
	 * The maximum number of ancesteral generations based on the 
	 * data structure and the paper size, displayed on the slider
	 */
	protected int maxAncesSlider;
	/**
	 * The maximum number of descendant generations based on the 
	 * data structure and the paper size, displayed on the slider
	 */
	protected int maxDescSlider;
	
	/**
	 * The chosen number of ancestors on the slider
	 */
	protected int ancesSliderValue;
	/**
	 * The chosen number of descendants on the slider
	 */
	protected int descSliderValue;
	
	protected ArrayList<Individual> collapsedAncestors;
	
	protected HashMap<String, String> duplicateIndis;
	
	protected HashMap<String, Single<String>> duplicateLabels;
	
	protected ArrayList<Individual> globalIndiFlags;
	
	public OpgOptions(){
		
		this.maxFontSize = 12;
		this.minFontSize = 8;
		this.italic = false;
		this.bold = false;
		this.font = OpgFont.values()[0];
		
		this.maxAncesSlider = 1;
		this.maxDescSlider = 1;
		
		this.ancesSliderValue = 1;
		this.descSliderValue = 1;
		
		marginData = new ChartMarginData();
		chartSchemes = new ArrayList<StylingBoxScheme>();
		chartType = null;
		
		collapsedAncestors = new ArrayList<Individual>();
		duplicateIndis = new HashMap<String, String>();
		duplicateLabels = new HashMap<String, Single<String>>();
		globalIndiFlags = new ArrayList<Individual>();
		
		listenerset = new CopyOnWriteArraySet<ChangeListener>();

	}
	
	
	
	public boolean isBold() {
		return bold;
	}

	public void setBold(boolean bold) {
		changed(13);
		this.bold = bold;
	}

	public boolean isItalic() {
		return italic;
	}

	public void setItalic(boolean italic) {
		changed(15);
		this.italic = italic;
	}
	
	public float getMaxFontSize() {
		return maxFontSize;
	}

	public void setMaxFontSize(float maxFontSize) {
		changed(16);
		this.maxFontSize = maxFontSize;
	}

	public float getMinFontSize() {
		return minFontSize;
	}

	public void setMinFontSize(float minFontSize) {
		changed(17);
		this.minFontSize = minFontSize;
	}
	
	public OpgFont getFont() {
		return font;
	}

	public void setFont(OpgFont font) {
		changed(14);
		this.font = font;
	}
	
	public void setNewChartScheme(boolean set){
		changed(24);
		newChartScheme = set;
	}
	
	public boolean getNewChartScheme(){
		return newChartScheme;
	}
	
	public ArrayList<StylingBoxScheme> getChartStyles() {
		return chartSchemes;
	}
	
	public void setChartStyles(ArrayList<StylingBoxScheme> styles){
		chartSchemes = styles;
	}
	
	public void setChartMargins(double left, double right, double top, double bottom){
		marginData.setLeft(left);
		marginData.setRight(right);
		marginData.setTop(top);
		marginData.setBottom(bottom);
	}
	
	public ChartMarginData getChartMargins(){
		return marginData;
	}
	
	//returns the chart type that these options are for
	public ChartType getChartType(){return chartType;}
	
	public void setChartType(ChartType chartType) {
		changed(3);
		this.chartType = chartType;
	}
	
	/**
	 * Used for printing purposes.  Returns true if this chart type has a certain height
	 * it must be printed at.
	 * @return
	 */
	public boolean isPreferredLength(){
		return isPreferredLength;
	}
	
	/**
	 * Used for printing purposes.  If the chart type has a certain height it needs to be 
	 * printed at, this returns the height.
	 * @return
	 */
	public double getPreferredLength(){
		return preferredLength;
	}
	
	public void setPreferredLength(boolean pref, double height){
		isPreferredLength = pref;
		preferredLength = height;
	}
	
	/**
	 * returns the maximum number of ancestor generations that can be included in the chart
	 * @return
	 */
	public int getMaxAncesSlider(){return maxAncesSlider;}
	/**
	 * returns the maximum number of descendent generations that can be included in the chart
	 * @return
	 */
	public int getMaxDescSlider(){return maxDescSlider;}
	
	/**
	 * Sets the maximum amount of ancestors with the current root, setting the limit
	 * for the ancestor slider.  Only works if this is the primary ChartMaker
	 * @param maxAncesGens
	 * @param isPrimaryMaker
	 */
	public void setMaxAncesSlider(int maxAncesGens, boolean isPrimaryMaker) {
		if(isPrimaryMaker){
			changed(4);
			this.maxAncesSlider = maxAncesGens;
		}
	}

	/**
	 * Sets the maximum amount of descendants with the current root, setting the limit
	 * for the descendant slider.  Only works if this is the primary ChartMaker
	 * @param maxDescGens
	 * @param isPrimaryMaker
	 */
	public void setMaxDescSlider(int maxDescGens, boolean isPrimaryMaker) {
		if(isPrimaryMaker){
			changed(5);
			this.maxDescSlider = maxDescGens;
		}
	}
	
	/**
	 * Returns the value for ancestors chosen by the ancestor slider
	 */
	public int getAncesSliderValue(){return ancesSliderValue;}
	/**
	 * Returns the value for descendants chosen by the ancestor slider
	 */
	public int getDescSliderValue(){return descSliderValue;}
	/**
	 * Sets the chosen value of the Ancestor Slider
	 */
	public void setAncesSliderValue(int set){ancesSliderValue = set;}
	/**
	 * Sets the chosen value of the Descendant Slider
	 */
	public void setDescSliderValue(int set){descSliderValue = set;}

	public boolean isCollapsed(Individual indi){
		if (collapsedAncestors.contains(indi))
			return true;
		return false;
	}
	
	public void addCollapsed(Individual indi){
		collapsedAncestors.add(indi);
	}
	
	public void removeCollapsed(Individual indi){
		collapsedAncestors.remove(indi);
	}
	
	public ArrayList<Individual> getCollapsedList(){
		return collapsedAncestors;
	}
	
	public HashMap<String, String> getDuplicateMap(){
		return duplicateIndis;
	}
	
	public void resetDuplicateMap(){
		duplicateIndis.clear();
	}
	
	public HashMap<String, Single<String>> getDuplicateLabels(){
		return duplicateLabels;
	}
	
	public void resetDuplicateLabels(){
		duplicateLabels.clear();
	}
	
	public void addToGlobalIndiFlags(Individual indi){
		if (!globalIndiFlags.contains(indi))
			globalIndiFlags.add(indi);
	}
	
	public void clearGlobalIndiFlags(){
		for (Individual indi : globalIndiFlags){
			indi.getOriginatingPages().clear();
		}
		
		globalIndiFlags.clear();
	}
	
	public void setRefreshSchemeList(boolean set){
		refreshSchemeList = set;
	}
	
	public boolean getRefreshSchemeList(){
		return refreshSchemeList;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	protected void changed(int code){
//		System.out.println("changed!! " + code);
		changed = true;
		firePropertyChanged();
	}
	
	/**
	 * Function to allow change listener support
	 * @param arg0
	 */
	public void addChangeListener(ChangeListener arg0) {
		listenerset.add(arg0);
	}
	/**
	 * Function to allow change listener support
	 * @param arg0
	 */
	public void removeChangeListener(ChangeListener arg0) {
		listenerset.remove(arg0);
	}
	private void firePropertyChanged(){
		ChangeEvent e = new ChangeEvent(this);
		if(listenerset == null) 
			listenerset = new CopyOnWriteArraySet<ChangeListener>();
		for(ChangeListener listener : listenerset){
			listener.stateChanged(e);
		}
	}
}
