package edu.byu.cs.roots.opg.chart.portrait;

import java.awt.Color;
import java.util.ArrayList;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdMoveTo;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdRelFillRect;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdRelLineTo;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdRelRoundRect;
import edu.byu.cs.roots.opg.chart.preset.templates.ChartMargins;
import edu.byu.cs.roots.opg.chart.preset.templates.DescBoxParent;
import edu.byu.cs.roots.opg.chart.preset.templates.PresetChartOptions;
import edu.byu.cs.roots.opg.model.Individual;
import edu.byu.cs.roots.opg.model.OpgSession;

public class DescBox extends DescBoxParent
{
	private static final long serialVersionUID = 1L;
	public DescBox(Individual indi)
	{
		super(indi);
	}
	
	
	/**
	 * Recursively calculates the minimum coordinates for this individual
	 * (and its subtree(s)) based on the minimum font size
	 *
	 * @param options - chart options that contain the minimum font size
	 */
	@Override
	public void calcCoords(PresetChartOptions options, int curGen, int[] maxSpouseLineOffset)
	{
		int maxGen = options.getDescGens();
		if (curGen > maxGen)
			return;
		
		//calculate the dimensions for this box
		//boxHeight = printArray.get(curGen).getBoxHeight(this);
		setBoxHeight(styleBox.boxHeight * ((single)? 1.2 : innerBoxes.size()*(styleBox.layout.parallelCouple?1:2)*1.2));
		upperSubTreeHeight = getBoxHeight()/2.0 + styleBox.paddingAmount;
		lowerSubTreeHeight = -upperSubTreeHeight;
		//boxWidth = printArray.get(curGen).getBoxWidth();
		setUpperBounds(new ArrayList<Double>(maxGen - curGen));
		setLowerBounds(new ArrayList<Double>(maxGen - curGen));
		//inner spouse box offset calculations
		for (int i = 0; i < innerBoxes.size(); ++i) {
			DescInnerBox curSpouseBox = innerBoxes.get(i);
			curSpouseBox.innerBoxHeight = getBoxHeight() / innerBoxes.size();
			//places the inner boxes into their respective places in the container box
			curSpouseBox.pieceOffset = getBoxHeight()/2.0 - curSpouseBox.innerBoxHeight/2.0 - (i * curSpouseBox.innerBoxHeight); 
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
			double lastChildLowerBound = children.get(children.size()-1).getLowerBounds().get(0);
			children.get(children.size()-1).getLowerBounds().set(0, new Double(lastChildLowerBound - vertSeperation ));
			
			double childrenHeight = 0;
			if (children.size() > 0)
				children.get(0).setRelSiblingVertOffset(0);
			for (int i = 1; i < children.size(); ++i)
			{				
				int curSubTreeMaxGen = 0;
				double tempDist = 0;
				int controlSibling = -1;
				for (int j = i-1; j >= 0; --j)
				{
					double dist = children.get(j).getRelSiblingVertOffset() + DescBox.calcIntersectDist( children.get(j) , children.get(i), curSubTreeMaxGen);
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
				children.get(i).setRelSiblingVertOffset(childrenHeight);
				//TODO redistribute any children inbetween evenly//fix me
				if (controlSibling > -1)
					redistrubuteVertically(children, controlSibling, i);
			}//end children loop
			
			//calculate the distance from the center of the parent to the center of the first child
			//	this is half of the distance from the top of the first child box to the bottom of the last child box
			//relChildVertOffset = (childrenHeight + (children.get(0).boxHeight/2.0) + (children.get(children.size()-1).boxHeight/2.0) ) / 2.0;
			//relChildVertOffset = (childrenHeight + (children.get(0).boxHeight/2.0) ) / 2.0;
			relChildVertOffset = (childrenHeight) / 2.0;
			
			
		}
		
		//This is the shift up and down part
		for (int i = 0; i < innerBoxes.size(); i++){
			DescInnerBox curSpouseBox = innerBoxes.get(i);
			double childCenter = 0;
			if (curSpouseBox.children.size() > 0)
			{
				DescBoxParent firstChild = curSpouseBox.children.get(0);
				DescBoxParent lastChild = curSpouseBox.children.get(curSpouseBox.children.size()-1);
				childCenter = (firstChild.getRelSiblingVertOffset() + lastChild.getRelSiblingVertOffset())/2.0;
				double childCenterDiff = relChildVertOffset - (firstChild.getRelSiblingVertOffset() + lastChild.getRelSiblingVertOffset())/2.0 - curSpouseBox.pieceOffset;
				if (childCenterDiff > 0){
					if (i == 0){
						relChildVertOffset -= childCenterDiff;
						upperSubTreeHeight = getBoxHeight()/2.0 + styleBox.paddingAmount;
						lowerSubTreeHeight = -upperSubTreeHeight;
					}
					else
					{
						for (int j = i; j < innerBoxes.size(); j++){
							DescInnerBox curBox = innerBoxes.get(j);
							for (DescBoxParent child : curBox.children){
								child.setRelSiblingVertOffset(child.getRelSiblingVertOffset() + childCenterDiff);

							}
						}
					}
				}
				else if (childCenterDiff < 0){
					if (i == 0){
						for (int j = i; j < innerBoxes.size(); j++){
							DescInnerBox curBox = innerBoxes.get(j);
							for (DescBoxParent child : curBox.children){
								child.setRelSiblingVertOffset(child.getRelSiblingVertOffset() + childCenterDiff);

							}
						}
						
						
					}
					else
					{
						setBoxHeight(getBoxHeight() - childCenterDiff);
						relChildVertOffset -= childCenterDiff/2.0;
						for (int j = 0; j < innerBoxes.size(); j++){
							innerBoxes.get(j).pieceOffset -= childCenterDiff/2.0;
						}
						for (int j = i; j < innerBoxes.size(); j++){
							innerBoxes.get(j).pieceOffset += childCenterDiff;
						}
						
						upperSubTreeHeight = getBoxHeight()/2.0 + styleBox.paddingAmount;
						lowerSubTreeHeight = -upperSubTreeHeight;
					}
				}
				
				
			}
				
		}
		
		for (DescBoxParent child: children)
		{
			upperSubTreeHeight = Math.max(upperSubTreeHeight, relChildVertOffset - child.getRelSiblingVertOffset() + child.upperSubTreeHeight);
			lowerSubTreeHeight = Math.min(lowerSubTreeHeight, relChildVertOffset - child.getRelSiblingVertOffset() + child.lowerSubTreeHeight);
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
					DescBoxParent firstChild = curSpouse.children.get(0);
					DescBoxParent lastChild = curSpouse.children.get(curSpouse.children.size()-1);
					//find if children center is above or below piece
					double childCenterDiff = relChildVertOffset - (firstChild.getRelSiblingVertOffset() + lastChild.getRelSiblingVertOffset())/2.0 - curSpouse.pieceOffset;
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
							childCenterDiff = relChildVertOffset - (firstChild.getRelSiblingVertOffset() + lastChild.getRelSiblingVertOffset())/2.0 - innerBoxes.get(curIdx).pieceOffset;
							
							DescBoxParent prevLastChild = prevSpouse.children.get(prevSpouse.children.size()-1);
							if (relChildVertOffset - prevLastChild.getRelSiblingVertOffset() + prevLastChild.getBoxHeight()/2.0 >= innerBoxes.get(curIdx).pieceOffset)
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
							childCenterDiff = relChildVertOffset - (firstChild.getRelSiblingVertOffset() + lastChild.getRelSiblingVertOffset())/2.0 - innerBoxes.get(curIdx).pieceOffset;
							
							DescBoxParent prevLastChild = prevSpouse.children.get(prevSpouse.children.size()-1);
							if (relChildVertOffset -prevLastChild.getRelSiblingVertOffset() + prevLastChild.getBoxHeight()/2.0 <= innerBoxes.get(curIdx).pieceOffset)
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
		
		//calculate first element of upper bounds array
		getUpperBounds().add(new Double(getBoxHeight()/2.0 + styleBox.paddingAmount));
		//calculate first element of lower bounds array
		getLowerBounds().add(new Double( -(getBoxHeight()/2.0 + vertSeperation + styleBox.paddingAmount) ));
		
		if (curGen < maxGen)
		{
			//calculate rest of upper bounds array
			int curSubTreeMaxGen = 0;
			for (int i = 0; i < children.size(); ++i)
			{
				DescBoxParent curChild = children.get(i);
				if (curChild != null && curChild.maxGensInTree > curSubTreeMaxGen)
				{
					for (int j = curSubTreeMaxGen; j < curChild.maxGensInTree && curChild != null; ++j)
					{
						getUpperBounds().add(relChildVertOffset - curChild.getRelSiblingVertOffset() + (curChild.getUpperBounds() == null ? 0 : curChild.getUpperBounds().get(j)));
					}
					curSubTreeMaxGen = curChild.maxGensInTree;
				}
			}
			
			//calculate rest of lower bounds array
			
			curSubTreeMaxGen = 0;
			for (int i = children.size()-1; i >= 0; --i)
			{
				DescBoxParent curChild = children.get(i);
				if (curChild.maxGensInTree > curSubTreeMaxGen && curChild != null)
				{
					for (int j = curSubTreeMaxGen; j < curChild.maxGensInTree; ++j)
					{
						getLowerBounds().add(relChildVertOffset - curChild.getRelSiblingVertOffset() + (curChild.getLowerBounds() == null ? 0 : curChild.getLowerBounds().get(j)));
					}
					curSubTreeMaxGen = curChild.maxGensInTree;
				}
			}
		}
			
	
			
			

	}
	

	
	//new drawing code
	@Override
	public void drawDescRootTree(ChartMargins chart, PresetChartOptions options, ArrayList<ArrayList<DescBoxParent>> genPositions, double x, double y, OpgSession session)
	{
				
		fillGaps(genPositions, 0, options.getDescGens(), options);
		
		
		//start recursion on self
		int genToStart = (options.isIncludeSpouses()) ? 1 : 0;
		if (options.getDescGens() > 0)
			drawDescTreeRec(genToStart, chart, options, x, y, session);		
	}
	
	
	
	@Override
	public void drawDescTreeRec(int curGen, ChartMargins chart, PresetChartOptions options, double x, double y, OpgSession session)
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
			//c.boxWidth = getpresetWidth(widthScalar);
			baseXSepValue = styleBox.getRelativeOffset();//(float)(c.boxWidth * 0.1);
			//xsepvalue = baseXSepValue*((xSepScalar + 1)/2.0);//xsepvalue = baseXSepValue + baseXSepValue*((xSepScalar + 1)/2);
			xsepvalue = baseXSepValue;
			boolean rootIsDescendantStyle =(this.gen == 0 && options.getDescGens() > 0 && !options.isIncludeSpouses());
			
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
					chart.addDrawCommand(new DrawCmdRelRoundRect(rootIsDescendantStyle?styleBox.intrudeWidth:styleBox.getBoxWidth(), (upperRelBoxBound - lowerRelBoxBound), styleBox.borderlineWidth, styleBox.cornerCurve, Color.black, Color.white, boxInfo));					
				}
				else
					chart.addDrawCommand(new DrawCmdRelFillRect(rootIsDescendantStyle?styleBox.intrudeWidth:styleBox.getBoxWidth(), (upperRelBoxBound - lowerRelBoxBound), styleBox.borderlineWidth, Color.black, Color.white, boxInfo));				
				
				//chart.addDrawCommand(new DrawCmdRelFillRect(presetWidth, boxHeight, 1, Color.black, Color.white));
			}
			
			//draw "pieces" inside container
			for (int i = 0; i < innerBoxes.size(); ++i)
			{
				
				DescInnerBox curPiece = innerBoxes.get(i);
				boolean oneSpouse = curPiece.husband == null || curPiece.wife == null; 
				
				if (single )
				{
					//single individual
					drawSingle(chart, options, chart.xOffset(x), chart.yOffset(y), styleBox.getBoxWidth(), (upperRelBoxBound - lowerRelBoxBound), curPiece, false, session);
					//drawSingle(x, y, boxWidth, yXForm(boxHeight),chart, options, curPiece);
				}
				else
				{
					//married or has children					
					//drawMarriage(x,y,curGen, maxGen, chart, printArray, c, i, (MarriageContainerPiece)curPiece, Print.floatToColor(tempColorPos) );
					//drawSingle(x, y, boxWidth, yXForm(boxHeight),chart, options, curPiece);
					
					
					
					if (innerBoxes.size() == 1)
					{
						if (oneSpouse)
							drawSingle(chart, options, chart.xOffset(x), chart.yOffset(y), styleBox.getBoxWidth(), (upperRelBoxBound - lowerRelBoxBound), curPiece, false, session);
						else
							drawMarriageBox(chart, options, chart.xOffset(x), chart.yOffset(y), rootIsDescendantStyle?styleBox.intrudeWidth:styleBox.getBoxWidth(), (boxHeight), curPiece, true, session);						
					}
					else
					{
						if(oneSpouse){
							System.out.println(curPiece.husband + " , " + curPiece.wife);
							System.out.println("null spouse");
							drawSingle(chart, options, chart.xOffset(x), chart.yOffset(y), styleBox.getBoxWidth(), (upperRelBoxBound - lowerRelBoxBound), curPiece, true, session);
						}
						else{
							double height = (upperRelBoxBound - lowerRelBoxBound);
							double innerBoxHeight = curPiece.innerBoxHeight;
							double childCenter = 0;
							
							if (curPiece.children.size() > 0){
								DescBoxParent firstChild = curPiece.children.get(0);
								DescBoxParent lastChild = curPiece.children.get(curPiece.children.size()-1);
								childCenter = (firstChild.getRelSiblingVertOffset() + lastChild.getRelSiblingVertOffset())/2.0;
								
							}
							double innerBoxYPos = y + curPiece.pieceOffset;
							drawInnerMarriageBox(chart, options, chart.xOffset(x), chart.yOffset(innerBoxYPos), rootIsDescendantStyle?styleBox.intrudeWidth:styleBox.getBoxWidth(), innerBoxHeight, curPiece, session);
						}
					}
					
					if (curGen < options.getDescGens())
					{
						int numChildren = curPiece.children.size();
						//Color lineColor = (curPiece.spouseChildOffset % 2 == 1)? Color.black : Color.red ;
						Color lineColor = Color.black;
						if (numChildren > 0)
						{
							DescBoxParent firstChild = curPiece.children.get(0);
							DescBoxParent lastChild = curPiece.children.get(curPiece.children.size()-1);
							double childCenter = (firstChild.getRelSiblingVertOffset() + lastChild.getRelSiblingVertOffset())/2.0;
							
							//print lines to children (the ones shared by all children of this marriage
							
							if (innerBoxes.size() > 1)
							{
								chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x - xsepvalue + baseXSepValue/2.0), chart.yOffset(y + (relChildVertOffset - curPiece.children.get(0).getRelSiblingVertOffset())*scaler )) );
								chart.addDrawCommand(new DrawCmdRelLineTo(0, -(curPiece.children.get(curPiece.children.size()-1).getRelSiblingVertOffset() - curPiece.children.get(0).getRelSiblingVertOffset())* scaler, 1, lineColor));
								//to do:fix this line so it always connects to the child
								double lineSegmentWidth = (xsepvalue - baseXSepValue/2.0);
								chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x + framesepvalue), chart.yOffset(y + curPiece.pieceOffset)));
								chart.addDrawCommand(new DrawCmdRelLineTo(-(lineSegmentWidth) - framesepvalue, 0, 1, lineColor));
							}
							else
							{
								lineColor = Color.black;
								chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x - xsepvalue + baseXSepValue/2.0), chart.yOffset( (y + relChildVertOffset*scaler)  )));
								chart.addDrawCommand(new DrawCmdRelLineTo(0, -curPiece.children.get(curPiece.children.size()-1).getRelSiblingVertOffset() * scaler, 1, Color.black));
								chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x), chart.yOffset(y)) );
								chart.addDrawCommand(new DrawCmdRelLineTo(-xsepvalue + baseXSepValue/2.0 , 0, 1, Color.black));
							}
						}
						//Draws connection lines and recursively calls again
						for (DescBoxParent curChild: curPiece.children)
						{
														
							//TODO:print line to child
							chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x - xsepvalue), chart.yOffset((y + (relChildVertOffset - curChild.getRelSiblingVertOffset())* scaler))) );
							chart.addDrawCommand(new DrawCmdRelLineTo(baseXSepValue/2.0, 0, 1, lineColor));
							
							
							
							//print child container on chart (recursive)
							curChild.drawDescTreeRec(curGen+1, chart, options, x - curChild.styleBox.getBoxWidth() - xsepvalue, y + yXForm(relChildVertOffset - curChild.getRelSiblingVertOffset()), session);
							//curChild.drawContainers(x - c.boxWidth - xsepvalue, y + c.relChildVertOffset - curPiece.children.get(j).relSiblingVertOffset, curGen+1, maxGen, chart, printArray, curColorPos, curColorPos + childColorGap, heightScalar, widthScalar);
							
						}
					}					
				}
			}//end pieces loop
			
			
		}

	}
	
	private void drawInnerMarriageBox(ChartMargins chart,
			PresetChartOptions options, double x, double y,
			double width, double height, DescInnerBox innerBox, OpgSession session) {
		drawMarriageBox(chart, options, x+2, y, width-4, height-4, innerBox, false, session);
		
	}

	private void drawMarriageBox(ChartMargins chart, 
			PresetChartOptions options, double x, double y,
			double width, double height, DescInnerBox innerBox, boolean drawBorder, OpgSession session) {	
		Individual wife = innerBox.wife;
		Individual husband = innerBox.husband;
		
		BoxLayout b = new BoxLayout();
		styleBox.layout.convertToSpecificLayout(b);
		b.drawDescMarried(chart, styleBox, options, x, y, width, height,  
				getIndi(), husband, wife, drawBorder, maleDescendant, 
				innerBoxes.size() > 1?null:boxInfo, innerBoxes.indexOf(innerBox), session);

		
	}

	protected void drawSingle(ChartMargins chart,
			PresetChartOptions options, double x, double y,
			double width, double height, DescInnerBox piece, boolean innerBox, OpgSession session) {
		//pick out the person we're drawing
		Individual indi = (maleDescendant)? piece.husband : piece.wife;
		Individual spouse = (!maleDescendant)? piece.husband : piece.wife;
		if(indi==null && spouse != null)
			indi = spouse;

		height = getBoxHeight();
		double X = innerBox ? x+2 : x;
		double WIDTH = innerBox ? width - 4 : width;

		BoxLayout b = new BoxLayout();
		styleBox.layout.convertToSpecificLayout(b);
		b.drawDescSingle(chart, styleBox, options, X, y, WIDTH, height, indi, innerBoxes.size() > 1?null:boxInfo, session);
		
	}

	
	
	
	@Override
	public DescBoxParent addSpecificBox(Individual indi){return new DescBox(indi);};
	
	
}

