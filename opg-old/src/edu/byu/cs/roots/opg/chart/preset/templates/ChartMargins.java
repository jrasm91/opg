package edu.byu.cs.roots.opg.chart.preset.templates;

import java.io.Serializable;

import edu.byu.cs.roots.opg.chart.ChartDrawInfo;
import edu.byu.cs.roots.opg.chart.ChartMarginData;
import edu.byu.cs.roots.opg.chart.cmds.DrawCommand;

public class ChartMargins implements Serializable{

	private static final long serialVersionUID = 1L;
	private ChartDrawInfo chart;
	double xOrig;
	double yOrig;
	double width;
	double height;
	double headerSize;
	
	//currently unused
	public ChartMargins(ChartDrawInfo chart, double x, double y, double width, double height)
	{
		this.chart = chart;
		xOrig = x;
		yOrig = y;
		this.width = width;
		this.height = height;
	}
	
	public ChartMargins(ChartDrawInfo chart, ChartMarginData margin)
	{
		this.chart = chart;
		xOrig = margin.getLeft();
		yOrig = margin.getTop();
		width = chart.getXExtent() - (margin.getLeft() + margin.getRight());
		height = chart.getYExtent() - (margin.getTop() + margin.getBottom());
	}
	
	public ChartMargins(ChartDrawInfo chart, ChartMarginData margin, double titleHeight)
	{
		this.chart = chart;
		xOrig = margin.getLeft();
		yOrig = margin.getTop();
		width = chart.getXExtent() - (margin.getLeft() + margin.getRight());
		height = chart.getYExtent() - (margin.getTop() + margin.getBottom()) - titleHeight;
		headerSize = titleHeight;
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
	/**
	 * Gets the amount of space taken up by the Title and Generational labels
	 * @return Double
	 */
	public double getHeaderSize()
	{
		return headerSize;
	}
	
	
}
