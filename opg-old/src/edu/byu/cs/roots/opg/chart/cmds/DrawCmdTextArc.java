package edu.byu.cs.roots.opg.chart.cmds;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import edu.byu.cs.roots.opg.util.NameAbbreviator;

public class DrawCmdTextArc extends DrawCommand implements Serializable {
	private String text;
	private double centerX, centerY;
	private double rotAngle;
	private double textDegree;
	private double horizAxis, vertAxis;
	private Point coords;
	private Font txtFont;
	static final long serialVersionUID = 1000L;
	
	public DrawCmdTextArc(String text, double centerX, double centerY, double horizAxis, double vertAxis, double rotAngle, double textDegree)
	{
		this.text = text;
		this.centerX = centerX;
		this.centerY = centerY;
		this.horizAxis = horizAxis;
		this.vertAxis = vertAxis;
		this.rotAngle = rotAngle;
		this.textDegree = textDegree;
		coords = new Point(DrawCommand.curPos);
		txtFont = new Font(DrawCommand.curFontName, DrawCommand.curFontStyle, DrawCommand.curFontSize);
	}
	
	public DrawCmdTextArc(String text, double centerX, double centerY, double radius)
	{
		this.text = text;
		this.centerX = centerX;
		this.centerY = centerY;
		this.horizAxis = radius;
		this.vertAxis = radius;
		this.rotAngle = 0;
		this.textDegree = 0;
		coords = new Point(DrawCommand.curPos);
		txtFont = new Font(DrawCommand.curFontName, DrawCommand.curFontStyle, DrawCommand.curFontSize);
	}
	
	
	public String getText() { return text; }
	public double getCenterX() { return centerX; }
	public double getCenterY() { return centerY; }
	public double getHorizAxis() { return horizAxis; }
	public double getVertAxis() { return vertAxis; }
	public double getTextDegree() { return textDegree; }

	public void execute(Graphics2D g, DrawState state) 
	{
		// Need to use rot angle to transform the graphics for the word
		Graphics2D rotG = (Graphics2D) g.create();
		rotG.rotate(rotAngle);
		rotG.drawString(text, (int) (state.pos.x), (int) (state.pos.y)); //(int) (state.pos.x), (int) (state.pos.y + i*1.1*size));
		
	}
	@Override
	public void execute(Graphics2D g, DrawState state, int width, int height, double zoom, Point multiChartOffset) {
		execute(g, state);
	}

	@Override
	public Rectangle getShapeBox() {
		Rectangle2D textSize = txtFont.getStringBounds(text, NameAbbreviator.frc);
		return new Rectangle(coords.x,coords.y,(int)textSize.getWidth(),(int)textSize.getHeight());
	}
	
	@Override
	public void executeAbsolute(Graphics2D g, DrawState state, int width, int height, double zoom) {
		g.setFont(txtFont);
		g.translate(coords.x, coords.y);
		g.rotate(Math.toRadians(rotAngle));
		g.drawString(text, 0, 0);	
		g.rotate(Math.toRadians(-rotAngle));
		g.translate(-coords.x, -coords.y);
	}

	
}
