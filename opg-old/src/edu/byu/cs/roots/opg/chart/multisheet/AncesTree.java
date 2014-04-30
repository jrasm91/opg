package edu.byu.cs.roots.opg.chart.multisheet;

import java.io.Serializable;

import edu.byu.cs.roots.opg.chart.preset.templates.ChartMargins;
import edu.byu.cs.roots.opg.model.Individual;
import edu.byu.cs.roots.opg.model.OpgSession;

public class AncesTree implements Serializable{

	private static final long serialVersionUID = 1L;
	public AncesBox ancesBox = null;
	/** A by-the-generation list of AncestorBoxes, including their positions
	 * First parameter is the generation
	 * Second parameter is where they fall in the tree
	 */
	

	public AncesTree(Individual root, OpgSession session)
	{
		ancesBox = new AncesBox(root);
		ancesBox.gen = 0;
		ancesBox.buildBoxTree(1, session);
	}
	
	public void DrawTree(ChartMargins chart, MultisheetChartOptions options, double x, double y, OpgSession session)
	{
		ancesBox.drawAncesRootTree(chart, options, x, y, session);
	}

}
