package edu.byu.cs.roots.opg.util;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;

public class FontMaximizer {

	
	
	public static float getMaxFontSize(Graphics g, Font font, String string, float space){
		AffineTransform at = AffineTransform.getRotateInstance(0);
		font = font.deriveFont(at);
		FontMetrics fm = g.getFontMetrics(font);
		float size = font.getSize2D();
		//float width = fm.stringWidth(string);		
		float width = (float) fm.getStringBounds(string, g).getWidth();
		font = font.deriveFont(size*.98f*space/width);
		fm = g.getFontMetrics(font);
//		System.out.println(string + " space: " + space+ " width: "+ width + " font: " + size + "->" + font.getSize2D() + " new size: " + fm.stringWidth(string));
		return font.getSize2D();	
	}
	
	
}
