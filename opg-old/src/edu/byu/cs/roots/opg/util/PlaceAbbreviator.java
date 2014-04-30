package edu.byu.cs.roots.opg.util;

import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.util.ArrayList;
import java.util.HashMap;

public class PlaceAbbreviator {
	
	static HashMap<String, String> stateAbbrMap = setUpStateAbbreviations();
	static HashMap<String, String> countryAbbrMap = setUpCountryAbbreviations();
	public static FontRenderContext frc = NameAbbreviator.frc;
	static boolean carrots = false;
	static ArrayList<String> extraneousWords = setUpExtraneousWords();

		
	/**
	 * Abbreviates a place name as much as necessary to fit within a specified width.
	 * @param line the place string to fit (assumes comma separate list of places - e.g. "Seattle, King County, Washington, USA")
	 * @param width the width in points in which to fit this string (1 point = 1/72 inches)
	 * @param font the font to use to draw this object
	 * @return the abbreviated version of the place name
	 * 
	 * 
	 * Order of abbreviations:
	 *  1. check to see if the original line can already fit in the given width
	 *  2. if there are carrots (< and >) around the line, it will keep the carrots
	 *     until there is no more room left
	 *  3. remove all periods
	 *  4. remove blank places (e.g. this: ,,Pennsylvania -> to this: Pennsylvania)
	 *  5. the country (if included) will be abbreviated
	 *  6. the state (if included) will be abbreviated
	 *  7. extraneous words that describe the county will be removed
	 *  8. Shorten North, South, East and West to N, S, E, W respectively
	 *  9. county is removed
	 * 10. all the vowels below the state or country are removed
	 * 11. everything is removed but the state or country
	 * 12. if nothing fits, then a blank string is returned
	 * 
	 * question: should we take out parenthesis?  ( and )??
	 * 
	 */
	static public String placeFit(String line, float width, Font font)
	{
		if (line == null || line.equals(""))
			return "";
		//remove unwanted items (aka < > . (...) of etc)
		line = removeUnwants(line);
		
		//Make all the words only first letter capitalized
		line = firstLetterCapsOnly(line);
		
		//System.out.println("lineWidth: " + font.getStringBounds(line, frc).getWidth());
		//System.out.println("width: " + width);
		
		if (font.getStringBounds(line, frc).getWidth() <= width)
			return line;
				
		String[] places = line.split(",");
		String result = line;
		for (int i = 0; i < places.length; ++i)
			places[i] = places[i].trim();
		
		
				
		//if the towns/places are unknown, but not null, remove
		for (int i = 0; i < places.length; i++) {
			if (places[i].equals(""))
				places[i] = null;
		}		
		//test for fit
		result = toPlaceString(places);
		if (font.getStringBounds(result, frc).getWidth() <= width)
			return result;

		
		
		//chop off extraneous words: County, Co., Co, of, Twp, Twp., Township
		//check to make sure that the name is not the extraneous word!  we don't
		//want to get rid of a town named Probably or County (it might happen...)		
		for (int l = 0; l < places.length-1; l++) {
			if (places[l] != null) {
				String[] extran = places[l].split(" ");
				places[l] = "";
				for (int k = 0; k < extran.length; k++) {
					String word = extran[k].toLowerCase();
					if(!extraneousWords.contains(word)) {
						places[l] += extran[k] + " ";
					}
				}
				places[l] = places[l].trim();
			}
		}		
		//test for fit
		result = toPlaceString(places);
		if(font.getStringBounds(result, frc).getWidth() <= width)
			return result;
		
		
		
		//Shorten North, South, East and West to N, S, E and W respectively
		for (int i = 0; i < places.length-1; i++) {
			if (places[i] != null) {
				String[] split = places[i].split(" ");
				places[i] = "";
				for (int j = 0; j < split.length; j++) {
					String word = split[j].toLowerCase();
					if (word.equals("north"))
						places[i] += "N ";
					else if (word.equals("east")) {
						places[i] += "E ";
					} else if (word.equals("south")) {
						places[i] += "S ";
					} else  if (word.equals("west")) {
						places[i] += "W ";
					} else
						places[i] += split[j] + " ";
				}
				places[i] = places[i].trim();
			}
		}		
		//test for fit
		result = toPlaceString(places);
		if(font.getStringBounds(result, frc).getWidth() <= width)
			return result;
		
				
		
		//abbreviate country if applicable - assumes country name is top level
		String abbr = countryAbbrMap.get(places[places.length-1].toLowerCase());
		if (abbr != null)
		{
			places[places.length-1] = abbr;
			if (abbr.equals(""))
				places[places.length-1] = null;
			
		}
		//test for fit
		result = toPlaceString(places);
		if (font.getStringBounds(result, frc).getWidth() <= width)
			return result;
		
		//Abbreviate State if applicable
		Integer stateIdx = null;
		for(int i = (places.length)-1; i >= 0; --i)
		{
			abbr = stateAbbrMap.get((places[i] != null)?places[i].toLowerCase():null);
			if (abbr != null)
			{
				places[i] = abbr;
				stateIdx = i;
				
				//test for fit
				result = toPlaceString(places);
				if (font.getStringBounds(result, frc).getWidth() <= width)
					return result;
				break;
			}
		}
		
		
		//Take out county if applicable - assumes county is one level below state and at least level 2
		if (stateIdx != null && stateIdx >= 2)
		{
			places[stateIdx-1] = null;
			
			//test for fit
			result = toPlaceString(places);
			if (font.getStringBounds(result, frc).getWidth() <= width)
				return result;
		}
		
				
		//remove vowels for levels below State/Country
		//however, if the vowel is only one letter like in E Bloomfield, let it be :)
		if (stateIdx != null)
		{
			for (int i = 0; i < stateIdx; ++i)
			{
				if (places[i] != null) {
					String[] wrds = places[i].split(" ");
					places[i] = "";
					for (int j = 0; j < wrds.length; j++)
					{
						wrds[j] = RemoveVowels(wrds[j]);
						places[i] += wrds[j] + " ";
					}
					places[i] = places[i].trim();
				//	System.out.println("the word: " + places[i] + "!!!!!");
				}
			}
		}
		//test for fit
		result = toPlaceString(places);
		if (font.getStringBounds(result, frc).getWidth() <= width)
			return result;
				
		//Take out everything below state or
		//	take out everything below country
		Integer startIdx = null;
		if (stateIdx != null)
		{
			for (int i = 0; i < stateIdx; ++i)
				places[i] = null;
		}
		else
		{
			for (int i = places.length-1; i >= 0; --i)
			{
				if (places[i] != null)
				{
					startIdx = i;
					break;
				}
			}
			if (startIdx != null)
			{
				for (int i = 0; i < startIdx; ++i)
				{
					places[i] = null;
					result = toPlaceString(places);
					if (font.getStringBounds(result, frc).getWidth() <= width)
						return result;
				}
			}
		}
		//test for fit
		result = toPlaceString(places);
		if (font.getStringBounds(result, frc).getWidth() <= width)
			return result;
		
		//nothing seems to fit, return a blank string
		return "";
	}
	
	private static String firstLetterCapsOnly(String line) {
		String[] places = line.split(",");
		String finalString = "";
		for(String place : places) {
			place = place.trim();
			String[] splt = place.split(" ");
			String finalPlace = "";
			for(String name : splt) {
				if (name != null) {
					if (name.length() > 3) {
						name = name.toLowerCase();
						String lowername = name.substring(1);
						name = Character.toString(Character.toUpperCase(name.charAt(0)));
						name = name.concat(lowername);
					}
					finalPlace += name + " ";
				}
			}
			finalPlace = finalPlace.trim();
			finalString += finalPlace + ", ";
		}
		finalString = finalString.substring(0, finalString.length()-2);
		return finalString;
	}

	/*
	 * Concats the place string together again, separating the
	 * words with a comma and a space.
	 */
	static String toPlaceString(String[] places)
	{
		String result = "";
		for (int i = 0; i < places.length; ++i)
		{
			if (places[i] != null && !places[i].equals(""))
			result += places[i] + ", ";
		}
		if(result.length()==0) //protects against out-of-bounds exception
			return result;
		result = result.substring(0, result.length()-2);
		return result;
	}
	
	//removes the vowels. If it is only one letter, it returns it whether or not it is a vowel.  Also, 
	//the first letter is always skipped in the name, so the gist of the name can be preserved.
	static String RemoveVowels(String oldString)
	{
		if (oldString.length() == 1 || oldString.length() == 0)
			return oldString;
		String ret = oldString.substring(0, 1);
		for (int i = 1; i < oldString.length(); ++i)
		{
			String check = oldString.substring(i, i+1);
			String lower = check.toLowerCase();
			if (!lower.equals("a") && !lower.equals("e") && !lower.equals("i") && !lower.equals("o") && !lower.equals("u"))
			{
				ret += check;
			}
		}
				
		return ret;
		
	}
	
	//when comparing to this list, it assumes you will compare with a lower case word
	/*
	 * This is a list of extraneous words that should not be displayed
	 * on the chart. Does not assume that periods have been removed the
	 * place, however, it probably should! 
	 */
	static private ArrayList<String> setUpExtraneousWords()
	{
		ArrayList<String> extraneousWords = new ArrayList<String>();
		
		//as more are found, please add (remember, lower case!!!)!
		extraneousWords.add("county");
		extraneousWords.add("co.");
		extraneousWords.add("co");
		extraneousWords.add("of");
		extraneousWords.add("twp");
		extraneousWords.add("twp.");
		extraneousWords.add("township");
		extraneousWords.add("district");
		extraneousWords.add("dist.");
		extraneousWords.add("dist");
		extraneousWords.add("dist");
		extraneousWords.add("prob");
		extraneousWords.add("prob.");
		//extraneousWords.add("probably");
		extraneousWords.add("near");
		extraneousWords.add("from");
		
		return extraneousWords;
	}
	
	/*
	 * Grabs the 3 letter place abbreviation for Place. If the place is
	 * located in the United States, then the 2 letter state abbreviation 
	 * is returned. If the place is unknown, "" is returned, and a print
	 * line outputs what the place was (for debugging)
	 */
	static public String getPlaceAbbreviation(String place) {
		//take out everything not wanted in the string
		String line = removeUnwants(place);
		
		//first, grab only the last part of the place (after last ,)
		String[] places = line.split(",");
		
		String mainPlace = places[places.length-1].trim();
		
		//make the word lower case
		mainPlace = mainPlace.toLowerCase();
		
		//check to see if the last word is United States, or a form of it.
		//if so, we want to display the state, so move it one!
		if (isUnitedStates(mainPlace) && places.length > 2)
			mainPlace = places[places.length-2].trim().toLowerCase();
				
		//try to find the location in one of the hash maps
		if(stateAbbrMap.containsKey(mainPlace))
			return stateAbbrMap.get(mainPlace);
		else if (countryAbbrMap.containsKey(mainPlace))
			return countryAbbrMap.get(mainPlace);
		
		//TODO: This should probably throw an error if we do not find the state or country
		//if (line.compareTo("") != 0)
			//System.out.println("three letter place name not found for [" + line + "] in PlaceAbbreviator");
		return "";
	}
	
	/*
	 * This removes all, carrots, parenthesis, '.', '?', trailing commas,
	 * and trailing white space from place. It is contained in a loop until
	 * the string in the beginning of the loop is equal to the string at the
	 * end of the loop (aka, there were no more changes made). This is to make
	 * sure that if there are multiple unwanted characters (like 3 periods in
	 * the name) they are all removed before sending it back.
	 */
	private static String removeUnwants(String place) 
	{
		String before = "";
		String after = place;
		do {
			before = after;
			after = removeCarrots(after);
			after = removeParenthesis(after);
			after = removeChar(after, '?');
			after = removeChar(after, '.');
			after = removeOf(after);
			after = after.trim();
			after = removeLastComma(after);
			// remove of
						
		} while (!after.equalsIgnoreCase(before));	
		return after;
	}

	/*
	 * This removes of from the place.
	 * 
	 * Example:
	 *   Saxony, of Germany
	 *     -> goes to:
	 *   Saxony, Germany
	 */
	private static String removeOf(String place) {
		int of = findOf(place);
		if (of != -1) {
			String newPlace = "";
			if (of != 0)
				newPlace = place.substring(0, of);
			if ((of+1) != (place.length()-1))
				newPlace = newPlace.concat(place.substring(of+2));
			return newPlace;
		}
		return place;
	}

	/*
	 * Determines whether 'of' is in the place name
	 * if it is not contained in the location, return -1
	 */
	private static int findOf(String place) {
		place = place.toLowerCase();
		int start = 0;
		while (start != -1) {
			int o = findCharAfter(place, 'o', start);
			if (o != -1) {
				int f = findCharAfter(place, 'f', o);
				if (f == o + 1) {
					//check to make sure of is not part of the place name:
					if (o > 2) {
						if (Character.isLetter(place.charAt(o-2)))
							return -1;
					}
					return o;
				}
				start = o+1;
			} else
				start = -1;
		}
		return -1;
	}

	/*
	 * This checks to make sure the last character is not a
	 * comma.  if it is, then it will remove it from place
	 */
	private static String removeLastComma(String place) {
		int lastComma = findLastChar(place, ',');
		if (lastComma != -1 && lastComma == place.length()-1)
			return place.substring(0, place.length()-1);
		return place;
	}

	/*
	 * Given the char to remove, it will find the first one in
	 * the list and then remove it!
	 */
	private static String removeChar(String place, char rmv) {
		int theChar = findChar(place, rmv);
		if (theChar != -1) {
			String newPlace = "";
			if (theChar != 0) {
				newPlace = place.substring(0, theChar);
			}
			if (theChar != (place.length()-1))
				newPlace = newPlace.concat(place.substring(theChar+1));
			return newPlace;
		}
		return place;
	}

	/*
	 * Removes parenthesis from the place, and everything inside
	 * Assumes that if there is a (, then there will also be a )
	 * 
	 * example:
	 * London, England (now part of the United Kingdom)
	 * =
	 * London, England
	 */
	private static String removeParenthesis(String place) {
		int openParen = findChar(place, '(');
		int closeParen = findChar(place, ')');
		if (openParen != -1 && closeParen != -1) {
			String newPlace = "";
			if (openParen != 0)
				newPlace = place.substring(0, openParen);
			if (closeParen != (place.length()-1))
				newPlace = newPlace.concat(place.substring(closeParen+1));
			return newPlace;
		}
		return place;
	}

	/*
	 * Removes < and > from the place.
	 * This assumes that there is only one set of < and >
	 * and that > always comes after <, and only if < is contained in the place
	 * 
	 * (aka, they cannot have:  Richmond, Conneticut> or <San Diego, California)
	 * 
	 * example:
	 * <Seattle, Washington>
	 * =
	 * Seattle, Washington
	 */
	private static String removeCarrots(String place) {
		int openCarrot = findChar(place, '<');
		int closeCarrot = findChar(place, '>');
		if (openCarrot != -1 && closeCarrot != -1 ) {
			String newPlace = "";
			if (openCarrot != 0)
				newPlace = place.substring(0, openCarrot-1);
			newPlace = newPlace.concat(place.substring(openCarrot+1, closeCarrot));
			if (closeCarrot != (place.length()-1))
				newPlace = newPlace.concat(place.substring(closeCarrot+1));
			return newPlace;
		}
		return place;
	}

	/*
	 * Returns the first location of the char in the 
	 * given string.  If the char is not located in
	 * the string, -1 is returned.
	 */
	private static int findChar(String place, char c) {
		for (int i = 0; i < place.length(); i++) {
			if (place.charAt(i) == c)
				return i;
		}
		return -1;
	}
	
	/*
	 * Returns the first location of the char in the
	 * given string after the given index.  If the char
	 * is not location in that part of the string, -1 is
	 * returned
	 */
	private static int findCharAfter(String place, char c, int after) {
		for (int i = after; i < place.length(); i++) {
			if (place.charAt(i) == c)
				return i;
		}
		return -1;
	}
	
	/*
	 * Returns the last location of the char in the
	 * given string. If the char is not located in the
	 * string, -1 is returned.
	 */
	private static int findLastChar(String place, char c) {
		for(int i = place.length()-1; i >= 0; i--) {
			if(place.charAt(i) == c)
				return i;
		}
		return -1;
	}

	static private boolean isUnitedStates(String place) {
		if (place.equalsIgnoreCase("UnitedStates") ||
				place.equalsIgnoreCase("USA") ||
				place.equalsIgnoreCase("U.S.A") ||
				place.equalsIgnoreCase("United States") ||
				place.equalsIgnoreCase("U S A") ||
				place.equalsIgnoreCase("US") ||
				place.equalsIgnoreCase("U.S.") ||
				place.equalsIgnoreCase("U. S. A.") ||
				place.equalsIgnoreCase("U. S.") ||
				place.equalsIgnoreCase("Untd Stts") ||
				place.equalsIgnoreCase("U S of A") ||
				place.equalsIgnoreCase("America") ||
				place.equalsIgnoreCase("United States of America"))
			return true;
		return false;
	}
	
	static private HashMap<String, String> setUpStateAbbreviations()
	{
		HashMap<String, String> stateAbbrMap = new HashMap<String, String>();
		stateAbbrMap.put("alaska","AK");stateAbbrMap.put("ak","AK");
		stateAbbrMap.put("american samoa","AS");stateAbbrMap.put("as","AS");
		stateAbbrMap.put("arizona","AZ");stateAbbrMap.put("az","AZ");
		stateAbbrMap.put("arkansas","AR");stateAbbrMap.put("ar","AR");
		stateAbbrMap.put("california","CA");stateAbbrMap.put("ca","CA");
		stateAbbrMap.put("colorado","CO");stateAbbrMap.put("co","CO"); 
		stateAbbrMap.put("connecticut", "CT");stateAbbrMap.put("ct", "CT");
		stateAbbrMap.put("conn", "CT");/*->*/stateAbbrMap.put("cn", "CT"); /*<- found this one, don't know if it is a real one or not, but i put it in just in case.*/
		stateAbbrMap.put("conn.", "CT"); stateAbbrMap.put("ct.", "CT");
		stateAbbrMap.put("delaware", "DE");stateAbbrMap.put("de", "DE");
		stateAbbrMap.put("district of columbia", "DC");stateAbbrMap.put("dc", "DC");
		stateAbbrMap.put("d.c.", "DC");
		stateAbbrMap.put("federated states of micronesia","FM");stateAbbrMap.put("fm","FM");
		stateAbbrMap.put("florida","FL");stateAbbrMap.put("fl","FL");
		stateAbbrMap.put("georgia","GA");stateAbbrMap.put("ga","GA");
		stateAbbrMap.put("guam","GU");stateAbbrMap.put("gu","GU");
		stateAbbrMap.put("hawaii","HI");stateAbbrMap.put("hi","HI");
		stateAbbrMap.put("idaho","ID");stateAbbrMap.put("id","ID");
		stateAbbrMap.put("illinois","IL");stateAbbrMap.put("il","IL");
		stateAbbrMap.put("indiana","IN");stateAbbrMap.put("in","IN");
		stateAbbrMap.put("iowa","IA");stateAbbrMap.put("ia","IA");
		stateAbbrMap.put("kansas","KS");stateAbbrMap.put("ks","KS");
		stateAbbrMap.put("kentucky","KY");stateAbbrMap.put("ky","KY");
		stateAbbrMap.put("louisiana","LA");stateAbbrMap.put("la","LA");
		stateAbbrMap.put("maine","ME");stateAbbrMap.put("me","ME");
		stateAbbrMap.put("marshall islands","MH");stateAbbrMap.put("mh","MH");
		stateAbbrMap.put("maryland","MD");stateAbbrMap.put("md","MD");
		stateAbbrMap.put("massachusetts", "MA");stateAbbrMap.put("ma", "MA");
		stateAbbrMap.put("mass.", "MA");
		stateAbbrMap.put("mass", "MA");
		stateAbbrMap.put("michigan","MI");stateAbbrMap.put("mi","MI");
		stateAbbrMap.put("minnesota","MN");stateAbbrMap.put("mn","MN");
		stateAbbrMap.put("mississippi","MS");stateAbbrMap.put("ms","MS");
		stateAbbrMap.put("missouri","MO");stateAbbrMap.put("mo","MO");
		stateAbbrMap.put("montana","MT");stateAbbrMap.put("mt","MT");
		stateAbbrMap.put("nebraska","NE");stateAbbrMap.put("ne","NE");
		stateAbbrMap.put("nevada","NV");stateAbbrMap.put("nv","NV");
		stateAbbrMap.put("new hampshire","NH");stateAbbrMap.put("nh","NH");
		stateAbbrMap.put("new jersey","NJ");stateAbbrMap.put("nj","NJ");
		stateAbbrMap.put("new mexico","NM");stateAbbrMap.put("nm","NM");
		stateAbbrMap.put("new york","NY");stateAbbrMap.put("ny","NY");
		stateAbbrMap.put("n.y.","NY");
		stateAbbrMap.put("n. y.","NY");
		stateAbbrMap.put("north carolina","NC");stateAbbrMap.put("nc","NC");
		stateAbbrMap.put("n.carolina","NC");
		stateAbbrMap.put("n. carolina","NC");
		stateAbbrMap.put("n carolina","NC");
		stateAbbrMap.put("no. carolina","NC");
		stateAbbrMap.put("no carolina","NC");
		stateAbbrMap.put("no.carolina","NC");
		stateAbbrMap.put("north dakota","ND");stateAbbrMap.put("nd","ND");
		stateAbbrMap.put("n.dakota","ND");
		stateAbbrMap.put("n. dakota","ND");
		stateAbbrMap.put("no. dakota","ND");
		stateAbbrMap.put("no dakota","ND");
		stateAbbrMap.put("no.dakota","ND");
		stateAbbrMap.put("northern mariana islands","MP");stateAbbrMap.put("mp","MP");
		stateAbbrMap.put("ohio","OH");stateAbbrMap.put("oh","OH");
		stateAbbrMap.put("oklahoma","OK");stateAbbrMap.put("ok","OK");
		stateAbbrMap.put("oregon","OR");stateAbbrMap.put("or","OR");
		stateAbbrMap.put("palau","PW");stateAbbrMap.put("pw","PW");
		stateAbbrMap.put("pennsylvania","PA");stateAbbrMap.put("pa","PA");
		stateAbbrMap.put("puerto rico","PR");stateAbbrMap.put("pr","PR");
		stateAbbrMap.put("rhode island","RI");stateAbbrMap.put("ri","RI");
		stateAbbrMap.put("south carolina","SC");stateAbbrMap.put("sc","SC");
		stateAbbrMap.put("s.carolina","SC");
		stateAbbrMap.put("s. carolina","SC");
		stateAbbrMap.put("s carolina","SC");
		stateAbbrMap.put("so. carolina","SC");
		stateAbbrMap.put("so carolina","SC");
		stateAbbrMap.put("so.carolina","SC");
		stateAbbrMap.put("south dakota","SD");stateAbbrMap.put("sd","SD");
		stateAbbrMap.put("s.dakota","SD");
		stateAbbrMap.put("s. dakota","SD");
		stateAbbrMap.put("s dakota","SD");
		stateAbbrMap.put("so dakota","SD");
		stateAbbrMap.put("so.dakota","SD");
		stateAbbrMap.put("so. dakota","SD");
		stateAbbrMap.put("tennessee","TN");stateAbbrMap.put("t","TN");
		stateAbbrMap.put("texas","TX");stateAbbrMap.put("tx","TX");
		stateAbbrMap.put("utah","UT");stateAbbrMap.put("ut","UT");
		stateAbbrMap.put("vermont","VT");stateAbbrMap.put("vt","VT");
		stateAbbrMap.put("virgin islands","VI");stateAbbrMap.put("vi","VI");
		stateAbbrMap.put("virginia","VA");stateAbbrMap.put("va","VA");
		stateAbbrMap.put("washington","WA");stateAbbrMap.put("wa","WA");
		stateAbbrMap.put("west virginia","WV");stateAbbrMap.put("wv","WV");
		stateAbbrMap.put("w. virginia","WV");
		stateAbbrMap.put("w.virginia","WV");
		stateAbbrMap.put("w virginia","WV");
		stateAbbrMap.put("wisconsin","WI");stateAbbrMap.put("wi","WI");
		stateAbbrMap.put("wyoming","WY");stateAbbrMap.put("wy","WY");
		
		return stateAbbrMap;
	}
	
	static private HashMap<String, String> setUpCountryAbbreviations()
	{
		HashMap<String, String> countryAbbrMap = new HashMap<String, String>();
		countryAbbrMap = new HashMap<String, String>();
		//country abbreviations
		//North America
		countryAbbrMap.put("united states", "");
		countryAbbrMap.put("united states of america", "");
		countryAbbrMap.put("us", "");
		countryAbbrMap.put("usa", "");
		countryAbbrMap.put("u.s.a.", "");
		countryAbbrMap.put("u.s.", "");
		countryAbbrMap.put("u. s.", "");
		countryAbbrMap.put("u. s. a.", "");
		countryAbbrMap.put("canada","CAN");
		countryAbbrMap.put("can", "CAN");
		countryAbbrMap.put("mexico", "MEX");
		countryAbbrMap.put("m�xico", "MEX");
		countryAbbrMap.put("american samoa", "ASM");
		
		//North american regions
		countryAbbrMap.put("new england", "");
		
		countryAbbrMap.put("acadia", "ACA");
		countryAbbrMap.put("albania","ALB");
		countryAbbrMap.put("algeria", "DZA");
		countryAbbrMap.put("andorra","AND");
		countryAbbrMap.put("anguilla", "AIA");
		countryAbbrMap.put("antarctica", "ATA");
		countryAbbrMap.put("argentina", "ARG");
		countryAbbrMap.put("armenia", "ARM");
		countryAbbrMap.put("aruba", "ABW");
		countryAbbrMap.put("austria","AUT");
		countryAbbrMap.put("australia", "AUS");
		countryAbbrMap.put("azerbaijan", "AZE");
		countryAbbrMap.put("azores", "AZR");
		countryAbbrMap.put("oesterreich","AUT");
		countryAbbrMap.put("bahamas", "BHS");
		countryAbbrMap.put("bahrain", "BHR");
		countryAbbrMap.put("bangladesh", "BGD");
		countryAbbrMap.put("barbados", "BRB");
		countryAbbrMap.put("belarus", "BLR");
		countryAbbrMap.put("belgium", "BEL");
		countryAbbrMap.put("belize", "BLZ");
		countryAbbrMap.put("benin", "BEN");
		countryAbbrMap.put("bermuda", "BMU");
		countryAbbrMap.put("bhutan", "BTN");
		countryAbbrMap.put("bolivia", "BOL");
		countryAbbrMap.put("bosnia", "BIH");
		countryAbbrMap.put("bosnia and herzegovinia", "BIH");
		countryAbbrMap.put("bosnia hercegovina", "BIH");
		countryAbbrMap.put("botswana", "BWA");
		countryAbbrMap.put("bouvet island", "BVT");
		countryAbbrMap.put("bouvet", "BVT");
		countryAbbrMap.put("brazil", "BRA");
		countryAbbrMap.put("british empire", "BRT");
		countryAbbrMap.put("brunei darussalam", "BRN");
		countryAbbrMap.put("bulgaria", "BGR");
		countryAbbrMap.put("burkina faso", "BFA");
		countryAbbrMap.put("burundi", "BDI");
		countryAbbrMap.put("croatia", "HRV");
		countryAbbrMap.put("cambodia", "KHM");
		countryAbbrMap.put("cameroon", "CMR");
		countryAbbrMap.put("cape colony", "CAP");
		countryAbbrMap.put("cape verde", "CPV");
		countryAbbrMap.put("cayman islands", "CYM");
		countryAbbrMap.put("chad", "TCD");
		countryAbbrMap.put("chile", "CHL");
		countryAbbrMap.put("china", "CHN");
		countryAbbrMap.put("christmas island", "CXR");
		countryAbbrMap.put("cocos islands", "CCK");
		countryAbbrMap.put("keeling islands", "CCK");
		countryAbbrMap.put("keeling", "CCK");
		countryAbbrMap.put("cocos", "CCK");
		countryAbbrMap.put("colombia", "COL");
		countryAbbrMap.put("comoros", "COM");
		countryAbbrMap.put("cook islands", "COK");
		countryAbbrMap.put("congo", "COG");
		countryAbbrMap.put("costa rica", "CRI");
		countryAbbrMap.put("cote d'ivoire", "CIV");
		countryAbbrMap.put("cuba", "CUB");
		countryAbbrMap.put("cyprus", "CYP");
		countryAbbrMap.put("czechoslovakia", "CSK");
		countryAbbrMap.put("czech republic", "CZE");
		countryAbbrMap.put("czech", "CZE");
		countryAbbrMap.put("channel islands", "CHI");
		countryAbbrMap.put("denmark","DNMK");
		countryAbbrMap.put("djibouti", "DJI");
		countryAbbrMap.put("dominica", "DMA");
		countryAbbrMap.put("denmark", "DNK");
		countryAbbrMap.put("dominican republic", "DOM");
		countryAbbrMap.put("el salvador", "SLE");
		countryAbbrMap.put("england", "ENG");
		countryAbbrMap.put("engl", "ENG");
		countryAbbrMap.put("engl.", "ENG");
		countryAbbrMap.put("eng", "ENG");
		countryAbbrMap.put("eng.", "ENG");
		countryAbbrMap.put("ecuador", "ECU");
		countryAbbrMap.put("egypt", "EGY");
		countryAbbrMap.put("equatorial guinea", "GNQ");
		countryAbbrMap.put("eritrea", "ERI");
		countryAbbrMap.put("estonia", "EST");
		countryAbbrMap.put("ethiopia", "ETH");
		countryAbbrMap.put("falkland islands", "FLK");
		countryAbbrMap.put("malvinas", "FLK");
		countryAbbrMap.put("faroe islands", "FRO");
		countryAbbrMap.put("federated states of micronesia", "FSM");
		countryAbbrMap.put("fiji", "FJI");
		countryAbbrMap.put("finland", "FIN");
		countryAbbrMap.put("flanders", "FLD");
		countryAbbrMap.put("france","FRA");
		countryAbbrMap.put("french guiana", "GUF");
		countryAbbrMap.put("french polynesia", "PYF");
		countryAbbrMap.put("gabon", "GAB");
		countryAbbrMap.put("gambia", "GMB");
		countryAbbrMap.put("georgia", "GEO");
		countryAbbrMap.put("germany","GER");//ISO code is "DEU"
		countryAbbrMap.put("ghana", "GHA");
		countryAbbrMap.put("gibraltar", "GIB");
		countryAbbrMap.put("great britain", "GBR");
		countryAbbrMap.put("greece", "GRC");
		countryAbbrMap.put("greenland", "GRL");
		countryAbbrMap.put("grenada", "GRD");
		countryAbbrMap.put("guadeloupe", "GLP");
		countryAbbrMap.put("guam", "GUM");
		countryAbbrMap.put("guatemala", "GTM");
		countryAbbrMap.put("guyana", "GUY");
		countryAbbrMap.put("guinea", "GIN");
		countryAbbrMap.put("guinea-bissau", "GNB");
		countryAbbrMap.put("guinea bissau", "GNB");
		countryAbbrMap.put("haiti", "HTI");
		countryAbbrMap.put("holland","HOL");
		countryAbbrMap.put("hong kong", "HKG");
		countryAbbrMap.put("honduras", "HND");
		countryAbbrMap.put("hungary", "HUN");
		countryAbbrMap.put("magyarorszag", "HUN");
		countryAbbrMap.put("iceland", "ISL");
		countryAbbrMap.put("india", "IND");
		countryAbbrMap.put("indonesia", "IND");
		countryAbbrMap.put("iran", "IRN");
		countryAbbrMap.put("iraq", "IRQ");
		countryAbbrMap.put("ireland", "IRL");
		countryAbbrMap.put("israel", "ISR");
		countryAbbrMap.put("italy","ITA");
		countryAbbrMap.put("jamaica", "JAM");
		countryAbbrMap.put("jordan", "JOR");
		countryAbbrMap.put("japan", "JPN");
		countryAbbrMap.put("kazakhstan", "KAZ");
		countryAbbrMap.put("kenya", "KEN");
		countryAbbrMap.put("kiribati", "KIR");
		countryAbbrMap.put("korea", "KOR");
		countryAbbrMap.put("republic of korea", "KOR");
		countryAbbrMap.put("kuwait", "KWT");
		countryAbbrMap.put("kyrgyzstan", "KGZ");
		countryAbbrMap.put("laos", "LAO");
		countryAbbrMap.put("latvia", "LVA");
		countryAbbrMap.put("lebanon", "LBN");
		countryAbbrMap.put("lesotho", "LSO");
		countryAbbrMap.put("liberia", "LBR");
		countryAbbrMap.put("libyan arab jamhiriya", "LBY");
		countryAbbrMap.put("liechtenstein", "LIE");
		countryAbbrMap.put("lithuania", "LTU");
		countryAbbrMap.put("luxembourg", "LUX");
		countryAbbrMap.put("macau", "MAC");
		countryAbbrMap.put("macedonia", "MKD");
		countryAbbrMap.put("madagascar", "MDG");
		countryAbbrMap.put("malaysia", "MYS");
		countryAbbrMap.put("malawi", "MWI");
		countryAbbrMap.put("maldives", "MDV");
		countryAbbrMap.put("mali", "MLI");
		countryAbbrMap.put("malta", "MLT");
		countryAbbrMap.put("marshall islands", "MHL");
		countryAbbrMap.put("martinique", "MTQ");
		countryAbbrMap.put("mauritania", "MRT");
		countryAbbrMap.put("mauritius", "MUS");
		countryAbbrMap.put("mayotte", "MYT");
		countryAbbrMap.put("mexico", "MEX");
		countryAbbrMap.put("mongollia", "MNG");
		countryAbbrMap.put("montserrat", "MSR");
		countryAbbrMap.put("morocco", "MAR");
		countryAbbrMap.put("moldava", "MDA");
		countryAbbrMap.put("monaco", "MCO");
		countryAbbrMap.put("mozambique", "MOZ");
		countryAbbrMap.put("namibia", "NAM");
		countryAbbrMap.put("nauru", "NRU");
		countryAbbrMap.put("nepal", "NPL");
		countryAbbrMap.put("new caledonia", "NCL");
		countryAbbrMap.put("new zeland", "NZL");
		countryAbbrMap.put("niger", "NER");
		countryAbbrMap.put("nigeria", "NGA");
		countryAbbrMap.put("nicaragua", "NIC");
		countryAbbrMap.put("niue", "NIU");
		countryAbbrMap.put("netherlands","NLD");
		countryAbbrMap.put("norway","NOR");
		countryAbbrMap.put("oman", "OMN");
		countryAbbrMap.put("pakistan", "PAK");
		countryAbbrMap.put("panama", "PAN");
		countryAbbrMap.put("paraguay", "PRY");
		countryAbbrMap.put("pitcairn", "PCN");
		countryAbbrMap.put("peru", "PER");
		countryAbbrMap.put("philippines", "PHL");
		countryAbbrMap.put("papua new guinea", "PNG");
		countryAbbrMap.put("poland", "POL");
		countryAbbrMap.put("palau", "PLW");
		countryAbbrMap.put("belau", "PLW");
		countryAbbrMap.put("portugal", "PRT");
		countryAbbrMap.put("qatar", "QAT");
		countryAbbrMap.put("reunion", "REU");
		countryAbbrMap.put("romania", "ROM");
		countryAbbrMap.put("russia","RUS");
		countryAbbrMap.put("rwanda", "RWA");
		countryAbbrMap.put("saudi arabia", "SAU");
		countryAbbrMap.put("samoa", "WSM");
		countryAbbrMap.put("soviet union", "USSR");
		countryAbbrMap.put("union of soviet socialist republics", "USSR");
		countryAbbrMap.put("spain", "ESP");
		countryAbbrMap.put("saint helena", "SHN");
		countryAbbrMap.put("san marino", "SMR");
		countryAbbrMap.put("sicily", "SIC");
		countryAbbrMap.put("solomon islands", "SLB");
		countryAbbrMap.put("sierra leone", "SLE");
		countryAbbrMap.put("saint lucia", "LCA");
		countryAbbrMap.put("senegal", "SEN");
		countryAbbrMap.put("serbia", "SER");
		countryAbbrMap.put("seychelles", "SYC");
		countryAbbrMap.put("singapore", "SGP");
		countryAbbrMap.put("scotland", "SCT");
		countryAbbrMap.put("scot", "SCT");
		countryAbbrMap.put("scot.", "SCT");
		countryAbbrMap.put("sctl", "SCT");
		countryAbbrMap.put("somalia", "SOM");
		countryAbbrMap.put("suriname", "SUR");
		countryAbbrMap.put("slovenia", "SVN");
		countryAbbrMap.put("sudan", "SDN");
		countryAbbrMap.put("south africa", "ZAF");
		countryAbbrMap.put("sri lanka", "LKA");
		countryAbbrMap.put("sweden","SWE");
		countryAbbrMap.put("swaziland", "SWZ");
		countryAbbrMap.put("switzerland", "CHE");
		countryAbbrMap.put("togo", "TGO");
		countryAbbrMap.put("thailand", "THA");
		countryAbbrMap.put("tajikistan", "TJK");
		countryAbbrMap.put("tokelau", "TKL");
		countryAbbrMap.put("tonga", "TON");
		countryAbbrMap.put("transylvania", "TRN");
		countryAbbrMap.put("tunisia", "TUN");
		countryAbbrMap.put("trinidad and tobago", "TTO");
		countryAbbrMap.put("turkey", "TUR");
		countryAbbrMap.put("tuvalu", "TUV");
		countryAbbrMap.put("taiwan", "TWN");
		countryAbbrMap.put("uganda", "UGA");
		countryAbbrMap.put("ukraine", "UKR");
		countryAbbrMap.put("uruguay", "URY");
		countryAbbrMap.put("uzbekistan", "UZB");
		countryAbbrMap.put("united arab emirates", "ARE");
		countryAbbrMap.put("united kingdom","UK");
		countryAbbrMap.put("uk", "UK");
		countryAbbrMap.put("venezuela", "VEN");
		countryAbbrMap.put("virgin island", "VGB");
		countryAbbrMap.put("vanuatu", "VUT");
		countryAbbrMap.put("wales", "WLS");
		countryAbbrMap.put("western sahara", "ESH");
		countryAbbrMap.put("western samoa", "ESM");
		countryAbbrMap.put("yemen", "YEM");
		countryAbbrMap.put("yugoslavia", "YUG");
		countryAbbrMap.put("zaire", "ZAR");
		countryAbbrMap.put("zambia", "ZMB");
		countryAbbrMap.put("zimbabwe", "ZWE");
		//see site http://helpdesk.rootsweb.com/codes/codes1.html for all of them!
		
		return countryAbbrMap;
	}
}
