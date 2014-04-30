package edu.byu.cs.roots.opg.chart.preset.templates;

import java.io.Serializable;
import java.util.ArrayList;



public class StylingBox implements Serializable{

	private static final long serialVersionUID = 1L;

	public enum TextDirection{NORMAL, NINETY, ONE_EIGHTY, TWO_SEVENTY;
	public String toString(){
		if(this == NORMAL)
			return "0\u00b0";
		else if (this == NINETY)
			return "90\u00b0";
		else if (this == ONE_EIGHTY)
			return "180\u00b0";
		else
			return "270\u00b0";
	};
}
	public enum WeddingPositions{HUSBAND_POSTFIX, WIFE_POSTFIX, BOTH_POSTFIX, DIRECT_DESCENDANT_POSTFIX;
	public String toString(){
		if(this == HUSBAND_POSTFIX)
			return "Husband";
		else if (this == WIFE_POSTFIX)
			return "Wife";
		else if (this == BOTH_POSTFIX)
			return "Both";
		else
			return "Direct Descendant";
	};
}


	/**
	 * This doubles when used for marriage descendant boxes
	 */
	public double boxHeight;
	private double boxWidth;
	private double tempBoxWidth;
	public BoxLayoutParent layout;
	public double fontSize;
	public double fontNameSize;
	private double relativeOffset;
	public double rootBackOffset;
	private double temporaryRelativeOffset;
	
	public double paddingAmount;
	
	public double endLineArrowShaftLength;
	public double endLineArrowShaftHeight;
	public double endLineArrowFontSize;
	public double endLineArrowHeadLength;
	public double endLineArrowHeadHeight;
	
	
	public double borderlineWidth;
	public int cornerCurve;
	public ArrayList<Double> textPositions;
	public double textMargin;
	
	public boolean isIntruding;
	public double intrudeWidth;
	
	public BoxLayoutParent weddingLayout;
	public WeddingPositions weddingDisplayType;
	
	public TextDirection direction;
	
	
	public void setOffset(double in){
		temporaryRelativeOffset = in;
	}
	
	public void setPermOffset(double in){
		relativeOffset = in;
		temporaryRelativeOffset = in;
	}
	
	public double getRelativeOffset(){
		return temporaryRelativeOffset;
	}
	
	public void resetTemporaries(){
		temporaryRelativeOffset = relativeOffset;
		tempBoxWidth = boxWidth;
	}
	
	public void setWidth(double in){
		tempBoxWidth = in;
	}
	
	public void setPermWidth(double in){
		boxWidth = in;
		tempBoxWidth = in;
	}
	
	public double getBoxWidth(){
		return tempBoxWidth;
	}
}
