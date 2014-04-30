package edu.byu.cs.roots.opg.chart.selectvertical;

import java.awt.Font;
import java.util.ArrayList;

import edu.byu.cs.roots.opg.fonts.OpgFont;


public class BoxFormat {
	
	static public ArrayList<BoxFormat> boxFormats = makeFormats();
	
	//dimensions
	protected double height; //max box height
	protected double width; //box width
	

	//font attributes
	protected static OpgFont font = OpgFont.GENTIUM;
	protected float nameFontSize;  //note: currently only using this font
	protected float bodyFontSize;
	
	
	//vertical and horizontal padding
	public double verticalOffset;    //offset of the text from the top of the box
	protected double verticalPadding;   //added to the overall height of the box
	private double horizontalOffset;  //offset of the text from the left side of box
	protected double verticalSpacing;   //spacing between boxes
	
	public int boxIndex; //index of boxformat in the array -- not currently used


	//These will 
	protected static double genWidth;     //universal generation width
	protected static boolean useGenWidth; //true if using universal generation width
	
	//private double generationWidth;		//the width in between the current genderation's left side
										//and the next generation's left side. **IF NEGATIVE, INTRUSION!!**

	//text attributes
	private int numOfLines; //max number of lines to be displayed

	public static ArrayList<BoxFormat>FORMATS = makeFormats(); //Formats used in spacing algorithm (biggest to smallest)
	
	
	

	public BoxFormat(int bi, double height, double width, float nameFontSize, float bodyFontSize, double vp, double vo, double hp, int vs, int numOfLines)
	{
		this.boxIndex = bi;
		this.height = height;
		this.width = width;
		this.nameFontSize = nameFontSize;
		this.bodyFontSize = bodyFontSize;
		this.verticalPadding = vp;
		this.verticalOffset = vo;
		this.setHorizontalOffset(hp);
		this.verticalSpacing = vs; //between boxes
		this.setNumOfLines(numOfLines);

	}
	//144 126 108 90 72 54 36 18 Heights
	//216 width
	public static ArrayList<BoxFormat> makeFormats()
	{
		ArrayList<BoxFormat> temp = new ArrayList<BoxFormat>();
		
		                    //index    mheight, width, nfontsize, bfontsize, vpad  voffset  hpad,  vspace, linenums
		temp.add(new BoxFormat( 0   ,   144   ,   216   ,   22   ,   28   ,   12   ,  1   ,   10   ,   9   ,   5   ));
		temp.add(new BoxFormat( 1   ,   126   ,   216   ,   20   ,   24   ,   10   ,  1   ,   10   ,   8   ,   5   ));
		temp.add(new BoxFormat( 2   ,   108   ,   216   ,   18   ,   22   ,   8   ,   1   ,   10   ,   7   ,   5   ));
		temp.add(new BoxFormat( 3   ,    90   ,   216   ,   16   ,   20   ,   8   ,   1   ,   10   ,   6   ,   4   ));
		temp.add(new BoxFormat( 4   ,    72   ,   216   ,   14   ,   20   ,   8   ,   1   ,    8   ,   5   ,   4   ));
		temp.add(new BoxFormat( 5   ,    54   ,   216   ,   14   ,   14   ,   7   ,   1   ,    8   ,   4   ,   3   ));
		temp.add(new BoxFormat( 6   ,    36   ,   216   ,   12   ,   10   ,   6   ,   1   ,    8   ,   3   ,   2   ));
		temp.add(new BoxFormat( 7   ,    18   ,   216   ,   11   ,    8   ,   4   ,   0   ,    8   ,   2   ,   1   ));
		return temp;
	}
	
	public static BoxFormat getDefault() {
		return FORMATS.get(7);
	}
	
	public int getBoxIndex()
	{
		return boxIndex;
	}
	
	public static void setUseGenWidth(boolean use)
	{
		useGenWidth = use;
	}
	
	public static void setGenWidth(double width)
	{
		genWidth = width;
	}
	
	public static double getGenWidth()
	{
		return genWidth;
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
		return font.getFont(Font.PLAIN, (float)nameFontSize);
	}
	

	public double getHeight() {
		return height;
	}
	
	public double getMinHeight()
	{
		return font.font.getSize2D() + verticalPadding;
	}
	
	public void setHeight(double height) {
		this.height = height;
	}
	
	public double getNameFontSize() {
		return nameFontSize;
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
	

	public double getVetricalPadding() {
		return verticalPadding;
	}
	public void setVeticalPadding(double vp) {
		this.verticalPadding = vp;
	}
	
	public void setTopPadding(double hp) {
		this.setHorizontalOffset(hp);
	}
	public double getWidth() {
		return (useGenWidth)? genWidth : width;
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
	public void setHorizontalOffset(double horizontalOffset) {
		this.horizontalOffset = horizontalOffset;
	}
	public double getHorizontalOffset() {
		return horizontalOffset;
	}
	
	public double getLineHeight()
	{
		Font f = getNameFont();
		return f.getSize2D()*1.05;
		//return font.getStringBounds("xhgCAPSjkty",NameAbbreviator.frc).getHeight();		
	}
}
