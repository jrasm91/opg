package edu.byu.cs.roots.opg.chart.presetvertical;

import java.awt.Font;

import edu.byu.cs.roots.opg.fonts.OpgFont;

/**
 * Represents the format of a chart box
 */
public class BoxFormat {
	
	//dimensions
	protected double height; //max box height
	protected double width; //box width
	

	//font attributes
	protected static OpgFont font = OpgFont.GENTIUM;
	protected float nameFontSize;  //note: currently only using this font
	protected float bodyFontSize;
	
	protected double verticalSpacing;   //spacing between boxes
	
	public int boxIndex; //index of boxformat in the array -- not currently used

	//text attributes
	private int numOfLines; //max number of lines to be displayed

	private double lineWidth; //the width of the line around the boxes, and connecting boxes
						   //should get smaller as the fontsize gets smaller
	
	private double roundedCorners;  //this will contain how round the corners should be
	
	
	public BoxFormat(int bi, double height, double width, float nameFontSize, float bodyFontSize, int vs, int numOfLines, double lineWidth, double roundness)
	{
		this.boxIndex = bi;
		this.height = height;
		this.width = width;
		this.nameFontSize = nameFontSize;
		this.bodyFontSize = bodyFontSize;
		this.verticalSpacing = vs; //between boxes
		this.setNumOfLines(numOfLines);
		this.lineWidth = lineWidth;
		this.roundedCorners = roundness;
	}
	//144 126 108 90 72 54 36 18 Heights
	//216 width
	
	public double getRoundnessOfCorners() {
		return roundedCorners;
	}
	
	public double getLineWidth() {
		return lineWidth;
	}
	
	public int getBoxIndex()
	{
		return boxIndex;
	}
	
	public static void setFont(OpgFont opgFont)
	{
		font = opgFont;
	}
	
	public static OpgFont getOpgFont()
	{
		return font;
	}
	
	public Font getFont()
	{
		return font.getFont(Font.PLAIN, (float)bodyFontSize);
	}
	

	public double getHeight() {
		return height;
	}
	
	public double getMinHeight()
	{
		return font.font.getSize2D();
	}
	
	public void setHeight(double height) {
		this.height = height;
	}
	
	public double getNameFontSize() {
		return nameFontSize;
	}
	
	public double getBodyFontSize() {
		return bodyFontSize;
	}
	
	public Font getNameFont() {
		return font.getFont(Font.BOLD,nameFontSize);
	}
	
	public Font getBodyFont() {
		return font.getFont(Font.BOLD,bodyFontSize);
	}
	
	public void setNameFontSize(float nameFontSize) {
		this.nameFontSize = nameFontSize;
	}

	public double getWidth() {
		return width;
	}
	
	public void setWidth(double width) {
		this.width = width;
	}
	public double getVerticalSpacing() {
		return verticalSpacing;
	}
	public void setVerticalSpacing(double verticalSpacing) {
		this.verticalSpacing = verticalSpacing;
	}
	public void setNumOfLines(int numOfLines) {
		this.numOfLines = numOfLines;
	}
	public int getNumOfLines() {
		return numOfLines;
	}

	public double getNameLineHeight() {
		Font f = getNameFont();
		return f.getSize2D()*1.2;
	}
	
	public double getBodyLineHeight()
	{
		Font f = getBodyFont();
		return f.getSize2D()*1.05;
		//return font.getStringBounds("xhgCAPSjkty",NameAbbreviator.frc).getHeight();		
	}
}
