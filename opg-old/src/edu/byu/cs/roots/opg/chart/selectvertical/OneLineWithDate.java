package edu.byu.cs.roots.opg.chart.selectvertical;

import edu.byu.cs.roots.opg.chart.selectvertical.LineItem.LineItemType;
import edu.byu.cs.roots.opg.model.Individual;

public class OneLineWithDate extends BoxLayout{
	
	public OneLineWithDate(){
		LineLayout NameLineWithDate = new LineLayout();
		NameLineWithDate.items.add(new LineItem(LineItemType.ABBREVIATED_NAME));
		NameLineWithDate.items.add(new LineItem(LineItemType.FIXED_STRING," ("));
		NameLineWithDate.items.add(new LineItem(LineItemType.BIRTH_DATE_YEAR));
		NameLineWithDate.items.add(new LineItem(LineItemType.FIXED_STRING,"-"));
		NameLineWithDate.items.add(new LineItem(LineItemType.DEATH_DATE_YEAR));
		NameLineWithDate.items.add(new LineItem(LineItemType.FIXED_STRING,")"));
		NameLineWithDate.items.add(new LineItem(LineItemType.DUPLICATE_LABEL));
		lines.add(NameLineWithDate);
	}
	
	public boolean canFit(Individual indi, double width, double height, VerticalChartOptions ops, double fontSize, String dupLabel ){
		if(indi.birth == null || indi.birth.yearString == null || indi.birth.yearString.compareTo("")==0){
			if(indi.death == null || indi.death.yearString == null || indi.death.yearString.compareTo("")==0)
				return false;
		}
		return true;
	}
	
}
