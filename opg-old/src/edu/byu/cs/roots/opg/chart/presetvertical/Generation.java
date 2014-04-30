package edu.byu.cs.roots.opg.chart.presetvertical;

import java.util.ArrayList;
import java.util.List;


public class Generation {
	
	private List<Box> boxes;
	private int genNum;
	private BoxFormat format;
	private double width;
	
	public Generation(int genNum) {
		this.setGenNum(genNum);
		boxes = new ArrayList<Box>();
		format = null;//BoxFormat.getDefault();
	}
	
	public boolean add(Box b) {
		b.setGeneration(this);
		return boxes.add(b);
	}
	
	public boolean remove(Box b) {
		return boxes.remove(b);
	}
	
	public double getHeight() {
		if(boxes.size() == 0)
			return 0;
		boxes.get(0);
		return 0;
	}
	
	public int getOrderIndex(Box b) {
		return boxes.indexOf(b);
	}
	
	public int getCount() {
		return boxes.size();
	}
	
	public List<Box> getBoxes() {
		return boxes;
	}

	public void setGenNum(int genNum) {
		this.genNum = genNum;
	}

	public int getGenNum() {
		return genNum;
	}
	
	public boolean isEmpty() {
		return boxes.isEmpty();
	}
	
	public void setFormat(BoxFormat bf) {
		format = bf;
		for(Box b : boxes) 
			b.setFormat(bf);
	}
	
	public void setWidth(double width) {
		this.width = width;
	}
	
	public double getWidth() {
		return width;
	}
	
	public BoxFormat getFormat() {
		return format;
	}
	
	public String toString() {
		return "Generation " + genNum +" size: " +boxes.size();
	}

}
