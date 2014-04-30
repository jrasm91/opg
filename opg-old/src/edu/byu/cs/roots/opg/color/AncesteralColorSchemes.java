package edu.byu.cs.roots.opg.color;

import java.lang.reflect.Constructor;

public enum AncesteralColorSchemes {
	INVSPECTRUM("Inv. Spectrum", InverseSpectrumColorScheme.class),
	SPECTRUM("Spectrum", SpectrumColorScheme.class),
	PATINVSPECTRUM("Paternal Inv. Spectrum", PaternalInverseColorScheme.class),
	FOURCOLOR("Four Color", FourColorScheme.class), 
	SINGLETONE("Single Color", SingleToneColorScheme.class), 
	//GENDER("Gender Based", GenderColorScheme.class),
	LDS("LDS Ordinances", LDSOrdinanceColorScheme.class);
	
	public String displayName;
	public Class<? extends ColorScheme> schemeclass;
	
	private AncesteralColorSchemes(String displayName, Class<? extends ColorScheme> schemeclass){
		this.displayName = displayName;
		this.schemeclass = schemeclass;
	}
	
	public String toString(){
		return displayName;
	}
	
	public ColorScheme getScheme(){
		Object obj;
		try{
			Constructor<? extends ColorScheme> constructor = schemeclass.getConstructor((Class[])null);
		   	obj = constructor.newInstance((Object[])null);
		}
		catch(Exception e){
			return null;
		}
		return (ColorScheme) obj;
	}
	
}
