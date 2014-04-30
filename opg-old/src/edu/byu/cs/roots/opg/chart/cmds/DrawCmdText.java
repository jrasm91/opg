package edu.byu.cs.roots.opg.chart.cmds;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import edu.byu.cs.roots.opg.fonts.OpgFont;
import edu.byu.cs.roots.opg.util.NameAbbreviator;

//import edu.byu.cs.roots.opg.chart.ChartConversion;

public class DrawCmdText extends DrawCommand implements Serializable {
	private String text;
	private double angle;//degrees
	private double lowerX, lowerY;
	static final long serialVersionUID = 1000L;
	private Point coords;
	private String fontName;
	private Color txtColor;
	private int fntStyle;
	private int fntSize;
	//private double yOffset=-1;
	
	public DrawCmdText(String text)
	{
		this.text = text;
		angle = 0.0;
		coords = new Point(DrawCommand.curPos);
		//txtFont = new Font(DrawCommand.curFont.getName(), DrawCommand.curFont.getStyle(), DrawCommand.curFont.getSize());
		fontName = DrawCommand.curFontName;
		fntSize = DrawCommand.curFontSize;
		fntStyle = DrawCommand.curFontStyle;
		txtColor = DrawCommand.curColor;
	}
	
	public DrawCmdText(String text, double angle)
	{
		this.text = text;
		this.angle = angle;
		this.lowerX = 0;
		this.lowerY = 0;
		coords = new Point(DrawCommand.curPos);
		//txtFont = new Font(DrawCommand.curFont.getName(), DrawCommand.curFont.getStyle(), DrawCommand.curFont.getSize());
		fontName = DrawCommand.curFontName;
		fntSize = DrawCommand.curFontSize;
		fntStyle = DrawCommand.curFontStyle;
		txtColor = DrawCommand.curColor;
	}
	public DrawCmdText(String text, double angle, double lowerX, double lowerY)
	{
		this.lowerX = lowerX;
		this.lowerY = lowerY;
		this.text = text;
		this.angle = angle;
		coords = new Point(DrawCommand.curPos);
		//txtFont = new Font(DrawCommand.curFont.getName(), DrawCommand.curFont.getStyle(), DrawCommand.curFont.getSize());
		fontName = DrawCommand.curFontName;
		fntSize = DrawCommand.curFontSize;
		fntStyle = DrawCommand.curFontStyle;
		txtColor = DrawCommand.curColor;
	}
	
	public String getText() { return text; }
	public double getAngle() { return angle; }
	
	public String toString()
	{
		return super.toString() + " - " + text + angle;
	}
	public void execute(Graphics2D g, DrawState state) 
	{
		Font tempFont = DrawCommand.fonts.get(fontName);
		if (tempFont == null)
		{
			for(OpgFont font: OpgFont.values()){
				Font testFont = font.font;
				if (testFont.getName().equals(fontName))
				{
					tempFont = testFont;
					break;
				}
			}
			if (tempFont == null)
				tempFont = new Font(fontName, fntStyle, fntSize);
			DrawCommand.fonts.put(tempFont.getName(), tempFont);
		}
		tempFont = tempFont.deriveFont(fntStyle, fntSize);		
		
		g.setFont(tempFont);
		Color tempColor = g.getColor();
		g.setColor(txtColor);
		
		if(angle!=0.0)
		{
			//This is a hack
			lowerX = (lowerX == 0.0)? state.pos.x : lowerX;
			lowerY = (lowerY == 0.0)? /*state.yExtent - */state.pos.y : lowerY;
			//End hack
			
			int x=  (int)lowerX; // + 7; //- 3;
			int y = (int)(state.yExtent - lowerY) ;

			//translate to the correct location (yExtent is the height of the page)
			g.translate(x, y);
			g.rotate(Math.toRadians(angle));
			g.drawString(text, 0.0f, 0.0f);
			//g.drawString(text, 0, 0);	
			g.rotate(Math.toRadians(-angle));
			g.translate(-x, -y);
		}
		else
		{
			g.drawString(text, (float) state.pos.x, (float)((double)state.yExtent - state.pos.y ) );
		}
		g.setColor(tempColor);		
	}
	@Override
	public void execute(Graphics2D g, DrawState state, int width, int height, double zoom, Point multiChartOffset) {
		//Font font = state.font == null ? new Font("Times New Roman", Font.PLAIN, 10) : state.font;
		//Rectangle2D theString = font.getStringBounds(text, new FontRenderContext(new AffineTransform(), false, false));
		//double textHeight = theString.getHeight();
		//double textLength = theString.getWidth();
		//Point curPos = ChartConversion.convertToScreenCoord(new Point((int)state.pos.x,(int)(state.yExtent - (state.pos.y + textHeight))), zoom, state);
		//Rectangle objectBox = new Rectangle(curPos.x, curPos.y, (int)(ChartConversion.convertToScreenSize(textLength, zoom)), (int)(ChartConversion.convertToScreenSize(textHeight, zoom)));

		//this has a bug.. in printing it doesn't allow any text to be seen... grr
		//if (viewable(getScreenArray(width, height), objectBox))
			//execute(g, state);
		
		//TEST code from executeAbsolute
		Font tempFont = DrawCommand.fonts.get(fontName);
		if (tempFont == null)
		{
			for(OpgFont font: OpgFont.values()){
				Font testFont = font.font;
				if (testFont.getName().equals(fontName))
				{
					tempFont = testFont;
					break;
				}
			}
			if (tempFont == null)
				tempFont = new Font(fontName, fntStyle, fntSize);
			DrawCommand.fonts.put(tempFont.getName(), tempFont);
		}
		tempFont = tempFont.deriveFont(fntStyle, fntSize);
		
		
		g.setFont(tempFont);
		Color tempColor = g.getColor();
		g.setColor(txtColor);
		
		if(angle!=0.0)
		{
			//This is a hack
			lowerX = (lowerX == 0.0)? state.pos.x : lowerX;
			lowerY = (lowerY == 0.0)? /*state.yExtent - */state.pos.y : lowerY;
			//End hack
			
			int x=  (int)lowerX; // + 7; //- 3;
			int y = (int)(state.yExtent - lowerY) ;

			//translate to the correct location (yExtent is the height of the page)
			g.translate(x, y);
			g.rotate(Math.toRadians(angle));
			g.drawString(text, 0.0f, 0.0f);
			//g.drawString(text, 0, 0);	
			g.rotate(Math.toRadians(-angle));
			g.translate(-x, -y);
		}
		else
		{
			g.drawString(text, (float) state.pos.x, (float)((double)state.yExtent - state.pos.y ) );
		}
		g.setColor(tempColor);
		
	}
	
	@Override
	public Rectangle getShapeBox() {
		Font tempFont = DrawCommand.fonts.get(fontName).deriveFont(fntStyle, fntSize);
		Rectangle2D textSize = tempFont.getStringBounds(text, NameAbbreviator.frc);
		return new Rectangle(coords.x,coords.y,(int)textSize.getWidth(),(int)textSize.getHeight());
	}
	
	@Override
	public void executeAbsolute(Graphics2D g, DrawState state, int width, int height, double zoom) {
		//Font tempFont = DrawCommand.fonts.get(fontName).deriveFont(fntStyle, fntSize);
		
		Font tempFont = DrawCommand.fonts.get(fontName);
		if (tempFont == null)
		{
			for(OpgFont font: OpgFont.values()){
				Font testFont = font.font;
				if (testFont.getName().equals(fontName))
				{
					tempFont = testFont;
					break;
				}
			}
			if (tempFont == null)
				tempFont = new Font(fontName, fntStyle, fntSize);
			DrawCommand.fonts.put(tempFont.getName(), tempFont);
		}
		tempFont = tempFont.deriveFont(fntStyle, fntSize);
		
		
		g.setFont(tempFont);
		Color tempColor = g.getColor();
		g.setColor(txtColor);
		if(angle!=0.0)
		{
			int x= (int)lowerX; // + 7; //- 3;
			int y = (int)(state.yExtent - lowerY) ; 

			//translate to the correct location (yExtent is the height of the page)
			g.translate(x, y);
			g.rotate(Math.toRadians(angle));
			g.drawString(text, 0, 0);
			g.rotate(Math.toRadians(-angle));
			g.translate(-x, -y);
		}
		else
		{
			g.drawString(text, (int) coords.x, (int)(state.yExtent - coords.y ) );
		}
		g.setColor(tempColor);
	}

}
