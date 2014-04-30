package edu.byu.cs.roots.opg.model;

import java.io.Serializable;
import java.util.ArrayList;

import edu.byu.cs.roots.opg.chart.ChartDrawInfo;
import edu.byu.cs.roots.opg.chart.ChartMaker;
import edu.byu.cs.roots.opg.chart.ChartOptions;
import edu.byu.cs.roots.opg.chart.ChartType;
import edu.byu.cs.roots.opg.chart.cmds.DrawCommand;
import edu.byu.cs.roots.opg.chart.multisheet.MultisheetChartMaker;

public class OpgPage implements Serializable
{

	private static final long serialVersionUID = 1L;
	private ArrayList<ChartOptions> pageOptions;
	private ArrayList<ChartMaker> pageMakers;
	private ArrayList<ImageFile> pageImages;
	private ArrayList<Integer> pageOffsets;
	private ArrayList<ChartDrawInfo> charts;
	
	private double remainingHeight;
	
	private double pageHeight;
	private double pageWidth;
	private double pageMinimumHeight;
	
	public OpgPage(){
		
		pageOptions = new ArrayList<ChartOptions>();
		ChartOptions options = new ChartOptions();
		pageOptions.add(options);
		
		
		pageMakers = new ArrayList<ChartMaker>();
		pageOffsets = new ArrayList<Integer>();
		
		pageImages = new ArrayList<ImageFile>();
		
		charts = new ArrayList<ChartDrawInfo>();
		
		remainingHeight = 0d;
		
		pageHeight = 0;
		pageWidth = 0;
	}
	/**
	 * Used when the chart type is changed
	 */
	public OpgPage(ChartOptions options, ChartType type, OpgSession session){
		pageMakers = new ArrayList<ChartMaker>();
		ChartMaker toAdd = type.getMaker();
		toAdd.setIsPrimaryMaker(true);
		pageMakers.add(toAdd);
		
		
		pageOptions = new ArrayList<ChartOptions>();
		pageOptions.add(pageMakers.get(0).convertToSpecificOptions(options));
		
		pageOffsets = new ArrayList<Integer>();
		pageOffsets.add(0);
		
		pageImages = new ArrayList<ImageFile>();
		
		charts = new ArrayList<ChartDrawInfo>();
		
		remainingHeight = session.getOpgOptions().getPreferredLength();
		
	}
	
	/**
	 * Used when adding a new maker
	 */
	public OpgPage(ChartOptions options, ChartType type, Individual root, OpgSession session){
		pageMakers = new ArrayList<ChartMaker>();
		pageOptions = new ArrayList<ChartOptions>();
		
		pageOffsets = new ArrayList<Integer>();
		pageOffsets.add(0);
		
		pageImages = new ArrayList<ImageFile>();
		
		charts = new ArrayList<ChartDrawInfo>();
		
		ChartMaker temp = type.getMaker();
		temp.setIsPrimaryMaker(false);
		pageMakers.add(temp);
		
		
		ChartOptions tempOptions = temp.convertToSpecificOptions(new ChartOptions(options));
		tempOptions.setRoot(root, session);
		pageOptions.add(tempOptions);
		
		int size = pageMakers.size()-1;
		double addedHeight = session.getChartHeight(pageMakers.get(size), pageOptions.get(size));

		remainingHeight = session.getOpgOptions().getPreferredLength();
		remainingHeight -= addedHeight;
		
	}
	
	public ChartMaker addMaker(ChartType type, Individual root, int genOffset, OpgSession session){
		ChartMaker temp = type.getMaker();
		temp.setIsPrimaryMaker(false);
		if (type == ChartType.MULTISHEET){
			((MultisheetChartMaker) temp).setGenOffset(genOffset);
		}
		if(pageMakers.size() == 1)
		{
			Individual firstRoot = pageOptions.get(0).getRoot();
			firstRoot.pageId.setItem(firstRoot.pageId.getItem()+":1");
		}
		pageMakers.add(temp);
		
		ChartOptions tempOptions = temp.convertToSpecificOptions(new ChartOptions(getFirstOptions()));
		tempOptions.setRoot(root, session);
		pageOptions.add(tempOptions);
		
		int size = pageMakers.size()-2;
//		if(pageMakers.size() == 1)
//			pageOffsets.add(0);
//		else
//			pageOffsets.add(pageOffsets.get(pageOffsets.size()-2)+(int)session.getChartHeight(pageMakers.get(size), pageOptions.get(size)));
		pageOffsets.add(0);
		size = pageMakers.size()-1;
		double addedHeight = session.getChartHeight(pageMakers.get(size), pageOptions.get(size));
		
		
		remainingHeight -= addedHeight;
		
		return temp;
	}
	
	private ArrayList<ChartDrawInfo> getCharts(OpgSession session){
		charts = new ArrayList<ChartDrawInfo>();
		boolean landscape = getFirstOptions().isLandscape();
		if(pageMakers.get(0) != null){
			pageHeight = 0;
			pageWidth = 0;
			pageMinimumHeight = 0;
			for (int i = 0; i < pageMakers.size(); i++){
				ChartDrawInfo toAdd = pageMakers.get(i).getChart(pageOptions.get(i), session);
				toAdd.setYOffset(pageOffsets.get(i));
				charts.add(toAdd);
				
				if(landscape){
					pageHeight += toAdd.getXExtent();
					pageWidth = toAdd.getYExtent();
				}else{
					pageHeight += toAdd.getYExtent();
					pageWidth = toAdd.getXExtent();
				}
//				if (pageWidth == 0)
//					pageWidth = getFirstOptions().isLandscape()?toAdd.getYExtent():toAdd.getXExtent();
//				pageHeight += getFirstOptions().isLandscape()?toAdd.getXExtent():toAdd.getYExtent();
				pageMinimumHeight+=pageOptions.get(i).getMinPaperLength();
				
			}
		}
		return charts;
	}
	
	public ArrayList<ChartDrawInfo> processCharts(OpgSession session){
		charts = new ArrayList<ChartDrawInfo>();
		try{
			if(session.state == SessionState.edit){
				
			}
			if (session.state == SessionState.view)
				charts.add(session.hollowchart);
			else{
				if (session.record != null){
					charts = getCharts(session);
				}
				else{
					charts.add(session.hollowchart);
				}
			}
		}
		catch(Exception e){
		}
		
		return charts;
	}
	
	public ChartOptions getOptions(int n){
		if (n >= pageOptions.size())
			return null;
		else
			return pageOptions.get(n);
	}
	
	public ChartOptions getFirstOptions(){
		if (pageOptions != null && pageOptions.size() > 0)
			return pageOptions.get(0);
		return null;
	}
	
	public ArrayList<ChartOptions> getOptionsList(){
		return pageOptions;
	}
	
	public ChartMaker getMaker(int n){
		if (n >= pageOptions.size())
			return null;
		else
			return pageMakers.get(n);
	}
	
	public ChartMaker getFirstMaker(){
		if (pageMakers != null && pageMakers.size() > 0)
			return pageMakers.get(0);
		return null;
	}
	
	public ArrayList<ChartMaker> getMakerList(){
		return pageMakers;
	}
	
	public int getChartCount(){
		if (pageMakers == null)
			return -1;
		return pageMakers.size();
	}
	
	public ArrayList<ImageFile> getImages(){return pageImages;}
	public double getRemainingHeight(){return remainingHeight;}
	public double getPageHeight(){return pageHeight;}
	public double getPageWidth(){return pageWidth;}
	public double getPageMinimumHeight(){return pageMinimumHeight;}

	public ChartMaker getMakerByRoot(String id){
		for (int i = 0; i < pageOptions.size(); i++){
			if(pageOptions.get(i).getRoot().id.contentEquals(id))
				return pageMakers.get(i);
		}
		return null;
	}
	
	public ChartOptions getOptionsByRoot(String id){
		for (int i = 0; i < pageOptions.size(); i++){
			if(pageOptions.get(i).getRoot().id.contentEquals(id))
				return pageOptions.get(i);
		}
		return null;
	}
	
	public boolean containsRoot(String id){
		for(ChartOptions o : pageOptions)
			if(o.getRoot().id.contentEquals(id))
				return true;
		return false;
	}
	
}
