package edu.byu.cs.roots.opg.chart.landscape;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;



import edu.byu.cs.roots.opg.chart.ChartMaker;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdEndChartArrow;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdMoveTo;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdRelLineTo;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdSetFont;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdText;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdTriangle;
import edu.byu.cs.roots.opg.chart.preset.templates.*;
import edu.byu.cs.roots.opg.chart.preset.templates.StylingBox.TextDirection;
import edu.byu.cs.roots.opg.model.Gender;
import edu.byu.cs.roots.opg.model.Individual;
import edu.byu.cs.roots.opg.model.OpgOptions;
import edu.byu.cs.roots.opg.model.OpgSession;
import edu.byu.cs.roots.opg.model.Single;
import edu.byu.cs.roots.opg.util.NameAbbreviator;

public class AncesBox extends AncesBoxParent
{
	private static final long serialVersionUID = 1L;
	ArrayList<String> endLineText;
	
	public AncesBox(Individual indi)
	{
		super(indi);
		endLineText = new ArrayList<String>();
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
		
		endLineText = new ArrayList<String>();
		getIndi().isInTree = true;
		session.addIndiToTree(getIndi());
		//----------------
		int maxGen = options.getAncesGens();
		OpgOptions opgOptions = session.getOpgOptions();
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
		getUpperBounds().add(new Double(boxWidth/2.0 + styleBox.paddingAmount));
		//calculate first element of lower bounds array for tight fit
		getLowerBounds().add(new Double( -(boxWidth/2.0 + vertSeperation + styleBox.paddingAmount)));
		
		upperBoxBound = boxWidth/2.0;
		lowerBoxBound = -upperBoxBound;
		
		upperSubTreeHeight = boxWidth/2.0 + styleBox.paddingAmount;
		lowerSubTreeHeight = -upperSubTreeHeight;
		
		
		int maxGensOfSubTree = 0;
		HashMap<String, Single<String>> duplicateLabels = opgOptions.getDuplicateLabels();
		if (curGen < maxGen)
		{
			if ((father != null) && (!opgOptions.isCollapsed(getIndi().father)))
			{
				//This is where duplicate labels are determined
				if (father.getIndi().isInTree && !duplicateLabels.containsKey(father.getIndi().id))
				{
					duplicateLabels.put(father.getIndi().id, new Single<String>(options.assignDuplicate()+""));
				}
				
				father.calcCoords(options, maker, curGen+1, stylingBoxes, session);
				//keep track of the maximum number of generations in each subTree
				if (father.maxGensOfTree > maxGensOfSubTree)
					maxGensOfSubTree = father.maxGensOfTree;
			}
			if ((mother != null) && (!opgOptions.isCollapsed(getIndi().mother)))
			{
				//This is where duplicate labels are determined
				if (mother.getIndi().isInTree && !duplicateLabels.containsKey(mother.getIndi().id))
				{
					duplicateLabels.put(mother.getIndi().id, new Single<String>(options.assignDuplicate()+""));
				}
				
				mother.calcCoords(options, maker, curGen+1, stylingBoxes, session);
				//keep track of the maximum number of generations in each subTree
				if (mother.maxGensOfTree > maxGensOfSubTree)
					maxGensOfSubTree = mother.maxGensOfTree;
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
					if (getLowerBounds().get(i - gen) > getLowerBounds().get(0))
						getLowerBounds().set(i - gen, getLowerBounds().get(0));
				}
				else 
					getLowerBounds().add(getLowerBounds().get(0));
				
				if (getUpperBounds().size() > (i - gen)){
					if (getUpperBounds().get(i - gen) < getUpperBounds().get(0))
						getUpperBounds().set(i-gen, getUpperBounds().get(0));
					}
				else 
					getUpperBounds().add(getUpperBounds().get(0));
				remainingIntrudeWidth -= (stylingBoxes.getAncesStyle(i).boxHeight + stylingBoxes.getAncesStyle(i).getRelativeOffset());
			
			}
		}
		
		if ((father != null || mother != null) && (((father == null)? false : !opgOptions.isCollapsed(getIndi().father)) || ((mother == null)? false : !opgOptions.isCollapsed(getIndi().mother))) && curGen < maxGen)
		{
			
				
			//if no father is visible
			if ((father == null) || (opgOptions.isCollapsed(getIndi().father)))
			{
					//add gap onto bottom of lower parent to ensure that different sets of grandparents are spaced father than spouses
					mother.getLowerBounds().set(0, mother.getLowerBounds().get(0) - vertSeperation);
					motherVOffset = ((styleBox.isIntruding && options.isAllowIntrusion())?getLowerBounds().get(0) - mother.getUpperBounds().get(0):-3);////insert vertical offset here
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
			else if ((mother == null) || (opgOptions.isCollapsed(getIndi().mother)))
			{
					//add gap onto bottom of lower parent to ensure that different sets of grandparents are spaced father than spouses
					father.getLowerBounds().set(0, father.getLowerBounds().get(0) - vertSeperation);
					fatherVOffset = ((styleBox.isIntruding && options.isAllowIntrusion())?getUpperBounds().get(0) - father.getLowerBounds().get(0):3);////insert vertical offset here
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
					double remainingIntrudeWidth = styleBox.intrudeWidth - (stylingBoxes.getAncesStyle(gen).boxHeight + stylingBoxes.getAncesStyle(gen).getRelativeOffset());
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
						
						remainingIntrudeWidth -= (stylingBoxes.getAncesStyle(i+1).boxHeight + stylingBoxes.getAncesStyle(i+1).getRelativeOffset());
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
			
		}
	}
	
	
	//-----------------------------------------------------------------------
	
	void drawAncesRootTree(ChartMargins chart, LandscapeChartOptions options, double x, double y, OpgSession session)
	{
		//start recursion on self
		if (!(options.getAncesGens() == 0 && options.getDescGens() > 0) ||
				(options.getAncesGens() == 0 && options.isIncludeSpouses()))
			drawAncesTreeRec(0, chart, options, x,y, session);		
	}
	
	

	//-----------------------------------------------------------------------
	@Override
	public void drawAncesTreeRec(int curGen, ChartMargins chart, PresetChartOptions options, double x, double y, OpgSession session)
	{
		//TODO fix intrusion direction for landscape
		//increase box Width and height to allow intrusion of a box into the next generation
		double myBoxWidth = boxWidth;
		//TODO make it take the actual height not the preset
		double myBoxHeight = styleBox.boxHeight;
		boolean intrudes = false;
		OpgOptions opgOptions = session.getOpgOptions();
		if (styleBox.isIntruding && options.isAllowIntrusion())
		{
			myBoxWidth = styleBox.intrudeWidth;
			intrudes = true;
		}
		//TODO remove this
		String chartIndex = "CHART WHAT?!";
		
				
		if (!(curGen == 0 && options.getAncesGens() > 0 && options.getDescGens() > 0) || (options.isIncludeSpouses() && options.getDescGens() > 0))
			drawBox(chart, options, chart.xOffset(x), chart.yOffset(y), (upperBoxBound - lowerBoxBound), myBoxHeight, chartIndex, session);
		
		
		if (options.getAncesGens() > curGen)
		{
			double xsepvalue = styleBox.getRelativeOffset();
			//TODO Put the else statements for drawing collapsed ancestor info into the normal if statement
			if ((father != null) && !opgOptions.isCollapsed(getIndi().father))
			{
				
				//draw father (recursively)
				try {
					father.drawAncesTreeRec(curGen+1, chart, options, x + yXForm(-fatherVOffset), y + styleBox.boxHeight + xsepvalue, session);
				} catch (Exception e) {
					//  Auto-generated catch block
					e.printStackTrace();
				}
				
				//draw lines to father
				if (!intrudes)
				{
					chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x), chart.yOffset(y + myBoxHeight)));
					chart.addDrawCommand(new DrawCmdRelLineTo(0.0,xsepvalue/2.0,1, Color.BLACK));
					chart.addDrawCommand(new DrawCmdRelLineTo(yXForm(fatherVOffset),0.0,1, Color.BLACK));
					chart.addDrawCommand(new DrawCmdRelLineTo(0.0,xsepvalue/2.0,1, Color.BLACK));
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
			else if (father != null)
			{
				
				chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x + styleBox.getBoxWidth()), chart.yOffset(y)));
				chart.addDrawCommand(new DrawCmdTriangle(upperBoxBound,upperBoxBound,3, Color.black, session.getBaseOptions().getAncesScheme().getColor(getIndi().id), father.getBoxInfo()));
				chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x + styleBox.getBoxWidth()), chart.yOffset(y)));
				chart.addDrawCommand(new DrawCmdSetFont(new Font("Vera.ttf", Font.PLAIN, (int)upperBoxBound - 1), Color.black));
				chart.addDrawCommand(new DrawCmdText("" + father.maxGensInTree + " " + (getIndi().father.numberOfAncestors + 1))); //the plus one is to count the father himself
				
			}
			if ((mother != null) && !opgOptions.isCollapsed(getIndi().mother))
			{
				//draw mother (recursively)
				mother.drawAncesTreeRec(curGen + 1, chart, options, x + yXForm(-motherVOffset), y + styleBox.boxHeight + xsepvalue, session);
				
				//draw lines to mother
				if (!intrudes)
				{
					chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x), chart.yOffset(y + myBoxHeight)));
					chart.addDrawCommand(new DrawCmdRelLineTo(0.0,xsepvalue/2.0,1, Color.BLACK));
					chart.addDrawCommand(new DrawCmdRelLineTo(yXForm(motherVOffset),0.0,1, Color.BLACK));
					chart.addDrawCommand(new DrawCmdRelLineTo(0.0,xsepvalue/2.0,1, Color.BLACK));
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
			else if (mother != null)
			{
				
				chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x + styleBox.getBoxWidth()), chart.yOffset(y) - upperBoxBound));
				chart.addDrawCommand(new DrawCmdTriangle(upperBoxBound,upperBoxBound,3, Color.black, session.getBaseOptions().getAncesScheme().getColor(getIndi().id), mother.getBoxInfo()));
				chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x + styleBox.getBoxWidth()), chart.yOffset(y) - upperBoxBound));
				chart.addDrawCommand(new DrawCmdSetFont(new Font("Vera.ttf", Font.PLAIN, (int)upperBoxBound - 1), Color.black));
				chart.addDrawCommand(new DrawCmdText("" + mother.maxGensInTree + " " + (getIndi().mother.numberOfAncestors + 1))); //the plus one is to count the father himself
				
			}
		}
		else
		{
			//TODO get the arrows at the ends of lists drawing. Also, standardize the font with the rest of program
			//TODO change the null on the endchart arrow!
			//If the program has reached here, it's because its drawing the last generation.
			//If there are people beyond that not shown, it will draw an arrow that displays how
			//Many generations and ancestors
			if(((father != null) || (mother!=null) || ((father == null)? false : !opgOptions.isCollapsed(getIndi().father)) || ((mother == null)? false : !opgOptions.isCollapsed(getIndi().mother))) && options.isDrawEndOfLineArrows())
			{
				Font arrowFont = session.getOpgOptions().getFont().getFont(Font.PLAIN, (float)styleBox.endLineArrowFontSize);
				double arrowFontHeight = arrowFont.getStringBounds("GP", NameAbbreviator.frc).getHeight();
				double spaceDistance = ((2*upperBoxBound - 2*arrowFontHeight) / 3.0);
				
				chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x + styleBox.getBoxWidth()), chart.yOffset(y)));
				chart.addDrawCommand(new DrawCmdEndChartArrow(styleBox.endLineArrowShaftHeight, styleBox.endLineArrowHeadHeight, styleBox.endLineArrowHeadLength, styleBox.endLineArrowShaftLength, styleBox.borderlineWidth, Color.black, session.getBaseOptions().getAncesScheme().getColor(getIndi().id)));
				chart.addDrawCommand(new DrawCmdSetFont(arrowFont, Color.black));
				chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x + styleBox.getBoxWidth()), chart.yOffset(y + (upperBoxBound - (spaceDistance+arrowFontHeight)))));
			
				BoxLayout b = new BoxLayout();
				styleBox.layout.convertToSpecificLayout(b);
				String[] inps = new String[endLineText.size()];
				for (int i = 0; i < endLineText.size(); i++)
					inps[i] = endLineText.get(i);
				
				b.drawEndArrowText(chart, styleBox, options, x, y, inps, this, session, arrowFont);

			}
			
			
		}
		
	}
	
	//-----------------------------------------------------------------------
	
	void drawBox (ChartMargins chart, PresetChartOptions options, double x, double y, 
			double width, double height, String chartIndex, 
			OpgSession session)
	{
		setBoxInfo(new Rectangle2D.Double());

		HashMap<String, Single<String>> duplicateLabels = session.getOpgOptions().getDuplicateLabels();
		String duplicateLabel = (duplicateLabels.containsKey(getIndi().id)? ("<" + duplicateLabels.get(getIndi().id) + ">") : "");
		BoxLayout b = new BoxLayout();
		styleBox.layout.convertToSpecificLayout(b);
		b.drawAnces(chart, options, styleBox, x, y, width, height, 
				getIndi(), duplicateLabel, getBoxInfo(), chartIndex, session);
		
	}
		
	@Override
	public AncesBoxParent addSpecificBox(Individual indi){return new AncesBox(indi);};
}
