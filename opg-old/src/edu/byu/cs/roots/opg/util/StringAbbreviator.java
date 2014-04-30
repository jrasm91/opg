package edu.byu.cs.roots.opg.util;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.util.StringTokenizer;

import edu.byu.cs.roots.opg.model.EventClass;
import edu.byu.cs.roots.opg.model.Individual;

//this class is for abbreviating genealogial data strings in a logical manner


// redo the methods of this class correctly
public class StringAbbreviator
{
	//--------------------------------------------------------------------
	public static String abbrName(Individual indi, Font font, double width)
	{
		FontRenderContext frc = NameAbbreviator.frc;
		
		String result1 = "";
		String result2 = "";
		String result3 = "";
		if (indi.givenName != null)
		{
			result1 = indi.givenName;
			result2 = indi.givenName;
			result3 = ((indi.givenName.length()>0)?indi.givenName.charAt(0)+"": "");
			if (indi.middleName != null)
			{
				result1 += " " + indi.middleName;
				result2 += " " + ((indi.middleName.length()>0)?indi.middleName.charAt(0): "");
				result3 += " " + ((indi.middleName.length()>0)?indi.middleName.charAt(0): "");
			}
			if (indi.surname != null)
			{
				result1 += " " + indi.surname;
				result2 += " " + indi.surname;
				result3 += " " + indi.surname;
			}
			if (indi.nameSuffix != null)
			{
				result1 += " " + indi.nameSuffix;
				result2 += " " + indi.nameSuffix;
				result3 += " " + indi.nameSuffix;
			}
		}
		else if (indi.surname != null)
		{
			result1 = indi.surname;
			result2 = indi.surname;
			result3 = indi.surname;
			if (indi.nameSuffix != null)
			{
				result1 += " " + indi.nameSuffix;
				result2 += " " + indi.nameSuffix;
				result3 += " " + indi.nameSuffix;
			}
		}
		else if (indi.nameSuffix != null)
		{
			result1 = indi.nameSuffix;
			result2 = indi.nameSuffix;
			result3 = indi.nameSuffix;
		}
		else
		{
			return "<Unknown>";
		}
		
		//check to see which one fits
		double width1 = font.getStringBounds(result1, frc).getWidth();
		double width2 = font.getStringBounds(result2, frc).getWidth();
		double width3 = font.getStringBounds(result3, frc).getWidth();
		if (width1 < width)
			return result1;
		else if (width2 < width)
			return result2;
		else if (width3 < width)
			return result3;
		else
			return result3;
	}
	//--------------------------------------------------------------------
	public static String abbrDate(String prefix, EventClass event, Font font, double width)
	{
		if (event.date.equalsIgnoreCase("DEAD") || event.date.equalsIgnoreCase("DECEASED") )
			return "";
		return prefix + event.date;
	}
	//--------------------------------------------------------------------
	public static String abbrDateYear(String prefix, EventClass event, Font font, double width)
	{
		String date = event.date;
		StringTokenizer tokenizer = new StringTokenizer(date);
		String year = "";
		while (tokenizer.hasMoreTokens())
			year = tokenizer.nextToken();
		return prefix + year;
	}
	//--------------------------------------------------------------------
	public static String abbrPlace(String prefix, EventClass event, Font font, double width)
	{
		// redo this method correctly, including redoing the variables layout for places in EventClass
		
		String result = "";
		if (!event.place1.trim().equals(""))
		{
			if (!event.place2.trim().equals(""))
			{
				result = event.place1 + ", " + event.place2;
			}
			else
				result = event.place1;
		}
		else
		{
			if (!event.place2.trim().equals(""))
			{
				result = event.place2;
			}
		}
		
		return prefix + result;
	}
	//--------------------------------------------------------------------
}
