package edu.byu.cs.roots.opg.gui;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;

class PreviewCanvas extends Canvas {

	/** Serilzed version */
	private static final long serialVersionUID = 18901280213120L;
	
	private Image displayImage;
	
	public void setImage(Image newImage) {
		displayImage = newImage;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(displayImage, 0, 0, null);
	}
};
