package edu.byu.cs.roots.opg.chart.presetvertical;

import java.util.ArrayList;


/**
 * This is a class that is used to space a AncessTree. This if done by setting 
 * the parent vertical offsets for each AncesBox in the tree.
 *
 */
public class AncesSpacer {
	
	private AncesTree tree;
	private final double DEFAULT_VOFFSET = 3;
	public AncesSpacer() {
		return;
	}
	
	/**
	 * Space an AncesTree
	 * @param tree AncesTree to space
	 * @param maxGen max generation to space
	 */
	public void space(AncesTree tree, int maxGen) {
		this.tree = tree;
		calcCoords((AncesBox)tree.root,maxGen,tree.root.gen.getGenNum());
	}
	
	/**
	 * Recursively calculates the minimum coordinates for this individual
	 * (and its subtree(s)) based on the minimum font size
	 *
	 * @param options - chart options that contain the minimum font size
	 * @param curGen - current generation of Individual in this AncesBox (usually starts as 0)
	 * @param duplicateMap 
	 */
	void calcCoords(AncesBox b, int maxGen, int curGen)
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
	
		
		//Tight fit (jigsaw puzzle like) intersection
		//calculate the distances between each of the parents by intersecting their subtrees
		if(curGen < maxGen)
		{
			
			//recursively expand parents
			if (b.father != null)
				calcCoords((AncesBox)b.father,maxGen, curGen+1);
			if (b.mother != null)
				calcCoords((AncesBox)b.mother,maxGen, curGen+1);
			
			double[] dist = findOffsets(b);
			b.setParentOffsets(dist[0], dist[1]);
			populateBounds(b);
			
		}
	}
	
	/**
	 * Calcuates the upper and lower subtree bounds for a given AncesBox.
	 */
	private void populateBounds(AncesBox b) {
		
		//upper
		int i=0;
		if(b.father != null)
		for (; i < b.father.upperBounds.size(); ++i) {
			b.addUpperBound(b.fatherVOffset + b.father.upperBounds.get(i));
		}
		if(b.mother != null)
		for ( ; i < b.mother.upperBounds.size(); ++i) {
			b.addUpperBound(b.motherVOffset + b.mother.upperBounds.get(i));
		}
		
		//lower
		int k=0;
		if(b.mother != null)
		for (; k < b.mother.lowerBounds.size(); ++k) {
			b.addLowerBound(b.motherVOffset + b.mother.lowerBounds.get(k));
		}
		if(b.father != null)
		for ( ; k < b.father.lowerBounds.size(); ++k){
			b.addLowerBound(b.fatherVOffset + b.father.lowerBounds.get(k));
		}
	}
	
	/**
	 * this method finds the closest distance two individuals (with their subtrees) can be while only touchng at
	 * one point (assuming the bounds for all generations are at different distances)
	 * 
	 * @param child box to find offsets for
	 */
//	private double[] findOffsets(AncesBox child)
//	{
//		AncesBox father = child.father;
//		AncesBox mother = child.mother;
//		boolean intrude = child.gen.getWidth() < child.getWidth();
//		
//		//if child doesn't have both parents
//		if(father == null || mother == null) {
//			double parentHeight = 0;
//			if(father != null) {
//				parentHeight = father.getHeight();
//				child.upperBounds.set(0, parentHeight + child.getVerticalSpace());
//			}
//			else if(mother != null) {
//				parentHeight = mother.getHeight();
//				child.lowerBounds.set(0, -parentHeight - child.getVerticalSpace());
//			}
//			
//			if(intrude) {
//				double dist = child.getHeight() + parentHeight + child.getVerticalSpace()*2;
//				return new double[] {dist/2.0,dist/2.0};
//			}
//			else
//				return new double[] {DEFAULT_VOFFSET,DEFAULT_VOFFSET};
//		}
//		
//		//if child has both parents
//		double maxDist = 0; //represents mother offset + father offset
//		int maxGen = Math.min(father.lowerBounds.size(),mother.upperBounds.size()); //determines how far to compare
//		
//		//find the max distance between them (the closest they can be while "intersecting" only at one point)
//		for (int i = 0; i < maxGen; ++i) {
//			
//			double curDist =  mother.upperBounds.get(i) - father.lowerBounds.get(i) + father.getVerticalSpace();
//			maxDist = Math.max(curDist, maxDist);
//		}
//		
//		int genBase = child.gen.getGenNum(); //used to index into generation array
//		if(genBase < 0) genBase = 0; //make sure its not negative
//		double intrudeLen = child.getWidth()-child.gen.getWidth(); //used to find generation that is being intruded upon
//		double adjustMother = 0.0; //adjustment for offsets
//		double adjustFather = 0.0; //adjustment for offsets
//		
//		for(int i=1; i < maxGen+1; i++) {
//			intrudeLen -= tree.getGeneration(genBase+i).getWidth();
//			
//			//adjust offsets if intruding into another box
//			if(intrude && intrudeLen <= 0.0) {
//				double mOffset = mother.upperBounds.get(i-1) + child.getVerticalSpace()/2;
//				double fOffset = -father.lowerBounds.get(i-1)+ child.getVerticalSpace()/2;
////				double betweenDiff = Math.abs(mother.upperBounds.get(i-1)+father.lowerBounds.get(i-1));
////				if(betweenDiff < child.getHeight())
////					maxDist += child.getHeight() - betweenDiff;
//				double mDiff = mOffset + child.getHeight()/2 - maxDist/2.0;
//				double fDiff = fOffset + child.getHeight()/2 - maxDist/2.0;
////				adjustFather = fDiff;
////				adjustMother = mDiff;
//				if(fDiff > 0) {
//					adjustFather += fDiff;
////					adjustMother += -mother.upperSubTreeOffset-mDiff;
//				}
//				if(mDiff > 0) {
//					adjustMother += mDiff;
////					adjustFather += father.lowerSubTreeOffset+fDiff;
//				}
//				double join = mother.upperSubTreeOffset - (maxDist/2.0 + adjustMother);
//				fDiff = -father.lowerSubTreeOffset + child.getHeight()/2 - maxDist/2.0;
//
//				
//				break;
//			}
//		}
//		
//		return new double[] {maxDist/2.0 +adjustFather, -(maxDist/2.0 + adjustMother)};
//	}
	
	private double[] findOffsets(AncesBox child)
	{
		AncesBox father = child.father;
		AncesBox mother = child.mother;
		boolean intrude = child.gen.getWidth() < child.getWidth();
		
		//if child doesn't have both parents
		if(father == null || mother == null) {
			double parentHeight = 0;
			if(father != null) {
				parentHeight = father.getHeight();
				child.upperBounds.set(0, parentHeight + child.getVerticalSpace());
			}
			else if(mother != null) {
				parentHeight = mother.getHeight();
				child.lowerBounds.set(0, -parentHeight - child.getVerticalSpace());
			}
			
			if(intrude) {
				double dist = child.getHeight() + parentHeight + child.getVerticalSpace()*2;
				return new double[] {dist/2.0,dist/2.0};
			}
			else
				return new double[] {DEFAULT_VOFFSET,DEFAULT_VOFFSET};
		}
		
		
		
		//if child has both parents
		double maxDist = 0; //represents distance from the childs mother and father boxes
		int maxGen = Math.min(father.lowerBounds.size(),mother.upperBounds.size()); //determines how far to compare
		
		//find the max distance between them (the closest they can be while "intersecting" only at one point)
		for (int i = 0; i < maxGen; ++i) {
			
			double curDist =  mother.upperBounds.get(i) - father.lowerBounds.get(i) + father.getVerticalSpace();
			maxDist = Math.max(curDist, maxDist);
		}
		
		//child is centered between the parents
		double mOffset = -maxDist/2;
		double fOffset = maxDist/2;
		
		int genBase = child.gen.getGenNum(); //used to index into generation array
		if(genBase < 0) genBase = 0; //make sure its not negative
		double intrudeLen = child.getWidth()-child.gen.getWidth(); //used to find generation that is being intruded upon
		
		for(int i=1; i < maxGen+1; i++) {
			intrudeLen -= tree.getGeneration(genBase+i).getWidth();
			
			//if intruding, adjust offsets to center the child between parents in the generation that is being intruded
			if(intrude && intrudeLen <= 0.0) {
				double temp = mother.upperBounds.get(i-1) + father.lowerBounds.get(i-1);
				double diff = maxDist - Math.abs( mother.upperBounds.get(i-1) - father.lowerBounds.get(i-1));
				//center the child between the parents
				fOffset -= temp/2;
				mOffset -= temp/2;
				
				//expand the offsets if gap between parents is too small
				if(diff < child.getHeight()+child.getVerticalSpace()*2) {
					double expand = child.getHeight()+child.getVerticalSpace()*3 - diff;
					fOffset += expand/2;
					mOffset -= expand/2;
				}
				
				break;
			}
		}
		
		return new double[] {fOffset, mOffset};
	}
	


	
	public void expandOffsets(AncesTree tree, double paperHeight) {
		double ratio = paperHeight /tree.getHeight();
		if(ratio > 1.0){
			multiplyOffsets(tree.root,ratio);
		}
		
	}
	
	private void multiplyOffsets(AncesBox b, double multiplier) {
		if(b == null)
			return;
		
		b.fatherVOffset *= multiplier;
		b.motherVOffset *= multiplier;
		
		multiplyOffsets(b.father,multiplier);
		multiplyOffsets(b.mother,multiplier);
	}
}
