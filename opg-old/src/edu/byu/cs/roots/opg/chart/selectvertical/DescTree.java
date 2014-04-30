package edu.byu.cs.roots.opg.chart.selectvertical;

import java.util.ArrayList;

import edu.byu.cs.roots.opg.model.Individual;
import edu.byu.cs.roots.opg.model.OpgSession;

public class DescTree {

	public DescBox descBox = null;
	public ArrayList<ArrayList<DescBox>> descGenPositions;
	public int[] maxSpouseLineOffset;

	public DescTree(Individual root)
	{
		descBox = new DescBox(root);
		descGenPositions = new ArrayList<ArrayList<DescBox>>();
		descBox.gen = 0;
		descGenPositions.add(new ArrayList<DescBox>());
		//descGenPositions.get(0).add(descBox);
		descBox.numInGen = 0;
		descBox.buildBoxTree(descGenPositions, 0);
	}
	
	public void calcCoords(VerticalChartOptions ops)
	{
		maxSpouseLineOffset = new int[ops.getDescGens()+1];
		descBox.calcCoords(ops, 0, maxSpouseLineOffset);
	}
	
	public void DrawTree(OpgSession session, ChartMargins chart, VerticalChartOptions options, double x, double y)
	{
		//descBox.drawDescRootTree(chart, options, descGenPositions, x, y);
		descBox.drawDescRootTree(session, chart, options, descGenPositions, x, y);
	}
	
	

	
	
}
