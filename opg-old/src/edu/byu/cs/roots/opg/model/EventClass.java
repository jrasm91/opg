package edu.byu.cs.roots.opg.model;
import java.util.Date;

/*
This class holds information for events such as marriages, births, and deaths.
It may have a date and/or two places that have been parsed from the gedcom
*/

public class EventClass
{
	public String date;
	public String place1;
	public String place2;
	public Date sortDate;
	
	public EventClass() {};
	public EventClass(String date, String place1, String place2)
	{
		this.date = date;
		this.place1 = place1;
		this.place2 = place2;
	}
	
	public String getDate()
	{
		return date;
	}
	
	public String getPlace1()
	{
		return place1;
	}
	
	public String getPlace2()
	{
		return place2;
	}
	
	public String toString()
	{
		System.out.print(date);
		return "date: " + date + " place1: " + place1 + " place2: " + place2;
	}
	
}

/*
	Porting Notes: Watch out for public access to the member variables, esp. assignment
*/