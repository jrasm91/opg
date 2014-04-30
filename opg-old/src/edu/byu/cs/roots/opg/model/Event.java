package edu.byu.cs.roots.opg.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;


public class Event implements Serializable{

	static final long serialVersionUID = 1000L;
	
	public EventType type; 
	public String date;
	public String place;
	public String yearString = null;
	public String monthString = null;
	public String dayString = null;
	boolean eraAD = true; //ignore unless yearString is not null
	
	public int yearInt; //only used for comparison, to generate a quick guess of
	//which person in the tree was the most recently born.	
	
	public String place1;//
	public String place2;//
	public Date sortDate;
	
	public Event(EventType type){
		this.type = type;
		this.date = null;
		this.place = null;
		this.place1 = null;
		this.place2 = null;
		this.sortDate = null;
	}
	
	public Event(){
		this.date = null;
		this.place = null;
		this.place1 = null;
		this.place2 = null;
		this.sortDate = null;
	}
	
	public String toString(){
		StringBuilder s = new StringBuilder();
		s.append("\n   DATE: "+date+"\n");
		s.append("   PLACE: "+place);

		return s.toString();
	}
	
	public String getplaceCityState()
	{
		String retValue = "";
		if(place!=null)
		{
			String[] places = place.split(",");
			if (places[0].equals(""))
			{
				places[0] = places[1];
				places[1] = "";
			}
			switch (places.length)
			{
			case 0:
				break;
			case 1:
				retValue = abbrState(places[0].trim());
				break;
			case 2:
				retValue = abbrState(places[0].trim()) + ", " + abbrState(places[1].trim());
				break;
			case 3:
				retValue = abbrState(places[0].trim()) + ", " + abbrState(places[2].trim());
				break;
			case 4:
				retValue = abbrState(places[0].trim()) + ", " + abbrState(places[2].trim());
				break;
			default:
				retValue = abbrState(places[0].trim()) + ", " + abbrState(places[2].trim());
				break;
					
			}

		}
		return retValue;
	}
	
	public String abbrState(String state)
	{
		if(state.compareTo("Alabama") == 0) return "AL";
		if(state.compareTo("Alaska") == 0) return "AK";
		if(state.compareTo("Arizona") == 0) return "AZ";
		if(state.compareTo("Arkansas") == 0) return "AR";
		if(state.compareTo("California") == 0) return "CA";
		if(state.compareTo("Colorado") == 0) return "CO";
		if(state.compareTo("Florida") == 0) return "FL";
		if(state.compareTo("Georgia") == 0) return "GA";
		if(state.compareTo("Hawaii") == 0) return "HI";
		if(state.compareTo("Idaho") == 0) return "ID";
		if(state.compareTo("Illinois") == 0) return "IL";
		if(state.compareTo("Indiana") == 0) return "IN";
		if(state.compareTo("Iowa") == 0) return "IA";
		if(state.compareTo("Kansas") == 0) return "KS";
		if(state.compareTo("Lousiana") == 0) return "LA";
		if(state.compareTo("Maryland") == 0) return "MD";
		if(state.compareTo("Massachusetts") == 0) return "MA";
		if(state.compareTo("Michigan") == 0) return "MI";
		if(state.compareTo("Minnesota") == 0) return "MN";
		if(state.compareTo("Mississippi") == 0) return "MS";
		if(state.compareTo("Missouri") == 0) return "MO";
		if(state.compareTo("Montana") == 0) return "MT";
		if(state.compareTo("Nebraska") == 0) return "NE";
		if(state.compareTo("Nevada") == 0) return "NV";
		if(state.compareTo("New Mexico") == 0) return "NM";
		if(state.compareTo("New York") == 0) return "NY";
		if(state.compareTo("North Carolina") == 0) return "NC";
		if(state.compareTo("North Dakota") == 0) return "ND";
		if(state.compareTo("Ohio") == 0) return "OH";
		if(state.compareTo("Oregon") == 0) return "OR";
		if(state.compareTo("Oklahoma") == 0) return "OK";
		if(state.compareTo("Pennsylvania") == 0) return "PA";
		if(state.compareTo("South Carolina") == 0) return "SC";
		if(state.compareTo("South Dakota") == 0) return "SD";
		if(state.compareTo("Tennessee") == 0) return "TN";
		if(state.compareTo("Texas") == 0) return "TX";
		if(state.compareTo("Utah") == 0) return "UT";
		if(state.compareTo("Virginia") == 0) return "VA";
		if(state.compareTo("Washington") == 0) return "WA";
		if(state.compareTo("Washington DC") == 0) return "DC";
		if(state.compareTo("West Virginia") == 0) return "WV";
		if(state.compareTo("Wisconsin") == 0) return "WI";
		if(state.compareTo("Wyoming") == 0) return "WY";
		if(state.compareTo("New Zealand") == 0) return "NZ";
//		if(place2.length() > 1)System.out.println("|"+place2+"|");
		if(state.contains("Parish")) return state.replace("Parish", "");
		if(state.contains("Area")) return state.replace("Area", "");
		return state;
	}

	public void parseDateParts() {
		if (date == null || date.equals(""))
			return;
		
		Integer year, month, day;
		boolean AD = true;
		year = month = day = null;
		
		StringTokenizer t = new StringTokenizer(date, " \t\n\r\f,/-<>()[]{}|");
		while (t.hasMoreTokens())
		{
			String temp = t.nextToken().toLowerCase();
			try
			{
				int num = Integer.parseInt(temp);
				if (year == null)
				{
					if (day != null)
						year = num;
					else if (num > 31)
						year = num;
					else
						day = num;
				}
					else if (day == null)
				{
					if (num <= 31 && num > 0)
					 day = num;
				}
			}
			catch (NumberFormatException e)
			{
				//assume token is a string
				//see if it's a month (must be 3 characters or longer)
				if (temp.length() > 2 && month == null)
				{
					String monthAbbr = temp.substring(0,3);
					for (int i = 0; i < months.length; ++i)
					{
						if (monthAbbr.equals(months[i].toLowerCase()) )
						{
							month = i;
							break;
						}
					}
				}
				//check to see if it's BC or B.C.
				if (temp.equals("bc") || temp.equals("b.c."))
					AD = false;
				//if it's not a month or era indicator, then ignore it
			}
		}

		if (year == null && day != null){
			year = day;
			day = null;
		}
		
		
		
		
		//if a field is missing, then substitute the possible midpoint for lower fields
		//	ie. if month is missing, set month & day corresponding to midpoint of year
		Calendar calendar = new GregorianCalendar();
		if (year != null)
			calendar.set(Calendar.YEAR, year);
		if (!AD && year > 0)
			calendar.set(Calendar.ERA, GregorianCalendar.BC);
		else
			calendar.set(Calendar.ERA, GregorianCalendar.AD);
		
		if (month == null)
		{
			calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR)/2);
		}
		else if (day == null)
		{
			calendar.set(Calendar.MONTH, month);
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH)/2);
		}
		else
		{
			calendar.set(Calendar.MONTH, month);
			calendar.set(Calendar.DAY_OF_MONTH, day);
		}
		if (year != null) {
			yearString = calendar.get(Calendar.YEAR) + "";
			eraAD = AD;
		}
		if (month != null)
			monthString = months[calendar.get(Calendar.MONTH)];
		if (day != null)
			dayString = calendar.get(Calendar.DAY_OF_MONTH) + "";
		
		
//	    yearInt is assigned either the year, or a value (1 or -1)
//	    that will mean since we don't know their birth year, don't worry about
//	    giving them a birth year integer to compare against others.
			yearInt = calendar.get(Calendar.YEAR);
			if(!AD)
				yearInt = -yearInt;
			
			if(year == null)
				yearInt /= Math.abs(yearInt);
	}
	
	public static final String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
	
}
