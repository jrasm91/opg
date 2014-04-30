package edu.byu.cs.roots.opg.chart.presetvertical;

import edu.byu.cs.roots.opg.model.Individual;

public class RootBox extends Box {
	
	private DescBox desc;
	private AncesBox ances;
	
	public RootBox() {
		super(new Individual());
		setDesc(new DescBox(indi));
		setAnces(new AncesBox(indi));
	}
	
	protected void drawSubTree(ChartMargins chart, VerticalChartOptions options, double x, double y)
	{
		//Draw parents with same he horizontal position as the fake box
		if (options.getAncesGens() > gen.getGenNum())
		{
			if (ances.father != null)
			{
				AncesBox f = (AncesBox) ances.father;
				f.drawSubTree(chart, options, x, y + ances.fatherVOffset);

			}
			if (ances.mother != null)
			{
				AncesBox m = (AncesBox) ances.mother;
				m.drawSubTree(chart, options, x, y + ances.motherVOffset);
			}
		}
		
		if (options.getDescGens() > Math.abs(gen.getGenNum()))
		{
			for(int i=0; i < desc.children.size(); i++) {
				desc.drawChildConnector(chart,x,y,desc.vOffsets.get(i));
				desc.children.get(i).drawSubTree(chart, options, x - desc.getWidth()-desc.genGap(), y + desc.vOffsets.get(i));
			}
		}
		
	}
	
	protected void drawBox (ChartMargins chart, double fontSize, VerticalChartOptions options, double x, double y)
	{
		//Fake box is not drawn
	}

	public void setDesc(DescBox desc) {
		this.desc = desc;
	}

	public DescBox getDesc() {
		return desc;
	}

	public void setAnces(AncesBox ances) {
		this.ances = ances;
	}

	public AncesBox getAnces() {
		return ances;
	}
	

}
