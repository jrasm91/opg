package edu.byu.cs.roots.opg.chart;

import java.lang.reflect.Constructor;

//import edu.byu.cs.roots.opg.chart.circ.CircularChartMaker;
import edu.byu.cs.roots.opg.chart.landscape.LandscapeChartMaker;
import edu.byu.cs.roots.opg.chart.multisheet.MultisheetChartMaker;
import edu.byu.cs.roots.opg.chart.portrait.PortraitChartMaker;
import edu.byu.cs.roots.opg.chart.working.WorkingVerticalFixedChartMaker;

public enum ChartType
{
	
    //Removed Select, Preset and Circular, they didn't work too well and weren't necessary.  Kept the code for future use
	PORTRAIT("Portrait", PortraitChartMaker.class),
	LANDSCAPE("Landscape", LandscapeChartMaker.class),
	//SELECT("Select", SelectVerticalChartMaker.class), 
	//PRESET("Preset", PresetChartMaker.class),
	WORKING("Working", WorkingVerticalFixedChartMaker.class),
	MULTISHEET("Multi Sheet", MultisheetChartMaker.class);
	//CIRCULAR("Circular", CircularChartMaker.class),
	
	public String displayName;
	public Class<? extends ChartMaker> makerclass;
	
	private ChartType(String displayName, Class<? extends ChartMaker> makerclass)
	{
		this.displayName = displayName;
		this.makerclass = makerclass;
	}
	
	public String toString()
	{
		return displayName;
	}
	
	public ChartMaker getMaker()
	{
		Object obj;
		try
		{
			Constructor<? extends ChartMaker> constructor = makerclass.getConstructor((Class[])null);
		   	obj = constructor.newInstance((Object[])null);
		}
		catch(Exception e)
		{
			return null;
		}
		return (ChartMaker) obj;
	}
	
}