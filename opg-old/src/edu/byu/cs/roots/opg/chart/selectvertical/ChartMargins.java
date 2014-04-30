package edu.byu.cs.roots.opg.chart.selectvertical;

import edu.byu.cs.roots.opg.chart.ChartDrawInfo;
import edu.byu.cs.roots.opg.chart.cmds.DrawCommand;

public class ChartMargins {

	private ChartDrawInfo chart;
	double xOrig;
	double yOrig;
	double width;
	double height;
	
	public ChartMargins(ChartDrawInfo chart, double x, double y, double width, double height)
	{
		this.chart = chart;
		xOrig = x;
		yOrig = y;
		this.width = width;
		this.height = height;
	}
	
	public ChartMargins(ChartDrawInfo chart, double uniformMarginSize)
	{
		this.chart = chart;
		xOrig = uniformMarginSize;
		yOrig = uniformMarginSize;
		width = chart.getXExtent() - (uniformMarginSize*2);
		height = chart.getYExtent() - (uniformMarginSize*2);
	}
	
	public ChartMargins(ChartDrawInfo chart, double uniformMarginSize, double titleHeight)
	{
		this.chart = chart;
		xOrig = uniformMarginSize;
		yOrig = uniformMarginSize;
		width = chart.getXExtent() - (uniformMarginSize*2);
		height = chart.getYExtent() - (uniformMarginSize*2) - titleHeight;
	}
	
	public ChartDrawInfo getChart(){
		return chart;
	}
	
	public void addDrawCommand(DrawCommand cmd)
	{
		//relies on draw command to offset by correct amount
		chart.addDrawCommand(cmd);
	}
	
	public double getXExtent(){
		return width;
	}
	
	public double getYExtent(){
		return height;
	}
	
	public double xOffset(double x){
		return x + xOrig;
	}
	
	public double yOffset(double y){
		return y + yOrig;
	}
	
}
