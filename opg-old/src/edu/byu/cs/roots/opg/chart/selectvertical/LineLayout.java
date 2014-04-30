package edu.byu.cs.roots.opg.chart.selectvertical;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import edu.byu.cs.roots.opg.chart.ChartDrawInfo;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdMoveTo;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdSetFont;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdText;
import edu.byu.cs.roots.opg.fonts.OpgFont;
import edu.byu.cs.roots.opg.model.Individual;
import edu.byu.cs.roots.opg.util.NameAbbreviator;

public class LineLayout {
	
	ArrayList<LineItem> items;
	
	public LineLayout(){
		items = new ArrayList<LineItem>();
	}
	
	String getText() {
		return null;
	}
	
	String getLine(double fontSize, OpgFont opgFont,double width,Individual indi, String dupLabel){
		Font regFont = opgFont.getFont(Font.PLAIN, (float)fontSize);
		Font boldFont = opgFont.getBoldFont((float)fontSize);
		
		
		double widthSoFar = 0;
		Font font;
		String str ="";
		for(LineItem item : items)
		{ 
			if(item.shouldBeBold())
				font = boldFont;
			else
				font = regFont;

			str += item.getText(font, width - widthSoFar, indi,dupLabel);
			widthSoFar = font.getStringBounds(str, NameAbbreviator.frc).getWidth();
			
		}
		return str;
	}
	
	TextLine getTextLine(double fontSize, OpgFont opgFont,double width,Individual indi, String dupLabel){
		Font regFont = opgFont.getFont(Font.PLAIN, (float)fontSize);
		Font boldFont = opgFont.getBoldFont((float)fontSize);
		
		
		double widthSoFar = 0;
		Font font;
		String str ="";
		TextLine tl = new TextLine();
		for(LineItem item : items)
		{ 
			if(item.shouldBeBold())
				font = boldFont;
			else
				font = regFont;
			
			str = item.getText(font, width - widthSoFar, indi,dupLabel);
			tl.AddItem(str, new Boolean(item.shouldBeBold()));
			widthSoFar = font.getStringBounds(str, NameAbbreviator.frc).getWidth();
			
		}
		return tl;
	}
	
	
	double getWidth(double fontSize, OpgFont opgFont,double width,Individual indi, String dupLabel){
		Font regFont = opgFont.getFont(Font.PLAIN, (float)fontSize);
		Font boldFont = opgFont.getBoldFont((float)fontSize);
		
		Font font;
		String str ="";
		double widthSoFar = 0;
		for(LineItem item : items)
		{ 
			if(item.shouldBeBold())
				font = boldFont;
			else
				font = regFont;

			str = item.getText(font, 1000, indi,dupLabel);
			widthSoFar = font.getStringBounds(str, NameAbbreviator.frc).getWidth();
		}
		return widthSoFar;
	}
	
	public double getWidth(double fontSize,OpgFont opgFont,Individual indi, String dupLabel, double boxWidth){
		double width = 0;
		for(LineItem item : items){
			width += item.getWidth(fontSize, opgFont, indi, dupLabel, boxWidth - width);
		}
		return width;
	}
	
	public double getHeight(){
		return 1;
	}
	
	
	public void draw(ChartDrawInfo chart, double fontSize, OpgFont opgfont,
			double x, double y, double width, double height, Individual indi, String dupLabel){
		
		Font regFont = opgfont.getFont(Font.PLAIN, (float)fontSize);
		Font boldFont = opgfont.getFont(Font.BOLD,(float)fontSize);
		
		
		double xPos = x;
		double widthSoFar = 0;
		Font font;
		
		for(LineItem item : items){ 
			if(item.shouldBeBold())
				font = boldFont;
			else
				font = regFont;
			//font = regFont;
			
			String str = item.getText(font, width - widthSoFar, indi,dupLabel);

			
//			if(item.type == LineItemType.ABBREVIATED_NAME)
//				font = font.deriveFont((float)NameAbbreviator.getSize());
//				font = font.deriveFont((float)Math.floor((float)NameAbbreviator.getSize()));
			
			chart.addDrawCommand(new DrawCmdSetFont(font,Color.black));
			chart.addDrawCommand(new DrawCmdMoveTo(xPos,y));
			chart.addDrawCommand(new DrawCmdText(str));
			//chart.addDrawCommand(new DrawCmdMoveTo(xPos,y));
			
			//Debugging code: draws line to show calculated width of string.
			//if(item.type == LineItemType.ABBREVIATED_NAME)
			//	chart.addDrawCommand(new DrawCmdRelLineTo(font.getStringBounds(str, NameAbbreviator.frc).getWidth(),0,1,Color.blue));
			
			//xPos += item.getWidth(fontSize, options, indi, dupLabel, width - widthSoFar);
			//xPos += font.getStringBounds(str, NameAbbreviator.frc).getWidth();
			xPos += font.getStringBounds(str, NameAbbreviator.frc).getWidth();
			widthSoFar = xPos - x;
		}

	}
	
	public void draw(ChartDrawInfo chart, double fontSize, OpgFont opgfont,
			double x, double y, double width, Individual indi, String dupLabel){
		
		Font regFont = opgfont.getFont(Font.PLAIN, (float)fontSize);
		Font boldFont = opgfont.getFont(Font.BOLD,(float)fontSize);
		
		
		double xPos = x;
		double widthSoFar = 0;
		Font font;
		
		for(LineItem item : items){ 
			if(item.shouldBeBold())
				font = boldFont;
			else
				font = regFont;
			//font = regFont;
			
			String str = item.getText(font, width - widthSoFar, indi,dupLabel);

			
//			if(item.type == LineItemType.ABBREVIATED_NAME)
//				font = font.deriveFont((float)NameAbbreviator.getSize());
//				font = font.deriveFont((float)Math.floor((float)NameAbbreviator.getSize()));
			
			chart.addDrawCommand(new DrawCmdSetFont(font,Color.black));
			chart.addDrawCommand(new DrawCmdMoveTo(xPos,y));
			chart.addDrawCommand(new DrawCmdText(str));
			//System.out.println(str+" "+xPos+" "+y);
			//chart.addDrawCommand(new DrawCmdMoveTo(xPos,y));
			
			//Debugging code: draws line to show calculated width of string.
			//if(item.type == LineItemType.ABBREVIATED_NAME)
			//	chart.addDrawCommand(new DrawCmdRelLineTo(font.getStringBounds(str, NameAbbreviator.frc).getWidth(),0,1,Color.blue));
			
			xPos += font.getStringBounds(str, NameAbbreviator.frc).getWidth();
			widthSoFar = xPos - x;
		}

	}
	
}