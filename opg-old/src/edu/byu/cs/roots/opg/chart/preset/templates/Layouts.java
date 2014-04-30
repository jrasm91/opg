package edu.byu.cs.roots.opg.chart.preset.templates;

import java.util.ArrayList;


import edu.byu.cs.roots.opg.chart.preset.templates.LineItem.LineItemType;

public class Layouts {
	//Initializes LineLayouts
	public static LineLayouts linelayouts = new LineLayouts();
	public static TextBoxLayouts textboxlayouts = new TextBoxLayouts();
	
	public static class LineLayouts{
		//Any line you need.
		/**
		 * {J. Doe  <1>BPL (1837-1920)}
		 */
		LineLayout autoNameBPlaceBDYears;
		
		/**
		 * {J.Doe <1> (1837-1920)}
		 */
		LineLayout autoNameBDYears;
		
		/**
		 * {[Abbreviated-name] <1>
		 */
		LineLayout abbreviatedNameLine;
		/**
		 * {B:1 Jul 1837 D: 13 Aug 1920}
		 */
		LineLayout fullBirthDeathDates;
		/**
		 * {B:1837 D:1920}
		 */
		LineLayout birthDeathYears;
		/**
		 * {B:1 Jul 1837 New York}
		 */
		LineLayout birthDatePlaceFull;
		/**
		 * {B:1 Jul 1837}
		 */
		LineLayout birthDateFull;
		/**
		 * {B:1837}
		 */
		LineLayout birthYear;
		/**
		 * {D:1 Jul 1837 New York}
		 */
		LineLayout deathDatePlaceFull;
		/**
		 * {D:1 Jul 1837}
		 */
		LineLayout deathDateFull;
		/**
		 * {D:1837}
		 */
		LineLayout deathYear;
		/**
		 * {M:1860 B:New York}
		 */
		LineLayout marriageYearBirthPlace;   //TODO: Check Marriage dates and places for empty strings.
		/**
		 * {   M:14 Jan 1860}
		 */
		LineLayout fullMarriageDate;
		/**
		 * {M:14 Jan 1860}
		 */
		LineLayout noIndentFullMarriageDate;
		/**
		 * {M:1860}
		 */
		LineLayout marriageYear;
		/**
		 * {   B: New York}
		 */
		LineLayout birthPlace;
		
		/**
		 * {        New York}
		 */
		LineLayout noTitleBirthPlace;
		
		/**
		 * {D: New Hampshire}
		 */
		LineLayout deathPlace;
		
		/**
		 * {        New Hampshire}
		 */
		LineLayout noTitleDeathPlace;
		/**
		 * {M: 13 Jan 1860 Salt Lake City
		 */
		LineLayout fullMarriageDatePlace;
		/**
		 * {M: England}
		 */
		LineLayout marriagePlace;
		
		/**
		 * {        England}
		 */
		LineLayout noTitleMarriagePlace;
		
		LineLayout TEST_CASE;
		
		//initializes all lineLayouts. Pretty ugly looking.
		private LineLayouts(){
			LineLayout layout;
			
			
			layout = (autoNameBPlaceBDYears = new LineLayout());
			layout.items.add(new LineItem(LineItemType.ABBREVIATED_NAME));
			layout.items.add(new LineItem(LineItemType.CMD_PLACE_DATE_RIGHT_JUSTIFY));
			layout.items.add(new LineItem(LineItemType.DUPLICATE_LABEL));
			layout.items.add(new LineItem(LineItemType.FIXED_STRING," "));
			layout.items.add(new LineItem(LineItemType.ABBREVIATED_THREE_BIRTH_PLACE));
			layout.items.add(new LineItem(LineItemType.FIXED_STRING," ("));
			layout.items.add(new LineItem(LineItemType.BIRTH_DATE_YEAR));
			layout.items.add(new LineItem(LineItemType.FIXED_STRING," - "));
			layout.items.add(new LineItem(LineItemType.DEATH_DATE_YEAR));
			layout.items.add(new LineItem(LineItemType.FIXED_STRING,")"));
			
			layout = (autoNameBDYears = new LineLayout());
			layout.items.add(new LineItem(LineItemType.ABBREVIATED_NAME));
			layout.items.add(new LineItem(LineItemType.CMD_DATE_RIGHT_JUSTIFY));
			layout.items.add(new LineItem(LineItemType.DUPLICATE_LABEL));
			layout.items.add(new LineItem(LineItemType.FIXED_STRING," ("));
			layout.items.add(new LineItem(LineItemType.BIRTH_DATE_YEAR));
			layout.items.add(new LineItem(LineItemType.FIXED_STRING," - "));
			layout.items.add(new LineItem(LineItemType.DEATH_DATE_YEAR));
			layout.items.add(new LineItem(LineItemType.FIXED_STRING,")"));
			
			layout = (abbreviatedNameLine = new LineLayout());
			layout.items.add(new LineItem(LineItemType.ABBREVIATED_NAME));
			layout.items.add(new LineItem(LineItemType.CMD_DUPE_RIGHT_JUSTIFY));
			layout.items.add(new LineItem(LineItemType.DUPLICATE_LABEL));
			
			layout = (fullBirthDeathDates = new LineLayout());
			layout.items.add(new LineItem(LineItemType.FIXED_STRING,"   B: "));
			layout.items.add(new LineItem(LineItemType.HALF_BIRTH_DATE_TEXT));
			layout.items.add(new LineItem(LineItemType.FIXED_STRING," D: "));
			layout.items.add(new LineItem(LineItemType.HALF_DEATH_DATE_TEXT));
			
			layout = (birthDeathYears = new LineLayout());
			layout.items.add(new LineItem(LineItemType.FIXED_STRING,"B: "));
			layout.items.add(new LineItem(LineItemType.BIRTH_DATE_YEAR));
			layout.items.add(new LineItem(LineItemType.FIXED_STRING," D: "));
			layout.items.add(new LineItem(LineItemType.DEATH_DATE_YEAR));
			
			layout = (birthDatePlaceFull = new LineLayout());
			layout.items.add(new LineItem(LineItemType.FIXED_STRING,"   B: "));
			layout.items.add(new LineItem(LineItemType.BIRTH_DATE_TEXT));
			layout.items.add(new LineItem(LineItemType.FIXED_STRING," "));
			layout.items.add(new LineItem(LineItemType.ABBREVIATED_BIRTH_PLACE));
			
			layout = (birthDateFull = new LineLayout());
			layout.items.add(new LineItem(LineItemType.FIXED_STRING,"   B: "));
			layout.items.add(new LineItem(LineItemType.BIRTH_DATE_TEXT));
			
			layout = (birthYear = new LineLayout());
			layout.items.add(new LineItem(LineItemType.FIXED_STRING,"B: "));
			layout.items.add(new LineItem(LineItemType.BIRTH_DATE_YEAR));
			
			layout = (deathDatePlaceFull = new LineLayout());
			layout.items.add(new LineItem(LineItemType.FIXED_STRING,"   D: "));
			layout.items.add(new LineItem(LineItemType.DEATH_DATE_TEXT));
			layout.items.add(new LineItem(LineItemType.FIXED_STRING," "));
			layout.items.add(new LineItem(LineItemType.ABBREVIATED_DEATH_PLACE));
			
			layout = (deathDateFull = new LineLayout());
			layout.items.add(new LineItem(LineItemType.FIXED_STRING,"   D: "));
			layout.items.add(new LineItem(LineItemType.DEATH_DATE_TEXT));
			
			layout = (deathYear = new LineLayout());
			layout.items.add(new LineItem(LineItemType.FIXED_STRING,"D: "));
			layout.items.add(new LineItem(LineItemType.DEATH_DATE_YEAR));
			
			layout = (marriageYearBirthPlace = new LineLayout());
			layout.items.add(new LineItem(LineItemType.FIXED_STRING,"M: "));
			layout.items.add(new LineItem(LineItemType.MARRIAGE_DATE_YEAR));
			layout.items.add(new LineItem(LineItemType.FIXED_STRING," B: "));
			layout.items.add(new LineItem(LineItemType.ABBREVIATED_BIRTH_PLACE));
			
			layout = (fullMarriageDatePlace = new LineLayout());
			layout.items.add(new LineItem(LineItemType.FIXED_STRING,"   M: "));
			layout.items.add(new LineItem(LineItemType.MARRIAGE_DATE_TEXT));
			layout.items.add(new LineItem(LineItemType.FIXED_STRING," "));
			layout.items.add(new LineItem(LineItemType.ABBREVIATED_MARRIAGE_PLACE));
			
			layout = (fullMarriageDate = new LineLayout());
			layout.items.add(new LineItem(LineItemType.FIXED_STRING,"   M: "));
			layout.items.add(new LineItem(LineItemType.MARRIAGE_DATE_TEXT));
			
			layout = (noIndentFullMarriageDate = new LineLayout());
			layout.items.add(new LineItem(LineItemType.FIXED_STRING,"M: "));
			layout.items.add(new LineItem(LineItemType.MARRIAGE_DATE_TEXT));
			
			layout = (marriageYear = new LineLayout());
			layout.items.add(new LineItem(LineItemType.FIXED_STRING,"M: "));
			layout.items.add(new LineItem(LineItemType.MARRIAGE_DATE_YEAR));
			
			layout = (deathPlace = new LineLayout());
			layout.items.add(new LineItem(LineItemType.FIXED_STRING,"   D: "));
			layout.items.add(new LineItem(LineItemType.ABBREVIATED_DEATH_PLACE));
			
			layout = (noTitleDeathPlace = new LineLayout());
			layout.items.add(new LineItem(LineItemType.FIXED_STRING,"        "));
			layout.items.add(new LineItem(LineItemType.ABBREVIATED_DEATH_PLACE));
			
			layout = (birthPlace = new LineLayout());
			layout.items.add(new LineItem(LineItemType.FIXED_STRING,"   B: "));
			layout.items.add(new LineItem(LineItemType.ABBREVIATED_BIRTH_PLACE));
			
			layout = (noTitleBirthPlace = new LineLayout());
			layout.items.add(new LineItem(LineItemType.FIXED_STRING,"        "));
			layout.items.add(new LineItem(LineItemType.ABBREVIATED_BIRTH_PLACE));
			
			layout = (marriagePlace = new LineLayout());
			layout.items.add(new LineItem(LineItemType.FIXED_STRING,"M: "));
			layout.items.add(new LineItem(LineItemType.ABBREVIATED_MARRIAGE_PLACE));
			
			layout = (noTitleMarriagePlace = new LineLayout());
			layout.items.add(new LineItem(LineItemType.FIXED_STRING,"        "));
			layout.items.add(new LineItem(LineItemType.ABBREVIATED_MARRIAGE_PLACE));

			layout = (TEST_CASE = new LineLayout());
			layout.items.add(new LineItem(LineItemType.ABBREVIATED_NAME));
			layout.items.add(new LineItem(LineItemType.ABBREVIATED_NAME));
			layout.items.add(new LineItem(LineItemType.ABBREVIATED_NAME));
			layout.items.add(new LineItem(LineItemType.ABBREVIATED_NAME));
			layout.items.add(new LineItem(LineItemType.ABBREVIATED_NAME));
			
		}
	}
	public static class TextBoxLayouts{
		/**
		 * {[Name Fit to space] <1>}
		 */
		public BoxLayoutParent OneLineAbbrName;
		/**
		 * {[Name Fit to space]  <1>ENG(1837-1920)}
		 */
		public BoxLayoutParent OneLineAbbrNameBPlaceBDYears;
		/**
		 * [Name Fit to space] <1>(1837-1920)
		 *    B: [New York]
		 */
		public BoxLayoutParent TwoLineAbbrNameBDYearsBPlace;
		
		public BoxLayoutParent ThreeLineAbbrNameBDYearsBDPlaces;
		
		public BoxLayoutParent FourLineAbbrNameBDYearsBDPlaces;
		
		public BoxLayoutParent FiveLine1;
		
		public BoxLayoutParent ThreeLineJointDescBox;
		
		public BoxLayoutParent ThreeLineRotatedSingleDescBox;
		
		public BoxLayoutParent testCase;
		
		
		
		
		public BoxLayoutParent TwoLineWeddingLayout;
		
		public BoxLayoutParent OneLineWeddingLayout;
		
		public BoxLayoutParent NoIndentTwoLineWeddingLayout;
		
		public BoxLayoutParent NoIndentOneLineWeddingLayout;
		
		public BoxLayoutParent NoWeddingLayout;
		
		
		public ArrayList<BoxLayoutParent> list = new ArrayList<BoxLayoutParent>();
		public ArrayList<BoxLayoutParent> weddinglist = new ArrayList<BoxLayoutParent>();
		
		private TextBoxLayouts(){
			BoxLayoutParent layout;
			
			layout = OneLineAbbrName = new BoxLayoutParent();
			layout.lines.add(linelayouts.abbreviatedNameLine);
			layout.displayName = "One Line Name";
			list.add(layout);
			
			layout = OneLineAbbrNameBPlaceBDYears = new BoxLayoutParent();
			layout.lines.add(linelayouts.autoNameBPlaceBDYears);
			layout.displayName = "One Line Name and Dates";
			list.add(layout);
			
			layout = TwoLineAbbrNameBDYearsBPlace = new BoxLayoutParent();
			layout.lines.add(linelayouts.autoNameBDYears);
			layout.lines.add(linelayouts.birthPlace);
			layout.displayName = "Two Line";
			list.add(layout);
			
			layout = ThreeLineAbbrNameBDYearsBDPlaces = new BoxLayoutParent();
			layout.lines.add(linelayouts.autoNameBDYears);
			layout.lines.add(linelayouts.birthPlace);
			layout.lines.add(linelayouts.deathPlace);
			layout.displayName = "Three Line";
			list.add(layout);
			
			layout = FourLineAbbrNameBDYearsBDPlaces = new BoxLayoutParent();
			layout.lines.add(linelayouts.abbreviatedNameLine);
			layout.lines.add(linelayouts.fullBirthDeathDates);
			layout.lines.add(linelayouts.birthPlace);
			layout.lines.add(linelayouts.deathPlace);
			layout.displayName = "Four Line";
			list.add(layout);
			
			layout = FiveLine1 = new BoxLayoutParent();
			layout.lines.add(linelayouts.abbreviatedNameLine);
			layout.lines.add(linelayouts.birthDateFull);
			layout.lines.add(linelayouts.noTitleBirthPlace);
			layout.lines.add(linelayouts.deathDateFull);
			layout.lines.add(linelayouts.noTitleDeathPlace);
			layout.displayName = "Five Line";
			list.add(layout);
			
			layout = ThreeLineJointDescBox = new BoxLayoutParent();
			layout.lines.add(linelayouts.abbreviatedNameLine);
			layout.lines.add(linelayouts.birthDatePlaceFull);
			layout.lines.add(linelayouts.deathDatePlaceFull);
			layout.parallelCouple = true;
			layout.displayName = "Three Line Parallel Descendant Box";
			list.add(layout);
			
			layout = ThreeLineRotatedSingleDescBox = new BoxLayoutParent();
			layout.lines.add(linelayouts.abbreviatedNameLine);
			layout.lines.add(linelayouts.birthDatePlaceFull);
			layout.lines.add(linelayouts.deathDatePlaceFull);
			layout.rotateSingleDescendants = true;
			layout.displayName = "Three Line Rotated Single Descendant Box";
			list.add(layout);
			
			layout = testCase = new BoxLayoutParent();
			layout.lines.add(linelayouts.TEST_CASE);
			layout.displayName = "TEST_CASE";
			list.add(layout);
			
			
			layout = TwoLineWeddingLayout = new BoxLayoutParent();
			layout.lines.add(linelayouts.fullMarriageDate);
			layout.lines.add(linelayouts.noTitleMarriagePlace);
			layout.displayName = "Two Line Wedding";
			weddinglist.add(layout);
			
			layout = OneLineWeddingLayout = new BoxLayoutParent();
			layout.lines.add(linelayouts.fullMarriageDate);
			layout.displayName = "One Line Wedding";
			weddinglist.add(layout);
			
			layout = NoIndentTwoLineWeddingLayout = new BoxLayoutParent();
			layout.lines.add(linelayouts.noIndentFullMarriageDate);
			layout.lines.add(linelayouts.noTitleMarriagePlace);
			layout.displayName = "No Indent Two Line Wedding";
			weddinglist.add(layout);
			
			layout = NoIndentOneLineWeddingLayout = new BoxLayoutParent();
			layout.lines.add(linelayouts.noIndentFullMarriageDate);
			layout.displayName = "No Indent One Line Wedding";
			weddinglist.add(layout);
			
			layout = NoWeddingLayout = new BoxLayoutParent();
			layout.displayName = "No Wedding Layout";
			weddinglist.add(layout);
		}
	}
}
