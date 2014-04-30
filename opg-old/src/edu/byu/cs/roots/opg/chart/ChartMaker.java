package edu.byu.cs.roots.opg.chart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JPanel;


import edu.byu.cs.roots.opg.chart.preset.templates.StylingBoxScheme;
import edu.byu.cs.roots.opg.gui.OnePageMainGui;
import edu.byu.cs.roots.opg.model.ImageFile;
import edu.byu.cs.roots.opg.model.OpgOptions;
import edu.byu.cs.roots.opg.model.OpgSession;

/**
 * ChartMaker is the general interface that OnePage uses to create charts
 * 
 * When making a new chart type you should do the following
 * 1) Create a new package for your new chart type in opg.chart (e.g. opg.chart.NEWCHARTTYPE), place all new classes in this package
 * 2) Create a Chart Maker class which implements the ChartMaker interface
 * 3) Create an options class which extends ChartOptions and put any additional options for 
 *    your chart type in that class
 * 4) Create a class which extends SpecificOptionsPanel and has any buttons/boxes/labels/logic ect
 *    needed to populate the options class you created
 * 5) Add your chart type to the opg.chart.ChartType enum with appropriate reference to your chartmaker
 *    class.
 * 
 * If you don't need any chart specific options then you are free to use the default TypeSpecificOptions
 * and SpecificOptionsPanel
 * 
 * @author Travix
 *
 */
public interface ChartMaker extends Serializable{

	public static final String programLogo = "www.OnePageGenealogy.com";
	
	/**
	 * Returns an Options panel which is designed to work with and modify the specific chart options
	 * object which coresponds to this specific ChartMaker
	 * @return
	 */
	public JPanel getSpecificOptionsPanel(ChartOptions options, OnePageMainGui parent);
	
	/**
	 * Takes a ChartOptions object and converts it to an equivelent options object 
	 * which is specific to this ChartMaker
	 * @param options
	 * @return
	 */
	public ChartOptions convertToSpecificOptions(ChartOptions options);
	
	public void convertOpgOptions(OpgOptions options);
	
	/**
	 * Draws the Chart with the given options
	 * @param options
	 * @param parser
	 * @return
	 */
	public ChartDrawInfo getChart(ChartOptions options, OpgSession session);
	
	/**
	 * Gets the shapes of the boxes for clickable interface.
	 * @param max distance of ancestors
	 * @param max distance of descendants
	 * @return A list of shapes of what is physically drawn on the chart 
	 * and the x and y coordiantes in points relative to the chart.
	 */
	public LinkedList<ShapeInfo> getChartShapes(int maxAnces, int maxDesc, OpgSession session);
	
	/**
	 * Gets the ShapeInfo for anyone that might've been clicked.
	 * @param the x and y coordinates of where was clicked
	 * @param the max Ancestors requested by user
	 * @param the max Descendants requested by the user
	 * @return A shape of what is drawn on the chart, x and y coords,
	 * as well as the Indi.  Returns null if noone is clicked.
	 */
	public ShapeInfo getIndiIntersect(double x, double y, int maxAnces, int maxDesc, OpgSession session);
	
	public void setChartStyle(StylingBoxScheme style);

	public StylingBoxScheme getBoxStyles();
	
	public ArrayList<ImageFile> getImages();
	
	public void setIsPrimaryMaker(boolean set);
	
}
