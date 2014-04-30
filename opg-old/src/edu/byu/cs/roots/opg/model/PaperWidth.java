package edu.byu.cs.roots.opg.model;

public enum PaperWidth{
	
	wd1("8.5\"",8.5*72),wd2("2'", 24*72), wd3("3'", 36*72), wd4("3'6\"", 42*72);//, wd5("Ha!", 48*72);
	
	public double width;
	public String displayName;
	
	private PaperWidth(String displayName, double width){
		this.displayName = displayName;
		this.width = width;
	}
	
	public static PaperWidth getLargest(){ return wd3;}
	
	public static PaperWidth findClosestSimpleFit(double width){
		return findClosestFit(width*72);
	}
	
	public static PaperWidth findClosestFit(double width){
//		System.out.println("trying to fit " + width);
		PaperWidth tempsize = PaperWidth.values()[0];
		PaperWidth max = tempsize;
		for(PaperWidth psize:PaperWidth.values()) 
			if(psize.width > max.width) 
				max = psize;
		tempsize = max;
		for(PaperWidth psize:PaperWidth.values()){
			if(psize.width >= width && psize.width < tempsize.width) 
				tempsize = psize;
		}
		//TODO This causes the max in the paper width
		if(tempsize.width < width){
			System.out.println("Error chart too big, Chart size required " + width + " max available " + tempsize.width);
			return getLargest();
		}
		else return tempsize;
	}
	
	public static final double minChartWidth = 1728;
	public static final double maxChartWidth = 3024;
	public static final double minMargin = 54;
	public static final double maxMargin = 72;
	
	
}
