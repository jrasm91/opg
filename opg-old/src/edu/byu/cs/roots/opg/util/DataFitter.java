package edu.byu.cs.roots.opg.util;

import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.util.ArrayList;
import java.util.HashMap;

import edu.byu.cs.roots.opg.model.Gender;
import edu.byu.cs.roots.opg.model.Individual;

public class DataFitter {

	public static FontRenderContext frc = NameAbbreviator.frc;
	
	public static final int DELETE = 0;
	public static final int SINGLE_NAME_TO_INITIAL = 1;
	public static final int TO_FIRST_WORD = 2;
	public static final int PLACE_AB_STATE = 3;
	public static final int MULTIWORD_TO_ACRONYM = 4;
	
	private static Abbreviator ab1_1 = null;
	private static Abbreviator ab2_1 = null;
	private static Abbreviator ab2_2 = null;
//	private static Abbreviator ab3_1 = null;
	private static Abbreviator ab3_2 = null;
	private static Abbreviator ab3_3 = null;
//	private static Abbreviator ab4_1 = null;
	private static Abbreviator ab4_2 = null;
	private static Abbreviator ab4_3 = null;
	private static Abbreviator ab4_4 = null;
//	private static Abbreviator ab5_1 = null;
	private static Abbreviator ab5_2 = null;
	private static Abbreviator ab5_3 = null;
	private static Abbreviator ab5_4 = null;
	private static Abbreviator ab5_5 = null;
//	private static Abbreviator ab6_1 = null;
	private static Abbreviator ab6_2 = null;
	private static Abbreviator ab6_3 = null;
	private static Abbreviator ab6_4 = null;
	private static Abbreviator ab6_5 = null;
	private static Abbreviator ab6_6 = null;
//	private static Abbreviator ab7_1 = null;
//	private static Abbreviator ab7_2 = null;
//	private static Abbreviator ab7_3 = null;
//	private static Abbreviator ab7_4 = null;
//	private static Abbreviator ab7_5 = null;
//	private static Abbreviator ab7_6 = null;
//	private static Abbreviator ab7_7 = null;
	
	static HashMap<String, String> countryAbbrMap = setUpCountryAbbreviations();
	static HashMap<String, String> stateAbbrMap = setUpStateAbbreviations();
	
	public DataFitter()
	{
		if (stateAbbrMap == null)
			setUpStateAbbreviations();
		if (countryAbbrMap == null)
			setUpCountryAbbreviations();
	}
	
	
	public static ArrayList<String> fit(Individual indi, float height, float width, Font font, String duplicateLabel){
		//if(!duplicateLabel.equals(""))
		//	System.out.println(duplicateLabel+":"+indi.givenName+" "+indi.surname);
//		System.out.println("fitting " + indi.givenName + " " + indi.surname);
//		System.out.println("Font heigth " + font.getLineMetrics("", frc).getHeight());
//		int maxlines = (int) (height/(font.getLineMetrics("", frc).getHeight()));
		int maxlines = (int) (height/(font.getSize()*1.1));
//		System.out.println(font.getSize());
		if(maxlines == 0) maxlines++;
		ArrayList<String> textlines = new ArrayList<String>(maxlines);
		ArrayList<String> line;
		
		if(indi==null){
			System.out.println("WHY ARE YOU GIVING ME NULL PEOPLE????????");
			return textlines;
		}
		else if (indi.givenName==null)
			System.out.println("Name null");
		
		double duplicateWidth = font.getStringBounds(duplicateLabel, NameAbbreviator.frc).getWidth();
//This algorithym may be a bit inefficient, but it allows total control of how the different 
//lines are abbreviated. If there is any abbreviation which can't be accomplished with the
//SingleAbbreviation class then a custom class can be created which implements Abbreviation

		
		
		
		switch(maxlines){
		case 0:
			break;

// Format: [First] [middle] [last] ([birthdate]-[deathdate])
		case 1:
			line = new ArrayList<String>();
			if (!indi.givenName.equals(""))
				line.add(indi.givenName + " ");
			else
				line.add("");
			if (!indi.middleName.equals(""))
				line.add(indi.middleName + " ");
			else
				line.add("");
			if (!indi.surname.equals(""))
				line.add(indi.surname + " ");
			else
				line.add("");
			line.add("(" + ((indi.birth != null && indi.birth.date != null)? indi.birth.date : "    ") + " - " + ((indi.death != null && indi.death.date != null) ? indi.death.date : "    ") + ")");
			if(ab1_1 == null){
				ArrayList<LineOperation> ablist11 = new ArrayList<LineOperation>();
				ablist11.add(new SingleAbbreviation(1, SINGLE_NAME_TO_INITIAL));
				ablist11.add(new SingleAbbreviation(1, DELETE));
				ablist11.add(new SingleAbbreviation(3, DELETE));
				ablist11.add(new SingleAbbreviation(2, TO_FIRST_WORD));
				ablist11.add(new SingleAbbreviation(0, SINGLE_NAME_TO_INITIAL));
				ab1_1 = new AbExecuter(ablist11);
			}
			//textlines.add(ab1_1.fit(line, width, font));
			NameAbbreviator.nameFit(indi.namePrefix.trim(), indi.givenName.trim(), indi.surname, indi.nameSuffix, width - (float) duplicateWidth, font);
			
			String nameOnly = NameAbbreviator.getName() + " " + duplicateLabel;
			String birthYear = (indi.birth == null ? null : (indi.birth.yearString == null ? null : indi.birth.yearString));
			String deathYear = (indi.death == null ? null : (indi.death.yearString == null ? null : indi.death.yearString));
			String fullDate = "";
			if (indi.birth != null && indi.birth.date != null){
				fullDate += "(" + indi.birth.date;
			}
			if (indi.death != null && indi.death.date != null){
				if (indi.birth == null || indi.birth.date == null)
					fullDate += "(   - " + indi.death.date + ")";
				else fullDate += " - " + indi.death.date + ")";
			}
			else if (indi.birth != null && indi.birth.date != null)
				fullDate += " -   )";
			
			String yearsOnly = (birthYear == null && deathYear == null ? ""
					: "(" + (birthYear == null ? "   " : birthYear) + " - " + (deathYear == null ? "   " : deathYear) + ")");
			String birthOnly = birthYear == null ? (deathYear == null ? "" : "(D: " + deathYear + ")") : "(B: " + birthYear + ")";//TODO: added spaces after B: and D:
			
			double stringWidth = font.getStringBounds(nameOnly, NameAbbreviator.frc).getWidth();
			double dateWidth = font.getStringBounds(" " + yearsOnly, NameAbbreviator.frc).getWidth();
			double birthWidth = font.getStringBounds(" " + birthOnly, NameAbbreviator.frc).getWidth();
			double fullDateWidth = font.getStringBounds(" " + fullDate, NameAbbreviator.frc).getWidth();
			
			String whole;
			if (stringWidth + fullDateWidth < width)
				whole = nameOnly + " " + fullDate;
			else if (stringWidth + dateWidth < width)
				whole = nameOnly + " " + yearsOnly;
			else if (stringWidth + birthWidth < width)
				whole = nameOnly + " " + birthOnly;
			else
				whole = nameOnly;
 			
			//String whole = (stringWidth + dateWidth > width) ? nameOnly : nameOnly + " " + yearsOnly;
			//System.out.println(indi.id);
			textlines.add(whole);
			break;
// Format: [First] [middle] [last]
//         [birthdate] [-deathdate]
		case 2:
			line = new ArrayList<String>();
			line.add(indi.givenName + " ");
			line.add(indi.middleName + " ");
			line.add(indi.surname + " ");
			if(ab2_1 == null){
				ArrayList<LineOperation> ablist21 = new ArrayList<LineOperation>();
				ablist21.add(new SingleAbbreviation(1, SINGLE_NAME_TO_INITIAL));
				ablist21.add(new SingleAbbreviation(1, DELETE));
				ablist21.add(new SingleAbbreviation(2, TO_FIRST_WORD));
				ablist21.add(new SingleAbbreviation(0, SINGLE_NAME_TO_INITIAL));
				ab2_1 = new AbExecuter(ablist21);
			}
			//textlines.add(ab2_1.fit(line, width, font));
			NameAbbreviator.nameFit(indi.namePrefix.trim(), indi.givenName.trim(), indi.surname, indi.nameSuffix, width - (float) duplicateWidth, font);
			textlines.add(NameAbbreviator.getName() + " " + duplicateLabel);
			
			line.clear();
			
			if(indi.birth != null){ 
				line.add((indi.birth != null && indi.birth.date != null)? indi.birth.date : "____"); 
				line.add(" - " + ((indi.death != null && indi.death.date != null) ? indi.death.date : "____") + " ");
				
				if(ab2_2 == null){
					ArrayList<LineOperation> ablist22 = new ArrayList<LineOperation>();
					ablist22.add(new SingleAbbreviation(1, DELETE));
					ablist22.add(new SingleAbbreviation(0, DELETE));
					ab2_1 = new AbExecuter(ablist22);
				}
				textlines.add(ab2_1.fit(line, width, font));
			}
			
			break;
// Format: [First] [middle] [last]
//         [B: ] [birthdate] [birthplace]
//		   [D: ] [deathdate] [deathplace]
		case 3:
			line = new ArrayList<String>();
			line.add(indi.givenName + " ");
			line.add(indi.middleName + " ");
			line.add(indi.surname + " ");
			if(ab2_1 == null){
				ArrayList<LineOperation> ablist31 = new ArrayList<LineOperation>();
				ablist31.add(new SingleAbbreviation(1, SINGLE_NAME_TO_INITIAL));
				ablist31.add(new SingleAbbreviation(1, DELETE));
				ablist31.add(new SingleAbbreviation(2, TO_FIRST_WORD));
				ablist31.add(new SingleAbbreviation(0, SINGLE_NAME_TO_INITIAL));
				ab2_1 = new AbExecuter(ablist31);
			}
			//textlines.add(ab2_1.fit(line, width, font));
			NameAbbreviator.nameFit(indi.namePrefix.trim(), indi.givenName, indi.surname, indi.nameSuffix, width - (float) duplicateWidth, font);
			textlines.add(NameAbbreviator.getName() + " " + duplicateLabel);
			
			line.clear();
			
			if(indi.birth != null){ 
				line.add("B: ");
				line.add((indi.birth.date != null) ? indi.birth.date + " ": "unknown " );
				line.add((indi.birth.place != null) ? indi.birth.place : "" );
				
				if(ab3_2 == null){
					ArrayList<LineOperation> ablist32 = new ArrayList<LineOperation>();
					ablist32.add(new SingleAbbreviation(2, DELETE));
					ablist32.add(new SingleAbbreviation(0, DELETE));
					ablist32.add(new SingleAbbreviation(1, DELETE));
					ab3_2 = new AbExecuter(ablist32);
				}
				textlines.add(ab3_2.fit(line, width, font));
				
				line.clear();
			}
			
			if(indi.death != null){
				line.add("D: ");
				line.add((indi.death.date != null) ? indi.death.date + " ": "unknown " );
				line.add((indi.death.place != null) ? indi.death.place : "" );
				
				if(ab3_3 == null){
					ArrayList<LineOperation> ablist33 = new ArrayList<LineOperation>();
					ablist33.add(new SingleAbbreviation(2, DELETE));
					ablist33.add(new SingleAbbreviation(0, DELETE));
					ablist33.add(new SingleAbbreviation(1, DELETE));
					ab3_3 = new AbExecuter(ablist33);
				}
				textlines.add(ab3_3.fit(line, width, font));
			}	
			
			break;
//	 Format: [First] [middle] [last]
//	         [B: ] [birthdate] [birthplace]
//			 [D: ] [deathdate] [deathplace]
//			 [M: ] [marriagedate] [marriageplace]

		case 4:
			line = new ArrayList<String>();
			line.add(indi.givenName + " ");
			line.add(indi.middleName + " ");
			line.add(indi.surname + " ");
			if(ab2_1 == null){
				ArrayList<LineOperation> ablist41 = new ArrayList<LineOperation>();
				ablist41.add(new SingleAbbreviation(1, SINGLE_NAME_TO_INITIAL));
				ablist41.add(new SingleAbbreviation(1, DELETE));
				ablist41.add(new SingleAbbreviation(2, TO_FIRST_WORD));
				ablist41.add(new SingleAbbreviation(0, SINGLE_NAME_TO_INITIAL));
				ab2_1 = new AbExecuter(ablist41);
			}
			//textlines.add(ab2_1.fit(line, width, font));
			NameAbbreviator.nameFit(indi.namePrefix.trim(), indi.givenName.trim(), indi.surname, indi.nameSuffix, width - (float) duplicateWidth, font);
			textlines.add(NameAbbreviator.getName() + " " + duplicateLabel);
			
			line.clear();
			
			if(indi.birth != null){ 
				line.add("B: ");
				line.add((indi.birth.date != null) ? indi.birth.date + " ": "unknown " );
				line.add((indi.birth.place != null) ? indi.birth.place : "" );
				
				if(ab4_2 == null){
					ArrayList<LineOperation> ablist42 = new ArrayList<LineOperation>();
					ablist42.add(new SingleAbbreviation(2, DELETE));
					ablist42.add(new SingleAbbreviation(0, DELETE));
					ablist42.add(new SingleAbbreviation(1, DELETE));
					ab4_2 = new AbExecuter(ablist42);
				}
				textlines.add(ab4_2.fit(line, width, font));
				
				line.clear();
			}
			
			if(indi.death != null){
				line.add("D: ");
				line.add((indi.death.date != null) ? indi.death.date + " ": "unknown " );
				line.add((indi.death.place != null) ? indi.death.place : "" );
				
				if(ab4_3 == null){
					ArrayList<LineOperation> ablist43 = new ArrayList<LineOperation>();
					ablist43.add(new SingleAbbreviation(2, DELETE));
					ablist43.add(new SingleAbbreviation(0, DELETE));
					ablist43.add(new SingleAbbreviation(1, DELETE));
					ab4_3 = new AbExecuter(ablist43);
				}
				textlines.add(ab4_3.fit(line, width, font));
				
				line.clear();
			}
			
			if(indi.gender == Gender.MALE && indi.fams.size() != 0 && indi.fams.get(0).marriage != null){
				line.add("M: ");
				line.add((indi.fams.get(0).marriage.date != null) ? indi.fams.get(0).marriage.date + " ": "unknown " );
				line.add((indi.fams.get(0).marriage.place != null) ? indi.fams.get(0).marriage.place : "" );
				
				if(ab4_4 == null){
					ArrayList<LineOperation> ablist44 = new ArrayList<LineOperation>();
					ablist44.add(new SingleAbbreviation(2, DELETE));
					ablist44.add(new SingleAbbreviation(0, DELETE));
					ablist44.add(new SingleAbbreviation(1, DELETE));
					ab4_4 = new AbExecuter(ablist44);
				}
				textlines.add(ab4_4.fit(line, width, font));
			}
			
			break;
//	 Format: [First] [middle] [last]
//	         [B: ] [birthdate] 
//			 [birthplace]
//			 [D: ] [deathdate]
//			 [deathplace]
		case 5:
			line = new ArrayList<String>();
			line.add(indi.givenName + " ");
			line.add(indi.middleName + " ");
			line.add(indi.surname + " ");
			if(ab2_1 == null){
				ArrayList<LineOperation> ablist51 = new ArrayList<LineOperation>();
				ablist51.add(new SingleAbbreviation(1, SINGLE_NAME_TO_INITIAL));
				ablist51.add(new SingleAbbreviation(1, DELETE));
				ablist51.add(new SingleAbbreviation(2, TO_FIRST_WORD));
				ablist51.add(new SingleAbbreviation(0, SINGLE_NAME_TO_INITIAL));
				ab2_1 = new AbExecuter(ablist51);
			}
			//textlines.add(ab2_1.fit(line, width, font));
			NameAbbreviator.nameFit(indi.namePrefix.trim(), indi.givenName.trim(), indi.surname, indi.nameSuffix, width - (float) duplicateWidth, font);
			textlines.add(NameAbbreviator.getName() + " " + duplicateLabel);
			line.clear();
			
			if(indi.birth != null){ 
				line.add("B: ");
				line.add((indi.birth.date != null) ? indi.birth.date + " ": "unknown " );
				
				if(ab5_2 == null){
					ArrayList<LineOperation> ablist52 = new ArrayList<LineOperation>();
					ablist52.add(new SingleAbbreviation(0, DELETE));
					ablist52.add(new SingleAbbreviation(1, DELETE));
					ab5_2 = new AbExecuter(ablist52);
				}
				textlines.add(ab5_2.fit(line, width, font));
				line.clear();
				
				//line.add((indi.birth.place != null) ? indi.birth.place : "" );
				if (indi.birth.place != null)
					line.add(placeFit(indi.birth.place,width, font));
	
				if(ab5_3 == null){
					ArrayList<LineOperation> ablist53 = new ArrayList<LineOperation>();
					ablist53.add(new SingleAbbreviation(0, DELETE));
					ab5_3 = new AbExecuter(ablist53);
				}
				
				textlines.add(ab5_3.fit(line, width, font));
				line.clear();
			}
			
			if(indi.death != null){
				line.add("D: ");
				line.add((indi.death.date != null) ? indi.death.date + " ": "unknown " );
				
				if(ab5_4 == null){
					ArrayList<LineOperation> ablist54 = new ArrayList<LineOperation>();
					ablist54.add(new SingleAbbreviation(0, DELETE));
					ablist54.add(new SingleAbbreviation(1, DELETE));
					ab5_4 = new AbExecuter(ablist54);
				}
				textlines.add(ab5_4.fit(line, width, font));
				line.clear();
				
				//line.add((indi.death.place != null) ? indi.death.place : "" );
				if (indi.death.place != null)
					line.add(placeFit(indi.death.place,width, font));
	
				if(ab5_5 == null){
					ArrayList<LineOperation> ablist55 = new ArrayList<LineOperation>();
					ablist55.add(new SingleAbbreviation(0, DELETE));
					ab5_5 = new AbExecuter(ablist55);
				}
				textlines.add(ab5_5.fit(line, width, font));
			}
			break;
//	 Format: [First] [middle] [last]
//	         [B: ] [birthdate] 
//			 [birthplace]
//			 [D: ] [deathdate]
//			 [deathplace]
//			 [M: ] [marriagedate] [marriageplace]
		default:
			line = new ArrayList<String>();
			line.add(indi.givenName + " ");
			line.add(indi.middleName + " ");
			line.add(indi.surname + " ");
			if(ab2_1 == null){
				ArrayList<LineOperation> ablist51 = new ArrayList<LineOperation>();
				ablist51.add(new SingleAbbreviation(1, SINGLE_NAME_TO_INITIAL));
				ablist51.add(new SingleAbbreviation(1, DELETE));
				ablist51.add(new SingleAbbreviation(2, TO_FIRST_WORD));
				ablist51.add(new SingleAbbreviation(0, SINGLE_NAME_TO_INITIAL));
				ab2_1 = new AbExecuter(ablist51);
			}
			//textlines.add(ab2_1.fit(line, width, font));
			NameAbbreviator.nameFit(indi.namePrefix.trim(), indi.givenName.trim(), indi.surname, indi.nameSuffix, width - (float) duplicateWidth, font);
			textlines.add(NameAbbreviator.getName() + " " + duplicateLabel);
			line.clear();
			
			
			if(indi.birth != null){ 
				line.add("B: ");
				line.add((indi.birth.date != null) ? indi.birth.date + " ": "unknown " );
				
				if(ab6_2 == null){
					ArrayList<LineOperation> ablist62 = new ArrayList<LineOperation>();
					ablist62.add(new SingleAbbreviation(0, DELETE));
					ablist62.add(new SingleAbbreviation(1, DELETE));
					ab6_2 = new AbExecuter(ablist62);
				}
				textlines.add(ab6_2.fit(line, width, font));
				line.clear();
				
				//line.add((indi.birth.place != null) ? indi.birth.place : "" );
				if (indi.birth.place != null)
					line.add(placeFit(indi.birth.place, width, font));
	
				if(ab6_3 == null){
					ArrayList<LineOperation> ablist63 = new ArrayList<LineOperation>();
					ablist63.add(new SingleAbbreviation(0, DELETE));
					ab6_3 = new AbExecuter(ablist63);
				}
				
				textlines.add(ab6_3.fit(line, width, font));
				line.clear();
			}
			
			if(indi.death != null){
				
				line.add("D: ");
				line.add((indi.death.date != null) ? indi.death.date + " ": "unknown " );
				
				if(ab6_4 == null){
					ArrayList<LineOperation> ablist64 = new ArrayList<LineOperation>();
					ablist64.add(new SingleAbbreviation(0, DELETE));
					ablist64.add(new SingleAbbreviation(1, DELETE));
					ab6_4 = new AbExecuter(ablist64);
				}
				textlines.add(ab6_4.fit(line, width, font));
				line.clear();
				
				//line.add((indi.death.place != null) ? indi.death.place : "" );
				if (indi.death.place != null)
					line.add(placeFit(indi.death.place, width, font));
	
				if(ab6_5 == null){
					ArrayList<LineOperation> ablist65 = new ArrayList<LineOperation>();
					ablist65.add(new SingleAbbreviation(0, DELETE));
					ab6_5 = new AbExecuter(ablist65);
				}
				textlines.add(ab6_5.fit(line, width, font));
				line.clear();
			}
				
			if(indi.gender == Gender.MALE && indi.fams.size() != 0 && indi.fams.get(0).marriage != null){
				line.add("M: ");
				line.add((indi.fams.get(0).marriage.date != null) ? indi.fams.get(0).marriage.date + " ": "unknown " );
				line.add((indi.fams.get(0).marriage.place != null) ? indi.fams.get(0).marriage.place : "" );
				
				if(ab6_6 == null){
					ArrayList<LineOperation> ablist66 = new ArrayList<LineOperation>();
					ablist66.add(new SingleAbbreviation(2, DELETE));
					ablist66.add(new SingleAbbreviation(0, DELETE));
					ablist66.add(new SingleAbbreviation(1, DELETE));
					ab6_6 = new AbExecuter(ablist66);
				}
				textlines.add(ab6_6.fit(line, width, font));
			}
			break;
			
//		default:
//			textlines.add(indi.givenName + " " + indi.surname);
//			if(indi.birth != null){ 
//				textlines.add((indi.birth.date != null) ? "B: " + indi.birth.date : "B: unknown" );
//				textlines.add((indi.birth.place != null) ? indi.birth.place : "unknown" );
//			}
//			if(indi.gender == Gender.MALE && indi.fams.size() != 0 && indi.fams.get(0).marriage != null){
//				textlines.add((indi.fams.get(0).marriage.date != null) ? "M: " + indi.fams.get(0).marriage.date : "M: unknown" );
//				textlines.add((indi.fams.get(0).marriage.place != null) ? indi.fams.get(0).marriage.place : "unknown" );
//			}
//			if(indi.death != null){ 
//				textlines.add((indi.death.date != null) ? "D: " + indi.death.date : "D: unknown" );
//				textlines.add((indi.death.place != null) ? indi.death.place : "unknown" );
//			}
			
			
			
		}
		
		return textlines;
	}
	
	
	interface LineOperation{
		
		public void execute(ArrayList<String> master, ArrayList<String> working);
		
	}
	
	static class DisEnvowel implements LineOperation{

		public void execute(ArrayList<String> master, ArrayList<String> working) {
			String vowel = "aeiou";
			for(int i = 0;i<working.size();i++){
				String s = working.get(i);
				String temp = "";
				for(int j = 0;j<s.length();j++){
					if(vowel.indexOf(s.charAt(j)) > -1) temp += s.charAt(j);
				}
				working.set(i,temp);
			}
			
		}
		
	}
	
	static class SingleAbbreviation implements LineOperation{

		int level;
		int index;
		
		public SingleAbbreviation(int index, int level) {
			this.level = level;
			this.index = index;
		}
		
		public void execute(ArrayList<String> master, ArrayList<String> working){
			String temp = master.get(index);
			switch(level){
				case DELETE:
					temp = ""; 
					break;
				case SINGLE_NAME_TO_INITIAL:
					if(temp.length() > 3) temp = temp.charAt(0) + "";
					break;
				case TO_FIRST_WORD:
					temp = temp.substring(0, temp.indexOf(" ")+1);
					break;
				case PLACE_AB_STATE:
					
				case MULTIWORD_TO_ACRONYM:
					
			}
			working.set(index, temp);
		}

	}
	
	interface Abbreviator{
		
		public String fit(ArrayList<String> line, float width, Font font);
		
	}
	
	static class AbExecuter implements Abbreviator{

		ArrayList<LineOperation> ablist;
		
		public AbExecuter(ArrayList<LineOperation> ablist) {
			this.ablist = ablist;
		}

		public String fit(ArrayList<String> line, float width, Font font) {
			ArrayList<String> temp = new ArrayList<String>();
			temp.addAll(line);
			if(!test(temp, width, font))
			for(LineOperation a: ablist){
				a.execute(line, temp);
				if(test(temp, width, font)) break;
			}
			
			StringBuffer buf = new StringBuffer();
			for(String s:temp){
				buf.append(s);
			}
			
			return buf.toString();
		}
		
		private boolean test(ArrayList<String> line, float width, Font font){
			float tmpwidth = 0;
			for(String s: line){
				tmpwidth += font.getStringBounds(s, frc).getWidth();
			}
			return width > tmpwidth;
		}
		
	}
	
	/*
	static class PlaceAbbreviator implements Abbreviator{

		public String fit(ArrayList<String> line, float width, Font font) {

			return null;
		}
		
		
		
	}
	*/
	
	static public String placeFit(String line, float width, Font font)
	{
		return PlaceAbbreviator.placeFit(line, width, font);
	}
	
	static public String placeFitOld(String line, float width, Font font)
	{
		if (line == null)
			return "";
		if (font.getStringBounds(line, frc).getWidth() <= width)
		{
//			System.out.println("perfect fit! " + font.getStringBounds(line, frc).getWidth() + " " + width);
			return line;
		}
		String[] places = line.split(",");
		String result = line;
		for (int i = 0; i < places.length; ++i)
			places[i] = places[i].trim();
		
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
		
		//TO DO:chop off extraneous words: County, Co., Co, of, Twp, Twp., Township
		
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
		
		
		//TO DO:disenvowel levels below State/Country
		
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
	
	static String toPlaceString(String[] places)
	{
		String result = "";
		for (int i = 0; i < places.length; ++i)
		{
			if (places[i] != null && !places[i].equals(""))
			result += places[i] + ",";
		}
		result = result.substring(0, result.length()-1);
		return result;
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
		stateAbbrMap.put("conn", "CT");
		stateAbbrMap.put("conn.", "CT");
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
		countryAbbrMap.put("canada","CAN");
		countryAbbrMap.put("mexico", "MEX");
		countryAbbrMap.put("m�xico", "MEX");
		
		//North american regions
		countryAbbrMap.put("new england", "");
		
		//Europe
		countryAbbrMap.put("albania","ALB");
		countryAbbrMap.put("andorra","AND");
		countryAbbrMap.put("austria","AUT");
		countryAbbrMap.put("oesterreich","AUT");
		countryAbbrMap.put("belarus", "BLR");
		countryAbbrMap.put("belgium", "BEL");
		//countryAbbrMap.put("Bosnia", "BIH");
		//countryAbbrMap.put("bosnia and herzegovinia", "BIH");
		//countryAbbrMap.put("bosnia hercegovina", "BIH");
		countryAbbrMap.put("bulgaria", "BGR");
		//croatia??
		countryAbbrMap.put("cyprus", "CYP");
		countryAbbrMap.put("denmark","DNMK");
		countryAbbrMap.put("england", "ENG");
		countryAbbrMap.put("engl", "ENG");
		countryAbbrMap.put("engl.", "ENG");
		countryAbbrMap.put("eng", "ENG");
		countryAbbrMap.put("eng.", "ENG");
		countryAbbrMap.put("france","FRA");
		countryAbbrMap.put("germany","GER");//ISO code is "DEU"
		countryAbbrMap.put("holland","HOL");
		countryAbbrMap.put("italy","ITA");
		//countryAbbrMap.put("netherlands","NLD");
		countryAbbrMap.put("norway","NOR");
		countryAbbrMap.put("russia","RUS");
		countryAbbrMap.put("soviet union", "USSR");
		countryAbbrMap.put("union of soviet socialist republics", "USSR");
		countryAbbrMap.put("spain", "ESP");
		countryAbbrMap.put("sweden","SWE");
		//countryAbbrMap.put("switzerland","CHE");
		countryAbbrMap.put("united kingdom","UK");
		
		return countryAbbrMap;
	}
	
	public static ArrayList<String> simpleFit(Individual indi, float height, float width, Font font){
//		int maxlines = (int) (height/(font.getSize()*1.1));
		return null;
		
	}
	
}