package edu.byu.cs.roots.opg.chart.selectvertical;

import java.awt.Font;

import edu.byu.cs.roots.opg.fonts.OpgFont;
import edu.byu.cs.roots.opg.model.Gender;
import edu.byu.cs.roots.opg.model.Individual;
import edu.byu.cs.roots.opg.util.NameAbbreviator;
import edu.byu.cs.roots.opg.util.PlaceAbbreviator;

public class LineItem {
	
	enum LineItemType {
		ABBREVIATED_NAME, FIRST_NAME, MIDDLE_NAMES, MIDDLE_INITIAL, GIVEN_NAMES, SURNAME, NAME_SUFFIX,
		FIXED_STRING, COLLAPSABLE_SPACE, DUPLICATE_LABEL, FIRST_INITIAL, SURNAME_INITIAL, SURNAME_DISENVOWELLED, 
		ABBREVIATED_BIRTH_DATE, BIRTH_DATE_TEXT, BIRTH_DATE_DAY, BIRTH_DATE_MONTH, BIRTH_DATE_YEAR,
		ABBREVIATED_DEATH_DATE, DEATH_DATE_TEXT, DEATH_DATE_DAY, DEATH_DATE_MONTH, DEATH_DATE_YEAR,
		ABBREVIATED_BIRTH_OR_DEATH_YEAR,
		ABBREVIATED_MARRIAGE_DATE, MARRIAGE_DATE_TEXT, MARRIAGE_DATE_DAY, MARRIAGE_DATE_MONTH, MARRIAGE_DATE_YEAR,
		ABBREVIATED_BIRTH_PLACE, BIRTH_PLACE_LOW, BIRTH_PLACE_HIGH,
		ABBREVIATED_DEATH_PLACE, DEATH_PLACE_LOW, DEATH_PLACE_HIGH,
		ABBREVIATED_MARRIAGE_PLACE, DEATH_MARRIAGE_LOW, DEATH_MARRIAGE_HIGH,
		PHOTO, CONTAINER
	};
	
	int fontStyle;
	double relFontSize;
	private String str = "";
	LineItemType type;
	boolean override;
	
	public double getWidth(double fontSize,OpgFont opgFont,Individual indi, String dupLabel, double boxWidth){
		Font font = opgFont.getFont(Font.PLAIN, (float)fontSize);
		if(shouldBeBold())
			font = opgFont.getBoldFont((float)fontSize);
		String str = getText(font, boxWidth, indi, dupLabel);
		if(type == LineItemType.ABBREVIATED_NAME)
			font = opgFont.getBoldFont((float)NameAbbreviator.getSize());
		return font.getStringBounds(str, NameAbbreviator.frc).getWidth();
	}
	
	public boolean shouldBeBold(){
		switch (type){
		case ABBREVIATED_NAME:
		case FIRST_INITIAL:
		case FIRST_NAME:
		case SURNAME:
		case SURNAME_INITIAL:
		case MIDDLE_NAMES:
		case MIDDLE_INITIAL:
		case NAME_SUFFIX:
			return true; //disabled because bold fonts are not currently calculating their widths correctly.
			//return false;
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
	
	String getText(Font font, double width, Individual indi, String duplicate) {
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
			result += ". ";
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
			result += ". ";
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
			return str;
			
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
			else 
				result = "";
			break;
		case DEATH_DATE_TEXT:
			if (indi.death != null && indi.death.date != null)
				result = indi.death.date;
			else 
				result = "";
			break;
		case MARRIAGE_DATE_YEAR:
			if(indi.gender != Gender.MALE || indi.fams.size() == 0 || indi.fams.get(0).marriage == null){
				result = "";
				break;
			}
			result = indi.fams.get(0).marriage.yearString;
			break;
		case MARRIAGE_DATE_TEXT:
			if(indi.gender != Gender.MALE || indi.fams.size() == 0 || indi.fams.get(0).marriage == null){
				result = "";
				break;
			}
			result = indi.fams.get(0).marriage.date;
			break;
		case ABBREVIATED_MARRIAGE_PLACE:
			if(indi.gender != Gender.MALE || indi.fams.size() == 0 || indi.fams.get(0).marriage == null){
				result = "";
				break;
			}
			String mplace = indi.fams.get(0).marriage.place;
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
		case DUPLICATE_LABEL:
			result = duplicate;
			break;
			
		default:
			result = "NOT IMPLEMENTED "+ type;
			System.out.println(result);
		}
		
		return result==null? "" : result.trim();
	}
	
}

