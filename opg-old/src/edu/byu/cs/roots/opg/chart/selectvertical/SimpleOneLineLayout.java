package edu.byu.cs.roots.opg.chart.selectvertical;

import edu.byu.cs.roots.opg.chart.selectvertical.LineItem.LineItemType;
import edu.byu.cs.roots.opg.model.Individual;


public class SimpleOneLineLayout extends BoxLayout{
	
	public SimpleOneLineLayout(){
		LineLayout simpleNameLine = new LineLayout();
		simpleNameLine.items.add(new LineItem(LineItemType.ABBREVIATED_NAME));
		simpleNameLine.items.add(new LineItem(LineItemType.DUPLICATE_LABEL));
		lines.add(simpleNameLine);
	}
	
	public boolean canFit(Individual indi, double width, double height, VerticalChartOptions ops, double fontSize, String dupLabel ){
		return true;
	}
}
