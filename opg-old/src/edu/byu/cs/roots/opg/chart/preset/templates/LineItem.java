package edu.byu.cs.roots.opg.chart.preset.templates;

import java.awt.Font;
import java.io.Serializable;

import edu.byu.cs.roots.opg.chart.ChartOptions;
import edu.byu.cs.roots.opg.model.Gender;
import edu.byu.cs.roots.opg.model.Individual;
import edu.byu.cs.roots.opg.model.OpgSession;
import edu.byu.cs.roots.opg.util.NameAbbreviator;
import edu.byu.cs.roots.opg.util.PlaceAbbreviator;

public class LineItem implements Serializable{
	private static final long serialVersionUID = 1L;

	/**CMD_DATE_RIGHT_JUSTIFY: All date line items following this will be grouped together and pushed to the right
	 * ALL_PURPOSE_NAME: Automatically chooses a name format dependant on box width and amount of other info.
	 *
	 * 
	 *
	 *As far as im planning, it'll only be possible to have one auto choosing item per line
	 */
	public enum LineItemType {
		ABBREVIATED_NAME, FIRST_NAME, MIDDLE_NAMES, MIDDLE_INITIAL, GIVEN_NAMES, SURNAME, NAME_SUFFIX,
		FIXED_STRING, FIXED_BOLD_STRING, COLLAPSABLE_SPACE, DUPLICATE_LABEL, FIRST_INITIAL, 
		SURNAME_INITIAL, SURNAME_DISENVOWELLED, 
		ABBREVIATED_BIRTH_DATE, BIRTH_DATE_TEXT, HALF_BIRTH_DATE_TEXT, BIRTH_DATE_DAY, BIRTH_DATE_MONTH, BIRTH_DATE_YEAR,
		ABBREVIATED_DEATH_DATE, DEATH_DATE_TEXT, HALF_DEATH_DATE_TEXT, DEATH_DATE_DAY, DEATH_DATE_MONTH, DEATH_DATE_YEAR,
		ABBREVIATED_BIRTH_OR_DEATH_YEAR,
		ABBREVIATED_MARRIAGE_DATE, MARRIAGE_DATE_TEXT, MARRIAGE_DATE_DAY, MARRIAGE_DATE_MONTH, MARRIAGE_DATE_YEAR,
		ABBREVIATED_BIRTH_PLACE, ABBREVIATED_THREE_BIRTH_PLACE, BIRTH_PLACE_LOW, BIRTH_PLACE_HIGH,
		ABBREVIATED_DEATH_PLACE, ABBREVIATED_THREE_DEATH_PLACE, DEATH_PLACE_LOW, DEATH_PLACE_HIGH,
		ABBREVIATED_MARRIAGE_PLACE, DEATH_MARRIAGE_LOW, DEATH_MARRIAGE_HIGH,
		PHOTO, CONTAINER, CMD_DATE_RIGHT_JUSTIFY, CMD_PLACE_DATE_RIGHT_JUSTIFY, CMD_DUPE_RIGHT_JUSTIFY
	};
	
	int fontStyle;
	double relFontSize;
	String str = "";
	LineItemType type;
	boolean override;
	
	
	public double getWidth(OpgSession session, double fontSize, ChartOptions options,Individual indi, 
			String dupLabel, double boxWidth, int famCode){
		Font font;
		
		if(isNameType())
			font = session.getOpgOptions().getFont().getBoldFont((float)fontSize);
		else
			font = session.getOpgOptions().getFont().getFont(Font.PLAIN, (float)fontSize);
		double yearWidth = font.getStringBounds("1555", NameAbbreviator.frc).getWidth();
		
		String str = getText(font, boxWidth, indi, dupLabel, famCode);
		
		if(type == LineItemType.ABBREVIATED_NAME)
			font = session.getOpgOptions().getFont().getBoldFont((float)NameAbbreviator.getSize());
		if(type == LineItemType.BIRTH_DATE_YEAR || type == LineItemType.DEATH_DATE_YEAR)
			return yearWidth;
		if(type == LineItemType.ABBREVIATED_THREE_BIRTH_PLACE || type == LineItemType.ABBREVIATED_THREE_DEATH_PLACE)
			font.getStringBounds("KKK", NameAbbreviator.frc).getWidth();
		double width = font.getStringBounds(str, NameAbbreviator.frc).getWidth();
		if(font.getSize2D()<7 && (str.contains("B:")||str.contains("D:")||str.contains("M:")))
			return width + 2;
		return width;
		
	}
	
	public boolean isNameType(){
		switch (type){
		case ABBREVIATED_NAME:
		case FIRST_INITIAL:
		case FIRST_NAME:
		case SURNAME:
		case SURNAME_INITIAL:
		case MIDDLE_NAMES:
		case MIDDLE_INITIAL:
		case NAME_SUFFIX:
		case FIXED_BOLD_STRING:
			return true;
		default:
			return false;
		}
	}
	
	
	public LineItem(LineItemType t){
		type = t;
		
				
	}
	public LineItem(LineItemType t, String s){
		this(t);
		str = s;
	}
	public LineItem(LineItemType t, boolean ovr){
		this(t);
		override = ovr;
	}
	double getHeight() {
		return 0;
	}
	
	String getText(Font font, double width, Individual indi, String duplicate, int famCode) {
		String result = "";
		switch(type) {
		case ABBREVIATED_NAME:
			double dupWid = font.getStringBounds(duplicate+" ", NameAbbreviator.frc).getWidth();
			if(duplicate.compareTo("")==0)
				dupWid=0;
			NameAbbreviator.nameFit(indi.namePrefix.trim(), indi.givenName.trim(), indi.surname, indi.nameSuffix, (float)width - (float)dupWid, font);
			result = NameAbbreviator.getName();
			break;
		case FIRST_NAME:
		{
			int idx = indi.givenName.trim().indexOf(' ');
			if (idx < 0)
				result = indi.givenName;
			else
				result = indi.givenName.substring(0, idx);
			break;
		}
		case FIRST_INITIAL:
		{
			if(indi.givenName.compareTo("")==0)
				return "";
			result = indi.givenName.substring(0,1);
			break;
		}
		case MIDDLE_NAMES:
		{
			int idx = indi.givenName.trim().indexOf(' ');
			if (idx < 0)
				result = "";
			else
				result = indi.givenName.substring(idx);
			break;
		}
		case MIDDLE_INITIAL:
		{
			int idx = indi.givenName.trim().indexOf(' ');
			if (idx < 0)
				result = "";
			else
				result = indi.givenName.substring(idx,idx+1);
			break;
		}
		case SURNAME:
			
			result = indi.surname;
			break;
		case SURNAME_INITIAL:
			if(indi.surname.compareTo("")==0)
				return "";
			result = indi.surname.substring(0,1);
			break;
		case SURNAME_DISENVOWELLED:
			String surname = indi.surname;
			if(surname==null || surname.compareTo("")==0){
				result = "";
				break;
			}
			String novowels=surname.charAt(0)+"";
			String vowels="aeiouAEIOU";
			for(int i=1; i<surname.length() ; i++){
				char c = surname.charAt(i);
				if(vowels.indexOf(c)==-1)
					novowels+=c;
			}
			result = novowels;
			break;
		case NAME_SUFFIX:
			result = indi.nameSuffix;
			break;
		case FIXED_STRING:
			result = str;
			break;
		case FIXED_BOLD_STRING:
			result = str;
			break;
		case COLLAPSABLE_SPACE:
			result = " ";
			break;
		case ABBREVIATED_BIRTH_DATE:
			if (indi.birth != null && indi.birth.date != null)
				result = indi.birth.date; 
			else 
				result = "";
			break;
		case ABBREVIATED_DEATH_DATE:
			if (indi.death != null && indi.death.date != null)
				result = indi.death.date;
			else 
				result = "";
			break;
		case BIRTH_DATE_YEAR: 
			if (indi.birth != null && indi.birth.date != null)
				result = indi.birth.yearString; 
			else 
				result = "";
			break;
		case DEATH_DATE_YEAR:
			if (indi.death != null && indi.death.date != null)
				result = indi.death.yearString; 
			else 
				result = "";
			break;
		case BIRTH_DATE_TEXT:
			if (indi.birth != null && indi.birth.date != null)
				result = indi.birth.date;
				//result = DateAbbreviator.dateFit(indi.birth.date, (float)width/2, font);
			else 
				result = "";
			break;
		case HALF_BIRTH_DATE_TEXT:
			if (indi.birth != null && indi.birth.date != null){
				result = indi.birth.date;
//				result = DateAbbreviator.dateFit(indi.birth.date, (float)width/2, font);
//				if(result.equals(""))
//					result = DateAbbreviator.dateFit(indi.birth.yearString, (float)width/2, font);
			}
			else 
				result = "";
			break;	
		case DEATH_DATE_TEXT:
			if (indi.death != null && indi.death.date != null)
				result = indi.death.date;
				//result = DateAbbreviator.dateFit(indi.death.date, (float)width/2, font);
			else 
				result = "";
			break;
		case HALF_DEATH_DATE_TEXT:
			if (indi.death != null && indi.death.date != null)
			{
				result = indi.birth.date;
//				result = DateAbbreviator.dateFit(indi.death.date, (float)width/2, font);
//				if(result.equals(""))
//					result = DateAbbreviator.dateFit(indi.death.yearString, (float)width/2, font);
			}
			else 
				result = "";
			break;
		case MARRIAGE_DATE_YEAR:
			if(indi.gender != Gender.MALE || indi.fams.size() == 0 || indi.fams.get(famCode).marriage == null){
				result = "";
				break;
			}
			result = indi.fams.get(famCode).marriage.yearString;
			break;
		case MARRIAGE_DATE_TEXT:
			if(indi.gender != Gender.MALE || indi.fams.size() == 0 || indi.fams.get(famCode).marriage == null){
				result = "";
				break;
			}
			result = indi.fams.get(famCode).marriage.date;
			break;
		case ABBREVIATED_MARRIAGE_PLACE:
			if(indi.gender != Gender.MALE || indi.fams.size() == 0 || indi.fams.get(famCode).marriage == null){
				result = "";
				break;
			}
			String mplace = indi.fams.get(famCode).marriage.place;
			result = PlaceAbbreviator.placeFit(mplace, (float)width, font);
			break;
		case ABBREVIATED_BIRTH_PLACE:
			//lets assume the width we are given is the rest of the line available.
			if(indi.birth == null || indi.birth.place == null || indi.birth.place.compareTo("")==0){
				result = "";
				break;
			}
			String place = indi.birth.place;
			result = PlaceAbbreviator.placeFit(place, (float)width, font);
			break;
		case ABBREVIATED_DEATH_PLACE:
			if(indi.death == null || indi.death.place == null || indi.death.place.compareTo("")==0){
				result = "";
				break;
			}
			String dplace = indi.death.place;
			result = PlaceAbbreviator.placeFit(dplace, (float)width, font);
			break;
		case ABBREVIATED_THREE_BIRTH_PLACE:
			//lets assume the width we are given is the rest of the line available.
			if(indi.birth == null || indi.birth.place == null || indi.birth.place.compareTo("")==0){
				result = "";
				break;
			}
			String shortplace = indi.birth.place;
			result = PlaceAbbreviator.getPlaceAbbreviation(shortplace);
			break;
		case ABBREVIATED_THREE_DEATH_PLACE:
			if(indi.death == null || indi.death.place == null || indi.death.place.compareTo("")==0){
				result = "";
				break;
			}
			String shortdplace = indi.death.place;
			result = PlaceAbbreviator.getPlaceAbbreviation(shortdplace);
			break;
		case DUPLICATE_LABEL:
			result = duplicate;
			break;
		case CMD_DATE_RIGHT_JUSTIFY:
			//returns no string, after this command all following strings are grouped and shifted right
			result = "";
			break;
		case CMD_PLACE_DATE_RIGHT_JUSTIFY:
			result = "";
			break;
		case CMD_DUPE_RIGHT_JUSTIFY:
			result = "";
			break;
		default:
			result = "NOT IMPLEMENTED "+ type;
		}
		
		return result==null?"":result;
	}
	
	public String toString(){
		return type.toString();
	}
	
}