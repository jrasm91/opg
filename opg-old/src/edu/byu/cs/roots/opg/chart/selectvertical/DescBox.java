package edu.byu.cs.roots.opg.chart.selectvertical;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.LinkedList;

import edu.byu.cs.roots.opg.chart.ShapeInfo;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdFillRect;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdMoveTo;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdRelFillRect;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdRelLineTo;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdRelRoundRect;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdRoundRect;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdSetFont;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdText;

import edu.byu.cs.roots.opg.model.Family;
import edu.byu.cs.roots.opg.model.Gender;
import edu.byu.cs.roots.opg.model.Individual;
import edu.byu.cs.roots.opg.model.OpgSession;
import edu.byu.cs.roots.opg.util.DataFitter;
import edu.byu.cs.roots.opg.util.NameAbbreviator;

public class DescBox extends Box
{
	ArrayList<DescInnerBox> innerBoxes = new ArrayList<DescInnerBox>();
	ArrayList<DescBox> children = new ArrayList<DescBox>();
	boolean maleDescendant;
	boolean single;//is this person single?
	public int maxGensInTree; //this the maximum number of generations in this individual's tree (1 = self, 2 = self & children, etc.)
	
	//size/position variables
	double upperSubTreeHeight;
	double lowerSubTreeHeight;
	double vertSeperation = 0;//vertical seperation between boxes to do: modify this to be non-zero
	double relSiblingVertOffset;
	double relChildVertOffset;
	double spouseChildOffset;
	double maxSpouseChildOffset;
	double boxHeight;
	static double boxWidth;
	static double scaler = 1.0;
	double hPos,vPos;
	
	double upperRelBoxBound; //the upper bound of the individual box relative to the base position
	double lowerRelBoxBound; //the upper bound of the individual box relative to the base position
	
	public double baseXSepValue;//this is the default horizontal seperation value between the generation that uses this printing method and the next without any scaling for space for lines from multiple spouses to their children
	public double xsepvalue;//min distance between containers in the x direction. This shouldn't really ever change
	int xSepScalar; //the number of times that the base horizontal seperation value should be nultiplied for additional spacing between this generation and the next to allow for space for non-crossing lines from multiple spouses to their children groups
	public double framesepvalue = 2;//This is the tiny seperation for the distance between the colored fill-in of the box and the frame
	public int gen;
	public int numInGen;
	public double minHeight; //the minimum height for the current configuration of the tree
	
	static final double maxBoxHeightRelToWidthFactor = 0.618;
	static final double gapFill = 0.9; // proportion of gap between boxes areas that is actually filled by box 
	
	Rectangle2D.Double boxInfo = new Rectangle2D.Double();

	public DescBox(Individual indi)
	{
		this.indi = indi;
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
	void buildBoxTree(ArrayList<ArrayList<DescBox>> descGenPositions, int curGen)
	{
		if (indi.fams.size() == 0)
			single = true;
		else
			single = false;//to do: account for fams structures with no children and one spouse
		
		if (descGenPositions.size() <= curGen)
			descGenPositions.add(new ArrayList<DescBox>());
		descGenPositions.get(curGen).add(this);
		gen = curGen;
		numInGen = descGenPositions.get(curGen).size() - 1;
		
		//lets prune out families that will give us problems later.
		ArrayList<Family> goodFamilies = new ArrayList<Family>();
		for (int i=0; i<indi.fams.size(); i++){
			Family curFam = indi.fams.get(i);
			boolean hasSpouse = curFam.husband!= null && curFam.wife != null;
			if(curFam.children.size()>0 || hasSpouse || i==0) //this is a useful family we need to keep;
				goodFamilies.add(curFam);
		}
		if(goodFamilies.size()>1){
			Family first = goodFamilies.get(0);
			if((first.husband==null || first.wife==null) && first.children.size()<=0)
				goodFamilies.remove(0);
		}
		indi.fams = goodFamilies;
		
		//build spouse boxes
		for (Family curFam: indi.fams)
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
					
					DescBox curChildBox = new DescBox(curChild);
					curChildBox.maleDescendant = (curChild.gender == Gender.MALE);
					curChildBox.buildBoxTree(descGenPositions, curGen + 1);
					curFamBox.children.add(curChildBox);
					children.add(curChildBox);
					if (curChildBox.maxGensInTree > maxGensInTree)
						maxGensInTree = curChildBox.maxGensInTree;
				}
			}
		}
		if (indi.fams.size() == 0)
		{
			DescInnerBox box = new DescInnerBox();
			if (indi.gender == Gender.FEMALE)
				box.wife = indi;
			else
				box.husband = indi;
			innerBoxes.add(box);
		}
		++maxGensInTree;
	}
	
	/**
	 * Recursively calculates the minimum coordinates for this individual
	 * (and its subtree(s)) based on the minimum font size
	 *
	 * @param options - chart options that contain the minimum font size
	 */
	void calcCoords(VerticalChartOptions options, int curGen, int[] maxSpouseLineOffset)
	{
		// todo: If we are not getting any children then make sure we don't get a null error
		int maxGen = options.getDescGens();
		if (curGen > maxGen)
			return;
		
		//calculate the dimensions for this box
		//boxHeight = printArray.get(curGen).getBoxHeight(this);
		//boxHeight = options.getMinFontSize() * ((single)? 1.2 : innerBoxes.size()*2*1.2);
		upperSubTreeHeight = boxHeight/2.0;
		lowerSubTreeHeight = -upperSubTreeHeight;
		//boxWidth = printArray.get(curGen).getBoxWidth();
		upperBounds = new ArrayList<Double>(maxGen - curGen);
		lowerBounds = new ArrayList<Double>(maxGen - curGen);
		//inner spouse box offset calculations
		for (int i = 0; i < innerBoxes.size(); ++i) {
			DescInnerBox curSpouseBox = innerBoxes.get(i);
			double totalHeights = boxHeight * innerBoxes.size();
			curSpouseBox.pieceOffset = - boxHeight/2.0 + -(boxHeight*i) + totalHeights/2.0; 
		}
		
		//recurse on all of the children
		int maxGensOfSubTree = 0;
		if (curGen < maxGen)
		{
			for (int i = 0; i < children.size(); ++i)
			{
				children.get(i).calcCoords(options, curGen+1, maxSpouseLineOffset);
				//keep track of the maximum number of generations in each subTree
				if (children.get(i).maxGensInTree > maxGensOfSubTree)
					maxGensOfSubTree = children.get(i).maxGensInTree;
			}
		}
		//set the maximum number of generations in this tree
		maxGensInTree = maxGensOfSubTree + 1;
		
		//calculate the distances between each of the children by intersecting their subtrees
		if  (children.size() > 0 && curGen < maxGen)
		{
			//add gap onto bottom of last child to ensure that siblings are spaced closer than siblings to cousins
			double lastChildLowerBound = children.get(children.size()-1).lowerBounds.get(0);
			children.get(children.size()-1).lowerBounds.set(0, new Double(lastChildLowerBound - vertSeperation ));
			
			double childrenHeight = 0;
			for (int i = 1; i < children.size(); ++i)
			{				
				int curSubTreeMaxGen = 0;
				double tempDist = 0;
				int controlSibling = -1;
				for (int j = i-1; j >= 0; --j)
				{
					double dist = children.get(j).relSiblingVertOffset + DescBox.calcIntersectDist( children.get(j) , children.get(i), curSubTreeMaxGen);
					if (dist > tempDist)
					{
						controlSibling = j;
						tempDist = dist;
						curSubTreeMaxGen = children.get(j).maxGensInTree;
					}
				}
				//set the offset distance of this child to the maximum distance
				//children.get(i).relSiblingVertOffset = tempDist;
				childrenHeight = tempDist;
				children.get(i).relSiblingVertOffset = childrenHeight;
				//redistribute any children inbetween evenly//fix me
				if (controlSibling > -1)
					redistrubuteVertically(children, controlSibling, i);
			}//end children loop
			
			//calculate the distance from the center of the parent to the center of the first child
			//	this is half of the distance from the top of the first child box to the bottom of the last child box
			//relChildVertOffset = (childrenHeight + (children.get(0).boxHeight/2.0) + (children.get(children.size()-1).boxHeight/2.0) ) / 2.0;
			//relChildVertOffset = (childrenHeight + (children.get(0).boxHeight/2.0) ) / 2.0;
			relChildVertOffset = (childrenHeight) / 2.0;
			
			for (DescBox child: children)
			{
				upperSubTreeHeight = Math.max(upperSubTreeHeight, relChildVertOffset - child.relSiblingVertOffset + child.upperSubTreeHeight);
				lowerSubTreeHeight = Math.min(lowerSubTreeHeight, relChildVertOffset - child.relSiblingVertOffset + child.lowerSubTreeHeight);
			}
		}
		
		//calculate first element of upper bounds array
		upperBounds.add(new Double(boxHeight/2.0));
		//calculate first element of lower bounds array
		lowerBounds.add(new Double( -(boxHeight/2.0 + vertSeperation) ));
		
		if (curGen < maxGen)
		{
			//calculate rest of upper bounds array
			int curSubTreeMaxGen = 0;
			for (int i = 0; i < children.size(); ++i)
			{
				DescBox curChild = children.get(i);
				if (curChild != null && curChild.maxGensInTree > curSubTreeMaxGen)
				{
					for (int j = curSubTreeMaxGen; j < curChild.maxGensInTree && curChild != null; ++j)
					{
						upperBounds.add(relChildVertOffset - curChild.relSiblingVertOffset + (curChild.upperBounds == null ? 0 : curChild.upperBounds.get(j)));
					}
					curSubTreeMaxGen = curChild.maxGensInTree;
				}
			}
			
			//calculate rest of lower bounds array
			
			curSubTreeMaxGen = 0;
			for (int i = children.size()-1; i >= 0; --i)
			{
				DescBox curChild = children.get(i);
				if (curChild.maxGensInTree > curSubTreeMaxGen && curChild != null)
				{
					for (int j = curSubTreeMaxGen; j < curChild.maxGensInTree; ++j)
					{
						lowerBounds.add(relChildVertOffset - curChild.relSiblingVertOffset + (curChild.lowerBounds == null ? 0 : curChild.lowerBounds.get(j)));
					}
					curSubTreeMaxGen = curChild.maxGensInTree;
				}
			}
		}
			
	
			//calculate the spacing for lines from multiple spouses to their children
			int maxOffset = 0;
			if (innerBoxes.size() > 1)//if there is more than one spouse
			{
				//loop through all of the spouses
				for (int i = 0; i < innerBoxes.size(); ++i)
				{
					DescInnerBox curSpouse = innerBoxes.get(i);
					if (curSpouse.children.size() > 0)
					{
						DescBox firstChild = curSpouse.children.get(0);
						DescBox lastChild = curSpouse.children.get(curSpouse.children.size()-1);
						//find if children center is above or below piece
						double childCenterDiff = relChildVertOffset - (firstChild.relSiblingVertOffset + lastChild.relSiblingVertOffset)/2.0 - curSpouse.pieceOffset;
						//below
						if (childCenterDiff < 0)
						{
							int firstPosIdx = i;
							int curIdx = i;
							DescInnerBox prevSpouse =  innerBoxes.get(curIdx);
							while (childCenterDiff < 0)
							{
								//move to next spouse
								++curIdx;
								//quit if we've past the last spouse
								if (curIdx >= innerBoxes.size())
									break;
								//skip spouses with no children
								if ( innerBoxes.get(curIdx).children.size() == 0)
									continue;
								//if the spouse has children, then check to see if this spouses children are
								//	centered below the spouse and that the previous spouse's (with
								//	children) children's lower edge is below this spouses center
								firstChild = innerBoxes.get(curIdx).children.get(0);
								lastChild = innerBoxes.get(curIdx).children.get(innerBoxes.get(curIdx).children.size()-1);
								childCenterDiff = relChildVertOffset - (firstChild.relSiblingVertOffset + lastChild.relSiblingVertOffset)/2.0 - innerBoxes.get(curIdx).pieceOffset;
								
								DescBox prevLastChild = prevSpouse.children.get(prevSpouse.children.size()-1);
								if (relChildVertOffset - prevLastChild.relSiblingVertOffset + prevLastChild.boxHeight/2.0 >= innerBoxes.get(curIdx).pieceOffset)
									break;
								prevSpouse = innerBoxes.get(curIdx);
							}
							//set the children connecting line offsets for all of the spouses that overlap
							int k = 0;
							for (int j = firstPosIdx; j <= curIdx  - 1; ++j)
							{
								if (innerBoxes.get(j).children.size() > 0)
									++k;
								innerBoxes.get(j).spouseChildOffset = k;
							}
							if (k > maxOffset)
								maxOffset = k;
							//set the piece counter(i) to the next piece(spouse)
							i = curIdx - 1;
						}
						//above
						else if (childCenterDiff > 0)
						{
							int firstPosIdx = i;
							int curIdx = i;
							DescInnerBox prevSpouse =  innerBoxes.get(curIdx);
							while (childCenterDiff > 0)
							{
								//move to next spouse
								++curIdx;
								//quit if we've past the last spouse
								if (curIdx >= innerBoxes.size())
									break;
								//skip spouses with no children
								if ( innerBoxes.get(curIdx).children.size() == 0)
									continue;
								//if the spouse has children, then check to see if this spouses children are
								//	centered below the spouse and that the previous spouse's (with
								//	children) children's lower edge is below this spouses center
								firstChild = innerBoxes.get(curIdx).children.get(0);
								lastChild = innerBoxes.get(curIdx).children.get(innerBoxes.get(curIdx).children.size()-1);
								childCenterDiff = relChildVertOffset - (firstChild.relSiblingVertOffset + lastChild.relSiblingVertOffset)/2.0 - innerBoxes.get(curIdx).pieceOffset;
								
								DescBox prevLastChild = prevSpouse.children.get(prevSpouse.children.size()-1);
								if (relChildVertOffset -prevLastChild.relSiblingVertOffset + prevLastChild.boxHeight/2.0 <= innerBoxes.get(curIdx).pieceOffset)
									break;
								prevSpouse = innerBoxes.get(curIdx);
							}
							//set the children connecting line offsets for all of the spouses that overlap
							int k = 0;
							for (int j = curIdx - 1 ; j >= firstPosIdx; --j)
							{
								if (innerBoxes.get(j).children.size() > 0)
									++k;
								innerBoxes.get(j).spouseChildOffset = k;
							}
							if (k > maxOffset)
								maxOffset = k;
							//set the piece counter(i) to the next piece(spouse)
							i = curIdx - 1;
						}
						
					}
					else
						curSpouse.spouseChildOffset = 0;
					
				}
			}
			maxSpouseChildOffset = maxOffset;
			if (maxOffset > maxSpouseLineOffset[curGen])
				maxSpouseLineOffset[curGen] = maxOffset;
			//

	}
	
//	------------------------------------------------------
	//this method finds the closest distance two containers can be while only touchng at
	//	one point (assuming the bounds for all generations are at different distances)
	//	top - the container on top
	//	bottom - the container on bottom
	//	startGen - the generation to start checking for intersection
	static private double calcIntersectDist(DescBox top, DescBox bottom, int startGen)
	{
		double maxDist = 0;
		int maxGen = Math.min(top.lowerBounds.size(), bottom.upperBounds.size());
		//if the starting generation is greater than one of the trees, then return 
		if (maxGen < startGen)
			return -1;
		
		//find the max distance between them (the closest they can be while "intesecting" only at one point)
		for (int i = startGen; i < maxGen; ++i)
		{
			double curDist = bottom.upperBounds.get(i) - top.lowerBounds.get(i);
			if (curDist > maxDist)
				maxDist = curDist;
		}
		return maxDist;
	}
	//------------------------------------------------------
	//this method spaces all of the containers in the siblings array between those at top
	//	and bottom so that they are evenly distributed between the two
	//fix me: this method currently does not take other intersections past the first generation into account
	static private void redistrubuteVertically(ArrayList<DescBox> siblings, int top, int bottom)
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
	
	void setScaler(double newScaler)
	{
		if (newScaler < 1)//allow for round-off error
			
			scaler=1;
		else
			scaler = newScaler;
	}
	
	
	//new drawing code
	void drawDescRootTree(OpgSession session, ChartMargins chart, VerticalChartOptions options, ArrayList<ArrayList<DescBox>> genPositions, double x, double y)
	{
				
		double[] smallestSizePerGen = new double[options.getDescGens()+1];//keep track of the smallest box size per generation
//		fillGaps(genPositions, smallestSizePerGen, 0, options.getDescGens(), options);
		
		//make sure font sizes per generation aren't too big or too small
//		double minFont = options.getMinFontSize();
//		double maxFont = options.getMaxFontSize();
//		for (int i = 0; i < smallestSizePerGen.length; ++i)
//		{			
//			if (smallestSizePerGen[i] < minFont)
//				smallestSizePerGen[i] = minFont;
//			if (smallestSizePerGen[i] > maxFont)
//				smallestSizePerGen[i] = maxFont;
//		}
		
		
		//start recursion on self
		int genToStart = options.isIncludeSpouses() ? 1 : 0;
		if (options.getDescGens() > 0)
			drawDescTreeRec(session, genToStart, chart, options, smallestSizePerGen, x,y);		
	}
	
	
//	private void fillGaps(ArrayList<ArrayList<DescBox>> genPositions, double[] smallestSizePerGen, int curGen, int maxGen, VerticalChartOptions options) {
//		ArrayList<DescBox> myGen = genPositions.get(gen);
//		double halfMaxHeight = (maxBoxHeightRelToWidthFactor * innerBoxes.size() * boxWidth)/2.0;
//		double halfMinHeight = options.getMinFontSize() * ((single)? 1.2 : innerBoxes.size()*2*1.2);
//		//special case for first and last boxes in a generation
//			if (numInGen == 0)
//				upperRelBoxBound = halfMaxHeight;
//			else
//			{
//				double distToNeighbor = scaler*(myGen.get(numInGen-1).vPos - vPos);
//				upperRelBoxBound = Math.min(distToNeighbor/2.0 * gapFill, halfMaxHeight);
//				if (upperRelBoxBound < halfMinHeight)
//					upperRelBoxBound = halfMinHeight;
//			}
//			if (numInGen == myGen.size()-1)
//				lowerRelBoxBound = -halfMaxHeight;
//			else
//			{
//				double distToNeighbor = scaler*(vPos - myGen.get(numInGen+1).vPos);
//				lowerRelBoxBound = -Math.min(distToNeighbor/2.0 * gapFill, halfMaxHeight);
//				if (lowerRelBoxBound > -halfMinHeight)
//					lowerRelBoxBound = -halfMinHeight;
//			}
//			
//			//make symmetric
//			upperRelBoxBound = Math.min(upperRelBoxBound,-lowerRelBoxBound);
//			lowerRelBoxBound = -upperRelBoxBound;
//			
//			//keep track of lowest total box size per generation
//			if ((upperRelBoxBound - lowerRelBoxBound) < smallestSizePerGen[curGen] || (smallestSizePerGen[curGen] == 0))
//				smallestSizePerGen[curGen] = upperRelBoxBound - lowerRelBoxBound;
//			
//		//recurse on children
//		if( curGen  < maxGen)
//		{
//			for (DescBox child: children)
//			{
//				child.fillGaps(genPositions, smallestSizePerGen, curGen+1, maxGen, options);
//				
//			}
//		}		
//	}


	void drawDescTreeRec(OpgSession session, int curGen, ChartMargins chart, VerticalChartOptions options, double[] smallestSizePerGen, double x, double y)
	{
		/* this method recursively draws a container onto a chart
		 * x,y - origin position where container should be drawn (origin is the right middle of the Individual's box)
		 * curGen - the current generation number from the root
		 * maxGen - the maximum number of generations to draw
		 * chart - the ChartDrawInfo object into which the chart DrawCommands will be placed
		 * printArray - an ArrayList of Print objects that are to be used in drawing each corresponding generation
		 * c - the Container to be drawn
		*/
		//public void drawContainer(double x, double y, int curGen, int maxGen, ChartDrawInfo chart, ArrayList<Print> printArray, Container c, float upperColor, float lowerColor, double heightScalar, double widthScalar) throws IOException
		{
			//calculate current box width
			//c.boxWidth = getBoxWidth(widthScalar);
			baseXSepValue = boxWidth * 0.1;//(float)(c.boxWidth * 0.1);
			//xsepvalue = baseXSepValue*((xSepScalar + 1)/2.0);//xsepvalue = baseXSepValue + baseXSepValue*((xSepScalar + 1)/2);
			xsepvalue = baseXSepValue + baseXSepValue*((xSepScalar + 1)/2);
			
			//draw individual box for container
			//calculate lower? left corner
			//double boxX = x - boxWidth;
			//double boxY = y - (boxHeight/2.0);
			
			
			//draw outer box for box with multiple spouse inner boxes
			if (innerBoxes.size() > 1)
			{
				chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x),chart.yOffset( y - (upperRelBoxBound - lowerRelBoxBound)/2.0)));
				if (options.isRoundedCorners())
				{
					chart.addDrawCommand(new DrawCmdRelRoundRect(boxWidth, (upperRelBoxBound - lowerRelBoxBound), 1, 5, Color.black, Color.white, null));					
				}
				else
					chart.addDrawCommand(new DrawCmdRelFillRect(boxWidth, (upperRelBoxBound - lowerRelBoxBound), 1, Color.black, Color.white, null));				
				
				//chart.addDrawCommand(new DrawCmdRelFillRect(boxWidth, boxHeight, 1, Color.black, Color.white));
			}
			
			//draw "pieces" inside container
			for (int i = 0; i < innerBoxes.size(); ++i)
			{
				DescInnerBox curPiece = innerBoxes.get(i);
				boolean oneSpouse = curPiece.husband == null || curPiece.wife == null; 
				
				if (single )
				{
					//single individual
					drawSingle(session, chart, smallestSizePerGen[curGen], options, chart.xOffset(x), chart.yOffset(y), boxWidth, (upperRelBoxBound - lowerRelBoxBound), curPiece, false);
					//drawSingle(x, y, boxWidth, yXForm(boxHeight),chart, options, curPiece);
				}
				else
				{
					//married or has children					
					//drawMarriage(x,y,curGen, maxGen, chart, printArray, c, i, (MarriageContainerPiece)curPiece, Print.floatToColor(tempColorPos) );
					//drawSingle(x, y, boxWidth, yXForm(boxHeight),chart, options, curPiece);
					
					double pieceOffsetAbsolute = 0;
					
					if (innerBoxes.size() == 1)
					{
						if (oneSpouse)
							drawSingle(session, chart, smallestSizePerGen[curGen], options, chart.xOffset(x), chart.yOffset(y), boxWidth, (upperRelBoxBound - lowerRelBoxBound), curPiece, false);
						else
							drawMarriageBox(session, chart, smallestSizePerGen[curGen], options, chart.xOffset(x), chart.yOffset(y), boxWidth, (upperRelBoxBound - lowerRelBoxBound), curPiece, true);						
					}
					else
					{
						if(oneSpouse){
							System.out.println(curPiece.husband + " , " + curPiece.wife);
							System.out.println("null spouse");
							drawSingle(session, chart, smallestSizePerGen[curGen], options, chart.xOffset(x), chart.yOffset(y), boxWidth, (upperRelBoxBound - lowerRelBoxBound), curPiece, true);
						}
						else{
							double height = (upperRelBoxBound - lowerRelBoxBound);
							double innerBoxHeight = height / innerBoxes.size();
							pieceOffsetAbsolute = (height/2.0) - innerBoxHeight/2.0 - (i * innerBoxHeight);
							double innerBoxYPos = y + pieceOffsetAbsolute;
							drawInnerMarriageBox(session, chart, smallestSizePerGen[curGen], options, chart.xOffset(x), chart.yOffset(innerBoxYPos), boxWidth, height/innerBoxes.size(), curPiece);
						}
					}
					
					if (curGen < options.getDescGens())
					{
						int numChildren = curPiece.children.size();
						Color lineColor = (curPiece.spouseChildOffset % 2 == 1)? Color.black : Color.red ;
						
						if (numChildren > 0)//to do: && children haven't been drawn before 
						{
							DescBox firstChild = curPiece.children.get(0);
							DescBox lastChild = curPiece.children.get(curPiece.children.size()-1);
							double childCenter = (firstChild.relSiblingVertOffset + lastChild.relSiblingVertOffset)/2.0;
							//print lines to children (the ones shared by all children of this marriage
							
							if (innerBoxes.size() > 1)
							{
								chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x - xsepvalue + baseXSepValue/2.0), chart.yOffset(y + (relChildVertOffset - curPiece.children.get(0).relSiblingVertOffset)*scaler )) );
								chart.addDrawCommand(new DrawCmdRelLineTo(0, -(curPiece.children.get(curPiece.children.size()-1).relSiblingVertOffset - curPiece.children.get(0).relSiblingVertOffset)* scaler, 1, lineColor));
								//to do:fix this line so it always connects to the child
								double lineSegmentWidth = (xsepvalue - baseXSepValue/2.0)/(maxSpouseChildOffset + 1);
								chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x + framesepvalue), chart.yOffset(y + pieceOffsetAbsolute)));
								chart.addDrawCommand(new DrawCmdRelLineTo(-(lineSegmentWidth * (maxSpouseChildOffset + 1 - curPiece.spouseChildOffset)) - framesepvalue, 0, 1, lineColor));
								chart.addDrawCommand(new DrawCmdRelLineTo(0, (relChildVertOffset - childCenter)*scaler - pieceOffsetAbsolute, 1, lineColor));
								chart.addDrawCommand(new DrawCmdRelLineTo(-lineSegmentWidth * curPiece.spouseChildOffset, 0, 1, lineColor));
							}
							else
							{
								lineColor = Color.black;
								chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x - xsepvalue + baseXSepValue/2.0), chart.yOffset( (y + relChildVertOffset*scaler)  )));
								chart.addDrawCommand(new DrawCmdRelLineTo(0, -curPiece.children.get(curPiece.children.size()-1).relSiblingVertOffset * scaler, 1, Color.black));
								chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x), chart.yOffset(y)) );
								chart.addDrawCommand(new DrawCmdRelLineTo(-xsepvalue + baseXSepValue/2.0 , 0, 1, Color.black));
							}
						}
						for (DescBox curChild: curPiece.children)
						{
														
							//fix me:print line to child
							chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x - xsepvalue), chart.yOffset((y + (relChildVertOffset - curChild.relSiblingVertOffset)* scaler))) );
							chart.addDrawCommand(new DrawCmdRelLineTo(baseXSepValue/2.0, 0, 1, lineColor));

							//print child container on chart (recursive)
							curChild.drawDescTreeRec(session, curGen+1, chart, options, smallestSizePerGen, x - boxWidth - xsepvalue, y + yXForm(relChildVertOffset - curChild.relSiblingVertOffset));
							//curChild.drawContainers(x - c.boxWidth - xsepvalue, y + c.relChildVertOffset - curPiece.children.get(j).relSiblingVertOffset, curGen+1, maxGen, chart, printArray, curColorPos, curColorPos + childColorGap, heightScalar, widthScalar);
						}
					}					
				}
			}//end pieces loop
			
			
		}

	}
	
	private void drawInnerMarriageBox(OpgSession session, ChartMargins chart, double fontSize,
			VerticalChartOptions options, double x, double y,
			double width, double height, DescInnerBox innerBox) {
		drawMarriageBox(session, chart, fontSize, options, x+2, y, width-4, height-4, innerBox, false);
		
	}

	private void drawMarriageBox(OpgSession session, ChartMargins chart, double fontSize,
			VerticalChartOptions options, double x, double y,
			double width, double height, DescInnerBox innerBox, boolean drawBorder) {	
		Individual wife = innerBox.wife;
		Individual husband = innerBox.husband;
		
		//pick bold font for whichever spouse is the primary descendant
		Font wifeFont;
		Font husbandFont;
		Font normalFont = session.getOpgOptions().getFont().font.deriveFont((float)fontSize);
		if (maleDescendant)
		{
			husbandFont = session.getOpgOptions().getFont().getBoldFont((float)Math.floor(fontSize));
			wifeFont = normalFont;
		} else
		{
			husbandFont = normalFont;
			wifeFont = session.getOpgOptions().getFont().getBoldFont((float)Math.floor(fontSize));
		}
		
		//abbreviate and fit husband's info into box
		int horizTextGap = 3;
		ArrayList<String> husbandText = DataFitter.fit(husband, (float)((height-5)/2.0), (float)width-horizTextGap*2, husbandFont, "");
		double husbandNameFontSize = NameAbbreviator.getSize();
		double husbandHeight = husbandText.size() * (fontSize * 1.1) + (fontSize * 0.3);
		
		//abbreviate and fit wife's info into box
		ArrayList<String> wifeText = DataFitter.fit(wife, (float)((height-5)/2.0), (float)width-horizTextGap*2, wifeFont, "");
		double wifeNameFontSize = NameAbbreviator.getSize();
		double wifeHeight = wifeText.size() * (fontSize * 1.1) + (fontSize * 0.3);
		height = husbandHeight + wifeHeight;
		
		//draw box
		double lineWidth = (options.isBoxBorder() && drawBorder)? 1 : 0;
		if (options.isRoundedCorners())
		{
			chart.addDrawCommand(new DrawCmdRoundRect(x, y-height/2.0, width,height, lineWidth, 5, Color.BLACK, options.getDescScheme().getColor(indi.id), boxInfo));
		}
		else
			chart.addDrawCommand(new DrawCmdFillRect(x, y-height/2.0, width,height, lineWidth, Color.BLACK, options.getDescScheme().getColor(indi.id), boxInfo));
		
		Font font = normalFont;
		//draw fit lines of text from above
		//husband text
		chart.addDrawCommand(new DrawCmdSetFont(font, Color.BLACK));
		double vertTextPos = y + height/2.0 - husbandFont.getSize2D();
		for(int i = 0; i < husbandText.size(); ++i)
		{
			String line = husbandText.get(i);
			Font oldFont = font;
			if (i == 0)
			{
				font = husbandFont.deriveFont((float)husbandNameFontSize);
				chart.addDrawCommand(new DrawCmdSetFont(font, Color.BLACK));
			}
			chart.addDrawCommand(new DrawCmdMoveTo(x+horizTextGap,vertTextPos));
			chart.addDrawCommand(new DrawCmdText(line));
			vertTextPos -= font.getSize2D() * 1.1;
			if (oldFont != font)
				chart.addDrawCommand(new DrawCmdSetFont(oldFont, Color.BLACK));
			font = oldFont;
		}
		
		//wife text
		chart.addDrawCommand(new DrawCmdSetFont(font, Color.BLACK));
		//vertTextPos = y + height/2.0 - husbandFont.getSize2D();
		for(int i = 0; i < wifeText.size(); ++i)
		{
			String line = wifeText.get(i);
			Font oldFont = font;
			if (i == 0)
			{
				font = wifeFont.deriveFont((float)wifeNameFontSize);
				chart.addDrawCommand(new DrawCmdSetFont(font, Color.BLACK));
			}
			chart.addDrawCommand(new DrawCmdMoveTo(x+horizTextGap,vertTextPos));
			chart.addDrawCommand(new DrawCmdText(line));
			vertTextPos -= font.getSize2D() * 1.1;
			if (oldFont != font)
				chart.addDrawCommand(new DrawCmdSetFont(oldFont, Color.BLACK));
			font = oldFont;
		}
		
		
	}

	protected void drawSingle(OpgSession session, ChartMargins chart, double fontSize,
			VerticalChartOptions options, double x, double y,
			double width, double height, DescInnerBox piece, boolean innerBox) {
				
		//pick out the person we're drawing
		Individual indi = (maleDescendant)? piece.husband : piece.wife;
		Individual spouse = (!maleDescendant)? piece.husband : piece.wife;
		if(indi==null && spouse != null)
			indi = spouse;
		
		Font font = session.getOpgOptions().getFont().font.deriveFont((float)fontSize);
		//abbreviate and fit individual's info into box
		int horizTextGap = 3;
		ArrayList<String> text = DataFitter.fit(indi, (float)height-5, (float)width-horizTextGap*2, font, "");
		double nameFontSize = NameAbbreviator.getSize();
		height = text.size() * (fontSize * 1.1) + (fontSize * 0.3);
		
		//draw box
		double lineWidth = (options.isBoxBorder())? 1 : 0;
		if(innerBox) lineWidth = 0;
		
		double X = innerBox ? x+2 : x;
		double Y = y;
		double HEIGHT = innerBox ? height : height;
		double WIDTH = innerBox ? width - 4 : width;
		if (options.isRoundedCorners())
		{
			chart.addDrawCommand(new DrawCmdRoundRect(X, Y-HEIGHT/2.0, WIDTH,HEIGHT, lineWidth, 5, Color.BLACK, options.getDescScheme().getColor(indi.id), boxInfo));
		}
		else
			chart.addDrawCommand(new DrawCmdFillRect(X, Y-HEIGHT/2.0, WIDTH,HEIGHT, lineWidth, Color.BLACK, options.getDescScheme().getColor(indi.id), boxInfo));
		
		//draw fit lines of text from above
		chart.addDrawCommand(new DrawCmdSetFont(font, Color.BLACK));
		double vertTextPos = Y + HEIGHT/2.0 - font.getSize2D();
		for(int i = 0; i < text.size(); ++i)
		{
			String line = text.get(i);
			Font oldFont = font;
			if (i == 0 )
			{
				//font = font.deriveFont((float)nameFontSize);
				font = session.getOpgOptions().getFont().getBoldFont((float)Math.floor(nameFontSize));
				chart.addDrawCommand(new DrawCmdSetFont(font, Color.BLACK));
			}
			chart.addDrawCommand(new DrawCmdMoveTo(X+horizTextGap,vertTextPos));
			chart.addDrawCommand(new DrawCmdText(line));
			vertTextPos -= font.getSize2D() * 1.1;
			if (oldFont != font)
				chart.addDrawCommand(new DrawCmdSetFont(oldFont, Color.BLACK));
			font = oldFont;
		}
		
	}

	
	/**
	 * this inner class is for the spouse boxes inside each descendent box.
	 * A descendent that has zero or one spouses will only have one DescInnerBox.
	 * @author derek
	 *
	 */
	protected class DescInnerBox
	{
		Individual husband = null;
		Individual wife = null;
		boolean maleDescendant; //is the husband the direct descendant?
		//boolean single;//is this person single?
		ArrayList<DescBox> children = new ArrayList<DescBox>(5);
		double pieceOffset;
		double spouseChildOffset;
	}
	
	double yXForm(double y)
	{
		return y * scaler;
	}

	public void setRelativePositions(int curGen, int maxGen)
	{
		//set child box positions relative to this box
		if (curGen < maxGen)
		{
			for (DescBox child: children)
			{
				child.vPos = vPos + relChildVertOffset - child.relSiblingVertOffset;
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
		for (DescBox child : children)
		{
			if ((curGen  < maxGen))
				list = child.getBoxes(list, curGen + 1, maxGen);
			if ((child.boxInfo != null) && (curGen < maxGen)) //this is for the root parent, who was already drawn by the ancestor boxes, thus they have no boxInfo here
				list.add(new ShapeInfo(child.boxInfo, child.indi, child.gen, false));
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
			return new ShapeInfo(boxInfo, indi, gen, false);
		
		for (DescBox child : children)
		{
			if(child.boxInfo == null){
				System.out.println("debug: WARNING - you tried to find the intersect on"
						+ " a DescBox for " + indi.namePrefix + ",whose boxInfo == " +
						"null -> this would result nullPointerException and should be fixed!");
				//Copied this bug from AncesBox, don't know if it'll happen, I guess we'll look into it
				
				//return null; commented out, stopped the tracing
			}
//			
			if ((child.boxInfo != null) && (curGen < maxGen) && child.boxInfo.contains(x, y))
			{
				System.out.println("THIS HAS COUNTED THE INTERSECT: " + child.indi.givenName + ": " + child.boxInfo);
				return new ShapeInfo(child.boxInfo, child.indi, child.gen, false);
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
	
}

