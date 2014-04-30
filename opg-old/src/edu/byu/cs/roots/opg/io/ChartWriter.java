package edu.byu.cs.roots.opg.io;
//import java.awt.Graphics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import edu.byu.cs.roots.opg.chart.ChartDrawInfo;
import edu.byu.cs.roots.opg.model.OpgPage;
import edu.byu.cs.roots.opg.model.OpgSession;

/**
 * This ChartWriter iterface is to be implemented by a class that can take a ChartDrawInfo class
 * and produce an output form of that chart
 * @author Derek
 */

public interface ChartWriter
{
	/**
	 * Creates a chart based on a ChartDrawInfo object and outputs it to a file
	 * named fileName
	 */
	public void createChart(ChartDrawInfo chartInfo, String fileName) throws IOException;
	
	/**
	 * Creates a chart and draws it onscreen
	 */
	public void createChart(OpgPage page, Graphics2D g, int width, int height, double zoom, int x, int y, Graphics g2, boolean ruler, OpgSession session);
}

