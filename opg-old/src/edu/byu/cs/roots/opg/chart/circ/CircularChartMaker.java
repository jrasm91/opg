package edu.byu.cs.roots.opg.chart.circ;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import edu.byu.cs.roots.opg.chart.ChartDrawInfo;
import edu.byu.cs.roots.opg.chart.ChartMaker;
import edu.byu.cs.roots.opg.chart.ChartOptions;
import edu.byu.cs.roots.opg.chart.ShapeInfo;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdFilledCircle;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdMoveTo;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdSetFont;
import edu.byu.cs.roots.opg.chart.preset.templates.StylingBoxScheme;
import edu.byu.cs.roots.opg.gui.OnePageMainGui;
import edu.byu.cs.roots.opg.model.Gender;
import edu.byu.cs.roots.opg.model.ImageFile;
import edu.byu.cs.roots.opg.model.Individual;
import edu.byu.cs.roots.opg.model.OpgOptions;
import edu.byu.cs.roots.opg.model.OpgSession;
import edu.byu.cs.roots.opg.model.PaperWidth;
import edu.byu.cs.roots.opg.util.DataFitter;

public class CircularChartMaker implements ChartMaker {

	private static final long serialVersionUID = 1L;

	public static Logger log = Logger.getLogger(CircularChartMaker.class);
	
	private float boxWidth = 90, generationWidth = 130;
	private int ancesgens = 20, descgens = 7;
	private float spaceper = .9f;
	
	private int ancestorDescendantSeperation;
	
	//The radius of the root circle
	private float rootRadius = 50;
	
	private int marginw = 72;
	private int marginl = 72;
	private ChartDrawInfo cache = null;
	private CircularOptions options =  null;
	
	private float root_angle = 45;
	private float spouse_root_angle = 15;
	
	private float linewidth = 2;
	
	private boolean isPrimaryMaker = false;
	
	protected ArrayList<ImageFile> images = new ArrayList<ImageFile>();
	
	
	
	public ChartOptions convertToSpecificOptions(ChartOptions options) {
		return new CircularOptions(options);
	}
	
	public void convertOpgOptions(OpgOptions options){
		
	}

	public ChartDrawInfo getChart(ChartOptions ops, OpgSession session) {
		
		if(!session.isChanged() && cache != null) return cache;
		
		OpgOptions opgOptions = session.getOpgOptions();
		options = (CircularOptions) ops;
		ChartDrawInfo chart;
		
		boxWidth = options.boxWidth;
		generationWidth = options.boxSpacing+boxWidth;
		rootRadius = options.rootRadius;
		
		//check ancestor generations
		int maxAncesGens = findMaxDepth(options.getRoot());
		for(int i = 1; i < maxAncesGens + 1 ; i++){
			//circumference of half circle divided by number of boxes at that generation.
			if(opgOptions.getMinFontSize() > (((i * generationWidth) + rootRadius)* Math.PI) / (Math.pow(2,i))){
				maxAncesGens = i-1;
				break;
			}
		}
		
		
		int maxDescGens = findMaxDescGens(options.getRoot());
		
//		double upperRadiusMaximum = (maxAncesGens * generationWidth) + rootRadius;
//		double lowerRadiusMaximum = (maxDescGens * generationWidth) + rootRadius;
//		double horizontalRadiusMaximum = (Math.max(maxAncesGens, maxDescGens) * generationWidth) + rootRadius;
//		double paperWidth = (options.isLandscape() ? options.getPaperLength() : options.getPaperWidth().width);
		
		/*
		while (upperRadiusMaximum + rootRadius > options.getPaperLength() || 
				(horizontalRadiusMaximum * 2) > options.getPaperWidth().width){
			maxAncesGens--;
		
			upperRadiusMaximum = maxAncesGens * (generationWidth + rootRadius);
			horizontalRadiusMaximum = Math.max(maxAncesGens, maxDescGens) * (generationWidth + rootRadius);
		}*/
		opgOptions.setMaxAncesSlider(maxAncesGens, isPrimaryMaker);
		//options.setMaxAncesGens(20);
		if(options.getAncesGens() > opgOptions.getMaxAncesSlider()) 
			options.setAncesGens(opgOptions.getMaxAncesSlider(), session);
		opgOptions.setMaxDescSlider(maxDescGens, isPrimaryMaker);
		
		ancesgens = options.getAncesGens();
		descgens = options.getDescGens();
		
		
		if(descgens > 0) ancestorDescendantSeperation = 10;
		else ancestorDescendantSeperation = 0;
		
		
		
		
		//soon I will change this to fit to the text size
		int ancesheight = (int)(generationWidth * ancesgens + rootRadius);
		int descheight = (int)(generationWidth * descgens + rootRadius);
		double width, height;
		
//		float spread = 1.5f;
		int marginy;
//		int marginx;
		if(options.isLandscape()){
			PaperWidth temp = PaperWidth.findClosestFit(ancesheight+descheight + 2*marginw);
			
			if(options.getPaperWidth() != null){
				if(temp.width < options.getPaperWidth().width) temp = options.getPaperWidth();
			}
			options.setPaperWidth(temp);

			height = (int)( options.getPaperWidth().width);
			width = 2*(Math.max(ancesheight,descheight) + marginl);
			options.setMinPaperLength(72.0);
			width = Math.max(options.getPaperLength(), width);
			options.setPaperLength(width);
			chart = new ChartDrawInfo((int) width,(int) height);
//			marginx = marginl;
			marginy = marginw;
		}
		else{
			//figure out the proper width as the maximum of the the ancestor and descendant heights
			PaperWidth temp = PaperWidth.findClosestFit(2*(Math.max(ancesheight,descheight) + marginw));

//			System.out.println("size = " + temp.width);
			if(options.getPaperWidth() != null){
				if(temp.width < options.getPaperWidth().width) temp = options.getPaperWidth();
			}
			options.setPaperWidth(temp);
			
			width = (int)( options.getPaperWidth().width);
			height = ancesheight+descheight + 2*marginl;
			options.setMinPaperLength(72.0);
			height = Math.max(options.getPaperLength(), height);
			options.setPaperLength(height);
			
			
			chart = new ChartDrawInfo((int) width, (int) height);
//			marginx = marginw;
			marginy = marginl;
		}
		chart.addDrawCommand(new CmdSetColor(Color.black));
		chart.addDrawCommand(new DrawCmdSetFont(getFont(session, options, opgOptions.getMinFontSize()), Color.black));
		

		chart.addDrawCommand(new DrawCmdMoveTo(width/2, (ancesheight+ancestorDescendantSeperation) + marginy));
		
		if(options.isIncludeSpouses()){
			Individual spouse = (options.getRoot().gender == Gender.MALE) ? options.getRoot().fams.get(0).wife : options.getRoot().fams.get(0).husband;
			Individual left, right;
			if(spouse.gender == Gender.MALE){
				right = spouse;
				left = options.getRoot();
			}
			else{
				left = spouse;
				right = options.getRoot();
			}
//			System.out.println("drawing semi circles");
			chart.addDrawCommand(new DrawSemiCircle(rootRadius, linewidth, Color.black, options.getAncesScheme().getColor(left.id), 90, 180));
			chart.addDrawCommand(new DrawSemiCircle(rootRadius, linewidth, Color.black, options.getAncesScheme().getColor(right.id), 270, 180));
			chart.addDrawCommand(new DrawCmdMoveTo(width/2, ancesheight + marginy));
			
			drawTree(session, chart, right, 0, 0, 1);
			drawTree(session, chart, left, 0, 1, 1);
			
			
			chart.addDrawCommand(new DrawCmdSetFont(getFont(session, options, opgOptions.getMinFontSize()), Color.black));
//			float rootFontSize = options.getMinFontSize();
			
			ArrayList<String> fit = DataFitter.fit(left, (float) (2*Math.sin(Math.toRadians(spouse_root_angle)) * rootRadius),  (float) (Math.cos(Math.toRadians(spouse_root_angle)) * rootRadius) - 2*linewidth,getFont(session, options, opgOptions.getMinFontSize()),"");
			String[] textlines = new String[fit.size()];
			DrawTextBox text = new DrawTextBox(textlines);
			fit.toArray(textlines);
			chart.addDrawCommand(new DrawCmdMoveTo(width/2 - (float) (Math.cos(Math.toRadians(spouse_root_angle)) * rootRadius) + linewidth, 
								ancesheight + linewidth + marginy - (float) (Math.sin(Math.toRadians(spouse_root_angle)) * rootRadius)));
			chart.addDrawCommand(text);
			
			fit = DataFitter.fit(right, (float) (2*Math.sin(Math.toRadians(spouse_root_angle)) * rootRadius),  (float) (Math.cos(Math.toRadians(spouse_root_angle)) * rootRadius) - 2*linewidth,getFont(session, options, opgOptions.getMinFontSize()),"");
			textlines = new String[fit.size()];
			text = new DrawTextBox(textlines);
			fit.toArray(textlines);
			chart.addDrawCommand(new DrawCmdMoveTo(width/2  + linewidth, 
									ancesheight + linewidth + marginy - (float) (Math.sin(Math.toRadians(spouse_root_angle)) * rootRadius)));
			chart.addDrawCommand(text);
			
//			chart.addDrawCommand(new DrawCmdSetFont(getFont(options, rootFontSize), Color.black));
//			chart.addDrawCommand(new DrawCmdMoveTo(width/2 -  rootsize+ 5,(ancesheight + rootFontSize) + marginy));
//			chart.addDrawCommand(new DrawText(left.givenName));
//			chart.addDrawCommand(new DrawCmdMoveTo(width/2 -  rootsize+5,(ancesheight+2*rootFontSize) + marginy));
//			chart.addDrawCommand(new DrawText(left.surname));
//			
//			chart.addDrawCommand(new DrawCmdMoveTo(width/2+5,(ancesheight + rootFontSize) + marginy));
//			chart.addDrawCommand(new DrawText(right.givenName));
//			chart.addDrawCommand(new DrawCmdMoveTo(width/2+5,(ancesheight+2*rootFontSize) + marginy));
//			chart.addDrawCommand(new DrawText(right.surname));	
			
			
			
		}else{
//			System.out.println("drawing circle!!");
			chart.addDrawCommand(new DrawCmdFilledCircle(rootRadius, 2, Color.black, options.getAncesScheme().getColor(options.getRoot().id)));
			chart.addDrawCommand(new DrawCmdMoveTo(width/2, ancesheight + marginy));
			
			drawTree(session, chart, options.getRoot(), 0, 0, 0);
			
//			float rootFontSize = options.getMinFontSize();
			
			ArrayList<String> fit = DataFitter.fit(options.getRoot(), (float) (2*Math.sin(Math.toRadians(root_angle)) * rootRadius),  (float) (2*Math.cos(Math.toRadians(root_angle)) * rootRadius) - 2*linewidth,getFont(session, options, opgOptions.getMinFontSize()),"");
			String[] textlines = new String[fit.size()];
			DrawTextBox text = new DrawTextBox(textlines);
			fit.toArray(textlines);
			chart.addDrawCommand(new DrawCmdMoveTo(width/2 - (float) (Math.cos(Math.toRadians(root_angle)) * rootRadius) + 2*linewidth, 
								ancesheight + 2*linewidth + marginy - (float) (Math.sin(Math.toRadians(root_angle)) * rootRadius)));
			chart.addDrawCommand(text);
			
//			chart.addDrawCommand(new DrawCmdSetFont(getFont(options, rootFontSize), Color.black));
//			chart.addDrawCommand(new DrawCmdMoveTo(width/2 -  rootsize,(ancesheight + rootFontSize) + marginy));
//			chart.addDrawCommand(new DrawText(options.getRoot().givenName));
//			chart.addDrawCommand(new DrawCmdMoveTo(width/2 -  rootsize,(ancesheight+2*rootFontSize) + marginy));
//			chart.addDrawCommand(new DrawText(options.getRoot().surname));
//			chart.addDrawCommand(new DrawCmdMoveTo(width/2 -  rootsize,(ancesheight+3*rootFontSize) + marginy));
//			chart.addDrawCommand(new DrawText(((options.getRoot().birth != null && options.getRoot().birth.date != null)? options.getRoot().birth.date : "____") + " - " + ((options.getRoot().death != null && options.getRoot().death.date != null) ? options.getRoot().death.date : "____")));
		}
		chart.addDrawCommand(new DrawCmdMoveTo(width/2, ancesheight + marginy + 2*ancestorDescendantSeperation));
		
		
		
		drawDescTree(session, chart, options.getRoot(), 0, 180, 180);
		
		
		
		
		
//		chart.addDrawCommand(new DrawCmdSetFont(getFont(options, options.getMaxFontSize()), Color.black));
		
//		for(int i = 1;i <= options.getMaxAncesGens();i++){
//			int tmp = (int) Math.pow(2, i);
//			for(int j = 0;j<tmp;j++){
//				chart.addDrawCommand(new FilledArcSeg(100 * i, 80, (j*180f/tmp),(180f*.9f/tmp), Color.gray));
//			}
//		}
		
		
		int logoOffset = 60;
		chart.addDrawCommand(new DrawCmdSetFont(getFont(session, options, opgOptions.getMinFontSize()), Color.black));
		chart.addDrawCommand(new DrawCmdMoveTo(
				chart.getXExtent() - 72 * 5, chart.getYExtent() - logoOffset));
		chart.addDrawCommand(new DrawText(
			"One Page Genealogy Project"));
		chart.addDrawCommand(new DrawCmdMoveTo(
				chart.getXExtent() - 72 * 5, chart.getYExtent() - logoOffset + opgOptions.getMinFontSize()));
		chart.addDrawCommand(new DrawText(
			"Digital Roots Lab � http://roots.cs.byu.edu/pedigree/"));
		
		session.resetChanged();
		
		cache = chart;
//		if(ops.getBoxes().size() == 0){
//			ops.getBoxes().add(new MediaBox((int) (1000*Math.random()), (int) (1000*Math.random()), 100, 100));
//			ops.getBoxes().add(new MediaBox((int) (1000*Math.random()), (int) (1000*Math.random()), 100, 100));
//			ops.getBoxes().add(new MediaBox((int) (1000*Math.random()), (int) (1000*Math.random()), 100, 100));
//			ops.getBoxes().add(new MediaBox((int) (1000*Math.random()), (int) (1000*Math.random()), 100, 100));
//		}
//		chart.setBoxes(ops.getBoxes());
		
		return chart;
	}

	private int findMaxDepth(Individual indi){
//		System.out.println("Current individual is " + indi.givenName +  " " + indi.surname);
//		if(indi.mother != null) System.out.println("Mother is " + indi.mother.givenName +  " " + indi.mother.surname);
//		else System.out.println("Mother is null");
//		if(indi.father != null) System.out.println("Father is " + indi.father.givenName +  " " + indi.father.surname);
//		else System.out.println("Father is null");
		if(indi.father == null && indi.mother == null) return 0;
		if(indi.father == null) return 1 + findMaxDepth(indi.mother);
		if(indi.mother == null) return 1 + findMaxDepth(indi.father);
		return 1 + Math.max(findMaxDepth(indi.father), findMaxDepth(indi.mother));
	}
	
	private int findMaxDescGens(Individual indi){
		int gens = 0;
		if(indi.fams.size() < 1) 
			return 0;
		for(Individual ind : indi.fams.get(0).children){
			gens = Math.max(gens, findMaxDescGens(ind) + 1);
		}
		return gens;
	}
	
	
	
	
	
// prototype for one way of implementing width adjustment on a decendancy tree	
//	private void createTreeDescriptor(Individual indi, ArrayList<Integer> des, int level){
//		if(des.size() < level+1){
//			des.add(1);
//		}
//		else{
//			des.set(level, des.get(level) + 1);
//		}
//		if(indi.father != null) createTreeDescriptor(indi.father,des, level+1);
//		if(indi.mother != null) createTreeDescriptor(indi.mother,des, level+1);
//		
//	}
	
	private void drawTree(OpgSession session, ChartDrawInfo chart, Individual indi, int level, int pos, int leveloffset ){
		if (level != 0) {
			if(indi == null){
				int tmp = (int) Math.pow(2, level + leveloffset);
				float size = (180f * spaceper / tmp);
				float space = 180*(1-spaceper)/ (tmp-1);
				FilledArcSeg com = new FilledArcSeg(rootRadius + level * generationWidth-.5f*boxWidth, boxWidth,
						(pos * (size + space)), size, Color.white);
				chart.addDrawCommand(com);
			}
			else{
				int tmp = (int) Math.pow(2, level + leveloffset);
				float size = (180f * spaceper / tmp);
				float space = 180*(1-spaceper)/ (tmp-1);
				float rad = rootRadius + level * generationWidth-.5f*boxWidth;
	//			System.out.println("Level " + level + " space " + space + " size " + (180f * spaceper / tmp));
				FilledArcSeg com = new FilledArcSeg(rad, boxWidth,
						(pos * (size + space)), size, options.getAncesScheme().getColor(indi.id));
	//			Color.getHSBColor((float) Math.random(), 1, 1)
				
				ArrayList<String> fit = DataFitter.fit(indi, (float) (Math.toRadians(size)* rad), boxWidth,getFont(session, options, session.getOpgOptions().getMinFontSize()),"");
				com.textlines = new String[fit.size()];
				fit.toArray(com.textlines);

				com.fontsize = session.getOpgOptions().getMinFontSize();
				com.maxfontsize = session.getOpgOptions().getMaxFontSize();
				com.minfontsize = session.getOpgOptions().getMinFontSize();
				chart.addDrawCommand(com);
			}
		}		
		if(level >= ancesgens) return;
		if(indi != null){
			if(indi.mother != null || options.includeEmpty) drawTree(session, chart, indi.mother, level + 1, pos*2+1, leveloffset );
			if(indi.father != null || options.includeEmpty) drawTree(session, chart, indi.father, level + 1, pos*2, leveloffset );
		}else{
			if(options.includeEmpty) drawTree(session, chart, null, level + 1, pos*2+1, leveloffset );
			if(options.includeEmpty) drawTree(session, chart, null, level + 1, pos*2, leveloffset );
		}

	}
	
	
	private void drawDescTree(OpgSession session, ChartDrawInfo chart, Individual indi, int level, float startang, float sweep  ){
		if(level != 0){
//			int tmp = (int) Math.pow(2, level + leveloffset);
//			float size = (180f * spaceper / tmp);
//			float space = 180*(1-spaceper)/ (tmp-1);
			float rad = rootRadius + level * generationWidth-.5f*boxWidth;
			FilledArcSeg com = new FilledArcSeg(rad, boxWidth,
					startang, sweep, options.getDescScheme().getColor(indi.id));
			
			ArrayList<String> fit = DataFitter.fit(indi, (float) (Math.toRadians(sweep)*rad), boxWidth,getFont(session, options, session.getOpgOptions().getMinFontSize()),"");
			com.textlines = new String[fit.size()];
			fit.toArray(com.textlines);
//			System.out.println(com.textlines);
			com.fontsize = session.getOpgOptions().getMinFontSize();
			com.maxfontsize = session.getOpgOptions().getMaxFontSize();
			com.minfontsize = session.getOpgOptions().getMinFontSize();
			chart.addDrawCommand(com);
		}
		if(level >= descgens ) return;
		if(indi.fams.size() < 1) return;
		
		float childsweep = sweep/(indi.fams.get(0).children.size());
		
		
		float childnum = 0;
		float totalchildren = indi.fams.get(0).children.size();
		for(Individual ind : indi.fams.get(0).children){
			drawDescTree(session, chart, ind, level+1, startang + (childnum/totalchildren) * sweep, childsweep );
			childnum++;
		}
	}
	
	
	
	private Font getFont(OpgSession session, ChartOptions options, float size){
		int style = 0;
		if(session.getOpgOptions().isBold()) style|=Font.BOLD;
		if(session.getOpgOptions().isItalic()) style|=Font.ITALIC;
		return session.getOpgOptions().getFont().getFont(style, size);
	}
	
	public JPanel getSpecificOptionsPanel(ChartOptions options, OnePageMainGui parent) {
		return new CircularOptionsPanel(options, parent);
	}

	public LinkedList<ShapeInfo> getChartShapes() {
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