package edu.byu.cs.roots.opg.chart.selectvertical;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import edu.byu.cs.roots.opg.chart.ChartOptions;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdMoveTo;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdSetFont;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdText;
import edu.byu.cs.roots.opg.fonts.OpgFont;
import edu.byu.cs.roots.opg.model.Gender;
import edu.byu.cs.roots.opg.model.Individual;
import edu.byu.cs.roots.opg.model.OpgSession;
/** 
 * 
 * BoxDrawer is used to draws the content within a individual box
 * It uses a given BoxFormat in order to format the box appropriately.
 *
 */
/**
 * @author One Page Genealogy
 *
 */
public class BoxDrawer 
{
	ArrayList<LineLayout> lines;
	double topPadding;
	double leftPadding;
	OpgFont opgFont;
	BoxFormat boxFormat;
	BoxLayout bl;
	Individual indi;
	float fontSize;
	double height;
	double width;
	boolean intrude;
	int numOfLines;
	
	
	public BoxDrawer()
	{
		
	}
//	public TextBox(OpgFont opgFont, float fontSize, double height, double width)
//	{
//		update(opgFont, fontSize, height, width);
//	}
	
	public BoxDrawer(BoxFormat bf, Individual indi)
	{
		this.indi = indi;
		update(bf);
	}
	
	public double getHeight()
	{
		return height;
	}
	
	public double getWidth()
	{
		return /*(intrude)? boxFormat.getWidth()*1.5 :*/ boxFormat.getWidth();
	}
	
	public double calculateHeight()
	{
		double sum = getLineHeight() * numOfLines;
		return sum  + boxFormat.verticalPadding; 
		
	}
	
	public double getLineHeight()
	{
		
		Font font = opgFont.getFont(Font.BOLD, fontSize);
		return font.getSize2D()*1.05;
		//return font.getStringBounds("xhgCAPSjkty",NameAbbreviator.frc).getHeight();		
	}
	
	public int getBoxFormatIndex()
	{
		return boxFormat.boxIndex;
	}
	
	/**
	 * Updates the current BoxDrawer to have to uses BoxFormat bf
	 */
	public void update(BoxFormat bf)
	{
		boxFormat = bf;
		this.opgFont = BoxFormat.font;
		this.fontSize = (float)bf.nameFontSize;
		numOfLines = Math.min(bf.getNumOfLines(), linesAvailable());
		height = calculateHeight();
		intrude = false;
		indi.isDisplayed = false;
	}
	
	/**
	 * Sets if the box should intrude on the next generation
	 * @param intrude
	 */
	public void setIntrude(boolean intrude)
	{
		this.intrude = intrude;
	}
	
	/**
	 * 
	 * Determines the max amount lines can be drawn with the existing information.
	 */
	public int linesAvailable()
	{
		int num = 1;
		
		if(indi.hasBirthDate())
			num += (indi.hasBirthPlace())? 2 : 1;
		if(indi.hasDeathDate())
			num += (indi.hasDeathPlace())? 2 : 1;
		if(num > 1)
		if(indi.gender == Gender.MALE && indi.fams.size() != 0 && indi.fams.get(0).marriage != null)
			num += (indi.hasMarriagePlace())? 2 : 1;
		
		return Math.min(num, 5);
	}
	
	public void blDraw(OpgSession session, ChartMargins chart, double x, double y, ChartOptions options, String dupLabel)
	{
		bl.draw(session, chart.getChart(), fontSize, (VerticalChartOptions)options, x, y, getWidth(), height, indi, dupLabel);
	}
	
	public void drawTextBox(ChartMargins chart, double x, double y, String dupLabel)
	{
		BoxLayoutManager bm = new BoxLayoutManager();
		lines = new ArrayList<LineLayout>();
			
		lines = bm.getLineLayouts(boxFormat, indi, dupLabel);
		//draw dynamic text
		//Draw each line
		Font font = opgFont.getFont(Font.PLAIN,(float)fontSize); 
		double linepos = y + height/2.0 - font.getSize2D() - boxFormat.verticalOffset;
		double hPad = boxFormat.getHorizontalOffset();
		for(LineLayout l : lines){
			l.draw(chart.getChart(), fontSize, opgFont, x+hPad, linepos, getWidth() - 2*hPad, font.getSize2D() , indi, dupLabel);
			linepos -= getLineHeight();
		}
		indi.isDisplayed = true;
	}
	
	public void draw(ChartMargins chart, double x, double y, String dupLabel)
	{
		//Draw each line
		
		Font font = opgFont.getFont(Font.PLAIN,(float)fontSize);    //get font
		chart.addDrawCommand(new DrawCmdSetFont(font,Color.black)); //set color
		double linepos = y + height/2.0 - font.getSize2D();
		double horizTextGap = boxFormat.getHorizontalOffset();
		for(LineLayout line : lines){
			String str = line.getLine(fontSize, opgFont, getWidth() - (horizTextGap* 2), indi, dupLabel);
			chart.addDrawCommand(new DrawCmdMoveTo(x + horizTextGap,linepos));
			chart.addDrawCommand(new DrawCmdText(str));
			linepos -= font.getSize2D() * 1.1;
		}
	}


}
