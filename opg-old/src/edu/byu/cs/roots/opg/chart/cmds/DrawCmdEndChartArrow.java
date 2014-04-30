package edu.byu.cs.roots.opg.chart.cmds;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import org.apache.fop.pdf.PDFLink;
import org.apache.fop.svg.PDFDocumentGraphics2D;

public class DrawCmdEndChartArrow extends DrawCommand implements Serializable {
	private double shaftHeight, headHeight, headWidth, lineWidth, shaftWidth;
	private Color lineColor, fillColor;
	private Rectangle2D.Double boxInfo;
	static final long serialVersionUID = 1000L;
	
	
	private Point coords;
	
	public DrawCmdEndChartArrow(double shaftHeight, double headHeight, double headWidth, double shaftWidth, double lineWidth, Color lineColor, Color fillColor)
	{
		
		this.shaftHeight = shaftHeight;
		this.headHeight = headHeight;
		this.headWidth = headWidth;
		this.lineWidth = lineWidth;
		this.lineColor = lineColor;
		this.fillColor = fillColor;
		this.shaftWidth = shaftWidth;
		offset = new Point2D.Double(0,0);
		coords = new Point(DrawCommand.curPos); 
	}
	public DrawCmdEndChartArrow(double shaftHeight, double headHeight, double headWidth, double shaftWidth, double lineWidth, Color lineColor, Color fillColor, Rectangle2D.Double boxCoords)
	{
		
		this.shaftHeight = shaftHeight;
		this.headHeight = headHeight;
		this.headWidth = headWidth;
		this.lineWidth = lineWidth;
		this.lineColor = lineColor;
		this.fillColor = fillColor;
		this.shaftWidth = shaftWidth;
		offset = new Point2D.Double(0,0);
		coords = new Point(DrawCommand.curPos);
		boxInfo = boxCoords;
	}
	
	
	//access methods
	public double getHeight() { return shaftHeight; }
	public double getWidth() { return headWidth; }
	public double getRectangleWidth() { return shaftWidth; }
	public double getLineWidth() { return lineWidth; }
	public Color getLineColor() { return lineColor; };
	public Color getFillColor() { return fillColor; };
	
	public String toString()
	{
		return super.toString() + " - " + shaftHeight + headWidth + lineColor.getRed() + lineColor.getGreen() + lineColor.getBlue() + fillColor.getRed() + fillColor.getGreen() + fillColor.getBlue();
	}

	public void execute(Graphics2D g, DrawState state) {
		BasicStroke oldStroke = (BasicStroke) g.getStroke();
		g.setStroke(new BasicStroke((float) lineWidth));
		if (state.curColor != fillColor)
		{
			state.curColor = fillColor;
			g.setColor(fillColor);
		}
		g.fillPolygon(
				new int[]{
				(int) ((int) state.pos.x + offset.x), 
				(int) ((int) state.pos.x + offset.x), 
				((int) (state.pos.x + shaftWidth + offset.x)), 
				((int) (state.pos.x + shaftWidth + offset.x)), 
				(int) ((int) state.pos.x + offset.x + (int)(headWidth + shaftWidth)), 
				((int) (state.pos.x + offset.x + shaftWidth)),
				((int) (state.pos.x + offset.x + shaftWidth))}, 
				new int[]{ 
				(int)(state.yExtent + offset.y - (state.pos.y + shaftHeight/2.0)), 
				(int)(state.yExtent + offset.y - (state.pos.y - shaftHeight/2.0)), 
				(int)(state.yExtent + offset.y - (state.pos.y - shaftHeight/2.0)), 
				(int)(state.yExtent + offset.y - (state.pos.y - headHeight/2.0)), 
				(int)(state.yExtent + offset.y - (state.pos.y)),
				(int)(state.yExtent + offset.y - (state.pos.y + headHeight/2.0)),
				(int)(state.yExtent + offset.y - (state.pos.y + shaftHeight/2.0))}, 7);
		if (state.curColor != lineColor)
		{
			state.curColor = lineColor;
			g.setColor(lineColor);
		}
		g.drawPolygon(
				new int[]{
					(int) ((int) state.pos.x + offset.x), 
					(int) ((int) state.pos.x + offset.x), 
					((int) (state.pos.x + shaftWidth + offset.x)), 
					((int) (state.pos.x + shaftWidth + offset.x)), 
					(int) ((int) state.pos.x + offset.x + (int)(headWidth + shaftWidth)), 
					((int) (state.pos.x + offset.x + shaftWidth)),
					((int) (state.pos.x + offset.x + shaftWidth))}, 
					new int[]{ 
					(int)(state.yExtent + offset.y - (state.pos.y + shaftHeight/2.0)), 
					(int)(state.yExtent + offset.y - (state.pos.y - shaftHeight/2.0)), 
					(int)(state.yExtent + offset.y - (state.pos.y - shaftHeight/2.0)), 
					(int)(state.yExtent + offset.y - (state.pos.y - headHeight/2.0)), 
					(int)(state.yExtent + offset.y - (state.pos.y)),
					(int)(state.yExtent + offset.y - (state.pos.y + headHeight/2.0)),
					(int)(state.yExtent + offset.y - (state.pos.y + shaftHeight/2.0))}, 7);
		//g.fillRect( (int) state.pos.x, (int) (state.yExtent - (state.pos.y + width)), (int)(length), (int)(width) );
			
		
		//set color
		if (state.curColor != lineColor)
		{
			state.curColor = lineColor;
			//g.setColor(new Color( (float)curColor.getRed() / 255.0f, (float)curColor.getGreen() / 255.0f, (float)curColor.getBlue() / 255.0f, (float)scaleFactor ) );
			g.setColor(lineColor);
		}
		
		if (boxInfo != null)
		{
			boxInfo.x = state.pos.x + offset.x;
			boxInfo.y = state.yExtent - (state.pos.y + headWidth) + offset.y;
			boxInfo.width = shaftHeight;
			boxInfo.height = headWidth;
		}
		
		g.setStroke(oldStroke);
		
		
	}
	@Override
	public void execute(Graphics2D g, DrawState state, int width, int height, double zoom, Point multiChartOffset) {
		execute(g, state);
	}

	@Override
	public Rectangle getShapeBox() {
		
		return new Rectangle(coords.x + (int)offset.x, (int)(coords.y - headWidth / 2) + (int)offset.y, (int)shaftHeight, (int)headWidth);
	}

	@Override
	public void executeAbsolute(Graphics2D g, DrawState state, int width, int height, double zoom) {
		g.setColor(fillColor);
		g.drawPolygon(new int[]{(int) coords.x, (int) coords.x, ((int) coords.x + (int)(width))},new int[]{ 5, 5 ,5 , 5 }, 3);
		//set color
		g.setColor(lineColor);
		g.drawRect( (int) coords.x, (int) (state.yExtent -(coords.y + width)), (int)(height), (int)(width) );
		
	}

}


