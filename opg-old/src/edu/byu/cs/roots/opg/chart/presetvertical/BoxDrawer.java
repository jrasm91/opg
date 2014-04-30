package edu.byu.cs.roots.opg.chart.presetvertical;

import java.awt.Color;
import java.awt.Font;
import java.awt.font.FontRenderContext;

import edu.byu.cs.roots.opg.chart.ChartDrawInfo;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdMoveTo;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdSetFont;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdText;
import edu.byu.cs.roots.opg.fonts.OpgFont;
import edu.byu.cs.roots.opg.model.Individual;
import edu.byu.cs.roots.opg.util.NameAbbreviator;
import edu.byu.cs.roots.opg.util.PlaceAbbreviator;

public class BoxDrawer {
	
	private double x;
	private double y;
	private ChartDrawInfo chart;
	private BoxFormat box;
	private Individual indi;
	private String dupLabel;
	private FontRenderContext frc = NameAbbreviator.frc;
	private OpgFont opgFont = BoxFormat.font;
	
	private double boxHorizontalOffset;
	private double startX;
	private double startY;
	private double workingWidth;
	private double indentation;


	double dupLabelWidth;

	private double dateWidth;	
	private double yearWidth;
	private double leftParenWidth;
	private double hyphenWidth;
	
	private Font font;
	private Font boldFont;
	
	private double birthLabelWidth;
	private double deathLabelWidth;
	private double marriageLabelWidth;
	
	public BoxDrawer(ChartDrawInfo c, BoxFormat b, Individual i, double xVal, double yVal, String d) {
		chart = c;
		box = b;
		indi = i;
		x = xVal;
		y = yVal;
		dupLabel = d;
		
		
		//figure out how long the (date-date) will be
		font = opgFont.getFont(Font.PLAIN, (float)box.getBodyFontSize());
		boldFont = opgFont.getFont(Font.BOLD, (float)box.getNameFontSize());
		
		if(frc == null)
			System.out.println("FontrenderContext IS NULL!!!");
		
		boxHorizontalOffset = font.getStringBounds("I", frc).getWidth();
		
		//startY an startX move the cursor to the top left hand corner of the box
		//they also take into account the offsets 
		startY = y + box.getHeight()/2;
		startX = x + boxHorizontalOffset;
		
		//determine some common widths
		dateWidth = font.getStringBounds("(K555-K555)", frc).getWidth();		
		yearWidth = font.getStringBounds("K555", frc).getWidth();
		leftParenWidth = font.getStringBounds("(", frc).getWidth();
		hyphenWidth = font.getStringBounds("-", frc).getWidth();
		
		//figure out how much space is needed for the duplicate label
		dupLabelWidth = font.getStringBounds(dupLabel, frc).getWidth();
		
		//figure out how long the box will be (without the offset)
		workingWidth = box.getWidth() - 2*boxHorizontalOffset;
		
		//make the indentation amount
		indentation = font.getStringBounds("M", frc).getWidth();
		
		//figure out the label widths for the labels
		birthLabelWidth = font.getStringBounds("B: ", frc).getWidth();
		deathLabelWidth = font.getStringBounds("D: ", frc).getWidth();
		marriageLabelWidth = font.getStringBounds("M: ", frc).getWidth();
	}
	
	
	/*
	 * This method is called when a chart needs to be filled in. Here is the layout of the thing:
	 *  1. call a new method for how many lines there are in the box
	 *  2. the method will take care of the rest
	 *  (pretty easy) :)
	 */
	public void drawBox() {
		if (indi == null)
			return;
		switch(box.getNumOfLines()) {
		case(1):
			draw1Line();
		break;
		case(2):
			draw2Lines();
		break;
		case(3):
			draw3Lines();
		break;
		case(32):
			draw3v2Lines();
		break;
		case(4):
			draw4Lines();
		break;
		default:
			//this is for 5 or more (there shouldn't be 6+, so if for some reason there is, we reduce it to 5)
			draw5Lines();		
		}
	}
	
	/*
	 * This draws only one line of information.  the information will look like this:
	 *                                                  			*the [] are not shown.
	 * -----------------------------------------------------------
	 * |[abbrev name][tab][dupLabel][birth location][(date-date)]|
	 * -----------------------------------------------------------
	 * 
	 * dupLabel is only there is there is a duplicate label.
	 * tab is only present when the name doesn't take up all the space
	 */
	private void draw1Line() {		
		//figure out how much space is needed for the duplicate label
		double dupLabelWidth = font.getStringBounds(dupLabel, frc).getWidth();
		
		//get the indi's birth place
		double placeWidth = font.getStringBounds("KKK", frc).getWidth()+2;
		String birthPlace = "";
		if (indi.birth != null && indi.birth.place != null)
			birthPlace = PlaceAbbreviator.getPlaceAbbreviation(indi.birth.place);
		
		//subtract dateWidth and dupLabel Width from workingWidth to determine
		//how much room we have to display the indi's name
		double maxNameWidth = workingWidth - dateWidth - dupLabelWidth - placeWidth-5;
		
		//using NameAbbreviator, shrink the name (if needed) to nameWidth
		String namePrefix = (indi.namePrefix != null ? indi.namePrefix.trim() : "");
		String givenName = (indi.givenName != null ? indi.givenName.trim() : "");
		String surnames = (indi.surname != null ? indi.surname.trim() : "");
		String nameSuffix = (indi.nameSuffix != null ? indi.nameSuffix.trim() : "");
		
		NameAbbreviator.nameFit(namePrefix, givenName, surnames, nameSuffix, (float)maxNameWidth, boldFont);
		
		//get the name and it's width
		String nameString = NameAbbreviator.getName().trim();
					
		//make birthYear and deathYear
		String birthYear = "";
		String deathYear = "";
		if (indi.birth != null && indi.birth.yearString != null)
			birthYear = indi.birth.yearString.trim();
		if (indi.death != null && indi.death.yearString != null)
			deathYear = indi.death.yearString.trim();
				
		//draw the name
		chart.addDrawCommand(new DrawCmdSetFont(boldFont,Color.black));
		chart.addDrawCommand(new DrawCmdMoveTo(startX,startY-boldFont.getSize2D()));
		chart.addDrawCommand(new DrawCmdText(nameString));
		
		//draw the 3 letter birth place
		chart.addDrawCommand(new DrawCmdSetFont(font,Color.black));
		chart.addDrawCommand(new DrawCmdMoveTo(startX+workingWidth-dateWidth-placeWidth, startY-font.getSize2D()));
		chart.addDrawCommand(new DrawCmdText(birthPlace));
		
		//draw the date		
		chart.addDrawCommand(new DrawCmdMoveTo(startX+workingWidth-dateWidth, startY-font.getSize2D()));
		chart.addDrawCommand(new DrawCmdText("("));
		chart.addDrawCommand(new DrawCmdMoveTo(startX+workingWidth-dateWidth+leftParenWidth, startY-font.getSize2D()));
		chart.addDrawCommand(new DrawCmdText(birthYear));
		chart.addDrawCommand(new DrawCmdMoveTo(startX+workingWidth-dateWidth+leftParenWidth+yearWidth, startY-font.getSize2D()));
		chart.addDrawCommand(new DrawCmdText("-"));
		chart.addDrawCommand(new DrawCmdMoveTo(startX+workingWidth-dateWidth+leftParenWidth+yearWidth+hyphenWidth, startY-font.getSize2D()));
		chart.addDrawCommand(new DrawCmdText(deathYear));
		chart.addDrawCommand(new DrawCmdMoveTo(startX+workingWidth-dateWidth+leftParenWidth+yearWidth+hyphenWidth+yearWidth, startY-font.getSize2D()));
		chart.addDrawCommand(new DrawCmdText(")"));
		
		//if there is a dubLabel, draw it too
		chart.addDrawCommand(new DrawCmdSetFont(font, Color.darkGray));
		chart.addDrawCommand(new DrawCmdMoveTo (startX+workingWidth-dateWidth-placeWidth-dupLabelWidth-5, startY-font.getSize2D()));
		chart.addDrawCommand(new DrawCmdText(dupLabel));
	}
	
	/*
	 * This draws two lines of information.  the information will look like this:
	 *                                                     *the [] are not shown in the box.
	 * -------------------------------------------
	 * |[abbrev name][tab][dupLabel][(date-date)]|
	 * |  B:[birth location]					 |
	 * -------------------------------------------
	 * 
	 * dupLabel is only there is there is a duplicate label.
	 * tab is only present when the name doesn't take up all the space
	 */
	private void draw2Lines() {		
		//figure out how much space is needed for the duplicate label
		double dupLabelWidth = font.getStringBounds(dupLabel, frc).getWidth();
				
		//subtract dateWidth and dupLabel Width from workingWidth to determine
		//how much room we have to display the indi's name
		double maxNameWidth = workingWidth - dateWidth - dupLabelWidth;;
		
		//using NameAbbreviator, shrink the name (if needed) to nameWidth
		String namePrefix = (indi.namePrefix != null ? indi.namePrefix.trim() : "");
		String givenName = (indi.givenName != null ? indi.givenName.trim() : "");
		String surnames = (indi.surname != null ? indi.surname.trim() : "");
		String nameSuffix = (indi.nameSuffix != null ? indi.nameSuffix.trim() : "");
		
		NameAbbreviator.nameFit(namePrefix, givenName, surnames, nameSuffix, (float)maxNameWidth, boldFont);
		
		//get the name and it's width
		String nameString = NameAbbreviator.getName().trim();
		
		//make birthYear and deathYear
		String birthYear = "";
		String deathYear = "";
		if (indi.birth != null && indi.birth.yearString != null)
			birthYear = indi.birth.yearString.trim();
		if (indi.death != null && indi.death.yearString != null)
			deathYear = indi.death.yearString.trim();
		
		String birthPlace = "";
		if (indi.birth != null && indi.birth.place != null)
			birthPlace = PlaceAbbreviator.placeFit(indi.birth.place, (float)(workingWidth-indentation-birthLabelWidth), font);
		
		//draw the name
		chart.addDrawCommand(new DrawCmdSetFont(boldFont,Color.black));
		chart.addDrawCommand(new DrawCmdMoveTo(startX,startY-boldFont.getSize2D()));
		chart.addDrawCommand(new DrawCmdText(nameString));
						
		//draw the date		
		chart.addDrawCommand(new DrawCmdSetFont(font,Color.black));
		chart.addDrawCommand(new DrawCmdMoveTo(startX+workingWidth-dateWidth, startY-font.getSize2D()));
		chart.addDrawCommand(new DrawCmdText("("));
		chart.addDrawCommand(new DrawCmdMoveTo(startX+workingWidth-dateWidth+leftParenWidth, startY-font.getSize2D()));
		chart.addDrawCommand(new DrawCmdText(birthYear));
		chart.addDrawCommand(new DrawCmdMoveTo(startX+workingWidth-dateWidth+leftParenWidth+yearWidth, startY-font.getSize2D()));
		chart.addDrawCommand(new DrawCmdText("-"));
		chart.addDrawCommand(new DrawCmdMoveTo(startX+workingWidth-dateWidth+leftParenWidth+yearWidth+hyphenWidth, startY-font.getSize2D()));
		chart.addDrawCommand(new DrawCmdText(deathYear));
		chart.addDrawCommand(new DrawCmdMoveTo(startX+workingWidth-dateWidth+leftParenWidth+yearWidth+hyphenWidth+yearWidth, startY-font.getSize2D()));
		chart.addDrawCommand(new DrawCmdText(")"));
		
		//if there is a dubLabel, draw it too
		chart.addDrawCommand(new DrawCmdSetFont(font, Color.darkGray));
		chart.addDrawCommand(new DrawCmdMoveTo (startX+workingWidth-dateWidth-dupLabelWidth-5, startY-font.getSize2D()));
		chart.addDrawCommand(new DrawCmdText(dupLabel));
		
		//draw the birth place
		chart.addDrawCommand(new DrawCmdMoveTo(startX+indentation, startY-boldFont.getSize2D()-font.getSize2D()-1));
		chart.addDrawCommand(new DrawCmdText("B: "));
		chart.addDrawCommand(new DrawCmdMoveTo(startX+indentation+birthLabelWidth, startY-boldFont.getSize2D()-font.getSize2D()-1));
		chart.addDrawCommand(new DrawCmdText(birthPlace));	
	}
	
	/*
	 * This draws three lines of information.  the information will look like this:
	 *                                                     *the [] are not shown in the box.
	 * ------------------------------------------
	 * | [abbreviated name]						|
	 * |   B:[birth date]						|
	 * |     [birth location]					|
	 * ------------------------------------------
	 */
	private void draw3v2Lines() {
		//figure out how much space is needed for the duplicate label
		double dupLabelWidth = font.getStringBounds(dupLabel, frc).getWidth();
		
		//subtract dateWidth and dupLabel Width from workingWidth to determine
		//how much room we have to display the indi's name
		double maxNameWidth = workingWidth - dupLabelWidth;
		
		//using NameAbbreviator, shrink the name (if needed) to nameWidth
		String namePrefix = (indi.namePrefix != null ? indi.namePrefix.trim() : "");
		String givenName = (indi.givenName != null ? indi.givenName.trim() : "");
		String surnames = (indi.surname != null ? indi.surname.trim() : "");
		String nameSuffix = (indi.nameSuffix != null ? indi.nameSuffix.trim() : "");
		
		NameAbbreviator.nameFit(namePrefix, givenName, surnames, nameSuffix, (float)maxNameWidth, boldFont);
		
		//get the name and it's width
		String nameString = NameAbbreviator.getName().trim();
		
		String birthDate = "";
		if (indi.birth != null && indi.birth.date != null)
			birthDate = indi.birth.date.trim();
		
		String birthPlace = "";
		if (indi.birth != null && indi.birth.place != null)
			birthPlace = PlaceAbbreviator.placeFit(indi.birth.place, (float)(workingWidth-indentation-birthLabelWidth), font);
		
		//draw the name
		chart.addDrawCommand(new DrawCmdSetFont(boldFont,Color.black));
		chart.addDrawCommand(new DrawCmdMoveTo(startX,startY-boldFont.getSize2D()));
		chart.addDrawCommand(new DrawCmdText(nameString));
				
		//if there is a dubLabel, draw it too
		chart.addDrawCommand(new DrawCmdSetFont(font, Color.darkGray));
		chart.addDrawCommand(new DrawCmdMoveTo (startX+workingWidth-dupLabelWidth, startY-font.getSize2D()-1));
		chart.addDrawCommand(new DrawCmdText(dupLabel));
		
		//draw the birth date
		chart.addDrawCommand(new DrawCmdMoveTo(startX+indentation, startY-font.getSize2D()-boldFont.getSize2D()-3));
		chart.addDrawCommand(new DrawCmdText("B: "));
		chart.addDrawCommand(new DrawCmdMoveTo(startX+indentation+birthLabelWidth, startY-font.getSize2D()-boldFont.getSize2D()-3));
		chart.addDrawCommand(new DrawCmdText(birthDate));
		
		//draw the birth place
		chart.addDrawCommand(new DrawCmdMoveTo(startX+indentation+birthLabelWidth, startY-(font.getSize2D()*2)-boldFont.getSize2D()-4));
		chart.addDrawCommand(new DrawCmdText(birthPlace));
	}
	
	/*
	 * This draws three lines of information.  the information will look like this:
	 *                                                     *the [] are not shown in the box.
	 * -------------------------------------------
	 * |[abbrev name][tab][dupLabel][(date-date)]|
	 * |  B:[birth location]					 |
	 * |  D:[death location]					 |
	 * -------------------------------------------
	 * 
	 * dupLabel is only there is there is a duplicate label.
	 * tab is only present when the name doesn't take up all the space
	 */
	private void draw3Lines() {		
		//figure out how much space is needed for the duplicate label
		double dupLabelWidth = font.getStringBounds(dupLabel, frc).getWidth();
				
		//subtract dateWidth and dupLabel Width from workingWidth to determine
		//how much room we have to display the indi's name
		double maxNameWidth = workingWidth - dateWidth - dupLabelWidth;
		
		//using NameAbbreviator, shrink the name (if needed) to nameWidth
		String namePrefix = (indi.namePrefix != null ? indi.namePrefix.trim() : "");
		String givenName = (indi.givenName != null ? indi.givenName.trim() : "");
		String surnames = (indi.surname != null ? indi.surname.trim() : "");
		String nameSuffix = (indi.nameSuffix != null ? indi.nameSuffix.trim() : "");
		
		NameAbbreviator.nameFit(namePrefix, givenName, surnames, nameSuffix, (float)maxNameWidth, boldFont);
		
		//get the name and it's width
		String nameString = NameAbbreviator.getName().trim();
		
		//make birthYear and deathYear
		String birthYear = "";
		String deathYear = "";
		if (indi.birth != null && indi.birth.yearString != null)
			birthYear = indi.birth.yearString.trim();
		if (indi.death != null && indi.death.yearString != null)
			deathYear = indi.death.yearString.trim();
				
		
		String birthPlace = "";
		if (indi.birth != null && indi.birth.place != null)
			birthPlace = PlaceAbbreviator.placeFit(indi.birth.place, (float)(workingWidth-indentation-birthLabelWidth), font);
		
		String deathPlace = "";
		if (indi.death != null && indi.death.place != null)
			deathPlace = PlaceAbbreviator.placeFit(indi.death.place, (float)(workingWidth-indentation-deathLabelWidth), font);
		
		//draw the name
		chart.addDrawCommand(new DrawCmdSetFont(boldFont,Color.black));
		chart.addDrawCommand(new DrawCmdMoveTo(startX,startY-boldFont.getSize2D()));
		chart.addDrawCommand(new DrawCmdText(nameString));
				
		//draw the date		
		chart.addDrawCommand(new DrawCmdSetFont(font,Color.black));
		chart.addDrawCommand(new DrawCmdMoveTo(startX+workingWidth-dateWidth, startY-font.getSize2D()-1));
		chart.addDrawCommand(new DrawCmdText("("));
		chart.addDrawCommand(new DrawCmdMoveTo(startX+workingWidth-dateWidth+leftParenWidth, startY-font.getSize2D()-1));
		chart.addDrawCommand(new DrawCmdText(birthYear));
		chart.addDrawCommand(new DrawCmdMoveTo(startX+workingWidth-dateWidth+leftParenWidth+yearWidth, startY-font.getSize2D()-1));
		chart.addDrawCommand(new DrawCmdText("-"));
		chart.addDrawCommand(new DrawCmdMoveTo(startX+workingWidth-dateWidth+leftParenWidth+yearWidth+hyphenWidth, startY-font.getSize2D()-1));
		chart.addDrawCommand(new DrawCmdText(deathYear));
		chart.addDrawCommand(new DrawCmdMoveTo(startX+workingWidth-dateWidth+leftParenWidth+yearWidth+hyphenWidth+yearWidth, startY-font.getSize2D()-1));
		chart.addDrawCommand(new DrawCmdText(")"));
		
		//if there is a dubLabel, draw it too
		chart.addDrawCommand(new DrawCmdSetFont(font, Color.darkGray));
		chart.addDrawCommand(new DrawCmdMoveTo (startX+workingWidth-dateWidth-dupLabelWidth-5, startY-font.getSize2D()-1));
		chart.addDrawCommand(new DrawCmdText(dupLabel));
		
		//draw the birth place
		chart.addDrawCommand(new DrawCmdMoveTo(startX+indentation, startY-font.getSize2D()-boldFont.getSize2D()-3));
		chart.addDrawCommand(new DrawCmdText("B: "));
		chart.addDrawCommand(new DrawCmdMoveTo(startX+indentation+birthLabelWidth, startY-font.getSize2D()-boldFont.getSize2D()-3));
		chart.addDrawCommand(new DrawCmdText(birthPlace));
		
		//draw the death place
		chart.addDrawCommand(new DrawCmdMoveTo(startX+indentation, startY-(font.getSize2D() * 2)-boldFont.getSize2D()-4));
		chart.addDrawCommand(new DrawCmdText("D: "));
		chart.addDrawCommand(new DrawCmdMoveTo(startX+indentation+deathLabelWidth, startY-(font.getSize2D() * 2)-boldFont.getSize2D()-4));
		chart.addDrawCommand(new DrawCmdText(deathPlace));
	}
	
	/*
	 * This draws four lines of information.  the information will look like this:
	 *                                                     *the [] are not shown in the box.
	 * -------------------------------------------
	 * |[abbrev name][tab][dupLabel][(date-date)]|
	 * |  B:[birth location]					 |
	 * |  M:[marriage location]					 |
	 * |  D:[death location]					 |
	 * -------------------------------------------
	 * 
	 * dupLabel is only there is there is a duplicate label.
	 * tab is only present when the name doesn't take up all the space
	 * 
	 * *because this is not as appealing, and because every other box does not display 
	 *  marital information, this will not be drawn in the boxes, unless necessary!
	 */
	private void draw4Lines() {
		//figure out how much space is needed for the duplicate label
		double dupLabelWidth = font.getStringBounds(dupLabel, frc).getWidth();
		
		//subtract dateWidth and dupLabel Width from workingWidth to determine
		//how much room we have to display the indi's name
		double maxNameWidth = workingWidth - dateWidth - dupLabelWidth;
		
		//using NameAbbreviator, shrink the name (if needed) to nameWidth
		String namePrefix = (indi.namePrefix != null ? indi.namePrefix.trim() : "");
		String givenName = (indi.givenName != null ? indi.givenName.trim() : "");
		String surnames = (indi.surname != null ? indi.surname.trim() : "");
		String nameSuffix = (indi.nameSuffix != null ? indi.nameSuffix.trim() : "");
		
		NameAbbreviator.nameFit(namePrefix, givenName, surnames, nameSuffix, (float)maxNameWidth, boldFont);
		
		//get the name and it's width
		String nameString = NameAbbreviator.getName().trim();
		
		//make birthYear and deathYear
		String birthYear = "";
		String deathYear = "";
		if (indi.birth != null && indi.birth.yearString != null)
			birthYear = indi.birth.yearString.trim();
		if (indi.death != null && indi.death.yearString != null)
			deathYear = indi.death.yearString.trim();
				
		
		String birthPlace = "";
		if (indi.birth != null && indi.birth.place != null)
			birthPlace = PlaceAbbreviator.placeFit(indi.birth.place, (float)(workingWidth-indentation-birthLabelWidth), font);

		String marriagePlace = "";
		if (indi.fams != null && indi.fams.get(0).marriage != null && indi.fams.get(0).marriage.place != null)
			marriagePlace = PlaceAbbreviator.placeFit(indi.fams.get(0).marriage.place.trim(), (float)(workingWidth-indentation-marriageLabelWidth), font);
		
		String deathPlace = "";
		if (indi.death != null && indi.death.place != null)
			deathPlace = PlaceAbbreviator.placeFit(indi.death.place, (float)(workingWidth-indentation-deathLabelWidth), font);
		
		//draw the name
		chart.addDrawCommand(new DrawCmdSetFont(boldFont,Color.black));
		chart.addDrawCommand(new DrawCmdMoveTo(startX,startY-boldFont.getSize2D()));
		chart.addDrawCommand(new DrawCmdText(nameString));
				
		//draw the date		
		chart.addDrawCommand(new DrawCmdSetFont(font,Color.black));
		chart.addDrawCommand(new DrawCmdMoveTo(startX+workingWidth-dateWidth, startY-font.getSize2D()-1));
		chart.addDrawCommand(new DrawCmdText("("));
		chart.addDrawCommand(new DrawCmdMoveTo(startX+workingWidth-dateWidth+leftParenWidth, startY-font.getSize2D()-1));
		chart.addDrawCommand(new DrawCmdText(birthYear));
		chart.addDrawCommand(new DrawCmdMoveTo(startX+workingWidth-dateWidth+leftParenWidth+yearWidth, startY-font.getSize2D()-1));
		chart.addDrawCommand(new DrawCmdText("-"));
		chart.addDrawCommand(new DrawCmdMoveTo(startX+workingWidth-dateWidth+leftParenWidth+yearWidth+hyphenWidth, startY-font.getSize2D()-1));
		chart.addDrawCommand(new DrawCmdText(deathYear));
		chart.addDrawCommand(new DrawCmdMoveTo(startX+workingWidth-dateWidth+leftParenWidth+yearWidth+hyphenWidth+yearWidth, startY-font.getSize2D()-1));
		chart.addDrawCommand(new DrawCmdText(")"));
		
		//if there is a dubLabel, draw it too
		chart.addDrawCommand(new DrawCmdSetFont(font, Color.darkGray));
		chart.addDrawCommand(new DrawCmdMoveTo (startX+workingWidth-dateWidth-dupLabelWidth-5, startY-font.getSize2D()-1));
		chart.addDrawCommand(new DrawCmdText(dupLabel));
		
		//draw the birth place
		chart.addDrawCommand(new DrawCmdMoveTo(startX+indentation, startY-font.getSize2D()-boldFont.getSize2D()-3));
		chart.addDrawCommand(new DrawCmdText("B: "));
		chart.addDrawCommand(new DrawCmdMoveTo(startX+indentation+birthLabelWidth, startY-font.getSize2D()-boldFont.getSize2D()-3));
		chart.addDrawCommand(new DrawCmdText(birthPlace));
		
		//draw the marriage place
		chart.addDrawCommand(new DrawCmdMoveTo(startX+indentation, startY-(font.getSize2D() * 2)-boldFont.getSize2D()-4));
		chart.addDrawCommand(new DrawCmdText("M: "));
		chart.addDrawCommand(new DrawCmdMoveTo(startX+indentation+marriageLabelWidth, startY-(font.getSize2D() * 2)-boldFont.getSize2D()-4));
		chart.addDrawCommand(new DrawCmdText(marriagePlace));
		
		//draw the death place
		chart.addDrawCommand(new DrawCmdMoveTo(startX+indentation, startY-(font.getSize2D() * 3)-boldFont.getSize2D()-5));
		chart.addDrawCommand(new DrawCmdText("D: "));
		chart.addDrawCommand(new DrawCmdMoveTo(startX+indentation+deathLabelWidth, startY-(font.getSize2D() * 3)-boldFont.getSize2D()-5));
		chart.addDrawCommand(new DrawCmdText(deathPlace));
	}
	
	/*
	 * This draws five lines of information.  the information will look like this:
	 *                                                     *the [] are not shown in the box.
	 * -------------------------------------------
	 * | [abbreviated name]						 |
	 * |   B:[birth date]						 |
	 * |     [birth location]					 |
	 * |   D:[death date]						 | 	
	 * |     [death location] 					 |
	 * -------------------------------------------
	 */
	private void draw5Lines() {
		//figure out how much space is needed for the duplicate label
		double dupLabelWidth = font.getStringBounds(dupLabel, frc).getWidth();
		
		//subtract dateWidth and dupLabel Width from workingWidth to determine
		//how much room we have to display the indi's name
		double maxNameWidth = workingWidth - dupLabelWidth;
		
		//using NameAbbreviator, shrink the name (if needed) to nameWidth
		String namePrefix = (indi.namePrefix != null ? indi.namePrefix.trim() : "");
		String givenName = (indi.givenName != null ? indi.givenName.trim() : "");
		String surnames = (indi.surname != null ? indi.surname.trim() : "");
		String nameSuffix = (indi.nameSuffix != null ? indi.nameSuffix.trim() : "");
		
		NameAbbreviator.nameFit(namePrefix, givenName, surnames, nameSuffix, (float)maxNameWidth, boldFont);
		
		//get the name and it's width
		String nameString = NameAbbreviator.getName().trim();
		
		String birthDate = "";
		if (indi.birth != null && indi.birth.date != null)
			birthDate = indi.birth.date.trim();
		
		String deathDate = "";
		if (indi.death != null && indi.death.date != null)
			deathDate = indi.death.date.trim();
		
		String birthPlace = "";
		if (indi.birth != null && indi.birth.place != null)
			birthPlace = PlaceAbbreviator.placeFit(indi.birth.place, (float)(workingWidth-indentation-birthLabelWidth), font);

		String deathPlace = "";
		if (indi.death != null && indi.death.place != null)
			deathPlace = PlaceAbbreviator.placeFit(indi.death.place, (float)(workingWidth-indentation-deathLabelWidth), font);
		
		//draw the name
		chart.addDrawCommand(new DrawCmdSetFont(boldFont,Color.black));
		chart.addDrawCommand(new DrawCmdMoveTo(startX,startY-boldFont.getSize2D()));
		chart.addDrawCommand(new DrawCmdText(nameString));
				
		//if there is a dubLabel, draw it too
		chart.addDrawCommand(new DrawCmdSetFont(font, Color.darkGray));
		chart.addDrawCommand(new DrawCmdMoveTo (startX+workingWidth-dupLabelWidth, startY-font.getSize2D()-1));
		chart.addDrawCommand(new DrawCmdText(dupLabel));
		
		//draw the birth date
		chart.addDrawCommand(new DrawCmdMoveTo(startX+indentation, startY-font.getSize2D()-boldFont.getSize2D()-3));
		chart.addDrawCommand(new DrawCmdText("B: "));
		chart.addDrawCommand(new DrawCmdMoveTo(startX+indentation+birthLabelWidth, startY-font.getSize2D()-boldFont.getSize2D()-3));
		chart.addDrawCommand(new DrawCmdText(birthDate));
		
		//draw the birth place
		chart.addDrawCommand(new DrawCmdMoveTo(startX+indentation+birthLabelWidth, startY-(font.getSize2D()*2)-boldFont.getSize2D()-4));
		chart.addDrawCommand(new DrawCmdText(birthPlace));
		
		//draw the death date
		chart.addDrawCommand(new DrawCmdMoveTo(startX+indentation, startY-(font.getSize2D() * 3)-boldFont.getSize2D()-5));
		if (!deathDate.equalsIgnoreCase("") || !deathPlace.equalsIgnoreCase(""))
			chart.addDrawCommand(new DrawCmdText("D: "));
		chart.addDrawCommand(new DrawCmdMoveTo(startX+indentation+deathLabelWidth, startY-(font.getSize2D() * 3)-boldFont.getSize2D()-5));
		chart.addDrawCommand(new DrawCmdText(deathDate));
		
		//draw the death place
		chart.addDrawCommand(new DrawCmdMoveTo(startX+indentation+deathLabelWidth, startY-(font.getSize2D() * 4)-boldFont.getSize2D()-6));
		chart.addDrawCommand(new DrawCmdText(deathPlace));
	}
}
