package opg.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.LinkedList;
import java.util.List;

import opg.main.Const;

public class DrawablePerson implements IDrawable{

	private Point p;
	private Dimension d;
	private List<String> text;
	private Color color;

	public DrawablePerson(Point p, Dimension d, List<String> text, String id){
		this(p, d, text, id, Color.WHITE);
	}

	public DrawablePerson(Point p, Dimension d, List<String> text, String id, Color color){
		this.p = p;
		this.d = d;
		this.text = text;
		this.color = color;
		if(text == null){
			this.text = new LinkedList<String>();
			this.text.add("N: " + id);
		}
	}

	@Override
	public void draw(Graphics2D g){
		Color oldColor = g.getColor();
		int	boxMargin = Const.MARGIN_BOX_SPACING;
		Point newP = new Point(p.x + boxMargin/2, p.y + boxMargin/2);
		Dimension newD = new Dimension(d.width - boxMargin, d.height - boxMargin);
		Rectangle box = new Rectangle(newP, newD);

		int rounded = d.height / 5;
		g.setColor(this.color);
		g.fillRoundRect(box.x, box.y, box.width, box.height, rounded, rounded);
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(Const.STROKE_SIZE/2));
		g.drawRoundRect(box.x, box.y, box.width, box.height, rounded, rounded);

		int textHeight = g.getFontMetrics().getHeight();
		int textMargin = 2;
		// to vertically center text
		int rowHeight = (Math.min(newD.height, newD.width) - textMargin * 2)/Math.max(text.size(), 1);
		int rowOffset = (rowHeight - textHeight)/2;

		int xStart = newP.x + textMargin;
		int yStart = newP.y;

		AffineTransform saveAt = g.getTransform();
		
		for(int i = 0; i < text.size(); i++){
			int rotated = newD.width < newD.height? newD.height: 0;
			if(newD.width < newD.height){
				g.rotate(-Math.PI/2, p.x, p.y);
			}
			g.drawString(text.get(i), xStart - rotated, yStart + (rowHeight * (i + 1)) - rowOffset);
			g.setTransform(saveAt);
		}

		g.setColor(oldColor);
	}
}
