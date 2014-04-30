package edu.byu.cs.roots.opg.io;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import java.util.Map.Entry;

import edu.byu.cs.roots.opg.model.Family;
import edu.byu.cs.roots.opg.model.Individual;
import edu.byu.cs.roots.opg.nfs.RelationMap;

public class GedcomRecord implements Serializable
{
	
	private static final long serialVersionUID = 1000L;
	private HashMap<String, Individual> individuals;
	private HashMap<String, Family> families;
	private boolean isNFS;
	private RelationMap relMap;
	
	public GedcomRecord(){
		this.individuals = new HashMap<String, Individual>();
		this.families = new HashMap<String, Family>();
		setNFS(false);
		relMap = null;
	}
		
	public void linkRecord(){
		System.out.println("Linking...");
		linkFamiliesToIndividuals();
//		linkChildrenToParents();
//		linkSpouseToFamily();

//		linkIndividualsToFamilies();
//		linkParents();
		//calculateRelatives();
	}
	

/*	private void calculateRelatives() {
		Collection<Individual> indis = individuals.values();
		for(Individual ind : indis){
			ind.numberOfDescendants = calcDescendants(ind);
		}
	}*/


/*	private int calcDescendants(Individual ind) {
		ArrayList<Family> childrenFams = ind.fams;
		int descendants = 0;
		for(Family children : childrenFams){
			descendants += children.children.size();
			for(Individual child : children.children){
				if(child == null){
					continue;
				}
				if(child.numberOfDescendants < 0)
					descendants += calcDescendants(child);
				else descendants += child.numberOfDescendants;	
			}
		}
		return descendants;
	}*/

	/**
	 * Used after downloading a family from new.familysearch.org.  This goes through
	 * after receiving all the information, and fills up the children ArrayLists in each
	 * family (if only the id is found and not an actual indi, then the child is not added,
	 * and the childID is deleted from the childXRefIds.
	 * THIS COULD POSE A PROBLEM!!! User's kids are not downloaded (so they would be deleted)
	 * but then we want them for the descendancy (we might want to just start with the user
	 * again, get all of their spouses (&families=all) and then get the children's id's again.
	 */
	public void linkChildrenToParents() {
		Collection<String> indiIds = individuals.keySet();
		for(String indiID : indiIds) {
			Individual indi = individuals.get(indiID);
			for (Family fam : indi.fams) {
				for (String childID : fam.childrenXRefIds) {
					//delete the children!
					Individual child = individuals.get(childID);
					if (child != null)
						fam.children.add(child);
					else
						fam.childrenXRefIds.remove(child);
				}
			}
		}
	}
	/**
	 * Used by the OpgSession, this goes through each family and makes sure that the 
	 * both parents point to the family, not just one.
	 */
	public void linkSpouseToFamily() {
		Collection<String> famIds = families.keySet();
		for (String famID : famIds) {
			Family fam = families.get(famID);
			Individual currentFamIndi = individuals.get(fam.husbandId);
			if (currentFamIndi != null && !currentFamIndi.famsIds.contains(fam.id)) {
				currentFamIndi.famsIds.add(fam.id);
				currentFamIndi.fams.add(fam);
			}
			currentFamIndi = individuals.get(fam.wifeId);
			if (currentFamIndi != null && !currentFamIndi.famsIds.contains(fam.id)){
				currentFamIndi.famsIds.add(fam.id);
				currentFamIndi.fams.add(fam);
			}
		}
	}
	
	public void printAllFamilies() {
		System.out.println("family size: " + families.size());
		for (int id = 0; id < families.size(); id++) {
			Family famOut;
			if (id < 10)
				famOut = families.get("0F0" + id);
			else
				famOut = families.get("0F" + id);
			System.out.println("id: " + id);
			System.out.println(famOut.id);
			System.out.println("father: " + famOut.husbandId);
			System.out.println("mother: " + famOut.wifeId);
		}
	}
	
	private void linkFamiliesToIndividuals(){
		Collection<String> familyIds = families.keySet();
		ArrayList<String> childIds;
		Family family;
		inflateIndividuals();
		for(String id : familyIds)
		{
			family = families.get(id);
			childIds = family.childrenXRefIds;
			family.children = new ArrayList<Individual>();
			
			//retrieve spouses
			family.husband = individuals.get(family.husbandId);
			family.wife = individuals.get(family.wifeId);
			
			 //add family to wife
			if(family.wife != null)
				family.wife.fams.add(family);
			
			
			//add family to husband
			if(family.husband != null) 
				family.husband.fams.add(family);
			
			
			//sealing information
			if (family.sealing)
			{
				if (family.husband != null)
					family.husband.sealingToSpouse = true;
				if (family.wife != null)
					family.wife.sealingToSpouse = true;
			}
			if (family.sealingComplete)
			{
				if (family.husband != null)
					family.husband.sealingToSpouseComplete = true;
				if (family.wife != null)
					family.wife.sealingToSpouseComplete = true;
			}
			
			
			for(String childId : childIds){
				Individual child = individuals.get(childId);
				if(child != null && !family.children.contains(child))
				{
					family.children.add(child);
					child.mother = family.wife;
					child.father = family.husband;
				}
			}
		}
	}
	
	public void inflateIndividuals()
	{
		for(Entry<String,Individual> e : individuals.entrySet())
			e.getValue().fams = new ArrayList<Family>();
	}
	
	
	
	//ACCESSOR METHODS
	public HashMap<String, Family> getFamilies() {
		return families;
	}

	public void setFamilies(HashMap<String, Family> families) {
		this.families = families;
	}

	public HashMap<String, Individual> getIndividuals() {
		return individuals;
	}

	public void setIndividuals(HashMap<String, Individual> individuals) {
		this.individuals = individuals;
	}
	
	public synchronized Family getFamily(String famId){
		return families.get(famId);
	}
	
	public synchronized Individual getIndividual(String indId){
		return individuals.get(indId);
	}
	
	public synchronized void addFamily(String famId, Family family){
		families.put(famId, family);
	}
	
	public synchronized void addIndividual(String indId, Individual individual){
		if(!individuals.containsKey(indId))
			individuals.put(indId, individual);
	}
	
	public synchronized boolean containsIndividual(String indId) {
		return individuals.containsKey(indId);
	}

	public void setRelationMap(RelationMap relMap) {
		this.relMap = relMap;
	}

	public RelationMap getRelationMap() {
		return relMap;
	}

	public void setNFS(boolean isNFS) {
		this.isNFS = isNFS;
	}

	public boolean isNFS() {
		return isNFS;
	}
}





