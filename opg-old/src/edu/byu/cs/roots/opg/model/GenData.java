package edu.byu.cs.roots.opg.model;

import java.util.Iterator;

import edu.byu.cs.roots.opg.model.Family;
import edu.byu.cs.roots.opg.model.Gender;
import edu.byu.cs.roots.opg.model.Individual;
import edu.byu.cs.roots.opg.util.IterableHashMap;

public class GenData {

	IterableHashMap<String,Family> families = new IterableHashMap<String,Family>();
	IterableHashMap<String,Individual> individuals = new IterableHashMap<String,Individual>();
	
	public boolean hasLDSOrdinanceData = false;
//	private boolean isLinked = false;
	
	public void addFamily(Family fam)
	{
		families.put(fam.id,fam);
	}
	
	public void addIndividual(Individual indi)
	{
		individuals.put(indi.id, indi);
	}
		
	public void link()
	{
		//link individuals
		Individual indi;
		
		Iterator<Individual> it = individuals.iterator();
		while (it.hasNext())
		{
			indi = it.next();
			//link fams to individuals
			Iterator<String> famIterator = indi.famsIds.iterator();
			boolean sealingToSpouseComplete = false;
			boolean sealingToSpouse = false;
			if (indi.gender == Gender.MALE)
			{
				sealingToSpouseComplete = true;
				sealingToSpouse = true;
			}
			while (famIterator.hasNext())
			{
				Family curFam =  families.get(famIterator.next());
				indi.fams.add(curFam);
				
				//determine sealing to spouse status
				//: this part is broken
				if (indi.gender == Gender.MALE)
				{
					if (sealingToSpouseComplete && !curFam.sealingComplete)
						sealingToSpouseComplete = false;
					if (sealingToSpouse && !curFam.sealing)
					{
						sealingToSpouse = false;
						sealingToSpouseComplete = false;
					}
				}else if (indi.gender == Gender.FEMALE)
				{
					if (!sealingToSpouseComplete && curFam.sealingComplete)
					{
						sealingToSpouse = true;
						sealingToSpouseComplete = true;
					}
					if (!sealingToSpouse && curFam.sealing)
						sealingToSpouse = true;
				}
			}
			indi.sealingToSpouse = sealingToSpouse;
			indi.sealingToSpouseComplete = sealingToSpouseComplete;
			
			//link famc to individuals
			famIterator = indi.famcIds.iterator();
			while (famIterator.hasNext())
			{
				indi.famc.add(families.get(famIterator.next()));
			}
			//link mother and father
			if (indi.primaryParents >= 0)
			{
				indi.father = individuals.get(families.get(indi.famcIds.get(indi.primaryParents)).husbandId);
				indi.mother = individuals.get(families.get(indi.famcIds.get(indi.primaryParents)).wifeId);
			}
			
			//is there LDS Ordinance data?
			if (!hasLDSOrdinanceData && (indi.baptismComplete || indi.endowmentComplete || indi.sealingToParentsComplete || indi.sealingToSpouseComplete) )
				hasLDSOrdinanceData = true;
			
			//clear out ids that have been linked in
			indi.famcIds = null;
			indi.famsIds = null;
		}
		
		//link families
		Family fam;
		Iterator<Family> itF = families.iterator();
		while (itF.hasNext())
		{
			fam = itF.next();
			//link children to family
			Iterator<String> childIterator = fam.childrenXRefIds.iterator();
			while (childIterator.hasNext())
			{
				fam.children.add(individuals.get(childIterator.next()));
			}
			//link in husband and wife
			fam.husband = individuals.get(fam.husbandId);
			fam.wife = individuals.get(fam.wifeId);
						
			//is there LDS Ordinance data?
			if (!hasLDSOrdinanceData && fam.sealing)
				hasLDSOrdinanceData = true;
			
			//clear out ids that have been linked in
			fam.childrenXRefIds = null;
		}
	
//		isLinked = true;
	}
	
	
}
