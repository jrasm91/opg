package edu.byu.cs.roots.opg.chart;

import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.io.Serializable;
import java.util.Observable;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import edu.byu.cs.roots.opg.color.ColorScheme;
import edu.byu.cs.roots.opg.model.Individual;
import edu.byu.cs.roots.opg.model.OpgSession;
import edu.byu.cs.roots.opg.model.PaperWidth;

	/**
	 * This Class represents all the generic options nessecary to create a chart.
	 * The chart maker will take this class as a parameter and return a 
	 * ChartDrawInfo. Size options may be changed to accommodate
	 * settings such as root, descendant generations, and ancestor generations.
	 * 
	 * 1 Point = 1/72 of an inch
	 * 
	 * @author Travix
	 *
	 */
	public class ChartOptions extends Observable implements Serializable
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1000L;

		//TODO remove all the "this has been updated" variables in favor of using the changed listener
		//especially do this for updating the scheme list
		
		public static Logger log = Logger.getLogger(ChartOptions.class);
		
		public static boolean firstLoad = false; //true on first load of gedcom.
		//Used to set initial chart size.
		
		transient CopyOnWriteArraySet<ChangeListener> listenerset;
		
		protected transient PageFormat format;
		
		protected boolean TreeTrimed = false;
		
		/**
		 * Paper width object which holds the width of the different rolls in points
		 */
		protected PaperWidth paperWidth;
		
		/**
		 * Current length of the paper (in points)
		 */
		protected double paperLength;
		
		/**
		 * Minimum length required of the current chart (in points)
		 */
		protected double minPaperLength;

		
		/**
		 * Is the paper in landscape mode or portrait,
		 * true = in landscape mode
		 * false = in portrait
		 */
		protected boolean landscape;
		
		
		/**
		 * Currently selected number of ancesteral generations
		 */
		protected int ancesGens;
		/**
		 * Currently selected number of descendant generations
		 */
		protected int descGens;
		
		/**
		 * Should we include the spouse(s) and associated genealogy in the chart
		 */
		protected boolean includeSpouses;
		
		/**
		 * Has an ascendency tree been turned on or off
		 */
		protected boolean drawTreeHasChanged;
		
		/**
		 * Specifies the Root person
		 */
		protected Individual root;
		
		/**
		 * Has a stylebox been changed
		 */
		protected boolean styleBoxChanged;
		
		/**
		 * Does this chart type allow a choice between landscape or portrait
		 */
		protected boolean paperOrientationChoice;
		
		/**
		 * Does this chart type allow you to choose the paper width
		 */
		protected boolean paperWidthChoice;
		
		/**
		 * Does this chart type allow you to choose the paper height
		 */
		protected boolean paperHeightChoice;
		
		/**
		 * Does this chart type allow a choice between spouses included or not
		 */
		protected boolean spouseIncludedChoice;
		
		
		/**
		 * Weather the margins have been changed
		 */
		protected boolean marginsChanged;
		
//This is the old way
//		protected IndividualRecord root;
		
		
		

	
		
		
		
		
		protected ColorScheme ancesScheme;
		
		protected ColorScheme descScheme;
		


		//constructor
		public ChartOptions()
		{
			this.format = new PageFormat();
			this.paperLength = 0.0;
			this.paperWidth = PaperWidth.wd1;
			this.minPaperLength = 0.0;
			this.landscape = false;
			this.ancesGens = 0;
			this.descGens = 0;
			this.includeSpouses = false;//true; //Removed By: Spencer Hoffa 2/19/2013
			this.drawTreeHasChanged = false;
			this.styleBoxChanged = false;
			this.spouseIncludedChoice = true;
			this.paperOrientationChoice = true;
			this.paperWidthChoice = true;
			this.paperHeightChoice = true;


			this.ancesScheme = null;
			this.descScheme = null;

			listenerset = new CopyOnWriteArraySet<ChangeListener>();
			
			
		}

		public ChartOptions(ChartOptions options) {
			super();
			this.format = options.format == null ? PrinterJob.getPrinterJob().defaultPage() : options.format;
			this.paperWidth = options.paperWidth;
			this.paperLength = options.paperLength;
			this.minPaperLength = options.minPaperLength;
			this.landscape = options.landscape;
			this.ancesGens = options.ancesGens;
			this.descGens = options.descGens;
			this.includeSpouses = options.includeSpouses;
			this.drawTreeHasChanged = false;
			this.styleBoxChanged = false;
			this.spouseIncludedChoice = true;
			this.paperOrientationChoice = true;
			this.paperWidthChoice = true;
			this.paperHeightChoice = true;
			this.root = options.root;
//			this.parser = options.parser;
			this.ancesScheme = options.ancesScheme;
			this.descScheme = options.descScheme;
			this.listenerset = options.listenerset;
			
		}

		protected void changed(int code){
//			System.out.println("changed!! " + code);
			firePropertyChanged();
		}
		
		public void changedTree()
		{
			TreeTrimed = true;	
			changed(1024);
		}
		
		public boolean wasTreeTrimed()
		{
			return TreeTrimed;
		}
		
		public void resetTreeTrimming() {
			TreeTrimed = false;
		}
		
		/**
		 * Function to allow change listener support
		 * @param arg0
		 */
		public void addChangeListener(ChangeListener arg0) {
			listenerset.add(arg0);
		}
		/**
		 * Function to allow change listener support
		 * @param arg0
		 */
		public void removeChangeListener(ChangeListener arg0) {
			listenerset.remove(arg0);
		}
		/**
		 * Function to allow change listener support
		 * @param arg0
		 */
		private void firePropertyChanged(){
			ChangeEvent e = new ChangeEvent(this);
			if(listenerset == null) 
				listenerset = new CopyOnWriteArraySet<ChangeListener>();
			for(ChangeListener listener : listenerset){
				listener.stateChanged(e);
			}
		}


		
		
		
				
		/**
		 * Returns the current number of Ancestor generations 
		 * that can be included in the chart
		 * 
		 *  @author derek
		 *  @return The number of generations requested. 
		 */
		public int getAncesGens(){return ancesGens > 0 ? ancesGens : 0;}

		/**
		 * Returns the current number of Descendent generations that can be included in the chart
		 * @author derek
		 * @return An integer containing the number of descedents requested by the user. 
		 */
		public int getDescGens(){return descGens;}
		
		/**
		 * Sets the current number of Ancestor generations this chart can display
		 */
		public void setAncesGens(int gens, OpgSession session) throws IllegalArgumentException
		{
			int maxAncesGens = session.getOpgOptions().getMaxAncesSlider();
			changed(1);
			if (gens > maxAncesGens)
				throw new IllegalArgumentException("Ancestor generations " + gens + " exceeded maximum (" + maxAncesGens + ").");
			else
				ancesGens = gens;
			if(gens < 0)
				gens = 0;
		}
		
		/**
		 * Sets the current number of descendent generations this chart can display
		 */
		public void setDescGens(int gens, OpgSession session) throws IllegalArgumentException
		{
			int maxDescGens = session.getOpgOptions().getMaxDescSlider();
			changed(2);
			if (gens > maxDescGens)
				throw new IllegalArgumentException("Descendent generations exceeded maximum.");
			else
				descGens = gens;
		}

		public void setLandscape(boolean landscape) {
			changed(6);
			this.landscape = landscape;
		}

		public void setPaperWidth(PaperWidth paperWidth) {
			changed(7);
			this.paperWidth = paperWidth;
		}


		public double getPaperLength() {
			return paperLength;
		}


		public void setPaperLength(double paperLength) {
			changed(8);
			this.paperLength = paperLength;
		}


		public PaperWidth getPaperWidth() {
			return paperWidth;
		}
		
		
		public double paperHeight() {
			return (landscape)?  paperWidth.width : paperLength;
		}
		
		public double paperWidth() {
			return (!landscape)? paperWidth.width : paperLength;
		}
		
		public PaperWidth yLength() {
			return paperWidth;
		}

		public double getMinPaperLength() {
			return minPaperLength;
		}


		public void setMinPaperLength(double minPaperLength) {
			changed(10);
			this.minPaperLength = minPaperLength;
		}

		public boolean isIncludeSpouses() {
			return includeSpouses;
		}

		public void setIncludeSpouses(boolean includeSpouses) {
			changed(11);
			this.includeSpouses = includeSpouses;
		}
		
		public boolean getDrawTreeHasChanged(){
			return drawTreeHasChanged;
		}
		
		public void setDrawTreeHasChanged(boolean drawChanged){
			//changed(22);
			this.drawTreeHasChanged = drawChanged;
		}

		public boolean getStyleBoxChanged(){
			return styleBoxChanged;
		}
		
		public void setStyleBoxChanged(boolean boxChanged){
			changed(23);
			this.styleBoxChanged = boxChanged;
		}
		public Individual getRoot() {
			return root;
		}

		public void setRoot(Individual root, OpgSession session) {
			changed(12);
			if(this.root!=null) 
				session.resetIndiFlags();
			this.root = root;
		}

		public boolean isLandscape() {
			return landscape;
		}

		

		

		public ColorScheme getAncesScheme() {
			return ancesScheme;
		}

		public void setAncesScheme(ColorScheme colorscheme) {
			changed(18);
			this.ancesScheme = colorscheme;
		}

		/**
		 * @return the descScheme
		 */
		public ColorScheme getDescScheme() {
			return descScheme;
		}

		/**
		 * @param descScheme the descScheme to set
		 */
		public void setDescScheme(ColorScheme descScheme) {
			changed(19);
			this.descScheme = descScheme;
		}

		/**
		 * @return the format
		 */
		public PageFormat getFormat() {
			return format;
		}

		/**
		 * @param format the format to set
		 */
		public void setFormat(PageFormat format) {
			changed(21);
			this.format = format;
		}
		
		
		public void setPaperOrientationChoice(boolean set){
			changed(25);
			paperOrientationChoice = set;
		}
		
		public boolean getPaperOrientationChoice(){
			return paperOrientationChoice;
		}
		
		public void setPaperWidthChoice(boolean set){
			paperWidthChoice = set;
		}
		
		public boolean getPaperWidthChoice(){
			return paperWidthChoice;
		}
		
		public void setPaperHeightChoice(boolean set){
			paperHeightChoice = set;
		}
		
		public boolean getPaperHeightChoice(){
			return paperHeightChoice;
		}
		
		public void setMarginsChanged(boolean set){
			marginsChanged = set;
		}
		
		public boolean getMarginsChanged(){
			return marginsChanged;
		}

		public boolean getSpouseIncludedChoice(){
			return spouseIncludedChoice;
		}
		
		public void setSpouseIncludedChoice(boolean set){
			spouseIncludedChoice = set;
		}
		
	}
	
	
	