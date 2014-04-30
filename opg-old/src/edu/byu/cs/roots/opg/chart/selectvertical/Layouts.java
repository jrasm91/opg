package edu.byu.cs.roots.opg.chart.selectvertical;

import edu.byu.cs.roots.opg.chart.selectvertical.LineItem.LineItemType;
import edu.byu.cs.roots.opg.fonts.OpgFont;
import edu.byu.cs.roots.opg.model.Gender;
import edu.byu.cs.roots.opg.model.Individual;

public class Layouts{
	//Initializes LineLayouts
	public static Layouts linelayouts = new Layouts();
	//Any line you need.
	//{J. Doe (D:1920) <1>}
	LineLayout nameFirstInitialLastDeathYear;
	//{J. Doe (B:1837) <1>}
	LineLayout nameFirstInitialLastBirthYear;
	//{J. Doe (1837-1920) <1>}
	LineLayout nameFirstInitialLastBirthDeathYears;
	//{John Doe Jr. (D: 1920) <1>}
	LineLayout nameFirstLastSuffDeathYear;
	//{John Doe Jr. (B: 1837) <1>}
	LineLayout nameFirstLastSuffBirthYear;
	//{John Doe Jr. (1837-1920) <1>}
	LineLayout nameFirstLastSuffBirthDeathYears;
	//{John K. Doe Jr. (1837-1920) <1>}
	LineLayout nameFirstMidInitialLastSuffBirthDeathYears;
	//{John Kenneth Doe Jr. (1837-1920) <1>}
	LineLayout nameFirstMiddleLastSuffBirthDeathYears;
	//{[Abbreviated-name] <1>
	LineLayout abbreviatedNameLine;
	//{B:1 Jul 1837 D: 13 Aug 1920}
	LineLayout fullBirthDeathDates;
	//{B:1837 D:1920}
	LineLayout birthDeathYears;
	//{B:1 Jul 1837 New York}
	LineLayout birthDatePlaceFull;
	//{B:1 Jul 1837}
	LineLayout birthDateFull;
	//{B:1837}
	LineLayout birthYear;
	//{D:1 Jul 1837 New York}
	LineLayout deathDatePlaceFull;
	//{D:1 Jul 1837}
	LineLayout deathDateFull;
	//{D:1837}
	LineLayout deathYear;
	//{M:1860 B:New York}
	LineLayout marriageYearBirthPlace;   //TODO: Check MArriage dates and places for empty strings.
	//{M:14 Jan 1860}
	LineLayout fullMarriageDate;
	//{M:1860}
	LineLayout marriageYear;
	//{B: New York}
	LineLayout birthPlace;
	//{D: New Hampshire}
	LineLayout deathPlace;
	//{M: 13 Jan 1860 Salt Lake City
	LineLayout fullMarriageDatePlace;
	//{M: England}
	LineLayout marriagePlace;
	
	//initializes all lineLayouts. Pretty ugly looking.
	private Layouts(){
		LineLayout layout;
		layout = (nameFirstInitialLastDeathYear = new LineLayout());
		layout.items.add(new LineItem(LineItemType.FIRST_INITIAL));
//		layout.items.add(new LineItem(LineItemType.FIXED_STRING,". "));
		layout.items.add(new LineItem(LineItemType.SURNAME));
		layout.items.add(new LineItem(LineItemType.FIXED_STRING," (D: "));
		layout.items.add(new LineItem(LineItemType.DEATH_DATE_YEAR));
		layout.items.add(new LineItem(LineItemType.FIXED_STRING,")"));
		layout.items.add(new LineItem(LineItemType.DUPLICATE_LABEL));
		layout = (nameFirstInitialLastBirthYear = new LineLayout());
		layout.items.add(new LineItem(LineItemType.FIRST_INITIAL));
//		layout.items.add(new LineItem(LineItemType.FIXED_STRING,". "));
		layout.items.add(new LineItem(LineItemType.SURNAME));
		layout.items.add(new LineItem(LineItemType.FIXED_STRING," (B: "));
		layout.items.add(new LineItem(LineItemType.BIRTH_DATE_YEAR));
		layout.items.add(new LineItem(LineItemType.FIXED_STRING,")"));
		layout.items.add(new LineItem(LineItemType.DUPLICATE_LABEL));
		layout = (nameFirstInitialLastBirthDeathYears = new LineLayout());
		layout.items.add(new LineItem(LineItemType.FIRST_INITIAL));
//		layout.items.add(new LineItem(LineItemType.FIXED_STRING,". "));
		layout.items.add(new LineItem(LineItemType.SURNAME));
		layout.items.add(new LineItem(LineItemType.FIXED_STRING," ("));
		layout.items.add(new LineItem(LineItemType.BIRTH_DATE_YEAR));
		layout.items.add(new LineItem(LineItemType.FIXED_STRING," - "));
		layout.items.add(new LineItem(LineItemType.DEATH_DATE_YEAR));
		layout.items.add(new LineItem(LineItemType.FIXED_STRING,")"));
		layout.items.add(new LineItem(LineItemType.DUPLICATE_LABEL));
		layout = (nameFirstLastSuffDeathYear = new LineLayout());
		layout.items.add(new LineItem(LineItemType.FIRST_NAME));
///		layout.items.add(new LineItem(LineItemType.FIXED_STRING," "));
		layout.items.add(new LineItem(LineItemType.SURNAME));
///		layout.items.add(new LineItem(LineItemType.FIXED_STRING," "));
		layout.items.add(new LineItem(LineItemType.NAME_SUFFIX));
		layout.items.add(new LineItem(LineItemType.FIXED_STRING," (D: "));
		layout.items.add(new LineItem(LineItemType.DEATH_DATE_YEAR));
		layout.items.add(new LineItem(LineItemType.FIXED_STRING,")"));
		layout.items.add(new LineItem(LineItemType.DUPLICATE_LABEL));
		layout = (nameFirstLastSuffBirthYear = new LineLayout());
		layout.items.add(new LineItem(LineItemType.FIRST_NAME));
///		layout.items.add(new LineItem(LineItemType.FIXED_STRING,"x"));
		layout.items.add(new LineItem(LineItemType.SURNAME));
///		layout.items.add(new LineItem(LineItemType.FIXED_STRING,"x"));
		layout.items.add(new LineItem(LineItemType.NAME_SUFFIX));
		layout.items.add(new LineItem(LineItemType.FIXED_STRING," (B: "));
		layout.items.add(new LineItem(LineItemType.BIRTH_DATE_YEAR));
		layout.items.add(new LineItem(LineItemType.FIXED_STRING,")"));
		layout.items.add(new LineItem(LineItemType.DUPLICATE_LABEL));
		layout = (nameFirstLastSuffBirthDeathYears = new LineLayout());
		layout.items.add(new LineItem(LineItemType.FIRST_NAME));
///		layout.items.add(new LineItem(LineItemType.FIXED_STRING,"x"));
//		layout.items.add(new LineItem(LineItemType.SURNAME));
///		layout.items.add(new LineItem(LineItemType.FIXED_STRING,"x"));
//		layout.items.add(new LineItem(LineItemType.NAME_SUFFIX));
		layout.items.add(new LineItem(LineItemType.ABBREVIATED_NAME));
		layout.items.add(new LineItem(LineItemType.FIXED_STRING," ("));
		layout.items.add(new LineItem(LineItemType.BIRTH_DATE_YEAR));
		layout.items.add(new LineItem(LineItemType.FIXED_STRING," - "));
		layout.items.add(new LineItem(LineItemType.DEATH_DATE_YEAR));
		layout.items.add(new LineItem(LineItemType.FIXED_STRING,")"));
		layout.items.add(new LineItem(LineItemType.DUPLICATE_LABEL));
		layout = (nameFirstMidInitialLastSuffBirthDeathYears = new LineLayout());
		layout.items.add(new LineItem(LineItemType.FIRST_NAME));
///		layout.items.add(new LineItem(LineItemType.FIXED_STRING,"x"));
		layout.items.add(new LineItem(LineItemType.MIDDLE_INITIAL));
	//	layout.items.add(new LineItem(LineItemType.FIXED_STRING,"x"));
		layout.items.add(new LineItem(LineItemType.SURNAME));
///		layout.items.add(new LineItem(LineItemType.FIXED_STRING,"x"));
		layout.items.add(new LineItem(LineItemType.NAME_SUFFIX));
		layout.items.add(new LineItem(LineItemType.FIXED_STRING," ("));
		layout.items.add(new LineItem(LineItemType.BIRTH_DATE_YEAR));
		layout.items.add(new LineItem(LineItemType.FIXED_STRING," - "));
		layout.items.add(new LineItem(LineItemType.DEATH_DATE_YEAR));
		layout.items.add(new LineItem(LineItemType.FIXED_STRING,")"));
		layout.items.add(new LineItem(LineItemType.DUPLICATE_LABEL));
		layout = (nameFirstMiddleLastSuffBirthDeathYears = new LineLayout());
		layout.items.add(new LineItem(LineItemType.FIRST_NAME));
///		layout.items.add(new LineItem(LineItemType.FIXED_STRING,"x"));
		layout.items.add(new LineItem(LineItemType.MIDDLE_NAMES));
///		layout.items.add(new LineItem(LineItemType.FIXED_STRING,"x"));
		layout.items.add(new LineItem(LineItemType.SURNAME));
///		layout.items.add(new LineItem(LineItemType.FIXED_STRING,"x"));
		layout.items.add(new LineItem(LineItemType.NAME_SUFFIX));
		layout.items.add(new LineItem(LineItemType.FIXED_STRING," ("));
		layout.items.add(new LineItem(LineItemType.BIRTH_DATE_YEAR));
		layout.items.add(new LineItem(LineItemType.FIXED_STRING," - "));
		layout.items.add(new LineItem(LineItemType.DEATH_DATE_YEAR));
		layout.items.add(new LineItem(LineItemType.FIXED_STRING,")"));
		layout.items.add(new LineItem(LineItemType.DUPLICATE_LABEL));
		layout = (abbreviatedNameLine = new LineLayout());
		layout.items.add(new LineItem(LineItemType.ABBREVIATED_NAME));
		layout.items.add(new LineItem(LineItemType.FIXED_STRING," "));
		layout.items.add(new LineItem(LineItemType.DUPLICATE_LABEL));
		layout = (fullBirthDeathDates = new LineLayout());
		layout.items.add(new LineItem(LineItemType.FIXED_STRING,"B: "));
		layout.items.add(new LineItem(LineItemType.BIRTH_DATE_TEXT));
		layout.items.add(new LineItem(LineItemType.FIXED_STRING," D: "));
		layout.items.add(new LineItem(LineItemType.DEATH_DATE_TEXT));
		layout = (birthDeathYears = new LineLayout());
		layout.items.add(new LineItem(LineItemType.FIXED_STRING,"B: "));
		layout.items.add(new LineItem(LineItemType.BIRTH_DATE_YEAR));
		layout.items.add(new LineItem(LineItemType.FIXED_STRING," D: "));
		layout.items.add(new LineItem(LineItemType.DEATH_DATE_YEAR));
		layout = (birthDatePlaceFull = new LineLayout());
		layout.items.add(new LineItem(LineItemType.FIXED_STRING,"B: "));
		layout.items.add(new LineItem(LineItemType.BIRTH_DATE_TEXT));
		layout.items.add(new LineItem(LineItemType.FIXED_STRING," "));
		layout.items.add(new LineItem(LineItemType.ABBREVIATED_BIRTH_PLACE));
		layout = (birthDateFull = new LineLayout());
		layout.items.add(new LineItem(LineItemType.FIXED_STRING,"B: "));
		layout.items.add(new LineItem(LineItemType.BIRTH_DATE_TEXT));
		layout = (birthYear = new LineLayout());
		layout.items.add(new LineItem(LineItemType.FIXED_STRING,"B: "));
		layout.items.add(new LineItem(LineItemType.BIRTH_DATE_YEAR));
		layout = (deathDatePlaceFull = new LineLayout());
		layout.items.add(new LineItem(LineItemType.FIXED_STRING,"D: "));
		layout.items.add(new LineItem(LineItemType.DEATH_DATE_TEXT));
		layout.items.add(new LineItem(LineItemType.FIXED_STRING," "));
		layout.items.add(new LineItem(LineItemType.ABBREVIATED_DEATH_PLACE));
		layout = (deathDateFull = new LineLayout());
		layout.items.add(new LineItem(LineItemType.FIXED_STRING,"D: "));
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
		layout.items.add(new LineItem(LineItemType.FIXED_STRING,"M: "));
		layout.items.add(new LineItem(LineItemType.MARRIAGE_DATE_TEXT));
		layout.items.add(new LineItem(LineItemType.FIXED_STRING," "));
		layout.items.add(new LineItem(LineItemType.ABBREVIATED_MARRIAGE_PLACE));
		layout = (fullMarriageDate = new LineLayout());
		layout.items.add(new LineItem(LineItemType.FIXED_STRING,"M: "));
		layout.items.add(new LineItem(LineItemType.MARRIAGE_DATE_TEXT));
		layout = (marriageYear = new LineLayout());
		layout.items.add(new LineItem(LineItemType.FIXED_STRING,"M: "));
		layout.items.add(new LineItem(LineItemType.MARRIAGE_DATE_YEAR));
		layout = (deathPlace = new LineLayout());
		layout.items.add(new LineItem(LineItemType.FIXED_STRING,"D: "));
		layout.items.add(new LineItem(LineItemType.ABBREVIATED_DEATH_PLACE));
		layout = (birthPlace = new LineLayout());
		layout.items.add(new LineItem(LineItemType.FIXED_STRING,"B: "));
		layout.items.add(new LineItem(LineItemType.ABBREVIATED_BIRTH_PLACE));
		layout = (marriagePlace = new LineLayout());
		layout.items.add(new LineItem(LineItemType.FIXED_STRING,"M: "));
		layout.items.add(new LineItem(LineItemType.ABBREVIATED_MARRIAGE_PLACE));
		
	
	}
}
//All layouts least desirable to most desirable.


//{[Name FIt to space] <1>}
class OneLineAbbrName extends BoxLayout{
	public OneLineAbbrName(){
		lines.add(Layouts.linelayouts.abbreviatedNameLine);
	}
	public boolean canFit(Individual indi, double width, double height, VerticalChartOptions opgFont, double fontSize, String dupLabel ){
		return true;
	}
	
}

//{J. Doe (D:1920) <1>}
class OneLineAbbrFirstDeathDate extends BoxLayout{
	public OneLineAbbrFirstDeathDate(){
		lines.add(Layouts.linelayouts.nameFirstInitialLastDeathYear);
	}
	public boolean canFit(Individual indi, double width, double height, OpgFont opgFont, double fontSize, String dupLabel ){
		if(!indi.hasDeathYear())
			return false;
		return super.canFit(indi, width, height, opgFont, fontSize, dupLabel);
	}
}
//{J. Doe (B:1837) <1>}
class OneLineAbbrFirstBirthDate extends BoxLayout{
	public OneLineAbbrFirstBirthDate(){
		lines.add(Layouts.linelayouts.nameFirstInitialLastBirthYear);
	}
	public boolean canFit(Individual indi, double width, double height, OpgFont opgFont, double fontSize, String dupLabel ){
		if(!indi.hasBirthYear())
			return false;
		return super.canFit(indi, width, height, opgFont, fontSize, dupLabel);
	}
}
//{J. Doe (1837-1920) <1>}
class OneLineAbbrFirstAllYears extends BoxLayout{
	public OneLineAbbrFirstAllYears(){
		lines.add(Layouts.linelayouts.nameFirstInitialLastBirthDeathYears);
	}
	public boolean canFit(Individual indi, double width, double height, OpgFont opgFont, double fontSize, String dupLabel ){
		if(!indi.hasBirthYear() || !indi.hasDeathYear())
			return false;
		return super.canFit(indi, width, height, opgFont, fontSize, dupLabel);
	}
}
//{John Doe Jr. (D: 1920) <1>}
class OneLineFullFirstLastDeathYear extends BoxLayout{
	public OneLineFullFirstLastDeathYear(){
		lines.add(Layouts.linelayouts.nameFirstLastSuffDeathYear);
	}
	public boolean canFit(Individual indi, double width, double height, OpgFont opgFont, double fontSize, String dupLabel ){
		if(!indi.hasDeathYear())
			return false;
		return super.canFit(indi, width, height, opgFont, fontSize, dupLabel);
	}
}
//{John Doe Jr. (B: 1837) <1>}
class OneLineFullFirstLastBirthYear extends BoxLayout{
	public OneLineFullFirstLastBirthYear(){
		lines.add(Layouts.linelayouts.nameFirstLastSuffBirthYear);
	}
	public boolean canFit(Individual indi, double width, double height, OpgFont opgFont, double fontSize, String dupLabel ){
		if(!indi.hasBirthYear() )
			return false;
		return super.canFit(indi, width, height, opgFont, fontSize, dupLabel);
	}
}



//{John Doe Jr. (1837-1920) <1>}
class OneLineFullFirstLastAllYears extends BoxLayout{
	public OneLineFullFirstLastAllYears(){
		lines.add(Layouts.linelayouts.nameFirstLastSuffBirthDeathYears);
	}
	public boolean canFit(Individual indi, double width, double height, OpgFont opgFont, double fontSize, String dupLabel ){
		if(!indi.hasBirthYear() || !indi.hasDeathYear())
			return false;
		return super.canFit(indi, width, height, opgFont, fontSize, dupLabel);
	}
}




//{John K. Doe Jr. (1837-1920) <1>}
class OneLineMidInitAllYears extends BoxLayout{
	public OneLineMidInitAllYears(){
		lines.add(Layouts.linelayouts.nameFirstMidInitialLastSuffBirthDeathYears);
	}
	public boolean canFit(Individual indi, double width, double height, OpgFont opgFont, double fontSize, String dupLabel ){
		if(!indi.hasBirthYear() || !indi.hasDeathYear())
			return false;
		return super.canFit(indi, width, height, opgFont, fontSize, dupLabel);
	}
}


//{John Kenneth Doe Jr. (1837-1920) <1>}
class OneLineFullNameAllYears extends BoxLayout{
	public OneLineFullNameAllYears(){
		lines.add(Layouts.linelayouts.nameFirstMiddleLastSuffBirthDeathYears);
	}
	public boolean canFit(Individual indi, double width, double height, OpgFont opgFont, double fontSize, String dupLabel ){
		if(!indi.hasBirthYear() && !indi.hasDeathYear())
			return false;
		return super.canFit(indi, width, height, opgFont, fontSize, dupLabel);
	}
}
/*  
[Abbreviated Name] <1>
 B:1 Jul 1837 D:13 Aug 1920 
 */
class TwoLineFullBirthDeathDates extends BoxLayout{
	public TwoLineFullBirthDeathDates(){
		lines.add(Layouts.linelayouts.abbreviatedNameLine);
		lines.add(Layouts.linelayouts.fullBirthDeathDates);
	}
	public boolean canFit(Individual indi, double width, double height, OpgFont opgFont, double fontSize, String dupLabel ){
		if(!indi.hasBirthDate() || !indi.hasDeathDate())
			return false;
		return super.canFit(indi, width, height, opgFont, fontSize, dupLabel);
	}
}
/*
[Abbreviated Name] <1>
B:1837 D:1920 
*/
class TwoLineBirthDeathYears extends BoxLayout{
	public TwoLineBirthDeathYears(){
		lines.add(Layouts.linelayouts.abbreviatedNameLine);
		lines.add(Layouts.linelayouts.birthDeathYears);
	}
	public boolean canFit(Individual indi, double width, double height, OpgFont opgFont, double fontSize, String dupLabel ){
		if(!indi.hasBirthYear() || !indi.hasDeathYear())
			return false;
		return super.canFit(indi, width, height, opgFont, fontSize, dupLabel);
	}
}
/*
 [Name] <1>
 B:1 Jul 1837 [Abbrevated Place]
 */
class TwoLineBirthDatePlaceFull extends BoxLayout{
	public TwoLineBirthDatePlaceFull(){
		lines.add(Layouts.linelayouts.abbreviatedNameLine);
		lines.add(Layouts.linelayouts.birthDatePlaceFull);
	}
	public boolean canFit(Individual indi, double width, double height, OpgFont opgFont, double fontSize, String dupLabel ){
		if(!indi.hasBirthDate() || !indi.hasBirthPlace())
			return false;
		return super.canFit(indi, width, height, opgFont, fontSize, dupLabel);
	}
}
/*
[Name] <1>
B:1 Jul 1837 
*/
class TwoLineBirthDateFull extends BoxLayout{
	public TwoLineBirthDateFull(){
		lines.add(Layouts.linelayouts.abbreviatedNameLine);
		lines.add(Layouts.linelayouts.birthDateFull);
	}
	public boolean canFit(Individual indi, double width, double height, OpgFont opgFont, double fontSize, String dupLabel ){
		if(!indi.hasBirthDate()){
			if(indi.surname.compareToIgnoreCase("Peterson")==0  )
				System.out.println("No BirthDate");
			return false;
		}
		return super.canFit(indi, width, height, opgFont, fontSize, dupLabel);
	}
}
/*
[Name] <1>
B:1837
*/
class TwoLineBirthYear extends BoxLayout{
	public TwoLineBirthYear(){
		lines.add(Layouts.linelayouts.abbreviatedNameLine);
		lines.add(Layouts.linelayouts.birthYear);
	}
	public boolean canFit(Individual indi, double width, double height, OpgFont opgFont, double fontSize, String dupLabel ){
		if(!indi.hasBirthDate())
			return false;
		return super.canFit(indi, width, height, opgFont, fontSize, dupLabel);
	}
}
/*
[Name] <1>
D:1 Jul 1837 [Abbrevated Place]
*/
class TwoLineDeathDatePlaceFull extends BoxLayout{
	public TwoLineDeathDatePlaceFull(){
		lines.add(Layouts.linelayouts.abbreviatedNameLine);
		lines.add(Layouts.linelayouts.deathDatePlaceFull);
	}
	public boolean canFit(Individual indi, double width, double height, OpgFont opgFont, double fontSize, String dupLabel ){
		if(!indi.hasDeathDate() || !indi.hasDeathPlace())
			return false;
		return super.canFit(indi, width, height, opgFont, fontSize, dupLabel);
	}
}
/*
[Name] <1>
B:1 Jul 1837 
*/
class TwoLineDeathDateFull extends BoxLayout{
	public TwoLineDeathDateFull(){
		lines.add(Layouts.linelayouts.abbreviatedNameLine);
		lines.add(Layouts.linelayouts.deathDateFull);
	}
	public boolean canFit(Individual indi, double width, double height, OpgFont opgFont, double fontSize, String dupLabel ){
		if(!indi.hasDeathDate())
			return false;
		return super.canFit(indi, width, height, opgFont, fontSize, dupLabel);
	}
}
/*
[Name] <1>
B:1837
*/
class TwoLineDeathYear extends BoxLayout{
	public TwoLineDeathYear(){
		lines.add(Layouts.linelayouts.abbreviatedNameLine);
		lines.add(Layouts.linelayouts.deathYear);
	}
	public boolean canFit(Individual indi, double width, double height, OpgFont opgFont, double fontSize, String dupLabel ){
		if(!indi.hasDeathDate())
			return false;
		return super.canFit(indi, width, height, opgFont, fontSize, dupLabel);
	}
}
/*
 [Name] <1>
 B:1 Jul 1837 D:13 Aug 1920
 M:1860 B:[New Yourk]
 */
class ThreeLine1 extends BoxLayout{
	public ThreeLine1(){
		lines.add(Layouts.linelayouts.abbreviatedNameLine);
		lines.add(Layouts.linelayouts.fullBirthDeathDates);
		lines.add(Layouts.linelayouts.marriageYearBirthPlace);
	}
	public boolean canFit(Individual indi, double width, double height, OpgFont opgFont, double fontSize, String dupLabel ){
		
		if(!indi.hasDeathDate() || !indi.hasDeathDate() || !indi.hasBirthPlace() ||
				indi.gender != Gender.MALE || indi.fams.size() == 0 || indi.fams.get(0).marriage == null){
			return false;
		}
		return super.canFit(indi, width, height, opgFont, fontSize, dupLabel);
	}
}
/*
[Name] <1>
B:1837 D:1920
M: 14 Jan 1860
*/
class ThreeLine2 extends BoxLayout{
	public ThreeLine2(){
		lines.add(Layouts.linelayouts.abbreviatedNameLine);
		lines.add(Layouts.linelayouts.birthDeathYears);
		lines.add(Layouts.linelayouts.fullMarriageDate);
	}
	public boolean canFit(Individual indi, double width, double height, OpgFont opgFont, double fontSize, String dupLabel ){
		
		if(!indi.hasDeathDate() || !indi.hasBirthDate() || 
				indi.gender != Gender.MALE || indi.fams.size() == 0 || indi.fams.get(0).marriage == null){
			return false;
		}
		return super.canFit(indi, width, height, opgFont, fontSize, dupLabel);
	}
}
/*
[Name] <1>
B:1837 D:1920
M: 1860
*/
class ThreeLine3 extends BoxLayout{
	public ThreeLine3(){
		lines.add(Layouts.linelayouts.abbreviatedNameLine);
		lines.add(Layouts.linelayouts.birthDeathYears);
		lines.add(Layouts.linelayouts.marriageYear);
	}
	public boolean canFit(Individual indi, double width, double height, OpgFont opgFont, double fontSize, String dupLabel ){
		
		if(!indi.hasDeathDate() || !indi.hasBirthDate() || 
				indi.gender != Gender.MALE || indi.fams.size() == 0 || indi.fams.get(0).marriage == null){
			return false;
		}
		return super.canFit(indi, width, height, opgFont, fontSize, dupLabel);
	}
}
/*
[Name] <1>
B: 1 Jul 1837
M: 14 Jan 1860
*/
class ThreeLine4 extends BoxLayout{
	public ThreeLine4(){
		lines.add(Layouts.linelayouts.abbreviatedNameLine);
		lines.add(Layouts.linelayouts.birthDateFull);
		lines.add(Layouts.linelayouts.fullMarriageDate);
	}
	public boolean canFit(Individual indi, double width, double height, OpgFont opgFont, double fontSize, String dupLabel ){
		
		if(!indi.hasBirthDate()  ||
				indi.gender != Gender.MALE || indi.fams.size() == 0 || indi.fams.get(0).marriage == null){
			return false;
		}
		return super.canFit(indi, width, height, opgFont, fontSize, dupLabel);
	}
}
/*
[Name] <1>
D: 13 Aug 1920
M: 14 Jan 1860
*/
class ThreeLine5 extends BoxLayout{
	public ThreeLine5(){
		lines.add(Layouts.linelayouts.abbreviatedNameLine);
		lines.add(Layouts.linelayouts.deathDateFull);
		lines.add(Layouts.linelayouts.fullMarriageDate);
	}
	public boolean canFit(Individual indi, double width, double height, OpgFont opgFont, double fontSize, String dupLabel ){
		
		if(!indi.hasDeathDate() ||
				indi.gender != Gender.MALE || indi.fams.size() == 0 || indi.fams.get(0).marriage == null){
			return false;
		}
		return super.canFit(indi, width, height, opgFont, fontSize, dupLabel);
	}
}
/*
[Name] <1>
B: 1837
M: 1860
*/
class ThreeLine6 extends BoxLayout{
	public ThreeLine6(){
		lines.add(Layouts.linelayouts.abbreviatedNameLine);
		lines.add(Layouts.linelayouts.birthYear);
		lines.add(Layouts.linelayouts.marriageYear);
	}
	public boolean canFit(Individual indi, double width, double height, OpgFont opgFont, double fontSize, String dupLabel ){
		
		if(!indi.hasBirthDate() ||
				indi.gender != Gender.MALE || indi.fams.size() == 0 || indi.fams.get(0).marriage == null){
			return false;
		}
		return super.canFit(indi, width, height, opgFont, fontSize, dupLabel);
	}
}
/*
[Name] <1>
D: 1920
M: 1860
*/
class ThreeLine7 extends BoxLayout{
	public ThreeLine7(){
		lines.add(Layouts.linelayouts.abbreviatedNameLine);
		lines.add(Layouts.linelayouts.deathYear);
		lines.add(Layouts.linelayouts.marriageYear);
	}
	public boolean canFit(Individual indi, double width, double height, OpgFont opgFont, double fontSize, String dupLabel ){
		
		if(!indi.hasDeathDate() ||
				indi.gender != Gender.MALE || indi.fams.size() == 0 || indi.fams.get(0).marriage == null){
			return false;
		}
		return super.canFit(indi, width, height, opgFont, fontSize, dupLabel);
	}
}
/*
[Name] <1>
B: 1 Jul 1837 New York
D: 13 Aug 1920 New Hampshire
*/
class ThreeLine8 extends BoxLayout{
	public ThreeLine8(){
		lines.add(Layouts.linelayouts.abbreviatedNameLine);
		lines.add(Layouts.linelayouts.birthDatePlaceFull);
		lines.add(Layouts.linelayouts.deathDatePlaceFull);
	}
	public boolean canFit(Individual indi, double width, double height, OpgFont opgFont, double fontSize, String dupLabel ){
		
		if(!indi.hasDeathDate() || ! indi.hasDeathPlace() || !indi.hasBirthDate() || !indi.hasBirthPlace())
			return false;
		return super.canFit(indi, width, height, opgFont, fontSize, dupLabel);
	}
}
/*
[Name] <1>
B: 1837 D: 1920
B: New York
*/
class ThreeLine9 extends BoxLayout{
	public ThreeLine9(){
		lines.add(Layouts.linelayouts.abbreviatedNameLine);
		lines.add(Layouts.linelayouts.birthDeathYears);
		lines.add(Layouts.linelayouts.birthPlace);
	}
	public boolean canFit(Individual indi, double width, double height, OpgFont opgFont, double fontSize, String dupLabel ){
		
		if(!indi.hasDeathDate() || !indi.hasBirthDate() || !indi.hasBirthPlace())
			return false;
		return super.canFit(indi, width, height, opgFont, fontSize, dupLabel);
	}
}
/*
[Name] <1>
B: 1837 D: 1920
B: New York
*/
class ThreeLine10 extends BoxLayout{
	public ThreeLine10(){
		lines.add(Layouts.linelayouts.abbreviatedNameLine);
		lines.add(Layouts.linelayouts.birthDeathYears);
		lines.add(Layouts.linelayouts.birthPlace);
	}
	public boolean canFit(Individual indi, double width, double height, OpgFont opgFont, double fontSize, String dupLabel ){
		
		if(!indi.hasDeathDate() || !indi.hasBirthDate() || !indi.hasBirthPlace())
			return false;
		return super.canFit(indi, width, height, opgFont, fontSize, dupLabel);
	}
}
/*
[Name] <1>
B: 1837 D: 1920
D: New Jersey
*/
class ThreeLine11 extends BoxLayout{
	public ThreeLine11(){
		lines.add(Layouts.linelayouts.abbreviatedNameLine);
		lines.add(Layouts.linelayouts.birthDeathYears);
		lines.add(Layouts.linelayouts.deathPlace);
	}
	public boolean canFit(Individual indi, double width, double height, OpgFont opgFont, double fontSize, String dupLabel ){
		
		if(!indi.hasDeathDate() || !indi.hasBirthDate() || !indi.hasDeathPlace())
			return false;
		return super.canFit(indi, width, height, opgFont, fontSize, dupLabel);
	}
}
/*
[Name] <1>
B: 1 Jul 1837
B: New York
*/
class ThreeLine12 extends BoxLayout{
	public ThreeLine12(){
		lines.add(Layouts.linelayouts.abbreviatedNameLine);
		lines.add(Layouts.linelayouts.birthDateFull);
		lines.add(Layouts.linelayouts.birthPlace);
	}
	public boolean canFit(Individual indi, double width, double height, OpgFont opgFont, double fontSize, String dupLabel ){
		
		if(!indi.hasBirthDate() || !indi.hasBirthPlace())
			return false;
		return super.canFit(indi, width, height, opgFont, fontSize, dupLabel);
	}
}
/*
[Name] <1>
D: 1 Jul 1837
D: New York
*/
class ThreeLine13 extends BoxLayout{
	public ThreeLine13(){
		lines.add(Layouts.linelayouts.abbreviatedNameLine);
		lines.add(Layouts.linelayouts.deathDateFull);
		lines.add(Layouts.linelayouts.deathPlace);
	}
	public boolean canFit(Individual indi, double width, double height, OpgFont opgFont, double fontSize, String dupLabel ){
		
		if(!indi.hasDeathDate() || !indi.hasDeathPlace())
			return false;
		return super.canFit(indi, width, height, opgFont, fontSize, dupLabel);
	}
}
/*
[Name] <1>
B: 1 Jul 1837
B: New York
*/
class ThreeLine14 extends BoxLayout{
	public ThreeLine14(){
		lines.add(Layouts.linelayouts.abbreviatedNameLine);
		lines.add(Layouts.linelayouts.birthYear);
		lines.add(Layouts.linelayouts.birthPlace);
	}
	public boolean canFit(Individual indi, double width, double height, OpgFont opgFont, double fontSize, String dupLabel ){
		
		if(!indi.hasBirthDate() || !indi.hasBirthPlace())
			return false;
		return super.canFit(indi, width, height, opgFont, fontSize, dupLabel);
	}
}
/*
[Name] <1>
D: 1 Jul 1837
D: New York
*/
class ThreeLine15 extends BoxLayout{
	public ThreeLine15(){
		lines.add(Layouts.linelayouts.abbreviatedNameLine);
		lines.add(Layouts.linelayouts.deathYear);
		lines.add(Layouts.linelayouts.deathPlace);
	}
	public boolean canFit(Individual indi, double width, double height, OpgFont opgFont, double fontSize, String dupLabel ){
		
		if(!indi.hasDeathDate() || !indi.hasDeathPlace())
			return false;
		return super.canFit(indi, width, height, opgFont, fontSize, dupLabel);
	}
}
/*
[NAME] <1>
B: 1 Jul 1837 New York
D: 13 Aug 1920 New Jersey
M: 23 Jan 1860 Wherever
 */
class FourLines1 extends BoxLayout{
	public FourLines1(){
		lines.add(Layouts.linelayouts.abbreviatedNameLine);
		lines.add(Layouts.linelayouts.birthDatePlaceFull);
		lines.add(Layouts.linelayouts.deathDatePlaceFull);
		lines.add(Layouts.linelayouts.fullMarriageDatePlace);
	}
	public boolean canFit(Individual indi, double width, double height, OpgFont opgFont, double fontSize, String dupLabel ){
		if(!indi.hasBirthDate() || !indi.hasDeathDate() ||
				indi.gender != Gender.MALE || indi.fams.size() == 0 || indi.fams.get(0).marriage == null){
			return false;
		}
		return super.canFit(indi, width, height, opgFont, fontSize, dupLabel);
	}
}
/*
[NAME] <1>
B: 1 Jul 1837 
B: New York
M: 1860 Wherever
 */
class FourLines2 extends BoxLayout{
	public FourLines2(){
		lines.add(Layouts.linelayouts.abbreviatedNameLine);
		lines.add(Layouts.linelayouts.birthDateFull);
		lines.add(Layouts.linelayouts.birthPlace);
		lines.add(Layouts.linelayouts.fullMarriageDatePlace);
	}
	public boolean canFit(Individual indi, double width, double height, OpgFont opgFont, double fontSize, String dupLabel ){
		if(!indi.hasBirthDate() || !indi.hasBirthPlace() ||
				indi.gender != Gender.MALE || indi.fams.size() == 0 || indi.fams.get(0).marriage == null){
			return false;
		}
		return super.canFit(indi, width, height, opgFont, fontSize, dupLabel);
	}
}
/*
[NAME] <1>
B: 1 Jul 1837
B: New York
D: 13 Aug 1920 New Jersey
 */
class FourLines3 extends BoxLayout{
	public FourLines3(){
		lines.add(Layouts.linelayouts.abbreviatedNameLine);
		lines.add(Layouts.linelayouts.birthDateFull);
		lines.add(Layouts.linelayouts.birthPlace);
		lines.add(Layouts.linelayouts.deathDatePlaceFull);
	}
	public boolean canFit(Individual indi, double width, double height, OpgFont opgFont, double fontSize, String dupLabel ){
		if(!indi.hasBirthDate() || !indi.hasBirthPlace() || !indi.hasDeathDate()){
			return false;
		}
		return super.canFit(indi, width, height, opgFont, fontSize, dupLabel);
	}
}
/*
[NAME] <1>
B: 1 Jul 1837
B: New York
D: 13 Aug 1920
 */
class FourLines4 extends BoxLayout{
	public FourLines4(){
		lines.add(Layouts.linelayouts.abbreviatedNameLine);
		lines.add(Layouts.linelayouts.birthYear);
		lines.add(Layouts.linelayouts.birthPlace);
		lines.add(Layouts.linelayouts.deathYear);
	}
	public boolean canFit(Individual indi, double width, double height, OpgFont opgFont, double fontSize, String dupLabel ){
		if(!indi.hasBirthDate() || !indi.hasBirthPlace() || !indi.hasDeathDate()){
			return false;
		}
		return super.canFit(indi, width, height, opgFont, fontSize, dupLabel);
	}
}

/*
[NAME] <1>
D: 1 Jul 1837
D: New York
M: 13 Aug 1920
 */
class FourLines5 extends BoxLayout{
	public FourLines5(){
		lines.add(Layouts.linelayouts.abbreviatedNameLine);
		lines.add(Layouts.linelayouts.deathYear);
		lines.add(Layouts.linelayouts.deathPlace);
		lines.add(Layouts.linelayouts.fullMarriageDatePlace);
	}
	public boolean canFit(Individual indi, double width, double height, OpgFont opgFont, double fontSize, String dupLabel ){
		if(!indi.hasDeathDate() || !indi.hasDeathPlace() ||
				indi.gender != Gender.MALE || indi.fams.size() == 0 || indi.fams.get(0).marriage == null){
			return false;
		}
		return super.canFit(indi, width, height, opgFont, fontSize, dupLabel);
	}
}


/*
[NAME] <1>
B: 1 Jul 1837
B: New York
D: 13 Aug 1920 UT
M: 23 Jun 1860 ENG
 */
class FiveLines1 extends BoxLayout{
	public FiveLines1(){
		lines.add(Layouts.linelayouts.abbreviatedNameLine);
		lines.add(Layouts.linelayouts.birthDateFull);
		lines.add(Layouts.linelayouts.birthPlace);
		lines.add(Layouts.linelayouts.deathDatePlaceFull);
		lines.add(Layouts.linelayouts.fullMarriageDatePlace);
	}
	public boolean canFit(Individual indi, double width, double height, OpgFont opgFont, double fontSize, String dupLabel ){
		if(!indi.hasBirthDate() || !indi.hasBirthPlace() || !indi.hasDeathDate() ||
				indi.gender != Gender.MALE || indi.fams.size() == 0 || indi.fams.get(0).marriage == null){
			return false;
		}
		return super.canFit(indi, width, height, opgFont, fontSize, dupLabel);
	}
}
/*
[NAME] <1>
B: 1 Jul 1837
B: New York
M: 23 Jun 1860
M: Oxford, England
 */
class FiveLines2 extends BoxLayout{
	public FiveLines2(){
		lines.add(Layouts.linelayouts.abbreviatedNameLine);
		lines.add(Layouts.linelayouts.birthDateFull);
		lines.add(Layouts.linelayouts.birthPlace);
		lines.add(Layouts.linelayouts.fullMarriageDate);
		lines.add(Layouts.linelayouts.marriagePlace);
	}
	public boolean canFit(Individual indi, double width, double height, OpgFont opgFont, double fontSize, String dupLabel ){
		if(!indi.hasBirthDate() || !indi.hasBirthPlace() || !indi.hasMarriagePlace() ||
				indi.gender != Gender.MALE || indi.fams.size() == 0 || indi.fams.get(0).marriage == null){
			return false;
		}
		return super.canFit(indi, width, height, opgFont, fontSize, dupLabel);
	}
}
/*
[NAME] <1>
B: 1 Jul 1837
B: New York
D: date
D: place
 */
class FiveLines3 extends BoxLayout{
	public FiveLines3(){
		lines.add(Layouts.linelayouts.abbreviatedNameLine);
		lines.add(Layouts.linelayouts.birthDateFull);
		lines.add(Layouts.linelayouts.birthPlace);
		lines.add(Layouts.linelayouts.deathDateFull);
		lines.add(Layouts.linelayouts.deathPlace);
	}
	public boolean canFit(Individual indi, double width, double height, OpgFont opgFont, double fontSize, String dupLabel ){
		if(!indi.hasBirthDate() || !indi.hasBirthPlace() || !indi.hasDeathDate() || !indi.hasDeathPlace()){
			return false;
		}
		return super.canFit(indi, width, height, opgFont, fontSize, dupLabel);
	}
}



