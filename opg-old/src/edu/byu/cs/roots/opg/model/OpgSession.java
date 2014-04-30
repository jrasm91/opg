package edu.byu.cs.roots.opg.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import edu.byu.cs.roots.opg.chart.ChartDrawInfo;
import edu.byu.cs.roots.opg.chart.ChartMaker;
import edu.byu.cs.roots.opg.chart.ChartOptions;
import edu.byu.cs.roots.opg.chart.ChartType;
import edu.byu.cs.roots.opg.chart.multisheet.MultisheetChartMaker;
import edu.byu.cs.roots.opg.color.AncesteralColorSchemes;
import edu.byu.cs.roots.opg.color.ColorScheme;
import edu.byu.cs.roots.opg.color.DescendantColorSchemes;
import edu.byu.cs.roots.opg.conf.ConfigData;
import edu.byu.cs.roots.opg.exc.FailedToLoadException;
import edu.byu.cs.roots.opg.exc.FailedToSaveException;
import edu.byu.cs.roots.opg.io.GedcomParser;
import edu.byu.cs.roots.opg.io.GedcomRecord;
import edu.byu.cs.roots.opg.io.InvalidSyntaxException;
import edu.byu.cs.roots.opg.io.PAF5Parser;
import edu.byu.cs.roots.opg.io.Project;
import edu.byu.cs.roots.opg.nfs.AccessFamilySearch;
import edu.byu.cs.roots.opg.nfs.NFSDownloadThread;
import edu.byu.cs.roots.opg.nfs.UsernamePasswordException;


public class OpgSession {

	public static Logger log = Logger.getLogger(OpgSession.class);
	public SessionState state;
	public String gedfile;
	public String projfile;
	
	private ArrayList<OpgPage> pages;
	private OpgOptions opgOptions;
	//private ArrayList<ChartOptions> options;
	public ArrayList<Individual> names_dataProvider;
	public OpgCursor cursor;
//	public boolean showRuler;
	public ConfigData config;
	public boolean changed;
	private String tempFile;
	public ArrayList<ColorScheme> descSchemes;
	public ArrayList<ColorScheme> ancesSchemes;
	
	public GedcomRecord record;
	private AccessFamilySearch access;
//	private Parser parser;
	
	//private ArrayList<ChartMaker> maker;
	private int pageNumber;
	/** Storage for all the individuals in the current box tree structure. 
	 * Used to clear flags without stack overflow.*/
	protected ArrayList<Individual> indisInTree;
	
	/** Storage for all the Families in the current box tree structure. 
	 * Used to clear flags without stack overflow.*/
	protected ArrayList<Family> familiesInTree;
	
	public NFSDownloadThread thread;
	
	public String getTempFile()
	{
		return tempFile;
	}
	
	public void setTempFile(String tempFileName)
	{
		tempFile = tempFileName;
	}
	
	private ChangeListener listener = new ChangeListener(){
		public void stateChanged(ChangeEvent e) {
			setChanged(true);
		}
	};

	/**
	 * A collection of raw chart drawing commands, this represents a chart file
	 * which has been loaded. No configuration can be done, this is for viewing
	 * purposes only
	 */
	public ChartDrawInfo hollowchart;
	
	/**
	 * Chart used for displaying the preview of the chart to the user when adding
	 * additional charts to the order.
	 */
	ChartDrawInfo previewChart;
	
	
	public OpgSession(){
		state = SessionState.newsession;
		gedfile = null;
		projfile = null;
		
//		parser = null;
		record = null;
		access = new AccessFamilySearch();
		pageNumber = 0;
		
		pages = new ArrayList<OpgPage>();
		pages.add(new OpgPage());
		
		opgOptions = new OpgOptions();
		
		ChartOptions options = currentPage().getOptions(0);
		
		options.addChangeListener(listener);
		opgOptions.addChangeListener(listener);
		names_dataProvider = new ArrayList<Individual>();
		cursor = OpgCursor.MOVE;
		descSchemes = new ArrayList<ColorScheme>();
		ancesSchemes = new ArrayList<ColorScheme>();
		for(AncesteralColorSchemes scheme:AncesteralColorSchemes.values()){
			ancesSchemes.add(scheme.getScheme());
		}
		for(DescendantColorSchemes scheme:DescendantColorSchemes.values()){
			descSchemes.add(scheme.getScheme());
		}
		options.setAncesScheme(ancesSchemes.get(0));
		options.setDescScheme(descSchemes.get(0));
		indisInTree = new ArrayList<Individual>();
		familiesInTree = new ArrayList<Family>();
		
		thread = new NFSDownloadThread(this);
	}
	
	public void clear(){
		state = SessionState.newsession;
		gedfile = null;
		projfile = null;
		pageNumber = 0;
//		parser = null;
		record = null;
		
		opgOptions = new OpgOptions();
		
		hollowchart = null;
		
		pages = new ArrayList<OpgPage>();
		pages.add(new OpgPage());
		ChartOptions options = currentPage().getOptions(0);
//		changeType(ChartType.values()[0]);
		options.addChangeListener(listener);
		opgOptions.addChangeListener(listener);
		names_dataProvider = new ArrayList<Individual>();
		descSchemes = new ArrayList<ColorScheme>();
		ancesSchemes = new ArrayList<ColorScheme>();
		for(AncesteralColorSchemes scheme:AncesteralColorSchemes.values()){
			ancesSchemes.add(scheme.getScheme());
		}
		for(DescendantColorSchemes scheme:DescendantColorSchemes.values()){
			descSchemes.add(scheme.getScheme());
		}
		options.setAncesScheme(ancesSchemes.get(0));
		options.setDescScheme(descSchemes.get(0));
		
		indisInTree = new ArrayList<Individual>();
		familiesInTree = new ArrayList<Family>();
		
		thread = new NFSDownloadThread(this);
	}
	
	public ChartDrawInfo getPreview()
	{
		return previewChart;
	}
	
//	public void download(String username, String password) throws UsernamePasswordException, IOException {
//		clear();
//		ChartOptions.firstLoad = true;
//		changeType(ChartType.values()[0]);
//		
//		access.newUsernameAndPassword(username, password);
//		
//	    int ok = access.getInformation(null);
//		switch(ok) {
//		case(1):
//			throw new UsernamePasswordException();
//		case(2):
//		case(3):
//		case(4):
//			throw new IOException();
//		}
//		if (access.GetMore()) {
//			Object[] opt = {"Yes", "No"};
//			int n = JOptionPane.showOptionDialog(null,
//					"Nine generations have been uploaded,\n" +
//					"but there are still more to download.\n" +
//					"Do you want to continue downloading your genealogy?",
//					"One Page Genealogy",
//					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
//					null, opt, opt[0]);
//			if (n == 0)
//				access.continueAfterNine(null);
//			else if (n == 1)
//				access.doNotContinue();
//		}	
//		record = access.getGedcomRecord();
//		record.linkChildrenToParents();
//		record.linkSpouseToFamily();
//		record.setNFS(true);
//		
//		hollowchart = null;
//		state = SessionState.edit;
//		names_dataProvider = getIndividualList();
//		//does this have a gedfile?
//		gedfile = "newdownload.ged";
//		changed = true;
//		
//		this.getBaseOptions().setRoot(access.GetRoot(), this);
//		changed = false;
//	}
	
	public void update(String username, String password) {
		clear();
		ChartOptions.firstLoad = true;
		changeType(ChartType.values()[0]);
		
		//check the root.  if it is different than this person's person, 
		//then show the user who is the root, and get that information!
		try {
			access.resetUsernameAndPassword(username, password);
			access.update(0, null);
		}
		catch (UsernamePasswordException e) {
			JOptionPane.showMessageDialog(null, 
					e.getMessage(),
					"Error!", JOptionPane.ERROR_MESSAGE);
			log.debug("Error downloading file");
		}
		
		record = access.getGedcomRecord();
		record.linkChildrenToParents();
		record.linkSpouseToFamily();
				
		hollowchart = null;
		state = SessionState.edit;
		names_dataProvider = getIndividualList();
		//does this have a gedfile?
		gedfile = "newdownload.ged";
		setChanged(true);
		
		pages.get(0).getFirstOptions().setRoot(access.GetRoot(), this);
		resetChanged();
		
		//check all the families!
	}
	
	public void loadGedcom(File file) throws IOException, InvalidSyntaxException{
		String filename = file.getCanonicalPath();
		GedcomParser gedparser = new GedcomParser(filename);
		clear();
		ChartOptions.firstLoad = true;
		changeType(ChartType.values()[0]);
		record = gedparser.parseGedcom();
		hollowchart = null;
		state = SessionState.edit;
		names_dataProvider = getIndividualList();
		gedfile = filename;
		setChanged(true);
	}
	

	
	public void loadPAF5(File file) throws IOException{
		String fileName = file.getCanonicalPath();
		clear();
		changeType(ChartType.values()[0]);
		record = PAF5Parser.parsePAF5File(fileName);
		hollowchart = null;
		state = SessionState.edit;
		names_dataProvider = getIndividualList();
		gedfile = fileName;
		setChanged(true);
	}
	
	
	public void open(File file) throws FailedToLoadException, IOException {
			String filename = file.getCanonicalPath();
			log.debug("attempting to open " + filename);
			if(filename.endsWith(".ged") || filename.endsWith(".GED")){
				try {
					loadGedcom(file);
					pages.get(0).getFirstOptions().setRoot(names_dataProvider.get(0), this);
					resetChanged();
				} catch (InvalidSyntaxException e) {
					log.debug("Error loading gedcom",e);
					throw new FailedToLoadException(e.toString());
				}
			} else if(filename.endsWith(".paf") || filename.endsWith(".PAF") ||
					filename.endsWith(".zip") || filename.endsWith(".ZIP")){
				try {
					loadPAF5(file);
					pages.get(0).getFirstOptions().setRoot(names_dataProvider.get(0), this);
					resetChanged();
				} catch (IOException e) {
					log.debug("Error loading paf ",e);
					throw new FailedToLoadException(e.toString());
				}
			}else if(filename.endsWith(".opg") || filename.endsWith(".OPG")){
				Project p = null;
				try{
					p = Project.open(file);
				}
				catch(Exception e){
					log.debug("Error loading" , e);
					throw new FailedToLoadException(e.toString());
				}
				pages = p.pages;
				gedfile = p.gedfile;
				record = p.gedRecord;
				opgOptions = p.opgOptions;
				if (record != null && record.isNFS())
					access.open(record, pages.get(0).getFirstOptions().getRoot());
				descSchemes = new ArrayList<ColorScheme>();
				ancesSchemes = new ArrayList<ColorScheme>();
				for(AncesteralColorSchemes scheme:AncesteralColorSchemes.values()){
					if(scheme.schemeclass == pages.get(0).getFirstOptions().getAncesScheme().getClass())
						ancesSchemes.add(pages.get(0).getFirstOptions().getAncesScheme());
					else ancesSchemes.add(scheme.getScheme());
				}
				for(DescendantColorSchemes scheme:DescendantColorSchemes.values()){
					if(scheme.schemeclass == pages.get(0).getFirstOptions().getDescScheme().getClass())
						descSchemes.add(pages.get(0).getFirstOptions().getDescScheme());
					else descSchemes.add(scheme.getScheme());
				}
				log.debug("Loading chart with ancesScheme = " + pages.get(0).getFirstOptions().getAncesGens());
				log.debug("Loading chart with descScheme = " + pages.get(0).getFirstOptions().getDescGens());
				if(record == null) //if no record
				{
					//create editable chart from ged/paf
					try{
						if (gedfile.endsWith(".ged") || gedfile.endsWith(".GED"))
						{
							GedcomParser gedparser = new GedcomParser(gedfile);
							record = gedparser.parseGedcom();
						} else if (gedfile.endsWith(".paf") || gedfile.endsWith(".PAF") ||
								gedfile.endsWith(".zip") || gedfile.endsWith(".ZIP"))
						{
							record = PAF5Parser.parsePAF5File(gedfile);
						} else throw new Exception("Invalid genealogy file specified");
						pages.get(0).getFirstOptions().setRoot(record.getIndividual(pages.get(0).getFirstOptions().getRoot().id), this);
						hollowchart = null;
						previewChart = p.charts==null?null:p.charts.size()==0?null:p.charts.get(0);
						state = SessionState.edit;
						names_dataProvider = getIndividualList();
						changeType(opgOptions.getChartType());
						setChanged(true);
					}
					catch(Exception e) //chart is only viewable
					{
						log.debug("Ack~!!!! could not open gedcom",e);
						state = SessionState.view;
						record = null;
						gedfile = null;
						hollowchart = p.charts==null?null:p.charts.size()==0?null:p.charts.get(0);;
						previewChart = p.charts==null?null:p.charts.size()==0?null:p.charts.get(0);;
						changeType(opgOptions.getChartType());
					}
				}
				
				else //create chart using record
				{
					log.debug("Loaded record");
					pages.get(0).getFirstOptions().setRoot(record.getIndividual(pages.get(0).getFirstOptions().getRoot().id), this);
					hollowchart = null;
					previewChart = p.charts==null?null:p.charts.size()==0?null:p.charts.get(0);;
					state = SessionState.edit;
					names_dataProvider = getIndividualList();
					changeType(opgOptions.getChartType());
					//changed = true;
				}
				projfile = filename;
				//changed = false;
			}
			else{
				throw new FailedToLoadException("Unsupported File Type");
			}
	}
	

	/*
	 * Saves the current chart to disk as an .opg file
	 * fileName - the name of the file to which to save the chart (.opg will be appended if it is not
	 * already part of the file name
	 * 
	 * throws: IllegalArgumentException if the fileName is null;
	 * 	IOException when a file i/o error occurs (eg. invalid file path, disk write error, etc.)
	 */
	public void save(File file, boolean useRec) throws FailedToSaveException, IOException {	
		if (hollowchart == null && gedfile == null)
				throw new IllegalStateException("A chart must be created before saving.");
//		ChartSerializer chartOut = new ChartSerializer();
//	    chartOut.createChart(getChart(), projfile);
//		System.out.println("Saving chart with ancesScheme = " + options.getAncesScheme());
//		System.out.println("Saving chart with descScheme = " + options.getDescScheme());
//		System.out.println("Saving chart with root = " + options.getRoot().id);
		Project export = new Project(getCharts(), pages, opgOptions, gedfile, record, useRec);
		try{

			Project.save(export, file);
			projfile = file.getCanonicalPath();
		}
		catch(Exception e){
			log.debug("Error Saving file", e);
			throw new FailedToSaveException(e.toString());
		}
	    resetChanged();
	}
	
	/**
	 * Sets the session root and whether or not to include said root's spouse's tree.
	 * 
	 * (Root is not stored in the implementation of OpgSession but in the instance of
	 * opg.chart.ChartOptions that OpgSession contains.)
	 * 
	 * @param indi is the individual to be set as root
	 * @param includeSpouse true if spouse's tree is also desired, else false
	 */
	public void setRoot(Individual indi, boolean includeSpouse){
		//options.setRoot(parser.individualmap.get(indiID));\
		currentPage().getFirstOptions().setRoot(indi, this);
		currentPage().getFirstOptions().setIncludeSpouses(includeSpouse);
	}
	
	public Individual getCurrentPageBaseRoot(){
		return currentPage().getFirstOptions().getRoot();
	}
	
	public Individual getBaseRoot(){
		return pages.get(0).getFirstOptions().getRoot();
	}
	

	public ArrayList<Individual> getIndividualList() throws IllegalStateException
    {
		ArrayList<Individual> indiList = new ArrayList<Individual>();
		Set<String> idset = record.getIndividuals().keySet();
		Map<String, Individual> map = record.getIndividuals();
//		log.debug("populating list");
		for(String id:idset)
		{
			indiList.add(map.get(id));
		}
		
//		log.debug("sorting");
		Collections.sort(indiList);
//		log.debug("done");
		return indiList;
    }
	
	

	public void changeType(ChartType type) {
		log.debug("type change, creating maker");
		System.out.println("Type Change " + type);
		if(type!=null)
		{	
			pageNumber = 0;
			
			ChartOptions tempOptions = pages.get(0).getFirstOptions();
			pages = new ArrayList<OpgPage>();
			pages.add(new OpgPage(tempOptions, type, this));
			currentPage().getFirstMaker().convertOpgOptions(opgOptions);
			
			opgOptions.setChartType(type);
			
			
		}
	}

	/**
	 * Gets the first maker of the first page.  This is the maker where all 'general'
	 * information is stored and processed in.
	 * @return
	 */
	public ChartMaker getBaseMaker() {
		return pages.get(0).getFirstMaker();
	}
	//TODO problems
	public ChartMaker getMaker() {
//		if (pageMakers == null)
//			return null;
//		return pageMakers.get(pageNumber).get(chartNumber);
		if (currentPage().getFirstMaker() == null)
			return null;
		return currentPage().getFirstMaker();
	}
	
	public ChartMaker addMaker(ChartType type, Individual root, int genOffset) {
		if (getMakerByRoot(root.id) != null)
		{
			System.out.println("Attempted to add duplicate maker");
			if(type == ChartType.MULTISHEET && genOffset < ((MultisheetChartMaker)getMakerByRoot(root.id)).getGenOffset())
				((MultisheetChartMaker)getMakerByRoot(root.id)).setGenOffset(genOffset);
			return null;
		}
		
		ChartMaker retVal;
		
		
		//Here is where it checks if it fits on current page, if does, inserts there
		retVal = type.getMaker();
		ChartOptions tempOptions = retVal.convertToSpecificOptions(new ChartOptions(getBaseOptions()));
		tempOptions.setRoot(root, this);
		
		double addedHeight = getChartHeight(retVal, tempOptions);//retVal.getChart(tempOptions, this).getYExtent();
		
		if (pages.get(pages.size()-1).getRemainingHeight() > addedHeight){
			retVal = pages.get(pages.size()-1).addMaker(type, root, genOffset, this);
			
			root.pageId.setItem(pages.size() + ":" + pages.get(pages.size() - 1).getChartCount());
		}
		else{
			
			//If not . . .
			
			OpgPage tempPage = new OpgPage(pages.get(0).getFirstOptions(), type, root, this);
			retVal = tempPage.getFirstMaker();
			if (type == ChartType.MULTISHEET){
				MultisheetChartMaker temp = (MultisheetChartMaker) retVal;
				temp.setGenOffset(genOffset);
			}
			pages.add(tempPage);
			root.pageId.setItem(pages.size()+"");
		}
		
		
		
		
		return retVal;
	}
	
	/**
	 * Used to determine the height of a chart, without recursively drawing new charts
	 * Only used for Multisheet Charts
	 * @return
	 */
	public double getChartHeight(ChartMaker maker, ChartOptions options){
		double retVal = 0f;
		((MultisheetChartMaker)maker).gettingChartHeight = true;
		retVal = maker.getChart(options, this).getYExtent();
		((MultisheetChartMaker)maker).gettingChartHeight = false;
		return retVal;
	}
	
	public ChartMaker getMakerByRoot(String id){
		ChartMaker retVal = null;
		for (OpgPage page : pages){
			retVal = page.getMakerByRoot(id);
			if(retVal != null)
				break;
		}
		
		return retVal;
	}
	
	public OpgPage getPageByRoot(String id){
		for(OpgPage page : pages){
			if(page.containsRoot(id))
				return page;
		}
		return null;
	}
	
	public int getIndexOf(OpgPage page){
		return pages.indexOf(page);
	}
	
	public ChartOptions getOptionsByRoot(String id){
		ChartOptions retVal = null;
		for (OpgPage page : pages){
			retVal = page.getOptionsByRoot(id);
			if(retVal != null)
				break;
		}
		
		return retVal;
	}
	
	public ChartOptions getBaseOptions(){
		return pages.get(0).getFirstOptions();
	}
	
	//TODO this has a problem
	public ChartOptions getOptions(){
		return currentPage().getFirstOptions();
	}
	
	public OpgOptions getOpgOptions(){
		return opgOptions;
	}
	
	
	public void getIncMaker(){
		if (pageNumber < pages.size() - 1)
			pageNumber++;
	}
	
	public void getDecMaker(){
		if (pageNumber > 0)
			pageNumber--;
	}

	public int getPageNumber(){
		return pageNumber;
	}
	
	public int getPages(){
		return pages.size();
	}
	
	public OpgPage getPage(int i){
		return pages.get(i);
	}
	
	//TODO this will probably also have to wipe each maker in the first page, except the first
	public void resetPages(){
		
		while (pages.size() > 1)
			pages.remove(1);		
	}
	
	public void resetIndiList(){
		indisInTree = new ArrayList<Individual>();
	}
	
	public void resetIndiFlags(){
		for (Individual indi : indisInTree)
			indi.resetFlags();
		resetIndiList();
	}

	/**
	 * Adds the current individual to the list of Indis in the current box tree structure.
	 * Allows resetting of flags without stack overflow.
	 * @param indi
	 */
	public void addIndiToTree(Individual indi){
		if (!indisInTree.contains(indi))
			indisInTree.add(indi);
	}
	
	public void resetFamilyList(){
		familiesInTree = new ArrayList<Family>();
	}
	
	public void resetFamilyFlags(){
		for (Family fam : familiesInTree)
			fam.resetFlags();
	}

	/**
	 * Adds the family to the list of families in the current box tree structure.
	 * Allows resetting of flags without stack overflow
	 * @param fam
	 */
	public void addFamToTree(Family fam){
		if (!familiesInTree.contains(fam))
			familiesInTree.add(fam);
	}
	
	
	public OpgPage currentPage(){
		return pages.get(pageNumber);
	}
	
	public boolean isChanged(){
		return changed;
	}
	
	public void setChanged(boolean set){
		changed = set;
	}
	
	public void resetChanged(){
		changed = false;
	}
	
	public ArrayList<ChartDrawInfo> getCharts(){
		ArrayList<ChartDrawInfo> retVal = null;
		for (int i = 0; i < pages.size(); i++){
			if (pages.get(i) == currentPage())
				retVal = pages.get(i).processCharts(this);
			else
				pages.get(i).processCharts(this);
		}
		return retVal;
	}
	
	public ArrayList<ChartDrawInfo> getAllCharts(){
		ArrayList<ChartDrawInfo> retVal = new ArrayList<ChartDrawInfo>();
		for(int i = 0; i < pages.size(); i++){
			retVal.addAll(pages.get(i).processCharts(this));
		}
		return retVal;
	}
	
	public edu.byu.cs.roots.opg.chart.ShapeInfo getIndiIntersect(double x, double y, 
			int maxAnces, int maxDesc, OpgSession session){
		edu.byu.cs.roots.opg.chart.ShapeInfo retVal = null;
		double chartHeight = 0;
		for(int i = 0; i < currentPage().getChartCount(); i++){
			retVal = currentPage().getMaker(i).getIndiIntersect(x, y-chartHeight, maxAnces, maxDesc, session);
			chartHeight+=currentPage().getMaker(i).getChart(currentPage().getOptions(i), this).getYExtent();
			if (retVal != null)
				break;
		}
		
		return retVal;
	}
}
