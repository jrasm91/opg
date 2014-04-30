package edu.byu.cs.roots.opg.util;
//this class represents a place and it's abbreviation

public class PlaceAbbr
{

	public int frequency = 0;//how often the place occurs in a gedcom file
	public int level; //numeric level of place (eg. continent = 0, country = 1, provience/state = 2, county/district = 3, town/city = 4)
	public int known; // how well the abbreviation is known (0 = implied (eg. "north america" or "USA"), 1 = well known ("FL"), 10 = virtually unknown) 
	public String abbr; // the abbreviated string for this place
	
	public PlaceAbbr(String abbr,int level, int known)
	{
		this.abbr = abbr;
		this.level = level;
		this.known = known;
	}
	
}
