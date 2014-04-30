package edu.byu.cs.roots.opg.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The individual holds the information about the name,
 * gender, event dates and places, id, the number
 * of ansestors or descendants, the connection to parents,
 * connection to family members and spouses, photos,
 * and LDS ordinance information.
 * @author derek
 * @version 1.0.0
 * @see Event
 */
public class Individual implements Comparable<Individual>, Serializable{

	/**	Serial Number Generated so that we can save or 
	 * transfer this data over a network or on disk. */
	private static final long serialVersionUID = 1000L;
	public String id; //unique id String of this person
	public String givenName = ""; //first given name only
	public String surname = "";
	public String middleName = ""; //string of all the middle names
	public String nameSuffix = "";
	public String surnamePrefix = "";
	public String namePrefix = "";
	public Gender gender;
	public Event birth;
	public Event death;
	public int numberOfDescendants = -1;
	public int numberOfAncestors = 0;
	public int totalPathLength = -1;
	public boolean isDisplayed = false;
	
	
	
	private String label;
	
	public String version = "";
	
	public Single<String> pageId;

	//cyclical debugging
//	public String pathFromRoot = "";
//	public int lastVisit=0;
//	public int deltaVisit=0;
	
	//these were transient
	transient public  Individual mother = null; //reference to the primary mother
	transient public  Individual father = null; //reference to the primary father
	//public Event marriage;//????
	
	public  ArrayList<String> famsIds = new ArrayList<String>(1);
	public  ArrayList<String> famcIds = new ArrayList<String>(1);
	transient public  ArrayList<Family> fams = new ArrayList<Family>(1);
	transient public  ArrayList<Family> famc = new ArrayList<Family>(1);
	
	/**
	 * Used by the Multisheet chart to store what charts create this one
	 */
	public ArrayList<Individual> originatingPages = new ArrayList<Individual>();
	
	public int primaryParents = -1; //this is an index into the famc/famcIds Array
	
	
	//whether or not there is a repeat entry
	public boolean isInTree = false;
	/*each set of repeat individuals is assigned a different set number
	(e.g. Brigham Young repeated 5000 times (numberOfOccurances) will be set #23 (repeatSetNumber) while George Washington repeated 314 
	times might be set number 26*/
	public int numberOfOccurances = 0;
	public int repeatSetNumber = 0;
	
	public String photoPath; //absolute path of photo of this person.
	public boolean hasPhoto = false; // this is whether or not the individual has photo information
	
	//LDS ordinance data
	public boolean baptism = false;
	public boolean baptismComplete = false;
	public boolean endowment = false;
	public boolean endowmentComplete = false;
	public boolean sealingToParents = false;
	public boolean sealingToParentsComplete = false;
	public boolean sealingToSpouse = false;
	public boolean sealingToSpouseComplete = false;
	
	public Individual(String id)
	{
		this.id = id;
		fams = new ArrayList<Family>();
		famcIds = new ArrayList<String>();
		famsIds = new ArrayList<String>();
		pageId = new Single<String>("");
		
	}
	
	public Individual()
	{
		this.id = null;
		fams = null;
		famcIds = new ArrayList<String>();
		famsIds = new ArrayList<String>();
		fams = new ArrayList<Family>(1);
		famc = new ArrayList<Family>(1);
		pageId = new Single<String>("");
	}
	
	
		
	public String toString(){
		if(label == null){
			//System.out.println("computing label");
			label = surname + ", " + this.givenName + " " + middleName + " (" + ((birth != null && birth.date != null)? birth.date: " " ) + "-" + ((death != null && death.date != null)? death.date: " " ) + ")";
		}
		return label;
	}
	
	
	//Quick checks to see if data exists
	public boolean hasBirthYear(){
		return (birth != null && birth.yearString != null && birth.yearString.compareTo("") != 0);
	}
	public boolean hasDeathYear(){
		return (death != null && death.yearString != null && death.yearString.compareTo("") != 0);
	}
	public boolean hasBirthDate(){
		return (birth != null && birth.date != null && birth.date.compareTo("") != 0);
	}
	public boolean hasDeathDate(){
		return (death != null && death.date != null && death.date.compareTo("") != 0);
	}
	public boolean hasBirthPlace(){
		return (birth != null && birth.place != null && birth.place.compareTo("") != 0);
	}
	public boolean hasMarriagePlace(){
		return (fams != null && fams.size() > 0 && fams.get(0) != null && fams.get(0).marriage != null && fams.get(0).marriage.place != null && fams.get(0).marriage.place.compareTo("")!=0);
	}
	public boolean hasDeathPlace(){
		return (death != null && death.place != null && death.place.compareTo("") != 0);
	}
	
	
	public String toStringVerbose(){
		System.out.println("verbose");
		StringBuilder s = new StringBuilder();
		s.append("IND_ID:"+this.id+"\n");
		s.append("GIVEN: "+this.givenName+"\n");
		s.append("SURNAME: "+this.surname+"\n");
		s.append("SURNAME-PRE: "+this.surnamePrefix+"\n");
		s.append("NAME-PRE: "+this.namePrefix+"\n");
		s.append("NAME-SUF: "+this.nameSuffix+"\n");
		s.append("GENDER: "+gender+"\n");
		s.append("BIRTH: "+birth+"\n");
		s.append("DEATH: "+death+"\n");
		s.append("FAMS-IDS: "+famsIds+"\n");
		s.append("FAMC-IDS: "+famcIds+"\n\n");
		s.append("BAPTISM: "+baptismComplete+"\n");
		s.append("ENDOWMENT: "+endowmentComplete+"\n");
		s.append("SEALING PARENT: "+sealingToParentsComplete+"\n");
		s.append("SEALING SPOUSE: "+sealingToSpouseComplete+"\n");
		s.append("PAGE ID: "+pageId+"\n");
		s.append("Origins: ");
		for (Individual indi : originatingPages)
			s.append(indi.pageId + " ,");
		return s.toString();
	}
	public int compareTo(Individual other)
    {
    	int labelTest = toString().compareToIgnoreCase( other.toString() );
    	if (labelTest == 0)
    		return id.compareTo(other.id);
    	return  labelTest;
    }
	
	@Override
    public boolean equals(Object o)
    {
		Individual other = (Individual)o;
		//System.out.println("Comparing " + id + " to " + other.id);
    	return (o != null) ? (id.compareTo(other.id) == 0) : false;
    }
	public void resetFlags()
	{
		isInTree = false;
		repeatSetNumber=0;
		numberOfOccurances=0;
	}
	
	public boolean isMarried() {
		if(fams != null && !fams.isEmpty()) {
			return fams.get(0).husband != null &&
			       fams.get(0).wife != null;
		}
		return false;
	}
	
	public ArrayList<Individual> getOriginatingPages(){
		return originatingPages;
	}
	
	public void addToOriginatingPages(Individual indi){
		if (!originatingPages.contains(indi))
			originatingPages.add(indi);
	}
	
}
