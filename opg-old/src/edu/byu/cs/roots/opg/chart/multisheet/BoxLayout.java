/*
 * This file is a brainstorming file for a new system of box layout methods.
 * It is not currently used in any production code.
 * 
 * Actually, it is.  Currently used, that is.
 */

package edu.byu.cs.roots.opg.chart.multisheet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.roots.opg.chart.cmds.DrawCmdFillRect;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdMoveTo;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdPageLink;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdRoundRect;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdText;
import edu.byu.cs.roots.opg.chart.preset.templates.AncesBoxParent;
import edu.byu.cs.roots.opg.chart.preset.templates.BoxLayoutParent;
import edu.byu.cs.roots.opg.chart.preset.templates.ChartMargins;
import edu.byu.cs.roots.opg.chart.preset.templates.LineLayout;
import edu.byu.cs.roots.opg.chart.preset.templates.PresetChartOptions;
import edu.byu.cs.roots.opg.chart.preset.templates.StylingBox;
import edu.byu.cs.roots.opg.chart.preset.templates.StylingBox.TextDirection;
import edu.byu.cs.roots.opg.model.Gender;
import edu.byu.cs.roots.opg.model.Individual;
import edu.byu.cs.roots.opg.model.OpgSession;
import edu.byu.cs.roots.opg.util.NameAbbreviator;
public class BoxLayout extends BoxLayoutParent{
	private static final long serialVersionUID = 1L;
	double totalHeight = 0;
	
	
	
	
	public BoxLayout(){
		lines = new ArrayList<LineLayout>();
		
	}
	
	double getHeight(){
		return totalHeight;
	}
	
	//override this if you need to check that data exists.
	public boolean canFit(Individual indi, double width, double height, MultisheetChartOptions ops, double fontSize, String dupLabel ){
		//TODO THIS IS NO LONGER USED
		return true;
	}
	
	public void drawNormalLines(OpgSession session, ChartMargins chart, PresetChartOptions options, StylingBox style,
			double x, double y, double width, double totalHeight, double textPos,
			Individual indi, LineLayout line,
			String dupLabel, boolean maleDescendant, Rectangle2D.Double boxCoords){
		double horizTextGap = style.textMargin;
		double linepos = 0.0;
		TextDirection direction = style.direction;
		if(direction == StylingBox.TextDirection.NORMAL)
		{
			linepos = y + (totalHeight / 2.0) - textPos;
			line.draw(session, chart, style, options, x + horizTextGap, linepos, width - (horizTextGap* 2), totalHeight, indi, dupLabel, maleDescendant, 0, "", direction);

		}
		else if (direction == StylingBox.TextDirection.NINETY)
		{
			linepos = x + width - textPos;
			line.draw(session, chart, style, options, linepos, y + (totalHeight / 2.0) - horizTextGap, totalHeight - (horizTextGap* 2), width, indi, dupLabel, maleDescendant, 0, "", direction);

		}
		else if (direction == StylingBox.TextDirection.ONE_EIGHTY)
		{
			linepos = y - (totalHeight / 2.0) + textPos;
			line.draw(session, chart, style, options, x + width - horizTextGap, linepos, width - (horizTextGap* 2), totalHeight, indi, dupLabel, maleDescendant, 0, "", direction);
		}
		else
		{
			linepos = x + textPos;
			line.draw(session, chart, style, options, linepos, y - (totalHeight / 2.0) + horizTextGap, totalHeight - (horizTextGap* 2), width, indi, dupLabel, maleDescendant, 0, "", direction);

		}
	}
	
	public void drawMarriageLines(OpgSession session, ChartMargins chart, PresetChartOptions options, StylingBox style,
		double x, double y, double width, double totalHeight, double textPos,
		Individual indi, LineLayout line,
		String dupLabel, Rectangle2D.Double boxCoords, int famCode){
		double horizTextGap = style.textMargin;
		double linepos = 0.0;
		TextDirection direction = style.direction;
		if(direction == StylingBox.TextDirection.NORMAL)
		{
			linepos = y + (totalHeight / 2.0) - textPos;
			line.draw(session, chart, style, options, x + horizTextGap, linepos, width - (horizTextGap* 2), totalHeight, indi, dupLabel, false, famCode, "", direction);

		}
		else if (direction == StylingBox.TextDirection.NINETY)
		{
			linepos = x + width - textPos;
			line.draw(session, chart, style, options, linepos, y + (totalHeight / 2.0) - horizTextGap, totalHeight - (horizTextGap* 2), width, indi, dupLabel, false, famCode, "", direction);
		}
		else if (direction == StylingBox.TextDirection.ONE_EIGHTY)
		{
			linepos = y - (totalHeight / 2.0) + textPos;
			line.draw(session, chart, style, options, x + width - horizTextGap, linepos, width - (horizTextGap* 2), totalHeight, indi, dupLabel, false, famCode, "", direction);

		}
		else
		{
			linepos = x + textPos;
			line.draw(session, chart, style, options, linepos, y - (totalHeight / 2.0) + horizTextGap, totalHeight - (horizTextGap* 2), width, indi, dupLabel, false, famCode, "", direction);

		}
	}
	
	
	//Used to draw the box and text of an Ancestor Box
	public void drawAnces(ChartMargins chart, PresetChartOptions options, StylingBox style,
			double x, double y,	double width, double hgt, Individual indi, String dupLabel, 
			Rectangle2D.Double boxCoords, OpgSession session){
		//Draw your box;
		totalHeight = hgt;
		
		double lineWidth = (options.isBoxBorder())? style.borderlineWidth : 0;
				
		if (options.isRoundedCorners())
			chart.addDrawCommand(new DrawCmdRoundRect(x, y-totalHeight/2.0, width,totalHeight, lineWidth, style.cornerCurve, Color.BLACK, session.getBaseOptions().getAncesScheme().getColor(indi.id), boxCoords));
		else
			chart.addDrawCommand(new DrawCmdFillRect(x, y-totalHeight/2.0, width,totalHeight, lineWidth, Color.BLACK, session.getBaseOptions().getAncesScheme().getColor(indi.id), boxCoords));
		
		
		
		//Draw each line
		for(int i = 0; i < lines.size(); i++){
			LineLayout line = lines.get(i);
			double textPos = (i<style.textPositions.size()?style.textPositions.get(i):style.textPositions.get(style.textPositions.size() - 1));
			
			drawNormalLines(session, chart, options, style, x, y, width, totalHeight, textPos, indi, line, dupLabel, true, boxCoords);
			
			
		}
		
		if (style.weddingLayout.lines.size() > 0){
			
			if((indi.gender == Gender.MALE && style.weddingDisplayType == StylingBox.WeddingPositions.HUSBAND_POSTFIX) ||
					(indi.gender == Gender.FEMALE && style.weddingDisplayType == StylingBox.WeddingPositions.WIFE_POSTFIX) ||
					style.weddingDisplayType == StylingBox.WeddingPositions.BOTH_POSTFIX){
				for (int i = 0; i < style.weddingLayout.lines.size(); i++){
					int listPosition = i + style.layout.lines.size();
					LineLayout line = style.weddingLayout.lines.get(i);
					double textPos = (listPosition<style.textPositions.size()?style.textPositions.get(listPosition):style.textPositions.get(style.textPositions.size() - 1));
					
					drawMarriageLines(session, chart, options, style, x, y, width, totalHeight, textPos, indi, line, dupLabel, boxCoords, 0);
										
				}
			}
		}
		
	}
	
	
	
	public void drawDescSingle(ChartMargins chart, StylingBox style, 
			PresetChartOptions options, double x, double y, 
			double width, double hgt,  
			Individual indi, Rectangle2D.Double boxCoords, OpgSession session){
		//Draws the box;
		totalHeight = hgt;
		double lineWidth = (options.isBoxBorder())? style.borderlineWidth : 0;
		
		
		if (options.isRoundedCorners())
			chart.addDrawCommand(new DrawCmdRoundRect(x, y-totalHeight/2.0, width,totalHeight, lineWidth, style.cornerCurve, Color.BLACK, session.getBaseOptions().getDescScheme().getColor(indi.id), boxCoords));
		else
			chart.addDrawCommand(new DrawCmdFillRect(x, y-totalHeight/2.0, width,totalHeight, lineWidth, Color.BLACK, session.getBaseOptions().getDescScheme().getColor(indi.id), boxCoords));

		
		//Draw each line
		for(int i = 0; i < lines.size(); i++){
			LineLayout line = lines.get(i);
			double textPos = (i<style.textPositions.size()?style.textPositions.get(i):style.textPositions.get(style.textPositions.size() - 1));
			
			drawNormalLines(session, chart, options, style, x, y, width, totalHeight, textPos, indi, line, "", true, boxCoords);
			
		}
	}
	
	public void drawDescMarried(ChartMargins chart, StylingBox style, PresetChartOptions options, 
			double x, double y, double width, double hgt, 
			Individual indi, Individual husband, Individual wife, boolean drawBorder, 
			boolean maleDescendant, Rectangle2D.Double boxCoords, int famCode, OpgSession session){
		
		totalHeight = hgt;
		double lineWidth = (options.isBoxBorder() && drawBorder)? style.borderlineWidth : 0;
		double xPos = x;
		
		if (options.isRoundedCorners())
			chart.addDrawCommand(new DrawCmdRoundRect(x, y-totalHeight/2.0, width,totalHeight, lineWidth, style.cornerCurve, Color.BLACK, session.getBaseOptions().getDescScheme().getColor(indi.id), boxCoords));
		else
			chart.addDrawCommand(new DrawCmdFillRect(x, y-totalHeight/2.0, width,totalHeight, lineWidth, Color.BLACK, session.getBaseOptions().getDescScheme().getColor(indi.id), boxCoords));

		if (parallelCouple)
		{
			if(style.direction == StylingBox.TextDirection.NORMAL || style.direction == StylingBox.TextDirection.ONE_EIGHTY)
				width = width/2.0;
			else
				totalHeight = totalHeight/2.0;

		}
		
		int positionMarker = 0;
		//Draw each line
		for(int i = 0; i < lines.size(); i++){
			LineLayout line = lines.get(i);
			double textPos = (i<style.textPositions.size()?style.textPositions.get(i):style.textPositions.get(style.textPositions.size() - 1));
			drawNormalLines(session, chart, options, style, x, y, width, totalHeight, textPos, husband, line, "", maleDescendant, boxCoords);
			positionMarker++;
		}
		//Displays wedding information after the husband, if that's what's chosen
		if((style.weddingDisplayType == StylingBox.WeddingPositions.HUSBAND_POSTFIX || 
				(style.weddingDisplayType == StylingBox.WeddingPositions.DIRECT_DESCENDANT_POSTFIX && maleDescendant) ||
				style.weddingDisplayType == StylingBox.WeddingPositions.BOTH_POSTFIX) && 
				style.weddingLayout!= null && !parallelCouple){
			for(int i = 0; i < style.weddingLayout.lines.size(); i++){
				LineLayout line = style.weddingLayout.lines.get(i);
				double textPos = (positionMarker<style.textPositions.size()?style.textPositions.get(positionMarker):style.textPositions.get(style.textPositions.size() - 1));
				
				drawMarriageLines(session, chart, options, style, x, y, width, totalHeight, textPos, indi, line, "", boxCoords, famCode);
				positionMarker++;
			}
		}
		if (parallelCouple)
		{
			if(style.direction == StylingBox.TextDirection.NORMAL || style.direction == StylingBox.TextDirection.ONE_EIGHTY)
				x += (width);
			else
				y += (totalHeight);
			
			positionMarker = 0;
		}
		for(int i = 0; i < lines.size(); i++){
			LineLayout line = lines.get(i);
			double textPos = (positionMarker<style.textPositions.size()?style.textPositions.get(positionMarker):style.textPositions.get(style.textPositions.size() - 1));
			
			drawNormalLines(session, chart, options, style, x, y, width, totalHeight, textPos, wife, line, "", !maleDescendant, boxCoords);
			positionMarker++;
		}
		if((style.weddingDisplayType == StylingBox.WeddingPositions.WIFE_POSTFIX || 
				(style.weddingDisplayType == StylingBox.WeddingPositions.DIRECT_DESCENDANT_POSTFIX && !maleDescendant) ||
				style.weddingDisplayType == StylingBox.WeddingPositions.BOTH_POSTFIX) && 
				style.weddingLayout!= null && !parallelCouple){
			for(int i = 0; i < style.weddingLayout.lines.size(); i++){
				LineLayout line = style.weddingLayout.lines.get(i);
				double textPos = (positionMarker<style.textPositions.size()?style.textPositions.get(positionMarker):style.textPositions.get(style.textPositions.size() - 1));
				
				drawMarriageLines(session, chart, options, style, x, y, width, totalHeight, textPos, indi, line, "", boxCoords, famCode);
				positionMarker++;
			}
		}
		if (parallelCouple){
			for(int i = 0; i < style.weddingLayout.lines.size(); i++){
				LineLayout line = style.weddingLayout.lines.get(i);
				double textPos = (positionMarker<style.textPositions.size()?style.textPositions.get(positionMarker):style.textPositions.get(style.textPositions.size() - 1));
				double offset = ((width*2) - line.getWidth(session, style.fontSize, options, indi, "", width, famCode))/2.0;
				drawMarriageLines(session, chart, options, style, xPos+offset, y, (width*2), totalHeight, textPos, indi, line, "", boxCoords, famCode);
				positionMarker++;
			}
		}
	}
	
	public void drawEndArrowText(ChartMargins chart, StylingBox style,
			PresetChartOptions options, double x, double y,
			AncesBoxParent box, OpgSession session, Font font){
		ArrayList<String> lines = new ArrayList<String>();
		double xOffset = ((style.isIntruding && options.isAllowIntrusion())?style.intrudeWidth:style.getBoxWidth());
		
		if(options.getArrowStyle() == PresetChartOptions.EndLineArrowStyle.GENERATIONS){
			if ((box.getIndi().father != null) && (box.getIndi().mother == null))
				lines.add("G: " + box.father.maxGensInTree);
			else if ((box.mother != null) && (box.father == null))
				lines.add("G: " + box.mother.maxGensInTree);
			else
				lines.add("G: " + Math.max(box.mother.maxGensInTree, box.father.maxGensInTree));

			lines.add("I: "+box.getIndi().numberOfAncestors);
		}
		else if(options.getArrowStyle() == PresetChartOptions.EndLineArrowStyle.PARENTS){
			if (box.getIndi().father != null && box.getIndi().mother != null){
				lines.add("F: " + box.getIndi().father.pageId);
				lines.add("M: " + box.getIndi().mother.pageId);
			}
			else if(box.getIndi().father != null)
				lines.add("F: " + box.getIndi().father.pageId);
			else if(box.getIndi().mother != null)
				lines.add("M: " + box.getIndi().mother.pageId);
			
			
		}
		else if(options.getArrowStyle() == PresetChartOptions.EndLineArrowStyle.SELF){
			lines.add("Ch: "+ box.getIndi().pageId);
		}
		
		double fontHeight = 0.0;
		for(int i = 0; i < lines.size(); i++){
			double size;
			if ((size = font.getStringBounds(lines.get(i), NameAbbreviator.frc).getHeight()) > fontHeight)
				fontHeight = size;
		}
		
		
		double yPos = y - fontHeight/4.0 + ((fontHeight/2.0)*(lines.size()-1));
		for(int i = 0; i < lines.size(); i++){
			chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x + xOffset), chart.xOffset(yPos)));
			chart.addDrawCommand(new DrawCmdText(lines.get(i)));
			
			yPos-=fontHeight;
		}
		
	}
	
	public void drawStartArrowText(ChartMargins chart, StylingBox style,
			PresetChartOptions options, double x, double y, double arrowWidth, double arrowHeadHeight, double arrowShaftHeight,
			ArrayList<String> lines, OpgSession session, Font font, List<Individual> pointBackList){
		
		
		
		double fontHeight = 0.0;
		double fontWidth = 0.0;
		for(int i = 0; i < lines.size(); i++){
			double size;
			if ((size = font.getStringBounds(lines.get(i), NameAbbreviator.frc).getHeight()) > fontHeight)
				fontHeight = size;
			double width;
			if((width = font.getStringBounds(lines.get(i), NameAbbreviator.frc).getWidth()) > fontWidth)
				fontWidth = width;
		}
		
		double heightIncrease = arrowHeadHeight - arrowShaftHeight;
		
		
		double totalWidth = arrowWidth;
		
		double splitHeight = fontHeight;

		
		
		double yPos = y - fontHeight/4.0 + ((fontHeight/2.0)*(lines.size()-1));
		for(int i = 0; i < lines.size(); i++){
			chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x-fontWidth), chart.xOffset(yPos)));
			chart.addDrawCommand(new DrawCmdText(lines.get(i)));
			
		
			yPos-=fontHeight;
		}
		yPos = y - fontHeight/4.0 + ((fontHeight/2.0)*(lines.size()-1)) + fontHeight/4.0;
		for(int i = 0; i < lines.size(); i++){
			chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x-totalWidth), chart.yOffset(yPos)));
			chart.addDrawCommand(new DrawCmdPageLink(arrowWidth, splitHeight, pointBackList.get(i)));
			yPos-=splitHeight;
		}
		
	}
	
	public String toString(){
		return displayName;
	}
	
}


