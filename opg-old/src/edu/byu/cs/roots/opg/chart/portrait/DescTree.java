package edu.byu.cs.roots.opg.chart.portrait;

import java.util.ArrayList;

import edu.byu.cs.roots.opg.chart.preset.templates.ChartMargins;
import edu.byu.cs.roots.opg.chart.preset.templates.DescBoxParent;
import edu.byu.cs.roots.opg.model.Individual;
import edu.byu.cs.roots.opg.model.OpgSession;

public class DescTree {

	public DescBox descBox = null;
	public ArrayList<ArrayList<DescBoxParent>> descGenPositions;
	public int[] maxSpouseLineOffset;

	public DescTree(Individual root, OpgSession session)
	{
		descBox = new DescBox(root);
		descGenPositions = new ArrayList<ArrayList<DescBoxParent>>();
		descBox.gen = 0;
		descGenPositions.add(new ArrayList<DescBoxParent>());
		//descGenPositions.get(0).add(descBox);
		descBox.numInGen = 0;
		descBox.buildBoxTree(descGenPositions, 0, session);
	}
	
	public void calcCoords(PortraitChartOptions ops)
	{
		maxSpouseLineOffset = new int[ops.getDescGens()+1];
		descBox.calcCoords(ops, 0, maxSpouseLineOffset);
	}
	
	public void DrawTree(ChartMargins chart, PortraitChartOptions options, double x, double y, OpgSession session)
	{
		//descBox.drawDescRootTree(chart, options, descGenPositions, x, y);
		descBox.drawDescRootTree(chart, options, descGenPositions, x, y, session);
	}
	
	

	
	
}
