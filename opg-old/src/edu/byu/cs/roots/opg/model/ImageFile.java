package edu.byu.cs.roots.opg.model;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public class ImageFile implements Serializable{

	private static final long serialVersionUID = 1L;
	private BufferedImage image;
	public double x;
	public double y;
	public double width;
	public double height;
	
	public ImageFile(double x, double y, BufferedImage image){
		this.x = x;
		this.y = y;
		this.image = image;
		
		width = image.getWidth();
		height = image.getHeight();
	}
	
	public BufferedImage getImage(){
		return image;
	}
	
	
}
