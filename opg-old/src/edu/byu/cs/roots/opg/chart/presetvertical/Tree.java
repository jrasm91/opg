package edu.byu.cs.roots.opg.chart.presetvertical;

public class Tree {
	
	private AncesTree ancestry;
	private DescTree descendancy;
	
	public Tree(AncesTree a, DescTree d) {
		ancestry = a;
		descendancy = d;
	}

	public void setAncestry(AncesTree ancestry) {
		this.ancestry = ancestry;
	}

	public AncesTree getAncestry() {
		return ancestry;
	}

	public void setDecendency(DescTree descendency) {
		this.descendancy = descendency;
	}

	public DescTree getDescendency() {
		return descendancy;
	}
	
	public void drawTree(ChartMargins m, double width, double height) {
		double ancesRootYPos = 0;
		double descRootYPos = 0;
		double rootXPos = 0;
		if (descendancy != null)
			rootXPos = descendancy.getWidth(m.getOptions().getDescGens()) - descendancy.root.getWidth();
		//System.out.println("xlen:"+rootXPos);
		//draw boxes on chart
		//ancesBox.drawAncesRootTree(chartMargins, ops, ancesGenPositions, 0, rootYPos);
		//descBox.drawDescRootTree(chartMargins, ops);
		if (ancestry != null)
		{
			ancesRootYPos = (height - ancestry.getRootOffset())/2 ;
			ancestry.DrawTree(m, m.getOptions(), rootXPos, ancesRootYPos);
		}
		
		if (descendancy!= null)
		{
			descRootYPos = (height- descendancy.getRootOffset())/2 ;
			descendancy.drawTree(m, m.getOptions(), rootXPos, descRootYPos);
		}
		
	}
}
	
	

