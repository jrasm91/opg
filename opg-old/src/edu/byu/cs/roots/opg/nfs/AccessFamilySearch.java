package edu.byu.cs.roots.opg.nfs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.familysearch.ws.client.familytree.v2.schema.EventAssertion;
import org.familysearch.ws.client.familytree.v2.schema.EventValue;
import org.familysearch.ws.client.familytree.v2.schema.FamilyReference;
import org.familysearch.ws.client.familytree.v2.schema.FamilyTree;
import org.familysearch.ws.client.familytree.v2.schema.GenderAssertion;
import org.familysearch.ws.client.familytree.v2.schema.GenderType;
import org.familysearch.ws.client.familytree.v2.schema.NameAssertion;
import org.familysearch.ws.client.familytree.v2.schema.NameForm;
import org.familysearch.ws.client.familytree.v2.schema.NamePiece;
import org.familysearch.ws.client.familytree.v2.schema.NamePieceType;
import org.familysearch.ws.client.familytree.v2.schema.NameType;
import org.familysearch.ws.client.familytree.v2.schema.OrdinanceAssertion;
import org.familysearch.ws.client.familytree.v2.schema.OrdinanceType;
import org.familysearch.ws.client.familytree.v2.schema.ParentReference;
import org.familysearch.ws.client.familytree.v2.schema.ParentsReference;
import org.familysearch.ws.client.familytree.v2.schema.Pedigree;
import org.familysearch.ws.client.familytree.v2.schema.Person;
import org.familysearch.ws.client.familytree.v2.schema.PersonReference;
import org.familysearch.ws.client.identity.v2a.schema.Identity;

import edu.byu.cs.roots.opg.io.GedcomRecord;
import edu.byu.cs.roots.opg.model.Event;
import edu.byu.cs.roots.opg.model.EventType;
import edu.byu.cs.roots.opg.model.Family;
import edu.byu.cs.roots.opg.model.Gender;
import edu.byu.cs.roots.opg.model.Individual;

public class AccessFamilySearch {

	private String username;
	private String password;
	private final static String devKey = "WCQY-7J1Q-GKVV-7DNM-SQ5M-9Q5H-JX3H-CMJK";
	private final static String nfsKey = "3MRF-LC1D-JJ5S-ML42-223W-7PD7-KLR8-6B83";
	private final static String baseURL = "https://api.familysearch.org";
	private String PersonID;
	private DefaultHttpClient httpclient; 
	private ResponseHandler<String> responseHandler;
	private static int famCount = 0; 
	private GedcomRecord gedRecord;
	private ArrayList<String> waitingQueue;
	private final int MAX_CALLS = 10;
	private final int MAX_GENS = 9; //only want to grab 9 generations for the first call
	
	private boolean afterNine; //this boolean is used in getParentValues().  it is false until
								//the program calls continueAfterNine().  This makes sure that the names
								//are being added to the waiting list and not being sent to the UserPedigree()
								//which will use up calls and time.
	private boolean continuedAfterNine = false;
	private boolean continued;
	private RelationMap relMap;
	private Timer timer;
	
	//used with throttled()
	private int throttledAmt;
	private final int UPDATE = 0;
	private final int GETINFO = 1;
	private final int AFTER9 = 2;
	private String firstOfQueue = "";
	
	
	
	private final int twentyMinutes = 1200000;

	public AccessFamilySearch() {		
		httpclient = new DefaultHttpClient();
		responseHandler = new BasicResponseHandler();
		username = "";
		password = "";
		afterNine = false;
		
		ActionListener timerListener = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				System.out.println("this is getting in here!");
				try {
					getIdentity();
				} catch (ClientProtocolException e) {
					System.out.println("there is an error reobtaining the Identity object");
					e.printStackTrace();
				} catch (IOException e) {
					System.out.println("there is an error reobtaining the Identity object");
					e.printStackTrace();
				} catch (JAXBException e) {
					System.out.println("there is an error reobtaining the Identity object");
					e.printStackTrace();
				}
			}
		};
		timer = new Timer(twentyMinutes, timerListener);
	}

	/*
	 * After the user decides to download a new pedigree, this method is called, which
	 * resets the global variables, and readies the program to start another download.
	 */
	public void newUsernameAndPassword(String un, String pw) {
		username = un;
		password = pw;
		httpclient.getCredentialsProvider().clear();
		httpclient.getCredentialsProvider().setCredentials(
				new AuthScope(baseURL, 80), 
				new UsernamePasswordCredentials(username, password));
		gedRecord = new GedcomRecord();
		waitingQueue = new ArrayList<String>();
		afterNine = false;
		relMap = new RelationMap();
		if (timer.isRunning())
			timer.stop();
		throttledAmt = 0;
		
		firstOfQueue = "";
	}

	public Individual GetRoot() {
		return gedRecord.getIndividual(PersonID);
	}

	/*
	 * This method returns true if all of the individuals have obtained their information
	 */
	public boolean GetMore() {
		return !relMap.getAllNotDone().isEmpty();
	}
	/*
	 * returns the gedRecord.  Before the gedRecord is returned,
	 * the relMap is saved in the record.
	 */
	public GedcomRecord getGedcomRecord() {
		gedRecord.setRelationMap(relMap);
		return gedRecord;
	}
	
	/*
	 * This method preps the pedigree for updates.  the tenth generation individuals are
	 * stored in families.	
	 */
	public void doNotContinue() {
		continuedAfterNine = false;
		afterNine = true;
		ArrayList<String> tenthGen = relMap.getGenerationList(MAX_GENS + 1);
		do {
			String ID = "";
			int amt = MAX_CALLS;
			if (tenthGen.size() < MAX_CALLS)
				amt = tenthGen.size();
			for (int i = 0; i < amt; i++)
				ID += tenthGen.get(i) + ",";
			ID = ID.substring(0, ID.length()-1);
		
			try {
				String requestUrl = baseURL+"/familytree/v2/person/";
				requestUrl += ID;
				//request all the information about the person
				requestUrl += "?names=none&events=none&families=summary&children=all";
				String responseBody = httpclient.execute(new HttpGet(requestUrl), responseHandler);
				FamilyTree extract = (FamilyTree) JAXBContext.newInstance(FamilyTree.class).createUnmarshaller().unmarshal(new StringReader(responseBody));
				String[] IDS = ID.split(",");
				int IDcount = 0;
				for (Person person: extract.getPersons()) {
					Individual indiNew;
					//change ID so it is the correct one if making multiple calls!
					if(!gedRecord.containsIndividual(IDS[IDcount])) {
						indiNew = new Individual(IDS[IDcount]);
						gedRecord.addIndividual(indiNew.id, indiNew);
					} else
						indiNew = gedRecord.getIndividual(IDS[IDcount]);
					
					indiNew.version = person.getVersion();
					
					indiNew = getGender(indiNew, person);
					indiNew = getFamilyValues(indiNew, person);
					
					IDcount++;
				}
			} catch (ClientProtocolException e) {
				System.out.println("getting into ClientProgocolException in doNotContinue");
			} catch (IOException e) {
				System.out.println("getting into IOException in doNotContinue");
			} catch (JAXBException e) {
				System.out.println("getting into JAXBException in doNotContinue");
			}
			for (int i = 0; i < amt; i++)
				tenthGen.remove(0);
		} while (!tenthGen.isEmpty());
	}
	
	/*
	 * this method is called when a user wants to open a previously downloaded pedigree
	 */
	public void open (GedcomRecord record, Individual root) {
		gedRecord = record;
		relMap = gedRecord.getRelationMap();
		waitingQueue = new ArrayList<String>();
		PersonID = root.id;
		famCount = record.getFamilies().size();
		checkAfterNine();
	}
	
	/*
	 * this method determines if the pedigree had already downloaded more than nine or not
	 */
	private void checkAfterNine() {
		int highestGen = relMap.getOldestGeneration();
		ArrayList<String> highestGenList = relMap.getGenerationList(highestGen);
		/*
		 * this if statement checks to see if the generations is one over the max generation
		 * because the ID's of some of the tenth generations are stored, the grabbing of the
		 * tenth generation could be wrong.  This checks to see if they have been done or not.
		 * if they haven't, then we say the highest generation is nine, or MAX_GENS.
		 */
		if (highestGen == (MAX_GENS + 1) && relMap.getAllNotDone().contains(highestGenList.get(0)))
				highestGen--;
		if (highestGen > MAX_GENS) {
			afterNine = true;
			continuedAfterNine = true;
		} else if (highestGen == MAX_GENS)
			afterNine = true;
		
	}
	
	/*
	 * If only the username and password need to be reset, this method resets
	 * and stores the new username and password, and then runs Identity to
	 * make sure they both work.
	 */
	public void resetUsernameAndPassword(String un, String pw) throws UsernamePasswordException {
		username = un;
		password = pw;
		httpclient.getCredentialsProvider().clear();
		httpclient.getCredentialsProvider().setCredentials(
				new AuthScope(baseURL, 80), 
				new UsernamePasswordCredentials(username, password));	
		try {
			getIdentity();
		} catch (ClientProtocolException e) {
			System.out.println("getting into ClientProgocolException in checkUsernameAndPassword");
			if (((HttpResponseException) e).getStatusCode() == 401)
				throw new UsernamePasswordException();
		} catch (IOException e) {
			System.out.println("getting into IOException in checkUsernameAndPassword");
		} catch (JAXBException e) {
			System.out.println("getting into JAXBException in checkUsernameAndPassword");
		}
	}

	/**
	 * this method goes in and grabs the session ID or key from new.familysearch
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws JAXBException 
	 */
	private void getIdentity() throws ClientProtocolException, IOException, JAXBException {
		// DefaultHttpClient handles cookies automatically
        // BasicResponseHandler returns the HTTP response body as a String
        String responseBody;
        String requestUrl = baseURL+"/identity/v2/login?key=" + nfsKey;
//        requestUrl += "&username="+username;
//        requestUrl += "&password="+password;
        requestUrl += "&agent="+ "OnePageGenealogy";
        
        HttpGet get = new HttpGet(requestUrl);
        get.setHeader("username", username);
        get.setHeader("password", password);
		responseBody = httpclient.execute(get, responseHandler);
		// Unmarshal the Identity object from the response body and save the session ID
        Identity identity = (Identity) JAXBContext.newInstance(Identity.class).createUnmarshaller().unmarshal(new StringReader(responseBody));
        String sessionId = identity.getSession().getId();
 
        System.out.println("Session ID: " + sessionId);
	}


	/**
	 * this is used the first time that the user accesses new FamilySearch.  The program goes in
	 * and gets up to 9 generations of the user's pedigree.
	 * 
	 */
	public int getInformation(ArrayList<String> getInfoFor) {
       System.out.println("Get Information");
       
		try {
			
			
        	if (getInfoFor == null) {
        		getIdentity();
        		getUserRoot();
        		
        		//add the user to the relMap
        		relMap.add(PersonID, 0, false);

        		
            	
        		getUserPedigree(PersonID);
        	}
        	else
        		waitingQueue = getInfoFor;

        
        	String getInf;
        	while (!waitingQueue.isEmpty()) {
        		getInf  = "";
        		int amt = MAX_CALLS;
        		if (waitingQueue.size() < MAX_CALLS)
        			amt = waitingQueue.size();
        		for (int i = 0; i < amt; i++)
        			getInf += waitingQueue.get(i) + ",";
        		getInf = getInf.substring(0, getInf.length()-1);
        		getIndividualInfo(getInf);
        		//dequeue after the call is made.
        		for (int i = 0; i < amt; i++)
        			waitingQueue.remove(0);

        		ArrayList<String> smToLg = null;
        		if (waitingQueue.size() < MAX_CALLS)
        			smToLg = relMap.getSmToLgNotDone();
        		int counter = 0;
        		//check to see if we should do more pedigree calls
        		while (waitingQueue.size() < MAX_CALLS &&  counter < smToLg.size()) {
        			String nextIDCall = smToLg.get(counter);
        			counter++;
        			if (!waitingQueue.contains(nextIDCall)) {
        				if (relMap.getGeneration(nextIDCall) <= MAX_GENS)
        					getUserPedigree(nextIDCall);
        				else        		
        					break;
        			}
        		}
        	}
         }
        catch (HttpResponseException e){
        	System.out.println("Getting into HttpResponseException");
        	if (e.getStatusCode() == 401)
        		return 1;
        	else if (e.getStatusCode() == 503)
        		throttled(0, waitingQueue, GETINFO);
        	else
        		return 2;
        }
        catch (ClientProtocolException e) {
        	System.out.print("Getting into exception ClientProtocolException\nstacktrace:");
        	return 2;
        }
        catch (IOException e) {
        	System.out.println("Getting into IOException");
        	return 3;
        }
        catch (JAXBException e) {
        	System.out.println("Getting into JAXBException");
        	return 4;
        }
      //  httpclient.getConnectionManager().shutdown();
 
        findPlaceInTree();
        return 0;
	}
	
	/**
	 * The user can choose to download their own pedigree, or type in a Person Identifier
	 * from New FamilySearch and download their pedigree instead.  
	 * @throws IOException
	 * @throws JAXBException
	 */
	public void getUserRoot() throws IOException, JAXBException {
		String[] possibleValues = { "Self", "Enter Person Identifier" };
		String selectedValue = (String) JOptionPane.showInputDialog(null,
				"Do you want to download a personal pedigree\n" +
				"with your information or enter a Person Identifier\n" +
				"of another individual on new FamilySearch?", "Choose Root Individual", 
				JOptionPane.INFORMATION_MESSAGE, null, possibleValues, possibleValues[0]);
		if (selectedValue.equals(possibleValues[0]))
			getUser();
		else {
			PersonID = JOptionPane.showInputDialog("Please input the Person Identifier.\n" +
					"If no one is entered, the User's\npedigree will be downloaded.");
			if (PersonID.equals(""))
				getUser();
			else
				PersonID = PersonID.toUpperCase();
		}
        System.out.println("\n--------");
        System.out.println("Pedigree for: " + PersonID);
	}

	/**
	 * finds the first person/root of the tree, and stores their id
	 * @throws IOException 
	 * @throws JAXBException 
	 */
	public void getUser() throws IOException, JAXBException {
		String requestUrl = baseURL+"/familytree/v2/person";
        String responseBody = httpclient.execute(new HttpGet(requestUrl), responseHandler);
        FamilyTree firstPerson = (FamilyTree) JAXBContext.newInstance(FamilyTree.class).createUnmarshaller().unmarshal(new StringReader(responseBody));
        Person fPerson = firstPerson.getPersons().get(0);
        PersonID = fPerson.getId();
        
	}

	/**
	 * given the personId, this will get the pedigree!
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws JAXBException 
	 */
	public void getUserPedigree(String ID) throws ClientProtocolException, IOException, JAXBException {
		String requestUrl = baseURL+"/familytree/v2/pedigree/";
		requestUrl += ID + "?ancestors=9";
		
		String responseBody = httpclient.execute(new HttpGet(requestUrl), responseHandler);
		FamilyTree familytree = (FamilyTree) JAXBContext.newInstance(FamilyTree.class).createUnmarshaller().unmarshal(new StringReader(responseBody));
		for (Pedigree pedigree : familytree.getPedigrees()) {
			System.out.println();
			System.out.println("-----");
			System.out.println();
			System.out.println("Pedigree for: " + pedigree.getId());
			
			for (Person person : pedigree.getPersons()) {
				if (!(relMap.contains(person.getId()) && relMap.isCompleted(person.getId()))) {
					waitingQueue.add(person.getId());
					System.out.println("Adding: " + person.getId());
//					Family fam = new Family();
					
				}
				
				
			}
			//System.out.println("size: " + waitingQueue.size());
		}
		
		
	}
	
	public void getPersonDescendants(String toGet){
		
	}
	
	/**
	 * given the personID, this will go in and acquire all the info
	 * @throws JAXBException 
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */	
	public void getIndividualInfo(String ID) throws JAXBException, ClientProtocolException, IOException {
		String requestUrl = baseURL+"/familytree/v2/person/";
		requestUrl += ID;
		//request all the information about the person
		requestUrl += "?personas=mine&names=summary&events=summary&ordinances=all&families=summary&parents=summary&children=all";
		
		String responseBody = httpclient.execute(new HttpGet(requestUrl), responseHandler);
		FamilyTree extract = (FamilyTree) JAXBContext.newInstance(FamilyTree.class).createUnmarshaller().unmarshal(new StringReader(responseBody));
		String[] IDS = ID.split(",");
		int IDcount = 0;
		for (Person person: extract.getPersons()) {
			Individual indiNew;
			//change ID so it is the correct one if making multiple calls!
			if(!gedRecord.containsIndividual(IDS[IDcount])) {
				indiNew = new Individual(IDS[IDcount]);
				gedRecord.addIndividual(indiNew.id, indiNew);
			} else
				indiNew = gedRecord.getIndividual(IDS[IDcount]);
			
			indiNew.version = person.getVersion();
			
			indiNew = getNameValues(indiNew, person);
			indiNew = getGender(indiNew, person);
			indiNew = getEventValues(indiNew, person);
			indiNew = getFamilyValues(indiNew, person);
			indiNew = getParentValues(indiNew, person);
			indiNew = getOrdinanceValues(indiNew, person);

			//by this time they should already be in the relMap!!
			/*if (!relMap.contains(indiNew.id))
				System.out.println(indiNew.id + " IS NOT IN THE RELMAP!");
			else*/
				relMap.nowCompleted(indiNew.id);
			
				
			IDcount++;
			
		}
	}

	/**
	 * Iterates through the name values and assigns them to the correct spots.
	 * because new FamilySearch does not use middle names, no middle names are assigned,
	 * and most of the name is stored in given names.
	 */
	private Individual getNameValues(Individual indi, Person p) {
		if (p.getAssertions().getNames() == null)
			return indi;
		indi = initializeNames(indi);
		for (NameAssertion n: p.getAssertions().getNames()) {
			if(n.getValue().getType() == NameType.Name) {
				for (NameForm name: n.getValue().getForms()) {
					String given = "";
					String surname = "";
					for (NamePiece na: name.getPieces()) {
						if (na.getType() == NamePieceType.Given)
							given += na.getValue() + " ";
						else if (na.getType() == NamePieceType.Family)
							surname += na.getValue() + " ";
						else if (na.getType() == NamePieceType.Prefix)
							indi.namePrefix = na.getValue();
						else if (na.getType() == NamePieceType.Suffix)
							indi.nameSuffix = na.getValue();
					}
					given = given.trim();
					indi.givenName = given;
					surname = surname.trim();
					indi.surname = surname;
				}
			}
		}
		return indi;
	}

	/**
	 * goes through each name string and initializes them to zero.  This makes sure that
	 * there are no null pointer exceptions when the program later tries to use the informaiton
	 * @param indi
	 * @return
	 */
	private Individual initializeNames(Individual indi) {
		indi.givenName = "";
		indi.middleName = "";
		indi.surname = "";
		indi.nameSuffix = "";
		indi.surnamePrefix = "";
		indi.namePrefix = "";
		indi.nameSuffix = "";
		
		return indi;
	}

	/**
	 * gets the gender of the individual.  if there are multiple genders, then it will return
	 * the first one.
	 * @param indi
	 * @param p
	 * @return
	 */
	private Individual getGender(Individual indi, Person p) {
		if (p.getAssertions().getGenders() == null)
			return indi;
		GenderAssertion gen = p.getAssertions().getGenders().get(0);
		if (gen.getValue().getType() == GenderType.Male)
			indi.gender = Gender.MALE;
		else if (gen.getValue().getType() == GenderType.Female)
			indi.gender = Gender.FEMALE;
		else
			indi.gender = Gender.UNKNOWN;
		return indi;
	}

	/**
	 * gets the birth and death dates and places of the individual.
	 * The informaiton is left null if there is nothing stored in new FamilySearch
	 * @param indi
	 * @param p
	 * @return
	 */
	private Individual getEventValues(Individual indi, Person p) {
		if (p.getAssertions().getEvents() == null)
			return indi;
		for (EventAssertion event: p.getAssertions().getEvents()) {
			Event ev = null;
			EventValue evalue = event.getValue();
			if (evalue.getType() == org.familysearch.ws.client.familytree.v2.schema.EventType.Birth) {
				ev = new Event(EventType.BIRTH);
				indi.birth = ev;
			}
			else if (evalue.getType() == org.familysearch.ws.client.familytree.v2.schema.EventType.Death) {
				ev = new Event(EventType.DEATH);
				indi.death = ev;
			}
			if (ev != null) {
				if (evalue.getDate() != null)
					ev.date = evalue.getDate().getNormalized();
				if (evalue.getPlace() != null && evalue.getPlace().getNormalized() != null)
					ev.place = evalue.getPlace().getNormalized().getValue();
			}
		}
		
		return indi;
	}

	/**
	 * Creates the Family for the person if their spouse has not already established the family.
	 * stores the children's ids (but doesn't store them in the RelativeMap, we don't want to get
	 * all the children) and also gets the spouse's id (creates a new individual if it is not already
	 * created) and the marriage information (date and place).
	 * 
	 * if this is the main person, add the children into the RelationMap.
	 * @param indi
	 * @param p
	 * @return
	 */
	private Individual getFamilyValues(Individual indi, Person p) {
		for (FamilyReference fam: p.getFamilies()) {
			/* this makes sure that only the first spouse of the family creates a new Family
			 * the other parent is added into the Family in the GedcomRecord.  If the second
			 * spouse tries to create the family first, the method determines if the first
			 * spouse is in the relMap or not, and then adds them if it needs to.
			 */
			if (!(indi.gender == Gender.MALE && fam.getParents().get(0).getGender() == GenderType.Male)
					&& !(indi.gender == Gender.FEMALE && fam.getParents().get(0).getGender() == GenderType.Female)) {
				if (!relMap.contains(fam.getParents().get(0).getId()))
					relMap.add(fam.getParents().get(0).getId(), relMap.getGeneration(indi.id), false);
				return indi;
			}
			String famID;
			if (famCount < 10)
				famID = "0F0" + famCount;
			else if (famCount > 99) {
				int beg = 0;
				int end = famCount;
				while (famCount > 99) {
					beg++;
					end = end - 100;
				}
				famID = beg + "F" + end;
			}
			else
				famID = "0F" + famCount;
			Family newFamily = new Family(famID);
			famCount++;
			indi.famsIds.add(famID);
			if (fam.getChildren() != null) {
				for (PersonReference child : fam.getChildren()) {
					String childId = child.getId();
					if (relMap.contains(childId))
						relMap.add(indi.id, relMap.getGeneration(childId) + 1, false);
					else if (relMap.contains(indi.id))
						relMap.add(childId, relMap.getGeneration(indi.id), false);
					newFamily.childrenXRefIds.add(childId);
					indi.famcIds.add(childId);
				}
			}
			for(PersonReference parent: fam.getParents()) {
				//if the spouse is not in the relMap, add them at the same generation level
				if (!indi.id.equals(parent.getId()) && !relMap.contains(parent.getId())) {
					relMap.add(parent.getId(), relMap.getGeneration(indi.id), false);
				}
				if (parent.getGender() == GenderType.Male) {
					newFamily.husbandId = parent.getId();
					if (gedRecord.getIndividuals().containsKey(parent.getId()))
						newFamily.husband = gedRecord.getIndividuals().get(parent.getId());
					else {
						gedRecord.addIndividual(parent.getId(), new Individual(parent.getId()));
						newFamily.husband = gedRecord.getIndividual(parent.getId());
					}
				} else {
					newFamily.wifeId = parent.getId();
					if (gedRecord.getIndividuals().containsKey(parent.getId()))
						newFamily.wife = gedRecord.getIndividuals().get(parent.getId());
					else {
						gedRecord.addIndividual (parent.getId(), new Individual(parent.getId()));
						newFamily.wife = gedRecord.getIndividual(parent.getId());
					}
				}
			}
			if (fam.getMarriage() != null) {
				EventValue evalue = fam.getMarriage().getValue();
				if (evalue.getType() == org.familysearch.ws.client.familytree.v2.schema.EventType.Marriage) {
					Event marr = new Event(EventType.MARRIAGE);
					if (evalue.getDate() != null)
						marr.date = evalue.getDate().getNormalized();
					if (evalue.getPlace() != null && evalue.getPlace().getNormalized() != null)
						marr.place = evalue.getPlace().getNormalized().getValue();
					newFamily.marriage = marr;
				}
				//currently, i do not know how to get the divorce.  Technically, i could just get ALL
				//the events, however, if a person has a lot of events on their pedigree, this could
				//slow down the download EXTREMELY! But maybe at some point they will make the information
				//readily available and easy for us to grab.  Till then, we will not show it!

				else if (evalue.getType() == org.familysearch.ws.client.familytree.v2.schema.EventType.Divorce)
					newFamily.divorce = true;
			}
			if (indi.fams.size() > 0) {
				for (Family famCheck : indi.fams){
					if (((newFamily.husband != null && famCheck.husband != null && newFamily.husband.id.equals(famCheck.husband.id)) ||
							(newFamily.husband == null && famCheck.husband == null)) && 
							((newFamily.wife != null && famCheck.wife != null &&newFamily.wife.id.equals(famCheck.wife.id)) ||
							(newFamily.wife == null && famCheck.wife == null))){
						famCount--;
						return indi;
					}
				}
			}
			gedRecord.addFamily(newFamily.id, newFamily);
			indi.fams.add(newFamily);
		}
		return indi;
	}

	/**
	 * does not store information about the ordinance, only if they have been performed/completed
	 * yet.  The ordinances it cares about is Baptism, Endowment, Sealing to Spouse, and Sealing
	 * to Parents.
	 * this method is done after the getFamilyValues to make sure the individual already has a family
	 * value created.
	 * @param indi
	 * @param p
	 * @return
	 */
	//because the new.familysearch will not let me put fake ordinance in it's devnet site, there
	//is no way to test this and make sure they are correct.  Because of this, we will need to this this
	//first thing when we can run it on the actual site.  VERY important that we get this going together!
	private Individual getOrdinanceValues(Individual indi, Person p) {
		if (p.getAssertions().getOrdinances() == null)
			return indi;
		for (OrdinanceAssertion ordinance: p.getAssertions().getOrdinances()) {
			if (ordinance.getValue().getType() == OrdinanceType.Baptism) {
				indi.baptism = true;
				indi.baptismComplete = true;
			}
			else if (ordinance.getValue().getType() == OrdinanceType.Born_in_Covenant
					|| ordinance.getValue().getType() == OrdinanceType.Sealing_to_Parents) {
				indi.sealingToParents = true;
				indi.sealingToParentsComplete = true;
			}
			else if (ordinance.getValue().getType() == OrdinanceType.Endowment) {
				indi.endowment = true;
				indi.endowmentComplete = true;
			}
			else if (ordinance.getValue().getType() == OrdinanceType.Sealing_to_Spouse) {
				indi.sealingToSpouse = true;
				indi.sealingToSpouseComplete = true;
				for (int i = 0; i < indi.fams.size(); i++) {
					Family parentID = indi.fams.get(i);
					if (parentID.husbandId == indi.id || parentID.wifeId == indi.id) {
						parentID.sealing = true;
						parentID.sealingComplete = true;
					}
				}
			}
			/*where do we get the information if the marriage was annuled?
							we also need a boolean, so if this comes before the sealing,
							it won't be marked as true
			else if (ordinance.getValue().getType() == OrdinanceType.Marriage_Annuled) {
				indi.sealingToSpouse = false;
				indi.sealingToSpouseComplete = false;
				for (int i = 0; i < indi.fams.size(); i++) {
					Family parentID = indi.fams.get(i);
					if (parentID.husbandId == indi.id || parentID.wifeId == indi.id) {
						parentID.sealing = false;
						parentID.sealingComplete = false;
						//is this needed?  (could not be...tricky to say when i cannot exactly text this)
						for (int j = 0; j < parentID.children.size(); j++) {
							Individual child = parentID.children.get(j);
							child.sealingToParents = false;
							child.sealingToParents.complete = false;
						}
					}
				}
				
			}*/
		}
		return indi;
	}

	
	/**
	 * Stores/creates individuals for the person's parents.
	 * @param indi
	 * @param p
	 * @return
	 */
	private Individual getParentValues(Individual indi, Person p) {
		if (p.getParents() == null)
			return indi;
		for (ParentsReference parents: p.getParents()) {
			for (ParentReference parent: parents.getParents()) {
				//check to see if the parents are done (this is for use with descendants)
				if (relMap.contains(parent.getId()) && !relMap.contains(indi.id))
					relMap.add(indi.id, relMap.getGeneration(parent.getId())-1, false);
				//adds the parent into relMap
				if (!relMap.contains(parent.getId()))
					relMap.add(parent.getId(), relMap.getGeneration(indi.id)+1, false);

				if (parent.getGender() == GenderType.Male && !gedRecord.containsIndividual(parent.getId())) {
					gedRecord.addIndividual(parent.getId(), new Individual(parent.getId()));
					indi.father = gedRecord.getIndividual(parent.getId());
				}
				else if (parent.getGender() == GenderType.Male && gedRecord.containsIndividual(parent.getId()))
					indi.father = gedRecord.getIndividual(parent.getId());	
				else if (parent.getGender() == GenderType.Female && !gedRecord.containsIndividual(parent.getId())) {
					gedRecord.addIndividual(parent.getId(), new Individual(parent.getId()));
					indi.mother = gedRecord.getIndividual (parent.getId());
				}
				else if (parent.getGender() == GenderType.Female && gedRecord.containsIndividual(parent.getId()))
						indi.mother = gedRecord.getIndividual(parent.getId());	
					

				//instead of relying on getUserPedigree(), this will automatically add the parents
				//into the waitingQueue, which will mean less calls for pedigree's.
				if (!waitingQueue.contains(parent.getId())) {
					if (relMap.getGeneration(indi.id) < MAX_GENS || afterNine)
						waitingQueue.add(parent.getId());
					//else if (relMap.getGeneration(indi.id) >= MAX_GENS && !afterNine)
						//afterNineQueue.add(parent.getId());
				}
			}
		}
		return indi;
	}

	/**
	 * this goes through and for every person, figures out where they are in the tree.
	 * it also determines the total amount of people in the generation line.  all this information
	 * is then stored in the individual.
	 */
	public void findPlaceInTree() {
		int youngest = relMap.getYoungestGeneration();
		int oldest = relMap.getOldestGeneration();
		int total = oldest - youngest + 1;

		ArrayList<String> allIDs = relMap.getAllDone();
		for (int i = 0; i < allIDs.size(); i++) {
			String indiID = allIDs.get(i);
			Individual indi = gedRecord.getIndividual(indiID);
			indi.totalPathLength = total;
			indi.numberOfAncestors = oldest - relMap.getGeneration(indiID);
			indi.numberOfDescendants = relMap.getGeneration(indiID) - youngest;	
		//	System.out.println(i + " Indi: " + indi.id + " ances: " + indi.numberOfAncestors +
		//			"\ndescen: " + indi.numberOfDescendants + " total: " + indi.totalPathLength);
		}
	}
	
	/**
	 * This method is only called if the user has a family tree larger than 9 generations, and wants everyone
	 * in there tree stored in this One Page Genealogy file.  
	 * Currently, there are no limits as to how far back this method will run, it will go until all the
	 * people are added into One Page Genealogy.  So it could very possibly take a very long time... 
	 * @return gedRecord
	 */
	public int continueAfterNine(ArrayList<String> toGetQueue) {
		continuedAfterNine = true;
		try {
        	afterNine = true;
        	ArrayList<String> notDone = relMap.getAllNotDone();
        	if (toGetQueue == null) {
        		for (int i = 0; i < notDone.size(); i++)
        			waitingQueue.add(notDone.get(i));
        	}
        	else
        		waitingQueue = toGetQueue;
        	
        	String getInf;
        	while (!waitingQueue.isEmpty()) {
        		getInf  = "";
        		//dequeue after the call is made.  this will allow the program to see
        		//if the ID is being processed or has already been called!
        		int amt = MAX_CALLS;
        		if (waitingQueue.size() < MAX_CALLS)
        			amt = waitingQueue.size();
        		for (int i = 0; i < amt; i++)
        			getInf += waitingQueue.get(i) + ",";
        		getInf = getInf.substring(0, getInf.length()-1);
        		getIndividualInfo(getInf);
        		for (int i = 0; i < amt; i++)
        			waitingQueue.remove(0);

        		ArrayList<String> smToLg = null;
        		if (waitingQueue.size() < MAX_CALLS)
        			smToLg = relMap.getSmToLgNotDone();
        		int counter = 0;
        		//check to see if we should do more pedigree calls
        		while (waitingQueue.size() < MAX_CALLS &&  counter < smToLg.size()) {
        			String nextIDCall = smToLg.get(counter);
        			counter++;
        			if (!waitingQueue.contains(nextIDCall))
       					getUserPedigree(nextIDCall);
        		}
        	}
        }
        catch (HttpResponseException e){
        	System.out.println("Getting into HttpResponseException");
        	System.out.println("code: " + e.getStatusCode());
        	if (e.getStatusCode() == 401)
        		return 1;
        	if (e.getStatusCode() == 503) {
        		throttled(0, waitingQueue, AFTER9);
        	}
        	else
        		return 2;
        }
        catch (ClientProtocolException e) {
        	System.out.print("Getting into exception ClientProtocolException\nstacktrace:");
        	return 2;
        }
        catch (IOException e) {
        	System.out.println("Getting into IOException");
        	return 3;
        }
        catch (JAXBException e) {
        	System.out.println("Getting into JAXBException");
        	return 4;
        }
        findPlaceInTree();
        return 0;
	}
	
		
	/**
	 * This would be only called from a drop down menu.  it goes through the whole tree and
	 * checks for changes.  If there are any, then the people's information is then asked for again,
	 * and updated.  Also checks the leaf people to see if anyone else has been added to the list since
	 * the last time it was updated.
	 * The user can also decide if they have yet to add their family members beyond 9 gens if
	 * they want to add them now.  it is not done immediately, however.
	 */
	public void update(int c, ArrayList<String> updateQueue) {
		ArrayList<String> getAllVersions = new ArrayList<String>();
		
		getAllVersions = getVersionNumber(GetRoot(), getAllVersions);
		//get spouses families' versions
		Individual mainPerson = gedRecord.getIndividual(PersonID);
		if (mainPerson.gender == Gender.MALE)
			getAllVersions = getVersionNumber(mainPerson.fams.get(0).wife, getAllVersions);
		else //mainPerson.gender == Gender.FEMALE
			getAllVersions = getVersionNumber(mainPerson.fams.get(0).husband, getAllVersions);
		
		
		ArrayList<String> toBeUpdated;
		if (updateQueue == null) {
			continued = false;
			toBeUpdated = new ArrayList<String>();
		}
		else
			toBeUpdated = updateQueue;
		/*
		 * This is where the user decides they want to continue going back if they still 
		 * have more family to download.  If they do, then continueAfterNine() is called
		 * to do the work.
		 */
		if (afterNine && !continuedAfterNine) {
			int choice = JOptionPane.showConfirmDialog(null,
					"Nine generations were previously updated.\n" +
					"Do you want to update the rest of the pedigree?",
					"Continue Updating", JOptionPane.YES_NO_OPTION);
			if (choice == 0) {
				continued = true;
				continueAfterNine(null);
			}
		}
		String idString;
		int maxGet = 10;
		int count = c;
		while (count < getAllVersions.size()) {
			idString = "";
			if ((getAllVersions.size() - count) < maxGet)
				maxGet = getAllVersions.size() - count;
			for (int i = 0; i < maxGet; i++) {
				idString += getAllVersions.get(i + count) + ",";
			}
			idString = idString.substring(0, idString.length()-1);
			ArrayList<String> toUpdate = getVersion(idString);
			if (!toUpdate.isEmpty() && toUpdate.get(toUpdate.size()-1).equals("503 error")) {
				toUpdate.remove(toUpdate.size()-1);
				throttled(count, toBeUpdated, UPDATE);
				break;
			}
			count += maxGet;
			for (int i = 0; i < toUpdate.size(); i++)
				toBeUpdated.add(toUpdate.remove(0));
		}
		//check to make sure all the names were parsed (might be throttled and in a loop)
		if (count == getAllVersions.size()) {
				if (!toBeUpdated.isEmpty()) {
					getInformation(toBeUpdated);
				}
				else if (!continued){
					JOptionPane.showMessageDialog(null, 
							"There were no Updates Found",
							"No Updates", JOptionPane.INFORMATION_MESSAGE);
				}
		}
		if (!toBeUpdated.isEmpty()) {
			for (int i = 0; i < toBeUpdated.size(); i++)
				System.out.println(toBeUpdated.get(i));
		}
	}
	
	/**
	 * A recursive method that grabs the version numbers of everyone in the pedigree
	 */
	public ArrayList<String> getVersionNumber(Individual indi, ArrayList<String> versionNumbers) {
		if (indi != null) {
			if (indi.version != "")
				versionNumbers.add(indi.id);
			versionNumbers = getVersionNumber(indi.father, versionNumbers);
			versionNumbers = getVersionNumber(indi.mother, versionNumbers);
		}			
		return versionNumbers;
		
	}

	/**
	 * called by update(), this method asks new.familysearch.org for all the versions of the people in our tree.
	 * if they are different than the ones stored, then the person keep in a list that is passed back to update()
	 */	
	public ArrayList<String> getVersion(String ID) {
		ArrayList<String> toUpdate = new ArrayList<String>();
		String requestUrl = baseURL+"/familytree/v2/person/";
		requestUrl += ID;
		//requests none of the information about the person (just want the version, which won't take as long)
		requestUrl += "?names=none&genders=none&events=none";
		try {
			String responseBody = httpclient.execute(new HttpGet(requestUrl), responseHandler);
			FamilyTree versions = (FamilyTree) JAXBContext.newInstance(FamilyTree.class).createUnmarshaller().unmarshal(new StringReader(responseBody));
			String[] IDS = ID.split(",");
			int count = 0;
			for (Person person : versions.getPersons())
				if (!gedRecord.getIndividual(IDS[count]).version.equals(person.getVersion())) {
					toUpdate.add(IDS[count]);
				count++;
			}
		}
		catch (HttpResponseException e) {
			System.out.println("ID: " + ID);
			System.out.println("Getting into HttpResponseException.");
			if (e.getStatusCode() == 503)
				toUpdate.add("503 error");
		}
		catch (ClientProtocolException e) {
			System.out.println("ID: " + ID);
			System.out.println("Getting into ClientProtocolException");
		}
		catch (IOException e) {
			System.out.println("Getting into IOException");
		}
		catch (JAXBException e) {
			System.out.println("Getting into JAXBException");
		}
		return toUpdate;
	}
	
	/**
	 * when the program gets throttled, this method takes care of pausing for the 
	 * correct amount of time (if it gets throttled multiple times on the same spot,
	 * then the program waits longer (up to 31 seconds) each time until it is finally
	 * allowed to return to business.
	 */
	synchronized void throttled(int count, ArrayList<String> queue, int method) {
		int waitTime[] = {5, 10, 20, 31};		
		try {
			System.out.println("Program Throttled on " + method + ". waiting " + waitTime[throttledAmt] + " seconds");
			wait(waitTime[throttledAmt] * 1000);
		} catch (InterruptedException e) {
			System.out.println("Getting into InterruptedException");
		}
		//this checks to see if it is getting throttled at the same spot or further along in the retrieval
		if (throttledAmt < (waitTime.length - 1) && firstOfQueue.equals(queue.get(0)))
			throttledAmt++;
		firstOfQueue = queue.get(0);
		switch (method) {
			case (UPDATE):
				update(count, queue);
				break;
			case (GETINFO):
				getInformation(queue);
				break;
			case (AFTER9):
				continueAfterNine(queue);
				break;
		}
		
	}
	
	
}



 






