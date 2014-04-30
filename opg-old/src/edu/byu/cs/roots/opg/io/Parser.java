package edu.byu.cs.roots.opg.io;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.StringTokenizer;

import edu.byu.cs.roots.opg.model.EventClass;
import edu.byu.cs.roots.opg.model.FamilyClass;
import edu.byu.cs.roots.opg.model.IndividualRecord;
import edu.byu.cs.roots.opg.util.PlaceAbbr;

//  1 BAPL  for baptism living  followed by 2 date,  2 place  (EVENT)


public class Parser{
	static BufferedReader br;
	public static HashMap<String, IndividualRecord>individualmap;
	public static HashMap<String, FamilyClass> familymap;
	static EventClass nullEvent = new EventClass("","","");
	static EventClass nullBapl = new EventClass("-1  ","","");
	static HashMap<String, PlaceAbbr> abbrMap; //list of abbreviations
	static Calendar calendar = new GregorianCalendar(); ;//Calendar object used in parsing of dates

	public Parser(String filename)throws IOException{
		familymap = new HashMap<String, FamilyClass>();
		individualmap = new HashMap<String, IndividualRecord>();
		setUpAbbreviationMaps();

		if (filename == null)
			throw new IllegalArgumentException ("filename is null");

		try {
			br = new BufferedReader(new FileReader(filename));
		}
		catch (IOException e) {
			System.out.println("Can't open gedcom file: " + filename);
			return ;
		}
	}

	/* ===========================================================================================================*/
	public static void  gedparse(Parser p)throws IOException{
		/* ===========================================================================================================*/
		IndividualRecord indi;
		FamilyClass fam;
		//String line;
		//int nb=0, tb=0, lb=0, ni = 0;
		//int nb1=0, tb1=0, lb1=0;

		// This parser assumes that the gedcom file is organized as follows:
		// Header material
		// all individuals
		// all families
		// ending material
		// All gedcom files that I have observed follow this order, although the gedcom standard does not require it

		while((indi = Parser.nextIndi()) != null){  // Read in all individuals
			Parser.individualmap.put(indi.id, indi);
			//		System.out.println("Indi:" + indi.givenName + "|" + indi.middleName + "|" + indi.lastName+ "|" + indi.id);
			//System.out.println(indi.givenName + "|" + indi.lastName+ "|" + indi.bapl.yearInt()+ "|" + indi.death.yearInt());
			//ni++;
		}
		//System.out.println("nb = " + nb + "; tb = " + tb + "; lb = " + lb + "; ni= " + ni);
		//System.out.println("nb1 = " + nb1 + "; tb1 = " + tb1 + "; lb1 = " + lb1 + "; ni= " + ni);

		while((fam = Parser.nextFam()) != null){  // Read in all families
			if( (((fam.husband == null) || (fam.wife == null)) && fam.children.size() == 0) )
				System.out.println("Family with one spouse and no kids: " + fam.id); 
			Parser.familymap.put(fam.id, fam); //if(fam.id.compareTo("F12") == 0)System.out.println("Putting F12");
			//		System.out.println("Putting " + fam.id);
			for(int i=0; i < fam.children.size(); i++){   // Link children to parents
				indi = fam.children.get(i);
				if(indi == null) System.out.println("null indi " + fam.id);
				else{
					indi.father = fam.husband;
					//if(indi.father != null)indi.father.nChildren++;
					indi.mother = fam.wife;
					//if(indi.mother != null)indi.mother.nChildren++;
				}
			}
			//}/
		} // end while(fam ...
		//System.out.println("Out of nextFam");
		p.close();
	} // end gedParse

	/* ===========================================================================================================*/
	static IndividualRecord nextIndi() throws IOException{  // Returns the next individual in the gedcom file. Returns null if there are no more indis.
		/* ===========================================================================================================*/
		String line, type, code;
		br.mark(2000);
		while((line = br.readLine()) != null) {
			StringTokenizer t = new StringTokenizer(line, " @");
			code = t.nextToken();
			if(code.compareTo("0") == 0){
				String tmpid = t.nextToken();
				if(t.countTokens() > 0){
					type = t.nextToken();
					if(type.compareTo("INDI")==0)
						return parseIndi(tmpid);
					if(type.compareTo("FAM")==0){
						br.reset();
						return null;  // no more indis in this file
					}
				}
			}  // end "0"
		} // end while
		return null;
	}

	/* ===========================================================================================================*/
	static IndividualRecord parseIndi(String tmpid) throws IOException{
		/* ===========================================================================================================*/
		IndividualRecord indi = new IndividualRecord(tmpid);
		String line, code, type;
		br.mark(2000);
		while((line = br.readLine()) != null) {
			StringTokenizer t = new StringTokenizer(line, " @");
			code = t.nextToken();
			if(code.compareTo("0") == 0){
				br.reset();
				return indi;
			}  // end "0"
			if(code.compareTo("1") == 0){       // System.out.println(line);
				if(t.hasMoreTokens()){
					type = t.nextToken();
					if(type.compareTo("NAME")==0){
						if(t.hasMoreTokens()){
							indi.givenName = t.nextToken();
							if(indi.givenName.charAt(0) == '/')indi.givenName = "";
						}
						indi.middleName = getMiddleNames(line);
						t = new StringTokenizer(line, "/");
						if(t.hasMoreTokens()){
							t.nextToken();
							indi.surName = "";
						}
						if(t.hasMoreTokens())
							indi.surName = t.nextToken();
						br.mark(2000);
						if((line = br.readLine()) == null) return null;
						if(line.substring(2,6).compareTo("NSFX")==0)
							indi.surName += line.substring(6);
						else
							br.reset();
					}  // end NAME
					else if(type.compareTo("BAPL")==0){
						//br.mark(3000);
						//indi.bapl = nextEvent();
					}								
					else if(type.compareTo("BIRT")==0){
						br.mark(2000);
						indi.birth = nextEvent();
					}								
					else if(type.compareTo("DEAT")==0){
						br.mark(2000);
						indi.death = nextEvent();
					}  // "DEAT"
					else if(type.compareTo("FAMC")==0)
					{
						//TODO check for Primary/No multiple family assignments (ie use null or something)
						indi.famc = t.nextToken();				
					}
					else if(type.compareTo("FAMS")==0)
						indi.fams.add(t.nextToken());	
					else if (type.compareTo("SEX")==0)
					{
						String sex = t.nextToken();
						if (sex.equalsIgnoreCase("M"))
							indi.gender = 0;
						else
							indi.gender = 1;
					}
					else if (type.equals("OBJE"))
					{
						line = br.readLine();
						t = new StringTokenizer(line);
						t.nextToken();
						if (t.nextToken().equals("FORM"))
						{
							String fileType = t.nextToken();
							if (fileType.equalsIgnoreCase("jpg"))
							{
								line = br.readLine();
								t = new StringTokenizer(line);
								t.nextToken();
								if (t.nextToken().equals("FILE"))
								{
									indi.photoPath = line.substring(7);
									indi.hasPhoto = true;
								}
							}
						}
					}
				} // end if t.hasMoreTokens
			}  //  end "1"
			br.mark(2000);

		}  // end while
		System.out.println("returning null individual: " + line);
		return null;
	}

	/* ===========================================================================================================*/
	static FamilyClass nextFam() throws IOException{
		/* ===========================================================================================================*/
		FamilyClass fam = null;
		String line, code, type;
		StringTokenizer t;
		br.mark(2000);
		while((line = br.readLine()) != null) {
			t = new StringTokenizer(line, " @");
			if (!t.hasMoreTokens())
				continue;
			code = t.nextToken();
			if(code.compareTo("0") == 0){
				String tmpid = t.nextToken();
				if(t.countTokens() > 0){
					type = t.nextToken();
					if(type.compareTo("FAM")==0){
						fam = new FamilyClass(tmpid);
						br.mark(2000);
						while((line = br.readLine()) != null) { 
							t = new StringTokenizer(line, " @");
							code = t.nextToken();
							if(code.compareTo("0") == 0){
								br.reset();
								return fam;
							}
							else if(code.compareTo("1") == 0){
								type = t.nextToken();
								if(type.compareTo("CHIL")==0){
									String cid = t.nextToken();
									IndividualRecord child = individualmap.get(cid);
									fam.children.add(child);
								}								
								if(type.compareTo("MARR")==0){
									br.mark(2000);
									fam.marriage = nextEvent();
									if (fam.husband != null)
									{
										fam.husband.marriage = fam.marriage;
									}
									else if (fam.wife != null)
									{
										fam.wife.marriage = fam.marriage;
									}
									else
									{
										//System.out.print("no spouses");
									}
								}
								if(type.compareTo("HUSB")==0){
									String id = t.nextToken();
									fam.husband = individualmap.get(id); 
								}								
								if(type.compareTo("WIFE")==0)
									fam.wife = individualmap.get(t.nextToken());								
							} // "1"
							//else 
							//br.reset();
						} // end while
					} // end if FAM
				} // end if countTokens > 0
			} //  if "0"
		}  // end while
		return null;
	}  // end nextFam()


	/***********************************************************************************/
	static EventClass nextEvent()throws IOException{
		/***********************************************************************************/
		EventClass e = new EventClass("","","");
		String line, code;
		while((line = br.readLine()) != null) {
			StringTokenizer t = new StringTokenizer(line, " @");
			code = t.nextToken();
			if(code.compareTo("0") == 0 || code.compareTo("1") == 0){
				br.reset();
				return e;
			}
			if(code.compareTo("2") == 0)
			{
				if(line.length() < 6)System.out.println("Parser error: Expecting a line of at least six characters: " + line);
				if(line.substring(2,6).compareTo("DATE")==0)
				{
					e.date = line.substring(7);
					e.sortDate = parseDate(e.date);
					//br.mark(200);
				}
				else if(line.substring(2,6).compareTo("PLAC")==0)
				{
					if(line.length() > 6)
					{
						StringTokenizer place = new StringTokenizer(line.substring(7), ",()");
						if(place.hasMoreTokens())
							e.place1 = place.nextToken();
						if (
								e.place1.trim().equalsIgnoreCase("of") ||
								(e.place1.trim().equalsIgnoreCase(".")) ||
								(e.place1.trim().equalsIgnoreCase("<of")) ||
								(e.place1.trim().equalsIgnoreCase("<"))
								)
							e.place1 = place.nextToken();
						while(place.hasMoreTokens())
						{
							// Make the place parse correctly might need to be implemented
							String tempPlacePart = place.nextToken().trim();
							if (!tempPlacePart.equals("USA") )
								e.place2 = tempPlacePart;
						}
						e.place2 = Parser.abbreviatePlace(e.place2);
					}
				}
				/*else if(line.substring(2,6).compareTo("TEMP")==0){
				if(line.length() > 7)
						e.temple = line.substring(7,line.length());
				else e.temple = "Temple";*/
			}
			//	br.reset();
			// end if "2"
		}  // end while
		return e;
	}  // end nextEvent

	/* ===========================================================================================================*/
	static String getMiddleNames(String line){
		/* ===========================================================================================================*/
		int len = line.length();
		if(len < 8) return "";
		boolean lastCharacterWasASpace = true;
		int numberOfNames = 0;
		int middleNameStart = 0, middleNameEnd = 0;

		for(int i=7; i<len; i++){
			if (line.charAt(i) == '/')
			{
				if (lastCharacterWasASpace)
				{
					lastCharacterWasASpace = false;
					numberOfNames++;
					if(numberOfNames == 2)
						middleNameStart = i;
				}
				break;
			}
			if(line.charAt(i) == ' '){
				lastCharacterWasASpace = true;
			}
			else if (lastCharacterWasASpace){
				lastCharacterWasASpace = false;
				numberOfNames++;
				if(numberOfNames == 2)
					middleNameStart = i;
			}
		}
		if(middleNameStart < 7) return "";
		middleNameEnd = line.indexOf("/");
		if(middleNameEnd < 7) return "";
		return  (line.substring(middleNameStart, middleNameEnd)).trim();
	}


	/* ===========================================================================================================*/
	void close()throws IOException{
		/* ===========================================================================================================*/
		br.close();
	}

	/* ===========================================================================================================*/
	void reOpen(String filename)throws IOException{
		/* ===========================================================================================================*/
		br.close();
		try {
			br = new BufferedReader(new FileReader(filename));
		}
		catch (IOException e) {
			System.out.println("Can't open gedcom file: " + filename);
			return ;
		}
	}
	/* ===========================================================================================================*/
	static String abbreviatePlace(String str)
	{
		PlaceAbbr abbr = abbrMap.get(str.toLowerCase());
		if (abbr != null && abbr.known < 4)
		{
			++abbr.frequency;
			return abbr.abbr;
		}
		else
			return str;

	}

	/* ===========================================================================================================*/

	static void setUpAbbreviationMaps()
	{
		//this method sets up all of the abbreviations for states, countries, etc.
		//all abbreviations are listed in lower case but they can map to any case

		abbrMap = new HashMap<String, PlaceAbbr>();
		//US State and Territory abbreviations
		abbrMap.put("alabama", new PlaceAbbr("AL",2,2));
		abbrMap.put("alaska", new PlaceAbbr("AK",2,2));
		abbrMap.put("american samoa", new PlaceAbbr("AS",1,7));
		abbrMap.put("arizona", new PlaceAbbr("AZ",2,1));
		abbrMap.put("arkansas", new PlaceAbbr("AR",2,2));
		abbrMap.put("california", new PlaceAbbr("CA",2,1));
		abbrMap.put("colorado", new PlaceAbbr("CO",2,2)); 
		PlaceAbbr ct = new PlaceAbbr("CT",2,2);
		abbrMap.put("connecticut", ct);
		abbrMap.put("conn", ct);
		abbrMap.put("conn.", ct);
		abbrMap.put("delaware",  new PlaceAbbr("DE",2,3));
		abbrMap.put("district of columbia", new PlaceAbbr("DC",2,1));
		abbrMap.put("federated states of micronesia", new PlaceAbbr("FM",1,7));
		abbrMap.put("florida", new PlaceAbbr("FL",2,1));
		abbrMap.put("georgia", new PlaceAbbr("GA",2,2));
		abbrMap.put("guam", new PlaceAbbr("GU",1,9));
		abbrMap.put("hawaii", new PlaceAbbr("HI",2,2));
		abbrMap.put("idaho", new PlaceAbbr("ID",2,2));
		abbrMap.put("illinois", new PlaceAbbr("IL",2,2));
		abbrMap.put("indiana", new PlaceAbbr("IN",2,2));
		abbrMap.put("iowa", new PlaceAbbr("IA",2,2));
		abbrMap.put("kansas", new PlaceAbbr("KS",2,2));
		abbrMap.put("kentucky", new PlaceAbbr("KY",2,2));
		abbrMap.put("louisiana", new PlaceAbbr("LA",2,2));
		abbrMap.put("maine", new PlaceAbbr("ME",2,3));
		abbrMap.put("marshall islands", new PlaceAbbr("MH",1,7));
		abbrMap.put("maryland", new PlaceAbbr("MD",2,2));
		PlaceAbbr ma = new PlaceAbbr("MA",2,2);
		abbrMap.put("massachusetts", ma);
		abbrMap.put("mass.", ma);
		abbrMap.put("michigan", new PlaceAbbr("MI",2,2));
		abbrMap.put("minnesota", new PlaceAbbr("MN",2,2));
		abbrMap.put("mississippi", new PlaceAbbr("MS",2,3));
		abbrMap.put("missouri", new PlaceAbbr("MO",2,3));
		abbrMap.put("montana", new PlaceAbbr("MT",2,2));
		abbrMap.put("nebraska", new PlaceAbbr("NE",2,2));
		abbrMap.put("nevada", new PlaceAbbr("NV",2,2));
		abbrMap.put("new hampshire", new PlaceAbbr("NH",2,2));
		abbrMap.put("new jersey", new PlaceAbbr("NJ",2,2));
		abbrMap.put("new mexico", new PlaceAbbr("NM",2,2));
		abbrMap.put("new york", new PlaceAbbr("NY",2,1));
		abbrMap.put("north carolina", new PlaceAbbr("NC",2,2));
		abbrMap.put("north dakota", new PlaceAbbr("ND",2,2));
		abbrMap.put("northern mariana islands", new PlaceAbbr("MA",1,9));
		abbrMap.put("ohio", new PlaceAbbr("OH",2,2));
		abbrMap.put("oklahoma", new PlaceAbbr("OK",2,2));
		abbrMap.put("oregon", new PlaceAbbr("OR",2,2));
		abbrMap.put("palau", new PlaceAbbr("PW",1,9));
		abbrMap.put("pennsylvania", new PlaceAbbr("PA",2,2));
		abbrMap.put("puerto rico", new PlaceAbbr("PR",1,5));
		abbrMap.put("rhode island", new PlaceAbbr("RI",2,2));
		abbrMap.put("south carolina", new PlaceAbbr("SC",2,2));
		abbrMap.put("south dakota", new PlaceAbbr("SD",2,2));
		abbrMap.put("tennessee", new PlaceAbbr("TN",2,2));
		abbrMap.put("texas", new PlaceAbbr("TX",2,1));
		abbrMap.put("utah", new PlaceAbbr("UT",2,2));
		abbrMap.put("vermont", new PlaceAbbr("VT",2,2));
		abbrMap.put("virgin islands", new PlaceAbbr("VI",1,5));
		abbrMap.put("virginia", new PlaceAbbr("VA",2,2));
		abbrMap.put("washington", new PlaceAbbr("WA",2,2));
		abbrMap.put("west virginia", new PlaceAbbr("WV",2,2));
		abbrMap.put("wisconsin", new PlaceAbbr("WI",2,2));
		abbrMap.put("wyoming", new PlaceAbbr("WY",2,2));

		//country abbreviations
		//North America
		PlaceAbbr usa = new PlaceAbbr("USA",1,0);
		abbrMap.put("united states", usa);
		abbrMap.put("united states of america", usa);
		abbrMap.put("us", usa);
		abbrMap.put("usa", usa);
		abbrMap.put("canada", new PlaceAbbr("CAN",1,1));
		PlaceAbbr mexico = new PlaceAbbr("MEX", 1,1);
		abbrMap.put("mexico", mexico);
		abbrMap.put("m�xico", mexico);

		//Europe
		abbrMap.put("albania", new PlaceAbbr("ALB",1,5));
		abbrMap.put("andorra", new PlaceAbbr("AND",1,8));
		abbrMap.put("austria", new PlaceAbbr("AUT",1,7));
		abbrMap.put("oesterreich", new PlaceAbbr("AUT",1,7));
		abbrMap.put("belarus", new PlaceAbbr("BLR",2,7));
		abbrMap.put("belgium", new PlaceAbbr("BEL",2,5));
		PlaceAbbr bosnia = new PlaceAbbr("BIH",2,9);
		abbrMap.put("Bosnia", bosnia);
		abbrMap.put("bosnia and herzegovinia", bosnia);
		abbrMap.put("bosnia hercegovina", bosnia);
		abbrMap.put("bulgaria", new PlaceAbbr("BGR",2,7));
		//croatia??
		abbrMap.put("cyprus", new PlaceAbbr("CYP",2,7));
		abbrMap.put("denmark", new PlaceAbbr("DNK",2,6));
		PlaceAbbr england = new PlaceAbbr("ENG",1,2);
		abbrMap.put("england", england);
		abbrMap.put("engl", england);
		abbrMap.put("engl.", england);
		abbrMap.put("eng", england);
		abbrMap.put("eng.", england);
		abbrMap.put("france", new PlaceAbbr("FRA",2,4));
		abbrMap.put("germany", new PlaceAbbr("GER",2,4));//ISO code is "DEU"
		abbrMap.put("holland", new PlaceAbbr("HOL",1,4));
		abbrMap.put("italy", new PlaceAbbr("ITA",2,5));
		abbrMap.put("netherlands", new PlaceAbbr("NLD",1,6));
		abbrMap.put("norway", new PlaceAbbr("NOR",2,5));
		abbrMap.put("russia", new PlaceAbbr("RUS",1,5));
		PlaceAbbr ussr = new PlaceAbbr("USSR",1,1);
		abbrMap.put("soviet union", ussr);
		abbrMap.put("union of soviet socialist republics", ussr);
		abbrMap.put("spain", new PlaceAbbr("ESP",2,8));
		abbrMap.put("sweden", new PlaceAbbr("SWE",2,5));
		abbrMap.put("switzerland", new PlaceAbbr("CHE",1,9));
		abbrMap.put("united kingdom",new PlaceAbbr("UK",1,1));

		//abbrMap.put("", new PlaceAbbr("", 0,0));

		//Austrailia and Pacific Islands (Oceania)
		abbrMap.put("austraila", new PlaceAbbr("AUS", 1,2));

		//Asia

		//South America


		//Africa

		//continents
		abbrMap.put("north america", new PlaceAbbr("N.America",0,0));
		abbrMap.put("south america", new PlaceAbbr("S.America",0,0));
		abbrMap.put("europe", new PlaceAbbr("EUR",0,0));
		abbrMap.put("asia", new PlaceAbbr("Asia",0,0));
		abbrMap.put("africa", new PlaceAbbr("Africa",0,0));
		abbrMap.put("oceania", new PlaceAbbr("Oceania",0,10));


	}
	/* ===========================================================================================================*/
	/*
	 * This method takes a string and attempts to create a Date from it.
	 * It returns null if not possible
	 */
	public static Date parseDate(String str)
	{
		Integer year, month, day;
		boolean AD = true;
		year = month = day = null;

		StringTokenizer t = new StringTokenizer(str, " \t\n\r\f,/-<>()");
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

		if (year == null && day != null)
			year = day;

		//if year is missing, return null
		if (year == null)
			return null;

		//if a field is missing, then substitute the possible midpoint for lower fields
		//	ie. if month is missing, set month & day corresponding to midpoint of year
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

		return calendar.getTime();
	}

	/* ===========================================================================================================*/
	public static final String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
	/* ===========================================================================================================*/

}

