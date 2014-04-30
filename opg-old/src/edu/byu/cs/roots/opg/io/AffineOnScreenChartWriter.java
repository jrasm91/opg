package edu.byu.cs.roots.opg.io;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.fop.svg.PDFDocumentGraphics2D;
import org.apache.log4j.Logger;

import edu.byu.cs.roots.opg.chart.ChartConversion;
import edu.byu.cs.roots.opg.chart.ChartDrawInfo;
import edu.byu.cs.roots.opg.chart.cmds.DrawCommand;
import edu.byu.cs.roots.opg.chart.cmds.DrawState;
import edu.byu.cs.roots.opg.model.OpgPage;
import edu.byu.cs.roots.opg.model.OpgSession;

public class AffineOnScreenChartWriter implements ChartWriter {
	
	public static Logger log = Logger.getLogger(AffineOnScreenChartWriter.class);
	
	//the position of the upper left corner of the page in relation to the upperleft corner of the panel
//	private int xOffset, yOffset;
	
//	private double scaleFactor;
//	private boolean firstTime = true;
//	private boolean inDrag = false;
//	private int dragStartX, dragStartY, dragStartXOffset, dragStartYOffset;
	private DrawState state = new DrawState();
	//private double fontSize;
	//private Font font;


	public void createChart(ChartDrawInfo chartInfo, String fileName) throws IOException {

	}
	
	private static final double rulerWidth = 72; // In points for paper
	private static final double specialRulerWidth = 20; // In pixels for screen
	private static final Color darkShade3D = new Color(100, 100, 100);
	private static final Color normalColor3D = new Color(180, 180, 180);
	private static final Color lightShade3D = new Color(230, 230, 230);
	private static final Color rulerBackground = Color.WHITE;
	private static final Color rulerFontColor = Color.RED;
	private static final Color rulerMarkColor = Color.BLACK;

	/**
	 * Binary Search that quickly (logn) will get which commands should be removed from the lists of
	 * draw commands because they are not in the viewing space.
	 * @param cutOff - The X or Y that should be the last thing to include
	 * @param dir - Which direction we are cutting off from
	 * @param SortedCmds - The list of commands to chop.
	 * @return A list containting the elements of the original list that should be removed.
	 */
	/*private List<DrawCommand> getElementsToRemove(double cutOff, SortDirection dir, List<DrawCommand> SortedCmds)
	{
		int lowerbound = 0;
		int upperbound = SortedCmds.size();
		ArrayList<DrawCommand> retValue = new ArrayList<DrawCommand>(SortedCmds);
		//System.err.println(retValue.get(0).toString());
		while (lowerbound != upperbound)
		{
			int index = (lowerbound + upperbound) / 2;
			Rectangle spBox = retValue.get(index).getShapeBox();
			double curPos = 0;
			switch(dir)
			{
			case BOTTOM:
				curPos = spBox.y;
				break;
			case LEFT:
				curPos = spBox.x + spBox.width;
				break;
			case RIGHT:
				curPos = spBox.x;
				break;
			case TOP:
				curPos = spBox.y + spBox.height;
				break;
			}
			if (curPos >= cutOff)
			{
				switch(dir)
				{
				case BOTTOM:
					upperbound = index;
					break;
				case LEFT:
					upperbound = index;
					break;
				case RIGHT:
					upperbound = index;
					break;
				case TOP:
					lowerbound = index;
					break;
				}
			}
			else
			{
				switch(dir)
				{
				case BOTTOM:
					lowerbound = index;
					break;
				case LEFT:
					lowerbound = index;
					break;
				case RIGHT:
					lowerbound = index;
					break;
				case TOP:
					upperbound = index;
					break;
				}
			}
			if (Math.abs(upperbound - lowerbound) <= 1)
			{
				if (dir == SortDirection.BOTTOM || dir == SortDirection.LEFT)
				{
					upperbound = lowerbound;
				}
				else
				{
					lowerbound = upperbound;
				}
			}
		}
		return retValue.subList((dir == SortDirection.BOTTOM || dir == SortDirection.LEFT) ? 0 : lowerbound, 
				(dir == SortDirection.BOTTOM || dir == SortDirection.LEFT) ? upperbound : SortedCmds.size());
	}
	*/

	
	public void createChart(OpgPage page, Graphics2D g, int width, int height, double zoom, int xOffset, int yOffset, Graphics screenG, boolean ruler, OpgSession session) {
		ArrayList<ChartDrawInfo> charts = session.getCharts();
		if(charts == null || charts.size() == 0 || charts.get(0) == null){	return;	}
		state.reset();
		state.chartLeftToDisplay = xOffset;
		state.chartTopToDisplay = yOffset;
		state.session = session;
		
		double screenRulerHeight = ChartConversion.convertToScreenSize(rulerWidth, zoom);
		//turn on fractional font metrics (eliminate font rounding) and antialiasing
		g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		//This part is where the back gets printed white
		double chartHeight = 0;
		for (ChartDrawInfo chart : charts){
			chartHeight += chart.getYExtent();
		}
		double chartWidth = charts.get(0).getXExtent();
		boolean leftRuler = false;
		boolean topRuler = false;
		g.setColor(Color.white);
		Point upperLeft = ChartConversion.convertToScreenCoord(new Point(0,0), zoom, state);
		Point lowerRight = ChartConversion.convertToScreenCoord(new Point((int)chartWidth, (int)chartHeight), zoom, state);
		double chartLeft = 0;
		double chartTop = 0;
		
		
		if (upperLeft.y < screenRulerHeight)
		{
			if (upperLeft.y < 0)
			{
				chartTop = ChartConversion.convertDoubleToChartDouble(0, zoom, yOffset);
				chartHeight -= chartTop;
			}
			topRuler = true;
		}
		if (lowerRight.y > height)
		{
			chartHeight = ChartConversion.convertToChartSize(height, zoom) + 10;
		}
		if (upperLeft.x < screenRulerHeight)
		{
			if (upperLeft.x < 0)
			{
				chartLeft = ChartConversion.convertDoubleToChartDouble(0, zoom, xOffset);
				chartWidth -= chartLeft;
			}
			leftRuler = true;
		}
		if (lowerRight.x > width)
		{
			chartWidth = ChartConversion.convertToChartSize(width, zoom) + 10;
		}
		
		g.fillRect((int)chartLeft, (int)chartTop, (int)chartWidth, (int)chartHeight);
		
		double chartHeightIncrease = 0;
		for (ChartDrawInfo chartInfo : charts){
			//g.translate(0d, chartInfo.getYOffset());

			
			
			state.xExtent = chartInfo.getXExtent();
			state.yExtent = chartInfo.getYExtent();
			
			//if (chartInfo.getUpSortedDrawCmds() == null)
			//	chartInfo.updateSortedCharts();
			
			List<DrawCommand> sortCmds = new LinkedList<DrawCommand>(chartInfo.getDrawCommands());//new LinkedList<DrawCommand>(chartInfo.getUpSortedDrawCmds());
			// Sort the commands only if the chart changed
			//sortCmds.removeAll(getElementsToRemove(-xOffset, SortDirection.LEFT, chartInfo.getLeftSortedDrawCmds()));
			//sortCmds.removeAll(getElementsToRemove(-xOffset + width + 117, SortDirection.RIGHT, chartInfo.getRightSortedDrawCmds()));
			//sortCmds.removeAll(getElementsToRemove(yOffset, SortDirection.TOP, chartInfo.getUpSortedDrawCmds()));
			//sortCmds.removeAll(getElementsToRemove(yOffset + height, SortDirection.BOTTOM, chartInfo.getDownSortedDrawCmds()));
			
			// Sort the drawing commands to be the last things written
			//sortCmds = chartInfo.sortTextCmds(sortCmds);
			
			// Execute the commands for drawing
			boolean useAbsolute = false;
			for(DrawCommand cmd : (useAbsolute ? sortCmds : chartInfo.getDrawCommands())){
				if (useAbsolute) 
					cmd.executeAbsolute(g, state, width, height, zoom);
				else
					cmd.execute(g, state, width, height, zoom, new Point(0, (int)chartHeightIncrease));
			}
			chartHeightIncrease+=chartInfo.getYExtent();
			g.translate(0d, chartInfo.getYExtent());
		}
		// Draw the rulers
		if (ruler)
		{
			if (!topRuler)
			{
				double rulerWidth = 36 * 72;
				//rulerWidth = chartInfo.getXExtent();
				/* Draw the normal top ruler*/
				g.setColor(new Color(217, 180,94));
				g.fill3DRect(0, -80-(int)(page.getPageHeight()), (int)rulerWidth, 72, true);
				//System.out.println("Draw Normal Top Ruler");
				double currentXPos = 2;
				int startRuler = 0;
				int divider = 8;
				double minMarkSpacing = 72.0 / divider;
				while (currentXPos < rulerWidth - 4)
				{
					int initialHeight = 24;
					int markLength = 14;
					int temp = 2;
					// Inch mark
					while (startRuler % temp == 0 && temp <= divider)
					{
						// 8
						markLength = initialHeight;
						initialHeight += 6;
						temp *= 2;
					}
					
					// Draw the mark
					g.setColor(rulerMarkColor);
					g.drawLine((int)currentXPos, -10-(int)(page.getPageHeight()), (int)currentXPos, -8 - (3 + markLength)-(int)(page.getPageHeight()));
					if (startRuler % divider == 0 && startRuler != 0)
					{
						g.setColor(rulerMarkColor);
						// Write inch mark number
						g.setFont(new Font("Times New Roman",0, 22 ));
						g.drawString("" + startRuler / divider, (int)currentXPos - 5, -60-(int)(page.getPageHeight()));
						g.setColor(rulerMarkColor);
					}
					
					startRuler++;
					if (minMarkSpacing > 0)
						currentXPos += minMarkSpacing;
					else
						currentXPos += 1;
				}
			}
			if (leftRuler)
			{
				/* Draw the special left ruler*/
				double rulerChartWidth = specialRulerWidth + 2;
				Point rulerStart = new Point(0,0);
				double rulerChartLength = width;
				screenG.setColor(darkShade3D);
				screenG.fillRect(rulerStart.x, rulerStart.y, (int)Math.ceil(rulerChartWidth), (int)Math.ceil(rulerChartLength) + 2);
				screenG.setColor(normalColor3D);
				screenG.fillRect(rulerStart.x, rulerStart.y, (int)Math.ceil(rulerChartWidth)-2, (int)Math.ceil(rulerChartLength));
				screenG.setColor(lightShade3D);
				screenG.fillRect(rulerStart.x, rulerStart.y, (int)Math.ceil(rulerChartWidth) - 2, 1);
				//System.out.println("Draw Special Left Ruler");
				//System.out.println("Draw Special Top Ruler");
				screenG.setColor(darkShade3D);
				screenG.fillRect(0, 0, (int)specialRulerWidth+2, (int)specialRulerWidth+2);
				screenG.setColor(normalColor3D);
				screenG.fillRect(0, 0, (int)specialRulerWidth, (int)specialRulerWidth);
				screenG.setColor(lightShade3D);
				screenG.fillRect(0, 0, (int)specialRulerWidth, 1);
				screenG.fillRect(0, 0, 1, (int)specialRulerWidth);
				
				// Draw the background for the ruler
				screenG.setColor(rulerBackground);
				screenG.fillRect(2, (int)specialRulerWidth + 4, (int)specialRulerWidth - 4, height - (int)specialRulerWidth - 6);
				screenG.setColor(darkShade3D);
				screenG.drawLine(2, (int)specialRulerWidth + 4, 2, height - 4);
				screenG.drawLine(2, (int)specialRulerWidth + 4, (int)specialRulerWidth - 3, (int)specialRulerWidth + 4);
				screenG.setColor(lightShade3D);				
				screenG.drawLine((int)specialRulerWidth - 3, (int)specialRulerWidth + 4, (int)specialRulerWidth - 3, height - 4);
				screenG.drawLine(2, height - 4, (int)specialRulerWidth - 3, height - 4);				
				//System.out.println("Draw Special Left Ruler");
				
				double pointsPerPixel = 1/zoom;
				double pixelsPerInch = 72 / pointsPerPixel;

				
				// Draw the lines.
				//	Find the initial coordinate for a 1/divider of an inch
				int startRuler = 0;
				int currentYPos = (int)(specialRulerWidth + 4); 
				double divider = Math.min(Math.pow(2, (int)Math.floor(Math.log(pixelsPerInch)/Math.log(2))), 32);
				boolean drawFootMarks = false;
				if (divider <= 8.0)
				{
					drawFootMarks = true;
					divider = Math.min(Math.pow(2, (int)Math.floor(Math.log((pixelsPerInch * 12))/Math.log(2))), 32);
				}

				double markSpacing = ((drawFootMarks ? pixelsPerInch * 12 : pixelsPerInch) / divider);
				int marks = 0;
				int minHeight = 3;
				int maxHeight = 16;
				double heightDivisions = (maxHeight - minHeight)/(Math.log(divider)/Math.log(2));
				double yEdge = yOffset;
				startRuler = -(int)(((yEdge - currentYPos) / (drawFootMarks ? pixelsPerInch * 12 : pixelsPerInch)) * divider);
				
				/*screenG.setColor(Color.BLUE);
				int lineMark = yOffset;
				screenG.drawLine(0, lineMark, (int)specialRulerWidth, lineMark);*/
				
				currentYPos += -yEdge - (startRuler / divider * (drawFootMarks ? pixelsPerInch * 12 : pixelsPerInch));
				screenG.setColor(rulerMarkColor);
				while (currentYPos < height - 4)
				{
					marks++;
					int markLength = minHeight;
					double curExtension = heightDivisions;
					int temp = 2;
					// Inch mark
					while (startRuler % temp == 0 && temp <= divider)
					{
						// 8
						markLength = (int)curExtension;
						curExtension += heightDivisions;
						temp *= 2;
					}
					
					// Draw the mark
					screenG.drawLine((int)specialRulerWidth - 3, currentYPos, (int)specialRulerWidth - (3 + markLength), currentYPos);
					if (startRuler % divider == 0)
					{
						screenG.setColor(rulerFontColor);
						// Write inch or foot mark number
						screenG.drawString("" + (int)(startRuler / divider) + (drawFootMarks ? "'" : "\""), 4, currentYPos - 2);
						screenG.setColor(rulerMarkColor);
					}
					currentYPos = (int)(specialRulerWidth + 4 + marks * markSpacing); 
					startRuler++;
				}
			}
			else
			{
				/* Draw the normal left ruler*/
				double rulerHeight = 36 * 72;
				//rulerHeight = chartInfo.getYExtent();
				g.setColor(new Color(217, 180,94));
				g.fill3DRect(-80, 0-(int)(page.getPageHeight()), 72, (int)rulerHeight, true);
				//System.out.println("Draw Normal Left Ruler");
				double currentYPos = 2;
				int startRuler = 0;
				int divider = 8;
				double minMarkSpacing = 72.0 / divider;
				while (currentYPos < rulerHeight - 1)
				{
					int initialHeight = 24;
					int markLength = 14;
					int temp = 2;
					// Inch mark
					while (startRuler % temp == 0 && temp <= divider)
					{
						// 8
						markLength = initialHeight;
						initialHeight += 6;
						temp *= 2;
					}
					
					// Draw the mark
					g.setColor(rulerMarkColor);
					g.drawLine(-10, (int)currentYPos-(int)(page.getPageHeight()),  -8 - (3 + markLength), (int)currentYPos-(int)(page.getPageHeight()));
					if (startRuler % divider == 0 && startRuler != 0)
					{
						g.setColor(rulerMarkColor);
						// Write inch mark number
						g.setFont(new Font("Times New Roman",0, 22 ));
						g.drawString("" + startRuler / divider, -75, (int)currentYPos + 6-(int)(page.getPageHeight()));
						g.setColor(rulerMarkColor);
					}
					
					startRuler++;
					if (minMarkSpacing > 0)
						currentYPos += minMarkSpacing;
					else
						currentYPos += 1;
				}
			}
			if (topRuler)
			{
				/* Draw the special top ruler*/
				double pointsPerPixel = 1/zoom;
				double pixelsPerInch = 72 / pointsPerPixel;
				double rulerChartLength = specialRulerWidth;
				Point rulerStart = new Point(0,0);
				double rulerChartWidth = width;
				screenG.setColor(darkShade3D);
				screenG.fillRect(rulerStart.x, rulerStart.y, (int)Math.ceil(rulerChartWidth), (int)Math.ceil(rulerChartLength) + 2);
				screenG.setColor(normalColor3D);
				screenG.fillRect(rulerStart.x, rulerStart.y, (int)Math.ceil(rulerChartWidth)-2, (int)Math.ceil(rulerChartLength));
				screenG.setColor(lightShade3D);
				screenG.fillRect(rulerStart.x, rulerStart.y, (int)Math.ceil(rulerChartWidth) - 2, 1);
				screenG.setColor(darkShade3D);
				screenG.fillRect(0, 0, (int)specialRulerWidth+2, (int)specialRulerWidth+2);
				screenG.setColor(normalColor3D);
				screenG.fillRect(0, 0, (int)specialRulerWidth, (int)specialRulerWidth);
				screenG.setColor(lightShade3D);
				screenG.fillRect(0, 0, (int)specialRulerWidth, 1);
				screenG.fillRect(0, 0, 1, (int)specialRulerWidth);
				
				// Draw the background for the ruler
				screenG.setColor(rulerBackground);
				screenG.fillRect((int)specialRulerWidth + 4, 2, width - (int)specialRulerWidth - 6, (int)specialRulerWidth - 4);
				screenG.setColor(darkShade3D);
				screenG.drawLine((int)specialRulerWidth + 4, 2, width - 4, 2);
				screenG.drawLine((int)specialRulerWidth + 4, 2, (int)specialRulerWidth + 4, (int)specialRulerWidth - 3);
				screenG.setColor(lightShade3D);				
				screenG.drawLine((int)specialRulerWidth + 4, (int)specialRulerWidth - 3, width - 4, (int)specialRulerWidth - 3);
				screenG.drawLine(width - 4, 2, width - 4, (int)specialRulerWidth - 3);
				
				// Find the initial coordinate for a 1/divider of an inch
				int startRuler = 0;
				int currentXPos = (int)(specialRulerWidth + 4); 
				double divider = Math.min(Math.pow(2, (int)Math.floor(Math.log(pixelsPerInch)/Math.log(2))), 32);
				boolean drawFootMarks = false;
				if (divider <= 8.0)
				{
					drawFootMarks = true;
					divider = Math.min(Math.pow(2, (int)Math.floor(Math.log((pixelsPerInch * 12))/Math.log(2))), 32);
				}
				double markSpacing = ((drawFootMarks ? pixelsPerInch * 12 : pixelsPerInch) / divider);
				int marks = 0;
				int minHeight = 3;
				int maxHeight = 16;
				double heightDivisions = (maxHeight - minHeight)/(Math.log(divider)/Math.log(2));
				double xEdge = xOffset;
				startRuler = -(int)Math.floor((xEdge - currentXPos) / (drawFootMarks ? pixelsPerInch * 12 : pixelsPerInch) * divider);
				currentXPos += -xEdge - (startRuler / divider * (drawFootMarks ? pixelsPerInch * 12 : pixelsPerInch));
				screenG.setColor(rulerMarkColor);
				while (currentXPos < width - 4)
				{
					marks++;
					int markLength = minHeight;
					double curExtension = heightDivisions;
					int temp = 2;
					// Inch mark
					while (startRuler % temp == 0 && temp <= divider)
					{
						// 8
						markLength = (int)curExtension;
						curExtension += heightDivisions;
						temp *= 2;
					}
					
					// Draw the mark
					screenG.drawLine(currentXPos, (int)specialRulerWidth - 3, currentXPos, (int)specialRulerWidth - (3 + markLength));
					if (startRuler % divider == 0)
					{
						screenG.setColor(rulerFontColor);
						// Write inch mark number
						screenG.drawString("" + (int)(startRuler / divider) + (drawFootMarks ? "'" : "\""), currentXPos + 2, 13);
						screenG.setColor(rulerMarkColor);
					}
					currentXPos = (int)(specialRulerWidth + 4 + marks * markSpacing); 
					startRuler++;
				}
				
			}
		}
	}
	
	public void createChart(ChartDrawInfo chartInfo, PDFDocumentGraphics2D g){
		if(chartInfo == null){	return;	}
		state.reset();
		
		//paint the page white
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, (int)(chartInfo.getXExtent()), (int)(chartInfo.getYExtent()) );
		
		state.xExtent = chartInfo.getXExtent();
		state.yExtent = chartInfo.getYExtent();
		
		
		for(DrawCommand cmd : chartInfo.getDrawCommands()){
			cmd.execute(g, state);
		}
	}
	
	public void createChart(ChartDrawInfo chartInfo, Graphics2D g, double xOffset, double yOffset){
		if(chartInfo == null){	return;	}
		state.reset();

		
		g.translate(xOffset, yOffset);
		//paint the page white
		g.setColor(Color.white);
		g.fillRect(0, 0, (int)(chartInfo.getXExtent()), (int)(chartInfo.getYExtent()) );
		
		state.xExtent = chartInfo.getXExtent();
		state.yExtent = chartInfo.getYExtent();

		
		for(DrawCommand cmd : chartInfo.getDrawCommands()){
			cmd.execute(g, state);
		}
		g.translate(-xOffset, -yOffset);
	}
}

