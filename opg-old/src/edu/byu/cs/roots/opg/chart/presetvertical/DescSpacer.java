package edu.byu.cs.roots.opg.chart.presetvertical;

import java.util.ArrayList;
import java.util.List;
/**
 * Jigsaw spacer for descendents. 
 *
 */
public class DescSpacer {
	
	//private DescTree tree;
	private final double DEFAULT_VOFFSET = 0;
	
	public DescSpacer() {
		return;
	}
	
	public void space(DescTree tree, int maxGen) {
	//	this.tree = tree;
		calcCoords(tree.root,Math.abs(maxGen),Math.abs(tree.root.gen.getGenNum()));
	}
	
	/**
	 * Recursively calculates the minimum coordinates for this individual
	 * (and its subtree(s)) based on the minimum font size
	 *
	 * @param options - chart options that contain the minimum font size
	 * @param curGen - current generation of Individual in this DescBox (usually starts as 0)
	 */
	private void calcCoords(DescBox b, int maxGen, int curGen)
	{
		if (curGen > maxGen)
			return;
		
	
		//calculate the dimensions for this box
		b.upperBounds = new ArrayList<Double>(maxGen - curGen);
		b.lowerBounds = new ArrayList<Double>(maxGen - curGen);

		//calculate first element of upper bounds array for tight fit
		double halfHieght = b.getHeight()/2.0;
		b.upperBounds.add(halfHieght);
		b.lowerBounds.add(-halfHieght);
		b.upperSubTreeOffset = halfHieght;
		b.lowerSubTreeOffset = -halfHieght;
		b.vOffsets.clear();
	
		
		if(curGen < maxGen)
		{
			for(DescBox child : b.children) {
				calcCoords(child,maxGen,curGen+1);
			}
			
			//use default offsets
			if(b.children.size() < 2) {
				b.vOffsets.add(DEFAULT_VOFFSET);	
			}
			//calculate distance between children for offsets
			else
			{
				List<Double> childrenOffsets = new ArrayList<Double>();
				for(int i=0; i < b.children.size()-1; i++) 
					childrenOffsets.add(calcIntersectDist( b.children.get(i), b.children.get(i+1)));
				
				double total = 0;
				double half = getListSum(childrenOffsets)/2;
				b.vOffsets.add(half);
				for(Double d : childrenOffsets) {
					total += d;
					b.vOffsets.add(half-total);
				}		
			}
			populateBounds(b);
			updateSubTreeOffsets(b);
		}
	}
	
	private void updateSubTreeOffsets(DescBox b) {
//		double usth = 0;
//		double lsth = 0;
//		if(!b.children.isEmpty()) {
//			usth = b.vOffsets.get(0) + b.children.get(0).upperSubTreeOffset;
//			lsth = b.vOffsets.get(b.vOffsets.size()-1) + b.children.get(b.children.size()-1).lowerSubTreeOffset;
//			
//			b.upperSubTreeOffset = Math.max(usth, b.upperSubTreeOffset);
//			b.lowerSubTreeOffset = Math.min(lsth, b.lowerSubTreeOffset);
//		}
		double temp = 0;
		for(double bound : b.upperBounds)
			temp = Math.max(temp, bound);
		b.upperSubTreeOffset = temp;
		
		temp = 0;
		for(double bound : b.lowerBounds)
			temp = Math.min(temp, bound);
		
		b.lowerSubTreeOffset = temp;
		
	}
	
	private double getListSum(List<Double> list) {
		double result = 0;
		for(Double d : list) {
			result += d;
		}
		return result;
	}
	
	private void populateBounds(DescBox b) {
		
		//upper
		int j=0;
		for (int i=0; i < b.children.size(); i++) {
			DescBox db = b.children.get(i);
			for(; j < db.upperBounds.size(); j++)
				b.addUpperBound(b.vOffsets.get(i) + db.upperBounds.get(j));
		}
		
		j=0;
		for (int i=b.children.size()-1; i >= 0; i--) {
			DescBox db = b.children.get(i);
			for(; j < db.lowerBounds.size(); j++)
				b.addLowerBound(b.vOffsets.get(i) + db.lowerBounds.get(j));
		}
			
	}
	
	/**
	 * this method finds the closest distance two individuals (with their subtrees) can be while only touchng at
	 * one point (assuming the bounds for all generations are at different distance)
	 * 
	 * @param child1 - the individual on top
	 * @param child2 - the individual on bottom
	 */
	public double calcIntersectDist(Box child1, Box child2)
	{
		double maxDist = 0;
		int maxGen = Math.min(child1.upperBounds.size(),child2.upperBounds.size() );
		int startGen = 0;
		//find the max distance between them (the closest they can be while "intesecting" only at one point)
		for (int i = startGen; i < maxGen; ++i) {
			double curDist = child2.upperBounds.get(i) - child1.lowerBounds.get(i) + child1.getVerticalSpace();
			maxDist = Math.max(curDist, maxDist);
		}
		return maxDist;
	}
}
