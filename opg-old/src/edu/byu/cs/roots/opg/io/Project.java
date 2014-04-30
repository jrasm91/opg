package edu.byu.cs.roots.opg.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import edu.byu.cs.roots.opg.chart.ChartDrawInfo;
import edu.byu.cs.roots.opg.model.OpgOptions;
import edu.byu.cs.roots.opg.model.OpgPage;

/**
 * This class wraps all data needed to save a project
 * @author Travix
 *
 */
public class Project implements Serializable{
	
	/**
	 * 
	 */
	public static final long serializedVersionID = 1000L;
	private static final long serialVersionUID = serializedVersionID;
	public String version = null;
	public String osName = null;
	public String archType = null;
	public ArrayList<ChartDrawInfo> charts;
	public ArrayList<OpgPage> pages = null;
	public OpgOptions opgOptions;
	public String gedfile;
	public GedcomRecord gedRecord;
	public static final String VERSION = "" + ((double)serialVersionUID / 1000);
	public transient String versionID;
	
	
	public Project(ArrayList<ChartDrawInfo> charts, ArrayList<OpgPage> pages, OpgOptions opgOptions, String gedfile, GedcomRecord gedrec, boolean useRec) {
		this.charts = charts;
		this.pages = pages;
		this.gedfile = gedfile;
		this.gedRecord = (useRec)? gedrec : null;
		this.opgOptions = opgOptions;
	}
	
	public Project() {
		this.charts = null;
		this.pages = null;
		this.gedfile = null;
		this.gedRecord = null;
		this.opgOptions = null;
	}
	
	public static void save(Project p, File file) throws FileNotFoundException, IOException{ 
		FileOutputStream fileOut = new FileOutputStream(file);
		GZIPOutputStream zipOut = new GZIPOutputStream(fileOut);
        ObjectOutputStream objectOut = new ObjectOutputStream(zipOut);
		
        try{
        	p.version = System.getProperty("java.version");
        } catch (SecurityException e){
        	p.version = System.getProperty("Secured");
        } catch (IllegalArgumentException e){
        	p.version = System.getProperty("Undefined");
        }
        //write os name
        try{
        	p.osName = System.getProperty("os.name");
        } catch (SecurityException e){
        	p.osName = System.getProperty("Secured");
        } catch (IllegalArgumentException e){
        	p.osName = System.getProperty("Undefined");
        }
        //write architecture type
        try{
        	p.archType = System.getProperty("os.arch");
        } catch (SecurityException e){
        	p.archType = System.getProperty("Secured");
        } catch (IllegalArgumentException e){
        	p.archType = System.getProperty("Undefined");
        }
		
        objectOut.writeObject(VERSION);
        objectOut.writeObject(p);
        objectOut.close();
        zipOut.close();
        fileOut.close();
	}
	
	public static Project open(File file) throws IOException, FileNotFoundException, ClassNotFoundException{
		if (file == null)
			return null;
		
		FileInputStream inFile = new FileInputStream(file);
		GZIPInputStream zipInput = new GZIPInputStream(inFile);
		ObjectInputStream objectInput = new ObjectInputStream(zipInput);
		Project p = null;
		String tempVersionID = VERSION;
		try
		{
			tempVersionID = (String)objectInput.readObject();
		}
		catch (ClassCastException e){}
		p = (Project) objectInput.readObject();
		p.versionID = tempVersionID;
		if(p.gedRecord != null)
			p.gedRecord.linkRecord();
		return p;	
	}
	
}
