package edu.byu.cs.roots.opg.chart.presetvertical;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JPanel;

import edu.byu.cs.roots.opg.chart.ChartDrawInfo;
import edu.byu.cs.roots.opg.chart.ChartMaker;
import edu.byu.cs.roots.opg.chart.ChartOptions;
import edu.byu.cs.roots.opg.chart.ShapeInfo;
import edu.byu.cs.roots.opg.chart.preset.templates.StylingBoxScheme;
import edu.byu.cs.roots.opg.chart.presetvertical.VerticalChartOptions.Change;
import edu.byu.cs.roots.opg.gui.OnePageMainGui;
import edu.byu.cs.roots.opg.model.Gender;
import edu.byu.cs.roots.opg.model.ImageFile;
import edu.byu.cs.roots.opg.model.Individual;
import edu.byu.cs.roots.opg.model.OpgOptions;
import edu.byu.cs.roots.opg.model.OpgSession;

/**
 * ChartMaker for the preset chart.
 */
public class PresetChartMaker implements ChartMaker {
	private static final long serialVersionUID = 1L;
	protected ChartDrawInfo chart = null;
	protected ChartMargins chartMargins = null;
	
	//options for current chart (this changes the state variables)
	protected VerticalChartOptions ops;
	
	protected Tree tree;
	protected AncesTree ancesTree;
	protected DescTree descTree;

	private boolean isPrimaryMaker= false;
	
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
		
		//switch on the type of the change
		switch(ops.getChange()) {
			case CREATE :
				createTree(session);
			case MODIFY :
				modifyChart(session);
			case REDRAW :
				generateChart(session);
		}
		ops.setChange(Change.NONE);
		return chart;
	}

	public JPanel getSpecificOptionsPanel(ChartOptions options, OnePageMainGui parent)
	{
		//options.setMinFontSize(12);
		//options.setMaxFontSize(200);
		return new VerticalChartOptionsPanel(options, parent);
		
	}
	
	/**
	 * Build ancestor Trees (ancesTrees) and and descendant trees (descTress). Sets the maximum generation that
	 * are able to fit on a page. Called when root or generations change.
	 */
	protected void createTree(OpgSession session)
	{
		OpgOptions opgOptions = session.getOpgOptions();
		chart = new ChartDrawInfo(0,0);
		chartMargins = new ChartMargins(chart, ops);
		Individual root = ops.getRoot();
		
		//set up AncesTree
		ancesTree = null;
		//add root ancesTree
		if(ops.isIncludeSpouses()) {
			if(root.isMarried())
				if(root.gender == Gender.MALE)
					ancesTree = new AncesTree(root,root.fams.get(0).wife);
				else
					ancesTree = new AncesTree(root.fams.get(0).husband,root);
		}
		else
			ancesTree = new AncesTree(root); //create main tree
		
		descTree = new DescTree(root);
		chartMargins.updateWhiteSpace();
		
		tree = new Tree(ancesTree,null);
		opgOptions.setMaxAncesSlider(ancesTree.getGenerationCount()-1, isPrimaryMaker);
		
		if(ops.getAncesGens() > opgOptions.getMaxAncesSlider())
			ops.setAncesGens(opgOptions.getMaxAncesSlider(), session);
	}
	
	/**
	 * Respaces chart. Called when chart changes in some way.
	 */
	protected void modifyChart(OpgSession session)
	{	
		TreeFormat tf = FormatSelector.instance().select(ops.getAncesGens());
		ancesTree.setFormat(tf);
		descTree.setFormat(FormatSelector.instance().createTreeFormat(1010));
		//Run the spacing algorithm
		//here
		
		AncesSpacer spacer = new AncesSpacer();
		spacer.space(ancesTree,ops.getAncesGens());
		//spacer.expandOffsets(ancesTree, ops.paperHeight()-chartMargins.getWhiteSpace());
		//System.out.println("modify");
		DescBBSpacer dspacer = new DescBBSpacer();
		dspacer.space(descTree,ops.getDescGens());
		
		//calculate needed size for generation labels
		chartMargins.findLabelFontSize();
		
		chartMargins.updateWhiteSpace();
		
		if(ancesTree.getGenerationCount()-1 < ops.getAncesGens())
			ops.setAncesGens(ancesTree.getGenerationCount()-1, session);
		
	}
	
//this means draw chart
	/**
	 * Redraws chart
	 */
	protected void generateChart(OpgSession session)
	{
		//System.out.println("draw");
		BoxFormat.setFont(session.getOpgOptions().getFont());
		chart = new ChartDrawInfo((int)ops.paperWidth(),(int)ops.paperHeight());
		//calculate needed size for generation labels
		chartMargins.setChart(chart);
		chartMargins.findLabelFontSize();
		
		double paperHeight = ops.paperHeight();
		double paperWidth = ops.paperWidth();
		paperHeight -= chartMargins.getWhiteSpace();
		paperWidth -= chartMargins.totalMargins();
		
		tree.drawTree(chartMargins, paperWidth, paperHeight);
		
		//draw titles on chart
		if (ops.drawTitles)
			chartMargins.drawTitles(ancesTree, descTree);
		//draw Logo on chart - branding
		chartMargins.drawLogo();

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

	@Override
	public ArrayList<ImageFile> getImages() {
		return null;
	}

	@Override
	public void setIsPrimaryMaker(boolean set) {
		isPrimaryMaker = set;
	}

	


}


