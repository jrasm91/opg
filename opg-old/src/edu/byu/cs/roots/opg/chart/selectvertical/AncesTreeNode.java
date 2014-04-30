package edu.byu.cs.roots.opg.chart.selectvertical;

import java.awt.Color;
import java.awt.Font;
import java.awt.font.FontRenderContext;

import edu.byu.cs.roots.opg.chart.cmds.DrawCmdFillRect;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdMoveTo;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdSetFont;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdText;
import edu.byu.cs.roots.opg.model.Individual;
import edu.byu.cs.roots.opg.util.NameAbbreviator;

/**
 * This contains the information needed to draw this Node on 
 * a tree in the chart.
 * @author thedqs
 * @version 0.0.1
 * @category TreeCreation
 */
public class AncesTreeNode {
	// Constants
	/** The Width of the box */
	static double boxWidth = 144; 
	/** Width of each column */
	static final double columnWidth = 11 / 10 * boxWidth;
	/** Width of the space between boxes */
	static final double spaceWidth = columnWidth - boxWidth;
	
	// Global variables set the AncesTree
	/** The height of the chart used in FillUp and FillDown */
	static double chartHeight = 0;
	/** The width of the chart used in FillUp and FillDown */
	static double chartWidth = 0;
	
	/** The individual of this node */
	Individual nodeIndi;
	
	/** The father of this node */
	AncesTreeNode father;
	
	/** The mother of this node */
	AncesTreeNode mother;
	
	/** This is the height of the father and his subtree */
	double fatherHeight;
	
	/** This is the bottom portion of the father from the midpoint.
	 * This is used for positioning the mother right under the father */
	double fatherBottom;

	/** This is the bottom portion of the father from the midpoint.
	 * This is used for positioning the father right above the mother */
	double motherTop;
	
	/** This is the height of the mother and her subtree */
	double motherHeight;

	/** This is the height of our tree and subtrees */
	double treeHeight;
	
	/** This is the middle point of the tree's height or in
	 * other words where this node should be placed in
	 * respect with the 0 height. 
	 */
	double treeMiddle;
	
	/**
	 * Create a node based on the individual passed to us
	 * @param indi - The individual to make this node for.
	 * @param curGen - The current number of generations
	 * we have already traversed.
	 * @param maxGen - The maximum number of generations 
	 * we will traverse.
	 */
	public AncesTreeNode(Individual indi, int curGen, int maxGen) {
		if (indi.father != null && curGen < maxGen)
			father = new AncesTreeNode(indi.father, curGen + 1, maxGen);
		else
			father = null;
		
		if (indi.mother != null && curGen < maxGen)
			mother = new AncesTreeNode(indi.mother, curGen + 1, maxGen);
		else
			mother = null;
		
		motherHeight = (mother == null) ? 0 : mother.GetHeight();
		fatherHeight = (father == null) ? 0 : father.GetHeight();
		motherTop = motherHeight - (mother == null ? 0 : mother.GetMiddle());
		fatherBottom = father == null ? 0 : father.GetMiddle();
		double upHeight = (fatherHeight + motherHeight) / 2;
		double downHeight = upHeight;
		
		// TO DO: Calculate the Max Height we need here and use it instead of just 2/9ths of an inch
		treeHeight = upHeight + downHeight;
		treeHeight = Math.max(treeHeight, 16);
		
		treeMiddle = Math.max(motherHeight, 8);
		
		nodeIndi = indi;
	}
	
	/**
	 * Retreive the height of the node and its subtrees
	 * @return The height of this subtree
	 */
	public double GetHeight() {
		return treeHeight;
	}
	
	/**
	 * Retrieve the middle of the tree based on 0 being 
	 * the bottom of the height
	 * @return The middle of the tree, or where this node
	 * should be placed.
	 */
	public double GetMiddle() {
		return treeMiddle;
	}

	/**
	 * Draw this box onto the chart relative to the Y 
	 * and curGen we receive.
	 * @param chart - The chart to draw to
	 * @param curGen - The current generation we are on
	 * @param y - The Y coordinate that we are building on
	 */
	public void DrawBox(ChartMargins chart, int curGen, double y)
	{
		// Margin from the top of the chart
		double verticalMargin = 150;
		// Margin of space for the text in each box
		double textMargin = 10;
		// The height to the base of our box
		double baseHeight = -(verticalMargin + y + treeHeight - treeMiddle);
		// The height of our box
		double boxHeight = Math.min(treeHeight, boxWidth);
		
		// Get the name to display in the box
		String indiName = nodeIndi.givenName.trim() + " " +nodeIndi.surname.trim(); 
		
		// Set up the height of the font for this box based on the name
		int fontMin = 4;
		int fontMax = (int)(boxHeight / 4);
		int fontHeight = Math.min(Math.max(fontMin, (int)(treeHeight / 4)), fontMax);
		// The font conext uses the identity matrix transformation, is not anti-aliasing and uses
		// fractional matrices.
		FontRenderContext fontContext = NameAbbreviator.frc;
		Font min = new Font("Times New Roman", Font.ITALIC, fontHeight);
		// Get the size down until we can fit the name into the box
		while (min.getStringBounds(indiName, fontContext).getWidth() > (boxWidth - 2 * textMargin)&& 
				fontHeight >= fontMin)
		{
			min = new Font("Times New Roman", Font.ITALIC, --fontHeight);
		}
		
		// Draw the parents first and build the tree from the end to the beginning.
		if (father != null)
			father.DrawBox(chart, curGen + 1, 
					(mother != null) ? y - (motherHeight + father.GetMiddle()) : y);
		if (mother != null)
			mother.DrawBox(chart, curGen + 1, 
					(father != null) ? y + (fatherHeight + (motherHeight - mother.GetMiddle())) : y);
		
		// Draw the box and the text inside the box
		chart.addDrawCommand(new DrawCmdFillRect(curGen * columnWidth, baseHeight, 
				boxWidth, boxHeight , 1, Color.BLACK, Color.LIGHT_GRAY, null));
		chart.addDrawCommand(new DrawCmdMoveTo(curGen * columnWidth + textMargin, baseHeight + boxHeight - fontHeight));
		chart.addDrawCommand(new DrawCmdSetFont(min, Color.BLACK));
		chart.addDrawCommand(new DrawCmdText(indiName));
	}
	
	/**
	 * Squishes all the boxes to the top since their current height
	 * is the min height then we can see how much room we have to squish
	 * and then squish them all up there. Uses chartHeight 
	 * @param curGen - The current generation we are
	 * @param arySpace - The current filled array of space.
	 */
	public void FillUp(int curGen, double[] arySpace) {
		
	}
}
