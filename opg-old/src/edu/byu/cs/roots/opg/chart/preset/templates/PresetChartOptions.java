package edu.byu.cs.roots.opg.chart.preset.templates;

import edu.byu.cs.roots.opg.chart.ChartOptions;
/**
 * This class extends the ChartOptions class and adds the options
 * of getting whether we have a boarder around the boxes, if we
 * need to draw the titles, and if we should have rounded corners.
 * @author derek
 * @see ChartOptions
 * @version 1.0.0
 */
public class PresetChartOptions extends ChartOptions{

	public enum EndLineArrowStyle{SELF,PARENTS,GENERATIONS;
	public String toString(){
		if(this == SELF)
			return "Self";
		else if(this == PARENTS)
			return "Parents";
		else
			return "Generations";
	}
}
	/** Serial ID for when we want to save this class as a bit stream. */
	private static final long serialVersionUID = -6787151923234766638L;
	/** Should the boxes have rounded corners */
	protected boolean roundedCorners;
	/** Should the info box have a border */
	protected boolean boxBorder;
	/** Should we display titles on this chart */
	public boolean drawTitles;
	/** Should we allow intrusion of lower generations into higher generations **/
	protected boolean allowIntrusion;
	/** Should all names be bolded */
	protected boolean boldNames;
	/** Should we display 'End of Line' arrows */
	protected boolean drawEndOfLineArrows;
	/**What style is the "End of Line" arrows in */
	protected EndLineArrowStyle arrowStyle;
	/**The current Duplicate Label coun	 */
	protected int duplicateCount;
	
	public PresetChartOptions(ChartOptions options){
		super(options);
	}
	
	/**
	 * Do we need to draw titles onto this chart.
	 * @return A boolean telling whether or not to draw titles 
	 * onto the chart.
	 */
	public boolean isDrawTitles() {
		return drawTitles;
	}

	/**
	 * Sets the condition to draw titles according to what we are
	 * passed by the user. 
	 * @param drawTitles - True if we want to draw titles or false
	 * if otherwise.
	 */
	public void setDrawTitles(boolean drawTitles) {
		changed(1);
		this.drawTitles = drawTitles;
	}
	
	/**
	 * Are the boxes going to be bordered.
	 * @return True is we are going to have a border,
	 * false if otherwise. 
	 */
	public boolean isBoxBorder() {
		return boxBorder;
	}


	/**
	 * Sets whether we are going to have a border.
	 * @param boxBorder - True to show a border, false otherwise
	 */
	public void setBoxBorder(boolean boxBorder) {
		changed(2);
		this.boxBorder = boxBorder;
	}

	/**
	 * Returns whether the boxes are going to round or square.
	 * @return - True if we have rounded corners, false for square.
	 */
	public boolean isRoundedCorners() {
		changed(3);
		return roundedCorners;
	}

	/**
	 * Sets whether we are going to have rounded corners or square
	 * corners.
	 * @param roundedCorners - True for rounded corners, false for
	 * square corners.
	 */
	public void setRoundedCorners(boolean roundedCorners) {
		changed(4);
		this.roundedCorners = roundedCorners;
	}

	/**
	 * Do we allow intrusion of one generation into the blank space of another?
	 * @return the allowIntrusion
	 */
	public boolean isAllowIntrusion() {
		return allowIntrusion;
	}

	/**
	 * Sets whether we allow one generation to intrude into the blank space of another.
	 * @param allowIntrusion the boolean value to set
	 */
	public void setAllowIntrusion(boolean allowIntrusion) {
		changed(5);
		this.allowIntrusion = allowIntrusion;
	}
	
	/**
	 * Do we draw the end of line arrows?
	 * @return drawEndOfLineArrows
	 */
	public boolean isDrawEndOfLineArrows(){
		return drawEndOfLineArrows;
	}
	
	/**
	 * Sets whether or not we draw the end of line arrows.
	 * @param drawEndOfLineArrows the boolean value to set
	 */
	public void setDrawEndOfLineArrows(boolean drawArrows){
		changed(6);
		this.drawEndOfLineArrows = drawArrows;
	}
	
	public EndLineArrowStyle getArrowStyle(){
		return arrowStyle;
	}
	
	public void setArrowStyle(EndLineArrowStyle style){
		arrowStyle = style;
		this.setDrawTreeHasChanged(true);
	}

	public void resetDuplicateCount(){
		duplicateCount = 0;
	}
	
	public int assignDuplicate(){
		duplicateCount++;
		return duplicateCount;
	}

	
}
