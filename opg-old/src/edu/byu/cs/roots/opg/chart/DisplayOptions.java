package edu.byu.cs.roots.opg.chart;

//TODO unused?
public class DisplayOptions {

	private boolean blBirthDate;
	//private boolean blBirthPlacee;
	//private boolean blDeathDate;
	//private boolean blDeathPlace;
	//private boolean blMarriageDate;
	//private boolean blMarriagePlace;
	private boolean blHideMe;
	//private boolean blPicture;
	
	public DisplayOptions(int preset)
	{
		blBirthDate = (preset < 10 ? true : false);
		blHideMe = (preset > -1 ? true : false);
	}
	
	public boolean displayBDate()
	{
		return blBirthDate;
	}
	
	public boolean hideNode() {
		return blHideMe;
	}
}
