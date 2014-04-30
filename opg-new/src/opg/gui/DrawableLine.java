package opg.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class DrawableLine implements IDrawable {

	private Point start, end;
	private Color color;
	
	public DrawableLine(Point start, Point end){
		this(start, end, Color.BLACK);
	}
	
	public DrawableLine(Point start, Point end, Color color){
		this.start = start;
		this.end = end;
		this.color = color;
	}
	
	@Override
	public void draw(Graphics2D g) {
		Color oldColor = g.getColor();
		g.setColor(this.color);
		g.drawLine(this.start.x, this.start.y, this.end.x, this.end.y);
		g.setColor(oldColor);
	}

}
