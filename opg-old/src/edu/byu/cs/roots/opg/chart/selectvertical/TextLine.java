package edu.byu.cs.roots.opg.chart.selectvertical;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;


import edu.byu.cs.roots.opg.chart.cmds.DrawCmdMoveTo;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdSetFont;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdText;
import edu.byu.cs.roots.opg.fonts.OpgFont;
import edu.byu.cs.roots.opg.util.NameAbbreviator;

public class TextLine
{
	/**
	 * 
	 */
	private ArrayList<String> lineItems;
	private ArrayList<Boolean> boldItems;
	
	public TextLine()
	{
		lineItems = new ArrayList<String>();
		boldItems = new ArrayList<Boolean>();
		
	}
	
	public void AddItem(String text, Boolean boldness)
	{
		lineItems.add(text);
		boldItems.add(boldness);
	}
	
	public double getHeight(OpgFont opgFont, float fontsize)
	{
		Font font = opgFont.getFont(Font.PLAIN, fontsize);
		return font.getStringBounds("xhgACPSjkty",NameAbbreviator.frc).getHeight();
	}
	
	public void drawLine(ChartMargins chart, double xPos, double yPos, OpgFont opgFont, float fontSize)
	{
		double curPos = xPos;
		Font font = opgFont.getFont(Font.PLAIN, fontSize);
		chart.addDrawCommand(new DrawCmdSetFont(font,Color.black));
		for(int i= 0; i < lineItems.size(); i++)
		{
			if(boldItems.get(i))
				font = opgFont.getFont(Font.PLAIN, fontSize);
			else
				font = opgFont.getFont(Font.BOLD, fontSize);
				
			chart.addDrawCommand(new DrawCmdMoveTo(curPos,yPos));
			chart.addDrawCommand(new DrawCmdText(lineItems.get(i)));
			//chart.addDrawCommand(new DrawCmdMoveTo(curPos,yPos));
			curPos += font.getStringBounds(lineItems.get(i),NameAbbreviator.frc).getWidth();
			
		}
	}

}

