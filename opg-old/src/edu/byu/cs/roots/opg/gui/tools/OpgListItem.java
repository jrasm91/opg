package edu.byu.cs.roots.opg.gui.tools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import edu.byu.cs.roots.opg.chart.ChartDrawInfo;
import edu.byu.cs.roots.opg.chart.cmds.DrawCommand;
import edu.byu.cs.roots.opg.chart.cmds.DrawState;
import edu.byu.cs.roots.opg.exc.FailedToLoadException;
import edu.byu.cs.roots.opg.exc.NotAValidFileException;
import edu.byu.cs.roots.opg.model.Family;
import edu.byu.cs.roots.opg.model.OpgSession;

public class OpgListItem {

	String rootName;
	LinkedList<String> spouses;
	String surName;
	String uploadPath;
	ChartDrawInfo chart;
	boolean includeSpouse;
	double width,length;
	
	public OpgListItem(String filePath) throws FileNotFoundException, NotAValidFileException, FailedToLoadException
	{
		File opgFile = new File(filePath);
		if (!opgFile.exists())
			throw new FileNotFoundException(opgFile.getAbsolutePath());
		if (!opgFile.isFile())
			throw new NotAValidFileException();

		OpgSession mySession = new OpgSession();
		
		try
		{
			mySession.open(opgFile);
		}
		catch (IOException e)
		{
			throw new FailedToLoadException("IO Exception occured during the opening of an OPG File");
		}
		
		chart = mySession.getPreview();
		uploadPath = filePath;
		rootName = mySession.getBaseOptions().getRoot().givenName;
		surName = mySession.getBaseOptions().getRoot().surname;
		spouses = new LinkedList<String>();
		includeSpouse = mySession.getBaseOptions().isIncludeSpouses();
		ArrayList<Family> wifes = mySession.getBaseOptions().getRoot().fams;
		if (wifes != null){
			Iterator<Family> curSpouse = wifes.iterator();
			while (curSpouse.hasNext())
			{
				spouses.add(curSpouse.next().wife.givenName);
			}
		}
		width = mySession.getBaseOptions().getPaperWidth().width / 72;
		length = mySession.getBaseOptions().getPaperLength() / 72;
	}
	public String getWidth(){
		return (int)width + "";
	}
	public String getLength(){
		return (int)length + "";
	}
	
	public void DrawChart(Graphics g, Rectangle bounds)
	{
		Graphics2D g2d = (Graphics2D)g.create();
		
		// Define the graphics for the preview box
		double fullZoom = Math.min(bounds.getHeight() / chart.getYExtent(), bounds.getWidth() / chart.getXExtent());
		// Center the chart
		g2d.translate((int)(bounds.getWidth() - (chart.getXExtent() * fullZoom)) / 2, 
				(int)(bounds.getHeight() - (chart.getYExtent() * fullZoom)) / 2);
		// Scale it so that it is correctly drawn on the canvas
		g2d.scale(fullZoom, fullZoom);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		
		// Give the preview background and border
		g.clearRect(0, 0, bounds.width, bounds.height);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, bounds.width, bounds.height);
		g.setColor(Color.WHITE);
		// Draw the background in the correct place
		g.fillRect((int)(bounds.getWidth() - (chart.getXExtent() * fullZoom)) / 2, 
				(int)(bounds.getHeight() - (chart.getYExtent() * fullZoom)) / 2, (int)(chart.getXExtent() * fullZoom), (int)(chart.getYExtent() * fullZoom));
		
		// Set up the starting state
		DrawState curState = new DrawState();
		curState.reset();
		curState.chartLeftToDisplay = 0;
		curState.chartTopToDisplay = 0;
		curState.xExtent = chart.getXExtent();
		curState.yExtent = chart.getYExtent();

		// Run the commands
		Iterator<DrawCommand> cmdIter = chart.getDrawCommands().iterator();
		
		while (cmdIter.hasNext())
		{
			DrawCommand cmd = cmdIter.next();
			cmd.execute(g2d, curState);
		}
	}
	
	/**
	 * Gets the path that was given to open this file.
	 * @return The path to the original file
	 */
	public String getPath() {
		return uploadPath;
	}
	
	/**
	 * Puts the OpgListItem to a printable string. Basiaclly the root and their spouses will be displayed.
	 * @return A string with teh printable text version of the OpgListItem
	 */
	@Override
	public String toString() {
		String retValue = rootName;
		if (includeSpouse && spouses.toArray() instanceof String[])
		{
			String[] wives = (String[])spouses.toArray();
			for (int indexWife = 0; indexWife < wives.length; indexWife++)
				retValue += " & " + wives[indexWife];
		}
		retValue += " " + surName;
		return retValue;
	}
}
