package edu.byu.cs.roots.opg.chart.circ;

import java.io.Serializable;

import edu.byu.cs.roots.opg.chart.ChartOptions;
//import edu.byu.cs.roots.opg.chart.ChartType;

public class CircularOptions extends ChartOptions implements Serializable{
	
	float boxWidth, boxSpacing, rootRadius;
	boolean includeEmpty;
	
	static final long serialVersionUID = 1000L;

	public CircularOptions(ChartOptions options){
		super(options);
		//setChartType(ChartType.CIRCULAR);
		boxWidth = 2*72;
		boxSpacing = 1*72;
		rootRadius = 1.1f*72;
	}

	

	/**
	 * @return the includeEmpty
	 */
	public boolean isIncludeEmpty() {
		return includeEmpty;
	}



	/**
	 * @param includeEmpty the includeEmpty to set
	 */
	public void setIncludeEmpty(boolean includeEmpty) {
		this.includeEmpty = includeEmpty;
		changed(14);
	}



	
	
	/**
	 * @return the boxSpacing
	 */
	public float getBoxSpacing() {
		return boxSpacing;
	}


	/**
	 * @param boxSpacing the boxSpacing to set
	 */
	public void setBoxSpacing(float boxSpacing) {
		this.boxSpacing = boxSpacing;
		changed(11);
	}


	/**
	 * @return the boxWidth
	 */
	public float getBoxWidth() {
		return boxWidth;
	}


	/**
	 * @param boxWidth the boxWidth to set
	 */
	public void setBoxWidth(float boxWidth) {
		this.boxWidth = boxWidth;
		changed(12);
	}


	/**
	 * @return the rootRadius
	 */
	public float getRootRadius() {
		return rootRadius;
	}


	/**
	 * @param rootRadius the rootRadius to set
	 */
	public void setRootRadius(float rootRadius) {
		this.rootRadius = rootRadius;
		changed(13);
	}


}
