package edu.byu.cs.roots.opg.chart.presetvertical;

import java.awt.Color;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;

import edu.byu.cs.roots.opg.chart.ChartDrawInfo;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdMoveTo;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdSetFont;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdText;
import edu.byu.cs.roots.opg.chart.cmds.DrawCommand;
import edu.byu.cs.roots.opg.fonts.OpgFont;
import edu.byu.cs.roots.opg.model.Individual;
import edu.byu.cs.roots.opg.util.NameAbbreviator;
/**
 * Represents the chart margins and titles
 *
 */
//TODO: make font size of titles variable
public class ChartMargins {

	private ChartDrawInfo chart;
	double xOrig;
	double yOrig;
	double width;
	double height;
	private VerticalChartOptions ops;
	double marginSize = 72;//one-inch margins
	double headerSize = 0;//size (in points) of title(s) at top of chart
	double titleSize = 0;//size of chart title (to be implemented later)
	float labelFontSize = 12;//font size of generation labels at top of chart 
	double whiteSpace;
	float MAX_GEN_WIDTH = 800;

	public ChartMargins(ChartDrawInfo chart, VerticalChartOptions ops)
	{
		this.chart = chart;
		this.setOptions(ops);
		xOrig = marginSize;
		yOrig = marginSize;
		width = chart.getXExtent() - (marginSize*2);
		height = chart.getYExtent() - (marginSize*2) - titleSize;
	}
	
	public ChartDrawInfo getChart(){
		return chart;
	}
	
	public void setChart(ChartDrawInfo c) {
		chart = c;
	}
	
	public void addDrawCommand(DrawCommand cmd)
	{
		//relies on draw command to offset by correct amount
		chart.addDrawCommand(cmd);
	}
	
	public double getXExtent(){
		return width;
	}
	
	public double getYExtent(){
		return height;
	}
	
	public double xOffset(double x){
		return x + xOrig;
	}
	
	public double yOffset(double y){
		return y + yOrig;
	}
	
	//this returns an appropriate label for the generation - gen - 0 = self, 1 = parents, -1 = children, etc.
	protected String getGenerationLabel(int gen)
	{
		switch (gen)
		{
		case 0:
			//return root.givenName + " " + root.middleName + " " + root.surname;//
			Font font = OpgFont.getDefaultSansSerifFont(Font.BOLD, labelFontSize);
			Individual root = getOptions().getRoot();
			NameAbbreviator.nameFit(root.namePrefix.trim(), root.givenName.trim(), root.surname, root.nameSuffix, MAX_GEN_WIDTH, font);
			return NameAbbreviator.getName();
			
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
				return gen-2 + getOrdinalSuffix(gen-2) + " Great-Grandparents";	
			else
				return (-gen)-2 + getOrdinalSuffix((-gen)-2) + " Great-Grandchildren"; 
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
	
	protected void findLabelFontSize()
	{
		//find width of longest label
		FontRenderContext frc = NameAbbreviator.frc;
		float testFontSize = 72;
		Font font = OpgFont.getDefaultSansSerifFont(Font.BOLD, testFontSize);
		headerSize = 72;
		double longestWidth = 0;
		for(int gen = -getOptions().getDescGens(); gen <= getOptions().getAncesGens(); ++gen)
		{
			//if (gen == 0) continue;
			double width = font.getStringBounds(getGenerationLabel(gen), frc).getWidth();
			if (width > longestWidth)
				longestWidth = width;
		}
		//set font size so that longest label barely fits over box
		labelFontSize = (float)(testFontSize * (MAX_GEN_WIDTH / longestWidth));
		final float MAXLABELFONTSIZE = 80;
		if(labelFontSize > MAXLABELFONTSIZE)
			labelFontSize=MAXLABELFONTSIZE;
		LineMetrics lm = font.deriveFont(labelFontSize).getLineMetrics("gjpqyjCAPSQJbdfhkl", frc);
		
		if (getOptions().isDrawTitles())
			headerSize = titleSize + lm.getHeight() + lm.getLeading();
		else
			headerSize = titleSize;
	}
	
	protected void drawTitles(AncesTree atree, DescTree dtree)
	{
		// new way to print the generation labels		
		FontRenderContext frc = NameAbbreviator.frc;
		//start at the grandparents
		Font font = OpgFont.getDefaultSansSerifFont(Font.BOLD, (float) 10);		
		LineMetrics lm = font.getLineMetrics("gjpqyjCAPSQJbdfhkl", frc);
		//move the position over to the start of the grandparents.
		double horizPos = moveHorizontallyToGen(2, atree, dtree);
		double vertPos = getOptions().paperHeight();
		vertPos += -marginSize - headerSize + (2*lm.getLeading()) + lm.getDescent();
		//draw ancestor labels
		chart.addDrawCommand(new DrawCmdSetFont(font, Color.RED));
		for(int gen = 2; gen <= getOptions().getAncesGens(); gen++) {
			if (gen % 2 == 0) {
				chart.addDrawCommand(new DrawCmdMoveTo(horizPos, vertPos));
				chart.addDrawCommand(new DrawCmdText(getGenerationLabel(gen)));
			}
			horizPos = addGenerationToHoriz(gen, horizPos, atree);
		}
				
		/*
		for(int gen = -getOptions().getDescGens(); gen <= getOptions().getAncesGens(); ++gen)
		{
			double width = font.getStringBounds(getGenerationLabel(gen), frc).getWidth();
			//draw label centered above boxes for generation
			System.out.println("gen: " + gen);
			if (gen == -1)
				continue;
			chart.addDrawCommand(new DrawCmdMoveTo(horizPos + (atree.getGeneration(gen).getWidth() - width)/2.0, vertPos ));
			chart.addDrawCommand(new DrawCmdText(getGenerationLabel(gen)));
			//draw spouse's name beneath root's name if root only has one spouse
			if (gen == 0 && getOptions().isIncludeSpouses() && root.fams.size() == 1)
			{
				double spouseVertPos = vertPos;
				spouseVertPos -= lm.getHeight();
				width = font.getStringBounds("and", frc).getWidth();
				chart.addDrawCommand(new DrawCmdMoveTo(horizPos + (atree.getGeneration(gen).getWidth() - width)/2.0, spouseVertPos ));
				chart.addDrawCommand(new DrawCmdText("and"));
				spouseVertPos -= lm.getHeight();
				Individual spouse = (root.gender == Gender.MALE)? root.fams.get(0).wife : root.fams.get(0).husband;
				NameAbbreviator.nameFit(spouse.namePrefix.trim(), spouse.givenName.trim(), spouse.surname, spouse.nameSuffix, (float)AncesBox.boxWidth, font);
				String spouseName = NameAbbreviator.getName();
				width = font.getStringBounds(spouseName, frc).getWidth();	
				chart.addDrawCommand(new DrawCmdMoveTo(horizPos + (atree.getGeneration(gen).getWidth() - width)/2.0, spouseVertPos ));
				chart.addDrawCommand(new DrawCmdText(spouseName));
			}
			horizPos += atree.getGeneration(gen).getWidth() * 11.0/10.0;
		}*/
		
	}
	
	private double moveHorizontallyToGen(int gen, AncesTree atree, DescTree dtree) {
		double totalHorizontal = marginSize;
		//add the descendants
		/*
		 * Because Decendants were never completed, the widths of each generation are 
		 * not recorded. thus, we cannot grab the width of a descendant generation, and so
		 * the labels will not match up with the graph if a descendant is also pictured on 
		 * the chart. It must be removed to have them aligned.
		 */
		for (int y = -getOptions().getDescGens(); y < 0; y++) {
			totalHorizontal += dtree.getGeneration(-y).getWidth();
		}
		//add the ancestors
		for (int i = 0; i < gen; i++) {
			totalHorizontal = addGenerationToHoriz(i, totalHorizontal, atree);
		}
		return totalHorizontal;
	}
	
	private double addGenerationToHoriz(int gen, double HorizPos, AncesTree atree) {
		HorizPos += atree.getGeneration(gen).getWidth();
		return HorizPos;
	}
	
	public void updateWhiteSpace()
	{
		if (getOptions().isDrawTitles())
			findLabelFontSize();
		whiteSpace =  marginSize*2 + headerSize;
	}
	
	
	public double getWhiteSpace() {
		updateWhiteSpace();
		return whiteSpace;
	}
	
	public void drawLogo()
	{
		chart.addDrawCommand(new DrawCmdMoveTo(marginSize+chart.getXExtent()-275, marginSize-12));
		chart.addDrawCommand(new DrawCmdSetFont(OpgFont.getDefaultSerifFont(Font.PLAIN, 12),Color.LIGHT_GRAY));
		chart.addDrawCommand(new DrawCmdText("www.OnePageGenealogy.com"));
	}
	
	public double totalMargins() {
		return marginSize*2;
	}

	public void setOptions(VerticalChartOptions ops) {
		this.ops = ops;
	}

	public VerticalChartOptions getOptions() {
		return ops;
	}
	
}
