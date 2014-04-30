package edu.byu.cs.roots.opg.chart;

import java.io.Serializable;

public class ChartMarginData implements Serializable
{

	private static final long serialVersionUID = 1L;
	private double left, right, top, bottom;
	
	public ChartMarginData(){
		left = 0.0;
		right = 0.0;
		top = 0.0;
		bottom = 0.0;
	}
	public ChartMarginData(double left, double right, double top, double bottom){
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom;
	}
	
	public double getLeft(){
		return left;
	}
	public double getRight(){
		return right;
	}
	public double getTop(){
		return top;
	}
	public double getBottom(){
		return bottom;
	}
	
	public void setLeft(double left){
		this.left = left;
	}
	public void setRight(double right){
		this.right = right;
	}
	public void setTop(double top){
		this.top = top;
	}
	public void setBottom(double bottom){
		this.bottom = bottom;
	}

}
