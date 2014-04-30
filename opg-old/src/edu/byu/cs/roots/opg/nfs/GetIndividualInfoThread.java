package edu.byu.cs.roots.opg.nfs;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
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
import org.familysearch.ws.client.familytree.v2.schema.Person;
import org.familysearch.ws.client.familytree.v2.schema.PersonReference;

import edu.byu.cs.roots.opg.io.GedcomRecord;
import edu.byu.cs.roots.opg.model.Event;
import edu.byu.cs.roots.opg.model.EventType;
import edu.byu.cs.roots.opg.model.Family;
import edu.byu.cs.roots.opg.model.Gender;
import edu.byu.cs.roots.opg.model.Individual;

public class GetIndividualInfoThread extends Thread{

	private String baseURL, ID;
	private GedcomRecord gedRecord;
	private RelationMap relMap;
	private int choiceGens;
	private boolean choiceAllDescendants, choiceRootDescendants;
	private NFSDownloadThread parent;
	
	public GetIndividualInfoThread(NFSDownloadThread parent, GedcomRecord gedRecord, RelationMap relMap, String baseURL, String ID, int choiceGens, boolean choiceAllDescendants, boolean choiceRootDescendants){
		this.setDaemon(true);
		this.baseURL = baseURL;
		this.ID = ID;
		this.gedRecord = gedRecord;
		this.relMap = relMap;
		this.choiceGens = choiceGens;
		this.choiceAllDescendants = choiceAllDescendants;
		this.choiceRootDescendants = choiceRootDescendants;
		this.parent = parent;
	}
	public void run(){
		this.setName("Get Info: " + ID);
		String requestUrl = baseURL+"/familytree/v2/person/";
		requestUrl += ID;
		//request all the information about the person
		requestUrl += "?personas=mine&names=summary&events=summary&ordinances=all&families=summary&parents=summary&children=all";
		
		parent.updatePublished("Getting information for: " + ID);
		System.out.println("Getting information for: " + ID);
		
		BasicResponseHandler responseHandler = new BasicResponseHandler();
		String responseBody;
		FamilyTree extract = null;
		try {
			responseBody = parent.httpclient.execute(new HttpGet(requestUrl), responseHandler);
			extract = (FamilyTree) JAXBContext.newInstance(FamilyTree.class).createUnmarshaller().unmarshal(new StringReader(responseBody));
			
		} catch (HttpResponseException e) {
			if (e.getStatusCode() == 503){
				//Throttled
				parent.updatePublished("Throttled!");
			}
			e.printStackTrace();
		} catch (JAXBException e){
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
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
			indiNew = assignIndiAsParent(indiNew, person);

			//by this time they should already be in the relMap!!
			/*if (!relMap.contains(indiNew.id))
				System.out.println(indiNew.id + " IS NOT IN THE RELMAP!");
			else*/
			relMap.nowCompleted(indiNew.id);
			
				
			IDcount++;
			parent.updatePublished("Processed: " + indiNew.givenName + " (" + indiNew.id + ")");
			System.out.println("Processed: " + indiNew.givenName + " (" + indiNew.id + ")");
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
		if (indi.birth != null)
			indi.birth.parseDateParts();
		if (indi.death != null)
			indi.death.parseDateParts();
		
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
			int localFamCount = parent.getFamCount();
			if (localFamCount < 10)
				famID = "0F0" + localFamCount;
			else if (localFamCount > 99) {
				int beg = 0;
				int end = localFamCount;
				
				while(end > 99){
					beg++;
					end = end - 100;
				}
				
				String middle = "F";
				if (end < 10)
					middle = "F0";
				famID = beg + middle + end;
			}
			else
				famID = "0F" + localFamCount;
			Family newFamily = new Family(famID);
			parent.incFamCount();
			indi.famsIds.add(famID);
			if (fam.getChildren() != null) {
				for (PersonReference child : fam.getChildren()) {
					String childId = child.getId();
					if (relMap.contains(childId))
					{
						System.out.println("Adding "+indi.id+" as parent to "+childId);
						relMap.add(indi.id, relMap.getGeneration(childId) + 1, false);
					}
					//Here's where descendants get downloaded
					else if (relMap.contains(indi.id))
					{
						if (choiceAllDescendants){
							relMap.add(childId, relMap.getGeneration(indi.id), false);
						}
						else if (choiceRootDescendants){
							if (relMap.isRootDescendant(indi.id))
							{
								relMap.add(childId, relMap.getGeneration(indi.id), false);
								relMap.addRootDescendant(childId);
							}
						}
						
					}
					newFamily.childrenXRefIds.add(childId);
					indi.famcIds.add(childId);
					
				}
			}
			for(PersonReference parent: fam.getParents()) {
				//if the spouse is not in the relMap, add them at the same generation level
				if (!indi.id.equals(parent.getId()) && !relMap.contains(parent.getId())) {
					System.out.println("Adding "+parent.getId()+" as spouse");
					relMap.add(parent.getId(), relMap.getGeneration(indi.id), false);
				}
				if (parent.getGender() == GenderType.Male && relMap.getGeneration(indi.id) < choiceGens) {
					newFamily.husbandId = parent.getId();
					if (gedRecord.getIndividuals().containsKey(parent.getId()))
						newFamily.husband = gedRecord.getIndividuals().get(parent.getId());
					else {
						gedRecord.addIndividual(parent.getId(), new Individual(parent.getId()));
						newFamily.husband = gedRecord.getIndividual(parent.getId());
					}
				} else if (relMap.getGeneration(indi.id) < choiceGens){
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
						parent.decFamCount();
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
	//TODO because the new.familysearch will not let me put fake ordinance in it's devnet site, there
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
				//check to see if the parents are done
				if (relMap.contains(parent.getId()) && !relMap.contains(indi.id))
					relMap.add(indi.id, relMap.getGeneration(parent.getId())-1, false);
				//adds the parent into relMap
				if (!relMap.contains(parent.getId()) && relMap.getGeneration(indi.id) < choiceGens)
				{
					System.out.println("Adding "+parent.getId()+" as parent again");
					relMap.add(parent.getId(), relMap.getGeneration(indi.id)+1, false);
				}

				if(relMap.getGeneration(indi.id) < choiceGens){
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
					
				}
				//instead of relying on getUserPedigree(), this will automatically add the parents
				//into the waitingQueue, which will mean less calls for pedigree's.
				
				
//				if (!(relMap.contains(parent.getId()) && relMap.isCompleted(parent.getId()))) {
//					if (!waitingQueue.contains(parent.getId()) && relMap.getGeneration(indi.id) < options.getChoiceGens()){
//							waitingQueue.add(parent.getId());
//							System.out.println("Adding: " +parent.getId() + " as parent");
//					}
//					
//				}
					
					//else if (relMap.getGeneration(indi.id) >= MAX_GENS && !afterNine)
						//afterNineQueue.add(parent.getId());
				
			}
		}
		return indi;
	}
	
	private Individual assignIndiAsParent(Individual indi, Person p){
		for(FamilyReference fam : p.getFamilies()){
			for(PersonReference child : fam.getChildren()){
				if(gedRecord.containsIndividual(child.getId())){
					if(indi.gender == Gender.MALE){
						gedRecord.getIndividual(child.getId()).father = indi;
					}
					else if(indi.gender == Gender.FEMALE){
						gedRecord.getIndividual(child.getId()).mother = indi;
					}
				}
			}
		}
		return indi;
	}
}
