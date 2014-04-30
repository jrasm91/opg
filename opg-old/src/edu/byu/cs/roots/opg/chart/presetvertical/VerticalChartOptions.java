package edu.byu.cs.roots.opg.chart.presetvertical;

import edu.byu.cs.roots.opg.chart.ChartOptions;
import edu.byu.cs.roots.opg.chart.ChartType;
import edu.byu.cs.roots.opg.color.ColorScheme;
import edu.byu.cs.roots.opg.model.Individual;
import edu.byu.cs.roots.opg.model.OpgSession;
import edu.byu.cs.roots.opg.model.PaperWidth;

/**
 * This class extends the ChartOptions class and adds the options
 * of getting whether we have a boarder around the boxes, if we
 * need to draw the titles, and if we should have rounded corners.
 * @author derek
 * @see ChartOptions
 * @version 1.0.0
 */
public class VerticalChartOptions extends ChartOptions
{
	/** Serial ID for when we want to save this class as a bit stream. */
	private static final long serialVersionUID = -6787151923234766638L;
	/** Should the boxes have rounded corners */
	protected boolean roundedCorners;
	/** Should the info box have a border */
	protected boolean boxBorder;
	/** Should we display titles on this chart */
	protected boolean drawTitles;
	/** Should we allow intrusion of lower generations into higher generations **/
	protected boolean allowIntrusion;
	//** Should all names be bolded */
	protected boolean boldNames;
	
	public static enum Change {CREATE, MODIFY, REDRAW,NONE};
	
	private Change stateChange;
	
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
		setChange(Change.REDRAW);
		changed(1);
		this.drawTitles = drawTitles;
	}

	/**
	 * Creates the Vertical Chart's Options by passing the options
	 * to the parent class and setting the chart type to 
	 * ChartType.VERTICAL
	 * @param options - The current options of this chart.
	 * @see ChartOptions
	 * @see ChartType
	 */
	public VerticalChartOptions(ChartOptions options)
	{
		super(options);
		stateChange = Change.CREATE;
		setPaperWidth(PaperWidth.findClosestFit(options.getPaperWidth().width));
		if(firstLoad){
			setPaperWidth(PaperWidth.findClosestFit(36*72));
			setPaperLength(48*72);
			firstLoad = false;
		}
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
		setChange(Change.REDRAW);
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
		setChange(Change.REDRAW);
		changed(4);
		this.roundedCorners = roundedCorners;
	}

	/**
	 * Do we allow intrusion of one generation into the blank space of another?
	 * @return the allowIntrusion
	 */
	public boolean isAllowIntrusion() {
		if(isIncludeSpouses() && getDescGens() > 0)
			return false;
		return allowIntrusion;
	}

	/**
	 * Sets whether we allow one generation to intrude into the blank space of another.
	 * @param allowIntrusion the boolean value to set
	 */
	public void setAllowIntrusion(boolean allowIntrusion) {
		setChange(Change.REDRAW);
		changed(5);
		this.allowIntrusion = allowIntrusion;
	}
	
	public void setAncesGens(int maxAncesGens, OpgSession session) {
		setChange(Change.MODIFY);
		super.setAncesGens(maxAncesGens, session);
	}

	public void setDescGens(int maxDescGens, OpgSession session) {
		setChange(Change.MODIFY);
		super.setDescGens(maxDescGens, session);
	}
	
	public void setLandscape(boolean landscape) {
		setChange(Change.MODIFY);
		super.setLandscape(landscape);
	}

	public void setPaperWidth(PaperWidth paperWidth) {
		setChange(Change.MODIFY);
		super.setPaperWidth(paperWidth);
	}
	
	public void setPaperLength(double paperLength) {
		setChange(Change.MODIFY);
		super.setPaperLength(paperLength);
	}
	
	public void setIncludeSpouses(boolean includeSpouses) {
		setChange(Change.CREATE);
		super.setIncludeSpouses(includeSpouses);
	}
	
	public void setRoot(Individual root, OpgSession session) {
		setChange(Change.CREATE);
		super.setRoot(root, session);
	}
		
	public void setAncesScheme(ColorScheme colorscheme) {
		setChange(Change.REDRAW);
		super.setAncesScheme(colorscheme);
	}
	
	public void setDescScheme(ColorScheme descScheme) {
		setChange(Change.REDRAW);
		super.setDescScheme(descScheme);
	}

	public void setChange(Change change) {
		if(stateChange == Change.CREATE) {
			if(change != Change.MODIFY && change != Change.REDRAW)
				stateChange = change;
		}
		else if(stateChange == Change.MODIFY) {
			if(change != Change.REDRAW)
				stateChange = change;
		}
		else
			stateChange = change;
	}

	public Change getChange() {
		return stateChange;
	}
	
	
	




	
}
