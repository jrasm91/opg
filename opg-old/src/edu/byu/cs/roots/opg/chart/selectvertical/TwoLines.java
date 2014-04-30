package edu.byu.cs.roots.opg.chart.selectvertical;

import edu.byu.cs.roots.opg.chart.selectvertical.LineItem.LineItemType;
import edu.byu.cs.roots.opg.model.Individual;

public class TwoLines extends BoxLayout {
	public TwoLines(){
		LineLayout simpleNameLine = new LineLayout();
		simpleNameLine.items.add(new LineItem(LineItemType.ABBREVIATED_NAME));
		simpleNameLine.items.add(new LineItem(LineItemType.DUPLICATE_LABEL));
		lines.add(simpleNameLine);
		LineLayout simpleDateLine = new LineLayout();
		simpleDateLine.items.add(new LineItem(LineItemType.FIXED_STRING,"("));
		simpleDateLine.items.add(new LineItem(LineItemType.ABBREVIATED_BIRTH_DATE));
		simpleDateLine.items.add(new LineItem(LineItemType.FIXED_STRING,"-"));
		simpleDateLine.items.add(new LineItem(LineItemType.ABBREVIATED_DEATH_DATE));
		simpleDateLine.items.add(new LineItem(LineItemType.FIXED_STRING,")"));
		lines.add(simpleDateLine);
	}
	public boolean canFit(Individual indi, double width, double height, VerticalChartOptions ops, double fontSize, String dupLabel ){
		if(indi.birth == null || indi.birth.yearString == null || indi.birth.yearString.compareTo("")==0){
			if(indi.death == null || indi.death.yearString == null || indi.death.yearString.compareTo("")==0)
				return false;
		}
		if(height > 2* fontSize)
			return true;
		return false;
	}
}
