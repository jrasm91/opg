package edu.byu.cs.roots.opg.chart.presetvertical;

import edu.byu.cs.roots.opg.model.Individual;

/**
 * Fake child that is used to connect two parents when they are at the root of a tree.
 *
 */
public class FakeAncesBox extends AncesBox {
	
	
	public FakeAncesBox(Individual indi) {
		super(indi);
	}
	
	protected void drawSubTree(ChartMargins chart, VerticalChartOptions options, double x, double y)
	{
		//Draw parents with same he horizontal position as the fake box
		if (options.getAncesGens() > gen.getGenNum())
		{
			if (father != null)
			{
				AncesBox f = (AncesBox) father;
				f.drawSubTree(chart, options, x, y + fatherVOffset);

			}
			if (mother != null)
			{
				AncesBox m = (AncesBox) mother;
				m.drawSubTree(chart, options, x, y + motherVOffset);
			}
		}
		
	}
	
	protected void drawBox (ChartMargins chart, double fontSize, VerticalChartOptions options, double x, double y)
	{
		//Fake box is not drawn
	}


}
