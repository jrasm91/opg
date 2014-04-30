package edu.byu.cs.roots.opg.color;

import java.awt.Color;

public class SingleToneColorScheme extends ColorScheme {

	private static final long serialVersionUID = 2612455302598507569L;
	private Color color;
	
	
	public SingleToneColorScheme(){
		coloroptions.add("Color");
		color = Color.white;
	}
	
	@Override
	public Color getColor(String id) {
		return color;
	}

	@Override
	public String toString() {
		return "Single Color";
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	
	
	
	
}
