package edu.byu.cs.roots.opg.chart.preset.templates;

import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;
import java.util.ArrayList;


import edu.byu.cs.roots.opg.chart.ChartOptions;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdMoveTo;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdSetFont;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdText;
import edu.byu.cs.roots.opg.chart.preset.templates.LineItem.LineItemType;
import edu.byu.cs.roots.opg.chart.preset.templates.StylingBox.TextDirection;
import edu.byu.cs.roots.opg.model.Individual;
import edu.byu.cs.roots.opg.model.OpgSession;
import edu.byu.cs.roots.opg.util.NameAbbreviator;

public class LineLayout implements Serializable{
	
	private static final long serialVersionUID = 1L;
	public ArrayList<LineItem> items;
	
	public LineLayout(){
		items = new ArrayList<LineItem>();
	}
	
	String getText() {
		return null;
	}
	
	public double getWidth(OpgSession session, double fontSize, ChartOptions options,Individual indi, String dupLabel, double boxWidth, int famCode){
		double width = 0;
		for(LineItem item : items){
			width += item.getWidth(session, fontSize, options, indi, dupLabel, boxWidth - width, famCode);
		}
		return width;
	}
	
	public double getHeight(){
		return 1;
	}
	public void draw(OpgSession session, ChartMargins chart, StylingBox style, ChartOptions options,
			double x, double y, double width, double height,Individual indi, 
			String dupLabel, boolean allowNameBold, int famCode, String chartIndex, TextDirection direction){
		
		Font regFont = session.getOpgOptions().getFont().getFont(Font.PLAIN, (float)style.fontSize);
		Font boldFont = session.getOpgOptions().getFont().getBoldFont((float)style.fontNameSize);
		Font nameNotBoldFont = session.getOpgOptions().getFont().getFont(Font.PLAIN, (float)style.fontNameSize);
		
		double xPos = x;
		double yPos = y;
		double widthSoFar = 0;
		Font font;
		
		for(int i = 0; i < items.size(); i++){ 
			LineItem item = items.get(i);
			if(item.isNameType() && allowNameBold)
				font = boldFont;
			else if(item.isNameType())
				font = nameNotBoldFont;
			else
				font = regFont;
			
			double postWidth = 0;
			for (int j = items.indexOf(item) + 1; j < items.size(); j++){
				postWidth += items.get(j).getWidth(session, style.fontSize, options, indi, dupLabel, width - widthSoFar, famCode);
			}
			String str = item.getText(font, width - widthSoFar - postWidth, indi,dupLabel, famCode);
//			if (item.type == LineItemType.ABBREVIATED_NAME)
//				str += chartIndex;
			//if(!str.equals(""))
			chart.addDrawCommand(new DrawCmdSetFont(font,Color.black)); //TODO: Optimize this so it only changes when it needs to.
			chart.addDrawCommand(new DrawCmdMoveTo(xPos,yPos));
			if((direction == TextDirection.NORMAL || direction == TextDirection.ONE_EIGHTY))
			{
				if (direction == TextDirection.NORMAL)
				{
					chart.addDrawCommand(new DrawCmdText(str));
					xPos += item.getWidth(session, style.fontSize, options, indi, dupLabel, width - widthSoFar, famCode);
					widthSoFar = xPos - x;
					if(item.type == LineItemType.CMD_PLACE_DATE_RIGHT_JUSTIFY)
					{
						xPos = (x + width) - font.getStringBounds("KKK (K555 - K555)", NameAbbreviator.frc).getWidth();
						if(dupLabel != "")
							xPos -= font.getStringBounds(dupLabel, NameAbbreviator.frc).getWidth();
					}
					if(item.type == LineItemType.CMD_DATE_RIGHT_JUSTIFY)
					{
						xPos = (x + width) - font.getStringBounds("(K555 - K555)", NameAbbreviator.frc).getWidth();
						if(dupLabel != "")
							xPos -= font.getStringBounds(dupLabel, NameAbbreviator.frc).getWidth();
					}
					if(item.type == LineItemType.CMD_DUPE_RIGHT_JUSTIFY)
					{
						xPos = (x + width) - font.getStringBounds(dupLabel, NameAbbreviator.frc).getWidth();
					}
				}
				else
				{
					chart.addDrawCommand(new DrawCmdText(str, 180));
					xPos -= item.getWidth(session, style.fontSize, options, indi, dupLabel, width - widthSoFar, famCode);
					widthSoFar = (x)- xPos;
					if(item.type == LineItemType.CMD_PLACE_DATE_RIGHT_JUSTIFY)
					{
						xPos = (x - width) + font.getStringBounds("KKK (K555 - K555)", NameAbbreviator.frc).getWidth();
						if(dupLabel != "")
							xPos -= font.getStringBounds(dupLabel, NameAbbreviator.frc).getWidth();	
					}
					if(item.type == LineItemType.CMD_DATE_RIGHT_JUSTIFY)
					{
						xPos = (x - width) + font.getStringBounds("(K555 - K555)", NameAbbreviator.frc).getWidth();
						if(dupLabel != "")
							xPos -= font.getStringBounds(dupLabel, NameAbbreviator.frc).getWidth();	
					}
				}

				
				
				
			}
			else
			{
				if(direction == TextDirection.NINETY){
					chart.addDrawCommand(new DrawCmdText(str,90));
					yPos -= item.getWidth(session, style.fontSize, options, indi, dupLabel, width - widthSoFar, famCode);
					widthSoFar = y - yPos;
					
					if(item.type == LineItemType.CMD_PLACE_DATE_RIGHT_JUSTIFY)
					{
						yPos = (y - (width)) + font.getStringBounds("KKK (K555 - K555)", NameAbbreviator.frc).getWidth();
						if(dupLabel != "")
							yPos -= font.getStringBounds(dupLabel, NameAbbreviator.frc).getWidth();	
					}
					if(item.type == LineItemType.CMD_DATE_RIGHT_JUSTIFY)
					{
						yPos = (y - (width)) + font.getStringBounds("(K555 - K555)", NameAbbreviator.frc).getWidth();
						if(dupLabel != "")
							yPos -= font.getStringBounds(dupLabel, NameAbbreviator.frc).getWidth();	
					}
				}
				else{
					chart.addDrawCommand(new DrawCmdText(str,270));
					yPos += item.getWidth(session, style.fontSize, options, indi, dupLabel, width - widthSoFar, famCode);
					widthSoFar = yPos - y;
					
					if(item.type == LineItemType.CMD_PLACE_DATE_RIGHT_JUSTIFY)
					{
						yPos = (y + (width)) - font.getStringBounds("KKK (K555 - K555)", NameAbbreviator.frc).getWidth();
						if(dupLabel != "")
							yPos += font.getStringBounds(dupLabel, NameAbbreviator.frc).getWidth();	
					}
					if(item.type == LineItemType.CMD_DATE_RIGHT_JUSTIFY)
					{
						yPos = (y + (width)) - font.getStringBounds("(K555 - K555)", NameAbbreviator.frc).getWidth();
						if(dupLabel != "")
							yPos += font.getStringBounds(dupLabel, NameAbbreviator.frc).getWidth();	
					}
				}
				
			}

			
						
			
			
			
		}
	}
}
