package edu.byu.cs.roots.opg.chart.working;

import edu.byu.cs.roots.opg.chart.ChartOptions;

public class WorkingVerticalFixedChartOptions extends ChartOptions 
{
	
	private static final long serialVersionUID = 3381391387177524104L;
	boolean drawTitles=true;
	
//----------------------CONSTRUCTORS-------------------------	
	WorkingVerticalFixedChartOptions(ChartOptions options)
	{
		super(options);
	}
		
//----------------------METHODS-------------------------	
	public boolean isDrawTitles() {
		return drawTitles;
	}

	public void setDrawTitles(boolean drawTitles) 
	{
		changed(1);
		this.drawTitles = drawTitles;
	}

}
