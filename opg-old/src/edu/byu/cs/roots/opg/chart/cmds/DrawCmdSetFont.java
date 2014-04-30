package edu.byu.cs.roots.opg.chart.cmds;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.HashMap;

public class DrawCmdSetFont extends DrawCommand implements Serializable {
	static final long serialVersionUID = 1000L;

	private Font currentFont;
	private Color currentColor;

	public DrawCmdSetFont(Font font, Color color){
		currentFont = font;
		currentColor = color;
		DrawCommand.curFontStyle = font.getStyle();
		DrawCommand.curFontSize = font.getSize();
		DrawCommand.curFontName = font.getName();
		if (!DrawCommand.fonts.containsKey(font.getName()))
			DrawCommand.fonts.put(font.getName(), font);
		DrawCommand.curColor = color;
	}

	public DrawCmdSetFont(String typeface, int size, Color color, int style)
	{
		DrawCommand.curFontStyle = style;
		DrawCommand.curFontSize = size;
		Font tmp = new Font(typeface, style, size);
		DrawCommand.curFontName = tmp.getName();
		if (DrawCommand.fonts == null)
			DrawCommand.fonts = new HashMap<String, Font>();
		if (!DrawCommand.fonts.containsKey(tmp.getName()))
			DrawCommand.fonts.put(tmp.getName(), tmp);
		DrawCommand.curColor = color;
	}

	public void execute(Graphics2D g, DrawState state) {		
		g.setColor(currentColor);
		state.curColor = currentColor;
		g.setFont(currentFont);	
		state.font = currentFont;
	}
	@Override
	public void execute(Graphics2D g, DrawState state, int width, int height, double zoom, Point multiChartOffset) {
		execute(g, state);
	}

	// Font Change is no size so null means that it MUST be displayed.
	@Override
	public Rectangle getShapeBox() {
		return null;
	}

	@Override
	public void executeAbsolute(Graphics2D g, DrawState state, int width, int height, double zoom) {
		return;
	}

	public int getSize() {
		return DrawCommand.curFontSize;
	}

	public Color getColor() {
		return DrawCommand.curColor;
	}

	public String getTypeface() {
		return DrawCommand.curFontName;
	}

}
