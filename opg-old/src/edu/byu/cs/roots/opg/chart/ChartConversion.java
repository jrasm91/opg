package edu.byu.cs.roots.opg.chart;

import java.awt.Point;

import edu.byu.cs.roots.opg.chart.cmds.DrawState;

public class ChartConversion {
	
	public static Point convertToScreenCoord(Point chartCoord, double zoom, DrawState state) {
		return new Point((int)convertDoubleToScreenDouble(chartCoord.x, zoom, state.chartLeftToDisplay), (int)convertDoubleToScreenDouble(chartCoord.y, zoom, state.chartTopToDisplay));
	}
	
	public static Point convertToChartCoord(Point screenCoord, double zoom, DrawState state) {
		return new Point((int)convertDoubleToChartDouble(screenCoord.x, zoom, state.chartLeftToDisplay), (int)convertDoubleToChartDouble(screenCoord.y, zoom, state.chartTopToDisplay));
	}
	
	public static double convertToScreenSize(double input, double zoom) {
		return input * zoom;
	}
	
	public static double convertToChartSize(double input, double zoom) {
		return input / zoom;
	}
	
	public static double convertDoubleToChartDouble(double input, double zoom, double offset)
	{
		return ((input - offset) / zoom);
	}
	
	public static double convertDoubleToScreenDouble(double input, double zoom, double offset)
	{
		return ((input * zoom) + offset);
	}
	
	public static Point convertMousePixelToChartPoint(Point mouse, double zoom, double yOffset, double xOffset)
	{
		int mX = mouse.x;
		int mY = mouse.y;
		return new Point((int)((mX - xOffset) / zoom), (int)((mY - yOffset) / zoom)); 
	}
}
