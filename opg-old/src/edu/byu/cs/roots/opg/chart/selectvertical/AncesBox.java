package edu.byu.cs.roots.opg.chart.selectvertical;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import edu.byu.cs.roots.opg.chart.ShapeInfo;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdFillRect;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdMoveTo;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdRelLineTo;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdRoundRect;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdText;
import edu.byu.cs.roots.opg.model.Event;
import edu.byu.cs.roots.opg.model.Individual;
import edu.byu.cs.roots.opg.model.OpgOptions;
import edu.byu.cs.roots.opg.model.OpgSession;


public class AncesBox extends Box
{
	//tree structure variables
	protected AncesBox father;
	protected AncesBox mother;
	protected Event marriage = null;
	public int maxGensOfTree; //this the maximum number of generations in this individual's tree (1 = self, 2 = self & parent(s), etc.)
	public int maxGensInTree; //this the maximum number of generations in this individual's tree (1 = self, 2 = self & parent(s), etc.)
	int gen; // the generation this box is a part of (zero based)
	int numInGen; //the vertical ordinal number (zero based) of this box in a generation
	int occuranceNum;
	//size/position variables
	double upperSubTreeHeight;
	double lowerSubTreeHeight;
	double hPos,vPos;
	double fatherVOffset;
	double motherVOffset;
	static double scaler;
	double minHeight; //the minimum height for the current configuration of the tree
	double upperRelBoxBound; //the upper bound of the individual box relative to the base position
	double lowerRelBoxBound; //the upper bound of the individual box relative to the base position
	
	static double boxWidth;//: change to some other way of keeping track of the boxes' widths?
	static ArrayList<Double> genBoxHeights;  //box heights of generations
	
	static protected ArrayList<Double> genWidths;
	static protected ArrayList<BoxFormat> genFormats = new ArrayList<BoxFormat>();
	//all duplicate ancestors on the current chart are stored in here by id
	static protected HashMap<String, Integer> duplicateMap = new HashMap<String, Integer>();
	static protected int duplicateIndex  = 1;
	
	Rectangle2D.Double boxInfo = new Rectangle2D.Double();
	
	BoxDrawer drawBox;
	
	public AncesBox(Individual indi)
	{
		this.indi = indi;
		upperBounds = new ArrayList<Double>(1); 
		lowerBounds = new ArrayList<Double>(1);
		drawBox =  new BoxDrawer(BoxFormat.FORMATS.get(7), indi);
	}
	
	/**
	 * Recursively builds a tree of Boxes representing an individual's genealogy without
	 * duplicate subtrees.
	 * @param curGen 
	 * @param genPositions 
	 * 
	 */
	void buildBoxTree(ArrayList<ArrayList<AncesBox>> genPositions, int curGen)
	{		
		maxGensInTree = 1;
		
		if (indi.father != null && indi.father.surname != null)
		{
			//add father
			father = new AncesBox(indi.father);
			//set generational position
			father.gen = curGen;
			if (genPositions.size() <= curGen)
				genPositions.add(new ArrayList<AncesBox>());
			genPositions.get(curGen).add(father);
			father.numInGen = genPositions.get(curGen).size() - 1;
			
			if (!duplicateMap.containsKey(indi.father.id)) //not in tree yet
			{
				duplicateMap.put(indi.father.id, 0); //non-duplicate
				father.buildBoxTree(genPositions, curGen+1);
				maxGensInTree = Math.max(maxGensInTree, father.maxGensInTree + 1);
			}
			else
			{
				duplicateMap.put(indi.father.id, -1); //duplicate
				maxGensInTree +=1;
			}
		}
		if (indi.mother != null && indi.mother.surname != null)
		{
			//add mother
			mother = new AncesBox(indi.mother);
			//set generational position
			mother.gen = curGen;
			if (genPositions.size() <= curGen)
				genPositions.add(new ArrayList<AncesBox>());
			genPositions.get(curGen).add(mother);
			mother.numInGen = genPositions.get(curGen).size() - 1;
			
			if (!duplicateMap.containsKey(indi.mother.id)) //not in tree yet
			{
				duplicateMap.put(indi.mother.id, 0); //non-duplicate
				mother.buildBoxTree(genPositions, curGen+1);
				maxGensInTree = Math.max(maxGensInTree, mother.maxGensInTree + 1);
			}
			else
			{
				duplicateMap.put(indi.mother.id, -1); //duplicate
				maxGensInTree +=1;
			}
		}
		
	}
	
	
	
	/**
	 * Reset the duplicate map and the duplicate index used for labeling
	 **/
	public static void resetDuplicates()
	{
		duplicateMap = new HashMap<String, Integer>();
		duplicateIndex = 1;
	}
	
	
	
	/**
	 * Sets index number for duplicate individual. Expects duplicateMap is already be populated
	 * where an id is mapped to -1 if there is a duplicate, and 0 otherwise. 
	 */
	public void setDuplicateIndex()
	{
		if(duplicateMap.get(indi.id) == -1)
				duplicateMap.put(indi.id, duplicateIndex++); //new duplicate index
	}
	
	
	/**
	 * Recursively calculates the minimum coordinates for this individual
	 * (and its subtree(s)) based on the minimum font size
	 *
	 * @param options - chart options that contain the minimum font size
	 * @param curGen - current generation of Individual in this AncesBox (usually starts as 0)
	 * @param duplicateMap 
	 */
	void calcCoords(int maxGen, int curGen)
	{

		if (curGen > maxGen)
			return;

		drawBox.update(genFormats.get(curGen));
		
		double boxHeight = drawBox.getHeight();
		//double boxHeight = genFormats.get(curGen).getMinHeight();
		
		upperRelBoxBound = drawBox.getHeight()/2;
		lowerRelBoxBound = -drawBox.getHeight()/2 ;
		
		double vertSeperation = 0;//genFormats.get(curGen).getVerticalSpacing(); //: calculate vertical separation
		
		//calculate the dimensions for this box
		upperBounds = new ArrayList<Double>(maxGen - curGen);
		lowerBounds = new ArrayList<Double>(maxGen - curGen);

		//calculate first element of upper bounds array for tight fit
		upperBounds.add(new Double(boxHeight/2.0));
		
		//calculate first element of lower bounds array for tight fit
		lowerBounds.add(new Double( -(boxHeight/2.0 + vertSeperation )));
		upperSubTreeHeight = boxHeight/2.0;
		lowerSubTreeHeight = -upperSubTreeHeight;
		
		int maxGensOfSubTree = 0;
		if (curGen < maxGen)
		{
			if (father != null)
			{
				father.calcCoords(maxGen, curGen+1);
				//keep track of the maximum number of generations in each subTree
					maxGensOfSubTree = Math.max(maxGensOfSubTree, father.maxGensOfTree);
			}
			if (mother != null)
			{
				
				mother.calcCoords(maxGen, curGen+1);
				//keep track of the maximum number of generations in each subTree
				if (mother.maxGensOfTree > maxGensOfSubTree)
					maxGensOfSubTree = mother.maxGensOfTree;
			}
		}
		
		//set the maximun number of generations in this tree
		maxGensOfTree = maxGensOfSubTree + 1;

		//Tight fit (jigsaw puzzle like) intersection
		//calculate the distances between each of the parents by intersecting their subtrees
		if ((father != null || mother != null) && curGen < maxGen)
		{
			if (father == null)
			{
					//add gap onto bottom of lower parent to ensure that different sets of grandparents are spaced father than spouses
					mother.lowerBounds.set(0, mother.lowerBounds.get(0) - vertSeperation);
					motherVOffset = -3;////insert vertical offset here
					//calculate rest of upper and lower bounds array
					for (int i = 0; i < mother.upperBounds.size(); ++i)
						upperBounds.add(new Double(motherVOffset + mother.upperBounds.get(i)));
					for (int i = 0; i < mother.lowerBounds.size(); ++i)
						lowerBounds.add(new Double(motherVOffset + mother.lowerBounds.get(i)));
					
					upperSubTreeHeight = Math.max(upperSubTreeHeight, motherVOffset + mother.upperSubTreeHeight);
					lowerSubTreeHeight = Math.min(lowerSubTreeHeight, motherVOffset + mother.lowerSubTreeHeight);
			}
			else if (mother == null)
			{
					//add gap onto bottom of lower parent to ensure that different sets of grandparents are spaced father than spouses
					father.lowerBounds.set(0, father.lowerBounds.get(0) - vertSeperation);
					fatherVOffset = 3;////insert vertical offset here
					//calculate rest of upper and lower bounds array
					for (int i = 0; i < father.upperBounds.size(); ++i)
						upperBounds.add(new Double(fatherVOffset + father.upperBounds.get(i)));
					for (int i = 0; i < father.lowerBounds.size(); ++i)
						lowerBounds.add(new Double(fatherVOffset + father.lowerBounds.get(i)));
					
					upperSubTreeHeight = Math.max(upperSubTreeHeight, fatherVOffset + father.upperSubTreeHeight);
					lowerSubTreeHeight = Math.min(lowerSubTreeHeight, fatherVOffset + father.lowerSubTreeHeight);
					
			}
			else
			{
				//add gap onto bottom of lower parent to ensure that different sets of grandparents are spaced father than spouses
				mother.lowerBounds.set(0, mother.lowerBounds.get(0) - vertSeperation );
				
				//calculate distance inbetween mother and father and offsets from child
				double dist = calcIntersectDist2( father , mother, curGen);
				fatherVOffset = dist / 2.0;
				motherVOffset = -fatherVOffset;
				
				//calculate rest of upper bounds array
				int i;
				for (i = 0; i < maxGensOfTree - 1 && i < father.upperBounds.size(); ++i)
					upperBounds.add(new Double(fatherVOffset + father.upperBounds.get(i)));
				for ( ; i < maxGensOfTree - 1 && i < mother.upperBounds.size(); ++i)
					upperBounds.add(new Double(motherVOffset + mother.upperBounds.get(i)));
				//calculate rest of lower bounds array
				for (i = 0; i < maxGensOfTree - 1 && i < mother.lowerBounds.size(); ++i)
					lowerBounds.add(new Double(motherVOffset + mother.lowerBounds.get(i)));
				for ( ; i < maxGensOfTree - 1 && i < father.lowerBounds.size(); ++i)
					lowerBounds.add(new Double(fatherVOffset + father.lowerBounds.get(i)));
				
				//upperSubTreeHeight = Math.max(upperSubTreeHeight, upperSubTreeHeight + fatherVOffset + father.upperSubTreeHeight);
				//lowerSubTreeHeight = Math.min(lowerSubTreeHeight, lowerSubTreeHeight + motherVOffset + mother.lowerSubTreeHeight);
				upperSubTreeHeight = Math.max(Math.max(upperSubTreeHeight, fatherVOffset + father.upperSubTreeHeight), motherVOffset + mother.upperSubTreeHeight);
				lowerSubTreeHeight = Math.min(Math.min(lowerSubTreeHeight, motherVOffset + mother.lowerSubTreeHeight), fatherVOffset + father.lowerSubTreeHeight);
			}
			
		}
		

	}
	

	
	//-----------------------------------------------------------------------
	/**
	 * this method finds the closest distance two individuals (with their subtrees) can be while only touchng at
	 * one point (assuming the bounds for all generations are at different distances)
	 * 
	 * @param father - the individual on top
	 * @param mother - the individual on bottom
	 */
	public static double calcIntersectDist(AncesBox father, AncesBox mother, int boxGen)
	{
		double maxDist = 0;
		//int maxGen = Math.min(father.maxGensOfTree, mother.maxGensOfTree);
		int maxGen = Math.min(father.upperBounds.size(), mother.upperBounds.size() );
		int startGen = 0;
		//find the max distance between them (the closest they can be while "intesecting" only at one point)
		for (int i = startGen; i < maxGen; ++i)
		{
			double curDist = mother.upperBounds.get(i) - father.lowerBounds.get(i) + genFormats.get(boxGen+i+1).getVerticalSpacing();
			if (curDist > maxDist)
				maxDist = curDist;
		}
		return maxDist;
	}
	
	/**
	 * Returns the closest distance between two subtrees. Uses the distance between peer generation and the next.
	 */
	public static double calcIntersectDist2(AncesBox father, AncesBox mother, int boxGen)
	{
		double maxDist = 0;
		//int maxGen = Math.min(father.maxGensOfTree, mother.maxGensOfTree);
		int maxGen = Math.min(father.upperBounds.size(), mother.upperBounds.size() );
		int startGen = 0;
		//find the max distance between them (the closest they can be while "intesecting" only at one point)
		for (int i = startGen; i < maxGen; ++i)
		{
			double curDist = mother.upperBounds.get(i) - father.lowerBounds.get(i) + genFormats.get(boxGen+i+1).getVerticalSpacing();
			if(father.upperBounds.size() > i+1)
			{
				double nextDist = mother.upperBounds.get(i) - father.lowerBounds.get(i+1) + genFormats.get(boxGen+i+2).getVerticalSpacing();
				curDist = Math.max(curDist, nextDist);
			}
			if(mother.lowerBounds.size() > i+1)
			{
				double nextDist = mother.upperBounds.get(i+1) - father.lowerBounds.get(i) + genFormats.get(boxGen+i+2).getVerticalSpacing();
				curDist = Math.max(curDist, nextDist);
			}
			if (curDist > maxDist)
				maxDist = curDist;
		}
		return maxDist;
	}
	
	//-----------------------------------------------------------------------
//	public void setRelativePositions(int curGen, int maxGen)
//	{
//		//set father box position relative to this box
//		if (father != null && curGen < maxGen)
//		{
//			father.vPos = vPos + fatherVOffset;
//			father.setRelativePositions(curGen+1, maxGen);
//		}
//		//set mother box position relative to this box
//		if (mother != null)
//		{
//			mother.vPos = vPos + motherVOffset;
//			mother.setRelativePositions(curGen+1, maxGen);
//		}
//	}
	
	//-----------------------------------------------------------------------
	public void setIntrusions(boolean[] canIntrudeGen, int curGen)
	{
		int maxGen = canIntrudeGen.length;
		
		if (curGen < maxGen)
		{
			if (father != null && mother != null)
			{
				//check if there is enough room to intrude
				if (((fatherVOffset*scaler + father.lowerRelBoxBound) - genFormats.get(curGen).getVerticalSpacing()*4 +
						(-motherVOffset*scaler - mother.upperRelBoxBound)) < upperRelBoxBound - lowerRelBoxBound)
					canIntrudeGen[curGen] = false;
			} 
			else
				canIntrudeGen[curGen] = false;
		}
		
		if (father != null && curGen < maxGen)
			father.setIntrusions(canIntrudeGen, curGen+1);
		if (mother != null && curGen < maxGen)
			mother.setIntrusions(canIntrudeGen, curGen+1);
		
	}
	//-----------------------------------------------------------------------
	
	double setHeight(double newHeight)
	{
		//set the scaler so that chart will be the right height
		double curHeight = upperSubTreeHeight - lowerSubTreeHeight;
		//curHeight = Math.
		if (curHeight < minHeight)
			throw new IllegalArgumentException ("Cannot scale chart smaller than minimum");
		scaler = newHeight/minHeight;
		return scaler;
	}
	
	//-----------------------------------------------------------------------
	
	void setScaler(double newScaler)
	{
		if (newScaler < 0.99)
			throw new IllegalArgumentException ("Cannot scale chart smaller than minimum");
		else
			scaler = newScaler;
	}
	
	//-----------------------------------------------------------------------
	
	void drawAncesRootTree(ChartMargins chart, VerticalChartOptions options, ArrayList<ArrayList<AncesBox>> genPositions, double x, double y)
	{
		//calculate box width (constant across all generations for now)
		//boxWidth = (chart.getXExtent()/(options.getAncesGens()+1)) * 0.9;
		//boxWidth = chart.getXExtent() / ((11.0/10.0 * (options.getAncesGens() + options.getDescGens())) + 1 );
		
//		double[] smallestSizePerGen = new double[options.getAncesGens()+1];//keep track of the smallest box size per generation
		boolean[] canIntrudeGen = new boolean[options.getAncesGens()];
		for (int i = 0; i < canIntrudeGen.length; ++i)
			canIntrudeGen[i] = options.isAllowIntrusion();
		setIntrusions(canIntrudeGen, 0);

		
		///-----------------------------------------
		
		//start recursion on self
		if (!(options.getAncesGens() == 0 && options.getDescGens() > 0) ||
				(options.getAncesGens() == 0 && options.isIncludeSpouses()))
			drawAncesTreeRec(0, chart, options, canIntrudeGen, x,y);		
	}
	

	//-----------------------------------------------------------------------
	
	void drawAncesTreeRec(int curGen, ChartMargins chart, VerticalChartOptions options, boolean[] canIntrudeGen, double x, double y)
	{
		//draw self box

		//increase box Width and height to allow intrusion of a box into the next generation
		double myBoxWidth = boxWidth;
		boolean intrudes = false;
		if (curGen < options.getAncesGens() && canIntrudeGen[curGen])
			intrudes = canIntrudeGen[curGen];

		drawBox.setIntrude(intrudes); //update text layout
		
		if (!(curGen == 0 && options.getAncesGens() > 0 && options.getDescGens() > 0) || (options.isIncludeSpouses() && options.getDescGens() > 0))
				drawBox(chart, 0, options, chart.xOffset(x), chart.yOffset(y), myBoxWidth, (upperRelBoxBound - lowerRelBoxBound));
		
		
		if (options.getAncesGens() > curGen)
		{
			if (father != null)
			{
				//draw father (recursively)
				try {
					father.drawAncesTreeRec(curGen+1, chart, options, canIntrudeGen, x + boxWidth*1.1, y + yXForm(fatherVOffset));
				} catch (Exception e) {
					//  Auto-generated catch block
					e.printStackTrace();
				}
				
				//draw lines to father
				if (!intrudes)
				{
					chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x + boxWidth), chart.yOffset(y)));
					chart.addDrawCommand(new DrawCmdRelLineTo(boxWidth*0.05,0.0,1, Color.BLACK));
					chart.addDrawCommand(new DrawCmdRelLineTo(0.0,yXForm(fatherVOffset),1, Color.BLACK));
					chart.addDrawCommand(new DrawCmdRelLineTo(boxWidth*0.05,0.0,1, Color.BLACK));
				} else
				{
					if(curGen==0 && options.getDescGens() == 0){
						chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x + boxWidth/2.0), chart.yOffset(y + upperRelBoxBound)));
						chart.addDrawCommand(new DrawCmdRelLineTo(0.0,yXForm(fatherVOffset)- upperRelBoxBound ,1, Color.black));
						chart.addDrawCommand(new DrawCmdRelLineTo(boxWidth/2.0 + boxWidth*0.1, 0.0,1, Color.black));
					}
					else if (curGen == 0){
						chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x + boxWidth/2.0), chart.yOffset(y)));
						chart.addDrawCommand(new DrawCmdRelLineTo(0.0,yXForm(fatherVOffset) ,1, Color.black));
						chart.addDrawCommand(new DrawCmdRelLineTo(boxWidth/2.0 + boxWidth*0.1, 0.0,1, Color.black));
					}
					else{
						chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x + boxWidth/2.0), chart.yOffset(y + upperRelBoxBound)));
						chart.addDrawCommand(new DrawCmdRelLineTo(0.0,yXForm(fatherVOffset) - father.upperRelBoxBound,1, Color.BLACK));
						chart.addDrawCommand(new DrawCmdRelLineTo(boxWidth/2.0 + boxWidth*0.1, 0.0,1, Color.BLACK));
					}
				}
			}
			if (mother != null)
			{
				//draw mother (recursively)
				mother.drawAncesTreeRec(curGen + 1, chart, options, canIntrudeGen, x + boxWidth*1.1, y + yXForm(motherVOffset));
				
				//draw lines to mother
				if (!intrudes)
				{
					chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x + boxWidth), chart.yOffset(y)));
					chart.addDrawCommand(new DrawCmdRelLineTo(boxWidth*0.05,0.0,1, Color.BLACK));
					chart.addDrawCommand(new DrawCmdRelLineTo(0.0, yXForm(motherVOffset),1, Color.BLACK));
					chart.addDrawCommand(new DrawCmdRelLineTo(boxWidth*0.05,0,1, Color.BLACK));
				} else
				{
					if(curGen == 0 && options.getDescGens() == 0){
						chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x + boxWidth/2.0), chart.yOffset(y + lowerRelBoxBound)));
						chart.addDrawCommand(new DrawCmdRelLineTo(0.0,yXForm(motherVOffset) - lowerRelBoxBound,1, Color.BLACK));
						chart.addDrawCommand(new DrawCmdRelLineTo(boxWidth/2.0 + boxWidth*0.1, 0.0,1, Color.BLACK));
					}
					else if(curGen == 0){
						chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x + boxWidth/2.0), chart.yOffset(y)));
						chart.addDrawCommand(new DrawCmdRelLineTo(0.0,yXForm(motherVOffset),1, Color.BLACK));
						chart.addDrawCommand(new DrawCmdRelLineTo(boxWidth/2.0 + boxWidth*0.1, 0.0,1, Color.BLACK));
					}
					else{
						chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x + boxWidth/2.0), chart.yOffset(y + lowerRelBoxBound)));
						chart.addDrawCommand(new DrawCmdRelLineTo(0.0,yXForm(motherVOffset) - lowerRelBoxBound,1, Color.BLACK));
						chart.addDrawCommand(new DrawCmdRelLineTo(boxWidth/2.0 + boxWidth*0.1, 0.0,1, Color.BLACK));
					}
				}
			}
		}
		
	}
	
	//-----------------------------------------------------------------------
	
	void drawBox (ChartMargins chart, double fontSize, VerticalChartOptions options, double x, double y, double width, double height)
	{
		//Font font = options.getFont().font.deriveFont((float)fontSize);
		//abbreviate and fit individual's info into box
		String dupLabel = (duplicateMap.get(indi.id) != 0)? (" <" + duplicateMap.get(indi.id) + ">") : "";
		
		double totalHeight = height;
		totalHeight = drawBox.getHeight();
		width = drawBox.getWidth();
		
		//update height of ancesBox to match height with text
		//upperRelBoxBound = totalHeight/2.0;
		//lowerRelBoxBound = -upperRelBoxBound;
		
		//144 126 108 90 72 54 36 18
		//DRAW BOX 
		double lineWidth = (options.isBoxBorder())? 1 : 0;
		//chart.addDrawCommand(new DrawCmdRoundRect(x, y-height/2.0, width ,height, 1, 5, Color.BLACK, Color.black));
		//System.out.println(Double.toString(totalHeight)+" "+ Double.toString(height));
		if (options.isRoundedCorners())
			chart.addDrawCommand(new DrawCmdRoundRect(x, y-totalHeight/2.0, width ,totalHeight, lineWidth, 5, Color.BLACK, options.getAncesScheme().getColor(indi.id), boxInfo));
		else
			chart.addDrawCommand(new DrawCmdFillRect(x, y-totalHeight/2.0, width ,totalHeight, lineWidth, Color.BLACK, options.getAncesScheme().getColor(indi.id), boxInfo));

			
		//draw content of box
		drawBox.drawTextBox(chart, x, y, dupLabel);
		
		chart.addDrawCommand(new DrawCmdMoveTo(x-10,y));
		chart.addDrawCommand(new DrawCmdText(drawBox.getBoxFormatIndex()+""));

		
	}
	
	/**
	 * Recursively returns a LinkedList of all ancestors displayed on the chart.
	 * @param the list that will get built through recursion
	 * @param the starting generation, usually 0
	 * @param the max generation to search to, chosen by user
	 * @return a LinkedList of ShapeInfos, storing all visible ancestors
	 */
	public LinkedList<ShapeInfo> getBoxes(LinkedList<ShapeInfo> list, int curGen, int maxGen, OpgSession session)
	{
		//If an ancestor is not supposed to be drawn, still sticks them on the list to check, since their triangle can be clicked, however
		//Sends the current generation in as the max, so it wont recurse further.
		OpgOptions opgOptions = session.getOpgOptions();
		if ((father != null) && (curGen < maxGen))
		{
			if(!opgOptions.isCollapsed(indi.father))
				list = father.getBoxes(list, curGen + 1, maxGen, session);
			else
				list = father.getBoxes(list, maxGen, maxGen, session);
		}
		if ((mother != null) && (curGen < maxGen))
		{
			if(!opgOptions.isCollapsed(indi.mother))
				list = mother.getBoxes(list, curGen + 1, maxGen, session);
			else
				list = mother.getBoxes(list, maxGen, maxGen, session);
		}
		if (boxInfo == null)
			System.out.println("ERROR: Null box info on Ancestor");
		else
			list.add(new ShapeInfo(boxInfo, indi, gen, true));
		return list;
	}
	
	/**
	 * Recursively determines if the passed in point clicks any of the ancestors visible.
	 * @param x coord of click
	 * @param y coord of click
	 * @param current generation, usually 0
	 * @param max generation displayed, chosen by user
	 * @return the ShapeInfo of whoever was clicked, null if no intersect
	 */
	public ShapeInfo checkIntersect(double x, double y, int curGen, int maxGen, OpgSession session)
	{
		if(boxInfo == null){
			System.out.println("debug: WARNING - you tried to find the intersect on"
					+ " an AncesBox for " + indi.namePrefix + ",whose boxInfo == " +
					"null -> this would result nullPointerException and should be fixed!");
			//Andrew - found this bug when looking at my family gedcom file for Elizabeth LaFlesh
			// next time we meet I'll try to recreate it and we can look at it.
			
			return null;
		}
		
		OpgOptions opgOptions = session.getOpgOptions();
		
		ShapeInfo retVal = null;
		if (boxInfo.contains(x, y))
			return new ShapeInfo(boxInfo, indi, gen, true);
		else
		{
			//If an ancestor is not supposed to be drawn, still sticks them on the list to check, since their triangle can be clicked, however
			//Sends the current generation in as the max, so it wont recurse further.
			if ((father != null) && (curGen < maxGen))
			{
				if(!opgOptions.isCollapsed(indi.father))
					retVal = father.checkIntersect(x, y, curGen + 1, maxGen, session);
				else
					retVal = father.checkIntersect(x, y, maxGen, maxGen, session);
			}
			if ((retVal == null) && (mother != null) && (curGen < maxGen))
			{
				if(!opgOptions.isCollapsed(indi.mother))
					retVal = mother.checkIntersect(x, y, curGen + 1, maxGen, session);
				else
					retVal = mother.checkIntersect(x, y, maxGen, maxGen, session);
			}
			return retVal;
		}
		
	}
	
	//-----------------------------------------------------------------------
	
	double yXForm(double y)
	{
		return y * scaler;
	}
	
}