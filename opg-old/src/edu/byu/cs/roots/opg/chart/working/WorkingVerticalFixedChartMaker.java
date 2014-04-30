package edu.byu.cs.roots.opg.chart.working;

import java.awt.Color;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import edu.byu.cs.roots.opg.chart.ChartDrawInfo;
import edu.byu.cs.roots.opg.chart.ChartMaker;
import edu.byu.cs.roots.opg.chart.ChartMarginData;
import edu.byu.cs.roots.opg.chart.ChartOptions;
import edu.byu.cs.roots.opg.chart.ShapeInfo;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdMoveTo;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdPicture;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdRelLineTo;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdSetFont;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdText;
import edu.byu.cs.roots.opg.chart.preset.templates.ChartMargins;
import edu.byu.cs.roots.opg.chart.preset.templates.StylingBoxScheme;
import edu.byu.cs.roots.opg.color.ColorScheme;
import edu.byu.cs.roots.opg.fonts.OpgFont;
import edu.byu.cs.roots.opg.gui.OnePageMainGui;
import edu.byu.cs.roots.opg.model.Gender;
import edu.byu.cs.roots.opg.model.ImageFile;
import edu.byu.cs.roots.opg.model.Individual;
import edu.byu.cs.roots.opg.model.OpgOptions;
import edu.byu.cs.roots.opg.model.OpgSession;
import edu.byu.cs.roots.opg.model.PaperWidth;
import edu.byu.cs.roots.opg.util.NameAbbreviator;
import edu.byu.cs.roots.opg.util.PlaceAbbreviator;


public class WorkingVerticalFixedChartMaker implements ChartMaker 
{
	private static final long serialVersionUID = 1L;

	public static Logger log = Logger.getLogger(WorkingVerticalFixedChartMaker.class);
	
	protected ChartDrawInfo chart = null;
	protected ChartMargins chartMargins = null;
	protected WorkingVerticalFixedChartOptions ops;
	protected int ancesGens = -1;
	protected int descGens = -1;
	protected Individual root; //the root individual currently on the tree
	
	//chart margin size - consider moving to VerticalChartOptions
	//TODO switch to changeable margins
	ChartMarginData marginStorage = new ChartMarginData();
	double marginSize = 72;//one-inch margins
	double headerSize = 0;//size (in points) of title(s) at top of chart
	double titleSize = 0;//size of chart title (to be implemented later)
	float labelFontSize = 11;//font size of generation labels at top of chart 
	//ToDo: make sure that the labelFontSize can be made bigger or smaller!
	double boxWidth=0.0;
	int maxGensThatFit=0;
	protected float oneLine;
	
	protected ArrayList<ImageFile> images = new ArrayList<ImageFile>();
	
	private boolean isPrimaryMaker = false;
	
	public ChartOptions convertToSpecificOptions(ChartOptions options) 
	{
		WorkingVerticalFixedChartOptions newOptions = new WorkingVerticalFixedChartOptions(options);
		newOptions.setDrawTitles(true);
		newOptions.setPaperHeightChoice(true);
		return newOptions;
	}
	
	public void convertOpgOptions(OpgOptions options){
		options.setPreferredLength(false, 0);
	}

	public ChartDrawInfo getChart(ChartOptions options, OpgSession session) 
	{
		ops = (WorkingVerticalFixedChartOptions) options;
		if (session.isChanged())
		{
			initializeChart(session);
			root = ops.getRoot();
			ancesGens = ops.getAncesGens();
			//chart.updateSortedCharts();
		}
		return chart;
	}
	

	public JPanel getSpecificOptionsPanel(ChartOptions options, OnePageMainGui parent) {
		return new WorkingVerticalFixedOptionsPanel(options, parent);
	}

	protected void initializeChart(OpgSession session)
	{
		double chartWidth = (ops.isLandscape())?  ops.getPaperLength() : ops.getPaperWidth().width;
		double chartHeight = (ops.isLandscape())? ops.getPaperWidth().width : ops.getPaperLength();
		double usableChartHeight = chartHeight - marginSize * 2.0;
		OpgOptions opgOptions = session.getOpgOptions();
		
		// Find the size for the Name
		// FontRenderContext frc = new FontRenderContext(new AffineTransform(), false, true);
		Font testFont = opgOptions.getFont().font.deriveFont(opgOptions.getMinFontSize());
		LineMetrics lm = testFont.getLineMetrics("gjpqyjCAPSQJbdfhkl", NameAbbreviator.frc);
				
		// Calculate the Max Generations that we can fit on a chart and set the UI
		// appropriately.
		
		double maxChartHeight = (ops.isLandscape()) ? PaperWidth.maxChartWidth : PaperWidth.maxChartWidth*3;
		double maxUsableChartHeight = maxChartHeight - marginSize * 2.0;
		oneLine = lm.getHeight(); //Math.round(ops.getMinFontSize() * 1.6);
		ops.setMinPaperLength(oneLine + (marginSize*2.0));
		int maxGensAvaliable = (int)Math.floor(Math.log10(maxUsableChartHeight / oneLine )/Math.log10(2)); //-1 ; // + 1;// - 1;
		maxGensThatFit = (int)Math.floor(Math.log10(usableChartHeight / oneLine )/Math.log10(2));//-1 ; // + 1;// - 1;
		
		opgOptions.setMaxAncesSlider(maxGensAvaliable, isPrimaryMaker);
		opgOptions.setMaxDescSlider(0, isPrimaryMaker);
		
		if (ops.getAncesGens() > maxGensAvaliable)
			ops.setAncesGens(maxGensAvaliable, session);

		/**  COLOR SCHEMES **/
		ColorScheme ancesScheme = ops.getAncesScheme();
		ColorScheme descScheme = ops.getDescScheme();
		descScheme.clearTree();
		descScheme.colorTree(ops.getRoot(), ColorScheme.colordown);
		ancesScheme.clearTree();
		ancesScheme.colorTree(ops.getRoot(), ColorScheme.colorup);
		
		
		
		
		
		// Page resizing
		if (maxGensThatFit < Math.min(ops.getAncesGens(),maxGensAvaliable))
		{
			chartHeight = (Math.pow(2,ops.getAncesGens()) * oneLine) + (marginSize * 2.0);
			// If we are in landscape mode then we have to format the slider bar
			if (ops.isLandscape())
			{
				if (chartHeight > ops.getPaperWidth().width)
					ops.setPaperWidth(PaperWidth.findClosestFit(chartHeight));
				chartHeight = ops.getPaperWidth().width;
				
//				if (chartHeight > PaperWidth.wd3.width)
//				{
//					chartHeight = PaperWidth.wd3.width;
//					ops.setPaperWidth(PaperWidth.wd3);
//				}
//				else if (chartHeight > PaperWidth.wd2.width)
//				{
//					chartHeight = PaperWidth.wd3.width;
//					ops.setPaperWidth(PaperWidth.wd3);					
//				}
//				else if (chartHeight > PaperWidth.wd1.width)
//				{
//					chartHeight = PaperWidth.wd2.width;
//					ops.setPaperWidth(PaperWidth.wd2);					
//				}
//				else
//				{
//					chartHeight = PaperWidth.wd1.width;
//					ops.setPaperWidth(PaperWidth.wd1);					
//				}
			}
			else
			{
				ops.setPaperLength(chartHeight);
				ops.setMinPaperLength(chartHeight);
			}
			
			// Recalculate the Maximum number of generations to fit on this chart
			usableChartHeight = chartHeight - marginSize * 2.0;
			maxGensThatFit = (int)Math.floor(Math.log10(usableChartHeight / oneLine )/Math.log10(2)); //-1 ; // + 1;// - 1;
		}
		chart = new ChartDrawInfo((int)chartWidth, (int)chartHeight);
		
		maxGensThatFit = Math.min(maxGensThatFit, ops.getAncesGens());

		
		
		//make sure that oneLine fits on the last one!
		double SpacePerPerson = usableChartHeight/Math.pow(2, ops.getAncesGens()+1);
		if ((SpacePerPerson + 5) < oneLine)
		{
			do {			
				testFont = opgOptions.getFont().font.deriveFont(opgOptions.getMinFontSize());
				lm = testFont.getLineMetrics("gjpqyjCAPSQJbdfhkl", NameAbbreviator.frc);
				oneLine = lm.getHeight();
			} while (oneLine > SpacePerPerson + 5);
			
		}
		
		// See how wide the information of a person can be.
		boxWidth = (chartWidth-marginSize*2.0) / ((11.0/10.0 * maxGensThatFit) + 1);//
		
		//make an array that controls the size of a generation's font
		float fontGenSizes[] = new float[maxGensThatFit+1];
		float div = 0;
		if (maxGensThatFit != 0) {
			div = (opgOptions.getMaxFontSize() - opgOptions.getMinFontSize())/maxGensThatFit;
		}
		for (int i = 0; i < (maxGensThatFit+1); i++) {
			fontGenSizes[i] = opgOptions.getMaxFontSize() - (float)(i * div);
		}
		
		
		//draw the chart!
	//	chart.addDrawCommand(new DrawCmdSetFont(ops.getFont().font.deriveFont(oneLine), Color.BLACK));
		drawPersonLine(session, marginSize, chartHeight/2.0, chartHeight-2*marginSize, 0, ops.getRoot(), null, fontGenSizes);	
		root = ops.getRoot();
		if (ops.drawTitles)
			drawTitles();
		drawLogo(chartWidth, chartHeight);
		
		for (ImageFile f : images){
			chart.addDrawCommand(new DrawCmdMoveTo(f.x, f.y));
			chart.addDrawCommand(new DrawCmdPicture(f.width, f.height, f.getImage()));
		}
		session.resetChanged();

	}
	/**
	 * drawPersonLine is an unstoppable force. Resistance is futile. All your base are belong to us.
	 * 
	 * 
	 * @param baseX
	 * @param baseY
	 * @param heightLeft
	 * @param currentGen
	 * @param indi 
	 */
	
	protected void drawPersonLine(OpgSession session, double baseX, double baseY, double heightLeft, int currentGen, 
			Individual indi, Individual spouseIndi, float[] fontGenSizes)
	{
		/*Notes of things to add:
		 * CHECK! 1) names and things so they all fit nicely like
		 * CHECK! 2) Fix font size so that it scales according to some height thingy..???
		 * DONE!  3) Fix width if (width of text > length of box) shrink text;
		 * CHECK! 4) Ordinance work & colors! 
		 * CHECK! 5) Fix sliders/text size inputs
		 * CHECK! 6) Fix marriage dates!
		 * CHECK! 7) Titles & Logo
		 * 8) Couples!!!!
		 * 9) Descendants
		 * 10) Add Options to the Advance Tab (Apply Color Scheme, Show LDS Information)
		 */
		
		OpgOptions opgOptions = session.getOpgOptions();
		
		//If we maxed out the generations then stop
		if(currentGen <= maxGensThatFit)
		{
			//DRAW THE LINE
			chart.addDrawCommand(new DrawCmdMoveTo(baseX, baseY));
			chart.addDrawCommand(new DrawCmdRelLineTo(boxWidth, 0, 1, Color.black));
			
			//ADD CONNECTING LINES
			if (currentGen < maxGensThatFit)
			{
				chart.addDrawCommand(new DrawCmdRelLineTo(boxWidth/10.0, 0, 1.0, Color.black));
				chart.addDrawCommand(new DrawCmdRelLineTo(0.0, heightLeft / 4.0, 1.0, Color.black));
				chart.addDrawCommand(new DrawCmdRelLineTo(0.0, -heightLeft / 2.0, 1.0, Color.black));
			}
						
			//add in LDS ordinance data
			String ordinancesComplete = "";
			if(indi!=null)
			{				
				String beps = "";
				if(indi.baptism) beps += (indi.baptismComplete) ? "B" : "b";
				if(indi.endowment) beps += (indi.endowmentComplete) ? "E" : "e";
				if(indi.sealingToParents) beps += (indi.sealingToParentsComplete) ? "P" : "p";
				if(indi.sealingToSpouse) beps += (indi.sealingToSpouseComplete) ? "S" : "s";
				if (!beps.equals(""))
					ordinancesComplete += " - " + beps;
			}			

			// Determine the Font Color by ordinance work
			Color infoColor = (indi == null) ? Color.BLACK : (ops.getAncesScheme().getColor(indi.id));
			infoColor = (infoColor == Color.WHITE) ? Color.BLACK : infoColor; 
			int maxColor = Math.max(infoColor.getBlue(), Math.max(infoColor.getRed(), infoColor.getGreen()));
			int maxValue = 125;
			if (maxColor > maxValue)
			{
				double divider = maxColor / maxValue;
				infoColor = new Color((int)(infoColor.getRed() / divider), 
						(int)(infoColor.getGreen()/ divider), 
						(int)(infoColor.getBlue()/ divider));
			}
			
			//ADD TEXT
			String abbrName = "Name : ";
			if (indi != null)
			{
				NameAbbreviator.nameFit(indi.namePrefix.trim(), indi.givenName.trim(), indi.surname.trim(), indi.nameSuffix.trim(), (float)boxWidth, opgOptions.getFont().font.deriveFont(fontGenSizes[currentGen]));
				abbrName = NameAbbreviator.getName();
			}
						
			//draw name correctly using the abbreviated name for the user selected font size
			Font testFont = opgOptions.getFont().font.deriveFont(fontGenSizes[currentGen]);
			LineMetrics lm = testFont.getLineMetrics(abbrName, NameAbbreviator.frc);
			double XPos = baseX+lm.getBaselineIndex()+5;
			double YPos = baseY+lm.getLeading()+ lm.getDescent();
			chart.addDrawCommand(new DrawCmdSetFont(opgOptions.getFont().font.deriveFont(fontGenSizes[currentGen]), infoColor));
			chart.addDrawCommand(new DrawCmdMoveTo(XPos, YPos));
			chart.addDrawCommand(new DrawCmdText(abbrName + ordinancesComplete));
			
			
			//because of lack of room, the information can only be so big.  the constant
			//number that i am checking against is one third of the boxWidth.  if the font
			//gets bigger than that, then the information will not be as big as the name
			double biggestInfo = boxWidth/3;
			Font font;
			double width;
			float size = fontGenSizes[currentGen]+1;
			do {
				size--;
				font = opgOptions.getFont().font.deriveFont(size);
				width = font.getStringBounds("Birth PL: ", NameAbbreviator.frc).getWidth();
			} while (biggestInfo < width);			
			
			lm = font.getLineMetrics("JQupgyBIWo/FhlY()PAS", NameAbbreviator.frc);
			
			
			
			//Determine the size given to each individual in the tree
			//width is always the same, so you don't have to worry about that
			double chartHeight = (ops.isLandscape())? ops.getPaperWidth().width: ops.getPaperLength();
			//account for the margins
			chartHeight = chartHeight - 2*marginSize;
			int GenAmt = (int) Math.pow(2, (double) currentGen);
			//included the already added names to the chartHeight
			LineMetrics lmName = testFont.getLineMetrics("JQupgyBIWo/FhlYPAS", NameAbbreviator.frc);
			chartHeight = chartHeight - GenAmt * lmName.getHeight();
			double spacePerPerson = chartHeight/GenAmt;
			int amtText =(int)((int) spacePerPerson/lm.getHeight());
			
			float linesPossible = 6;
			if (amtText < 6)
				linesPossible = amtText;
			
			if (abbrName != null && abbrName.equals("David John Bever")) {
				System.out.println("This is the dude!");
				if(indi.fams.get(0).divorce)
					System.out.println("DIVORCED!!!");
			}
			
			//this will calculate how much width is taken by the beginning letters(e.g. 'D: ' or 'Birth: ')
			width = 0;
									
			//create additional information which uses linesPossible 
			String AdditionalInfo[] = null;
			try
			{
				AdditionalInfo = new String[6];
				String[] marriageInfo = getMarriageDateAndPlace(indi, spouseIndi);
			
				if (linesPossible <= 5) {
					AdditionalInfo[0] = "B: " + ((indi == null || indi.birth == null || indi.birth.date == null) ?  "" : indi.birth.date);
					//check to see if the place is included, otherwise don't do the operations!
					if (indi != null && indi.birth != null && indi.birth.date != null && !indi.birth.place.equals(""))
					{
						AdditionalInfo[0] += "/";
						width = font.getStringBounds(AdditionalInfo[0], NameAbbreviator.frc).getWidth();
						AdditionalInfo[0] += PlaceAbbr(session, indi.birth.place, width, size);
					}
					if (linesPossible == 2 || linesPossible > 2 && indi != null && indi.gender == Gender.FEMALE)
					{
						AdditionalInfo[1] = "D: " + ((indi == null || indi.death == null || indi.death.date == null) ?  "" : indi.death.date);
						if (indi != null && indi.death != null && indi.death.place != null && !indi.birth.place.equals(""))
						{
							AdditionalInfo[1] += "/";
							width = font.getStringBounds(AdditionalInfo[1], NameAbbreviator.frc).getWidth();
							AdditionalInfo[1] += PlaceAbbr(session, indi.death.place, width, size);
						}
						linesPossible = 2;
					}
					else if (linesPossible > 2)
					{
						AdditionalInfo[1] = "M: " + ((marriageInfo[0] == null) ? "" : marriageInfo[0]);
						if (marriageInfo[1] != null && !marriageInfo[1].equals(""))
						{
							AdditionalInfo[1] += "/";
							width = font.getStringBounds(AdditionalInfo[1], NameAbbreviator.frc).getWidth();
							AdditionalInfo[1] += PlaceAbbr(session, marriageInfo[1], width, size);
						}
						AdditionalInfo[2] = "D: " + ((indi == null || indi.death == null || indi.death.date == null) ?  "" : indi.death.date);
						if (indi != null && indi.death != null && indi.death.place != null && !indi.birth.place.equals(""))
						{
							AdditionalInfo[2] += "/";
							width = font.getStringBounds(AdditionalInfo[2], NameAbbreviator.frc).getWidth();
							AdditionalInfo[2] += PlaceAbbr(session, indi.death.place, width, size);
						}
						linesPossible = 3;
					}
					
				}else
				{
					AdditionalInfo[0] = "Birth: " + ((indi == null || indi.birth == null || indi.birth.date == null) ?  "" : indi.birth.date);
					AdditionalInfo[1] = "     ";
					width = font.getStringBounds(AdditionalInfo[1], NameAbbreviator.frc).getWidth();
					AdditionalInfo[1] += ((indi == null || indi.birth == null || indi.birth.place == null) ?  "" :  PlaceAbbr(session, indi.birth.place, width, size));
					if(indi!=null && linesPossible!=0 && indi.gender == Gender.FEMALE )
					{
						AdditionalInfo[2] = "Death: " + ((indi == null || indi.death == null || indi.death.date == null) ?  "" :   indi.death.date);
						AdditionalInfo[3] = "     ";
						width = font.getStringBounds(AdditionalInfo[3], NameAbbreviator.frc).getWidth();
						AdditionalInfo[3] += ((indi == null || indi.death == null || indi.death.place == null) ?  "" :  PlaceAbbr(session, indi.death.place, width, size));
						linesPossible = 4;
					}
					else
					{
						AdditionalInfo[2] = "Marriage: " + ((marriageInfo[2] == null) ? "" : marriageInfo[2]) + ((marriageInfo[0] == null) ? "" : marriageInfo[0]);
						AdditionalInfo[3] = "     ";
						width = font.getStringBounds(AdditionalInfo[3], NameAbbreviator.frc).getWidth();
						AdditionalInfo[3] += ((marriageInfo[1] == null) ? "" : PlaceAbbr(session, marriageInfo[1], width, size));
					}
					AdditionalInfo[4] = "Death: " + ((indi == null || indi.death == null || indi.death.date == null) ?  "" :   indi.death.date);
					AdditionalInfo[5] = "     ";
					width = font.getStringBounds(AdditionalInfo[5], NameAbbreviator.frc).getWidth();
					AdditionalInfo[5] += ((indi == null || indi.death == null || indi.death.place == null) ?  "" :  PlaceAbbr(session, indi.death.place, width, size));
				}
				
			}
			catch(NullPointerException e)
			{
				e.getMessage();
			}
			
						
			//Add the Additional Personal Info
			if (AdditionalInfo != null)
			{
				//add all the lines to the chart
				for (int LineNum = 0; LineNum < (int)linesPossible; LineNum++)
				{
					chart.addDrawCommand(new DrawCmdSetFont(opgOptions.getFont().font.deriveFont(size), infoColor)); 
					chart.addDrawCommand(new DrawCmdMoveTo(baseX+10, baseY-(1+lm.getAscent()+(size * LineNum))));
					if (AdditionalInfo[LineNum] != null)
						chart.addDrawCommand(new DrawCmdText(AdditionalInfo[LineNum]));
				}
			}	
			
			
			
			
			//recurse to parent-descendants
			Individual Father = indi == null ? null : indi.father;
			Individual Mother = indi == null ? null : indi.mother;
			if(currentGen != maxGensThatFit){
				drawPersonLine(session, baseX + (boxWidth * 11.0/10.0), baseY + (heightLeft / 4.0 ), (heightLeft / 2.0 ), currentGen+1, Father, Mother, fontGenSizes);
				drawPersonLine(session, baseX + (boxWidth * 11.0/10.0), baseY - (heightLeft / 4.0 ), (heightLeft / 2.0 ), currentGen+1, Mother, null, fontGenSizes);
			}
		}	
		
	}
	
	protected String PlaceAbbr(OpgSession session, String place, double width, float fontSize) 
	{
		if (place != null) {
			return PlaceAbbreviator.placeFit(place, (float)(boxWidth - width), session.getOpgOptions().getFont().font.deriveFont(fontSize));
		} else
			return "";
	}
	
	protected String[] getMarriageDateAndPlace(Individual indi, Individual mother)
	{
		String[] retValue = new String[3];
		// If there is more then one spouse we need to add the correct spouse
		// to connect to the person's genealogy. Search through the tree.
		if (indi != null && mother != null && indi.fams.size() > 1)
		{
			int motherID = -1;
			// Search for the correct mom
			for (int indexMom = 0; indexMom < indi.fams.size(); indexMom++)
			{
				// If we find the mom then we are done.
				if (indi.fams.get(indexMom).wifeId.equals(mother.id))
				{
					motherID = indexMom;
					// Stop our search
					indexMom = indi.fams.size();
				}
			}

			// Set the strings accordingly
			retValue[0] = (motherID == -1) ? null : ((indi.fams.get(motherID).marriage == null || 
					indi.fams.get(motherID).marriage.date == null) ? "" : 
						indi.fams.get(motherID).marriage.date);
			
			retValue[1] = (motherID == -1) ? null : ((indi.fams.get(motherID).marriage == null || 
					indi.fams.get(motherID).marriage.place == null) ? "" : 
						indi.fams.get(motherID).marriage.place);
			retValue[2] = (motherID == -1) ? null : ((indi.fams.get(motherID).divorce) ? "(D)" : "");
				
		}
		// If there was only one spouse then we know that (s)he is the correct one
		else if (indi != null && mother == null && indi.fams.size() > 1)
		{
			//Individual Spouse = (indi.id != indi.fams.get(0).husband.id) ? indi.fams.get(0).husband : indi.fams.get(0).wife;
			retValue[0] = (indi.fams.get(0).marriage == null || 
					indi.fams.get(0).marriage.date == null) ? "" : indi.fams.get(0).marriage.date;
			retValue[1] = (indi.fams.get(0).marriage == null || 
					indi.fams.get(0).marriage.place == null) ? "" : indi.fams.get(0).marriage.place;
			retValue[2] = (indi.fams.get(0).divorce) ? "(D)" : "";

			/*			retValue[0] = (indi.fams.get(indi.fams.size() - 1).marriage == null || 
					indi.fams.get(indi.fams.size() - 1).marriage.date == null) ? "" : indi.fams.get(indi.fams.size() - 1).marriage.date;
			retValue[1] = (indi.fams.get(indi.fams.size() - 1).marriage == null || 
					indi.fams.get(indi.fams.size() - 1).marriage.place == null) ? "" : indi.fams.get(indi.fams.size() - 1).marriage.place;*/			
		}		
		// If there was only one spouse then we know that (s)he is the correct one
		else if (indi != null && indi.fams.size() == 1)
		{
			//Individual Spouse = (indi.id != indi.fams.get(0).husband.id) ? indi.fams.get(0).husband : indi.fams.get(0).wife;
			retValue[0] = (indi.fams.get(0).marriage == null || 
					indi.fams.get(0).marriage.date == null) ? "" : indi.fams.get(0).marriage.date;
			retValue[1] = (indi.fams.get(0).marriage == null || 
					indi.fams.get(0).marriage.place == null) ? "" : indi.fams.get(0).marriage.place;
			retValue[2] = (indi.fams.get(0).divorce ? "(D)" : "");
		}
		// No spouse then no Marriage Info
		else
		{
			retValue[0] = null;
			retValue[1] = null;
		}
		
		return retValue;
	}
	
	protected void drawLogo(double chartWidth, double chartHeight)
	{
		String strLogo = programLogo;
		chart.addDrawCommand(new DrawCmdSetFont(OpgFont.getDefaultSerifFont(Font.PLAIN, 12),Color.LIGHT_GRAY));
		double width = OpgFont.getDefaultSerifFont(Font.PLAIN, 12).getStringBounds(strLogo, NameAbbreviator.frc).getWidth();
		chart.addDrawCommand(new DrawCmdMoveTo((chartWidth - (width + 20)), 20));
		chart.addDrawCommand(new DrawCmdText(strLogo));
	}
	
	protected String getGenerationLabel(int gen)
	{
		switch (gen)
		{
		case 0:
			return root.givenName + " " + root.middleName + " " + root.surname;//
		case 1:
			return "Parents";
		case 2:
			return "Grandparents";
		case 3:
			return "Great-Grandparents";
		case -1:
			return "Children";
		case -2:
			return "Grandchildren";
		case -3:
			return "Great-Grandchildren";
		default:
			if (gen > 0)
				return gen-2 + getOrdinalSuffix(gen-2) + " GGP";
			else
				return (-gen)-2 + getOrdinalSuffix((-gen)-2) + " GGC";
		}
	}
		
	protected static String getOrdinalSuffix(int gen)
	{
		if (11 <= (gen%100) && 13 >= (gen%100))
			return "th";

		switch (gen%10)
		{
		case 1:
			return "st";
		case 2:
			return "nd";
		case 3:
			return "rd";
		default:
			return "th";
		}
	}
		
	protected void drawTitles()
	{
		//choose font size for generation labels
		FontRenderContext frc = NameAbbreviator.frc;
		
		String name = root.namePrefix + root.givenName + root.middleName + root.surname + root.nameSuffix;
		
		
		//draw each generation label		
		Font font = OpgFont.getDefaultSansSerifFont(Font.BOLD, labelFontSize);
		LineMetrics lm = font.getLineMetrics("gjpqyjCAPSQJbdfhkl", frc);
		double horizPos = marginSize + (boxWidth * 11.0/10.0);
		double vertPos = (ops.isLandscape())? ops.getPaperWidth().width : ops.getPaperLength() - marginSize - headerSize + (2*lm.getLeading()) + lm.getDescent();
		//draw ancestor labels
		chart.addDrawCommand(new DrawCmdSetFont(font, Color.RED));
		for(int gen = 1; gen <= ops.getAncesGens(); ++gen)
		{
			double width = font.getStringBounds(getGenerationLabel(gen), frc).getWidth();
			//draw label centered above boxes for generation
			chart.addDrawCommand(new DrawCmdMoveTo(horizPos + (boxWidth - width)/2.0, vertPos ));
			chart.addDrawCommand(new DrawCmdText(getGenerationLabel(gen)));
			horizPos += boxWidth * 11.0/10.0;
		}
		//draw individual label
		double width = font.getStringBounds(name, frc).getWidth();
		chart.addDrawCommand(new DrawCmdMoveTo(marginSize + (boxWidth - width)/2.0, vertPos));
		chart.addDrawCommand(new DrawCmdText(name));
	}

	public LinkedList<ShapeInfo> getChartShapes() {
		//  Auto-generated method stub
		return null;
	}

	@Override
	public ShapeInfo getIndiIntersect(double x, double y, int maxAnces, int maxDesc, OpgSession session) {
		return null;
	}

	@Override
	public LinkedList<ShapeInfo> getChartShapes(int maxAnces, int maxDesc, OpgSession session) {
		return null;
	}

	@Override
	public void setChartStyle(StylingBoxScheme style) {
		
	}

	@Override
	public StylingBoxScheme getBoxStyles() {
		return null;
	}

	public ArrayList<ImageFile> getImages(){
		return images;
	}
	
	@Override
	public void setIsPrimaryMaker(boolean set) {
		isPrimaryMaker = set;
	}
	
	
}
