package edu.byu.cs.roots.opg.chart.selectvertical;

import java.awt.Color;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JPanel;

import edu.byu.cs.roots.opg.chart.ChartDrawInfo;
import edu.byu.cs.roots.opg.chart.ChartMaker;
import edu.byu.cs.roots.opg.chart.ChartOptions;
import edu.byu.cs.roots.opg.chart.ShapeInfo;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdMoveTo;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdRelLineTo;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdSetFont;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdText;
import edu.byu.cs.roots.opg.chart.preset.templates.StylingBoxScheme;


import edu.byu.cs.roots.opg.fonts.OpgFont;
import edu.byu.cs.roots.opg.gui.OnePageMainGui;
import edu.byu.cs.roots.opg.model.Family;
import edu.byu.cs.roots.opg.model.Gender;
import edu.byu.cs.roots.opg.model.ImageFile;
import edu.byu.cs.roots.opg.model.Individual;
import edu.byu.cs.roots.opg.model.OpgOptions;
import edu.byu.cs.roots.opg.model.OpgSession;
import edu.byu.cs.roots.opg.model.PaperWidth;
import edu.byu.cs.roots.opg.util.NameAbbreviator;

public class SelectVerticalChartMaker implements ChartMaker {
	private static final long serialVersionUID = 1L;
	protected ChartDrawInfo chart = null;
	protected ChartMargins chartMargins = null;
	
	//options for current chart (this changes the state variables)
	protected VerticalChartOptions ops;
	
	//state variables
	protected float maxFont = 12, minFont=8;
	protected OpgFont font;
	protected double paperHeight;
	protected double paperWidth;
	protected int ancesGens = -1;
	protected int descGens = -1;
	protected boolean includeSpouses;
	protected boolean landscape = false;
	protected Individual root = null; //the root individual currently on the tree
	protected boolean roundedCorner = true;
	protected boolean boxBorder = true;
	protected boolean allowIntrude = true;
	protected boolean genLabels = true;
	
	//state changed flags (set by updateStateChanges())
	protected boolean includeSpouseChanged = false;
	protected boolean paperSizeChanged = false;
	protected boolean fontChanged = false;
	protected boolean fontSizeChanged = false;
	protected boolean descGenChanged = false;
	protected boolean ancesGenChanged = false;
	protected boolean rootChanged = false;
	protected boolean landscapeChanged = false;
	protected boolean specificOpChanged = false;
	
	
	//Tree variables (dependent on the state variables)
	protected ArrayList<AncesTree> ancesTrees;
	protected ArrayList<DescTree> descTrees;
	protected double ancesMinHeight;
	protected double descMinHeight;
	protected AncesBox ancesBox = null;
	protected DescBox descBox = null;
	protected ArrayList<ArrayList<AncesBox>> ancesGenPositions;
	protected int[] maxSpouseLineOffset;
	protected int maxGensOnPage;
	protected double whiteSpace;
	
	
	//chart margin size - consider moving to VerticalChartOptions
	double marginSize = 72;//one-inch margins
	double headerSize = 0;//size (in points) of title(s) at top of chart
	double titleSize = 0;//size of chart title (to be implemented later)
	float labelFontSize = 12;//font size of generation labels at top of chart 
	
	private boolean isPrimaryMaker = false;
	
	public ChartOptions convertToSpecificOptions(ChartOptions options) {
		VerticalChartOptions newOptions = new VerticalChartOptions(options);

		//set default values for options specific to Verical Chart here
		newOptions.setBoxBorder(true);
		newOptions.setRoundedCorners(true);
		newOptions.setDrawTitles(true);
		newOptions.setAllowIntrusion(true);
		newOptions.setMinPaperLength(12*72); // 1 foot
		return newOptions;
	}
	
	public void convertOpgOptions(OpgOptions options){
		
	}

	public ChartDrawInfo getChart(ChartOptions options, OpgSession session) {
		//  Account for margins and titles
		ops = (VerticalChartOptions) options;
		OpgOptions opgOptions = session.getOpgOptions();
		//sets state change flags and updates state variables with the currently selected options
		updateStateChanges(session);
		
		//create new tree if root of spouse change / draw new chart
		if (chart == null || rootChanged || includeSpouseChanged || landscapeChanged)
		{
			chart = new ChartDrawInfo(0,0);
			chartMargins = new ChartMargins(chart, marginSize, headerSize);
			createTree(session);
			if(ancesGens <= opgOptions.getMaxAncesSlider())
			{
				modifyChart(session);
				generateChart(session);
			}
		}
		
		//modify chart if settings change
		else if(paperSizeChanged || ancesGenChanged || descGenChanged || fontSizeChanged || fontChanged || specificOpChanged )
		{
			BoxFormat.setFont(font);
			if(ancesGens <= opgOptions.getMaxAncesSlider())
			{
				modifyChart(session);
				generateChart(session);
			}
		}
		
		return chart;
	}

	public JPanel getSpecificOptionsPanel(ChartOptions options, OnePageMainGui parent)
	{
		//options.setMinFontSize(12);
		//options.setMaxFontSize(200);
		return new VerticalChartOptionsPanel(options, parent);
		
	}
	
	protected void updateStateChanges(OpgSession session)
	{
		//set state change flags
		rootChanged = root != ops.getRoot();
		includeSpouseChanged = includeSpouses != ops.isIncludeSpouses();
		paperSizeChanged = ops.paperHeight() != paperHeight || ops.paperWidth() != paperWidth;
		ancesGenChanged = ancesGens != ops.getAncesGens();
		descGenChanged = descGens != ops.getDescGens();
		fontSizeChanged =  maxFont != session.getOpgOptions().getMaxFontSize() || minFont != session.getOpgOptions().getMinFontSize();
		fontChanged = font != session.getOpgOptions().getFont();
		landscapeChanged  =  landscape != ops.isLandscape();
		specificOpChanged = roundedCorner != ops.isRoundedCorners() || boxBorder != ops.isBoxBorder() ||
							allowIntrude != ops.isAllowIntrusion() || genLabels != ops.isDrawTitles();
		
		//update
		root = ops.getRoot();
		ancesGens = ops.getAncesGens();
		descGens = ops.getDescGens();
		paperHeight = ops.paperHeight();
		paperWidth = ops.paperWidth();
		includeSpouses = ops.isIncludeSpouses();
		maxFont = session.getOpgOptions().getMaxFontSize();
		minFont = session.getOpgOptions().getMinFontSize();
		font = session.getOpgOptions().getFont();
		landscape = ops.isLandscape();
		roundedCorner = ops.isRoundedCorners();
		boxBorder = ops.isBoxBorder();
		allowIntrude = ops.isAllowIntrusion();
		genLabels = ops.isDrawTitles();
		
	}
	
	/**
	 * Build ancestor Trees (ancesTrees) and and descendant trees (descTress). Sets the maximum generation that
	 * are able to fit on a page.
	 */
	protected void createTree(OpgSession session)
	{
		AncesBox.resetDuplicates();
		OpgOptions opgOptions = session.getOpgOptions();
		//set up list of AncesTree
		ancesTrees = new ArrayList<AncesTree>();
		//add root ancesTree
		ancesTrees.add(new AncesTree(root)); //create main tree
		
		int maxOfMaxAncesGens = ancesTrees.get(0).ancesBox.maxGensInTree-1;
		//add spouse ancesTrees
		if (includeSpouses)
			for (Family fam : root.fams)
			{
				Individual spouse = (root.gender == Gender.MALE)? fam.wife : fam.husband;
				if (spouse != null)
				{
					ancesTrees.add(new AncesTree(spouse));
					int spouseMaxAncesGens = ancesTrees.get(ancesTrees.size()-1).ancesBox.maxGensInTree-1;
					if (spouseMaxAncesGens > maxOfMaxAncesGens)
						maxOfMaxAncesGens = spouseMaxAncesGens;
				}
			}
		
		//initialize generation formats
		AncesBox.genFormats.clear();
		for (int i=0; i < maxOfMaxAncesGens+1; i++)
			AncesBox.genFormats.add(BoxFormat.FORMATS.get(BoxFormat.FORMATS.size()-1)); //smallest box
		
		
		//Set numbers that label duplicates 
		for (AncesTree tree: ancesTrees)
			tree.setDuplicateNumbers();
		
		
		//System.out.println("maxancesgen = " + maxOfMaxAncesGens);
		
		//-----find max ances
		
		//maxGensOnPage = maxOfMaxAncesGens+1;
		
		whiteSpace =  marginSize*2 + headerSize;
		
		double maxChartHeight = ancesMinHeight = (ops.isLandscape())? PaperWidth.maxChartWidth : PaperWidth.maxChartWidth*3;

		
		//get the height-constrained generation
		maxGensOnPage = ancesTrees.get(0).getGenAtMaxHieght(ancesMinHeight, BoxFormat.FORMATS.get(BoxFormat.FORMATS.size()-1));
		
		//decrease number of ancestor generations until chart fits in maximum allowable length
		do {
			maxGensOnPage--;
			for (AncesTree tree: ancesTrees)
				tree.ancesBox.calcCoords(maxGensOnPage, 0);
			updateAncesHeight(); 
		}while(ancesMinHeight + whiteSpace > maxChartHeight);
		
		//System.out.println("maxonpage = " + maxGensOnPage);
		
		//---end find max ances

		opgOptions.setMaxAncesSlider(maxGensOnPage, isPrimaryMaker);
		
		if(ops.getAncesGens() > opgOptions.getMaxAncesSlider())
		{
			ops.setAncesGens(opgOptions.getMaxAncesSlider(), session);
			ancesGens = opgOptions.getMaxAncesSlider();
		}
		
		//set up DescTrees
		for (Family fam: root.fams)
			fam.resetFlags();
		descTrees = new ArrayList<DescTree>();
		descTrees.add(new DescTree(root));
		int maxOfMaxDescGens = descTrees.get(0).descBox.maxGensInTree-1;
		ops.setDescGens(0, session);
		
		if (ops.getDescGens() < maxOfMaxDescGens)
		{
			if (opgOptions.getMaxDescSlider() < maxOfMaxDescGens)
				opgOptions.setMaxDescSlider(maxOfMaxDescGens, isPrimaryMaker);
		//	ops.setDescGens(maxOfMaxDescGens);
		}
		opgOptions.setMaxDescSlider(maxOfMaxDescGens, isPrimaryMaker);

	}
	

	protected void modifyChart(OpgSession session)
	{	
		OpgOptions opgOptions = session.getOpgOptions();
		//calculate needed size for generation labels
		setBoxWidth();
		findLabelFontSize();
	
		for (DescTree tree: descTrees)
		{
			tree.calcCoords(ops);
			tree.descBox.minHeight = tree.descBox.upperSubTreeHeight - tree.descBox.lowerSubTreeHeight;
			
			//expand boxes to fill gaps
			tree.descBox.vPos = 0;
			tree.descBox.setRelativePositions(0, ops.getDescGens());
			
			updateDescHeight();
		}
			

		updateWhiteSpace();
			
		int maxGens = opgOptions.getMaxAncesSlider()+1;

		//reset generation formats
		for (int i=0; i < maxGens; i++)
			AncesBox.genFormats.set(i,BoxFormat.FORMATS.get(7)); //smallest box
		//decrease number of ancestor generations until chart fits in maximum allowable length	
		do {
			maxGens--;
			//ops.setAncesGens(maxGen);
			for (AncesTree tree: ancesTrees)
				tree.ancesBox.calcCoords(maxGens, 0);
			updateAncesHeight();
		}while(ancesMinHeight + whiteSpace > ops.paperHeight());
		maxGensOnPage = maxGens;
		
		
		//decrease number of descendant generations until chart fits in maximum allowable length
		while (descMinHeight + whiteSpace > ops.paperHeight())
			{
			opgOptions.setMaxDescSlider(ops.getDescGens()-1, isPrimaryMaker);
			ops.setDescGens(opgOptions.getMaxDescSlider(), session);
			for (DescTree tree: descTrees)
			tree.calcCoords(ops);
			updateDescHeight();
			updateWhiteSpace();
		}
		
		//System.out.println("m:"+maxGensOnPage);
		//System.out.println("ag"+maxGensOnPage);
		if(ops.getAncesGens() > maxGensOnPage)
		{
			ops.setAncesGens(maxGensOnPage, session);
			ancesGens = maxGensOnPage;
		}
		increaseGenBoxSizes5();
		
	}
	
	/**Alternate formatting algorithm */
	protected void increaseGenBoxSizes1()
	{
		int maxGen = ancesGens;
		double whiteSpace =  marginSize*2 + headerSize; 
		
		//starting at the leaves increase generation box sizes until the chart doesn't fit on the page
		//for(int i=maxGen; i >= 0; i--) //go through all generation starting at leaves
		for(int i=0; i <= maxGen; i++)
		{
			int f = 0;   //reset format box index
			do 
			{
				if (f >= BoxFormat.FORMATS.size())  //no more formats?
					break;
				
				AncesBox.genFormats.set(i,BoxFormat.FORMATS.get(f++)); //set generation i's format
				
				ancesMinHeight = 0; //reset ancestor tree height
				
				//run spacing algorithm on all ances trees
				for (AncesTree tree: ancesTrees)
				{
					tree.ancesBox.calcCoords(maxGen, 0);
					ancesMinHeight += tree.ancesBox.upperSubTreeHeight - tree.ancesBox.lowerSubTreeHeight;
				}
				
			} while(ancesMinHeight + whiteSpace > ops.paperHeight()); //check if chart fits on the page
		}
	}
	
	/**Alternate formatting algorithm */
	protected void increaseGenBoxSizes2()
	{
		int maxGen = ancesGens;
		double whiteSpace =  marginSize*2 + headerSize; 
		
		//starting at the leaves increase generation box sizes until the chart doesn't fit on the page
		for(int i=maxGen; i >= 0; i--) //go through all generation starting at leaves
		{
			int f = 0;   //reset format box index
			do 
			{
				if (f >= BoxFormat.FORMATS.size())  //no more formats?
					break;
				
				AncesBox.genFormats.set(i,BoxFormat.FORMATS.get(f++)); //set generation i's format
				
				ancesMinHeight = 0; //reset ancestor tree height
				
				//run spacing algorithm on all ances trees
				for (AncesTree tree: ancesTrees)
				{
					tree.ancesBox.calcCoords(maxGen, 0);
					ancesMinHeight += tree.ancesBox.upperSubTreeHeight - tree.ancesBox.lowerSubTreeHeight;
				}

			} while(ancesMinHeight + whiteSpace > ops.paperHeight()); //check if chart fits on the page
		}
	}
	
	/**Alternate formatting algorithm */
	protected void increaseGenBoxSizes()
	{
		int maxGen = ancesGens;
		double whiteSpace =  marginSize*2 + headerSize; 
		int[] f = new int[ancesGens+1];
		
		//reset generation formats
		for (int i=0; i <= maxGen; i++)
		{
			AncesBox.genFormats.set(i,BoxFormat.FORMATS.get(0)); 
			f[i] = 1;
		}
		int i=maxGen;
		while(true)
		{
			if (f[i] >= BoxFormat.FORMATS.size())  //no more formats?
				continue;
				
			AncesBox.genFormats.set(i,BoxFormat.FORMATS.get(f[i]++)); //set generation i's format
				
			ancesMinHeight = 0; //reset ancestor tree height
				
			//run spacing algorithm on all ances trees
			for (AncesTree tree: ancesTrees)
			{
				tree.ancesBox.calcCoords(maxGen, 0);
				ancesMinHeight += tree.ancesBox.upperSubTreeHeight - tree.ancesBox.lowerSubTreeHeight;
			}

			if (!(ancesMinHeight + whiteSpace > ops.paperHeight()))
				break;
			i = (i <= 0)? maxGen : i - 1;
		}
	}
	
	/**Alternate formatting algorithm */
	protected void increaseGenBoxSizes4()
	{
		int maxGen = ancesGens;
		double whiteSpace =  marginSize*2 + headerSize; 
		int[] f = new int[ancesGens+1];
		
		//reset generation formats
		for (int i=0; i <= maxGen; i++)
		{
			AncesBox.genFormats.set(i,BoxFormat.FORMATS.get(0)); 
			f[i] = 1;
		}
		int i=maxGen;
		while(true)
		{
			if (f[i] >= BoxFormat.FORMATS.size())  //no more formats?
				continue;
			

			if(i >= ancesTrees.get(0).getLargestGen())	
			{
			AncesBox.genFormats.set(i,BoxFormat.FORMATS.get(f[i]++)); //set generation i's format
				
			ancesMinHeight = 0; //reset ancestor tree height
				
			//run spacing algorithm on all ances trees
			for (AncesTree tree: ancesTrees)
			{
				tree.ancesBox.calcCoords(maxGen, 0);
				ancesMinHeight += tree.ancesBox.upperSubTreeHeight - tree.ancesBox.lowerSubTreeHeight;
			}
			}
			if (ancesMinHeight + whiteSpace <= ops.paperHeight())
				break;
			i = (i <= 0)? maxGen : i - 1;
		}
	}
	
	/**Current formatting algorithm */
	protected void increaseGenBoxSizes5()
	{
		int maxGen = ancesGens;
		int maxDiff = 1;
		
		int maxFormat = BoxFormat.FORMATS.size()-1;
		while(maxFormat >= 0 && BoxFormat.FORMATS.get(maxFormat).getMinHeight() > 1.1*BoxFormat.getGenWidth())
			maxFormat--;
		//maxFormat = 7;
		updateWhiteSpace();
		int[] f = new int[maxGen+1];
		//reset generation formats
		for (int i=0; i <= maxGen; i++)
		{
			f[i] = BoxFormat.FORMATS.size()-1;
			AncesBox.genFormats.set(i,BoxFormat.FORMATS.get(f[i])); 
		}
		int i=0;
		int stopGen = maxGen;
		while(true)
		{
			AncesBox.genFormats.set(i,BoxFormat.FORMATS.get(f[i])); //set generation i's format
			
			//run spacing algorithm on all ances trees
			for (AncesTree tree: ancesTrees)
				tree.ancesBox.calcCoords(maxGen, 0);
				
			updateAncesHeight();
			updateWhiteSpace();
			if (ancesMinHeight + whiteSpace > ops.paperHeight())
			{ 
				AncesBox.genFormats.set(i,BoxFormat.FORMATS.get(++f[i])); //decrease box size of current generation
				stopGen = i-1;
			}
			
			--f[i]; //increase box size
			if(f[i] > maxFormat)
				break;
			if (i < maxGen && (f[i+1] - f[i]) > maxDiff) //too much difference between generations
				break;
			if (f[i] < 0 || stopGen < 0)  //no more formats or at the end
				break;
			
			i = (i >= stopGen)? 0 : i + 1;
		}
		
		//run spacing again
		for (AncesTree tree: ancesTrees)
			tree.ancesBox.calcCoords(maxGen, 0);
		updateAncesHeight();
		
	}
	
	
	
	protected void updateAncesHeight()
	{
		//ancesMinHeight = ancesTrees.get(0).ancesBox.upperSubTreeHeight - ancesTrees.get(0).ancesBox.lowerSubTreeHeight;
		ancesMinHeight = 0;
		for (AncesTree tree: ancesTrees)
		{
			ancesMinHeight += tree.getHeight();
		}
	}
	
	protected void updateDescHeight()
	{
		descMinHeight = 0;
		if(ops.isIncludeSpouses() && ops.getDescGens() > 0){
			ArrayList<DescBox> descs = descTrees.get(0).descBox.children;
			for(DescBox box: descs)
				descMinHeight += box.upperSubTreeHeight - box.lowerSubTreeHeight;
		}
		else
			for (DescTree tree: descTrees)
			{
				descMinHeight += tree.descBox.upperSubTreeHeight - tree.descBox.lowerSubTreeHeight;
			}
	}

	protected void generateChart(OpgSession session)
	{
		
		//width and height vary based on paper orientation
		double paperHeight = ops.paperHeight();
		double paperWidth = ops.paperWidth();
		setBoxWidth();
		
		//calculate needed size for generation labels
		findLabelFontSize();
		
		//create new chart
		chart = new ChartDrawInfo((int)paperWidth, (int)paperHeight);
		chartMargins = new ChartMargins(chart, marginSize);
		
		paperHeight -= marginSize*2 + headerSize;
		paperWidth -= marginSize*2;
		
		//updateDescHeight();
		//updateAncesHeight();
		//double ancesHeight = ancesBox.upperSubTreeHeight - ancesBox.lowerSubTreeHeight;
		double ancesHeight = ancesMinHeight;
		double descHeight = descMinHeight;//descBox == null ? 0 : descBox.upperSubTreeHeight - descBox.lowerSubTreeHeight;
		//double chartHeight = Math.max(ancesHeight, descHeight);
		double ancesRootYPos = 0;
		double descRootYPos = 0;
		double rootXPos = AncesBox.boxWidth*11.0/10.0 * descGens;
		
		double scaler = 1;
		double dscaler = paperHeight / descHeight;

		//set height modifier so that the chart is the correct size
		if (ancesHeight >= descHeight && descTrees != null && ancesTrees != null) //ancestral tree is larger
		{
			scaler = paperHeight / ancesHeight;
		
			
			//double scaler = ancesBox.setHeight(paperHeight);
			for (AncesTree tree: ancesTrees)
				//tree.ancesBox.setScaler(scaler < 1.0 ? 1.0 : scaler);
				tree.ancesBox.setScaler(scaler < 1.0 ? 1.0 : scaler);
			for (DescTree tree: descTrees)
				if(ops.isIncludeSpouses() && ops.getDescGens() > 0)
					//tree.descBox.setScaler(dscaler < 1.0 ? 1.0 : dscaler);
					tree.descBox.setScaler(1);
				else
					//tree.descBox.setScaler(scaler < 1.0 ? 1.0 : scaler);
					tree.descBox.setScaler(1);
			
			//descBox.setScaler(scaler);
			
			ancesHeight *= scaler;
			//rootYPos = -(ancesBox.lowerSubTreeHeight*scaler);
			ancesRootYPos = 0;
			descRootYPos = -ancesTrees.get(ancesTrees.size()-1).ancesBox.lowerSubTreeHeight*scaler;//chart.getYExtent() / 2 - marginSize;
			descRootYPos += descTrees.get(0).descBox.lowerSubTreeHeight*scaler;
			if(ops.isIncludeSpouses() && ops.getDescGens() > 0)
				descRootYPos=0;
		}
		else if (descTrees != null && ancesTrees != null) //descendant tree is larger
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
				tree.DrawTree(chartMargins, ops, rootXPos, ancesRootYPos);
				//if include spouse and descendants we need to connect some lines.
				if(ops.isIncludeSpouses() && ops.getDescGens() > 0){
					if(ancesRootYPos > maxYPos)
						maxYPos = ancesRootYPos;
					if(ancesRootYPos < minYPos)
						minYPos = ancesRootYPos;
					double descXPos = AncesBox.boxWidth*11.0/10.0 * (descGens - 1);
					double midDistance = (rootXPos - descXPos - AncesBox.boxWidth)/2;
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
				ArrayList<DescBox> children = t.descBox.children;
				double descXPos = AncesBox.boxWidth*11.0/10.0 * (descGens - 1);
				double midDistance = (rootXPos - descXPos - AncesBox.boxWidth)/2;
				
				//Draw each child
				for (int i = children.size()-1; i >= 0; --i){
					DescBox child = children.get(i);
					descRootYPos += -(child.lowerSubTreeHeight*dscaler);
					child.drawDescRootTree(session, chartMargins, ops, t.descGenPositions, descXPos, descRootYPos);
					chartMargins.addDrawCommand(new DrawCmdMoveTo(chartMargins.xOffset(descXPos+AncesBox.boxWidth),chartMargins.yOffset(descRootYPos)));
					chartMargins.addDrawCommand(new DrawCmdRelLineTo(midDistance,0,1,Color.black));
					if(descRootYPos > maxYPos)
						maxYPos = descRootYPos;
					if(descRootYPos < minYPos)
						minYPos = descRootYPos;
					descRootYPos += child.upperSubTreeHeight*dscaler;
				}
				chartMargins.addDrawCommand(new DrawCmdMoveTo(chartMargins.xOffset(descXPos+AncesBox.boxWidth+midDistance),chartMargins.yOffset(minYPos)));
				chartMargins.addDrawCommand(new DrawCmdRelLineTo(0,maxYPos-minYPos,1,Color.black));
			}
			else
				for (int i = descTrees.size()-1; i >= 0; --i)
				{
					DescTree tree = descTrees.get(i);
					descRootYPos += -(tree.descBox.lowerSubTreeHeight*scaler);
					tree.DrawTree(session, chartMargins, ops, rootXPos, descRootYPos);
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
				chartMargins.addDrawCommand(new DrawCmdMoveTo(chartMargins.xOffset( ops.isAllowIntrusion() ? AncesBox.boxWidth / 3.0: AncesBox.boxWidth / 2.0),
															chartMargins.yOffset( -spouseBox.lowerSubTreeHeight*scaler + spouseBox.upperRelBoxBound) ));
				chartMargins.addDrawCommand(new DrawCmdRelLineTo(0,((spouseBox.upperSubTreeHeight - rootBox.lowerSubTreeHeight)*scaler) - spouseBox.upperRelBoxBound + rootBox.lowerRelBoxBound,1,Color.BLACK ));
			}
			
			//: for 3 or more spouses, draw a connecting line to the side
		}
		
		
		//draw titles on chart
		if (ops.drawTitles)
			drawTitles();
		
		//draw Logo on chart - branding
		drawLogo();
		
		//ops.resetChanged();
		
		

	}
	
	protected void drawTitles()
	{
		//choose font size for generation labels
		FontRenderContext frc = NameAbbreviator.frc;
		
		//draw each generation label
		Font font = OpgFont.getDefaultSansSerifFont(Font.BOLD, labelFontSize);
		LineMetrics lm = font.getLineMetrics("gjpqyjCAPSQJbdfhkl", frc);
		double horizPos = marginSize;
		double vertPos = ops.paperHeight();
		vertPos += -marginSize - headerSize + (2*lm.getLeading()) + lm.getDescent();
		//draw ancestor labels
		chart.addDrawCommand(new DrawCmdSetFont(font, Color.RED));
		for(int gen = -ops.getDescGens(); gen <= ops.getAncesGens(); ++gen)
		{
			double width = font.getStringBounds(getGenerationLabel(gen), frc).getWidth();
			//draw label centered above boxes for generation
			chart.addDrawCommand(new DrawCmdMoveTo(horizPos + (AncesBox.boxWidth - width)/2.0, vertPos ));
			chart.addDrawCommand(new DrawCmdText(getGenerationLabel(gen)));
			//draw spouse's name beneath root's name if root only has one spouse
			if (gen == 0 && ops.isIncludeSpouses() && root.fams.size() == 1)
			{
				double spouseVertPos = vertPos;
				spouseVertPos -= lm.getHeight();
				width = font.getStringBounds("and", frc).getWidth();
				chart.addDrawCommand(new DrawCmdMoveTo(horizPos + (AncesBox.boxWidth - width)/2.0, spouseVertPos ));
				chart.addDrawCommand(new DrawCmdText("and"));
				spouseVertPos -= lm.getHeight();
				Individual spouse = (root.gender == Gender.MALE)? root.fams.get(0).wife : root.fams.get(0).husband;
				NameAbbreviator.nameFit(spouse.namePrefix.trim(), spouse.givenName.trim(), spouse.surname, spouse.nameSuffix, (float)AncesBox.boxWidth, font);
				String spouseName = NameAbbreviator.getName();
				width = font.getStringBounds(spouseName, frc).getWidth();	
				chart.addDrawCommand(new DrawCmdMoveTo(horizPos + (AncesBox.boxWidth - width)/2.0, spouseVertPos ));
				chart.addDrawCommand(new DrawCmdText(spouseName));
			}
			horizPos += AncesBox.boxWidth * 11.0/10.0;
		}
		
	}
	
	protected void drawLogo()
	{
		chartMargins.addDrawCommand(new DrawCmdMoveTo(marginSize+chartMargins.getXExtent()-150, marginSize-12));
		chart.addDrawCommand(new DrawCmdSetFont(OpgFont.getDefaultSerifFont(Font.PLAIN, 12),Color.LIGHT_GRAY));
		chart.addDrawCommand(new DrawCmdText("www.OnePageGenealogy.com"));
	}
	
	//this returns an appropriate label for the generation - gen - 0 = self, 1 = parents, -1 = children, etc.
	protected String getGenerationLabel(int gen)
	{
		switch (gen)
		{
		case 0:
			//return root.givenName + " " + root.middleName + " " + root.surname;//
			Font font = OpgFont.getDefaultSansSerifFont(Font.BOLD, labelFontSize);
			NameAbbreviator.nameFit(root.namePrefix.trim(), root.givenName.trim(), root.surname, root.nameSuffix, (float)AncesBox.boxWidth, font);
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
		for(int gen = -ops.getDescGens(); gen <= ops.getAncesGens(); ++gen)
		{
			//if (gen == 0) continue;
			double width = font.getStringBounds(getGenerationLabel(gen), frc).getWidth();
			if (width > longestWidth)
				longestWidth = width;
		}
		//set font size so that longest label barely fits over box
		setBoxWidth();
		labelFontSize = (float)(testFontSize * (AncesBox.boxWidth / longestWidth));
		final float MAXLABELFONTSIZE = 80;
		if(labelFontSize > MAXLABELFONTSIZE)
			labelFontSize=MAXLABELFONTSIZE;
		LineMetrics lm = font.deriveFont(labelFontSize).getLineMetrics("gjpqyjCAPSQJbdfhkl", frc);
		
		if (ops.isDrawTitles())
			headerSize = titleSize + lm.getHeight() + lm.getLeading();
		else
			headerSize = titleSize;
	}
	
	private void setBoxWidth()
	{
		BoxFormat.setUseGenWidth(true);
		
		double paperWidth = ops.paperWidth();
		int numGens = ops.getAncesGens() + ops.getDescGens();
		AncesBox.boxWidth = (paperWidth - 2*marginSize) / ((11.0/10.0 * numGens) + 1 );
		DescBox.boxWidth = AncesBox.boxWidth; 
		BoxFormat.setGenWidth(AncesBox.boxWidth);
	}

	public LinkedList<ShapeInfo> getChartShapes() {
		//  Auto-generated method stub
		return null;
	}

	public void updateWhiteSpace()
	{
		if (ops.isDrawTitles())
		{
			findLabelFontSize();
		}
		whiteSpace =  marginSize*2 + headerSize;
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

	@Override
	public void setChartStyle(StylingBoxScheme style) {
		
	}

	@Override
	public StylingBoxScheme getBoxStyles() {
		return null;
	}

	@Override
	public ArrayList<ImageFile> getImages() {
		return null;
	}

	@Override
	public void setIsPrimaryMaker(boolean set) {
		isPrimaryMaker = set;
	}

	
}


