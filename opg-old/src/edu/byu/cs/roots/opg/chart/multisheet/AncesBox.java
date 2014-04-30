package edu.byu.cs.roots.opg.chart.multisheet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;



import edu.byu.cs.roots.opg.chart.ChartMaker;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdEndChartArrow;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdMoveTo;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdPageLink;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdRelLineTo;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdSetFont;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdStartChartArrow;
import edu.byu.cs.roots.opg.chart.preset.templates.AncesBoxParent;
import edu.byu.cs.roots.opg.chart.preset.templates.ChartMargins;
import edu.byu.cs.roots.opg.chart.preset.templates.PresetChartOptions;
import edu.byu.cs.roots.opg.chart.preset.templates.StylingBox;
import edu.byu.cs.roots.opg.chart.preset.templates.StylingBoxScheme;
import edu.byu.cs.roots.opg.chart.preset.templates.StylingBox.TextDirection;
import edu.byu.cs.roots.opg.model.Gender;
import edu.byu.cs.roots.opg.model.Individual;
import edu.byu.cs.roots.opg.model.OpgOptions;
import edu.byu.cs.roots.opg.model.OpgSession;
import edu.byu.cs.roots.opg.model.Single;
import edu.byu.cs.roots.opg.chart.multisheet.MultisheetChartMaker;
public class AncesBox extends AncesBoxParent
{
	
	private static final long serialVersionUID = 1L;
	public AncesBox(Individual indi) {
		super(indi);
	}


	/**
	 * Recursively calculates the minimum coordinates for this indivdual
	 * (and its subtree(s)) based on the minimum font size.
	 * Steps:
	 * 1) Create two arrays for each generation to store the upper and lower bounds for each generation
	 * 2) 
	 *
	 * @param options - chart options that contain the minimum font size
	 * @param curGen - current generation of Individual in this AncesBox (usually starts as 0)
	 * @param duplicateMap 
	 */
	@Override
	public void calcCoords(PresetChartOptions options, ChartMaker maker, int curGen, StylingBoxScheme stylingBoxes, OpgSession session)
	{
		OpgOptions opgOptions = session.getOpgOptions();
		getIndi().isInTree = true;
		session.addIndiToTree(getIndi());
		//----------------
		int maxGen;
		if(maker.equals(session.getBaseMaker()) && options.getAncesGens() == 5)
			maxGen = options.getAncesGens()-1;
		else
			maxGen = options.getAncesGens();
		double boxHeight = styleBox.boxHeight;
		boxWidth = styleBox.getBoxWidth();
		if (styleBox.weddingLayout.lines.size() > 0){
			
			if((getIndi().gender == Gender.MALE && styleBox.weddingDisplayType == StylingBox.WeddingPositions.HUSBAND_POSTFIX) ||
					(getIndi().gender == Gender.FEMALE && styleBox.weddingDisplayType == StylingBox.WeddingPositions.WIFE_POSTFIX) ||
					styleBox.weddingDisplayType == StylingBox.WeddingPositions.BOTH_POSTFIX){
				double increaseVariable = 0.0;
				for (int i = 0; i < styleBox.weddingLayout.lines.size(); i++){
					int listPosition = i + styleBox.layout.lines.size();
					if (styleBox.textPositions.size() == 0)
						styleBox.textPositions.add(0.0);
					if (styleBox.textPositions.size() == 1)
						increaseVariable += styleBox.textPositions.get(0);
					if (listPosition >= styleBox.textPositions.size())
						increaseVariable += (styleBox.textPositions.get(styleBox.textPositions.size() - 1) - styleBox.textPositions.get(styleBox.textPositions.size() - 2));
					else
						increaseVariable += (styleBox.textPositions.get(listPosition) - styleBox.textPositions.get(listPosition - 1));
				}
				if(styleBox.direction == StylingBox.TextDirection.NORMAL || styleBox.direction == TextDirection.ONE_EIGHTY)
					boxHeight += increaseVariable;
				else
					boxWidth += increaseVariable;
			}
		}
		double vertSeperation = 0; //: calculate vertical seperation
		
		if (curGen > maxGen)
			return;
		
		setUpperBounds(new ArrayList<Double>(maxGen - curGen));
		setLowerBounds(new ArrayList<Double>(maxGen - curGen));
		intersectUpperHeight = 0.0;
		intersectLowerHeight = 0.0;
				
		
		//calculate first element of upper bounds array for tight fit
		getUpperBounds().add(new Double(boxHeight/2.0 + styleBox.paddingAmount));
		//calculate first element of lower bounds array for tight fit
		getLowerBounds().add(new Double( -(boxHeight/2.0 + vertSeperation + styleBox.paddingAmount)));
		
		upperBoxBound = boxHeight/2.0;
		lowerBoxBound = -upperBoxBound;
		
		upperSubTreeHeight = boxHeight/2.0 + styleBox.paddingAmount;
		lowerSubTreeHeight = -upperSubTreeHeight;
		
		
		int maxGensOfSubTree = 0;
		
		int offset = curGen + 1;
		HashMap<String, Single<String>> duplicateLabels = opgOptions.getDuplicateLabels();
		HashMap<String, String> duplicates = opgOptions.getDuplicateMap();
		if (curGen < maxGen)
		{
			if ((father != null) && ((!opgOptions.isCollapsed(getIndi()) && !duplicates.containsKey(getIndi().id)) || options.getRoot() == getIndi()))
			{
				//This is where you add the specific duplicate label
				if (duplicates.containsKey(father.getIndi().id))
				{
					//ChartMaker addedMaker = session.addMaker(ChartType.MULTISHEET, father.getIndi(), offset);
				
					((MultisheetChartMaker) maker).addMakerToAddList(father.getIndi(), offset);
					duplicateLabels.put(father.getIndi().id, father.getIndi().pageId);
				}
				
				father.calcCoords(options, maker, curGen+1, stylingBoxes, session);
				//keep track of the maximum number of generations in each subTree
				if (father.maxGensOfTree > maxGensOfSubTree)
					maxGensOfSubTree = father.maxGensOfTree;
			}
			if ((mother != null) && ((!opgOptions.isCollapsed(getIndi()) && !duplicates.containsKey(getIndi().id)) || options.getRoot() == getIndi()))
			{
				//This is where you add the specific duplicate label
				if (duplicates.containsKey(mother.getIndi().id))
				{
					//ChartMaker addedMaker = session.addMaker(ChartType.MULTISHEET, father.getIndi(), offset);
					
					((MultisheetChartMaker) maker).addMakerToAddList(mother.getIndi(), offset);
					duplicateLabels.put(mother.getIndi().id, mother.getIndi().pageId);
				}
				
				mother.calcCoords(options, maker, curGen+1, stylingBoxes, session);
				
				//keep track of the maximum number of generations in each subTree
				if (mother.maxGensOfTree > maxGensOfSubTree)
					maxGensOfSubTree = mother.maxGensOfTree;
			}

		}
		if (options.getArrowStyle() == PresetChartOptions.EndLineArrowStyle.PARENTS){
			//Create the new Page for the father, and store the page number
			if (curGen == maxGen &&//session.getBaseOptions().getAncesGens() && 
					(getIndi().father != null))
			{
//				MultisheetChartMaker temp = 
//					(MultisheetChartMaker) session.addMaker(ChartType.MULTISHEET, getIndi().father, offset);
				
				((MultisheetChartMaker) maker).addMakerToAddList(getIndi().father, offset);
			}
			//Create the new Page for the mother, and store the page number
			if (curGen == maxGen &&//session.getBaseOptions().getAncesGens() && 
					(getIndi().mother != null))
			{
//				MultisheetChartMaker temp = 
//					(MultisheetChartMaker)session.addMaker(ChartType.MULTISHEET, getIndi().mother, offset);
				
				((MultisheetChartMaker) maker).addMakerToAddList(getIndi().mother, offset);
			}
		}
		//If new page style is 'self', create the new page with this box as the root. Store the page number
		else if (options.getArrowStyle() == PresetChartOptions.EndLineArrowStyle.SELF){
			if (curGen == maxGen &&//session.getBaseOptions().getAncesGens() && 
					(getIndi().father != null || getIndi().mother != null))
			{
				offset = offset - 1;
//				MultisheetChartMaker temp = 
//					(MultisheetChartMaker)session.addMaker(ChartType.MULTISHEET, getIndi(), offset);
				((MultisheetChartMaker) maker).addMakerToAddList(getIndi(), offset);
			}
		}
		
		
		//set the maximun number of generations in this tree
		maxGensOfTree = maxGensOfSubTree + 1;

		//Tight fit (jigsaw puzzle like) intersection
		//calculate the distances between each of the parents by intersecting their subtrees		
		
		//Extends the upper and lower box trees to include this intruding box
		if (styleBox.isIntruding && options.isAllowIntrusion()){
			double remainingIntrudeWidth = styleBox.intrudeWidth;
			for (int i = gen; remainingIntrudeWidth > 0; i++)
			{
				if (getLowerBounds().size() > (i - gen)){
					if (getLowerBounds().get(i - gen) > (getLowerBounds().get(0)))
						getLowerBounds().set(i - gen, getLowerBounds().get(0));
				}
				else 
					getLowerBounds().add(getLowerBounds().get(0));
				
				if (getUpperBounds().size() > (i - gen)){
					if (getUpperBounds().get(i - gen) < (getUpperBounds().get(0)))
						getUpperBounds().set(i-gen, getUpperBounds().get(0));
					}
				else 
					getUpperBounds().add(getUpperBounds().get(0));
				remainingIntrudeWidth -= (stylingBoxes.getAncesStyle(i).getBoxWidth() + stylingBoxes.getAncesStyle(i).getRelativeOffset());
			
			}
		}else if(opgOptions.getDuplicateMap().containsKey(getIndi().id) && options.getRoot() != getIndi()){
			double remainingIntrudeWidth = styleBox.getBoxWidth() + styleBox.endLineArrowShaftLength + styleBox.endLineArrowHeadLength;
			for (int i = gen; remainingIntrudeWidth > 0; i++)
			{
				if (getLowerBounds().size() > (i - gen)){
					if (getLowerBounds().get(i - gen) > (getLowerBounds().get(0)))
						getLowerBounds().set(i - gen, getLowerBounds().get(0));
				}
				else 
					getLowerBounds().add(getLowerBounds().get(0));
				
				if (getUpperBounds().size() > (i - gen)){
					if (getUpperBounds().get(i - gen) < (getUpperBounds().get(0)))
						getUpperBounds().set(i-gen, getUpperBounds().get(0));
					}
				else 
					getUpperBounds().add(getUpperBounds().get(0));
				remainingIntrudeWidth -= (stylingBoxes.getAncesStyle(i).getBoxWidth() + stylingBoxes.getAncesStyle(i).getRelativeOffset());
			
			}
		}
		
		if ((father != null || mother != null) && ((!opgOptions.isCollapsed(getIndi()) && !duplicates.containsKey(getIndi().id)) || options.getRoot() == getIndi()) && curGen < maxGen)
		{
			
				
			//if no father is visible
			if ((father == null))
			{
					//add gap onto bottom of lower parent to ensure that different sets of grandparents are spaced father than spouses
					mother.getLowerBounds().set(0, mother.getLowerBounds().get(0) - vertSeperation);
					motherVOffset = ((styleBox.isIntruding && options.isAllowIntrusion())?(getLowerBounds().get(0) - mother.getUpperBounds().get(0)):-3);////insert vertical offset here
					if(styleBox.isIntruding && options.isAllowIntrusion()){
						double lowerLine = motherVOffset - getLowerBounds().get(0);
						double motherUpperBound = mother.getUpperBounds().get(0) + mother.intersectUpperHeight;
						
						if(((lowerLine) + (motherUpperBound))>0)
							motherVOffset = (-(motherUpperBound - getLowerBounds().get(0)));
					}
					
					//calculate rest of upper and lower bounds array
					for (int i = 0; i < mother.getUpperBounds().size(); ++i)
					{
						if (getUpperBounds().size() > i + 1)
							getUpperBounds().set(i+1, Math.max(new Double(motherVOffset + mother.getUpperBounds().get(i)), getUpperBounds().get(0)));
						else
							getUpperBounds().add(new Double(motherVOffset + mother.getUpperBounds().get(i)));
					}
					for (int i = 0; i < mother.getLowerBounds().size(); ++i)
					{
						if (getLowerBounds().size() > i + 1)
							getLowerBounds().set(i+1, new Double(motherVOffset + mother.getLowerBounds().get(i)));
						else
							getLowerBounds().add(new Double(motherVOffset + mother.getLowerBounds().get(i)));
					}
					upperSubTreeHeight = Math.max(upperSubTreeHeight, motherVOffset + mother.upperSubTreeHeight);
					lowerSubTreeHeight = Math.min(lowerSubTreeHeight, motherVOffset + mother.lowerSubTreeHeight);
			}
			//if no mother is visible
			else if ((mother == null))
			{
					//add gap onto bottom of lower parent to ensure that different sets of grandparents are spaced father than spouses
					father.getLowerBounds().set(0, father.getLowerBounds().get(0) - vertSeperation);
					fatherVOffset = ((styleBox.isIntruding && options.isAllowIntrusion())?(getUpperBounds().get(0) - father.getLowerBounds().get(0)):3);////insert vertical offset here
					if(styleBox.isIntruding && options.isAllowIntrusion()){
						double upperLine = fatherVOffset - getUpperBounds().get(0);
						double fatherLowerBound = (father.getLowerBounds().get(0) + father.intersectLowerHeight);
						
						if(((upperLine) + (fatherLowerBound))<0)
							fatherVOffset = (-(fatherLowerBound - getUpperBounds().get(0)));
					}
					//calculate rest of upper and lower bounds array
					for (int i = 0; i < father.getUpperBounds().size(); ++i)
					{
						if (getUpperBounds().size() > i + 1)
							getUpperBounds().set(i+1, new Double(fatherVOffset + father.getUpperBounds().get(i)));
						else
							getUpperBounds().add(new Double(fatherVOffset + father.getUpperBounds().get(i)));
					}
					for (int i = 0; i < father.getLowerBounds().size(); ++i)
					{
						if (getLowerBounds().size() > i + 1)
							getLowerBounds().set(i+1, Math.min(new Double(fatherVOffset + father.getLowerBounds().get(i)), getLowerBounds().get(0)));
						else
							getLowerBounds().add(new Double(fatherVOffset + father.getLowerBounds().get(i)));
					}
					upperSubTreeHeight = Math.max(upperSubTreeHeight, fatherVOffset + father.upperSubTreeHeight);
					lowerSubTreeHeight = Math.min(lowerSubTreeHeight, fatherVOffset + father.lowerSubTreeHeight);
					
			}
			else
			{
				//add gap onto bottom of lower parent to ensure that different sets of grandparents are spaced father than spouses
				mother.getLowerBounds().set(0, mother.getLowerBounds().get(0) - vertSeperation );
				
				//calculate distance in between mother and father and offsets from child
				fatherVOffset = 0;
				motherVOffset = -fatherVOffset;
				int intrudeGen = 0;
				//TODO FIX THESE CHECKS!
				boolean firstNotSpouse = false;
				firstNotSpouse = (this.gen == 0 && options.getDescGens() > 0 && !options.isIncludeSpouses());
				if (styleBox.isIntruding && options.isAllowIntrusion() && !firstNotSpouse){
					double remainingIntrudeWidth = styleBox.intrudeWidth - (stylingBoxes.getAncesStyle(gen).getBoxWidth() + stylingBoxes.getAncesStyle(gen).getRelativeOffset());
					for (int i = gen; remainingIntrudeWidth > 0; i++)
					{
						if (father.getLowerBounds().size() > (i - gen)){
							if (-father.getLowerBounds().get(i - gen) > (fatherVOffset - getUpperBounds().get(0))){
								fatherVOffset += -father.getLowerBounds().get(i - gen) - (fatherVOffset - getUpperBounds().get(0));
							}
						}
						else
							father.getLowerBounds().add(-(fatherVOffset + getUpperBounds().get(0)));
						
						if (mother.getUpperBounds().size() > (i - gen)){
							if (mother.getUpperBounds().get(i - gen) > (-motherVOffset - getUpperBounds().get(0))){
								motherVOffset -= mother.getUpperBounds().get(i - gen)  - (-motherVOffset - getUpperBounds().get(0));
							}
						}
						else
							mother.getUpperBounds().add(-(motherVOffset - getUpperBounds().get(0)));
						
						remainingIntrudeWidth -= (stylingBoxes.getAncesStyle(i+1).getBoxWidth() + stylingBoxes.getAncesStyle(i+1).getRelativeOffset());
						intrudeGen = i - gen;
					}
					
					int maxIntersectGen = Math.min(father.getUpperBounds().size(), mother.getUpperBounds().size());
					for (int i = intrudeGen; i < maxIntersectGen; i++){
						if ((fatherVOffset + father.getLowerBounds().get(i))<(motherVOffset + mother.getUpperBounds().get(i))){
							double distance = ((motherVOffset + mother.getUpperBounds().get(i)) - (fatherVOffset + father.getLowerBounds().get(i)))/2.0;
							fatherVOffset += (distance < 0? -distance:distance);
							motherVOffset -= (distance < 0? -distance:distance);
						}
					}
					
				}
				else
				{
					double maxDistance = calcIntersectDist(father, mother);
					fatherVOffset = maxDistance / 2.0;
					motherVOffset = -fatherVOffset;
				}
				
				//This is to stop the lines from crossing into intruding boxes
				if(styleBox.isIntruding && options.isAllowIntrusion()){

					double upperLine = fatherVOffset - getUpperBounds().get(0);
					double fatherLowerBound = (father.getLowerBounds().get(0) + father.intersectLowerHeight);
					
					if(((upperLine) + (fatherLowerBound))<0)
						fatherVOffset = (-(fatherLowerBound - getUpperBounds().get(0)));
					
					double lowerLine = motherVOffset - getLowerBounds().get(0);
					double motherUpperBound = mother.getUpperBounds().get(0) + mother.intersectUpperHeight;
					
					if(((lowerLine) + (motherUpperBound))>0)
						motherVOffset = (-(motherUpperBound - getLowerBounds().get(0)));
				}
				
				//calculate rest of upper bounds array
				int i;
				for (i = 0; i < father.getUpperBounds().size(); ++i)
				{
					if (getUpperBounds().size() > i + 1)
						getUpperBounds().set(i+1, new Double(fatherVOffset + father.getUpperBounds().get(i)));
					else
						getUpperBounds().add(new Double(fatherVOffset + father.getUpperBounds().get(i)));
				}
				for ( ; i < mother.getUpperBounds().size(); ++i)
				{
					if (getUpperBounds().size() > i + 1)
						getUpperBounds().set(i+1, new Double(motherVOffset + mother.getUpperBounds().get(i)));
					else
						getUpperBounds().add(new Double(motherVOffset + mother.getUpperBounds().get(i)));
				}
				
				//calculate rest of lower bounds array
				for (i = 0; i < mother.getLowerBounds().size(); ++i)
				{
					if (getLowerBounds().size() > i + 1)
						getLowerBounds().set(i+1, new Double(motherVOffset + mother.getLowerBounds().get(i)));
					else
						getLowerBounds().add(new Double(motherVOffset + mother.getLowerBounds().get(i)));
				}
				for ( ; i < father.getLowerBounds().size(); ++i)
				{
					if (getLowerBounds().size() > i + 1)
						getLowerBounds().set(i+1, new Double(fatherVOffset + father.getLowerBounds().get(i)));
					else
						getLowerBounds().add(new Double(fatherVOffset + father.getLowerBounds().get(i)));
				}
				
				upperSubTreeHeight = Math.max(Math.max(upperSubTreeHeight, fatherVOffset + father.upperSubTreeHeight), motherVOffset + mother.upperSubTreeHeight);
				lowerSubTreeHeight = Math.min(Math.min(lowerSubTreeHeight, motherVOffset + mother.lowerSubTreeHeight), fatherVOffset + father.lowerSubTreeHeight);
			}
			if(options.isAllowIntrusion() && styleBox.isIntruding){
				intersectUpperHeight = (fatherVOffset - getUpperBounds().get(0)) + 3*styleBox.borderlineWidth;
				intersectLowerHeight = (motherVOffset - getLowerBounds().get(0)) - 3*styleBox.borderlineWidth;
			}
		}
	}
	
	
	
	
	
	
	//-----------------------------------------------------------------------
	
	void drawAncesRootTree(ChartMargins chart, MultisheetChartOptions options, double x, double y, OpgSession session)
	{
		//start recursion on self
		if (!(options.getAncesGens() == 0 && options.getDescGens() > 0) ||
				(options.getAncesGens() == 0 && options.isIncludeSpouses()))
		{
			drawAncesTreeRec(0, chart, options, x,y, session);
		}
	}
	

	//-----------------------------------------------------------------------
	@Override
	public void drawAncesTreeRec(int curGen, ChartMargins chart, PresetChartOptions options, double x, double y, OpgSession session)
	{
		OpgOptions opgOptions = session.getOpgOptions();
		int maxGen;
		/*if(maker.equals(session.getBaseMaker()))
			maxGen = options.getAncesGens()-1;
		else
		maxGen = options.getAncesGens();*/
		if(session.getMakerByRoot(options.getRoot().id) == session.getBaseMaker() && options.getAncesGens() == 5)
			maxGen = options.getAncesGens() -1;
		else
			maxGen = options.getAncesGens();
		//increase box Width and height to allow intrusion of a box into the next generation
		double myBoxWidth = boxWidth;
		boolean intrudes = false;
		if (styleBox.isIntruding && options.isAllowIntrusion())
		{
			myBoxWidth = styleBox.intrudeWidth;
			intrudes = true;
		}
		System.out.println(getIndi().id+" "+getIndi().givenName+" "+getIndi().surname+" "+this.maxVisibleGensInTree);
		if (!(curGen == 0 && options.getAncesGens() > 0 && options.getDescGens() > 0) || (options.isIncludeSpouses() && options.getDescGens() > 0))
				drawBox(chart, options, chart.xOffset(x), chart.yOffset(y), myBoxWidth, (upperBoxBound - lowerBoxBound), session);
		
		if (maxGen > curGen)//options.getAncesGens() > curGen)
		{
			double xsepvalue = styleBox.getRelativeOffset();
			
			if ((!opgOptions.isCollapsed(getIndi()) && !opgOptions.getDuplicateMap().containsKey(getIndi().id)) || options.getRoot() == getIndi())
			{
				if ((father != null))
				{
					
					//draw father (recursively)
					try {
						father.drawAncesTreeRec(curGen+1, chart, options, x + styleBox.getBoxWidth() + xsepvalue, y + yXForm(fatherVOffset), session);
					} catch (Exception e) {
						//  Auto-generated catch block
						e.printStackTrace();
					}
					
					//draw lines to father
					if (!intrudes)
					{
						chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x + styleBox.getBoxWidth()), chart.yOffset(y)));
						chart.addDrawCommand(new DrawCmdRelLineTo(xsepvalue/2.0,0.0,1, Color.BLACK));
						chart.addDrawCommand(new DrawCmdRelLineTo(0.0,yXForm(fatherVOffset),1, Color.BLACK));
						chart.addDrawCommand(new DrawCmdRelLineTo(xsepvalue/2.0,0.0,1, Color.BLACK));
					} else
					{
						if(curGen==0 && options.getDescGens() == 0){
							chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x + styleBox.getBoxWidth()/2.0), chart.yOffset(y + upperBoxBound)));
							chart.addDrawCommand(new DrawCmdRelLineTo(0.0,yXForm(fatherVOffset)- upperBoxBound ,1, Color.black));
							chart.addDrawCommand(new DrawCmdRelLineTo(styleBox.getBoxWidth()/2.0 + xsepvalue, 0.0,1, Color.black));
						}
						else if (curGen == 0){
							chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x + styleBox.getBoxWidth()/2.0), chart.yOffset(y + upperBoxBound)));
							chart.addDrawCommand(new DrawCmdRelLineTo(0.0,yXForm(fatherVOffset)- upperBoxBound ,1, Color.black));
							chart.addDrawCommand(new DrawCmdRelLineTo(styleBox.getBoxWidth()/2.0 + xsepvalue, 0.0,1, Color.black));
						}
						else{
							chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x + styleBox.getBoxWidth()/2.0), chart.yOffset(y + upperBoxBound)));
							chart.addDrawCommand(new DrawCmdRelLineTo(0.0,yXForm(fatherVOffset) - upperBoxBound,1, Color.BLACK));
							chart.addDrawCommand(new DrawCmdRelLineTo(styleBox.getBoxWidth()/2.0 + xsepvalue, 0.0,1, Color.BLACK));
						}
					}
				}
				if ((mother != null))
				{
					//draw mother (recursively)
					mother.drawAncesTreeRec(curGen + 1, chart, options, x + styleBox.getBoxWidth() + xsepvalue, y + yXForm(motherVOffset), session);
					
					//draw lines to mother
					if (!intrudes)
					{
						chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x + styleBox.getBoxWidth()), chart.yOffset(y)));
						chart.addDrawCommand(new DrawCmdRelLineTo(xsepvalue/2.0,0.0,1, Color.BLACK));
						chart.addDrawCommand(new DrawCmdRelLineTo(0.0, yXForm(motherVOffset),1, Color.BLACK));
						chart.addDrawCommand(new DrawCmdRelLineTo(xsepvalue/2.0,0,1, Color.BLACK));
					} else
					{
						if(curGen == 0 && options.getDescGens() == 0){
							chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x + styleBox.getBoxWidth()/2.0), chart.yOffset(y + lowerBoxBound)));
							chart.addDrawCommand(new DrawCmdRelLineTo(0.0,yXForm(motherVOffset) - lowerBoxBound,1, Color.BLACK));
							chart.addDrawCommand(new DrawCmdRelLineTo(styleBox.getBoxWidth()/2.0 + xsepvalue, 0.0,1, Color.BLACK));
						}
						else if(curGen == 0){
							chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x + styleBox.getBoxWidth()/2.0), chart.yOffset(y + lowerBoxBound)));
							chart.addDrawCommand(new DrawCmdRelLineTo(0.0,yXForm(motherVOffset) - lowerBoxBound,1, Color.BLACK));
							chart.addDrawCommand(new DrawCmdRelLineTo(styleBox.getBoxWidth()/2.0 + xsepvalue, 0.0,1, Color.BLACK));
						}
						else{
							chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x + styleBox.getBoxWidth()/2.0), chart.yOffset(y + lowerBoxBound)));
							chart.addDrawCommand(new DrawCmdRelLineTo(0.0,yXForm(motherVOffset) - lowerBoxBound,1, Color.BLACK));
							chart.addDrawCommand(new DrawCmdRelLineTo(styleBox.getBoxWidth()/2.0 + xsepvalue, 0.0,1, Color.BLACK));
						}
					}
				}
			}else if(opgOptions.getDuplicateMap().containsKey(getIndi().id)){
				Font arrowFont = session.getOpgOptions().getFont().getFont(Font.PLAIN, (float)styleBox.endLineArrowFontSize);
				double xOffset = ((styleBox.isIntruding && options.isAllowIntrusion())?styleBox.intrudeWidth:styleBox.getBoxWidth());
				chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x + xOffset), chart.yOffset(y)));
				chart.addDrawCommand(new DrawCmdEndChartArrow(styleBox.endLineArrowShaftHeight, styleBox.endLineArrowHeadHeight, styleBox.endLineArrowHeadLength, styleBox.endLineArrowShaftLength, styleBox.borderlineWidth, Color.black, session.getBaseOptions().getAncesScheme().getColor(getIndi().id)));
				chart.addDrawCommand(new DrawCmdPageLink(styleBox.endLineArrowHeadLength + styleBox.endLineArrowShaftLength, styleBox.endLineArrowHeadHeight, this.getIndi()));
				chart.addDrawCommand(new DrawCmdSetFont(arrowFont, Color.black));
				chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x + xOffset), chart.yOffset(y)));
				
				BoxLayout b = new BoxLayout();
				styleBox.layout.convertToSpecificLayout(b);
				
				b.drawEndArrowText(chart, styleBox, options, x, y, this, session, arrowFont);
			}
			
		}
		else
		{
			//TODO get the arrows at the ends of lists drawing. Also, standardize the font with the rest of program
			//TODO change the null on the endchart arrow!
			//If the program has reached here, it's because its drawing the last generation.
			//If there are people beyond that not shown, it will draw an arrow that displays how
			//Many generations and ancestors
			//if(((father != null) || (mother!=null) || ((father == null)? false : (!opgOptions.isCollapsed(getIndi().father))) || ((mother == null)? false : (!opgOptions.isCollapsed(getIndi().mother)))) && options.isDrawEndOfLineArrows())
			if(((father == null)? false : (!opgOptions.isCollapsed(getIndi().father))) || ((mother == null)? false : (!opgOptions.isCollapsed(getIndi().mother))) && options.isDrawEndOfLineArrows())
			{
				Font arrowFont = session.getOpgOptions().getFont().getFont(Font.PLAIN, (float)styleBox.endLineArrowFontSize);
				double xOffset = ((styleBox.isIntruding && options.isAllowIntrusion())?styleBox.intrudeWidth:styleBox.getBoxWidth());
				chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x + xOffset), chart.yOffset(y)));
				chart.addDrawCommand(new DrawCmdEndChartArrow(styleBox.endLineArrowShaftHeight, styleBox.endLineArrowHeadHeight, styleBox.endLineArrowHeadLength, styleBox.endLineArrowShaftLength, styleBox.borderlineWidth, Color.black, session.getBaseOptions().getAncesScheme().getColor(getIndi().id)));
				chart.addDrawCommand(new DrawCmdPageLink(styleBox.endLineArrowHeadLength + styleBox.endLineArrowShaftLength, styleBox.endLineArrowHeadHeight, this.getIndi()));
				chart.addDrawCommand(new DrawCmdSetFont(arrowFont, Color.black));
				chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x + xOffset), chart.yOffset(y)));
				
				BoxLayout b = new BoxLayout();
				styleBox.layout.convertToSpecificLayout(b);
				
				b.drawEndArrowText(chart, styleBox, options, x, y, this, session, arrowFont);
			}
			
		}
		
		if(gen == 0 && !getIndi().originatingPages.isEmpty()){
			ArrayList<String> lines = new ArrayList<String>();
			Font arrowFont = session.getOpgOptions().getFont().getFont(Font.PLAIN, (float)styleBox.endLineArrowFontSize);
			
			for(Individual i : getIndi().getOriginatingPages()){
				lines.add(""+i.pageId);
			}
			chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x), chart.yOffset(y)));
			chart.addDrawCommand(new DrawCmdStartChartArrow(styleBox.endLineArrowShaftHeight, styleBox.endLineArrowHeadHeight, styleBox.endLineArrowHeadLength, styleBox.endLineArrowShaftLength, styleBox.borderlineWidth, Color.black, session.getBaseOptions().getAncesScheme().getColor(getIndi().id), lines, arrowFont));
			
			
			BoxLayout b = new BoxLayout();
			styleBox.layout.convertToSpecificLayout(b);
			b.drawStartArrowText(chart, styleBox, options, x, y, 
					styleBox.endLineArrowShaftLength + styleBox.endLineArrowHeadLength, 
					styleBox.endLineArrowHeadHeight, styleBox.endLineArrowShaftHeight,
					lines, session, arrowFont, getIndi().getOriginatingPages());
		}
		
	}
	
	//-----------------------------------------------------------------------
	
	void drawBox (ChartMargins chart, PresetChartOptions options, double x, double y, double width, double height, OpgSession session)
	{
		setBoxInfo(new Rectangle2D.Double());

		HashMap<String, Single<String>> duplicateLabels = session.getOpgOptions().getDuplicateLabels();
		String duplicateLabel = (duplicateLabels.containsKey(getIndi().id)) ? ("<" + duplicateLabels.get(getIndi().id) + ">") : "";
		BoxLayout b = new BoxLayout();
		styleBox.layout.convertToSpecificLayout(b);
			
		b.drawAnces(chart, options, styleBox, x, y, width, height, 
				getIndi(), duplicateLabel, getBoxInfo(), session);
//		if (duplicateMap.containsKey(getIndi().id))
//			System.out.println(duplicateLabel+": F:"+getIndi().father+" M:"+getIndi().mother);
		

	}
	@Override
	public AncesBoxParent addSpecificBox(Individual indi){return new AncesBox(indi);};
	
	

}
