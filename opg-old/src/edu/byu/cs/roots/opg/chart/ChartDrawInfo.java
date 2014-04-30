package edu.byu.cs.roots.opg.chart;
/*
 * A ChartDrawInfo class contains all of the information necessary to draw a chart.
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.byu.cs.roots.opg.chart.cmds.DrawCmdText;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdTextArc;
import edu.byu.cs.roots.opg.chart.cmds.DrawCommand;
/**
 * This class represents all the draw commands for a chart
 *
 */
public class ChartDrawInfo implements Serializable
{
	private int xExtent; //the horizontal size of the chart
	private int yExtent; //the vertical size of the chart
	private ArrayList<DrawCommand> drawCommands;
	private int yOffset = 0;

	private List<DrawCommand> upSorted = null;
	private List<DrawCommand> downSorted = null;
	private List<DrawCommand> leftSorted = null;
	private List<DrawCommand> rightSorted = null;

	public enum SortDirection { TOP, BOTTOM, LEFT, RIGHT, TEXT };
	
	private class Sorter implements Comparator<DrawCommand>
	{
		
		SortDirection dir;
		public Sorter(SortDirection direction)
		{
			dir = direction;
		}
		
		public int compare(DrawCommand o1, DrawCommand o2) {
			int retValue = 0;
			
			// If I get into this if statement there was a serious error and need to report it.
			if (o1 == null || o2 == null || o1.getShapeBox() == null || o2.getShapeBox() == null)
			{
				System.err.println("A null " + (o1 == null || o2 == null ? "DrawCommand was passed" : "ShapeBox was returned") + " in the comparision for the DrawCommand sort.");
				return -1;
			}
			switch (dir) {
			// Top Bounds
			case TOP:
				retValue = (o1.getShapeBox().y + o1.getShapeBox().height) - 
				(o2.getShapeBox().y + o2.getShapeBox().height); 				
				break;

			// Bottom Bounds
			case BOTTOM:
				retValue = o1.getShapeBox().y - o2.getShapeBox().y; 
				break;
			// Left Bounds
			case LEFT:
				retValue = (o1.getShapeBox().x + o1.getShapeBox().width) - 
				(o2.getShapeBox().x + o2.getShapeBox().width); 
				break;
			// Right Bounds
			case RIGHT:
				retValue = o1.getShapeBox().x - o2.getShapeBox().x; 				
				break;
			case TEXT:
				if (o1 instanceof DrawCmdText || o1 instanceof DrawCmdTextArc) {
					if (o2 instanceof DrawCmdText || o2 instanceof DrawCmdTextArc) {
						retValue = 0;
					}
					else
					{
						retValue = 1;
					}
				}
				else if (o2 instanceof DrawCmdText || o2 instanceof DrawCmdTextArc) {
					retValue = -1;
				}
				else
					retValue = (o1.getZOrder() - o2.getZOrder());
				break;
			}
			return retValue;
		}
		
	}
	
	public List<DrawCommand> getUpSortedDrawCmds()
	{
		return upSorted;
	}
	
	public List<DrawCommand> getDownSortedDrawCmds()
	{
		return downSorted;
	}
	
	public List<DrawCommand> getLeftSortedDrawCmds()
	{
		return leftSorted;
	}
	
	public List<DrawCommand> getRightSortedDrawCmds()
	{
		return rightSorted;
	}
	
	public void updateSortedCharts()
	{
		List<DrawCommand> sortCmds = new LinkedList<DrawCommand>(drawCommands);
		List<DrawCommand> nullCmds = getNonSpaceCmds(sortCmds);
		sortCmds.removeAll(nullCmds);
		upSorted = new LinkedList<DrawCommand>(sortCmds);
		Collections.sort(upSorted, new Sorter(SortDirection.TOP));
		downSorted = new LinkedList<DrawCommand>(sortCmds);
		Collections.sort(downSorted, new Sorter(SortDirection.BOTTOM));
		leftSorted = new LinkedList<DrawCommand>(sortCmds);
		Collections.sort(leftSorted, new Sorter(SortDirection.LEFT));
		rightSorted = new LinkedList<DrawCommand>(sortCmds);
		Collections.sort(rightSorted, new Sorter(SortDirection.RIGHT));
	}
	
	private List<DrawCommand> getNonSpaceCmds(List<DrawCommand> drwCmds) {
		List<DrawCommand> retValue = new LinkedList<DrawCommand>(drwCmds);
		retValue.removeAll(getCompareCollection(false));
		return retValue;
	}
	
	private Collection<DrawCommand> getCompareCollection (boolean getNulls)
	{
		Collection<DrawCommand>retValue = getNulls ? 
				new Collection<DrawCommand>(){
				public boolean add(DrawCommand o) {return false;}
				public boolean addAll(Collection<? extends DrawCommand> c) {return false;}
				public void clear() {}
	
				public boolean contains(Object o) {
					boolean retValue = false;
					if (o instanceof DrawCommand) {
						DrawCommand cmd = (DrawCommand) o;
						retValue = (cmd.getShapeBox() == null);
					}
					return retValue;
				}
	
				public boolean containsAll(Collection<?> c) {return false;}
				public boolean isEmpty() {return false;}
				public Iterator<DrawCommand> iterator() {return null;}
				public boolean remove(Object o) {return false;}
				public boolean removeAll(Collection<?> c) {return false;}
				public boolean retainAll(Collection<?> c) {return false;}
				public int size() {return 0;}
				public Object[] toArray() {return null;}
				public <T> T[] toArray(T[] a) {return null;}} 
		: 
				new Collection<DrawCommand>(){
				public boolean add(DrawCommand o) {return false;}
				public boolean addAll(Collection<? extends DrawCommand> c) {return false;}
				public void clear() {}

				public boolean contains(Object o) {
					boolean retValue = false;
					if (o instanceof DrawCommand) {
						DrawCommand cmd = (DrawCommand) o;
						retValue = (cmd.getShapeBox() != null);
					}
					return retValue;
				}

				public boolean containsAll(Collection<?> c) {return false;}
				public boolean isEmpty() {return false;}
				public Iterator<DrawCommand> iterator() {return null;}
				public boolean remove(Object o) {return false;}
				public boolean removeAll(Collection<?> c) {return false;}
				public boolean retainAll(Collection<?> c) {return false;}
				public int size() {return 0;}
				public Object[] toArray() {return null;}
				public <T> T[] toArray(T[] a) {return null;}};
				
		return retValue;
	}


	static final long serialVersionUID = 1000L;
	
	public ChartDrawInfo(int x, int y)
	{
		xExtent = x;
		yExtent = y;
		drawCommands = new ArrayList<DrawCommand>();
	}
	
	//member variable access and set methods
	public int getXExtent() { return xExtent; }
	
	public int getYExtent()	{ return yExtent; }
	
	
	public void setXExtent(int x) {	xExtent = x; }
	
	public void setYExtent(int y) {	yExtent = y; }
	
	
	//This method adds a drawing command to the list of commands
	public void addDrawCommand(DrawCommand cmd)
	{
		drawCommands.add(cmd);
	}
	
	//returns an iterator over the list of DrawCommands
	public Iterator<DrawCommand> iterator()
	{
		return drawCommands.iterator();
	}
	
	public List<DrawCommand> getDrawCommands(){
		return drawCommands;
	}
	
	
	public String toString()
	{
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < drawCommands.size(); ++i)
		{
			result.append(drawCommands.get(i).toString());
		}
		return result.reverse().toString();
	}
//
//	/**
//	 * @return the boxes
//	 */
//	public ArrayList<MediaBox> getBoxes() {
//		return boxes;
//	}
//
//	/**
//	 * @param boxes the boxes to set
//	 */
//	public void setBoxes(ArrayList<MediaBox> boxes) {
//		this.boxes = boxes;
//	}

	public List<DrawCommand> sortTextCmds(List<DrawCommand> sortCmds) {
		List<DrawCommand> retValue = new LinkedList<DrawCommand>(sortCmds);
		Collections.sort(retValue, new Sorter(SortDirection.TEXT));
		return retValue;
	}
	
	public int getYOffset(){
		return yOffset;
	}
	public void setYOffset(int set){
		yOffset = set;
	}
}
