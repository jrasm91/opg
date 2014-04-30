package edu.byu.cs.roots.opg.chart;

//TODO Unused?
public class ChartRegion extends ChartDrawInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -194666378559329674L;
	int uniformMarginSize;
	
	ChartRegion(int x, int y, int uniformMarginSize)
	{
		super(x, y);
		this.uniformMarginSize = uniformMarginSize;
	}
	
	public int getXExtent() { return getXExtent() - (2* uniformMarginSize); }
	
	public int getYExtent()	{ return getYExtent() - (2* uniformMarginSize); }
	
	public void setXExtent(int x) { setXExtent(x + (2* uniformMarginSize)); }
	
	public void setYExtent(int y) { setXExtent(y + (2* uniformMarginSize)); }
}
