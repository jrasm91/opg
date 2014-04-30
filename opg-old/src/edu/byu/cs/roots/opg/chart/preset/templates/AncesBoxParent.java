package edu.byu.cs.roots.opg.chart.preset.templates;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import edu.byu.cs.roots.opg.chart.ChartMaker;
import edu.byu.cs.roots.opg.chart.ShapeInfo;
import edu.byu.cs.roots.opg.model.Event;
import edu.byu.cs.roots.opg.model.Individual;
import edu.byu.cs.roots.opg.model.OpgOptions;
import edu.byu.cs.roots.opg.model.OpgSession;


public class AncesBoxParent extends Box{
	private static final long serialVersionUID = 1L;
	//tree structure variables
	public AncesBoxParent father;
	public AncesBoxParent mother;
	protected Event marriage = null;
	public int maxGensOfTree; //this the maximum number of generations in this individual's tree (1 = self, 2 = self & parent(s), etc.)
	public int maxGensInTree; //this the maximum number of generations in this individual's tree (1 = self, 2 = self & parent(s), etc.)
	public int maxVisibleGensInTree; //duplicate of variable above, only it only follows the boxes that can be seen by not being collapsed
	public int gen; // the generation this box is a part of (zero based)
	protected int numInGen; //the vertical ordinal number (zero based) of this box in a generation
		
	
	public double intersectUpperHeight = 0.0;
	public double intersectLowerHeight = 0.0;
	//size/position variables
	/**
	 * Distance from origin to the top most ancestor of this box
	 * (The highest point of the entire subtree, including this box)
	 */
	public double upperSubTreeHeight;
	/**
	 * Distance from origin to the bottom most ancestor of this box
	 * (The lowest point of the entire subtree, including this box)
	 */
	public double lowerSubTreeHeight;
	protected double hPos;
	public double vPos;
	/**
	 * The fathers vertical offset from the centre of this indi
	 */
	public double fatherVOffset;
	/**
	 * the mothers vertical offset from the centre of this indi
	 */
	public double motherVOffset;
	protected static double scaler;
	public double minHeight; //the minimum height for the current configuration of the tree
	
	/**the upper bound of the individual box relative to the base position. Used to draw the box.
	 */
	public double upperBoxBound;
	
	/**the lower bound of the individual box relative to the base position. Used to draw the box.
	 */	
	public double lowerBoxBound; 
	
	protected double boxWidth;
	
	private Rectangle2D.Double boxInfo; //this is where the coords and size are stored
	
	public AncesBoxParent(Individual indi)
	{
		this.setIndi(indi);
		setUpperBounds(new ArrayList<Double>(1)); 
		setLowerBounds(new ArrayList<Double>(1));
		setBoxInfo(new Rectangle2D.Double());
	}
	
	/**
	 * Recursively builds a tree of Boxes representing an individual's genealogy without
	 * duplicate subtrees.  Note, the tree has no coordinates yet.
	 * @param curGen 
	 * @param genPositions 
	 * 
	 */
	public void buildBoxTree(int curGen, OpgSession session)
	{		
		maxGensInTree = 1;
		getIndi().numberOfAncestors = 0;
		OpgOptions opgOptions = session.getOpgOptions();
		HashMap<String,String> duplicateMap = opgOptions.getDuplicateMap();
		session.addIndiToTree(getIndi());
		
		if (getIndi().father != null)
		{
			//add father
			father = addSpecificBox(getIndi().father);
			//set generational position
			father.gen = curGen;
						
			//if father hasn't appeared yet, trace the rest of the line. If he has, stop here
			if (!getIndi().father.isInTree)
			{
				getIndi().father.isInTree = true;
				father.buildBoxTree(curGen+1, session);
				maxGensInTree = (maxGensInTree < father.maxGensInTree + 1)? father.maxGensInTree + 1: maxGensInTree;
				getIndi().numberOfAncestors += getIndi().father.numberOfAncestors + 1;
			}
			else
			{
				maxGensInTree +=1;
				if (!duplicateMap.containsKey(getIndi().father.id))
					duplicateMap.put(getIndi().father.id, "");
			}
			
			//if(opgOptions.isCollapsed(getIndi().father))
			//	maxVisibleGensInTree = curGen - 2;
			//else
				maxVisibleGensInTree = (maxVisibleGensInTree < father.maxVisibleGensInTree + 1)? father.maxVisibleGensInTree + 1: maxVisibleGensInTree;
			
			
		}
		if (getIndi().mother != null)
		{
			//add mother
			mother = addSpecificBox(getIndi().mother);
			//set generational position
			mother.gen = curGen;
						
			//if mother hasn't appeared yet, trace the rest of the line. If she has, stop here
			if (!getIndi().mother.isInTree)
			{
				getIndi().mother.isInTree = true;
				mother.buildBoxTree(curGen+1, session);
				maxGensInTree = (maxGensInTree < mother.maxGensInTree + 1)? mother.maxGensInTree  + 1: maxGensInTree;
				getIndi().numberOfAncestors += getIndi().mother.numberOfAncestors + 1;
			}
			else
			{
				maxGensInTree +=1;
				if (!duplicateMap.containsKey(getIndi().mother.id))
					duplicateMap.put(getIndi().mother.id, "");
			}
			
			//if(opgOptions.isCollapsed(getIndi().father))
			//	maxVisibleGensInTree = curGen - 2;
			//else
				maxVisibleGensInTree = (maxVisibleGensInTree < mother.maxVisibleGensInTree + 1)? mother.maxVisibleGensInTree + 1: maxVisibleGensInTree;
			
		}
		
		if(duplicateMap.containsKey(getIndi().id))
		{
			if (curGen != 1)
				maxVisibleGensInTree = 0;
			else
			{
				if(mother != null && father != null)
					maxVisibleGensInTree = Math.max(father.maxVisibleGensInTree, mother.maxVisibleGensInTree)+1;
				else if(mother != null)
					maxVisibleGensInTree = mother.maxVisibleGensInTree+1;
				else if(father != null)
					maxVisibleGensInTree = father.maxVisibleGensInTree+1;
				else
					maxVisibleGensInTree = 0;
			}
				
		}
	}
	//Methods that MUST be inherited and overriden by the subclass AncesBoxes
	public void calcCoords(PresetChartOptions options, ChartMaker maker, int curGen, StylingBoxScheme stylingBoxes, OpgSession session){};
	public void drawAncesTreeRec(int curGen, ChartMargins chart, PresetChartOptions options, double x, double y, OpgSession session){};
	public AncesBoxParent addSpecificBox(Individual indi){return null;};
	//-----------------------------------------------------------------------
	/**
	 * this method finds the closest distance two individuals (with their subtrees) can be while only touching at
	 * one point (assuming the bounds for all generations are at different distances)
	 * 
	 * @param father - the individual on top
	 * @param mother - the individual on bottom
	 */
	public double calcIntersectDist(AncesBoxParent father, AncesBoxParent mother)
	{
		double maxDist = 0;
		int maxGen = Math.min(father.getUpperBounds().size(), mother.getUpperBounds().size() );
		
		//find the max distance between them (the closest they can be while "intersecting" only at one point)
		for (int i = 0; i < maxGen; ++i)
		{	
			double curDist = mother.getUpperBounds().get(i) - father.getLowerBounds().get(i);
			
			if (curDist > maxDist)
				maxDist = curDist;
		}
		return maxDist;
	}
	
	//-----------------------------------------------------------------------
	public void setRelativePositions(int curGen, int maxGen)
	{
		//set father box position relative to this box
		if (father != null && curGen < maxGen)
		{
			father.vPos = vPos + fatherVOffset;
			father.setRelativePositions(curGen+1, maxGen);
		}
		//set mother box position relative to this box
		if (mother != null)
		{
			mother.vPos = vPos + motherVOffset;
			mother.setRelativePositions(curGen+1, maxGen);
		}
	}
	
	//-----------------------------------------------------------------------
	
	double setHeight(double newHeight)
	{
		//set the scaler so that chart will be the right height
		double curHeight = upperSubTreeHeight - lowerSubTreeHeight;
		if (curHeight < minHeight)
			throw new IllegalArgumentException ("Cannot scale chart smaller than minimum");
		scaler = newHeight/minHeight;
		return scaler;
	}
	
	//-----------------------------------------------------------------------
	
	public void setScaler(double newScaler)
	{
		if (newScaler < 0.99)
			throw new IllegalArgumentException ("Cannot scale chart smaller than minimum");
		else
			scaler = newScaler;
	}
	
//-----------------------------------------------------------------------
	
	/**
	 * Recursively returns a LinkedList of all ancestors displayed on the chart.
	 * @param the list that will get built through recursion
	 * @param the starting generation, usually 0
	 * @param the max generation to search to, chosen by user
	 * @return a LinkedList of ShapeInfos, storing all visible ancestors
	 */
	public LinkedList<ShapeInfo> getBoxes(LinkedList<ShapeInfo> list, int curGen, int maxGen, OpgSession session)
	{
		OpgOptions opgOptions = session.getOpgOptions();
		//If an ancestor is not supposed to be drawn, still sticks them on the list to check, since their triangle can be clicked, however
		//Sends the current generation in as the max, so it wont recurse further.
		if ((father != null) && (curGen < maxGen))
		{
			if(!opgOptions.isCollapsed(getIndi().father))
				list = father.getBoxes(list, curGen + 1, maxGen, session);
			else
				list = father.getBoxes(list, maxGen, maxGen, session);
		}
		if ((mother != null) && (curGen < maxGen))
		{
			if(!opgOptions.isCollapsed(getIndi().mother))
				list = mother.getBoxes(list, curGen + 1, maxGen, session);
			else
				list = mother.getBoxes(list, maxGen, maxGen, session);
		}
		if (getBoxInfo() == null)
			System.out.println("ERROR: Null box info on Ancestor");
		else
			list.add(new ShapeInfo(getBoxInfo(), getIndi(), gen, true));
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
		if(getBoxInfo() == null){
			System.out.println("debug: WARNING - you tried to find the intersect on"
					+ " an AncesBox for " + getIndi().namePrefix + ",whose boxInfo == " +
					"null -> this would result nullPointerException and should be fixed!");
			//Andrew - found this bug when looking at my family gedcom file for Elizabeth LaFlesh
			// next time we meet I'll try to recreate it and we can look at it.
			
			return null;
		}
		OpgOptions opgOptions = session.getOpgOptions();
		ShapeInfo retVal = null;
		if (getBoxInfo().contains(x, y))
			return new ShapeInfo(getBoxInfo(), getIndi(), gen, true);
		else
		{
			//If an ancestor is not supposed to be drawn, still sticks them on the list to check, since their triangle can be clicked, however
			//Sends the current generation in as the max, so it wont recurse further.
			if ((father != null) && (curGen < maxGen))
			{
				if(!opgOptions.isCollapsed(getIndi().father))
					retVal = father.checkIntersect(x, y, curGen + 1, maxGen, session);
				else
					retVal = father.checkIntersect(x, y, maxGen, maxGen, session);
			}
			if ((retVal == null) && (mother != null) && (curGen < maxGen))
			{
				if(!opgOptions.isCollapsed(getIndi().mother))
					retVal = mother.checkIntersect(x, y, curGen + 1, maxGen, session);
				else
					retVal = mother.checkIntersect(x, y, maxGen, maxGen, session);
			}
			return retVal;
		}
		
	}
	
	protected double yXForm(double y)
	{
		return y * scaler;
	}
	
	public void setBoxStyle(StylingBoxScheme scheme){
		if(father!=null)
			father.setBoxStyle(scheme);
		if(mother!=null)
			mother.setBoxStyle(scheme);
		
		if(gen >= scheme.AncesByGenList.size())
			scheme.increaseAncesList(gen);
		styleBox = scheme.getAncesStyle(gen);
		styleBox.resetTemporaries();
	}

	public void setBoxInfo(Rectangle2D.Double boxInfo) {
		this.boxInfo = boxInfo;
	}

	public Rectangle2D.Double getBoxInfo() {
		return boxInfo;
	}
	
}
