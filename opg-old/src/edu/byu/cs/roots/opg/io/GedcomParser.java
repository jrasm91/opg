package edu.byu.cs.roots.opg.io;

import java.io.IOException;

import static edu.byu.cs.roots.opg.io.FSM.*;
import edu.byu.cs.roots.opg.model.Event;
import edu.byu.cs.roots.opg.model.EventType;
import edu.byu.cs.roots.opg.model.Family;
import edu.byu.cs.roots.opg.model.Gender;
import edu.byu.cs.roots.opg.model.Individual;

/**
 * @author Graham Henry
 * Gedcom Parser - parses any valid gedcom file 
 */
public class GedcomParser {

	private FSM tokenizer;
	private GedcomRecord record;
	/**
	 * Creates an instance of a GedcomParser, and initialized the Lexer
	 * @param fileToParse - The file name of the gedcom you wish to parse,
	 * note: the file name doesn't have to end in .ged
	 */
	public GedcomParser(String fileToParse) throws IOException
	{
		
		this.tokenizer = new FSM(fileToParse);	
			

	}
	

	/**
	 * Parses the gedcom file that was given in the constructor
	 * @return A linked GedcomRecord, which contains a HashMap of
	 * the families and a HashMap of the individuals contained in 
	 * the gedcom file 
	 */
	public GedcomRecord parseGedcom() throws InvalidSyntaxException, IOException{
		GedcomRecord record = null;
		
		try{
			parseHead();
			record = parseBody();
			record.linkRecord();
		}
		catch(InvalidSyntaxException e){
			System.err.println("INVALID SYNTAX:\n"+e);
			throw e;
		}
		catch(IOException e){
			System.err.println("EOF");
			//e.printStackTrace();
		}
		catch(Exception e)
		{
			//do nothing
		}
		return record;
	}

	/**
	 * Parses the head of the gedcom file by assuring it starts with "0 HEAD",
	 * and skips the rest
	 * @throws IOException
	 * @throws InvalidSyntaxException
	 */
	private void parseHead() throws IOException, InvalidSyntaxException {
		System.out.println("Parsing HEAD");
		
		if(tokenizer.nextTokenId() != LEVEL_0 && tokenizer.nextTokenId() != HEAD)
			throw new InvalidSyntaxException("INVALID HEADER",tokenizer.lineNumber);

		skipRecord(LEVEL_0);
	}
	
	/**
	 * Parses the Body of the gedcom file, which contains individuals and
	 * families.  It will parse them in any order.
	 * @return An unlinked GedcomRecord that contains a HashMap of the Families
	 * and a HashMap of the Individuals in the gedcom file.  The linking is done 
	 * in the parseGedcom() method
	 * @throws IOException
	 * @throws InvalidSyntaxException
	 */
	private GedcomRecord parseBody() throws IOException, InvalidSyntaxException {
		System.out.println("Parsing BODY");	
		String id;	
		
		record = new GedcomRecord();
		
		while(tokenizer.peekTokenId() != TRLR && tokenizer.peekTokenId() != TRAILER){
			tokenizer.resetToken();
			String xrefIdString = tokenizer.nextWord();
            if (xrefIdString.indexOf('@') < 0) {
                //this is not a GEDCOM cross-reference id
                // delimited by '@' characters - skip to the next record
                skipRecord(LEVEL_0);
                continue;
            }
            String[] fields = xrefIdString.split("@");
			if(fields.length<1) throw new InvalidSyntaxException("Invalid Individual ID",tokenizer.lineNumber);
			id = fields[0];
			int token = tokenizer.peekTokenId();
			switch(token){
				case INDIVIDUAL:
				case INDI:
					record.addIndividual(id, parseIndividual(id));
					break;
				case FAMILY:
				case FAM:
					record.addFamily(id, parseFamily(id));
					break;
				case IGNORE:
					System.out.println("IGNORE: " +token+" On Line " + (tokenizer.lineNumber-1));
					skipRecord(LEVEL_0);
					break;
				case EOF:
					return record;
				default:
					//tokenizer.reader.mark(1000);
					//System.out.println(tokenizer.reader.readLine());
					//tokenizer.reader.reset();
					System.out.println(token+" On Line " + (tokenizer.lineNumber-1));
					skipRecord(LEVEL_0);
					//throw new InvalidSyntaxException("ERROR: Expected INDI or FAM tags",tokenizer.lineNumber-1);
			}
		}
		return record;
	}
	
	/**
	 * Parses an individual in the gedcom file
	 * @param id - the individual ID of the person to parse
	 * @return - an instance of the Individual class
	 * @throws IOException
	 * @throws InvalidSyntaxException
	 */
	private Individual parseIndividual(String id) throws IOException, InvalidSyntaxException {
		Individual individual = new Individual(id);
		String[] fields;
		while(tokenizer.peekTokenId() != LEVEL_0){
			switch(tokenizer.peekTokenId()){
			case NAME:
				parseName(individual); 
				break;
			case SEX:
				individual.gender = parseGender(tokenizer.nextLine());
				break;
			case BIRTH:
				individual.birth = parseEvent(EventType.BIRTH); 
				break;
			case BIRT:
				individual.birth = parseEvent(EventType.BIRTH); 
				break;
			case DEATH:
				individual.death = parseEvent(EventType.DEATH); 
				break;
			case DEAT:
				individual.death = parseEvent(EventType.DEATH); 
				break;
			case FAMILY_SPOUSE:
			case FAMS:
				fields = tokenizer.nextLine().split("@");
				if(fields.length < 2) throw new InvalidSyntaxException("Invalid Individual ID in your gedcom file for individual "+id,tokenizer.lineNumber);
				individual.famsIds.add(fields[1]); 
				break;
			case FAMILY_CHILD:
			case FAMC:
				parseFamc(individual);
				break;
			case IGNORE:
				skipRecord(LEVEL_1);
				tokenizer.resetToken();
				break;
			case BAPL:
				parseBapl(individual);
				break;
			case CONL:
				parseBapl(individual);
				break;
			case ENDL:
				parseEndl(individual);
				break;
			case SLGC:
				parseSlgc(individual);
				break;
			case OBJE:
				parseObje(individual);
				break;
			default:
				System.out.println(tokenizer.peekTokenId());
				throw new InvalidSyntaxException("Gedcom error with Individual "+id,tokenizer.lineNumber);
			}
		}
		return individual;
	}
	
	private void parseFamc(Individual ind) throws IOException, InvalidSyntaxException {
		boolean foundStat = false;

		int token = 0;
		//Takes in the first famc and keeps it, unless a Primary specifies a later one
		String famId = "";
		String prevId = "";
		
		do{
			
			if(token == _PRIMARY){
				if(foundStat == false) throw new InvalidSyntaxException("Invalid Individual ID in your gedcom file for individual "+ind.id,tokenizer.lineNumber);
				famId = prevId;
			}
			else{
				String[] fields = tokenizer.nextLine().split("@");
				if(fields.length < 2) throw new InvalidSyntaxException("Invalid Individual ID in your gedcom file for individual "+ind.id,tokenizer.lineNumber);
				prevId = (fields[1]);
				if (!foundStat){
					famId = (fields[1]);
					foundStat = true;
				}
			}
			
		}
		while(((token = tokenizer.peekTokenId(2)) == FAMC) || token == _PRIMARY);
		
		tokenizer.resetToken();
		ind.famcIds.add(famId);
	}
	
	private void parseBapl(Individual ind) throws IOException {
		boolean foundStat = false;
		int token;
		while((token = tokenizer.peekTokenId()) != LEVEL_1 && token != LEVEL_0){
			switch(tokenizer.peekTokenId()) {
				case STAT:
					foundStat = true;
					switch(tokenizer.peekTokenId()) {
						case CHILD:
						case INFANT:
						case STILLBORN:
						case COMPLETED:
						case PRE_1970:
							ind.baptismComplete = true;
						case CLEARED:
						case SUBMITTED:
						case QUALIFIED:
							ind.baptism = true;
					}
					break;
				default:
					tokenizer.skipLine();
			}
		}
		if(!foundStat) {
			ind.baptism = true; 
			ind.baptismComplete = true;
		}
		tokenizer.resetToken();
	}
	//TODO Add TEMP (temple)
	private void parseEndl(Individual ind) throws IOException {
		boolean foundStat = false;
		int token;
		while((token = tokenizer.peekTokenId()) != LEVEL_1 && token != LEVEL_0){
			switch(tokenizer.peekTokenId()) {
				case STAT:
					foundStat = true;
					switch(tokenizer.peekTokenId()) {
						case BIC:
						case DNS:
						case STILLBORN:
						case COMPLETED:
						case PRE_1970:
							ind.endowmentComplete = true;
						case CLEARED:
						case SUBMITTED:
						case QUALIFIED:
							ind.endowment = true;
					}
					break;
				default:
					tokenizer.skipLine();
			}
		}
		if(!foundStat) {
			ind.endowment = true; 
			ind.endowmentComplete = true;
		}
		tokenizer.resetToken();
	}
	
	private void parseSlgc(Individual ind) throws IOException {
		boolean foundStat = false;
		int token;
		while((token = tokenizer.peekTokenId()) != LEVEL_1 && token != LEVEL_0){
			switch(tokenizer.peekTokenId()) {
				case STAT:
					foundStat = true;
					switch(tokenizer.peekTokenId()) {
						case BIC:
						case DNS:
						case STILLBORN:
						case COMPLETED:
						case PRE_1970:
							ind.sealingToParentsComplete = true;
						case CLEARED:
						case SUBMITTED:
						case QUALIFIED:
							ind.sealingToParents = true;
					}
					break;
				default:
					tokenizer.skipLine();
			}
		}
		if(!foundStat) {
			ind.sealingToParents = true; 
			ind.sealingToParentsComplete = true;
		}
		tokenizer.resetToken();
	}
	
	private void parseSlgs(Family fam) throws IOException {
		boolean foundStat = false;
		int token;
		while((token = tokenizer.peekTokenId()) != LEVEL_1 && token != LEVEL_0){
			switch(tokenizer.peekTokenId()) {
				case STAT:
					foundStat = true;
					switch(tokenizer.peekTokenId()) {
						case DNS:
						case DNS_CAN:
						case COMPLETED:
						case PRE_1970:
							fam.sealingComplete = true;
						case CLEARED:
						case SUBMITTED:
						case QUALIFIED:
							fam.sealing = true;
					}
					break;
				default:
					tokenizer.skipLine();
			}
		}
		if(!foundStat) {
			fam.sealing = true; 
			fam.sealingComplete = true;
		}
		tokenizer.resetToken();
	}
	
	private void parseObje(Individual ind) throws IOException {
		int token;
		while((token = tokenizer.peekTokenId()) != LEVEL_1 && token != LEVEL_0){
			switch(tokenizer.peekTokenId()) {
				case FILE:
					//add file as photo if it's a jpg, gif, or png (built in supported types for Java)
					String fileName = tokenizer.nextLine();
					if (fileName.endsWith(".jpg") || fileName.endsWith(".gif") || fileName.endsWith(".png"))
					{
						ind.photoPath = fileName;
						ind.hasPhoto = true;
					}
					break;
				default:
					tokenizer.skipLine();
			}
		}
		tokenizer.resetToken();
	}
	
	/**
	 * Parses a String to determine the gender of an individual.  It is case 
	 * insensitive, and accepts a single letter ex. 'M' or the entire word ex. 'MALE'
	 * @param gender - A string containing the gender of an individual
	 * @return - A Gender enum with possible values MALE, FEMALE, or UNKNOWN
	 */
	private Gender parseGender(String gender) {
		String g = gender.toUpperCase();
		if(g.equals("M") || g.equals("MALE")) return Gender.MALE;
		else if(g.equals("F") || g.equals("FEMALE")) return Gender.FEMALE;
		else return Gender.UNKNOWN;
	}
	
	/**
	 * Parses an Event in the gedcom file, including Date and Place
	 * @param type - Possible values: BIRTH, DEATH 
	 * @return -An instance of the Event class
	 * @throws IOException
	 */
	private Event parseEvent(EventType type) throws IOException {
		Event event = new Event(type);

		if(tokenizer.peekTokenId() != Y) tokenizer.resetToken();
		while(tokenizer.peekTokenId() > LEVEL_1){
			switch(tokenizer.nextTokenId()){
			case DATE: 
				event.date = tokenizer.nextLine().trim();
				event.parseDateParts();
				break;
			case PLACE:
			case PLAC:
				event.place = tokenizer.nextLine().trim();
				break;
			default: tokenizer.nextLine();
			}
		}
		tokenizer.resetToken();
		return event;
	}
	

	/**
	 * Parses the name of an individual, including given, surname, 
	 * name-prefix, surname-prefix and name-suffix
	 * @param the current Individual the parser is parsing
	 * @throws IOException
	 * @throws InvalidSyntaxException
	 */
	private void parseName(Individual individual) throws IOException, InvalidSyntaxException {
		String nameline = tokenizer.nextLine();
		
		individual.givenName = getGivenName(nameline);
		individual.surname = getSurname(nameline);
		individual.nameSuffix = getNameSuffix(nameline);

		while(tokenizer.peekTokenId() > LEVEL_1){
			switch(tokenizer.peekTokenId()){
			case NPFX:
				individual.namePrefix = tokenizer.nextLine().trim();
				break;
			case SPFX:
				individual.surnamePrefix = tokenizer.nextLine().trim(); 
				break;
			case SURN:
				individual.surname = tokenizer.nextLine().trim(); 
				break;
			case NSFX:
				individual.nameSuffix = tokenizer.nextLine().trim(); 
				break;
			default:
				tokenizer.nextLine();
			}
		}
		tokenizer.resetToken();
	}
	

	private String getNameSuffix(String nameline) {
		int start = nameline.lastIndexOf("/");
		if(start != -1 && start < nameline.length()-1)
			return nameline.substring(start+1);
		
		else return "";
	}


	private String getSurname(String nameline) {
		int start = nameline.indexOf("/");
		int end = nameline.lastIndexOf("/");

		if(start != -1 && end != -1 && start < nameline.length()-1 && start != end){
			return nameline.substring(start+1, end);
		}
		else if(start != -1 && end == -1 && start < nameline.length()-1 && start != end){
			return nameline.substring(start+1);
		}
		else return "";
	}


	private String getGivenName(String nameline) {
		int end = nameline.indexOf("/");
		if(end != -1)
			return nameline.substring(0,end);
		else return nameline.trim();
	}


	/**
	 * Parses a family in the gedcom File
	 * @param id - The ID of the family the parser is about to parse
	 * @return - An instance of the Family class, setting only the ID's.
	 * The actual Husband, Wife, Children are added after the parsing is done,
	 * when the Record is linked.
	 * @throws IOException
	 */
	private Family parseFamily(String id) throws IOException {
		int token;	
		Family family = new Family(id);

		while((token = tokenizer.peekTokenId()) > LEVEL_0){
			if(token == LEVEL_1){
				switch(tokenizer.peekTokenId()){
				case HUSBAND:
				case HUSB:
					family.husbandId = tokenizer.nextLine().split("@")[1];
					break;
				case WIFE:
					family.wifeId = tokenizer.nextLine().split("@")[1];					
					break;
				case CHILD:
				case CHIL:
					String code = tokenizer.nextLine().split("@")[1];
					Individual indi;
					if((indi = record.getIndividual(code)) != null)
						if(indi.famcIds.get(0).contains(id))
							family.childrenXRefIds.add(code);					
					break;
				case MARRIAGE:
				case MARR:
					family.marriage = parseEvent(EventType.MARRIAGE);
					break;
				case SLGS:
					parseSlgs(family);
					//skipRecord(LEVEL_1);
					//tokenizer.resetToken();
					break;
				default:
					tokenizer.skipLine();
				}
			}
			else tokenizer.skipLine();
		}
		return family;
	}
	
	
	/**
	 * Skips a Record, ignoring all tags until the stop level is reached
	 * @param stopLevel - The level you wish to stop skipping tags at.
	 * @throws IOException
	 */
	private void skipRecord(int stopLevel) throws IOException{
		tokenizer.skipLine();
		while(tokenizer.peekTokenId() > stopLevel)
			tokenizer.skipLine();
	}
	
	public static void main(String[] args){

		System.err.println("here");
		GedcomParser parser = null;
		GedcomRecord record = null;
		try{
			parser = new GedcomParser("");
			record = parser.parseGedcom();
		}
		catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
		System.out.println("Individuals: "+record.getIndividuals().size());
		System.out.println("Families: "+record.getFamilies().size());
	}
}