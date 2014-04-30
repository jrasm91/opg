package edu.byu.cs.roots.opg.model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Page {
	/**
	 * Paper width object which holds the width of the different rolls in points
	 */
	protected PaperWidth paperWidth;
	
	/**
	 * Current length of the paper (in points)
	 */
	protected double paperLength;
	/**
	 * Is the paper in landscape mode or portrait,
	 * true = in landscape mode
	 * false = in portrait
	 */
	protected boolean landscape;
	
	/**
	 * Flag variable to tell if the options of have been changed by the user
	 */
	protected boolean changed;
	
	
	private ArrayList<Scrap> scraps;
	
	
	public Page(){
		paperWidth = PaperWidth.values()[0];
		paperLength = 1500;
		scraps = new ArrayList<Scrap>();
	}
	
	public void paint(Graphics g){
		g.setColor(Color.white);
		if(landscape){
			g.fillRect(0, 0, (int) paperLength, (int) paperWidth.width );
			
		}else{
			g.fillRect(0, 0,(int) paperWidth.width, (int) paperLength );
			
		}
		for(Scrap scrap:scraps){
			scrap.paint(g);
		}
		
		
	}

	/**
	 * @return the landscape
	 */
	public boolean isLandscape() {
		return landscape;
	}

	/**
	 * @param landscape the landscape to set
	 */
	public void setLandscape(boolean landscape) {
		changed = true;
		this.landscape = landscape;
	}

	/**
	 * @return the paperLength
	 */
	public double getPaperLength() {
		return paperLength;
	}

	/**
	 * @param paperLength the paperLength to set
	 */
	public void setPaperLength(double paperLength) {
		changed = true;
		this.paperLength = paperLength;
	}

	/**
	 * @return the paperWidth
	 */
	public PaperWidth getPaperWidth() {
		return paperWidth;
	}

	/**
	 * @param paperWidth the paperWidth to set
	 */
	public void setPaperWidth(PaperWidth paperWidth) {
		changed = true;
		this.paperWidth = paperWidth;
	}
	
	
	
	
}
