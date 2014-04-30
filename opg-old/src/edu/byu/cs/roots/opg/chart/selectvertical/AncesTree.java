package edu.byu.cs.roots.opg.chart.selectvertical;

import java.util.ArrayList;

import edu.byu.cs.roots.opg.model.Individual;

public class AncesTree {

	public AncesBox ancesBox = null;
	public ArrayList<ArrayList<AncesBox>> ancesGenPositions;

	public AncesTree(Individual root)
	{
		ancesBox = new AncesBox(root);
		ancesGenPositions = new ArrayList<ArrayList<AncesBox>>();
		ancesBox.gen = 0;
		ancesGenPositions.add(new ArrayList<AncesBox>());
		ancesGenPositions.get(0).add(ancesBox);
		ancesBox.numInGen = 0;
		AncesBox.duplicateMap.put(ancesBox.indi.id,0); //add root to duplicate map
		ancesBox.buildBoxTree(ancesGenPositions, 1);
	}
	
	public void DrawTree(ChartMargins chart, VerticalChartOptions options, double x, double y)
	{
		ancesBox.drawAncesRootTree(chart, options, ancesGenPositions, x, y);
	}
	
	
	public void setDuplicateNumbers()
	{
		for(int i=0; i < ancesGenPositions.size(); i++)
		{
			for(AncesBox ab : ancesGenPositions.get(i))
				ab.setDuplicateIndex();
		}
	}
	
	public int getLargestGen()
	{
		double max = 0;
		int index = 0;
		for(int i=0; i < ancesGenPositions.size(); i++)
		{
			double temp = ancesGenPositions.get(i).size();
			if(max < temp )
			{
				max = temp;
				index = i;
			}
			
		}
		return index;
	}
	
	public int getGenAtMaxHieght(double maxheight, BoxFormat bf)
	{
		double temp;
		double bh = bf.getMinHeight();
		
		int i;
		for(i=0; i < ancesGenPositions.size(); i++)
		{
			temp = ancesGenPositions.get(i).size()*bh;
			if(maxheight < temp )
			{
				i--;
				break;
			}
		}
		return i;
	}
	
	public double getLargestGenHeight(BoxFormat bf)
	{
		int l = getLargestGen();
		double mh = bf.getMinHeight();
		return ancesGenPositions.get(l).size()*mh;
	}
	
	public int geGenerationSize(int gen)
	{
		return ancesGenPositions.get(gen).size();
	}
	
	public double getHeight()
	{
		return ancesBox.upperSubTreeHeight - ancesBox.lowerSubTreeHeight;
	}
	

}
