/*
 * This file is a brainstorming file for a new system of box layout methods.
 * It is not currently used in any production code.
 */

package edu.byu.cs.roots.opg.chart.selectvertical;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import edu.byu.cs.roots.opg.chart.ChartDrawInfo;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdFillRect;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdRoundRect;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdSetFont;
import edu.byu.cs.roots.opg.fonts.OpgFont;
import edu.byu.cs.roots.opg.model.Individual;
import edu.byu.cs.roots.opg.model.OpgSession;
import edu.byu.cs.roots.opg.util.NameAbbreviator;

public class BoxLayout {
	
	ArrayList<LineLayout> lines;
	double totalHeight = 0;
	
	
	
	public BoxLayout(){
		lines = new ArrayList<LineLayout>();
	}
	
	double getTextHeight(double height, OpgFont opgFont, double fontSize)
	{
		int maxlines = (int) (height/(fontSize*1.1));
		if(maxlines == 0) maxlines++;
		return maxlines *opgFont.getFont(Font.PLAIN, (float)fontSize).getStringBounds("TJtj", NameAbbreviator.frc).getHeight();
	}
	
	//override this if you need to check that data exists.
	public boolean canFit(Individual indi, double width, double height, OpgFont opgFont, double fontSize, String dupLabel ){
		int horizTextGap = 10;
		for (LineLayout line : lines){
			if(line.getWidth(fontSize, opgFont, indi, dupLabel, width - (horizTextGap * 2)) > width - (horizTextGap * 2))
				return false;
		}
		return true;
	}
	
	public boolean canFit(Individual indi, BoxFormat bf, String dupLabel ){
		
		double fontSize = bf.nameFontSize;
		OpgFont opgFont = BoxFormat.font;
		double width = bf.getWidth();
		double horizTextGap = bf.getHorizontalOffset();
		for (LineLayout line : lines){
			if(line.getWidth(fontSize, opgFont, indi, dupLabel, width - (horizTextGap * 2)) > width - (horizTextGap * 2))
				return false;
		}
		return true;
	}
	
	public ArrayList<String> getContent(BoxFormat fbox, Individual indi, String dupLabel)
	{
		double fontSize = fbox.nameFontSize;
		OpgFont opgFont = BoxFormat.font;
		double width = fbox.width;
		//double height = fbox.height;
		
		int horizTextGap = 3;
		ArrayList<String> content = new ArrayList<String>();
		for(LineLayout l : lines)
			content.add( l.getLine(fontSize, opgFont, width - (horizTextGap* 2), indi, dupLabel));
		return content;
			
	}
	public ArrayList<TextLine> getTextLines(BoxFormat fbox, Individual indi, String dupLabel)
	{
		double fontSize = fbox.nameFontSize;
		OpgFont opgFont = BoxFormat.font;
		double width = fbox.width;
		//double height = fbox.height;
		
		int horizTextGap = 3;
		ArrayList<TextLine> textLines = new ArrayList<TextLine>();

		for(LineLayout l : lines)
			textLines.add( l.getTextLine(fontSize, opgFont, width - (horizTextGap* 2), indi, dupLabel));
		return textLines;
			
	}
	
	public ArrayList<LineLayout> getLineLayouts()
	{
		return lines;
	}
	
	public double getMaxWidth(BoxFormat fbox, Individual indi, String dupLabel)
	{
		double fontSize = fbox.nameFontSize;
		OpgFont opgFont = BoxFormat.font;
		double width = fbox.width;
		//double height = fbox.height;
		
		int horizTextGap = 3;
		double max = 0;
		for(LineLayout l : lines)
		{
			max = Math.max(max,l.getWidth(fontSize, opgFont, width - (horizTextGap* 2), indi, dupLabel));
		}
		return max;
			
	}
	
	public void draw(OpgSession session, ChartDrawInfo chart, double fontSize, VerticalChartOptions options,
			double x, double y, double width, double hgt,Individual indi, String dupLabel){
		//Draw your box;
		totalHeight = lines.size() * (fontSize * 1.2) + (fontSize * 0.3);
		double lineWidth = (options.isBoxBorder())? 1 : 0;
		
		
		if (options.isRoundedCorners())
		{
			chart.addDrawCommand(new DrawCmdRoundRect(x, y-totalHeight/2.0, width,totalHeight, lineWidth, 5, Color.BLACK, options.getAncesScheme().getColor(indi.id), null));
			//chart.addDrawCommand(new DrawCmdRoundRect(x, y-height/2.0, width,height, lineWidth, 5, Color.BLACK, c));
		}
		else
			chart.addDrawCommand(new DrawCmdFillRect(x, y-totalHeight/2.0, width,totalHeight, lineWidth, Color.BLACK, options.getAncesScheme().getColor(indi.id), null));
			//chart.addDrawCommand(new DrawCmdFillRect(x, y-height/2.0, width,height, lineWidth, Color.BLACK, c));
		
		//Draw each line
		Font font = session.getOpgOptions().getFont().getFont(Font.PLAIN,(float)fontSize);
		chart.addDrawCommand(new DrawCmdSetFont(font,Color.black)); 
		double linepos = y + totalHeight/2.0 - font.getSize2D();
		int horizTextGap = 10;
		for(LineLayout line : lines){
		//	String str = line.getLine(fontSize, options.getFont(), width - (horizTextGap* 2), indi, dupLabel);
			line.draw(chart,fontSize, session.getOpgOptions().getFont(),x + horizTextGap, linepos, width - (horizTextGap* 2), totalHeight, indi, dupLabel);
			linepos -= font.getSize2D() * 1.1;
		}
	}
}
