package edu.byu.cs.roots.opg.chart.multisheet;

import java.awt.Color;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JPanel;
import edu.byu.cs.roots.opg.chart.ChartDrawInfo;
import edu.byu.cs.roots.opg.chart.ChartMaker;
import edu.byu.cs.roots.opg.chart.ChartMarginData;
import edu.byu.cs.roots.opg.chart.ChartOptions;
import edu.byu.cs.roots.opg.chart.ChartType;
import edu.byu.cs.roots.opg.chart.ShapeInfo;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdMoveTo;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdPicture;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdRelLineTo;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdSetFont;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdText;
import edu.byu.cs.roots.opg.chart.preset.templates.ChartMargins;
import edu.byu.cs.roots.opg.chart.preset.templates.DescBoxParent;
import edu.byu.cs.roots.opg.chart.preset.templates.PresetChartOptions;
import edu.byu.cs.roots.opg.chart.preset.templates.StylingBoxScheme;
import edu.byu.cs.roots.opg.chart.preset.templates.PresetChartOptionsPanel;
import edu.byu.cs.roots.opg.fonts.OpgFont;
import edu.byu.cs.roots.opg.gui.OnePageMainGui;
import edu.byu.cs.roots.opg.model.Family;
import edu.byu.cs.roots.opg.model.Gender;
import edu.byu.cs.roots.opg.model.ImageFile;
import edu.byu.cs.roots.opg.model.Individual;
import edu.byu.cs.roots.opg.model.OpgOptions;
import edu.byu.cs.roots.opg.model.OpgPage;
import edu.byu.cs.roots.opg.model.OpgSession;
import edu.byu.cs.roots.opg.model.Pair;
import edu.byu.cs.roots.opg.model.PaperWidth;
import edu.byu.cs.roots.opg.util.NameAbbreviator;

public class MultisheetChartMaker implements ChartMaker {
	private static final long serialVersionUID = 1L;
	protected ChartDrawInfo chart = null;
	protected ChartMargins chartMargins = null;
	/**
	 * A list of AncesTree (The max in here is two, one for root, one for spouse)
	 */
	protected ArrayList<AncesTree> ancesTrees;
	protected double ancesMinHeight;
	protected double descMinHeight;
	protected boolean includeSpouses;
	protected boolean allowIntrusion;
	protected boolean drawTreeHasChanged = false;
	
	protected ArrayList<DescTree> descTrees;
	
	protected AncesBox ancesBox = null;
	protected DescBox descBox = null;
	protected MultisheetChartOptions ops;
	protected Individual root; //the root individual currently on the tree
	/**
	 * Amount of visible ancestor generations
	 */
	protected int ancesGens = -1;
	/**
	 * Amount of visible descendant generations
	 */
	protected int descGens = -1;
	
	final static int maxGenLimit = 5;
	
	/**
	 * A list of pre-determined box style variables
	 */
	StylingBoxScheme boxStyles;
	
	
	protected ArrayList<ArrayList<AncesBox>> ancesGenPositions;
	
	protected int[] maxSpouseLineOffset;
	protected float maxFont = 12, minFont=8;
	
	//chart margin size - consider moving to VerticalChartOptions
	double headerSize = 0;//size (in points) of title(s) at top of chart
	//TODO implement Titles
	double titleSize = 0;//size of chart title (to be implemented later)
	float labelFontSize = 12;//font size of generation labels at top of chart 
	
	private int generationalOffset = 0;
	private final int PRINT_LENGTH = 792;
	
	protected ArrayList<ImageFile> images = new ArrayList<ImageFile>();
	
	protected boolean isPrimaryMaker = false;
	
	public ArrayList<Pair<Individual, Integer>> toAddMakerList = new ArrayList<Pair<Individual, Integer>>();
	
	public boolean gettingChartHeight = false;
	
	public ChartOptions convertToSpecificOptions(ChartOptions options) {
		MultisheetChartOptions newOptions = new MultisheetChartOptions(options);
		
		//set default values for options specific to Multisheet Chart here
		newOptions.setBoxBorder(true);
		newOptions.setRoundedCorners(true);
		newOptions.setDrawTitles(true);
		newOptions.setAllowIntrusion(true);
		newOptions.setDrawEndOfLineArrows(true);
		newOptions.setArrowStyle(PresetChartOptions.EndLineArrowStyle.SELF);
		newOptions.setPaperOrientationChoice(false);
		newOptions.setPaperWidthChoice(false);
		newOptions.setPaperHeightChoice(false);
		newOptions.setLandscape(false);
		newOptions.setIncludeSpouses(false);
		newOptions.setSpouseIncludedChoice(true);
		

		return newOptions;
	}
	
	public void convertOpgOptions(OpgOptions options){
		options.setChartMargins(9, 9, 9, 9);
		options.setPreferredLength(true, PRINT_LENGTH);
	}

	public ChartDrawInfo getChart(ChartOptions options, OpgSession session) {
		//  Account for margins and titles
		ops = (MultisheetChartOptions) options;
		
		//chart initialization and preparation section
		if (chart == null ||  root != ops.getRoot() || ancesGens != ops.getAncesGens() || descGens != ops.getDescGens()
				|| includeSpouses != ops.isIncludeSpouses() || ops.getDrawTreeHasChanged()
				|| allowIntrusion != ops.isAllowIntrusion() || ops.getStyleBoxChanged() || ops.getMarginsChanged()){
			initializeChart(session);
		}

		//chart generation - draw chart again to new paper size or options
		if (session.isChanged())
		{	
			generateChart(session);
		}
		
		return chart;
	}

	public JPanel getSpecificOptionsPanel(ChartOptions options, OnePageMainGui parent)
	{
		PresetChartOptions ops = (PresetChartOptions)options;
		return new PresetChartOptionsPanel(ops, parent);
	}

	protected void initializeChart(OpgSession session)
	{	
		boolean reset = false;
		OpgOptions opgOptions = session.getOpgOptions();
		if (isPrimaryMaker && !ops.getStyleBoxChanged())
		{
			System.out.println("Clear");
			session.resetPages();
			opgOptions.clearGlobalIndiFlags();
			reset = true;
		}
		
			
		chart = new ChartDrawInfo(0,0);
		ChartMarginData margins = opgOptions.getChartMargins();
		
		//chartMargins = new ChartMargins(chart, marginSize);
		chartMargins = new ChartMargins(chart, margins, headerSize);
		boolean includeSpouseChanged = includeSpouses != ops.isIncludeSpouses();
		boolean allowIntrusionChanged = allowIntrusion != ops.isAllowIntrusion();
		boolean styleBoxChanged = ops.getStyleBoxChanged();
		boolean drawTreeChanged = ops.getDrawTreeHasChanged();
		boolean newChartScheme = opgOptions.getNewChartScheme();
		
		allowIntrusion = ops.isAllowIntrusion();
		includeSpouses = ops.isIncludeSpouses();
		ops.setStyleBoxChanged(false);
		ops.setDrawTreeHasChanged(false);
		opgOptions.setNewChartScheme(false);
		ops.setMarginsChanged(false);
		
		//initialize box data structures (create tree)
		//This triggers if: 
		//Program started, changed the root,
		//Changed whether spouse is shown or not, or collapsed a tree
		if (root != ops.getRoot() || includeSpouseChanged  || drawTreeChanged || reset)
		{	
			root = ops.getRoot();
			if (isPrimaryMaker)
				opgOptions.resetDuplicateMap();
			
			ancesGens = -1; //reset ancestor generations to allow recalculation of tree layout
			
			//reset isInTree flags for individuals & families
			//this is so we can rebuild the tree from scratch
			if (root == null)
				System.out.println("NULL ROOT!");
//			root.resetFlags();
//			//if the spouse is visible, their flags need to be reset as well
//			if (includeSpouses){
//				for (Family fam : root.fams){
//					Individual spouse = (root.gender == Gender.MALE)? fam.wife : fam.husband;
//					if (spouse != null)
//						spouse.resetFlags();
//				}
//			}
			session.resetIndiFlags();
			
			
			//set up list of AncesTree (The max in here is two, one for root, one for spouse)
			ancesTrees = new ArrayList<AncesTree>();
			
			//add root ancesTree
			ancesTrees.add(new AncesTree(root, session));
			
			//TODO This is where we can change auto resize when a tree is collapsed
			
			//Gets the max depth of the trees
			int maxOfMaxAncesGens = ancesTrees.get(0).ancesBox.maxGensInTree-1;/*ancesTrees.get(0).ancesBox.maxVisibleGensInTree;*///(ancesTrees.get(0).ancesBox.maxVisibleGensInTree == 0? ancesTrees.get(0).ancesBox.maxGensInTree:ancesTrees.get(0).ancesBox.maxVisibleGensInTree );
			
			//If the spouse goes further, updates max depth to theirs
			if (includeSpouses && isPrimaryMaker)
				for (Family fam : root.fams)
				{
					Individual spouse = (root.gender == Gender.MALE)? fam.wife : fam.husband;
					if (spouse != null)
					{
						this.addMakerToAddList(spouse, 0);
					}
				}
			
			//Makes sure the slider isn't allowing more generations then are possible using max depth
			opgOptions.setMaxAncesSlider(maxOfMaxAncesGens, isPrimaryMaker);
			if (ops.getAncesGens() < 0)
			{
				ops.setAncesGens(Math.min(maxOfMaxAncesGens, 0), session);
			}
			if(ops.getAncesGens() > opgOptions.getMaxAncesSlider())
			{
				ops.setAncesGens(opgOptions.getMaxAncesSlider(), session);
			}
			
			
			//set up DescTrees by resetting the flags so the tree can be rebuilt
			session.resetFamilyFlags();
			session.resetFamilyList();
			
			descTrees = new ArrayList<DescTree>();
			//descTrees.add(new DescTree(root, session));
			int maxOfMaxDescGens = 0;
			ops.setDescGens(0, session);
			
			if (ops.getDescGens() < maxOfMaxDescGens)
			{
				if (opgOptions.getMaxDescSlider() < maxOfMaxDescGens)
					opgOptions.setMaxDescSlider(maxOfMaxDescGens, isPrimaryMaker);
				ops.setDescGens(maxOfMaxDescGens, session);
			}
			opgOptions.setMaxDescSlider(maxOfMaxDescGens, isPrimaryMaker);
			
			//reset isInTree flags for individuals & families again 
			//to allow subsequent charts to have all flags clear
//			root.resetFlags();
//			if (includeSpouses)
//				for (Family fam : root.fams)
//				{
//					Individual spouse = (root.gender == Gender.MALE)? fam.wife : fam.husband;
//					if (spouse != null)
//						spouse.resetFlags();
//				}
//						
			
			session.resetIndiFlags();
			
			
						
		}
		
		if (root != ops.getRoot() || includeSpouseChanged  || drawTreeChanged ||
				ancesGens != ops.getAncesGens() || descGens != ops.getDescGens() || newChartScheme || reset){
			//Set the box look variables
			if (!newChartScheme)
			{
				ArrayList<StylingBoxScheme> styles;
				if(ops.getAncesGens() == 5)
					styles = StyleBoxFactory.getStyleListFor5(ops.getAncesGens(), ops.getDescGens(), !root.originatingPages.isEmpty());
				else
					styles = StyleBoxFactory.getStyleList(ops.getAncesGens(), ops.getDescGens(), !root.originatingPages.isEmpty());
				//ArrayList<StylingBoxScheme> styles = StyleBoxFactory.getStyleList(ops.getAncesGens(), ops.getDescGens(), !root.originatingPages.isEmpty());
				
				opgOptions.setChartStyles(styles);
				boxStyles = styles.get(0);
				opgOptions.setRefreshSchemeList(true);
			}
			for(AncesTree tree : ancesTrees){
				ArrayList<StylingBoxScheme> styles;
				if(ops.getAncesGens() == 5)
					styles = StyleBoxFactory.getStyleListFor5(tree.ancesBox.maxVisibleGensInTree, ops.getDescGens(), !root.originatingPages.isEmpty());
				else
					styles = StyleBoxFactory.getStyleList(tree.ancesBox.maxVisibleGensInTree, ops.getDescGens(), !root.originatingPages.isEmpty());
				tree.ancesBox.setBoxStyle(styles.get(0));
				//opgOptions.setChartStyles(styles);
				//boxStyles = styles.get(0);
				//opgOptions.setRefreshSchemeList(true);
					//tree.ancesBox.setBoxStyle(boxStyles);
			}
			for(DescTree tree : descTrees){
				tree.descBox.setBoxStyle(boxStyles);
			}
			
			styleBoxChanged = true;
			
		}
		
		
		
		
		//calculate needed size for generation labels
		findLabelFontSize();
		
		//reset flags
//		root.resetFlags();
//		if (includeSpouses)
//		for (Family fam : root.fams)
//		{
//			Individual spouse = (root.gender == Gender.MALE)? fam.wife : fam.husband;
//			if (spouse != null)
//				spouse.resetFlags();
//		}

		//clear duplicate labels
		session.resetIndiFlags();
		
		//calculate base coordinates
		if ((ancesGens != ops.getAncesGens() || drawTreeChanged || includeSpouseChanged || 
				styleBoxChanged || allowIntrusionChanged || reset) && ancesTrees != null)
		{
			ancesGens = ops.getAncesGens();
			
			if (isPrimaryMaker)
				opgOptions.resetDuplicateLabels();
			for (AncesTree tree: ancesTrees)
			{
				ArrayList<StylingBoxScheme> styles;
				if(ops.getAncesGens() == 5)
					styles = StyleBoxFactory.getStyleListFor5(tree.ancesBox.maxVisibleGensInTree, ops.getDescGens(), !root.originatingPages.isEmpty());
				else
					styles = StyleBoxFactory.getStyleList(tree.ancesBox.maxVisibleGensInTree, ops.getDescGens(), !root.originatingPages.isEmpty());
				//tree.ancesBox.calcCoords(ops, this, 0, boxStyles, session);
				tree.ancesBox.calcCoords(ops, this, 0, styles.get(0), session);
				
				//reset flags
//				root.resetFlags();
//				if (includeSpouses)
//				for (Family fam : root.fams)
//				{
//					Individual spouse = (root.gender == Gender.MALE)? fam.wife : fam.husband;
//					if (spouse != null)
//						spouse.resetFlags();
//				}
				session.resetIndiFlags();
				
				tree.ancesBox.minHeight = tree.ancesBox.upperSubTreeHeight - tree.ancesBox.lowerSubTreeHeight;
								
				//expand boxes to fill gaps
				tree.ancesBox.vPos = 0;
				//tree.ancesBox.setRelativePositions(0,ops.getAncesGens());
				if(this == session.getBaseMaker()&& ops.getAncesGens() == 5)
					tree.ancesBox.setRelativePositions(0,4);
				else
					tree.ancesBox.setRelativePositions(0,ops.getAncesGens());
			}
			
		}
		updateAncesHeight();
		if ((descGens != ops.getDescGens() || drawTreeChanged || 
				includeSpouseChanged || styleBoxChanged || allowIntrusionChanged || reset) && descTrees != null)
		{
			descGens = ops.getDescGens();
			for (DescTree tree: descTrees)
			{
				tree.calcCoords(ops);
				tree.descBox.minHeight = tree.descBox.upperSubTreeHeight - tree.descBox.lowerSubTreeHeight;
			
				//expand boxes to fill gaps
				tree.descBox.vPos = 0;
				tree.descBox.setRelativePositions(0, ops.getDescGens());
				
				
			}
		}
		updateDescHeight();
		
		//set chart options for portrait orientation chart
		if (!ops.isLandscape())
		{
			double whiteSpace =  margins.getTop() + margins.getBottom() + headerSize;
			//increase chart height to at least the minimum calculated height if necessary
			double chartHeight = Math.max(ancesMinHeight, descMinHeight) + whiteSpace;
			
//			if ((ops.getPaperLength()) < chartHeight)
//			{
			ops.setPaperLength(chartHeight);
			ops.setPaperWidth(boxStyles.preferredWidth);	
//			}
			ops.setMinPaperLength(chartHeight);
			//set max paper length
			
		}
		else
		{
			double whiteSpace =  margins.getRight() + margins.getLeft() + headerSize;
					
			//increase chart height (paper width) to at least the minimum calculated height if necessary
			double chartHeight = Math.max(ancesMinHeight, descMinHeight) + whiteSpace;
//			if (ops.getPaperWidth().width < chartHeight)
//			{
				//find paper width that is just larger than chartHeight
			ops.setPaperWidth(PaperWidth.findClosestFit(chartHeight));
			ops.setPaperLength(boxStyles.preferredWidth.width);
//			for (int i = 0; i < PaperWidth.values().length; ++i)
//				{
//					if (PaperWidth.values()[i].width > chartHeight)
//					{
//						ops.setPaperWidth(PaperWidth.values()[i]);
//						break;
//					}
//				}
//			}
		}
		
		//this line just makes sure that the changed variable is set so that the chart generation section will execute 
		ops.setPaperLength(ops.getPaperLength());
	
		if(isPrimaryMaker)
			root.pageId.setItem("1");
	}
	
	protected void updateAncesHeight()
	{
		ancesMinHeight = 0;
		for (AncesTree tree: ancesTrees)
		{
			ancesMinHeight += tree.ancesBox.upperSubTreeHeight - tree.ancesBox.lowerSubTreeHeight;
		}
	}
	
	protected void updateDescHeight()
	{
		descMinHeight = 0;
		if(ops.isIncludeSpouses() && ops.getDescGens() > 0){
			ArrayList<DescBoxParent> descs = descTrees.get(0).descBox.children;
			for(DescBoxParent box: descs)
				descMinHeight += box.upperSubTreeHeight - box.lowerSubTreeHeight;
		}
		else if(ops.getDescGens() == 0)
			descMinHeight = 0;
		else
			for (DescTree tree: descTrees)
			{
				descMinHeight += tree.descBox.upperSubTreeHeight - tree.descBox.lowerSubTreeHeight;
			}		
	}

	protected void generateChart(OpgSession session)
	{
		OpgOptions opgOptions = session.getOpgOptions();
		
		if(!gettingChartHeight)
			processAddedMakers(session);
		
		//width and height vary based on paper orientation
		double paperHeight = (ops.isLandscape())? ops.getPaperWidth().width : ops.getPaperLength();
		double paperWidth = (!ops.isLandscape())? ops.getPaperWidth().width : ops.getPaperLength();
		
		//calculate needed size for generation labels
		findLabelFontSize();
		
		//create new chart
		ChartMarginData margins = opgOptions.getChartMargins();
		chart = new ChartDrawInfo((int)paperWidth, (int)paperHeight);
		chartMargins = new ChartMargins(chart, margins);
		
		double horizontal = margins.getLeft() + margins.getRight();
		double vertical = margins.getTop() + margins.getBottom();
		paperHeight -= (ops.isLandscape())? horizontal + headerSize : vertical + headerSize;
		paperWidth -= (ops.isLandscape())? horizontal : vertical;
		
		updateDescHeight();
		updateAncesHeight();
		//double ancesHeight = ancesBox.upperSubTreeHeight - ancesBox.lowerSubTreeHeight;
		double ancesHeight = ancesMinHeight;
		double descHeight = descMinHeight;//descBox == null ? 0 : descBox.upperSubTreeHeight - descBox.lowerSubTreeHeight;
		//double chartHeight = Math.max(ancesHeight, descHeight);
		double ancesRootYPos = 0;
		double descRootYPos = 0;
		
		//This is to make sure the Offset from the center is equal
		if (session.getOptions().isIncludeSpouses())
		{
			boxStyles.getDescStyle(0).setOffset(boxStyles.getAncesStyle(0).rootBackOffset);
			boxStyles.getDescStyle(0).setWidth(boxStyles.getAncesStyle(0).getBoxWidth());
		}
		else if (session.getOptions().getDescGens() == 0){
			boxStyles.getDescStyle(0).setOffset(boxStyles.getAncesStyle(0).getRelativeOffset());
			boxStyles.getDescStyle(0).setWidth(boxStyles.getAncesStyle(0).getBoxWidth());
		}
		else
		{
			boxStyles.getAncesStyle(0).setOffset(boxStyles.getDescStyle(0).rootBackOffset);
			boxStyles.getAncesStyle(0).setWidth(boxStyles.getDescStyle(0).getBoxWidth());
		}
		
		double rootXPos = root.originatingPages.isEmpty()?0:boxStyles.getAncesStyle(0).endLineArrowHeadLength + boxStyles.getAncesStyle(0).endLineArrowShaftLength;
		
		double scaler = 1;
		double dscaler = paperHeight / descHeight;

		//set height modifier so that the chart is the correct size
		if (ancesHeight >= descHeight && descTrees != null && ancesTrees != null)
		{
			scaler = paperHeight / ancesHeight;
			//double scaler = ancesBox.setHeight(paperHeight);
			for (AncesTree tree: ancesTrees)
				tree.ancesBox.setScaler(scaler < 1.0 ? 1.0 : scaler);
			for (DescTree tree: descTrees)
				if(ops.isIncludeSpouses() && ops.getDescGens() > 0)
					tree.descBox.setScaler(dscaler < 1.0 ? 1.0 : dscaler);
				else
					tree.descBox.setScaler(scaler < 1.0 ? 1.0 : scaler);
			//descBox.setScaler(scaler);
			
			ancesHeight *= scaler;
			//rootYPos = -(ancesBox.lowerSubTreeHeight*scaler);
			ancesRootYPos = 0;
			descRootYPos = -ancesTrees.get(ancesTrees.size()-1).ancesBox.lowerSubTreeHeight*scaler;//chart.getYExtent() / 2 - marginSize;
			//descRootYPos += descTrees.get(0).descBox.lowerSubTreeHeight*scaler;
			if(ops.isIncludeSpouses() && ops.getDescGens() > 0)
				descRootYPos=0;
		}
		else if (descTrees != null && ancesTrees != null)
		{
			scaler = paperHeight / descHeight;//descBox.setHeight(paperHeight);
			if(ops.isIncludeSpouses() && ops.getDescGens() > 0)
				scaler = paperHeight / ancesHeight;
			for (DescTree tree: descTrees)
			{
				if(ops.isIncludeSpouses() && ops.getDescGens() > 0)
					tree.descBox.setScaler(dscaler < 1.0 ? 1.0 : dscaler);
				else
					tree.descBox.setScaler(scaler < 1.0 ? 1.0 : scaler);
			}
			for (AncesTree tree: ancesTrees)
			{
				tree.ancesBox.setScaler(scaler < 1.0 ? 1.0 : scaler);
			}
			
			//ancesRootYPos = descRootYPos = -descTrees.get(0).descBox.lowerSubTreeHeight;
			descRootYPos = 0;
			ancesRootYPos = -descTrees.get(descTrees.size()-1).descBox.lowerSubTreeHeight*scaler;//chart.getYExtent() / 2 - marginSize;
			ancesRootYPos += ancesTrees.get(ancesTrees.size()-1).ancesBox.lowerSubTreeHeight*scaler;
			if(ops.isIncludeSpouses() && ops.getDescGens() > 0)
				ancesRootYPos=0;
		}
				
		//draw boxes on chart
		//ancesBox.drawAncesRootTree(chartMargins, ops, ancesGenPositions, 0, rootYPos);
		//descBox.drawDescRootTree(chartMargins, ops);
		double maxYPos=0,minYPos=paperHeight;
		if (ancesTrees != null)
		{
			for (int i = ancesTrees.size()-1; i >= 0; --i)
			{
				AncesTree tree = ancesTrees.get(i);
				ancesRootYPos += -(tree.ancesBox.lowerSubTreeHeight*scaler);
				tree.DrawTree(chartMargins, ops, rootXPos, ancesRootYPos, session);
				//if include spouse and descendants we need to connect some lines.
				if(ops.isIncludeSpouses() && ops.getDescGens() > 0){
					if(ancesRootYPos > maxYPos)
						maxYPos = ancesRootYPos;
					if(ancesRootYPos < minYPos)
						minYPos = ancesRootYPos;
					double descXPos = boxStyles.getTotalDescWidth(2, descGens) + boxStyles.getTotalDescOffset(1, descGens - 1);
					double midDistance = (rootXPos - descXPos - boxStyles.getTotalDescWidth(1, 1))/2;
					chartMargins.addDrawCommand(new DrawCmdMoveTo(chartMargins.xOffset(rootXPos-midDistance),chartMargins.yOffset(ancesRootYPos)));
					chartMargins.addDrawCommand(new DrawCmdRelLineTo(midDistance,0,1,Color.black));
				}
					ancesRootYPos += tree.ancesBox.upperSubTreeHeight* scaler;
			}
		}
		
		if (descTrees != null)
		{
			//works differently if spouse is included.
			if(ops.isIncludeSpouses() && ops.getDescGens() > 0){
				descRootYPos=0;
				DescTree t = descTrees.get(descTrees.size()-1);
				ArrayList<DescBoxParent> children = t.descBox.children;
				double descXPos = boxStyles.getTotalDescWidth(2, descGens) + boxStyles.getTotalDescOffset(1, descGens - 1);
				double midDistance = (rootXPos - descXPos - boxStyles.getTotalDescWidth(1, 1))/2;
				
				//Draw each child
				for (int i = children.size()-1; i >= 0; --i){
					DescBoxParent child = children.get(i);
					descRootYPos += -(child.lowerSubTreeHeight*dscaler);
					child.drawDescRootTree(chartMargins, ops, t.descGenPositions, descXPos, descRootYPos, session);
					chartMargins.addDrawCommand(new DrawCmdMoveTo(chartMargins.xOffset(descXPos+boxStyles.getTotalDescWidth(1, 1)),chartMargins.yOffset(descRootYPos)));
					chartMargins.addDrawCommand(new DrawCmdRelLineTo(midDistance,0,1,Color.black));
					if(descRootYPos > maxYPos)
						maxYPos = descRootYPos;
					if(descRootYPos < minYPos)
						minYPos = descRootYPos;
					descRootYPos += child.upperSubTreeHeight*dscaler;
				}
				chartMargins.addDrawCommand(new DrawCmdMoveTo(chartMargins.xOffset(descXPos+boxStyles.getTotalDescWidth(1, 1)+midDistance),chartMargins.yOffset(minYPos)));
				chartMargins.addDrawCommand(new DrawCmdRelLineTo(0,maxYPos-minYPos,1,Color.black));
			}
			else
				for (int i = descTrees.size()-1; i >= 0; --i)
				{
					DescTree tree = descTrees.get(i);
					descRootYPos += -(tree.descBox.lowerSubTreeHeight*scaler);
					tree.DrawTree(chartMargins, ops, rootXPos, descRootYPos, session);
					descRootYPos += tree.descBox.upperSubTreeHeight*scaler;
				}
		}
		
		
		//draw root connection lines
		if (descGens == 0)
		{
			//if only 2 spouses and no descendants, draw line between the two boxes
			if (ancesTrees.size() == 2)
			{
				AncesBox rootBox = ancesTrees.get(0).ancesBox;
				AncesBox spouseBox = ancesTrees.get(1).ancesBox;
				chartMargins.addDrawCommand(new DrawCmdMoveTo(chartMargins.xOffset( ops.isAllowIntrusion() ? boxStyles.getTotalAncesWidth(0, 0) / 3.0: boxStyles.getTotalAncesWidth(0, 0) / 2.0),
															chartMargins.yOffset( -spouseBox.lowerSubTreeHeight*scaler + spouseBox.upperBoxBound) ));
				chartMargins.addDrawCommand(new DrawCmdRelLineTo(0,((spouseBox.upperSubTreeHeight - rootBox.lowerSubTreeHeight)*scaler) - spouseBox.upperBoxBound + rootBox.lowerBoxBound,1,Color.BLACK ));
			}
			
			//: for 3 or more spouses, draw a connecting line to the side
		}
		
		
		chart.addDrawCommand(new DrawCmdMoveTo(session.getOpgOptions().getChartMargins().getLeft(), session.getOpgOptions().getChartMargins().getBottom()));
		Font regFont = session.getOpgOptions().getFont().getFont(Font.PLAIN, 12);
		chart.addDrawCommand(new DrawCmdSetFont(regFont, Color.BLACK));
		//TODO this is where the page number is put
		chart.addDrawCommand(new DrawCmdText(root.pageId.toString()));
		
		
		
		//draw titles on chart
		if (ops.drawTitles)
			drawTitles(session, rootXPos);
		
		//draw Logo on chart - branding
		OpgPage page = session.getPageByRoot(root.id);
		if(page != null){
			if(page.getMaker(page.getMakerList().size()-1) == page.getMakerByRoot(root.id))
			drawLogo(session);
		}
		for (ImageFile f : images){
			chart.addDrawCommand(new DrawCmdMoveTo(f.x, f.y));
			chart.addDrawCommand(new DrawCmdPicture(f.width, f.height, f.getImage()));
		}
		//session.resetChanged();

	}
	
	protected void drawTitles(OpgSession session, double rootXPos)
	{
		//choose font size for generation labels
		FontRenderContext frc = NameAbbreviator.frc;
		
		//draw each generation label
		Font font = OpgFont.getDefaultSansSerifFont(Font.BOLD, labelFontSize);
		LineMetrics lm = font.getLineMetrics("gjpqyjCAPSQJbdfhkl", frc);
		ChartMarginData margins = session.getOpgOptions().getChartMargins();
		double horizPos = rootXPos + margins.getLeft();
		double vertPos = (ops.isLandscape())? ops.getPaperWidth().width : ops.getPaperLength();// - marginSize - headerSize + (2*lm.getLeading()) + lm.getDescent();
		vertPos += -margins.getTop() - headerSize + (2*lm.getLeading()) + lm.getDescent();
		//draw ancestor labels
		chart.addDrawCommand(new DrawCmdSetFont(font, Color.RED));
		//for(int gen = 0; gen <= ancesTrees.get(0).ancesBox.maxVisibleGensInTree; ++gen)
		int max = ancesTrees.get(0).ancesBox.maxVisibleGensInTree;
		if(max > ancesGens)
		{
			max = ancesGens;
		}
		if(this == session.getBaseMaker() && ancesGens ==5)
		{
			max = ancesGens-1;
		}
		for(int gen = 0; gen <= max; ++gen)
		{
			//next six lines added to get the correct style type for the else in the if(gen<0) statement. commented out line of else replaced by the line above
			ArrayList<StylingBoxScheme> styles;
			if(ops.getAncesGens() == 5)
				styles = StyleBoxFactory.getStyleListFor5(ancesTrees.get(0).ancesBox.maxVisibleGensInTree, ops.getDescGens(), !root.originatingPages.isEmpty());
			else
				styles = StyleBoxFactory.getStyleList(ancesTrees.get(0).ancesBox.maxVisibleGensInTree, ops.getDescGens(), !root.originatingPages.isEmpty());
			double curBoxWidth;
			
			if (gen < 0)
				curBoxWidth = boxStyles.getDescStyle(-gen).getBoxWidth();
			else
				curBoxWidth = styles.get(0).getAncesStyle(gen).getBoxWidth();
				//curBoxWidth = boxStyles.getAncesStyle(gen).getBoxWidth();
			
			double width = font.getStringBounds(getGenerationLabel(gen + this.getGenOffset(), curBoxWidth), frc).getWidth();
			//draw label centered above boxes for generation, and justified left for intruding boxes
			//if (boxStyles.getAncesStyle(gen).isIntruding == true)
			if (styles.get(0).getAncesStyle(gen).isIntruding == true)
				chart.addDrawCommand(new DrawCmdMoveTo(horizPos, vertPos));
			else
				chart.addDrawCommand(new DrawCmdMoveTo(horizPos + (curBoxWidth - width)/2.0, vertPos ));
			chart.addDrawCommand(new DrawCmdText(getGenerationLabel(gen + this.getGenOffset(), curBoxWidth)));
			//draw spouse's name beneath root's name if root only has one spouse
			if (gen == 0 && ops.isIncludeSpouses() && root.fams.size() == 1 && isPrimaryMaker)
			{
				Individual spouse = (root.gender == Gender.MALE)? root.fams.get(0).wife : root.fams.get(0).husband;
				//If spouse is equal to null, do nothing! 
				if (spouse != null) {	
					double spouseVertPos = vertPos;
					spouseVertPos -= lm.getHeight();
					width = font.getStringBounds("and", frc).getWidth();
					chart.addDrawCommand(new DrawCmdMoveTo(horizPos + (curBoxWidth - width)/2.0, spouseVertPos ));
					chart.addDrawCommand(new DrawCmdText("and"));
					spouseVertPos -= lm.getHeight();
					NameAbbreviator.nameFit(spouse.namePrefix.trim(), spouse.givenName.trim(), spouse.surname, spouse.nameSuffix, (float)curBoxWidth, font);
					String spouseName = NameAbbreviator.getName();
					width = font.getStringBounds(spouseName, frc).getWidth();	
					chart.addDrawCommand(new DrawCmdMoveTo(horizPos + (curBoxWidth - width)/2.0, spouseVertPos ));
					chart.addDrawCommand(new DrawCmdText(spouseName));
				}
			}
			horizPos += curBoxWidth + (gen<0?boxStyles.getDescStyle(-gen - 1).getRelativeOffset():styles.get(0).getAncesStyle(gen).getRelativeOffset());
			//horizPos += curBoxWidth + (gen<0?boxStyles.getDescStyle(-gen - 1).getRelativeOffset():boxStyles.getAncesStyle(gen).getRelativeOffset());
		}
		
	}
	
	protected void drawLogo(OpgSession session)
	{
		ChartMarginData margins = session.getOpgOptions().getChartMargins();
		int fontSize = getLogoFontSize(session);
		Font font = OpgFont.getDefaultSansSerifFont(Font.PLAIN, fontSize);
		chartMargins.addDrawCommand(new DrawCmdMoveTo(chartMargins.getXExtent()-
				(margins.getRight() + font.getStringBounds(programLogo, NameAbbreviator.frc).getWidth()), 
				margins.getBottom()-font.getStringBounds(programLogo, NameAbbreviator.frc).getHeight()));
		chart.addDrawCommand(new DrawCmdSetFont(font,Color.LIGHT_GRAY));
		chart.addDrawCommand(new DrawCmdText(programLogo));
	}
	
	public int getLogoFontSize(OpgSession session){
		int maxSize = 72;
		boolean canFit = false;
		ChartMarginData margins = session.getOpgOptions().getChartMargins();
		double allowedHeight = margins.getBottom()/2.0;
		double allowedWidth = (chartMargins.getXExtent()-(margins.getLeft() + margins.getRight()))/3.0;
		
		while(!canFit && maxSize >= 6){
			Font testFont = OpgFont.getDefaultSansSerifFont(Font.PLAIN, maxSize);
			Rectangle2D rect = testFont.getStringBounds(programLogo, NameAbbreviator.frc);
			
			if(rect.getHeight() <= allowedHeight && rect.getWidth() <= allowedWidth)
				canFit = true;
			else
				maxSize--;
		}
		
		return maxSize;
	}
	
	//this returns an appropriate label for the generation - gen - 0 = self, 1 = parents, -1 = children, etc.
	protected String getGenerationLabel(int gen, double boxWidth)
	{
		switch (gen)
		{
		case 0:
			//return root.givenName + " " + root.middleName + " " + root.surname;//
			Font font = OpgFont.getDefaultSansSerifFont(Font.BOLD, labelFontSize);
			NameAbbreviator.nameFit(root.namePrefix.trim(), root.givenName.trim(), root.surname, root.nameSuffix, (float)boxWidth, font);
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
	
	protected void findLabelFontSize()
	{
		//find width of longest label
		FontRenderContext frc = NameAbbreviator.frc;
		float testFontSize = 72;
		labelFontSize = testFontSize;
		Font font = OpgFont.getDefaultSansSerifFont(Font.BOLD, testFontSize);
		headerSize = 72;
		//iterates through each generation label, finding the one that takes up the most space
		for(int gen = -ops.getDescGens(); gen <= ancesTrees.get(0).ancesBox.maxVisibleGensInTree; ++gen)
		{
			double curBoxWidth = gen < 0? boxStyles.getTotalDescWidth(-gen, -gen):boxStyles.getTotalAncesWidth(gen, gen);
			double width = font.getStringBounds(getGenerationLabel(gen + this.getGenOffset(), curBoxWidth), frc).getWidth();
			float newFontSize = (float)(testFontSize * (curBoxWidth / width));
			if (newFontSize < labelFontSize){
				labelFontSize = newFontSize;
			}
				
		}
		//set font size so that longest label barely fits over box
		//setBoxWidth();
		
		//Make the label size slightly smaller so it's not as tight
		labelFontSize *= 0.75;
		
		final float MAXLABELFONTSIZE = 80;
		if(labelFontSize > MAXLABELFONTSIZE)
			labelFontSize=MAXLABELFONTSIZE;
		LineMetrics lm = font.deriveFont(labelFontSize).getLineMetrics("gjpqyjCAPSQJbdfhkl", frc);
		
		if (ops.isDrawTitles())
			headerSize = titleSize + lm.getHeight() + lm.getLeading();
		else
			headerSize = titleSize;
		
		
	}
	
	
	/**
	 * Goes through all visible ancestors and descendants, returning a LinkedList of their ShapeInfo
	 * @param max visible ancestors, chosen by user
	 * @param max visible descendants, chosen by user
	 * @return LinkedList of ShapeInfo of all visible people
	 */
	public LinkedList<ShapeInfo> getChartShapes(int maxAnc, int maxDesc, OpgSession session) 
	{
		LinkedList<ShapeInfo> retVal = new LinkedList<ShapeInfo>();
		for (AncesTree tree: ancesTrees)
			retVal = tree.ancesBox.getBoxes(retVal, 0, maxAnc, session);
		for (DescTree tree: descTrees)
			retVal = tree.descBox.getBoxes(retVal, 0, maxDesc);
		return retVal;
	}

	/**
	 * Goes through all visible ancestors and descendants, checking if the passed in point intersects with them.
	 * If so, returns the ShapeInfo of that person.
	 * @param x coord of click
	 * @param y coord of click
	 * @param max visible ancestors chosen by user
	 * @param max visible descendants chosen by user
	 * @return the ShapeInfo of the clicked person. Null if no intersect.
	 */
	public ShapeInfo getIndiIntersect(double x, double y, int maxAnc, int maxDesc, OpgSession session) 
	{
		for (AncesTree tree: ancesTrees)
		{
			ShapeInfo retVal = tree.ancesBox.checkIntersect(x, y, 0, maxAnc, session);
			if (retVal != null)
				return retVal;
			
		}
		for (DescTree tree: descTrees)
		{
			ShapeInfo retVal = tree.descBox.checkIntersect(x, y, 0, maxDesc);
			if (retVal != null)
			{

				return retVal;
			}
//			if (retVal == null)
//			{
//				System.out.println("NULL BOX RETURNED? HUH?!");
//			}
		}
			
				
		return null;
	}
	
	public StylingBoxScheme getBoxStyles(){
		return boxStyles;
	}



	
	public void setChartStyle(StylingBoxScheme style) {
		boxStyles = style;
		
	}
	
	public int getGenOffset(){
		return generationalOffset;
	}
	
	public void setGenOffset(int i){
		generationalOffset = i;
	}
	
	public ArrayList<ImageFile> getImages(){
		return images;
	}

	@Override
	public void setIsPrimaryMaker(boolean set) {
		isPrimaryMaker = set;
	}
	
	public void addMakerToAddList(Individual indi, int genOffset){
		toAddMakerList.add(new Pair<Individual, Integer>(indi, genOffset));
	}
	
	public void processAddedMakers(OpgSession session){
		for (Pair<Individual, Integer> pair : toAddMakerList){
			pair.getLeft().addToOriginatingPages(root);
			session.addMaker(ChartType.MULTISHEET, pair.getLeft(), pair.getRight()+this.getGenOffset());
		}
		toAddMakerList.clear();
	}

}


