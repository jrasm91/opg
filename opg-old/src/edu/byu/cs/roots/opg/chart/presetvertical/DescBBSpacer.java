package edu.byu.cs.roots.opg.chart.presetvertical;

import java.util.ArrayList;
import java.util.List;
/**
 * Descendency bounding box spacer. Set the childrenOffsets for DescBoxes
 */
public class DescBBSpacer {

	private final double DEFAULT_VOFFSET = 0;
	private List<Double> childrenOffsets;

	public DescBBSpacer() {
		return;
	}

	public void space(DescTree tree, int maxGen) {
		childrenOffsets = new ArrayList<Double>();
		calcCoords(tree.root, Math.abs(maxGen),
				Math.abs(tree.root.gen.getGenNum()));
	}

	/**
	 * Recursively calculates the minimum coordinates for this individual (and
	 * its subtree(s)) based on the minimum font size
	 * 
	 * @param options
	 *            - chart options that contain the minimum font size
	 * @param curGen
	 *            - current generation of Individual in this DescBox (usually
	 *            starts as 0)
	 * @param duplicateMap
	 */
	private void calcCoords(DescBox b, int maxGen, int curGen) {
		if (curGen > maxGen)
			return;

		// calculate the dimensions for this box
		b.upperBounds = new ArrayList<Double>(maxGen - curGen);
		b.lowerBounds = new ArrayList<Double>(maxGen - curGen);

		// calculate first element of upper bounds array for tight fit
		double halfHieght = b.getHeight() / 2.0;
		//b.upperBounds.add(halfHieght);
		//b.lowerBounds.add(-halfHieght);
		b.upperSubTreeOffset = halfHieght;
		b.lowerSubTreeOffset = -halfHieght;
		b.vOffsets.clear();

		if (curGen < maxGen) {
			for (DescBox child : b.children) {
				calcCoords(child, maxGen, curGen + 1);
			}

			// use default offsets
			if (b.children.size() < 2) {
				b.vOffsets.add(DEFAULT_VOFFSET);
			}
			// calculate distance between children for offsets
			else {
				for (int i = 0; i < b.children.size() - 1; i++)
					childrenOffsets.add(calcIntersectDist(b.children.get(i),
							b.children.get(i + 1)));
				addMarriageBoxPadding(b);
				double total = 0;
				double half = getListSum(childrenOffsets) / 2;
				b.vOffsets.add(half);
				for (Double d : childrenOffsets) {
					total += d;
					b.vOffsets.add(half - total);
				}
			}
			updateSubTreeOffsets(b);
		}
	}
	
	private void addMarriageBoxPadding(DescBox b) {
		
	}

	private void updateSubTreeOffsets(DescBox b) {
		double usth = 0;
		double lsth = 0;
		if (!b.children.isEmpty()) {
			usth = b.vOffsets.get(0) + b.children.get(0).upperSubTreeOffset;
			lsth = b.vOffsets.get(b.vOffsets.size() - 1)
					+ b.children.get(b.children.size() - 1).lowerSubTreeOffset;

			b.upperSubTreeOffset = Math.max(usth, b.upperSubTreeOffset);
			b.lowerSubTreeOffset = Math.min(lsth, b.lowerSubTreeOffset);
		}
	}

	private double getListSum(List<Double> list) {
		double result = 0;
		for (Double d : list) {
			result += d;
		}
		return result;
	}

	/**
	 * Finds difference between bounding boxes
	 * 
	 * @param child1
	 *            - the individual on top
	 * @param child2
	 *            - the individual on bottom
	 */
	public double calcIntersectDist(Box child1, Box child2) {
		return child2.upperSubTreeOffset - child1.lowerSubTreeOffset
				+ child1.getVerticalSpace();
	}
}
