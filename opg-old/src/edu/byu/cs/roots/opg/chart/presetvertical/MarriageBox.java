package edu.byu.cs.roots.opg.chart.presetvertical;

import java.awt.Color;
import java.util.ArrayList;

import edu.byu.cs.roots.opg.chart.cmds.DrawCmdFillRect;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdRoundRect;

/**
 * Represents a group of DecsBoxes for multiple marriages.
 *
 */
public class MarriageBox {
	
	private double padding = 3;
	private ArrayList<DescBox> marriages;
	private boolean canDraw;
	private double height;
	
	public MarriageBox() {
		marriages = new ArrayList<DescBox>();
		canDraw = true;
		height = 0;
	}
	
	public void addMarriage(DescBox d) {
		d.mContainer = this;
		marriages.add(d);
	}
	
	public double getPadding() {
		return padding;
	}
	
	public void addOffset(double offset) {
		height += offset;
	}
	
	public void draw(ChartMargins chart, VerticalChartOptions ops, double x, double y) {

		if(canDraw && marriages.size() > 1) {
			DescBox d = marriages.get(0);
			double height = marriages.size() * d.getHeight();
			height += (marriages.size()-1)*d.getVerticalSpace();
			double width = d.getWidth();
			double childY = y + d.getHeight()/2.0 - height/2.0;
			drawMarriageBox(chart,ops,height,width,x,childY);
		}
		canDraw = false;
	}
	
	//TODO in the future, if implementing this chart type, the drawCommands require a Rectangle2D, which will
	//be used to store its' position on the chart.
	private void drawMarriageBox(ChartMargins chart, VerticalChartOptions options, double height, double width, double x, double y) {
		
		double lineWidth = (options.isBoxBorder())? 1 : 0;
		if (options.isRoundedCorners())
			chart.addDrawCommand(new DrawCmdRoundRect(x, y-height/2.0, width ,height, lineWidth, 5, Color.BLACK, Color.GRAY, null));
		else
			chart.addDrawCommand(new DrawCmdFillRect(x, y-height/2.0, width ,height, lineWidth, Color.BLACK, Color.GRAY, null));
	}
	
	public void restDraw() {
		canDraw = true;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getHeight() {
		return height;
	}
}
