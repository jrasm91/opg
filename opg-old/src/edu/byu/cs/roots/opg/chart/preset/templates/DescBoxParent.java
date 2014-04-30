package edu.byu.cs.roots.opg.chart.preset.templates;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.LinkedList;

import edu.byu.cs.roots.opg.chart.ShapeInfo;
import edu.byu.cs.roots.opg.model.Family;
import edu.byu.cs.roots.opg.model.Gender;
import edu.byu.cs.roots.opg.model.Individual;
import edu.byu.cs.roots.opg.model.OpgSession;

public class DescBoxParent extends Box{
	private static final long serialVersionUID = 1L;
	protected ArrayList<DescInnerBox> innerBoxes = new ArrayList<DescInnerBox>();
	public ArrayList<DescBoxParent> children = new ArrayList<DescBoxParent>();
	protected boolean maleDescendant;
	protected boolean single;//is this person single?
	public int maxGensInTree; //this the maximum number of generations in this individual's tree (1 = self, 2 = self & children, etc.)
	
	//size/position variables
	public double upperSubTreeHeight;
	public double lowerSubTreeHeight;
	protected double vertSeperation = 0;//vertical seperation between boxes to do: modify this to be non-zero
	private double relSiblingVertOffset;
	/**
	 * I think this is the offset from the parent's center to the children's center
	 * In multiple marriage boxes, it would be center of the border box to center of all their children
	 */
	protected double relChildVertOffset;
	double spouseChildOffset;
	protected double maxSpouseChildOffset;
	protected double boxHeight;
	static double boxWidth;
	protected static double scaler = 1.0;
	double hPos;
	public double vPos;
	
	protected double upperRelBoxBound; //the upper bound of the individual box relative to the base position
	protected double lowerRelBoxBound; //the upper bound of the individual box relative to the base position
	
	public double baseXSepValue;//this is the default horizontal seperation value between the generation that uses this printing method and the next without any scaling for space for lines from multiple spouses to their children
	public double xsepvalue;//min distance between containers in the x direction. This shouldn't really ever change
	protected int xSepScalar; //the number of times that the base horizontal seperation value should be nultiplied for additional spacing between this generation and the next to allow for space for non-crossing lines from multiple spouses to their children groups
	public double framesepvalue = 2;//This is the tiny seperation for the distance between the colored fill-in of the box and the frame
	/**
	 * What generation the descendant is in
	 */
	public int gen;
	/**
	 * The order this appears vertically in the generation
	 */
	public int numInGen;
	public double minHeight; //the minimum height for the current configuration of the tree
	
	static final double maxBoxHeightRelToWidthFactor = 0.618;
	static final double gapFill = 0.9; // proportion of gap between boxes areas that is actually filled by box 
	
	protected Rectangle2D.Double boxInfo = new Rectangle2D.Double(); //this is where the coords and size are stored
	

	public DescBoxParent(Individual indi)
	{
		this.setIndi(indi);
		if (indi.gender == Gender.MALE)
			maleDescendant = true;
	}
	
	/**
	 * Recursively builds a tree of Boxes representing an individual's genealogy without
	 * duplicate subtrees.
	 * @param k 
	 * @param descGenPositions 
	 * 
	 */
	public void buildBoxTree(ArrayList<ArrayList<DescBoxParent>> descGenPositions, int curGen, OpgSession session)
	{
		for (Family fam : getIndi().fams)
			session.addFamToTree(fam);
		
		if (getIndi().fams.size() == 0)
			single = true;
		//Handles case of a fam with no children and one spouse
		else if (getIndi().fams.size() == 1){
			if (getIndi().fams.get(0).children.size() == 0){
				if (getIndi().gender == Gender.MALE && getIndi().fams.get(0).wife == null)
					single = true;
				else if (getIndi().gender == Gender.FEMALE && getIndi().fams.get(0).husband == null)
					single = true;
			}
		}
		else
			single = false;//TODO: account for fams structures with no children and one spouse
		
		

		
		if (descGenPositions.size() <= curGen)
			descGenPositions.add(new ArrayList<DescBoxParent>());
		descGenPositions.get(curGen).add(this);
		gen = curGen;
		numInGen = descGenPositions.get(curGen).size() - 1;
		
		//lets prune out families that will give us problems later.
		ArrayList<Family> goodFamilies = new ArrayList<Family>();
		for (int i=0; i<getIndi().fams.size(); i++){
			Family curFam = getIndi().fams.get(i);
			boolean hasSpouse = curFam.husband!= null && curFam.wife != null;
			if(curFam.children.size()>0 || hasSpouse || i==0) //this is a useful family we need to keep;
				goodFamilies.add(curFam);
		}
		if(goodFamilies.size()>1){
			Family first = goodFamilies.get(0);
			if((first.husband==null || first.wife==null) && first.children.size()<=0)
				goodFamilies.remove(0);
		}
		getIndi().fams = goodFamilies;
		
		//build spouse boxes
		for (Family curFam: getIndi().fams)
		{
			if (!curFam.isInTree)
			{
				DescInnerBox curFamBox = new DescInnerBox();
				curFamBox.husband = curFam.husband;
				curFamBox.wife = curFam.wife;
				innerBoxes.add(curFamBox);
				curFam.isInTree = true;
				
				
				//add children
				for (Individual curChild: curFam.children)
				{
					//single = false;
					
					DescBoxParent curChildBox = addSpecificBox(curChild);
					curChildBox.maleDescendant = (curChild.gender == Gender.MALE);
					curChildBox.buildBoxTree(descGenPositions, curGen + 1, session);
					curFamBox.children.add(curChildBox);
					children.add(curChildBox);
					if (curChildBox.maxGensInTree > maxGensInTree)
						maxGensInTree = curChildBox.maxGensInTree;
				}
			}
		}
		if (getIndi().fams.size() == 0)
		{
			DescInnerBox box = new DescInnerBox();
			if (getIndi().gender == Gender.FEMALE)
				box.wife = getIndi();
			else
				box.husband = getIndi();
			innerBoxes.add(box);
		}
		++maxGensInTree;
	}
	
	public void calcCoords(PresetChartOptions options, int curGen, int[] maxSpouseLineOffset){};
	public void drawDescTreeRec(int curGen, ChartMargins chart, PresetChartOptions options, double x, double y, OpgSession session){};
	public void drawDescRootTree(ChartMargins chart, PresetChartOptions options, ArrayList<ArrayList<DescBoxParent>> genPositions, double x, double y, OpgSession session){};
	public DescBoxParent addSpecificBox(Individual indi){return null;};
//	------------------------------------------------------
	//this method finds the closest distance two containers can be while only touching at
	//	one point (assuming the bounds for all generations are at different distances)
	//	top - the container on top
	//	bottom - the container on bottom
	//	startGen - the generation to start checking for intersection
	protected static double calcIntersectDist(DescBoxParent top, DescBoxParent bottom, int startGen)
	{
		double maxDist = 0;
		int maxGen = Math.min(top.getLowerBounds().size(), bottom.getUpperBounds().size());
		//if the starting generation is greater than one of the trees, then return 
		if (maxGen < startGen)
			return -1;
		
		//find the max distance between them (the closest they can be while "intersecting" only at one point)
		for (int i = startGen; i < maxGen; ++i)
		{
			double curDist = bottom.getUpperBounds().get(i) - top.getLowerBounds().get(i);
			if (curDist > maxDist)
				maxDist = curDist;
		}
		return maxDist;
	}
	//------------------------------------------------------
	//TODO: this method currently does not take other intersections past the first generation into account

	/**this method spaces all of the containers in the siblings array between those at top
	//	and bottom so that they are evenly distributed between the two
	*/
	protected static void redistrubuteVertically(ArrayList<DescBoxParent> siblings, int top, int bottom)
	{
		//if the siblings are right next to each other, then there can be no redistribution of the
		//	ones between, so quit
		if (bottom-1 <= top)
			return;
		
		//find the closest inner sibling to the bottom sibling
		
		//calculate the minimum distance from the bottom sibling abd tge closest inner sibling
		//double freeSpace = calcIntersectDist(siblings.get(bottom-1), siblings.get(bottom), 0);//fix me
		
		
		//calculate number of inner sibling groups until the closest one to the bottom
		
		
		//calculate the gap needed between the inner siblings based on the available space calculated above
		
		//add that gap space to the vertical sibling offset of each inner sibling, adding on the
		//	bounds of each sibling inbetween, treating siblings whose bounding is not controlled by
		//	by the sibling directly above as one sibling and moving them and those inbetween as
		//	a static group
		
		
		
		
		
		/*		
		double distance = siblings.get(bottom).relSiblingVertOffset - siblings.get(top).relSiblingVertOffset;
		double gap = distance / (double)(bottom - top);
		double position = siblings.get(top).relSiblingVertOffset;
		
		for (int i = top + 1; i < bottom; ++i)
		{
			position += gap;
			siblings.get(i).relSiblingVertOffset = position;
		}
		*/
		
	}
	//------------------------------------------------------
	
	
	
	
	double setHeight(double height)
	{
		return 0;
	}
	
	public void setScaler(double newScaler)
	{
		if (newScaler < 1)//allow for round-off error
			
			scaler=1;
		else
			scaler = newScaler;
	}
	
	protected void fillGaps(ArrayList<ArrayList<DescBoxParent>> genPositions, int curGen, int maxGen, PresetChartOptions options) {
		ArrayList<DescBoxParent> myGen = genPositions.get(gen);
		double halfMaxHeight = /*(maxBoxHeightRelToWidthFactor * innerBoxes.size() * */getBoxHeight()/2.0;
		double halfMinHeight = 0;
		//special case for first and last boxes in a generation
			if (numInGen == 0)
				upperRelBoxBound = halfMaxHeight;
			else
			{
				upperRelBoxBound = halfMaxHeight;
				if (upperRelBoxBound < halfMinHeight)
					upperRelBoxBound = halfMinHeight;
			}
			if (numInGen == myGen.size()-1)
				lowerRelBoxBound = -halfMaxHeight;
			else
			{
				lowerRelBoxBound = -halfMaxHeight;
				if (lowerRelBoxBound > -halfMinHeight)
					lowerRelBoxBound = -halfMinHeight;
			}
			
			//make symmetric
			upperRelBoxBound = Math.min(upperRelBoxBound,-lowerRelBoxBound);
			lowerRelBoxBound = -upperRelBoxBound;
			
		//recurse on children
		if( curGen  < maxGen)
		{
			for (DescBoxParent child: children)
			{
				child.fillGaps(genPositions, curGen+1, maxGen, options);
				
			}
		}		
	}

	protected double yXForm(double y)
	{
		return y * scaler;
	}

	public void setRelativePositions(int curGen, int maxGen)
	{
		//set child box positions relative to this box
		if (curGen < maxGen)
		{
			for (DescBoxParent child: children)
			{
				child.vPos = vPos + relChildVertOffset - child.getRelSiblingVertOffset();
				child.setRelativePositions(curGen+1, maxGen);
			}
		}
	}
	
	/**
	 * Recursively returns a LinkedList of all descendants displayed on the chart.
	 * @param the list that will get built through recursion
	 * @param the starting generation, usually 0
	 * @param the max generation to search to, chosen by user
	 * @return a LinkedList of ShapeInfos, storing all visible ancestors
	 */
	public LinkedList<ShapeInfo> getBoxes(LinkedList<ShapeInfo> list, int curGen, int maxGen)
	{
		for (DescBoxParent child : children)
		{
			if ((curGen  < maxGen))
				list = child.getBoxes(list, curGen + 1, maxGen);
			if ((child.boxInfo != null) && (curGen < maxGen)) //this is for the root parent, who was already drawn by the ancestor boxes, thus they have no boxInfo here
				list.add(new ShapeInfo(child.boxInfo, child.getIndi(), child.gen, false));
		}
		return list;
	}
	
	/**
	 * Recursively determines if the passed in point clicks any of the descendants visible.
	 * @param x x coord of click
	 * @param y y coord of click
	 * @param curGen current generation, usually 0
	 * @param maxGen max generation displayed, chosen by user
	 * @return the ShapeInfo of whoever was clicked, null if no intersect
	 */
	public ShapeInfo checkIntersect(double x, double y, int curGen, int maxGen)
	{
		ShapeInfo retVal = null;
		
		if(boxInfo.contains(x,y))
			return new ShapeInfo(boxInfo, getIndi(), gen, false);
		
		for (DescBoxParent child : children)
		{
			if(child.boxInfo == null){
				System.out.println("debug: WARNING - you tried to find the intersect on"
						+ " a DescBox for " + getIndi().namePrefix + ",whose boxInfo == " +
						"null -> this would result nullPointerException and should be fixed!");
				//Copied this bug from AncesBox, don't know if it'll happen, I guess we'll look into it
				
				//return null; commented out, stopped the tracing
			}
//			
			if ((child.boxInfo != null) && (curGen < maxGen) && child.boxInfo.contains(x, y))
			{
				System.out.println("THIS HAS COUNTED THE INTERSECT: " + child.getIndi().givenName + ": " + child.boxInfo);
				return new ShapeInfo(child.boxInfo, child.getIndi(), child.gen, false);
			}
			else
			{
				
				if ((!children.isEmpty()) && (curGen + 1 < maxGen))
				{
					retVal = child.checkIntersect(x, y, curGen + 1, maxGen);
					
				}
				
				
			}
			if(retVal != null)
				break;
		}
		return retVal; //triggers if no descendants
		
	}
	
	public String toString(){
		return getIndi().givenName + getIndi().middleName + getIndi().surname;
	}
	
	public void setBoxStyle(StylingBoxScheme scheme){
		for(DescBoxParent child : children)
			child.setBoxStyle(scheme);
		if(gen >= scheme.DescByGenList.size())
			scheme.increaseDescList(gen);
		styleBox = scheme.getDescStyle(gen);
		styleBox.resetTemporaries();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void setRelSiblingVertOffset(double relSiblingVertOffset) {
		this.relSiblingVertOffset = relSiblingVertOffset;
	}

	public double getRelSiblingVertOffset() {
		return relSiblingVertOffset;
	}
















	public void setBoxHeight(double boxHeight) {
		this.boxHeight = boxHeight;
	}

	public double getBoxHeight() {
		return boxHeight;
	}
















	/**
	 * this inner class is for the spouse boxes inside each descendent box.
	 * A descendent that has zero or one spouses will only have one DescInnerBox.
	 * @author derek
	 *
	 */
	public class DescInnerBox
	{
		public Individual husband = null;
		public Individual wife = null;
		boolean maleDescendant; //is the husband the direct descendant?
		//boolean single;//is this person single?
		public ArrayList<DescBoxParent> children = new ArrayList<DescBoxParent>(5);
		public double pieceOffset;
		public double spouseChildOffset;
		public double innerBoxHeight;
	}
}
