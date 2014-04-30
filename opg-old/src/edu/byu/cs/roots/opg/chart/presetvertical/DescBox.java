package edu.byu.cs.roots.opg.chart.presetvertical;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.cs.roots.opg.chart.cmds.DrawCmdFillRect;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdMoveTo;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdRelLineTo;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdRoundRect;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdText;
import edu.byu.cs.roots.opg.model.Family;
import edu.byu.cs.roots.opg.model.Gender;
import edu.byu.cs.roots.opg.model.Individual;

/**
 * Represents box of a decendency tree.
 * Note: Not fully implemented
 */
public class DescBox extends Box
{
	ArrayList<DescBox> children;
	Map<String,MarriageBox> childMarriages;
	Family family;
	boolean maleDescendant;
	Individual spouse;
	public List<Double> vOffsets;
	public double framesepvalue = 2;//This is the tiny seperation for the distance between the colored fill-in of the box and the frame
	public MarriageBox mContainer;
	
	Rectangle2D.Double boxInfo = new Rectangle2D.Double();

	
	public DescBox(Individual indi, Family fam)
	{
		super(indi);
		setFamily(fam);
		vOffsets = new ArrayList<Double>();
		children = new ArrayList<DescBox>();
		childMarriages = new HashMap<String,MarriageBox>();
		if (indi.gender == Gender.MALE)
			maleDescendant = true;
		//drawBox = new BoxDrawer(BoxFormat.getDefault(),indi);

	}
	
	public DescBox(Individual indi)
	{
		super(indi);
		setFamily(null);
		vOffsets = new ArrayList<Double>();
		children = new ArrayList<DescBox>();
		childMarriages = new HashMap<String,MarriageBox>();
		if (indi.gender == Gender.MALE)
			maleDescendant = true;
		//drawBox = null;//new BoxDrawer(BoxFormat.getDefault(),indi);

	}
	
	private void setFamily(Family fam) {
		family = fam;
		spouse = null;
		if(family != null) {
			if(indi.gender == Gender.MALE) 
				spouse = family.wife;
			else
				spouse = family.husband;
		}
	}
	

	public void addChild(DescBox child) {	
		if(child != null &&!children.contains(child)) {
			children.add(child);
			if(childMarriages.containsKey(child.indi.id))
				childMarriages.get(child.indi.id).addMarriage(child);
			else {
				MarriageBox m = new MarriageBox();
				m.addMarriage(child);
				childMarriages.put(child.indi.id, m);
			}
		}
	}
	
	protected void drawSubTree(ChartMargins chart, VerticalChartOptions options, double x, double y)
	{
		//double boxWidth = getWidth(); 
		
		if (Math.abs(gen.getGenNum()) > 0)
			drawBox(chart, 0, options, chart.xOffset(x), chart.yOffset(y));
		
//		for(MarriageBox m : childMarriages.values())
//			m.restDraw();
		
		/*if (options.getDescGens() > Math.abs(gen.getGenNum()))
		{
			
			for(int i=0; i < children.size(); i++) {
				DescBox d = children.get(i);
				//drawChildConnector(chart,x,y,vOffsets.get(i));
				//childMarriages.get(d.indi.id).draw(chart,options,chart.xOffset(x - boxWidth-genGap()),chart.yOffset(y + vOffsets.get(i)));
				children.get(i).drawSubTree(chart, options, x - boxWidth-genGap(), y + vOffsets.get(i));
			}
			
		}*/
		
	}
	
	//-----------------------------------------------------------------------
	
	protected void drawBox (ChartMargins chart, double fontSize, VerticalChartOptions options, double x, double y)
	{
		//abbreviate and fit individual's info into box
		/*String dupLabel = (dupIndex != 0)? (" <" + dupIndex + ">") : "";
		
		//drawBox.update(boxFormat);
		double totalHeight = getHeight();
		double width = getWidth();
		
		//DRAW BOX 
		double lineWidth = (options.isBoxBorder())? 1 : 0;
		//chart.addDrawCommand(new DrawCmdRoundRect(x, y-height/2.0, width ,height, 1, 5, Color.BLACK, Color.black));
		//System.out.println(Double.toString(totalHeight)+" "+ Double.toString(height));
		if (options.isRoundedCorners())
			chart.addDrawCommand(new DrawCmdRoundRect(x, y-totalHeight/2.0, width ,totalHeight, lineWidth, 5, Color.BLACK, options.getDescScheme().getColor(indi.id)));
		else
			chart.addDrawCommand(new DrawCmdFillRect(x, y-totalHeight/2.0, width ,totalHeight, lineWidth, Color.BLACK, options.getDescScheme().getColor(indi.id)));

			
		//draw content of box
		//drawBox.drawTextBox(chart, x, y, dupLabel);
		//System.out.println(indi.toString());
		
		//draw content of box	copied from AncesBox  CODE DUPLICATION
		 ArrayList<LineLayout> lines = BoxLayoutManager.instance().getLineLayouts(boxFormat, indi, dupLabel);
		lines = BoxLayoutManager.instance().getLineLayouts(boxFormat, indi, dupLabel);
		//draw dynamic text
		//Draw each line
		Font font = boxFormat.getFont();
		double linepos = y + totalHeight/2.0 - font.getSize2D();
		double hPad = 0/*boxFormat.getHorizontalOffset()* /;
		for(LineLayout l : lines){
			l.draw(chart.getChart(), boxFormat.getNameFontSize(), BoxFormat.getOpgFont(), x+hPad, linepos, getWidth() - 2*hPad , indi, dupLabel);
			if(l.containsName())
				linepos -= boxFormat.getNameLineHeight();
			else
				linepos -= boxFormat.getBodyLineHeight();
		}
		
		chart.addDrawCommand(new DrawCmdMoveTo(x-10,y));
		chart.addDrawCommand(new DrawCmdText(boxFormat.boxIndex+""));
		*/
	}
	
	protected void drawMarriageBoxes (ChartMargins chart, VerticalChartOptions options, double x, double y)
	{
		chart.addDrawCommand(new DrawCmdMoveTo(x,y));
		chart.addDrawCommand(new DrawCmdText("x"));
		DescBox current = null;
		double height = 0;
		double width = 0;
		double childY = 0;
		double childX = x - getWidth() -genGap();
		//initial child marriage
		if(!children.isEmpty()) {
			current = children.get(0);
			childY = y + vOffsets.get(0);
			height = children.get(0).getHeight();
			width = children.get(0).getWidth();
		}
		//draw children marriages
		for(int i=1; i < children.size(); i++) {
			DescBox c = children.get(i);
			if(current.indi.toString() == c.indi.toString()) {
				height += c.getHeight();
				width += c.getWidth();
				childY -= current.getHeight()/2 -c.getVerticalSpace();
			}
			else { 
				drawMarriageBox(chart,options,height,width,childX,childY);
				childY = y + vOffsets.get(i);
				height = c.getHeight();
				width = c.getWidth();
			}
			current = c;
		}
		drawMarriageBox(chart,options,height,width,childX,childY);
	}
	
	private void drawMarriageBox(ChartMargins chart, VerticalChartOptions options, double height, double width, double x, double y) {
		
		double lineWidth = (options.isBoxBorder())? 1 : 0;
		if (options.isRoundedCorners())
			chart.addDrawCommand(new DrawCmdRoundRect(x, y-height/2.0, width ,height, lineWidth, 5, Color.BLACK, Color.GRAY, boxInfo));
		else
			chart.addDrawCommand(new DrawCmdFillRect(x, y-height/2.0, width ,height, lineWidth, Color.BLACK, Color.GRAY, boxInfo));
	}
		
	
	
	protected void drawChildConnector(ChartMargins chart, double x, double y, double offset) {
		chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x), chart.xOffset(y)));
		chart.addDrawCommand(new DrawCmdRelLineTo(-connectorLength(),0.0,1, Color.BLACK));
		chart.addDrawCommand(new DrawCmdRelLineTo(0.0,offset,1, Color.BLACK));
		chart.addDrawCommand(new DrawCmdRelLineTo(-connectorLength(),0,1, Color.BLACK));
	}
	
	private double connectorLength() {
		return genGap()/2;
	}
	
	protected double genGap() {
		return getWidth()*.1;
	}
	
	public String toString() {
		if(family == null)
			return indi.toString();
		return family.husband.givenName.toString()+" + "+family.wife.givenName.toString();
			
	}
	


	
}

