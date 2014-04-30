package edu.byu.cs.roots.opg.chart.presetvertical;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.util.ArrayList;

import edu.byu.cs.roots.opg.chart.cmds.DrawCmdFillRect;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdMoveTo;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdRelLineTo;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdRoundRect;
import edu.byu.cs.roots.opg.model.Individual;

/**
 * Represents a box in an ancestry chart. 
 * Contains spacing information and a reference to its formating.
 *
 */
public class AncesBox extends Box
{
	//tree structure variables
	protected AncesBox father;
	protected AncesBox mother;
	double hPos,vPos;
	static double scaler;
	double subTreeHeight; //the minimum height for the current configuration of the tree
	boolean expand; //false if box is duplicated, true otherwise
	double fatherVOffset;
	double motherVOffset;
	
	Rectangle2D.Double boxInfo = new Rectangle2D.Double();
	
	static ArrayList<Double> genBoxHeights;  //box heights of generations
	
	public AncesBox(Individual indi)
	{
		super(indi);
		father = null;
		mother = null;
		expand = true;
	}
	
	public double setHeight(double newHeight)
	{
		//set the scaler so that chart will be the right height
		double curHeight = upperSubTreeOffset - lowerSubTreeOffset;
		//curHeight = Math.
		if (curHeight < subTreeHeight)
			throw new IllegalArgumentException ("Cannot scale chart smaller than minimum");
		scaler = newHeight/subTreeHeight;
		return scaler;
	}
	
	//-----------------------------------------------------------------------
	
	void setScaler(double newScaler)
	{
		if (newScaler < 0.99)
			throw new IllegalArgumentException ("Cannot scale chart smaller than minimum");
		else
			scaler = newScaler;
	}
	
	protected void drawSubTree(ChartMargins chart, VerticalChartOptions options, double x, double y)
	{
		
		if (gen.getGenNum() >= 0)
			drawBox(chart, chart.xOffset(x), chart.yOffset(y));
		
		
		if (options.getAncesGens() > gen.getGenNum())
		{
			if (father != null)
			{
				AncesBox f = (AncesBox) father;
				drawParentConnector(chart,x,y,fatherVOffset);
				f.drawSubTree(chart, options, x + gen.getWidth(), y + fatherVOffset);
			}
			if (mother != null)
			{
				AncesBox m = (AncesBox) mother;
				drawParentConnector(chart,x,y,motherVOffset);
				m.drawSubTree(chart, options, x + gen.getWidth(), y + motherVOffset);
			}
		}
		
	}
	
	protected void drawBox (ChartMargins chart, double x, double y)
	{
		
		String dupLabel = (dupIndex != 0)? (" <" + dupIndex + ">") : "";
		VerticalChartOptions options = chart.getOptions();
		double height = boxFormat.getHeight();
		double width = boxFormat.getWidth();
		
		//Draw a boarder, if they select they want one, and whether it has rounded corners or not.
		double lineWidth = (options.isBoxBorder())? boxFormat.getLineWidth() : 0;
		if (options.isRoundedCorners())
			chart.addDrawCommand(new DrawCmdRoundRect(x, y-height/2.0, width ,height, lineWidth, boxFormat.getRoundnessOfCorners(), Color.BLACK, options.getAncesScheme().getColor(indi.id), boxInfo));
		else
			chart.addDrawCommand(new DrawCmdFillRect(x, y-height/2.0, width ,height, lineWidth, Color.BLACK, options.getAncesScheme().getColor(indi.id), boxInfo));
		  
		// draw content of the box
		BoxDrawer drawer = new BoxDrawer(chart.getChart(), boxFormat, indi, x, y, dupLabel);		
		drawer.drawBox();
				
		//These draw the boxFormat number assigned to the box being drawn on the chart 
		//(The number is assigned in the FormatSelector)
		//drawBox.drawTextBox(chart, x, y, dupLabel);
		//chart.addDrawCommand(new DrawCmdMoveTo(x-20,y));
		//chart.addDrawCommand(new DrawCmdText(boxFormat.boxIndex+""));
	}
	
	/**
	 * Draws the connector line to a boxes parents.
	 * @param chart chart to draw the lines on
	 * @param x x coordinate of left-middle of the box
	 * @param y y coordinate of left-middle of the box
	 * @param offset y offset to the parent (father > 0 and mother < 0).
	 */
	protected void drawParentConnector(ChartMargins chart, double x, double y, double offset) {
		if(genGap() > 0) {
			chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x + getWidth()), chart.yOffset(y)));
			chart.addDrawCommand(new DrawCmdRelLineTo(connectorLength(),0.0,boxFormat.getLineWidth(), Color.BLACK));
			chart.addDrawCommand(new DrawCmdRelLineTo(0.0,offset,boxFormat.getLineWidth(), Color.BLACK)); 
			chart.addDrawCommand(new DrawCmdRelLineTo(connectorLength(),0,boxFormat.getLineWidth(), Color.BLACK));
		}
		//intrusion
		else {
			double boxOffset = 0;
			//adjust offsets to make line come from top/bottom of the box
			if(offset < 0) { //bottom (mother)
				offset += getHeight()/2;
				boxOffset = -getHeight()/2;
			}
			//top (father)
			else { 
				offset -= getHeight()/2;
				boxOffset = getHeight()/2;
			}
			chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x + gen.getWidth() - connectorLength()),
												    chart.yOffset(y) + boxOffset));
			chart.addDrawCommand(new DrawCmdRelLineTo(0.0,offset,boxFormat.getLineWidth(), Color.BLACK));
			chart.addDrawCommand(new DrawCmdRelLineTo(connectorLength(),0,boxFormat.getLineWidth(), Color.BLACK));
		}
	}
	
	/**
	 * Length of the line that just out of the box 
	 */
	private double connectorLength() {
		return (getWidth()*.1)/2;
	}
	
	/**
	 * Gap between generations. Value is negative if there is intrusion.
	 */
	protected double genGap() {
		return (gen.getWidth() - getWidth());
	}
	
	public void setFather(AncesBox father) {
		this.father = father;
	}

	public Box getFather() {
		return father;
	}

	public void setMother(AncesBox mother) {
		this.mother = mother;
	}
	
	public Box getMother() {
		return mother;
	}

	public String toString() {
		return "AncesBox: "+ indi.toString();
	}
	
	public void setParentOffsets(double fatherOffset, double motherOffset) {
		fatherVOffset = fatherOffset;
		motherVOffset = motherOffset;
		updateSubTreeOffsets();
	}
	
	private void updateSubTreeOffsets() {
		double usth = 0;
		double lsth = 0;
		if(father != null){
			usth = fatherVOffset + father.upperSubTreeOffset;
			lsth = fatherVOffset +  father.lowerSubTreeOffset;
		}
		if(mother != null) {
			usth = Math.max(usth, motherVOffset + mother.upperSubTreeOffset);
			lsth = Math.min(lsth, motherVOffset + mother.lowerSubTreeOffset);
		}
			
		upperSubTreeOffset = Math.max(usth, upperSubTreeOffset);
		lowerSubTreeOffset = Math.min(lsth, lowerSubTreeOffset);
	}
}